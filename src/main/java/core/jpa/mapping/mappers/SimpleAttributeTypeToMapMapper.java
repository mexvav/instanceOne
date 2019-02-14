package core.jpa.mapping.mappers;

import com.google.common.collect.Maps;
import core.jpa.Constants;
import core.jpa.entity.fields.types.SimpleEntityFieldType;

import java.util.Map;

public class SimpleAttributeTypeToMapMapper extends AbstractMapper<SimpleEntityFieldType, Map> {

    @Override
    public Class<SimpleEntityFieldType> getFromClass() {
        return SimpleEntityFieldType.class;
    }

    @Override
    public Class<Map> getToClass() {
        return Map.class;
    }

    @Override
    public Map transform(SimpleEntityFieldType from) {
        Map<String, Object> transition = Maps.newHashMap();
        transition.put(Constants.HasCode.CODE, from.getCode());
        return transition;
    }
}
