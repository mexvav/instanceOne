package core.entity.field;

import core.Constants;
import core.entity.field.fields.DateEntityField;
import core.entity.field.fields.IntegerEntityField;
import core.entity.field.fields.LinkEntityField;
import core.entity.field.fields.StringEntityField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;


class EntityFieldFactoryTest extends AbstractSpringContextTest {

    private EntityFieldFactory fieldFactory;

    @Autowired
    EntityFieldFactoryTest(EntityFieldFactory fieldFactory) {
        this.fieldFactory = fieldFactory;
    }

    @Test
    void testCreateStringFieldByType() {
        EntityField entityField = fieldFactory.get(Constants.EntityFieldType.STRING);
        Assertions.assertEquals(entityField.getClass(), StringEntityField.class);
    }

    @Test
    void testCreateIntegerFieldByType() {
        EntityField entityField = fieldFactory.get(Constants.EntityFieldType.INTEGER);
        Assertions.assertEquals(entityField.getClass(), IntegerEntityField.class);
    }

    @Test
    void testCreateDateFieldByType() {
        EntityField entityField = fieldFactory.get(Constants.EntityFieldType.DATE);
        Assertions.assertEquals(entityField.getClass(), DateEntityField.class);
    }

    @Test
    void testCreateLinkFieldByType() {
        EntityField entityField = fieldFactory.get(Constants.EntityFieldType.LINK);
        Assertions.assertEquals(entityField.getClass(), LinkEntityField.class);
    }
}
