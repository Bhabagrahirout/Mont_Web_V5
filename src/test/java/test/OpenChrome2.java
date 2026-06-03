package test;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OpenChrome2 {
	
	
	
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");


		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));
		
		WebDriver driver = new ChromeDriver(options);
//		driver.get("https://licindia.in/");
		driver.get("https://www.axisamc.com/homepage");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object res = js.executeAsyncScript("navigator.geolocation.getCurrentPosition(arguments[0]);");

		System.out.println(res);
		String latitude=res.toString().split("latitude=")[1].split(",")[0];
		String longitude=res.toString().split("longitude=")[1].split(",")[0];
		System.out.println(latitude); // The result should be a Map containing latitude and longitude
		System.out.println(longitude);
		driver.quit();
	
		
		
	}

}
