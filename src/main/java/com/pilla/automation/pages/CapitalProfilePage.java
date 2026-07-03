package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static java.sql.DriverManager.getDriver;

public class CapitalProfilePage extends BasePage {
    private WebElement capitalSolution;
    private WebElement confirmButton;
    private WebElement selectStrategyButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected CapitalProfilePage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));

        String confirmButtonLocator = locators.get("product-selection-confirm");
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(confirmButtonLocator)));

        String capitalSolutionLocator = locators.get("product-selection-capital-solution");
        capitalSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(capitalSolutionLocator)));

        String selectStrategyButtonLocator = locators.get("product-selection-strategy-button");
        selectStrategyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectStrategyButtonLocator)));
    }

    public CapitalProfilePage clickCapitalSolution() {
        capitalSolution.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        return this;
    }

    public CapitalProfilePage clickConfirmButton() {
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

    // In CapitalProfilePage.java
    public String getCapitalProfileDescriptionText() {
        // Assuming the text is in a TextView with a specific resource-id
        return getDriver().findElement((SearchContext) By.id("capitalProfileDescriptionTextView")).getText();
    }

    private By getDriver() {
        return null;
    }

}