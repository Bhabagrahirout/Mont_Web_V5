package FlutterTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.taskdefs.SendEmail;
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

public class HDFCInvestor {

	public static WebDriver driver;

	// first try with normal xapth, if not work then use area-label

	// https://flutter-angular.web.app/#/

	public static void main(String[] args) throws InterruptedException, AWTException {
		driver = new ChromeDriver(capa());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://investor-web.hdfcfund.com/");
		Thread.sleep(10000);
		String query = "document.querySelectorAll('flt-semantics-placeholder')[0]?.click()";
		System.out.println("query ==" + query);
		((JavascriptExecutor) driver).executeScript(query);
		Thread.sleep(4000);
		Actions ac = new Actions(driver);
		Robot rb = new Robot();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type=\"text\"]")));
		ac.moveToElement(ele).sendKeys("9773447989").perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[@role=\"checkbox\"]")))
				.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()=\"PROCEED\"]")))
				.click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type=\"text\"]")))
//		.sendKeys("9773447975");

//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()=\"User ID\"]")))
//				.click();
//		wait.until(
//				ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()=\"LOGIN WITH PAN\"]")))
//				.click();
		Thread.sleep(3000);
//		wait.until(ExpectedConditions
//				.visibilityOfElementLocated(By.xpath("(//input[@data-semantics-role=\"text-field\"])[1]")));
//				.sendKeys("Bhaba");
//		wait.until(ExpectedConditions
//				.visibilityOfElementLocated(By.xpath("(//input[@data-semantics-role=\"text-field\"])[2]")))
//				.sendKeys("123Vgvh");

//		WebElement ele = wait.until(ExpectedConditions
//				.presenceOfElementLocated(By.xpath("(//input[@data-semantics-role=\"text-field\"])[1]")));

//		Point location = ele.getLocation();
//		System.out.println(location.getX()+"=="+location.getY());
//		ac.moveByOffset(location.getX(), location.getY()).sendKeys("Bhaba").perform();
//		Thread.sleep(3000);
//		ac.moveByOffset(-location.getX(), -location.getY()).perform(); // reset (hacky)
//		Thread.sleep(3000);
//		ele = wait.until(ExpectedConditions
//				.presenceOfElementLocated(By.xpath("(//input[@data-semantics-role=\"text-field\"])[2]")));
//		location = ele.getLocation();
//		System.out.println(location.getX()+"=="+location.getY());
//		ac.moveByOffset(location.getX(),location.getY()).sendKeys("GCAPR8542F").perform();
//		System.out.println("Send successfully");

//		JOptionPane.showConfirmDialog(null, "Ok");
		ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type=\"text\"]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].focus();",ele);
		ele.sendKeys("123456");
//		ac.moveToElement(ele).click().sendKeys("Bhaba").perform();
//		Thread.sleep(6000);
//		ele.sendKeys("Bhaba");

	}

	public static ChromeOptions capa() {

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";

		ChromeOptions options2 = new ChromeOptions();

		options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
		options2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		options2.addArguments("--disable-blink-features=AutomationControlled");// for cloud flarecaptcha
		options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
//		options2.addArguments("--ignore-certificate-errors");
		options2.addArguments("--test-type");
		options2.addArguments("--allow-running-insecure-content");
//		options2.addArguments("--disable-web-security");
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

		if ("".equalsIgnoreCase("Y")) {
			options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		}

		// for location allow
		options2.addArguments("--disable-blink-features=AutomationControlled");
		options2.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false); // Disable credential saving(Password Saveing)
		prefs.put("profile.default_content_setting_values.automatic_downloads", 1);// Disable credential

		System.out.println("Default Download Set " + "");
		if ("".equalsIgnoreCase("N")) {
			System.out.println("Default Download Set " + "");
			prefs.put("plugins.always_open_pdf_externally", true); // make Chrome download PDFs instead of
																	// opening them
			prefs.put("download.prompt_for_download", false);
			prefs.put("download.directory_upgrade", true);
		}

		options2.setExperimentalOption("prefs", prefs);

		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		return options2;

	}

}
