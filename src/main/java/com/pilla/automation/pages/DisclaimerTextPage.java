package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class DisclaimerTextPage extends BasePage {
    private WebElement disclaimerCheckbox;
    private WebElement disclaimerButton;
    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected DisclaimerTextPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));

        String disclaimerCheckboxLocator= locators.get("disclaimer-checkbox");
        disclaimerCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(disclaimerCheckboxLocator)));

        String disclaimerAcceptAndContinueLocator = locators.get("diclaimer-accept-and-continue");
        disclaimerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(disclaimerAcceptAndContinueLocator)));

    }

    public DisclaimerTextPage clickDisclaimerCheckbox() {
        disclaimerCheckbox.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        return this;
    }

    public DynamicProfilePage clickDisclaimerButton() {
        disclaimerButton.click();
        return new DynamicProfilePage(driver,locators);
    }

    public GrowthProfilePage clickGrowthDisclaimerButton() {
        disclaimerButton.click();
        return new GrowthProfilePage(driver,locators);
    }

    public CapitalProfilePage clickCapitalDisclaimerButton() {
        disclaimerButton.click();
        return new CapitalProfilePage(driver,locators);
    }

}
//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.RectView