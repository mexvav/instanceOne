package spring_context_tests;

import core.jpa.dao.DAOEntity;
import core.jpa.entity.EntityBlank;
import core.jpa.entity.EntityService;
import core.utils.FactoryEntityBlank;
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
    DAOEntity daoEntity;

    @Test
    void testEntityLifeCircle() {
        EntityBlank entityBlank = FactoryEntityBlank.create();
        entityService.createEntity(entityBlank);

        Assertions.assertTrue(entityService.isEntityExist(entityBlank.getCode()));
        Assertions.assertTrue(tableExist(entityBlank.getCode()));

        entityService.removeEntity(entityBlank.getCode());

        Assertions.assertFalse(entityService.isEntityExist(entityBlank.getCode()));
        Assertions.assertFalse(tableExist(entityBlank.getCode()));
    }

    @Test
    void testHardClean() {
        List<EntityBlank> entityBlanks = Stream.generate(FactoryEntityBlank::create).limit(10).collect(Collectors.toList());
        entityService.createEntity(entityBlanks.toArray(new EntityBlank[0]));

        Assertions.assertTrue(entityBlanks.stream().allMatch(entityBlank -> entityService.isEntityExist(entityBlank.getCode())));
        Assertions.assertTrue(entityBlanks.stream().allMatch(entityBlank -> tableExist(entityBlank.getCode())));

        entityService.hardClean();

        Assertions.assertTrue(entityBlanks.stream().noneMatch(entityBlank -> entityService.isEntityExist(entityBlank.getCode())));
        Assertions.assertTrue(entityBlanks.stream().noneMatch(entityBlank -> tableExist(entityBlank.getCode())));
    }

    private boolean tableExist(String code) {
        return daoEntity.getAllTables().contains(code);
    }

}
