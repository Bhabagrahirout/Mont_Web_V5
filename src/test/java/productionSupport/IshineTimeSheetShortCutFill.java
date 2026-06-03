package productionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IshineTimeSheetShortCutFill {

	private static String iShineId;
	private static String iShinePassword;
	private static String reVerify;
	private static WebDriverWait wait;

	private static WebDriver driver;
	private static String eodThisMonthPath = "//a[text()=\"EOD This Month\"]";
	private static String createTimeSheetPath = "//div[text()=\" Create Timesheet \"]";
	private static int pending = 1;

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

		String path = "";
		String osnamedump = System.getProperty("os.name");
		System.out.println(osnamedump);
		String osname = "Ubuntu";
		if (osnamedump.toUpperCase().contains("WIN")) {
			osname = "Windows";
			path = System.getProperty("user.dir") + File.separator + "chromedriver.exe";
		} else {
			path = System.getProperty("user.dir") + File.separator + "chromedriver";
//			path="/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		}
		System.out.println(osname);
		System.setProperty("webdriver.chrome.driver", path);

		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });

		driver = new ChromeDriver(options);
		driver.get("https://ishine.apmosys.com/#/login");
		driver.manage().window().maximize();

		readAllCredentials();

		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(iShineId);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userPassword"))).sendKeys(iShinePassword);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\"]"))).click();
		String activationcode = JOptionPane.showInputDialog(null, "Enter otp");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userOtp")))
				.sendKeys(new CharSequence[] { activationcode });
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\"]"))).click();

		while (pending > 0) {
			System.out.println("Pending Time Sheet -----------------> " + pending);
			Thread.sleep(3000);
			ScrollIntoElement(eodThisMonthPath);
			click(eodThisMonthPath);
			pending = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(createTimeSheetPath)))
					.size();
			System.out.println("Pending Time Sheet After Checking --> " + pending);
			javaScriptClick2(createTimeSheetPath);
			ScrollIntoElement("//h6[text()=\"Activities\"]");

			Thread.sleep(3000);
			click("//label[text()=\"In Time\"]//following::mat-form-field");
			click("//span[text()=\"09\"]");
			click("(//label[text()='In Time']//following::mat-form-field)[2]");
			click("//span[text()='30']");
			click("(//label[text()='In Time']//following::mat-form-field)[3]");
			click("//span[text()='AM']");
			Thread.sleep(3000);
			click("(//label[text()='Out Time']//following::mat-form-field)[1]");
			click("//span[text()=\"06\"]");
			click("(//label[text()='Out Time']//following::mat-form-field)[2]");
			click("(//span[text()='30'])[2]");
			click("(//label[text()='Out Time']//following::mat-form-field)[3]");
			click("//span[text()='PM']");

			if(reVerify.equalsIgnoreCase("Y"))
			{
				JOptionPane.showMessageDialog(null, "ReverifyThenClick");
			}
			javaScriptClick("//button[@type=\"submit\"]");
			javaScriptClick("//button[text()=\"OK\"]");
//			javaScriptClick("href=\"#/home\"");
			Thread.sleep(2000);
		}
	}

	public static void readAllCredentials() throws FileNotFoundException, IOException {

		String path = System.getProperty("user.dir") + File.separator + "Config.properties";
		Properties prop = new Properties();
		prop.load(new FileInputStream(path));
		iShineId = prop.getProperty("iShineId");
		iShinePassword = prop.getProperty("iShinePassword");
		reVerify= prop.getProperty("reVerify","Y");
	}

	public static void select(String path, String text) {

		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		Select dropdown = new Select(ele);
		dropdown.selectByVisibleText(text);

	}

	public static void click(Object target) {
		try {
			WebElement ele = null;

			if (target instanceof String) {
				String path = (String) target;
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
			} else if (target instanceof WebElement) {
				ele = (WebElement) target;
			}

			ele.click();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Select the current Field!!");
		}
	}

	public static void javaScriptClick(Object target) {
		WebElement ele = null;
		try {
			if (target instanceof String) {
				String path = (String) target;
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
			} else if (target instanceof WebElement) {
				ele = (WebElement) target;
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].click()", ele);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Select the current Field!!");
		}
	}

	public static void javaScriptClick2(Object target) {
		WebElement ele = null;
		try {
			if (target instanceof String) {
				String path = (String) target;
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
			} else if (target instanceof WebElement) {
				ele = (WebElement) target;
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].click()", ele);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Select the current Field!!");
		}
	}

	public static void sendKeys(String path, String text) {

		try {
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
			ele.sendKeys(text);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Write the current input Field!!");
		}

	}

	public static void ScrollIntoElement(Object target) {
		try {

			WebElement ele = null;
			if (target instanceof String) {
				String path = (String) target;
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
			} else if (target instanceof WebElement) {
				ele = (WebElement) target;
			}

			((JavascriptExecutor) driver)
					.executeScript("arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", ele);
		} catch (Exception e) {
			System.out.println("Elememt Not Found,Scroll Manually");
		}
	}

}
