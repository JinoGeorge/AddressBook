package com.addressbook.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ContactRepository extends JpaRepository<ContactEntity, String> {

    Collection<ContactEntity> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);

    Collection<ContactEntity> findByEmailContaining(String email);
}
