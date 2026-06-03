package protenTest;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.codec.binary.Base64;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.svg.SVGDocument;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Base62Captch12 {

	public static void main(String[] args) {
		
		
		
		try {
			ChromeOptions options2 = new ChromeOptions();
			options2.addArguments(new String[] { "--remote-allow-origins=*" });
			System.setProperty("webdriver.chrome.driver",
					"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");
			WebDriver driver = new ChromeDriver(options2);
//			driver.get("https://myadhu.aadharhousing.com/vendor-onboarding-portal/public"); // replace with your target
//			WebElement ele = driver
//					.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div/form[1]/div[2]/div[1]/img"));
			driver.get("https://netbanking.tngb.bank.in/"); // replace with your target
			WebElement ele = driver
					.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div[2]/div/div/div[3]/form/div/div[4]/div[2]/div/img"));
//			String pageSource = driver.getPageSource();
//			System.out.println(pageSource);
			Thread.sleep(3000);
			String filePath = null;
			String imageUrl = ele.getAttribute("src");
			System.out.println("Image src === " + imageUrl);

			String[] parts = imageUrl.split(",");
			if (parts.length == 2) {

				filePath = System.getProperty("user.dir") + "/captcha3.png";
				String base64Data = parts[1];
				byte[] decodedData = Base64.decodeBase64(base64Data);

				FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				fileOutputStream.write(decodedData);
				fileOutputStream.close();
				System.out.println("Captcha image Save to == : " + filePath);

			}

//			readData(filePath);
			readCaptcha(filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	static void readData(String imgPath) throws TesseractException
	{
		Mat src = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_GRAYSCALE);
		Imgproc.threshold(src, src, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		Imgproc.medianBlur(src, src, 3);
		Imgcodecs.imwrite("cleaned.png", src);

		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata/");
		tesseract.setLanguage("eng");
		String text = tesseract.doOCR(new File("cleaned.png"));

		System.out.println("Captcha text: " + text);

	}

	public static String readCaptcha(String svgFilePath) {

		String textContent = null;
		try {
			// Provide the path to your SVG file.

			// Create a document factory and parse the SVG file.
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
			SVGDocument svgDocument = (SVGDocument) factory.createDocument(svgFilePath);

			// Extract and print the text content from the SVG document.
			textContent = svgDocument.getRootElement().getTextContent();
			textContent = textContent.replace(",", "");
			System.out.println("SVG Captcha Is === " + textContent);
//	            Frame frame=new Frame();
//	            JOptionPane.showMessageDialog(frame,textContent.replace(",",""));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return textContent;
	}
}