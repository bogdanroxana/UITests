package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.CommonTask;
import util.Waiting;


public class AjustesPage {

    WebDriver driver;

    public AjustesPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "navigation-tab-settings")
    private WebElement ajustesTab;
    @FindBy(id = "emailNotification")
    private WebElement emailCheckbox;
    @FindBy(xpath = "//*[@class=\"sc-hKwDye gqbSjB\"]//div[1]//span")
    private  WebElement emailText;
    @FindBy(id = "printout")
    private WebElement digitalInvoiceCheckbox;
    @FindBy(xpath = "//*[@class=\"sc-hKwDye gqbSjB\"]//div[2]//span")
    private WebElement invoiceText;
    @FindBy(className = "css-1nt0ni0")
    private WebElement saveButton;
    @FindBy(className = "css-159qaa0")
    private WebElement cancelButton;
    @FindBy(className = "css-gvmf41")
    private WebElement titleElement;
    @FindBy(css = ".sc-dkPtRN.gYqWAc.css-1qnf0nw")
    private WebElement alert;

    public void clickAjustesTab() throws InterruptedException {
        CommonTask.clickElement(driver, ajustesTab, "Ajustes tab");
        try {
            Waiting.visibilityOfElement(driver, invoiceText, "Invoice text");
        } catch(AssertionError e) {
            driver.navigate().refresh();
            Waiting.visibilityOfElement(driver, invoiceText, "Invoice text");
        }
    }

    public Boolean isEmailCheckboxSelected() throws InterruptedException {
        Waiting.visibilityOfElement(driver, emailText, "Email text");
        return emailCheckbox.isSelected();
    }

    public Boolean isDigitalInvoiceCheckboxSelected() throws InterruptedException {
        Waiting.visibilityOfElement(driver, invoiceText, "Invoice text");
        return digitalInvoiceCheckbox.isSelected();
    }

    public void selectEmailCheckbox(){
        CommonTask.clickCheckbox(driver, emailCheckbox, "Email checkbox");
    }

    public void selectDigitalInvoiceCheckbox(){
        CommonTask.clickCheckbox(driver, digitalInvoiceCheckbox, "Digital invoice checkbox");
    }

    public void clickSaveButton() throws InterruptedException {
        Waiting.elementToBeClickable(driver, saveButton, "Save button");
        CommonTask.clickElement(driver, saveButton, "Save button");
    }

    public String getAlertText() throws InterruptedException {
        Waiting.visibilityOfElement(driver, alert, "Alert notification");
        return alert.getText();
    }

    public void clickCancelButton(){
        Waiting.elementToBeClickable(driver, cancelButton, "Cancel button");
        CommonTask.clickElement(driver, cancelButton, "Cancel Button");
    }

    public String getTitlePage() throws InterruptedException {
        Waiting.visibilityOfElement(driver, titleElement, "Facturas page title");
        return titleElement.getText();
    }
}
