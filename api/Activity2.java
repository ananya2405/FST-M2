package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {
    String baseURI = "https://petstore.swagger.io/v2/user/";

    @Test(priority = 0)
    public void createUser() throws IOException {
        // Importing JSON file for request payload
        File file = new File("src/test/java/activities/UserData.json");
        FileInputStream inputJSON = new FileInputStream(file);
        // Read JSON file as String
        String reqBody = new String(inputJSON.readAllBytes());

        // Specify the base URL to the RESTful web service
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                .body(reqBody)
                .when().post(baseURI); // Run POST request

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().statusCode(200);
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo(Integer.toString(2424)));

    }

    @Test(priority = 1)
    public void getUserDetails() throws IOException {
        // Specify the base URL to the RESTful web service
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                .pathParams("username", "testuser")
                .when().get(baseURI+"{username}"); // Run GET request

        String responseBody = response.asPrettyString();
        System.out.println("Response Body is =>  " + responseBody);

        //Write the response of the GET request to an external JSON file
        try {
            File respFile = new File("src/test/java/activities/RespData.json");
            respFile.createNewFile();
            FileWriter writer = new FileWriter(respFile.getPath());
            writer.write(responseBody);
            writer.close();
            }
        catch(Exception e) {
            System.out.println(e.getStackTrace());

        }

        // Assertions
        response.then().statusCode(200);
        response.then().body("username", equalTo("testuser"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));

    }

    @Test(priority = 2)
    public void deleteUserDetails() {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                .pathParams("username", "testuser")
                .when().delete(baseURI+"{username}"); // Run DELETE request

        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().statusCode(200);
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("testuser"));

    }

}
