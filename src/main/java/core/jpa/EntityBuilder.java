package core.jpa;

import com.google.common.collect.Maps;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.builder.AbstractAttributeBuilder;
import core.jpa.attribute.builder.AttributeBuilder;
import core.jpa.entity.AbstractEntity;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Component
public class EntityBuilder {

    private Map<String, AttributeBuilder> attributeBuilders;

    /**
     * Params for generate entity
     */
    public static class EntityParam {
        private String entityName;
        private String tablePrefix;
        private String tablePostfix;
        private Collection<Attribute> attributes;

        EntityParam(String entityName) {
            this.entityName = entityName;
        }

        EntityParam() {
        }

        public String getTablePrefix() {
            return tablePrefix;
        }

        public void setTablePrefix(String tablePrefix) {
            this.tablePrefix = tablePrefix;
        }

        public String getTablePostfix() {
            return tablePostfix;
        }

        public void setTablePostfix(String tablePostfix) {
            this.tablePostfix = tablePostfix;
        }

        public String getEntityName() {

            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public Collection<Attribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(Collection<Attribute> attributes) {
            this.attributes = attributes;
        }

        public String getTableName() {
            if (null == entityName) {
                throw new EntityBuildException(EntityBuildException.ExceptionCauses.NAME_IS_EMPTY);
            }
            return new StringBuilder()
                    .append(null == tablePrefix ? "" : tablePrefix)
                    .append(entityName)
                    .append(null == tablePostfix ? "" : tablePostfix)
                    .toString();
        }
    }

    /**
     * Exception for entity generator
     */
    static private class EntityBuildException extends RuntimeException {
        enum ExceptionCauses {
            NAME_IS_EMPTY("Entity name is empty, use setEntityName(entityName)"),
            ENTITY_ALREADY_EXIST("Entity already exist"),
            UNKNOWN_ATTRIBUTE("Unknown attribute");

            private String cause;

            ExceptionCauses(String cause) {
                this.cause = cause;
            }

            public String getCause() {
                return cause;
            }
        }

        EntityBuildException(String message) {
            super(message);
        }

        EntityBuildException(ExceptionCauses cause) {
            super(cause.getCause());
        }
    }

    public Class<?> build(EntityParam param) {
        if (null == param.entityName) {
            throw new EntityBuildException(EntityBuildException.ExceptionCauses.NAME_IS_EMPTY);
        }
        if (null != RuntimeHelper.getCtClass(param.entityName)) {
            throw new EntityBuildException(EntityBuildException.ExceptionCauses.ENTITY_ALREADY_EXIST);
        }

        CtClass abstractEntity = RuntimeHelper.getCtClass(AbstractEntity.class.getName());
        CtClass ctClass = RuntimeHelper.getClassPool().makeClass(param.entityName, abstractEntity);
        setEntityNameField(ctClass, param.entityName);
        setEntityAnnotations(ctClass, param.getTableName());

        for (Attribute attribute : param.getAttributes()) {
            String typeCode = attribute.getType().getTypeCode();
            if(!getAttributeBuilders().containsKey(typeCode)){
                throw new EntityBuildException(EntityBuildException.ExceptionCauses.UNKNOWN_ATTRIBUTE);
            }
            getAttributeBuilders().get(typeCode).build(ctClass, attribute);
        }

        try {
            return ctClass.toClass();
        } catch (CannotCompileException e) {
            throw new EntityBuildException(e.getMessage());
        }
    }

    public static void setEntityNameField(CtClass ctClass, String entityName) {
        try {
            CtField fieldEntityName = new CtField(RuntimeHelper.getClassPool().get(String.class.getName()), "entityName", ctClass);
            fieldEntityName.setModifiers(Modifier.PRIVATE + Modifier.FINAL);

            CtMethod entityNameGetter = CtNewMethod.getter("getEntityName", fieldEntityName);

            ctClass.addField(fieldEntityName, CtField.Initializer.constant(entityName));
            ctClass.addMethod(entityNameGetter);
        } catch (CannotCompileException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setEntityAnnotations(CtClass ctClass, String tableName) {
        ClassFile ccFile = ctClass.getClassFile();
        ConstPool constPool = ccFile.getConstPool();

        Annotation annotationEntity = new Annotation(Entity.class.getName(), constPool);
        Annotation annotationTable = new Annotation(Table.class.getName(), constPool);
        annotationTable.addMemberValue("name", new StringMemberValue(tableName, constPool));

        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        annotationsAttribute.addAnnotation(annotationEntity);
        annotationsAttribute.addAnnotation(annotationTable);

        ccFile.addAttribute(annotationsAttribute);
    }

    private Map<String, AttributeBuilder> getAttributeBuilders() {
        if (null == attributeBuilders) {
            attributeBuilders = Maps.newHashMap();
            Reflections reflections = new Reflections("core");
            Set<Class<? extends AbstractAttributeBuilder>> builders = reflections.getSubTypesOf(AbstractAttributeBuilder.class);
            for (Class<? extends AttributeBuilder> builder : builders) {
                try {
                    AttributeBuilder instance = builder.newInstance();
                    attributeBuilders.put(instance.getTypeCode(), instance);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return attributeBuilders;
    }

}
