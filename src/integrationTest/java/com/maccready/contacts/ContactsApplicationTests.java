package com.maccready.contacts;

import com.maccready.contacts.helpers.JsonFromFileHelper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static com.maccready.contacts.helpers.JsonFromFileHelper.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactsApplicationTests {

    @Test
    @Order(1)
    void getContacts() throws Exception {
        String response = get("/contacts").body().asString();
        JSONAssert.assertEquals(getJson("get-contacts.json"), response, true);
    }

    @Test
    @Order(2)
    void getContact() throws Exception {
        String response = get("/contacts/1").body().asString();
        JSONAssert.assertEquals(getJson("get-contact.json"), response, true);
    }

    @Test
    @Order(2)
    void createContact() {
        RequestSpecification request = given();
        request.header("content-type", MediaType.APPLICATION_JSON_VALUE);
        request.body(JsonFromFileHelper.getJson("post-contact.json"));
        Response response = request.post("/contacts").andReturn();
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String location = response.getHeader("location");
        assertTrue(String.format("%s should end with /contacts/5", location), location.endsWith("/contacts/5"));
    }
    @Test
    @Order(3)
    void deleteContact() {
        assertEquals(HttpStatus.OK.value(), get("/contacts/5").andReturn().getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT.value(), delete("/contacts/5").andReturn().getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), get("/contacts/5").andReturn().getStatusCode());
    }
}
