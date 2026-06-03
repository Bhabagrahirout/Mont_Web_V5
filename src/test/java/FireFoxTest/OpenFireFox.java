package FireFoxTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OpenFireFox {

	public static void main(String[] args) {


		System.setProperty("webdriver.gecko.driver",
				"/home/apmosys/Desktop/Selenium Tools/geckodriver-v0.34.0-linux64/geckodriver");
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("--profile", "/home/apmosys/.mozilla/firefox/s3v60wo7.default-release");
	
		options.addPreference("dom.webdriver.enabled", false);
		options.addPreference("useAutomationExtension", false);

		
//		options.addPreference("dom.webdriver.enabled", false);
//		options.addPreference("useAutomationExtension", false);
//		options.addPreference("privacy.trackingprotection.enabled", false);
//		options.addPreference("privacy.resistFingerprinting", false);

//		options.addPreference("general.useragent.override",
//		"Mozilla/5.0 (X11; Linux x86_64) Gecko/20100101 Firefox/121.0");


		
//		options.addPreference("network.http.referer.XOriginPolicy", 0);
//		options.addPreference("network.http.referer.XOriginTrimmingPolicy", 0);
//		options.addPreference("privacy.trackingprotection.enabled", false);

		WebDriver driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(90L, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(90L, TimeUnit.SECONDS);
		driver.manage().window().maximize();
//		driver.get("https://www.google.com/");
		driver.get("https://www.saucedemo.com/");
//		driver.get("https://www.yes.bank.in/");
//		driver.get("https://www.youtube.com");
//		driver.get("https://mail.apmosys.com/webmail/");

	

	}

}
