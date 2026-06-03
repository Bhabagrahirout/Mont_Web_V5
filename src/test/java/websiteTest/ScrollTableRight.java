package websiteTest;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ScrollTableRight {

	public static void main(String[] args) throws InterruptedException {

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });

		options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		ChromeDriver driver = new ChromeDriver(options2);
		driver.manage().window().maximize();
		driver.get("https://mui.com/material-ui/react-table/");
		driver.manage().window().maximize();

		Thread.sleep(6000);
//		JOptionPane.showMessageDialog(null, "Ok");
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		WebElement ele = wait
//				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@class=\"MuiTableRow-root css-qniy3t\"])[10]")));
//		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='2px solid red'", ele);
//		((JavascriptExecutor)driver).executeScript("arguments[0].scrollLeft=arguments[1];", ele, 250);

		
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector(\"settings-ui\").shadowRoot\n"
				+ "    .querySelector(\"settings-main\").shadowRoot\n"
				+ "    .querySelector(\"settings-basic-page\").shadowRoot\n"
				+ "    .querySelector(\"settings-privacy-page\").shadowRoot\n"
				+ "    .querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot\n"
				+ "    .querySelector(\"cr-button[id='clearButton']\").click();");
		Thread.sleep(2000);
		
		
		
	}

}
