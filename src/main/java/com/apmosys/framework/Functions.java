
package com.apmosys.framework;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Button;
import org.sikuli.script.Location;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class Functions extends Framework {
	static int deftime;
	public static Properties pro;
	public static String path2;
	private static String errorMsg;
	private static String extraMsg;
	public static String url;
	private static String quote;
	private static String numberAsString;
	public static String mailTime;
	public static char otp1;
	public static char otp2;
	public static char otp3;
	public static char otp4;
	public static char otp5;
	public static char otp6;
	static String message;
	private static String otp_g;
	static String otpTime;
	static String objectType;
	private static String value;
	private static String scType;
	public static String referenceid;
	public static String captchText;

	public static Map<String, String> globleValues = new HashMap<String, String>();
	private static String licFlag = null;
	public static String screenShotPath;
	private static String currentUrl;
	public static boolean isYesBankPopup = false;
	private static int captchaRetryCount = 1;
	private static String errorTypeLocal = "";
	private static String errorMessageLocal = "";

	public static void Actions(String controlRDS, String objectTypeRDS, String propertyNameRDS, String propertyValueRDS,
			String dataFieldRDS, String actionRDS, String srNo, int fieldStatus, String pageName) throws Exception {

		FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "//mf_web.properties");
		Functions.pro.load(fi);
		fi.close();
		Map<String, String> map = new HashMap<String, String>();
		map.put("bankHoliday", pro.getProperty("bankHoliday"));

		String actualResult = "";

		System.out.println("Control Value    ---------------> " + controlRDS);
		System.out.println("Property Name    ---------------> " + propertyNameRDS);
		System.out.println("Property Value   ---------------> " + propertyValueRDS);
		System.out.println("Action Value     ---------------> " + actionRDS);
		System.out.println("DataField Value  ---------------> " + dataFieldRDS);
		System.out.println("ObjectType Value ---------------> " + objectTypeRDS);
		System.out.println("Default Time     ---------------> " + Framework.defaultwaittime);

		WebElement webElementVal = null;
		if (fieldStatus == 0 || dataFieldRDS.length() > 0) {
			if (actionRDS.equalsIgnoreCase("ExplicitWaits")) {
				Waitforcondition(propertyNameRDS, propertyValueRDS, dataFieldRDS, srNo, pageName);
			} else if (Framework.browser.equalsIgnoreCase("API")) {
				System.out.println("Api Execution.......");
			} else {
				Framework.logstatus = "1";

				if (!browser.equalsIgnoreCase("sikuli") && !propertyNameRDS.equalsIgnoreCase("")
						&& (propertyNameRDS != null) && (!propertyNameRDS.isEmpty())
						&& !actionRDS.equalsIgnoreCase("CHECK_CAPTCHA&VISIBILITY")
						&& !actionRDS.equalsIgnoreCase("CHECK_OTP&VISIBILITY")) {
					webElementVal = CheckObjectVisibility(propertyNameRDS, propertyValueRDS, objectTypeRDS, pageName);
					if (webElementVal == null)
						return;
					if (Framework.showLocator.equalsIgnoreCase("Y")) {
						showLocator(webElementVal);
					}
				}
			}
		}
		if (actionRDS.equalsIgnoreCase("INPUTBOX_CAPTCHA")) {
			Frame frame = new Frame();
			frame.setVisible(false);
			frame.toFront();
			String activationcode = JOptionPane.showInputDialog(frame, "Enter the result for verification");
			webElementVal.sendKeys(new CharSequence[] { activationcode });
			Alert alt = Functions.driver.switchTo().alert();
			alt.accept();
		}
		try {
			if (actionRDS.equalsIgnoreCase("StartTime")) {
				Framework.tStartTime = Monitoring_FrameWork.StartTime();
			} else if (actionRDS.equalsIgnoreCase("SEQ")) {
				String ans = Framework.driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a"))
						.getText();
				System.out.println("ans:" + ans);
				if (ans.contains("Financial")) {
					try {
						Framework.driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]"))
								.sendKeys(new CharSequence[] { "TRUPTI0491" });
						Framework.driver.findElement(By.xpath("//*[@id=\"password\"]"))
								.sendKeys(new CharSequence[] { "May@2021" });
					} catch (Exception e16) {
						System.out.println("In Catch block");
					}
				}
			} else if (actionRDS.equalsIgnoreCase("ExplisitWait")) { // Explicit Wait
				try {
					System.out.println("--Datafield rds--------" + dataFieldRDS);
					int maxwait = Integer.parseInt(dataFieldRDS);
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(maxwait));
					By loc = null;
					String s52;
					String trim = s52 = (propertyNameRDS = propertyNameRDS.toUpperCase().trim());
					switch (s52) {
					case "XPATHS": {
						loc = By.xpath(propertyValueRDS);
						break;
					}
					case "CLASSNAME": {
						loc = By.className(propertyValueRDS);
						break;
					}
					case "TAGNAME": {
						loc = By.tagName(propertyValueRDS);
						break;
					}
					case "ID": {
						loc = By.id(propertyValueRDS);
						break;
					}
					case "NAME": {
						loc = By.name(propertyValueRDS);
						break;
					}
					case "XPATH": {
						loc = By.xpath(propertyValueRDS);
						break;
					}
					case "LINKTEXT": {
						loc = By.linkText(propertyValueRDS);
						break;
					}
					}
					System.out.println("Locator is -----> " + loc + "  :" + propertyValueRDS + ":");
					if (loc != null) {
						System.out.println("waiting for visibilityOfElement ------->" + new Date().toString());
						wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
						System.out.println("waiting for visibilityOfElement ------->" + new Date().toString());
					}
				} catch (UnhandledAlertException ert) {
					Alert alert = Framework.driver.switchTo().alert();
					String error = alert.getText();
					Framework.TakeScreenshots();
					System.out.println("2 " + Framework.ScreenshotfileLocation);
					Framework.extent.log(LogStatus.INFO, "<br> UnExpected Alert <br>"
							+ Framework.extent.addScreenCapture(Framework.ScreenshotfileLocation));
					Framework.extentrpt.flush();
					alert.accept();
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					Framework.extent.log(LogStatus.FAIL, "");
					Monitoring_FrameWork.SaveResult("Run Time Error ", dataFieldRDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (webElementVal != null) {
				actionRDS = actionRDS.toUpperCase().trim();
				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}
				String s53 = actionRDS;
				switch (s53) {

				case "FILE_UPLOADER": {
					WebElement uploadElement = Functions.driver.findElement(By.xpath("//input[@id='file']"));
					uploadElement.sendKeys(new CharSequence[] { "C:\\Users\\apmosys\\Documents\\Balic/SBI.txt\r\n" });
					break;
				}
				case "FRAMECHECK": {
					System.out.println("We are in FrameSize method====================================");
					final int size = Framework.driver.findElements(By.tagName("iframe")).size();
					System.out.println("Size of the frame is ----------------" + size);
					for (int i = 0; i == size; ++i) {
						Framework.driver.switchTo().frame(i);
						final int total = Framework.driver.findElements(By.xpath(propertyValueRDS)).size();
						System.out.println("Current frame size is " + total);
					}
					break;
				}
				case "CHECK_ENABILITY": {
					actualResult = String.valueOf(webElementVal.isEnabled());
					System.out.println(actionRDS);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
						break;
					}
					break;
				}
				case "GETTEXT": {
					if (objectTypeRDS.equalsIgnoreCase("Editbox")) {
						actualResult = webElementVal.getAttribute("value");
					} else {
						actualResult = webElementVal.getText();
						System.out.println(actualResult);
					}

					if (!(dataFieldRDS.equalsIgnoreCase(" ")) || dataFieldRDS != null) {
						globleValues.put(dataFieldRDS, actualResult);
						System.out.println(" !!! Added text in globle map !!!");
					}

					if (controlRDS.equalsIgnoreCase("V")) {
						System.out.println("1" + actualResult + "2" + dataFieldRDS);
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
					}
					if (controlRDS.equalsIgnoreCase("S")) {
						Framework.savetoextfile(Framework.sTestCaseID, actualResult, dataFieldRDS);
						break;
					}
					System.out.println("ACtual Value Is == " + actualResult);
					break;
				}
				case "NICKNAME": {
					System.out.println("Nickname...........................................!!!!!!!!!!!!!!!!!!!!!!!!!");
					if (dataFieldRDS.equalsIgnoreCase("Y")) {
						System.out.println("Enter into the NickName field");
						final Random value = new Random();
						final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWSYZ";
						final char[] x = new char[6];
						for (int j = 0; j < 6; ++j) {
							x[j] = "ABCDEFGHIJKLMNOPQRSTUVWSYZ"
									.charAt(value.nextInt("ABCDEFGHIJKLMNOPQRSTUVWSYZ".length()));
						}
						final String xyz = new String(x);
						System.out.println(xyz);
						Framework.driver.findElement(By.id(propertyValueRDS)).sendKeys(new CharSequence[] { xyz });
						break;
					}
					break;
				}
				case "LOGIN_CHECK_VISIBILITY": {
					try {
						actualResult = String.valueOf(webElementVal.isDisplayed());
						System.out.println("------act---------" + actualResult);
					} catch (Exception e2) {
						Framework.errorsatus = "1";
						Framework.errorpagename = pageName;
						e2.printStackTrace();
						Framework.TakeScreenshots();
						System.out.println("Screenshot to be Added " + Framework.ScreenshotfileLocation);
						Framework.extent.log(LogStatus.FAIL, "<br> Runtime Error <br>"
								+ Framework.extent.addScreenCapture(Framework.ScreenshotfileLocation));
						Framework.extentrpt.flush();
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
					}
					System.out.println(actionRDS);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
						break;
					}
					break;
				}
				case "CHECK_INVISIBILITY": {
					actualResult = String.valueOf(!webElementVal.isDisplayed());
					Thread.sleep(2000L);
					System.out.println(actionRDS);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
						break;
					}
					break;
				}
				case "MSGBOX": {
					if (dataFieldRDS.equalsIgnoreCase("Y")) {
						final JFrame frame2 = new JFrame();
						frame2.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(frame2, "Please Enter OTP Manually");
						break;
					}
					break;
				}
				case "INPUTBOX": {
					System.out.println("!................. Input Box .................!");
					String inputValue = JOptionPane.showInputDialog(new Frame(), "Enter the result for verification");
					if (objectTypeRDS.equalsIgnoreCase("char")) {
						webElementVal.sendKeys(new CharSequence[] { inputValue });
					} else {
						try {
							webElementVal.sendKeys(inputValue);
						} catch (Exception e) {
							webElementVal.sendKeys(new CharSequence[] { inputValue });
						}
					}
					break;
				}
				case "CLOSEF4": {
					try {
						if (webElementVal.isDisplayed()) {
							final Frame frame4 = new Frame();
							frame4.setVisible(true);
							frame4.toFront();
							JOptionPane.showMessageDialog(frame4,
									String.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.ScriptName))))
											+ " \n Please close the current window manually. Then Click Ok Buton \n"
											+ Framework.pagename);
						} else {
							System.out.println("Element Not Found................");
						}
					} catch (Exception e18) {
						System.out.println(" Element Not Found...................in catch");
					}
					break;
				}
				case "ARRDOWN_ENTER": {
					try {
						webElementVal.click();
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.ARROW_DOWN });
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.ENTER });
					} catch (Exception ex16) {
					}
					break;
				}
				case "SENDKEYS": {
					try {
						if (webElementVal.isEnabled()) {
							System.out.println("actionRDS=  " + actionRDS);
							System.out.println(dataFieldRDS);
							if (dataFieldRDS.equalsIgnoreCase("TIMESTAMP")) {
								dataFieldRDS = String.valueOf(System.currentTimeMillis());
							}
							if (dataFieldRDS.equalsIgnoreCase("SystemDate")) {
								final Date oldstring = new Date();
								dataFieldRDS = new SimpleDateFormat("dd/MM/yyyy").format(oldstring);
							}
							webElementVal.sendKeys(new CharSequence[] { dataFieldRDS });
							Thread.sleep(1000L);
						}
					} catch (Exception e3) {
						e3.printStackTrace();
					}
					break;
				}
				case "ARRUP_ENTER": {
					try {
						webElementVal.click();
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.ARROW_UP });
						Thread.sleep(1000L);
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.ENTER });
					} catch (Exception ex17) {
					}
					break;
				}
				case "ARROW_DOWN": {
					try {
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.ARROW_DOWN });
					} catch (Exception ex18) {
					}
					break;
				}

				case "ARROW_UP": {
					try {
						webElementVal.click();
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.ARROW_UP });
					} catch (Exception ex19) {
					}
					break;
				}
				case "TAB": {
					try {
						webElementVal.click();
						webElementVal.sendKeys(new CharSequence[] { (CharSequence) Keys.TAB });
					} catch (Exception ex20) {
					}
					break;
				}
				case "DOWN_ARROW": {
					try {
						Robot rb = new Robot();
						rb.keyPress(40);
						rb.keyRelease(40);
						Thread.sleep(1000L);
						rb.keyPress(40);
						rb.keyRelease(40);
						Thread.sleep(1000L);
						rb.keyPress(10);
						rb.keyRelease(10);
					} catch (Exception ex21) {
					}
					break;
				}
				case "CHECKLOADING": {
					try {
						int cnt = 1;
						while (webElementVal.isDisplayed()) {
							Thread.sleep(2000L);
							System.out.println("Element Visible Yet................" + cnt);
							++cnt;
						}
						System.out.println("Element Invisible Now ................");
					} catch (Exception ex12) {
						Thread.currentThread().interrupt();
					}
					break;
				}
				case "MOVETOVISIBILITY_ABIFO": {
					try {
						System.out.println("Web Element==>" + webElementVal);
						int cnt = 1;
						while (true) {
							if (!webElementVal.isDisplayed()) {
								System.out.println("Record Found yet ................" + cnt);
								JavascriptExecutor jse2 = (JavascriptExecutor) Framework.driver;
								jse2.executeScript("window.scrollBy(0, 3000);", new Object[0]);
								jse2.executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
								Thread.sleep(2000L);
							} else if (controlRDS.equalsIgnoreCase("V")) {
								break;
							}
							++cnt;
						}
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
						System.out.println("............................. Record Found Now ................" + cnt);
						Thread.sleep(8000L);
					} catch (Exception ex13) {
						Thread.currentThread().interrupt();
					}
					break;
				}
				case "CLICK_VISIBILITY_BAL": {
					System.out.println("INside CLICK VISIBILITY..........................");
					try {
						actualResult = String.valueOf(webElementVal.isDisplayed());
						System.out.println("------act---------" + actualResult);
						Thread.sleep(1000L);
						webElementVal.click();
					} catch (Exception e2) {
						Framework.errorsatus = "1";
						Thread.currentThread().interrupt();
					}
					System.out.println(actionRDS);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
						break;
					}
					break;
				}
				}
			}
			if (actionRDS.equalsIgnoreCase("BROWSEURL")) {

				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}

//				Framework.driver.manage().deleteAllCookies();
				Framework.driver.get(dataFieldRDS);

				if (Framework.location.isEmpty()) {
					try {
						JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
						Object res = js.executeAsyncScript("navigator.geolocation.getCurrentPosition(arguments[0]);");

						String latitude = res.toString().split("latitude=")[1].split(",")[0];
						String longitude = res.toString().split("longitude=")[1].split(",")[0];

						String urlString = "https://nominatim.openstreetmap.org/reverse?lat=" + latitude + "&lon="
								+ longitude + "&format=json";
						StringBuilder response = new StringBuilder();

						try {
							URL url = new URL(urlString);
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setRequestProperty("User-Agent", "Mozilla/5.0");

							BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String inputLine;
							while ((inputLine = in.readLine()) != null) {
								response.append(inputLine);
							}
							in.close();

						} catch (Exception e) {
							System.out.println("Location         ---------------> " + "Error in Fetching Location");
						}
						Framework.location = response.toString().split("\"city\":\"")[1].split(",")[0]
								.replaceAll("\"", "").trim();
						System.out.println("Location         ---------------> " + Framework.location);
						DB_Tables.updateLocation(Framework.location);

					} catch (Exception e) {
						System.out.println("Location         ---------------> " + "Error in Fetching Location");
					}
				} else {
					System.out.println("Location         ---------------> " + "Already Fetch");
				}

			}

			else if (actionRDS.equalsIgnoreCase("UtkarshBankBROWSEURL")) {

				try {
					Framework.driver.get(dataFieldRDS);
					Thread.sleep(10000);
					if (controlRDS.equalsIgnoreCase("T")) {
						Framework.tStartTime = Monitoring_FrameWork.StartTime();
					}
					driver.navigate().refresh();

				} catch (Exception e) {
					System.out.println("Retry......");
					driver.navigate().refresh();
				}

			}

			if (actionRDS.equalsIgnoreCase("page")) {
				WebElement wb = Framework.driver.findElement(By.xpath("(//*[@class='tickIcon'])[1]"));
				JavascriptExecutor jse3 = (JavascriptExecutor) Framework.driver;
				jse3.executeScript("arguments[0].scrollIntoView();", new Object[] { wb });
				Thread.sleep(5000L);
				Framework.driver.findElement(By.xpath("//*[@class='declare']")).click();
				Thread.sleep(3000L);
				Framework.driver
						.findElement(By.xpath(
								"/html/body/div[3]/div/div/div/div/div[3]/div[1]/div[1]/div[3]/div[1]/div[13]/a"))
						.click();
			} else if (actionRDS.equalsIgnoreCase("NumericSendkeys")) {
				final char[] ch = new char[dataFieldRDS.length()];
				for (int k = 0; k < dataFieldRDS.length(); ++k) {
					ch[k] = dataFieldRDS.charAt(k);
					webElementVal.sendKeys(new CharSequence[] { String.valueOf(ch[k]) });
					Thread.sleep(1000);
				}
			} else if (actionRDS.equalsIgnoreCase("RB_BackSpace")) {
				Robot rb2 = new Robot();
				for (int count = Integer.parseInt(dataFieldRDS); count != 0; --count) {
					rb2.keyPress(8);
					rb2.keyRelease(8);
				}
			} else if (actionRDS.equalsIgnoreCase("RB_BACK")) {
				try {
					Robot robot = new Robot();
					robot.keyPress(18);
					robot.keyPress(37);
					robot.delay(200);
					robot.keyRelease(37);
					robot.keyRelease(18);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (actionRDS.equalsIgnoreCase("RB_NEWTAB")) {
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_T);
				r.keyRelease(KeyEvent.VK_T);
				r.keyRelease(KeyEvent.VK_CONTROL);
				System.out.println("------ New Tab Open Successfully ------");
			} else if (actionRDS.equalsIgnoreCase("RB_OPENURL")) {
				StringSelection s = new StringSelection(dataFieldRDS.trim());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_V);
				r.keyRelease(KeyEvent.VK_V);
				r.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(2000);
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
			} else if (actionRDS.equalsIgnoreCase("CLEAR_USING_ACTION")) {
				final Actions action2 = new Actions(Framework.driver);
				action2.moveToElement(webElementVal).doubleClick()
						.sendKeys(new CharSequence[] { (CharSequence) Keys.BACK_SPACE }).perform();
			} else if (actionRDS.equalsIgnoreCase("CLEAR_USING_ACTION1")) {
				final Actions action3 = new Actions(Framework.driver);
				action3.moveToElement(webElementVal).keyDown((CharSequence) Keys.CONTROL)
						.sendKeys(new CharSequence[] { "a", (CharSequence) Keys.BACK_SPACE })
						.keyUp((CharSequence) Keys.CONTROL).perform();
			} else if (actionRDS.equalsIgnoreCase("CLEAR_USING_ROBOT")) {
				final Robot r = new Robot();
				r.keyPress(17);
				r.keyPress(65);
				r.keyPress(127);
				r.keyRelease(17);
				r.keyRelease(65);
				r.keyRelease(127);
			} else if (actionRDS.equalsIgnoreCase("getTimeForOTP") && controlRDS.equalsIgnoreCase("T")) {
				Framework.tStartTime = Monitoring_FrameWork.StartTime();
				final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				final Date date = new Date();
				Functions.otpTime = dateFormat.format(date);
			} else if (actionRDS.equalsIgnoreCase("Robot_otp")) {
				CustomFunctions.robot_otp(Functions.otp_g);
			} else if (actionRDS.equalsIgnoreCase("RobotClick")) {
				final Robot robot2 = new Robot();
				final int x2 = Integer.parseInt(propertyValueRDS.split(" ")[0]);
				final int y3 = Integer.parseInt(propertyValueRDS.split(" ")[1]);
				System.out.println("X coordinate : " + x2 + " Y coordinate : " + y3);
				robot2.mouseMove(x2, y3);
				robot2.mousePress(1024);
				robot2.mouseRelease(1024);
			} else if (actionRDS.equalsIgnoreCase("RBClick")) {
				try {
					// Get the location of the WebElement
					Point location = webElementVal.getLocation();
					int x = location.getX();
					int y = location.getY();
					System.out.println("X coordinate : " + x + " Y coordinate : " + y);
					// Create a Robot instance
					Robot robot = new Robot();
					// Simulate moving the mouse to the element's coordinates
					robot.mouseMove(x, y);

					// Simulate a mouse click using Robot
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// cj
			if (actionRDS.equalsIgnoreCase("checkEmailAndNo")) {

				String value = webElementVal.getAttribute("value");
				System.out.println("------- " + value);
				if (!value.equalsIgnoreCase(dataFieldRDS)) {
					System.out.println("Mobile no and Email id Value are not changed");
					JOptionPane.showMessageDialog(null, "Mobile no and Email id Value are not changed");
					scriptEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
					DB_Tables.updateEndTimeRunTimeInstance("terminated", scriptEndTime, applicationId,
							runTimeInstanceId);
					System.exit(0);
				}

			}
			// cj
			if (actionRDS.equalsIgnoreCase("checkUrl")) {
				String currentUrl = driver.getCurrentUrl();
				String title = driver.getTitle();

				System.out.println("Provided URL=== " + dataFieldRDS);
				System.out.println("URL =========== " + currentUrl);
				System.out.println("Title ========= " + title);

				if (!currentUrl.equalsIgnoreCase(dataFieldRDS) && title.equalsIgnoreCase("Page Not Found")) {
					JOptionPane.showMessageDialog(new Frame(), "URL has been changed. Plese inform to customer.");
				}

			}

			if (actionRDS.equalsIgnoreCase("AXISBROWSEURL")) {
				System.out.println("In AXISBROWSEURL");
				NewCustomFunctions.axis_BROWSEURL(objectTypeRDS, dataFieldRDS, controlRDS);
			}

			else if (actionRDS.equalsIgnoreCase("sendKeysCopyPaste")) {
				NewCustomFunctions.sendKeysCopyPaste(webElementVal, dataFieldRDS);
			}

			else if (actionRDS.equalsIgnoreCase("fileUpdateAndUpload")) {
				NewCustomFunctions.fileUpdateAndUpload(propertyValueRDS.trim(), objectTypeRDS.trim());
			} else if (actionRDS.equalsIgnoreCase("fileUpdateAndUploadVendor")) {
				NewCustomFunctions.fileUpdateAndUploadVendor(propertyValueRDS.trim(), objectTypeRDS.trim());
			}

			else if (actionRDS.equalsIgnoreCase("copyUrlOnNewTab")) {
				CustomFunctions.copyUrlOnNewTab(webElementVal);
			} else if (actionRDS.equalsIgnoreCase("RobotMouseMove")) {
				Robot robot2 = new Robot();
				int x2 = Integer.parseInt(propertyValueRDS.split(" ")[0]);
				int y3 = Integer.parseInt(propertyValueRDS.split(" ")[1]);
				System.out.println("X coordinate : " + x2 + " Y coordinate : " + y3);
				robot2.mouseMove(x2, y3);
			} else if (actionRDS.equalsIgnoreCase("js_sendKeys")) {
				JavascriptExecutor jse4 = (JavascriptExecutor) Framework.driver;
				jse4.executeScript("arguments[0].value='" + dataFieldRDS + "';", new Object[] { webElementVal });
			} else if (actionRDS.equalsIgnoreCase("ActionSendkeys")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(webElementVal, new CharSequence[] { dataFieldRDS }).build().perform();
			} else if (actionRDS.equalsIgnoreCase("movetosendkeys")) {
				Actions act2 = new Actions(Framework.driver);
				act2.moveToElement(webElementVal).click().build().perform();
				Thread.sleep(1000L);
				act2.sendKeys(new CharSequence[] { dataFieldRDS }).build().perform();
			} else if (actionRDS.equalsIgnoreCase("normalSendKeys")) {
				webElementVal.sendKeys(new CharSequence[] { dataFieldRDS });
			} else if (actionRDS.equalsIgnoreCase("executeJs")) {
				((JavascriptExecutor) Framework.driver).executeScript(propertyValueRDS);
			}

			else if (actionRDS.equalsIgnoreCase("selectDOB")) {
				((JavascriptExecutor) Framework.driver)
						.executeScript("document.getElementById('dob').value='" + propertyValueRDS + "'");
			}

			else if (actionRDS.equalsIgnoreCase("js_script")) {
				System.out.println("In js_script");
				NewCustomFunctions.js_script(propertyValueRDS, dataFieldRDS);
			}
			if (actionRDS.contains("VERIFYELEMENT(")) {
				String testcase = actionRDS.split("\\(")[1].split("\\)")[0];
				Framework.Srno = Framework.recordsetRDS.getField("Srno");
				String[] coordinate = dataFieldRDS.split("#");
				int xcor = Integer.parseInt(coordinate[0]);
				int ycor = Integer.parseInt(coordinate[1]);
				WebDriverWait wait2 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				Robot rb4 = new Robot();
				boolean checkElement = false;
				try {
					WebElement wb2 = (WebElement) wait2
							.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyNameRDS)));
					checkElement = wb2.isDisplayed();
				} catch (Exception e19) {
					if (!checkElement) {
						rb4.mouseMove(xcor, ycor);
						rb4.delay(1000);
						rb4.mousePress(1024);
						rb4.mouseRelease(1024);
						rb4.delay(2000);
						while (!Framework.Srno.equals(testcase)) {
							Framework.recordsetRDS.moveNext();
							Framework.Srno = Framework.recordsetRDS.getField("Srno");
						}
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("sendkeysrobot")) {
				final StringSelection s33 = new StringSelection(dataFieldRDS);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s33, null);
			}
			if (actionRDS.equalsIgnoreCase("checkAndClickSikuli")) {
				try {
					System.out.println("------------- Inside checkAndClickSikuli-------------------------");
					final Screen s34 = new Screen();
					s34.wait(propertyValueRDS, 10.0);
					s34.exists(propertyValueRDS, 10).click();
					if (controlRDS.equalsIgnoreCase("T")) {
						Framework.tStartTime = Monitoring_FrameWork.StartTime();
					}
				} catch (Exception ex22) {
				}
			}

			if (actionRDS.equalsIgnoreCase("checkLICPageStatus1")) {

				final String filepath = System.getProperty("user.dir");
				final File file = new File(String.valueOf(filepath) + "/LICPageStatus.txt");
				FileReader reader = null;

				if (file.exists()) {
					try {
						reader = new FileReader(file);
						final char[] chars = new char[(int) file.length()];
						reader.read(chars);
						licFlag = new String(chars).trim();
						reader.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}

					System.out.println("lic flag status === " + licFlag);
					if (licFlag.equalsIgnoreCase("True")) {

						file.createNewFile();
						final FileWriter fileWritter = new FileWriter(file, false);
						final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write("False");
						bufferWritter.close();
						System.out.println("flag Write success.......False");

					} else {
						while (true) {
							if ((!Framework.pagename.equalsIgnoreCase(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}

					}

				} else {

					JOptionPane.showMessageDialog(null, "LICPageStatus.txt File not found");

				}

			}
			if (actionRDS.equalsIgnoreCase("checkLICPageStatus2")) {

				final File file = new File(System.getProperty("user.dir") + "/LICPageStatus.txt");

				System.out.println("lic flag status === " + licFlag);
				if (licFlag.equalsIgnoreCase("False")) {
					file.createNewFile();
					final FileWriter fileWritter = new FileWriter(file, false);
					final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write("True");
					bufferWritter.close();
					System.out.println("flag Write success.......True");

				} else {
					while (true) {
						if ((!Framework.pagename.equalsIgnoreCase(propertyValueRDS))) {
							Framework.recordsetRDS.moveNext();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
						} else {
							Framework.recordsetRDS.movePrevious();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
							break;
						}
					}

				}
			}

			if (actionRDS.equalsIgnoreCase("refreshUntillElementVisibile")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
				for (int i = 0; i < 100; i++) {
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
						System.out.println("Element  found ........");
						break;
					} catch (Exception e) {
						System.out.println("Element not found ........");
						Framework.driver.navigate().refresh();
					}
					Thread.sleep(2000);
				}
			}
			// prasad

			if (actionRDS.equalsIgnoreCase("sendValueRb")) {

				CustomFunctions.sendValueRb(webElementVal, objectTypeRDS);
			} else if (actionRDS.equalsIgnoreCase("sendValueRb2")) {

				CustomFunctions.sendValueRb2(webElementVal, objectTypeRDS);
			}

			if (actionRDS.equalsIgnoreCase("selectDropDown")) {
				Thread.sleep(2000);
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(60));
				try {
					WebElement sel = wait
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));
					Thread.sleep(2000);

					Select dropdown2 = new Select(sel);
					dropdown2.selectByVisibleText(objectTypeRDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (actionRDS.equalsIgnoreCase("openChrome")) {
				try {
					Runtime.getRuntime().exec("google-chrome");
				} catch (IOException e) {
					System.out.println("Error launching Chrome: " + e.getMessage());
				}
			} else if (actionRDS.equalsIgnoreCase("checkAndClickSikuli")) {
				try {
					System.out.println("------------- Inside checkAndClickSikuli-------------------------");
					Screen s34 = new Screen();
					s34.wait((Object) propertyValueRDS, 10.0);
					s34.exists((Object) propertyValueRDS).click();
					if (controlRDS.equalsIgnoreCase("T")) {
						Framework.tStartTime = Monitoring_FrameWork.StartTime();
					}
				} catch (Exception ex22) {
				}
			} else if (actionRDS.equalsIgnoreCase("SIKULISCROLLDOWN")) {

				try {
					Screen s1 = new Screen();
					System.out.println("=========inside scrolldown===================");
					// Pattern hover_element = new Pattern(PropertyValueRDS);
					// s1.hover(hover_element);
					Thread.sleep(1000);
					// s1.wheel(1, -8);
					s1.wheel(1, 10);

				} catch (Exception e) {
					e.printStackTrace();

				}
			} else if (actionRDS.equalsIgnoreCase("SIKULISCROLLDOWN_half")) {

				try {
					Screen s1 = new Screen();
					System.out.println("=========inside scrolldown===================");
					// Pattern hover_element = new Pattern(PropertyValueRDS);
					// s1.hover(hover_element);
					Thread.sleep(1000);
					// s1.wheel(1, -8);
					s1.wheel(1, 5);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (actionRDS.equalsIgnoreCase("sikuliMove")) {
				final Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 90.0);
				Location location = s34.find(propertyValueRDS).getTarget();
				// Move the mouse to the location
				s34.mouseMove(location);
			}

			if (actionRDS.equalsIgnoreCase("verifyAlert")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
				Thread.sleep(2000);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS))).click();
				Thread.sleep(2000);
				try {
					Alert alert = wait.until(ExpectedConditions.alertIsPresent());
					Thread.sleep(1000);
					alert.accept();
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult("true", "true");
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult("false", "true");
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("startBrowser")) {
				// Har genratation
				// proxy = new BrowserMobProxyServer();
				// proxy.start(0);
				//
				// Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

				ChromeOptions options2 = new ChromeOptions();
				// options2.setProxy(seleniumProxy);

				// for dislabe chrome is automated bar
				options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				options2.addArguments(new String[] { "--disable-notifications" });
				options2.addArguments(new String[] { "--disable-extentions" });
				options2.addArguments(new String[] { "disable-infobars" });
				options2.addArguments(new String[] { "disable-captcha" });
				options2.addArguments(new String[] { "--remote-allow-origins=*" });
				options2.addArguments(new String[] { "--disable-popup-blocking" });
				options2.addArguments(new String[] { "--no-sandbox" });
//				options2.addArguments(new String[] { "window-size=1920,1080" });
				// new added for disable password window popup
				options2.addArguments(new String[] { "--disable-save-password-bubble" });
				// options2.addArguments(new String[] { "--ignore-certificate-errors" });
				// options2.addArguments("Browser.setDownloadBehavior","allow");
				if (Framework.headless.equalsIgnoreCase("true")) {
					options2.addArguments(new String[] { "--headless" });
					options2.addArguments(new String[] { "window-size=1920,1080" });
				}

				Framework.driver = (WebDriver) new ChromeDriver(options2);
				Framework.driver.manage().window().maximize();
				// Framework.driver.manage().deleteAllCookies();
				// Framework.driver.manage().timeouts().pageLoadTimeout(90L, TimeUnit.SECONDS);
				try {
					Framework.driver.manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(Framework.pageLoadTime)));
				} catch (Exception e) {
					Framework.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
				}
				// proxy
				// proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,
				// CaptureType.RESPONSE_CONTENT);
				// proxy.newHar(ApplicationName);

				// Framework.driver.manage().window().maximize();
				// Framework.browsersts = 1;

				// wait = new WebDriverWait(Framework.driver,
				// Duration.ofSeconds(Framework.defaultwaittime));

			}
			if (actionRDS.equalsIgnoreCase("LicStartBrowser")) {
				ChromeOptions options2 = new ChromeOptions();
				options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				options2.addArguments(new String[] { "--remote-allow-origins=*" });
				options2.addArguments("--disable-blink-features=AutomationControlled");// for cloud flarecaptcha
				if (Framework.headless.equalsIgnoreCase("true")) {
					options2.addArguments(new String[] { "--headless" });
					options2.addArguments(new String[] { "window-size=1920,1080" });
				}
				if (fastPageLoad.equalsIgnoreCase("Y")) {
					options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
				}

				Framework.driver = (WebDriver) new ChromeDriver(options2);
				Framework.driver.manage().window().maximize();
				// Framework.driver.manage().deleteAllCookies();
				// Framework.driver.manage().timeouts().pageLoadTimeout(90L, TimeUnit.SECONDS);
				try {
					Framework.driver.manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(Framework.pageLoadTime)));
				} catch (Exception e) {
					Framework.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
				}
				System.out.println("Browser has Started Successfully");
			}

			if (actionRDS.equalsIgnoreCase("validateLocator")) {
				CustomFunctions.validateLocator(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("AxisResetUrl")) {
				String host = "smtp.gmail.com";
				String port = "587";
				String username = objectTypeRDS.trim();
				String pass = propertyValueRDS.trim();
				String dataField = "Axis Bank - Password Reset Link";

				String link = FetchMail.getResetLinkForAxis(host, username, pass, port, dataField);

				((JavascriptExecutor) driver).executeScript("window.open()");
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				System.out.println("Total tab Size is === " + tabs.size());
				int size = tabs.size();
				driver.switchTo().window(tabs.get(size - 1));
				driver.get(link);
				Thread.sleep(1000);
				Framework.tStartTime = Monitoring_FrameWork.StartTime();

			}
			if (actionRDS.equalsIgnoreCase("verifyYonoSbiPages")) {

				SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
				String currentDate = sm.format(new Date());
				String day = currentDate.split(" ")[1];
				System.out.println("Day is == " + day);
				String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
				System.out.println("Current hour is == " + hour);

				if ((Float.parseFloat(hour) >= 7.00 && Float.parseFloat(hour) < 20.00)) {
					System.out.println(" == continue running pages == ");
				} else {
					System.out.println("===Skip Pages===");
					while (!Framework.pagename.equals(propertyValueRDS)) {
						Framework.recordsetRDS.moveNext();
						Framework.pagename = Framework.recordsetRDS.getField("PageName");
					}
					Framework.recordsetRDS.movePrevious();

				}
			}

			if (actionRDS.equalsIgnoreCase("VerifyHDFCNiftyETF")) {

				System.out.println("In VerifyHDFCNiftyETF");
				if (Framework.bankHoliday.equalsIgnoreCase("N")) {
					SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
					String currentDate = sm.format(new Date());
					String day = currentDate.split(" ")[1];
					System.out.println("Day is == " + day);
					String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
					System.out.println("Current hour is == " + hour);

					if ((Float.parseFloat(hour) >= 9.00 && Float.parseFloat(hour) < 16.00)
							&& !day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday")) {

						NewCustomFunctions.VerifyHDFCNiftyETF(webElementVal);
					} else {
						System.out.println("This function didn't work in off Market");
					}
				}
			} else if (actionRDS.equalsIgnoreCase("VerifyHDFCGoldETF")) {
				System.out.println("In VerifyHDFCGoldETF");
				if (Framework.bankHoliday.equalsIgnoreCase("N")) {
					SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
					String currentDate = sm.format(new Date());
					String day = currentDate.split(" ")[1];
					System.out.println("Day is == " + day);
					String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
					System.out.println("Current hour is == " + hour);

					// run in 23:30 to 9:10
					if (((Float.parseFloat(hour) >= 9.30 && Float.parseFloat(hour) < 9.45)
							|| ((Float.parseFloat(hour) >= 23.30) && (Float.parseFloat(hour) < 23.59)))
							&& (!day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday"))) {
						NewCustomFunctions.VerifyHDFCGoldETF(webElementVal);

					} else {
						System.out.println("This function didn't work in on Market and HoliDay");
					}
				}
			}

			else if (actionRDS.equalsIgnoreCase("VerifyHDFCSilverETF")) {
				System.out.println("In VerifyHDFCSilverETF");
				if (Framework.bankHoliday.equalsIgnoreCase("N")) {
					SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
					String currentDate = sm.format(new Date());
					String day = currentDate.split(" ")[1];
					System.out.println("Day is == " + day);
					String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
					System.out.println("Current hour is == " + hour);

					// run in 23:30 to 9:10
					if (((Float.parseFloat(hour) >= 9.30 && Float.parseFloat(hour) < 9.45)
							|| ((Float.parseFloat(hour) >= 23.30) && (Float.parseFloat(hour) < 23.59)))
							&& (!day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday"))) {
						NewCustomFunctions.VerifyHDFCSilverETF(webElementVal);

					} else {
						System.out.println("This function didn't work in on Market and HoliDay");
					}
				}
			}

			if (actionRDS.equalsIgnoreCase("verifyPortfolio")) {
				CustomFunctions.verifyPortfolio(propertyValueRDS, dataFieldRDS);
			}
			if (actionRDS.equalsIgnoreCase("clickOn1stMaker")) {
				CustomFunctions.clickOn1stMaker(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("verifyStocks")) {
				CustomFunctions.verifyStocks(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("findRow&click1RS")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				// tbody/tr
				try {
					List<WebElement> list = Framework.driver.findElements(By.xpath(propertyValueRDS));
					int size = list.size();
					System.out.println("List Size is === " + size);
					for (int j = 1; j <= size; j++) {
						// gettext from rupees
						String xpath = "(" + propertyValueRDS + "/td[7])[" + j + "]";
						System.out.println("Rupees Xpath Is == " + xpath);
						String status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
								.getText();
						System.out.println("Rupees Is  == " + status);

						// owner gettext
						String ownerXpath = "(" + propertyValueRDS + "/td[3])[" + j + "]";
						System.out.println("Owner Xpath Is == " + ownerXpath);
						String ownername = wait
								.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ownerXpath))).getText();
						System.out.println("Owner Is  == " + ownername);

						if (status.equalsIgnoreCase("₹ 1.00") && ownername.equalsIgnoreCase("Bibhu")) {
							// ₹ 2,00,000.00
							System.out.println("Actual Owner Name is == " + ownername);
							System.out.println("Actual Rupees Is == " + status);
							Thread.sleep(3000);
							Framework.driver.findElement(By.xpath(
									"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
											+ j + "]/td[1]/div/label/input"))
									.click();
							break;
						}
					}
				} catch (Exception e) {
				}
			}

			if (actionRDS.equalsIgnoreCase("checkLastTratedPrice")) {

				SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
				String currentDate = sm.format(new Date());
				String day = currentDate.split(" ")[1];
				System.out.println("Day is == " + day);
				String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
				System.out.println("Current hour is == " + hour);

				if ((Float.parseFloat(hour) >= 9.00 && Float.parseFloat(hour) < 15.30)
						&& !day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday")) {
					CustomFunctions.checkLastTratedPrice(propertyValueRDS, dataFieldRDS);
				}
			}

//			if (actionRDS.equalsIgnoreCase("checkLTPIndices")) {
//
//				SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
//				String currentDate = sm.format(new Date());
//				String day = currentDate.split(" ")[1];
//				System.out.println("Day is == " + day);
//				String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
//				System.out.println("Current hour is == " + hour);
//
//				if ((Float.parseFloat(hour) >= 9.00 && Float.parseFloat(hour) < 15.30)
//						&& !day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday")) {
//					CustomFunctions.checkLTPIndices(propertyValueRDS, dataFieldRDS, objectTypeRDS);
//				}
//			}
			// Bhaba,
			else if (actionRDS.equalsIgnoreCase("sendMinimumValue")) {
				NewCustomFunctions.sendMinimumValue(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("sendModifiedValue")) {
				NewCustomFunctions.sendModifiedValue(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("sendMaximunValue")) {
				NewCustomFunctions.sendMaximumValue(objectTypeRDS, webElementVal, dataFieldRDS);
			}
			else if (actionRDS.equalsIgnoreCase("selectL&TPlHlDob")) {
				NewCustomFunctions.selectLAndTPLHLDob(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("checkElement&Refresh")) {
				NewCustomFunctions.checkElementandRefresh(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("hdfcInvestorSendkeys")
					|| actionRDS.equalsIgnoreCase("flutterSendkeys")) {
				NewCustomFunctions.flutterSendkeys(propertyValueRDS, dataFieldRDS);

			}

			else if (actionRDS.equalsIgnoreCase("clickAtEndOfElement")) {
				NewCustomFunctions.clickAtEndOfElement(webElementVal);
			} else if (actionRDS.equalsIgnoreCase("verifyAcceptalert")) {

				try {
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
					Alert alert = wait.until(ExpectedConditions.alertIsPresent());
					Thread.sleep(1000);
					alert.accept();
					System.out.println("Alert is found and accepted");

				} catch (Exception e) {
					System.out.println("Alert is not appear");
				}
			} else if (actionRDS.equalsIgnoreCase("SELECT")) {
				Select select = new Select(webElementVal);
				select.selectByVisibleText(dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("SELECTINDEX")) {
				Select select = new Select(webElementVal);
				select.selectByIndex(Integer.parseInt(dataFieldRDS));
			} else if (actionRDS.equalsIgnoreCase("FILE_UPLOAD")) {
				webElementVal.sendKeys(new CharSequence[] { dataFieldRDS });
			} else if (actionRDS.equalsIgnoreCase("CHECK_VISIBILITY")) {
				try {
					actualResult = String.valueOf(webElementVal.isDisplayed());
					System.out.println("WebElemnt is displayed ---------> " + actualResult);
				} catch (Exception e) {// handling staleelement
//					try {
//						webElementVal = CheckObjectVisibility(propertyNameRDS, propertyValueRDS, objectTypeRDS,
//								pageName);
//						actualResult = String.valueOf(webElementVal.isDisplayed());
//					} catch (Exception e1) {
					e.printStackTrace();
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					Framework.errorType = e.getClass().getName();
					try {
						Framework.errorMessage = e.getMessage().split("\n")[0];
					} catch (Exception e1) {
						Framework.errorMessage = null;
					}
					Monitoring_FrameWork.SaveResult("False", dataFieldRDS);
//					}
				}
				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
				}
			} else if (actionRDS.equalsIgnoreCase("RECHECK_VISIBILITY")) {
				try {
					actualResult = String.valueOf(webElementVal.isDisplayed());
					System.out.println("WebElemnt is displayed ---------> " + actualResult);
				} catch (Exception e) {// handling staleelement
					try {
						webElementVal = CheckObjectVisibility(propertyNameRDS, propertyValueRDS, objectTypeRDS,
								pageName);
						actualResult = String.valueOf(webElementVal.isDisplayed());
					} catch (Exception e1) {
						e.printStackTrace();
						Framework.errorsatus = "1";
						Framework.errorpagename = pageName;
						Framework.errorType = e.getClass().getName();
						try {
							Framework.errorMessage = e.getMessage().split("\n")[0];
						} catch (Exception e3) {
							Framework.errorMessage = null;
						}
						Monitoring_FrameWork.SaveResult("False", dataFieldRDS);
					}
				}
				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
				}
			}

			else if (actionRDS.equalsIgnoreCase("CHECK_OTP&VISIBILITY")) {

				try {
					String path = objectTypeRDS;
					System.out.println("Captcha Locator=" + path);
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(6));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
					System.out.println("OTP alert Found ....");

					actualResult = "false";
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					Framework.errorType = "Incorrect OTP";
					Framework.errorMessage = "A technical error occurred due to incorrect OTP validation, preventing successful navigation to the next page and requiring immediate investigation and resolution.";
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
					}

				} catch (Exception e) {
					System.out.println("No OTP alert Found ....");
					try {
						webElementVal = CheckObjectVisibility(propertyNameRDS, propertyValueRDS, objectTypeRDS,
								pageName);
						actualResult = String.valueOf(webElementVal.isDisplayed());
						System.out.println("WebElemnt is displayed ---------> " + actualResult);
					} catch (Exception e2) {
						Framework.errorsatus = "1";
						Framework.errorpagename = pageName;
						e2.printStackTrace();
					}
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
					}
				}

			} else if (actionRDS.equalsIgnoreCase("CHECK_CAPTCHA&VISIBILITY")) {

				try {
					int recordSetBacktimes = Integer.parseInt(objectTypeRDS.split("#")[1]);
					String path = objectTypeRDS.split("#")[0];
					System.out.println("Captcha Locator=" + path);
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(6));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
					System.out.println("Captcha alert Found ....");
					captchaRetryCount++;
					NewCustomFunctions.SendCaptchaStatus("false");
					if (captchaRetryCount > 3) {
						actualResult = "false";
						Framework.errorsatus = "1";
						Framework.errorpagename = pageName;
						Framework.errorType = "Incorrect Captcha";
						Framework.errorMessage = "A technical error occurred due to incorrect Captcha validation, preventing successful navigation to the next page and requiring immediate investigation and resolution.";
						if (controlRDS.equalsIgnoreCase("V")) {
							Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
						}
					} else {
						while (recordSetBacktimes >= 0) { // one more time need
							Framework.recordsetRDS.movePrevious();
							recordSetBacktimes--;
						}
					}

				} catch (Exception e) {
					System.out.println("No captcha alert Found ....");
					NewCustomFunctions.SendCaptchaStatus("true");
					try {
						webElementVal = CheckObjectVisibility(propertyNameRDS, propertyValueRDS, objectTypeRDS,
								pageName);
						actualResult = String.valueOf(webElementVal.isDisplayed());
						System.out.println("WebElemnt is displayed ---------> " + actualResult);
					} catch (Exception e2) {
						Framework.errorsatus = "1";
						Framework.errorpagename = pageName;
						e2.printStackTrace();
					}
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
					}
				}
			} else if (actionRDS.equalsIgnoreCase("GETCSSVALUE")) {
				actualResult = webElementVal.getCssValue(dataFieldRDS);
				System.out.println("Attribute Value get  -----------> " + actualResult);
				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
				}
			} else if (actionRDS.equalsIgnoreCase("MOUSEOVER")) {
				Actions action = new Actions(Framework.driver);
				action.moveToElement(webElementVal).build().perform();
			} else if (actionRDS.equalsIgnoreCase("JAVASCRIPTCLICK")) {
				JavascriptExecutor executor = (JavascriptExecutor) Framework.driver;
				try {
					executor.executeScript("arguments[0].click();", new Object[] { webElementVal });
				} catch (Exception e3) {
					executor.executeScript("arguments[0].click();", new Object[] { webElementVal });
				}
			} else if (actionRDS.equalsIgnoreCase("CLICK")) {
				webElementVal.click();
			} else if (actionRDS.equalsIgnoreCase("DOUBLECLICK")) {
				Actions actions = new Actions(driver);
				actions.moveToElement(webElementVal).doubleClick().build().perform();
			} else if (actionRDS.equalsIgnoreCase("CLEAR")) {
				webElementVal.clear();
			}

			/////
			else if (actionRDS.equalsIgnoreCase("ReadBandhanBNPMailOtp")) {
				NewCustomFunctions.ReadBandhanBNPMailOtp(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("openBandhanurl")) {
				NewCustomFunctions.openBandhanurl(webElementVal);
			} else if (actionRDS.equalsIgnoreCase("readAffinityMailotp")) {
				NewCustomFunctions.readAffinityMailotp(webElementVal, dataFieldRDS);
			}

			// jp

			else if (actionRDS.equalsIgnoreCase("clearAndSendValue")) {
				NewCustomFunctions.clearAndSendValue(webElementVal, dataFieldRDS);
			}

			else if (actionRDS.equalsIgnoreCase("readMstockMailOtp")) {
				NewCustomFunctions.readMstockMailOtp(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("readYesBankMailOtp")) {
				NewCustomFunctions.readYesBankMailOtp(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("sendbajajMobileNo") || actionRDS.equalsIgnoreCase("sendSlotValue")) {
				NewCustomFunctions.sendbajajMobileNo(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("verifyAxisSubmitPage")) {

				System.out.println(" In verifyAxisSubmitPage");
				LocalTime curTime = LocalTime.now();
				LocalTime startTime = LocalTime.of(9, 15);
				LocalTime endTime = LocalTime.of(18, 30);

				if (curTime.isAfter(startTime) && curTime.isBefore(endTime)) {
					System.out.println("Time To be check for Submit");
					NewCustomFunctions.verifyAxisSubmitPage(propertyValueRDS);
				} else {
					System.out.println("Time over can't check for Submit");
					while (true) {
						if (!Framework.pagename.equals(propertyValueRDS)) {
							Framework.recordsetRDS.moveNext();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
						} else {
							Framework.recordsetRDS.movePrevious();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
							break;
						}
					}
				}
			}

			else if (actionRDS.equalsIgnoreCase("CHECK_CONSOLABILITY")) {
				String txt = "";
				JavascriptExecutor js = (JavascriptExecutor) driver;
				try {
					txt = (String) js.executeScript(propertyValueRDS);
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("Your text" + txt);

				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(txt.trim(), dataFieldRDS.trim());
				}
			} else if (actionRDS.equalsIgnoreCase("verifyDownloadedFile")) {
				NewCustomFunctions.verifyDownloadedFile(objectTypeRDS, propertyValueRDS, dataFieldRDS);
			}

			else if (actionRDS.equalsIgnoreCase("MultipleCheck_Visibility")) {
				System.out.println("In MultipleCheck_Visibility");
				try {
					String paths[] = dataFieldRDS.split("#");

					WebDriverWait wait = new WebDriverWait(Framework.driver, 15);
					for (int i = 0; i < paths.length; i++) {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(paths[i])));
					}
					System.out.println("All checkVisibility Passes");
					actualResult = "True";
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, "True");
					}
				} catch (Exception e3) {
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					e3.printStackTrace();
					actualResult = "False";
					Monitoring_FrameWork.SaveResult(actualResult, "True");
				}

			} else if (actionRDS.equalsIgnoreCase("CHECK_PRESENCEABILITY")) {
				System.out.println("In CHECK_PRESENCEABILITY");
				WebElement ele = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));
				actualResult = String.valueOf(ele.isEnabled());
				System.out.println("actualResult" + actualResult);
				System.out.println("dataFieldRDS" + actualResult);
				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
				}
			} else if (actionRDS.equalsIgnoreCase("verifySessionExpire")) {
				System.out.println("In verifySessionExpire");

				try {
					WebElement ele = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime))
							.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
					System.out.println("Session Expired , Login With Credentials");
				} catch (Exception e) {
					System.out.println(" No Session Expired ");
					NewCustomFunctions.verifySessionExpire();
				}
			}

			else if (actionRDS.equalsIgnoreCase("verifyexecuteJs")) {
				System.out.println("In verifyexecuteJs");
				try {
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(15));
					wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(objectTypeRDS.trim())));
					((JavascriptExecutor) Framework.driver).executeScript(propertyValueRDS);
				} catch (Exception e) {
					System.out.println("---- No popup is present!-----");
				}
			} else if (actionRDS.equalsIgnoreCase("clickUsingxpath")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS.trim()))).click();
			} else if (actionRDS.equalsIgnoreCase("sendkeysUsingxpath")) {
				NewCustomFunctions.sendkeysUsingxpath(propertyValueRDS, dataFieldRDS);
			}

			else if (actionRDS.equalsIgnoreCase("fileUploadAxis")) {
				String filePath = NewCustomFunctions.renameFile(propertyValueRDS, dataFieldRDS);
				System.out.println("filepath is == " + filePath);
				final Robot robot = new Robot();
				final StringSelection svfl = new StringSelection(filePath);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(svfl, null);
				CustomFunctions.sleep(2000);

				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				CustomFunctions.sleep(50);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);

				CustomFunctions.sleep(3000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

			} else if (actionRDS.equalsIgnoreCase("scrollElementToLeft")) {
				System.out.println("In scrollElementToLeft");
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].scrollLeft=arguments[1];",
						webElementVal, dataFieldRDS.trim());

			}

			else if (actionRDS.equalsIgnoreCase("verifypage")) {
				NewCustomFunctions.verifypage(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("sendUniqueMobileNo")) {
				NewCustomFunctions.sendUniqueMobileNo(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("readLibertyApiotp")) {
				NewCustomFunctions.readLibertyApiotp(webElementVal, objectTypeRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("clickAmountTwoLakh")) {
				NewCustomFunctions.clickAmountTwoLakh(propertyValueRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("recheckVisibility")) {
				CustomFunctionAxis.recheckVisibility(propertyValueRDS, pageName);
			}

			if (actionRDS.equalsIgnoreCase("deleteChecker")) {
				CustomFunctions.deleteChecker(webElementVal, dataFieldRDS);
			}
			// cj
			if (actionRDS.equalsIgnoreCase("GrowthAndIDCWClick")) {
				webElementVal.click();
				CustomFunctions.clickOnGrowthAndIDCW(dataFieldRDS);
			}
			// cj
			if (actionRDS.equalsIgnoreCase("DailyMonthlyWeeklyClick")) {
				webElementVal.click();
				CustomFunctions.clickDailyMonthlyWeekly(dataFieldRDS);
			}
			if (actionRDS.equalsIgnoreCase("approveChecker")) {
				CustomFunctions.approveChecker(webElementVal, dataFieldRDS);
			}
			if (actionRDS.equalsIgnoreCase("clickOnImpsAndRtgs1")) {
				CustomFunctions.checkerImpsRtgs(propertyValueRDS, dataFieldRDS, objectTypeRDS);
			}
			if (actionRDS.equalsIgnoreCase("clickOnImpsAndRtgs")) {
				NewCustomFunctions.checkerImpsRtgs(propertyValueRDS, dataFieldRDS, objectTypeRDS);
			}
			if (actionRDS.equalsIgnoreCase("navigateTo")) {
				Framework.driver.navigate().to(dataFieldRDS.trim());
			}

			if (actionRDS.equalsIgnoreCase("verifyAxisFiles")) {
				CustomFunctions.verifyFiles(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("executor_click")) {
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("document.getElementsByClassName('" + propertyValueRDS + "')[0].click();");
			}
			if (actionRDS.equalsIgnoreCase("verifyAxisDownloadedFiles")) {
				CustomFunctions.axisDownloadedFiles(propertyValueRDS, dataFieldRDS);
			}
			if (actionRDS.equalsIgnoreCase("verifyDownloadedPdfFiles")) {
				CustomFunctions.verifyDownloadedPdfFiles(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("verifyDownloadedFiles")) {
				CustomFunctions.verifyDownloadedFiles(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("verifyABMFLoginPage")) {
				NewCustomFunctions.verifyABMFLoginPage(propertyValueRDS, dataFieldRDS);
			}

//			if (actionRDS.equalsIgnoreCase("getCaptch")) {
//				NewCustomFunctions.readCaptchfromApi(webElementVal);
//			}
//			if (actionRDS.equalsIgnoreCase("sendCaptch")) {
//				NewCustomFunctions.sendCaptch(webElementVal);
//			}
			else if (actionRDS.equalsIgnoreCase("retryFetchCaptcha")) {
				Captcha.retryFetchCaptcha(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("fetchCaptcha")) {
				NewCustomFunctions.fetchCaptcha(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("sendCaptcha")) {
				NewCustomFunctions.fetchCaptcha(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("readOTPAPI")) {
				System.out.println("In readOTPAPI");
				NewCustomFunctions.getOTPAPI(webElementVal, objectTypeRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("readOTPAPIAxis")) {
				System.out.println("In readOTPAPI");
				NewCustomFunctions.getOTPAPIAxis(webElementVal, objectTypeRDS, dataFieldRDS);
			} else if (actionRDS.toUpperCase().contains("READOTPAPI(")) {
				System.out.println("In READOTPAPI()");
				String times = actionRDS.split("\\(")[1].split("\\)")[0];
				NewCustomFunctions.getOTPAPIloopTime(webElementVal, objectTypeRDS, dataFieldRDS, times,
						propertyValueRDS);
			} else if (actionRDS.toUpperCase().contains("READOTPAPILATEST(")) {
				System.out.println("In READOTPAPILATEST");
				String times = actionRDS.split("\\(")[1].split("\\)")[0];
				NewCustomFunctions.readOtpApiLatest(webElementVal, objectTypeRDS, dataFieldRDS, times,
						propertyValueRDS);
			} else if (actionRDS.toUpperCase().contains("READMOBILEOTPORMAILOTP(")) {
				System.out.println("In readMobileOtpOrMailOtp");
				String times = actionRDS.split("\\(")[1].split("\\)")[0];
				NewCustomFunctions.readMobileOtpOrMailOtp(webElementVal, objectTypeRDS, dataFieldRDS, times,
						propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("VerifyUtkarshAdharOtp")) {

				System.out.println("In VerifyUtkarshAdharOtp");
				int minute = LocalDateTime.now().getMinute();
				if (minute <= 15) {
					System.out.println("Now  its time to check Adhar Otp.");
					NewCustomFunctions.verifyUtkarshAdharOtp(dataFieldRDS);
				} else {
					System.out.println("It has already run; it runs once an hour.");
				}
				try {
					actualResult = String.valueOf(webElementVal.isDisplayed());
					System.out.println("WebElemnt is displayed ---------> " + actualResult);
				} catch (Exception e2) {
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					e2.printStackTrace();
				}
				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(actualResult, "true");
				}

			} else if (actionRDS.equalsIgnoreCase("readOTPAPILongTime")) {
				NewCustomFunctions.getOTPAPILongTime(webElementVal, objectTypeRDS, dataFieldRDS);
			} else if (actionRDS.toUpperCase().contains("READBAJAJOTPAPI(")) {

				System.out.println("In readBajajOtpApi()");
				String times = actionRDS.split("\\(")[1].split("\\)")[0];
				NewCustomFunctions.readBajajOtpApi(webElementVal, objectTypeRDS, dataFieldRDS, times, propertyValueRDS);
			}
			if (actionRDS.equalsIgnoreCase("readCaptcha")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				WebElement wb1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));

				String captcha = Captcha.downloadCaptcha(wb1);

				WebElement wb = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS)));

				if (objectTypeRDS.equalsIgnoreCase("js")) {
					JavascriptExecutor jse4 = (JavascriptExecutor) Framework.driver;
					jse4.executeScript("arguments[0].value='" + captcha + "';", new Object[] { wb });
				} else {
					wb.sendKeys(captcha);
				}
			}
			if (actionRDS.equalsIgnoreCase("captchaForESAF")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				String captcha = webElementVal.getAttribute("value").replaceAll(" ", "");
				System.out.println("captcha is == " + captcha);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS))).sendKeys(captcha);
			}

			if (actionRDS.equalsIgnoreCase("verify_SBI_Maker_RefrenceNo")) {
				Total_Report.refrenceId(propertyValueRDS, dataFieldRDS);
			}
			if (actionRDS.equalsIgnoreCase("verify_SBI_Maker_Date")) {
				Total_Report.verifyDate(propertyValueRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("SBINPSverifyAndClick")) {
				NewCustomFunctions.SBINPSverifyAndClick(propertyValueRDS);
			}
			if (actionRDS.equalsIgnoreCase("verifyChequeNumber")) {
				Total_Report.checkNumber(propertyValueRDS, dataFieldRDS);
			}
			if (actionRDS.equalsIgnoreCase("pauseScript")) {
				Frame frame = new Frame();
				frame.setVisible(true);
				frame.toFront();
				JOptionPane.showMessageDialog(frame, "Press OK To Continue");
			}

			if (actionRDS.equalsIgnoreCase("verifyShadowRoot")) {
				Thread.sleep(2000);
				WebElement shadowHost = Framework.driver.findElement(By.xpath(propertyValueRDS));
				JavascriptExecutor jsExecutor = (JavascriptExecutor) Framework.driver;

				WebElement shadowRoot = (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot;",
						shadowHost);
				WebElement shadowElement = shadowRoot.findElement(By.xpath(dataFieldRDS));
				Thread.sleep(2000);
				if (shadowElement.isDisplayed()) {
					System.out.println(" == Element found == ");
					Monitoring_FrameWork.SaveResult("true", "true");
				} else {
					System.out.println(" == Element not found == ");
					Monitoring_FrameWork.SaveResult("true", "false");
				}
			}
			if (actionRDS.equalsIgnoreCase("clickOnCurrentDate")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
				SimpleDateFormat sm = new SimpleDateFormat("dd");
				String date = sm.format(new Date());

				if (String.valueOf(date.charAt(0)).equals("0")) {
					String resultStr = date.replaceFirst("^0+", "");
					date = resultStr;
				}

				System.out.println("Current date is == " + date);
				// a[text()='23']
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + date + "']"))).click();

			}

			if (actionRDS.equalsIgnoreCase("verifyFrameClick")) {
				final WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
				try {
					if (objectTypeRDS.equalsIgnoreCase("FRAMEINDEX")) {
						final int index = Integer.parseInt(propertyValueRDS);
						Framework.driver.switchTo().frame(index);
						System.out.println("switch to.........................." + propertyValueRDS);
					}
					if (objectTypeRDS.equalsIgnoreCase("FRAMENAME")) {
						Framework.driver.switchTo().frame(Framework.driver.findElement(By.name(propertyValueRDS)));
						System.out.println("switch to........................." + propertyValueRDS);
					}
					if (objectTypeRDS.equalsIgnoreCase("FRAMEXPATH")) {
						Framework.driver.switchTo().frame(Framework.driver.findElement(By.xpath(propertyValueRDS)));
						System.out.println("switch to........................." + propertyValueRDS);
					}
					if (objectTypeRDS.equalsIgnoreCase("FRAMEID")) {
						Framework.driver.switchTo().frame(Framework.driver.findElement(By.id(propertyValueRDS)));
						System.out.println("switch to........................" + propertyValueRDS);
					}

					try {
						((WebElement) wait3.until(
								(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS))))
								.click();
					} catch (Exception ex23) {
					}
					Framework.driver.switchTo().defaultContent();
					System.out.println("switch to.....................Deafult Content");

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("No Frame Available");

				}

			}

			// axisNeo function for vansh

			if (actionRDS.equalsIgnoreCase("AxisNeoValueCheck")) {

				String currency = "";
				List<String> curList = null;
				try {
					SimpleDateFormat smd = new SimpleDateFormat("dd/MM/yy");
					String date = smd.format(new Date());
					System.out.println("Today's date --> " + date);

					Fillo fillo = new Fillo();
					com.codoid.products.fillo.Connection con = fillo.getConnection(dataFieldRDS);

					String query = "Select * from Sheet1 where DATE='" + date + "'";

//											String query = "Select * from Sheet1 ";
					Recordset recordset = con.executeQuery(query);
					if (recordset.next()) {
						String SheetDate = recordset.getField("DATE").trim();
						currency = recordset.getField("CURRENCY");

						System.out.println("Date == " + SheetDate + " Currencies == " + currency);
						String[] currencies = currency.split(",");
						curList = new ArrayList<>();

						for (String x : currencies) {
							curList.add(x + "INR");
						}
						System.out.println(curList);

					} else {
						System.out.println("No data Present");
					}

				} catch (Exception e) {
					System.out.println("NO record found for today's date");
				}
				CustomFunctionAxis.checkCurrencyValuesNew(objectTypeRDS, propertyValueRDS.split("#")[0],
						propertyValueRDS.split("#")[1], curList);

			} else if (actionRDS.equalsIgnoreCase("verifyAxisNeoPage")) {
				try {
					new WebDriverWait(driver, 30)
							.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)))
							.isDisplayed();
				} catch (Exception e) {

					System.out.println("Page is not visible");
					if (scType.equalsIgnoreCase("robot")) {
						Framework.TakeScreenshots();
					} else {
						Monitoring_FrameWork.takeScreenshot();
					}
					String subject = "FM Page not visible";
					String messageBody = "Dear Sir/Ma'am,<br>"
							+ "FM Connect URL is not working. Please check the same & accordingly take action <br><br>"
							+ "Best regards,<br>" + "ApMoSys Technologies";

					FetchMail.sendAlertMail(subject, messageBody);
				}

			}

			if (actionRDS.equalsIgnoreCase("L&TcheckMinute")) {

				String output = null;
				final String filepath = System.getProperty("user.dir");
				final File file = new File(String.valueOf(filepath) + "/L&TTimeCheck.txt");
				FileReader reader = null;
				if (file.exists()) {
					try {
						reader = new FileReader(file);
						final char[] chars = new char[(int) file.length()];
						reader.read(chars);
						output = new String(chars);
						reader.close();
					} catch (Exception ex) {
						ex.printStackTrace();
						if (reader != null) {
							try {
								reader.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}

					String minuteDiff = BusinessHour.monitoringTimeCompare(output);

					if (Integer.parseInt(minuteDiff) >= 60) {
						file.createNewFile();
						final FileWriter fileWritter = new FileWriter(file, false);
						final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(BusinessHour.currentDateAndTime);
						bufferWritter.close();

					} else {
						while (true) {
							if ((Framework.pagename.equals(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}

					}

				} else {
					final SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					String currentDateAndTime = d.format(new Date());
					System.out.println("Current Date And Time Is === " + currentDateAndTime);
					file.createNewFile();
					final FileWriter fileWritter = new FileWriter(file, false);
					final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(currentDateAndTime);
					bufferWritter.close();
				}

			}

			// remove some robot static function

			else if (actionRDS.equalsIgnoreCase("singleManualOTP4")) {

				Frame frame = new Frame();
				frame.setVisible(false);
				frame.toFront();
				String activationcode = JOptionPane.showInputDialog(frame, "Enter the otp");
				System.out.println("OTP Is === " + activationcode);
				Functions.otp1 = activationcode.charAt(0);
				Functions.otp2 = activationcode.charAt(1);
				Functions.otp3 = activationcode.charAt(2);
				Functions.otp4 = activationcode.charAt(3);
			} else if (actionRDS.equalsIgnoreCase("runOnOffTime")) {

				SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
				SimpleDateFormat day = new SimpleDateFormat("EEEE");
				String currentDay = day.format(new Date());
				System.out.println("Current Day Is === " + currentDay);
				Date starttime = parser.parse("17:00:00");
				Date Endtime = parser.parse("09:30:00");
				Date currenttime = parser.parse(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				System.out.println();

				if (!currentDay.equalsIgnoreCase("Saturday") || !currentDay.equalsIgnoreCase("Sunday")) {
					if (currenttime.after(starttime) && currenttime.before(Endtime)) {
						System.out.println("  === Continue Running Pages===");
					} else {
						System.out.println("===Skip Pages===");
						while (true) {
							if ((Framework.pagename.equals(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}
					}
				}
			}

			else if (actionRDS.equalsIgnoreCase("getCurrentDate")) {

				SimpleDateFormat sm = new SimpleDateFormat(dataFieldRDS);

				String date = sm.format(new Date());
				System.out.println("Current date is === " + date);

				if (objectTypeRDS.equalsIgnoreCase("js")) {
					final JavascriptExecutor jse4 = (JavascriptExecutor) Framework.driver;
					jse4.executeScript("arguments[0].value='" + date + "';", new Object[] { webElementVal });
				} else {
					webElementVal.sendKeys(date);
				}

			}

			else if (actionRDS.equalsIgnoreCase("verifyPowerAxisPages")) {

				SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
				String currentDate = sm.format(new Date());
				String day = currentDate.split(" ")[1];
				System.out.println("Day is == " + day);
				String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
				System.out.println("Current hour is == " + hour);

				if ((Float.parseFloat(hour) >= 7.00 && Float.parseFloat(hour) < 23.00)) {
					System.out.println(" == continue running pages == ");
				} else {
					System.out.println("===Skip Pages===");
					while (!Framework.pagename.equals(propertyValueRDS)) {
						Framework.recordsetRDS.moveNext();
						Framework.pagename = Framework.recordsetRDS.getField("PageName");
					}
					Framework.recordsetRDS.movePrevious();

				}
			}

			else if (actionRDS.equalsIgnoreCase("takeScreenShot")) {

				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Rectangle screenRectangle = new Rectangle(screenSize);
				Robot robot = new Robot();
				BufferedImage image = robot.createScreenCapture(screenRectangle);
				// new File(String.valueOf(Framework.homedir) + "/screenshot/").mkdir();

				Date nowScreen = new Date();
				String ScreenShotTime = Framework.pagename + "_" + nowScreen.getHours() + "_" + nowScreen.getMinutes()
						+ "_" + nowScreen.getSeconds();

				String ScreenshotfileLocation = Framework.homedir + ScreenShotTime + ".jpg";
				ImageIO.write(image, "jpg", new File(ScreenshotfileLocation));

			}

			/*
			 * if (actionRDS.equalsIgnoreCase("HDFC_checkDate")) {
			 * 
			 * List<WebElement> list; SimpleDateFormat sm = new
			 * SimpleDateFormat("dd MMM yyyy, HH:mm:ss"); SimpleDateFormat smDay = new
			 * SimpleDateFormat("EEEE");
			 * 
			 * String currentDate = sm.format(new Date());
			 * 
			 * String currentWar = smDay.format(new Date()); System.out.println(currentWar);
			 * 
			 * String hour = currentDate.split(", ")[1].split(":")[0] + "." +
			 * currentDate.split(", ")[1].split(":")[1]; String currentDay =
			 * currentDate.split(", ")[0];
			 * 
			 * String xpath = propertyValueRDS; String actualPrice = null; String
			 * expectedPrice = null; String actualTime = null; String expectedTime = null;
			 * String actualDate = null;
			 * 
			 * if (Float.parseFloat(hour) >= 9.15 &&
			 * !currentWar.equalsIgnoreCase("Saturday") &&
			 * !currentWar.equalsIgnoreCase("Sunday") && Float.parseFloat(hour) < 15.30) {
			 * list = Framework.driver.findElements(By.xpath(xpath));
			 * System.out.println("List Is Size Is === " + list.size());
			 * 
			 * for (int i = 1; i <= list.size(); i++) { String schemeXpath =
			 * propertyValueRDS + "[" + i + "]/td[1]/a"; String schemeName =
			 * driver.findElement(By.xpath(schemeXpath)).getText();
			 * 
			 * if (schemeName.equalsIgnoreCase(dataFieldRDS)) {
			 * 
			 * // for price get String priceXpath = propertyValueRDS + "[" + i +
			 * "]/td[2]/p[1]"; String timeXpath = propertyValueRDS + "[" + i +
			 * "]/td[2]/p[2]";
			 * 
			 * WebElement priceElement = Framework.driver.findElement(By.xpath(priceXpath));
			 * WebElement timeElement = Framework.driver.findElement(By.xpath(timeXpath));
			 * 
			 * actualTime = timeElement.getText(); actualDate =
			 * actualTime.split(",")[0].trim(); actualTime =
			 * actualTime.split(",")[1].trim();
			 * 
			 * actualPrice = priceElement.getText(); actualPrice = actualPrice.replace("₹",
			 * "");
			 * 
			 * Thread.sleep(10000);
			 * 
			 * expectedTime = timeElement.getText(); expectedTime =
			 * expectedTime.split(",")[1].trim();
			 * 
			 * expectedPrice = priceElement.getText(); expectedPrice =
			 * expectedPrice.replace("₹", ""); break;
			 * 
			 * } Thread.sleep(1000); } String status = null; String message = "Page Name : "
			 * + Framework.pagename + "<br/>" + "NAV Rate : " + actualPrice + "<br/>" +
			 * "Refreshed NAV Rate : " + expectedPrice + "<br/>" + "Start Time : " +
			 * actualTime + "<br/>" + "End Time : " + expectedTime + "<br/>" + "Status :";
			 * if (!(currentDay.equalsIgnoreCase(actualDate))) { status =
			 * "FAIL (Date Not Refreshed)";
			 * 
			 * String msg = message + status; // mail send
			 * Total_Report.sendEmailAlert(Framework.host, Framework.port,
			 * Framework.mailFrom, Framework.password, Framework.mailTo, msg,
			 * Framework.mailCc);
			 * 
			 * }
			 * 
			 * if (actualTime.equalsIgnoreCase(expectedTime)) { status =
			 * "FAIL (Time Not Refreshed)";
			 * 
			 * String msg = message + status; // mail send
			 * Total_Report.sendEmailAlert(Framework.host, Framework.port,
			 * Framework.mailFrom, Framework.password, Framework.mailTo, msg,
			 * Framework.mailCc); }
			 * 
			 * if (actualPrice.equalsIgnoreCase(expectedPrice)) { status =
			 * "FAIL (NAV Rate Not Refreshed)";
			 * 
			 * String msg = message + status; // mail send
			 * Total_Report.sendEmailAlert(Framework.host, Framework.port,
			 * Framework.mailFrom, Framework.password, Framework.mailTo, msg,
			 * Framework.mailCc); } }
			 * 
			 * }
			 */
			/*
			 * 
			 * if (actionRDS.equalsIgnoreCase("Changetime")) {
			 * 
			 * if (map.get("bankHoliday").equalsIgnoreCase("N")) {
			 * 
			 * SimpleDateFormat sm = new SimpleDateFormat("dd MM yyyy, HH:mm:ss"); String
			 * currentDate = sm.format(new Date());
			 * 
			 * SimpleDateFormat formatterAfter = new
			 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date dateAfter = new Date(); String
			 * DateTime = formatterAfter.format(dateAfter);
			 * 
			 * SimpleDateFormat sm2 = new SimpleDateFormat("yyyy-MM-dd"); String curDate =
			 * sm2.format(new Date());
			 * 
			 * SimpleDateFormat smDay = new SimpleDateFormat("EEEE"); String currentWar =
			 * smDay.format(new Date()); System.out.println(currentWar);
			 * 
			 * String hour = currentDate.split(", ")[1].split(":")[0] + "." +
			 * currentDate.split(", ")[1].split(":")[1]; String currentDay =
			 * currentDate.split(", ")[0];
			 * 
			 * String status4 = null;
			 * 
			 * if (Float.parseFloat(hour) >= 9.00 &&
			 * !currentWar.equalsIgnoreCase("Saturday") &&
			 * !currentWar.equalsIgnoreCase("Sunday") && Float.parseFloat(hour) < 16.00) {
			 * 
			 * String actualTime = webElementVal.getText();
			 * 
			 * System.out.println("actualTime get ==== " + actualTime); // String
			 * date=actualTime.split(", ")[0]; // SimpleDateFormat datef = new
			 * SimpleDateFormat(date); // String navDate = datef.format(new Date());
			 * 
			 * actualTime = actualTime.split(", ")[1];
			 * 
			 * Thread.sleep(15000);
			 * 
			 * String expectedTime = webElementVal.getText(); expectedTime =
			 * expectedTime.split(", ")[1];
			 * 
			 * if (actualTime.equalsIgnoreCase(expectedTime)) { status4 = "FAIL";
			 * 
			 * } else { status4 = "PASS";
			 * 
			 * }
			 * 
			 * if (actualTime.equalsIgnoreCase(expectedTime)) {
			 * 
			 * Connection conn3 = null; Statement statement3 = null; try {
			 * Class.forName("com.mysql.jdbc.Driver").newInstance(); conn3 =
			 * DriverManager.getConnection(Framework.dburl, Framework.dbuser,
			 * Framework.dbpassword); final String qry3 =
			 * "INSERT INTO Hdfc_Time(Pagename,startTime,endTime,Status,trigger_flag,DateandTime) VALUES ('"
			 * + Framework.pagename + "','" + curDate + " " + actualTime + "','" + curDate +
			 * " " + expectedTime + "','" + status4 + "','" + "Y" + "','" + DateTime + "')";
			 * statement3 = conn3.createStatement(); final int rs3 =
			 * statement3.executeUpdate(qry3); System.out.println(rs3); } catch (Exception
			 * e9) { e9.printStackTrace(); } finally { conn3.close(); } } } } }
			 */

			if (actionRDS.equalsIgnoreCase("hdfc_check")) {
				List<WebElement> list;
				SimpleDateFormat sm = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
				SimpleDateFormat smDay = new SimpleDateFormat("EEEE");

				String currentDate = sm.format(new Date());

				String currentWar = smDay.format(new Date());
				System.out.println(currentWar);

				String hour = currentDate.split(", ")[1].split(":")[0] + "." + currentDate.split(", ")[1].split(":")[1];
				String currentDay = currentDate.split(", ")[0];

				String xpath = propertyValueRDS;
				String actualPrice = null;
				String expectedPrice = null;
				String actualTime = null;
				String expectedTime = null;
				String actualDate = null;

				if (Float.parseFloat(hour) >= 9.15 && !currentWar.equalsIgnoreCase("Saturday")
						&& !currentWar.equalsIgnoreCase("Sunday") && Float.parseFloat(hour) < 15.30) {
					list = Framework.driver.findElements(By.xpath(xpath));
					System.out.println("List Is Size Is === " + list.size());

					for (int i = 1; i <= list.size(); i++) {
						String schemeXpath = propertyValueRDS + "[" + i + "]/td[1]/a";
						String schemeName = driver.findElement(By.xpath(schemeXpath)).getText();

						if (schemeName.equalsIgnoreCase(dataFieldRDS)) {

							// for price get
							String priceXpath = propertyValueRDS + "[" + i + "]/td[2]/p[1]";
							String timeXpath = propertyValueRDS + "[" + i + "]/td[2]/p[2]";

							WebElement priceElement = Framework.driver.findElement(By.xpath(priceXpath));
							WebElement timeElement = Framework.driver.findElement(By.xpath(timeXpath));

							actualTime = timeElement.getText();
							actualDate = actualTime.split(",")[0].trim();
							actualTime = actualTime.split(",")[1].trim();

							actualPrice = priceElement.getText();
							actualPrice = actualPrice.replace("₹", "");

							Thread.sleep(10000);

							expectedTime = timeElement.getText();
							expectedTime = expectedTime.split(",")[1].trim();

							expectedPrice = priceElement.getText();
							expectedPrice = expectedPrice.replace("₹", "");
							break;

						}
						Thread.sleep(1000);
					}
					Monitoring_FrameWork.SaveResult(actualResult, expectedTime);

					String status = null;
					String message = "Page Name : " + Framework.pagename + "<br/>" + "NAV Rate : " + actualPrice
							+ "<br/>" + "Refreshed NAV Rate : " + expectedPrice + "<br/>" + "Start Time : " + actualTime
							+ "<br/>" + "End Time : " + expectedTime + "<br/>" + "Status :";
					if (!(currentDay.equalsIgnoreCase(actualDate))) {
						status = "FAIL (Date Not Refreshed)";

						String msg = message + status;
						// mail send
						Total_Report.sendEmailAlert(Framework.host, Framework.port, Framework.mailFrom,
								Framework.password, Framework.mailTo, msg, Framework.mailCc);

					}

					if (actualTime.equalsIgnoreCase(expectedTime)) {
						status = "FAIL (Time Not Refreshed)";

						String msg = message + status;
						// mail send
						Total_Report.sendEmailAlert(Framework.host, Framework.port, Framework.mailFrom,
								Framework.password, Framework.mailTo, msg, Framework.mailCc);
					}

					if (actualPrice.equalsIgnoreCase(expectedPrice)) {
						status = "FAIL (NAV Rate Not Refreshed)";

						String msg = message + status;
						// mail send
						Total_Report.sendEmailAlert(Framework.host, Framework.port, Framework.mailFrom,
								Framework.password, Framework.mailTo, msg, Framework.mailCc);
					}
				}

			}

			if (actionRDS.equalsIgnoreCase("clickJQuery")) {
				String jqueryLocator = propertyValueRDS;
				// "$(\".popup-close\")";
				JavascriptExecutor jsExecutor = (JavascriptExecutor) Framework.driver;
				jsExecutor.executeScript(jqueryLocator + ".click();");
			}

			if (actionRDS.equalsIgnoreCase("getValue")) {
				if (objectTypeRDS.equalsIgnoreCase("editbox")) {
					value = webElementVal.getAttribute("value");
				} else {
					value = webElementVal.getText();
				}
				System.out.println("Get Value Is == " + value);
			}
			if (actionRDS.equalsIgnoreCase("sendValue1")) {
				System.out.println("Send Value Is == " + value);

				if (objectTypeRDS.equalsIgnoreCase("js")) {
					((JavascriptExecutor) driver).executeScript("arguments[0].value='" + value + "';",
							new Object[] { webElementVal });
				} else {
					webElementVal.sendKeys(value);
				}
			}
			if (actionRDS.equalsIgnoreCase("getPurposalNumber")) {
				CustomFunctions.getPurposalNumber(propertyValueRDS, dataFieldRDS);

			}

			if (actionRDS.equalsIgnoreCase("FrameSwitch")) {
				if (objectTypeRDS.equalsIgnoreCase("FRAMEINDEX")) {
					int index = Integer.parseInt(propertyValueRDS);
					Framework.driver.switchTo().frame(index);
					System.out.println("switch to.........................." + propertyValueRDS);
				}
				if (objectTypeRDS.equalsIgnoreCase("FRAMENAME")) {
					Framework.driver.switchTo().frame(Framework.driver.findElement(By.name(propertyValueRDS)));
					System.out.println("switch to........................." + propertyValueRDS);
				}
				if (objectTypeRDS.equalsIgnoreCase("FRAMEXPATH")) {
					Framework.driver.switchTo().frame(Framework.driver.findElement(By.xpath(propertyValueRDS)));
					System.out.println("switch to........................." + propertyValueRDS);
				}
				if (objectTypeRDS.equalsIgnoreCase("FRAMEID")) {
					Framework.driver.switchTo().frame(Framework.driver.findElement(By.id(propertyValueRDS)));
					System.out.println("switch to........................" + propertyValueRDS);
				}
				if (objectTypeRDS.equalsIgnoreCase("parent")) {
					Framework.driver.switchTo().parentFrame();
					System.out.println("switch to......................Parent Frame");
				}
				if (objectTypeRDS.equalsIgnoreCase("default")) {
					Framework.driver.switchTo().defaultContent();
					System.out.println("switch to.....................Deafult Content");
				}
			}
			if (actionRDS.equalsIgnoreCase("switchOnWindow")) {
				ArrayList<String> tabs = new ArrayList<String>(Framework.driver.getWindowHandles());
				System.out.println("Total Window Is === " + tabs.size());
				Framework.driver.switchTo().window((String) tabs.get(Integer.parseInt(propertyValueRDS)));
				System.out.println("  == Window switched successfully == ");
			}
			if (actionRDS.equalsIgnoreCase("Js_click")) {
				final WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(10L));
				try {
					final WebElement web = (WebElement) wait3.until((Function) ExpectedConditions
							.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS))));
					((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();",
							new Object[] { web });
				} catch (StaleElementReferenceException e20) {
					System.out.println("Handdled 'StaleElementReferenceException'.");
				}
			}
			if (actionRDS.equalsIgnoreCase("verifyBalance")) {
				try {
					final List<WebElement> list2 = (List<WebElement>) Framework.driver
							.findElements(By.xpath(propertyValueRDS));
					final int size2 = list2.size();
					System.out.println("List Size is === " + size2);
					for (int l = 1; l <= size2; ++l) {
						final String xpath3 = "//*[@id='searchResList']/div[2]/div/div[" + l + "]/div[6]";
						final String value2 = Framework.driver.findElement(By.xpath(xpath3)).getText();
						System.out.println("Get Text Value Is == " + value2);
						Thread.sleep(1000L);
						if (value2.equalsIgnoreCase("INR 5000.00")) {
							final String xpath4 = "//*[@id=\"searchResList\"]/div[2]/div/div[" + l + "]/div[7]/i";
							Framework.driver.findElement(By.xpath(xpath4)).click();
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (actionRDS.equalsIgnoreCase("ActionClick")) {
				System.out.println("== IN Action Click == ");
				final Actions act2 = new Actions(Framework.driver);
				act2.click(webElementVal).build().perform();
				Thread.sleep(1000L);
			}
			if (actionRDS.equalsIgnoreCase("verify_click")) {
				final Actions act = new Actions(Framework.driver);
				try {
					final int s35 = Framework.driver.findElements(By.xpath(dataFieldRDS)).size();
					System.out.println("list stored==" + s35);
					for (int m = 1; m <= s35; ++m) {
						act.click(webElementVal).build().perform();
						Thread.sleep(1000L);
					}
				} catch (Exception e4) {
					e4.printStackTrace();
					System.out.println("=== in catch ===");
				}
			}

			// cj
			if (actionRDS.equalsIgnoreCase("idbiOffmarketTime")) {
				String fileName = "IDBI Off Market Business Time.txt";
				String filePath = System.getProperty("user.dir") + "/" + fileName;
				ArrayList<String> lines = new ArrayList<>();

				if (new File(filePath).exists()) {
					System.out.println("File is exist.... Reading the file");

					LocalDate currentDate1 = LocalDate.now();
					int currentDay = currentDate1.getDayOfMonth();
					LocalTime currentTime = LocalTime.now();
					int hours = currentTime.getHour();
					int minutes = currentTime.getMinute();
					SimpleDateFormat s = new SimpleDateFormat("EEEE");
					String war2 = s.format(new Date());
					double time = 0;
					int day = 0;
					String war1 = null;
					try {
						FileReader fileReader = new FileReader(filePath);
						BufferedReader bufferedReader = new BufferedReader(fileReader);

						String line;
						while ((line = bufferedReader.readLine()) != null) {
							// Read each line from the file and print it
							lines.add(line);
						}
						day = Integer.parseInt(lines.get(0));
						war1 = lines.get(1);
						time = Double.parseDouble(lines.get(2));
						System.out.println("Data read from file === " + day + " " + war1 + " " + time);

						// Close the BufferedReader to release resources
						bufferedReader.close();

						System.out.println("Reading file.....");
					} catch (IOException e) {
						System.err.println("Error while reading the file: " + e.getMessage());
					}
					// new time
					String content1 = currentDay + "\n" + war2 + "\n" + hours + "." + minutes;

					try {
						FileWriter fileWriter = new FileWriter(new File(filePath));
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

						bufferedWriter.write(content1);
						bufferedWriter.close();

						System.out.println("File '" + fileName + "' has been written successfully.");
					} catch (IOException e) {
						System.err.println("Error while writing to the file: " + e.getMessage());
					}

					if ((time >= 15.30 && day != currentDay && !(war1.equalsIgnoreCase("Saturday"))
							&& !(war1.equalsIgnoreCase("Sunday")))

							&&

							(time <= 16.00 && day != currentDay && !(war1.equalsIgnoreCase("Saturday"))
									&& !(war1.equalsIgnoreCase("Sunday")))) {
						// ext

					} else {
						while (true) {
							if ((Framework.pagename.equals(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}
					}

				} else {
					System.out.println("File is not exist....");

					// Get the day of the month from the current date
					LocalDate currentDate = LocalDate.now();
					LocalTime ct = LocalTime.now();
					int dayOfMonth = currentDate.getDayOfMonth();
					SimpleDateFormat s = new SimpleDateFormat("EEEE");
					String war = s.format(new Date());
					int h = ct.getHour();
					int m = ct.getMinute();
					double time = Double.parseDouble(h + "." + m);
					String content = dayOfMonth + "\n" + war + "\n" + h + "." + m;

					// exution

					if ((time >= 15.30 && !(war.equalsIgnoreCase("Saturday")) && !(war.equalsIgnoreCase("Sunday")))

							&&

							(time <= 16.00 && !(war.equalsIgnoreCase("Saturday"))
									&& !(war.equalsIgnoreCase("Sunday")))) {
						// ext
						try {
							FileWriter fileWriter = new FileWriter(new File(filePath));
							BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

							bufferedWriter.write(content);
							bufferedWriter.close();

							System.out.println("File '" + fileName + "' has been created and written successfully.");
						} catch (IOException e) {
							System.err.println("Error while writing to the file: " + e.getMessage());
						}

					} else {
						while (true) {
							if ((Framework.pagename.equals(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}
					}

				}
			}
			// cj
			if (actionRDS.equalsIgnoreCase("idbiOnmarketTime")) {

				String fileName = "IDBI On Markrt Business Time.txt";
				String filePath = System.getProperty("user.dir") + "/" + fileName;
				ArrayList<String> lines = new ArrayList<>();

				if (new File(filePath).exists()) {
					System.out.println("File is exist.... Reading the file");
					LocalDate currentDate1 = LocalDate.now();
					int currentDay = currentDate1.getDayOfMonth();
					LocalTime currentTime = LocalTime.now();
					int hours = currentTime.getHour();
					int minutes = currentTime.getMinute();
					SimpleDateFormat s = new SimpleDateFormat("EEEE");
					String war2 = s.format(new Date());
					double time = 0;
					int day = 0;
					String war1 = null;
					try {
						FileReader fileReader = new FileReader(filePath);
						BufferedReader bufferedReader = new BufferedReader(fileReader);

						String line;
						while ((line = bufferedReader.readLine()) != null) {
							// Read each line from the file and print it
							lines.add(line);
						}
						day = Integer.parseInt(lines.get(0));
						war1 = lines.get(1);
						time = Double.parseDouble(lines.get(2));
						System.out.println(day + " " + war1 + " " + time);

						// Close the BufferedReader to release resources
						bufferedReader.close();

						System.out.println("Reading file.....");
					} catch (IOException e) {
						System.err.println("Error while reading the file: " + e.getMessage());
					}

					// new time
					String content1 = currentDay + "\n" + war2 + "\n" + hours + "." + minutes;

					try {
						FileWriter fileWriter = new FileWriter(new File(filePath));
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

						bufferedWriter.write(content1);
						bufferedWriter.close();

						System.out.println("File '" + fileName + "' has been written successfully.");
					} catch (IOException e) {
						System.err.println("Error while writing to the file: " + e.getMessage());
					}

					if ((time >= 9.15 && day != currentDay && !(war1.equalsIgnoreCase("Saturday"))
							&& !(war1.equalsIgnoreCase("Sunday")))

							&&

							(time <= 15.30 && day != currentDay && !(war1.equalsIgnoreCase("Saturday"))
									&& !(war1.equalsIgnoreCase("Sunday")))) {
						// ext

					} else {
						while (true) {
							if ((Framework.pagename.equals(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}
					}

				} else {
					System.out.println("File is not exist....");
					// Get the day of the month from the current date
					LocalDate currentDate = LocalDate.now();
					LocalTime ct = LocalTime.now();
					int dayOfMonth = currentDate.getDayOfMonth();
					SimpleDateFormat s = new SimpleDateFormat("EEEE");
					String war = s.format(new Date());
					int h = ct.getHour();
					int m = ct.getMinute();
					double time = Double.parseDouble(h + "." + m);
					String content = dayOfMonth + "\n" + war + "\n" + h + "." + m;

					if ((time >= 9.15 && !(war.equalsIgnoreCase("Saturday")) && !(war.equalsIgnoreCase("Sunday")))

							&&

							(time <= 15.30 && !(war.equalsIgnoreCase("Saturday"))
									&& !(war.equalsIgnoreCase("Sunday")))) {
						// ext
						try {
							FileWriter fileWriter = new FileWriter(new File(filePath));
							BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

							bufferedWriter.write(content);
							bufferedWriter.close();

							System.out.println("File '" + fileName + "' has been created and written successfully.");
						} catch (IOException e) {
							System.err.println("Error while writing to the file: " + e.getMessage());
						}

					} else {
						while (true) {
							if ((Framework.pagename.equals(propertyValueRDS))) {
								Framework.recordsetRDS.moveNext();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
							} else {
								Framework.recordsetRDS.movePrevious();
								Framework.pagename = Framework.recordsetRDS.getField("PageName");
								break;
							}
						}
					}

				}
			}

			if (actionRDS.equalsIgnoreCase("verify_page")) {
				final WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				try {
					final WebElement wb4 = (WebElement) wait3.until(
							(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
					if (wb4.isDisplayed()) {
						System.out.println(" == ELEMENT IS VISIBLE == ");
					}
				} catch (Exception e4) {
					Framework.driver.close();
					Thread.sleep(2000L);
					Framework.driver.switchTo().window(Framework.parenwindow);
					Framework.parenwindow = Framework.driver.getWindowHandle();
				}
			}
			if (actionRDS.equalsIgnoreCase("verifyAndClick")) {
				WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				try {
					((WebElement) wait3.until(
							(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS))))
							.click();
				} catch (Exception ex23) {
				}
			}
			// Amar,Bhaba
			else if (actionRDS.equalsIgnoreCase("clickLicPolicyNo")) {
				NewCustomFunctions.clickLicPolicyNo(objectTypeRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("CheckLibertyPreviousPaymentMode")) {
				NewCustomFunctions.CheckLibertyPreviousPaymentMode();
			} else if (actionRDS.equalsIgnoreCase("writeLibertyPaymentMode")) {
				NewCustomFunctions.writeLibertyPaymentMode(dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("verifyBandhanMFSkipPages")) {
				NewCustomFunctions.verifyBandhanMFSkipPages();
			} else if (actionRDS.equalsIgnoreCase("openSafari")) {
				Runtime.getRuntime().exec("open -a Safari");
				System.out.println("Safari browser started successfully");
			} else if (actionRDS.equalsIgnoreCase("ClickGCIRISTodaysDate")) {
				System.out.println("In ClickGCIRISTodaysDate");
				String path = "(//span[text()='" + LocalDate.now().getDayOfMonth() + "'])[last()]";
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
			} else if (actionRDS.equalsIgnoreCase("sendUtkarshAdharNo")) {
				System.out.println("In sendUtkarshAdharNo");
				NewCustomFunctions.sendUtkarshAdharNo(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("Rb_NavigateBack")) {
				System.out.println("In Rb_NavigateBack");
				NewCustomFunctions.rb_NavigateBack();
			} else if (actionRDS.equalsIgnoreCase("skipFileUploadGC")) {
				System.out.println("In skipFileUploadGC");
				String str = "skip";
				NewCustomFunctions.skipFileUploadGC(propertyValueRDS, str);
			} else if (actionRDS.equalsIgnoreCase("AxisCINBSETLastTab")) {
				System.out.println("In AxisCINBLastTab");
				NewCustomFunctions.axisCINBLastTab(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("verifyMACMAppPage")) {
				System.out.println("In verifyMACMAppPage");
				NewCustomFunctions.verifyMACMAppPage();
			} else if (actionRDS.equalsIgnoreCase("verifyBandhanPartnerSkipPages")) {
				System.out.println("In verifyBandhanPartnerSkipPages");
				NewCustomFunctions.verifyBandhanPartnerSkipPages();
			} else if (actionRDS.equalsIgnoreCase("verifySharekhanSkipPages")) {
				System.out.println("In verifySharekhanSkipPages");
				NewCustomFunctions.verifySharekhanSkipPages();
			} else if (actionRDS.equalsIgnoreCase("VerifyskipCurrentPage")) {
				System.out.println("In VerifyskipCurrentPage");
				NewCustomFunctions.VerifyskipCurrentPage(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("verifyBajajTellUsPage")) {
				System.out.println("In verifyBajajTellUsPage");
				NewCustomFunctions.verifyBajajTellUsPage(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("verifyYesBankValues")) {
				System.out.println("In verifyYesBankValues");
				NewCustomFunctions.verifyYesBankValues();
			} else if (actionRDS.equalsIgnoreCase("YesBankBulkFileUpload")) {
				System.out.println("In YesBankBulkFileUpload");
				NewCustomFunctions.YesBankBulkFileUpload(propertyValueRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("VerifyYesbankPaymentmode")) {
				System.out.println("In VerifyYesbankPaymentmode");
				NewCustomFunctions.VerifyYesbankPaymentmode(propertyValueRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("verifyEsafUrl")) {
				System.out.println("In verifyEsafUrl");
				NewCustomFunctions.verifyEsafUrl(propertyValueRDS, dataFieldRDS, controlRDS, objectTypeRDS);
			} else if (actionRDS.equalsIgnoreCase("YesBankMultipleVisibility")) {
				System.out.println("In YesBankMultipleVisibility");
				NewCustomFunctions.yesBankMultipleVisibility(dataFieldRDS, objectTypeRDS, controlRDS);
			} else if (actionRDS.equalsIgnoreCase("verifySbiDownloadConfirmationPage")) {
				System.out.println("IN verifySbiDownloadConfirmationPage");
				NewCustomFunctions.verifySbiDownloadConfirmationPage(webElementVal);
			} else if (actionRDS.equalsIgnoreCase("VerifyUtkarshRefNo")) {
				System.out.println("IN VerifyUtkarshRefNo");
				NewCustomFunctions.verifyUtkarshRefNo(dataFieldRDS);
			} else if (actionRDS.toUpperCase().contains("VERIFYREADYESBANKOTP(")) {
				if (controlRDS.equalsIgnoreCase("V")) {
					System.out.println("IN VERIFYREADYESBANKOTP");
					String times = actionRDS.split("\\(")[1].split("\\)")[0];
					NewCustomFunctions.verifyReadYesBankOtp(webElementVal, objectTypeRDS, dataFieldRDS, times,
							propertyValueRDS);
				}
			} else if (actionRDS.equalsIgnoreCase("SkipPagesEdelweissMFInvestor")) {
				LocalDateTime localDateTime = LocalDateTime.now();
				int hour = localDateTime.getHour();
				int min = localDateTime.getMinute();
				if (hour == 8 && min <= 15) {
					System.out.println("==========> Run All Pages <==========");
				} else {
					System.out.println("Skipping Pages");
					while (Framework.pagename != (propertyValueRDS.trim())) {
						Framework.recordsetRDS.moveNext();
						Framework.pagename = Framework.recordsetRDS.getField("PageName");
					}
					Framework.recordsetRDS.movePrevious();
					System.out.println("Move To :- " + Framework.pagename + " Page");
				}

			} else if (actionRDS.equalsIgnoreCase("RobotClickFileUpload")) {
				System.out.println("In RobotClickFileUpload");
				NewCustomFunctions.robotClickFileUpload(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("publicFetchCaptcha")) {
				NewCustomFunctions.publicFetchCaptcha(objectTypeRDS, webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("SetFutureDate")) {
				NewCustomFunctions.setFutureDate(webElementVal, dataFieldRDS, objectTypeRDS);
			}
			// -----------------------------------------------------------------------
			else if (actionRDS.equalsIgnoreCase("sendNickname")) {
				NewCustomFunctions.sendNickname(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("scrollElementToLeft")) {
				System.out.println("In scrollElementToLeft");
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].scrollLeft=arguments[1];",
						webElementVal, dataFieldRDS.trim());
			} else if (actionRDS.equalsIgnoreCase("File_Upload2")) {
				System.out.println("In File_Upload2");
				new WebDriverWait(Framework.driver, Duration.ofSeconds(20))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)))
						.sendKeys(new CharSequence[] { dataFieldRDS });
			} else if (actionRDS.equalsIgnoreCase("checkPaymentMode")) {
				System.out.println("In checkPaymentMode");
				NewCustomFunctions.checkPaymentMode(propertyValueRDS, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("AxisClickLastCheckBox")) {
				NewCustomFunctions.AxisClickLastCheckBox(dataFieldRDS);

			} else if (actionRDS.equalsIgnoreCase("openTor")) {
				Runtime.getRuntime().exec(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("VerifyCaptchaError")) {
				System.out.println("In VerifyCaptchaError");
				NewCustomFunctions.verifyCaptchaError(dataFieldRDS, ObjectTypeRDS);
			} else if (actionRDS.equalsIgnoreCase("sendRandomNo")) {
				NewCustomFunctions.sendRandomNo(webElementVal, dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("readOtpMail")) {
				String mailId = ObjectTypeRDS.split("#")[0];
				System.out.println("MailId : " + mailId);
				String passkeys = ObjectTypeRDS.split("#")[1];
				String mailSubject = ObjectTypeRDS.split("#")[2];
				String fromMail = dataFieldRDS.trim();

				String otp = CustomFunctions.readMailOtp(mailId, passkeys, mailSubject, fromMail);
				if (!otp.isEmpty()) {
					System.out.println("Extracted Otp : " + otp);
					webElementVal.sendKeys(otp);
				} else {
					System.out.println("Otp is Empty !!!");
					String otpS = JOptionPane.showInputDialog(null, "Enter Otp");
					webElementVal.sendKeys(otpS);
				}

			} else if (actionRDS.equalsIgnoreCase("BajajOtpMail")) {
				System.out.println("BajajOtpMail Call");
				String mailId = Framework.pro.getProperty("mailIdOtp");
				String passkeys = Framework.pro.getProperty("passOtp");
				System.out.println("MailId : " + mailId);
				String mailSubject = ObjectTypeRDS.trim();
				String fromMail = propertyValueRDS.trim();

				String bajajOtp = CustomFunctions.readMailOtp(mailId, passkeys, mailSubject, fromMail);
				if (!bajajOtp.isEmpty()) {
					System.out.println("Extracted Otp : " + bajajOtp);
					Functions.otp1 = bajajOtp.charAt(0);
					Functions.otp2 = bajajOtp.charAt(1);
					Functions.otp3 = bajajOtp.charAt(2);
					Functions.otp4 = bajajOtp.charAt(3);

				} else {
					System.out.println("Otp is Empty !!!");
				}

			} else if (actionRDS.equalsIgnoreCase("readHDFCMailotp")) {
				NewCustomFunctions.readHDFCMailotp(webElementVal, dataFieldRDS);
			}

			else if (actionRDS.equalsIgnoreCase("verifyLibertyElement")) {
				CustomFunctions.verifyLibertyElement(objectTypeRDS, dataFieldRDS, propertyValueRDS);
			}

			if (actionRDS.equalsIgnoreCase("adjustScreen")) {
				System.out.println("== In adjust screen== ");
				final JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("document.body.style.zoom='" + propertyValueRDS + "%'", new Object[0]);
			}
			if (actionRDS.equalsIgnoreCase("verifyDay")) {
				final DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
				final String currentDay = String.valueOf(dayOfWeek);
				System.out.println("Current Day Is == " + currentDay);
			}
			if (actionRDS.equalsIgnoreCase("lastDatePicker")) {
				final WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(10L));
				final ArrayList<Integer> numbers = new ArrayList<Integer>();
				final List<WebElement> list3 = (List<WebElement>) wait3.until(
						(Function) ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(propertyValueRDS)));
				System.out.println("List Size Is == " + list3.size());
				for (int i2 = 0; i2 < list3.size(); ++i2) {
					final String date2 = list3.get(i2).getText();
					numbers.add(Integer.parseInt(date2));
				}
				int highestNumber = numbers.get(0);
				for (int i = 1; i < numbers.size(); ++i) {
					final int currentNumber = numbers.get(i);
					if (currentNumber > highestNumber) {
						highestNumber = currentNumber;
					}
				}
				System.out.println("Higest Date is == " + highestNumber);
				((WebElement) wait3.until((Function) ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//a[@data-date='" + highestNumber + "']")))).click();
			}
			if (actionRDS.equalsIgnoreCase("RB_gettext")) {
				Robot rb5 = null;
				try {
					rb5 = new Robot();
				} catch (AWTException e5) {
					e5.printStackTrace();
				}
				final StringSelection s36 = new StringSelection(propertyValueRDS.trim());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s36, null);
				rb5.keyPress(17);
				rb5.keyPress(16);
				rb5.keyPress(74);
				rb5.keyRelease(74);
				rb5.keyRelease(16);
				rb5.keyRelease(17);
				Thread.sleep(2000L);
				rb5.keyPress(17);
				rb5.keyPress(76);
				Thread.sleep(200L);
				rb5.keyRelease(76);
				rb5.keyRelease(17);
				Thread.sleep(1000L);
				rb5.keyPress(17);
				rb5.keyPress(86);
				Thread.sleep(200L);
				rb5.keyRelease(86);
				rb5.keyRelease(17);
				Thread.sleep(2000L);
				rb5.keyPress(10);
				Thread.sleep(100L);
				rb5.keyRelease(10);
				Thread.sleep(3000L);
				rb5.keyPress(17);
				rb5.keyPress(16);
				rb5.keyPress(74);
				rb5.keyRelease(74);
				rb5.keyRelease(16);
				rb5.keyRelease(17);
				Thread.sleep(3000L);
				String elementText = null;
				try {
					elementText = (String) ((JavascriptExecutor) Framework.driver)
							.executeScript("return " + objectTypeRDS, new Object[0]);
					System.out.println("Gettext value ============== " + elementText);
				} catch (Exception e3) {
					e3.printStackTrace();
				}
				if (controlRDS.equalsIgnoreCase("V")) {
					Monitoring_FrameWork.SaveResult(elementText.trim(), dataFieldRDS.trim());
				}
			}
			if (actionRDS.equalsIgnoreCase("js_gettext")) {
				final JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				if (objectTypeRDS.equalsIgnoreCase("innertext")) {
					final String elementText2 = (String) js.executeScript("return arguments[0].innerText;",
							new Object[] { webElementVal });
					System.out.println("Gettext value ============== " + elementText2);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(elementText2.trim(), dataFieldRDS.trim());
					}
				}
				if (objectTypeRDS.equalsIgnoreCase("classname")) {
					final String elementText2 = (String) js.executeScript(
							"return document.getElementByClassName('" + propertyValueRDS + "').value;", new Object[0]);
					System.out.println("Gettext value ============== " + elementText2);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(elementText2.trim(), dataFieldRDS.trim());
					}
				}
				if (objectTypeRDS.equalsIgnoreCase("name")) {
					final String elementText2 = (String) js.executeScript(
							"return document.getElementByName('" + propertyValueRDS + "').value;", new Object[0]);
					System.out.println("Gettext value ============== " + elementText2);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(elementText2.trim(), dataFieldRDS.trim());
					}
				}
				if (objectTypeRDS.equalsIgnoreCase("id")) {
					final String elementText2 = (String) js.executeScript(
							"return document.getElementById('" + propertyValueRDS + "').value;", new Object[0]);
					System.out.println("Gettext value ============== " + elementText2);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(elementText2.trim(), dataFieldRDS.trim());
					}
				}
				if (objectTypeRDS.equalsIgnoreCase("tagname")) {
					final String elementText2 = (String) js.executeScript(
							"return document.getElementByTagName('" + propertyValueRDS + "').value;", new Object[0]);
					System.out.println("Gettext value ============== " + elementText2);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(elementText2.trim(), dataFieldRDS.trim());
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("RobotSendkeys")) {

				NewCustomFunctions.robotSendkeys(webElementVal, dataFieldRDS);

				// webElementVal.click();
				// Thread.sleep(1000);
				// final Robot rb2 = new Robot();
				// System.out.println(dataFieldRDS);
				// final char[] ch2 = new char[dataFieldRDS.length()];
				// for (int l2 = 0; l2 < dataFieldRDS.length(); ++l2) {
				// ch2[l2] = dataFieldRDS.charAt(l2);
				// }
				// for (int m2 = 0; m2 < ch2.length; ++m2) {
				// if (ch2[m2] == '.') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(46);
				// rb2.keyRelease(46);
				// } else if (ch2[m2] == '@') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(50);
				// rb2.keyRelease(16);
				// rb2.keyRelease(50);
				// } else if (ch2[m2] == 'A') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(65);
				// rb2.keyRelease(16);
				// rb2.keyRelease(65);
				// } else if (ch2[m2] == 'B') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(66);
				// rb2.keyRelease(16);
				// rb2.keyRelease(66);
				// } else if (ch2[m2] == 'C') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(67);
				// rb2.keyRelease(16);
				// rb2.keyRelease(67);
				// } else if (ch2[m2] == 'D') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(68);
				// rb2.keyRelease(16);
				// rb2.keyRelease(68);
				// } else if (ch2[m2] == 'E') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(69);
				// rb2.keyRelease(16);
				// rb2.keyRelease(69);
				// } else if (ch2[m2] == 'F') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(70);
				// rb2.keyRelease(16);
				// rb2.keyRelease(70);
				// } else if (ch2[m2] == 'G') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(71);
				// rb2.keyRelease(16);
				// rb2.keyRelease(71);
				// } else if (ch2[m2] == 'H') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(72);
				// rb2.keyRelease(16);
				// rb2.keyRelease(72);
				// } else if (ch2[m2] == 'I') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(73);
				// rb2.keyRelease(16);
				// rb2.keyRelease(73);
				// } else if (ch2[m2] == 'J') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(74);
				// rb2.keyRelease(16);
				// rb2.keyRelease(74);
				// } else if (ch2[m2] == 'K') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(75);
				// rb2.keyRelease(16);
				// rb2.keyRelease(75);
				// } else if (ch2[m2] == 'L') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(76);
				// rb2.keyRelease(16);
				// rb2.keyRelease(76);
				// } else if (ch2[m2] == 'M') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(77);
				// rb2.keyRelease(16);
				// rb2.keyRelease(77);
				// } else if (ch2[m2] == 'N') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(78);
				// rb2.keyRelease(16);
				// rb2.keyRelease(78);
				// } else if (ch2[m2] == 'O') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(79);
				// rb2.keyRelease(16);
				// rb2.keyRelease(79);
				// } else if (ch2[m2] == 'P') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(80);
				// rb2.keyRelease(16);
				// rb2.keyRelease(80);
				// } else if (ch2[m2] == 'Q') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(81);
				// rb2.keyRelease(16);
				// rb2.keyRelease(81);
				// } else if (ch2[m2] == 'R') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(82);
				// rb2.keyRelease(16);
				// rb2.keyRelease(82);
				// } else if (ch2[m2] == 'S') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(83);
				// rb2.keyRelease(16);
				// rb2.keyRelease(83);
				// } else if (ch2[m2] == 'T') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(84);
				// rb2.keyRelease(16);
				// rb2.keyRelease(84);
				// } else if (ch2[m2] == 'U') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(85);
				// rb2.keyRelease(16);
				// rb2.keyRelease(85);
				// } else if (ch2[m2] == 'V') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(86);
				// rb2.keyRelease(16);
				// rb2.keyRelease(86);
				// } else if (ch2[m2] == 'W') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(87);
				// rb2.keyRelease(16);
				// rb2.keyRelease(87);
				// } else if (ch2[m2] == 'X') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(88);
				// rb2.keyRelease(16);
				// rb2.keyRelease(88);
				// } else if (ch2[m2] == 'Y') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(89);
				// rb2.keyRelease(16);
				// rb2.keyRelease(89);
				// } else if (ch2[m2] == 'Z') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(90);
				// rb2.keyRelease(16);
				// rb2.keyRelease(90);
				// } else if (ch2[m2] == 'a') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(65);
				// rb2.keyRelease(65);
				// } else if (ch2[m2] == 'b') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(66);
				// rb2.keyRelease(66);
				// } else if (ch2[m2] == 'c') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(67);
				// rb2.keyRelease(67);
				// } else if (ch2[m2] == 'd') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(68);
				// rb2.keyRelease(68);
				// } else if (ch2[m2] == 'e') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(69);
				// rb2.keyRelease(69);
				// } else if (ch2[m2] == 'f') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(70);
				// rb2.keyRelease(70);
				// } else if (ch2[m2] == 'g') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(71);
				// rb2.keyRelease(71);
				// } else if (ch2[m2] == 'h') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(72);
				// rb2.keyRelease(72);
				// } else if (ch2[m2] == 'i') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(73);
				// rb2.keyRelease(73);
				// } else if (ch2[m2] == 'j') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(74);
				// rb2.keyRelease(74);
				// } else if (ch2[m2] == 'k') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(75);
				// rb2.keyRelease(75);
				// } else if (ch2[m2] == 'l') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(76);
				// rb2.keyRelease(76);
				// } else if (ch2[m2] == 'm') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(77);
				// rb2.keyRelease(77);
				// } else if (ch2[m2] == 'n') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(78);
				// rb2.keyRelease(78);
				// } else if (ch2[m2] == 'o') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(79);
				// rb2.keyRelease(79);
				// } else if (ch2[m2] == 'p') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(80);
				// rb2.keyRelease(80);
				// } else if (ch2[m2] == 'q') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(81);
				// rb2.keyRelease(81);
				// } else if (ch2[m2] == 'r') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(82);
				// rb2.keyRelease(82);
				// } else if (ch2[m2] == 's') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(83);
				// rb2.keyRelease(83);
				// } else if (ch2[m2] == 't') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(84);
				// rb2.keyRelease(84);
				// } else if (ch2[m2] == 'u') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(85);
				// rb2.keyRelease(85);
				// } else if (ch2[m2] == 'v') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(86);
				// rb2.keyRelease(86);
				// } else if (ch2[m2] == 'w') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(87);
				// rb2.keyRelease(87);
				// } else if (ch2[m2] == 'x') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(88);
				// rb2.keyRelease(88);
				// } else if (ch2[m2] == 'y') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(89);
				// rb2.keyRelease(89);
				// } else if (ch2[m2] == 'z') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(90);
				// rb2.keyRelease(90);
				// } else if (ch2[m2] == '1') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(49);
				// rb2.keyRelease(49);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '2') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(50);
				// rb2.keyRelease(50);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '3') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(51);
				// rb2.keyRelease(51);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '4') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(52);
				// rb2.keyRelease(52);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '5') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(53);
				// rb2.keyRelease(53);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '6') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(54);
				// rb2.keyRelease(54);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '7') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(55);
				// rb2.keyRelease(55);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '8') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(56);
				// rb2.keyRelease(56);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '9') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(57);
				// rb2.keyRelease(57);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '0') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(48);
				// rb2.keyRelease(48);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '/') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(47);
				// rb2.keyRelease(47);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == '.') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(46);
				// rb2.keyRelease(46);
				// Thread.sleep(100L);
				// } else if (ch2[m2] == ':') {
				// System.out.println(ch2[m2]);
				// rb2.keyPress(16);
				// rb2.keyPress(59);
				// Thread.sleep(100L);
				// rb2.keyRelease(59);
				// rb2.keyRelease(16);
				// Thread.sleep(100L);
				// }
				// }
			}
			if (actionRDS.equalsIgnoreCase("SendValue")) {
				final String[] array = { dataFieldRDS };
				final StringBuilder sb = new StringBuilder();
				for (int m = 0; m < array.length; ++m) {
					sb.append(array[m]);
				}
				final String arrayAsString = sb.toString();
				System.out.println("Value Is == " + arrayAsString);
				final JavascriptExecutor jse5 = (JavascriptExecutor) Framework.driver;
				jse5.executeScript("arguments[0].value='" + arrayAsString + "';", new Object[] { webElementVal });
			}
			if (actionRDS.equalsIgnoreCase("SendInnerHtml")) {
				final String script = "arguments[0].innerHTML='" + dataFieldRDS + "'";
				((JavascriptExecutor) Framework.driver).executeScript(script, new Object[] { webElementVal });
			} else if (actionRDS.equalsIgnoreCase("ClickOnCoordinate")) {
				final ArrayList<String> list4 = new ArrayList<String>();
				final StringTokenizer st = new StringTokenizer(propertyValueRDS, " ");
				final int cout = st.countTokens();
				System.out.println("count::" + cout);
				while (st.hasMoreTokens()) {
					final String token = st.nextToken();
					list4.add(token);
				}
				final int x3 = Integer.parseInt(list4.get(0));
				final int y4 = Integer.parseInt(list4.get(1));
				System.out.println(list4);
				System.out.println("x::" + x3 + "y::" + y4);
				final Actions actions = new Actions(Framework.driver);
				actions.moveByOffset(x3, y4).click().build().perform();
			}
			if (actionRDS.equalsIgnoreCase("ValidatefromDropdown")) {
				final ArrayList<String> listvalueArrayList = new ArrayList<String>();
				final WebDriverWait wait = new WebDriverWait(Framework.driver,
						Duration.ofSeconds(Framework.defaultwaittime));
				final String[] valueStrings = dataFieldRDS.split("#");
				final String xpath5 = valueStrings[0];
				final String xpath6 = valueStrings[1];
				final String xpath7 = valueStrings[2];
				final String option1 = valueStrings[3];
				final String option2 = valueStrings[4];
				final String option3 = valueStrings[5];
				final String option4 = valueStrings[6];
				final String text1 = webElementVal.getText();
				System.out.println("1st value is ===" + text1);
				final String text2 = ((WebElement) wait
						.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath5)))).getText();
				System.out.println("2nd value is ===" + text2);
				final String text3 = ((WebElement) wait
						.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath6)))).getText();
				System.out.println("3rd value is ===" + text3);
				final String text4 = ((WebElement) wait
						.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath7)))).getText();
				System.out.println("4th value is ===" + text4);
				listvalueArrayList.add(text1);
				listvalueArrayList.add(text2);
				listvalueArrayList.add(text3);
				listvalueArrayList.add(text4);
				if (listvalueArrayList.contains(option1) && listvalueArrayList.contains(option2)
						&& listvalueArrayList.contains(option3) && listvalueArrayList.contains(option4)) {
					System.out.println("All data found");
				} else {
					final JFrame frame5 = new JFrame();
					JOptionPane.showMessageDialog(frame5, "All Data mentioned not found");
				}
			}
			if (actionRDS.equalsIgnoreCase("MDSELECTPRICE")) {
				String price = "";
				try {
					if (objectTypeRDS.equalsIgnoreCase("EditBox")) {
						price = webElementVal.getAttribute("Value");
					} else if (objectTypeRDS.equalsIgnoreCase("Mirae")) {
						price = webElementVal.getText().split(" ")[0];
					} else {
						price = webElementVal.getText();
					}
					System.out.println("price=" + price);
					final float f = Float.parseFloat(price);
					float m3 = Float.parseFloat(dataFieldRDS);
					System.out.println("before increase=============== " + m3);
					m3 /= 100.0f;
					final float value3 = f - f * m3;
					System.out.println("after inrcrese=============== " + value3);
					final String decimal = Float.toString(value3);
					final String val = Float.toString(value3).split("\\.")[1];

					String res;
					if (val.length() < 2) {
						res = decimal.split("\\.")[0] + "." + val.substring(0, 1) + "0";
						System.out.println("after round off=============== " + res);
					} else {
						final int ds = Integer.parseInt(val.substring(1, 2));

						if (ds < 5) {
							res = decimal.split("\\.")[0] + "." + val.substring(0, 1) + "0";
							System.out.println("after round off=============== " + res);
						} else {
							res = decimal.split("\\.")[0] + "." + val.substring(0, 1) + "5";
							System.out.println("after round off=============== " + res);
						}
					}
					Functions.numberAsString = res;
					System.out.println("After converting =" + Functions.numberAsString);
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
			if (actionRDS.equalsIgnoreCase("MISELECTPRICE")) {
				String price = "";
				try {
					if (objectTypeRDS.equalsIgnoreCase("EditBox")) {
						price = webElementVal.getAttribute("Value");
					} else if (objectTypeRDS.equalsIgnoreCase("Mirae")) {
						price = webElementVal.getText().split(" ")[0];
					} else {
						price = webElementVal.getText();
					}
					System.out.println("price=" + price);
					final float f = Float.parseFloat(price);
					float m3 = Float.parseFloat(dataFieldRDS);
					System.out.println("=============== " + m3);
					m3 /= 100.0f;
					final float value3 = f + f * m3;
					System.out.println("after inrcrese=============== " + value3);
					final String decimal = Float.toString(value3);
					final String val = Float.toString(value3).split("\\.")[1];
					final int ds = Integer.parseInt(val.substring(1, 2));
					String res;
					if (ds < 5) {
						res = decimal.split("\\.")[0] + "." + val.substring(0, 1) + "0";
						System.out.println("after round off=============== " + res);
					} else {
						res = decimal.split("\\.")[0] + "." + val.substring(0, 1) + "5";
						System.out.println("after round off=============== " + res);
					}
					Functions.numberAsString = res;
					System.out.println("After converting =" + Functions.numberAsString);
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
			if (actionRDS.equalsIgnoreCase("MSendprice")) {
				webElementVal.clear();
				System.out.println("Send value=>>  " + Functions.numberAsString);
				webElementVal.click();
				webElementVal.sendKeys(new CharSequence[] { Functions.numberAsString });
			}
			if (actionRDS.equalsIgnoreCase("RefreshRateCompare")) {
				try {
					final Date dte = new Date();
					final Calendar calendar = Calendar.getInstance();
					calendar.setTime(dte);
					final int currentTime = calendar.get(11);
					final SimpleDateFormat f2 = new SimpleDateFormat("EEEE");
					final String str = f2.format(new Date());
					if (str.equalsIgnoreCase("MONDAY") || str.equalsIgnoreCase("TUESDAY")
							|| str.equalsIgnoreCase("WEDNESDAY") || str.equalsIgnoreCase("THURSDAY")
							|| str.equalsIgnoreCase("FRIDAY")) {
						System.out.println("In this method RefreshRateCompare");
						if (currentTime >= 9 && currentTime < 16) {
							final Date now = new Date();
							final int year = Calendar.getInstance().get(1);
							final String MonthName = new SimpleDateFormat("MMMM").format(now);
							final int monthday = Calendar.getInstance().get(5);
							final String folderDir = System.getProperty("user.dir") + "/PriceValue/ETF Value/" + year
									+ "/" + MonthName + "/" + monthday;
							new File(folderDir).mkdirs();
							final File file = new File(folderDir + "/ETF Value.csv");
							final String second = dataFieldRDS;
							final int sec = Integer.parseInt(second);
							final Date date3 = new Date();
							final SimpleDateFormat dt = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
							final String startDate = dt.format(date3);
							final String expectedRate = webElementVal.getText();
							Thread.sleep(1000 * sec);
							final String actualRate = webElementVal.getText();
							final Date date4 = new Date();
							final SimpleDateFormat dt2 = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
							final String endDate = dt2.format(date4);
							System.out.println("First Value===" + expectedRate + "||Second Value===" + actualRate);
							if (controlRDS.equalsIgnoreCase("V")) {
								if (!file.exists()) {
									file.createNewFile();
									final FileWriter fileWritter = new FileWriter(file);
									try {
										final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
										try {
											bufferWritter.write(
													"Pagename,NAV rate,Refreshed NAV rate,Start time,End time,Status\n");
											bufferWritter.close();
											bufferWritter.close();
										} catch (Throwable t) {
											try {
												bufferWritter.close();
											} catch (Throwable exception) {
												t.addSuppressed(exception);
											}
											throw t;
										}
										fileWritter.close();
									} catch (Throwable t2) {
										try {
											fileWritter.close();
										} catch (Throwable exception2) {
											t2.addSuppressed(exception2);
										}
										throw t2;
									}
								}
								String status;
								if (actualRate.equalsIgnoreCase(expectedRate)) {
									status = "FAIL";
								} else {
									status = "PASS";
								}
								final FileWriter fileWritter2 = new FileWriter(file, true);
								final BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
								bufferWritter2.write(Framework.pagename + "," + actualRate + "," + expectedRate + ","
										+ startDate + "," + endDate + "," + status + "\n");
								bufferWritter2.close();
								if (status.equalsIgnoreCase("FAIL")) {
									Connection conn1 = null;
									Statement statement = null;
									try {
										Class.forName("com.mysql.jdbc.Driver").newInstance();
										conn1 = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
												Framework.dbpassword);
										final String qry = "INSERT INTO Hdfc_Priceval_Whatsapp(Pagename,NAV_rate,Refreshed_NAV_rate,Start_time,End_time,Status) VALUES ('"
												+ Framework.pagename + "','" + actualRate + "','" + expectedRate + "','"
												+ startDate + "','" + endDate + "','" + status + "')";
										statement = conn1.createStatement();
										final int rs = statement.executeUpdate(qry);
										System.out.println(rs);
									} catch (Exception e6) {
										e6.printStackTrace();
									} finally {
										conn1.close();
									}
								}
								Framework.tStartTime = Monitoring_FrameWork.StartTime();
							}
						} else {
							System.out.println("Time is either less than 9 AM or greater than 4 PM");
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			if (actionRDS.equalsIgnoreCase("DragAndDrop")) {
				final WebElement dropTo = CheckObjectVisibility(propertyNameRDS, propertyValueRDS, objectTypeRDS,
						pageName);
				if (webElementVal.isDisplayed() && dropTo.isDisplayed()) {
					final Actions dnd = new Actions(Framework.driver);
					dnd.clickAndHold(webElementVal).moveToElement(dropTo).release().build().perform();
				}
			}
			if (actionRDS.equalsIgnoreCase("Scroll5")) {
				final JavascriptExecutor jse6 = (JavascriptExecutor) Framework.driver;
				jse6.executeScript("window.scrollBy(0, 50000);", new Object[0]);
			}
			if (actionRDS.equalsIgnoreCase("ROBOT_PAGEDOWN")) {
				final Robot rb6 = new Robot();
				rb6.keyPress(34);
				rb6.keyRelease(34);
			}
			if (actionRDS.equalsIgnoreCase("SendRandom")) {
				webElementVal.sendKeys(new CharSequence[] { NewCustomFunctions.generateRandomString("10", "1", "AA") });
			}
			if (actionRDS.equalsIgnoreCase("GoldRateCompare")) {
				if (map.get("bankHoliday").equalsIgnoreCase("N")) {
					try {
						final Date dte = new Date();
						final Calendar calendar = Calendar.getInstance();
						calendar.setTime(dte);
						final SimpleDateFormat f3 = new SimpleDateFormat("EEEE");
						final String str2 = f3.format(new Date());
						final int currentTime2 = calendar.get(11);
						if (str2.equalsIgnoreCase("MONDAY") || str2.equalsIgnoreCase("TUESDAY")
								|| str2.equalsIgnoreCase("WEDNESDAY") || str2.equalsIgnoreCase("THURSDAY")
								|| str2.equalsIgnoreCase("FRIDAY")) {
							System.out.println("In this method GoldRateCompare");
							final Date now = new Date();
							final int year = Calendar.getInstance().get(1);
							final String MonthName = new SimpleDateFormat("MMMM").format(now);
							final int monthday = Calendar.getInstance().get(5);
							final String folderDir = System.getProperty("user.dir") + "/PriceValue/Gold Value/" + year
									+ "/" + MonthName + "/" + monthday;
							new File(folderDir).mkdirs();
							final File file = new File(folderDir + "/PriceValue.csv");
							new File(folderDir).mkdirs();
							final File fle = new File(folderDir + "/Count.txt");
							String count2 = null;
							if (!fle.exists()) {
								if (currentTime2 >= 0 && currentTime2 < 12) {
									count2 = "1";
								} else {
									count2 = "2";
								}
								fle.createNewFile();
								final FileWriter fileWritter3 = new FileWriter(fle, false);
								final BufferedWriter bufferWritter3 = new BufferedWriter(fileWritter3);
								bufferWritter3.write(count2);
								bufferWritter3.close();
							} else {
								if (currentTime2 >= 0 && currentTime2 < 12) {
									count2 = "1";
								} else {
									count2 = "2";
								}
								fle.createNewFile();
								final FileWriter fileWritter3 = new FileWriter(fle, false);
								final BufferedWriter bufferWritter3 = new BufferedWriter(fileWritter3);
								bufferWritter3.write(count2);
								bufferWritter3.close();
								FileReader reader = null;
								try {
									reader = new FileReader(fle);
									final char[] chars = new char[(int) fle.length()];
									reader.read(chars);
									count2 = new String(chars);
									reader.close();
								} catch (IOException ex11) {
									System.out.println(ex11);
								} finally {
									if (reader != null) {
										reader.close();
									}
								}
							}
							final File file2 = new File(folderDir + "/ETF Value.csv");
							String priceVal = "";
							String timeVal = "";
							if (currentTime2 >= 0 && currentTime2 < 12) {
								if (currentTime2 >= 9 && currentTime2 < 10) {
									final FileWriter fileWritter4 = new FileWriter(fle, false);
									final BufferedWriter bufferWritter4 = new BufferedWriter(fileWritter4);
									bufferWritter4.write("1");
									bufferWritter4.close();
									if (count2.equalsIgnoreCase("1")) {
										if (file2.exists()) {
											BufferedReader bufferedreader = null;
											FileReader filereader = null;
											try {
												filereader = new FileReader(file2);
												bufferedreader = new BufferedReader(filereader);
												String strCurrentLine;
												while ((strCurrentLine = bufferedreader.readLine()) != null) {
													priceVal = strCurrentLine;
												}
												timeVal = priceVal;
												System.out.println(priceVal);
												priceVal = priceVal.split(",")[2];
												System.out.println(priceVal);
												String time = timeVal.split(",")[3];
												time = time.split(" ")[1].split(":")[0];
												final int tm = Integer.parseInt(time);
												if (tm >= 0 && tm < 12) {
													System.out.println("Previous entry time is : " + tm);
												} else {
													final Date date5 = new Date();
													final SimpleDateFormat dt3 = new SimpleDateFormat(
															"YYYY/MM/dd HH:mm:ss");
													final String startDate2 = dt3.format(date5);
													final String expectedRate2 = webElementVal.getText();
													String status2;
													if (priceVal.equalsIgnoreCase(expectedRate2)) {
														status2 = "FAIL";
													} else {
														status2 = "PASS";
													}
													if (controlRDS.equalsIgnoreCase("V")) {
														final FileWriter fileWritter5 = new FileWriter(file2, true);
														final BufferedWriter bufferWritter5 = new BufferedWriter(
																fileWritter5);
														bufferWritter5.write(Framework.pagename + "," + priceVal + ","
																+ expectedRate2 + "," + startDate2 + "," + status2
																+ "\n");
														bufferWritter5.close();
														if (status2.equalsIgnoreCase("FAIL")) {
															Connection conn2 = null;
															Statement statement2 = null;
															try {
																Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
																conn2 = DriverManager.getConnection(Framework.dburl,
																		Framework.dbuser, Framework.dbpassword);
																final String qry2 = "INSERT INTO Hdfc_Priceval_Whatsapp(Pagename,NAV_rate,Refreshed_NAV_rate,Status,Date_And_time) VALUES ('"
																		+ Framework.pagename + "','" + priceVal + "','"
																		+ expectedRate2 + "','" + status2 + "','"
																		+ startDate2 + "')";
																statement2 = conn2.createStatement();
																final int rs2 = statement2.executeUpdate(qry2);
																System.out.println(rs2);
															} catch (Exception e7) {
																e7.printStackTrace();
															} finally {
																conn2.close();
															}
														}
														Framework.tStartTime = Monitoring_FrameWork.StartTime();
														System.out.println("Inserted sucessfully");
													}
												}
											} catch (IOException e8) {
												e8.printStackTrace();
											}
										} else {
											file2.createNewFile();
											final FileWriter fileWritter6 = new FileWriter(file2, true);
											final BufferedWriter bufferWritter6 = new BufferedWriter(fileWritter6);
											bufferWritter6.write(
													"Pagename,Gold NAV rate,Gold refreshed NAV rate,Date & Time,Status\n");
											final Date date6 = new Date();
											final SimpleDateFormat dt4 = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
											final String startDate3 = dt4.format(date6);
											final String expectedRate3 = webElementVal.getText();
											String status3;
											if (priceVal.equalsIgnoreCase(expectedRate3)) {
												status3 = "FAIL";
											} else {
												status3 = "PASS";
											}
											if (controlRDS.equalsIgnoreCase("V")) {
												bufferWritter6.write(Framework.pagename + "," + priceVal + ","
														+ expectedRate3 + "," + startDate3 + "," + status3 + "\n");
												bufferWritter6.close();
												if (status3.equalsIgnoreCase("FAIL")) {
													Connection conn3 = null;
													Statement statement3 = null;
													try {
														Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
														conn3 = DriverManager.getConnection(Framework.dburl,
																Framework.dbuser, Framework.dbpassword);
														final String qry3 = "INSERT INTO Hdfc_Priceval_Whatsapp(Pagename,NAV_rate,Refreshed_NAV_rate,Status,Date_And_time) VALUES ('"
																+ Framework.pagename + "','" + priceVal + "','"
																+ expectedRate3 + "','" + status3 + "','" + startDate3
																+ "')";
														statement3 = conn3.createStatement();
														final int rs3 = statement3.executeUpdate(qry3);
														System.out.println(rs3);
													} catch (Exception e9) {
														e9.printStackTrace();
													} finally {
														conn3.close();
													}
												}
												Framework.tStartTime = Monitoring_FrameWork.StartTime();
												System.out.println("Inserted sucessfully");
											}
										}
									} else {
										System.out.println(count2);
									}
								}
							} else if (currentTime2 >= 22 && currentTime2 < 23) {
								final FileWriter fileWritter4 = new FileWriter(fle, false);
								final BufferedWriter bufferWritter4 = new BufferedWriter(fileWritter4);
								bufferWritter4.write("2");
								bufferWritter4.close();
								if (count2.equalsIgnoreCase("2")) {
									String expectedRate4 = null;
									String startDate4 = null;
									if (file2.exists()) {
										BufferedReader bufferedreader2 = null;
										FileReader filereader2 = null;
										try {
											filereader2 = new FileReader(file2);
											bufferedreader2 = new BufferedReader(filereader2);
											String strCurrentLine2;
											while ((strCurrentLine2 = bufferedreader2.readLine()) != null) {
												priceVal = strCurrentLine2;
											}
											timeVal = priceVal;
											System.out.println(priceVal);
											priceVal = priceVal.split(",")[2];
											System.out.println(priceVal);
											String time2 = timeVal.split(",")[3];
											time2 = time2.split(" ")[1].split(":")[0];
											final int tm2 = Integer.parseInt(time2);
											System.out.println(tm2);
											if (tm2 >= 12 && tm2 <= 23) {
												System.out.println("Previous entry time is : " + tm2);
											} else {
												final Date date7 = new Date();
												final SimpleDateFormat dt5 = new SimpleDateFormat(
														"YYYY/MM/dd HH:mm:ss");
												startDate4 = dt5.format(date7);
												expectedRate4 = webElementVal.getText();
												String status2;
												if (priceVal.equalsIgnoreCase(expectedRate4)) {
													status2 = "FAIL";
												} else {
													status2 = "PASS";
												}
												if (controlRDS.equalsIgnoreCase("V")) {
													final FileWriter fileWritter5 = new FileWriter(file2, true);
													final BufferedWriter bufferWritter5 = new BufferedWriter(
															fileWritter5);
													bufferWritter5.write(Framework.pagename + "," + priceVal + ","
															+ expectedRate4 + "," + startDate4 + "," + status2 + "\n");
													bufferWritter5.close();
													if (status2.equalsIgnoreCase("FAIL")) {
														Connection conn2 = null;
														Statement statement2 = null;
														try {
															Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
															conn2 = DriverManager.getConnection(Framework.dburl,
																	Framework.dbuser, Framework.dbpassword);
															final String qry2 = "INSERT INTO Hdfc_Priceval_Whatsapp(Pagename,NAV_rate,Refreshed_NAV_rate,Status,Date_And_time) VALUES ('"
																	+ Framework.pagename + "','" + priceVal + "','"
																	+ expectedRate4 + "','" + status2 + "','"
																	+ startDate4 + "')";
															statement2 = conn2.createStatement();
															final int rs2 = statement2.executeUpdate(qry2);
															System.out.println(rs2);
														} catch (Exception e7) {
															e7.printStackTrace();
														} finally {
															conn2.close();
														}
													}
													Framework.tStartTime = Monitoring_FrameWork.StartTime();
												}
											}
										} catch (IOException e10) {
											e10.printStackTrace();
										}
									} else {
										file2.createNewFile();
										final FileWriter fileWritter7 = new FileWriter(file2, true);
										final BufferedWriter bufferWritter7 = new BufferedWriter(fileWritter7);
										bufferWritter7.write(
												"Pagename,Gold NAV rate,Gold refreshed NAV rate,Date & Time,Status\n");
										final Date date8 = new Date();
										final SimpleDateFormat dt6 = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
										startDate4 = dt6.format(date8);
										expectedRate4 = webElementVal.getText();
										String status3;
										if (priceVal.equalsIgnoreCase(expectedRate4)) {
											status3 = "FAIL";
										} else {
											status3 = "PASS";
										}
										if (controlRDS.equalsIgnoreCase("V")) {
											bufferWritter7.write(Framework.pagename + "," + priceVal + ","
													+ expectedRate4 + "," + startDate4 + "," + status3 + "\n");
											bufferWritter7.close();
											if (status3.equalsIgnoreCase("FAIL")) {
												Connection conn3 = null;
												Statement statement3 = null;
												try {
													Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
													conn3 = DriverManager.getConnection(Framework.dburl,
															Framework.dbuser, Framework.dbpassword);
													final String qry3 = "INSERT INTO Hdfc_Priceval_Whatsapp(Pagename,NAV_rate,Refreshed_NAV_rate,Status,Date_And_time) VALUES ('"
															+ Framework.pagename + "','" + priceVal + "','"
															+ expectedRate4 + "','" + status3 + "','" + startDate4
															+ "')";
													statement3 = conn3.createStatement();
													final int rs3 = statement3.executeUpdate(qry3);
													System.out.println(rs3);
												} catch (Exception e9) {
													e9.printStackTrace();
												} finally {
													conn3.close();
												}
											}
											Framework.tStartTime = Monitoring_FrameWork.StartTime();
										}
									}
								} else {
									System.out.println(count2);
								}
							}
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("CheckTimeChange")) {
				final Date dte = new Date();
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(dte);
				final int currentTimehour = calendar.get(11);
				final SimpleDateFormat f2 = new SimpleDateFormat("EEEE");
				final String str = f2.format(new Date());
				if (str.equalsIgnoreCase("MONDAY") || str.equalsIgnoreCase("TUESDAY")
						|| str.equalsIgnoreCase("WEDNESDAY") || str.equalsIgnoreCase("THURSDAY")
						|| str.equalsIgnoreCase("FRIDAY")) {
					System.out.println("In this method CheckTimeChange");
					if (currentTimehour >= 9 && currentTimehour < 16) {
						final Date now = new Date();
						final int year = Calendar.getInstance().get(1);
						final String MonthName = new SimpleDateFormat("MMMM").format(now);
						final int monthday = Calendar.getInstance().get(5);
						final String folderDir = System.getProperty("user.dir") + "/PriceValue/Date&time/" + year + "/"
								+ MonthName + "/" + monthday;
						new File(folderDir).mkdirs();
						final File file = new File(folderDir + "/Date&time.csv");
						String currentDateTimeGet = webElementVal.getText();
						System.out.println(currentDateTimeGet);
						currentDateTimeGet = currentDateTimeGet.split(", ")[1];
						final SimpleDateFormat dt7 = new SimpleDateFormat("HH:mm:ss");
						final String currentTime3 = dt7.format(dte);
						System.out.println(currentTime3);
						final Date d2 = dt7.parse(currentTime3);
						final Date d3 = dt7.parse(currentDateTimeGet);
						System.out.println(d3);
						System.out.println(d2);
						final long difference_In_Time = d2.getTime() - d3.getTime();
						final long difference_In_Seconds = difference_In_Time / 1000L % 60L;
						final long difference_In_Minutes = difference_In_Time / 60000L % 60L;
						final long difference_In_Hours = difference_In_Time / 3600000L % 24L;
						String status4 = null;
						if (controlRDS.equalsIgnoreCase("V")) {
							if (!file.exists()) {
								file.createNewFile();
								final FileWriter fileWritter8 = new FileWriter(file);
								try {
									final BufferedWriter bufferWritter8 = new BufferedWriter(fileWritter8);
									try {
										bufferWritter8.write("Pagename,System Time,ETF Time,Status\n");
										bufferWritter8.close();
										bufferWritter8.close();
									} catch (Throwable t3) {
										try {
											bufferWritter8.close();
										} catch (Throwable exception3) {
											t3.addSuppressed(exception3);
										}
										throw t3;
									}
									fileWritter8.close();
								} catch (Throwable t4) {
									try {
										fileWritter8.close();
									} catch (Throwable exception4) {
										t4.addSuppressed(exception4);
									}
									throw t4;
								}
							}
							if (difference_In_Seconds > 15L) {
								status4 = "FAIL";
							} else {
								status4 = "PASS";
							}
							final FileWriter fileWritter8 = new FileWriter(file, true);
							final BufferedWriter bufferWritter8 = new BufferedWriter(fileWritter8);
							bufferWritter8.write(Framework.pagename + "," + currentTime3 + "," + currentDateTimeGet
									+ "," + status4 + "\n");
							bufferWritter8.close();
						}
						if (difference_In_Seconds > 15L) {
							System.out.println(
									"Time not changed And Time difference is " + difference_In_Hours + " Hours "
											+ difference_In_Minutes + " Minutes " + difference_In_Seconds + " Seconds");
							Connection conn3 = null;
							Statement statement3 = null;
							try {
								Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
								conn3 = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
										Framework.dbpassword);
								final String qry3 = "INSERT INTO Hdfc_Priceval_Whatsapp(Pagename,Start_time,End_time,Status) VALUES ('"
										+ Framework.pagename + "','" + currentDateTimeGet + "','" + currentTime3 + "','"
										+ status4 + "')";
								statement3 = conn3.createStatement();
								final int rs3 = statement3.executeUpdate(qry3);
								System.out.println(rs3);
							} catch (Exception e9) {
								e9.printStackTrace();
							} finally {
								conn3.close();
							}
						} else {
							System.out.println("Time difference is " + difference_In_Hours + " Hours "
									+ difference_In_Minutes + " Minutes " + difference_In_Seconds + " Seconds");
						}
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("CheckDownload")) {
				Thread.sleep(5000L);
				int flag = 0;
				final File[] listFiles = new File(dataFieldRDS).listFiles();
				if (listFiles.length != 0) {
					for (int l2 = 0; l2 < listFiles.length; ++l2) {
						if (listFiles[l2].isFile()) {
							final String fileName = listFiles[l2].getName();
							System.out.println();
							System.out.println("File Name Is ===== " + fileName);
							if (fileName.startsWith("HDFCMF") && fileName.endsWith(".xls")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("HDFC AMC ") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("HDFC AMC_") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("BRSR -") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("Annual Report") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("ESOS disclosure") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("ESOS Disclosure") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("22nd Annual") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("FINAL UPLOAD") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("22nd AGM") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("Form_MGT_7") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("Form MGT-7") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else if (fileName.startsWith("Business Responsibility") && fileName.endsWith(".pdf")) {
								flag = 1;
								final boolean success = listFiles[l2].delete();
								if (success) {
									System.out.println("File deleted successfully");
								}
							} else {
								System.out.println("******** File not Found ********");
								System.out.println();
							}
						}
					}
					if (flag == 0) {
						final FileInputStream fis = new FileInputStream(Functions.path2);
						Functions.pro.load(fis);
						final String popup = Functions.pro.getProperty("popup");
						if (popup.equalsIgnoreCase("Y")) {
							final Frame frame6 = new Frame();
							frame6.setVisible(true);
							frame6.toFront();
							final Object[] objects = { "File not Found" };
							JOptionPane.showMessageDialog(null, objects, null, -1);
							frame6.setVisible(false);
						}
					}
				} else {
					System.out.println("File not Found");
					final FileInputStream fis = new FileInputStream(Functions.path2);
					Functions.pro.load(fis);
					final String popup = Functions.pro.getProperty("popup");
					if (popup.equalsIgnoreCase("Y")) {
						final Frame frame6 = new Frame();
						frame6.setVisible(true);
						frame6.toFront();
						final Object[] objects = { "File not Found" };
						JOptionPane.showMessageDialog(null, objects, null, -1);
						frame6.setVisible(false);
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("getQuote")) {
				Functions.quote = webElementVal.getText();
				System.out.println("Quote==" + Functions.quote);
			}
			if (actionRDS.equalsIgnoreCase("getQuoteTravel")) {
				Functions.quote = webElementVal.getText();
				Functions.quote = Functions.quote.split("ID: ")[1].trim();
				System.out.println("Quote==" + Functions.quote);
			}
			if (actionRDS.equalsIgnoreCase("GetQuoteMotor")) {
				if (objectTypeRDS.equalsIgnoreCase("Editbox")) {
					Functions.quote = webElementVal.getAttribute("value");
				} else {
					Functions.quote = webElementVal.getText();
				}
				System.out.println("Quote==" + Functions.quote);
				Functions.quote = Functions.quote.split("ID : ")[1].trim();
				System.out.println("Quote==" + Functions.quote);
			}
			if (actionRDS.equalsIgnoreCase("SendQuoteValue")) {
				webElementVal.sendKeys(new CharSequence[] { Functions.quote });
			}
			if (actionRDS.equalsIgnoreCase("MAILOTP_READ")) {
				System.out.println("//////////In OTP_READ");
				final String host = "smtp.gmail.com";
				final String username = "monitoring4apmosys@gmail.com";
				final String password = "Chase@123";
				String OTP = FetchMail1.getOtp("smtp.gmail.com", "monitoring4apmosys@gmail.com", "Chase@123");
				System.out.println("OTP_READ====" + OTP);
				if (OTP == null || OTP.equalsIgnoreCase("")) {
					OTP = FetchMail1.getOtp("smtp.gmail.com", "monitoring4apmosys@gmail.com", "Chase@123");
					System.out.println("OTP_READ====" + OTP);
				}
				webElementVal.clear();
				webElementVal.click();
				webElementVal.sendKeys(new CharSequence[] { OTP });
				final Date date9 = new Date();
				final SimpleDateFormat dft = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
				Framework.OTPcurrentTime = dft.format(date9);
				System.out.println(Framework.OTPcurrentTime);
			}
			if (actionRDS.equalsIgnoreCase("Mirae_OTP")) {
				final String senderName = dataFieldRDS;
				String otp2 = "";
				Functions.message = DB_Read_OTP.getMessageFromDb(senderName);
				otp2 = Functions.message.split(" ")[0].trim();
				System.out.println("OTP is  " + otp2);
				if (otp2 != null) {
					webElementVal.sendKeys(new CharSequence[] { otp2 });
				}
			}

			if (actionRDS.equalsIgnoreCase("OTP_FromMessage")) {
				String senderName = dataFieldRDS;
				String otp = "";
				message = DB_Read_OTP.getMessageFromDb(senderName);
				otp = message.split(" ")[0].trim();
				System.out.println("OTP From message is  " + otp);
				if (otp != null) {
					webElementVal.sendKeys(otp);
				}

			}
			if (actionRDS.equalsIgnoreCase("SikuliClicknew")) {
				System.out.println("===== INSIDE NEW SIKULI CLICK =====");
				final Screen s37 = new Screen();
				Thread.sleep(3000L);
				s37.wait((Object) propertyValueRDS, 90.0);
				final Pattern fileInputTextBox1 = new Pattern(propertyValueRDS);
				if (s37.find((Object) fileInputTextBox1) != null) {
					System.out.println("Element found by Sikuli");
					s37.click((Object) fileInputTextBox1);
				} else {
					System.out.println("Element not found by Sikuli");
				}

			} else if (actionRDS.equalsIgnoreCase("SIKULICLICK")) {
				System.out.println("------------- Inside SIKULICLICK-------------------------");
				final Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 60.0);
				s34.exists((Object) propertyValueRDS, 60.0).click();
				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}

			} else if (actionRDS.equalsIgnoreCase("pressKeyboard")) {
				CustomFunctions.pressKeyboardBtn(propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("SIKULIDOUBLECLICKAndSendkeys")) {
				System.out.println("------------- Inside SIKULIDOUBLECLICK-------------------------");
				final Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 90.0);
				s34.exists((Object) propertyValueRDS).doubleClick();
				Thread.sleep(2000);
				s34.type(dataFieldRDS);
				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}
			} else if (actionRDS.equalsIgnoreCase("SIKULIDOUBLECLICK")) {
				System.out.println("------------- Inside SIKULIDOUBLECLICK-------------------------");
				final Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 90.0);
				s34.exists((Object) propertyValueRDS).doubleClick();
				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}
			} else if (actionRDS.equalsIgnoreCase("SIKULICLICK1")) {
				System.out.println("== In SIKULICLICK 1===");
				final Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 90.0);
				s34.exists((Object) propertyValueRDS).click();
				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}
			} else if (actionRDS.equalsIgnoreCase("SIKULICLICK2")) {
				System.out.println("== In SIKULICLICK 2===");
				final Screen s38 = new Screen();
				final Pattern e12 = new Pattern(propertyValueRDS);
				s38.wait((Object) e12, 5.0);
				s38.click((Object) e12);
			} else if (actionRDS.equalsIgnoreCase("SIKULICLICK3")) {
				System.out.println("== In SIKULICLICK 3===");
				final Screen s34 = new Screen();
				s34.exists((Object) propertyValueRDS).click();
			} else if (actionRDS.equalsIgnoreCase("SIKULICLICK4")) {
				System.out.println("== In SIKULICLICK 4===");
				final Screen s34 = new Screen();
				s34.click((Object) propertyValueRDS);
			} else if (actionRDS.equalsIgnoreCase("SIKULICLICK5")) {
				System.out.println("== In SIKULICLICK 5===");
				final Screen s38 = new Screen();
				final Pattern e12 = new Pattern(propertyValueRDS);
				s38.click((Object) e12);
			} else if (actionRDS.equalsIgnoreCase("SIKULICHECKVISIBILITY")) {
				System.out.println("------------- Inside SIKULICHECKVISIBILITY-------------------------");
				final Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 60.0);
				if (s34.exists((Object) propertyValueRDS) != null) {
					System.out.println("Element Found -------------------------------vv");
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(Framework.pagename, Framework.pagename);
					}
				} else {
					System.out.println("Element Not Found -------------------------------vv");
				}
			} else if (actionRDS.equalsIgnoreCase("SIKULISENDKEYS")) {
				Screen s34 = new Screen();
				s34.wait((Object) propertyValueRDS, 60.0);
				s34.exists((Object) propertyValueRDS).click();
				s34.type(dataFieldRDS);
			} else if (actionRDS.equalsIgnoreCase("SENDKEYS_BIRTHDAY")) {
				try {
					if (webElementVal.isEnabled()) {
						System.out.println("actionRDS=  " + actionRDS);
						System.out.println(dataFieldRDS);
						final int mindate = 1;
						final int maxdate = 12;
						final int minmnth = 1;
						final int maxmnth = 28;
						final int minyear = 1977;
						final int maxyear = 2000;
						final int random_date = (int) (Math.random() * 12.0 + 1.0);
						final String date10 = String.valueOf(random_date);
						final int random_mnth = (int) (Math.random() * 28.0 + 1.0);
						final String mnth = String.valueOf(random_mnth);
						final int random_year = (int) (Math.random() * 24.0 + 1977.0);
						final String year2 = String.valueOf(random_year);
						final String finalDate = String.valueOf(date10) + "/" + mnth + "/" + year2;
						System.out.println(finalDate);
						final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						final Date d4 = df.parse(finalDate);
						System.out.println("Date: " + d4);
						System.out.println("Date in dd/MM/yyyy format is: " + df.format(d4));
						webElementVal.sendKeys(new CharSequence[] { df.format(d4) });
						Thread.sleep(10000L);
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
					Monitoring_FrameWork.SaveResult("False", "True");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYBAN")) {
				final String ques = Framework.driver.findElement(By.xpath(
						"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
						.getText();
				if (ques != null) {
					try {
						final String ans2 = Framework.driver.findElement(By.xpath(
								"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
								.getText();
						System.out.println("ans:" + ans2);
						if (ans2.contains("book")) {
							Framework.driver
									.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
									.sendKeys(new CharSequence[] { "book" });
						} else {
							Framework.driver
									.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
									.sendKeys(new CharSequence[] { "hero" });
						}
					} catch (Exception e16) {
						System.out.println("In Catch block");
					}
					try {
						final String ans3 = Framework.driver.findElement(By.xpath(
								"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
								.getText();
						System.out.println("ans1:" + ans3);
						if (ans3.contains("childhood ")) {
							Framework.driver
									.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
									.sendKeys(new CharSequence[] { "hero" });
						} else {
							Framework.driver
									.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
									.sendKeys(new CharSequence[] { "book" });
						}
					} catch (Exception e16) {
						System.out.println("childqueIn Catch block");
					}
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE_MARI")) {
				try {
					final String ans = Framework.driver
							.findElement(By.xpath(
									"/html/body/form/div[2]/div[2]/div/div/div/div/table/tbody/tr[2]/td[1]/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("work")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "COMPANY" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "MOTHER" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver
							.findElement(By.xpath(
									"/html/body/form/div[2]/div[2]/div/div/div/div/table/tbody/tr[3]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("maiden")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "MOTHER" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "COMPANY" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYMALDIVES")) {
				try {
					final String ans = Framework.driver
							.findElement(By.xpath(
									"/html/body/form/div[2]/div[2]/div/div/div/div/table/tbody/tr[2]/td[1]/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("favourite")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "road" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver
							.findElement(By.xpath(
									"/html/body/form/div[2]/div[2]/div/div/div/div/table/tbody/tr[3]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("grew")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "road" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYUK")) {
				try {
					final String ans = Framework.driver
							.findElement(
									By.xpath("/html/body/form/div[1]/div[2]/div/div/div/div/table/tbody/tr[2]/td/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("Color")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "name" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver
							.findElement(
									By.xpath("/html/body/form/div[1]/div[2]/div/div/div/div/table/tbody/tr[3]/td/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("book")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "name" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans:" + ans);
					if (ans.contains("book")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "hero" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("childhood ")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "hero" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQ")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(propertyValueRDS)).getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("favourite")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "road" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(dataFieldRDS)).getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("grew")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "road" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQ5")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(propertyValueRDS)).getText();
					System.out.println("Get Text Question Is === " + ans);
					if (ans.contains("favourite")) {
						System.out.println(" In Favourite send value is === book");
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						System.out.println(" In grew send value is === road");
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "road" });
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(dataFieldRDS)).getText();
					System.out.println("Get Text Question Is === " + ans4);
					if (ans4.contains("grew")) {
						System.out.println(" In grew send value is === road");
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "road" });
					} else {
						System.out.println(" In Favourite send value is === book");
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE1")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("Color")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "color" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("book")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "color" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE12")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("Color")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "color" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("book")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "color" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE_MR")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(propertyValueRDS)).getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("work")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "COMPANY" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "MOTHER" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
					e.printStackTrace();
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(dataFieldRDS)).getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("maiden")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "MOTHER" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "COMPANY" });
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE_MR2")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("work")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "COLOUR" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "MOTHER" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("maiden")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "MOTHER" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "COLOUR" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUE2")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans:" + ans);
					if (ans.contains("book")) {
						Framework.driver.findElement(By.xpath("//*[@id='AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]']"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id='AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]']"))
								.sendKeys(new CharSequence[] { "road" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("road")) {
						Framework.driver.findElement(By.xpath("//*[@id='AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]']"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id='AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]']"))
								.sendKeys(new CharSequence[] { "road" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SECURITYQUENEW")) {
				try {
					final String ans = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[2]/div/div/div/div/table/tbody[1]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("motherque:" + ans);
					if (ans.contains("Color")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "color" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[0]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					}
				} catch (Exception e) {
					System.out.println("In Catch block");
				}
				try {
					final String ans4 = Framework.driver.findElement(By.xpath(
							"/html/body/form/div[1]/div[1]/div[3]/div[2]/div/div/div/div/table/tbody[2]/tr[1]/td[1]/span"))
							.getText();
					System.out.println("ans1:" + ans4);
					if (ans4.contains("book")) {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "book" });
					} else {
						Framework.driver.findElement(By.xpath("//*[@id=\"AuthenticationFG.AUTHQNA_ANSWER_ARRAY[1]\"]"))
								.sendKeys(new CharSequence[] { "color" });
					}
				} catch (Exception e) {
					System.out.println("childqueIn Catch block");
				}
			} else if (actionRDS.equalsIgnoreCase("SELECTPRICE")) {
				String price = "";
				try {
					if (objectTypeRDS.equalsIgnoreCase("EditBox")) {
						price = webElementVal.getAttribute("Value");
					} else {
						price = webElementVal.getText();
					}
					System.out.println("price=" + price);
					final float f4 = Float.parseFloat(price);
					float m4 = Float.parseFloat(dataFieldRDS);
					System.out.println(m4);
					m4 /= 100.0f;
					final float value4 = f4 + f4 * m4;
					final DecimalFormat df2 = new DecimalFormat("#.00");
					Functions.numberAsString = df2.format(value4);
					System.out.println("After converting =" + Functions.numberAsString);
				} catch (Exception e13) {
					e13.printStackTrace();
				}
			}

			else if (actionRDS.equalsIgnoreCase("selectPriceDynamic")) {
				String price = "";
				try {
					if (objectTypeRDS.equalsIgnoreCase("EditBox")) {
						price = webElementVal.getAttribute("Value");
					} else {
						price = webElementVal.getText();
					}
					System.out.println("Current Price Is == " + price);
					final float f4 = Float.parseFloat(price);
					float m4 = Float.parseFloat(dataFieldRDS);
					m4 /= 100.0f;

					float value4;
					if (objectTypeRDS.equalsIgnoreCase("increase")) {
						value4 = f4 + f4 * m4;

					} else {
						value4 = f4 - f4 * m4;
					}

					Functions.numberAsString = CustomFunctions.roundToNumber(value4);
					System.out.println("After converting =" + Functions.numberAsString);
				} catch (Exception e13) {
					e13.printStackTrace();
				}
			}

			else if (actionRDS.equalsIgnoreCase("SendSelectPrice")) {
				Thread.sleep(2000);
				if (webElementVal != null) {
					System.out.println("Xpath found");
				}
//				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].style.border='2px solid red'",
//						webElementVal);
				System.out.println("price send to == " + Functions.numberAsString);
				if (objectTypeRDS.equalsIgnoreCase("js")) {
					((JavascriptExecutor) Framework.driver).executeScript(
							"arguments[0].value='" + Functions.numberAsString.trim() + "';",
							new Object[] { webElementVal });
				} else if (objectTypeRDS.equalsIgnoreCase("action")) {
					new Actions(Framework.driver).sendKeys(webElementVal, Functions.numberAsString.trim()).perform();
				} else {
					webElementVal.sendKeys(Functions.numberAsString.trim());
				}
			} else if (actionRDS.equalsIgnoreCase("RandomDigitSendkeys")) {
				final Random rd = new Random();
				final int num = rd.nextInt(9999);
				final String number = String.format("%04d", num);
				System.out.println(number);
				webElementVal.sendKeys(new CharSequence[] { number });
			}
			// cj
			else if (actionRDS.contains("SENDNUMBER(")) {
				String count = actionRDS.split("\\(")[1].split("\\)")[0];
				String name = CustomFunctions.generateRandomString(count, "1", "01");
				webElementVal.sendKeys(name);

				if (!(dataFieldRDS.equalsIgnoreCase(" ")) || dataFieldRDS != null) {
					globleValues.put(dataFieldRDS, name);
					System.out.println(" !!! Added in globle map !!!");
				}

			}
			// // cj
			// else if (actionRDS.equalsIgnoreCase("authorize")) {
			//
			// CustomFunctions.authorize(propertyValueRDS, dataFieldRDS);
			//
			// }

			else if (actionRDS.equalsIgnoreCase("RandomStringSendkeys")) {
				final String s39 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				final StringBuilder sb = new StringBuilder();
				final Random rd2 = new Random();
				for (int i2 = 1; i2 <= 2; ++i2) {
					final int index2 = rd2.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ".length());
					final char randoChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(index2);
					sb.append(randoChars);
				}
				final String randomString = sb.toString();
				System.out.println(randomString);
				webElementVal.sendKeys(new CharSequence[] { randomString });
			} else if (actionRDS.equalsIgnoreCase("BROWSEBACK")) {
				Framework.driver.navigate().back();
			} else if (actionRDS.equalsIgnoreCase("jsBack")) {
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				// js.executeScript("window.history.back();");
				js.executeScript("window.history.go(-1);");
			} else if (actionRDS.equalsIgnoreCase("jsBack2")) {
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("window.history.back();");
			} else if (actionRDS.equalsIgnoreCase("getCurrentUrl")) {
				currentUrl = Framework.driver.getCurrentUrl();
			} else if (actionRDS.equalsIgnoreCase("navigateUrl")) {
				Framework.driver.navigate().to(currentUrl);
			} else if (actionRDS.equalsIgnoreCase("actionBack")) {
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.BACK_SPACE).build().perform();
			}

			else if (actionRDS.equalsIgnoreCase("SikuliScroll")) {
				final Screen s34 = new Screen();
				s34.wheel(1, 10);
			} else if (actionRDS.equalsIgnoreCase("SikuliScrollright")) {
				final int val2 = Integer.parseInt(dataFieldRDS);
				final Screen s40 = new Screen();
				Thread.sleep(1000L);
				final Robot r2 = new Robot();
				r2.keyPress(16);
				s40.wheel(1, val2);
				r2.keyRelease(16);
			} else if (actionRDS.equalsIgnoreCase("ScrollRight")) {
				((JavascriptExecutor) Framework.driver).executeScript("window.scrollBy(2000,0)", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("Selectlist")) {
				Framework.driver.findElement(By.xpath("//*[@id='education_wrapperDiv']/div/div/span/span[1]/span"))
						.click();
				Thread.sleep(2000L);
				final WebElement Education = Framework.driver
						.findElement(By.xpath("//*[@id='commonInitializer']/span/span/span[2]/ul"));
				for (final WebElement Education_name : Education.findElements(By.tagName("li"))) {
					if (Education_name.getText().equalsIgnoreCase("Graduate")) {
						Education_name.click();
						break;
					}
				}
				Framework.driver
						.findElement(
								By.xpath("//*[@id='organisationName_wrapperDiv']/div/div/span/span[1]/span/span[2]"))
						.click();
				Thread.sleep(2000L);
				final WebElement compnylist = Framework.driver
						.findElement(By.xpath("//*[@id='commonInitializer']/span/span/span[2]/ul"));
				for (final WebElement compny : compnylist.findElements(By.tagName("li"))) {
					if (compny.getText().equalsIgnoreCase("3M India Ltd")) {
						compny.click();
						break;
					}
				}
			} else

			if (actionRDS.equalsIgnoreCase("checkLICPages")) {

				String output = null;
				String filepath = System.getProperty("user.dir");
				File file = new File(String.valueOf(filepath) + "/LICTimeCheck.txt");
				if (file.exists()) {

					List<String> lines = Files
							.readAllLines(Paths.get(String.valueOf(filepath) + File.separator + "LICTimeCheck.txt"));
					for (String line : lines) {
						output = line;
						break;
					}
					System.out.println("LIC Time Check Output is ----> " + output);
					String minuteDiff = BusinessHour.monitoringTimeCompare(output);

					if (Integer.parseInt(minuteDiff) >= 30) {
						System.out.println("script running continue...");

					} else {
						System.out.println("===Skip Pages===");
						while (!Framework.pagename.equals(propertyValueRDS)) {
							Framework.recordsetRDS.moveNext();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
						}
						Framework.recordsetRDS.movePrevious();
					}

				} else {
					System.out.println("LICTimeCheck.txt file not available....");
				}

			} else if (actionRDS.equalsIgnoreCase("writeLICPagesTime")) {

				String filepath = System.getProperty("user.dir");
				File file = new File(String.valueOf(filepath) + "/LICTimeCheck.txt");
				SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String currentDateAndTime = d.format(new Date());
				System.out.println("Current Time Is === " + currentDateAndTime);
				file.createNewFile();
				FileWriter fileWritter = new FileWriter(file, false);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(currentDateAndTime);
				bufferWritter.close();
			}

			// scroll a (table,div) Particular element to 100px
			else if (actionRDS.equalsIgnoreCase("scrollElement")) {
				System.out.println("In scrollElement");
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].scrollTop=arguments[1];",
						webElementVal, 250);
			}

			else if (actionRDS.equalsIgnoreCase("WAITINVISIBILITY")) {
				try {
					System.out.println("Waiting for element to disappear...");
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(60));
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(propertyValueRDS)));
					System.out.println("Element has disappeared.");
				} catch (Exception ex24) {
				}
			} else if (actionRDS.equalsIgnoreCase("WAITVISIBILITY")) {
				try {
					final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(60L));
					wait4.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
				} catch (Exception ex25) {
				}
			} else if (actionRDS.equalsIgnoreCase("ScrollDown")) {
				JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				jse7.executeScript("window.scrollBy(0, 2000);", new Object[0]);
				jse7.executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("scrollUpTo")) {
				JavascriptExecutor jse8 = (JavascriptExecutor) Framework.driver;
				jse8.executeScript("window.scrollBy(0," + dataFieldRDS + ")", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("Scroll")) {
				if (controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}
				try {
					JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
					jse7.executeScript("window.scrollBy(0, 500);", new Object[0]);
				} catch (Exception ex26) {
				}
			} else if (actionRDS.equalsIgnoreCase("Scrollorder")) {
				final JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				jse7.executeScript("window.scrollBy(0, 500);", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("Scrollsbi")) {
				final JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				jse7.executeScript("window.scrollBy(0, 200);", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("Scrollupsbi")) {
				final JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				jse7.executeScript("window.scrollBy(500, 0);", new Object[0]);
				jse7.executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("Scrollup")) {
				final JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				jse7.executeScript("window.scrollBy(2000, 0);", new Object[0]);
				jse7.executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
			} else if (actionRDS.equalsIgnoreCase("scrollIntoView")) {
				JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				WebElement element = Functions.driver.findElement(By.id("summaryTab"));
				jse7.executeScript("arguments[0].scrollIntoView();", new Object[] { element });
			} else if (actionRDS.equalsIgnoreCase("ScrollIntoElement")) {
				try {
					((JavascriptExecutor) Framework.driver).executeScript(
							"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", webElementVal);
					Thread.sleep(3000L);
				} catch (Exception e) {
					System.out.println("Elememt Not Found");
				}
			} else if (actionRDS.equalsIgnoreCase("scrollIntoViewOTP")) {
				final JavascriptExecutor jse7 = (JavascriptExecutor) Framework.driver;
				final WebElement element = Functions.driver.findElement(By.id("txtOneTimePassword"));
				jse7.executeScript("arguments[0].scrollIntoView();", new Object[] { element });
			} else if (!actionRDS.equalsIgnoreCase("CLOSEF")) {
				if (actionRDS.equalsIgnoreCase("APITEST")) {
					System.out.println("APITESTING////////////////////////////////////////////////////");
					final String filepath = String.valueOf(
							String.valueOf(String.valueOf(String.valueOf(Framework.homedir)))) + "/config.xlsx";
					System.out.println(filepath);
					final Fillo fillo = new Fillo();
					final com.codoid.products.fillo.Connection con = fillo.getConnection(filepath);
					final String strconfig = "Select * from sheet1";
					final Recordset recordset1 = con.executeQuery("Select * from sheet1");
					while (recordset1.next()) {
						final int count3 = recordset1.getCount();
						Framework.tStartTime = Monitoring_FrameWork.StartTime();
						final String uri = recordset1.getField("url");
						final String post = recordset1.getField("postRequest");
						final String body = recordset1.getField("requestBody");
						final String PN = recordset1.getField("page");
						final String token2 = recordset1.getField("token");
						final String tp = recordset1.getField("type");
						System.out.println("Type of API============================== " + tp);
						RestAssured.baseURI = uri;
						System.getProperty("java.classpath");
						if (tp.equals("POST")) {
							final Response r3 = (Response) ((ValidatableResponse) ((ValidatableResponse) ((Response) RestAssured
									.given().header("token_id", (Object) token2, new Object[0])
									.contentType("application/json").body(body).when().post(post, new Object[0]))
									.then()).using()).extract().response();
							final String resBody = r3.getBody().asString();
							System.out.println("Your response body is:" + resBody + "\n");
							final JSONObject json = new JSONObject(resBody);
							final String statusObtained = json.getString("status");
							System.out.println("Status : " + json.getString("status"));
							final int status5 = r3.getStatusCode();
							if (statusObtained.contains("F") || status5 != 200) {
								JOptionPane.showMessageDialog(null, "Invalid response code---" + status5);
							}
							System.out.println("This is status........................." + status5 + "\n");
							final String actual = Integer.toString(status5);
							System.out.println("Actual result is:" + actual);
							final String expected = Integer.toString(200);
							System.out.println("Expected result is:" + expected);
							Monitoring_FrameWork.ApiSaveResult(actual, expected, PN);
							System.out.println(String.valueOf(String.valueOf(String.valueOf(String.valueOf(actual))))
									+ ":is after loggs.........................");
						} else {
							if (!tp.equals("GET")) {
								continue;
							}
							RestAssured.baseURI = "https://webservices.bajajallianz.com/BagicWap/verifyWapUsers/865800022474685";
							final RequestSpecification httpRequest = RestAssured.given();
							final Response response = (Response) httpRequest.get("/EEZEETAB", new Object[0]);
							final int status6 = response.getStatusCode();
							if (status6 != 200) {
								JOptionPane.showMessageDialog(null, "Invalid response code---" + status6);
							}
							System.out.println("This is status........................." + status6 + "\n");
							final String actual2 = Integer.toString(status6);
							System.out.println("Actual result is:" + actual2);
							final String expected2 = Integer.toString(200);
							System.out.println("Expected result is:" + expected2);
							Monitoring_FrameWork.ApiSaveResult(actual2, expected2, PN);
							System.out.println(String.valueOf(String.valueOf(String.valueOf(String.valueOf(actual2))))
									+ ":is after loggs.........................");
						}
					}
				} else if (actionRDS.equalsIgnoreCase("OKPopup")) {
					try {
						final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(2000L));
						wait4.until((Function) ExpectedConditions.visibilityOfElementLocated(By.id("okBtn")));
						Framework.driver.findElement(By.id("okBtn")).click();
						Thread.sleep(2000L);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (actionRDS.equalsIgnoreCase("LICclearHistory")) {
					String imgPath = System.getProperty("user.dir") + "/Image/";
					Screen s48 = new Screen();
					ArrayList<String> tabs = new ArrayList<String>(Framework.driver.getWindowHandles());
					Framework.driver.switchTo().window((String) tabs.get(1));

					JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
					String link = "chrome://settings/clearBrowserData";
					Framework.driver.get(link);

					try {
						Thread.sleep(3000L);
					} catch (Exception e4) {
						e4.printStackTrace();
					}
					System.out.println("property value path == " + propertyValueRDS);
					// click on advance
					s48.wait((Object) propertyValueRDS, 90.0);
					s48.exists((Object) propertyValueRDS).click();

					// click on last hour
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[0], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[0]).click();

					// click on all time
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[1], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[1]).click();

					// click on 1st check box
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

					// for scroll down
					Thread.sleep(1000L);
					s48.wheel(1, 5);

					// for select 2nd box
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

					// for select 3rd box
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

					// for select 4th box
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

					// for click clear button
					s48.wait((Object) imgPath + dataFieldRDS.split("#")[3], 90.0);
					s48.exists((Object) imgPath + dataFieldRDS.split("#")[3]).click();

					Thread.sleep(1000);
					Framework.driver.close();
					Thread.sleep(2000);
					tabs = new ArrayList<String>(Framework.driver.getWindowHandles());
					Framework.driver.switchTo().window((String) tabs.get(0));
					Thread.sleep(1000);

				} else if (actionRDS.equalsIgnoreCase("GETTEXTALART")) {
					final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(8000L));
					wait4.until((Function) ExpectedConditions.alertIsPresent());
					final String myalert = Framework.driver.switchTo().alert().getText();
					System.out.println("Actual........................." + myalert);
					System.out.println("Expected....................." + dataFieldRDS);
					if (controlRDS.equalsIgnoreCase("V")) {
						Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
					}
				} else if (actionRDS.equalsIgnoreCase("SENDREPORT")) {
					NewCustomFunctions.SendReport();

				} else if (actionRDS.equalsIgnoreCase("AIRPAYandINSTANT")) {
					final int TotalCount_page1 = Framework.driver
							.findElements(By.xpath("//td[text()='AIRPAY PAYMENT SERVICES PRIVATE LIMITED']")).size();
					System.out.println("TotalCount_page1:" + TotalCount_page1);
					final int Pending_page1 = Framework.driver.findElements(By.xpath("//td[text()='Pending']")).size();
					System.out.println("Pending size:" + Pending_page1);
					final int Failure_page1 = Framework.driver.findElements(By.xpath("//td[text()='Failure']")).size();
					System.out.println("Failure size:" + Failure_page1);
					Framework.driver.findElement(By.xpath("//a[text()='2']")).click();
					final int TotalCount_page2 = Framework.driver
							.findElements(By.xpath("//td[text()='AIRPAY PAYMENT SERVICES PRIVATE LIMITED']")).size();
					System.out.println("TotalCount_page2:" + TotalCount_page2);
					final int Pending_page2 = Framework.driver.findElements(By.xpath("//td[text()='Pending']")).size();
					System.out.println("Pending size:" + Pending_page2);
					final int Failure_page2 = Framework.driver.findElements(By.xpath("//td[text()='Failure']")).size();
					System.out.println("Failure size:" + Failure_page2);
					final Select BC_name = new Select(Framework.driver
							.findElement(By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlchennalpartnerId']")));
					BC_name.selectByVisibleText("INSTANT GLOBAL PAYTECH PRIVATE LIMITED");
					Thread.sleep(3000L);
					final WebElement Submit = Framework.driver
							.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_btnSubmit']"));
					Submit.click();
					Framework.driver.findElement(By.xpath("//a[text()='1']")).click();
					final int Count_page1 = Framework.driver
							.findElements(By.xpath("//td[text()='INSTANT GLOBAL PAYTECH PRIVATE LIMITED']")).size();
					System.out.println("TotalCount_page1:" + Count_page1);
					final int Pending_page1INSTANT = Framework.driver.findElements(By.xpath("//td[text()='Pending']"))
							.size();
					System.out.println("Pending size:" + Pending_page1INSTANT);
					final int Failure_page1INSTANT = Framework.driver.findElements(By.xpath("//td[text()='Failure']"))
							.size();
					System.out.println("Failure size:" + Failure_page1INSTANT);
					Framework.driver.findElement(By.xpath("//a[text()='2']")).click();
					final int Count_page2 = Framework.driver
							.findElements(By.xpath("//td[text()='INSTANT GLOBAL PAYTECH PRIVATE LIMITED']")).size();
					System.out.println("TotalCount_page2:" + Count_page2);
					final int Pending_page2INSTANT = Framework.driver.findElements(By.xpath("//td[text()='Pending']"))
							.size();
					System.out.println("Pending size:" + Pending_page2INSTANT);
					final int Failure_page2INSTANT = Framework.driver.findElements(By.xpath("//td[text()='Failure']"))
							.size();
					System.out.println("Failure size:" + Failure_page2INSTANT);
					final int AIRPAY_Count = TotalCount_page1 + TotalCount_page2;
					final int AIRPAY_Pending = Pending_page1 + Pending_page2;
					final int AIRPAY_Failure = Failure_page1 + Failure_page2;
					System.out.println("Total_Status" + AIRPAY_Count);
					System.out.println("Total_Pending" + AIRPAY_Pending);
					System.out.println("Total_Failure" + AIRPAY_Failure);
					final int INSTANT_Count = Count_page1 + Count_page2;
					final int INSTANT_Pending = Pending_page1INSTANT + Pending_page2INSTANT;
					final int INSTANT_Failure = Failure_page1INSTANT + Failure_page2INSTANT;
					System.out.println("Total_Status" + INSTANT_Count);
					System.out.println("Total_Pending" + INSTANT_Pending);
					System.out.println("Total_Failure" + INSTANT_Failure);
					if (AIRPAY_Count > 0 && INSTANT_Count > 0) {
						final float Pending_PercentageAIRPAY = AIRPAY_Pending / (float) AIRPAY_Count * 100.0f;
						System.out.println("Pending_PercentageOfAIRPAY::" + Pending_PercentageAIRPAY);
						final float Failure_PercentageAIRPAY = AIRPAY_Failure / (float) AIRPAY_Count * 100.0f;
						System.out.println("Failure_PercentageOfAIRPAY::" + Failure_PercentageAIRPAY);
						final float Pending_PercentageINSTANT = INSTANT_Pending / (float) INSTANT_Count * 100.0f;
						System.out.println("Pending_PercentageOfINSTANT::" + Pending_PercentageINSTANT);
						final float Failure_PercentageINSTANT = INSTANT_Failure / (float) INSTANT_Count * 100.0f;
						System.out.println("Failure_PercentageOfINSTANT::" + Failure_PercentageINSTANT);
						final Date now3 = new Date();
						final int year4 = Calendar.getInstance().get(1);
						new File(String.valueOf(Framework.homedir) + "/Reports/" + year4).mkdir();
						final String MonthName3 = new SimpleDateFormat("MMMM").format(now3);
						new File(String.valueOf(Framework.homedir) + "/Reports/" + year4 + "/" + MonthName3).mkdir();
						final int monthday3 = Calendar.getInstance().get(5);
						new File(String.valueOf(Framework.homedir) + "/Reports/" + year4 + "/" + MonthName3 + "/"
								+ monthday3).mkdir();
						final int hr2 = Calendar.getInstance().get(10);
						final int hrofday2 = Calendar.getInstance().get(11);
						System.out.println("ht==>" + hr2 + "====== hrofday ===>" + hrofday2);
						final DecimalFormat ft2 = new DecimalFormat("00");
						final int min3 = Calendar.getInstance().get(12);
						final int min4 = Calendar.getInstance().get(14);
						Functions.mailTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date());
						System.out.println("min==>" + min3 + "====== min1 ===>" + min4);
						new File(String.valueOf(Framework.homedir) + "/Reports/" + year4 + "/" + MonthName3 + "/"
								+ monthday3 + "//" + Framework.ScriptName).mkdir();
						new File(String.valueOf(Framework.homedir) + "/Reports/" + year4 + "/" + MonthName3 + "/"
								+ monthday3 + "//" + Framework.ScriptName + "/Percentage-Sheet").mkdir();
						Framework.FileNameV = String.valueOf(Framework.homedir) + "/Reports/" + year4 + "/" + MonthName3
								+ "/" + monthday3 + "/" + Framework.ScriptName + "/NSDLPercentage_" + hrofday2 + "_"
								+ min3 + ".xlsx";
						final String fname2 = String.valueOf(Framework.homedir) + "/Reports/" + year4 + "/" + MonthName3
								+ "/" + monthday3 + "/" + Framework.ScriptName + "/NSDLPayment_" + hrofday2 + "_" + min3
								+ ".xlsx";
						System.out.println("File Name ==>" + Framework.FileNameV);
						String prevRunTime2 = "";
						final File ff2 = new File(String.valueOf(Framework.homedir) + "//Reports/Percentage2.xlsx");
						if (ff2.exists()) {
							final Fillo fl2 = new Fillo();
							final com.codoid.products.fillo.Connection con3 = fl2
									.getConnection(String.valueOf(Framework.homedir) + "//Reports/Percentage2.xlsx");
							final Recordset rs5 = con3.executeQuery("Select * from Configuration where Record = 1");
							final int count5 = rs5.getCount();
							System.out.println(" outside Count1 ==>" + count5);
							try {
								System.out.println("In side try block........");
								while (rs5.next() && rs5.getCount() != 0) {
									prevRunTime2 = rs5.getField("Time");
									System.out.println("prevRunTime |: " + prevRunTime2);
								}
							} catch (Exception e22) {
								System.out.println("In side catch block........");
							}
							con3.close();
						} else {
							System.out.println("File Not Exist... Please create file.");
						}
						final Fillo filloU2 = new Fillo();
						final com.codoid.products.fillo.Connection conU2 = filloU2
								.getConnection(String.valueOf(Framework.homedir) + "/Reports/Percentage2.xlsx");
						conU2.executeUpdate("update Configuration set AIRPAY_Total= '" + AIRPAY_Count
								+ "', AIRPAY_Failure='" + AIRPAY_Failure + "' , AIRPAY_Pending='" + AIRPAY_Pending
								+ "', INSTANT_Total='" + INSTANT_Count + "', INSTANT_Failure='" + INSTANT_Failure
								+ "' , INSTANT_Pending='" + INSTANT_Pending + "' , Time='" + ft2.format(hrofday2) + ":"
								+ ft2.format(min3) + "' where Record='1'");
						conU2.close();
						XSSFWorkbook workbook2 = new XSSFWorkbook();
						XSSFSheet spreadsheet2 = workbook2.createSheet("cellstyle");
						XSSFRow row8 = spreadsheet2.createRow(1);
						row8.setHeight((short) 800);
						XSSFCell cell19 = row8.createCell(6);
						cell19.setCellValue("Total Txn as on " + hrofday2 + ":" + min3 + ", " + monthday3 + "-"
								+ MonthName3 + "-" + year4);
						spreadsheet2.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));
						final XSSFCell cell20 = row8.createCell(9);
						cell20.setCellValue((double) AIRPAY_Count);
						spreadsheet2.addMergedRegion(new CellRangeAddress(1, 1, 9, 11));
						XSSFRow row9 = spreadsheet2.createRow(2);
						row9.setHeight((short) 800);
						final XSSFCell cell21 = row9.createCell(6);
						cell21.setCellValue("Total AIRPAY:");
						final XSSFCell cell22 = row9.createCell(7);
						cell22.setCellValue((double) AIRPAY_Count);
						spreadsheet2.addMergedRegion(new CellRangeAddress(2, 2, 7, 8));
						final XSSFCell cell23 = row9.createCell(9);
						cell23.setCellValue("Total INSTANT:");
						final XSSFCell cell24 = row9.createCell(10);
						cell24.setCellValue((double) INSTANT_Count);
						spreadsheet2.addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
						final XSSFRow row10 = spreadsheet2.createRow(3);
						row10.setHeight((short) 800);
						final XSSFCell cell25 = row10.createCell(7);
						cell25.setCellValue("Pending:");
						final XSSFCell cell26 = row10.createCell(8);
						cell26.setCellValue("Failure:");
						final XSSFCell cell27 = row10.createCell(10);
						cell27.setCellValue("Pending:");
						final XSSFCell cell28 = row10.createCell(11);
						cell28.setCellValue("Failure:");
						final XSSFRow row11 = spreadsheet2.createRow(4);
						row10.setHeight((short) 800);
						final XSSFCell cell29 = row11.createCell(6);
						final XSSFCell cell30 = row11.createCell(7);
						cell30.setCellValue((double) AIRPAY_Pending);
						final XSSFCell cell31 = row11.createCell(8);
						cell31.setCellValue((double) AIRPAY_Failure);
						final XSSFCell cell32 = row11.createCell(9);
						final XSSFCell cell33 = row11.createCell(10);
						cell33.setCellValue((double) INSTANT_Pending);
						final XSSFCell cell34 = row11.createCell(11);
						cell34.setCellValue((double) INSTANT_Failure);
						final XSSFRow row12 = spreadsheet2.createRow(5);
						row12.setHeight((short) 800);
						final XSSFCell cell_62 = row12.createCell(5);
						cell_62.setCellValue("Percentage");
						final XSSFCell cell_63 = row12.createCell(6);
						final XSSFCell cell_64 = row12.createCell(7);
						cell_64.setCellValue(String.valueOf(Pending_PercentageAIRPAY) + "%");
						final XSSFCell cell_65 = row12.createCell(8);
						cell_65.setCellValue(String.valueOf(Failure_PercentageAIRPAY) + "%");
						final XSSFCell cell_66 = row12.createCell(9);
						final XSSFCell cell_67 = row12.createCell(10);
						cell_67.setCellValue(String.valueOf(Pending_PercentageINSTANT) + "%");
						final XSSFCell cell_68 = row12.createCell(11);
						cell_68.setCellValue(String.valueOf(Failure_PercentageINSTANT) + "%");
						final FileOutputStream out3 = new FileOutputStream(new File(fname2));
						workbook2.write((OutputStream) out3);
						out3.close();
						System.out.println("................Creating File to attach with mail..................");
						workbook2 = new XSSFWorkbook();
						spreadsheet2 = workbook2.createSheet("cellstyle");
						spreadsheet2.setHorizontallyCenter(true);
						row8 = spreadsheet2.createRow(0);
						cell19 = row8.createCell(0);
						System.out.println("prevRunTime : " + prevRunTime2);
						final String timestamp2 = String.valueOf(ft2.format(monthday3)) + " " + MonthName3 + " " + year4
								+ " from " + prevRunTime2 + "Hrs to " + ft2.format(hrofday2) + ":" + ft2.format(min3)
								+ " Hrs";
						cell19.setCellValue(timestamp2);
						spreadsheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
						final CellStyle style2 = (CellStyle) cell19.getCellStyle();
						style2.setAlignment(HorizontalAlignment.CENTER);
						cell19.setCellStyle(style2);
						row9 = spreadsheet2.createRow(1);
						final XSSFCell cell2A2 = row9.createCell(0);
						cell2A2.setCellValue("Total AIRPAY:");
						final XSSFCell cell2B2 = row9.createCell(1);
						cell2B2.setCellValue("Pending");
						final XSSFCell cell2C2 = row9.createCell(2);
						cell2C2.setCellValue("Failure");
						final XSSFCell cell2E2 = row9.createCell(3);
						cell2E2.setCellValue(" ");
						spreadsheet2.addMergedRegion(new CellRangeAddress(1, 3, 4, 4));
						final XSSFCell cell2F2 = row9.createCell(4);
						cell2F2.setCellValue("");
						final XSSFCell cell2G2 = row9.createCell(5);
						cell2G2.setCellValue("INSTANT");
						final XSSFCell cell2H2 = row9.createCell(6);
						cell2H2.setCellValue("Pending");
						final XSSFCell cell2I2 = row9.createCell(7);
						cell2I2.setCellValue("Failure");
						final XSSFRow row13 = spreadsheet2.createRow(2);
						final XSSFCell cell3A4A2 = row13.createCell(0);
						cell3A4A2.setCellValue((double) AIRPAY_Count);
						spreadsheet2.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
						final XSSFCell cell3B2 = row13.createCell(1);
						cell3B2.setCellValue((double) AIRPAY_Pending);
						final XSSFCell cell3c2 = row13.createCell(2);
						cell3c2.setCellValue((double) AIRPAY_Failure);
						final XSSFCell cell3D2 = row13.createCell(3);
						final XSSFCell cell3F2 = row13.createCell(5);
						cell3F2.setCellValue((double) INSTANT_Count);
						spreadsheet2.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
						final XSSFCell cell3G2 = row13.createCell(6);
						cell3G2.setCellValue((double) INSTANT_Pending);
						final XSSFCell cell3H2 = row13.createCell(7);
						cell3H2.setCellValue((double) INSTANT_Failure);
						final XSSFCell cell3I2 = row13.createCell(8);
						final XSSFRow row14 = spreadsheet2.createRow(3);
						final XSSFCell cell4B2 = row14.createCell(1);
						cell4B2.setCellValue(String.valueOf(Pending_PercentageAIRPAY) + "%");
						final XSSFCell cell4c2 = row14.createCell(2);
						cell4c2.setCellValue(String.valueOf(Failure_PercentageAIRPAY) + "%");
						final XSSFCell cell4D2 = row14.createCell(3);
						final XSSFCell cell4G2 = row14.createCell(6);
						cell4G2.setCellValue(String.valueOf(Pending_PercentageINSTANT) + "%");
						final XSSFCell cell4H2 = row14.createCell(7);
						cell4H2.setCellValue(String.valueOf(Failure_PercentageINSTANT) + "%");
						final XSSFCell cell4I2 = row14.createCell(8);
						spreadsheet2.setHorizontallyCenter(true);
						spreadsheet2.setVerticallyCenter(true);
						spreadsheet2.autoSizeColumn(4);
						final FileOutputStream out4 = new FileOutputStream(new File(Framework.FileNameV));
						workbook2.write((OutputStream) out4);
						out4.close();
						boolean mailStatus2 = false;
						String alertMessage2 = "";
						if (Pending_PercentageAIRPAY >= 60.0f || Failure_PercentageAIRPAY >= 60.0f
								|| Pending_PercentageINSTANT >= 60.0f || Failure_PercentageINSTANT >= 60.0f) {
							Boolean commaOrAnd2 = false;
							if (Failure_PercentageINSTANT >= 60.0f) {
								alertMessage2 = String.valueOf(alertMessage2) + "INSTANT Failure";
								commaOrAnd2 = true;
							}
							if (Pending_PercentageINSTANT >= 60.0f) {
								if (commaOrAnd2) {
									alertMessage2 = String.valueOf(alertMessage2) + ", INSTANT Pending";
								} else {
									alertMessage2 = String.valueOf(alertMessage2) + "INSTANT Pending";
								}
							}
							if (Failure_PercentageAIRPAY >= 60.0f) {
								if (commaOrAnd2) {
									alertMessage2 = String.valueOf(alertMessage2) + " , AIRPAY Failure";
								} else {
									alertMessage2 = String.valueOf(alertMessage2) + "AIRPAY Failure";
								}
							}
							if (Pending_PercentageAIRPAY >= 60.0f) {
								if (commaOrAnd2) {
									alertMessage2 = String.valueOf(alertMessage2) + " and AIRPAY Pending";
								} else {
									alertMessage2 = String.valueOf(alertMessage2) + "AIRPAY Pending";
								}
							}
							final SimpleDateFormat parser2 = new SimpleDateFormat("HH:mm:ss");
							final Date starttime2 = parser2.parse("07:30:00");
							final Date Endtime2 = parser2.parse("23:30:00");
							final Date currenttime2 = parser2
									.parse(new SimpleDateFormat("HH:mm:ss").format(new Date()));
							if (currenttime2.after(starttime2) && currenttime2.before(Endtime2)) {
								NewCustomFunctions.insertDataInstantAirpay(timestamp2, AIRPAY_Count, AIRPAY_Pending,
										AIRPAY_Failure, INSTANT_Count, INSTANT_Pending, INSTANT_Failure,
										Pending_PercentageAIRPAY, Failure_PercentageAIRPAY, Pending_PercentageINSTANT,
										Failure_PercentageINSTANT);
								String subject2 = alertMessage2;
								String message2 = NewCustomFunctions.createMessage2(timestamp2, AIRPAY_Count,
										AIRPAY_Pending, AIRPAY_Failure, INSTANT_Count, INSTANT_Pending, INSTANT_Failure,
										Pending_PercentageAIRPAY, Failure_PercentageAIRPAY, Pending_PercentageINSTANT,
										Failure_PercentageINSTANT);
								TestMail.Sendmail1(subject2, message2);
								mailStatus2 = true;
								System.out.println("Mail sent....");
							}
						}
						if (mailStatus2) {
							final Frame frame8 = new Frame();
							frame8.setVisible(true);
							frame8.toFront();
							final Object[] objects3 = {
									String.valueOf(alertMessage2) + " Percentage is Greater than 60 ! " };
							JOptionPane.showMessageDialog(null, objects3, "Alert", -1);
							frame8.setVisible(false);
						}
					}
				}

				else if (actionRDS.equalsIgnoreCase("findRow&click2Lac")) {
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
					// tbody/tr
					try {
						List<WebElement> list = Framework.driver.findElements(By.xpath(propertyValueRDS));
						int size = list.size();
						System.out.println("List Size is === " + size);
						for (int j = 1; j <= size; j++) {
							// gettext from name
							String xpath = "(" + propertyValueRDS + "/td[7])[" + j + "]";
							System.out.println("Get Text Xpath Is == " + xpath);
							String status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
									.getText();
							System.out.println("Get Text Value Is == " + status);

							if (status.equalsIgnoreCase("₹ 2,00,000.00") && status != null && status != "") {
								// ₹ 2,00,000.00
								System.out.println("Actual Value Is == " + status);
								String s1 = "/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
										+ j + "]/td[1]/div/label/i";
								System.out.println("Actual Xpath is == " + s1);
								// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s1))).click();
								Thread.sleep(3000);
								Framework.driver.findElement(By.xpath(
										"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
												+ j + "]/td[1]/div/label/input"))
										.click();
								break;
							}
						}
					} catch (Exception e) {
					}
				}

				else if (actionRDS.equalsIgnoreCase("FindRow_Click")) {

					List<WebElement> trow = Framework.driver.findElements(By.tagName("tr"));
					String toFind = "";
					toFind = dataFieldRDS;
					int i;
					for (i = 1; i <= trow.size(); i++) {
						if (Framework.driver.findElement(By.xpath(
								"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
										+ i + "]/td[3]"))
								.getText().equals(toFind)) {
							break;

						}

					}
					System.out.println("Found at :- " + i);
					Thread.sleep(500);
					WebElement ele = Framework.driver.findElement(By.xpath(
							"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
									+ i + "]/td[1]/div/label/input"));
					((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();", ele);

				}

//				else if (actionRDS.equalsIgnoreCase("FindRow_Click")) {
//
//					List<WebElement> trow = Framework.driver.findElements(By.tagName("tr"));
//					String toFind = "";
//					toFind = dataFieldRDS;
//					int i;
//					for (i = 1; i <= trow.size(); i++) {
//						if (Framework.driver.findElement(By.xpath(
//								"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
//										+ i + "]/td[3]"))
//								.getText().equals(toFind)) {
//							break;
//
//						}
//
//					}
//					System.out.println("Found at :- " + i);
//					Thread.sleep(500);
//					Framework.driver.findElement(By.xpath(
//							"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
//									+ i + "]/td[1]/div/label/input"))
//							.click();
//
//				}

				else if (actionRDS.equalsIgnoreCase("WAITUNTIL")) {
					int waitTIme = Integer.parseInt(dataFieldRDS);
					try {
						WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofSeconds(waitTIme));
						System.out.println("Waiting Time Is  ---------------> " + waitTIme);
						WebElement ele = wait4
								.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));
						if (ele.isDisplayed()) {
							System.out.println("element found");
						} else {
							System.out.println("element not found");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (actionRDS.equalsIgnoreCase("WAITUNTILINVISIBLE")) {
					try {
						WebDriverWait wait4 = new WebDriverWait(Framework.driver,
								Duration.ofSeconds(Integer.parseInt(dataFieldRDS)));
						wait4.until(
								(Function) ExpectedConditions.invisibilityOfElementLocated(By.xpath(propertyValueRDS)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (actionRDS.equalsIgnoreCase("DefaultContent")) {
					Framework.driver.switchTo().defaultContent();
					System.out.println("Switch to Default Content");
				} else if (actionRDS.equalsIgnoreCase("ACCEPTALERT")) {
					try {
						String msg = "";
						Thread.sleep(2000L);
						final WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofMillis(30000L));
						((FluentWait) wait).until((Function) ExpectedConditions.alertIsPresent());
						final Alert alert2 = Framework.driver.switchTo().alert();
						msg = alert2.getText();
						System.out.println("alert is " + msg);
						if (!msg.equals("")) {
							alert2.accept();
						} else {
							System.out.println("alert is not present");
						}
					} catch (Exception e) {
						System.out.println("Catch 5");
						e.printStackTrace();
					}
				} else if (actionRDS.equalsIgnoreCase("RB_ENTER1")) {
					Robot rb7 = new Robot();
					rb7.keyPress(10);
					rb7.keyRelease(10);
					if (controlRDS.equalsIgnoreCase("T")) {
						Framework.tStartTime = Monitoring_FrameWork.StartTime();
					}
				} else if (actionRDS.equalsIgnoreCase("ALERT")) {
					try {
						Thread.sleep(2000L);
						final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(30000L));
						wait4.until((Function) ExpectedConditions.alertIsPresent());
						final Alert alert = Framework.driver.switchTo().alert();
						alert.accept();
					} catch (Exception e) {
						System.out.println("Alert is not present");
						e.printStackTrace();
					}
				} else if (actionRDS.equalsIgnoreCase("RB_ENTER")) {
					Robot rb7 = new Robot();
					rb7.keyPress(10);
					rb7.keyRelease(10);
				} else if (actionRDS.equalsIgnoreCase("RB_C")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(67);
					rb7.keyRelease(67);
				} else if (actionRDS.equalsIgnoreCase("RB_ESCAPE")) {
					final Robot robot = new Robot();
					robot.keyPress(27);
					robot.keyRelease(27);
				} else if (actionRDS.equalsIgnoreCase("RB_TAB")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(9);
					rb7.keyRelease(9);
				} else if (actionRDS.equalsIgnoreCase("RB_SPACE")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(32);
					rb7.keyRelease(32);
				} else if (actionRDS.equalsIgnoreCase("RB_SHIFT")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(16);
					rb7.keyRelease(16);
				} else if (actionRDS.equalsIgnoreCase("GETCAPCHABOD")) {
					final String cap = Framework.driver.findElement(By.xpath(propertyValueRDS)).getText();
					System.out.println(cap);
					Thread.sleep(3000L);
					Framework.driver.findElement(By.id("txtCaptcha")).sendKeys(new CharSequence[] { cap });
				} else if (actionRDS.equalsIgnoreCase("RB_VALUE")) {
					final Robot rb2 = new Robot();
					rb2.keyPress(84);
					rb2.keyRelease(84);
					rb2.keyPress(69);
					rb2.keyRelease(69);
					rb2.keyPress(83);
					rb2.keyRelease(83);
					rb2.keyPress(84);
					rb2.keyRelease(84);
				} else if (actionRDS.equalsIgnoreCase("RB_TRAN")) {
					final Robot rb2 = new Robot();
					rb2.keyPress(16);
					rb2.keyPress(65);
					rb2.keyRelease(65);
					rb2.keyRelease(16);
					rb2.keyPress(85);
					rb2.keyRelease(85);
					rb2.keyPress(71);
					rb2.keyRelease(71);
					rb2.keyPress(16);
					rb2.keyPress(50);
					rb2.keyRelease(50);
					rb2.keyRelease(16);
					rb2.keyPress(50);
					rb2.keyRelease(50);
					rb2.keyPress(48);
					rb2.keyRelease(48);
					rb2.keyPress(50);
					rb2.keyRelease(50);
					rb2.keyPress(50);
					rb2.keyRelease(50);
				} else if (actionRDS.equalsIgnoreCase("RB_TRAN1")) {
					NewCustomFunctions.rb_TRAN1();
				} else if (actionRDS.equalsIgnoreCase("RB_TRAN2")) {
					NewCustomFunctions.rb_TRAN2();
				} else if (actionRDS.equalsIgnoreCase("PAYU&PAYTM1")) {
					try {
						Fillo f5 = new Fillo();
						com.codoid.products.fillo.Connection c = f5.getConnection(Framework.datasheetspath);
						if (dataFieldRDS.equalsIgnoreCase("PayU")) {
							final String s41 = "Update Sheet2 set Payment = 'PayTM' where Payment = '" + dataFieldRDS
									+ "'";
							String s42 = "Update Sheet1 set RunStatus='Y' where PageName='Recommended Add-on Page(PayU)'";
							System.out.println(s42);
							c.executeUpdate(s42);
							s42 = "Update Sheet1 set RunStatus='Y' where PageName='Payment Method Page(PayU)'";
							System.out.println(s42);
							c.executeUpdate(s42);
							s42 = "Update Sheet1 set RunStatus='Y' where PageName= 'Payment Gateway Page(PayU)'";
							System.out.println(s42);
							c.executeUpdate(s42);
							String s43 = "Update Sheet1 set RunStatus='N' where PageName='Recommended Add-on Page(PayTM)'";
							System.out.println(s43);
							c.executeUpdate(s43);
							s43 = "Update Sheet1 set RunStatus='N' where PageName='Payment Method Page(PayTM)'";
							System.out.println(s43);
							c.executeUpdate(s43);
							s43 = "Update Sheet1 set RunStatus='N' where PageName= 'Payment Gateway Page(PayTM)'";
							System.out.println(s43);
							c.executeUpdate(s43);
							c.executeUpdate(s41);
							Thread.sleep(2000L);
						} else if (dataFieldRDS.equalsIgnoreCase("PayTM")) {
							final String s41 = "Update Sheet2 set Payment = 'PayU' where Payment = '" + dataFieldRDS
									+ "'";
							String s42 = "Update Sheet1 set RunStatus='Y' where PageName='Recommended Add-on Page(PayTM)'";
							System.out.println(s42);
							c.executeUpdate(s42);
							s42 = "Update Sheet1 set RunStatus='Y' where PageName='Payment Method Page(PayTM)'";
							System.out.println(s42);
							c.executeUpdate(s42);
							s42 = "Update Sheet1 set RunStatus='Y' where PageName='Payment Gateway Page(PayTM)'";
							System.out.println(s42);
							c.executeUpdate(s42);
							String s43 = "Update Sheet1 set RunStatus='N' where PageName='Recommended Add-on Page(PayU)'";
							c.executeUpdate(s43);
							System.out.println(s43);
							s43 = "Update Sheet1 set RunStatus='N' where PageName='Payment Method Page(PayU)'";
							c.executeUpdate(s43);
							System.out.println(s43);
							s43 = "Update Sheet1 set RunStatus='N' where PageName= 'Payment Gateway Page(PayU)'";
							c.executeUpdate(s43);
							System.out.println(s43);
							c.executeUpdate(s41);
							System.out.println(s41);
							Thread.sleep(2000L);
							c.close();

						}
					} catch (Exception ex27) {
					}
				}
				if (actionRDS.equalsIgnoreCase("SIKULIUP")) {
					Screen s44 = new Screen();
					Thread.sleep(1000L);
					s44.wheel(Button.WHEEL_UP, 10);
				}

				if (actionRDS.equalsIgnoreCase("robotFileUpload")) {

					Robot rb = new Robot();
					System.out.println("file path ==== " + propertyValueRDS);
					StringSelection s = new StringSelection(propertyValueRDS);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

					// press Contol+V for pasting
					rb.keyPress(KeyEvent.VK_CONTROL);
					rb.keyPress(KeyEvent.VK_V);
					// release Contol+V for pasting
					rb.keyRelease(KeyEvent.VK_CONTROL);
					rb.keyRelease(KeyEvent.VK_V);
					Thread.sleep(500);
					// for pressing and releasing Enter
					rb.keyPress(KeyEvent.VK_ENTER);
					rb.keyRelease(KeyEvent.VK_ENTER);

					Thread.sleep(2000);
				}

				if (actionRDS.equalsIgnoreCase("SIKULIDOWN")) {
					final Screen s44 = new Screen();
					Thread.sleep(1000L);
					s44.wheel(Button.WHEEL_DOWN, 10);
				} else if (actionRDS.equalsIgnoreCase("SIKULI")) {
					final Screen s45 = new Screen();
					Thread.sleep(1000L);
					s45.wheel(1, 5);
				} else if (actionRDS.equalsIgnoreCase("NewFolio")) {
					for (int k2 = 0; k2 < 100; ++k2) {
						System.out.println("-------- i " + k2);
						try {
							if (Framework.driver
									.findElement(By.xpath("//*[@id='folioSchemes']/div[" + k2 + "]/div/a/div/div/div"))
									.isDisplayed()) {
								System.out.println("-------- i321---- " + k2);
								Framework.driver
										.findElement(
												By.xpath("//*[@id='folioSchemes']/div[" + k2 + "]/div/a/div/div/div"))
										.click();
								break;
							}
						} catch (Exception ex30) {
						}
					}
				} else if (actionRDS.equalsIgnoreCase("DATAPIC")) {
					System.out.println("----in date picker--");
					final String imagepath = String
							.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.homedir)))) + "\\Images\\";
					System.out.println("----in date picker--" + imagepath);
					Thread.sleep(2000L);
					final Screen lb = new Screen();
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Nov18.png")).click();
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Year18.png")).click();
					Thread.sleep(1000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Left.png")).click();
					Thread.sleep(2000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Left.png")).click();
					Thread.sleep(2000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Y1994.PNG")).click();
					Thread.sleep(1000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Jan.png")).click();
					Thread.sleep(1000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "12date.png")).click();
				} else if (actionRDS.equalsIgnoreCase("CHROMELAUNCH")) {
					System.out.println("----in date picker--");
					final String imagepath = String
							.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.homedir)))) + "\\Images\\";
					System.out.println("----in date picker--" + imagepath);
					Thread.sleep(2000L);
					final Screen lb = new Screen();
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Nov18.png")).click();
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Year18.png")).click();
					Thread.sleep(1000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Left.png")).click();
					Thread.sleep(2000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Left.png")).click();
					Thread.sleep(2000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Y1994.PNG")).click();
					Thread.sleep(1000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Jan.png")).click();
					Thread.sleep(1000L);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "12date.png")).click();
				} else if (actionRDS.equalsIgnoreCase("ACCOUNT_NO")) {
					final Robot rb7 = new Robot();
					final StringSelection ACC = new StringSelection("34020310000673");
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ACC, null);
					rb7.keyPress(17);
					rb7.keyPress(86);
					rb7.keyRelease(17);
					rb7.keyRelease(86);
				} else if (actionRDS.equalsIgnoreCase("UTKARSH_NICKNAME")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(9);
					rb7.keyRelease(9);
					System.out.println("Enter into the NickName field");
					final Random value5 = new Random();
					final String alphabet2 = "ABCDEFGHIJKLMNOPQRSTUVWSYZ";
					final char[] x4 = new char[6];
					for (int j2 = 0; j2 < 6; ++j2) {
						x4[j2] = "ABCDEFGHIJKLMNOPQRSTUVWSYZ"
								.charAt(value5.nextInt("ABCDEFGHIJKLMNOPQRSTUVWSYZ".length()));
					}
					final String xyz2 = new String(x4);
					System.out.println(xyz2);
					final StringSelection ACC2 = new StringSelection(xyz2);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ACC2, null);
					rb7.keyPress(17);
					rb7.keyPress(86);
					rb7.keyRelease(17);
					rb7.keyRelease(86);
				} else if (actionRDS.equalsIgnoreCase("IFSC_CLICK")) {
					final Robot rb7 = new Robot();
					final WebElement elem = Framework.driver.findElement(By.className("x-form-cb-label"));
					final int width = elem.getSize().getWidth();
					final Actions act3 = new Actions(Framework.driver);
					act3.moveToElement(elem).moveByOffset(width / 2 - 2, 0).click().perform();
					Thread.sleep(1000L);
					rb7.keyPress(9);
					rb7.keyRelease(9);
					Thread.sleep(1000L);
					final StringSelection IFSC = new StringSelection("SRCB0000402");
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(IFSC, null);
					rb7.keyPress(17);
					rb7.keyPress(86);
					rb7.keyRelease(17);
					rb7.keyRelease(86);
					Thread.sleep(2000L);
					rb7.keyPress(9);
					rb7.keyRelease(9);
					final StringSelection BeneficiaryName = new StringSelection("Tester");
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(BeneficiaryName, null);
					rb7.keyPress(17);
					rb7.keyPress(86);
					rb7.keyRelease(17);
					rb7.keyRelease(86);
					Thread.sleep(2000L);
				} else if (actionRDS.equalsIgnoreCase("RB_SHIFTTAB")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(16);
					Thread.sleep(1000L);
					rb7.keyPress(9);
					rb7.keyRelease(9);
					Thread.sleep(1000L);
					rb7.keyRelease(16);
					Thread.sleep(1000L);
					rb7.keyPress(16);
					rb7.keyPress(9);
					rb7.keyRelease(9);
					Thread.sleep(1000L);
					rb7.keyRelease(16);
					Thread.sleep(1000L);
				} else if (actionRDS.equalsIgnoreCase("RB_ALT")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(18);
					rb7.keyRelease(18);
				} else if (actionRDS.equalsIgnoreCase("RB_CTRL")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(17);
					rb7.keyRelease(17);
				} else if (actionRDS.equalsIgnoreCase("RB_CTRL_V")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(17);
					rb7.keyPress(86);
					rb7.keyRelease(86);
					rb7.keyRelease(17);
					rb7.keyPress(10);
					rb7.keyRelease(10);
				} else if (actionRDS.equalsIgnoreCase("RB_UPARROW")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(38);
					rb7.keyRelease(38);
				} else if (actionRDS.equalsIgnoreCase("RB_PAGEDOWN")) {
					final Robot robot = new Robot();
					robot.keyPress(34);
					robot.keyRelease(34);
				} else if (actionRDS.equalsIgnoreCase("RB_DOWNARROW")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(40);
					rb7.keyRelease(40);
				} else if (actionRDS.equalsIgnoreCase("RB_RIGHTARROW")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(39);
					rb7.keyRelease(39);
					Thread.sleep(500L);
				} else if (actionRDS.equalsIgnoreCase("RB_PAGEUP")) {
					final Robot rb7 = new Robot();
					rb7.keyPress(33);
					rb7.keyRelease(33);
				} else if (actionRDS.equalsIgnoreCase("RB_RIGHT")) {
					NewCustomFunctions.rb_right();
				} else if (actionRDS.equalsIgnoreCase("RB_LEFT")) {
					NewCustomFunctions.rb_left();
				} else if (actionRDS.equalsIgnoreCase("DeleteBeneficiary")) {
					final String imagepath = String
							.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.homedir)))) + "/Image/";
					final Screen lb = new Screen();
					lb.wait((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "AccountNumber.png"), (double) Functions.deftime);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "AccountNumber.png")).doubleClick();
					Thread.sleep(3000L);
				} else if (actionRDS.equalsIgnoreCase("Proceed")) {
					final String imagepath = String
							.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.homedir)))) + "/Image/";
					final Screen lb = new Screen();
					lb.wait((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Proceed.png"), (double) Functions.deftime);
					lb.find((Object) (String.valueOf(String.valueOf(String.valueOf(String.valueOf(imagepath))))
							+ "Proceed.png")).doubleClick();
					Thread.sleep(3000L);
				} else if (actionRDS.contains("WAIT(")) {
					try {

						Long time = Long.valueOf(actionRDS.split("[(//)]")[1]);

						Thread.sleep(time);

						if (controlRDS.equalsIgnoreCase("T")) {
							Framework.tStartTime = Monitoring_FrameWork.StartTime();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (actionRDS.equalsIgnoreCase("SWITCHSMALLWINDOW")) {
					final String urlwin = Framework.parenwindow;
					final Set<String> chilwin = (Set<String>) Framework.driver.getWindowHandles();
					System.out.println("Total Window " + chilwin.size());
					System.out.println("driver is " + urlwin);
					for (final String currentwin : chilwin) {
						if (!currentwin.equalsIgnoreCase(urlwin)) {
							System.out.println("We are in SwitchSmallWindow Function-------------------");
							System.out.println(String.valueOf(String.valueOf(urlwin)) + "\n" + currentwin);
							Framework.driver.switchTo().window(currentwin);
							final String urlwin2 = Framework.driver.getWindowHandle();
							Thread.sleep(2000L);
							System.out.println("driver is1 " + urlwin2);
							System.out.println("Current window Title is ---->  " + Framework.driver.getTitle());
						}
						System.out.println("Inside for loop...................");
					}
					System.out.println("Outside for loop...................");
				} else if (actionRDS.equalsIgnoreCase("NewBrowser")) {
					if (Framework.browserToOpen.equalsIgnoreCase("chrome")) {
						final String osname = Framework.getosname();
						final String osarch = Framework.getosarch();
						final String driverpath = Framework.homedir + "/Drivers/" + osname + "/"
								+ Framework.browserToOpen + "/" + osarch;
						String path4 = driverpath + "/chromedriver.exe";
						if (osname.equalsIgnoreCase("Windows")) {
							path4 = driverpath + "/chromedriver.exe";
						} else {
							path4 = driverpath + "/chromedriver";
						}
						final ChromeOptions options = new ChromeOptions();
						options.addArguments(new String[] { "--disable-notifications" });
						options.addArguments(new String[] { "test-type" });
						options.addArguments(new String[] { "allow-running-insecure-content" });
						options.addArguments(new String[] { "--disable-extentions" });
						options.addArguments(new String[] { "disable-infobars" });
						options.addArguments(new String[] { "disable-captcha" });
						options.addArguments(new String[] { "--remote-allow-origins=*" });
						options.setCapability("acceptInsecureCerts", true);
						final Map<String, Object> prefs = new HashMap<String, Object>();
						prefs.put("profile.default_content_settings.popups", 0);
						prefs.put("download.prompt_for_download", false);
						options.setExperimentalOption("prefs", (Object) prefs);
						System.setProperty("webdriver.chrome.driver", path4);
						Framework.driver = (WebDriver) new ChromeDriver(options);
						Framework.driver.manage().timeouts().implicitlyWait(60L, TimeUnit.SECONDS);
						Framework.driver.manage().window().maximize();
						// wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
						Framework.browsersts = 1;
					} else if (Framework.browserToOpen.equalsIgnoreCase("FF")) {
						final String osname = Framework.getosname();
						final String osarch = Framework.getosarch();
						final String driverpath = Framework.homedir + "/Drivers/" + osname + "/"
								+ Framework.browserToOpen + "/" + osarch;
						String path5 = driverpath + "/geckodriver.exe";
						if (osname.equalsIgnoreCase("Windows")) {
							path5 = driverpath + "/geckodriver.exe";
						} else {
							path5 = driverpath + "/geckodriver";
						}
					} else if (Framework.browserToOpen.equalsIgnoreCase("Edge")) {
						final String osname = Framework.getosname();
						final String osarch = Framework.getosarch();
						final String driverpath = Framework.homedir + "/Drivers/" + osname + "/"
								+ Framework.browserToOpen + "/" + osarch;
						String path5 = driverpath + "/chromedriver.exe";
						if (osname.equalsIgnoreCase("Windows")) {
							path5 = driverpath + "/msedgedriver.exe";
						} else {
							path5 = driverpath + "/msedgedriver";
						}
						final EdgeOptions options2 = new EdgeOptions();
						options2.addArguments(new String[] { "--remote-allow-origins=*" });
						System.setProperty("webdriver.edge.driver", path5);
						Framework.driver = (WebDriver) new EdgeDriver(options2);
						Framework.driver.manage().window().maximize();
						Framework.driver.manage().timeouts().implicitlyWait(60L, TimeUnit.SECONDS);
						Framework.browsersts = 1;
					}
				} else if (actionRDS.equalsIgnoreCase("Closewindow")) {
					Framework.driver.close();
				} else if (actionRDS.equalsIgnoreCase("ClosewindowJS")) {
					((JavascriptExecutor) Framework.driver).executeScript("window.close();", new Object[0]);
				} else if (actionRDS.equalsIgnoreCase("ClosewindowAC")) {
					final Actions actions2 = new Actions(Framework.driver);
					actions2.keyDown((CharSequence) Keys.CONTROL).sendKeys(new CharSequence[] { "w" })
							.keyUp((CharSequence) Keys.CONTROL).perform();
				} else if (actionRDS.equalsIgnoreCase("quit")) {
					Framework.driver.quit();

				} else if (actionRDS.equalsIgnoreCase("Exit")) {

					Framework.scriptEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
					Framework.flag = false;
					System.out.println("\n" + divider);
					if (Monitoring_FrameWork.logoutFlag == true || Framework.exitFlag == true) {

						DB_Tables.updateEndTimeRunTimeInstance("terminated", scriptEndTime, applicationId,
								runTimeInstanceId);
//						DB_Tables.updateEndTimeReportsRuntime("terminated", Framework.scriptEndTime, Framework.applicationId,
//								Framework.reportRunTimeInstanceId);
						System.exit(1);
					} else {
						DB_Tables.updateEndTimeRunTimeInstance("success", scriptEndTime, applicationId,
								runTimeInstanceId);
//						DB_Tables.updateEndTimeReportsRuntime("success", Framework.scriptEndTime, Framework.applicationId,
//								Framework.reportRunTimeInstanceId);
						Framework.driver.quit();
						System.exit(0);
					}

				} else if (actionRDS.equalsIgnoreCase("REFRESH")) {
					Framework.driver.navigate().refresh();
				} else if (actionRDS.equalsIgnoreCase("SWITCH_WINDOW")) {
					String urlwin = Framework.parenwindow;
					Set<String> chilwin = (Set<String>) Framework.driver.getWindowHandles();
					System.out.println("Total Window " + chilwin.size());
					System.out.println("driver is " + urlwin);
					for (final String currentwin : chilwin) {
						if (!currentwin.equalsIgnoreCase(urlwin)) {
							System.out.println(String.valueOf(String.valueOf(String.valueOf(String.valueOf(urlwin))))
									+ "\n" + currentwin);
							Framework.driver.switchTo().window(currentwin);
							final String urlwin3 = Framework.driver.getWindowHandle();
							Thread.sleep(3000L);
							System.out.println("driver is1 " + urlwin3);
							Framework.driver.manage().window().maximize();
							System.out.println("Current window Title is ---->  " + Framework.driver.getTitle());
						}
					}
				} else if (actionRDS.equalsIgnoreCase("FRAME_SIZE")) {
					System.out.println("We are in FrameSize method====================================");
					final int size3 = Framework.driver.findElements(By.tagName("iframe")).size();
					System.out.println("Size of the frame is ----------------" + size3);
					for (int l3 = 0; l3 == size3; ++l3) {
						Framework.driver.switchTo().frame(l3);
						final int total2 = Framework.driver.findElements(By.xpath("propertyValueRDS")).size();
						System.out.println(total2);
					}
				} else if (actionRDS.equalsIgnoreCase("SYSTEM_DATE")) {
					final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					final LocalDateTime now4 = LocalDateTime.now();
					final LocalDateTime then = now4.minusDays(2L);
					System.out.println(String.format("Now:  %s\nThen: %s", now4.format(format), then.format(format)));
				} else if (actionRDS.equalsIgnoreCase("SET_PARENTWINDOW")) {
					Framework.parenwindow = Framework.driver.getWindowHandle();
				} else if (actionRDS.equalsIgnoreCase("SWITCH_PARENTWINDOW")) {
					Framework.driver.switchTo().window(Framework.parenwindow);
					Framework.parenwindow = Framework.driver.getWindowHandle();
				} else if (actionRDS.equalsIgnoreCase("DOB")) {
					final WebElement dobdate = Framework.driver
							.findElement(By.xpath("//*[@id='nomineedob']/div[1]/div/span/span[1]/span"));
					dobdate.click();
					final WebElement datelist = Framework.driver
							.findElement(By.xpath("//*[@id='commonInitializer']/span/span/span[2]/ul"));
					for (final WebElement date11 : datelist.findElements(By.tagName("li"))) {
						if (date11.getText().equalsIgnoreCase("1")) {
							date11.click();
							break;
						}
					}
					Thread.sleep(2000L);
					Framework.driver.findElement(By.xpath("//*[@id='nomineedob']/div[2]/div/span/span[1]/span"))
							.click();
					final WebElement monthlist = Framework.driver
							.findElement(By.xpath("//*[@id='commonInitializer']/span/span/span[2]/ul"));
					for (final WebElement month : monthlist.findElements(By.tagName("li"))) {
						if (month.getText().equalsIgnoreCase("Jan")) {
							month.click();
							break;
						}
					}
					Framework.driver.findElement(By.xpath("//*[@id='nomineedob']/div[3]/div/span/span[1]/span"))
							.click();
					final WebElement yearlist = Framework.driver
							.findElement(By.xpath("//*[@id='commonInitializer']/span/span/span[2]/ul"));
					for (final WebElement year5 : yearlist.findElements(By.tagName("li"))) {
						if (year5.getText().equalsIgnoreCase("1998")) {
							year5.click();
							break;
						}
					}
				} else if (actionRDS.equalsIgnoreCase("STARTTIME") && controlRDS.equalsIgnoreCase("T")) {
					Framework.tStartTime = Monitoring_FrameWork.StartTime();
				}
			}
			if (actionRDS.equalsIgnoreCase("findRow&click2")) {
				final WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				final List<WebElement> list = (List<WebElement>) Framework.driver
						.findElements(By.xpath(propertyValueRDS));
				final int size4 = list.size();
				System.out.println("List Size is === " + size4);
				for (int j3 = 1; j3 <= size4; ++j3) {
					final String xpath8 = "(" + propertyValueRDS + "/td[7])[" + j3 + "]";
					System.out.println("Get Text Xpath Is == " + xpath8);
					final String status7 = ((WebElement) wait3
							.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath8))))
							.getText();
					System.out.println("Get Text Value Is == " + status7);
					if (status7.equalsIgnoreCase("\u20b9 2,00,000.00") && status7 != null && status7 != "") {
						System.out.println("Actual Value Is == " + status7);
						final String s46 = "(" + propertyValueRDS + "/td[1])[" + j3 + "]";
						((WebElement) wait3
								.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(s46))))
								.click();
						break;
					}
				}
			}
			if (actionRDS.equalsIgnoreCase("get_claimId")) {
				final WebDriverWait wait3 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
				final List<WebElement> list = (List<WebElement>) Framework.driver
						.findElements(By.xpath(propertyValueRDS));
				final int size4 = list.size();
				System.out.println("List Size is === " + size4);
				String claimId = null;
				for (int j4 = 1; j4 <= size4; ++j4) {
					final String xpath = "(" + propertyValueRDS + "/td[9])[" + j4 + "]";
					System.out.println("Get Text Xpath Is == " + xpath);
					final String status8 = ((WebElement) wait3
							.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))))
							.getText();
					System.out.println("Get Text Value Is == " + status8);
					if (status8.equalsIgnoreCase("Commercial Approved") && status8 != null && status8 != "") {
						final String s47 = "(" + propertyValueRDS + "/td[2])[" + j4 + "]";
						claimId = ((WebElement) wait3
								.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(s47))))
								.getText();
						System.out.println("Claim Id Is  == " + claimId);
						break;
					}
				}
				try {
					if (claimId != null || !claimId.equalsIgnoreCase("")) {
						((WebElement) wait3.until(
								(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS))))
								.sendKeys(new CharSequence[] { claimId });
					} else {
						final Frame frame9 = new Frame();
						frame9.setVisible(false);
						frame9.toFront();
						final String activationcode3 = JOptionPane.showInputDialog(frame9,
								"Enter the result for verification");
						((WebElement) wait3.until(
								(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS))))
								.sendKeys(new CharSequence[] { activationcode3 });
					}
				} catch (Exception e23) {
					System.out.println("=== Claim Id Is Null ===");
					final Frame frame10 = new Frame();
					frame10.setVisible(false);
					frame10.toFront();
					final String activationcode4 = JOptionPane.showInputDialog(frame10,
							"Enter the result for verification");
					((WebElement) wait3
							.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS))))
							.sendKeys(new CharSequence[] { activationcode4 });
				}
			}

			if (actionRDS.equalsIgnoreCase("GetReferenceId")) {
				referenceid = webElementVal.getText();
				System.out.println("Reference id we get " + referenceid);

			}
			if (actionRDS.equalsIgnoreCase("sendReferenceId")) {
				char[] ch = new char[referenceid.length()];
				for (int k = 0; k < referenceid.length(); ++k) {
					ch[k] = referenceid.charAt(k);
					webElementVal.sendKeys(new CharSequence[] { String.valueOf(ch[k]) });
					Thread.sleep(1000);
				}
				System.out.println("Reference id sended successfully");
			}

			if (actionRDS.contains("VERIFYMOVETO(")) {
				final String testcase = actionRDS.split("\\(")[1].split("\\)")[0];
				if (objectTypeRDS.equalsIgnoreCase("stepno")) {
					Framework.Srno = Framework.recordsetRDS.getField("Srno");
					try {
						final WebDriverWait wait5 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
						final WebElement xpath9 = (WebElement) wait5.until(
								(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
						if (xpath9.isDisplayed()) {
							System.out.println("displayed");
						} else {
							while (!Framework.Srno.equals(testcase)) {
								Framework.recordsetRDS.moveNext();
								Framework.Srno = Framework.recordsetRDS.getField("Srno");
							}
						}
					} catch (Exception e4) {
						System.out.println("exception block");
						while (!Framework.Srno.equals(testcase)) {
							Framework.recordsetRDS.moveNext();
							Framework.Srno = Framework.recordsetRDS.getField("Srno");
						}
						Framework.recordsetRDS.movePrevious();
					}
				}
			}

			if (actionRDS.equalsIgnoreCase("SKIPPAGES")) {

				Framework.pagename = Framework.recordsetRDS.getField("PageName");
				String PageToSkip = dataFieldRDS;
				try {
					WebDriverWait wait5 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
					WebElement xpath9 = (WebElement) wait5.until(
							(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
					if (xpath9.isDisplayed()) {
						System.out.println("=== Pages Displayed === ");
						while (!Framework.pagename.equals(PageToSkip)) {
							Framework.recordsetRDS.moveNext();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
						}
						Framework.recordsetRDS.movePrevious();

					} else {

					}
				} catch (Exception e4) {
					System.out.println("In The Exception Block Pages Not Displayed");
					while (!Framework.pagename.equals(PageToSkip)) {
						Framework.recordsetRDS.moveNext();
						Framework.pagename = Framework.recordsetRDS.getField("PageName");
					}
					Framework.recordsetRDS.movePrevious();
				}

			} else if (actionRDS.equalsIgnoreCase("verifySKIPPAGES")) {
				System.out.println("In verifySKIPPAGES");
				LocalTime localTime = LocalTime.now();
				if (localTime.getHour() >= 23 || localTime.getHour() < 8) {
					System.out.println("Current time to skip the pages");
					while (!Framework.pagename.equals(dataFieldRDS)) {
						Framework.recordsetRDS.moveNext();
						Framework.pagename = Framework.recordsetRDS.getField("PageName");
					}
					Framework.recordsetRDS.movePrevious();
				} else {
					System.out.println("In this time all pages should run ");
				}
			} else if (actionRDS.equalsIgnoreCase("verifyToRunPages")) {
				NewCustomFunctions.verifyToRunPages(dataFieldRDS);
			}

			if (actionRDS.equalsIgnoreCase("OTP_READ_SIKULI")) {
				System.out.println("//////////In OTP_READ");
				String OTP2 = DB_Read_OTP.readotp(dataFieldRDS);
				System.out.println("OTP_READ====" + OTP2);
				if (OTP2 == null || OTP2.equalsIgnoreCase("")) {
					OTP2 = DB_Read_OTP.readotp(dataFieldRDS);
					System.out.println("OTP_READ====" + OTP2);
				}
				Screen s48 = new Screen();
				s48.wait((Object) propertyValueRDS, 60.0);
				s48.exists((Object) propertyValueRDS).click();
				s48.type(OTP2);
				Date date12 = new Date();
				SimpleDateFormat dft2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Framework.OTPcurrentTime = dft2.format(date12);
				System.out.println(Framework.OTPcurrentTime);
			}
			if (actionRDS.equalsIgnoreCase("OTP_READ_ALL_SIKULI")) {
				int time3 = 30;
				try {
					FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					String secondValue1 = Functions.pro.getProperty("secondValue");
					int secondValue2 = Integer.parseInt(secondValue1);
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In OTP_READ_ALL");
					String OTP3 = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
					System.out.println("OTP_READ====" + OTP3);
					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
						OTP3 = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
						System.out.println("OTP_READ====" + OTP3);
					}
					Screen s49 = new Screen();
					s49.wait((Object) propertyValueRDS, 60.0);
					s49.exists((Object) propertyValueRDS).click();
					s49.type(OTP3);
					Date date14 = new Date();
					SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Framework.OTPcurrentTime = dft3.format(date14);
					System.out.println(Framework.OTPcurrentTime);
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
			if (actionRDS.equalsIgnoreCase("OTP_READ")) {
				System.out.println("//////////In OTP_READ");
				String OTP2 = DB_Read_OTP.readotp(dataFieldRDS);
				System.out.println("OTP_READ====" + OTP2);
				if (OTP2 == null || OTP2.equalsIgnoreCase("")) {
					OTP2 = DB_Read_OTP.readotp(dataFieldRDS);
					System.out.println("OTP_READ====" + OTP2);
				}
				webElementVal.clear();
				final JavascriptExecutor executor3 = (JavascriptExecutor) Framework.driver;
				executor3.executeScript("arguments[0].click();", new Object[] { webElementVal });
				webElementVal.sendKeys(new CharSequence[] { OTP2 });
				final Date date15 = new Date();
				final SimpleDateFormat dft4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Framework.OTPcurrentTime = dft4.format(date15);
				System.out.println(Framework.OTPcurrentTime);
			}
			if (actionRDS.contains("OTP_READ_NEW(")) {
				int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					String secondValue1 = Functions.pro.getProperty("secondValue");
					int secondValue2 = Integer.parseInt(secondValue1);
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read New");
					String makerMobile = Functions.pro.getProperty("otp.makerMobileNo");
					String checkerMobile = Functions.pro.getProperty("otp.checkerMobileNo");
					String userMobile;
					if (objectTypeRDS.equalsIgnoreCase("Maker")) {
						userMobile = makerMobile;
					} else {
						userMobile = checkerMobile;
					}
					System.out.println("//////////In OTP_READ_NEW");
					String OTP4 = DB_Read_OTP.readotp1(userMobile, dataFieldRDS, time3);
					System.out.println("OTP_READ====" + OTP4);
					if (OTP4 == null || OTP4.equalsIgnoreCase("")) {
						OTP4 = DB_Read_OTP.readotp1(userMobile, dataFieldRDS, time3);
						System.out.println("OTP_READ====" + OTP4);
					}
					webElementVal.clear();
					final JavascriptExecutor executor4 = (JavascriptExecutor) Framework.driver;
					executor4.executeScript("arguments[0].click();", new Object[] { webElementVal });
					webElementVal.sendKeys(new CharSequence[] { OTP4 });
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}

			if (actionRDS.equalsIgnoreCase("clearHistory")) {
				System.out.println("----------------In clearHistory ----------------");
				Framework.driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("document.querySelector(\"settings-ui\").shadowRoot\n"
						+ "    .querySelector(\"settings-main\").shadowRoot\n"
						+ "    .querySelector(\"settings-basic-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-privacy-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot\n"
						+ "    .querySelector(\"cr-button[id='clearButton']\").click();");
				Thread.sleep(2000);
//				
//				document.querySelector("settings-ui").shadowRoot
//				  .querySelector("settings-main").shadowRoot
//				  .querySelector("settings-basic-page").shadowRoot
//				  .querySelector("settings-privacy-page").shadowRoot
//				  .querySelector("settings-clear-browsing-data-dialog").shadowRoot
//				  .querySelector("cr-button#clearButton").click();

			} else if (actionRDS.equalsIgnoreCase("clearHistory140")) {
				System.out.println("----------------In clearHistory 140 Version ----------------");
				Framework.driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript(
						"document.querySelector(\"settings-ui\").shadowRoot.querySelector(\"settings-main\").shadowRoot.querySelector(\"settings-privacy-page-index\").shadowRoot.querySelector(\"settings-basic-page\").shadowRoot.querySelector(\"settings-privacy-page\").shadowRoot.querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot.querySelector(\"#clearButton\").click();");
				Thread.sleep(2000);
			}

			else if (actionRDS.equalsIgnoreCase("clearHistory143")) {
				System.out.println("----------------In clearHistory 143 Version ----------------");
				Framework.driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript(
						"document.querySelector(\"settings-ui\").shadowRoot.querySelector(\"settings-main\").shadowRoot.querySelector(\"settings-privacy-page-index\").shadowRoot.querySelector(\"settings-privacy-page\").shadowRoot.querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot.querySelector(\"#clearButton\").click();");
				Thread.sleep(2000);
			}

			else if (actionRDS.equalsIgnoreCase("clearHistory145")) {
				System.out.println("----------------In clearHistory 145 Version ----------------");
				Framework.driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript(
						"document.querySelector(\"settings-ui\").shadowRoot.querySelector(\"settings-main\").shadowRoot.querySelector(\"settings-privacy-page-index\").shadowRoot.querySelector(\"settings-privacy-page\").shadowRoot.querySelector(\"settings-clear-browsing-data-dialog-v2\").shadowRoot.querySelector(\"#deleteButton\").click();");
				Thread.sleep(2000);
			} else if (actionRDS.equalsIgnoreCase("clearHistoryNew")) {
				System.out.println("----------------In clearHistory ----------------");
				Framework.driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				final JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("document.querySelector(\"settings-ui\").shadowRoot\n"
						+ "    .querySelector(\"settings-main\").shadowRoot\n"
						+ "    .querySelector(\"settings-basic-page-index\").shadowRoot\n"
						+ "    .querySelector(\"settings-privacy-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot\n"
						+ "    .querySelector(\"cr-button[id='clearButton']\").click();");
				Thread.sleep(2000);

			} else if (actionRDS.equalsIgnoreCase("deleteHistory")) {
				System.out.println("----------------In deleteHistory ----------------");
				Framework.driver.get("chrome://settings/clearBrowserData");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("document.querySelector(\"settings-ui\").shadowRoot\n"
						+ "    .querySelector(\"settings-main\").shadowRoot\n"
						+ "    .querySelector(\"settings-basic-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-privacy-page\").shadowRoot\n"
						+ "    .querySelector(\"settings-clear-browsing-data-dialog\").shadowRoot\n"
						+ "    .querySelector(\"cr-button[id='clearBrowsingDataConfirm']\").click();");
				Thread.sleep(2000);

			}
			if (actionRDS.equalsIgnoreCase("jsOpenNewTab")) {
				JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				js.executeScript("window.open()");

			}

			if (actionRDS.equalsIgnoreCase("openNewTab")) {
				Framework.driver.switchTo().newWindow(WindowType.TAB);
			}
			if (actionRDS.equalsIgnoreCase("axisNfcReadOtp")) {

//				String mailId = "apmosys03@gmail.com";
//				String password = "cehl btlj hjiq qymu";
//				String optSubject = "Code for Email Update";
//				String fromMail = "alerts@axisbank.com";

				final FileInputStream fis5 = new FileInputStream(Functions.path2);
				Functions.pro.load(fis5);
				final String mail = Functions.pro.getProperty("otpMail");
				final String password = Functions.pro.getProperty("otpMailPassword");
				String otp = CustomFunctions.axisNfcReadOtp(mail, password, propertyValueRDS.trim(),
						dataFieldRDS.trim());
				Functions.otp1 = otp.charAt(0);
				Functions.otp2 = otp.charAt(1);
				Functions.otp3 = otp.charAt(2);
				Functions.otp4 = otp.charAt(3);
				Functions.otp5 = otp.charAt(4);
				Functions.otp6 = otp.charAt(5);
			}
			if (actionRDS.equalsIgnoreCase("AxisOpenUrl")) {

				String host = "smtp.gmail.com";
				String port = "587";
				String username = dataFieldRDS.split("#")[0];
				String pass = dataFieldRDS.split("#")[1];
				String subject = "Link for login credentials";

				String link = FetchMail.getLinkForAxis(host, username, pass, port, subject);

				((JavascriptExecutor) driver).executeScript("window.open()");
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				System.out.println("Total tab Size is === " + tabs.size());
				int size = tabs.size();
				driver.switchTo().window(tabs.get(size - 1));
				driver.get(link);
				Thread.sleep(1000);
				Framework.tStartTime = Monitoring_FrameWork.StartTime();
			}
			if (actionRDS.equalsIgnoreCase("LICclearHistory98")) {
				String imgPath = System.getProperty("user.dir") + "/Image/";
				Screen s48 = new Screen();
				// ArrayList<String> tabs = new
				// ArrayList<String>(Framework.driver.getWindowHandles());
				// Framework.driver.switchTo().window((String) tabs.get(1));
				//
				// JavascriptExecutor js = (JavascriptExecutor) Framework.driver;
				String link = "chrome://settings/clearBrowserData";
				Framework.driver.get(link);
				try {
					Thread.sleep(3000L);
				} catch (Exception e4) {
					e4.printStackTrace();
				}
				// for time range
				s48.wait((Object) propertyValueRDS, 90.0);
				s48.exists((Object) propertyValueRDS).click();

				// for select all time
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[0], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[0]).click();

				// for select advance option
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[1], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[1]).click();

				// for time range
				s48.wait((Object) propertyValueRDS, 90.0);
				s48.exists((Object) propertyValueRDS).click();

				// for select all time
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[0], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[0]).click();

				// for select 1st box
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

				// for scroll down
				Thread.sleep(1000L);
				s48.wheel(1, 5);

				// for select 2nd box
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

				// for select 3rd box
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

				// for select 4th box
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[2], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[2]).click();

				// for click clear button
				s48.wait((Object) imgPath + dataFieldRDS.split("#")[3], 90.0);
				s48.exists((Object) imgPath + dataFieldRDS.split("#")[3]).click();

				// Framework.driver.close();
				// tabs = new ArrayList<String>(Framework.driver.getWindowHandles());
				// Framework.driver.switchTo().window((String) tabs.get(0));
			}

//			if (actionRDS.contains("OTP_READ_ALL1(")) {
//				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
//				try {
//					final FileInputStream fis2 = new FileInputStream(Functions.path2);
//					Functions.pro.load(fis2);
//					final String secondValue1 = Functions.pro.getProperty("secondValue");
//					final int secondValue2 = Integer.parseInt(secondValue1);
//					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//					final Date date13 = new Date();
//					System.out.println("Current Date " + dateFormat2.format(date13));
//					final Calendar c2 = Calendar.getInstance();
//					c2.setTime(date13);
//					c2.add(13, -secondValue2);
//					final Date currentDatePlusOne = c2.getTime();
//					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
//					System.out.println("Updated Date " + Framework.OTPcurrentTime);
//					System.out.println(Framework.OTPcurrentTime);
//					System.out.println("Inside Otp read All");
//					String mobileNo;
//					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
//						mobileNo = Functions.pro.getProperty(objectTypeRDS);
//					} else {
//						mobileNo = Functions.pro.getProperty("userMobile");
//					}
//					System.out.println("//////////In OTP_READ_ALL");
//					String OTP3 = DB_Read_OTP.readotp3(mobileNo, dataFieldRDS, time3);
//					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
//						OTP3 = DB_Read_OTP.readotp3(mobileNo, dataFieldRDS, time3);
//					}
//					webElementVal.clear();
//					final JavascriptExecutor jse9 = (JavascriptExecutor) Framework.driver;
//					try {
//						webElementVal.sendKeys(new CharSequence[] { OTP3 });
//					} catch (Exception e24) {
//						try {
//							webElementVal.sendKeys(new CharSequence[] { OTP3 });
//						} catch (Exception e25) {
//							webElementVal.sendKeys(new CharSequence[] { OTP3 });
//						}
//					}
//				} catch (Exception e4) {
//					e4.printStackTrace();
//				}
//			}

			if (actionRDS.contains("OTP_READ_ALL(")) {
				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					final String secondValue1 = Functions.pro.getProperty("secondValue");
					final int secondValue2 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					final Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					final Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In OTP_READ_ALL");
					String OTP3 = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
					System.out.println("OTP_READ====" + OTP3);
					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
						OTP3 = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
						System.out.println("OTP_READ====" + OTP3);
					}
					webElementVal.clear();
					final JavascriptExecutor jse9 = (JavascriptExecutor) Framework.driver;
					try {
						webElementVal.sendKeys(new CharSequence[] { OTP3 });
					} catch (Exception e24) {
						try {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						} catch (Exception e25) {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						}
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}

			if (actionRDS.equalsIgnoreCase("verifyAllLocators")) {
				boolean flag = true;
				try {
					WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[0])));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[1])));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[2])));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[3])));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[4])));
					flag = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (flag) {
					JOptionPane.showMessageDialog(null, "Menu Bar Options Is Missing...");
				} else {
					System.out.println("All Element Is Visible...");
				}
			}

			if (actionRDS.equalsIgnoreCase("OTP_READ_Canara")) {
				try {
					final FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					final String secondValue1 = Functions.pro.getProperty("secondValue");
					final int secondValue2 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					final Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					final Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo = Functions.pro.getProperty("userMobile").trim();

					System.out.println("//////////In OTP_READ_for 2 sender");
					String OTP3 = DB_Read_OTP.readotpFor2Sender(mobileNo, dataFieldRDS);

					if (OTP3.isEmpty()) {
						OTP3 = JOptionPane.showInputDialog(null, "enter otp manual...");
					}

					webElementVal.clear();
					try {
						webElementVal.sendKeys(new CharSequence[] { OTP3 });
					} catch (Exception e24) {
						try {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						} catch (Exception e25) {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						}
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}

			if (actionRDS.contains("AXISOTP(")) {
				final int time = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					final String secondValue = Functions.pro.getProperty("secondValue2");

					if (secondValue != null && !secondValue.equalsIgnoreCase("")) {
						final int secondValue2 = Integer.parseInt(secondValue);
						final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						final Date date13 = new Date();
						System.out.println("Current Date " + dateFormat2.format(date13));
						final Calendar c2 = Calendar.getInstance();
						c2.setTime(date13);
						c2.add(13, -secondValue2);
						final Date currentDatePlusOne = c2.getTime();
						Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
						System.out.println("Updated Date " + Framework.OTPcurrentTime);
						System.out.println(Framework.OTPcurrentTime);
						System.out.println("Inside axis Otp read");
					}

					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}

					String OTP3 = DB_Read_OTP.readAxisOtp(mobileNo, dataFieldRDS, time);
					System.out.println("OTP_READ====" + OTP3);
					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
						OTP3 = DB_Read_OTP.readAxisOtp(mobileNo, dataFieldRDS, time);
						System.out.println("OTP_READ====" + OTP3);
					}
					webElementVal.clear();
					final JavascriptExecutor jse9 = (JavascriptExecutor) Framework.driver;
					try {
						webElementVal.sendKeys(new CharSequence[] { OTP3 });
					} catch (Exception e24) {
						try {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						} catch (Exception e25) {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						}
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			} else if (actionRDS.equalsIgnoreCase("selectAxisPreviousdate")) {
				System.out.println("In selectAxisPreviousdate");
				int hour = LocalDateTime.now().getHour();
				if (hour <= 7 && hour > 0) {
					CustomFunctions.selectAxisPreviousdate(webElementVal);
				} else {
					System.out.println("This function didn't work in on Market");
				}
			} else if (actionRDS.equalsIgnoreCase("verifyAxisDownloadedFilesNew")) {
				CustomFunctions.verifyAxisDownloadedFilesNew(propertyValueRDS, dataFieldRDS);
			}

			if (actionRDS.contains("OTPALLINB(")) {
				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					final String secondValue1 = Functions.pro.getProperty("secondValue");
					final int secondValue2 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					final Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					final Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In OTP_READ_ALL_Inb");
					String OTP3 = DB_Read_OTP.readotpInb(mobileNo, dataFieldRDS, time3);
					System.out.println("OTP_READ====" + OTP3);
					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
						OTP3 = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
						System.out.println("OTP_READ====" + OTP3);
					}
					webElementVal.clear();
					final JavascriptExecutor jse9 = (JavascriptExecutor) Framework.driver;
					try {
						webElementVal.sendKeys(new CharSequence[] { OTP3 });
					} catch (Exception e24) {
						try {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						} catch (Exception e25) {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						}
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}

			if (actionRDS.equalsIgnoreCase("OTP_READ_ALL_2")) {

				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					final String secondValue1 = Functions.pro.getProperty("secondValue1");
					final int secondValue2 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					final Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					final Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile1");
					}
					System.out.println("//////////In OTP_READ_ALL");
					String OTP3 = DB_Read_OTP.readotp2(mobileNo, dataFieldRDS, time3);
					System.out.println("OTP_READ====" + OTP3);
					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
						OTP3 = DB_Read_OTP.readotp2(mobileNo, dataFieldRDS, time3);
						System.out.println("OTP_READ====" + OTP3);
					}
					webElementVal.clear();
					final JavascriptExecutor jse9 = (JavascriptExecutor) Framework.driver;
					try {
						webElementVal.sendKeys(new CharSequence[] { OTP3 });
					} catch (Exception e24) {
						try {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						} catch (Exception e25) {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						}
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}

			}

			if (actionRDS.contains("ICICIOTPREAD(")) {

				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis2 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis2);
					final String secondValue1 = Functions.pro.getProperty("secondValue");
					final int secondValue2 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date13 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date13));
					final Calendar c2 = Calendar.getInstance();
					c2.setTime(date13);
					c2.add(13, -secondValue2);
					final Date currentDatePlusOne = c2.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside ICICI OPT READ");
					String mobileNo;
					if (objectTypeRDS != null && !(objectTypeRDS.equalsIgnoreCase(""))) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In ICICI OTP READ");
					String OTP3 = DB_Read_OTP.iciciOtp(mobileNo, dataFieldRDS.split("#")[0], dataFieldRDS.split("#")[1],
							time3);
					System.out.println("OTP_READ====" + OTP3);
					if (OTP3 == null || OTP3.equalsIgnoreCase("")) {
						OTP3 = DB_Read_OTP.iciciOtp(mobileNo, dataFieldRDS.split("#")[0], dataFieldRDS.split("#")[1],
								time3);
						System.out.println("OTP_READ====" + OTP3);
					}
					webElementVal.clear();
					final JavascriptExecutor jse9 = (JavascriptExecutor) Framework.driver;
					try {
						webElementVal.sendKeys(new CharSequence[] { OTP3 });
					} catch (Exception e24) {
						try {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						} catch (Exception e25) {
							webElementVal.sendKeys(new CharSequence[] { OTP3 });
						}
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}

			} else if (actionRDS.equalsIgnoreCase("ViewCHECKClick")) {
				try {
					WebElement we = Framework.driver.findElement(
							By.xpath("//*[@id='collapse3']/div/div[3]/div/div[3]/div/div[2]/center[2]/button"));

					if (we != null) {
						System.out.println("Proceed as per DataSheet----------------------");
					} else {
						System.out.println("Going Click summary Tab-----------------");
						Framework.driver.findElement(By.id("summaryTab")).click();
						Thread.sleep(1500L);
						JavascriptExecutor jse = (JavascriptExecutor) Framework.driver;
						jse.executeScript("window.scrollBy(0, 2000);", new Object[0]);
						jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
					}
				} catch (Exception e) {
				}
			}

			if (actionRDS.contains("OTP_READ_NIVA(")) {
				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				final WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10L));
				try {
					final FileInputStream fis3 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis3);
					final String secondValue3 = Functions.pro.getProperty("secondValue");
					final int secondValue4 = Integer.parseInt(secondValue3);
					final SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date16 = new Date();
					System.out.println("Current Date " + dateFormat3.format(date16));
					final Calendar c3 = Calendar.getInstance();
					c3.setTime(date16);
					c3.add(13, -secondValue4);
					final Date currentDatePlusOne2 = c3.getTime();
					Framework.OTPcurrentTime = dateFormat3.format(currentDatePlusOne2);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo2;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo2 = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo2 = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In OTP_READ_ALL");
					String OTP5 = DB_Read_OTP.readotp1(mobileNo2, dataFieldRDS, time3);
					System.out.println("OTP_READ====" + OTP5);
					if (OTP5 == null || OTP5.equalsIgnoreCase("")) {
						OTP5 = DB_Read_OTP.readotp1(mobileNo2, dataFieldRDS, time3);
						System.out.println("OTP_READ====" + OTP5);
					}
					final WebElement wb5 = (WebElement) wait.until(
							(Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
					wb5.clear();
					final char[] ch3 = new char[OTP5.length()];
					for (int k3 = 0; k3 < OTP5.length(); ++k3) {
						ch3[k3] = OTP5.charAt(k3);
						wb5.sendKeys(new CharSequence[] { String.valueOf(ch3[k3]) });
						Thread.sleep(1000L);
					}
				} catch (Exception e14) {
					e14.printStackTrace();
				}
			}
			if (actionRDS.contains("SINGLE_OTPREAD_ALL(")) {
				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis4 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis4);
					final String secondValue1 = Functions.pro.getProperty("secondValue");
					final int secondValue5 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date17 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date17));
					final Calendar c4 = Calendar.getInstance();
					c4.setTime(date17);
					c4.add(13, -secondValue5);
					final Date currentDatePlusOne = c4.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read All");
					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In OTP_READ_ALL");
					final String OTP6 = Functions.otp_g = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
					System.out.println("OTP IS === " + OTP6);
					otp_g = OTP6;
					try {
						Functions.otp1 = OTP6.charAt(0);
						Functions.otp2 = OTP6.charAt(1);
						Functions.otp3 = OTP6.charAt(2);
						Functions.otp4 = OTP6.charAt(3);
						Functions.otp5 = OTP6.charAt(4);
						Functions.otp6 = OTP6.charAt(5);
					} catch (Exception e) {
						// TODO: handle exception
					}
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			} else if (actionRDS.equalsIgnoreCase("EmailOTP")) {
				final FileInputStream fis5 = new FileInputStream(Functions.path2);
				Functions.pro.load(fis5);
				final String dataField = dataFieldRDS;
				Functions.objectType = objectTypeRDS;
				final String mail = Functions.pro.getProperty("mailFrom");
				final String password2 = Functions.pro.getProperty("mailPassword");
				final String hostName = Functions.pro.getProperty("hostName");
				final String port = Functions.pro.getProperty("port");
				final String inputBox = Functions.pro.getProperty("inputBoxForOtp");
				System.out.println("DEVOTP------------------------otp");
				String otp3 = null;
				if (objectTypeRDS.equalsIgnoreCase("") || objectTypeRDS == null) {
					otp3 = FetchMail.getOtp(hostName, mail, password2, dataField, port);
				} else {
					otp3 = FetchMail.getOtp1(hostName, mail, password2, dataField, port);
				}
				System.out.println("-----------OTP-------------" + otp3);
				if (otp3 != null) {
					if (objectTypeRDS.equalsIgnoreCase("JS")) {
						((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + otp3 + "';",
								new Object[] { webElementVal });
					} else {
						webElementVal.sendKeys(new CharSequence[] { otp3 });
					}
				} else {
					if (inputBox.equalsIgnoreCase("Y")) {
						Thread.sleep(2000);
						otp3 = JOptionPane.showInputDialog(new Frame(), "Enter OTP for E-verification");
					}
				}
			} else if (actionRDS.equalsIgnoreCase("iciciIPRUOtp")) {
				FileInputStream fis5 = new FileInputStream(Functions.path2);
				Functions.pro.load(fis5);
				String dataField = dataFieldRDS;
				Functions.objectType = objectTypeRDS;
				String mail = Functions.pro.getProperty("mailFrom");
				String password2 = Functions.pro.getProperty("mailPassword");
				String hostName = Functions.pro.getProperty("hostName");
				String port = Functions.pro.getProperty("port");
				String inputBox = Functions.pro.getProperty("inputBoxForOtp");
				System.out.println("DEVOTP------------------------otp");
				String otp3 = NewCustomFunctions.readMail(hostName, mail, password2, "alerts@trans.esafbank.com",
						dataField);
//				if (objectTypeRDS.equalsIgnoreCase("") || objectTypeRDS == null) {
//					otp3 = FetchMail.iciciIPRUOtp(hostName, mail, password2, port, dataField);
//				} else {
//					otp3 = FetchMail.iciciIPRUOtp(hostName, mail, password2, port, dataField);
//				}
				if (otp3 == null) {
					otp3 = JOptionPane.showInputDialog(new Frame(), "Enter OTP");
					Thread.sleep(2000);
				} else {
					otp3 = otp3.split("Greetings from ESAF Small Finance Bank! ")[1].split(" ")[0].trim();
					System.out.println("otp =" + otp3);
				}
				System.out.println("-----------OTP-------------" + otp3);
				webElementVal.sendKeys(new CharSequence[] { otp3 });
			}

			if (actionRDS.equalsIgnoreCase("sendSingleOtp")) {
				for (int i = 0; i < otp_g.length(); i++) {
					String xpath = propertyValueRDS + "[" + (i + 1) + "]";
					String otp = String.valueOf(otp_g.charAt(i));
					System.out.println("Otp is ==== " + otp);
					// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))).sendKeys(otp);
					Thread.sleep(1000);
				}

			}

			if (actionRDS.equalsIgnoreCase("Refresh")) {
				Framework.driver.navigate().refresh();
				Thread.sleep(2000L);
			}
			if (actionRDS.equalsIgnoreCase("deleteAllCookies")) {
				Framework.driver.manage().deleteAllCookies();
				Thread.sleep(5000L);
				System.out.println("Cookies Delete Successfully ======================");
			}
			if (actionRDS.contains("Single_OTP_READ_4(")) {
				final int time3 = Integer.parseInt(actionRDS.split("\\(")[1].split("\\)")[0]);
				try {
					final FileInputStream fis4 = new FileInputStream(Functions.path2);
					Functions.pro.load(fis4);
					final String secondValue1 = Functions.pro.getProperty("secondValue");
					final int secondValue5 = Integer.parseInt(secondValue1);
					final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					final Date date17 = new Date();
					System.out.println("Current Date " + dateFormat2.format(date17));
					final Calendar c4 = Calendar.getInstance();
					c4.setTime(date17);
					c4.add(13, -secondValue5);
					final Date currentDatePlusOne = c4.getTime();
					Framework.OTPcurrentTime = dateFormat2.format(currentDatePlusOne);
					System.out.println("Updated Date " + Framework.OTPcurrentTime);
					System.out.println(Framework.OTPcurrentTime);
					System.out.println("Inside Otp read 4");
					String mobileNo;
					if (objectTypeRDS != null && !objectTypeRDS.equalsIgnoreCase("")) {
						mobileNo = Functions.pro.getProperty(objectTypeRDS);
					} else {
						mobileNo = Functions.pro.getProperty("userMobile");
					}
					System.out.println("//////////In OTP_READ_ALL");
					final String OTP6 = DB_Read_OTP.readotp1(mobileNo, dataFieldRDS, time3);
					Functions.otp1 = OTP6.charAt(0);
					Functions.otp2 = OTP6.charAt(1);
					Functions.otp3 = OTP6.charAt(2);
					Functions.otp4 = OTP6.charAt(3);
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
			if (actionRDS.equalsIgnoreCase("otp1")) {
				String otp_1 = String.valueOf(Functions.otp1);
				// webElementVal.clear();
				webElementVal.click();
				if (otp_1 == null) {
					final Frame frame12 = new Frame();
					otp_1 = JOptionPane.showInputDialog(frame12, "Enter OTP1");
					webElementVal.sendKeys(new CharSequence[] { otp_1 });
				} else {
					webElementVal.sendKeys(new CharSequence[] { otp_1 });

				}
			}
			if (actionRDS.equalsIgnoreCase("otp2")) {
				String otp_2 = String.valueOf(Functions.otp2);
				// webElementVal.clear();
				webElementVal.click();
				if (otp_2 == null) {
					final Frame frame12 = new Frame();
					otp_2 = JOptionPane.showInputDialog(frame12, "Enter OTP2");
					webElementVal.sendKeys(new CharSequence[] { otp_2 });
				} else {
					webElementVal.sendKeys(new CharSequence[] { otp_2 });

				}
			}
			if (actionRDS.equalsIgnoreCase("otp3")) {
				String otp_3 = String.valueOf(Functions.otp3);
				// webElementVal.clear();
				webElementVal.click();
				if (otp_3 == null) {
					final Frame frame12 = new Frame();
					otp_3 = JOptionPane.showInputDialog(frame12, "Enter OTP3");
					webElementVal.sendKeys(new CharSequence[] { otp_3 });
				} else {
					webElementVal.sendKeys(new CharSequence[] { otp_3 });

				}
			}
			if (actionRDS.equalsIgnoreCase("otp4")) {
				String otp_4 = String.valueOf(Functions.otp4);
				// webElementVal.clear();
				webElementVal.click();
				if (otp_4 == null) {
					final Frame frame12 = new Frame();
					otp_4 = JOptionPane.showInputDialog(frame12, "Enter OTP4");
					webElementVal.sendKeys(new CharSequence[] { otp_4 });
				} else {
					webElementVal.sendKeys(new CharSequence[] { otp_4 });
				}
			}
			if (actionRDS.equalsIgnoreCase("otp5")) {
				String otp_5 = String.valueOf(Functions.otp5);
				// webElementVal.clear();
				webElementVal.click();
				if (otp_5 == null) {
					final Frame frame12 = new Frame();
					otp_5 = JOptionPane.showInputDialog(frame12, "Enter OTP5");
					webElementVal.sendKeys(new CharSequence[] { otp_5 });
				} else {
					webElementVal.sendKeys(new CharSequence[] { otp_5 });
				}
			}
			if (actionRDS.equalsIgnoreCase("otp6")) {
				String otp_6 = String.valueOf(Functions.otp6);
				// webElementVal.clear();
				webElementVal.click();
				if (otp_6 == null) {
					final Frame frame12 = new Frame();
					otp_6 = JOptionPane.showInputDialog(frame12, "Enter OTP6");
					webElementVal.sendKeys(new CharSequence[] { otp_6 });
				} else {
					webElementVal.sendKeys(new CharSequence[] { otp_6 });
				}
			}
//			if (actionRDS.equalsIgnoreCase("OTP_READ_Canada")) {
//				System.out.println("//////////In OTP_READ");
//				final SimpleDateFormat f6 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				f6.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
//				Framework.OTPcurrentTime = f6.format(Calendar.getInstance().getTime());
//				System.out.println("OTPcurrentTime :" + Framework.OTPcurrentTime);
//				String OTP7 = DB_Read_OTP.readotp(dataFieldRDS);
//				System.out.println("OTP_READ====" + OTP7);
//				if (OTP7 == null || OTP7.equalsIgnoreCase("")) {
//					OTP7 = DB_Read_OTP.readotp(dataFieldRDS);
//					System.out.println("OTP_READ====" + OTP7);
//				}
//				webElementVal.clear();
//				final JavascriptExecutor executor5 = (JavascriptExecutor) Framework.driver;
//				executor5.executeScript("arguments[0].click();", new Object[] { webElementVal });
//				webElementVal.sendKeys(new CharSequence[] { OTP7 });
//				final Date date18 = new Date();
//				final SimpleDateFormat dft5 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Framework.OTPcurrentTime = dft5.format(date18);
//				System.out.println(Framework.OTPcurrentTime);
//			} 
			else if (actionRDS.equalsIgnoreCase("closepopupwindow")) {
				final String parentwin = Framework.driver.getWindowHandle();
				final String child = null;
				System.out.println("%%%%    gn n %%%%%%%%%%5");
				try {
					Thread.sleep(4000L);
					System.out.println("Total window " + Framework.driver.getWindowHandles().size());
					for (final String childwin : Framework.driver.getWindowHandles()) {
						if (!childwin.equalsIgnoreCase(parentwin)) {
							Framework.driver.close();
							Framework.driver.switchTo().window(parentwin);
						}
					}
				} catch (Exception e15) {
					e15.printStackTrace();
					Framework.driver.switchTo().window(child);
					Framework.driver.close();
					Framework.driver.switchTo().window(parentwin);
				}
			}

			else if (actionRDS.equalsIgnoreCase("CLASS_CLICK")) {
				final Robot rb7 = new Robot();
				final WebElement elem = Framework.driver.findElement(By.className("x-form-cb-label"));
				final int width = elem.getSize().getWidth();
				final Actions act3 = new Actions(Framework.driver);
				act3.moveToElement(elem).moveByOffset(width / 2 - 2, 0).click().perform();
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
			} else if (actionRDS.equalsIgnoreCase("NEFT")) {
				Framework.driver.findElement(By.name("LOC_AVAIL_BAL")).click();
				final Robot rb7 = new Robot();
				for (int l3 = 0; l3 < 5; ++l3) {
					rb7.keyPress(9);
					rb7.keyRelease(9);
					Thread.sleep(1000L);
				}
				Thread.sleep(2000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
			} else if (actionRDS.equalsIgnoreCase("PAYMENT_Mode")) {
				final Robot rb7 = new Robot();
				Thread.sleep(2000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
				Thread.sleep(4000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
				Thread.sleep(2000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(49);
				rb7.keyRelease(49);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(3000L);
				rb7.keyPress(84);
				rb7.keyRelease(84);
				rb7.keyPress(69);
				rb7.keyRelease(69);
				rb7.keyPress(83);
				rb7.keyRelease(83);
				rb7.keyPress(84);
				rb7.keyRelease(84);
			} else if (actionRDS.equalsIgnoreCase("IMPS")) {
				Framework.driver.findElement(By.name("LOC_AVAIL_BAL")).click();
				final Robot rb7 = new Robot();
				for (int l3 = 0; l3 < 5; ++l3) {
					rb7.keyPress(9);
					rb7.keyRelease(9);
					Thread.sleep(300L);
				}
				Thread.sleep(2000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
			} else if (actionRDS.equalsIgnoreCase("TOKIOPAYMENTMETHOD")) {
				if (!Functions.driver.findElement(By.xpath(
						"/html/body/div[1]/div[2]/div[4]/div[1]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/span/span[1]"))
						.isSelected()) {
					Functions.driver.findElement(By.xpath(
							"/html/body/div[1]/div[2]/div[4]/div[1]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/span/span[1]"))
							.click();
				} else {
					Functions.driver.findElement(By.xpath(
							"/html/body/div[1]/div[2]/div[4]/div[1]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/span/span[2]"))
							.click();
				}
			} else if (actionRDS.equalsIgnoreCase("PAYMENT_Mode1")) {
				final Robot rb7 = new Robot();
				Thread.sleep(2000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
				Thread.sleep(4000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(40);
				rb7.keyRelease(40);
				Thread.sleep(1000L);
				rb7.keyPress(10);
				rb7.keyRelease(10);
				Thread.sleep(2000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(1000L);
				rb7.keyPress(49);
				rb7.keyRelease(49);
				rb7.keyPress(9);
				rb7.keyRelease(9);
				Thread.sleep(3000L);
				rb7.keyPress(84);
				rb7.keyRelease(84);
				rb7.keyPress(69);
				rb7.keyRelease(69);
				rb7.keyPress(83);
				rb7.keyRelease(83);
				rb7.keyPress(84);
				rb7.keyRelease(84);
			}

			else if (actionRDS.equalsIgnoreCase("PAGE_UP")) {
				try {
					Framework.driver.switchTo().defaultContent();
					Actions actions3 = new Actions(Framework.driver);
					actions3.moveToElement(Framework.driver.findElement(By.id("home"))).build().perform();
					actions3.sendKeys(new CharSequence[] { (CharSequence) Keys.ARROW_LEFT }).build().perform();
				} catch (UnhandledAlertException ert) {
					final Alert alert = Framework.driver.switchTo().alert();
					Framework.TakeScreenshots();
					final String error = alert.getText();
					System.out.println("2 " + Framework.ScreenshotfileLocation);
					Framework.extent.log(LogStatus.INFO, "<br> UnExpected Alert <br>"
							+ Framework.extent.addScreenCapture(Framework.ScreenshotfileLocation));
					Framework.extentrpt.flush();
					alert.accept();
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					Framework.extent.log(LogStatus.FAIL, "");
					Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
				} catch (Exception ex31) {
				}
			} else if (actionRDS.equalsIgnoreCase("Clipboard")) {
				System.out.println("--------in clipboard------");
				Robot robot = new Robot();
				StringSelection svfl = new StringSelection(dataFieldRDS);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(svfl, null);
				robot.keyPress(17);
				robot.keyPress(86);
				robot.keyRelease(17);
				robot.keyRelease(86);
			} else if (actionRDS.equalsIgnoreCase("INPUTBOX1")) {
				System.out.println("MessageBox.............................!");
				Frame frame = new Frame();
				frame.setVisible(false);
				frame.toFront();
				String activationcode = JOptionPane.showInputDialog(frame, "Enter the result for verification");
				webElementVal.sendKeys(new CharSequence[] { activationcode });
			} else if (actionRDS.equalsIgnoreCase("MANUAL_CAPTCHA")) {
				Thread.sleep(1000L);
				Frame frame = new Frame();
				frame.setVisible(true);
				frame.toFront();
				JOptionPane.showMessageDialog(frame, "Fill Your Capthcha , then click on ok of popup");
				frame.setVisible(false);
			} else if (actionRDS.equalsIgnoreCase("INPUTBOX_CAPTCHACHECK")) {
				System.out.println("MessageBox.............................!");
				Frame frame = new Frame();
				frame.setVisible(false);
				frame.toFront();
				String activationcode = JOptionPane.showInputDialog(frame, "Enter the result for verification");
				String id_value = webElementVal.getAttribute("id");
				if (id_value.equalsIgnoreCase("nlpAnswer")) {
					webElementVal.sendKeys(new CharSequence[] { activationcode });
				} else {
					webElementVal = Framework.driver.findElement(By.id("captcha"));
					Thread.sleep(1000L);
					webElementVal.sendKeys(new CharSequence[] { activationcode });
				}
			} else if (actionRDS.equalsIgnoreCase("INPUTBOX_SIKULI")) {
				Screen s50 = new Screen();
				s50.wait((Object) propertyValueRDS, 90.0);
				System.out.println("MessageBox.............................!");
				Frame frame14 = new Frame();
				frame14.setVisible(false);
				frame14.toFront();
				String activationcode5 = JOptionPane.showInputDialog(frame14, "Enter the result for verification");
				s50.find((Object) propertyValueRDS).type(activationcode5);
			} else if (actionRDS.equalsIgnoreCase("FindName&Click")) {
				Thread.sleep(1000L);
				List<WebElement> list5;
				int k4;
				for (list5 = (List<WebElement>) Framework.driver
						.findElements(By.xpath(propertyValueRDS)), k4 = 1; k4 <= list5.size()
								&& !Framework.driver.findElement(By.xpath("(" + propertyValueRDS + ")[" + k4 + "]"))
										.getText().equalsIgnoreCase(dataFieldRDS); ++k4) {
				}
				Thread.sleep(1000L);
				Framework.driver.findElement(By.xpath(
						"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[3]/div/div/div/div/mx-layout/div/div[1]/div[2]/div[3]/div[2]/div/div/div[2]/div/div[2]/div/div["
								+ k4 + "]/div/div[3]/div/i"))
						.click();
			} else if (actionRDS.equalsIgnoreCase("FindRow&Click")) {
				int k4;
				List<WebElement> trow;
				for (trow = (List<WebElement>) Framework.driver.findElements(By.tagName("tr")), k4 = 1; k4 <= trow
						.size()
						&& !Framework.driver.findElement(By.xpath(
								"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
										+ k4 + "]/td[3]"))
								.getText().equals("Bibhu"); ++k4) {
				}
				Framework.driver.findElement(By.xpath(
						"/html/body/mx-screen/mx-segment[2]/mx-layout/mx-layout/section/section/section/section/section/section/section/section/div[1]/div[1]/div[1]/div/div/div/div/mx-layout/div/div/div/div[1]/div/div[8]/div[1]/div/table/tbody/tr["
								+ k4 + "]/td[1]/div/label/input"))
						.click();
			} else if (actionRDS.equalsIgnoreCase("CHECK_DELAY")) {
				String urlDate = null;
				String status9 = null;
				WebElement wel1 = Framework.driver.findElement(By.xpath(propertyValueRDS));
				String dt8;
				if (objectTypeRDS.equalsIgnoreCase("Editbox")) {
					dt8 = wel1.getAttribute("value");
					System.out.println("dt===============" + dt8);
				} else {
					dt8 = wel1.getText();
					System.out.println("dt===============" + dt8);
				}
				System.out.println("url Date === " + dt8);
				if (dataFieldRDS.equalsIgnoreCase("dd-MM-yyyy")) {
					urlDate = dt8.replace("-", "/");
				} else if (dataFieldRDS.equalsIgnoreCase("Data as on")) {
					urlDate = dt8.split("on:")[1].trim().replace("-", "/");
				} else {
					SimpleDateFormat dft6 = new SimpleDateFormat("dd/MM/yyyy");
					Date date19 = new Date(dt8);
					urlDate = dft6.format(date19);
				}
				System.out.println("Formatted  Date === " + urlDate);
				Date now5 = new Date();
				SimpleDateFormat dft7 = new SimpleDateFormat("dd/MM/yyyy");
				String todayDate = dft7.format(now5);
				System.out.println("Today's Date === " + todayDate);
				String[] d5 = todayDate.split("/");
				int day = Integer.parseInt(d5[0]) - 1;
				String dateToCompare = String.valueOf(day) + "/" + d5[1] + "/" + d5[2];
				System.out.println("Date to compare === " + dateToCompare);
				Date d6 = dft7.parse(dateToCompare);
				Date d7 = dft7.parse(urlDate);
				long diff = d6.getTime() - d7.getTime();
				long diffDays = diff / 86400000L;
				System.out.print(diffDays + " days");
				if (controlRDS.equalsIgnoreCase("V")) {
					int year6 = Calendar.getInstance().get(1);
					String MonthName4 = new SimpleDateFormat("MMMM").format(now5);
					int monthday4 = Calendar.getInstance().get(5);
					File file3 = new File(String.valueOf(Framework.homedir) + "/Logs/" + year6 + "/" + MonthName4 + "/"
							+ monthday4 + "/" + Framework.ScriptName + "/" + Framework.ScriptName + "_DELAY.csv");
					if (!file3.exists()) {
						file3.createNewFile();
						FileWriter fileWritter9 = new FileWriter(file3, true);
						BufferedWriter bufferWritter9 = new BufferedWriter(fileWritter9);
						bufferWritter9.write("Url,UrlDate,DateToComapre,Status,Delay\n");
						bufferWritter9.close();
					}
					if (diffDays == 0L) {
						status9 = "No Delay";
					} else {
						status9 = "Delay";
					}
					if (file3.exists()) {
						FileWriter fileWritter9 = new FileWriter(file3, true);
						BufferedWriter bufferWritter9 = new BufferedWriter(fileWritter9);
						bufferWritter9.write(Functions.url + "," + urlDate + "," + dateToCompare + "," + status9 + ","
								+ String.valueOf(diffDays) + "\n");
						bufferWritter9.close();
					}
				}
			} else if (actionRDS.equalsIgnoreCase("THRESHOLD")) {
				System.out.println("Function started -------------------------------vv");
				int count6 = 0;
				Framework.driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
				final List<WebElement> elements2 = (List<WebElement>) Framework.driver
						.findElements(By.xpath("//*[@id='userName1']"));
				for (int l2 = 0; l2 <= 7; ++l2) {
					final boolean value6 = elements2.isEmpty();
					if (!value6) {
						System.out.println("Element displayed -------------------------------vv");
						break;
					}
					++count6;
					System.out.println(count6);
					if (count6 == 7) {
						System.out.println("Element Not Found -------------------------------vv");
						Monitoring_FrameWork.sendEmailWithAttachments(Monitoring_FrameWork.host,
								Monitoring_FrameWork.port, Monitoring_FrameWork.mailTo,
								Monitoring_FrameWork.takeScreenshot(), Functions.errorMsg, Functions.extraMsg);
					}
				}
			} else if (actionRDS.equalsIgnoreCase("THRESHOLD_CINB_UPLOADER")) {
				try {
					System.out.println("Function started -------------------------------vv");
					final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(7000L));
					System.out.println("Waiting -----------------" + wait4);
					wait4.until((Function) ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//*[@id=\"postlogin-sbi\"]")));
					Framework.driver.findElement(By.xpath("//*[@id=\"postlogin-sbi\"]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Element Not Found -------------------------------vv");
					Monitoring_FrameWork.sendEmailWithAttachments(Monitoring_FrameWork.host, Monitoring_FrameWork.port,
							Monitoring_FrameWork.mailTo, Monitoring_FrameWork.takeScreenshot(), Functions.errorMsg,
							Functions.extraMsg);
				}
			} else if (!actionRDS.equalsIgnoreCase("THRESHOLD_CINB_UPLOADER_CORPORATEINB")) {
				if (actionRDS.equalsIgnoreCase("THRESHOLD_CINB_CORPORATE_INB")) {
					try {
						System.out.println("Function started -------------------------------vv");
						final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(7000L));
						System.out.println("Waiting -----------------" + wait4);
						wait4.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(
								"//*[@id=\"postlogin-sbi\"]/div[1]/div[1]/app-side-nav-bar/div[2]/aside/ul/li[2]/a[1]")));
						Framework.driver.findElement(By.xpath(
								"//*[@id=\"postlogin-sbi\"]/div[1]/div[1]/app-side-nav-bar/div[2]/aside/ul/li[2]/a[1]"))
								.isDisplayed();
					} catch (Exception e) {
						System.out.println("Element Not Found -------------------------------vv");
						Monitoring_FrameWork.sendEmailWithAttachments(Monitoring_FrameWork.host,
								Monitoring_FrameWork.port, Monitoring_FrameWork.mailTo,
								Monitoring_FrameWork.takeScreenshot(), Functions.errorMsg, Functions.extraMsg);
					}
				} else if (actionRDS.equalsIgnoreCase("THRESHOLD_CINB_CORPORATE_INB_CORPORATEINB")) {
					try {
						System.out.println("Function started -------------------------------vv");
						final WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(7000L));
						System.out.println("Waiting -----------------" + wait4);
						wait4.until((Function) ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[text()='My Accounts']")));
						Framework.driver.findElement(By.xpath("//*[text()='My Accounts']")).isDisplayed();
					} catch (Exception e) {
						System.out.println("Element Not Found -------------------------------vv");
						Monitoring_FrameWork.sendEmailWithAttachments(Monitoring_FrameWork.host,
								Monitoring_FrameWork.port, Monitoring_FrameWork.mailTo,
								Monitoring_FrameWork.takeScreenshot(), Functions.errorMsg, Functions.extraMsg);
					}
				} else if (actionRDS.equalsIgnoreCase("THRESHOLD_CINB_CORPORATE_INB_MERCHANT")) {
					try {
						System.out.println("Function started -------------------------------vv");
						WebDriverWait wait4 = new WebDriverWait(Framework.driver, Duration.ofMillis(7000L));
						System.out.println("Waiting -----------------" + wait4);
						wait4.until((Function) ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@id=\"navbar\"]/div[4]/a[1]")));
						Framework.driver.findElement(By.xpath("//*[@id=\"navbar\"]/div[4]/a[1]")).isDisplayed();
					} catch (Exception e) {
						System.out.println("Element Not Found -------------------------------vv");
						Monitoring_FrameWork.sendEmailWithAttachments(Monitoring_FrameWork.host,
								Monitoring_FrameWork.port, Monitoring_FrameWork.mailTo,
								Monitoring_FrameWork.takeScreenshot(), Functions.errorMsg, Functions.extraMsg);
					}
				} else if (actionRDS.equalsIgnoreCase("Monitoring_Properties")) {

					Date date20 = new Date();
					SimpleDateFormat dft8 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Framework.OTPcurrentTime = dft8.format(date20);
					Fillo fillo2 = new Fillo();
					String maincontrollerpath = String
							.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.homedir))))
							+ "/Main_Controller_Web.xlsx";
					com.codoid.products.fillo.Connection con4 = fillo2.getConnection(maincontrollerpath);
					String str3 = "Select * from Properties where InstanceName='" + Framework.functiontorun + "'";
					Recordset rs6 = con4.executeQuery(str3);
					while (rs6.next()) {
						// Monitoring_FrameWork.CreatePath(Framework.ScriptName);
						Framework.availability_alert = Integer.parseInt(rs6.getField("Availability_alert"));
						Framework.defaultwaittime = Integer.parseInt(rs6.getField("DefaultWait_Time"));
						Framework.iResponseTime = Integer.parseInt(rs6.getField("MaxResponseTime"));
						Framework.ResponseTime_alert = Integer.parseInt(rs6.getField("ResponseTime_alert"));
						System.out.println("~~~~~~~~~~~~~~ Read all Alert flag ~~~~~~~~~~~~~");

						// Framework.ReadDB();
					}
				}
			}

			if (controlRDS.equalsIgnoreCase("T") && !actionRDS.equalsIgnoreCase("BROWSEURL")) {
				Framework.tStartTime = Monitoring_FrameWork.StartTime();
			}
		} catch (Exception e) {

//			if (e instanceof ArithmeticException) {
//
//			} else if (e instanceof NullPointerException) {
//
//			}
//			if (e instanceof org.openqa.selenium.TimeoutException) {
//				
//			}

			Framework.errorType = e.getClass().getName();
			try {
				Framework.errorMessage = e.getMessage().split("\n")[0];
			} catch (Exception e1) {
				Framework.errorMessage = null;
			}
			Framework.errorsatus = "1";
			Framework.errorpagename = pageName;
			e.printStackTrace();
			System.out.println("---------------- In action catch ------------------");

			Monitoring_FrameWork.SaveResult("False", "True");

		}
	}

	public static void showLocator(WebElement Webelementval) {
		try {
			System.out.println("Show Locator");
			JavascriptExecutor executor = (JavascriptExecutor) Framework.driver;
			executor.executeScript("arguments[0].style.border='3px solid green'", new Object[] { Webelementval });
		} catch (Exception ex) { // dashed,dotted,groove,double,ridge
		}
	}

	public static WebElement CheckObjectVisibility(String propertyNameRDS, String propertyValueRDS,
			String objectTypeRDS, String pagename) throws Exception {

		WebElement WebElementVal = null;
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
		Properties prop = new Properties();
		try {
			scType = "n";
			scType = Functions.pro.getProperty("scType");
		} catch (Exception e) {
		}
		try {
			if (propertyNameRDS == null) {
				propertyNameRDS = "NP";
			}
			String s;
			String trim = s = (propertyNameRDS = propertyNameRDS.toUpperCase().trim());
			switch (s) {
			case "XPATHS": {
				return WebElementVal;
			}
			case "PARTENTFRAME": {
				if (objectTypeRDS.equalsIgnoreCase("FRAME")) {
					System.out.println("We are in parent frame");
					Framework.driver.switchTo().parentFrame();
					System.out.println("switch to..............................." + propertyValueRDS);
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "TAGNAME": {
				wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.tagName(propertyValueRDS)));
				if (Framework.driver.findElement(By.tagName(propertyValueRDS)).isDisplayed()) {
					List<WebElement> aTagsList = (List<WebElement>) Framework.driver.findElements(By.tagName("a"));
					WebElement requiredElement = aTagsList.get(0);
					requiredElement.click();
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "ID": {
				wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.id(propertyValueRDS)));
				wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.id(propertyValueRDS)));
				if (Framework.driver.findElement(By.id(propertyValueRDS)).isDisplayed()) {
					System.out.println(propertyValueRDS);
					WebElementVal = Framework.driver.findElement(By.id(propertyValueRDS));
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "CSS": {
				wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.cssSelector(propertyValueRDS)));
				if (Framework.driver.findElement(By.cssSelector(propertyValueRDS)).isDisplayed()) {
					WebElementVal = Framework.driver.findElement(By.cssSelector(propertyValueRDS));
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "FID": {
				if (objectTypeRDS.equalsIgnoreCase("FRAME")) {
					Framework.driver.switchTo().frame(Framework.driver.findElement(By.id(propertyValueRDS)));
					System.out.println("switch to..............................." + propertyValueRDS);
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "NAME": {
				wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.name(propertyValueRDS)));
				if (Framework.driver.findElement(By.name(propertyValueRDS)).isDisplayed()) {
					System.out.println(propertyValueRDS);
					WebElementVal = Framework.driver.findElement(By.name(propertyValueRDS));
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "FNAME": {
				if (objectTypeRDS.equalsIgnoreCase("FRAME")) {
					Framework.driver.switchTo().frame(Framework.driver.findElement(By.name(propertyValueRDS)));
					System.out.println("switch to..............................." + propertyValueRDS);
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "INDEX": {
				if (objectTypeRDS.equalsIgnoreCase("FRAME")) {
					int index = Integer.parseInt(propertyValueRDS);
					Framework.driver.switchTo().frame(index);
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "XPATH": {
				wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));
				wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
				WebElementVal = Framework.driver.findElement(By.xpath(propertyValueRDS));
				return WebElementVal;
			}
			case "PARTIALLINKTEXT": {
				wait.until(
						(Function) ExpectedConditions.presenceOfElementLocated(By.partialLinkText(propertyValueRDS)));
				if (Framework.driver.findElement(By.partialLinkText(propertyValueRDS)).isDisplayed()) {
					WebElementVal = Framework.driver.findElement(By.partialLinkText(propertyValueRDS));
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "LINKTEXT": {
				wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.linkText(propertyValueRDS)));
				if (Framework.driver.findElement(By.linkText(propertyValueRDS)).isDisplayed()) {
					WebElementVal = Framework.driver.findElement(By.linkText(propertyValueRDS));
					return WebElementVal;
				}
				return WebElementVal;
			}
			case "FXPATH": {
				if (objectTypeRDS.equalsIgnoreCase("FRAME")) {
					Framework.driver.switchTo().frame(Framework.driver.findElement(By.xpath(propertyValueRDS)));
					System.out.println("switch to..............................." + propertyValueRDS);
					return WebElementVal;
				}
				return WebElementVal;
			}
			default: {
				WebElementVal = null;
				break;
			}
			}
		} catch (Exception e) {
			Framework.errorsatus = "1";
			Framework.errorpagename = pagename;
			Framework.errorType = e.getClass().getName();
			try {
				Framework.errorMessage = e.getMessage().split("\n")[0];
			} catch (Exception e1) {
				Framework.errorMessage = null;
			}
			e.printStackTrace();
			Monitoring_FrameWork.SaveResult("Run Time Error ", " ");
		}
		return WebElementVal;
	}

	public static void Waitforcondition(String propertyNameRDS, final String propertyValueRDS, final String Condtion,
			final String srNo, final String pagename) throws Exception {
		try {
			System.out.println("Waiting for condition...............");
			final String[] Condtionsplit = Condtion.split("\\(");
			final String Condtionval1 = Condtionsplit[1];
			final String[] Condtionsplit2 = Condtionval1.split("\\)");
			final long waitval = Long.valueOf(Condtionsplit2[0]);
			final WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(waitval));
			By loc = null;
			final String s;
			final String trim = s = (propertyNameRDS = propertyNameRDS.toUpperCase().trim());
			switch (s) {
			case "CLASSNAME": {
				loc = By.className(propertyValueRDS);
				break;
			}
			case "TAGNAME": {
				loc = By.tagName(propertyValueRDS);
				break;
			}
			case "ID": {
				loc = By.id(propertyValueRDS);
				break;
			}
			case "NAME": {
				loc = By.name(propertyValueRDS);
				break;
			}
			case "XPATH": {
				loc = By.xpath(propertyValueRDS);
				break;
			}
			case "LINKTEXT": {
				loc = By.linkText(propertyValueRDS);
				break;
			}
			default: {
				loc = null;
				break;
			}
			}
			System.out.println("Locator is -----> " + loc + "  :" + propertyNameRDS + ":");
			if (loc != null) {
				if (Condtion.contains("invisibilityOfElement")) {
					System.out.println("waiting for invisibilityOfElement ------->" + new Date().toString());
					wait.until((Function) ExpectedConditions.invisibilityOfElementLocated(loc));
					System.out.println("waiting for invisibilityOfElement ------->" + new Date().toString());
				} else if (Condtion.contains("visibilityOfElement")) {
					System.out.println("waiting for visibilityOfElement ------->" + new Date().toString());
					wait.until((Function) ExpectedConditions.visibilityOfElementLocated(loc));
					System.out.println("waiting for visibilityOfElement ------->" + new Date().toString());
				} else if (Condtion.contains("presenceOfElement")) {
					System.out.println("waiting for Presence ------->" + new Date().toString());
					wait.until((Function) ExpectedConditions.presenceOfElementLocated(loc));
					System.out.println("waiting End for Presence ------->" + new Date().toString());
				} else if (Condtion.contains("elementToBeClickable")) {
					System.out.println("waiting for elementToBeClickable ------->" + new Date().toString());
					wait.until((Function) ExpectedConditions.elementToBeClickable(loc));
					System.out.println("waiting for elementToBeClickable ------->" + new Date().toString());
				}
			} else if (Condtion.contains("alertIsPresent")) {
				wait.until((Function) ExpectedConditions.alertIsPresent());
			}
		} catch (Exception e) {
			Framework.errorsatus = "1";
			Framework.errorpagename = pagename;
			e.printStackTrace();
			Monitoring_FrameWork.SaveResult("Locator not found", " ");
		}
	}

	static {
		Functions.pro = new Properties();
		Functions.path2 = System.getProperty("user.dir") + "//mf_web.properties";
	}
}
