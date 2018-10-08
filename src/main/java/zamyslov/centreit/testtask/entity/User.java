package zamyslov.centreit.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/***
 * Описание сущности Пользователь
 */
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    @NotEmpty
    @Column(length = 60)        // длина строки, выдаваемая BCrypt
    private String password;    // хеш пароля
    @ManyToOne
    private Role role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
