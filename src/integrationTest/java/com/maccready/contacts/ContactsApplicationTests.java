package com.maccready.contacts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ContactsApplicationTests {

    @Test
    void getPersons() throws Exception {
        String response = get("/contacts").body().asString();
        JSONAssert.assertEquals(getJson("get-contacts.json"), response, true);
    }


    private String getJson(String fileName) throws Exception {
        URL resource = getClass().getResource("/" + fileName);
        Path path = Paths.get(resource.toURI());
        return Files.lines(path).collect(Collectors.joining("\n"));

    }

}
