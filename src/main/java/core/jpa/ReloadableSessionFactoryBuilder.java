package core.jpa;

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
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/**
 * Implementation {@link SessionFactory}, allows to load new entity in runtime
 */
public class ReloadableSessionFactoryBuilder {

    private Properties properties;
    private StandardServiceRegistry serviceRegistry;
    private String[] packagesToScan;

    private Set<Class<?>> entities;

    private SessionFactory sessionFactory;
    private HibernateTransactionManager transactionManager;

    /**
     * @param properties      - hibernate property
     * @param serviceRegistry - standard service registry
     */
    public ReloadableSessionFactoryBuilder(
            Properties properties, StandardServiceRegistry serviceRegistry, String... packagesToScan) {
        this.properties = properties;
        this.serviceRegistry = serviceRegistry;
        this.packagesToScan = packagesToScan;
        scanEntityInProject();
        initSessionFactory();
    }

    public ReloadableSessionFactory build() {
        return (ReloadableSessionFactory)
                Proxy.newProxyInstance(
                        this.getClass().getClassLoader(),
                        new Class[]{ReloadableSessionFactory.class},
                        (proxy, method, args) -> {
                            if (Arrays.asList(ReloadableSessionFactory.class.getDeclaredMethods())
                                    .contains(method)) {
                                return this.getClass()
                                        .getDeclaredMethod(method.getName(), method.getParameterTypes())
                                        .invoke(this, args);
                            }
                            return method.invoke(sessionFactory, args);
                        });
    }

    /**
     * Method for {@link ReloadableSessionFactory} proxy
     */
    @SuppressWarnings("unused")
    private PlatformTransactionManager getPlatformTransactionManager() {
        return (PlatformTransactionManager)
                Proxy.newProxyInstance(
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
     * Method for {@link ReloadableSessionFactory} proxy
     */
    private Set<Class<?>> getEntities() {
        if (null == entities) {
            entities = Sets.newHashSet();
        }
        return entities;
    }

    /**
     * Method for {@link ReloadableSessionFactory} proxy
     */
    @SuppressWarnings("unused")
    private void reloadSessionFactory() {
        initSessionFactory();
    }

    /**
     * Scanning packages for exists entities
     */
    @SuppressWarnings("unused")
    private void scanEntityInProject() {
        for (String packageToScan : packagesToScan) {
            try {
                scanEntityInPackage(packageToScan);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * Scanning package for exists entities
     *
     * @param packageToScan - package with entities
     */
    private void scanEntityInPackage(@NotNull String packageToScan) {
        Reflections reflections = new Reflections(packageToScan);
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
        getEntities().addAll(entities);
    }

    /**
     * Create new {@link SessionFactory} and {@link HibernateTransactionManager} with all entities
     */
    private void initSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        getEntities().forEach(configuration::addAnnotatedClass);

        this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        this.transactionManager = new HibernateTransactionManager(sessionFactory);
    }
}
