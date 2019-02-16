package spring_context_tests;

import core.factories.EntityClassFactory;
import core.factories.EntityFieldFactory;
import core.jpa.dao.EntityDAO;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.entity.fields.EntityField;
import core.jpa.entity.fields.types.EntityFieldType;
import core.jpa.entity.fields.types.StringEntityFieldType;
import core.jpa.interfaces.HasLength;
import core.utils.EntityFieldUtils;
import core.utils.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EntityServiceTest extends SpringContextAbstractTest {

    @Autowired
    EntityService entityService;

    @Autowired
    EntityDAO entityDAO;

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

        entityService.createEntity(entityClass);
        checkEntityExist(entityClass);

        entityService.removeEntity(entityClass.getCode());
        checkEntityNotExist(entityClass);
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

        entityService.createEntity(entityClasses.toArray(new EntityClass[0]));
        entityClasses.forEach(this::checkEntityExist);

        entityService.hardClean();
        entityClasses.forEach(this::checkEntityNotExist);
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
     * <li>is column type suitable {@link EntityFieldType}</li>
     * <li>is column length equals {@link HasLength#getLength()}</li>
     * </ol>
     */
    @Test
    void testCreateEntityWithFields() {
        EntityClass entityClass = EntityClassFactory.create();
        EntityFieldFactory.setAllFields(entityClass);

        entityService.createEntity(entityClass);
        checkEntityExist(entityClass);

        Set<Map<String, String>> columns = entityDAO.getColumns(entityClass.getCode());
        EntityFieldUtils.checkFields(entityClass.getFields(), columns);
    }

    @Test
    void testLengthEntityField() {
        final int length = 100;

        EntityClass entityClass = EntityClassFactory.create();

        EntityField entityField = new EntityField(RandomUtils.getCode());
        StringEntityFieldType entityFieldType = new StringEntityFieldType();
        entityFieldType.setLength(length);
        entityField.setType(entityFieldType);
        entityClass.addFields(entityField);

        entityService.createEntity(entityClass);
        checkEntityExist(entityClass);

        EntityFieldUtils.checkField(entityField, entityDAO.getColumns(entityClass.getCode()));
    }

    /**
     * Check is entity exist
     * is Entity exist in {@link EntityService}
     * is Entity table exist
     *
     * @param entityClass {@link EntityClass}
     */
    private void checkEntityExist(EntityClass entityClass) {
        String code = entityClass.getCode();
        Assertions.assertTrue(entityService.isEntityExist(code),
                String.format("Entity '%s' is not exist in entityService", code));
        Assertions.assertTrue(entityDAO.getAllTables().contains(code),
                String.format("Entity table '%s' is not exist", code));
    }

    /**
     * Check is entity not exist
     * is Entity exist in {@link EntityService}
     * is Entity table exist
     *
     * @param entityClass {@link EntityClass}
     */
    private void checkEntityNotExist(EntityClass entityClass) {
        String code = entityClass.getCode();
        Assertions.assertFalse(entityService.isEntityExist(code),
                String.format("Entity '%s' is exist in entityService", code));
        Assertions.assertFalse(entityDAO.getAllTables().contains(code),
                String.format("Entity table '%s' is exist", code));
    }
}
