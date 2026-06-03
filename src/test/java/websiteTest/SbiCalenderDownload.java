package websiteTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SbiCalenderDownload {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");

		ChromeOptions options = new ChromeOptions();

		// Optional
		options.addArguments("--remote-allow-origins=*");

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("plugins.always_open_pdf_externally", true);
		prefs.put("download.prompt_for_download", false);
		prefs.put("download.directory_upgrade", true);

		options.setExperimentalOption("prefs", prefs);

		WebDriver driver = new ChromeDriver(options);

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().window().maximize();

		driver.get("https://sbi.bank.in/web/personal-banking/information-services/bank-calendar-holiday-list");

		// Better wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='January']"))).click();
		capturePdfScreenshot(new File("/home/apmosys/Downloads/Holiday_List_January_2025.pdf"));
	}
	
	
	public static void capturePdfScreenshot(File file) {
	    try (PDDocument document = PDDocument.load(file)) {

	        PDFRenderer pdfRenderer = new PDFRenderer(document);

	        // Convert first page to image
	        BufferedImage image = pdfRenderer.renderImageWithDPI(0, 150);

	        // Save as PNG
	        File outputFile = new File("/home/apmosys/Downloads/pdf_screenshot.png");
	        ImageIO.write(image, "png", outputFile);

	        System.out.println("📸 PDF screenshot saved: " + outputFile.getAbsolutePath());

	    } catch (Exception e) {
	        System.out.println("❌ Error capturing PDF screenshot: " + e.getMessage());
	    }
	}

}
