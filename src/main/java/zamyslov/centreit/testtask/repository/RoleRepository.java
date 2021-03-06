package zamyslov.centreit.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zamyslov.centreit.testtask.entity.Role;

/***
 * DAO сущности Роль
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
