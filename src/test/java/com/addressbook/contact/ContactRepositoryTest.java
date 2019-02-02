package com.addressbook.contact;

import com.addressbook.phonenumber.PhoneNumberEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void findByLastNameContainingOrFirstNameContaining() {
        // given
        PhoneNumberEntity phone1 = new PhoneNumberEntity(PhoneNumberEntity.Type.MOBILE, "0151-12345678");
        PhoneNumberEntity phone2 = new PhoneNumberEntity(PhoneNumberEntity.Type.HOME, "089-12345");
        PhoneNumberEntity phone3 = new PhoneNumberEntity(PhoneNumberEntity.Type.WORK, "089-67890");
        ContactEntity contact1 = new ContactEntity("Adam", "Simon", "adam@test.com");
        ContactEntity contact2 = new ContactEntity("Eve", "Simon", "eve@test.com");
        contact1.addPhoneNumber(phone1);
        contact1.addPhoneNumber(phone2);
        contact2.addPhoneNumber(phone3);
        entityManager.persist(contact1);
        entityManager.persist(contact2);
        entityManager.flush();

        // when, then
        assertThat(contactRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase("Adam", "Adam")).hasSize(1).contains(contact1);
         assertThat(contactRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase("simon", "simon")).hasSize(2).contains(contact1, contact2);
    }

    @Test
    void findByEmailContaining() {
        // given
        PhoneNumberEntity phone1 = new PhoneNumberEntity(PhoneNumberEntity.Type.MOBILE, "0151-12345678");
        PhoneNumberEntity phone2 = new PhoneNumberEntity(PhoneNumberEntity.Type.HOME, "089-12345");
        PhoneNumberEntity phone3 = new PhoneNumberEntity(PhoneNumberEntity.Type.WORK, "089-67890");
        ContactEntity contact1 = new ContactEntity("Adam", "Simon", "adam@test.com");
        ContactEntity contact2 = new ContactEntity("Eve", "Matt", "eve@test.com");
        contact1.addPhoneNumber(phone1);
        contact1.addPhoneNumber(phone2);
        contact2.addPhoneNumber(phone3);
        entityManager.persist(contact1);
        entityManager.persist(contact2);
        entityManager.flush();

        // when
        Collection<ContactEntity> contactsContainingEmail = contactRepository.findByEmailContaining("adam");

        // then
        assertThat(contactsContainingEmail).hasSize(1).contains(contact1);
    }
}
