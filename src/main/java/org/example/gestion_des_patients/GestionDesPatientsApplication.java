package org.example.gestion_des_patients;

import org.example.gestion_des_patients.entities.Patient;
import org.example.gestion_des_patients.repositories.PatientRepository;
import org.example.gestion_des_patients.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class GestionDesPatientsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDesPatientsApplication.class, args);
    }

    //@Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Hassan", new Date(), false, 40));
            patientRepository.save(new Patient(null, "Mohamed", new Date(), true, 35));
            patientRepository.save(new Patient(null, "Ali", new Date(), true, 100));
            patientRepository.save(new Patient(null, "Fatima", new Date(), false, 60));

            patientRepository.findAll().forEach(System.out::println);
        };
    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        return args -> {
            UserDetails u11 = jdbcUserDetailsManager.loadUserByUsername("user11");
            UserDetails u22 = jdbcUserDetailsManager.loadUserByUsername("user22");
            UserDetails admin = jdbcUserDetailsManager.loadUserByUsername("admin");
            if(u11 == null){
                jdbcUserDetailsManager.createUser(User.withUsername("user11").password(passwordEncoder().encode("1234")).roles("USER").build());
            }
            if(u22 == null){
                jdbcUserDetailsManager.createUser(User.withUsername("user22").password(passwordEncoder().encode("1234")).roles("USER").build());
            }
            if(admin == null){
                jdbcUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build());
            }
        };
    }

    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1", "1234", "1234");
            accountService.addNewUser("user2", "1234", "1234");
            accountService.addNewUser("admin", "1234", "1234");
            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
