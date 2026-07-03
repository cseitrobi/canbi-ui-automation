package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ThreeALowRiskPortfolioPage extends BasePage {

    private WebElement threeAlowRiskSolution;
    private WebElement confirmButton;
    private WebElement selectContinueButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected ThreeALowRiskPortfolioPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));

        String threeALowRiskSolutionLocator = locators.get("product-selection-lowrisk-solution");
        threeAlowRiskSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(threeALowRiskSolutionLocator)));

        String confirmButtonLocator = locators.get("product-selection-confirm");
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(confirmButtonLocator)));

        String selectStrategyButtonLocator = locators.get("product-selection-continue-button");
        selectContinueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(selectStrategyButtonLocator)));
    }

    public ThreeALowRiskPortfolioPage clickThreeALowRiskSolution() {
        threeAlowRiskSolution.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        return this;
    }

    public ThreeALowRiskPortfolioPage clickConfirmButton() {
        confirmButton.click();
        return this;
    }

    public ConclusionPage clickSelectStrategy() {
        selectContinueButton.click();
        return new ConclusionPage(driver, locators);
    }
    public TransferingYourCapitalPage clickContinueButtonFz() {
        selectContinueButton.click();
        return new TransferingYourCapitalPage(driver, locators);
    }
}
