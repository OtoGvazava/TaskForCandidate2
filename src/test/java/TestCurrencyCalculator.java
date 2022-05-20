import Common.Browser;
import Common.Configuration;
import Pages.Steps.CurrencyCalculatorSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class TestCurrencyCalculator {
    private CurrencyCalculatorSteps steps;

    @BeforeClass
    public void before () {
        Configuration.ImplicitWaitSeconds = 10;
        Configuration.ExplicitWaitSeconds = 20;
        Configuration.BaseURL = "https://www.paysera.lt/";

        Browser.StartBrowser();
        Browser.MaximizeWindow();

        steps = new CurrencyCalculatorSteps();
        steps.navigate();
    }

    @Test(priority = 1)
    public void check_sell_and_buy_inputs_auto_clear() {
        steps.scrollToCalculator();
        steps.waitCalculatorTableBodyVisible();

        steps.setSellInput(500.5);
        steps.checkBuyInputIsEmpty();

        steps.setBuyInput(600);
        steps.checkSellInputIsEmpty();
    }

    @Test(priority = 2)
    public void change_country_and_check_result() throws InterruptedException {
        String country = "Bulgaria";
        String currency = "BGN";
        steps.scrollToCalculator();
        steps.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesBeforeCountryChange =
                steps.getCurrencyOfficialRates();

        steps.scrollToFooter();
        steps.setCountry(country);

        steps.scrollToCalculator();
        steps.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesAfterCountryChange = steps.getCurrencyOfficialRates();

        steps.checkSellCurrency(currency);

        steps.assertIsNotEqualHashMapData(officialRatesBeforeCountryChange, officialRatesAfterCountryChange);
    }

    @Test(priority = 3)
    public void check_difference_amount() {
        steps.scrollToCalculator();
        steps.waitCalculatorTableBodyVisible();
        steps.checkDifferenceAmount();
    }

    @Test(priority = 4)
    public void change_currency_and_check_result() throws InterruptedException {
        String currency = "USD";
        steps.scrollToCalculator();
        steps.waitCalculatorTableBodyVisible();

        HashMap<String, String> officialRatesBeforeCurrencyChange = steps.getCurrencyOfficialRates();
        steps.changeSellCurrency(currency);
        steps.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesAfterCurrencyChange = steps.getCurrencyOfficialRates();

        steps.checkSellCurrency(currency);
        steps.assertIsNotEqualHashMapData(officialRatesBeforeCurrencyChange, officialRatesAfterCurrencyChange);
    }

    @Test(priority = 5)
    public void check_clear_filter() throws InterruptedException {
        String selectedCountryCurrency = "BGN";
        String defaultSellAmount = "100";
        steps.scrollToCalculator();
        steps.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesBeforeCurrencyChange = steps.getCurrencyOfficialRates();
        steps.clearFilter();
        steps.waitCalculatorTableBodyVisible();
        HashMap<String, String> officialRatesAfterCurrencyChange = steps.getCurrencyOfficialRates();
        steps.checkSellCurrency(selectedCountryCurrency);
        steps.checkSellAmount(defaultSellAmount);
        steps.checkBuyAmount("");
        steps.assertIsNotEqualHashMapData(officialRatesBeforeCurrencyChange, officialRatesAfterCurrencyChange);
    }

    @AfterClass
    public void after () {
        Browser.StopBrowser();
    }
}
