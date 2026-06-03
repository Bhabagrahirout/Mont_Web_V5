package protenTest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CaptureImage {

	
	
	private static int count=74;
	public static void main(String[] args) throws InterruptedException {
		
		while(true)
		{
			System.out.println("Start......");
			readImage();
			System.out.println("End......");
		}
	}
	public static void readImage() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");
		ChromeOptions op = new ChromeOptions();
		op.addArguments(new String[] { "--remote-allow-origins=*" });
		op.addArguments(new String[] { "--headless" });
		
		WebDriver driver = new ChromeDriver(op);
		driver.get("https://onlineservices.tin.egov-nsdl.com/TIN/UnAuthorizedView.do;jsessionid=7721B09020E6DED61019028AE54BD4AB.tomcat30");
		driver.manage().window().maximize();
		
		Thread.sleep(2000);
		
		String path = "/home/apmosys/Pictures/Egov_NSDL/"+count+".png";
		File destination = new File(path);
		File screenshotAs = driver.findElement(By.id("Captcha")).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(screenshotAs, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		count++;
		driver.close();
		
	}

}
