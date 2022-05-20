package Pages.Steps;

import Common.Browser;
import Pages.Maps.MainLayoutMap;
import org.openqa.selenium.By;

public class MainLayoutSteps {
    MainLayoutMap map;

    MainLayoutSteps() {
        map = new MainLayoutMap();
    }

    public void scrollToFooter() {
        Browser.JS.executeScript("arguments[0].scrollIntoView", map.FooterElement);
    }

    public void setCountry(String country) {
        map.LocalizationButton.click();
        map.LocalizationCountryButton.click();
        String countryXPATH = String.format("//*[normalize-space()='%s']", country);
        map.LocalizationCountryMenu.findElement(By.xpath(countryXPATH)).click();
    }
}
