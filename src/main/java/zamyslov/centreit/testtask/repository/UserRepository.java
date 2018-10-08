package zamyslov.centreit.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zamyslov.centreit.testtask.entity.User;

import java.util.Optional;

/***
 * DAO сущности Пользователь
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}
