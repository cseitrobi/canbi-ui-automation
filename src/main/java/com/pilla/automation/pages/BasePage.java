package com.pilla.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Base class for all page objects in the application.
 * This class provides common functionality and fields used by all page objects.
 * It follows the Page Object Model pattern to encapsulate page-specific behavior.
 */
public abstract class BasePage {

    /**
     * The Appium driver instance used to interact with the mobile application.
     */
    protected final AppiumDriver driver;

    /**
     * Map of locator identifiers to their XPath or other locator expressions.
     * This allows for centralized management of locators.
     */
    protected final Map<String, String> locators;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected BasePage(AppiumDriver driver, Map<String, String> locators) {
        this.driver = driver;
        this.locators = locators;
    }

    protected Optional<WebElement> waitForVisible(By locator, Duration timeout) {
        try {
            return Optional.of(new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator)));
        } catch (TimeoutException e) {
            return Optional.empty();
        }
    }

    protected Optional<WebElement> waitForClickable(By locator, Duration timeout) {
        try {
            return Optional.of(new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.elementToBeClickable(locator)));
        } catch (TimeoutException e) {
            return Optional.empty();
        }
    }

    public void scrollOneScreenDown(AppiumDriver driver) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        int screenWidth = driver.manage().window().getSize().getWidth();
        int screenHeight = driver.manage().window().getSize().getHeight();

        int startX = screenWidth / 2;
        int startY = (int) (screenHeight * 0.8); // Start near bottom
        int endY = (int) (screenHeight * 0.3);   // Swipe up to scroll down

        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }
}
