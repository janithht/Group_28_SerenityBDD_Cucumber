package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;

public class ProductPage extends PageObject {

    // Locators
    private final By PRODUCT_NAME = By.xpath("//a[@title='Radiant Tee']");
    private final By SIZE = By.xpath("//div[@id='option-label-size-143-item-167']");
    private final By COLOR = By.xpath("//div[@id='option-label-color-93-item-50']");
    private final By QUANTITY = By.xpath("//input[@name='qty']");
    private final By ADD_TO_CART = By.xpath("//button[@title='Add to Cart']");
    private final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class, 'message-success')]/div"); // Updated to reflect inline success message
    private final By CART_ICON = By.cssSelector(".minicart-wrapper"); // Cart icon locator
    private final By CART_PRODUCT_NAME = By.cssSelector(".product-item-name"); // Product name in cart

    /**
     * Selects size for the product.
     */
    public void selectSize() {
        $(SIZE).waitUntilClickable().click();
    }

    /**
     * Selects color for the product.
     */
    public void selectColor() {
        $(COLOR).waitUntilClickable().click();
    }

    /**
     * Sets the quantity for the product.
     *
     * @param quantity Desired quantity of the product.
     */
    public void setQuantity(String quantity) {
        $(QUANTITY).waitUntilVisible().clear(); // Clear any prefilled value
        $(QUANTITY).type(quantity); // Enter the quantity
    }

    /**
     * Adds the product to the cart.
     */
    public void addToCart() {
        $(ADD_TO_CART).waitUntilClickable().click();
    }

    /**
     * Verifies the success message displayed after adding the product to the cart.
     *
     * @return true if the success message is displayed, false otherwise.
     */
    public boolean isAddToCartSuccessMessageDisplayed() {
        return $(SUCCESS_MESSAGE).containsText("You added Radiant Tee to your ");
    }

    /**
     * Opens the cart to verify the product.
     */
    public void openCartDropdown() {
        $(CART_ICON).click(); // Click the cart icon
        waitForTextToAppear("My Cart"); // Wait for the cart dropdown to load
    }

    /**
     * Checks if the product is visible in the cart.
     *
     * @param productName Name of the product to verify.
     * @return true if the product is in the cart, false otherwise.
     */
    public boolean isProductInCart(String productName) {
        return $(CART_PRODUCT_NAME).getText().contains(productName);
    }
}
