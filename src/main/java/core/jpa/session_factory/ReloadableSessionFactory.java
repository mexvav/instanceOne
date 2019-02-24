package core.jpa.session_factory;

import org.hibernate.SessionFactory;

/**
 * <b>Version of {@link SessionFactory}, allows to load new entity in runtime</b>
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
public interface ReloadableSessionFactory extends Reloadable, SessionFactory {

}
