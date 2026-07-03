package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class BeforeStartPage extends BasePage {

    private WebElement beforeStartButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected BeforeStartPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        intializeElements();
    }

    private void intializeElements() {
        String beforeStartButtonLocator = locators.get("before-start-start-button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        beforeStartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                beforeStartButtonLocator)));
    }

    public PersonalSituationPage clickBeforeStartButton() {
        beforeStartButton.click();
        return new PersonalSituationPage(driver, locators);
    }
}
