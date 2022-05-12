package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.io.File;
import java.util.NoSuchElementException;

public class CommonTask {

    public WebDriver driver;

      /**
     * @param driver      - driver instance
     * @param element     - locator (WebElement)
     * @param elementName - element name
     */
    public static void moveToElement(WebDriver driver, WebElement element, String elementName) {
        System.out.println("- moving to element : " + elementName);
        try {
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();
        } catch (NoSuchElementException e) {
            Assert.fail("Element is not found : " + elementName);
        }
    }

    /**
     * @param driver      - driver instance
     * @param element     - locator (WebElement)
     * @param elementName - element name
     */
    public static void clickCheckbox(WebDriver driver, WebElement element, String elementName) {
        System.out.println("- moving to element : " + elementName);
        try {
            System.out.println("- selecting checkbox: " + elementName );
            Actions action = new Actions(driver);
            action.moveToElement(element).click().build().perform();
        } catch (NoSuchElementException e) {
            Assert.fail("Element is not found : " + elementName);
        }
    }

    /**
     * @param element     - locator (WebElement)
     * @param text        - text to be sent to element
     * @param elementName - element name
     */
    public static void sendKeys(WebElement element, String text, String elementName) {
        System.out.println("- sending keys : '" + text + "' : to " + elementName);
        try {
            System.out.println("- sending keys : '" + text + "' : to " + elementName);
            element.sendKeys(text);
        } catch(NoSuchElementException e) {
            Assert.fail("Element is not found : " + elementName);
        }
    }

    /**
     * @param driver      - driver instance
     * @param element     - locator (WebDriver)
     * @param elementName - element name
     */
    public static void clickElement(WebDriver driver, WebElement element, String  elementName) {
        try {
            System.out.println("- waiting for element : " + elementName);
            Waiting.elementToBeClickable(driver, element, elementName);
            moveToElement(driver, element, elementName);
            System.out.println("- clicking element : " + elementName);
            element.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element is not found : " + elementName);
        }
    }

    /**
     * @param fileName - name of the file we search for
     * @return true if fileName has been found and false if it wasn't found
     */
    public static boolean isFileDownloaded(String fileName) throws InterruptedException {
        Thread.sleep(5000);
        String downloadPath = "src\\test\\resources\\download";
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for(int i = 0; i < dirContents.length; i++) {
            if(dirContents[i].getName().equals(fileName)){
                // File has been found, it can now be deleted
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    public static String getDownloadedFileName(WebDriver driver) throws InterruptedException {
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open()");
        //switch to new window opened
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        Thread.sleep(5000);
        String fileName = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        driver.close();
        driver.switchTo().window(mainWindow);
        return fileName;
    }
}
