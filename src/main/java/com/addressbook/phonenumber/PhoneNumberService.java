package com.addressbook.phonenumber;

import com.addressbook.contact.ContactEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PhoneNumberService {

    Optional<PhoneNumberEntity> getById(String id);

    Collection<PhoneNumberEntity> getForContact(ContactEntity contact);

    Collection<PhoneNumberEntity> getAll();

}
