package core.mapping;

import com.google.common.collect.Maps;
import core.jpa.EntityBlank;
import core.jpa.attribute.Attribute;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperEntityBlankToJson extends MapperAbstract<EntityBlank, String> {

    @Override
    public Class<EntityBlank> getFromClass(){
        return EntityBlank.class;
    }

    @Override
    public Class<String> getToClass(){
        return String.class;
    }

    @Override
    public String transform(EntityBlank from) {
        Set<Map> attributes = from.getAttributes().stream()
                .map(attribute -> getMappingService().mapping(attribute, Map.class)).collect(Collectors.toSet());

        Map<String, Object> transition = Maps.newHashMap();
        transition.put("code", from.getCode());
        transition.put("title", from.getTitle());
        transition.put("attributes", attributes);

        return getMappingService().mapping(transition, String.class);
    }
}