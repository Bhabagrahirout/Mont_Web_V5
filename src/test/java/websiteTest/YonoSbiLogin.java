package websiteTest;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YonoSbiLogin {

	private static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");

		ChromeOptions options2 = new ChromeOptions();

		// for dislabe chrome is automated bar
		options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
//		options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//		options2.addArguments(new String[] { "--disable-notifications" });
//		options2.addArguments(new String[] { "--disable-extentions" });
//		options2.addArguments(new String[] { "disable-infobars" });
//		options2.addArguments(new String[] { "disable-captcha" });
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
//		options2.addArguments(new String[] { "--disable-popup-blocking" });
//		options2.addArguments("--user-data-dir=/tmp/new-profile");
//		options2.addArguments(new String[] { "--no-sandbox" });
//		options2.addArguments(new String[] { "window-size=1920,1080" });

//		options2.addArguments(new String[] { "--ignore-certificate-errors" });
		// options2.addArguments("Browser.setDownloadBehavior","allow");
//		options2.addArguments(
//				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.106 Safari/537.36");

		options2.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/120.0.0.0 Safari/537.36");

//		options2.setCapability("acceptInsecureCerts", false);

		driver = new ChromeDriver(options2);
//		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, 12);
		driver.get("https://yonobusiness.sbi/postybcug/");
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdownBasic2"))).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='New User Activation']"))).click();
//		
//		
//		Thread.sleep(3000);
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("window.scrollBy(0,1000)", new Object[0]);
//		Thread.sleep(3000);
//		WebElement ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"hollowbutton_without_dropdown ng-star-inserted\"]")));
//		ele.click();

		((JavascriptExecutor) driver).executeScript(
				"window.open('https://www.flipkart.com','_blank','toolbar=no,scrollbars=yes,resizable=yes,width=800,height=600');");
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		System.out.println("====================" + tabs.size());
		int size = tabs.size();
		driver.switchTo().window(tabs.get(size - 1));

		driver.switchTo().newWindow(WindowType.WINDOW);
		driver.get("https://google.com/");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q"))).sendKeys("Bhaba");

		tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Total Window Is === " + tabs.size());
		driver.switchTo().window((String) tabs.get(Integer.parseInt("0")));
		System.out.println("  == Window switched successfully == ");

	}

}
