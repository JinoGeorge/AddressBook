package com.addressbook.contact;

import com.addressbook.common.BaseEntity;
import com.addressbook.phonenumber.PhoneNumberEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isAllBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity(name = "contact")
public class ContactEntity extends BaseEntity {
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Email
    private String email;
    @Valid
    private AddressEntity address;
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private Collection<@Valid PhoneNumberEntity> phoneNumbers;

    @AssertTrue(message = "Both FirstName and Last Name can not be blank.")
    private boolean isValidNames() {
        return !isAllBlank(firstName, lastName);
    }

    @Valid
    public ContactEntity(String title, String firstName, String lastName, @Email String email) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void addPhoneNumber(PhoneNumberEntity phoneNumber) {
        phoneNumbers = phoneNumbers == null ? new ArrayList<>() : phoneNumbers;
        phoneNumbers.add(phoneNumber);
        phoneNumber.setContact(this);
    }
}
