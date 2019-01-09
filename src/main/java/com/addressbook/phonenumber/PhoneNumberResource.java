package com.addressbook.phonenumber;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/phonenumbers")
public class PhoneNumberResource {

    private PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberResource(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("{id}")
    public PhoneNumber get(@PathVariable @NotNull UUID id) {
        return phoneNumberService.getById(id)
                .map(PhoneNumber::fromEntity)
                .orElse(null);
    }

    @GetMapping(params = "contactId")
    public Collection<PhoneNumber> getForContact(@RequestParam("contactId") @NotNull UUID contactId) {
        return phoneNumberService.getForContact(contactId)
                .stream()
                .map(PhoneNumber::fromEntity)
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class PhoneNumber {

        private PhoneNumberEntity.Type type;
        private String number;

        public static PhoneNumber fromEntity(PhoneNumberEntity entity) {
            return PhoneNumber.builder()
                    .type(entity.getType())
                    .number(entity.getNumber())
                    .build();
        }
    }
}
