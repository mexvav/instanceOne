package core.factories;

import core.jpa.entity.EntityClass;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.DateEntityFieldType;
import core.jpa.entity.fields.types.IntegerEntityFieldType;
import core.jpa.entity.fields.types.StringEntityFieldType;
import core.utils.RandomUtils;

public class EntityFieldFactory {

    public static  EntityField stringEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new StringEntityFieldType());
        return entityField;
    }

    public static  EntityField dateEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new DateEntityFieldType());
        return entityField;
    }

    public static  EntityField integerEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new IntegerEntityFieldType());
        return entityField;
    }

    public static void setAllFields(EntityClass entityClass){
        entityClass.addFields(stringEntityField());
        entityClass.addFields(dateEntityField());
        entityClass.addFields(integerEntityField());
    }
}