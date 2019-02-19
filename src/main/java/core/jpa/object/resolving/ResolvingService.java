package core.jpa.object.resolving;

import core.jpa.entity.building.BuildingException;
import core.jpa.entity.field.EntityField;
import core.jpa.object.resolving.resolvers.EntityFieldValueResolver;
import core.utils.suitable.AbstractHasSuitableObjectsByClass;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ResolvingService  extends AbstractHasSuitableObjectsByClass<EntityFieldValueResolver> {
    public ResolvingService() {
        initializeSuitableObjects();
    }

    @SuppressWarnings("unchecked")
    public <R> R resolve(EntityField<R> entityField, Object value){
        return (R) getSuitableObject(entityField).resolve(value);
    }

    protected String getPackage(){
        return getSuitableClass().getPackage().getName();
    }

    protected Class<EntityFieldValueResolver> getSuitableClass(){
        return EntityFieldValueResolver.class;
    }

    @SuppressWarnings("unchecked")
    protected Consumer<Class<? extends EntityFieldValueResolver>> getSuitableObjectClassConsumer(){
        return (entityFieldResolverClass) -> {
            try {
                EntityFieldValueResolver entityFieldValueResolver = entityFieldResolverClass.newInstance();
                entityFieldValueResolver.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BuildingException(e.getMessage());
            }
        };
    }
}