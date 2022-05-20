package Common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Browser {
    public static WebDriver Driver;
    public static WebDriverWait Wait;
    public static JavascriptExecutor JS;

    public static void StartBrowser () {
        WebDriverManager.chromedriver().setup();
        Driver = new ChromeDriver();
        Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Configuration.ImplicitWaitSeconds));
        Wait = new WebDriverWait(Driver, Duration.ofSeconds(Configuration.ExplicitWaitSeconds));
        JS = (JavascriptExecutor) Driver;
    }

    public static void MaximizeWindow() {
        Driver.manage().window().maximize();
    }

    public static void MinimizeWindow() {
        Driver.manage().window().minimize();
    }

    public static void StopBrowser() {
        Driver.quit();
        JS = null;
    }
}