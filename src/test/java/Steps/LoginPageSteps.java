package Steps;

import UIPages.LoginPage;
import io.cucumber.java.en.*;

public class LoginPageSteps {

    private LoginPage loginPage;

    @Given("User is on the login page")
    public void user_is_on_the_login_page() {
        loginPage.open();
    }

    @When("User login with username and password")
    public void user_login_with_username_and_password() {
        loginPage.doLogin();
    }

    @Then("User should be logged in and see the account page")
    public void user_should_be_logged_in_and_see_the_account_page() {
        loginPage.accountPageIsVisible();
    }
}
