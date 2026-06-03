package websiteTest;

import java.io.File;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.apmosys.framework.Framework;

public class WhatsAppTest {
	
	
	public static void main(String[] args) {
		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
//		options2.addArguments(new String[] { "--headless" });

		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		ChromeDriver driver = new ChromeDriver(options2);
		driver.manage().window().maximize();
		driver.get("https://web.whatsapp.com/");
		
		driver.quit();
	
	}

}
