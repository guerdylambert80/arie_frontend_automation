package org.guerdy.lambert.tests;

import org.guerdy.lambert.base.BaseTest;
import org.guerdy.lambert.pages.DashboardPage;
import org.guerdy.lambert.pages.LoginPage;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.guerdy.lambert.utils.extentreports.ExtentTestManager.startTest;

public class DashboardTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(DashboardTest.class);

    DashboardPage dashboardPage = new DashboardPage();

    @Test(enabled = true, description = "Upload file")
    public void uploadFile() throws InterruptedException {

        logger.info("Upload file test started");
        // startTest adds this test to the extent report
        startTest(this.getClass().getName(), "Upload file");
        dashboardPage.uploadFile();

    }
}
