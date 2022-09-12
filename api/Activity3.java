package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {
    String baseURI = "https://petstore.swagger.io/v2/pet/";
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setBaseUri(baseURI)
        .build();

        // Create response specification
        responseSpec = new ResponseSpecBuilder()
        // Check status code in response
        .expectStatusCode(200)
        // Check response content type
        .expectContentType("application/json")
        // Check if response contains name property
        .expectBody("status", equalTo("alive"))
        // Build response specification
        .build();

    }

    @DataProvider
    public Object[][] petData(){
        Object[][] testData = new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
        return testData;
    }

    @Test(dataProvider = "petData" , priority = 0)
    public void createPet(int id, String name, String status) {
        //Creating the request payload
        HashMap data = new HashMap<>();
        data.put("id",id);
        data.put("name",name);
        data.put("status",status);

        Response response =
                given().spec(requestSpec) // Use requestSpec
                .body(data)
                .when().post(); // Run POST request

        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions using response spec
        response.then().spec(responseSpec);

    }

    @Test(dataProvider = "petData" ,priority = 1)
    public void getPetDetails(int id, String name, String status) {
        Response response =
                 given().spec(requestSpec) // Use requestSpec
                .pathParams("id", id)
                .when().get("{id}"); // Run GET request

        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions using response spec
        response.then().spec(responseSpec)
        .body("id", equalTo(id),
                "name", equalTo(name));

    }

    @Test(dataProvider = "petData" ,priority = 2)
    public void deletePetDetails(int id, String name, String status) {
        Response response =
                given().spec(requestSpec) // Use requestSpec
                .pathParams("id", id)
                .when().delete("{id}"); // Run Delete request

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().body("code", equalTo(200));

    }



}
