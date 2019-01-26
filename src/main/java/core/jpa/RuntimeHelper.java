package core.jpa;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import javax.annotation.Nullable;
import javax.persistence.Column;


public class RuntimeHelper {

    static private ClassPool classPool;

    public static ClassPool getClassPool() {
        if (null == classPool) {
            classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(EntityBuilder.class.getClassLoader()));
        }
        return classPool;
    }

    @Nullable
    public static CtClass getCtClass(String className) {
        try {
            return getClassPool().get(className);
        } catch (NotFoundException e) {
            return null;
        }
    }

    private static String capitalizeFirstLetter(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static void addSimpleField(String filedName, CtClass ctClass, Class<?> fieldClass, boolean required, boolean unique) throws NotFoundException, CannotCompileException {
        CtField field = new CtField(getClassPool().get(fieldClass.getName()), filedName, ctClass);
        CtMethod ctGetter = CtNewMethod.getter("get" + capitalizeFirstLetter(filedName), field);
        CtMethod ctSetter = CtNewMethod.setter("set" + capitalizeFirstLetter(filedName), field);

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
