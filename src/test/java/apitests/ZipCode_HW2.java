package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class ZipCode_HW2 {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("zipcode_api_url");

    }

    /*
    Documentation: https://www.zippopotam.us/ (Links to an external site.)
    BASEURL: api.zippopotam.us (Links to an external site.)



    Scenarios:

    Given Accept application/json
    And path zipcode is 22031
    When I send a GET request to /us endpoint
    Then status code must be 200
    And content type must be application/json
    And Server header is cloudflare
    And Report-To header exists
    And body should contains following information
        post code is 22031
        country  is United States
        country abbreviation is US
        place name is Fairfax
        state is Virginia
        latitude is 38.8604

     */


    @Test
    public void test1(){

        given().accept(ContentType.JSON)
                .and().pathParam("post code", 22031)
        .when().get("/us/{post code}")
        .then().assertThat().statusCode(200)
                .contentType("application/json")
                .and().header("Server", "cloudflare")
                .and().headers("Report-To", notNullValue())
//                .and().body("post code", is(equalTo("22031")))
//                .and().body("post code", equalTo(("22031"))
                .and().body("'post code'", is("22031"),
                        "country", is("United States"),
                        "'country abbreviation'", is("US"),
                        "places.'place name'[0]", is("Fairfax"),
                        "places.state[0]", is("Virginia"),
                        "places.latitude[0]", is("38.8604"));

    }


    /*
    Given Accept application/json
    And path zipcode is 50000
    When I send a GET request to /us endpoint
    Then status code must be 404
    And content type must be application/json

     */


    @Test
    public void test2(){
        given().accept(ContentType.JSON)
                .and().pathParam("post code", 50000)
        .when().get("/us/{post code}")
        .then().statusCode(404)
        .and().contentType("application/json");

    }




    /*
    Given Accept application/json
    And path state is va
    And path city is fairfax
    When I send a GET request to /us endpoint
    Then status code must be 200
    And content type must be application/json
    And payload should contains following information
    country abbreviation is US
    country  United States
    place name  Fairfax
    each places must contains fairfax as a value
    each post code must start with 22

     */


    @Test
    public void test3(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("city", "fairfax")
                .when().get("/us/{state}/{city}");


        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");

        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getString("'country abbreviation'"), "US");
        assertEquals(jsonPath.getString("country"),"United States" );
        assertEquals(jsonPath.getString("'place name'"),"Fairfax" );

        List<String> listOfPlaceNames = jsonPath.getList("places.'place name'");

        for (String placeName : listOfPlaceNames) {
            assertTrue(placeName.toLowerCase().contains("fairfax"));
        }

        List<String> listOfPostCodes = jsonPath.getList("places.\"post code\"");
        for (String postCode : listOfPostCodes) {
            assertTrue(postCode.startsWith("22"));
        }


//
//
//                 .then().statusCode(200)
//                .contentType("application/json")
//                .and().body("'country abbreviation'", is("US"),
//                        "country", is("United States"),
//                        "'place name'", is("Fairfax"));




    }






}
