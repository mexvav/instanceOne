package core.jpa;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;


public  class EntityRuntime {

    static private ClassPool classPool;

    public static ClassPool getClassPool() {
        if(null == classPool){
            classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(EntityRuntime.class.getClassLoader()));
        }
        return classPool;
    }

    public static Class getEntityClass() throws NotFoundException, CannotCompileException {
        String className = "wow";
        CtClass ctClass = getClassPool().makeClass(className);


        ///field
        CtField field = new CtField(getClassPool().get(Integer.class.getName()), "id" ,ctClass);
        CtMethod ctGetter = CtNewMethod.getter("getId", field);
        CtMethod ctSetter = CtNewMethod.setter("setId", field);

        ClassFile ccFile = ctClass.getClassFile();
        ConstPool constPool = ccFile.getConstPool();



        ctClass.addField(field);
        ctClass.addMethod(ctGetter);
        ctClass.addMethod(ctSetter);

        Annotation annotationColumn = new Annotation(Column.class.getName(), constPool);
        annotationColumn.addMemberValue("name", new StringMemberValue("value", constPool));

        Annotation annotationId = new Annotation(Id.class.getName(), constPool);

        AnnotationsAttribute annotationsAttribute1 = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        annotationsAttribute1.addAnnotation(annotationColumn);
        annotationsAttribute1.addAnnotation(annotationId);
        field.getFieldInfo().addAttribute(annotationsAttribute1);

        ///


        Annotation annotationEntity = new Annotation(Entity.class.getName(), constPool);
        Annotation annotationTable = new Annotation(Table.class.getName(), constPool);
        annotationTable.addMemberValue("name", new StringMemberValue(className + "_table", constPool));

        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        annotationsAttribute.addAnnotation(annotationEntity);
        annotationsAttribute.addAnnotation(annotationTable);

        ccFile.addAttribute(annotationsAttribute);

        return ctClass.toClass();
    }
}
