package com.maccready.contacts.controllers;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.services.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContactControllerTest {

    @Test
    void getAllContacts() {
        ContactService contactService = mock(ContactService.class);
        Contact expectedContact = new Contact("RJ", "MacReady", "ajolie@xyx.com", "5553216547");
        when(contactService.getAll()).thenReturn(Collections.singletonList(expectedContact));
        ContactController controller = new ContactController(contactService);
        List<Contact> allContacts = controller.getAllContacts();
        assertEquals(1, allContacts.size());
        assertSame(expectedContact, allContacts.get(0));
    }
    @Test
    void saveContact() throws URISyntaxException {
        ContactService contactService = mock(ContactService.class);
        Contact contactToSave = new Contact("RJ", "MacReady", "ajolie@xyx.com", "5553216547");
        Long id = 324L;
        when(contactService.save(contactToSave)).thenReturn(id);
        ContactController controller = new ContactController(contactService);
        ResponseEntity<Contact> result = controller.saveContact(contactToSave);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(new URI("/persons/324"),result.getHeaders().getLocation());
        assertSame(contactToSave, result.getBody());

    }
}