package org.guerdy.lambert.pages;

import org.guerdy.lambert.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.guerdy.lambert.utils.DashboardElementPage.upload1004Form;
import static org.guerdy.lambert.utils.LoginElementPage.getRightAppraiserEmail;
import static org.guerdy.lambert.utils.LoginElementPage.getRightAppraiserPassword;

public class DashboardPage extends BaseTest {

    private static final Logger log = LogManager.getLogger(DashboardPage.class);

    DashboardPage dashboardPage;

    public void uploadFile() throws InterruptedException {

        try {
            LoginPage loginPage = new LoginPage();
            dashboardPage = loginPage.Login_with_right_emailNpassword(getRightAppraiserEmail(), getRightAppraiserPassword());

            log.debug("Waiting for the Upload button to be visible");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLocatorsProp.getProperty("upload_button"))));

            upload1004Form();
        } catch (Exception e) {
            log.error("File upload failed: {}", e.getMessage(), e);
            throw e;
        }

    }

}
