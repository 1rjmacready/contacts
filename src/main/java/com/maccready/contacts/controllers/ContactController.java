package com.maccready.contacts.controllers;

import com.maccready.contacts.models.Contact;
import com.maccready.contacts.services.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("/contacts")
@RestController
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> getAllContacts(){
        return service.getAll();
    }
    @PostMapping
    public ResponseEntity<Contact> saveContact(@RequestBody @Valid Contact contact, HttpServletRequest request) throws URISyntaxException {
        Long id = service.save(contact);
        return ResponseEntity.created(new URI(request.getRequestURL() +"/" + id)).body(contact);
    }
    @GetMapping(path = "/{id}")
    ResponseEntity<Contact> getContact(@PathVariable long id){
        return service.get(id).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable long id){
        if(service.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
