package majorFunctionality;

import java.io.File;
import java.time.Duration;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import groovyjarjarantlr4.v4.codegen.model.Action;

public class FlutterApplication {

	public static void main(String[] args) throws InterruptedException {

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });

		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		ChromeDriver driver = new ChromeDriver(options2);
		driver.manage().window().maximize();// document.querySelector('flt-semantics-placeholder')?.click();
		driver.get("https://bandhanmutual.com/partner/");

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		JOptionPane.showConfirmDialog(null, "ok");
		js.executeScript("document.querySelector('flt-semantics-placeholder')?.click();");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(16));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span)[3]")));
		js.executeScript("arguments[0].style.border='3px solid green'", new Object[] { element });
		System.out.println(element.getText() + "----------------");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span)[3]/following::input")));
		System.out.println(element.getAttribute("aria-label") + "-------------");
		Actions ac = new Actions(driver);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span)[3]/following::input")));
		ac.moveToElement(element).click().sendKeys("12333")
//		  .sendKeys(new CharSequence[] { "1233" })
				.build().perform();
//		ac.moveToElement(element).sendKeys(new CharSequence[] { "1222" });

	}

}
