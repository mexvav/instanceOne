package core.jpa.object.resolving;

import core.jpa.entity.field.EntityField;
import core.jpa.object.ObjectServiceException;
import core.jpa.object.resolving.resolvers.EntityFieldValueResolver;
import core.utils.suitable.AbstractHasSuitableObjectsByClass;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ResolvingService extends AbstractHasSuitableObjectsByClass<EntityFieldValueResolver> {
    public ResolvingService() {
        initializeSuitableObjects();
    }

    /**
     * Resolve value for {@link EntityField}
     *
     * @param entityField target {@link EntityField}
     * @param value       object for resolving
     */
    @SuppressWarnings("unchecked")
    public <R> R resolve(EntityField<R> entityField, Object value) {
        return (R) getSuitableObject(entityField).resolve(value);
    }

    protected String getPackage() {
        return getObjectClass().getPackage().getName();
    }

    protected Class<EntityFieldValueResolver> getObjectClass() {
        return EntityFieldValueResolver.class;
    }

    @SuppressWarnings("unchecked")
    protected Consumer<Class<? extends EntityFieldValueResolver>> getSuitableObjectClassConsumer() {
        return (entityFieldResolverClass) -> {
            try {
                EntityFieldValueResolver entityFieldValueResolver = entityFieldResolverClass.newInstance();
                entityFieldValueResolver.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ObjectServiceException(e);
            }
        };
    }
}