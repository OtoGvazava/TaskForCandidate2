package Tests;

import Common.Browser;
import Common.Configuration;
import Pages.CurrencyConversionCalculatorPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CurrencyConversionCalculatorTests {
    private CurrencyConversionCalculatorPage objectCurrencyConversionCalculatorPage;

    @BeforeClass
    public void before () {
        Configuration.ImplicitWaitSeconds = 10;
        Configuration.BaseURL = "https://www.paysera.lt/";

        Browser.StartBrowser();
        Browser.MaximizeWindow();

        objectCurrencyConversionCalculatorPage = new CurrencyConversionCalculatorPage();
        objectCurrencyConversionCalculatorPage.navigate();
    }

    @Test(priority = 1)
    public void check_sell_and_buy_inputs_auto_clear() {
        objectCurrencyConversionCalculatorPage.scrollToCalculator();
        objectCurrencyConversionCalculatorPage.waitCalculatorTableBodyVisible();

        objectCurrencyConversionCalculatorPage.setSellInput(500.5);
        String buyInputValue = objectCurrencyConversionCalculatorPage.getBuyInput();
        Assert.assertEquals(buyInputValue, "", "When input Sell amount, Buy input doesn't empty!");

        objectCurrencyConversionCalculatorPage.setBuyInput(600);
        String sellInputValue = objectCurrencyConversionCalculatorPage.getSellInput();
        Assert.assertEquals(sellInputValue, "", "When input Buy amount, Sell input doesn't empty!");
    }

    @Test(priority = 2)
    public void select_country_and_check_result() {
        String country = "Bulgaria";
        String countryCurrency = "BGN";
        objectCurrencyConversionCalculatorPage.scrollToCalculator();
        objectCurrencyConversionCalculatorPage.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesBeforeCountryChange =
                objectCurrencyConversionCalculatorPage.getCurrencyOfficialRates();

        objectCurrencyConversionCalculatorPage.scrollToFooter();
        objectCurrencyConversionCalculatorPage.setCountry(country);

        objectCurrencyConversionCalculatorPage.scrollToCalculator();
        objectCurrencyConversionCalculatorPage.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesAfterCountryChange =
                objectCurrencyConversionCalculatorPage.getCurrencyOfficialRates();

        String SellCurrency = objectCurrencyConversionCalculatorPage.getSellCurrency();
        Assert.assertEquals(countryCurrency, SellCurrency,
                "Selected currency not equal to selected country currency!");

        objectCurrencyConversionCalculatorPage.
                assertIsNotEqualHashMapData(officialRatesBeforeCountryChange, officialRatesAfterCountryChange);
    }

    @Test(priority = 3)
    public void check_difference_amount() {
        objectCurrencyConversionCalculatorPage.scrollToCalculator();
        objectCurrencyConversionCalculatorPage.waitCalculatorTableBodyVisible();
        objectCurrencyConversionCalculatorPage.checkDifferenceAmount();
    }

    @AfterClass
    public void after () {
        Browser.StopBrowser();
    }
}
