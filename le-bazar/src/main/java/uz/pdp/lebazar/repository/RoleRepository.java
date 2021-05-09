package uz.pdp.lebazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lebazar.entity.Role;
import uz.pdp.lebazar.entity.enums.RoleName;

import java.util.Collection;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Set<Role> findAllByRoleNameIn(Collection<RoleName> roleNames);
}
