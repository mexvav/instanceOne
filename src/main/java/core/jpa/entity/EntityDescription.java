package core.jpa.entity;

import core.Constants;

import javax.persistence.*;

/**
 * Entity for storage information about runtime-created entity
 */
@Entity
@Table(name = Constants.dbEntityPrefix + EntityDescription.entityName)
public class EntityDescription extends AbstractEntity {
    public static final String entityName = "description";

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
