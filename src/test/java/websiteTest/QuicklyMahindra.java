package websiteTest;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.apmosys.framework.Framework;

public class QuicklyMahindra {
	private static WebDriver driver;
	public static String fastPageLoad = "y";

	public static void main(String[] args) {

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

		// options2.addArguments(new String[] { "window-size=1920,1080" });
//		 new added for disable password window popup
//		options2.addArguments(new String[] { "--disable-save-password-bubble" });
//		

		// for location allow
		options2.addArguments("--disable-blink-features=AutomationControlled");
		options2.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false); // Disable credential saving(Password Saveing)
		prefs.put("profile.default_content_setting_values.automatic_downloads", 1);// Disable credential

		options2.setExperimentalOption("prefs", prefs);

		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		String startBrowserStatus = "Y";
		if (startBrowserStatus.equalsIgnoreCase("Y")) {
			try {
				driver = new ChromeDriver(options2);
				driver.manage().window().maximize();
				try {
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt("45")));
				} catch (Exception e) {
					System.out.println("...........");
					driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
				}

				driver.get("https://www.quiklyz.com/videos");
				Thread.sleep(3000);
				
				System.out.println("----------------In clearHistory ----------------");
				driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("document.querySelector(\"settings-ui\").shadowRoot\n"
						+ "    .querySelector(\"settings-main\").shadowRoot\n"
						+ "    .querySelector(\"settings-basic-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-privacy-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot\n"
						+ "    .querySelector(\"cr-button[id='clearButton']\").click();");
				Thread.sleep(2000);
				
//				driver.switchTo().newWindow(WindowType.TAB);
//				closePreviousWinow();
				Thread.sleep(3000);
				driver.get("https://www.quiklyz.com/car-subscription-vs-car-loan");
				
//				Thread.sleep(3000);
//				driver.switchTo().newWindow(WindowType.TAB);
//				closePreviousWinow();
//				Thread.sleep(3000);
//				driver.get("https://www.quiklyz.com/car-leasing/check-eligibility");
//				
//				Thread.sleep(3000);
//				driver.switchTo().newWindow(WindowType.TAB);
//				closePreviousWinow();
//				Thread.sleep(3000);
//				driver.get("https://www.quiklyz.com/car-leasing/compare");
//				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void closePreviousWinow() {
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> al=new ArrayList<>(windowHandles);
		driver.switchTo().window(al.get(0));
		driver.close();
		driver.switchTo().window(al.get(1));
		driver.manage().deleteAllCookies();
	}

}
