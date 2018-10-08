package zamyslov.centreit.testtask.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.*;
import zamyslov.centreit.testtask.entity.*;
import zamyslov.centreit.testtask.repository.UserRepository;

import java.util.Optional;

/***
 * Служба, отвечающая за взаимодействие с данными о пользователях
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /***
     * Возвращает пользователя по логину
     * @param username логин
     * @return пользователь
     * @throws RecordNotFoundException если пользователь с таким логином не найден в БД
     */
    public User getUserByName(String username) throws RecordNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        return user.orElseThrow(() -> new RecordNotFoundException("User with login " + username + " not found"));
    }

    /***
     * Возвращает пользователя по логину и паролю
     * @param username логин
     * @param password пароль (в открытом виде)
     * @return пользователь
     * @throws RecordNotFoundException если пользователь с таким логином не найден в БД
     * @throws PasswordMismatchException если передан неправильный пароль
     */
    public User getUserByCredentials(String username, String password) throws RecordNotFoundException, PasswordMismatchException {
        User user = getUserByName(username);
        boolean isCorrectPassword = passwordEncoder.matches(password, user.getPassword());
        if (!isCorrectPassword)
            throw new PasswordMismatchException("Incorrect password for user " + username);
        return user;
    }

    /***
     * Возвращает пользователя по логину и паролю с указанными правами
     * @param username логин
     * @param password пароль (в открытом виде)
     * @param permission права доступа
     * @return пользователь
     * @throws RecordNotFoundException если пользователь с таким логином не найден в БД
     * @throws PasswordMismatchException если передан неправильный пароль
     * @throws AccessRightsException если у пользователя нет указанных прав доступа
     */
    public User getUserByCredentialsAndPermission(String username, String password, Integer permission) throws RecordNotFoundException, PasswordMismatchException, AccessRightsException {
        User user = getUserByCredentials(username, password);
        if (!Permissions.hasPermission(permission, user.getRole().getPermissions()))
            throw new AccessRightsException("User " + username + " doesn't have required permission");
        return user;
    }
}
