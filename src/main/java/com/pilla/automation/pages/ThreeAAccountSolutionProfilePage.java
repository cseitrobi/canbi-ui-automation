package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ThreeAAccountSolutionProfilePage extends BasePage {
    private WebElement selectContinueButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected ThreeAAccountSolutionProfilePage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        String selectContinueButtonLocator = locators.get("product-selection-continue-button");
        selectContinueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectContinueButtonLocator)));
    }
    public ConclusionPage clickSelectContinue() {
        selectContinueButton.click();
        return new ConclusionPage(driver, locators);
    }
    public TransferingYourCapitalPage clickSelectContinueFz() {
        selectContinueButton.click();
        return new TransferingYourCapitalPage(driver, locators);
    }
}
