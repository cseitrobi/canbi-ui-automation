package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;

/**
 * Page object representing the Welcome page of the application.
 * This is typically the first page users see when opening the app.
 * It provides options to register or login to the application.
 */
public class ConsentPage extends BasePage {

    /**
     * Constructor for the WelcomePage.
     * Initializes the page elements after calling the parent constructor.
     *
     * @param driver The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    public ConsentPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
    }

    /**
     * Clicks the Accept All button only (does not create WelcomePage).
     * Use when you need to switch language on the next screen before using WelcomePage.
     */
    public void clickAcceptAllButtonOnly() {
        findAcceptAllButton(Duration.ofSeconds(30)).click();
    }

    public boolean clickPermissionAllowIfPresent(Duration timeout) {
        return waitForClickable(AppiumBy.xpath(locators.get("notification-permission-allow")), timeout)
                .map(button -> {
                    button.click();
                    return true;
                })
                .orElse(false);
    }

    public boolean clickAcceptAllIfPresent(Duration timeout) {
        return waitForClickable(AppiumBy.xpath(locators.get("consent-accept-all")), timeout)
                .or(() -> waitForClickable(AppiumBy.xpath(locators.get("consent-accept-all-en")), timeout))
                .map(button -> {
                    button.click();
                    return true;
                })
                .orElse(false);
    }

    /**
     * Clicks the Accept All button and navigates to the Welcome page.
     *
     * @return A new instance of the WelcomePage
     */
    public WelcomePage clickAcceptAllButton() {
        clickAcceptAllButtonOnly();
        return new WelcomePage(driver, locators);
    }

    /**
     * Clicks DE, then English (language switch). Registration is clicked by the test via WelcomePage.
     */
    public void switchDeToEnglishIfPresent() {
        waitForClickable(AppiumBy.accessibilityId("DE"), Duration.ofSeconds(5)).ifPresent(enClick -> {
            enClick.click();
            waitForClickable(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"English\")"),
                    Duration.ofSeconds(5)
            ).ifPresent(WebElement::click);
        });
    }

    /**
     * Clicks DE -> English -> Registration, then returns WelcomePage (caller may be on Register screen).
     */
    public WelcomePage clickAcceptAllButtonDeThenSelectEnglish() {
        switchDeToEnglishIfPresent();
        return new WelcomePage(driver, locators);
    }

    /**
     * Clicks Skip for tutorial page, then returns WelcomePage (caller may be on Register screen).
     */
    public boolean clickSkipIfPresent(Duration timeout) {
        return waitForClickable(AppiumBy.xpath(locators.get("skip-en")), timeout)
                .map(button -> {
                    button.click();
                    return true;
                })
                .orElse(false);
    }

    private WebElement findAcceptAllButton(Duration timeout) {
        return waitForClickable(AppiumBy.xpath(locators.get("consent-accept-all")), timeout)
                .or(() -> waitForClickable(AppiumBy.xpath(locators.get("consent-accept-all-en")), timeout))
                .orElseThrow(() -> new TimeoutException("Consent 'Accept All' button was not visible."));
    }
}
