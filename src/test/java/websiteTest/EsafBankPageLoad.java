package websiteTest;

import java.io.File;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EsafBankPageLoad {

	
	static WebDriver driver = null;
	public static void main(String[] args) throws InterruptedException {
		

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });

		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		driver = new ChromeDriver(options2);
		driver.manage().window().maximize();
//		driver.get("https://www.esafbank.com/");
		driver.get("https://www.etisalat.ae/en/index.html");
		
		
		
		
		System.out.println("Refreshed.........");
		
		driver.navigate().refresh();
		
		System.out.println("Done");

	}

}
