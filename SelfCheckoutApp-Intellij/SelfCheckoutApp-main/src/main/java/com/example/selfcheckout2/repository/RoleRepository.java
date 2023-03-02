package com.example.selfcheckout2.repository;
import com.example.selfcheckout2.data.ERole;
import com.example.selfcheckout2.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
