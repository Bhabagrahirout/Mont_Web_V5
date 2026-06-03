// 
// Decompiled by Procyon v0.5.36
// 

package com.apmosys.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.svg.SVGDocument;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Captcha {

	private static int captchCount = 0;
	
	public static String downloadCaptcha(WebElement webElementVal) {

		String filePath = System.getProperty("user.dir")+"/data/captcha.svg";

		// Get the image source URL from the src attribute
		String imageUrl = webElementVal.getAttribute("src");
		System.out.println("Image src === " + imageUrl);

		String[] parts = imageUrl.split(",");
		if (parts.length == 2) {
			String base64Data = parts[1];

			// Decode the base64 data
			byte[] decodedData = Base64.decodeBase64(base64Data);

			try {
				// Write the decoded data to a file
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

		String captcha = Captcha.readCaptcha(filePath);
		return captcha;
	}

	public static String readCaptcha(String svgFilePath) {

		String textContent = null;
		try {
			// Provide the path to your SVG file.
			// String svgFilePath =
			// "/home/prasad.deshmukh@apmosys.mahape/Downloads/captcha.svg";

			// Create a document factory and parse the SVG file.
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
			SVGDocument svgDocument = (SVGDocument) factory.createDocument(svgFilePath);

			// Extract and print the text content from the SVG document.
			textContent = svgDocument.getRootElement().getTextContent();
			textContent = textContent.replace(",", "");
			System.out.println("SVG Captcha Is === " + textContent);
//            Frame frame=new Frame();
//            JOptionPane.showMessageDialog(frame,textContent.replace(",",""));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return textContent;
	}
	
	// Proten Api Captch
	
	public static void retryFetchCaptcha(String objectTypeRDS, WebElement ele, String dataFieldRDS) {

		
		File screenshotAs = ele.getScreenshotAs(OutputType.FILE);

		String r = RestAssured.given().multiPart("image", screenshotAs) // 'file' is the form field name, and 'file' is
																		// the File object
				.when().post("http://192.168.7.38:5011/predict").then().extract().response().asString();

//		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
		WebElement ele2 = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[0])));
		if (objectTypeRDS.equalsIgnoreCase("js")) {
			((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + data + "'", ele2);
		} else if (objectTypeRDS.equalsIgnoreCase("action")) {
			Actions act = new Actions(Framework.driver);
			act.sendKeys(ele2, data).build().perform();
		} else {
			ele2.sendKeys(data);
		}
		try {
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[1]))).click();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			// error xpath found
			new WebDriverWait(Framework.driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[1])));
			if (captchCount < 3) {
				System.out.println("Invalid Captch.......");
				captchCount++;
				try {
					Framework.driver.manage().deleteAllCookies();
					System.out.println("----------Delete all cookies------------");
					Thread.sleep(3000);
					while (!Framework.actionRDS.equalsIgnoreCase("BROWSEURL")) {
						Framework.recordsetRDS.movePrevious();
						Framework.actionRDS = Framework.recordsetRDS.getField("Action");
					}
					Framework.recordsetRDS.movePrevious();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("Three try completed !!!!!!");
				Monitoring_FrameWork.SaveResult("true", "false");
			}

		} catch (Exception e) {
			System.out.println("-------Captch send successfully------");

		}

	}

}
