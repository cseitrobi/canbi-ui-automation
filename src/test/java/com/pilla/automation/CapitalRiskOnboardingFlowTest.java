package com.pilla.automation;

import com.pilla.automation.base.AllureTestWatcher;
import com.pilla.automation.base.BaseTest;
import com.pilla.automation.pages.*;
import com.pilla.automation.util.AhvGenerator;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(AllureTestWatcher.class)
@Epic("User Onboarding")
@Feature("Capital Onboarding Flow")
@Story("Complete user onboarding flow including conclusion and document upload")
public class CapitalRiskOnboardingFlowTest extends BaseTest {

    public static final String OTP_CODE = "0000";
    private static final String PASSWORD = "Test@1234";
    private static final String AHV_NUMBER = AhvGenerator.generateAhv();

    private WelcomePage welcomePage;
    private RegisterPage registerPage;
    private PasswordCreatePage passwordCreatePage;
    private LoginPage loginPage;
    private SolutionPage solutionPage;
    private BeforeStartPage beforeStartPage;
    private PersonalSituationPage personalSituationPage;
    private InvestmentProfilePage investmentProfilePage;
    private InvestmentProfileSelectionPage investmentProfileSelectionPage;
    private CapitalProfilePage capitalProfilePage;
    private TransferingYourCapitalPage transferingYourCapitalPage;
    private ChooseFzSolutionPage chooseFzSolutionPage;
    private ConclusionPage conclusionPage;
    private CongratulationsPage congratulationsPage;

    @BeforeClass
    public void setup() throws InterruptedException {
        Allure.suite(getDeviceName());

        ConsentPage consentPage = new ConsentPage(getDriver(), getLocators());
        boolean permissionHandledBeforeConsent =
                consentPage.clickPermissionAllowIfPresent(Duration.ofSeconds(15));

        boolean consentHandled = consentPage.clickAcceptAllIfPresent(Duration.ofSeconds(10));
        if (consentHandled) {
            consentPage.switchDeToEnglishIfPresent();
            consentPage.clickSkipIfPresent(Duration.ofSeconds(5));
            welcomePage = new WelcomePage(getDriver(), getLocators());
            registerPage = welcomePage.clickRegistrationButton();
        }

        if (!permissionHandledBeforeConsent) {
            consentPage.clickPermissionAllowIfPresent(Duration.ofSeconds(5));
        }
    }

    @Test(priority = 1, description = "Navigate from Welcome screen and register a new user.")
    @Description("This test verifies the initial welcome screen and completes the user registration form.")
    public void testUserRegistration() {
        Allure.step(
                "Verify the registration form is open", () ->
                        Assert.assertTrue(
                                getDriver().getPageSource().contains("First name"),
                                "Registration screen text not found in page source."
                        )
        );

        Allure.step(
                "Navigate to the registration page and fill in user details", () -> {
                    if (registerPage == null) {
                        welcomePage = new WelcomePage(getDriver(), getLocators());
                        registerPage = welcomePage.clickRegistrationButton();
                    }
                    passwordCreatePage = registerPage
                            .clickSalutationRadioButton()
                            .setFirstName(faker.name().firstName())
                            .setLastName(faker.name().lastName())
                            .setPlaceOfResidence()
                            .setEmail()
                            .setPhoneCountryCode()
                            .setMobileNumber(faker.number().digits(9))
                            .clickRegisterButton();

                    // Assert that we have successfully moved to the password creation screen
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Confirm your password"),
                            "Text 'Confirm your password' not found on password creation page."
                    );
                }
        );
    }

    @Test(priority = 2, dependsOnMethods = "testUserRegistration", description = "Create a password and log in for the first time.")
    @Description("Sets a password for the newly registered user and performs the initial login.")
    public void testPasswordCreationAndLogin() {
        Allure.step(
                "Create and confirm the password", () ->
                        loginPage = passwordCreatePage
                                .setPassword(PASSWORD)
                                .setConfirmPassword(PASSWORD)
                                .clickRegisterButton()
        );

        Allure.step(
                "Log in with the newly created account credentials", () -> {
                    solutionPage = loginPage
                            .setEmail()
                            .setPassword(PASSWORD)
                            .clickLoginButton();

                    // Assert successful login by checking for an element on the next page
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Choose the solution that suits you"),
                            "Text 'Choose the solution' not found on the solution page."
                    );
                }
        );
    }

    @Test(priority = 3, dependsOnMethods = "testPasswordCreationAndLogin",  description = "Select a solution and navigate through the pre-check page.")
    @Description("Chooses the 'Pillar' solution and proceeds past the 'Before you start' information screen.")
    public void testSolutionSelectionAndPreCheck() throws InterruptedException {
        Allure.step(
                "Select the 'Pillar' solution and continue", () -> {
        beforeStartPage = solutionPage
                .clickPillarSolution()
                .clickContinueButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Before you start"),
                            "Text 'Before you start' not found on the pre-check page."
                    );
                }
        );
        personalSituationPage = beforeStartPage.clickBeforeStartButton();
    }

    @Test(priority = 4, dependsOnMethods = "testSolutionSelectionAndPreCheck", description = "Fill in personal and investment profile information.")
    @Description("Enters user's personal details (AHV, DOB, etc.) and determines the initial investment profile.")
    public void testFillPersonalAndInvestmentProfile() throws InterruptedException {
        Allure.step(
                "Fill in personal situation details", () -> {
        chooseFzSolutionPage = personalSituationPage
                .setPensionMember()
                .setAhvNumber(AHV_NUMBER)
                .setDateOfBirth()
                .setCivilStatus()
                .setChildren()
                .clickContinueButtonToChooseFz();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Selection of suitable investment solutions, higher earnings potential"),
                            "Text 'Selection of suitable investment solutions, higher earnings potential' not found on page."
                    );
                }
        );

        Allure.step(
                "Choose FZ asset", () -> {
        investmentProfilePage = chooseFzSolutionPage
                .clickChooseFzAssetButtonLocator();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Determining an investment profile"),
                            "Text 'Determining an investment profile' not found on page."
                    );
                }
        );

        Allure.step(
                "Fill in investment profile questionnaire", () -> {
        investmentProfilePage
                .clickHorizonDropdown()
                .setHorizonAge()
                .setExpenses()
                .setIncome()
                .setRiskProfile()
                .clickContinueButton();
                    Assert.assertTrue(
                            investmentProfilePage.waitForProfileDetermined(),
                            "Text 'Risk profile determined' not found on screen."
                    );
                }
        );

        Thread.sleep(20000); // Wait for risk profile determination
    }

    @Test(priority = 5, dependsOnMethods = "testFillPersonalAndInvestmentProfile", description = "Select the 'Capital Gains' profile and a corresponding product.")
    @Description("Overrides the determined profile to select 'Balanced', then chooses the 'Account' solution product.")
    public void testCapitalProfileAndProductSelection() throws InterruptedException {
        Allure.step(
                "Change the investment profile to 'Capital Gains'", () -> {
        investmentProfileSelectionPage = investmentProfilePage.clickSelectDifferentInvestmentProfileButton();
        capitalProfilePage = investmentProfileSelectionPage.clickCapitalProfile().clickCapitalConfirmButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("The following range of products is available to you"),
                            "Product range text not found on page."
                    );
                }
        );
    }

    @Test(priority = 6, dependsOnMethods = "testCapitalProfileAndProductSelection")
    public void testCapitalPage() {
        transferingYourCapitalPage = capitalProfilePage
                .clickCapitalSolution()
                .clickConfirmButton()
                .clickSelectStrategyFz();

        // Wait for a key element to ensure page loaded
        Allure.step(
                "Choose dont transfer FZ asset", () -> {
                    conclusionPage = transferingYourCapitalPage

                            .clickCTransferActionLocatorButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Conclusion"),
                            "Text 'Conclusion' not found on page."
                    );
                }
        );
    }

    @Test(priority = 7, dependsOnMethods = "testCapitalPage")
    public void testOnboardingConclusionAndDocumentUpload() throws InterruptedException {
        conclusionPage.setStreet("Bahnhofstrasse")
                .setStreetNumber("1")
                .setPostalCode("8610")
                .setTown("Uster")
                .setNationality()
                .clickContinueButton();

        conclusionPage.clickAcceptButton();
        conclusionPage.clickContinueButtonAfterAccept();
        conclusionPage.uploadDocument();
        conclusionPage.contractConsent();
        Thread.sleep(20000);
        congratulationsPage = conclusionPage.setOtpCode(OTP_CODE);
        Thread.sleep(20000);
    }

    @Test(priority = 8, dependsOnMethods = "testOnboardingConclusionAndDocumentUpload")
    public void testCongratulationPageAndNavigateToDashboard() {
        Allure.step(
                "Congratulations page is visible",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Congratulations, your opening application is complete!"),
                        "Congratulations page, your opening application is complete. is not visible."
                )
        );
        DashboardPage dashboardPage = congratulationsPage.clickDashboardButton();
    }
}
