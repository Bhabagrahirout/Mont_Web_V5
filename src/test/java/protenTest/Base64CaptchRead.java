package protenTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.apmosys.framework.Captcha;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Base64CaptchRead {

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
		driver.get("https://myadhu.aadharhousing.com/vendor-onboarding-portal/public");

		Thread.sleep(4000);

		WebElement element = driver.findElement(By.xpath("//img[@alt=\"CAPTCHA\"]"));
		String img = downloadCaptcha(element);
		singleImage(img);
	}

	public static String downloadCaptcha(WebElement webElementVal) {

		String filePath = null;

		String imageUrl = webElementVal.getAttribute("src");
		System.out.println("Image src === " + imageUrl);

		String[] parts = imageUrl.split(",");
		if (parts.length == 2) {
			
			filePath = System.getProperty("user.dir") + "/captcha2.png";
			String base64Data = parts[1];
			byte[] decodedData = Base64.decodeBase64(base64Data);

			try {
				FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				fileOutputStream.write(decodedData);
				fileOutputStream.close();

				System.out.println("Captcha image Save to == : " + filePath);
			} catch (IOException e) {
				System.err.println("Error while saving the data URL to a file: " + e.getMessage());
			}
		} else {
			System.err.println("Invalid data URL format");
		}
		return filePath;
	}

	public static void singleImage(String img) {

		File file = new File(img);

		String r = RestAssured.given().multiPart("image", file).when().post("http://192.168.7.38:5010/predict").then()
				.extract().response().asString();

		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);

	}

}
