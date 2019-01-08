package com.addressbook.phonenumber;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, UUID> {

    Collection<PhoneNumberEntity> findByContactId(UUID contactId);
}
