package core.mapping;

import com.google.gson.Gson;
import core.jpa.EntityBlank;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperMapToJson extends MapperAbstract<Map, String> {

    private static Gson gson;

    private Gson getGson(){
        if(null == gson){
            gson = new Gson();
        }
        return gson;
    }

    @Override
    public Class<Map> getFromClass(){
        return Map.class;
    }

    @Override
    public Class<String> getToClass(){
        return String.class;
    }

    @Override
    public String transform(Map from) {
        return getGson().toJson(from);
    }
}