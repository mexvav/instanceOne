package core.object.resolving.resolvers;

import core.entity.field.EntityField;
import core.object.resolving.ResolvingService;
import core.utils.register.RegisteredObjectWithClass;

import javax.annotation.Nullable;

public interface EntityFieldValueResolver<R, T extends EntityField<R>> extends RegisteredObjectWithClass<T, ResolvingService> {

    /**
     * Resolve value for {@link EntityField}
     *
     * @param rawValue the value for resolving
     */
    R resolve(@Nullable Object rawValue);
}
