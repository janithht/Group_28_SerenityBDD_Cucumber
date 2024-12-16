package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UpdateAPISteps {

    private static final String UPDATE_ENDPOINT = "http://localhost:7081/api/books/";

    @Given("User is authorized as an admin")
    public void user_is_authorized_as_an_admin() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "password");
    }

    @And("a book exists with Id {int}")
    public void a_book_exists_with_id(Integer bookId) {
        SerenityRest.given()
                    .auth().preemptive().basic("admin", "password")
                    .when().get(UPDATE_ENDPOINT + bookId)
                    .then().statusCode(200);
    }

    @When("User updates the book with ID {int} setting title to {string}, author to {string}")
    public void user_updates_the_book_with_id_setting_title_to_author_to(Integer bookId, String title, String author) {
        String requestBody = String.format("{\"id\": \"%s\",\"title\": \"%s\", \"author\": \"%s\"}",bookId, title, author);
        SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().put(UPDATE_ENDPOINT + bookId) // Use the bookId directly in the URL
                .then().statusCode(200);
    }

    @Then("application should return a status code {int}")
    public void application_should_return_a_status_code(Integer statusCode) {
        SerenityRest.then().statusCode(statusCode);
    }

    @And("the book with Id {int} should have updated details")
    public void the_book_with_id_should_have_updated_details(Integer bookId) {
        Response response = SerenityRest.lastResponse();
        String updatedAuthor = response.jsonPath().getString("author");
        assertThat(updatedAuthor, is(equalTo("Mark Twain")));
    }

}
