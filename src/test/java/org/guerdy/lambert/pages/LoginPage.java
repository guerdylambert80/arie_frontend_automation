package org.guerdy.lambert.pages;

import org.guerdy.lambert.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.guerdy.lambert.utils.LoginElementPage.*;

public class LoginPage extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public DashboardPage Login_with_right_emailNpassword(String email, String password) throws InterruptedException {

        try {
            GetSignInBtn().click();

            GetEmailInputField().sendKeys(email);
            logger.debug("Email: {} entered successfully", email);
            GetPasswordInputField().sendKeys(password);
            logger.debug("Password entered entered successfully (length: {})", password.length());

            WebElement loginBtn = GetLoginButton();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // This clicks the element even if it's covered
            js.executeScript("arguments[0].click();", loginBtn);
            logger.debug("Login button clicked successfully");
            // Wait for successful login by checking URL change to dashboard
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
            System.out.println("URL: " + driver.getCurrentUrl());
            //wait.until(ExpectedConditions.urlContains("/dashboard"));
            wait.until(ExpectedConditions.urlContains("https://arieplatform.com/"));
            logger.debug("User, {}, logged in successful", email);
        } catch (Exception e) {
            logger.error("Error occurred during login: {}", e.getMessage());
            throw e;
            //e.printStackTrace();
        }

        return new DashboardPage();
    }

    public void WaitUntilElementToBeClickable(WebElement elem) {
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, duration);
        System.out.println("%%%%%%% WaitUntilElementToBeClickable B4 GetRegisterNowLink() %%%%%%% " );
        wait.until(ExpectedConditions.elementToBeClickable(elem));

        System.out.println("%%%%%%% INSIDE LoginPage.RegisterUser() %%%%%%% WaitUntilElementToBeClickable" );

        //WebElement signinBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loginLocatorsProp.getProperty("signin_link"))));
    }
    public void WaitUntilElementToBeVisible(WebElement elem) {
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, duration);
        System.out.println("%%%%%%% WaitUntilElementToBeClickable B4 GetRegisterNowLink() %%%%%%% " );
        //wait.until(ExpectedConditions.visibilityOf(elem));
        //wait.until(ExpectedConditions.textToBePresentInElement(elem, elem.getText()));

        //wait.until(ExpectedConditions.elementToBeClickable(elem));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Please enter a valid email address.']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='User not found']")));
        System.out.println(elem.getText());
        //System.out.println("%%%%%%% INSIDE LoginPage.RegisterUser() %%%%%%% WaitUntilElementToBeClickable" );

        //WebElement signinBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loginLocatorsProp.getProperty("signin_link"))));
    }

}
