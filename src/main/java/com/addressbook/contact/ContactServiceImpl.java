package com.addressbook.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Optional<ContactEntity> getById(UUID id) {
        return contactRepository.findById(id);
    }

    @Override
    public Collection<ContactEntity> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public ContactEntity create(ContactEntity entity) {
        return contactRepository.saveAndFlush(entity);
    }

    @Override
    public Collection<ContactEntity> findAllByName(String name) {
        return contactRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(name, name);
    }

    @Override
    public Collection<ContactEntity> findAllByEmail(String email) {
        return contactRepository.findByEmailContaining(email);
    }
}
