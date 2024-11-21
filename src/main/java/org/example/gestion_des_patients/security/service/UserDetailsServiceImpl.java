package org.example.gestion_des_patients.security.service;

import lombok.AllArgsConstructor;
import org.example.gestion_des_patients.security.entities.AppRole;
import org.example.gestion_des_patients.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = accountService.loadUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(AppRole::getRole).toArray(String[]::new))
                .build();
    }
}
