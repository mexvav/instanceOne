package core.object.searching;

import com.google.common.collect.Maps;
import core.entity.EntityClass;
import core.entity.EntityService;
import core.entity.entities.Entity;
import core.entity.field.EntityField;
import core.object.ObjectService;
import core.object.processing.DataObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.assertions.ObjectAssertions;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.utils.RandomUtils;

import java.util.List;
import java.util.Map;

public class SearchingServiceTest extends AbstractSpringContextTest {

    private SearchingService searchingService;
    private EntityService entityService;
    private ObjectService objectService;

    @Autowired
    SearchingServiceTest(SearchingService searchingService,
                         EntityService entityService,
                         ObjectService objectService) {
        this.searchingService = searchingService;
        this.entityService = entityService;
        this.objectService = objectService;
    }

    @Test
    void testSearchObjectByStringField() {
        EntityClass entityClass = EntityClassFactory.create();
        EntityField stringField = EntityFieldFactory.stringEntityField();
        entityClass.addFields(stringField);
        entityService.createEntity(entityClass);

        String value = RandomUtils.getTitle();
        Map<String, Object> params = Maps.newHashMap();
        params.put(stringField.getCode(), value);

        DataObject object = objectService.create(entityClass.getCode(), params);

        List<DataObject> objects = objectService.search(entityClass.getCode(), params);
        ObjectAssertions.assertEquals(params, objects.iterator().next());
    }
}
