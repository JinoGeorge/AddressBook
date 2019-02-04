package com.addressbook.contact;

import java.util.Collection;
import java.util.Optional;

public interface ContactService {

    Optional<ContactEntity> getById(String id);

    Collection<ContactEntity> getAll();

    ContactEntity create(ContactEntity entity);

    Collection<ContactEntity> findAllByName(String name);

    Collection<ContactEntity> findAllByEmail(String email);
}
