package core;

import javax.persistence.*;

/**
 * This is just test entity
 */
@Entity
@Table(name = "test_table")
public class TestEntity {

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
