package core.jpa.mapping.mappers;

import com.google.gson.Gson;

import java.util.Map;

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