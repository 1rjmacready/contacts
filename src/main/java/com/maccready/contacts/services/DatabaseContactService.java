package com.maccready.contacts.services;

import com.maccready.contacts.entities.ContactRecord;
import com.maccready.contacts.models.Contact;
import com.maccready.contacts.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<Contact> get(long id) {
        return repository.findById(id).map(record -> new Contact(record.getFirstName(), record.getLastName(),
                record.getEmail(), record.getPhone()));
    }

    @Override
    public Long save(Contact contactToSave) {
        ContactRecord record = new ContactRecord(contactToSave.getFirstName(), contactToSave.getLastName(),
                contactToSave.getEmail(), contactToSave.getPhone());
        return repository.save(record).getId();
    }

    @Override
    public boolean delete(long id) {
        return repository.findById(id).map(record -> {
            repository.delete(record);
            return true;
        }).orElse(false);
    }
}
