
package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class PersonalSituationPageFz extends BasePage {


    private WebElement ahvNumber;
    private WebElement dateOfBirth;
    private WebElement dateOfBirthSelect;
    private WebElement civilStatus;
    private WebElement civilStatusSelect;
    private WebElement childrenSelect;
    private WebElement continueButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected PersonalSituationPageFz(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {

        String ahvNumberLocator = locators.get("personal-situation-ahv-number");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
        ahvNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(ahvNumberLocator)));

        String dateOfBirthLocator = locators.get("personal-situation-date-of-birth");
        dateOfBirth = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(dateOfBirthLocator)));
    }


    public PersonalSituationPageFz setAhvNumber(String number) {
        ahvNumber.sendKeys(number);
        return this;
    }

    public PersonalSituationPageFz setDateOfBirth() {
        dateOfBirth.click();
        String dateOfBirthSelectLocator = locators.get("personal-situation-date-of-birth-select");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
        dateOfBirthSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                dateOfBirthSelectLocator)));
        dateOfBirthSelect.click();

        String civilStatusLocator = locators.get("personal-situation-civil-status");
        civilStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(civilStatusLocator)));
        return this;
    }

    public PersonalSituationPageFz setCivilStatus() {
        civilStatus.click();

        String civilStatusSelectLocator = locators.get("personal-situation-single");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
        civilStatusSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                civilStatusSelectLocator)));
        civilStatusSelect.click();


        String continueButtonLocator = locators.get("personal-situation-continue-button");
        continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(continueButtonLocator)));
        return this;
    }



    public ChooseFzSolutionPage clickContinueButton() {
        continueButton.click();
        return new ChooseFzSolutionPage(driver, locators);
    }
}
