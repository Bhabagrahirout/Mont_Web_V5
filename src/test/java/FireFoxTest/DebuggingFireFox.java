package FireFoxTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DebuggingFireFox {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver",
				"/home/apmosys/Desktop/Selenium Tools/geckodriver-v0.34.0-linux64/geckodriver");

				FirefoxOptions options = new FirefoxOptions();

				// use your real profile
				options.setProfile(
				 new FirefoxProfile(
				  new java.io.File("/home/apmosys/.mozilla/firefox/s3v60wo7.default-release")
				 )
				);

				// disable selenium fingerprint
				options.addPreference("dom.webdriver.enabled", false);
				options.addPreference("useAutomationExtension", false);

				WebDriver driver = new FirefoxDriver(options);

				// open site
				driver.get("https://www.yesbank.in");

	}

}
