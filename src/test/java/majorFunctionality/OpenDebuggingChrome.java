package majorFunctionality;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class OpenDebuggingChrome {

	static WebDriver driver = null;
//	static String debuggerUrl = "127.0.0.1:9222";
	static String debuggerUrl = "localhost:9222";
//	static String chromecmd = "google-chrome --remote-debugging-port=9222";
	static String chromecmd="google-chrome --remote-debugging-port=9222 --user-data-dir=\"/home/apmosys/.config\" --profile-directory=\"Default\"";
//			#chromecmd=google-chrome --remote-debugging-port=9222 --user-data-dir=\"/home/apmosys/.config/google-chrome\" --profile-directory=\"Default\"

	public static void main(String[] args) throws IOException, InterruptedException {
		ManualChromeBrowser();

	}

	public static void ManualChromeBrowser() throws IOException, InterruptedException 
	{
		Process p = Runtime.getRuntime().exec(chromecmd);
		System.out.println("Chrome launched successfully!");
		Thread.sleep(5000);
		String driverPath = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		
		
//		String osName = Framework.getosname();
//
//		String driverPath = "";
//		if (osName.equalsIgnoreCase("Windows")) {
//			driverPath = Framework.driverpath + File.separator + "chromedriver.exe";
//		} else {
//			driverPath = Framework.driverpath + File.separator + "chromedriver";
//		}

		System.setProperty("webdriver.chrome.driver", driverPath);
		 driver = new ChromeDriver(setManualChromeCapability());
		 driver.get("https://neetcode.io/practice");
		 System.out.println("----------------");
	}

	public static ChromeOptions setManualChromeCapability()
	{
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress", debuggerUrl);
//		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//		options.addArguments(new String[] { "--disable-notifications" });
		return options;
	}
}
