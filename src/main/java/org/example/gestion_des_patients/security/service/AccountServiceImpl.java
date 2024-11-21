package org.example.gestion_des_patients.security.service;

import lombok.AllArgsConstructor;
import org.example.gestion_des_patients.security.entities.AppRole;
import org.example.gestion_des_patients.security.entities.AppUser;
import org.example.gestion_des_patients.security.repositories.AppRoleRepository;
import org.example.gestion_des_patients.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String confirmedPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("User already exists");
        if (!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser newUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        return appUserRepository.save(newUser);
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("Role already exists");
        AppRole newRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(newRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole == null) throw new RuntimeException("Role not found");
        user.getRoles().add(appRole);
    }

    @Override
    public void removeRoleToUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole == null) throw new RuntimeException("Role not found");
        user.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
