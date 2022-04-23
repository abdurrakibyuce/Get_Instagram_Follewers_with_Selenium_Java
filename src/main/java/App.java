import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class App {

    WebDriver driver;
    String baseUrl = "https:/www.instagram.com/";

    By usernamaLocator = new By.ByCssSelector("input[name='username']");
    By passwordLocator = new By.ByCssSelector("input[name='password']");
    By loginButtonLocator = new By.ByCssSelector("button[type='submit']");
    By followersLocator = By.cssSelector("span[class='g47SY ']");
    By followersLocator2 = By.xpath("(//span[@class='g47SY '])[3]");
    By followersListLocator = By.cssSelector("._7UhW9.xLCgt.qyrsm.KV-D4.se6yk");
    By infoSaveLocator = By.className("olLwo");
    By userControlLocator = By.xpath("//span[text()=\"Zoë Kravitz\"]");

    public App(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    private void waitMethod(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void login (String username, String password) {
        waitMethod(usernamaLocator);
        driver.findElement(usernamaLocator).sendKeys(username);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
    }

    public void navigateToProfile(String profileName) {
        waitMethod(infoSaveLocator);
        driver.navigate().to(baseUrl.concat(profileName));
    }

    public void clickFollowersCount() {
        waitMethod(userControlLocator);
        driver.findElements(followersLocator).get(2).click();
    }

    public void scrollDown() throws InterruptedException {

        waitMethod(followersListLocator);
        String jsCommand = "" +
                "sayfa = document.querySelector('.isgrP');" +
                "sayfa.scrollTo(0,sayfa.scrollHeight);" +
                "var sayfaSonu = sayfa.scrollHeight;" +
                "return sayfaSonu;";

        JavascriptExecutor js = (JavascriptExecutor)driver;
        var sayfaSonu = js.executeScript(jsCommand);
        while (true) {
            var son = sayfaSonu;
            Thread.sleep(750);
            sayfaSonu = js.executeScript(jsCommand);
            int followerCount = getcount();
            List<WebElement> followerListSize = driver.findElements(followersListLocator);
            if(followerCount == followerListSize.size())
                break;
        }
    }

    public void getFollowerList() {

        waitMethod(followersListLocator);
        int count = 1;
        List<WebElement> followerList = driver.findElements(followersListLocator);
        for (WebElement follewer : followerList) {
            System.out.println(count + ". " + follewer.getText());
            count++;
        }

    }

    public int getcount(){
        String count = driver.findElement(followersLocator2).getText();
        return Integer.parseInt(count);
    }

}
