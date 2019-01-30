package core.mapping;

import com.google.common.collect.Maps;
import core.jpa.EntityBlank;
import core.jpa.attribute.Attribute;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperJsonToEntityBlank extends MapperAbstract<String, EntityBlank> {

    @Override
    public Class<String> getFromClass(){
        return String.class;
    }

    @Override
    public Class<EntityBlank> getToClass(){
        return EntityBlank.class;
    }

    @Override
    public EntityBlank transform(String from) {
        Map transition = getMappingService().mapping(from, Map.class);
        Collection<Map> attributesTransition = (Collection<Map>) transition.get("attributes");
        Set<Attribute> attributes = attributesTransition.stream()
                .map(attribute -> getMappingService().mapping(attribute, Attribute.class))
                .collect(Collectors.toSet());

        EntityBlank entityBlank = new EntityBlank();
        entityBlank.setCode((String)transition.get("code"));
        entityBlank.setTitle((String)transition.get("title"));
        entityBlank.setAttributes(attributes);

        return entityBlank;
    }
}