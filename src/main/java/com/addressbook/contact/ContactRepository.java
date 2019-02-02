package com.addressbook.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {

    Collection<ContactEntity> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);

    Collection<ContactEntity> findByEmailContaining(String email);
}
