package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {
    int petId = 772322;
    String petName = "Riley";
    String petStatus = "alive";
    String baseURI = "https://petstore.swagger.io/v2/pet/";

    @Test(priority = 0)
    public void createPet() {
        //Creating the request payload
        HashMap data = new HashMap<>();
        data.put("id",petId);
        data.put("name",petName);
        data.put("status",petStatus);

        // Specify the base URL to the RESTful web service
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                .body(data)
                .when().post(baseURI); // Run POST request

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().statusCode(200);
        response.then().body("id", equalTo(petId));
        response.then().body("name", equalTo(petName));
        response.then().body("status", equalTo(petStatus));

    }

    @Test(priority = 1)
    public void getPetDetails() {
        // Specify the base URL to the RESTful web service with path parameter
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                .pathParams("id", petId)
                .when().get(baseURI+"{id}"); // Run GET request

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().statusCode(200);
        response.then().body("id", equalTo(petId));
        response.then().body("name", equalTo(petName));
        response.then().body("status", equalTo(petStatus));

    }

    @Test(priority = 2)
    public void deletePetDetails() {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                .pathParams("id", petId)
                .when().delete(baseURI+"{id}"); // Run Delete request

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().statusCode(200);
        response.then().body("message", equalTo(Integer.toString(petId)));

    }

}
