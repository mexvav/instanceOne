package core.jpa.entity.building.builders;

import core.jpa.RuntimeHelper;
import core.jpa.entity.EntityBlank;
import core.jpa.entity.building.BuildingException;
import core.jpa.entity.entities.AbstractEntity;
import core.jpa.entity.entities.NamedEntity;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;

public class BuilderEntity extends BuilderAbstract<EntityBlank> {

    @Override
    public Class<EntityBlank> getSuitableClass() {
        return EntityBlank.class;
    }

    /**
     * Building entity from entity blank. Param ctClass is ignored
     * @param entityBlank - entity blank
     * @return Class with annotation Entity and Table
     * @throws BuildingException if building is failed
     */
    @Override
    public CtClass build(@Nullable final CtClass ctClass, EntityBlank entityBlank) {
        if (entityBlank.getCode().isEmpty()) {
            throw new BuildingException(BuildingException.ExceptionCauses.NAME_IS_EMPTY);
        }
        if (null != RuntimeHelper.getCtClass(entityBlank.getCode())) {
            throw new BuildingException(BuildingException.ExceptionCauses.ENTITY_ALREADY_EXIST);
        }

        CtClass abstractEntity = RuntimeHelper.getCtClass(AbstractEntity.class.getName());

        final CtClass entityClass = RuntimeHelper.getClassPool().makeClass(entityBlank.getCode(), abstractEntity);
        setEntityNameMethod(entityClass, entityBlank.getCode());
        setEntityAnnotations(entityClass, entityBlank.getTableName());
        entityBlank.getAttributes().forEach(attribute -> buildingService.build(entityClass, attribute));

        return entityClass;
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

}
