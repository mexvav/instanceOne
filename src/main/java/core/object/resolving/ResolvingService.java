package core.object.resolving;

import core.entity.field.EntityField;
import core.object.resolving.resolvers.EntityFieldValueResolver;
import core.utils.register.AbstractRegisteringServiceByClass;
import org.springframework.stereotype.Component;

@Component
public class ResolvingService extends AbstractRegisteringServiceByClass<EntityFieldValueResolver> {

    /**
     * Resolve value for {@link EntityField}
     *
     * @param entityField the target {@link EntityField}
     * @param value       the object for resolving
     */
    @SuppressWarnings("unchecked")
    public <R> R resolve(EntityField<R> entityField, Object value) {
        return (R) get(entityField).resolve(value);
    }
}