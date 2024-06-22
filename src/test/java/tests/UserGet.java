package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGet extends BaseTestCase {

    @Test
    public void testGetUserDataNotAuth(){
        String[] expectedFieldNames = {"firstName", "lastName", "email"};
        Response responseUserData = RestAssured.get("https://playground.learnqa.ru/api/user/2").andReturn();
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assetJsonHasNotFields(responseUserData, expectedFieldNames);
    }

    @Test
    public void testGetUserDetailsAsAuthUserSame(){

        Map<String, String> authData = new HashMap<>();
        authData.put("email", "oleg@example.com");
        authData.put("password", "123");

        Response responseGetAuth = RestAssured.given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");


        Response responseUserData = RestAssured.given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/98887").andReturn();

        String[] expectedFields = {"username", "firstName", "lastName", "email"};

        Assertions.assertJsonHasFields(responseUserData, expectedFields);
    }
}
