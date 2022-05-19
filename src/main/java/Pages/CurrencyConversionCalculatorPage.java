package Pages;

import Common.Browser;
import Common.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class CurrencyConversionCalculatorPage extends MainLayout {
    private final String PageURL;

    @FindBy(xpath = "/html/body/main/article/section[3]/div")
    private WebElement CalculatorContainer;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/table/tbody")
    private WebElement CalculatorTableBody;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[1]/input")
    private WebElement SellInput;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[3]/input")
    private WebElement BuyInput;

    @FindBy(xpath = "//*[@id=\"currency-exchange-app\"]/div/div/div[2]/div[1]/form/div[1]/div/div[1]/span/span[2]/span")
    private WebElement SellCurrency;

    public CurrencyConversionCalculatorPage() {
        PageURL = "v2/en-LT/fees/currency-conversion-calculator#/";
        PageFactory.initElements(Browser.Driver, this);
    }

    public void navigate() {
        Browser.Driver.get(Configuration.BaseURL + PageURL);
    }

    public void scrollToCalculator() {
        Browser.JS.executeScript("arguments[0].scrollIntoView();", CalculatorContainer);
    }

    public void waitCalculatorTableBodyVisible () {
        Browser.Wait.until(ExpectedConditions.visibilityOf(CalculatorTableBody));
    }

    public void setSellInput (double sellAmount) {
        SellInput.clear();
        SellInput.sendKeys(Double.toString(sellAmount));
    }

    public String getSellInput () {
        return SellInput.getAttribute("value");
    }

    public void setBuyInput (double sellAmount) {
        BuyInput.clear();
        BuyInput.sendKeys(Double.toString(sellAmount));
    }

    public String getBuyInput () {
        return BuyInput.getAttribute("value");
    }

    public String getSellCurrency () {
        return SellCurrency.getText();
    }

    public HashMap<String, String> getCurrencyOfficialRates () {
        List<WebElement> currencyRows = CalculatorTableBody.findElements(By.tagName("tr"));
        HashMap<String, String> currencyRates = new HashMap<>();
        for (WebElement row : currencyRows) {
            String currency = row.findElement(By.xpath("td[1]")).getText();
            String officialRate = row.findElement(By.xpath("td[2]/span/span/span")).getText();
            currencyRates.put(currency, officialRate);
        }
        return currencyRates;
    }

    public void assertIsNotEqualHashMapData(HashMap<String, String> firstHashMap, HashMap<String, String> secondHashMap) {
        firstHashMap.forEach((key, value) -> {
            Assert.assertNotEquals(value, secondHashMap.get(key));
        });
    }

    public void checkDifferenceAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        List<WebElement> currencyRows = CalculatorTableBody.findElements(By.tagName("tr"));
        for (WebElement row : currencyRows) {
            Double payseraAmount = Double.parseDouble(row.findElement(By.xpath("td[4]/span/span/span")).getText()
                    .replace(",",""));
            IntStream providerBankIndexes = IntStream.range(5, 9);
            providerBankIndexes.forEach((int index)->{
                WebElement providerBank = row.findElement(By.xpath(String.format("td[%d]", index)));
                if (!providerBank.findElement(By.tagName("span")).getText().trim().equals("-")) {
                    Double amount = Double.parseDouble(providerBank.findElement(By.xpath("span/span/span[1]"))
                            .getText().replace(",",""));
                    if (amount < payseraAmount) {
                        try {
                            String differenceAmountStr = providerBank.findElement(By.xpath("span/span/span[2]")).getText();
                            differenceAmountStr = differenceAmountStr.replace(",","");
                            int length = differenceAmountStr.length();
                            Double differenceAmount = Double.parseDouble(differenceAmountStr.substring(1, length-1));
                            Assert.assertEquals(decimalFormat.format(differenceAmount), decimalFormat.format(amount - payseraAmount));
                        } catch (NoSuchElementException e) {
                            System.out.println("Difference amount element not found");
                        }
                    }
                }
            });
        }
    }
}
