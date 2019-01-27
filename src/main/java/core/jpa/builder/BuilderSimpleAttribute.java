package core.jpa.builder;

import core.jpa.RuntimeHelper;
import core.jpa.attribute.Attribute;
import core.jpa.attribute.type.AttributeSimpleType;
import core.jpa.attribute.type.AttributeType;
import core.utils.StringUtils;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class BuilderSimpleAttribute extends BuilderAbstractAttribute<AttributeSimpleType> {

    @Autowired
    BuilderSimpleAttribute(BuilderService builderService) {
        super(builderService);
    }

    public Class<AttributeSimpleType> getTypeClass(){
        return AttributeSimpleType.class;
    }

    protected void validationAttribute(Attribute attribute) {
        AttributeType attributeType = attribute.getType();
        if (!getTypeClass().isAssignableFrom(attributeType.getClass())) {
            throw new RuntimeException("Builder don't support this attribute type: " + attributeType.getCode());
        }
    }

    @Override
    public void build(CtClass ctClass, Attribute attribute) {
        validationAttribute(attribute);
        try {
            addSimpleField(attribute.getCode(), ctClass,
                    attribute.getType().getAttributeClass(), attribute.isRequired(), attribute.isUnique());
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

    private void addSimpleField(String filedName, CtClass ctClass, Class<?> fieldClass, boolean required, boolean unique) throws NotFoundException, CannotCompileException {
        CtField field = new CtField(RuntimeHelper.getClassPool().get(fieldClass.getName()), filedName, ctClass);
        CtMethod ctGetter = CtNewMethod.getter("get" + StringUtils.capitalizeFirstLetter(filedName), field);
        CtMethod ctSetter = CtNewMethod.setter("set" + StringUtils.capitalizeFirstLetter(filedName), field);

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
    }
}
