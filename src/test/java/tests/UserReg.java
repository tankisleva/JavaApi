package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserReg extends BaseTestCase {

    @Test
    public void testCreateUserWithExistingEmail(){
        String email = "oleg@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured.given().body(userData).post("https://playground.learnqa.ru/api/user/").andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '"+email+"' already exists");
    }


    @Test
    public void testCreateUserSuccess(){
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateAuth = RestAssured.given().body(userData).post("https://playground.learnqa.ru/api/user/").andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }
}
