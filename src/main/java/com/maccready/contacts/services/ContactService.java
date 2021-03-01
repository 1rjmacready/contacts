package com.maccready.contacts.services;

import com.maccready.contacts.models.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<Contact> getAll();

    Optional<Contact> get(long id);

    Long save(Contact contact);

    boolean delete(long id);


}
