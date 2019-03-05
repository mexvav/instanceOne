package core.session_factory;

import com.google.common.collect.Sets;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Builder for {@link ReloadableSessionFactory}
 */
public class ReloadableSessionFactoryBuilder {

    private ProxyReloadableMethodsSessionFactory reloadableSessionFactory;

    /**
     * @param properties      - hibernate property
     * @param serviceRegistry - standard service registry
     */
    public ReloadableSessionFactoryBuilder(
            Properties properties,
            StandardServiceRegistry serviceRegistry,
            String... packagesToScan) {

        this.reloadableSessionFactory = new ProxyReloadableMethodsSessionFactory(properties, serviceRegistry, packagesToScan);
    }

    /**
     * Build {@link ReloadableSessionFactory}
     */
    public ReloadableSessionFactory build() {
        return (ReloadableSessionFactory) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{ReloadableSessionFactory.class},
                (proxy, method, args) -> {
                    if (Arrays.asList(ReloadableMethods.class.getDeclaredMethods())
                            .contains(method)) {
                        return ProxyReloadableMethodsSessionFactory.class
                                .getDeclaredMethod(method.getName(), method.getParameterTypes())
                                .invoke(reloadableSessionFactory, args);
                    }
                    return method.invoke(reloadableSessionFactory.getTargetSessionFactory(), args);
                });
    }

    /**
     * Proxy class, implemented {@link ReloadableMethods} for created {@link ReloadableSessionFactory}
     */
    private static class ProxyReloadableMethodsSessionFactory implements ReloadableMethods {
        private Properties properties;
        private StandardServiceRegistry serviceRegistry;
        private Set<String> packagesToScan;

        private Set<Class<?>> currentEntities;
        private Set<Class<?>> persistentEntities;

        private SessionFactory sessionFactory;
        private HibernateTransactionManager transactionManager;

        ProxyReloadableMethodsSessionFactory(
                Properties properties,
                StandardServiceRegistry serviceRegistry,
                String... packagesToScan) {
            this.properties = properties;
            this.serviceRegistry = serviceRegistry;
            this.packagesToScan = Sets.newHashSet(packagesToScan);
            initSessionFactory();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Class<?>> getCurrentEntities() {
            return Collections.unmodifiableSet(getModificationCurrentEntities());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Class<?>> getPersistentEntities() {
            return Collections.unmodifiableSet(getModificationPersistentEntities());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addEntity(@NotNull Class<?> entity) {
            Entity entityAnnotation = Objects.requireNonNull(entity).getAnnotation(Entity.class);
            if(null == entityAnnotation){
                //todo add
            }
            getModificationCurrentEntities().add(entity);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeEntity(@NotNull Class<?> entity) {
            getModificationCurrentEntities().remove(Objects.requireNonNull(entity));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addEntityPackage(@NotNull String entityPackage) {
            packagesToScan.add(Objects.requireNonNull(entityPackage));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reloadSessionFactory() {
            initSessionFactory();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public PlatformTransactionManager getPlatformTransactionManager() {
            return (PlatformTransactionManager) Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class[]{
                            ResourceTransactionManager.class,
                            BeanFactoryAware.class,
                            InitializingBean.class,
                            PlatformTransactionManager.class,
                            Serializable.class
                    },
                    (proxy, method, args) -> method.invoke(transactionManager, args));
        }

        /**
         * Get actual session factory
         */
        SessionFactory getTargetSessionFactory() {
            return sessionFactory;
        }


        /**
         * <b>Get all non persistent classes with metadata for entity,
         * annotated {@link javax.persistence.Entity}</b>
         *
         * @return modifiable set of classes with annotation {@link javax.persistence.Entity}
         */
        private Set<Class<?>> getModificationCurrentEntities() {
            if (null == currentEntities) {
                currentEntities = Sets.newHashSet();
            }
            return currentEntities;
        }

        /**
         * <b>Get all persistent classes with metadata for entity,
         * annotated {@link javax.persistence.Entity}</b>
         *
         * @return modifiable set of classes with annotation {@link javax.persistence.Entity}
         */
        private Set<Class<?>> getModificationPersistentEntities() {
            if (null == persistentEntities) {
                persistentEntities = Sets.newHashSet();
            }
            return persistentEntities;
        }

        /**
         * Scanning packages ({@link #packagesToScan}) for classes with metadata for entity,
         * annotated {@link javax.persistence.Entity}</b>
         */
        private void scanEntityInPackages() {
            for (String packageToScan : packagesToScan) {
                try {
                    scanEntityInPackage(packageToScan);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        /**
         * Scanning single package for classes with metadata for entity,
         * annotated {@link javax.persistence.Entity}
         *
         * @param packageToScan package for scanning
         */
        private void scanEntityInPackage(@NotNull String packageToScan) {
            Reflections reflections = new Reflections(Objects.requireNonNull(packageToScan));
            Set<Class<?>> entities = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
            getModificationCurrentEntities().addAll(entities);
        }

        /**
         * Initializing session factory
         */
        private void initSessionFactory() {
            scanEntityInPackages();
            if (getCurrentEntities().equals(getPersistentEntities())) {
                return;
            }

            Configuration configuration = new Configuration();
            configuration.addProperties(properties);
            getModificationCurrentEntities().forEach(configuration::addAnnotatedClass);

            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            this.transactionManager = new HibernateTransactionManager(sessionFactory);
            persistentEntities = Sets.newHashSet(getCurrentEntities());
        }
    }
}
