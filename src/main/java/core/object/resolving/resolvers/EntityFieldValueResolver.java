package core.object.resolving.resolvers;

import core.entity.field.EntityField;
import core.object.resolving.ResolvingService;
import core.utils.suitable.SuitableObjectByClass;

import javax.annotation.Nullable;

public interface EntityFieldValueResolver<R, T extends EntityField<R>> extends SuitableObjectByClass<T, ResolvingService> {

    /**
     * Get class concrete {@link EntityField}
     */
    Class<T> getSuitableClass();

    /**
     * Resolve value for {@link EntityField}
     *
     * @param rawValue the value for resolving
     */
    R resolve(@Nullable Object rawValue);
}
