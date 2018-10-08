package zamyslov.centreit.testtask.json;

import java.io.Serializable;

/***
 * Содержит обязательные данные запроса пользователя к веб-сервису
 */
public class BasicRequest implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
