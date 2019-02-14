package spring_context_tests;

import core.factories.EntityClassFactory;
import core.jpa.dao.EntityDAO;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EntityServiceTest extends SpringContextAbstractTest {

    @Autowired
    EntityService entityService;

    @Autowired
    EntityDAO entityDAO;

    @Test
    void testEntityLifeCircle() {
        EntityClass entityClass = EntityClassFactory.create();
        entityService.createEntity(entityClass);

        Assertions.assertTrue(entityService.isEntityExist(entityClass.getCode()));
        Assertions.assertTrue(tableExist(entityClass.getCode()));

        entityService.removeEntity(entityClass.getCode());

        Assertions.assertFalse(entityService.isEntityExist(entityClass.getCode()));
        Assertions.assertFalse(tableExist(entityClass.getCode()));
    }

    @Test
    void testHardClean() {
        List<EntityClass> entityClasses =
                Stream.generate(EntityClassFactory::create).limit(10).collect(Collectors.toList());
        entityService.createEntity(entityClasses.toArray(new EntityClass[0]));

        Assertions.assertTrue(
                entityClasses.stream()
                        .allMatch(entityBlank -> entityService.isEntityExist(entityBlank.getCode())));
        Assertions.assertTrue(
                entityClasses.stream().allMatch(entityBlank -> tableExist(entityBlank.getCode())));

        entityService.hardClean();

        Assertions.assertTrue(
                entityClasses.stream()
                        .noneMatch(entityBlank -> entityService.isEntityExist(entityBlank.getCode())));
        Assertions.assertTrue(
                entityClasses.stream().noneMatch(entityBlank -> tableExist(entityBlank.getCode())));
    }

    private boolean tableExist(String code) {
        return entityDAO.getAllTables().contains(code);
    }
}
