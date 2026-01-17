package org.guerdy.lambert.tests;

import org.guerdy.lambert.base.BaseTest;
import org.guerdy.lambert.pages.DashboardPage;
import org.guerdy.lambert.pages.LoginPage;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.guerdy.lambert.utils.LoginElementPage.getRightAppraiserEmail;
import static org.guerdy.lambert.utils.LoginElementPage.getRightAppraiserPassword;
import static org.guerdy.lambert.utils.extentreports.ExtentTestManager.startTest;

public class LoginTest extends BaseTest {
    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Test(enabled = true, description = "Login with right email and password"
        , dataProvider = "validCredentials")
    public void Login_with_right_emailNpassword(String email, String password) throws InterruptedException {

        logger.info("Starting Login process with right email and password");
        // startTest adds this test to the extent report
        startTest(this.getClass().getName(), "Login with right email and password");
        //dashboardPage=loginPage.Login_with_right_emailNpassword(getRightAppraiserEmail(),getRightAppraiserPassword());
        dashboardPage=loginPage.Login_with_right_emailNpassword(email, password);

    }

    @DataProvider(name = "validCredentials")
    public Object[][] getValidCredentials() {
        return new Object[][]{
                {"subscribed@1.com", "bias2024!"},
                {"3@3.com", "bias2024!"},
                {"5@5.com", "bias2024!"},
                {"free@1.com", "bias2024!"}
        };
    }

}


