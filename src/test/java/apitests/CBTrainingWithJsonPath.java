package apitests;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class CBTrainingWithJsonPath {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("cbt_api_url");
    }


    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 24107)
                .when().get("/student/{id}");

        //verify status code
        assertEquals(response.statusCode(), 200);

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        //get values from jsonpath
        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);

        String lastName = jsonPath.getString("students.lastName[0]");
        System.out.println("lastName = " + lastName);

        //jemalin cozumu
        String phone = jsonPath.getString("students.contact[0].phone");
        System.out.println("phone = " + phone);

        //AMA bu da veriyor ayni sonucu
//        String phone2 = jsonPath.getString("students.contact.phone[0]");
//        System.out.println("phone = " + phone);


        //get me city zipcode, do assertion
        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println("city = " + city);
        assertEquals(city, "Lake Boris");

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);
        assertEquals(zipCode, 30284);

        // Json path de int a string atayabilirsin hata vermez response . path gibi
        String zipCode2 = jsonPath.getString("students.company[0].address.zipCode");
        System.out.println("zipCode2 = " + zipCode2);
        assertEquals(zipCode2, "30284");

        //Jso path hata vermez String in icine array listi  !!!! ama asagidaki ornekte oldugu gibi;
        String firstName2 = jsonPath.getString("students.firstName");
        System.out.println("firstName2 = " + firstName2);


        // response.path de; String in icine Array List i atayamayiz, hata verir!!   --> java.lang.ClassCastException: java.util.ArrayList cannot be cast to java.lang.String
//        String firstName3 = response.path("students.firstName");
//        System.out.println("firstName3 = " + firstName3);


        // response.path de; String in icine Integer i atayamayiz, hata verir!!    //java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
//        String zipCode3= response.path("students.company[0].address.zipCode");
//        System.out.println("zipCode3 = " + zipCode3);





    }

}