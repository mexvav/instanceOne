package core.object.processing;

import core.interfaces.HasEntityCode;
import core.interfaces.HasId;

import java.util.Map;
import java.util.Set;

/**
 * Object for storage params from entity object
 */
public interface DataObject extends HasId, HasEntityCode {

    /**
     * Get all property names
     */
    Set<String> propertyNames();

    /**
     * Get property
     *
     * @param key the name of property
     */
    Object get(String key);

    /**
     * Get all properties
     */
    Map<String, Object> getProperties();
}
