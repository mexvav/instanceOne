package test_core.assertions;

import core.entity.EntityClass;
import core.entity.EntityService;
import org.junit.jupiter.api.Assertions;
import test_core.utils.SpringContextUtils;

public class EntityAssertions {

    private EntityFieldAssertions entityFieldAssertions;
    private SpringContextUtils utils;

    public EntityAssertions(SpringContextUtils utils) {
        this.utils = utils;
        this.entityFieldAssertions = new EntityFieldAssertions();
    }

    public EntityFieldAssertions field() {
        return entityFieldAssertions;
    }

    /**
     * Check is entity exist
     * is Entity exist in {@link EntityService}
     * is Entity table exist
     *
     * @param entityClass {@link EntityClass}
     */
    public void assertExist(EntityClass entityClass) {
        String code = entityClass.getCode();
        Assertions.assertTrue(utils.getEntityService().isEntityExist(code),
                String.format("Entity '%s' is not exist in entityService", code));
        Assertions.assertTrue(utils.getAllTables().contains(code),
                String.format("Entity table '%s' is not exist", code));
    }

    /**
     * Check is entity not exist
     * is Entity exist in {@link EntityService}
     * is Entity table exist
     *
     * @param entityClass {@link EntityClass}
     */
    public void assertNotExist(EntityClass entityClass) {
        String code = entityClass.getCode();
        Assertions.assertFalse(utils.getEntityService().isEntityExist(code),
                String.format("Entity '%s' is exist in entityService", code));
        Assertions.assertFalse(utils.getAllTables().contains(code),
                String.format("Entity table '%s' is exist", code));
    }
}
