package com.addressbook.phonenumber;

import com.addressbook.contact.ContactEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PhoneNumberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Test
    void findByContactId() {
        // given
        PhoneNumberEntity phone1 = new PhoneNumberEntity(PhoneNumberEntity.Type.MOBILE, "0151-12345678");
        PhoneNumberEntity phone2 = new PhoneNumberEntity(PhoneNumberEntity.Type.HOME, "089-12345");
        PhoneNumberEntity phone3 = new PhoneNumberEntity(PhoneNumberEntity.Type.WORK, "089-67890");
        ContactEntity contact1 = new ContactEntity("MR.", "Adam", "Simon", "adam@test.com");
        ContactEntity contact2 = new ContactEntity("MS.", "Eve", "Matt", "eve@test.com");
        contact1.addPhoneNumber(phone1);
        contact1.addPhoneNumber(phone2);
        contact2.addPhoneNumber(phone3);

        entityManager.clear();
        contact1 = entityManager.persist(contact1);
        contact2 = entityManager.persist(contact2);
        entityManager.flush();

        // when
        assertThat(phoneNumberRepository.findByContact(contact1)).hasSize(2).contains(phone1, phone2);
        assertThat(phoneNumberRepository.findByContact(contact2)).hasSize(1).contains(phone3);
    }
}
