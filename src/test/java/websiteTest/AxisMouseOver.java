package websiteTest;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AxisMouseOver {


	static WebDriver driver = null;
	
	
//	public static void main(String[] args) {
//		String str=null;
//		
//		str+="Grahi";
//		System.out.println(str);
//	}

	public static void main(String[] args) throws InterruptedException {

		try {
			String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
			ChromeOptions options2 = new ChromeOptions();
			options2.addArguments(new String[] { "--remote-allow-origins=*" });

			options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
			File file = new File(path);
			file.setExecutable(true);
			System.setProperty("webdriver.chrome.driver", path);

//			Map<String, Object> prefs = new HashMap<>();
//			prefs.put("plugins.always_open_pdf_externally", true); // make Chrome download PDFs instead of opening them
//			prefs.put("download.directory_upgrade", true);
//			prefs.put("download.prompt_for_download", false);
//			prefs.put("safebrowsing.enabled", true); // enables safe browsing
//			prefs.put("safebrowsing.disable_download_protection", true); // disables the "Keep" prompt

//			options2.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(options2);
			driver.manage().window().maximize();
//			driver.get("https://www.axisbank.com/");
//			driver.get("http://speedtest.tele2.net/1MB.zip");
			driver.get("https://mail.apmosys.com/webmail/");
//			driver.close();

			new WebDriverWait(driver, Duration.ofSeconds(9)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("hhhh")));
			driver.findElement(By.xpath("jsdjjd"));
			Thread.sleep(10000);

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("-------------------");
			String errorType = e.getClass().getName();
			String errorMessage  = e.getMessage().split("\n")[0];
			if(e.getCause() != null) {
				System.out.println("Root Error present");
			    errorMessage =errorMessage+"\n Root Cause    :"+ e.getCause().getClass().getSimpleName() + " : " + e.getCause().getMessage().split("\n")[0];
			}
			
			System.out.println("errorType ====" + errorType);
			System.out.println();
			System.out.println(errorMessage);

		}

	}

}
