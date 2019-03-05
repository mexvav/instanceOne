package core.entity.entities;

import core.Constants;
import core.interfaces.HasEntityCode;
import core.interfaces.HasId;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * SuperClass for runtime built Entity
 */
@MappedSuperclass
public abstract class AbstractEntity implements Entity {

    @Id
    @GeneratedValue
    @Column(name = Constants.HasId.ID, nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
