package websiteTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AndhraGramyaBank {

	static WebDriver driver = null;
	static int i=63;

	public static void main(String[] args) throws InterruptedException {

//		while (true) {
			System.out.println("Start for ->" + i);
			String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
			ChromeOptions options2 = new ChromeOptions();
			options2.addArguments(new String[] { "--remote-allow-origins=*" });
//			options2.addArguments(new String[] { "--headless" });
			options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
			File file = new File(path);
			file.setExecutable(true);
			System.setProperty("webdriver.chrome.driver", path);
			driver = new ChromeDriver(options2);
			driver.manage().window().maximize();
			driver.get("https://onlinesgbank.in/");
			Thread.sleep(4000);
			WebElement element = driver.findElement(By.xpath("//img[@style=\"background: none;\"]"));
//			takeScreenShot(element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String txt = (String)js.executeScript("return document.querySelectorAll(\"span[class='MuiButton-label']\")[0].innerHTML;");

			System.out.println(txt);
			if(txt.equalsIgnoreCase("Login"))
			{
				System.out.println("Matched");
			}
			
			
			
//			driver.quit();
			i++;
//		}
	}

	public static void takeScreenShot(WebElement ele) {

		int year = Calendar.getInstance().get(1);
		String MonthName = new SimpleDateFormat("MMMM").format(new Date());
		int monthday = Calendar.getInstance().get(5);

		File screenshotAs = ele.getScreenshotAs(OutputType.FILE);
		
		String path="/home/apmosys/Pictures/AndhraGramyaBank/"+"img"+i+".png";
		
		File destination=new File(path);
		
		try {
			FileUtils.copyFile(screenshotAs, destination);
			System.out.println("File Save successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		String r = RestAssured.given().multiPart("image", screenshotAs) // 'file' is the form field name, and 'file' is
//																		// the File object
//				.when().post("http://192.168.7.38:5010/predict").then().extract().response().asString();
//
////		System.out.println("Response Body: " + r);
//		JsonPath js = new JsonPath(r);
//		String data = js.getString("text");
//		System.out.println("Text is === " + data);
//
//		WebElement ele2 = new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.visibilityOfElementLocated(By.id("captchaCode")));
//		ele2.sendKeys(data);
//
//		System.out.println(" Captch Send Successfully");

	}
}
