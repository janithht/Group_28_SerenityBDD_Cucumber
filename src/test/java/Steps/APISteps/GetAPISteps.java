package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetAPISteps {
    private static final String GET_ENDPOINT = "http://localhost:7081/api/books";
    private Response response;

    @Given("User is authorized as an admin to get books")
    public void user_is_authorized_to_get_all_books_as_an_admin() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "password");
    }

    @Given("User is authorized as a regular user to get books")
    public void user_is_authorized_to_get_all_books_as_a_regular_user() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("user", "password");
    }

    @When("the regular user sends a GET all books request")
    public void the_regular_user_sends_a_get_all_books_request() {
        response = SerenityRest.given()
                .auth().preemptive().basic("user", "password")
                .when().get(GET_ENDPOINT);
    }

    @When("the admin user sends a GET all books request")
    public void the_admin_user_sends_a_get_all_books_request() {
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .when().get(GET_ENDPOINT);
    }

    @Then("the status of the response should be {int}")
    public void the_status_of_the_response_should_be(Integer statusCode) {
        assertThat(response.statusCode(), is(equalTo(statusCode)));
    }


    @And("the response should contain a book with Id {int}")
    public void the_response_should_contain_a_book_with_Id(Integer bookId) {
        response.then().body("$", not(empty()));
        response.then().body("size()", greaterThan(0));

        // Check second book in the response(since first book is modified by updating)
        response.then().body("[1].title", equalTo("1984"));
        response.then().body("[1].author", equalTo("George Orwell"));
    }


}
