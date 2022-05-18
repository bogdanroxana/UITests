package testCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.FacturasPage;
import pages.LoginPage;
import util.TestData;

import java.io.IOException;

public class FacturasPageTests extends DriverBase{

    @BeforeClass
    public void setupTest() throws InterruptedException {
        DriverBase.setUp();
        facturasPage = new FacturasPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
    }

    @Test(priority = 1)
    public void searchByInvoiceNumber() throws Exception {
        facturasPage.setSearchField(TestData.SEARCH_BY_INVOICE_NUMBER);
        facturasPage.clickSearchButton();
        facturasPage.clickOpenInvoice();
        Assert.assertTrue(facturasPage.getInvoiceNumber().contains(TestData.SEARCH_BY_INVOICE_NUMBER));
        Assert.assertTrue(facturasPage.checkText());
    }

    @Test(priority = 2)
    public void selectMultipleInvoceis() throws Exception {
        facturasPage.clearSearchField();
        facturasPage.setSearchField(TestData.SELECT_MULTIPLE_INVOICES);
        facturasPage.clickSearchButton();
        facturasPage.setCheckbox();
        facturasPage.clickDownloadButton();
        Assert.assertTrue(facturasPage.checkIfDownloaded());
    }

    @Test(priority = 3)
    public void searchByDate() throws Exception {
        facturasPage.sendStartDate();
        facturasPage.sendStopDate();
        facturasPage.clearSearchField();
        facturasPage.clickSearchButton();
        Assert.assertTrue(facturasPage.compareStopDates() >= 0);
        Assert.assertTrue(facturasPage.compareStartDates() <= 0);
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