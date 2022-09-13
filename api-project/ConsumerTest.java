package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String, String> headers = new HashMap<>();

    //Resource path
    String resourcePath = "/api/users";

    //Create the pact
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        // Add the headers
        headers.put("Content-Type", "application/json");

        // Create the body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id",123)
                .stringType("firstName", "Test" )
                .stringType("lastName", "user")
                .stringType("email", "testuser@gmail.com");

        // Create the constract
        return builder.given("A request to create an user")
                .uponReceiving("A request to create an user")
                    .method("POST")
                    .path(resourcePath)
                    .headers(headers)
                    .body(requestResponseBody)
                .willRespondWith()
                    .status(201)
                    .body(requestResponseBody)
                .toPact();
        }

        @Test
        @PactTestFor(providerName = "UserProvider", port = "8282")
        public void consumerTest(){
            // Create Base URI
            String baseURI = "http://localhost:8282";

            // Create request body
            Map<String, Object> reqBody = new HashMap<>();
            reqBody.put("id", 123);
            reqBody.put("firstName", "test");
            reqBody.put("lastName", "user");
            reqBody.put("email", "testuser@gmail.com");

            // Generate response
                given()
                     .headers(headers)
                     .body(reqBody).log().all()
                     .when()
                     .post(baseURI+resourcePath)
                     .then()
                     .statusCode(201).log().all();


        }

}
