package com.addressbook.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String testUser = "testUser";
        String password = "testPass";

        if (!userName.equals(testUser)) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return User.withDefaultPasswordEncoder()
                .username(testUser)
                .password(password)
                .authorities(getAuthority())
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthority() {
        return Collections.emptyList();
    }
}
