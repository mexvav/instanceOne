package core.jpa.mapping.mappers;

import com.google.gson.Gson;

import java.util.Map;

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