package Steps.api;

import Steps.factory.RequestFactory;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

//POM - Separates API calls from step definitions
public class BookAPI {

    private static final String BASE_URL = "http://localhost:7081/api/books/";


    public Response getBooksByAdmin() {
        return RequestFactory.adminGetRequest()
                .when()
                .get(BASE_URL);
    }
    public Response getBooksByUser() {
        return RequestFactory.userGetRequest()
                .when()
                .get(BASE_URL);
    }
    public Response getBookByIdAdmin(Integer bookId) {
        return RequestFactory.adminGetRequest()
                .when()
                .get(BASE_URL + bookId);
    }
    public Response getBookByIdUser(Integer bookId) {
        return RequestFactory.userGetRequest()
                .when()
                .get(BASE_URL + bookId);
    }


    public Response createBook(String title, String author) {
        String payload = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);
        return RequestFactory.getCurrentRequest()  // This retrieves the current request (admin or user)
                .body(payload)
                .post(BASE_URL);
    }

    public Response createBookWithoutTitle(String author) {
        String payload = String.format("{\"author\": \"%s\"}", author);
        return RequestFactory.getCurrentRequest()  // This retrieves the current request (admin or user)
                .body(payload)
                .post(BASE_URL);
    }

    public Response getBookById(Integer bookId) {
        return RequestFactory.adminRequest()
                .when()
                .get(BASE_URL + bookId);
    }

    public Response updateBook(Integer bookId, String title, String author) {
        String requestBody = String.format("{\"id\": %d, \"title\": \"%s\", \"author\": \"%s\"}", bookId, title, author);
        return RequestFactory.adminRequest()
                .body(requestBody)
                .when()
                .put(BASE_URL + bookId);
    }

    public Response sendInvalidUpdateRequest(String id, String title, String author) {
        String requestBody = String.format("{\"id\": \"%s\", \"title\": \"%s\", \"author\": \"%s\"}", id, title, author);
        return RequestFactory.adminRequest()
                .body(requestBody)
                .when()
                .put(BASE_URL + id);  // Assuming 'id' is the part of the URL that might be wrong
    }

    public Response updateWithoutAuthentication(Integer bookId, String title, String author) {
        SerenityRest.reset();
        String requestBody = String.format("{\"id\": %d, \"title\": \"%s\", \"author\": \"%s\"}", bookId, title, author);
        return SerenityRest.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put(BASE_URL + bookId);
    }

    public Response updateBookMissingAuthor(Integer bookId, String title) {
        String requestBody = String.format("{\"id\": %d, \"title\": \"%s\"}", bookId, title);
        return RequestFactory.adminRequest()
                .body(requestBody)
                .when()
                .put(BASE_URL + bookId);
    }
}
