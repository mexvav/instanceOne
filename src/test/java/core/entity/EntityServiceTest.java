package core.entity;

import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.field.EntityField;
import core.entity.field.fields.StringEntityField;
import core.interfaces.HasLength;
import core.object.ObjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test_core.SpringContextAbstractTest;
import test_core.assertions.EntityFieldAssertions;
import test_core.factories.EntityClassFactory;
import test_core.factories.EntityFieldFactory;
import test_core.utils.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EntityServiceTest extends SpringContextAbstractTest {

    @Autowired
    EntityServiceTest(EntityService entityService,
                      ObjectService objectService,
                      DbDAO dbDAO,
                      ObjectDAO objectDAO) {
        super(entityService, objectService, dbDAO, objectDAO);
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
     * Testing creation Entity with all type of {@link EntityField}
     */
    @Test
    void testCreateEntityWithFields() {
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        Set<Map<String, String>> columns = utils.getDbDAO().getColumns(entityClass.getCode());
        EntityFieldAssertions.assertFields(entityClass.getFields(), columns);
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

    /**
     * Testing creation Entity with {@link HasLength} {@link EntityField}
     */
    @Test
    void testCreateEntityWithHasLengthField() {
        final int length = 100;

        EntityClass entityClass = EntityClassFactory.create();

        StringEntityField entityField = new StringEntityField();
        entityField.setCode(RandomUtils.getCode());
        entityField.setLength(length);
        entityClass.addFields(entityField);

        utils.getEntityService().createEntity(entityClass);
        utils.assertions().entity().assertExist(entityClass);

        EntityFieldAssertions.assertField(entityField, utils.getDbDAO().getColumns(entityClass.getCode()));
    }
}
