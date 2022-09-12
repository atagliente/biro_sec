package it.biro.biro_sec.repositories;

import it.biro.biro_sec.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(
            value = "SELECT * FROM role WHERE id NOT IN (SELECT role_id FROM role_users WHERE user_id = ?1)",
            nativeQuery = true
    )
    Set<Role> getUserNotRoles(final Long id);

}
