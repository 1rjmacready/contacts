package com.maccready.contacts.services;

import com.maccready.contacts.models.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAll();

    Long save(Contact contact);
}
