package Steps.UIStepDefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.annotations.Steps;
import pages.LoginPage;

public class Hooks {

    @Steps
    LoginPage loginPage;

    @Before
    public void setUpDefaultLogin() {
        // Open the login page
        loginPage.openAt("/customer/account/login");

        // Perform login with default test credentials
        loginPage.login("ym@gmail.com", "Ymeka2000");
    }
}
