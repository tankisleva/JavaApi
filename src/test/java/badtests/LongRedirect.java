package badtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirect {

    @Test
    public void testFollowRedirects() {
        String initialUrl = "https://playground.learnqa.ru/api/long_redirect";
        String redirectUrl = initialUrl;
        int statusCode;

        // Цикл, который будет выполнять запросы, пока не получит статус 200
        do {
            Response response = RestAssured
                    .given()
                    .redirects().follow(false) // Не следуем за редиректами автоматически
                    .get(redirectUrl);

            statusCode = response.getStatusCode();

            if (statusCode != 200) {
                redirectUrl = response.getHeader("Location");
                System.out.println("Redirecting to: " + redirectUrl);
            } else {
                System.out.println("Final URL: " + redirectUrl);
            }
        } while (statusCode != 200);

        // Дополнительно можно добавить проверку, что статус код действительно 200
        assert(statusCode == 200);
    }
}
