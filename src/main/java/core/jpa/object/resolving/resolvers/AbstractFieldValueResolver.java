package core.jpa.object.resolving.resolvers;

import core.jpa.entity.field.EntityField;
import core.jpa.object.resolving.ResolvingService;


public abstract class AbstractFieldValueResolver<R, T extends EntityField<R>>  implements EntityFieldValueResolver<R, T>{

    ResolvingService resolvingService;

    @Override
    public void init(ResolvingService resolvingService) {
        this.resolvingService = resolvingService;
        resolvingService.initSuitableObject(this);
    }
}
