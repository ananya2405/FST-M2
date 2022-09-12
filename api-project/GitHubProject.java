package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;


public class GitHubProject {
    String baseURI = "https://api.github.com/user/keys";
    RequestSpecification requestSpec;
    String tokenValue = "ghp_token"; //the actual token is removed because of security reason
    String sshKey = "ssh-public_key"; //the actual key is removed because of security reason
    int keyId=70974493;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "token "+tokenValue)
                .setContentType(ContentType.JSON).setBaseUri(baseURI).build();

    }

    @Test(priority = 0)
    public void addSSHKey() {
        //Creating the request payload
        HashMap data = new HashMap<>();
        data.put("title", "TestAPIKey");
        data.put("key", sshKey);

        Response response = given().spec(requestSpec) // Use requestSpec
                .body(data).when().post(); // Run POST request

        //Printing response through Reporter.log
        Reporter.log(response.asString());

        //Printing response in console
        response.then().log().all();

        // Assertions
        response.then().statusCode(201);
        keyId = response.getBody().path("id");
        System.out.println("The id is = " +keyId);

    }

    @Test(priority = 1)
    public void getSSHKey() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("id", keyId).when().get("/{id}"); // Run Get request

        //Printing response through Reporter.log
        Reporter.log(response.asString());

        //Printing response in console
        response.then().log().all();

        // Assertions
        response.then().statusCode(200);


    }

    @Test(priority = 2)
    public void deleteSSHKey() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("id", keyId).when().delete("/{id}"); // Run Delete request

        //Printing response through Reporter.log
        Reporter.log(response.asString());

        //Printing response in console
        response.then().log().all();

        // Assertions using response spec
        response.then().statusCode(204);


    }

    @Test(priority = 3)
    public void getSSHKeyAfterDeletion() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("id", keyId).when().get("/{id}"); // Run Get request

        //Printing response through Reporter.log
        Reporter.log(response.asString());

        //Printing response in console
        response.then().log().all();

        // Assertions
        response.then().statusCode(404);


    }

}
