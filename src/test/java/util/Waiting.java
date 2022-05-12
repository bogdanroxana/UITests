package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.NoSuchElementException;

public class Waiting {


    // ------------------------- Explcit Waits -------------------------

    /**
     * @param driver      - driver instance
     * @param element     - locator (WebElement)
     * @param elementName - element name
     */
    public static void elementToBeClickable(WebDriver driver, WebElement element, String elementName) {
        System.out.println("- waiting for element : " + elementName + " : to be clickable");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(TestData.SHORT_WAIT)).until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException toe) {
            System.out.println("Element is not clickable within : "+ TestData.SHORT_WAIT +" seconds");
        } catch (NoSuchElementException e) {
            Assert.fail("Element is not found : " + elementName);
        }
    }


    /**
     * @param driver      - driver instance
     * @param element     - locator (WebElement)
     * @param elementName - element name
     */
    public static void visibilityOfElement(WebDriver driver, WebElement element, String elementName) {
        try {
            System.out.println("- waiting fot element : " + elementName + " : to be visible");
            new WebDriverWait(driver, Duration.ofSeconds(TestData.SHORT_WAIT)).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException toe) {
            Assert.fail("Element is not visible within : " + TestData.SHORT_WAIT + " seconds");
        }
    }

    /**
     *
     * @param driver           - driver instance
     * @param specifiedTimeout - time that we wait for complete load
     */

    public static void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(specifiedTimeout));
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }




}
