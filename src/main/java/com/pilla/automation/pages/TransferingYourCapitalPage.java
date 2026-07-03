package com.pilla.automation.pages;
import com.pilla.automation.pages.BasePage;
import com.pilla.automation.pages.ConclusionPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class TransferingYourCapitalPage extends BasePage {

    private WebElement transferActionNoButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected TransferingYourCapitalPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        intializeElements();
    }

    private void intializeElements() {
        String transferActionNoLocator = locators.get("transfering-your-capital-no");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
        transferActionNoButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                transferActionNoLocator)));
    }

    public ConclusionPage clickCTransferActionLocatorButton() {
        transferActionNoButton.click();
        return new ConclusionPage(driver, locators);
    }
}
