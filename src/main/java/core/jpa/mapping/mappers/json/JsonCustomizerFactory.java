package core.jpa.mapping.mappers.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.jpa.entity.building.BuildingException;
import core.utils.suitable.AbstractHasSuitableObjectsByClass;

import java.util.function.Consumer;

public class JsonCustomizerFactory extends AbstractHasSuitableObjectsByClass<JsonCustomizer> {
    private ObjectMapper mapper;

    private SimpleModule module = new SimpleModule(
            JsonCustomizer.class.getName(),
            new Version(1, 0, 0, null, null, null));

    public JsonCustomizerFactory(){
        initializeSuitableObjects();
    }

    public ObjectMapper getJsonMapper() {
        if (null == mapper) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initSuitableObject(JsonCustomizer hasSuitableClassObject) {
        if(hasSuitableClassObject instanceof AbstractJsonCustomSerializer){
            module.addSerializer(hasSuitableClassObject.getSuitableClass(),
                    (AbstractJsonCustomSerializer)hasSuitableClassObject);
        }
        if(hasSuitableClassObject instanceof AbstractJsonCustomDeserializer){
            module.addDeserializer(hasSuitableClassObject.getSuitableClass(),
                    (AbstractJsonCustomDeserializer)hasSuitableClassObject);
        }
        getJsonMapper().registerModule(module);
    }

    protected String getPackage(){
        return getObjectClass().getPackage().getName();
    }

    protected Class<JsonCustomizer> getObjectClass(){
        return JsonCustomizer.class;
    }

    @SuppressWarnings("unchecked")
    protected Consumer<Class<? extends JsonCustomizer>> getSuitableObjectClassConsumer(){
        return (jsonCustomizerClass) -> {
            try {
                JsonCustomizer jsonCustomizer = jsonCustomizerClass.newInstance();
                jsonCustomizer.init(this);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BuildingException(e.getMessage());
            }
        };
    }
}
