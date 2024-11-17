package UIPages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;

@DefaultUrl("https://the-internet.herokuapp.com/login")
public class LoginPage extends PageObject {

    public void doLogin(){
        $("#username").sendKeys("tomsmith");
        $("#password").sendKeys("SuperSecretPassword!");
        $(".radius").click();
    }

    public void accountPageIsVisible(){
        $("#flash.success").shouldBeVisible();
    }
}
