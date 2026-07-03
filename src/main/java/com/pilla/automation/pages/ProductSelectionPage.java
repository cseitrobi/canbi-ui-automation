package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ProductSelectionPage extends BasePage {

    private WebElement accountSolution;
    private WebElement confirmButton;
    private WebElement continueButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected ProductSelectionPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        String accountSolutionLocator = locators.get("product-selection-account-solution");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        accountSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                accountSolutionLocator)));

        String confirmButtonLocator = locators.get("product-selection-confirm");
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(confirmButtonLocator)));

        String continueButtonLocator = locators.get("product-selection-continue");
        continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(continueButtonLocator)));
    }

    public ProductSelectionPage clickAccountSolution() {
        accountSolution.click();
        return this;
    }

    public ProductSelectionPage clickConfirmButton() {
        confirmButton.click();
        scrollOneScreenDown(driver);
        return this;
    }

    public ConclusionPage clickContinueButton() {
        continueButton.click();
        return new ConclusionPage(driver, locators);
    }
}
