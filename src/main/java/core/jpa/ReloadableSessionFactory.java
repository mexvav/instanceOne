package core.jpa;

import com.google.common.collect.Sets;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.reflections.Reflections;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Implementation {@link SessionFactory}, allows to load new entity in runtime
 */
public class ReloadableSessionFactory implements SessionFactory {

    private SessionFactory target;
    private Properties properties;
    private StandardServiceRegistry serviceRegistry;
    private Set<Class<?>> entities;

    public ReloadableSessionFactory(Properties properties, StandardServiceRegistry serviceRegistry) {
        this.properties = properties;
        this.serviceRegistry = serviceRegistry;
        scanEntityInProject();
        initSessionFactory();
    }

    public Set<Class<?>> getEntities() {
        if (null == entities) {
            entities = Sets.newHashSet();
        }
        return entities;
    }

    /**
     * Get exists entities
     */
    private void scanEntityInProject() {
        Reflections reflections = new Reflections("core");
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
        getEntities().addAll(entities);
    }

    private void initSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        getEntities().stream().forEach(configuration::addAnnotatedClass);

        this.target = configuration.buildSessionFactory(serviceRegistry);
    }

    public void reloadSessionFactory() {
        initSessionFactory();
    }

    @Override
    public SessionFactoryOptions getSessionFactoryOptions() {
        return target.getSessionFactoryOptions();
    }

    @Override
    public SessionBuilder withOptions() {
        return target.withOptions();
    }

    @Override
    public Session openSession() throws HibernateException {
        return target.openSession();
    }

    @Override
    public Session getCurrentSession() throws HibernateException {
        return target.getCurrentSession();
    }

    @Override
    public StatelessSessionBuilder withStatelessOptions() {
        return target.withStatelessOptions();
    }

    @Override
    public StatelessSession openStatelessSession() {
        return target.openStatelessSession();
    }

    @Override
    public StatelessSession openStatelessSession(Connection connection) {
        return target.openStatelessSession(connection);
    }

    @Override
    public Statistics getStatistics() {
        return target.getStatistics();
    }

    @Override
    public void close() throws HibernateException {
        target.close();
    }

    @Override
    public Map<String, Object> getProperties() {
        return target.getProperties();
    }

    @Override
    public boolean isClosed() {
        return target.isClosed();
    }

    @Override
    public Cache getCache() {
        return target.getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return target.getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String s, javax.persistence.Query query) {
        target.addNamedQuery(s, query);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return target.unwrap(aClass);
    }

    @Override
    public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {
        target.addNamedEntityGraph(s, entityGraph);
    }

    @Override
    public Set getDefinedFilterNames() {
        return target.getDefinedFilterNames();
    }

    @Override
    public FilterDefinition getFilterDefinition(String s) throws HibernateException {
        return target.getFilterDefinition(s);
    }

    @Override
    public boolean containsFetchProfileDefinition(String s) {
        return target.containsFetchProfileDefinition(s);
    }

    @Override
    public TypeHelper getTypeHelper() {
        return target.getTypeHelper();
    }

    @Override
    public ClassMetadata getClassMetadata(Class aClass) {
        return target.getClassMetadata(aClass);
    }

    @Override
    public ClassMetadata getClassMetadata(String s) {
        return target.getClassMetadata(s);
    }

    @Override
    public CollectionMetadata getCollectionMetadata(String s) {
        return target.getCollectionMetadata(s);
    }

    @Override
    public Map<String, ClassMetadata> getAllClassMetadata() {
        return target.getAllClassMetadata();
    }

    @Override
    public Map getAllCollectionMetadata() {
        return target.getAllCollectionMetadata();
    }

    @Override
    public Reference getReference() throws NamingException {
        return target.getReference();
    }

    @Override
    public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> aClass) {
        return target.findEntityGraphsByType(aClass);
    }

    @Override
    public EntityManager createEntityManager() {
        return target.createEntityManager();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return target.createEntityManager(map);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return target.createEntityManager(synchronizationType);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return target.createEntityManager(synchronizationType, map);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return target.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return target.getMetamodel();
    }

    @Override
    public boolean isOpen() {
        return target.isOpen();
    }
}
