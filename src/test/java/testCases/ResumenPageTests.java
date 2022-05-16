package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.FacturasPage;
import pages.LoginPage;
import pages.ResumenPage;
import util.CommonTask;
import util.TestData;


public class ResumenPageTests extends DriverBase{

    ResumenPage resumenPage;

    @Test
    public void downloadButtonNotAvailable() throws Exception {
        resumenPage = new ResumenPage(driver);
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.acceptCookies();
        resumenPage.clickResumenTab();
        Assert.assertTrue(resumenPage.isNotAvailable());
    }

    @Test
    public void downloadButtonAvailable() throws Exception {
        resumenPage = new ResumenPage(driver);
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.acceptCookies();
        resumenPage.clickResumenTab();
        Assert.assertTrue(resumenPage.isAvailable(), "Download button is not available");
        String fileName = resumenPage.getFileName();
        resumenPage.clickDownloadButton();
        Assert.assertTrue(CommonTask.isFileDownloaded(fileName), "File was not downloaded");
    }

}
