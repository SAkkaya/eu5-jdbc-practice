package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
//import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;

public class SpartanTestWithParameters {

    @BeforeClass
    public void beforeclass(){
        baseURI ="http://3.87.241.127:8000";
    }

      /*
          Given accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json;charset=UTF-8
          And "Blythe" should be in response payload(payload means also body)
       */

    @Test
    public void getSpartanID_Positive_PathParam(){

//        Response response = given().accept(ContentType.JSON)
//                .when().get("/api/spartans/5");

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5)
                .when().get("/api/spartans/{id}");

       assertEquals(response.statusCode(),200);

       assertEquals(response.contentType(), "application/json;charset=UTF-8");

       assertTrue(response.body().asString().contains("Blythe"));

    }

    /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json;charset=UTF-8
        And "Spartan Not Found" message should be in response payload
     */

    @Test
    public void getSpartanID_Negative_PathParam() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get("api/spartanns/{id}");

        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json");
//        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Spartan Not Found"));


    }


    /*
        Given accept type is Json
        And query parameter values are :
        gender|Female
        nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json;charset=UTF-8
        And "Female" should be in response payload
        And "Janette" should be in response payload
     */

    @Test
    public void positiveTestWithQueryParam(){

    }

    @Test
    public void positiveTestWithQueryParamWithMaps(){

    }


}
