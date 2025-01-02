package Steps.APISteps;

import Steps.api.BookAPI;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;

public class GetAPISteps {
    BookAPI bookAPI = BookAPI.getInstance();
    private Response response;

    @Given("User is authorized as an admin to get books")
    public void user_is_authorized_to_get_all_books_as_an_admin() {
        //Handled by Request Factory
    }

    @Given("User is authorized as a regular user to get books")
    public void user_is_authorized_to_get_all_books_as_a_regular_user() {
        //Handled by Request Factory
    }

    @When("the admin user sends a GET all books request")
    public void the_admin_user_sends_a_get_all_books_request() {
        response = bookAPI.getBooksByAdmin();
    }

    @When("the regular user sends a GET all books request")
    public void the_regular_user_sends_a_get_all_books_request() {
        response = bookAPI.getBooksByUser();
    }

    @When("the admin user sends a GET request with ID {int}")
    public void the_admin_user_sends_a_get_request_with_book_ID(Integer bookId) {
        response = bookAPI.getBookByIdAdmin(bookId);
    }

    @When("the regular user sends a GET request with ID {int}")
    public void the_regular_user_sends_a_get_request_with_book_ID(Integer bookId) {
        response = bookAPI.getBookByIdUser(bookId);
    }


    @Then("the status of the response should be {int}")
    public void the_status_of_the_response_should_be(Integer statusCode) {
        assertThat(response.statusCode(), is(equalTo(statusCode)));
    }


    @And("the response should contain list of available books")
    public void the_response_should_contain_a_list_of_available_books() {
        response.then().body("$", not(empty()));
        response.then().body("size()", equalTo(8));
        //System.out.println(response.body().asString());
        // Check books in the response
        response.then().body("[0].title", equalTo("To Kill a Mockingbird"));
        response.then().body("[0].author", equalTo("Harper Lee"));
        response.then().body("[1].title", equalTo("1984"));
        response.then().body("[1].author", equalTo("George Orwell"));
        response.then().body("[2].title", equalTo("The Great Gatsby"));
        response.then().body("[2].author", equalTo("F. Scott Fitzgerald"));
        response.then().body("[3].title", equalTo("Pride and Prejudice"));
        response.then().body("[3].author", equalTo("Jane Austen"));
        response.then().body("[4].title", equalTo("The Catcher in the Rye"));
        response.then().body("[4].author", equalTo("J.D. Salinger"));
        response.then().body("[5].title", equalTo("The Hobbit"));
        response.then().body("[5].author", equalTo("J.R.R. Tolkien"));
        response.then().body("[6].title", equalTo("The Lord of the Rings"));
        response.then().body("[6].author", equalTo(""));
        response.then().body("[7].title", equalTo( "" ));
        response.then().body("[7].author", equalTo("William Shakespeare"));

    }

    @And("the response should contain the details of the book")
    public void the_response_should_contain_the_details_of_the_book() {
        response.then().body("title", equalTo("1984"));
        response.then().body("author", equalTo("George Orwell"));
    }

    @And("the response should contain an error message \"Book not found\"")
    public void the_response_should_contain_an_error_message() {
        assertThat(response.body().asString(), containsString("Book not found"));
    }

}
