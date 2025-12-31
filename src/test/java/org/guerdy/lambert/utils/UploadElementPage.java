package org.guerdy.lambert.utils;

import org.guerdy.lambert.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.time.Duration;

public class UploadElementPage extends BaseTest {

    public static WebElement getXmlButton() {
        return driver.findElement(By.xpath(uploadLocatorsProp.getProperty("xml_button")));
    }

    public static WebElement getPdfButton() {
        return driver.findElement(By.xpath(uploadLocatorsProp.getProperty("pdf_button")));
    }

    public static void clickXmlButton() {
        getXmlButton().click();
    }

    public static void clickPdfButton() {
        getPdfButton().click();
    }

    public static WebElement getChooseFileButton() {
        return driver.findElement(By.xpath(uploadLocatorsProp.getProperty("choose_file_btn")));
        //return driver.findElement(By.cssSelector(uploadLocatorsProp.getProperty("choose_file_btn")));
    }
    public static void selectSinglePdfFileToUpload() throws InterruptedException, AWTException {

        File filePath = new File("/Users/gmac09/Desktop/Residential/ABIE_7_Appraisal Note 223013310 - JEFFERY CLAYTON DRAKE - App.pdf");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));

        // Locate the <input type="file"> element directly
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));

        fileInput.sendKeys(filePath.getAbsolutePath());

    }
    public static void selectSingleXmlFileToUpload() throws InterruptedException, AWTException {

        File filePath = new File("/Users/gmac09/Desktop/ResidentialXML/4 Dunwoody Ln.xml");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));

        // Locate the <input type="file"> element directly
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));

        fileInput.sendKeys(filePath.getAbsolutePath());

    }
    public static void selectMultiplePdfFilesToUpload() throws InterruptedException, AWTException {

        System.out.println("<---------- Selecting PDF files to upload... ---------->");
        String filePaths = "/Users/gmac09/Desktop/Residential/ABIE_7_Appraisal Note 223013310 - JEFFERY CLAYTON DRAKE - App.pdf" + "\n"
                + "/Users/gmac09/Desktop/Residential/Dominquez, Odalis - 1145 Princw Avenue.pdf" + "\n"
                + "/Users/gmac09/Desktop/Residential/Fisher, Lakeisha - 915 Jackson Street.pdf";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));

        // Locate the <input type="file"> element directly
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));

        fileInput.sendKeys(filePaths);

        System.out.println("Multiple PDF files uploaded successfully.");

    }

    public static void selectMultipleXmlFilesToUpload() {
        System.out.println("<---------- Selecting XML files to upload... ---------->");
        String filePaths = "/Users/gmac09/Desktop/ResidentialXML/4 Dunwoody Ln.xml" + "\n"
                + "/Users/gmac09/Desktop/ResidentialXML/11 Prentiss Ln.xml" + "\n"
                + "/Users/gmac09/Desktop/ResidentialXML/12 Ford Ln.xml" + "\n"
                + "/Users/gmac09/Desktop/ResidentialXML/15 Beech Dr.xml" + "\n"
                + "/Users/gmac09/Desktop/ResidentialXML/16 Argyle Ave.xml";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='file'])[1]")));

        fileInput.sendKeys(filePaths);
        System.out.println("Multiple XML files uploaded successfully.");
    }
    public static WebElement getBrowseButton() {

        return driver.findElement(By.xpath(uploadLocatorsProp.getProperty("browse_button")));
    }
    public static void clickBrowseButton() {
        getBrowseButton().click();

    }
    public static WebElement getSubmitButton() {
        return driver.findElement(By.xpath(uploadLocatorsProp.getProperty("file_submit_btn")));
    }
    public static void clickSubmitButton() {
        getSubmitButton().click();
    }

    //wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(uploadLocatorsProp.getProperty("browse_button")))));
    // (//div[@role='presentation'])[1]
    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    //WebElement fileInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(uploadLocatorsProp.getProperty("browse_button"))));
    //fileInput.sendKeys(filePath.getAbsolutePath());

    //WebElement fileInput = driver.findElement(By.xpath("//input[@type='files']"));
        /*WebElement fileInput = driver.findElement(By.xpath(uploadLocatorsProp.getProperty("browse_button")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display='block';", fileInput);
        fileInput.sendKeys(filePath.toString());*/

    //getChooseFileButton().sendKeys("/Users/gmac09/Desktop/residential/ABIE_7_Appraisal Note 223013310 - JEFFERY CLAYTON DRAKE - App.pdf");

    //getChooseFileButton().click();
}
