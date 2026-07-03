package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class PersonalSituationPage extends BasePage {

    private WebElement pensionMember;
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
    protected PersonalSituationPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        String pensionMemberLocator = locators.get("personal-situation-pension-member");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        pensionMember = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                pensionMemberLocator)));

        String ahvNumberLocator = locators.get("personal-situation-ahv-number");
        ahvNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(ahvNumberLocator)));

        String dateOfBirthLocator = locators.get("personal-situation-date-of-birth");
        dateOfBirth = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(dateOfBirthLocator)));
    }

    public PersonalSituationPage setPensionMember() {
        pensionMember.click();
        return this;
    }

    public PersonalSituationPage setAhvNumber(String number) {
        ahvNumber.sendKeys(number);
        return this;
    }

    public PersonalSituationPage setDateOfBirth() {
        dateOfBirth.click();
        String dateOfBirthSelectLocator = locators.get("personal-situation-date-of-birth-select");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        dateOfBirthSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                dateOfBirthSelectLocator)));
        dateOfBirthSelect.click();

        String civilStatusLocator = locators.get("personal-situation-civil-status");
        civilStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(civilStatusLocator)));
        return this;
    }

    public PersonalSituationPage setCivilStatus() {
        civilStatus.click();

        String civilStatusSelectLocator = locators.get("personal-situation-single");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        civilStatusSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                civilStatusSelectLocator)));
        civilStatusSelect.click();

        String childrenSelectLocator = locators.get("personal-situation-children");
        childrenSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(childrenSelectLocator)));

        String continueButtonLocator = locators.get("personal-situation-continue-button");
        continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(continueButtonLocator)));
        return this;
    }

    public PersonalSituationPage setChildren() {
        childrenSelect.click();
        return this;
    }

    public ChooseFzSolutionPage clickContinueButtonToChooseFz() {
        continueButton.click();
        return new ChooseFzSolutionPage(driver, locators);
    }

    public InvestmentProfilePage clickContinueButton() {
        continueButton.click();
        return new InvestmentProfilePage(driver, locators);
    }
}
