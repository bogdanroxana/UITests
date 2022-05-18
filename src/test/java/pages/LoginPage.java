package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Waiting;

import static util.CommonTask.clickElement;
import static util.CommonTask.sendKeys;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user_id")
    private WebElement userField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "submit")
    private WebElement loginButton;

    public  void login(String user, String password) throws InterruptedException {
        acceptCookies();
        Waiting.visibilityOfElement(driver, userField, "UserName Field");
        sendKeys(userField, user, "Username Field");
        sendKeys(passwordField, password, "Password Field");
        Waiting.elementToBeClickable(driver, loginButton, "Login Button");
        clickElement(driver, loginButton, "Login Button");
        Thread.sleep(1000);
        acceptCookies();
    }

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

}
