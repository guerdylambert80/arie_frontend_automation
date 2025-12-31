package org.guerdy.lambert.pages;

import org.openqa.selenium.WebElement;

import java.awt.*;

import static org.guerdy.lambert.utils.UploadElementPage.*;

public class UploadPage {

    public void ClickXmlButton() {
        clickXmlButton();
    }

    public void ClickPdfButton() {
        clickPdfButton();
    }

    public WebElement GetChooseFileButton() {
        return getChooseFileButton();
    }
    public void ClickChooseFileButton() throws InterruptedException, AWTException {
        //clickChooseFileButton();
        selectSinglePdfFileToUpload();
    }
    public void SelectPdfFileToUpload() throws InterruptedException, AWTException {
        //clickChooseFileButton();
        selectSinglePdfFileToUpload();
    }
    public void SelectPdfFilesToUpload() throws InterruptedException, AWTException {
        selectMultiplePdfFilesToUpload();
    }
    public void SelectXmlFileButton() throws InterruptedException, AWTException {
        selectSingleXmlFileToUpload();
    }
    public void SelectXmlFilesButton() throws InterruptedException, AWTException {
        selectMultipleXmlFilesToUpload();
    }
    public void ClickBrowseButton() {
        clickBrowseButton();
    }
    public void ClickSubmitButton() {
        clickSubmitButton();
    }
}
