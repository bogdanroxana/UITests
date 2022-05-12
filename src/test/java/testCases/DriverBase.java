package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.AjustesPage;
import pages.FacturasPage;
import pages.LoginPage;
import pages.ResumenPage;
import util.TestData;

import java.util.HashMap;

public class DriverBase {

    WebDriver driver;
    LoginPage loginPage;
    FacturasPage facturasPage;

    @BeforeMethod
    public void setUp() {
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

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
