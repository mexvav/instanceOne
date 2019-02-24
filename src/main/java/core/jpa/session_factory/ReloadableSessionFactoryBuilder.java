package core.jpa.session_factory;

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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Builder for {@link ReloadableSessionFactory}
 */
public class ReloadableSessionFactoryBuilder {

    private ProxyReloadableSessionFactory reloadableSessionFactory;

    /**
     * @param properties      - hibernate property
     * @param serviceRegistry - standard service registry
     */
    public ReloadableSessionFactoryBuilder(
            Properties properties,
            StandardServiceRegistry serviceRegistry,
            String... packagesToScan) {

        this.reloadableSessionFactory = new ProxyReloadableSessionFactory(properties, serviceRegistry, packagesToScan);
    }

    /**
     * Build {@link ReloadableSessionFactory}
     */
    public ReloadableSessionFactory build() {
        return (ReloadableSessionFactory) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{ReloadableSessionFactory.class},
                (proxy, method, args) -> {
                    if (Arrays.asList(Reloadable.class.getDeclaredMethods())
                            .contains(method)) {
                        return ProxyReloadableSessionFactory.class
                                .getDeclaredMethod(method.getName(), method.getParameterTypes())
                                .invoke(reloadableSessionFactory, args);
                    }
                    return method.invoke(reloadableSessionFactory.getTargetSessionFactory(), args);
                });
    }

    /**
     * Proxy class for implementation Reloadable
     */
    private static class ProxyReloadableSessionFactory implements Reloadable {
        private Properties properties;
        private StandardServiceRegistry serviceRegistry;
        private Set<String> packagesToScan;

        private Set<Class<?>> currentEntities;
        private Set<Class<?>> persistentEntities;

        private SessionFactory sessionFactory;
        private HibernateTransactionManager transactionManager;

        ProxyReloadableSessionFactory(
                Properties properties,
                StandardServiceRegistry serviceRegistry,
                String... packagesToScan) {
            this.properties = properties;
            this.serviceRegistry = serviceRegistry;
            this.packagesToScan = Sets.newHashSet(packagesToScan);
            initSessionFactory();
        }

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

        @Override
        public Set<Class<?>> getCurrentEntities() {
            return Collections.unmodifiableSet(getModificationCurrentEntities());
        }

        @Override
        public Set<Class<?>> getPersistentEntities() {
            return Collections.unmodifiableSet(getModificationPersistentEntities());
        }

        @Override
        public void addEntity(@NotNull Class<?> entity) {
            getModificationCurrentEntities().add(Objects.requireNonNull(entity));
        }

        @Override
        public void removeEntity(@NotNull Class<?> entity) {
            getModificationCurrentEntities().remove(Objects.requireNonNull(entity));
        }

        @Override
        public void addEntityPackage(String entityPackage) {
            packagesToScan.add(entityPackage);
        }

        @Override
        public void reloadSessionFactory() {
            initSessionFactory();
        }

        SessionFactory getTargetSessionFactory() {
            return sessionFactory;
        }

        private Set<Class<?>> getModificationCurrentEntities() {
            if (null == currentEntities) {
                currentEntities = Sets.newHashSet();
            }
            return currentEntities;
        }

        private Set<Class<?>> getModificationPersistentEntities() {
            if (null == persistentEntities) {
                persistentEntities = Sets.newHashSet();
            }
            return persistentEntities;
        }

        private void scanEntityInPackages() {
            for (String packageToScan : packagesToScan) {
                try {
                    scanEntityInPackage(packageToScan);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        private void scanEntityInPackage(@NotNull String packageToScan) {
            Reflections reflections = new Reflections(packageToScan);
            Set<Class<?>> entities = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
            getModificationCurrentEntities().addAll(entities);
        }

        private void initSessionFactory() {
            scanEntityInPackages();
            Configuration configuration = new Configuration();
            configuration.addProperties(properties);
            if (getCurrentEntities().equals(getPersistentEntities())) {
                return;
            }

            getModificationCurrentEntities().forEach(configuration::addAnnotatedClass);

            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            this.transactionManager = new HibernateTransactionManager(sessionFactory);
            persistentEntities = Sets.newHashSet(getCurrentEntities());
        }
    }
}
