package Steps.APISteps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
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

    @Given("User is authorized as a regular user")
    public void user_is_authorized_as_a_regular_user() {
        SerenityRest.reset();
        SerenityRest.given().auth().preemptive().basic("user", "password");
    }

    @When("User creates a new book with title {string} and author {string}")
    public void user_creates_a_new_book_with_title_and_author(String title, String author) {
        this.bookTitle = title;
        this.bookAuthor = author;

        String requestBody = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);

        response = SerenityRest.given()
                .auth().preemptive().basic("admin", "password") // Admin authorization
                .contentType("application/json")
                .body(requestBody)
                .when().post(POST_ENDPOINT);
    }

    @When("User submits a POST request to create a book with title {string} and author {string}")
    public void user_submits_a_post_request_to_create_a_book_with_title_and_author(String title, String author) {
        this.bookTitle = title;
        this.bookAuthor = author;

        String requestBody = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);

        response = SerenityRest.given()
                .auth().preemptive().basic("user", "password") // Regular user authorization
                .contentType("application/json")
                .body(requestBody)
                .when().post(POST_ENDPOINT);
    }

    @Then("application should return status code 201 for create operation")
    public void application_should_return_status_code_201_for_create_operation() {
        assertThat(response.getStatusCode(), is(equalTo(201)));
    }

    @And("the response should include the book ID")
    public void the_response_should_include_the_book_id() {
        Integer bookId = response.jsonPath().getInt("id");
        assertThat(bookId, is(notNullValue()));
    }

    @And("the book details should match the input title and author")
    public void the_book_details_should_match_the_input_title_and_author() {
        String createdTitle = response.jsonPath().getString("title");
        String createdAuthor = response.jsonPath().getString("author");

        assertThat(createdTitle, equalTo(bookTitle));
        assertThat(createdAuthor, equalTo(bookAuthor));
    }
}
