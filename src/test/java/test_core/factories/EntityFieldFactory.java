package test_core.factories;

import com.google.common.collect.Maps;
import core.Constants;
import core.entity.EntityClass;
import core.entity.entities.Entity;
import core.entity.field.EntityField;
import core.entity.field.fields.*;
import test_core.utils.EntityFieldUtils;
import test_core.utils.RandomUtils;

import java.util.Map;
import java.util.function.Function;

public class EntityFieldFactory {

    /**
     * Generator values for Boolean fields
     */
    private static Function<EntityField, Object> valueBooleanEntityFieldGenerator = (entityField) -> RandomUtils.getBoolean();

    /**
     * Generator values for String fields
     */
    private static Function<EntityField, Object> valueStringEntityFieldGenerator = (entityField) -> RandomUtils.generateEnglishString(EntityFieldUtils.getLength(entityField));
    /**
     * Generator values for Integer fields
     */
    private static Function<EntityField, Object> valueIntegerEntityFieldGenerator = (entityField) -> RandomUtils.getInteger();
    /**
     * Generator values for Date fields
     */
    static private Function<EntityField, Object> valueDateEntityFieldGenerator = (entityField) -> RandomUtils.getDate();

    /**
     * Get {@link BooleanEntityField}
     */
    public static EntityField booleanEntityField() {
        EntityField entityField = new BooleanEntityField();
        entityField.setCode(RandomUtils.getCode());
        return entityField;
    }

    /**
     * Get {@link StringEntityField}
     */
    public static EntityField stringEntityField() {
        EntityField entityField = new StringEntityField();
        entityField.setCode(RandomUtils.getCode());
        return entityField;
    }

    /**
     * Get {@link DateEntityField}
     */
    public static EntityField dateEntityField() {
        EntityField entityField = new DateEntityField();
        entityField.setCode(RandomUtils.getCode());
        return entityField;
    }

    /**
     * Get {@link IntegerEntityField}
     */
    public static EntityField integerEntityField() {
        EntityField entityField = new IntegerEntityField();
        entityField.setCode(RandomUtils.getCode());
        return entityField;
    }

    /**
     * Get {@link core.entity.field.fields.LinkEntityField}
     */
    public static EntityField linkEntityField(Class<Entity> entity) {
        LinkEntityField entityField = new LinkEntityField();
        entityField.setCode(RandomUtils.getCode());
        entityField.setLinkClass(entity);
        return entityField;
    }

    /**
     * Set all type {@link EntityField}
     *
     * @param entityClass entityClass for setting all type {@link EntityField}
     */
    public static void setAllFields(EntityClass entityClass) {
        entityClass.addFields(booleanEntityField());
        entityClass.addFields(stringEntityField());
        entityClass.addFields(dateEntityField());
        entityClass.addFields(integerEntityField());
    }

    /**
     * Get value for {@link EntityField}
     */
    public static Function<EntityField, Object> valueEntityField(EntityField entityField) {
        return entityFieldValueGenerators().get(entityField.getType());
    }

    /**
     * Get all field value generators
     */
    private static Map<String, Function<EntityField, Object>> entityFieldValueGenerators() {
        Map<String, Function<EntityField, Object>> generators = Maps.newHashMap();
        generators.put(Constants.EntityFieldType.BOOLEAN, valueBooleanEntityFieldGenerator);
        generators.put(Constants.EntityFieldType.STRING, valueStringEntityFieldGenerator);
        generators.put(Constants.EntityFieldType.INTEGER, valueIntegerEntityFieldGenerator);
        generators.put(Constants.EntityFieldType.DATE, valueDateEntityFieldGenerator);
        return generators;
    }
}