package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostAPISteps {

    private static final String POST_ENDPOINT = "http://localhost:7081/api/books/";
    private Response response;
    private String bookTitle;
    private String bookAuthor;

    @Given("User is authorized to create a book as an admin")
    public void user_is_authorized_to_create_a_book_as_an_admin() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("admin", "password");
    }

    @When("User creates a book with title {string} and author {string}")
    public void user_creates_a_book_with_title_and_author(String title, String author) {
        this.bookTitle = title;
        this.bookAuthor = author;
        String requestBody = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);
        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password")
                .contentType("application/json")
                .body(requestBody)
                .when().post(POST_ENDPOINT);
        response.then().statusCode(201);
    }

    @Then("application should return a status code {int} and the created book details")
    public void application_should_return_a_status_code_and_the_created_book_details(Integer statusCode) {
        // Assert status code
        response.then().statusCode(statusCode);

        // Get the created title and author from the response
        String createdTitle = response.jsonPath().getString("title");
        String createdAuthor = response.jsonPath().getString("author");

        // Validate if the title and author match what was created
        assertThat(createdTitle, equalTo(bookTitle));
        assertThat(createdAuthor, equalTo(bookAuthor));
    }
}
