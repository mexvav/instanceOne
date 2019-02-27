package core.object.resolving.resolvers;

import core.entity.field.EntityField;
import core.object.resolving.ResolvingService;

/**
 * Abstract resolver
 */
public abstract class AbstractFieldValueResolver<R, T extends EntityField<R>> implements EntityFieldValueResolver<R, T> {

    protected ResolvingService resolvingService;

    /**
     * Initialize resolver
     *
     * @param resolvingService resolving service
     */
    @Override
    public void init(ResolvingService resolvingService) {
        this.resolvingService = resolvingService;
        resolvingService.initSuitableObject(this);
    }
}
