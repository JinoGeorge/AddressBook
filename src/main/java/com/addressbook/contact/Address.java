package com.addressbook.contact;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Embeddable
public class Address {
    private String street;
    private String houseNumber;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
