package Pages.Steps;

import Common.Browser;
import Common.Configuration;
import Pages.Maps.CurrencyCalculatorMap;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class CurrencyCalculatorSteps extends MainLayoutSteps {
    private final CurrencyCalculatorMap map;
    private final String PageURL;

    public CurrencyCalculatorSteps() {
        map = new CurrencyCalculatorMap();
        PageURL = "v2/en-LT/fees/currency-conversion-calculator#/";
    }

    public void navigate() {
        Browser.Driver.get(Configuration.BaseURL + PageURL);
    }

    public void scrollToCalculator() {
        Browser.JS.executeScript("arguments[0].scrollIntoView();", map.CalculatorContainer);
    }

    public void waitCalculatorTableBodyVisible () {
        Browser.Wait.until(ExpectedConditions.visibilityOf(map.CalculatorTableBody));
    }

    public void setSellInput (double sellAmount) {
        map.SellInput.clear();
        map.SellInput.sendKeys(Double.toString(sellAmount));
    }

    public String getSellInput () {
        return map.SellInput.getAttribute("value");
    }

    public void setBuyInput (double sellAmount) {
        map.BuyInput.clear();
        map.BuyInput.sendKeys(Double.toString(sellAmount));
    }

    public String getBuyInput () {
        return map.BuyInput.getAttribute("value");
    }

    public void checkBuyInputIsEmpty() {
        String buyInputValue = this.getBuyInput();
        Assert.assertEquals(buyInputValue, "", "When input Sell amount, Buy input doesn't empty!");
    }

    public void checkSellInputIsEmpty() {
        String sellInputValue = this.getSellInput();
        Assert.assertEquals(sellInputValue, "", "When input Buy amount, Sell input doesn't empty!");
    }

    public String getSellCurrency () {
        return map.SellCurrency.getText();
    }

    public void checkSellCurrency(String currency) {
        String SellCurrency = this.getSellCurrency();
        Assert.assertEquals(currency, SellCurrency);
    }

    public HashMap<String, String> getCurrencyOfficialRates () {
        List<WebElement> currencyRows = map.CalculatorTableBody.findElements(By.tagName("tr"));
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
        List<WebElement> currencyRows = map.CalculatorTableBody.findElements(By.tagName("tr"));
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

    public void changeSellCurrency(String currency) throws InterruptedException {
        map.SellCurrency.click();
        String countryXPATH = String.format("//*[normalize-space()='%s']", currency);
        Browser.Wait.until(ExpectedConditions.visibilityOf(map.SellCurrencyMenu));
        map.SellCurrencyMenu.findElement(By.xpath(countryXPATH)).click();
        map.Filter.click();
        Thread.sleep(1000);
    }

    public void clearFilter() throws InterruptedException {
        map.ClearFilter.click();
        Thread.sleep(1000);
    }

    public void checkSellAmount(String amount) {
        Assert.assertEquals(this.getSellInput(), amount);
    }

    public void checkBuyAmount(String amount) {
        Assert.assertEquals(this.getBuyInput(), amount);
    }
}