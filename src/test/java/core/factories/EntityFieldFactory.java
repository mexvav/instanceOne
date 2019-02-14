package core.factories;

import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.DateEntityFieldType;
import core.jpa.entity.fields.types.IntegerEntityFieldType;
import core.jpa.entity.fields.types.StringEntityFieldType;
import core.utils.RandomUtils;

public class EntityFieldFactory {

    static public EntityField stringEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new StringEntityFieldType());
        return entityField;
    }

    static public EntityField dateEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new DateEntityFieldType());
        return entityField;
    }

    static public EntityField integerEntityField() {
        EntityField entityField = new EntityField(RandomUtils.getCode());
        entityField.setType(new IntegerEntityFieldType());
        return entityField;
    }
}