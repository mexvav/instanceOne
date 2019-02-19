package core.jpa.entity.field;

import core.jpa.entity.EntityServiceException;
import core.jpa.entity.building.BuildingException;
import core.utils.suitable.AbstractHasSuitableObjectsByCode;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EntityFieldFactoty extends AbstractHasSuitableObjectsByCode<EntityField> {

    public EntityFieldFactoty(){
        initializeSuitableObjects();
    }

    @Override
    protected String getPackage() {
        return EntityField.class.getPackage().getName();
    }

    protected Class<EntityField> getSuitableClass() {
        return EntityField.class;
    }

    protected Consumer<Class<? extends EntityField>> getSuitableObjectClassConsumer() {
        return (entityFieldClass) -> {
            try {
                EntityField entityField = entityFieldClass.newInstance();
                initSuitableObject(entityField);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BuildingException(e.getMessage());
            }
        };
    }

    public EntityField createByCode(String code) {
        try {
            return getEntityFieldClassByCode(code).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityServiceException(e.getMessage());
        }
    }

    public Class<? extends EntityField> getEntityFieldClassByCode(String code) {
        return getSuitableObject(code).getClass();
    }
}
