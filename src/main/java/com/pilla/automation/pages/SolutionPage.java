package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class SolutionPage extends BasePage {

    private WebElement pillarSolution;
    private WebElement continueButton;
    private WebElement fzSolution;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected SolutionPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        String pillarSolutionLocator = locators.get("solution-pillar");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        pillarSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                pillarSolutionLocator)));

        String continueButtonLocator = locators.get("solution-continue-button");
        continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(continueButtonLocator)));

        String fzSolutionLocator = locators.get("solution-fz");
        fzSolution = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                fzSolutionLocator)));
    }

    public SolutionPage clickPillarSolution() {
        pillarSolution.click();
        return this;
    }

    public SolutionPage clickFzSolution() {
        fzSolution.click();
        return this;
    }
    public BeforeStartPage clickContinueButton() {
        continueButton.click();
        return new BeforeStartPage(driver, locators);
    }
    public BeforeStartPageFz clickContinueButtonFz() {
        continueButton.click();
        return new BeforeStartPageFz(driver, locators);
    }
}
