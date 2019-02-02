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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("contacts")
public class ContactResource {

    private ContactService contactService;

    @Autowired
    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("{id}")
    public Contact get(@PathVariable @NotNull UUID id) {
        return contactService.getById(id)
                .map(Contact::fromEntity)
                .orElseThrow(() -> new ContactNotFoundException("Could not find a contact with the given id."));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid Contact contact) {
        ContactEntity entity = contactService.create(toContactEntity(contact));
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = "name")
    public List<Contact> findAllByName(@RequestParam("name") String name) {
        return contactService.findAllByName(name).stream().map(Contact::fromEntity).collect(toList());
    }

    @GetMapping(params = "email")
    public List<Contact> findAllByEmail(@RequestParam("email") String email) {
        return contactService.findAllByEmail(email).stream().map(Contact::fromEntity).collect(toList());
    }

    private ContactEntity toContactEntity(Contact contact) {
        ContactEntity entity = new ContactEntity(contact.getFirstName(), contact.getLastName(), contact.getEmail());
        entity.setTitle(contact.getTitle());
        entity.setBirthDate(contact.getBirthDate());
        entity.setPhoneNumbers(contact.getPhoneNumbers().stream().map(this::toPhoneNumberEntity).collect(toList()));
        entity.setAddress(toAddressEntity(contact.getAddress()));
        return entity;
    }

    private PhoneNumberEntity toPhoneNumberEntity(@Valid PhoneNumber phoneNumber) {
        return new PhoneNumberEntity(phoneNumber.getType(), phoneNumber.getNumber());
    }

    private AddressEntity toAddressEntity(Address address) {
        AddressEntity entity = new AddressEntity();
        entity.setStreet(address.getStreet());
        entity.setHouseNumber(address.getHouseNumber());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        entity.setCountry(address.getCountry());
        entity.setPostalCode(address.getPostalCode());
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
                            .collect(toList()))
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
