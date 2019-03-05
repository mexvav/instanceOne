package core.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.AbstractSpringContextTest;
import test_core.factories.EntityClassFactory;
import test_core.utils.SpringContextUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EntityServiceTest extends AbstractSpringContextTest {

    private SpringContextUtils utils;

    @Autowired
    EntityServiceTest(SpringContextUtils utils) {
        this.utils = utils;
    }

    /**
     * Testing creation Entity from {@link EntityClass} by {@link EntityService}
     */
    @Test
    void testCreateEntity() {
        EntityClass entityClass = EntityClassFactory.create();
        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);
    }

    /**
     * Testing removing Entity from {@link EntityClass} by {@link EntityService}
     */
    @Test
    void testRemoveEntity() {
        EntityClass entityClass = EntityClassFactory.create();
        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        utils.getEntityService().removeEntity(entityClass.getCode());
        utils.assertions().entity().assertNotExist(entityClass);
    }

    /**
     * Testing removing all Entities by {@link EntityService#hardClean()}
     */
    @Test
    void testHardClean() {
        int count = 10;
        List<EntityClass> entityClasses =
                Stream.generate(EntityClassFactory::create).limit(count).collect(Collectors.toList());

        utils.getEntityService().createEntity(entityClasses.toArray(new EntityClass[0]));
        entityClasses.forEach(utils.assertions().entity()::assertExist);

        utils.getEntityService().hardClean();
        entityClasses.forEach(utils.assertions().entity()::assertNotExist);
    }

    @Test
    void testEmbeddedReloadSessionFactory() {
        //todo add counting ReloadSessionFactory
        utils.actionWithReloadSessionFactory(() -> utils.actionWithReloadSessionFactory(() -> null));
    }
}
