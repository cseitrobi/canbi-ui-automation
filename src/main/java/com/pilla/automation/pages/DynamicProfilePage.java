
package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class DynamicProfilePage extends BasePage {
    private WebElement dynamicSolution;
    private WebElement confirmButton;
    private WebElement selectStrategyButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected DynamicProfilePage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));

        String confirmButtonLocator = locators.get("product-selection-confirm");
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(confirmButtonLocator)));

        String balancedSolutionLocator = locators.get("product-selection-dynamic-solution");
        dynamicSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(balancedSolutionLocator)));

        String selectStrategyButtonLocator = locators.get("product-selection-strategy-button");
        selectStrategyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectStrategyButtonLocator)));
    }

    public DynamicProfilePage clickDynamicSolution() {
        dynamicSolution.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        return this;
    }

    public DynamicProfilePage clickConfirmButton() {
        confirmButton.click();
        return this;
    }

    public ConclusionPage clickSelectStrategy() {
        selectStrategyButton.click();
        return new ConclusionPage(driver, locators);
    }

    public TransferingYourCapitalPage clickSelectStrategyFz() {
        selectStrategyButton.click();
        return new TransferingYourCapitalPage(driver, locators);
    }
}
