package core.jpa.session_factory;

import org.hibernate.SessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <b>Methods for {@link SessionFactory}, allows to load new entity in runtime</b>
 *
 * <ul>
 * For loading entity by classes with metadata for entity,
 * annotated {@link javax.persistence.Entity}
 * <li>use {@link #addEntityPackage(String)} for scanning package with entity classes;</li>
 * <li>use {@link #addEntity(Class)} for adding single entity;</li>
 * <li>use {@link #removeEntity(Class)} for removing entity.</li>
 * </ul>
 *
 * <p>After modification use {@link #reloadSessionFactory()}</p>
 *
 * <p>For getting collection with all loaded entity use {@link #getPersistentEntities()}</p>
 * <p>For getting current modification entity collection use {@link #getCurrentEntities()}</p>
 */
interface ReloadableMethods {

    /**
     * <b>Get {@link PlatformTransactionManager} for current {@link SessionFactory}</b>
     */
    PlatformTransactionManager getPlatformTransactionManager();

    /**
     * <b>Get all non persistent classes with metadata for entity,
     * annotated {@link javax.persistence.Entity}</b>
     *
     * <p>For update entities use {@link #reloadSessionFactory()}</p>
     *
     * @return unmodifiable set of classes with annotation {@link javax.persistence.Entity}
     */
    Set<Class<?>> getCurrentEntities();

    /**
     * <b>Get all persistent classes with metadata for existing entity</b>
     *
     * @return unmodifiable set of classes with annotation {@link javax.persistence.Entity}
     */
    Set<Class<?>> getPersistentEntities();

    /**
     * <b>Add class with metadata for entity, annotated {@link javax.persistence.Entity}
     * to collection for loading entity.</b>
     *
     * <p>For update entities use {@link #reloadSessionFactory()}</p>
     *
     * @param entity class with annotation {@link javax.persistence.Entity}
     */
    void addEntity(@NotNull Class<?> entity);

    /**
     * <b>Removing entity by class annotated {@link javax.persistence.Entity}</b>
     *
     * <p>For update entities use {@link #reloadSessionFactory()}</p>
     *
     * @param entity class with annotation {@link javax.persistence.Entity}
     */
    void removeEntity(@NotNull Class<?> entity);

    /**
     * <b>Add package for scanning classes, annotated {@link javax.persistence.Entity}</b>
     *
     * <p>For update entities use {@link #reloadSessionFactory()}</p>
     *
     * @param entityPackage the name of package with entity
     */
    void addEntityPackage(String entityPackage);

    /**
     * <b>Updating entities by {@link #getCurrentEntities()}</b>
     *
     * <p>{@link SessionFactory} will be recreated</p>
     */
    void reloadSessionFactory();
}
