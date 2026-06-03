package edgeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.apmosys.framework.Framework;
import com.apmosys.framework.Functions;

public class edgeBrowserLaunch {
	
	
	public static void main(String[] args) throws IOException {
		
		
		
		
		String path = "";
		String driverpath = null;
		String osname = null;
		if (osname.equalsIgnoreCase("Windows")) {
			path = driverpath + "/MicrosoftWebDriver.exe";
		} else {
			path = driverpath + "/msedgedriver";
		}

		System.setProperty("webdriver.edge.driver", path);
		FileInputStream fis2 = new FileInputStream(Functions.path2);
		Properties prop = new Properties();
		prop.load(fis2);
		String headless = prop.getProperty("headless");
		EdgeOptions op = new EdgeOptions();
		op.addArguments(new String[] { "--remote-allow-origins=*" });
		if (headless.equalsIgnoreCase("true")) {
			System.out.println("We are in Headless");
			op.addArguments(new String[] { "headless" });
		}
		Framework.driver = (WebDriver) new EdgeDriver(op);
		Framework.driver.manage().window().maximize();
		Framework.driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
		Framework.browsersts = 1;
	}

}
