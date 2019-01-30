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
                .thenReturn(Optional.of(new ContactEntity("MR.", "Adam", "Simon", "test@test.com")));

        // when
        Optional<ContactEntity> contactOptional = contactService.getById(id);

        // then
        ContactEntity contact = contactOptional.orElseThrow(() -> new NullPointerException("No Contact found!"));
        assertThat(contact.getTitle()).isEqualToIgnoringCase("MR.");
        assertThat(contact.getFirstName()).isEqualToIgnoringCase("Adam");
        assertThat(contact.getLastName()).isEqualToIgnoringCase("Simon");
        assertThat(contact.getEmail()).isEqualToIgnoringCase("test@test.com");
        verify(contactRepository).findById(id);
    }

    @Test
    void getAll() {
        // given
        ContactEntity contact1 = new ContactEntity("MR.", "Adam", "Simon", "adam@test.com");
        ContactEntity contact2 = new ContactEntity("MS.", "Eve", "Matt", "eve@test.com");
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
        ContactEntity contact = new ContactEntity("MR.", "Adam", "Simon", "adam@test.com");
        when(contactRepository.saveAndFlush(contact)).thenReturn(contact);

        // when
        contactService.create(contact);

        // then
        verify(contactRepository).saveAndFlush(contact);
    }
}
