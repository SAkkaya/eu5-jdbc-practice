package Day6_POJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class PostRequestDemo {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

     /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"MikeEU",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */

    @Test
    public void PostNewSpartan(){

        String jsonBody ="{\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"MikeEU\",\n" +
                "      \"phone\":8877445596\n" +
                "   }";

        Response response = given().log().all()
                .accept(ContentType.JSON) // hey API send me JSON response
                .and()
                .contentType(ContentType.JSON)    // I am sending you JSON
                .and()
                .body(jsonBody)
        .when().
                post("api/spartans");


        response.prettyPrint();


        //verify status code 201 for post
        assertEquals(response.statusCode(), 201);

        //verify content type
        assertEquals(response.contentType(),"application/json");

        //verify successful message

        String actualMessage = response.path("success");
        String expectedMessage = "A Spartan is Born!";

        assertEquals(actualMessage, expectedMessage);
//      short way
//      assertEquals(response.path("success"), "A Spartan is Born!");

//      assertion for spartan data
        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name, "MikeEU");
        assertEquals(gender, "Male");
        assertEquals(phone, 8877445596L);



    }



}
