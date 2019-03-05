package test_core.utils;

import core.aspects.WithReloadSessionFactory;
import core.dao.DbDAO;
import core.dao.ObjectDAO;
import core.entity.EntityService;
import core.object.ObjectService;
import test_core.assertions.SpringContextAssertions;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Utils for spring context test
 */
public class SpringContextUtils {

    private EntityService entityService;
    private ObjectService objectService;
    private DbDAO dbDAO;
    private ObjectDAO objectDAO;
    private SpringContextAssertions assertions;
    private SpringContextCreator creator;

    public SpringContextUtils(EntityService entityService,
                              ObjectService objectService,
                              DbDAO dbDAO,
                              ObjectDAO objectDAO) {
        this.entityService = entityService;
        this.objectService = objectService;
        this.dbDAO = dbDAO;
        this.objectDAO = objectDAO;

        assertions = new SpringContextAssertions(this);
        creator = new SpringContextCreator(this);

        CleanUtils.initCleaner(entityService);
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


    public SpringContextAssertions assertions() {
        return assertions;
    }

    public SpringContextCreator create(){
        return creator;
    }

    /**
     * Get all tables in dataBase
     */
    public Set<String> getAllTables() {
        return getDbDAO().getAllTables();
    }


    @WithReloadSessionFactory
    public <R> R actionWithReloadSessionFactory(Supplier<R> action) {
        return action.get();
    }

}
