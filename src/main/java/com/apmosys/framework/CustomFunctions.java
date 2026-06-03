package com.apmosys.framework;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomFunctions extends Framework {

	public static void checkerImpsRtgs(String propertyValueRDS, String dataFieldRDS, String objectTypeRDS) {

		// propertyValue= arrows xpath
		// dataField= 1) paymentModexpath 2) paymentMode
		// objectType= 1)name 2) price
		boolean flag = true;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		List<WebElement> arrows = driver.findElements(By.xpath(propertyValueRDS.trim()));

		System.out.println("All arrows in table ==== " + arrows.size());

		for (int i = 0; i < arrows.size(); i++) {

			String beneficiaryNameXpath = "//div[@row-id='" + i + "']/div[3]";
			String beneficiaryName = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(beneficiaryNameXpath))).getText();
			System.out.println("Beneficiary Name Is == " + beneficiaryName);

			if (beneficiaryName.equalsIgnoreCase("BIBHU")) {

//				String arrowXpath="(//div[@row-id='"+i+"'])[1]/div/div/span/span/span[2]/span";
				String arrowXpath = "(//div[@row-id='" + i + "'])[1]/div/span/span[2]/span";
				WebElement arrowElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(arrowXpath)));
				arrowElement.click();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				String paymentMode = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.trim()))).getText();
				System.out.println("Payment Mode is == " + paymentMode);
//			    check imps or rtgs	
				if (paymentMode.equalsIgnoreCase(objectTypeRDS.trim())) {
//					String checkBoxXpath="(//div[@row-id='"+i+"'])[1]/div/div/div/div/div[2]/input";

					String checkBoxXpath = "(//div[@row-id='" + i + "'])[1]/div[2]/div/div/span/input";
					// arrowElement.click();
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkBoxXpath))).click();
					flag = false;
					break;

				} else {

					String arrowXpath1 = "(//div[@row-id='" + i + "'])[1]/div/span/span[1]/span";
					WebElement arrowElement1 = wait
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(arrowXpath1)));
					arrowElement1.click();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		if (flag) {
			JOptionPane.showMessageDialog(new Frame(), "Payment Mode " + objectTypeRDS + " Not Found In The Table...");
		}
	}

	public static void clickOnGrowthAndIDCW(String dataFiledRDS) {

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(15));

		String filePath = System.getProperty("user.dir") + File.separator + "GrowthAndIDCWStatus.txt";
		try {
			// Read the content of the file
			String content = readFile(filePath);
			if (content.equals("Growth")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFiledRDS.split("#")[0].trim())))
						.click();
				writeFile(filePath, "IDCW");
			} else if (content.equals("IDCW")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFiledRDS.split("#")[1].trim())))
						.click();
				writeFile(filePath, "Growth");
			}
		} catch (IOException e) {
			System.err.println("An error occurred: " + e.getMessage());
		}

	}

	public static void clickDailyMonthlyWeekly(String dataFiledRDS) {

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(15));

		String filePath = System.getProperty("user.dir") + File.separator + "DailyMonthlyWeeklyStatus.txt";
		try {
			// Read the content of the file
			String content = readFile(filePath);
			if (content.equals("Daily")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFiledRDS.split("#")[0].trim())))
						.click();
				writeFile(filePath, "Monthly");
			} else if (content.equals("Monthly")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFiledRDS.split("#")[1].trim())))
						.click();
				writeFile(filePath, "Weekly");
			} else if (content.equals("Weekly")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFiledRDS.split("#")[2].trim())))
						.click();
				writeFile(filePath, "Daily");
			}
		} catch (IOException e) {
			System.err.println("An error occurred: " + e.getMessage());
		}

	}

	// Method to read the content of the file
	private static String readFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		String content = null;
		if (Files.exists(path)) {
			content = new String(Files.readAllBytes(path)).trim();
		} else {
			System.out.println("File not found.............\n" + filePath);
		}
		return content;
	}

	// Method to write content to the file
	private static void writeFile(String filePath, String content) throws IOException {
		Path path = Paths.get(filePath);
		Files.write(path, content.getBytes());
		System.out.println("write done === " + content);

	}

	public static void axisDownloadedFiles(String fileName, String dataFieldRDS) {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int flag = 0;
		File[] listFiles = new File(dataFieldRDS).listFiles();
		String extension = fileName.split("#")[1];
		System.out.println("extension is == " + extension);
		System.out.println("file name == " + fileName.split("#")[0]);
		if (listFiles.length != 0) {
			for (int l2 = 0; l2 < listFiles.length; ++l2) {
				if (listFiles[l2].isFile()) {
					String file = listFiles[l2].getName();
					String filePath = listFiles[l2].getAbsolutePath();
					if (file.startsWith(fileName.split("#")[0]) && file.endsWith(extension)) {
						System.out.println("File Found Successfully");
						System.out.println("File Name Is ===== " + file);
//						CustomFunctions.openFiles(extension, filePath);
						try {
							Monitoring_FrameWork.SaveResult("true", "true");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						flag = 1;
						boolean success = listFiles[l2].delete();
						if (success) {
							System.out.println("File deleted successfully");
						}
						break;
					} else {
//						System.out.println("******** File not Found ********");
//						System.out.println();
					}
				}
			}
			if (flag == 0) {
				System.out.println("************File Not Found**************");
				try {
					Monitoring_FrameWork.SaveResult("false", "true");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Folder Is Empty !!!!!!");
			try {
				Monitoring_FrameWork.SaveResult("false", "true");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String roundToNumber(double number) {
		// Split the number into integer and decimal parts
		double integerPart = Math.floor(number);
		double decimalPart = number - integerPart;

		double roundedDecimalPart = Math.round(decimalPart * 20.0) / 20.0;

		double result = integerPart + roundedDecimalPart;

		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(result);
	}

	public static void copyUrlOnNewTab(WebElement webElementVal) {

		String url;
		if (ObjectTypeRDS.equalsIgnoreCase("value")) {
			url = webElementVal.getAttribute("value");
		} else {
			url = webElementVal.getText();
		}
		System.out.println("URL Is == " + url);

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Total tab Size is === " + tabs.size());
		int size = tabs.size();
		driver.switchTo().window(tabs.get(size - 1));
		driver.get(url);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	public static void verifyDownloadedPdfFiles(String extension, String folderPath) {

		String fileName;
		File folder = new File(folderPath);

		File latestFile = Arrays.stream(folder.listFiles()).filter(File::isFile)
				.max(Comparator.comparing(File::lastModified)).orElse(null);

		fileName = latestFile.getName();
		System.out.println("Latest download file: " + fileName);

		String file = fileName.lastIndexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;

		if (fileName.startsWith(file) && fileName.endsWith(extension)) {

			try {
				Monitoring_FrameWork.SaveResult("true", "true");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean success = latestFile.delete();
			if (success) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("File not deleted ");

			}

		}

	}

	public static void verifyDownloadedFiles(String extension, String dataFieldRDS) {

		String fileName = dataFieldRDS.split("#")[0].trim();
		File folder = new File(dataFieldRDS.split("#")[1].trim());

		File latestFile = Arrays.stream(folder.listFiles()).filter(File::isFile)
				.max(Comparator.comparing(File::lastModified)).orElse(null);

		String latestFileName = latestFile.getName();
		System.out.println("Latest download file: " + latestFileName);

		String file = latestFileName.lastIndexOf(".") > 0 ? latestFileName.substring(0, latestFileName.lastIndexOf("."))
				: latestFileName;

		if (file.startsWith(fileName) && latestFileName.endsWith(extension)) {

			try {
				Monitoring_FrameWork.SaveResult("File downloaded", "File downloaded");
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean success = latestFile.delete();
			if (success) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("File not deleted ");

			}
		} else {
			try {
				Monitoring_FrameWork.SaveResult("File Not Downloaded", "File downloaded");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void validateLocator(String propertyValueRDS, String dataFieldRDS) {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(15));

		try {
			WebElement wb = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));

			if (wb.isDisplayed()) {
				try {
					Monitoring_FrameWork.SaveResult("true", "true");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println(" == Find For second locator == ");
			WebElement wb = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS)));

			if (wb.isDisplayed()) {
				try {
					Monitoring_FrameWork.SaveResult("true", "true");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	public static String generateRandomString(String length, String noOfString, String stringFormat) {
		String randomString = "";
		if (stringFormat.equals("AA")) {
			char[] chars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
					'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
			Random rnd = new Random();
			int charsLength = chars.length;

			for (int j = 1; j <= Integer.parseInt(noOfString); j++) {
				StringBuilder password = new StringBuilder();

				for (int i = 0; i < Integer.parseInt(length); i++) {
					int index = rnd.nextInt(charsLength - i - 1);
					char a = chars[i + index];
					chars[i + index] = chars[i];
					chars[i] = a;
					password.append(a);
				}
				randomString = password.toString();
				System.out.println(randomString);
			}
		} else if (stringFormat.equals("01")) {
			char[] chars = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '1', '2', '3', '4', '5', '6', '7',
					'8', '9', '1', '2', '3', '4', '5', '6', '7', '8', '9', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', };
			Random rnd = new Random();
			int charsLength = chars.length;
			for (int j = 1; j <= Integer.parseInt(noOfString); j++) {
				StringBuilder password = new StringBuilder();
				// String randomString = "";
				for (int i = 0; i < Integer.parseInt(length); i++) {
					int index = rnd.nextInt(charsLength - i - 1);
					char a = chars[i + index];
					chars[i + index] = chars[i];
					chars[i] = a;
					password.append(a);
				}
				randomString = password.toString();
				System.out.println(randomString);
				return randomString;
			}
		}
		return randomString;
	}

	public static void verifyFiles(String fileName, String dataFieldRDS) {

		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int flag = 0;
		File[] listFiles = new File(dataFieldRDS).listFiles();
		String extension = fileName.split("#")[1];
		System.out.println("extension is == " + extension);
		System.out.println("file name == " + fileName.split("#")[0]);
		if (listFiles.length != 0) {
			for (int l2 = 0; l2 < listFiles.length; ++l2) {
				if (listFiles[l2].isFile()) {
					String file = listFiles[l2].getName();
					String filePath = listFiles[l2].getAbsolutePath();
					System.out.println();
					System.out.println("File Name Is ===== " + file);
					if (file.startsWith(fileName.split("#")[0]) && file.endsWith(extension)) {

						CustomFunctions.openFiles(extension, filePath);
						try {
							Monitoring_FrameWork.SaveResult("true", "true");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						flag = 1;
//						boolean success = listFiles[l2].delete();
//						if (success) {
//							System.out.println("File deleted successfully");
//						}
						break;
					} else {
						System.out.println("******** File not Found ********");
						System.out.println();
					}
				}
			}
			if (flag == 0) {
				try {
					FileInputStream fis = new FileInputStream(Functions.path2);
					Functions.pro.load(fis);
				} catch (Exception e) {
					// TODO: handle exception
				}
				final String popup = Functions.pro.getProperty("popup");
				if (popup.equalsIgnoreCase("Y")) {
					Frame frame6 = new Frame();
					frame6.setVisible(true);
					frame6.toFront();
					Object[] objects = { "File not Found" };
					JOptionPane.showMessageDialog(null, objects, null, -1);
					frame6.setVisible(false);
				}
			}
		} else {
			try {
				System.out.println("File not Found");
				FileInputStream fis = new FileInputStream(Functions.path2);
				Functions.pro.load(fis);
			} catch (Exception e) {
				// TODO: handle exception
			}
			final String popup = Functions.pro.getProperty("popup");
			if (popup.equalsIgnoreCase("Y")) {
				Frame frame6 = new Frame();
				frame6.setVisible(true);
				frame6.toFront();
				Object[] objects = { "File not Found" };
				JOptionPane.showMessageDialog(null, objects, null, -1);
				frame6.setVisible(false);
			}
		}

	}

	private static void openFiles(String extension, String filePath) {

		try {
			// Build the command
			ProcessBuilder processBuilder = new ProcessBuilder("xdg-open", filePath);

			// Start the process
			Process process = processBuilder.start();

			// Wait for the process to finish
			int exitCode = process.waitFor();

			if (exitCode == 0) {
				System.out.println(" file opened successfully.");
			} else {
				System.out.println("Failed to open the CSV file.");
			}

			Thread.sleep(30000);

			Robot rb = new Robot();

			if (extension.equalsIgnoreCase("CSV") || extension.equalsIgnoreCase("csv")) {
				rb.keyPress(KeyEvent.VK_ENTER);
				rb.keyRelease(KeyEvent.VK_ENTER);
				Thread.sleep(2000);
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_Q);
				rb.keyRelease(KeyEvent.VK_Q);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			} else if (extension.equalsIgnoreCase("PDF") || extension.equalsIgnoreCase("pdf")) {
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_W);
				rb.keyRelease(KeyEvent.VK_W);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			} else {
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_Q);
				rb.keyRelease(KeyEvent.VK_Q);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			}

			System.out.println("File Closed Successfully");
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// cj
	public static void deleteChecker(WebElement table, String condition) {

		List<WebElement> rows = table.findElements(By.tagName("tr"));

		System.out.println("Total rows in table : " + rows.size());

		for (WebElement row : rows) {
			WebElement requiredColumn = row.findElements(By.tagName("td")).get(2);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			String textFromCell = requiredColumn.getText();

			System.out.println("text from column cell  : " + textFromCell.toString());

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			if (textFromCell.equalsIgnoreCase(condition)) {
				System.out.println("============= Condtion is true ==========");
				try {
					row.findElements(By.tagName("td")).get(1).click();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();",
							row.findElements(By.tagName("td")).get(1));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				try {
					row.findElements(By.tagName("td")).get(5).click();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();",
							row.findElements(By.tagName("td")).get(5));
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Framework.driver.switchTo().alert().accept();
				break;
			}

		}
	}

	public static String dynamicWebTable(WebElement table, String conditionText, int requiredColumnIndex,
			int columnIndex, String objectTypeRDS) {

		String text = null;
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		System.out.println("Total rows in table : " + rows.size());

		for (WebElement row : rows) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			WebElement requiredColumn = row.findElements(By.tagName("td")).get(requiredColumnIndex);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			String textFromCell = null;
			if (objectTypeRDS.equalsIgnoreCase("Value")) {
				textFromCell = requiredColumn.getAttribute("Value");
			} else {
				textFromCell = requiredColumn.getText();
			}
			System.out.println("text from column cell  : " + textFromCell.toString());

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			if (textFromCell.equalsIgnoreCase(conditionText)) {
				System.out.println("============= Condtion is true ==========");
				text = row.findElements(By.tagName("td")).get(columnIndex).getText();
				System.out.println("Text Is == " + text);
				break;
			}
		}
		return text;
	}

	// cj
	public static void approveChecker(WebElement table, String condition) {

		List<WebElement> rows = table.findElements(By.tagName("tr"));

		System.out.println("Total rows in table : " + rows.size());

		for (WebElement row : rows) {
			WebElement requiredColumn = row.findElements(By.tagName("td")).get(2);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			String textFromCell = requiredColumn.getText();

			System.out.println("text from column cell  : " + textFromCell.toString());

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			if (textFromCell.equalsIgnoreCase(condition)) {
				System.out.println("============= Condtion is true ==========");
				try {
					row.findElements(By.tagName("td")).get(1).click();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();",
							row.findElements(By.tagName("td")).get(1));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				break;
			}

		}
	}

	public static void removeFileUnderscore(String oldFilePath) {

		String directoryPath = System.getProperty("user.dir") + "/Datasheet/";

		File directory = new File(directoryPath);

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						if (file.getName().contains("_")) {
							// Get the file name and replace underscores with spaces
							String oldFileName = file.getName().replace("_", " ");

							// Construct the new file path with the modified file name
							String newFilePath = file.getParent() + File.separator + oldFileName;
							File newFile = new File(newFilePath);

							if (file.renameTo(newFile)) {
								System.out.println("File renamed successfully: " + newFile.getName());
							} else {
								System.out.println("Failed to rename the file: " + file.getName());
							}
						}
					}
				}
			} else {
				System.out.println("No files found in the directory.");
			}
		} else {
			System.out.println("The specified directory does not exist or is not a directory.");
		}

	}

	public static void checkLastTratedPrice(String propertyValueRDS, String dataFieldRDS) {

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));

		List<WebElement> listStocks = Framework.driver.findElements(By.xpath(propertyValueRDS));
		System.out.println("list Stocks == " + listStocks.size());

		for (int i = 2; i < listStocks.size(); i++) {
			String companyNameXpath = "((" + propertyValueRDS + ")[" + i + "]/span)[4]";

			String ltpXpath = "((" + propertyValueRDS + ")[" + i + "]/span)[9]";

			String companyName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(companyNameXpath)))
					.getText();
			System.out.println("Company Name Is == " + companyName);

			if (companyName.equalsIgnoreCase("BAJAJ FINANCE LIMITED") || companyName.equalsIgnoreCase("NTPC LTD")
					|| companyName.equalsIgnoreCase("RELIANCE INDUSTRIES LTD")
					|| companyName.equalsIgnoreCase("STATE BANK OF INDIA")
					|| companyName.equalsIgnoreCase("AXIS BANK LIMITED")
					|| companyName.equalsIgnoreCase("TATA CONSULTANCY SERV LT")
					|| companyName.equalsIgnoreCase("OIL AND NATURAL GAS CORP.")) {

				String ltpActual = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ltpXpath)))
						.getText();
				System.out.println("LTP Actual == " + ltpActual);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = format.format(new Date());
				System.out.println("Start time == " + startTime);

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}

				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endTime = format1.format(new Date());
				System.out.println("End Time Is == " + endTime);

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS))).click();

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				String ltpExpected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ltpXpath)))
						.getText();
				System.out.println("LTP Expected == " + ltpExpected);

				if (ltpActual.equalsIgnoreCase(ltpExpected)) {
					Total_Report.checkLTP(companyName, ltpActual, ltpExpected, startTime, endTime);
				} else {
					System.out.println(companyName + " LTP Values are different");
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}

		}

	}

	public static void checkLTPIndices(String propertyValueRDS, String dataFieldRDS, String objectTypeRDS) {

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));

		String nifty50 = "Nifty 50";
		String bankNifty = "Nifty Bank";
		String finNifty = "Nifty Fin Service";

		List<WebElement> list = Framework.driver.findElements(By.xpath(propertyValueRDS + "/tr"));
		System.out.println("List size is == " + list.size());

		for (int i = 2; i < list.size(); i = i + 2) {

			String companyNameXpath = propertyValueRDS + "/tr[" + i + "]/td[2]";
			String companyName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(companyNameXpath)))
					.getText();
			System.out.println("stock name is == " + companyName);

			if (companyName.equalsIgnoreCase(nifty50) || companyName.equalsIgnoreCase(bankNifty)
					|| companyName.equalsIgnoreCase(finNifty)) {

				String StockValuexpath = propertyValueRDS + "/tr[" + i + "]/td[3]";
				String actualValue = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(StockValuexpath)))
						.getText();
				System.out.println("Actual stock value is == " + actualValue);

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = format.format(new Date());
				System.out.println("Start time == " + startTime);

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}

				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endTime = format1.format(new Date());
				System.out.println("End Time Is == " + endTime);

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS))).click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}

				String ExpStockValuexpath = propertyValueRDS + "/tr[" + i + "]/td[3]";
				String expValue = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ExpStockValuexpath)))
						.getText();

				System.out.println("Expected value is == " + expValue);

				if (actualValue.equalsIgnoreCase(expValue)) {
					Total_Report.checkLTPforTopIndices(companyName, actualValue, expValue, startTime, endTime);
				} else {
					System.out.println(companyName + " Values are not same");
				}

			}

		}

	}

// cj
	public static void authorize(String propertyValueRDS, String dataFieldRDS) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		int tableSize = driver.findElements(By.xpath(propertyValueRDS)).size();
		System.out.println(" total rows in ");
		for (int i = 2; i <= tableSize; i++) {
			sleep(500);
			String brnCodeXpath = propertyValueRDS + "[" + i + "]";

			String brnCode = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(brnCodeXpath))).getText();
			brnCode = brnCode.split(" ")[0].trim();
			System.out.println("code : " + brnCode);
			sleep(500);
			String text = Functions.globleValues.get(dataFieldRDS);
			System.out.println("Verification text : " + text);
			if (brnCode.equalsIgnoreCase(text)) {
				System.out.println("Found !!!");
				String checkBox = propertyValueRDS + "/td[1]/input";
				sleep(500);

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkBox))).click();
				System.out.println("clcik on check box");
				sleep(1000);

				String authorizeBtn = propertyValueRDS + "/td[9]/a[2]";
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(authorizeBtn))).click();
				System.out.println("clcik on check box");
				sleep(2000);

				break;
			}
			System.out.println();
		}

	}

	public static void writeCSV(String stockName, String qty, String ltp, String investment, String today_PL,
			String overall_PL) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		String timeStamp = sdf.format(new Date());

		String propertyPath = System.getProperty("user.dir") + "/PortFolio/";
		new File(propertyPath).mkdirs();
		String filePath = propertyPath + "PortFolio_Report_" + timeStamp.split(" ")[0] + ".csv";
		File file = new File(filePath);

		if (!file.exists()) {
			try {
				file.createNewFile();
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("Stock_Name,QTY,LTP,Investment,Today_PL,Overall_PL,TimeStamp\n");
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(stockName + "," + qty + "," + ltp + "," + investment + "," + today_PL + "," + overall_PL
					+ "," + timeStamp + "\n");
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("data write successfully");

	}

	public static void verifyPortfolio(String propertyValueRDS, String dataFieldRDS) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		String investments = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)))
				.getText();
		System.out.println("Investments value : " + investments);

		String Total_of_Today_PL = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[0].trim())))
				.getText();
		Total_of_Today_PL = Total_of_Today_PL.split(" ")[0];
		System.out.println("Total_of_Today_PL value : " + Total_of_Today_PL);

		String Total_of_Overall_PL = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[1].trim())))
				.getText();
		Total_of_Overall_PL = Total_of_Overall_PL.split(" ")[0];
		System.out.println("Total_of_Overall_PL value : " + Total_of_Overall_PL);

		String qty = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[2].trim())))
				.getText();
		System.out.println("Qty value : " + qty);

		String ltp = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[3].trim())))
				.getText();
		System.out.println("LTP value : " + ltp);

		String stockName = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[4].trim())))
				.getText();
		System.out.println("Stock name : " + stockName);

		writeCSV(stockName, qty, ltp, investments, Total_of_Today_PL, Total_of_Overall_PL);

		if (investments.equalsIgnoreCase("0.00")) {
			JOptionPane.showMessageDialog(new Frame(), "Investments value is 0.00");
		}
		if (Total_of_Today_PL.equalsIgnoreCase("0.00")) {
			JOptionPane.showMessageDialog(new Frame(), "Total_of_Today_PL value is 0.00");
		}
		if (Total_of_Overall_PL.equalsIgnoreCase("0.00")) {
			JOptionPane.showMessageDialog(new Frame(), "Total_of_Overall_PL value is 0.00");
		}
		if (qty.equalsIgnoreCase("0.00")) {
			JOptionPane.showMessageDialog(new Frame(), "Qty value is 0.00");
		}
		if (ltp.equalsIgnoreCase("0.00")) {
			JOptionPane.showMessageDialog(new Frame(), "LTP value is 0.00");
		}

	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public static void verifyStocks(String propertyValueRDS, String dataFieldRDS) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		int size = driver.findElements(By.xpath(propertyValueRDS)).size();
		System.out.println("Total size : " + size);

		for (int i = 1; i <= size; i++) {

			String xpath1 = propertyValueRDS + "[" + i + "]/div[2]";
			String xpath2 = propertyValueRDS + "[" + i + "]/div[4]";

			String HoldingQty = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath1))).getText();
			System.out.println("HoldingQty value : " + HoldingQty);

			String LTP = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath2))).getText();
			System.out.println("LTP value : " + LTP);

			if (HoldingQty.equalsIgnoreCase("0.00")) {
				JOptionPane.showMessageDialog(new Frame(), "Holding Qty value is 0.00 in " + i + " Line");
			}
			if (LTP.equalsIgnoreCase("0.00")) {
				JOptionPane.showMessageDialog(new Frame(), "LTP value is 0.00 in " + i + " Line");
			}
			System.out.println();
		}

	}

	public static void clickOn1stMaker(String propertyValueRDS, String dataFieldRDS) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		int size = driver.findElements(By.xpath(propertyValueRDS)).size();
		System.out.println("Total size of : " + size);

		for (int i = 1; i <= size; i++) {

			String xpath1 = "(" + propertyValueRDS + "[" + i + "]/td)[2]/b";
			String xpath2 = "(" + propertyValueRDS + "[" + i + "]/td)[1]/input";
			System.out.println("xpath1 ======== " + xpath1);
			System.out.println("xpath2 ======== " + xpath2);

			String text = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath1))).getText();

			System.out.println("Request Text ==== " + text);

			if (text.equalsIgnoreCase(dataFieldRDS)) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath2))).click();
				break;
			}

		}
	}

	public static String readMailOtp(String mailId, String password, String optSubject, String fromMail) {
		try {
			String otp = "";
			Properties props = new Properties();
			// props.put("mail.store.protocol", "imaps");
			props.put("mail.imaps.host", "imap.gmail.com");
			props.put("mail.imaps.port", "993");
			props.put("mail.imaps.starttls.enable", "true");
			props.put("mail.imaps.auth", "true");

			Thread.sleep(5000);

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailId, password);
				}
			});
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", mailId, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message[] messages = inbox.getMessages();

			for (int i = messages.length - 1; i >= 0; i--) {

				Message message = messages[i];
				javax.mail.Address[] fromAddresses = message.getFrom();
				String senderEmail = ((InternetAddress) fromAddresses[0]).getAddress();
				if (message.getSubject() != null && message.getSubject().equalsIgnoreCase(optSubject)
						&& senderEmail.equalsIgnoreCase(fromMail)) {
					System.out.println("Done : .....");
					System.out.println("Email from: " + senderEmail);
					System.out.println("Email Subject: " + message.getSubject());
					Object content = message.getContent();
					String bodyString = "";

					if (content instanceof String) {
						System.out.println("Content is String...");
						bodyString = (String) content;
					} else if (content instanceof MimeMultipart) {
						// If the content is multipart (likely HTML), extract the plain text from it
						System.out.println("Content is multipart (likely HTML)...");
						MimeMultipart mimeMultipart = (MimeMultipart) content;
						int count = mimeMultipart.getCount();

						for (int j = 0; j < count; j++) {
							BodyPart bodyPart = mimeMultipart.getBodyPart(j);
							if (bodyPart.isMimeType("text/plain")) {
								bodyString = bodyString + bodyPart.getContent();
							} else if (bodyPart.isMimeType("text/html")) {
								String html = (String) bodyPart.getContent();
								bodyString = bodyString + Jsoup.parse(html).text();// Use JSoup to parse HTML and
																					// extract plain text
							}
						}

					}
					// System.out.println("Email Body: " + bodyString);
					if (fromMail.equalsIgnoreCase("noreply@bajajallianz.co.in")) {
						// 4-digit OTP
						System.out.println("Bajaj Allianz OTP Read");
						String optPart1 = bodyString.split("Dear Customer, ")[1].split("is your one-time password")[0]
								.trim();
						if (!optPart1.isEmpty() && optPart1.length() == 4) {
							otp = optPart1.trim();
						} else {
							System.out.println("Read Otp is either empty Or length > 4");
						}

					} else if (fromMail.equalsIgnoreCase("emfhelp@edelweissmf.com")) {
						// Define a pattern to match the 4-digit OTP
						System.out.println("Edelweiss OTP Read");
						String regex = "<strong[^>]*>(\\d{6})</strong>";
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(bodyString);

						if (matcher.find()) {
							System.out.println("Otp Pattern Matched...");
							otp = matcher.group(1).trim(); // Return the captured OTP
						} else {
							System.out.println("No OTP found in the email body.");
						}
					}
					break;
				}
			}
			inbox.close(false);
			store.close();
			return otp;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public static void verifyLibertyElement(String objectTypeRDS, String dataFieldRDS, String propertyValue) {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValue)));
			System.out.println("continue running script.....");
		} catch (Exception e) {
			System.out.println("page not load....");

			Framework.driver.navigate().back();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[0])))
					.sendKeys(objectTypeRDS.trim());

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[1]))).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[7]))).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[2]))).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[3]))).click();

			WebElement scroll = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[4])));// scroll
			((JavascriptExecutor) Framework.driver).executeScript("arguments[0].scrollIntoView(true);",
					new Object[] { scroll });

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[5]))).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[6]))).click();

		}
	}

	public static void pressKeyboardBtn(String input) {
		try {
			Robot robot = new Robot();

			for (char c : input.toCharArray()) {
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

				if (Character.isLowerCase(c) || Character.isDigit(c)) {
					System.out.println("Pressing lower/digit: " + c);
					robot.keyPress(keyCode);
					robot.keyRelease(keyCode);
				} else if (Character.isUpperCase(c)) {
					System.out.println("Pressing upper: " + c);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(keyCode);
					robot.keyRelease(keyCode);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (!Character.isLetterOrDigit(c)) {
					System.out.println("Pressing : " + c);
					String str = String.valueOf(c);
					if (str.equalsIgnoreCase(".") || str.equalsIgnoreCase("/") || str.equalsIgnoreCase(",")) {
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
					} else if (str.equalsIgnoreCase(":")) {
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_SEMICOLON);
						robot.keyRelease(KeyEvent.VK_SEMICOLON);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else if (str.equalsIgnoreCase("_")) {
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_MINUS);
						robot.keyRelease(KeyEvent.VK_MINUS);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else if (str.equalsIgnoreCase("-")) {
						robot.keyPress(KeyEvent.VK_MINUS);
						robot.keyRelease(KeyEvent.VK_MINUS);
					} else if (str.equalsIgnoreCase("@")) {
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_2);
						robot.keyRelease(KeyEvent.VK_2);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else if (str.equalsIgnoreCase("$")) {
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_4);
						robot.keyRelease(KeyEvent.VK_4);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else if (str.equalsIgnoreCase("#")) {
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_3);
						robot.keyRelease(KeyEvent.VK_3);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else {
						System.out.println("Pressing special: " + c);
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					}

				}

				Thread.sleep(300);
			}

		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sendValueRb(WebElement webElementVal, String objecttype) throws Exception {

		webElementVal.click();

		Thread.sleep(2000);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_T);
		rb.keyRelease(KeyEvent.VK_T);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_E);
		rb.keyRelease(KeyEvent.VK_E);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_S);
		rb.keyRelease(KeyEvent.VK_S);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_T);
		rb.keyRelease(KeyEvent.VK_T);

		new WebDriverWait(Framework.driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objecttype))).click();
		Thread.sleep(2000);

		rb.keyPress(KeyEvent.VK_W);
		rb.keyRelease(KeyEvent.VK_W);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_E);
		rb.keyRelease(KeyEvent.VK_E);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_L);
		rb.keyRelease(KeyEvent.VK_L);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_C);
		rb.keyRelease(KeyEvent.VK_C);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_O);
		rb.keyRelease(KeyEvent.VK_O);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_M);
		rb.keyRelease(KeyEvent.VK_M);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_E);
		rb.keyRelease(KeyEvent.VK_E);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_SHIFT);

		rb.keyPress(KeyEvent.VK_1);
		rb.keyRelease(KeyEvent.VK_1);

		rb.keyRelease(KeyEvent.VK_SHIFT);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_2);
		rb.keyRelease(KeyEvent.VK_2);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_0);
		rb.keyRelease(KeyEvent.VK_0);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_2);
		rb.keyRelease(KeyEvent.VK_2);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_4);
		rb.keyRelease(KeyEvent.VK_4);

	}

	public static void sendValueRb2(WebElement webElementVal, String objecttype) throws Exception {

		webElementVal.click();

		Thread.sleep(2000);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_T);
		rb.keyRelease(KeyEvent.VK_T);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_E);
		rb.keyRelease(KeyEvent.VK_E);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_S);
		rb.keyRelease(KeyEvent.VK_S);

		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_T);
		rb.keyRelease(KeyEvent.VK_T);

		new WebDriverWait(Framework.driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objecttype))).click();
		Thread.sleep(2000);

		// password=jan!@#$%2025
		rb.keyPress(KeyEvent.VK_F);
		rb.keyRelease(KeyEvent.VK_F);
		Thread.sleep(200);
		rb.keyPress(KeyEvent.VK_E);
		rb.keyRelease(KeyEvent.VK_E);
		Thread.sleep(200);
		rb.keyPress(KeyEvent.VK_B);
		rb.keyRelease(KeyEvent.VK_B);
		Thread.sleep(200);

		rb.keyPress(KeyEvent.VK_SHIFT);
		Thread.sleep(500);

		rb.keyPress(KeyEvent.VK_1);
		rb.keyRelease(KeyEvent.VK_1);
		Thread.sleep(200);
		rb.keyPress(KeyEvent.VK_2);
		rb.keyRelease(KeyEvent.VK_2);
		Thread.sleep(200);
		rb.keyPress(KeyEvent.VK_3);
		rb.keyRelease(KeyEvent.VK_3);
		Thread.sleep(200);
		rb.keyPress(KeyEvent.VK_4);
		rb.keyRelease(KeyEvent.VK_4);
		Thread.sleep(200);
		rb.keyPress(KeyEvent.VK_5);
		rb.keyRelease(KeyEvent.VK_5);

		Thread.sleep(500);
		rb.keyRelease(KeyEvent.VK_SHIFT);

		rb.keyPress(KeyEvent.VK_2);
		rb.keyRelease(KeyEvent.VK_2);

		Thread.sleep(1000);

		rb.keyPress(KeyEvent.VK_0);
		rb.keyRelease(KeyEvent.VK_0);

		Thread.sleep(2000);

		rb.keyPress(KeyEvent.VK_2);
		rb.keyRelease(KeyEvent.VK_2);

		Thread.sleep(2000);

		rb.keyPress(KeyEvent.VK_5);
		rb.keyRelease(KeyEvent.VK_5);

	}

	public static String axisNfcReadOtp(String mailId, String password, String optSubject, String fromMail) {
		String otp = "";
		try {
			Properties props = new Properties();
			props.put("mail.imaps.host", "imap.gmail.com");
			props.put("mail.imaps.port", "993");
			props.put("mail.imaps.starttls.enable", "true");
			props.put("mail.imaps.auth", "true");

			Thread.sleep(5000);

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailId, password);
				}
			});
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", mailId, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message[] messages = inbox.getMessages();

			for (int i = messages.length - 1; i >= 0; i--) {

				Message message = messages[i];
				Address[] fromAddresses = message.getFrom();
				String senderEmail = ((InternetAddress) fromAddresses[0]).getAddress();
				if (message.getSubject() != null && message.getSubject().equalsIgnoreCase(optSubject)
						&& senderEmail.equalsIgnoreCase(fromMail)) {
					System.out.println("Email from: " + senderEmail);
					System.out.println("Email Subject: " + message.getSubject());
					Object content = message.getContent();
					String bodyString = "";

					if (content instanceof String) {
						System.out.println("Content is String...");
						bodyString = (String) content;
					} else if (content instanceof MimeMultipart) {
						System.out.println("Content is multipart (likely HTML)...");
						MimeMultipart mimeMultipart = (MimeMultipart) content;
						int count = mimeMultipart.getCount();

						for (int j = 0; j < count; j++) {
							BodyPart bodyPart = mimeMultipart.getBodyPart(j);
							if (bodyPart.isMimeType("text/plain")) {
								bodyString = bodyString + bodyPart.getContent();
							} else if (bodyPart.isMimeType("text/html")) {
								String html = (String) bodyPart.getContent();
								bodyString = bodyString + Jsoup.parse(html).text();// Use JSoup to parse HTML and
																					// extract plain text
							}
						}

					}
					String actOtp = bodyString.split("banking with us. ")[1]
							.split(" is the code for verifying your email ID.")[0];
					actOtp = actOtp.trim();
					System.out.println("Email Body: " + actOtp);

					break;
				}
			}
			inbox.close(false);
			store.close();
			return otp;
		} catch (Exception e) {
			e.printStackTrace();
			return otp;
		}
	}

	//////////// ############################ Robot Functions
	//////////// ##########################

	
		public static void robot_otp(String DataFieldRDS) {
		try {
			Robot rb = new Robot();
			String data = DataFieldRDS;
			System.out.println(data);
			char[] ch = new char[data.length()];
			for (int i = 0; i < data.length(); ++i) {
				ch[i] = data.charAt(i);
			}
			for (int j = 0; j < ch.length; ++j) {
				if (ch[j] == '1') {
					System.out.println(ch[j]);
					rb.keyPress(49);
					rb.keyRelease(49);
					Thread.sleep(200L);
				} else if (ch[j] == '2') {
					System.out.println(ch[j]);
					rb.keyPress(50);
					rb.keyRelease(50);
					Thread.sleep(200L);
				} else if (ch[j] == '3') {
					System.out.println(ch[j]);
					rb.keyPress(51);
					rb.keyRelease(51);
					Thread.sleep(200L);
				} else if (ch[j] == '4') {
					System.out.println(ch[j]);
					rb.keyPress(52);
					rb.keyRelease(52);
					Thread.sleep(200L);
				} else if (ch[j] == '5') {
					System.out.println(ch[j]);
					rb.keyPress(53);
					rb.keyRelease(53);
					Thread.sleep(200L);
				} else if (ch[j] == '6') {
					System.out.println(ch[j]);
					rb.keyPress(54);
					rb.keyRelease(54);
					Thread.sleep(200L);
				} else if (ch[j] == '7') {
					System.out.println(ch[j]);
					rb.keyPress(55);
					rb.keyRelease(55);
					Thread.sleep(200L);
				} else if (ch[j] == '8') {
					System.out.println(ch[j]);
					rb.keyPress(56);
					rb.keyRelease(56);
					Thread.sleep(200L);
				} else if (ch[j] == '9') {
					System.out.println(ch[j]);
					rb.keyPress(57);
					rb.keyRelease(57);
					Thread.sleep(200L);
				} else if (ch[j] == '0') {
					System.out.println(ch[j]);
					rb.keyPress(48);
					rb.keyRelease(48);
					Thread.sleep(200L);
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}

	public static void verifyLic30minutesPayment(String propertyValueRDS) throws IOException {

		String filepath = System.getProperty("user.dir");
		File file = new File(String.valueOf(filepath) + File.separator + "data" + "/LICTimeCheck.txt");
		java.io.FileReader fileReader = new java.io.FileReader(file);
		BufferedReader bufferreader = new BufferedReader(fileReader);
		String previousTime = bufferreader.readLine();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime previous = LocalTime.parse(previousTime, formatter);
		LocalTime current = LocalTime.now();

		long diff = Duration.between(previous, current).toMinutes();

		if (diff < 0) {
			diff = Duration.between(previous, LocalTime.MIDNIGHT).toMinutes()
					+ Duration.between(LocalTime.MIN, current).toMinutes();

		}

		if (diff > 30) {
			System.out.println("Payment should be done");
			try {
				while (!Framework.pagename.equalsIgnoreCase(propertyValueRDS)) {
					Framework.recordsetRDS.moveNext();
					Framework.pagename = Framework.recordsetRDS.getField("PageName");
				}
				Framework.recordsetRDS.movePrevious();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Payment already done");
		}

	}

	public static void selectAxisPreviousdate(WebElement ele) {
		ele.click();
		int date = LocalDate.now().getDayOfMonth();
		date -= 1;
		System.out.println("Previous Date -->" + date);
		String dateStr = "//td[@class='day']/child::a[text()='" + date + "']";

		new WebDriverWait(Framework.driver, 15).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dateStr)))
				.click();

	}// ,downloadPath,fileName#extension#password#innerData

	public static void verifyAxisDownloadedFilesNew(String propertyValueRDS, String dataFieldRDS) throws Exception {

		File folder = new File(propertyValueRDS.trim());

		String actFileName = dataFieldRDS.split("#")[0].toLowerCase();
		String extension = dataFieldRDS.split("#")[1].toLowerCase();
//		String password = dataFieldRDS.split("#")[1];
//		String innerData = dataFieldRDS.split("#")[2];
//pdf,xl,csv,txt,mt940
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null && files.length > 0) {
				File latestFile = Arrays.stream(files).filter(File::isFile)
						.max(Comparator.comparingLong(File::lastModified)).orElse(null);

				if (latestFile == null) {
					saveResultFail();
					System.out.println("No files found in the folder.");
					return;
				}

				System.out.println("Latest File found: " + latestFile.getName());

				if (latestFile.getName().toLowerCase().startsWith(actFileName)
						&& latestFile.getName().toLowerCase().endsWith(extension)) {
					System.out.println("<------------ File Matched ------------>");
					fileCheck(latestFile);
					latestFile.delete();
					System.out.println("✅ File Deleted successfully");

				} else {
					System.out.println("❌ Latest file name does not start with expected: " + actFileName);
					saveResultFail();
				}
			} else {
				System.out.println("The folder is empty or cannot be accessed.");
				saveResultFail();
			}
		} else {
			System.out.println("Provided path is not a directory.");
			saveResultFail();
		}
	}

	public static void fileCheck(File file) {

		try {
			if (file.length() == 0) {
				System.out.println("File size is empty.");
				saveResultFail();
				return;
			}

			if (file.getName().toLowerCase().endsWith("pdf")) {
				PDDocument document = PDDocument.load(file);
				String text = new PDFTextStripper().getText(document);
				document.close();

				if (text.trim().isEmpty()) {
					System.out.println("PDF is empty.");
					saveResultFail();
				} else {
					System.out.println("PDF has content.");
					saveResultPass();
				}

			} else if (file.getName().toLowerCase().endsWith("txt") || file.getName().toLowerCase().endsWith("csv")) {

				String content = Files.readString(file.toPath());
				if (content.trim().isEmpty()) {
					System.out.println("TXT/CSV is  empty.");
					saveResultFail();
				} else {
					System.out.println("TXT/CSV has Content.");
					saveResultPass();
				}

			} else if (file.getName().toLowerCase().endsWith(".xlsx")
					|| file.getName().toLowerCase().endsWith(".xls")) {
				// write code
				System.out.println("XLSX/XLS has Content.");
				saveResultPass();
			} else if (file.getName().toLowerCase().endsWith("mt940")) {

				BufferedReader br = Files.newBufferedReader(file.toPath());
				String line;
				while ((line = br.readLine()) != null) {
					if (!line.trim().isEmpty()) {
						System.out.println("mt940 has Content.");
						saveResultPass();
						break;
					} else {
						System.out.println("mt940 is  empty.");
						saveResultFail();
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveResultPass() {
		try {
			Monitoring_FrameWork.SaveResult("true", "true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveResultFail() {
		try {
			Monitoring_FrameWork.SaveResult("false", "true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getPurposalNumber(String propertyValueRDS, String dataFieldRDS) throws InterruptedException {

		WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20L));
		List<WebElement> list = (List<WebElement>) Framework.driver.findElements(By.xpath(propertyValueRDS));
		System.out.println("List Size Is === " + list.size());
		int l = 2;
		String purposalNumber = null;
		System.out.println("pur is " + dataFieldRDS);
		while (list.size() != 0) {
			for (int i = 1; i <= list.size(); ++i) {
				final String xpath = "((//tr[@class=\"grid-row \"])[" + i + "]/td)[2]";
				purposalNumber = ((WebElement) wait3
						.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))).getText();
				System.out.println("Purposal Number Is === " + purposalNumber);
				if (purposalNumber.equalsIgnoreCase(dataFieldRDS)) {
					final String xpath2 = "(//input[@name='isSelect'])[" + i + "]";
					((WebElement) wait3
							.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath2)))).click();
					break;
				}
				if (!purposalNumber.equalsIgnoreCase(dataFieldRDS) && i == list.size()) {
					System.out.println("In next button");
					final WebElement wb3 = (WebElement) wait3
							.until((Function) ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//span[@class=\"glyphicon glyphicon-step-forward\"]")));
					wb3.click();
					Thread.sleep(8000L);
					wait3.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
					list.clear();
					list = (List<WebElement>) Framework.driver.findElements(By.xpath(propertyValueRDS));
				}
			}
			if (purposalNumber.equalsIgnoreCase(dataFieldRDS)) {
				break;
			}
		}
	}

}
