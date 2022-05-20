package Pages.Maps;

import Common.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainLayoutMap {

    @FindBy(tagName = "footer")
    public WebElement FooterElement;

    @FindBy(xpath = "/html/body/footer/div[2]/div/div/div[2]/div/span")
    public WebElement LocalizationButton;

    @FindBy(xpath = "//*[@id=\"countries-dropdown\"]")
    public WebElement LocalizationCountryButton;

    @FindBy(xpath = "//*[@aria-labelledby=\"countries-dropdown\"]")
    public WebElement LocalizationCountryMenu;

    public MainLayoutMap() {
        PageFactory.initElements(Browser.Driver, this);
    }
}