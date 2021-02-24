package com.maccready.contacts;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.services.ContactService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
}