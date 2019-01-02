package com.addressbook.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
public class Contact {
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private Address address;
    private Collection<PhoneNumber> phoneNumbers;
}
