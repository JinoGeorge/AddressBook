package com.addressbook.phonenumber;

import com.addressbook.contact.ContactEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Mockito tests for PhoneNumberServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceImplTest {

    @Mock
    private PhoneNumberRepository phoneNumberRepository;
    private PhoneNumberService phoneNumberService;

    @BeforeEach
    void init() {
        phoneNumberService = new PhoneNumberServiceImpl(phoneNumberRepository);
    }

    @Test
    void getById() {
        // given
        UUID id = UUID.randomUUID();
        when(phoneNumberRepository.findById(id))
                .thenReturn(Optional.of(new PhoneNumberEntity(PhoneNumberEntity.Type.MOBILE, "0151-12345678")));

        // when
        Optional<PhoneNumberEntity> phoneNumberOptional = phoneNumberService.getById(id);

        // then
        PhoneNumberEntity phoneNumber = phoneNumberOptional
                .orElseThrow(() -> new NullPointerException("No phone number found!"));
        assertThat(phoneNumber.getType()).isEqualTo((PhoneNumberEntity.Type.MOBILE));
        assertThat(phoneNumber.getNumber()).isEqualTo("0151-12345678");
        verify(phoneNumberRepository).findById(id);
    }

    @Test
    void getForContact() {
        // given
        UUID id = UUID.randomUUID();
        ContactEntity contact = new ContactEntity("MR.", "Adam", "Simon", "adam@test.com");
        ReflectionTestUtils.setField(contact, "id", id);
        PhoneNumberEntity phone1 = new PhoneNumberEntity(contact, PhoneNumberEntity.Type.MOBILE, "0151-12345678");
        PhoneNumberEntity phone2 = new PhoneNumberEntity(contact, PhoneNumberEntity.Type.HOME, "089-12345");
        when(phoneNumberRepository.findByContact(contact)).thenReturn(Arrays.asList(phone1, phone2));

        // when
        Collection<PhoneNumberEntity> phoneNumbers = phoneNumberService.getForContact(contact);

        // then
        assertThat(phoneNumbers).hasSize(2);
        assertThat(phoneNumbers).contains(phone1, phone2);
        verify(phoneNumberRepository).findByContact(contact);
    }

    @Test
    void getAll() {
        // given
        PhoneNumberEntity phone1 = new PhoneNumberEntity(PhoneNumberEntity.Type.MOBILE, "0151-12345678");
        PhoneNumberEntity phone2 = new PhoneNumberEntity(PhoneNumberEntity.Type.HOME, "089-12345");
        when(phoneNumberRepository.findAll()).thenReturn(Arrays.asList(phone1, phone2));

        // when
        Collection<PhoneNumberEntity> phoneNumbers = phoneNumberService.getAll();

        // then
        assertThat(phoneNumbers).hasSize(2);
        assertThat(phoneNumbers).contains(phone1, phone2);
        verify(phoneNumberRepository).findAll();
    }
}
