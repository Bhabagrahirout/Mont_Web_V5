package websiteTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AdharSahayogiPortal {

	public static void main(String[] args) throws InterruptedException {

		int i = 1696;

		while (true) {
			
			System.out.println("Start for --> " + i);
			String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
			ChromeOptions options2 = new ChromeOptions();
			options2.addArguments(new String[] { "--remote-allow-origins=*" });
//			options2.addArguments(new String[] { "--headless" });

			options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
			File file = new File(path);
			file.setExecutable(true);
			System.setProperty("webdriver.chrome.driver", path);

			ChromeDriver driver = new ChromeDriver(options2);
			driver.manage().window().maximize();
			driver.get("https://myadhu.aadharhousing.com/vendor-onboarding-portal/public");

			Thread.sleep(4000);
			WebElement element = driver.findElement(By.xpath("//img[@alt=\"CAPTCHA\"]"));
//			takeScreenShot(element, i);
			srctakeScreenShot(element, i);
			driver.quit();
			i++;
			
		}

	}
	public static void srctakeScreenShot(WebElement ele, int i) {
		
		String path = "/home/apmosys/Pictures/adharHousing_DecodeImage/" + "img" + i + ".png";
		String imageUrl = ele.getAttribute("src");
		System.out.println("Image src === " + imageUrl);

		String[] parts = imageUrl.split(",");
		if (parts.length == 2) {
			String base64Data = parts[1];

			byte[] decodedData = Base64.decodeBase64(base64Data);

			try {
				FileOutputStream fileOutputStream = new FileOutputStream(path);
				fileOutputStream.write(decodedData);
				fileOutputStream.close();

				System.out.println("Captcha image Save to == : " + path);
			} catch (IOException e) {
				System.err.println("Error while saving the data URL to a file: " + e.getMessage());
			}
		} else {
			System.err.println("Invalid data URL format");
		}
		
	}

	public static void takeScreenShot(WebElement ele, int i) {

		File screenshotAs = ele.getScreenshotAs(OutputType.FILE);
		String path = "/home/apmosys/Pictures/aadharhousing/" + "img" + i + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(screenshotAs, destination);
			System.out.println("File Save successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}

//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		String r = RestAssured.given().multiPart("image", screenshotAs) // 'file' is the form field name, and 'file' is
//																		// the File object
//				.when().post("http://192.168.7.38:5010/predict").then().extract().response().asString();
//
////		System.out.println("Response Body: " + r);
//		JsonPath js = new JsonPath(r);
//		String data = js.getString("text");
//		System.out.println("Text is === " + data);
//
//		WebElement ele2 = new WebDriverWait(driver, 10)
//				.until(ExpectedConditions.visibilityOfElementLocated(By.id("captchaCode")));
//		ele2.sendKeys(data);
//
//		System.out.println(" Captch Send Successfully");

	}
}
