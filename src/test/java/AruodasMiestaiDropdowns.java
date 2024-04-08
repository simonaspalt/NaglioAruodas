import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AruodasMiestaiDropdowns {
    WebDriver _globalDriver;
    private WebDriver globalDriver;
    @BeforeClass
    public void setupWebDriver (){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        _globalDriver = new ChromeDriver(options);
        _globalDriver.get("https://www.aruodas.lt/ideti-skelbima/?obj=10");
        elementWaiter(By.id("onetrust-accept-btn-handler")).click();//coookies

    }
    @Test
    public void aruodasAddress(){
        String savivaldybe ="Šiauliai";
        String gyvenviete = "zaliukiu k";
        String mikrorajonas = "";
        String gatve = "danes g";
        _globalDriver.findElement(By.xpath("//*[@id=\"newObjectForm\"]/ul/li[3]/span[1]/input[2]")).click();//click savivalddybe
        ////*[@id="regionDropdown"]/li[2]
        ////*[@id="regionDropdown"]/li[62]
        _globalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        for (int i = 2; i <= 62 ; i++) {
            if (_globalDriver.findElement(By.xpath("//*[@id=\"regionDropdown\"]/li[" + i + "]")).getText().contains(savivaldybe)){
                _globalDriver.findElement(By.xpath("//*[@id=\"regionDropdown\"]/li[" + i + "]")).click(); //choose from list
                break;
            }
        }
        _globalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        _globalDriver.findElement(By.id("district")).click();//click gyvenviete
        //dropdown-input-values-address
        List<WebElement> dropLists = _globalDriver.findElements(By.className("dropdown-input-values-address"));
        String addressID =  dropLists.get(1).getAttribute("id");
        System.out.println(addressID);

    }
    public WebElement elementWaiter(By by){
        WebDriverWait wait = new WebDriverWait(_globalDriver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element;
    }
}
