package websiteTest;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.apmosys.framework.Framework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class IndianBankCaptcha {

	public static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {

		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
		options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
		options2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options2.addArguments("--disable-blink-features=AutomationControlled");// for cloud flarecaptcha
		options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options2.addArguments("--test-type");
		options2.addArguments("--allow-running-insecure-content");
		options2.addArguments("--allow-insecure-localhost");
		options2.addArguments(new String[] { "--disable-notifications" });
		options2.addArguments(new String[] { "--disable-extentions" });
		options2.addArguments(new String[] { "disable-infobars" });
		options2.addArguments(new String[] { "disable-captcha" });
		options2.addArguments(new String[] { "--disable-popup-blocking" });
		options2.addArguments(
				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.106 Safari/537.36");

		options2.addArguments("--ignore-certificate-errors");
		options2.addArguments("--disable-web-security");

		options2.addArguments(new String[] { "--no-sandbox" });
		options2.addArguments("--no-proxy-server");
		options2.addArguments("--disable-blink-features=AutomationControlled");
		options2.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false); // Disable credential saving(Password Saveing)
		prefs.put("profile.default_content_setting_values.automatic_downloads", 1);// Disable credential

		options2.setExperimentalOption("prefs", prefs);

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);
		driver = new ChromeDriver(options2);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://online.indianbank.bank.in/RetailBanking/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"Login__Login__username_web\"]")))
				.sendKeys("30680508129");
		while (true) {
			WebElement element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//img[@id=\"Login__Login__LoginCaptchaImage\"]")));
			fetchCaptcha("", element, "//input[@id=\"Login__Login__LoginCaptchaInput\"]");

			JOptionPane.showConfirmDialog(null, "ok");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Validate']"))).click();
			Thread.sleep(3000);
		}
	}

	public static void fetchCaptcha(String objectTypeRDS, WebElement ele, String dataFieldRDS) {

		File screenshotAs = ele.getScreenshotAs(OutputType.FILE);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String r = RestAssured.given().multiPart("image", screenshotAs) // 'file' is the form field name, and 'file' is
																		// the File object
				.when().post("http://192.168.7.38:5011/predict").then().extract().response().asString();

//		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);

		WebElement ele2 = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS)));
		if (objectTypeRDS.equalsIgnoreCase("js")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + data + "'", ele2);
		} else if (objectTypeRDS.equalsIgnoreCase("action")) {
			Actions act = new Actions(driver);
			act.sendKeys(ele2, data).build().perform();
		} else if (objectTypeRDS.equalsIgnoreCase("upperCase")) {
			System.out.println("Text after upperCase === " + data.toUpperCase());
			ele2.sendKeys(data.toUpperCase());
		} else {
			ele2.sendKeys(data);
		}

		System.out.println(" Captch Send Successfully");

	}
}
