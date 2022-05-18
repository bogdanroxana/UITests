package testCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import pages.AjustesPage;
import pages.FacturasPage;
import pages.LoginPage;
import pages.ResumenPage;
import util.CommonTask;
import util.TestData;

import java.io.IOException;


public class ResumenPageTests extends DriverBase{

    ResumenPage resumenPage;

    @BeforeClass
    public void setupTest() throws InterruptedException {
        DriverBase.setUp();
        resumenPage = new ResumenPage(driver);
        facturasPage = new FacturasPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
    }

    @Test(priority = 1)
    public void downloadButtonNotAvailable() throws Exception {
        resumenPage.clickResumenTab();
        Assert.assertTrue(resumenPage.isNotAvailable());
    }

    @Test(priority = 2)
    public void downloadButtonAvailable() throws Exception {
        Assert.assertTrue(resumenPage.isAvailable(), "Download button is not available");
        String fileName = resumenPage.getFileName();
        resumenPage.clickDownloadButton();
        Assert.assertTrue(CommonTask.isFileDownloaded(fileName), "File was not downloaded");
    }

    @AfterMethod
    public void takescreenhot(ITestResult result) throws IOException {
        if(ITestResult.FAILURE == result.getStatus()) {
            DriverBase.takeScreenshot(driver, result.getName());
        }
    }

    @AfterClass
    public void teardown() throws IOException {
        DriverBase.tearDown();
    }
}
