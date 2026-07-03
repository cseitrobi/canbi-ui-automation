package com.pilla.automation;

import com.pilla.automation.base.AllureTestWatcher;
import com.pilla.automation.base.BaseTest;
import com.pilla.automation.pages.*;
import com.pilla.automation.util.AhvGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class validating the complete Conservative user onboarding flow in the Pilla application.
 * This class breaks the E2E flow into a sequence of dependent tests for clarity,
 * maintainability, and robust reporting. Assertions are performed by checking page source content.
 *
 * @see <a href="https://testng.org/doc/documentation-main.html#dependent-methods">TestNG Dependent Methods</a>
 */
@Listeners(AllureTestWatcher.class)
@Epic("User Onboarding")
@Feature("Dynamic Onboarding Flow")
@Story("As a new user, I want to register, define my profile as Dynamic, select a product, and complete the onboarding process.")
public class DynamicRiskOnboardingFlowTest extends BaseTest {

    public static final String OTP_CODE = "0000";
    // --- Test Data ---
    private static final String PASSWORD = "Test@1234";
    private static final String AHV_NUMBER = AhvGenerator.generateAhv();
    // --- Page Objects - Initialized as the flow progresses ---
    private WelcomePage welcomePage;
    private RegisterPage registerPage;
    private PasswordCreatePage passwordCreatePage;
    private LoginPage loginPage;
    private SolutionPage solutionPage;
    private BeforeStartPage beforeStartPage;
    private PersonalSituationPage personalSituationPage;
    private InvestmentProfilePage investmentProfilePage;
    private InvestmentProfileSelectionPage investmentProfileSelectionPage;
    private ChooseFzSolutionPage chooseFzSolutionPage;
    private TransferingYourCapitalPage transferingYourCapitalPage;
    private DynamicProfilePage dynamicProfilePage;
    private DisclaimerTextPage disclaimerTextPage;
    private ConclusionPage conclusionPage;
    private CongratulationsPage congratulationsPage;

    /**
     * Sets up the test class by initializing the driver and the first page.
     */
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

    @Test(priority = 3, dependsOnMethods = "testPasswordCreationAndLogin", description = "Select a solution and navigate through the pre-check page.")
    @Description("Chooses the 'Pillar' solution and proceeds past the 'Before you start' information screen.")
    public void testSolutionSelectionAndPreCheck() {
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

        Allure.step(
                "Proceed from the 'Before you start' page", () -> {
                    personalSituationPage = beforeStartPage.clickBeforeStartButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Personal situation"),
                            "Text 'Personal situation' not found on the personal situation page."
                    );
                }
        );
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
    }

    @Test(priority = 5, dependsOnMethods = "testFillPersonalAndInvestmentProfile", description = "Select the 'Dynamic' profile and a corresponding product.")
    @Description("Overrides the determined profile to select 'Dynamic', then chooses the 'Dynamic' solution product.")
    public void testDynamicProfileAndProductSelection() throws InterruptedException{
        Allure.step(
                "Change the investment profile to 'Dynamic'", () -> {
                    investmentProfileSelectionPage = investmentProfilePage.clickSelectDifferentInvestmentProfileButton();
                    investmentProfileSelectionPage.clickDynamicProfile();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("The dynamic risk profile corresponds to a strategy targeting higher price and/or foreign exchange gains rather than regular income."),
                            "Text 'The dynamic risk profile corresponds to a strategy targeting higher price and/or foreign exchange gains rather than regular income' not found on screen."
                    );
                }
        );

        Allure.step(
                "Confirm the growth-risk profile and select the 'Dynamic' product", () -> {
                    dynamicProfilePage = investmentProfileSelectionPage.clickDynamicConfirmButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("The following range of products is available to you"),
                            "Product range text not found on page."
                    );

                    transferingYourCapitalPage = dynamicProfilePage
                            .clickDynamicSolution()
                            .clickConfirmButton()
                            .clickSelectStrategyFz();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Would you like Liberty to take care of recovering your 3a pension assets from your previous institution?"),
                            "Text 'Would you like Liberty to take care of recovering your pension assets from your previous institution?' not found on the final conclusion page."
                    );
                }
        );
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


    @Test(priority = 7, dependsOnMethods = "testDynamicProfileAndProductSelection", description = "Complete final details, upload documents, and agree to terms.")
    @Description("Fills in final address details, performs document upload, and accepts the contract to finish onboarding.")
    public void testOnboardingConclusionAndDocumentUpload() throws InterruptedException {
        Allure.step(
                "Fill in final personal details", () -> {
                    conclusionPage.setStreet("Bahnhofstrasse")
                            .setStreetNumber("1")
                            .setPostalCode("8610")
                            .setTown("Uster")
                            .setNationality()
                            .clickContinueButton();
                    Thread.sleep(5000);
                    conclusionPage.clickAcceptButton();
                    conclusionPage.clickContinueButtonAfterAccept();
                    conclusionPage.uploadDocument();
                    conclusionPage.contractConsent();
                    Thread.sleep(20000);
                    congratulationsPage = conclusionPage.setOtpCode(OTP_CODE);
                    Thread.sleep(20000);
                }
        );
    }

    @Test(priority = 7, dependsOnMethods = "testOnboardingConclusionAndDocumentUpload", description = "Congratulations page, your opening application is complete.")
    @Description("Congratulations page, your opening application is complete.")
    public void testCongratulationPageAndNavigateToDashboard() throws InterruptedException {
        Allure.step(
                "Congratulations page is visible",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Congratulations, your opening application is complete!"),
                        "Congratulations page, your opening application is complete. is not visible."
                )
        );
        DashboardPage dashboardPage = congratulationsPage.clickDashboardButton();

        Allure.step(
                "Dashboard page is visible, onboarding successful",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Welcome"),
                        "Dashboard page is not visible."
                )
        );
        Allure.step(
                "Total assets is visible, onboarding successful",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Total assets pillar 3a"),
                        "Total assets is not visible."
                )
        );
        Allure.step(
                "Your portfolio will now be set up in the background, which may take up to 24 hours. is visible, onboarding successful",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Your portfolio will now be set up in the background, which may take up to 24 hours."),
                        "Your portfolio will now be set up in the background, which may take up to 24 hours is not visible."
                )
        );
    }
}
