package core.jpa.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity implements NamedEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
