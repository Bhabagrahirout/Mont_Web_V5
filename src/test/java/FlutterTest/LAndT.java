package FlutterTest;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

public class LAndT {

	public static WebDriver driver;

//	public static void main(String[] args) {
//
//		int shortMonth = LocalDate.now().getMonthValue();
//
////		if(LocalDate.now().getMonth().toString().equalsIgnoreCase(""))
////		{
//		System.out.println(shortMonth);
////		}
//	}

	public static void main(String[] args) throws InterruptedException {
		driver = new ChromeDriver(capa());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://personalloan.ltfinance.com/INSTA/");
		Thread.sleep(5000);
		String query = "document.querySelectorAll('flt-semantics-placeholder')[0]?.click()";
		System.out.println("query ==" + query);
		((JavascriptExecutor) driver).executeScript(query);
		Actions ac = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Thread.sleep(4000);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type=\"text\"]")));
		ac.moveToElement(ele).click().sendKeys("Bhaba").perform();

		String dob = "20-06-1995";
//		calender#upperbutoon#calenderDiv#allYearPath#prevousMonthPath#NextMonthpath
		String dataFiled = "//input[@aria-label=\"DD-MM-YYYY\"]#//flt-semantics[contains(text(), \"Select year\")]#//flt-semantics[@role=\"group\"]#//flt-semantics[@role=\"group\"]/child::flt-semantics[@role=\"button\"]#//flt-semantics[contains(text(), \"Previous month\")]#(//flt-semantics[contains(text(), \"Previous month\")]/following-sibling::flt-semantics)[1]";
		selectLAndTDob(dob, dataFiled);

	}

	public static void selectLAndTDob(String dob, String dataFiled) throws InterruptedException {

		String[] split = dataFiled.split("#");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[0])));
		ele.click();
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[1])));
		ele.click();
		Thread.sleep(4000);
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[2])));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,-180)", ele);

		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(split[3])));

		for (WebElement e : elements) {
			System.out.println(e.getText());

			if (e.getText().equalsIgnoreCase(dob.split("-")[2])) {
				e.click();
				break;
			}
		}

		Thread.sleep(2000);
		int currentMonthValue = LocalDate.now().getMonthValue();
		int MonthValue = Integer.valueOf(dob.split("-")[1]);
		int clickTime = currentMonthValue - MonthValue;

		System.out.println("Now click Time " + clickTime);
		if (clickTime > 0) {
			while (clickTime > 0) {
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[4])));
				ele.click();
				clickTime--;
				Thread.sleep(1500);
			}
		} else if (clickTime < 0) {

			while (clickTime < 0) {
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[5])));
				ele.click();
				clickTime++;
				Thread.sleep(1500);
			}
		}

		Thread.sleep(2000);
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//flt-semantics[contains(text(), \"" + dob.split("-")[0] + "\")]")));
		ele.click();
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//flt-semantics[text()=\"OK\"]")));
		ele.click();

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
