package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class ScrollTable {

	public static void main(String[] args) throws InterruptedException, AWTException {

		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));

		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.htmlelements.com/react/demos/table/table-responsive/");

		driver.manage().window().maximize();
		
		
		WebElement ele=driver.findElement(By.xpath("//h1[text()='Smart.Table']"));
		Actions ac=new Actions(driver);
		ac.moveToElement(ele).doubleClick().perform();
		Thread.sleep(4000);

		//RB_PAGEDOWN
		final Robot rb = new Robot();
		rb.keyPress(34);
		rb.keyRelease(34);
		Thread.sleep(1000);
		rb.keyPress(34);
		rb.keyRelease(34);
		Thread.sleep(1000);
		rb.keyPress(34);
		rb.keyRelease(34);

//			final Robot robot = new Robot();
//			robot.keyPress(34);
//			robot.keyRelease(34);
		
//		rb.keyPress(38);
//		rb.keyRelease(38);
	}

//		WebElement ele=driver.findElement(By.xpath("// article[@class=\"wide-content\"]"));
//		
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop=arguments[1];",
//				ele, 300);

}
