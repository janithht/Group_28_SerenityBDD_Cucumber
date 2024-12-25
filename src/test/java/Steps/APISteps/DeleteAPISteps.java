package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteAPISteps {

    private static final String DELETE_ENDPOINT = "http://localhost:7081/api/books/";
    private Response response;

    @Given("User is authorized as an admin")
    public void user_is_authorized_as_an_admin() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "password");
    }

    @Given("User is unauthorized")
    public void user_is_unauthorized() {
        SerenityRest.reset();
    }

    @And("a book exists with Id {int}")
    public void a_book_exists_with_Id(Integer bookId) {
        Response getResponse = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().get(DELETE_ENDPOINT + bookId);
        assertThat(getResponse.statusCode(), is(equalTo(200)));
    }

    @When("User sends a DELETE request for the book with Id {int}")
    public void user_sends_a_DELETE_request(Integer bookId) {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().delete(DELETE_ENDPOINT + bookId);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertThat(response.statusCode(), is(equalTo(statusCode)));
    }

    @And("the response message should be {string}")
    public void the_response_message_should_be(String message) {
        assertThat(response.body().asString(), containsString(message));
    }

    @And("the book with Id {int} should no longer exist")
    public void the_book_with_Id_should_no_longer_exist(Integer bookId) {
        Response getResponse = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().get(DELETE_ENDPOINT + bookId);
        assertThat(getResponse.statusCode(), is(equalTo(404)));
    }
}
