package testCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.AjustesPage;
import pages.FacturasPage;
import pages.LoginPage;
import util.TestData;

import java.io.IOException;


public class AjustesPageTests extends DriverBase{

    AjustesPage ajustesPage;

    @BeforeClass
    public void setupTest() throws InterruptedException {
        DriverBase.setUp();
        ajustesPage = new AjustesPage(driver);
        facturasPage = new FacturasPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.login(TestData.USER, TestData.PASSWORD);
    }

    @Test(priority = 1)
    public void checkCancelActionWorksAsExpected() throws Exception {

        String title = ajustesPage.getTitlePage();
        ajustesPage.clickAjustesTab();
        Boolean beforeCancel = ajustesPage.isDigitalInvoiceCheckboxSelected();
        ajustesPage.selectDigitalInvoiceCheckbox();
        ajustesPage.clickCancelButton();
        Assert.assertEquals(ajustesPage.getTitlePage(), title);
        ajustesPage.clickAjustesTab();
        Assert.assertTrue(ajustesPage.isDigitalInvoiceCheckboxSelected() == beforeCancel);
    }

    @Test(priority = 2)
    public void checkSaveActionWorksAsExpected() throws Exception {
        Boolean beforeSave = ajustesPage.isEmailCheckboxSelected();
        ajustesPage.selectEmailCheckbox();
        ajustesPage.clickSaveButton();
        Assert.assertTrue(ajustesPage.getAlertText().contains("Sus cambios han sido tramitados."),"Alert text");
        Assert.assertTrue(ajustesPage.isEmailCheckboxSelected() != beforeSave,"Changes not saved");
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
