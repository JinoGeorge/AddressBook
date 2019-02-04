package com.addressbook.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        entityManager.persistAndFlush(new UserEntity("admin1", "admin1", Collections.singleton(new RoleEntity("ADMIN"))));

        UserEntity admin = userRepository.findByUsername("admin1").orElse(null);
        assertThat(admin).isNotNull();
        assertThat(admin.getUsername()).isEqualTo("admin1");
        assertThat(admin.getPassword()).isEqualTo("admin1");
    }

    @Test
    void addUser() {
        UserEntity user = new UserEntity("user", "user", Collections.singleton(new RoleEntity("USER")));
        user = userRepository.save(user);

        UserEntity userEntity = entityManager.find(UserEntity.class, user.getId());

        assertThat(userEntity.getUsername()).isEqualTo(user.getUsername());
        assertThat(userEntity.getPassword()).isEqualTo(user.getPassword());
    }
}
