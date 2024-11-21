package org.example.gestion_des_patients.security.repositories;

import org.example.gestion_des_patients.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByRole(String role);
}
