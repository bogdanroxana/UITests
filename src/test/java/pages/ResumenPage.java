package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Month;
import util.Waiting;

import java.time.LocalDate;

import static util.CommonTask.clickElement;

public class ResumenPage {
    WebDriver driver;

    public ResumenPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //------------------------- Elements


    @FindBy(id = "navigation-tab-overview")
    private WebElement resumenTab;
    @FindBy(className = "css-1ugpce6")
    private WebElement table;
    @FindBy(xpath = "//table/tbody/tr[1]/td[1]/div/span[2]")
    private WebElement firstRowText;
    @FindBy(xpath = "//table/tbody/tr[1]/td[2]/button")
    private WebElement firstDownloadButton;
    @FindBy(xpath = "//table/tbody/tr[10]/td[1]/div")
    private WebElement rowText;
    @FindBy(xpath = "//table/tbody/tr[10]/td[2]/button")
    private WebElement downloadButton;




    public void clickResumenTab() throws InterruptedException {
        clickElement(driver, resumenTab, "Resumen Tab");
        Thread.sleep(3000);
        try {
            WebElement textSecondRow = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/table/tbody/tr[2]/td[1]/div/span[1]"));
        } catch(NoSuchElementException e) {
            driver.navigate().refresh();
            Thread.sleep(4000);
            System.out.println("\n\nrefresh\n\n");
        }
    }

    public boolean isNotAvailable() throws InterruptedException {
        Thread.sleep(3000);
        Waiting.visibilityOfElement(driver, firstRowText, "First row");
        String text = firstRowText.getText();
        int currentMonth = LocalDate.now().getMonthValue();
        Month availableMonth = Month.valueOf(text.substring(text.lastIndexOf(" ") + 1));
        if((availableMonth.getValue() == currentMonth + 1) || (currentMonth == 12 && availableMonth.getValue() == 1)){
            if((!firstDownloadButton.isEnabled()) && firstDownloadButton.isDisplayed()) {
                return true;
            }
        }
        return false;
    }



    public boolean isAvailable() throws InterruptedException {
        Thread.sleep(1000);
        Waiting.visibilityOfElement(driver, rowText, "Row");

        String text = rowText.getText();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        String [] firstline = text.split("\n");
        String [] str = firstline[1].split(" ");
        int availableMonth = Month.valueOf(str[str.length-1]).getValue();
        int availableYear = Integer.parseInt(str[str.length-2]);
        if((availableYear == currentYear && availableMonth >= currentMonth) || (availableYear > currentYear)){
            if(downloadButton.isDisplayed() && downloadButton.isEnabled()){
                return true;
            }
        }
        return false;
    }

    public String getFileName() {
        String text = rowText.getText();
        String [] firstline = text.split("\n");
        String [] str = firstline[0].split(" ");
        int availableMonth = Month.valueOf(str[0]).getValue();
        int availableYear = Integer.parseInt(str[1]);
        return availableMonth + "_" + availableYear + ".ods";
    }

    public void clickDownloadButton() {
        Waiting.elementToBeClickable(driver, downloadButton, "Download button");
        clickElement(driver, downloadButton, "Download button");
    }

}
