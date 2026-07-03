package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class LowRiskPortfolioPage extends BasePage {

    private WebElement lowRiskSolution;
    private WebElement confirmButton;
    private WebElement selectStrategyButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected LowRiskPortfolioPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));

        String lowRiskSolutionLocator = locators.get("product-selection-account-solution");
        lowRiskSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(lowRiskSolutionLocator)));

        String confirmButtonLocator = locators.get("product-selection-confirm");
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(confirmButtonLocator)));

        String selectStrategyButtonLocator = locators.get("product-selection-strategy-button");
        selectStrategyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectStrategyButtonLocator)));
    }

    public LowRiskPortfolioPage clickLowRiskSolution() {
        lowRiskSolution.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        return this;
    }

    public LowRiskPortfolioPage clickConfirmButton() {
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
