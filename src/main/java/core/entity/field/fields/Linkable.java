package core.entity.field.fields;

import core.entity.entities.Entity;

public interface Linkable {

    /**
     * Set linked entity
     */
    void setLinkClass(Class<Entity> entity);
}
