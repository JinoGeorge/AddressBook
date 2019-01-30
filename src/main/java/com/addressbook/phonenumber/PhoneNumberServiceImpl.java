package com.addressbook.phonenumber;

import com.addressbook.contact.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public Optional<PhoneNumberEntity> getById(UUID id) {
        return phoneNumberRepository.findById(id);
    }

    @Override
    public Collection<PhoneNumberEntity> getForContact(ContactEntity contact) {
        return phoneNumberRepository.findByContact(contact);
    }

    @Override
    public Collection<PhoneNumberEntity> getAll() {
        return phoneNumberRepository.findAll();
    }
}
