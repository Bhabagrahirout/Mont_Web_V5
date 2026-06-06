
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


    // ─────────────────────────────────────────────────────────────────────────
    // STEP 2 REFACTOR — Actions() now delegates to ActionDispatcher.
    //
    // The 7900-line method body has been split into focused classes:
    //   BrowserActions, WaitActions, AlertActions, VerifyActions,
    //   DropdownDateActions, ElementLocator, ActionDispatcher.
    //
    // All existing callers (Framework, custom functions, etc.) continue to
    // call Functions.Actions() — this method just forwards to the dispatcher.
    //
    // The original method body is preserved in Functions(old).java in the
    // repo for reference. Delete it once the team is happy with the refactor.
    // ─────────────────────────────────────────────────────────────────────────

    public static void Actions(String controlRDS, String objectTypeRDS,
            String propertyNameRDS, String propertyValueRDS,
            String dataFieldRDS, String actionRDS,
            String srNo, int fieldStatus, String pageName) throws Exception {

        ActionDispatcher.dispatch(controlRDS, objectTypeRDS, propertyNameRDS,
                propertyValueRDS, dataFieldRDS, actionRDS,
                srNo, fieldStatus, pageName);
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
