package core.jpa.entity.building;

import com.google.common.collect.Sets;
import core.jpa.entity.EntityBlank;
import core.jpa.RuntimeHelper;
import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeType;
import core.jpa.entity.building.builders.BuilderAttribute;
import core.jpa.entity.entities.AbstractEntity;
import core.jpa.entity.entities.NamedEntity;
import core.utils.ClassUtils;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for build Entity Class - class with annotation Entity
 */
@Component
public class BuilderService {

    private Set<BuilderAttribute> builderAttributes;


    public BuilderService(){
        initializeAttributeBuilders();
    }
    /**
     * Exception for entity builder
     */
    public static class BuilderException extends RuntimeException {
        public enum ExceptionCauses {
            NAME_IS_EMPTY("Code is empty, use setCode(code)"),
            ENTITY_ALREADY_EXIST("Entity already exist"),
            UNKNOWN_ATTRIBUTE("Unknown attribute: %s"),
            NOT_SUITABLE_ATTRIBUTE_BUILDER("Builder don't support this attribute type: %s");

            private String cause;

            ExceptionCauses(String cause) {
                this.cause = cause;
            }

            public String getCause() {
                return cause;
            }
        }

        public BuilderException(String message) {
            super(message);
        }

        BuilderException(ExceptionCauses cause) {
            super(cause.getCause());
        }

        public BuilderException(ExceptionCauses cause, String... args) {
            super(String.format(cause.getCause(), (Object[]) args));
        }
    }

    /**
     * Register {@link BuilderAttribute} for create attribute fields in Entity Class
     * @param builderAttribute - attribute builder
     */
    public void initAttributeBuilder(BuilderAttribute builderAttribute){
        getBuilderAttributes().add(builderAttribute);
    }

    /**
     * Build entity blank to Class
     *
     * @param entityBlank - entity blank
     * @return Class with annotation Entity and Table
     * @throws BuilderException if build fail
     */
    public Class<?> buildEntity(EntityBlank entityBlank) {
        if (entityBlank.getCode().isEmpty()) {
            throw new BuilderException(BuilderException.ExceptionCauses.NAME_IS_EMPTY);
        }
        if (null != RuntimeHelper.getCtClass(entityBlank.getCode())) {
            throw new BuilderException(BuilderException.ExceptionCauses.ENTITY_ALREADY_EXIST);
        }

        CtClass abstractEntity = RuntimeHelper.getCtClass(AbstractEntity.class.getName());
        final CtClass ctClass = RuntimeHelper.getClassPool().makeClass(entityBlank.getCode(), abstractEntity);
        setEntityNameMethod(ctClass, entityBlank.getCode());
        setEntityAnnotations(ctClass, entityBlank.getTableName());
        entityBlank.getAttributes().forEach(attribute -> buildAttribute(ctClass, attribute));

        try {
            return ctClass.toClass();
        } catch (CannotCompileException e) {
            throw new BuilderException(e.getMessage());
        }
    }

    /**
     * Add getEntityName() method for interface {@link NamedEntity}
     * @param ctClass - class blank
     * @param entityName - name of entity
     */
    private static void setEntityNameMethod(CtClass ctClass, String entityName) {
        try {
            CtMethod getEntityName = CtNewMethod.make("public String getEntityName(){return \""+ entityName +"\";}", ctClass);
            ctClass.addMethod(getEntityName);
        } catch (CannotCompileException  e) {
            e.printStackTrace();
        }
    }

    /**
     * Add annotations Entity and Table to class blank
     * @param ctClass - class blank
     * @param tableName - param fo annotation Table: @Table(name = tableName)
     */
    private static void setEntityAnnotations(CtClass ctClass, String tableName) {
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

    private void buildAttribute(CtClass ctClass, Attribute attribute) {
        BuilderAttribute builderAttribute = getAttributeBuilder(attribute);
        if (null == builderAttribute) {
            throw new BuilderException(BuilderException.ExceptionCauses.UNKNOWN_ATTRIBUTE, attribute.getType().getCode());
        }
        builderAttribute.build(ctClass, attribute);
    }

    @Nullable
    private BuilderAttribute getAttributeBuilder(Attribute attribute) {
        AttributeType type = attribute.getType();
        @SuppressWarnings("unchecked")
        Set<BuilderAttribute> suitableBuilders = getBuilderAttributes().stream()
                .filter(builder ->  builder.getTypeClass().isAssignableFrom(type.getClass()))
                .collect(Collectors.toSet());
        if (suitableBuilders.isEmpty()) {
            return null;
        }
        if (suitableBuilders.size() == 1) {
            return suitableBuilders.iterator().next();
        }

        Map<Class, Integer> fromClasses = ClassUtils.getHierarchyClass(type.getClass());
        return suitableBuilders.stream()
                .min(Comparator.comparingInt(b -> fromClasses.get(b.getTypeClass()))).get();
    }

    private Set<BuilderAttribute> getBuilderAttributes() {
        if (null == builderAttributes) {
            builderAttributes = Sets.newHashSet();
        }
        return builderAttributes;
    }

    /**
     * Initialize all default attribute builders
     */
    private void initializeAttributeBuilders() {
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());
        Set<Class<? extends BuilderAttribute>> builders = reflections.getSubTypesOf(BuilderAttribute.class);
        builders.forEach(builder -> {
            if (!Modifier.isAbstract(builder.getModifiers()) && !builder.isInterface()) {
                try {
                    BuilderAttribute builderAttribute = builder.newInstance();
                    initAttributeBuilder(builderAttribute);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BuilderException(e.getMessage());
                }
            }
        });
    }
}
