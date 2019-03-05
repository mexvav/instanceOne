package core.entity.field;

import com.google.common.collect.Maps;
import core.entity.EntityServiceException;
import core.utils.register.RegisteringServiceByCode;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EntityFieldFactory implements RegisteringServiceByCode<EntityField> {

    private Map<String, Class<? extends EntityField>> registeredObjects;

    /**
     * Get entity field  by type
     *
     * @param type the type of entity field
     */
    @Override
    public EntityField get(String type) {
        try {
            return getEntityFieldClass(type).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(EntityField field) {
        getEntityFieldClasses().put(field.getType(), field.getClass());
    }

    /**
     * Get class of entity field by type
     *
     * @param type the type of entity field
     */
    private Class<? extends EntityField> getEntityFieldClass(String type) {
        return getEntityFieldClasses().get(type);
    }


    /**
     * Get all classes of entity field
     */
    private Map<String, Class<? extends EntityField>> getEntityFieldClasses() {
        if (null == registeredObjects) {
            registeredObjects = Maps.newHashMap();
        }
        return registeredObjects;
    }
}
