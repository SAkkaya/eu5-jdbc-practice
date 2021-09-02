package apitests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.stylesheets.LinkStyle;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithJsonPath {


    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("hr_api_url");
    }


    @Test
    public void test1(){
        Response response = get("/countries");

        //assign to jsonpath
        JsonPath jsonPath = response.jsonPath();

        String secondCountryName = jsonPath.getString("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        //get all country ids

        List<String> countryIDs = jsonPath.getList("items.country_id");
        System.out.println( countryIDs);

        // get all country names where their region id is equal to 2
        List<String> countryNamesWithREgionID2 = jsonPath.getList("items.findAll{it.region_id==2}.country_name");
        System.out.println(countryNamesWithREgionID2);

        List<String> countryIDsWithREgionID2 = jsonPath.getList("items.findAll{it.region_id==3}.country_id");
        System.out.println(countryIDsWithREgionID2);

        // Gpath syntax oldugu icin path =>{"items.findAll{it.region_id==2}.country_name"} ile de ayni path calisir: EK BILGI
//        List<String> countryNAmesWithREgionID2WithGpath =response.path("items.findAll{it.region_id==2}.country_name");
//        System.out.println("countryNAmesWithREgionID2WithGpath = " + countryNAmesWithREgionID2WithGpath);

    }

    @Test
    public void test2(){
        Response response = given().queryParam("limit", 107)
                .when().get("/employees");

        JsonPath jsonPath = response.jsonPath();

        //get me all firstname of employees who is working is working as IT_PROG

//        List<String> firstNames = jsonPath.getList("items.first_name");
        List<String> firstNames = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.first_name");
        System.out.println(firstNames);

        //get me all email of employees who is working is working as IT_PROG
        List<String> emails = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println(emails);

        //get me all firstname of employees who is making more than 10000
        List<String> firstNames2 = jsonPath.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println(firstNames2);

        //get me all firstname of employees who is making less than 2200
        List<String> firstNames3 = jsonPath.getList("items.findAll {it.salary<2200}.first_name");
        System.out.println(firstNames3);  //TJ

        //get me firstname of who is making highest salary
        String kingName = jsonPath.get("items.max {it.salary}.first_name");
        System.out.println("kingName = " + kingName);

        //get me firstname of who is making lowest salary
        String TJName = jsonPath.get("items.min {it.salary}.first_name");
        System.out.println("TJName = " + TJName);

    }



}
