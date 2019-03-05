package core.entity.field.fields;

import core.entity.EntityClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.assertions.EntityFieldAssertions;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.utils.SpringContextUtils;

import java.util.Map;
import java.util.Set;

class DateEntityFieldTest extends AbstractSpringContextTest {

    private SpringContextUtils utils;

    @Autowired
    DateEntityFieldTest(SpringContextUtils utils) {
        this.utils = utils;
    }

    @Test
    void testEntityField() {
        EntityClass entityClass = EntityClassFactory.create();
        entityClass.addFields(EntityFieldFactory.dateEntityField());

        utils.create().entity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        Set<Map<String, String>> columns = utils.getDbDAO().getColumns(entityClass.getCode());
        EntityFieldAssertions.assertFields(entityClass.getFields(), columns);
    }
}
