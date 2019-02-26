package core.jpa.object.resolving.resolvers;

import core.jpa.entity.field.EntityField;
import core.jpa.object.resolving.ResolvingService;
import core.utils.suitable.SuitableObjectByClass;

public interface EntityFieldValueResolver<R, T extends EntityField<R>> extends SuitableObjectByClass<T, ResolvingService> {

    Class<T> getSuitableClass();

    R resolve(Object object);
}
