package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteAPISteps {

    private static final String BASE_URL = "http://localhost:7081/api/books/";
    private Response response;

    @Given("the admin user is logged in")
    public void theAdminUserIsLoggedIn() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "password");
    }

    @Given("the admin user is logged in with incorrect credentials")
    public void theAdminUserIsLoggedInWithIncorrectCredentials() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "incorrect-password");
    }

    @Given("a regular user is logged in")
    public void aRegularUserIsLoggedIn() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("user", "password");
    }

    @Given("a book with ID {int} exists in the database")
    public void aBookWithIDExistsInTheDatabase(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().get(BASE_URL + bookId);
        assertThat("Expected HTTP status 200 but found " + response.statusCode(), response.statusCode(), is(equalTo(200)));
    }

    @When("the admin sends a DELETE request without specifying a book ID")
    public void theAdminSendsADELETERequestWithoutSpecifyingABookID() {
        response = SerenityRest.given()
                .auth().preemptive().basic("user", "password")
                .when().delete(BASE_URL);
    }

    @When("the admin {string} to delete a book with ID {int}")
    public void theAdminTriesToDeleteABookWithID(String action, Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", action.equals("tries") ? "correct-password" : "incorrect-password")
                .when().delete(BASE_URL + bookId);
    }

    @When("the regular user tries to delete a book with ID {int}")
    public void theRegularUserTriesToDeleteABookWithID(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("user", "password")
                .when().delete(BASE_URL + bookId);
    }

    @Then("the server should respond with status code {int}")
    public void theServerShouldRespondWithStatusCode(int statusCode) {
        assertThat("Expected HTTP status " + statusCode + " but found " + response.statusCode(), response.statusCode(), is(equalTo(statusCode)));
    }

    @And("the server should return a {string} message")
    public void theServerShouldReturnAMessage(String message) {
        assertThat("Expected message to contain \"" + message + "\"", response.getBody().asString(), containsString(message));
    }

    @And("the database should no longer list the book with ID {int}")
    public void theDatabaseShouldNoLongerListTheBookWithID(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().get(BASE_URL + bookId);
        assertThat("Expected HTTP status 404 for book ID " + bookId, response.statusCode(), is(equalTo(404)));
    }

    @When("the admin tries to delete a book with a nonexistent ID {int}")
    public void the_admin_tries_to_delete_a_book_with_a_nonexistent_id(Integer bookId) {
        // Assuming the admin is already authenticated properly prior to this action.
        response = SerenityRest.given()
                .auth().preemptive().basic("user", "password")
                .when().delete(BASE_URL + bookId);
    }

    @When("the admin attempts to delete a book with ID {int} using invalid credentials")
    public void the_admin_attempts_to_delete_a_book_with_id_using_invalid_credentials(Integer bookId) {
        // Using incorrect credentials specifically for this action.
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "wrong-password")
                .when().delete(BASE_URL + bookId);
    }

    @When("the admin sends a DELETE request for the book with ID {int}")
    public void the_admin_sends_a_delete_request_for_the_book_with_id(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "correct-password")
                .when().delete(BASE_URL + bookId);
    }



}