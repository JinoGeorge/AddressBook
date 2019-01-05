package com.addressbook.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity(name = "phone_number")
public class PhoneNumber extends BaseEntity {

    @NotNull
    private Type type;
    @NotBlank
    private String number;

    enum Type {
        HOME, WORK, MOBILE
    }
}
