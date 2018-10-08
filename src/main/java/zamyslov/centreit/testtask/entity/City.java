package zamyslov.centreit.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/***
 * Описание сущности Город
 */
@Entity
public class City implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    @Column(unique = true) // в данной предметной области можно считать уникальным
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
