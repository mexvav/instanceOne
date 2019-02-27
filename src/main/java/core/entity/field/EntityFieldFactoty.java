package core.entity.field;

import core.entity.EntityServiceException;
import core.mapping.MappingException;
import core.utils.suitable.AbstractHasSuitableObjectsByCode;

import java.util.function.Consumer;

public class EntityFieldFactoty extends AbstractHasSuitableObjectsByCode<EntityField> {

    public EntityFieldFactoty() {
        initializeSuitableObjects();
    }

    @Override
    protected String getPackage() {
        return EntityField.class.getPackage().getName();
    }

    protected Class<EntityField> getObjectClass() {
        return EntityField.class;
    }

    protected Consumer<Class<? extends EntityField>> getSuitableObjectClassConsumer() {
        return (entityFieldClass) -> {
            try {
                EntityField entityField = entityFieldClass.newInstance();
                initSuitableObject(entityField);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new MappingException(e);
            }
        };
    }

    @Override
    public void initSuitableObject(EntityField hasSuitableClassObject) {
        getSuitableObjects().put(hasSuitableClassObject.getType(), hasSuitableClassObject);
    }

    public EntityField createByType(String code) {
        try {
            return getEntityFieldClassByType(code).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityServiceException(e);
        }
    }

    private Class<? extends EntityField> getEntityFieldClassByType(String code) {
        return getSuitableObject(code).getClass();
    }
}
