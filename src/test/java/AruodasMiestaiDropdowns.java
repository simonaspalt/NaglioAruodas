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
import java.util.ArrayList;
import java.util.Arrays;
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
        String savivaldybe = "Palanga";//region
        String gyvenviete = "Palangos";//town
        String mikrorajonas = "Šventoji";//quartal
        String gatve = "danes g";//street
        String otipas = "Sklypai kaime";
        List<String> parameters = new ArrayList<>(Arrays.asList("savivaldybe", "gyvenviete", "mikrorajonas", "gatve", "otipas"));

        _globalDriver.findElement(By.xpath("//*[@id=\"newObjectForm\"]/ul/li[3]/span[1]/input[2]")).click();//click savivalddybe
        _globalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> regionList = _globalDriver.findElement(By.id("regionDropdown")).findElements(By.className("drop-down-value-row"));//gets  all element in droplist
        for(WebElement region: regionList){//click savivaldybe in a drop list
            if(region.getText().contains(savivaldybe)){
                region.click();
                break;
            }
        }
        _globalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        _globalDriver.findElement(By.id("district")).click();//click gyvenviete
        //dropdown-input-values-address
        List<WebElement> dropList = _globalDriver.findElements(By.className("dropdown-input-values-address"));
        String districtID =  dropList.get(1).getAttribute("id");//gets second droplist id
        //System.out.println(districtID);
        List<WebElement> townList = _globalDriver.findElement(By.id(districtID)).findElements(By.className("drop-down-value-row"));//gets all elemets in droplist
        for(WebElement town: townList){//find and click gyvenviete in a drop list
            if (town.getText().contains(gyvenviete)){
                town.click();
                break;
            }
        }
        _globalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //dropdown-input-value-title//input-right-dropdown

        if (_globalDriver.findElement(By.id("quartalField")).getAttribute("class") != "field-disabled hide"){//checks if mikrorajonas is hiden
            _globalDriver.findElement(By.xpath("//*[@id=\"quartalTitle\"]")).click();//clicks mikrorajonas, pasirinkti
            dropList = _globalDriver.findElements(By.className("dropdown-input-values-address"));
            String quartalID =  dropList.get(2).getAttribute("id");//gets third droplist id
            //System.out.println(quartalID);
            List<WebElement> quartals = _globalDriver.findElement(By.id(quartalID)).findElements(By.className("drop-down-value-row"));//gets all elements in droplist
            for(WebElement quartal: quartals){//find and click mikrorajonas in a drop list
                if (quartal.getText().contains(mikrorajonas)){
                    quartal.click();
                    break;
                }

            }
        }
        ///html/body/div[1]/div[2]/form/ul/li[3]/span[1]/input[2]
        ///html/body/div[1]/div[2]/form/ul/li[3]/span[1]/input[2]
        ///html/body/div[1]/div[2]/form/ul/li[4]/span[1]/input[2]
        ///html/body/div[1]/div[2]/form/ul/li[6]/span[1]/input[2]
        ///html/body/div[1]/div[2]/form/ul/li[5]/span[1]/input[2]
        ///html/body/div[1]/div[2]/form/ul/li[7]/span[1]/input[2]

    }
    @Test
    public void aruodasAddressLoop(){
        String savivaldybe = "Šiauliai";//region
        String gyvenviete = "Žaliūkių k.";//town
        String mikrorajonas = "Šventoji";//quartal
        String gatve = "Danės g.";//street
        String otipas = "Sklypai kaime";//object type
        List<String> parameters = new ArrayList<>();
        parameters.add(savivaldybe);
        parameters.add(gyvenviete);
        parameters.add(mikrorajonas);
        parameters.add(gatve);
        parameters.add(otipas);
        int parameterCounter = 0;
        int dropListCounter = 3;

        for (int i = 0; i < 5; i++) {
            if (_globalDriver.findElement(By.xpath("html/body/div[1]/div[2]/form/ul/li[" + dropListCounter + "]")).getAttribute("class").contains("field-disabled")){
                parameterCounter++;
                dropListCounter++;
                i--;
                continue;//if drop list is hidden repeats loop with next parameter and droplist
            }
            if (parameterCounter > 5){
                break;
            }
            //System.out.println(dropListCounter);          ///html/body/div[1]/div[2]/form/ul/li[6]/span[1]/input[2]
            _globalDriver.findElement(By.xpath("html/body/div[1]/div[2]/form/ul/li[" + dropListCounter + "]/span[1]/input[2]")).click();//click on drop list
            //_globalDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> dropList = _globalDriver.findElements(By.className("dropdown-input-values-address"));//creates list of non hiden droplits
            //System.out.println(dropList.size());
            String droplistId = dropList.get(i).getAttribute("id");//gets droplist id
            List<WebElement> droplistElements = _globalDriver.findElement(By.id(droplistId)).findElements(By.className("drop-down-value-row"));//gets all elements in droplist
            for(WebElement element: droplistElements){//go through all elements, click when match is found and stop loop
                if(element.getText().contains(parameters.get(parameterCounter))){
                    element.click();
                    break;
                }
            }            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        parameterCounter++;
        dropListCounter++;
        }

    }
    public WebElement elementWaiter(By by){
        WebDriverWait wait = new WebDriverWait(_globalDriver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element;
    }
}
