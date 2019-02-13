package spring_context_tests;


import core.jpa.ReloadableSessionFactory;

import core.jpa.entity.EntityBlank;
import core.jpa.entity.entities.AbstractEntity;
import core.utils.FactoryEntityBlank;
import core.utils.StringUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;


class ByteBuddyTest {
    private static final String TABLE_NAME = "name";
    private static final String ENTITY_CODE_METHOD = "getEntityCode";

    private void test1(DynamicType.Builder builder){
        builder.defineField("test", String.class)
                .annotateField(AnnotationDescription.Builder.ofType(Column.class).build());
    }

    private void test2(DynamicType.Builder builder){
        builder.defineField("test", Integer.class)
                .annotateField(AnnotationDescription.Builder.ofType(Column.class).build());
    }

    @Test
    void test(){

        EntityBlank entityBlank = FactoryEntityBlank.create();

        final DynamicType.Builder builder = new ByteBuddy()
                .subclass(AbstractEntity.class)
                .name(AbstractEntity.class.getPackage().getName() + "."
                        + StringUtils.capitalizeFirstLetter(entityBlank.getCode()))
                .annotateType(AnnotationDescription.Builder
                        .ofType(Entity.class)
                        .build()
                )
                .annotateType(AnnotationDescription.Builder
                        .ofType(Table.class)
                        .define(TABLE_NAME, entityBlank.getCode())
                        .build()
                )
                .method(ElementMatchers.nameContains(ENTITY_CODE_METHOD))
                .intercept(FixedValue.value(entityBlank.getCode()));

        test1(builder);
        test2(builder);

        Class entity = builder
                .make()
                .load(ReloadableSessionFactory.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

        Field[] fields = entity.getDeclaredFields();


        /*
        String code = "test1";
        Class<? extends AbstractEntity> entity = new ByteBuddy()
                .subclass(AbstractEntity.class)
                .annotateType(
                        AnnotationDescription.Builder
                                .ofType(Entity.class)
                                .build()
                )
                .annotateType(
                        AnnotationDescription.Builder
                                .ofType(Table.class)
                                .define("name", code)
                                .build()
                )
                .method(ElementMatchers.nameContains("getEntityCode"))
                  .intercept(FixedValue.value(code))
                .defineField("test", String.class)
                  .annotateField(AnnotationDescription.Builder.ofType(Column.class).build())
                .make()
                .load(ReloadableSessionFactory.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
*/





        //Set<String> tables = dao.getAllTables();

    }
}
