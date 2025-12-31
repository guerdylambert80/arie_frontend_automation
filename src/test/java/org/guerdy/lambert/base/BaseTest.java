package org.guerdy.lambert.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.guerdy.lambert.utils.extentreports.ExtentManager;
import org.guerdy.lambert.utils.listeners.TestListener;

@Listeners(TestListener.class)
public class BaseTest {
    // Static initializer to ensure logs directory exists BEFORE Log4j2 initializes
    static {
        ensureLogsDirectoryExistsStatic();
        // Force Log4j2 to use its own provider instead of SLF4J bridge
        System.setProperty("log4j2.loggerContextFactory", "org.apache.logging.log4j.core.impl.Log4jContextFactory");
    }
    
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    @Getter
    public static WebDriver driver;

    public static Properties configLocatorsProp = new Properties();
    public static Properties loginLocatorsProp = new Properties();
    public static Properties dashboardLocatorsProp = new Properties();
    public static Properties uploadLocatorsProp = new Properties();

    public static FileReader configFileReader;
    public static FileReader loginLocatorsReader;
    public static FileReader dashboardLocatorsReader;
    public static FileReader uploadLocatorsReader;


    //@BeforeClass
    @BeforeMethod
    //@BeforeTest
    public void setUp() throws IOException {
        if(driver==null) {

            configFileReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/config.properties");
            loginLocatorsReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/loginLocators.properties");
            dashboardLocatorsReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/dashboardLocators.properties");
            uploadLocatorsReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/uploadLocators.properties");

            configLocatorsProp.load(configFileReader);
            loginLocatorsProp.load(loginLocatorsReader);
            dashboardLocatorsProp.load(dashboardLocatorsReader);
            uploadLocatorsProp.load(uploadLocatorsReader);

        }
        if(configLocatorsProp.getProperty("browser").equalsIgnoreCase("chrome")) {
            System.out.println( System.getProperty("user.dir"));
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();		//base
            driver.get(configLocatorsProp.getProperty("url"));
            driver.manage().window().maximize();
        }
        else if(configLocatorsProp.getProperty("browser").equalsIgnoreCase("firefox")) {
            System.out.println( System.getProperty("user.dir"));
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.get(configLocatorsProp.getProperty("url"));
        }

    }

    public void takeScreenshot(String methodName) throws IOException {

        Date currentDate = new Date();
        String screenShotFilename = currentDate.toString().replace(" ", "-").replace(":", "-");

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile,
                    new File("src/test/java/resources/screenshots/" + methodName + "_" + screenShotFilename + ".png"));
        } catch (IOException e) {
            throw e;
        }
    }

//    @AfterTest
//    //@AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            driver.close();
//        }
//        log.info("Tear down successful");
//    }

    @AfterSuite
    public void afterSuite() {
        // Ensure ExtentReports is flushed even if listener doesn't fire
        ExtentManager.extentReports.flush();
        log.info("ExtentReports flushed in AfterSuite");
    }

    /**
     * Static method to ensure the logs directory exists BEFORE Log4j2 initializes.
     * This is called from a static initializer block to run before Logger creation.
     * Logs directory is created in project root as 'logs/' for simplicity.
     */
    private static void ensureLogsDirectoryExistsStatic() {
        try {
            // Create logs directory in project root (simpler path)
            File logsDir = new File(System.getProperty("user.dir") + "/logs");
            if (!logsDir.exists()) {
                boolean created = logsDir.mkdirs();
                if (created) {
                    System.out.println("Created logs directory: " + logsDir.getAbsolutePath());
                } else {
                    System.err.println("Failed to create logs directory: " + logsDir.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.err.println("Error creating logs directory: " + e.getMessage());
            throw e;
        }
    }
}
