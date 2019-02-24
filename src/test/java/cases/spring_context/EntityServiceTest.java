package cases.spring_context;

import core.factories.EntityClassFactory;
import core.factories.EntityFieldFactory;
import core.jpa.dao.EntityDAO;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.entity.field.EntityField;
import core.jpa.entity.field.fields.StringEntityField;
import core.jpa.interfaces.HasLength;
import core.jpa.object.ObjectService;
import core.assertions.EntityFieldAssertions;
import core.utils.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EntityServiceTest extends SpringContextAbstractTest {

    @Autowired
    EntityServiceTest(EntityService entityService,
                      ObjectService objectService,
                      EntityDAO entityDAO) {
        super(entityService, objectService, entityDAO);
    }

    /**
     * Testing creation Entity from {@link EntityClass} by {@link EntityService}
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create "entityClass" of {@link EntityClass}</li>
     * <li>load entity from "entityClass" by {@link EntityService#createEntity(EntityClass...)}</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is entity exist in {@link EntityClass}</li>
     * <li>is entity exist in dataBase</li>
     * </ol>
     *
     * <b>Actions:</b>
     * <li>remove entity by {@link EntityService#removeEntity(String...)}</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is entity not exist in {@link EntityClass}</li>
     * <li>is entity not exist in dataBase</li>
     * </ol>
     */
    @Test
    void testEntityLifeCircle() {
        EntityClass entityClass = EntityClassFactory.create();

        testUtils.getEntityService().createEntity(entityClass);
        testUtils.assertEntityExist(entityClass);

        testUtils.getEntityService().removeEntity(entityClass.getCode());
        testUtils.assertEntityNotExist(entityClass);
    }

    /**
     * Testing removing all Entities by {@link EntityService#hardClean()}
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create 10 {@link EntityClass}</li>
     * <li>load all entity</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is all entity exist in {@link EntityClass}</li>
     * <li>is all entity exist in dataBase</li>
     * </ol>
     *
     * <b>Actions:</b>
     * <li>remove all entity by {@link EntityService#hardClean()}</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is all entity not exist in {@link EntityClass}</li>
     * <li>is all entity not exist in dataBase</li>
     * </ol>
     */
    @Test
    void testHardClean() {
        int count = 10;
        List<EntityClass> entityClasses =
                Stream.generate(EntityClassFactory::create).limit(count).collect(Collectors.toList());

        testUtils.getEntityService().createEntity(entityClasses.toArray(new EntityClass[0]));
        entityClasses.forEach(testUtils::assertEntityExist);

        testUtils.getEntityService().hardClean();
        entityClasses.forEach(testUtils::assertEntityNotExist);
    }

    /**
     * Testing creation Entity with {@link EntityField}
     *
     * <ol>
     * <b>Actions:</b>
     * <li>create "entityClass" of {@link EntityClass}</li>
     * <li>load entity from "entityClass" by {@link EntityService#createEntity(EntityClass...)}</li>
     * </ol>
     *
     * <ol>
     * <b>Verifications:</b>
     * <li>is column {@link EntityField} exist in table</li>
     * <li>is column nullable if {@link EntityField#isRequired()} != true</li>
     * <li>is column type suitable {@link EntityField}</li>
     * <li>is column length equals {@link HasLength#getLength()}</li>
     * </ol>
     */
    @Test
    void testCreateEntityWithFields() {
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        testUtils.getEntityService().createEntity(entityClass);
        testUtils.assertEntityExist(entityClass);

        Set<Map<String, String>> columns = testUtils.getEntityDAO().getColumns(entityClass.getCode());
        EntityFieldAssertions.assertFields(entityClass.getFields(), columns);
    }

    @Test
    void testLengthEntityField() {
        final int length = 100;

        EntityClass entityClass = EntityClassFactory.create();

        StringEntityField entityField = new StringEntityField();
        entityField.setCode(RandomUtils.getCode());
        entityField.setLength(length);
        entityClass.addFields(entityField);

        testUtils.getEntityService().createEntity(entityClass);
        testUtils.assertEntityExist(entityClass);

        EntityFieldAssertions.assertField(entityField, testUtils.getEntityDAO().getColumns(entityClass.getCode()));
    }
}
