package websiteTest;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class yesBank {

	public static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		driver = new ChromeDriver(capa());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://yesonline.yes.bank.in/index.html?module=login");
		Thread.sleep(5000);
//		String query = "document.querySelectorAll('flt-semantics-placeholder')[0]?.click()";
//		System.out.println("query ==" + query);
//		((JavascriptExecutor) driver).executeScript(query);
		Actions ac = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Thread.sleep(4000);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()=\"Login\"]")));
		ac.moveToElement(ele).perform();

		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),\"YES Online\")]")));
		ac.moveToElement(ele).click().perform();

	}

	public static ChromeOptions capa() {

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";

		ChromeOptions options2 = new ChromeOptions();

		options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
		options2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//
		options2.addArguments("--disable-blink-features=AutomationControlled");// for cloud flarecaptcha
		options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options2.addArguments("--ignore-certificate-errors");
		options2.addArguments("--test-type");
		options2.addArguments("--allow-running-insecure-content");
		options2.addArguments("--disable-web-security");
		options2.addArguments("--allow-insecure-localhost");
		options2.addArguments(new String[] { "--disable-notifications" });
		options2.addArguments(new String[] { "--disable-extentions" });
		options2.addArguments(new String[] { "disable-infobars" });
		options2.addArguments(new String[] { "disable-captcha" });
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
		options2.addArguments(new String[] { "--disable-popup-blocking" });
		options2.addArguments(
				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.106 Safari/537.36");
		options2.addArguments(new String[] { "--no-sandbox" });
		options2.addArguments("--no-proxy-server");


		// for location allow
//		options2.addArguments("--disable-blink-features=AutomationControlled");
//		options2.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));


		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		return options2;

	}

}
