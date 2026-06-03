package websiteTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.apmosys.framework.Framework;

		
public class IndianBank5EmptyOp {

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

		String path = System.getProperty("user.dir")+"/chromedriver";
		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);
		WebDriver driver = new ChromeDriver(options2);
		driver.manage().window().maximize();
//		driver.get("https://www.netbanking.indianbank.in/jsp/startIBPreview.jsp");
//		Thread.sleep(7000);
//		driver.switchTo().newWindow(WindowType.TAB);
//		driver.get("https://www.indianbank.bank.in/");
		driver.get("https://www.libertyinsurance.in/");

	}

}
