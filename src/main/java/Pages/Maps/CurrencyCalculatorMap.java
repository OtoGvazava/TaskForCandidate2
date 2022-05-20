package Pages.Maps;

import Common.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CurrencyCalculatorMap {
    @FindBy(xpath = "/html/body/main/article/section[3]/div")
    public WebElement CalculatorContainer;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/table/tbody")
    public WebElement CalculatorTableBody;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[1]/input")
    public WebElement SellInput;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[3]/input")
    public WebElement BuyInput;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[1]/div/div[1]/span/span[2]/span")
    public WebElement SellCurrency;

    @FindBy(xpath = "/html/body/main/article/section[3]/div/div[2]/div/div/div/div[2]/div[1]/form/div[1]/div/ul/li")
    public WebElement SellCurrencyMenu;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[4]/button")
    public WebElement Filter;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[5]/button")
    public WebElement ClearFilter;

    public CurrencyCalculatorMap() {
        PageFactory.initElements(Browser.Driver, this);
    }
}
