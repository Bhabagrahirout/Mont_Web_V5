package chromeTest;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.apmosys.framework.Framework;

public class ClearHistory {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));

		WebDriver driver = new ChromeDriver(options);
//		driver.get("https://licindia.in/");
		driver.get("https://www.axisamc.com/homepage");

		driver.manage().window().maximize();
		driver.switchTo().newWindow(WindowType.TAB);

		System.out.println("----------------In deleteHistory ----------------");
		driver.get("chrome://settings/clearBrowserData");
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
		        "document" +
		        "  .querySelector('settings-ui').shadowRoot" +
		        "  .querySelector('settings-main').shadowRoot" +
		        "  .querySelector('settings-basic-page').shadowRoot" +
		        "  .querySelector('settings-privacy-page').shadowRoot" +
		        "  .querySelector('settings-clear-browsing-data-dialog').shadowRoot" +
		        "  .querySelector('settings-checkbox#cookiesCheckboxBasic').shadowRoot" +
		        "  .querySelector('cr-checkbox').shadowRoot" +
		        "  .querySelector('div#checkbox')" +
		        "  .click();"
		);

		
		
		
		
		System.out.println("----------------In clearHistory ----------------");
		Thread.sleep(3000);
		js.executeScript("document.querySelector(\"settings-ui\").shadowRoot\n"
				+ "    .querySelector(\"settings-main\").shadowRoot\n"
				+ "    .querySelector(\"settings-basic-page\").shadowRoot\n"
				+ "    .querySelector(\"settings-privacy-page\").shadowRoot\n"
				+ "    .querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot\n"
				+ "    .querySelector(\"cr-button[id='clearButton']\").click();");
		Thread.sleep(2000);

	}

}
