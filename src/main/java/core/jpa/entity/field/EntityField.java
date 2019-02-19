package core.jpa.entity.field;

import core.jpa.interfaces.HasCode;
import core.utils.suitable.SuitableObjectByCode;

/**
 * Param blank for entity field
 */
public interface EntityField<C> extends HasCode, SuitableObjectByCode<C, EntityFieldFactoty> {

    /**
     * Set field type, see {@link core.jpa.Constants.EntityFieldType}
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
