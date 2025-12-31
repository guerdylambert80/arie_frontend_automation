package org.guerdy.lambert.utils;

import org.guerdy.lambert.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginElementPage extends BaseTest {
    public static WebElement GetSignInBtn() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("signin_btn")));
    }
    public static WebElement GetEmailInputField() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("email_inputfield")));
        //return driver.findElement(By.cssSelector(loginLocatorsProp.getProperty("email_inputfield")));
    }

    public static WebElement GetPasswordInputField() {
        return driver.findElement(By.cssSelector(loginLocatorsProp.getProperty("password_inputfield")));
    }
    public static WebElement GetNotAValidEmailPopup() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("not_a_valid_email_popup")));
    }
    public static WebElement GetUserNotFound() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("user_not_found")));
    }
    public static String GetBadCredentialMessage() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("bad_credentials_msg"))).getText();
    }
    public static WebElement GetRememberMeCheckbox() {
        return driver.findElement(By.cssSelector(loginLocatorsProp.getProperty("rememberme_link")));
    }
    public static WebElement GetForgotPasswordLink() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("forgot_password_link")));
    }
    public static WebElement GetRegisterNowLink() {

        return driver.findElement(By.cssSelector(loginLocatorsProp.getProperty("register_now_link")));
    }
    public static WebElement GetLoginButton() {
        return driver.findElement(By.cssSelector(loginLocatorsProp.getProperty("login_btn")));
    }

    public static void EnterEmail() {
        GetEmailInputField().sendKeys(loginLocatorsProp.getProperty("email"));
        //GetEmailInputField().sendKeys(loginLocatorsProp.getProperty("email"));
    }

    public static void ClickSubmitButton() {
        GetLoginButton().click();
    }
    public static WebElement GetDashboardLink() {
        return driver.findElement(By.xpath(loginLocatorsProp.getProperty("dashboard_link")));
    }

    public static void ClickDashboardLink() {
        driver.findElement(By.xpath(loginLocatorsProp.getProperty("dashboard_link"))).click();
    }

    public static String getRightEmail() {
        return loginLocatorsProp.getProperty("appraiser_email");
        //rightEmail;
    }
    public static String getRightPassword() {
        return loginLocatorsProp.getProperty("appraiser_password");
        //rightPassword;
    }
    public static String getRightAppraiserEmail() {
        String em = configLocatorsProp.getProperty("appraiser_email");
        //String em = loginLocatorsProp.getProperty("appraiser_email");
        System.out.println("Email From: " + em);
        return em;   //configLocatorsProp.getProperty("appraiser_email");
        //rightAppraiserEmail;
    }
    public static String getRightAppraiserPassword() {
        return configLocatorsProp.getProperty("appraiser_password");
        //rightAppraiserPassword;
    }
//    public static String getWrongEmail() {
//        return wrongEmail;
//    }
//    public static String getWrongPassword() {
//        return wrongPassword;
//    }

    public static String getExpectedSuccessfulLoginUrl() {
        return "https://app.abiescreen.com/dashboard";
    }
    public static String getExpectedUnsuccessfulLoginUrl() {
        return "https://app.abiescreen.com/login";
    }
}
