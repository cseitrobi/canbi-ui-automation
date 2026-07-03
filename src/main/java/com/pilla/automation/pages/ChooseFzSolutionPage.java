package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ChooseFzSolutionPage extends BasePage {

    private WebElement chooseFzAssetButton;
    private WebElement chooseFzAccountSolutionButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected ChooseFzSolutionPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        intializeElements();
    }

    private void intializeElements() {
        String chooseFzAssetButtonLocator = locators.get("choose-fz-asset");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        chooseFzAssetButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                chooseFzAssetButtonLocator)));
        String chooseFzAccountSolutionButtonLocator = locators.get("choose-fz-transfer");
        chooseFzAccountSolutionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                chooseFzAccountSolutionButtonLocator)));
    }

    public InvestmentProfilePage clickChooseFzAssetButtonLocator() {
        chooseFzAssetButton.click();
        return new InvestmentProfilePage(driver, locators);
    }

    public AccountSolutionProfilePage clickChooseFzAccountSolutionButtonLocator() {
        chooseFzAccountSolutionButton.click();
        return new AccountSolutionProfilePage(driver, locators);
    }

    public ThreeAAccountSolutionProfilePage clickChooseThreeAFzAccountSolutionButtonLocator() {
        chooseFzAccountSolutionButton.click();
        return new ThreeAAccountSolutionProfilePage(driver, locators);
    }
}
