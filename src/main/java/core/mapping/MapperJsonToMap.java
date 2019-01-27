package core.mapping;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import core.jpa.EntityBlank;
import core.jpa.attribute.type.AttributeSimpleType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperJsonToMap extends MapperAbstract<String, Map> {

    private static Gson gson;

    private Gson getGson(){
        if(null == gson){
            gson = new Gson();
        }
        return gson;
    }

    @Override
    public Class<String> getFromClass(){
        return String.class;
    }

    @Override
    public Class<Map> getToClass(){
        return Map.class;
    }

    @Override
    public Map transform(String from) {
        return getGson().fromJson(from, Map.class);
    }
}