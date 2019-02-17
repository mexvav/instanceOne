package core.factories;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.EntityClass;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.DateEntityFieldType;
import core.jpa.entity.fields.types.IntegerEntityFieldType;
import core.jpa.entity.fields.types.StringEntityFieldType;
import core.utils.EntityFieldUtils;
import core.utils.RandomUtils;

import java.util.Map;
import java.util.function.Function;

public class EntityFieldFactory {

    /**
     * Get {@link EntityField} with {@link StringEntityFieldType}
     */
    public static EntityField stringEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new StringEntityFieldType());
        return entityField;
    }

    /**
     * Get {@link EntityField} with {@link DateEntityFieldType}
     */
    public static EntityField dateEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new DateEntityFieldType());
        return entityField;
    }

    /**
     * Get {@link EntityField} with {@link IntegerEntityFieldType}
     */
    public static EntityField integerEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new IntegerEntityFieldType());
        return entityField;
    }

    /**
     * Set all type {@link EntityField}
     *
     * @param entityClass entityClass for setting all type {@link EntityField}
     */
    public static void setAllFields(EntityClass entityClass) {
        entityClass.addFields(stringEntityField());
        entityClass.addFields(dateEntityField());
        entityClass.addFields(integerEntityField());
    }

    /**
     * Get value for {@link EntityField}
     */
    public static Object valueEntityField(EntityField entityField) {
        return entityFieldValueGenerators().get(entityField.getType().getCode());
    }

    /**
     * Generator values for String fields
     */
    static private Function<EntityField, Object> valueStringEntityFieldGenerator = (entityField) -> RandomUtils.generateEnglishString(EntityFieldUtils.getLength(entityField));

    /**
     * Generator values for Integer fields
     */
    static private Function<EntityField, Object> valueIntegerEntityFieldGenerator = (entityField) -> RandomUtils.getInteger();

    /**
     * Generator values for Date fields
     */
    static private Function<EntityField, Object> valueDateEntityFieldGenerator = (entityField) -> RandomUtils.getDate();

    /**
     * Get all field value generators
     */
    public static Map<String, Function<EntityField, Object>> entityFieldValueGenerators(){
        Map<String, Function<EntityField, Object>> generators = Maps.newHashMap();
        generators.put(Constants.EntityFieldType.STRING, valueStringEntityFieldGenerator);
        generators.put(Constants.EntityFieldType.INTEGER, valueIntegerEntityFieldGenerator);
        generators.put(Constants.EntityFieldType.DATE, valueDateEntityFieldGenerator);
        return generators;
    }
}