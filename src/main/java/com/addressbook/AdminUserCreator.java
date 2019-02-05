package com.addressbook;

import com.addressbook.user.RoleEntity;
import com.addressbook.user.UserEntity;
import com.addressbook.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminUserCreator implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserEntity defaultAdmin = new UserEntity("admin", passwordEncoder.encode("admin"),
                    Collections.singleton(new RoleEntity("ADMIN")));
            userRepository.save(defaultAdmin);
        }
    }
}
