package core.object.resolving.resolvers;

import core.entity.field.EntityField;
import core.object.resolving.ResolvingService;

public abstract class AbstractFieldValueResolver<R, T extends EntityField<R>> implements EntityFieldValueResolver<R, T> {

    private ResolvingService resolvingService;

    private Class<T> suitableClass;

    AbstractFieldValueResolver(ResolvingService resolvingService, Class<T> suitableClass) {
        this.resolvingService = resolvingService;
        this.suitableClass = suitableClass;
        resolvingService.register(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<T> getSuitableClass() {
        return suitableClass;
    }

    protected ResolvingService getResolvingService() {
        return resolvingService;
    }
}
