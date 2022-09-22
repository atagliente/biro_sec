package it.biro.biro_sec.repositories;

import it.biro.biro_sec.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAll();

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE role SET deleted = true WHERE name = ?1",
            nativeQuery = true
    )
    void deleteByName(final String name);

}
