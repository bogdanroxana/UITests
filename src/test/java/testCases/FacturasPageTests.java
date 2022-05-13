package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FacturasPage;
import pages.LoginPage;
import util.TestData;

public class FacturasPageTests extends DriverBase{

    @Test
    public void searchByInvoiceNumber() throws Exception {
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.setSearchField(TestData.SEARCH_BY_INVOICE_NUMBER);
        facturasPage.clickSearchButton();
        facturasPage.clickOpenInvoice();
        Assert.assertTrue(facturasPage.getInvoiceNumber().contains(TestData.SEARCH_BY_INVOICE_NUMBER));
        Assert.assertTrue(facturasPage.checkText());

    }

    @Test
    public void selectMultipleInvoceis() throws Exception {
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.setSearchField(TestData.SELECT_MULTIPLE_INVOICES);
        facturasPage.clickSearchButton();
        facturasPage.setCheckbox();
        facturasPage.clickDownloadButton();
        Assert.assertTrue(facturasPage.checkIfDownloaded());
    }

    @Test
    public void searchByDate() throws Exception {
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.sendStartDate();
        facturasPage.sendStopDate();
        facturasPage.clickSearchButton();
        Assert.assertTrue(facturasPage.compareStopDates() >= 0);
        Assert.assertTrue(facturasPage.compareStartDates() <= 0);
    }
}