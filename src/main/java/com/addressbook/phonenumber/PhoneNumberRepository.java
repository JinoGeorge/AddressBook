package com.addressbook.phonenumber;

import com.addressbook.contact.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, String> {

    Collection<PhoneNumberEntity> findByContact(ContactEntity contact);
}
