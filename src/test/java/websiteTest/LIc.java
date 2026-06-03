package websiteTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

public class LIc {

	static WebDriver driver = null;

	public static void main(String[] args) throws InterruptedException, AWTException, FindFailed {

		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");

		ChromeOptions options2 = new ChromeOptions();
		options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
		options2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
		options2.addArguments("--disable-blink-features=AutomationControlled");// for cloud flarecaptcha
		options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
//		options2.addArguments("--ignore-certificate-errors");
		options2.addArguments("--test-type");
		options2.addArguments("--allow-running-insecure-content");
//		options2.addArguments("--disable-web-security");
		
		options2.addArguments("--allow-insecure-localhost");
		options2.addArguments(new String[] { "--disable-notifications" });
		options2.addArguments(new String[] { "--disable-extentions" });
		options2.addArguments(new String[] { "disable-infobars" });
		options2.addArguments(new String[] { "disable-captcha" });
		options2.addArguments(new String[] { "--disable-popup-blocking" });
//		options2.addArguments(
//				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.106 Safari/537.36");
		options2.addArguments(new String[] { "--no-sandbox" });
		options2.addArguments("--no-proxy-server");

		WebDriver driver = new ChromeDriver(options2);
		driver.get("https://licindia.in/");

		System.out.println("Done");
		
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_T);
		r.keyRelease(KeyEvent.VK_T);
		r.keyRelease(KeyEvent.VK_CONTROL);
		
		StringSelection s = new StringSelection("https://ebiz.licindia.in/D2CPM/#DirectPay");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		
		//https://ebiz.licindia.in/D2CPM/#DirectPay
		
		Screen ss = new Screen();
		ss.exists((Object) "/home/apmosys/Pictures/Screenshot from 2025-05-19 16-42-55.png", 60.0).click();
		
		JOptionPane.showMessageDialog(null, "ok");
		ss.wheel(Button.WHEEL_DOWN, 10);
		Thread.sleep(3000);
		ss.exists((Object) "/home/apmosys/Pictures/Screenshot from 2025-05-19 16-46-13.png").click();
		
		
		

	}

	public static void main11(String[] args) {

		try {
			Process p = Runtime.getRuntime().exec(
					"google-chrome --remote-debugging-port=9222 --user-data-dir=/home/apmosys/.config/google-chrome");
			System.out.println("Chrome launched successfully!");
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");
		String debuggerAddress = "127.0.0.1:9222";

		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
		options2.setExperimentalOption("debuggerAddress", debuggerAddress);
		options2.addArguments("--disable-blink-features=AutomationControlled");

		ChromeDriver driver = new ChromeDriver(options2);
		driver.get("https://licindia.in/");
		System.out.println("Done");

	}

	public static void main1(String[] args) throws InterruptedException {
		//

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
		options2.addArguments("--disable-blink-features=AutomationControlled");

		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		driver = new ChromeDriver(options2);
		driver.get("https://www.hdfcfund.com/product-solutions/overview/hdfc-silver-etf/regular");
		driver.manage().window().maximize();

		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://licindia.in/");

//		WebDriverWait wait = new WebDriverWait(driver, 60);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Close']"))).click();
//
//		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"meter\"]")));
//		Scroll(ele);
//		Thread.sleep(4000);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Calculate Premium ']/i")))
//				.click();
//
//		wait.until(ExpectedConditions.alertIsPresent()).accept();

//		Thread.sleep(4000);
//		SwichtWindow("2");
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("da_button-1045-btnInnerEl")));
//		SwichtWindow("1");

//		Thread.sleep(5000);
//		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt=\"Banner 7\"]")));
//		Scroll(ele);https://ebiz.licindia.in/D2CPM/#DirectPay
//
//		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()=' Pay Premium ']")));
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", new Object[] { ele });
//
//		Thread.sleep(5000);
//		driver.findElement(By.id("da_button-1093-btnEl")).click();
//
//		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("content")));
//		Scroll(ele);

	}

	public static void Scroll(WebElement ele) {

		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", ele);

	}

	public static void SwichtWindow(String no) {

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Total Window Is === " + tabs.size());
		driver.switchTo().window((String) tabs.get(Integer.parseInt(no)));
		System.out.println("  == Window switched successfully == ");

	}

}
