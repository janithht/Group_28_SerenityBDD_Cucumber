package pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;

public class LoginPage extends PageObject {

    private final By USERNAME_FIELD = By.id("email");
    private final By PASSWORD_FIELD = By.id("pass");
    private final By LOGIN_BUTTON = By.id("send2");

    public void login(String username, String password) {
        $(USERNAME_FIELD).type(username);   // Enter username
        $(PASSWORD_FIELD).type(password);   // Enter password
        $(LOGIN_BUTTON).click();           // Click the login button
    }
}
