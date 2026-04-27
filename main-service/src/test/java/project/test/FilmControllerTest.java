package project.test;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import project.model.Film;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {
    @LocalServerPort
    private int port;
    HttpClient client;

    @Test
    void getAllFilms() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void PostFilm() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().contains("Matrix"));
    }

    @Test
    void PostWrongFilmsName() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void PostWrongLengthDescription() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fi-Sci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiSci-fiiiii",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        assertEquals(400, response.statusCode());
    }

    @Test
    void PostWrongDate() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1895-12-28",
                  "duration": 120
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void PostCorrectDate() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1895-12-27",
                  "duration": 120
                }
                """;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void PostWrontDuration() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": -120
                }
                """;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void PostCorrectDuration() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void PutCorrectTest() throws IOException, InterruptedException{
        client = HttpClient.newHttpClient();

        String postFilm = """
                {
                 "name": "Matrix",
                 "description": "Sci-fi movie",
                 "releaseDate": "1999-03-31",
                 "duration": 120
                }
                """;
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(postFilm))
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        String putFilm = """
                {
                 "id": 1,
                 "name": "Matrix 2",
                 "description": "Sci-fi movie like Matrix 1",
                 "releaseDate": "1999-03-31",
                 "duration": 120
                }
                """;
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(putFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, putResponse.statusCode());
        assertTrue(putResponse.body().contains("Matrix 2"));

    }

    @Test
    void PutWrongTest() throws IOException, InterruptedException{
        client = HttpClient.newHttpClient();

        String postFilm = """
                {
                 "name": "Matrix",
                 "description": "Sci-fi movie",
                 "releaseDate": "1999-03-31",
                 "duration": 120
                }
                """;
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(postFilm))
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        String putFilm = """
                {
                 "id": 1,
                 "name": "Matrix 2",
                 "description": "Sci-fi movie like Matrix 1",
                 "releaseDate": "1700-03-31",
                 "duration": 120
                }
                """;
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(putFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(400, putResponse.statusCode());
    }

    @Test
    void deleteFilm() throws IOException, InterruptedException {
        String putFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        client = HttpClient.newHttpClient();
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(putFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, putResponse.statusCode());

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, deleteResponse.statusCode());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());
        assertEquals("[]", getResponse.body());
    }

    @Test
    void deleteWrongIdFilm() throws IOException, InterruptedException {
        String putFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        client = HttpClient.newHttpClient();
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(putFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, putResponse.statusCode());

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/2"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, deleteResponse.statusCode());
    }

    @Test
    void testAddLikeToFilm() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,likeResponse.statusCode());
    }

    @Test
    void  testAddLikeToFilmWrongUser() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/5/like/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(404,likeResponse.statusCode());

    }

    @Test
    void  testAddLikeToInvalidFilmId() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/5"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(404,likeResponse.statusCode());

    }

    @Test
    void deleteLike() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,likeResponse.statusCode());

        HttpRequest requestToDeleteLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/1"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> responseToDelete = client.send(requestToDeleteLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,responseToDelete.statusCode());
    }

    @Test
    void testDeleteLikeToFilmWrongUser() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, likeResponse.statusCode());

        HttpRequest requestToDeleteLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/5/like/1"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> responseToDelete = client.send(requestToDeleteLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, responseToDelete.statusCode());
    }

    @Test
    void testDeleteLikeToInvalidFilmId() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, likeResponse.statusCode());

        HttpRequest requestToDeleteLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/5"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> responseToDelete = client.send(requestToDeleteLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, responseToDelete.statusCode());
    }

    @Test
    void  getTopFilmsList() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();

        String testFilm = """
                {
                  "name": "Matrix",
                  "description": "Sci-fi movie",
                  "releaseDate": "1999-03-31",
                  "duration": 120
                }
                """;

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testFilm))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

        String testName = """
                {
                  "email": "abc@mail.ru",
                  "login": "abc",
                  "name": "Sergei",
                  "birthday": "1997-12-24"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(testName))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        HttpRequest requestToTestLike = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/1/like/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> likeResponse = client.send(requestToTestLike, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,likeResponse.statusCode());

        HttpRequest requestToTop = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/films/popular?count=10"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse responseToTop = client.send(requestToTop, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,likeResponse.statusCode());
    }

}

