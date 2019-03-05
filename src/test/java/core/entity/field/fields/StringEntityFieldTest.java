package core.entity.field.fields;

import core.entity.EntityClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.assertions.EntityFieldAssertions;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.utils.RandomUtils;
import test_core.utils.SpringContextUtils;

import java.util.Map;
import java.util.Set;

class StringEntityFieldTest extends AbstractSpringContextTest {

    private SpringContextUtils utils;

    @Autowired
    StringEntityFieldTest(SpringContextUtils utils) {
        this.utils = utils;
    }

    @Test
    void testStringEntityField() {
        EntityClass entityClass = EntityClassFactory.create();
        entityClass.addFields(EntityFieldFactory.stringEntityField());

        utils.create().entity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        Set<Map<String, String>> columns = utils.getDbDAO().getColumns(entityClass.getCode());
        EntityFieldAssertions.assertFields(entityClass.getFields(), columns);
    }

    @Test
    void testHasLengthField() {
        final int length = 100;
        EntityClass entityClass = EntityClassFactory.create();

        StringEntityField entityField = new StringEntityField();
        entityField.setCode(RandomUtils.getCode());
        entityField.setLength(length);
        entityClass.addFields(entityField);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        EntityFieldAssertions.assertField(entityField,
                utils.getDbDAO().getColumns(entityClass.getCode()));
    }
}
