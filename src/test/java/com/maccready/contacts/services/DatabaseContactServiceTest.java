package com.maccready.contacts.services;

import com.maccready.contacts.entities.ContactRecord;
import com.maccready.contacts.models.Contact;
import com.maccready.contacts.repositories.ContactRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DatabaseContactServiceTest {

    @Test
    void getAll() {
        ContactRepository repository = mock(ContactRepository.class);
        ContactRecord expectedContact = new ContactRecord("Biff", "Jones", "biff@xyx.com", "5435435432");
        when(repository.findAll()).thenReturn(Collections.singletonList(
                expectedContact));
        DatabaseContactService service = new DatabaseContactService(repository);
        List<Contact> allContacts = service.getAll();
        assertEquals(1, allContacts.size());
        Contact contact = allContacts.get(0);
        assertEquals(expectedContact.getFirstName(), contact.getFirstName());
        assertEquals(expectedContact.getLastName(), contact.getLastName());
        assertEquals(expectedContact.getEmail(), contact.getEmail());
        assertEquals(expectedContact.getPhone(), contact.getPhone());
    }
}