package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ChooseFzAccountSolutionPage extends BasePage {

    private WebElement chooseFzAccountSolutionButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected ChooseFzAccountSolutionPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        intializeElements();
    }

    private void intializeElements() {
        String chooseFzAccountSolutionButtonLocator = locators.get("choose-fz-transfer");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        chooseFzAccountSolutionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                chooseFzAccountSolutionButtonLocator)));
    }

    public InvestmentProfilePage clickChooseFzAccountSolutionButtonLocator() {
        chooseFzAccountSolutionButton.click();
        return new InvestmentProfilePage(driver, locators);
    }
}

