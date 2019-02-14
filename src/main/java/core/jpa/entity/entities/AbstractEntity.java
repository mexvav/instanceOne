package core.jpa.entity.entities;

import core.jpa.Constants;
import core.jpa.interfaces.HasEntityCode;
import core.jpa.interfaces.HasId;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements HasEntityCode, HasId {

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
