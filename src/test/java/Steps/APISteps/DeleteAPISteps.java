package Steps.APISteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteAPISteps {

    private static final String DELETE_ENDPOINT = "http://localhost:7081/api/books/";
    private Response response;

    @Given("User is authorized to delete books as an admin")
    public void user_is_authorized_to_delete_books_as_an_admin() {
        SerenityRest.reset();
        SerenityRest.given();
    //.auth().preemptive().basic("admin", "password");
    }

    @When("User sends a DELETE request for book ID {int}")
    public void user_sends_a_delete_request_for_book_id(Integer bookId) {
        response = SerenityRest.given()
                //.auth().preemptive().basic("admin", "password")
                .when().delete(DELETE_ENDPOINT + bookId);
    }

    @When("User attempts to delete a book without proper authentication")
    public void user_attempts_to_delete_a_book_without_proper_authentication() {
        SerenityRest.reset(); // Clear any authentication
        response = SerenityRest.given()
                .when().delete(DELETE_ENDPOINT + "999"); // Assuming 999 is a valid test ID
    }

    /*@When("User tries to delete a non-existent book with ID {int}")
    public void user_tries_to_delete_a_non_existent_book_with_id(Integer bookId) {
        response = SerenityRest.given()
                //.auth().preemptive().basic("admin", "password")
                .when().delete(DELETE_ENDPOINT + bookId);
    }

    @Then("The response status code for DELETE operation should be {int}")
    public void the_response_status_code_for_delete_operation_should_be(Integer statusCode) {
        assertThat(response.statusCode(), is(equalTo(statusCode)));
    }*/

    @When("User tries to delete a non-existent book with ID {int}")
    public void user_tries_to_delete_a_non_existent_book_with_id(Integer bookId) {
        response = SerenityRest.given()
                .when().delete(DELETE_ENDPOINT + bookId);
    }

    @Then("The response status code for DELETE operation should be {int}")
    public void the_response_status_code_for_delete_operation_should_be(Integer statusCode) {
        assertThat(response.statusCode(), is(equalTo(statusCode)));
    }

    @And("The response should state that the user is not permitted")
    public void the_response_should_state_that_the_user_is_not_permitted() {
        assertThat(response.statusCode(), is(equalTo(403)));
        assertThat(response.body().asString(), containsString("User is not permitted"));
    }

    @And("The response should indicate successful deletion for book ID {int}")
    public void the_response_should_indicate_successful_deletion(Integer bookId) {
        assertThat(response.body().asString(), containsString("Book with ID " + bookId + " deleted successfully"));
    }

    @And("The response should state that the book with ID {int} does not exist")
    public void the_response_should_state_that_the_book_with_id_does_not_exist(Integer bookId) {
        assertThat(response.body().asString(), containsString("Book with ID " + bookId + " not found"));
    }

    @And("The response should state that authentication is required for delete operation")
    public void the_response_should_state_that_authentication_is_required() {
        assertThat(response.body().asString(), containsString("Authentication required"));
    }

    @When("User sends a DELETE request for book ID {int} with invalid credentials")
    public void user_sends_a_delete_request_for_book_id_with_invalid_credentials(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("invalidUser", "invalidPassword") // Invalid credentials
                .when().delete(DELETE_ENDPOINT + bookId);
    }

    @Then("The response should state that the user is not authorized to perform delete operations")
    public void the_response_should_state_that_the_user_is_not_authorized_to_perform_delete_operations() {
        assertThat(response.body().asString(), containsString("Access denied")); // Ensure the error message matches your API's response
    }

}
