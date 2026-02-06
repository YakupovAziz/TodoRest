package todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {


}
