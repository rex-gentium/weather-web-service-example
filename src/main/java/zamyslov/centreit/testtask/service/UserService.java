package zamyslov.centreit.testtask.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zamyslov.centreit.testtask.exception.AccessRightsException;
import zamyslov.centreit.testtask.exception.PasswordMismatchException;
import zamyslov.centreit.testtask.exception.RecordNotFoundException;
import zamyslov.centreit.testtask.entity.Permissions;
import zamyslov.centreit.testtask.entity.User;
import zamyslov.centreit.testtask.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User getUserByName(String username) throws RecordNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        return user.orElseThrow(() -> new RecordNotFoundException("User with login " + username + " not found"));
    }

    public User getUserByCredentials(String username, String password) throws RecordNotFoundException, PasswordMismatchException {
        User user = getUserByName(username);
        boolean isCorrectPassword = passwordEncoder.matches(password, user.getPassword());
        if (!isCorrectPassword)
            throw new PasswordMismatchException("Incorrect password for user " + username);
        return user;
    }

    public User getUserByCredentialsAndPermission(String username, String password, Integer permission) throws RecordNotFoundException, PasswordMismatchException, AccessRightsException {
        User user = getUserByCredentials(username, password);
        if (!Permissions.hasPermission(permission, user.getRole().getPermissions()))
            throw new AccessRightsException("User " + username + " doesn't have required permission");
        return user;
    }
}
