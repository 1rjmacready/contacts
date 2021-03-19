package com.maccready.contacts;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.maccready.contacts.helpers.JsonFromFileHelper;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmployeeControllerTests {
    WireMockServer wireMockServer;
    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    public void setupStub() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/employees"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("body-api-v1-employees-2tOQL.json")));
    }

    @Test
    void getEmployees() throws JSONException {
        Response response = get("/wageearners").andReturn();
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        String result = response.getBody().asString();
        JSONAssert.assertEquals(JsonFromFileHelper.getJson("get-wageearners.json"), result, true);
    }
}
