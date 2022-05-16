package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AjustesPage;
import pages.FacturasPage;
import pages.LoginPage;
import util.TestData;


public class AjustesPageTests extends DriverBase{

    AjustesPage ajustesPage;

    @Test
    public void checkSaveButton() throws Exception {
        ajustesPage = new AjustesPage(driver);
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.acceptCookies();
        ajustesPage.clickAjustesTab();
        Boolean beforeSave = ajustesPage.isEmailCheckboxSelected();
        ajustesPage.selectEmailCheckbox();
        ajustesPage.clickSaveButton();
        Assert.assertTrue(ajustesPage.getAlertText().contains("Sus cambios han sido tramitados."),"Alert text");
        Assert.assertTrue(ajustesPage.isEmailCheckboxSelected() != beforeSave,"Changes not saved");
    }

    @Test
    public void checkCancelButton() throws Exception {
        ajustesPage = new AjustesPage(driver);
        facturasPage = new FacturasPage(driver);
        facturasPage.acceptCookies();
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
        facturasPage.acceptCookies();
        String title = ajustesPage.getTitlePage();
        ajustesPage.clickAjustesTab();
        Boolean beforeCancel = ajustesPage.isDigitalInvoiceCheckboxSelected();
        ajustesPage.selectDigitalInvoiceCheckbox();
        ajustesPage.clickCancelButton();
        Assert.assertEquals(ajustesPage.getTitlePage(), title);
        ajustesPage.clickAjustesTab();
        Assert.assertTrue(ajustesPage.isDigitalInvoiceCheckboxSelected() == beforeCancel);
    }

}
