package core.entity.field;

import core.Constants;
import core.utils.register.RegisteredObjectWithCode;

/**
 * Param blank for entity field
 */
public interface EntityField<C> extends RegisteredObjectWithCode<C, EntityFieldFactory> {

    /**
     * Set field type, see {@link Constants.EntityFieldType}
     */
    String getType();

    /**
     * Is field required
     */
    boolean isRequired();

    /**
     * Set field required
     */
    void setRequired(boolean required);

    /**
     * Is field unique
     */
    boolean isUnique();

    /**
     * Set field unique
     */
    void setUnique(boolean unique);

    /**
     * Class for field value
     */
    Class<C> getFieldClass();
}
