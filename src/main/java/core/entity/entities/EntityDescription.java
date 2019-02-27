package core.entity.entities;

import core.Constants;
import core.interfaces.HasCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for storage information about runtime-built entity
 */
@Entity
@Table(name = Constants.EntityDescription.TABLE)
public class EntityDescription extends AbstractEntity implements HasCode {

    public static final String entityName = Constants.EntityDescription.ENTITY_NAME;

    @Column(name = Constants.HasCode.CODE, nullable = false, unique = true)
    private String code;

    @Column(name = Constants.EntityDescription.DESCRIPTION, length = 234)
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
