package core.assertions;

import core.jpa.dao.EntityDAO;
import core.jpa.entity.EntityClass;
import core.jpa.entity.EntityService;
import core.jpa.object.ObjectService;
import core.utils.CleanUtils;
import org.junit.jupiter.api.Assertions;

import java.util.Set;

/**
 * Utils for spring context test
 */
public class SpringContextAssertions {

    private EntityService entityService;
    private ObjectService objectService;
    private EntityDAO entityDAO;

    public SpringContextAssertions(EntityService entityService,
                            ObjectService objectService,
                            EntityDAO entityDAO){
        this.entityService = entityService;
        this.objectService = objectService;
        this.entityDAO = entityDAO;
        CleanUtils.initCleaner(entityService);
    }

    public EntityService getEntityService(){
        return entityService;
    }

    public ObjectService getObjectService(){
        return objectService;
    }

    public EntityDAO getEntityDAO(){
        return entityDAO;
    }

    /**
     * Get all tables in dataBase
     */
    public Set<String> getAllTables(){
        return getEntityDAO().getAllTables();
    }

    /**
     * Check is entity exist
     * is Entity exist in {@link EntityService}
     * is Entity table exist
     *
     * @param entityClass {@link EntityClass}
     */
    public void assertEntityExist(EntityClass entityClass) {
        String code = entityClass.getCode();
        Assertions.assertTrue(getEntityService().isEntityExist(code),
                String.format("Entity '%s' is not exist in entityService", code));
        Assertions.assertTrue(getAllTables().contains(code),
                String.format("Entity table '%s' is not exist", code));
    }

    /**
     * Check is entity not exist
     * is Entity exist in {@link EntityService}
     * is Entity table exist
     *
     * @param entityClass {@link EntityClass}
     */
    public void assertEntityNotExist(EntityClass entityClass) {
        String code = entityClass.getCode();
        Assertions.assertFalse(getEntityService().isEntityExist(code),
                String.format("Entity '%s' is exist in entityService", code));
        Assertions.assertFalse(getAllTables().contains(code),
                String.format("Entity table '%s' is exist", code));
    }
}
