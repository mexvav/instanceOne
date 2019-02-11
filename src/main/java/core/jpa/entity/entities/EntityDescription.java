package core.jpa.entity.entities;

import core.jpa.Constants;
import core.jpa.interfaces.HasCode;

import javax.persistence.*;

/**
 * Entity for storage information about runtime-created entity
 */
@Entity
@Table(name = Constants.EntityDescription.TABLE)
public class EntityDescription extends AbstractEntity implements HasCode {
    public static final String entityName = Constants.EntityDescription.ENTITY_NAME;
    @Column(name = Constants.HasCode.CODE, nullable = false, unique = true)
    private String code;
    @Column(name = Constants.EntityDescription.DESCRIPTION)
    private String description;

    @Override
    public String getEntityCode() {
        return entityName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
