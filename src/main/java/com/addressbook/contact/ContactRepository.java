package com.addressbook.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
}
