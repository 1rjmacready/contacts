package com.maccready.contacts.services;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseContactService implements ContactService{

    private final ContactRepository repository;

    public DatabaseContactService(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Contact> getAll() {
        return repository.findAll().stream().map(record ->
                new Contact(record.getFirstName(), record.getLastName(),
                        record.getEmail(), record.getPhone())).collect(Collectors.toList());
    }

    @Override
    public Long save(Contact contactToSave) {
        return null;
    }
}
