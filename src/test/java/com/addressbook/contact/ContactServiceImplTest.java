package com.addressbook.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Mock tests for ContactServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;
    private ContactService contactService;

    @BeforeEach
    public void init() {
        contactService = new ContactServiceImpl(contactRepository);
    }

    @Test
    void getById() {
        // given
        UUID id = UUID.randomUUID();
        when(contactRepository.findById(id))
                .thenReturn(Optional.of(new ContactEntity("Adam", "Simon", "test@test.com")));

        // when
        Optional<ContactEntity> contactOptional = contactService.getById(id);

        // then
        ContactEntity contact = contactOptional.orElseThrow(() -> new NullPointerException("No Contact found!"));
        assertThat(contact.getFirstName()).isEqualToIgnoringCase("Adam");
        assertThat(contact.getLastName()).isEqualToIgnoringCase("Simon");
        assertThat(contact.getEmail()).isEqualToIgnoringCase("test@test.com");
        verify(contactRepository).findById(id);
    }

    @Test
    void getAll() {
        // given
        ContactEntity contact1 = new ContactEntity("Adam", "Simon", "adam@test.com");
        ContactEntity contact2 = new ContactEntity("Eve", "Matt", "eve@test.com");
        when(contactRepository.findAll()).thenReturn(Arrays.asList(contact1, contact2));

        // when
        final Collection<ContactEntity> contacts = contactService.getAll();

        // then
        assertThat(contacts).hasSize(2);
        assertThat(contacts).contains(contact1, contact2);
        verify(contactRepository).findAll();
    }

    @Test
    void create() {
        // given
        ContactEntity contact = new ContactEntity("Adam", "Simon", "adam@test.com");
        when(contactRepository.saveAndFlush(contact)).thenReturn(contact);

        // when
        contactService.create(contact);

        // then
        verify(contactRepository).saveAndFlush(contact);
    }

    @Test
    void findAllByName() {
        // when
        contactService.findAllByName("name");

        // then
        verify(contactRepository).findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(
                eq("name"), eq("name"));
    }

    @Test
    void findAllByEmail() {
        // when
        contactService.findAllByEmail("email");

        // then
        verify(contactRepository).findByEmailContaining(eq("email"));
    }
}
