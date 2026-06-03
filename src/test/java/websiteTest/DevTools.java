package websiteTest;

import java.io.File;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v96.page.Page;

public class DevTools {

	static WebDriver driver=null;
	public static void main(String[] args) {

		
		
			String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
			ChromeOptions options2 = new ChromeOptions();
			options2.addArguments(new String[] { "--remote-allow-origins=*" });
//		options2.addArguments(new String[] { "--headless" });
//		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
			File file = new File(path);
			file.setExecutable(true);
			System.setProperty("webdriver.chrome.driver", path);
			driver = new ChromeDriver(options2);
			driver.manage().window().maximize();
			driver.get("https://google.com");
			driver.get("https://onlinesgbank.in/");
		
		
//		driver.navigate().to("https://onlinesgbank.in/");

	}
	
	public static void main1() {
		
		
		org.openqa.selenium.devtools.DevTools devTools = ((HasDevTools) driver).getDevTools();
		devTools.createSession();

		// Enable DOM access
		devTools.send(Page.enable());

		// Inject script directly
		devTools.send(org.openqa.selenium.devtools.v96.runtime.Runtime.evaluate(
			    "document.querySelector('#open-button')?.click()",
			    Optional.empty(), // objectGroup
			    Optional.empty(), // includeCommandLineAPI
			    Optional.empty(), // silent
			    Optional.empty(), // contextId
			    Optional.empty(), // returnByValue
			    Optional.empty(), // generatePreview
			    Optional.empty(), // userGesture
			    Optional.empty(), // awaitPromise
			    Optional.empty(), // throwOnSideEffect
			    Optional.empty(), // timeout
			    Optional.empty(), // disableBreaks
			    Optional.empty(), // replMode
			    Optional.empty(), // allowUnsafeEvalBlockedByCSP
			    Optional.empty()  // uniqueContextId
			));
		
	}

}
