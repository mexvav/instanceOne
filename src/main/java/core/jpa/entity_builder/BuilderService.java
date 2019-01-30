package core.jpa.entity_builder;

import com.google.common.collect.Sets;
import core.jpa.EntityBlank;
import core.jpa.RuntimeHelper;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeType;
import core.jpa.entity.AbstractEntity;
import core.utils.ClassUtils;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    /**
     * Exception for entity generator
     */
    static private class EntityBuildException extends RuntimeException {
        enum ExceptionCauses {
            NAME_IS_EMPTY("Code is empty, use setCode(code)"),
            ENTITY_ALREADY_EXIST("Entity already exist"),
            UNKNOWN_ATTRIBUTE("Unknown attribute: %s");

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

        EntityBuildException(ExceptionCauses cause, String... args) {
            super(String.format(cause.getCause(), (Object[]) args));
        }
    }

    /**
     * Build entity blank to Class
     *
     * @param entityBlank - entity blank
     * @return Class with annotation Entity and Table
     * @throws EntityBuildException
     */
    public Class<?> buildEntity(EntityBlank entityBlank) {
        if (entityBlank.getCode().isEmpty()) {
            throw new EntityBuildException(EntityBuildException.ExceptionCauses.NAME_IS_EMPTY);
        }
        if (null != RuntimeHelper.getCtClass(entityBlank.getCode())) {
            throw new EntityBuildException(EntityBuildException.ExceptionCauses.ENTITY_ALREADY_EXIST);
        }

        CtClass abstractEntity = RuntimeHelper.getCtClass(AbstractEntity.class.getName());
        final CtClass ctClass = RuntimeHelper.getClassPool().makeClass(entityBlank.getCode(), abstractEntity);
        setEntityNameMethod(ctClass, entityBlank.getCode());
        setEntityAnnotations(ctClass, entityBlank.getTableName());
        entityBlank.getAttributes().forEach(attribute -> buildAttribute(ctClass, attribute));

        try {
            return ctClass.toClass();
        } catch (CannotCompileException e) {
            throw new EntityBuildException(e.getMessage());
        }
    }

    /**
     * Add getEntityName() method for interface {@link core.jpa.entity.NamedEntity}
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

    /**
     * Register {@link BuilderAttribute} for create attribute fields in Entity Class
     * @param builder - builder attribute
     */
    void initAttributeBuilder(BuilderAttribute builder) {
        getBuilderAttributes().add(builder);
    }

    private void buildAttribute(CtClass ctClass, Attribute attribute) {
        BuilderAttribute builderAttribute = getAttributeBuilder(attribute);
        if (null == builderAttribute) {
            throw new EntityBuildException(EntityBuildException.ExceptionCauses.UNKNOWN_ATTRIBUTE, attribute.getType().getCode());
        }
        builderAttribute.build(ctClass, attribute);
    }

    @Nullable
    private BuilderAttribute getAttributeBuilder(Attribute attribute) {
        AttributeType type = attribute.getType();
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
}
