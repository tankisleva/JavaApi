package badtests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ParseJson {


    @Test
    public void testParseJson(){
        JsonPath responseJson = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        responseJson.prettyPrint();

        // Извлекаем текст второго сообщения
        String secondMessage = responseJson.getString("messages[1].message");
        System.out.println("Второе сообщение: " + secondMessage);


//        System.out.println(responseJson);
//        responseJson.getString("messages");


    }


}
