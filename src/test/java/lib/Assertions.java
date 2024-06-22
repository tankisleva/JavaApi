package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {

    public static void assertJsonByName(Response Response, String name, int expectedValue){
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value,
                "JSON value is not equal to expected value");
    }


    public static void assertJsonByName(Response Response, String name, String expectedValue){
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value,
                "JSON value is not equal to expected value");
    }

    public static void assertResponseCodeEquals(Response Response, int expectedStatusCode){
        assertEquals(expectedStatusCode,
                Response.statusCode(),
                "Response status code is not as expected");

    }

    public static void assertResponseTextEquals(Response Response, String expectedAnswer){
        assertEquals(expectedAnswer,
                Response.asString(),
                "Response text is not as expected");
    }


    public static void assertJsonHasField(Response Response, String expectFieldName){
       Response.then().assertThat().body("$", hasKey(expectFieldName));
    }


    public static void assertJsonHasFields(Response Response, String[] expectFieldNames){
        for (String expectFieldName: expectFieldNames){
            Assertions.assertJsonHasField(Response,expectFieldName);
        }
    }


    public static void assetJsonHasNotField(Response Response, String expectFieldName){
        Response.then().assertThat().body("$", not(hasKey(expectFieldName)));
    }

    public static void assetJsonHasNotFields(Response Response, String[] expectFieldNames){
        for (String expectFieldName: expectFieldNames){
            Assertions.assetJsonHasNotField(Response,expectFieldName);
        }
    }
}
