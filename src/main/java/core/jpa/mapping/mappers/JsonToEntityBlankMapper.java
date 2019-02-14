package core.jpa.mapping.mappers;

import core.jpa.Constants;
import core.jpa.entity.EntityClass;
import core.jpa.entity.fields.EntityField;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonToEntityBlankMapper extends AbstractMapper<String, EntityClass> {

    @Override
    public Class<String> getFromClass() {
        return String.class;
    }

    @Override
    public Class<EntityClass> getToClass() {
        return EntityClass.class;
    }

    @Override
    public EntityClass transform(String from) {
        Map transition = getMappingService().mapping(from, Map.class);

        @SuppressWarnings("unchecked")
        Collection<Map> attributesTransition =
                (Collection<Map>) transition.get(Constants.Entity.ATTRIBUTES);

        Set<EntityField> entityFields =
                attributesTransition.stream()
                        .map(attribute -> getMappingService().mapping(attribute, EntityField.class))
                        .collect(Collectors.toSet());

        EntityClass entityClass = new EntityClass();
        entityClass.setCode((String) transition.get(Constants.HasCode.CODE));
        entityClass.setTitle((String) transition.get(Constants.HasTitle.TITLE));
        entityClass.setFields(entityFields);

        return entityClass;
    }
}
