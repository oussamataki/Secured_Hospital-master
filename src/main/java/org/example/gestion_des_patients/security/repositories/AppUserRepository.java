package org.example.gestion_des_patients.security.repositories;

import org.example.gestion_des_patients.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
