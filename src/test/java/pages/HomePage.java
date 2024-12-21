package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.concurrent.TimeUnit;

public class HomePage extends PageObject {

    private final By SEARCH_BAR = By.id("search");
    private final By PRODUCT_RESULTS = By.cssSelector(".product-item");
    private final By PRODUCT_RESULTS_NAMES = By.cssSelector(".product-item-name");
    private final By SEARCH_RESULTS_MESSAGE = By.cssSelector(".page-title");
    private final By NO_RESULTS_MESSAGE = By.cssSelector(".message.notice");

    public void openHomePage() {
        open();
    }

    public void searchForProduct(String productName) {
        $(SEARCH_BAR).type(productName);
        $(SEARCH_BAR).sendKeys(Keys.ENTER);
    }

    public boolean isSearchResultMessageDisplayed(String message) {
        return $(SEARCH_RESULTS_MESSAGE).getText().contains(message);
    }

    public boolean isProductListDisplayed() {
        waitForCondition().until(driver -> !findAll(PRODUCT_RESULTS).isEmpty());
        return $(PRODUCT_RESULTS).isVisible();
    }

    public boolean isProductListContainProductName(String productName) {
        return findAll(PRODUCT_RESULTS_NAMES).stream().anyMatch(element -> element.getText().toLowerCase().contains(productName.toLowerCase()));
    }

    public boolean isNoResultsMessageDisplayed() {
        return $(NO_RESULTS_MESSAGE).withTimeoutOf(5, TimeUnit.SECONDS).isVisible();
    }
}
