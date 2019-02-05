package com.addressbook.phonenumber;

import com.addressbook.common.BaseEntity;
import com.addressbook.contact.ContactEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@RequiredArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity(name = "phone_number")
public class PhoneNumberEntity extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private ContactEntity contact;
    @Enumerated(EnumType.STRING)
    private final Type type;
    @NotBlank
    private final String number;

    public enum Type {
        HOME, WORK, MOBILE
    }
}
