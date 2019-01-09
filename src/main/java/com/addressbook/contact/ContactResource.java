package com.addressbook.contact;

import com.addressbook.phonenumber.PhoneNumberEntity;
import com.addressbook.phonenumber.PhoneNumberResource.PhoneNumber;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping("contacts")
public class ContactResource {

    private ContactService contactService;

    @Autowired
    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("id")
    public Contact get(@PathVariable @NotNull UUID id) {
        return contactService.getById(id)
                .map(Contact::fromEntity)
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid Contact contact) {
        ContactEntity entity = new ContactEntity(contact.getTitle(), contact.getFirstName(), contact.getLastName(),
                contact.getEmail());
        entity.setBirthDate(contact.getBirthDate());
        entity.setPhoneNumbers(contact.getPhoneNumbers()
                .stream()
                .map(phoneNumber -> new PhoneNumberEntity(phoneNumber.getType(), phoneNumber.getNumber()))
                .collect(Collectors.toList()));
        entity.setAddress(toAddressEntity(contact));
        entity = contactService.create(entity);

        return ResponseEntity.created(fromUriString("/contacts/{id}")
                .buildAndExpand(entity.getId())
                .toUri())
                .build();
    }

    private AddressEntity toAddressEntity(@RequestBody @Valid ContactResource.@Valid Contact contact) {
        AddressEntity entity = new AddressEntity();
        entity.setStreet(contact.getAddress().getStreet());
        entity.setHouseNumber(contact.getAddress().getHouseNumber());
        entity.setCity(contact.getAddress().getCity());
        entity.setState(contact.getAddress().getState());
        entity.setCountry(contact.getAddress().getCountry());
        entity.setPostalCode(contact.getAddress().getPostalCode());
        return entity;
    }

    @Data
    @Builder
    static class Contact {
        private String title;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private Address address;
        @Email
        private String email;
        private Collection<@Valid PhoneNumber> phoneNumbers;

        public static Contact fromEntity(ContactEntity entity) {
            return Contact.builder()
                    .title(entity.getTitle())
                    .firstName(entity.getFirstName())
                    .lastName(entity.getLastName())
                    .birthDate(entity.getBirthDate())
                    .address(Address.fromEntity(entity.getAddress()))
                    .email(entity.getEmail())
                    .phoneNumbers(entity.getPhoneNumbers()
                            .stream()
                            .map(PhoneNumber::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @Builder
    static class Address {
        private String street;
        private String houseNumber;
        private String city;
        private String state;
        private String country;
        private String postalCode;

        public static Address fromEntity(AddressEntity entity) {
            return Address.builder()
                    .street(entity.getStreet())
                    .houseNumber(entity.getHouseNumber())
                    .city(entity.getCity())
                    .state(entity.getState())
                    .country(entity.getCountry())
                    .postalCode(entity.getPostalCode())
                    .build();
        }
    }
}
