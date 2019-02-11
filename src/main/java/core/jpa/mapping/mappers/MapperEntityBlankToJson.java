package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.EntityBlank;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperEntityBlankToJson extends MapperAbstract<EntityBlank, String> {

    @Override
    public Class<EntityBlank> getFromClass() {
        return EntityBlank.class;
    }

    @Override
    public Class<String> getToClass() {
        return String.class;
    }

    @Override
    public String transform(EntityBlank from) {
        Set<Map> attributes = from.getAttributes().stream()
                .map(attribute -> getMappingService().mapping(attribute, Map.class)).collect(Collectors.toSet());

        Map<String, Object> transition = Maps.newHashMap();
        transition.put(Constants.HasCode.CODE, from.getCode());
        transition.put(Constants.HasTitle.TITLE, from.getTitle());
        transition.put(Constants.Entity.ATTRIBUTES, attributes);

        return getMappingService().mapping(transition, String.class);
    }
}