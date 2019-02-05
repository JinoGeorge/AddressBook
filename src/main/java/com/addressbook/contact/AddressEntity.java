package com.addressbook.contact;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Embeddable
public class AddressEntity {
    private String street;
    private String houseNumber;
    @NotBlank
    private String city;
    private String state;
    @NotBlank
    private String country;
    private String postalCode;
}
