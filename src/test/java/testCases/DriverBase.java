package testCases;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.AjustesPage;
import pages.FacturasPage;
import pages.LoginPage;
import pages.ResumenPage;
import util.TestData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DriverBase {

    static WebDriver driver;
    LoginPage loginPage;
    FacturasPage facturasPage;

    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", TestData.PATH);
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        String downloadFilepath = System.getProperty("user.dir") + "\\src\\test\\resources\\download";
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("plugins.plugins_disabled", new String[] {
                "Chrome PDF Viewer"
        });
        prefs.put("plugins.always_open_pdf_externally", true);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.get(TestData.URL);
        driver.manage().window().maximize();

    }

    public static void takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
//        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(screenshotFile , new File("./src/test/resources/download/testFail.png"));
        try {
        TakesScreenshot ts =((TakesScreenshot) driver);
        File source = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("./src/test/resources/download/"+screenshotName+".png"));
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }

    public static void tearDown(){
        driver.quit();
    }
}
