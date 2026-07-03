package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ConservativePortfolioPage extends BasePage {

    private WebElement conservativeSolution;
    private WebElement confirmButton;
    private WebElement selectStrategyButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected ConservativePortfolioPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));

        String confirmButtonLocator = locators.get("product-selection-confirm");
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(confirmButtonLocator)));

        String conservativeSolutionLocator = locators.get("product-selection-conservative-solution");
        conservativeSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(conservativeSolutionLocator)));

        String selectStrategyButtonLocator = locators.get("product-selection-strategy-button");
        selectStrategyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectStrategyButtonLocator)));
    }

    public ConservativePortfolioPage clickConservativeSolution() {
        conservativeSolution.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        return this;
    }

    public ConservativePortfolioPage clickConfirmButton() {
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
