package test_core.utils;

import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.EntityService;
import core.object.ObjectService;
import test_core.assertions.SpringContextAssertions;

import java.util.Set;

/**
 * Utils for spring context test
 */
public class SpringContextUtils {

    private EntityService entityService;
    private ObjectService objectService;
    private DbDAO dbDAO;
    private ObjectDAO objectDAO;

    private SpringContextAssertions assertions;

    public SpringContextUtils(EntityService entityService,
                              ObjectService objectService,
                              DbDAO dbDAO,
                              ObjectDAO objectDAO) {
        this.entityService = entityService;
        this.objectService = objectService;
        this.dbDAO = dbDAO;
        this.objectDAO = objectDAO;

        CleanUtils.initCleaner(entityService);
        assertions = new SpringContextAssertions(this);
    }

    public EntityService getEntityService() {
        return entityService;
    }

    public ObjectService getObjectService() {
        return objectService;
    }

    public DbDAO getDbDAO() {
        return dbDAO;
    }

    public ObjectDAO getObjectDAO() {
        return objectDAO;
    }

    /**
     * Get all tables in dataBase
     */
    public Set<String> getAllTables() {
        return getDbDAO().getAllTables();
    }

    public SpringContextAssertions assertions() {
        return assertions;
    }
}
