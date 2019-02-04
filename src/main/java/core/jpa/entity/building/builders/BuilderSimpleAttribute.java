package core.jpa.entity.building.builders;

import core.jpa.Constants;
import core.jpa.RuntimeHelper;
import core.jpa.entity.attribute.Attribute;
import core.jpa.entity.attribute.type.AttributeSimpleType;
import core.jpa.entity.building.BuilderService;
import core.utils.StringUtils;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import javax.persistence.Column;

public class BuilderSimpleAttribute extends BuilderAbstractAttribute<AttributeSimpleType> {

    public Class<AttributeSimpleType> getTypeClass(){
        return AttributeSimpleType.class;
    }

    @Override
    public void build(CtClass ctClass, Attribute attribute) {
        validationAttribute(attribute);
        try {
            addSimpleField(attribute.getCode(), ctClass,
                    attribute.getType().getAttributeClass(), attribute.isRequired(), attribute.isUnique());
        } catch (NotFoundException | CannotCompileException e) {
            throw new BuilderService.BuilderException(e.getMessage());
        }
    }

    private void addSimpleField(String filedName, CtClass ctClass, Class<?> fieldClass, boolean required, boolean unique) throws NotFoundException, CannotCompileException {
        CtField field = new CtField(RuntimeHelper.getClassPool().get(fieldClass.getName()), filedName, ctClass);
        CtMethod ctGetter = CtNewMethod.getter(Constants.Builder.GETTER + StringUtils.capitalizeFirstLetter(filedName), field);
        CtMethod ctSetter = CtNewMethod.setter(Constants.Builder.SETTER + StringUtils.capitalizeFirstLetter(filedName), field);

        ClassFile ccFile = ctClass.getClassFile();
        ConstPool constPool = ccFile.getConstPool();

        ctClass.addField(field);
        ctClass.addMethod(ctGetter);
        ctClass.addMethod(ctSetter);

        Annotation annotationColumn = new Annotation(Column.class.getName(), constPool);
        annotationColumn.addMemberValue("name", new StringMemberValue(filedName, constPool));

        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        annotationsAttribute.addAnnotation(annotationColumn);
        field.getFieldInfo().addAttribute(annotationsAttribute);

        //TODO add required/unique param
    }
}
