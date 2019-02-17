package spring_context_tests;

import core.factories.EntityClassFactory;
import core.factories.EntityFieldFactory;
import core.factories.ObjectFactory;
import core.jpa.dao.EntityDAO;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.object.ObjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

class ObjectServiceTest extends SpringContextAbstractTest {

    @Autowired
    ObjectServiceTest(EntityService entityService,
                      ObjectService objectService,
                      EntityDAO entityDAO) {
        super(entityService, objectService, entityDAO);
    }

    /**
     * Testing creation object in Entity
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create "entityClass" of {@link EntityClass}</li>
     * <li>load "entity" from "entityClass" by {@link EntityService#createEntity(EntityClass...)}</li>
     * <li>create "object" in "entity"</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is object exist in table</li>
     * </ol>
     */
    @Test
    void testCreateObject() {
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        testUtils.getEntityService().createEntity(entityClass);
        testUtils.assertEntityExist(entityClass);

        Map<String, Object> params = ObjectFactory.createObjectParam(entityClass);
        testUtils.getObjectService().createObject(entityClass.getCode(), params);
    }
}
