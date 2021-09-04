package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class HW_Assignment1 {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("hr_api_url");

    }


   /*
    ORDS API:
    Q1:
            - Given accept type is Json
            - Path param value- US
            - When users sends request to /countries
            - Then status code is 200
            - And Content - Type is Json
            - And country_id is US
            - And Country_name is United States of America
            - And Region_id is 2

    */

    @Test
    public void test1(){
        //Request path solution -1-
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("values", "US")
                .when().get("/countries/{values}");

        //Response path solution
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json" );

        String country_id = response.path("country_id");
        assertEquals(country_id, "US");

        String country_name = response.path("country_name");
        assertEquals(country_name, "United States of America");

        int region_id = response.path("region_id");
        assertEquals(region_id, 2);


        //Json path solution -2-

        JsonPath jsonPath = response.jsonPath();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json" );

        String country_id2= jsonPath.getString("country_id");
        assertEquals(country_id2, "US");

        String country_name2 = jsonPath.getString("country_name");
        assertEquals(country_name2, "United States of America");

        int region_id2 = jsonPath.getInt("region_id");
        assertEquals(region_id2, 2);

        //HamcrestMatchers solution -3-

        given().accept(ContentType.JSON)
                .and().pathParam("values", "US")
                .when().get("/countries/{values}").then().statusCode(200)
//                .and().contentType("application/json");    // assertThat() olmadan da olur
                .and().assertThat().contentType("application/json")
//                .and().body("country_id", equalTo("US"),
                .and().assertThat().body("country_id", equalTo("US"),
                        "country_name", equalTo("United States of America"),
                      "region_id", equalTo(2));

    }




   /*
    Q2:
            - Given accept type is Json
            - Query param value - q={"department_id":80}
            - When users sends request to /employees
            - Then status code is 200
            - And Content - Type is Json
            - And all job_ids start with 'SA'
            - And all department_ids are 80
            - Count is 25

    */

    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\": 80}")
                .when().get("/employees");


        JsonPath jsonPath= response.jsonPath();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json" );

        List<String> listJob_IDs = jsonPath.getList("items.job_id");

        for (String listJob_id : listJob_IDs) {
            assertTrue(listJob_id.startsWith("SA"));
        }

        List<Integer> listDepartment_ids  = jsonPath.getList("items.department_id");

        for (int listDepartment_id : listDepartment_ids) {
            assertEquals(listDepartment_id, 80);
        }


//        int count = jsonPath.getInt("count");
//        assertEquals("count", 2);
         assertEquals(jsonPath.getInt("count"), 25);


    }



   /*
    Q3:
            - Given accept type is Json
            - Query param value q= region_id 3
            - When users sends request to /countries
            - Then status code is 200
            - And all regions_id is 3
            - And count is 6
            - And hasMore is false
            - And Country_name are;
            Australia,China,India,Japan,Malaysia,Singapore
*/

    @Test
    public void test3(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\": 3}")
                .when().get("/countries");

        JsonPath jsonPath= response.jsonPath();

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");

        List<Integer> listRegion_IDs = jsonPath.getList("items.region_id");

        for (int listRegion_id : listRegion_IDs) {
            assertEquals(listRegion_id, 3);
        }
        assertEquals(jsonPath.getInt("count"), 6);

        assertFalse(jsonPath.getBoolean("hasMore"));


        List<String> actualListOfCountry_Names = jsonPath.getList("items.country_name");

        List<String> expectedListOfCountry_Names = Arrays.asList("Australia","China","India","Japan","Malaysia","Singapore");

        assertEquals(actualListOfCountry_Names, expectedListOfCountry_Names);



    }





}
