package POJOtest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PojoCountryInfo {

    @Test
    public void pojoTest1(){

        Response response = given().accept(ContentType.JSON)
//                .pathParam("id", 22031)
                .when().get("http://api.zippopotam.us/us/22031");

        response.prettyPrint();

        CountryInfo2 as = response.body().as(CountryInfo2.class);
        System.out.println("as.getCountry() = " + as.getCountry());


    }


}
