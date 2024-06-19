import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LongTimeJob {

    @Test
    public void testLongtimeJob() throws InterruptedException {
        // Шаг 1: Создание задачи
        Response createJobResponse = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job");

        JsonPath createJobJson = createJobResponse.jsonPath();
        createJobJson.prettyPrint();
        int seconds = createJobJson.getInt("seconds");
        String token = createJobJson.getString("token");

        assertNotNull(token, "Token should not be null");
        System.out.println("Task created. Token: " + token + ", Time to wait: " + seconds + " seconds");

        // Шаг 2: Запрос с токеном до завершения задачи
        Response beforeJobReadyResponse = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job");

        JsonPath beforeJobReadyJson = beforeJobReadyResponse.jsonPath();
        beforeJobReadyJson.prettyPrint();
        String statusBefore = beforeJobReadyJson.getString("status");

        assertEquals("Job is NOT ready", statusBefore, "Status before job is ready should be 'Job is NOT ready'");
        System.out.println("Status before job is ready: " + statusBefore);

        // Шаг 3: Ожидание нужного количества секунд
        Thread.sleep(seconds * 1000);

        // Шаг 4: Запрос с токеном после завершения задачи
        Response afterJobReadyResponse = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job");

        JsonPath afterJobReadyJson = afterJobReadyResponse.jsonPath();
        afterJobReadyJson.prettyPrint();
        String statusAfter = afterJobReadyJson.getString("status");
        String result = afterJobReadyJson.getString("result");

        assertEquals("Job is ready", statusAfter, "Status after job is ready should be 'Job is ready'");
        assertNotNull(result, "Result should not be null after job is ready");
        System.out.println("Status after job is ready: " + statusAfter);
        System.out.println("Result: " + result);
    }
}
