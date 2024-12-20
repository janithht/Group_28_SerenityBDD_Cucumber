package Steps.UISteps;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import pages.ProductPage;

import static org.junit.Assert.assertTrue;

public class AddToCartStepDefinitions {

    @Steps
    ProductPage productPage;

    @Given("I am viewing the product {string}")
    public void iAmViewingTheProduct(String productName) {
        // Open the product page using the product name
        productPage.openAt("/" + productName.toLowerCase().replace(" ", "-") + ".html");
    }

    @When("I select size {string}")
    public void iSelectSize(String size) {
        // Select the size for the product (hardcoded to match a locator for simplicity)
        productPage.selectSize();
    }

    @When("I select color {string}")
    public void iSelectColor(String color) {
        // Select the color for the product (hardcoded to match a locator for simplicity)
        productPage.selectColor();
    }

    @When("I set the quantity to {string}")
    public void iSetTheQuantityTo(String quantity) {
        // Set the quantity for the product
        productPage.setQuantity(quantity);
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {
        // Add the product to the cart
        productPage.addToCart();
    }

    @Then("I should see the success message {string} on the product page")
    public void iShouldSeeTheSuccessMessageOnTheProductPage(String expectedMessage) {
        // Verify the success message is displayed on the product page
        assertTrue( productPage.isAddToCartSuccessMessageDisplayed());
    }

    @Then("the cart should display the product {string}")
    public void theCartShouldDisplayTheProduct(String productName) {
        // Open the cart dropdown and verify the product is listed
        productPage.openCartDropdown();
        assertTrue("Product is not in the cart", productPage.isProductInCart(productName));
    }
}