package com.addressbook.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@ToString
@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue
    @NotNull
    private final UUID id;
}

