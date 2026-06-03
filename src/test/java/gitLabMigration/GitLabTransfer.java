package gitLabMigration;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

public class GitLabTransfer {

	static WebDriverWait wait = null;
	public static String repoName = "";
	public static WebDriver driver = null;

	public static String gitLabUser = "ricky.rawani@apmosys.com";
	public static String gitLabPass = "Amopr8572J";
	public static String projLocation = "";
	public static String downloadPath = "/home/apmosys/Downloads/GitLab_Work";
	static int i = 1;
	static String fromUrl = "http://192.168.12.83:10007/web_monitoring/banking/axis";
	static String toUrl = "http://192.168.12.135:8089/web_monitoring/banking/axis";

	public static void main(String[] args) throws InterruptedException {
		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });
//		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", downloadPath);
		options2.setExperimentalOption("prefs", prefs);

		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		driver = new ChromeDriver(options2);
		driver.manage().window().maximize();
		driver.get(fromUrl);

		wait = new WebDriverWait(driver, Duration.ofSeconds(22));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_login")))
				.sendKeys("ricky.rawani@apmosys.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password"))).sendKeys("Ricky@123");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type=\"submit\"]"))).click();

		// Group Work
		List<WebElement> group = wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@data-testid=\"group-name\"]")));
		System.out.println("Total repo Size = " + group.size());
		for (int j = 7; j < group.size(); j++) {

			WebElement groupEle = group.get(j);
			repoName = groupEle.getText();
			System.out.println("RepoName " + repoName);
			ScrollIntoElement(groupEle);
			groupEle.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label=\"Settings\"]")))
					.click();
			exportWork();
//			Thread.sleep(100000000);
			if (i == 1) {
				importFirstLogin();
				i++;
			} else {
				importExitLogin();

			}
			importProject();
			Thread.sleep(4000);
			switchOnWindow("0");
			System.out.println("Success =="+j);
			System.out.println(" ####################################### \n \n");

			if (i != 1) {
				driver.get(fromUrl);
			}
			group = wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@data-testid=\"group-name\"]")));

			if (j == group.size() - 1) {
				System.out.println("Current page finished");
				try {
					WebElement next = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//a[@aria-label=\"Go to next page\"]")));
					System.out.println("More page Present .........");
					next.click();
					Thread.sleep(5000);
					group = wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//a[@data-testid=\"group-name\"]")));

				} catch (Exception e) {
					System.out.println("No more page !!!!");
				}

			}
		}

	}

	public static void ScrollIntoElement(WebElement ele) {
		try {
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", ele);
			Thread.sleep(3000L);
		} catch (Exception e) {
			System.out.println("Elememt Not Found to scroll");
		}
	}

	public static void exportWork() {

		ScrollIntoElement(
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()=\"Advanced\"]"))));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//h4[text()=\"Advanced\"]/following::button)[1]"))).click();
		ScrollIntoElement(wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[text()=\"The following items will NOT be exported:\"]"))));
		try {
			System.out.println("Checking  Exported or not ?");
			WebDriverWait smallwait = new WebDriverWait(driver, Duration.ofSeconds(6));
			smallwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\"Export project\"]")))
					.click();
			Thread.sleep(2000);
			exportWork();
		} catch (Exception e) {
			System.out.println("Already click Export, now we export");
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=\"Download export\"]"))).click();

		try {
			Screen s34 = new Screen();
//			s34.wait((Object) "/home/apmosys/Pictures/Screenshot from 2025-09-06 18-55-56.png", 60.0);
			s34.exists((Object) "/home/apmosys/Pictures/Screenshot from 2025-09-06 20-40-38.png", 10.0).click();
		} catch (Exception e) {
			try {
				System.out.println("Second sikuli attempt");
				Screen s34 = new Screen();
				s34.exists((Object) "/home/apmosys/Pictures/Screenshot from 2025-09-06 18-55-56.png", 15.0).click();
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Click manuall keep button");
			}
		}

	}

	public static void importFirstLogin() {
		toUrl = "http://192.168.12.135:8089/web_monitoring/banking/axis";
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get(toUrl);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_login"))).sendKeys(gitLabUser);// User Name
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password"))).sendKeys(gitLabPass);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"sign-in-button\"]")))
				.click();

	}

	public static void importExitLogin() {
		switchOnWindow("1");
		driver.get(toUrl);
	}

	public static void importProject() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='new-project-button']")))
				.click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@data-testid='panel-link'])[3]")))
				.click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-track-property=\"gitlab_export\"]")))
				.click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(repoName);
		String latestFile = latestFile();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='file']"))).sendKeys(latestFile);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"import-project-button\"]"))).click();
	}

	public static void switchOnWindow(String no) {

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Total Window Is === " + tabs.size());
		driver.switchTo().window((String) tabs.get(Integer.parseInt(no)));
		System.out.println("  == Window switched successfully == ");

	}

	public static String latestFile() {

		String fileName;
		File folder = new File(downloadPath);
		File latestFile = Arrays.stream(folder.listFiles()).filter(File::isFile)
				.max(Comparator.comparing(File::lastModified)).orElse(null);

		fileName = latestFile.getName();
		System.out.println("Latest download file: " + fileName);

		String file = fileName.lastIndexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
		System.out.println("Latest download full Path: " + latestFile.getAbsolutePath());

		return latestFile.getAbsolutePath();

	}

	public static void deletelatestFile() {

		String fileName;
		String extension = ".tar.gz";
		File folder = new File(downloadPath);
		File latestFile = Arrays.stream(folder.listFiles()).filter(File::isFile)
				.max(Comparator.comparing(File::lastModified)).orElse(null);

		fileName = latestFile.getName();
		System.out.println("Latest download file: " + fileName);

		String file = fileName.lastIndexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;

		if (fileName.startsWith(file) && fileName.endsWith(extension)) {

			boolean success = latestFile.delete();
			if (success) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("File not deleted ");

			}

		}

	}

}
