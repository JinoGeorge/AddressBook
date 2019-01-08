package com.addressbook.contact;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ContactService {

    Optional<ContactEntity> getById(UUID id);

    Collection<ContactEntity> getAll();

    ContactEntity create(ContactEntity entity);
}
