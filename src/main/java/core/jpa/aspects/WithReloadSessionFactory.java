package core.jpa.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * Wrapping current method in
 * {@link core.jpa.entity.EntityService#actionWithReloadSessionFactory(Supplier)}
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithReloadSessionFactory {
}
