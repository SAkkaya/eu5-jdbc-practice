package Day8;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SpartanBasicAuth {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void test1(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
        .when()
//                .get("http://54.198.216.176:8000/api/spartans")
                .get("/api/spartans")

        .then().log().all()
                .statusCode(200);
    }




}