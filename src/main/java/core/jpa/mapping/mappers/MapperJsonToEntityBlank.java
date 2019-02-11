package core.jpa.mapping.mappers;

import core.jpa.Constants;
import core.jpa.entity.EntityBlank;
import core.jpa.entity.attribute.Attribute;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperJsonToEntityBlank extends MapperAbstract<String, EntityBlank> {

    @Override
    public Class<String> getFromClass() {
        return String.class;
    }

    @Override
    public Class<EntityBlank> getToClass() {
        return EntityBlank.class;
    }

    @Override
    public EntityBlank transform(String from) {
        Map transition = getMappingService().mapping(from, Map.class);

        @SuppressWarnings("unchecked")
        Collection<Map> attributesTransition = (Collection<Map>) transition.get(Constants.Entity.ATTRIBUTES);

        Set<Attribute> attributes = attributesTransition.stream()
                .map(attribute -> getMappingService().mapping(attribute, Attribute.class))
                .collect(Collectors.toSet());

        EntityBlank entityBlank = new EntityBlank();
        entityBlank.setCode((String) transition.get(Constants.HasCode.CODE));
        entityBlank.setTitle((String) transition.get(Constants.HasTitle.TITLE));
        entityBlank.setAttributes(attributes);

        return entityBlank;
    }
}