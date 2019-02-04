package com.addressbook.user;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("users")
public class UserResource {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserResource(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody NewUser newUser) {
        UserEntity entity = userDetailsService.create(toUserEntity(newUser));
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private UserEntity toUserEntity(NewUser newUser) {
        return new UserEntity(newUser.username, newUser.password,
                newUser.roles.stream().map(RoleEntity::new).collect(Collectors.toSet()));
    }

    @Value
    private static class NewUser {
        private String username;
        private String password;
        private List<String> roles;
    }

}
