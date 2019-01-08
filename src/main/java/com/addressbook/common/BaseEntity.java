package com.addressbook.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@ToString
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @NotNull
    @Column(columnDefinition = "CHAR(36)")
    private final UUID id;
}

