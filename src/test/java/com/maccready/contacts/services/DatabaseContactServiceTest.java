package com.maccready.contacts.services;

import com.maccready.contacts.entities.ContactRecord;
import com.maccready.contacts.models.Contact;
import com.maccready.contacts.repositories.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseContactServiceTest {

    private ContactRepository repository;
    private ContactRecord expectedContact;
    private DatabaseContactService service;
    private ContactRecord expectedRecord;

    @BeforeEach
    void setUp() {
        repository = mock(ContactRepository.class);
        service = new DatabaseContactService(repository);
        expectedContact = new ContactRecord("Biff", "Jones", "biff@xyx.com", "5435435432");
        expectedRecord = new ContactRecord("Biff", "Jones", "biff@xyx.com", "5435435432");
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(
                expectedContact));
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
        Long expectedId = 134134L;
        ReflectionTestUtils.setField(expectedContact, "id", expectedId);
        when(repository.save(argThat(contactRecord -> {
            assertEquals("Biff", contactRecord.getFirstName());
            assertEquals("Jones", contactRecord.getLastName());
            assertEquals("biff@xyx.com", contactRecord.getEmail());
            assertEquals("5435435432", contactRecord.getPhone());
            return true;
        }))).thenReturn(expectedContact);
        Long id = service.save(new Contact("Biff", "Jones", "biff@xyx.com", "5435435432"));
        assertEquals(expectedId, id);
    }

    @Test
    void getSuccess() {
        when(repository.findById(123L)).thenReturn(Optional.of(expectedRecord));
        Contact result = service.get(123L).orElseThrow(() -> new RuntimeException("Contact result expected"));
        assertEquals("Biff",result.getFirstName());
        assertEquals("Jones",result.getLastName());
        assertEquals("biff@xyx.com",result.getEmail());
        assertEquals("5435435432",result.getPhone());
    }
    @Test
    void getNotFound() {
        when(repository.findById(123L)).thenReturn(Optional.empty());
        Optional<Contact> result = service.get(123L);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void deleteSuccess() {
        when(repository.findById(123L)).thenReturn(Optional.of(expectedRecord));
        boolean result = service.delete(123L);
        assertTrue(result);
        verify(repository, times(1)).delete(expectedRecord);
    }

    @Test
    void deleteFailure() {
        when(repository.findById(123L)).thenReturn(Optional.empty());
        boolean result = service.delete(123L);
        assertFalse(result);
        verify(repository, times(0)).delete(any(ContactRecord.class));
    }


}