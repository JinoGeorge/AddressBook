package com.addressbook.phonenumber;

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
    public Collection<PhoneNumberEntity> getForContact(UUID contactId) {
        return phoneNumberRepository.findByContactId(contactId);
    }

    @Override
    public Collection<PhoneNumberEntity> getAll() {
        return phoneNumberRepository.findAll();
    }
}
