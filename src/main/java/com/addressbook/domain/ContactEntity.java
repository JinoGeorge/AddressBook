package com.addressbook.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import java.time.LocalDate;
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
    private Address address;
    @OneToMany(mappedBy = "contact")
    private Collection<PhoneNumber> phoneNumbers;

    @AssertTrue(message = "Both FirstName and Last Name can not be blank.")
    private boolean isValidNames() {
        return !isAllBlank(firstName, lastName);
    }
}
