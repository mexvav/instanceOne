package core.mapping.mappers.json;


public interface JsonCustomizer<T>  {

    /**
     * Get suitable class for this customizer
     */
    Class<T> getRegisteredClass();
}
