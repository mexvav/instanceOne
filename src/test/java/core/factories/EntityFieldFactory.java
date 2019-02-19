package core.factories;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.EntityClass;
import core.jpa.entity.field.fields.DateEntityField;
import core.jpa.entity.field.EntityField;
import core.jpa.entity.field.fields.IntegerEntityField;
import core.jpa.entity.field.fields.StringEntityField;
import core.utils.EntityFieldUtils;
import core.utils.RandomUtils;

import java.util.Map;
import java.util.function.Function;

public class EntityFieldFactory {

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
    public static Function<EntityField, Object> valueEntityField(EntityField entityField) {
        return entityFieldValueGenerators().get(entityField.getType());
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