package Pages;

import Common.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainLayout {

    @FindBy(tagName = "footer")
    private WebElement FooterElement;

    @FindBy(xpath = "/html/body/footer/div[2]/div/div/div[2]/div/span")
    private WebElement LocalizationButton;

    @FindBy(xpath = "//*[@id=\"countries-dropdown\"]")
    private WebElement LocalizationCountryButton;

    @FindBy(xpath = "//*[@aria-labelledby=\"countries-dropdown\"]")
    private WebElement LocalizationCountryMenu;

    MainLayout() {
        PageFactory.initElements(Browser.Driver, this);
    }

    public void scrollToFooter() {
        Browser.JS.executeScript("arguments[0].scrollIntoView", FooterElement);
    }

    public void setCountry(String country) {
        this.LocalizationButton.click();
        this.LocalizationCountryButton.click();
        String countryXPATH = String.format("//*[normalize-space()='%s']", country);
        this.LocalizationCountryMenu.findElement(By.xpath(countryXPATH)).click();
    }
}
