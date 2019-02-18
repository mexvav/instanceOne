package core.jpa.object.resolvers;

import core.jpa.entity.fields.types.EntityFieldType;

public interface EntityFieldValueResolver<R, T extends EntityFieldType<R>> {

    Class<T> getSuitableType();

    R resolve(Object object);
}
