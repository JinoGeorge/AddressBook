package com.addressbook.phonenumber;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PhoneNumberService {

    Optional<PhoneNumberEntity> getById(UUID id);

    Collection<PhoneNumberEntity> getForContact(UUID contactId);

    Collection<PhoneNumberEntity> getAll();

}
