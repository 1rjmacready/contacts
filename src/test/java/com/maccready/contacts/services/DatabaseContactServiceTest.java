package com.maccready.contacts.services;

import com.maccready.contacts.entities.ContactRecord;
import com.maccready.contacts.models.Contact;
import com.maccready.contacts.repositories.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
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

    @Test
    void save() {
        ContactRepository repository = mock(ContactRepository.class);
        Long expectedId = 134134L;
        ContactRecord expectedContractRecord = new ContactRecord();
        ReflectionTestUtils.setField(expectedContractRecord, "id", expectedId);
        when(repository.save(argThat(contactRecord -> {
            assertEquals("Biff", contactRecord.getFirstName());
            assertEquals("Jones", contactRecord.getLastName());
            assertEquals("biff@xyx.com", contactRecord.getEmail());
            assertEquals("5435435432", contactRecord.getPhone());
            return true;
        }))).thenReturn(expectedContractRecord);
        DatabaseContactService service = new DatabaseContactService(repository);
        Long id = service.save(new Contact("Biff", "Jones", "biff@xyx.com", "5435435432"));
        assertEquals(expectedId, id);
    }
}