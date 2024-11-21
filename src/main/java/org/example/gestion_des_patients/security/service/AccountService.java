package org.example.gestion_des_patients.security.service;

import org.example.gestion_des_patients.security.entities.AppRole;
import org.example.gestion_des_patients.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String confirmedPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleToUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
