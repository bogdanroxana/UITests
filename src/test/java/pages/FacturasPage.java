package pages;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import util.CommonTask;
import util.TestData;
import util.Waiting;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static util.CommonTask.*;

public class FacturasPage {

    WebDriver driver;

    public FacturasPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //------------------------- Elements

    @FindBy(xpath = "//body//cms-cookie-disclaimer")
    private WebElement shadowHost;
    @FindBy(id = "id")
    private WebElement searchField;
    @FindBy(className = "css-1nt0ni0")
    private WebElement searchButton;
    @FindBy(xpath = "//table/tbody/tr[1]/td[3]")
    private WebElement invoiceNumber;
    @FindBy(xpath = "//table/tbody/tr[1]/td[7]/button")
    private WebElement openInvoice;
//    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/div[1]/div/div/div[1]/div/div[2]/div/div/input")
    @FindBy(xpath = "//*[@class=\"css-wq5ns2\"]//input")
    private WebElement startDate;
//    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/div[1]/div/div/div[2]/div/div[2]/div/div/input")
    @FindBy(xpath = "//*[@class=\"css-163kw6v\"]//input")
    private WebElement stopDate;
    @FindBy(xpath = "//div[text()=" + TestData.START_DAY + "]")
    private WebElement setStartDate;
    @FindBy(xpath = "//div[text()=" + TestData.STOP_DAY + "]")
    private WebElement setStopDate;

//    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/div[3]/div/button[2]")
    @FindBy(xpath = "//*[@class=\"sc-bdvvtL ePZfkB\"]//div[1]//button[2]")
    private WebElement nextPage;
//    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/div[3]/button")
    @FindBy(className = "css-j86jol")
    private WebElement downloadButton;

    @FindBy(className = "css-1ugpce6")
    private WebElement table;
    @FindBy(id = "headId")
    private WebElement selectAll;

    @FindBy(xpath = "//table/tbody/tr[1]/td[2]")
    private WebElement dateText;
    @FindBy(xpath = "//table/tbody/tr[1]/td[3]")
    private WebElement invoiceText;


    //------------------------- Actions

    public void acceptCookies() {
        Cookie cookie = new Cookie.Builder("allowedCookieCategories", "allowedCookieCategories")
                .domain(".makro.es")
                .isHttpOnly(false)
                .isSecure(false)
                .path("/")
                .build();
        driver.manage().addCookie(cookie);
        driver.navigate().refresh();
    }

    public void setSearchField(String invoice){
        Waiting.visibilityOfElement(driver, searchField, "Search Field");
        sendKeys(searchField, invoice, "Search Field");
    }

    public void clickSearchButton() throws InterruptedException {
        Waiting.elementToBeClickable(driver, searchButton, "Search Button");
        clickElement(driver, searchButton, "Search Button");
        Thread.sleep(3000);
    }

    public void clickOpenInvoice() throws InterruptedException {
        Waiting.elementToBeClickable(driver, openInvoice, "Open Invoice");
        clickElement(driver, openInvoice, "Open Invoice");
        Thread.sleep(5000);
    }
    public String getInvoiceNumber(){
        return invoiceNumber.getText();
    }


    public Boolean checkText() throws IOException, InterruptedException {
        String fileName = CommonTask.getDownloadedFileName(driver);

        String pdfFile = "src//test//resources//download//" + fileName;
        File file = new File(pdfFile);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String output = pdfStripper.getText(document);
        String text = invoiceNumber.getText();
        document.close();
        if (output.contains(text)){
            File f = new File(pdfFile);
            f.delete();
            return true;
        }
        return false;
    }
    public void setCheckbox() throws InterruptedException {
        Thread.sleep(2000);
        clickCheckbox(driver, selectAll, "Select all button");
    }
    public void clickDownloadButton() throws InterruptedException {
        Waiting.elementToBeClickable(driver, downloadButton, "Download Button");
        clickElement(driver, downloadButton, "Download Button");
        Thread.sleep(2000);
    }

    public boolean checkIfDownloaded() throws InterruptedException {
        String fileName = getDownloadedFileName(driver);
        return CommonTask.isFileDownloaded(fileName);
    }

    public void sendStartDate () {
        clickElement(driver, startDate, "start date");
        clickElement(driver, setStartDate, "Set start date");
    }

    public void sendStopDate () {
        clickElement(driver, stopDate, "stop date");
        clickElement(driver, setStopDate, "Set stop date");
    }

    public int compareStopDates() throws ParseException {
        String tableStop = getDate(table, "stop");
        int result = compareDates(stopDate.getAttribute("value").replace('.', '/'), tableStop);
        return result;
    }

    public int compareStartDates() throws ParseException, InterruptedException {
        goToLastPage();
        String tableStart = getDate(table, "start");
        int result = compareDates(startDate.getAttribute("value").replace('.', '/'), tableStart);
//        Assert.assertEquals(tableStart, "11/10/2021");
        return result;
    }

    public void goToLastPage() throws InterruptedException {
        Thread.sleep(3000);
        Waiting.visibilityOfElement(driver,nextPage, "Next page");
        while(nextPage.getAttribute("disabled") == null){
            String date = dateText.getText();
            String invoice = invoiceText.getText();
            Waiting.visibilityOfElement(driver, nextPage, "Next page");
            clickElement(driver, nextPage, "Next page");
            Thread.sleep(1000);
            waitNextPage(date, invoice);
        }
    }

    public void waitNextPage(String date, String invoice) throws InterruptedException {
        int i = 0;
        while(Objects.equals(date, dateText.getText()) && Objects.equals(invoice, invoiceText.getText())){
            try{
                i++;
                Thread.sleep(1000);
                if(i == 120)
                    throw new TimeoutException("The button is in loading loop for more than 120 sec.");
            } catch (TimeoutException e) {
                Assert.fail("The button is in loading loop for more than 120 sec.");
            }
        }
    }
//  compare two given dates and return -1 if givenDate < tableDate; 1 if givenDate > tableDate and 0 if givenDate = tableDate
    public int compareDates(String givenDate, String tableDate) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdformat.parse(givenDate);
        Date d2 = sdformat.parse(tableDate);
        return d1.compareTo(d2);
    }

//  get the first date from table when position = stop and last date from table when position = start
    public String getDate(WebElement table, String position){
        List<WebElement> rows = table.findElements(By.className("css-upnin5"));
        String date = new String();
        if(Objects.equals(position, "stop"))
            date = rows.get(0).findElement(By.className("css-ly1m6m")).getText();
        else if(Objects.equals(position, "start"))
            date = rows.get(rows.size() - 1).findElement(By.className("css-ly1m6m")).getText();
        String [] d1 = new String[2];
        d1 = date.split(",", 2);
        return d1[0];
    }
}
