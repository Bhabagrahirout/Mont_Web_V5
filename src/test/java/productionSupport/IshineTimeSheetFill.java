package productionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IshineTimeSheetFill {

	private static String iShineId;
	private static String iShinePassword;
	private static WebDriverWait wait;
	private static String project;
	private static String clientName;
	private static String clientLocation;
	private static String teamName;
	private static String activity;
	private static String description;
	private static int count = 0;

	private static String projectDivPath = "//label[text()=\"Project\"]/following::mat-select";

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

		WebDriver driver = new ChromeDriver(options);
		driver.get("https://ishine.apmosys.com/#/login");
		driver.manage().window().maximize();

		readAllCredentials();

		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(iShineId);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userPassword"))).sendKeys(iShinePassword);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\"]"))).click();
//		Frame frame = new Frame();
//		frame.setVisible(false);
//		frame.toFront();
		String activationcode = JOptionPane.showInputDialog(null, "Enter otp");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userOtp")))
				.sendKeys(new CharSequence[] { activationcode });
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\"]"))).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@title=\"Timesheets\"]"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"forSelf\"]"))).click();
		select("//select[@id=\"dayTypeCursor\"]", "Working");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Open calendar\"]")))
				.click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//td[@role='gridcell' and not(@aria-disabled)])[1]"))).click();
		click(projectDivPath);
		String projectPath = "//span[contains(text(),' " + project + " ')]";
		click(projectPath);
		Thread.sleep(3000);
		///////////////////////////////////////////////////
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//label[text()=\"In Time\"]//following::mat-form-field")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()=\"09\"]"))).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//label[text()='In Time']//following::mat-form-field)[2]")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='30']"))).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//label[text()='In Time']//following::mat-form-field)[3]")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='AM']"))).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//label[text()='Out Time']//following::mat-form-field)[1]")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()=\"06\"]"))).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//label[text()='Out Time']//following::mat-form-field)[2]")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[text()='30'])[2]"))).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//label[text()='Out Time']//following::mat-form-field)[3]")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='PM']"))).click();
		///////////////////////////////////////////////////

		clientName = "//span[contains(text(),' " + clientName + " ')]";
		clientLocation = "//span[contains(text(),' " + clientLocation + " ')]";
		teamName = "//span[contains(text(),' " + teamName + " ')]";
		activity = "//span[contains(text(),'" + activity + "')]";
		description = "//span[contains(text(),'" + description + "')]";
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//label[text()=\"Client Name\"]/following-sibling::mat-select")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientName))).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//label[text()=\"Client Location \"]/following-sibling::mat-select"))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientLocation))).click();

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//label[text()=\"Team Name\"]/following-sibling::mat-select")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(teamName))).click();

		String act = (activity.split("#").length == 1) ? activity.split("#")[0] : activity.split("#")[count];
		count++;

		System.out.println("act :" + act);
		select("(//select[@id=\"dayTypeCursor\"])[2]", act);
	}

	public static void readAllCredentials() throws FileNotFoundException, IOException {

		String path = System.getProperty("user.dir") + File.separator + "Config.properties";
		Properties prop = new Properties();
		prop.load(new FileInputStream(path));
		iShineId = prop.getProperty("iShineId");
		iShinePassword = prop.getProperty("iShinePassword");
		project = prop.getProperty("project");
		clientName = prop.getProperty("clientName");
		clientLocation = prop.getProperty("clientLocation");
		teamName = prop.getProperty("teamName");
		activity = prop.getProperty("activity");
		description = prop.getProperty("project");
	}

	public static void select(String path, String text) {

		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		Select dropdown = new Select(ele);
		dropdown.selectByVisibleText(text);

	}

	public static void click(String path) {

		try {
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
			ele.click();

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

}
