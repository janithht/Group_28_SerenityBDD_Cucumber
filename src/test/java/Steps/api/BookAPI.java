package Steps.api;

import Steps.factory.RequestFactory;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

//POM - Separates API calls from step definitions
public class BookAPI {

    private static BookAPI instance;

    private BookAPI() {}

    public static BookAPI getInstance() {
        if(instance == null) {
            instance = new BookAPI();
        }
        return instance;
    }

    private static final String BASE_URL = "http://localhost:7081/api/books/";


    public Response createBook(String title, String author) {
        String payload = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);
        return RequestFactory.getCurrentRequest()
                .body(payload)
                .post(BASE_URL);
    }

    public Response createBookWithoutTitle(String author) {
        String payload = String.format("{\"author\": \"%s\"}", author);
        return RequestFactory.getCurrentRequest()
                .body(payload)
                .post(BASE_URL);
    }
    public Response createBookWithoutAuthor(String title) {
        String payload = String.format("{\"title\": \"%s\"}", title);
        return RequestFactory.getCurrentRequest()
                .body(payload)
                .post(BASE_URL);
    }

    public Response getBooksByAdmin() {
        return RequestFactory.adminRequest()
                .when()
                .get(BASE_URL);
    }

    public Response getBooksByUser() {
        return RequestFactory.userRequest()
                .when()
                .get(BASE_URL);
    }

    public Response getBookByIdAdmin(Integer bookId) {
        return RequestFactory.adminRequest()
                .when()
                .get(BASE_URL + bookId);
    }

    public Response getBookByIdUser(Integer bookId) {
        return RequestFactory.userRequest()
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

    public Response updateWithoutCorrectAuthentication(Integer bookId, String title, String author) {
        SerenityRest.reset();
        String requestBody = String.format("{\"id\": %d, \"title\": \"%s\", \"author\": \"%s\"}", bookId, title, author);
        return RequestFactory.invalidAdminRequest()
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

    public Response checkBookExists(Integer bookId) {
        return RequestFactory.adminRequest()
                .when()
                .get(BASE_URL + bookId);
    }

    public Response deleteWithoutID() {
        return RequestFactory.adminRequest()
                .when()
                .delete(BASE_URL);
    }

    public Response deleteBookWithID(Integer bookId, boolean correctCredentials) {
        return RequestFactory.userRequest()
                .when()
                .delete(BASE_URL + bookId);
    }

    public Response deleteBookWithIDAsUser(Integer bookId) {
        return RequestFactory.userRequest()
                .when()
                .delete(BASE_URL + bookId);
    }

    public Response deleteBookWithInvalidCredentials(Integer bookId) {
        return RequestFactory.invalidAdminRequest()
                .when()
                .delete(BASE_URL + bookId);
    }
}
