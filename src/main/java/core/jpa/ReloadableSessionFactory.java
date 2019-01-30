package core.jpa;

import org.hibernate.SessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Set;

/**
 * Version {@link SessionFactory}, allows to load new entity in runtime
 */
public interface ReloadableSessionFactory extends SessionFactory {

    /**
     * Get PlatformTransactionManager
     */
    PlatformTransactionManager getPlatformTransactionManager();

    /**
     * Get all registered entities
     */
    Set<Class<?>> getEntities();

    /**
     * Load all registered entities
     */
    void reloadSessionFactory();
}
