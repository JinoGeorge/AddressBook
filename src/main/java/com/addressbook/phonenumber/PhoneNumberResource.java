package com.addressbook.phonenumber;

import com.addressbook.contact.ContactEntity;
import com.addressbook.contact.ContactNotFoundException;
import com.addressbook.contact.ContactService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("phonenumbers")
public class PhoneNumberResource {

    private PhoneNumberService phoneNumberService;
    private ContactService contactService;

    @Autowired
    public PhoneNumberResource(ContactService contactService, PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("{id}")
    public PhoneNumber get(@PathVariable @NotNull UUID id) {
        return phoneNumberService.getById(id).map(PhoneNumber::fromEntity).orElse(null);
    }

    @GetMapping(params = "contactId")
    public Collection<PhoneNumber> getByContact(@RequestParam("contactId") @NotNull UUID contactId) {
        ContactEntity contact = contactService.getById(contactId)
                .orElseThrow(() -> new ContactNotFoundException("Could not find the contact."));
        return phoneNumberService.getForContact(contact).stream().map(PhoneNumber::fromEntity).collect(toList());
    }

    @Data
    @Builder
    public static class PhoneNumber {
        private PhoneNumberEntity.Type type;
        private String number;

        public static PhoneNumber fromEntity(PhoneNumberEntity entity) {
            return PhoneNumber.builder().type(entity.getType()).number(entity.getNumber()).build();
        }
    }
}
