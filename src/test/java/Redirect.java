import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Redirect {

    @Test
    public void testRedirectUrl() {
        // Отправляем GET-запрос и следуем за редиректами
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()// Не следуем за редиректами автоматически
                .get("https://playground.learnqa.ru/api/long_redirect");

        // Извлекаем заголовок Location
        String redirectUrl = response.getHeader("Location");

        // Проверяем, что URL редиректа не пуст
        assertNotNull(redirectUrl, "Redirect URL should not be null");

        // Печатаем URL редиректа
        System.out.println("Redirect URL: " + redirectUrl);
    }
}
