package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;

public class UpdateAPISteps {

    private static final String UPDATE_ENDPOINT = "http://localhost:7081/api/books/";
    private Response response;

    @Given("User is authorized as an admin")
    public void user_is_authorized_as_an_admin() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "password");
    }

    @And("a book exists with Id {int}")
    public void a_book_exists_with_id(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().get(UPDATE_ENDPOINT + bookId);
        response.then().statusCode(200);  // Keep the response for later use or checks
    }

    @When("User updates the book with ID {int} setting title to {string}, author to {string}")
    public void user_updates_the_book_with_id_setting_title_to_author_to(Integer bookId, String title, String author) {
        String requestBody = String.format("{\"id\": %d,\"title\": \"%s\", \"author\": \"%s\"}",bookId, title, author);
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + bookId);
    }

    @When("User send a PUT request with invalid data including string in a integer field")
    public void user_send_put_request_with_invalid_data() {
        String requestBody = "{\"id\": \"2\", \"title\": \"Don Quixote\", \"author\": \"Miguel de Cervantes\"}";
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + "2");
    }

    @When("User send a PUT request with valid update data")
    public void user_send_put_request_with_valid_update_data() {
        String requestBody = "{\"id\": 101, \"title\": \"Treasure Island\", \"author\": \"Robert Louis Stevenson\"}";
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + "101");
    }

    @When("User send a PUT request missing the author field")
    public void user_send_put_request_missing_the_title_and_author_fields() {
        String requestBody = "{\"id\": 2, \"title\": \"Don Quixote updated title\"}";
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + "2");
    }

    @When("User send a PUT request without proper authentication credentials")
    public void user_send_put_request_without_proper_authentication_credentials() {
        SerenityRest.reset(); // Reset to remove authentication
        String requestBody = "{\"id\": \"1\", \"title\": \"Unauthorized Title\", \"author\": \"Unauthorized Author\"}";
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + "1");
    }

    @But("the user sends a PUT request to update the book with ID {int} without proper authentication credentials")
    public void the_user_sends_a_put_request_to_update_the_book_without_proper_authentication_credentials(Integer bookId) {
        SerenityRest.reset();  // This ensures that the authentication is cleared
        String requestBody = "{\"id\": " + bookId + ", \"title\": \"Unauthorized Update\", \"author\": \"Anonymous\"}";
        response = SerenityRest.given()
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + bookId);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.statusCode(), is(equalTo(statusCode)));
    }

    @And("the book with Id {int} should have updated details")
    public void the_book_with_id_should_have_updated_details(Integer bookId) {
        Response response = SerenityRest.lastResponse();
        String updatedAuthor = response.jsonPath().getString("author");
        assertThat(updatedAuthor, is(equalTo("Mark Twain")));
    }

    @And("the response should indicate that the book with ID {int} not found")
    public void the_response_should_indicate_that_the_book_with_id_does_not_exist(Integer bookId) {
        assertThat(response.body().asString(), containsString("not found"));
    }


    @And("the response should include a message that Mandatory parameters should not be null")
    public void the_response_should_include_a_message_that_required_fields_are_missing() {
        assertThat(response.body().asString(), containsString("Mandatory parameters should not be null"));
    }

    @And("the response should state that authentication is required")
    public void the_response_should_state_that_authentication_is_required() {
        assertThat(response.body().asString(), containsString("Authentication is required"));
    }

}
