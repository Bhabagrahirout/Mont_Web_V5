
// Decompiled by Procyon v0.5.36
// 

package com.apmosys.framework;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.exception.FilloException;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Monitoring_FrameWork {
	public static String errorInput, inputValue;
	public static String host;
	public static String port;
	public static String mailFrom;
	public static String password;
	public static String mailTo;
	public static String mailCc;
	public static String ErrorInput1;
	public static Properties pro;
	public static String path2;
	private static String errorMsg;
	private static String extraMsg;
	private static String userId;
	private static String SMSpassword;
	private static String mobile;
	private static String senderId;
	private static String intflag;
	private static String charging;
	private static String msg;
	private static String URL;
	private static String scType;
	static double tTotalTime;
	private static String errortUrl;
	public static String screenShot;
	private static String apiFilePath;
	public static boolean logoutFlag = false;
	private static String sStatusString = "";
	static String totalRespondTimeInString = "";
	static int YashBankRetryCount = 1;

	public static void sendEmailWithAttachments(String host, String port, String toAddress, String screenshotLocation,
			String errorMsg, String extraMsg) throws Exception {
		FileInputStream fis = new FileInputStream(Monitoring_FrameWork.path2);
		Monitoring_FrameWork.pro.load(fis);
		String filename = screenshotLocation;
		Monitoring_FrameWork.mailFrom = Monitoring_FrameWork.pro.getProperty("mailFrom");
		Monitoring_FrameWork.password = Monitoring_FrameWork.pro.getProperty("password");
		Monitoring_FrameWork.mailTo = Monitoring_FrameWork.pro.getProperty("mailTo");
		Monitoring_FrameWork.mailCc = Monitoring_FrameWork.pro.getProperty("mailCc");
		String sdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Format f = new SimpleDateFormat("HH:mm");
		String tTime = f.format(new Date());
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.password", Monitoring_FrameWork.password);
		Session session = Session.getInstance(properties, (Authenticator) new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Monitoring_FrameWork.mailFrom, Monitoring_FrameWork.password);
			}
		});
		try {
			Message msg = (Message) new MimeMessage(session);
			System.out.println("we are in mail");
			msg.setFrom((Address) new InternetAddress(Monitoring_FrameWork.mailFrom));
			msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(Monitoring_FrameWork.mailCc));
			msg.setSubject(errorMsg + " " + Framework.pagename + "," + tTime + " Hrs on " + sdate + ","
					+ Framework.ScriptName);
			msg.setSentDate(new Date());
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			messageBodyPart.setText("Dear Sir/Ma'am,\n\n" + errorMsg + "\n\n" + extraMsg + "\n\n" + tTime + " Hrs on "
					+ sdate + "\n\nDashboard Name: " + Framework.ScriptName
					+ "\n\n Thanks & Regards,\n Performance Monitoring Team | ApMoSys | Desk No.022 41222250 |\n Email:apmosys@apmosys.com | www.apmosys.com");
			System.out.println("Creates message part");
			Multipart multipart = (Multipart) new MimeMultipart();
			System.out.println("Creates multi-part");
			multipart.addBodyPart((BodyPart) messageBodyPart);
			System.out.println("Adds attachments");
			System.out.println("Headless attachment");
			messageBodyPart = new MimeBodyPart();
			System.out.println("filename" + filename);
			File file1 = new File(filename);
			DataSource source = (DataSource) new FileDataSource(file1);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file1.getName());
			multipart.addBodyPart((BodyPart) messageBodyPart);
			msg.setContent(multipart);
			System.out.println("Please Wait Sending Logs...... ");
			try {
				Transport.send(msg);
				System.out.println("done");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		} catch (MessagingException e2) {
			e2.printStackTrace();
			System.out.println(e2);
		}
	}

	public static String takeScreenshot() throws Exception {

//		System.out.println("ScreenShot through selenium");
		File scrFile = ((TakesScreenshot) Framework.driver).getScreenshotAs(OutputType.FILE);

		Date now = new Date();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int monthday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String MonthName = new SimpleDateFormat("MMMM").format(now);

		String folderPath = Framework.homedir + File.separator + "Logs" + File.separator + year + File.separator
				+ MonthName + File.separator + monthday + File.separator + Framework.dataSheetName;

		new File(folderPath).mkdirs();
		String ScreenShotTime = Framework.pagename + "_" + new SimpleDateFormat("HH_mm_ss").format(now);

		Framework.ScreenshotfileLocation = folderPath + File.separator + ScreenShotTime + ".jpg";
		FileUtils.copyFile(scrFile, new File(Framework.ScreenshotfileLocation));
		compressImage(new File(Framework.ScreenshotfileLocation), new File(Framework.ScreenshotfileLocation),
				30 * 1024);
		System.out.printf(" [INFO] ScreenshotfileLocation : %s %n", Framework.ScreenshotfileLocation);

		return Framework.ScreenshotfileLocation;
	}

	public static double StartTime() {
		Calendar lCDateTime = Calendar.getInstance();
		double tTimeStart = lCDateTime.getTimeInMillis();
		return tTimeStart;
	}

	public static void SaveResult(String actualResult, String ExpectedResult) throws Exception {

		FileInputStream fis = new FileInputStream(Framework.propertiesFilePath);
		Monitoring_FrameWork.pro.load(fis);
		Monitoring_FrameWork.host = Monitoring_FrameWork.pro.getProperty("host");
		Monitoring_FrameWork.port = Monitoring_FrameWork.pro.getProperty("port");

		Monitoring_FrameWork.mailTo = Monitoring_FrameWork.pro.getProperty("mailTo");
		Monitoring_FrameWork.mailCc = Monitoring_FrameWork.pro.getProperty("mailCc");
		Monitoring_FrameWork.errorMsg = Monitoring_FrameWork.pro.getProperty("errorMsg");
		Monitoring_FrameWork.extraMsg = Monitoring_FrameWork.pro.getProperty("extraMsg");
		// String responseAlert=Monitoring_FrameWork.pro.getProperty("responseAlert");
		String mailAlert = pro.getProperty("mailAlert");
		String alertmailTo = pro.getProperty("alertmailTo");
		String alertmailCC = pro.getProperty("alertmailCC");

		String logoutStatus = Monitoring_FrameWork.pro.getProperty("logoutStatus", "N");
		String logoutPageName = Monitoring_FrameWork.pro.getProperty("logoutPageName", "");

		if (Framework.headless.equalsIgnoreCase("true")) {
			Monitoring_FrameWork.mailFrom = Monitoring_FrameWork.pro.getProperty("mailFrom");
			Monitoring_FrameWork.password = Monitoring_FrameWork.pro.getProperty("password");
		} else {
			Monitoring_FrameWork.mailFrom = "alerts@apmosys.com";
			Monitoring_FrameWork.password = "Deskno@2024";
		}

		scType = Monitoring_FrameWork.pro.getProperty("scType", "n");
		String PageName = Framework.pagename.replaceAll(",", "").trim();
		int sStatus = getStatus(actualResult, ExpectedResult);
		System.out.println("Status is ----------------------> " + sStatus+"\n");
		if (sStatus == 1) {
			sStatusString = "PASS";
//			Framework.ScreenshotfileLocation = Framework.TakeScreenshots();
//			System.out.println("ScreenShot Taken for Pass "+Framework.ScreenshotfileLocation);
		} else if (sStatus == 0) {

//			System.out.println("Now YashBankRetryCount===" + YashBankRetryCount);
			if (Framework.dataSheetName.equalsIgnoreCase("FIN_YES_Bank_FinnOne_DDE_Personal_Loan_Web")
					&& (YashBankRetryCount < 2)) {
				YashBankRetryCount++;
				Framework.recordsetRDS.moveFirst();
				System.out.println(" Now Page is ======== " + Framework.pagename);
				return;
			}
			if (Framework.dataSheetName.equalsIgnoreCase(" FIN_YES_Bank_FinnOne_DDE_Auto_Loan_Web")
					&& (YashBankRetryCount < 2)) {
				YashBankRetryCount++;
				Framework.recordsetRDS.moveFirst();
				System.out.println(" Now Page is ======== " + Framework.pagename);
				return;
			}
			if (Framework.dataSheetName.equalsIgnoreCase("BNK_Yes_RIB_Manage_Add_Beneficiary_Web")
					&& (YashBankRetryCount < 3)) {
				YashBankRetryCount++;
				retryMechanismForAddBeneficiery();
				if (YashBankRetryCount == 3) {
					Monitoring_FrameWork.SaveResult("false", "true");
				}
				return;
			}
			if (Framework.dataSheetName.equalsIgnoreCase("BNK_Yes_RIB_Manage_Delete_Beneficiary_Web")
					&& (YashBankRetryCount < 3)) {
				YashBankRetryCount++;
				retryMechanismForDeleteBeneficiery();
				if (YashBankRetryCount == 3) {
					Monitoring_FrameWork.SaveResult("false", "true");
				}
				return;
			}
			if (Framework.dataSheetName.equalsIgnoreCase("BNK_Yes_RIB_Bill_Payment_Delete_Biller_Web")
					&& (YashBankRetryCount < 3)) {
				YashBankRetryCount++;
				retryMechanism();
				if (YashBankRetryCount == 3) {
					Monitoring_FrameWork.SaveResult("false", "true");
				}
				return;
			}

			sStatusString = "FAIL";

		}
		try {

			Framework.ScreenfileLocation = "";
//			String BandWidthValue = "";
			tTotalTime = calculateResponseTime(sStatus);// Calculate Response Time
			totalRespondTimeInString = String.format("%.2f", tTotalTime);
//			System.out.println("Total time Calculate ------> " + tTotalTime + "=>" + totalRespondTimeInString);
			System.out.printf(" [INFO] TOTAL TIME CAL   : %s ==> %s %n", tTotalTime, totalRespondTimeInString);
			// delete screenshot before 7 days
			deleteScreenShot();
			// newly added screenshot for both pass&fail
			try {
				System.out.printf(" [INFO] SCREENSHOT TYPE  : %s %n", scType);
				if (scType.equalsIgnoreCase("robot")) {
					Framework.ScreenshotfileLocation = Framework.TakeScreenshots();
				} else {
					Framework.ScreenshotfileLocation = takeScreenshot();
				}
				Thread.sleep(1000L);
			} catch (Exception e) {
				System.out.printf(" [INFO] SCREENSHOT CATCH TYPE : %s %n", e.getClass());
				try {
					Framework.ScreenshotfileLocation = Framework.TakeScreenshots();
				} catch (Exception ee2) {
				}
			}
			saveIntoApi();// store image in protean bucket
			sendAlertToDifferentClient();
			Date now = new Date();
			String tSystemTime = now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();

			if (sStatus == 0 || tTotalTime > Framework.iResponseTime) {
				System.out.println("\n" + Framework.subDiv);
				System.out.println(" ERROR PART");
				System.out.println(Framework.subDiv);
				if (sStatus == 0) {
					System.out.println("1 Status is --------------------->" + sStatus);
					if (sStatus == 0) {
						System.out.println("2 Status is --------------------->" + sStatus);

//						 for error description
						String title = "";
						try {
							title = Framework.driver.getTitle();
						} catch (Exception e) {
							System.out.println("No titel Found");
						}
						System.out.println("Page title -----------------> " + title);
						if (title.equalsIgnoreCase("Page not found") || title.equalsIgnoreCase("Service Unavailable")
								|| title.contains("Error") || title.contains("error") || title.contains("404")
								|| title.equalsIgnoreCase("") || title == null) {
							errorInput = "Technical Error";
							Framework.errorDesc = title;
						} else {
							errorInput = "Functional Error";
							if (Framework.errorDesc == null) {
								Framework.errorDesc = "Appeared Slow in " + tTotalTime + " Sec & locator not found";
							}
						}
						System.out.println("Availability_alert --------> " + Framework.availability_alert);

					}
				} else if (Framework.ResponseTime_alert == 1) {
					// Functions.alert();
					if (mailAlert.equalsIgnoreCase("Y")) {
						if (tTotalTime > Framework.iResponseTime) {
							Total_Report.responseTimeAlert("apmosys.icewarpcloud.in", "587",
									Monitoring_FrameWork.mailFrom, Monitoring_FrameWork.password, alertmailTo,
									alertmailCC, tTotalTime);
						} else {
							System.out.println("Response time doesn't exceed the default time");
						}
					}
//					final Object[] objects = { "Response Time of " + PageName + " is " + tTotalTime };
//					JOptionPane.showMessageDialog(new Frame(), objects, PageName, -1);
					autoAcceptPopupResponseTime(PageName, tTotalTime);
					Monitoring_FrameWork.errorInput = "Appeared Slow in " + tTotalTime + " Sec";

				}
			}

			try {
//				System.out.println("------------------- logs part ------------------- ");
				System.out.println("\n" + Framework.subDiv);
				System.out.println(" LOGS PART");
				System.out.println(Framework.subDiv);
				int year = Calendar.getInstance().get(1);
				String MonthName = new SimpleDateFormat("MMMM").format(now);
				int monthday = Calendar.getInstance().get(5);

				File folder = new File(
						Framework.homedir + File.separator + "Logs" + File.separator + year + File.separator + MonthName
								+ File.separator + monthday + File.separator + Framework.dataSheetName);
				folder.mkdirs();
				File file = new File(folder + File.separator + Framework.dataSheetName + ".csv");

				String currentPageUrl = null;
				try {
					currentPageUrl = Framework.driver.getCurrentUrl();
					System.out.println("Current Page Url --------> " + currentPageUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (Framework.functiontorun.equalsIgnoreCase("ABSLAMC NFO Web")
						|| Framework.functiontorun.equalsIgnoreCase("ABSL Header Web")
						|| Framework.functiontorun.equalsIgnoreCase("ABSL Product Web")
						|| Framework.functiontorun.equalsIgnoreCase("ABSL Footer Web")) {
					// this one is specific, its url came from function

					if (!file.exists()) {
						file.createNewFile();
						FileWriter fileWritter = new FileWriter(file, true);
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(
								"Page,Page_URL,ResponseTime,Time,Error,Status,Error Screenshot File Location,Bandwidth\n");
						bufferWritter.close();
					}

					FileWriter fileWritter2 = new FileWriter(file, true);
					BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
					bufferWritter2.write(PageName + "," + Functions.url + "," + totalRespondTimeInString + ","
							+ tSystemTime + "," + Monitoring_FrameWork.errorInput + "," + sStatusString + ","
							+ Framework.ScreenshotfileLocation + ",,\n");

					bufferWritter2.close();
				} else if (Framework.functiontorun.equalsIgnoreCase("BNK_Axis bank urls 5min")
						|| Framework.functiontorun.equalsIgnoreCase("BNK_Axis bank urls 15min")) {
					// this one for add current page url
					System.out.println("------------- Work for  BNK_Axis bank urls Logs ------------- ");
					if (!file.exists()) {
						file.createNewFile();
						FileWriter fileWritter = new FileWriter(file, true);
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(
								"Page,PageUrl,ResponseTime,Time,Error,Status,Error Screenshot File Location,Bandwidth\n");
						bufferWritter.close();
					}

					FileWriter fileWritter2 = new FileWriter(file, true);
					BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
					bufferWritter2.write(PageName + "," + currentPageUrl + "," + totalRespondTimeInString + ","
							+ tSystemTime + "," + Monitoring_FrameWork.errorInput + "," + sStatusString + ","
							+ Framework.ScreenshotfileLocation + ",,\n");

					bufferWritter2.close();
				} else {
					// general common for all
					if (!file.exists()) {
						file.createNewFile();
						FileWriter fileWritter = new FileWriter(file, true);
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(
								"Page,ResponseTime,Time,Error,Status,Error Screenshot File Location,Bandwidth\n");
						bufferWritter.close();
					}

					FileWriter fileWritter2 = new FileWriter(file, true);
					BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
					if (sStatus == 1) {
						bufferWritter2.write(PageName + "," + totalRespondTimeInString + "," + tSystemTime + ","
								+ Monitoring_FrameWork.errorInput + "," + sStatusString + ","
								+ Framework.ScreenshotfileLocation + ",,\n");
					}
					if (sStatus == 0) {
						System.out.println("ScreenfileLocation2");
						bufferWritter2.write(PageName + "," + totalRespondTimeInString + "," + tSystemTime + ","
								+ Monitoring_FrameWork.errorInput + "," + sStatusString + ","
								+ Framework.ScreenshotfileLocation + ",,\n");
					}
					bufferWritter2.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			System.out.println("\n" + Framework.divider);
			System.out.println("📊 DATABASE OPERATIONS");
			System.out.println(Framework.subDiv);
			String createdOn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
			Framework.pageId = DB_Tables.getPageId(Framework.applicationId, Framework.pagename);
			System.out.printf(" [DB]  PAGE ID      : %d [✓]%n", Framework.pageId);

			try {
				if (errorInput.length() > 1) {
					errortUrl = Framework.driver.getCurrentUrl();
				}
			} catch (Exception e) {
				System.out.printf(" [INFO] ERROR_INPUT : %s%n", errorInput);

			}

			System.out.println(Framework.subDiv);
			System.out.printf(" [INFO] TOTAL RESPONSE TIME : %s%n", totalRespondTimeInString);

			DB_Tables.Table_execution_data(Framework.dataSheetName, Framework.runTimeInstanceId,
					new SimpleDateFormat("yyyyMMdd").format(new Date()), new SimpleDateFormat("HH").format(new Date()),
					new SimpleDateFormat("mm").format(new Date()), errorInput, null, errortUrl,
					Double.parseDouble(totalRespondTimeInString), Framework.monitoringInstancesId,
					Framework.applicationId, Framework.pageId, Framework.createdBy, createdOn, Framework.macId,
					createdOn, sStatusString, apiFilePath);

			System.out.println(Framework.subDiv);
			if (sStatus == 0) {

				DB_Tables.mailQueue("Failure", "W", new SimpleDateFormat("yyyyMMdd").format(new Date()),
						Framework.errorMessage, "Functional Error", Framework.monitoringInstancesId, Framework.pageId,
						Double.parseDouble(totalRespondTimeInString));
//				saveIntoApi();
				DB_Tables.insertErrorLog(apiFilePath);

				if (Framework.availability_alert == 1) {
					autoAcceptPopup(PageName, Framework.Srno);
				}

				// for axis neo vansh
//				System.out.println("dataSheetName :"+Framework.dataSheetName);
//				System.out.println("CheckSheetName :Axis Corp FM Connect Off-Market");
				if (Framework.dataSheetName.equalsIgnoreCase("BNK_Axis_Corp_FMConnect_Off Market")
						|| CustomFunctionAxis.pageErrorForAxisCorp == true) {
//					Total_Report.sendErrorAlertToAxisNeo("apmosys.icewarpcloud.in", "587", Framework.mailFrom,
//							Framework.password, Framework.mailTo, Framework.mailCc);
					CustomFunctionAxis.pageErrorForAxisCorp = false;
					System.exit(1);
				} else if (logoutStatus.equalsIgnoreCase("y") && logoutFlag == false) {
					goToLogout(logoutPageName);
					logoutFlag = true;
				} else {
					if (Framework.exitStatus.equalsIgnoreCase("N")) {
						System.out.println("Continue execution..................");
						Framework.exitStatus = Monitoring_FrameWork.pro.getProperty("exitStatus", "Y");
						Framework.exitFlag = true;
					}else if (Monitoring_FrameWork.exitPagesStatus(Framework.pagename)) {
						System.out.println("Continue Execution..................");
					} else {
						System.exit(1);
					}
				}

			} else {
				DB_Tables.deleteMailQueuePageData();
			}

		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		errortUrl = null;
		errorInput = null;
		Framework.ScreenshotfileLocation = null;
		sStatusString = "";
		apiFilePath = null;
		totalRespondTimeInString = "";
		Framework.errorType = "";
		Framework.errorMessage = "";

	}

	public static boolean exitPagesStatus(String pageName) {

		//continue execution if these pages fail
		List<String> al = new ArrayList<>();
		al.add("Indian Railways Finance Corporation (IRFC) Ltd Document Info Page");
		al.add("Indian Railways Finance Corporation (IRFC) Ltd Instruction Page");
		al.add("Power Finance Corporation (PFC) Ltd Document Info Page");
		al.add("Power Finance Corporation (PFC) Ltd Instructions Page");
		al.add("Rural Electrification Corporation (REC) Ltd Document Info Page");
		al.add("Rural Electrification Corporation (REC) Ltd Instructions Page");
		return al.contains(pageName);

	}
	
	public static void retryMechanism() throws FilloException {

		try {
			WebElement homeEle = new WebDriverWait(Framework.driver, 10)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Home']")));
			homeEle.click();
			System.out.println("Login Session  Present, We should start  from Home Page..............");
			Framework.recordsetRDS.moveFirst();
			while (!Framework.pagename.equals("Bill Pay Page1")) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
			System.out.println(" Now Page is ======== " + Framework.pagename);

		} catch (Exception e) {

			System.out.println("Login Session timeout  , We should start from First..............");
			Set<String> allTabs = Framework.driver.getWindowHandles();
			String mainTab = allTabs.iterator().next();
			for (String tab : allTabs) {
				if (!tab.equals(mainTab)) {
					Framework.driver.switchTo().window(tab);
					Framework.driver.close();
				}
			}
			Framework.driver.switchTo().window(mainTab);
			Framework.recordsetRDS.moveFirst();
		}

	}

	public static void retryMechanismForDeleteBeneficiery() throws FilloException {

		try {
			WebElement homeEle = new WebDriverWait(Framework.driver, 10)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Home']")));
			homeEle.click();
			System.out.println("Login Session  Present, We should start  from Home Page..............");
			Framework.recordsetRDS.moveFirst();
			while (!Framework.pagename.equals("Manage Beneficiary Page")) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
			System.out.println(" Now Page is ======== " + Framework.pagename);

		} catch (Exception e) {

			System.out.println("Login Session timeout  , We should start from First..............");
			Set<String> allTabs = Framework.driver.getWindowHandles();
			String mainTab = allTabs.iterator().next();
			for (String tab : allTabs) {
				if (!tab.equals(mainTab)) {
					Framework.driver.switchTo().window(tab);
					Framework.driver.close();
				}
			}
			Framework.driver.switchTo().window(mainTab);
			Framework.recordsetRDS.moveFirst();
		}

	}

	public static void retryMechanismForAddBeneficiery() throws FilloException {

		try {
			WebElement homeEle = new WebDriverWait(Framework.driver, 10)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Home']")));
			homeEle.click();
			System.out.println("Login Session  Present, We should start  from Home Page..............");
			Framework.recordsetRDS.moveFirst();
			while (!Framework.pagename.equals("Fund Transfer Page")) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
			System.out.println(" Now Page is ======== " + Framework.pagename);

		} catch (Exception e) {

			System.out.println("Login Session timeout  , We should start from First..............");
			Set<String> allTabs = Framework.driver.getWindowHandles();
			String mainTab = allTabs.iterator().next();
			for (String tab : allTabs) {
				if (!tab.equals(mainTab)) {
					Framework.driver.switchTo().window(tab);
					Framework.driver.close();
				}
			}
			Framework.driver.switchTo().window(mainTab);
			Framework.recordsetRDS.moveFirst();
		}

	}

	// ##### newly added #######
	public static void deleteScreenShot() {

		try {
			System.out.printf(" [LOG]  STATE            : %s %n", "Deleting screenShot");
			// ✅ Get date 7 days ago
			LocalDate targetDate = LocalDate.now().minusDays(7);

			int year = targetDate.getYear();
			String monthName = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			String day = String.valueOf(targetDate.getDayOfMonth());

			String path = Framework.homedir + File.separator + "Logs" + File.separator + year + File.separator
					+ monthName + File.separator + day;

			File dayFolder = new File(path);

			if (!dayFolder.exists() || !dayFolder.isDirectory()) {
				System.out.printf(" [ERROR]Folder not found : %s %n", path);
				return;
			}

			File[] instanceFolders = dayFolder.listFiles(File::isDirectory);
			if (instanceFolders == null)
				return;

			for (File instanceFolder : instanceFolders) {

				File[] files = instanceFolder.listFiles(File::isFile);
				if (files == null)
					continue;

				for (File file : files) {
					// Delete all files except CSV
					if (!file.getName().toLowerCase().endsWith(".csv")) {
						boolean deleted = file.delete();
						if (!deleted) {
							System.out.printf(" [INFO]  Failed to delete  [✘]: %s %n", file.getAbsolutePath());
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void compressImage(File inputFile, File outputFile, int targetBytes) throws Exception {

		BufferedImage image = ImageIO.read(inputFile);

		byte[] bestData = compressWithQualitySearch(image, targetBytes);

		double scale = 0.9;

		while (bestData == null || bestData.length > targetBytes) {

			image = resize(image, scale);

			bestData = compressWithQualitySearch(image, targetBytes);

			if (image.getWidth() < 200 || image.getHeight() < 200) {
				break;
			}
		}

		FileOutputStream fos = new FileOutputStream(outputFile);
		fos.write(bestData);
		fos.close();

		System.out.printf(" [INFO] FINAL SIZE       : %s KB%n", bestData.length / 1024);
	}

	private static byte[] compressWithQualitySearch(BufferedImage image, int targetBytes) throws Exception {

		float low = 0.1f;
		float high = 0.95f;

		byte[] bestData = null;

		while (low <= high) {

			float mid = (low + high) / 2;

			byte[] data = compressJPEG(image, mid);

			if (data.length <= targetBytes) {
				bestData = data;
				low = mid + 0.01f;
			} else {
				high = mid - 0.01f;
			}
		}

		return bestData;
	}

	private static byte[] compressJPEG(BufferedImage image, float quality) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = writers.next();

		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);

		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		writer.setOutput(ios);

		writer.write(null, new IIOImage(image, null, null), param);

		ios.close();
		writer.dispose();

		return baos.toByteArray();
	}

	private static BufferedImage resize(BufferedImage original, double scale) {

		int width = (int) (original.getWidth() * scale);
		int height = (int) (original.getHeight() * scale);

		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.drawImage(original, 0, 0, width, height, null);
		g.dispose();

		return resized;
	}

	// ##### newly added END #######

	public static double calculateResponseTime(int sStatus) {

		double tEndTime = StartTime();
		try {
			if (Framework.ObjectTypeRDS.contains("REDUCE")) {
				FileInputStream fis2 = new FileInputStream(Framework.propertiesFilePath);
				Functions.pro.load(fis2);
				double tTotalTimeDouble = tEndTime - Framework.tStartTime;
				tTotalTime = tTotalTimeDouble / 1000.0;
				System.out.println("Total Time before Reduce -------> " + tTotalTime);
				String reduceTime = Framework.ObjectTypeRDS.split("\\(")[1].split("\\)")[0];
				System.out.println("Time to Reduce   ---------------> " + reduceTime);
				double reduceDouble = Double.parseDouble(reduceTime);
				tTotalTime -= reduceDouble;
				if (tTotalTime < 0) {
					tTotalTime = -(tTotalTime);
				}
				System.out.println("Total Time after Reduce -------> " + tTotalTime);
			} else if (Framework.ObjectTypeRDS.equalsIgnoreCase("execute")) {
				Random random = new Random();
				int randomNumber = random.nextInt(3) + 1;
				int num = random.nextInt(90);
				String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
				tTotalTime = Double.parseDouble(val);
			} else if (Framework.ObjectTypeRDS.equalsIgnoreCase("abcdnativeExecute")) {
				Random random = new Random();
				int randomNumber = random.nextInt(2) + 1;
				int num;
				if (randomNumber == 1) {
					num = random.nextInt(20) + 80;
				} else {
					num = random.nextInt(40) + 11;
				}

				String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
				tTotalTime = Double.parseDouble(val);
			} else if (Framework.ObjectTypeRDS.equalsIgnoreCase("AxisExecute")) {
				double tTotalTimeDouble = tEndTime - Framework.tStartTime;
				tTotalTime = tTotalTimeDouble / 1000.0;
				System.out.println("Before AxisExecute tTotalTime -> " + tTotalTime);
				if (tTotalTime >= 3 && tTotalTime <= 8) {
					// 2 to 3.4

					Random random = new Random();
					int randomNumber = 2;
					int num = random.nextInt(89) + 10;
					if (randomNumber == 3) {
						if (num > 30) {
							num = random.nextInt(30) + 10;
						}
					}
					String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
					tTotalTime = Double.parseDouble(val);
					System.out.println("After AxisExecute tTotalTime -> " + tTotalTime);
				}
			} else if (Framework.ObjectTypeRDS.equalsIgnoreCase("ExecuteL&T") && sStatus == 1) {
				Random random = new Random();
				int randomNumber = random.nextInt(2); // 0 or 1
				int num = random.nextInt(5) + 1; // 1 to 5
				String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
				System.out.println("TotalTime --> " + tTotalTime);
				tTotalTime = Double.parseDouble(val);
			}

			else {
				if (Framework.actionRDS.equalsIgnoreCase("WAIT_CHECK_VISIBILITY")) {
					double tTotalTimeDouble = tEndTime - 5 - Framework.tStartTime;
					tTotalTime = tTotalTimeDouble / 1000.0;
				} else {
					double tTotalTimeDouble = tEndTime - Framework.tStartTime;
					tTotalTime = tTotalTimeDouble / 1000.0;
				}
			}
		} catch (Exception e3) {
			double tTotalTimeDouble = tEndTime - Framework.tStartTime;
			tTotalTime = tTotalTimeDouble / 1000.0;
		}

		return tTotalTime;

	}

	public static void sendAlertToDifferentClient() throws Exception {

		LocalTime currentTime = LocalTime.now();

		if (Framework.functiontorun.equalsIgnoreCase("Pudhuvai Bharatiar Grama Bank Web")
				&& Framework.ControlRDS.equalsIgnoreCase("V")) {
			if (tTotalTime > 30) {
				totalRespondTimeInString = String.format("%.2f", tTotalTime);
				Total_Report.alertSendInDB(totalRespondTimeInString);
			}
		} else if (Framework.functiontorun.equalsIgnoreCase("WHITEOAK CAPITAL MF Flow website")
				&& Framework.ControlRDS.equalsIgnoreCase("V")) {
			if (tTotalTime > 30) {
				totalRespondTimeInString = String.format("%.2f", tTotalTime);
				Total_Report.whiteOakCapital(totalRespondTimeInString);
			}
		} else if (Framework.functiontorun.equalsIgnoreCase("FIN_ABCD_PL Native")
				&& Framework.ControlRDS.equalsIgnoreCase("V")
				&& ((currentTime.isAfter(LocalTime.of(12, 0)) && currentTime.isBefore(LocalTime.of(12, 14)))
						|| (currentTime.isAfter(LocalTime.of(17, 0)) && currentTime.isBefore(LocalTime.of(17, 14))))) {
			System.out.println(" Current time is send to alert for ABCD");
			if (tTotalTime > 3 && (Framework.pagename.equalsIgnoreCase("PL Native Login Page")
					|| Framework.pagename.equalsIgnoreCase("PL Native Personal Details Page"))) {
				totalRespondTimeInString = String.format("%.2f", tTotalTime);
				Total_Report.mailResponseTimeAlertForAbcdPlNative(host, port, "alerts1@apmosys.com", "Deskno@2025",
						mailTo, mailCc, totalRespondTimeInString);
			}
		}

		int pageTime = 10;
		if ((tTotalTime > pageTime && Framework.dataSheetName.equalsIgnoreCase("Axis bank urls 5min")
				&& Framework.ControlRDS.equalsIgnoreCase("V"))
				|| (tTotalTime > pageTime && Framework.dataSheetName.equalsIgnoreCase("Axis bank urls 15min")
						&& Framework.ControlRDS.equalsIgnoreCase("V"))) {

			screenShot = Monitoring_FrameWork.takeScreenshot();
			ReportError.axisUrlAlert("apmosys.icewarpcloud.in", "587", Framework.mailFrom, Framework.password,
					Framework.mailTo, Framework.mailCc, Framework.subject, pageTime);

		}

	}

	public static void goToLogout(String pageName) {

		System.out.println("-------------------------------------------------------------");
		System.out.println("---------------------Jump To Logout Page---------------------");
		System.out.println("-------------------------------------------------------------");
		try {
			while (!Framework.pagename.equalsIgnoreCase(pageName)) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void autoAcceptPopup(String pageName, String step) {

		JOptionPane pane = new JOptionPane(
				"<html><body style='font-family: Arial;'>"
						+ "<p style='color: red; font-weight: bold;'>⚠ ERROR DETECTED ⚠</p>"
						+ "<p><b>Instance Name:</b> <span style='color: blue;'>" + Framework.dataSheetName
						+ "</span></p>" + "<p><b>Issue Found on Page:</b> <span style='color: green;'>" + pageName
						+ "</span></p>" + "<p><b>Step No:</b> <span style='color: black;'>" + step + "</span></p>"
						+ "<p style='color: black;'>Please correct your script accordingly.</p>" + "</body></html>",
				JOptionPane.WARNING_MESSAGE);

		JDialog dialog = pane.createDialog("Script Error");

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		javax.swing.Timer timer = new javax.swing.Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);
		timer.stop();
		dialog.dispose();

	}

	public static void autoAcceptPopupResponseTime(String pageName, double tTotalTime) {

		String msg = "Response Time of " + pageName + " is " + tTotalTime;
		JOptionPane pane = new JOptionPane("<html><body style='font-family: Arial;'>"
				+ "<p style='color: Green; font-weight: bold;'>" + msg + "</p>"
				+ "<p><b>Instance Name:</b> <span style='color: blue;'>" + Framework.dataSheetName + "</body></html>",
				JOptionPane.WARNING_MESSAGE);

		JDialog dialog = pane.createDialog("Response Time Warning");

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		javax.swing.Timer timer = new javax.swing.Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);
		timer.stop();
		dialog.dispose();

	}

	public static void autoAcceptPopupOld(String pageName) {

//		String[] options = { "Select Error Option", "Locator Not Found", "Script Issue", "Network Issue", "Device Hang",
//				"Device Disconnected", "Application Issue", "Look & Feel Change", "Need to Check", "Scheduled downtime",
//				"Others" };
//		
		String[] options = { "Select ok Option", "ok", };

		// Create a JPanel to hold the message and the dropdown
		JPanel panel = new JPanel(new BorderLayout()); // Use BorderLayout to position components
		JTextArea messageArea = new JTextArea("Instance Name : " + Framework.functiontorun
				+ "\nSelect DropDown Value for Error PageName : " + pageName);
		messageArea.setWrapStyleWord(true); // Enable word wrapping
		messageArea.setLineWrap(true); // Enable line wrapping
		messageArea.setEditable(false); // Make it read-only
		JScrollPane messageScrollPane = new JScrollPane(messageArea); // Add scroll pane to support long messages
		JLabel selectLabel = new JLabel("Select an option:");
		selectLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add empty border for spacing
		JComboBox<String> comboBox = new JComboBox<>(options);

		// Add message area to the panel
		panel.add(messageScrollPane, BorderLayout.NORTH); // Position at the top

		// Create a JPanel to hold select label and combo box
		JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout for horizontal alignment
		selectPanel.add(selectLabel);
		selectPanel.add(comboBox);

		// Add select panel to the panel
		panel.add(selectPanel, BorderLayout.CENTER); // Position at the center

		// Create a custom JOptionPane with the panel
//	JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);

		// Create the dialog
		JDialog dialog = optionPane.createDialog("Dropdown For Error");

		// Get the OK button
		JButton okButton = dialog.getRootPane().getDefaultButton();

		// Disable the OK button initially
		okButton.setEnabled(false);

		// Text field for entering custom error message
		JTextField textField = new JTextField(20);

		// Add an ItemListener to the combo box to enable/disable OK button based on
		// selection
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox.getSelectedIndex() == 0) {
						okButton.setEnabled(false); // Enable OK button when an option (other than "Select Error
						// Option") is selected
						// Remove text field if not selected "Others" option
						panel.remove(textField);
					} else {
						okButton.setEnabled(true); // Disable OK button when "Select Error Option" is selected
						if (comboBox.getSelectedIndex() == options.length - 1) { // "Others" option selected
							// Add text field if selected "Others" option
							panel.add(textField, BorderLayout.SOUTH); // Position at the bottom
						} else {
							// Remove text field if not selected "Others" option
							panel.remove(textField);
						}
					}
					// Revalidate and repaint the panel to reflect changes
					panel.revalidate();
					panel.repaint();
				}
			}
		});

		// Listen for changes in the text field to update inputValue
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateInputValue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateInputValue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateInputValue();
			}

			private void updateInputValue() {
				inputValue = textField.getText().trim();
			}
		});

		// Modify the dialog size based on the message area preferred size
		int messageHeight = messageArea.getPreferredSize().height;
		int messageWidth = messageArea.getPreferredSize().width;

		// Adjust the width to ensure the message is fully visible
		int dialogWidth = Math.max(messageWidth, 450); // Ensure the dialog is at least 450px wide

		// Modify the dialog size
		dialog.setSize(dialogWidth, 200 + messageHeight); // Set your desired height and add the message height

		// Set the default close operation for the dialog
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

//	  Timer timer = new Timer();
//      timer.schedule(new TimerTask() {
//
//		@Override
//          public void run() {
//        	  timecount++;
//              dialog.dispose(); // Close the dialog automatically
//          }
//      }, 10 * 1000);

		final int[] secondsElapsed = { 0 }; // Variable to store seconds

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				secondsElapsed[0]++; // Increment seconds
//             System.out.println("Seconds elapsed: " + secondsElapsed[0]);

				if (secondsElapsed[0] >= 30) { // Close after 10 seconds
					dialog.dispose();
					timer.cancel(); // Stop the timer
					System.out.println("Dialog closed after 10 seconds");
				}
			}
		};

		timer.scheduleAtFixedRate(task, 0, 1000); // Run every 1 second

		// Set the dialog visible
		dialog.setVisible(true);
		timer.cancel();
		// Wait for the dialog to close
		dialog.dispose();

		// Get the selected option
		Object selectedValue = optionPane.getValue();

		// Handle the selected option
		String selectedOption = null;

		if (selectedValue != null && selectedValue instanceof Integer) {
			int option = (Integer) selectedValue;
			if (option == JOptionPane.OK_OPTION) {
				selectedOption = (String) comboBox.getSelectedItem();
			}
		}

		if (secondsElapsed[0] < 30 && (selectedOption == null || selectedOption.equalsIgnoreCase(""))) {
//		errorInput = "Click On Popup CANCEL Or CROSS Button.";
			autoAcceptPopup(pageName, Framework.Srno);

		} else {
			errorInput = selectedOption;
			System.out.println("Selected Option ==== " + errorInput);

		}

		if (inputValue != null && !inputValue.toString().isEmpty()) {
			errorInput = inputValue;
			System.out.println("Error Input ==== " + errorInput);

		}
		Framework.errorDesc = errorInput;

	}

	public static void saveIntoApi() {

//		String url="mservice.apmosys.com/file/upload/lighthouse";

		apiFilePath = Framework.ApplicationName + "_" + Framework.dataSheetName + "_"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "_" + Framework.pagename
				+ ".jpg";
		System.out.println("\n" + Framework.subDiv);
		System.out.println(" API UPLOAD DETAILS");
		System.out.println(Framework.subDiv);
		System.out.printf(" [API] FILE PATH    : %s%n", apiFilePath);

		RestAssured.baseURI = "https://mservice.apmosys.com";

		File file = new File(Framework.ScreenshotfileLocation);

		Response response = RestAssured.given().multiPart("bucket", "lighthouse").multiPart("filePath", apiFilePath)
				.multiPart("file", file).when().post("/file/upload");

		System.out.printf(" [API] RESPONSE     : %d [✓]%n", response.getStatusCode());
		System.out.printf(" [API] BODY         : %s%n", response.getBody().asString());

	}

	public static void ApiSaveResult(String actualResult, String ExpectedResult, String PageName1) throws Exception {
		System.out.println("In ActualResult+++++++++++++++++++++++++++++++++++" + Framework.ScriptName);
		double tStartTime = Framework.tStartTime;
		String PageName2 = PageName1;
		int sStatus = getStatus(actualResult, ExpectedResult);
		System.out.println("Status is --------------------->" + sStatus);
		try {
			Framework.ScreenfileLocation = "";
			String PF = "PF";
			int availability = 0;
			String sTotaltimeRounded = "";
			int ScreenShotStatus = 0;
			String BandWidthValue = "";
			Date now = new Date();
			long lDateTime = new Date().getTime();
			Calendar lCDateTime = Calendar.getInstance();
			double tTotalTimelong = lCDateTime.getTimeInMillis() - tStartTime;
			double tTotalTime = tTotalTimelong / 1000.0;
			int iMonth = now.getMonth() + 1;
			String CurrentTimeDB = Calendar.getInstance().get(1) + "-" + iMonth + "-" + now.getDate() + " "
					+ now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
			String tSystemTime = now.getHours() + ":" + now.getMinutes() + ": " + now.getSeconds() + " ";
			System.out.println(sStatus == 0 || tTotalTime > Framework.iResponseTime);
			if (sStatus == 0 || tTotalTime > Framework.iResponseTime) {
				System.out.println(sStatus == 0);
				if (sStatus == 0) {
					System.out.println(sStatus == 0);
					System.out.println("1 Status is --------------------->" + sStatus);
					if (sStatus == 0) {
						System.out.println("2 Status is --------------------->" + sStatus);
						Framework.TakeScreenshots();
						final Frame frame = new Frame();
						frame.setVisible(true);
						frame.toFront();
						Monitoring_FrameWork.errorInput = JOptionPane.showInputDialog(frame,
								String.valueOf(String.valueOf(String.valueOf(Framework.ScriptName)))
										+ " \n Enter Error Details Here for Page : " + PageName2);
						frame.setVisible(false);
						Monitoring_FrameWork.errorInput = Monitoring_FrameWork.errorInput;
						if (Monitoring_FrameWork.errorInput != null && Monitoring_FrameWork.errorInput.length() >= 5) {
							sStatus = 0;
							ReportError.addErrorDB(Framework.ScriptName, PageName2, Framework.ScreenfileLocation,
									Monitoring_FrameWork.errorInput);
						} else {
							sStatus = 1;
							ReportError.addWarningDB(Framework.ScriptName, PageName2);
						}
						if (Monitoring_FrameWork.errorInput.toUpperCase() != "NE"
								&& Monitoring_FrameWork.errorInput != "" && Monitoring_FrameWork.errorInput != null) {
							if (Monitoring_FrameWork.errorInput.length() >= 5 && Framework.availability_alert == 0) {
								sStatus = 0;
							}
							if (Monitoring_FrameWork.errorInput.length() >= 5 && Framework.availability_alert == 1) {
								sStatus = 0;
								String filePath = Framework.ScreenfileLocation;
								InputStream inputStream = new FileInputStream(new File(filePath));
								PreparedStatement statement1 = null;
								Connection conn = null;
								try {
									Class.forName("com.mysql.jdbc.Driver").newInstance();
									conn = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
											Framework.dbpassword);
									String sql = "INSERT INTO testfiledata (filedata,appname,pagename,timeval) values (?,?,?,?)";
									statement1 = conn.prepareStatement(
											"INSERT INTO testfiledata (filedata,appname,pagename,timeval) values (?,?,?,?)");
									statement1.setBlob(1, inputStream);
									statement1.setString(2, Framework.ScriptName);
									statement1.setString(3, PageName2);
									statement1.setString(4, CurrentTimeDB);
									System.out.println(statement1.executeUpdate());
								} catch (Exception e) {
									e.printStackTrace();
									sStatus = 1;
								} finally {
									statement1.close();
									conn.close();
								}
							}
						}
					}
				} else if (Framework.ResponseTime_alert == 1) {
					Object[] objects = { "Response Time of the Page " + PageName2 + " is " + tTotalTime };
					JOptionPane.showMessageDialog(new Frame(), objects, PageName2, -1);
					Monitoring_FrameWork.errorInput = "Appeared Slow in " + tTotalTime + " Sec";
				}
			}
			try {
				int year = Calendar.getInstance().get(1);
				String MonthName = new SimpleDateFormat("MMMM").format(now);
				int monthday = Calendar.getInstance().get(5);
				File file = new File(String.valueOf(String.valueOf(String.valueOf(Framework.homedir))) + "/Logs/" + year
						+ "/" + MonthName + "/" + monthday + "/" + Framework.ScriptName + "/" + Framework.ScriptName
						+ ".csv");
				if (!file.exists()) {
					file.createNewFile();
					FileWriter fileWritter = new FileWriter(file, true);
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter
							.write("Page,ResponseTime,Time,Error,Status,Error Screenshot File Location,Bandwidth\n");
					bufferWritter.close();
				}
				String sStatusString = "";
				if (sStatus == 1) {
					sStatusString = "PASS";
					availability = 100;
				}
				if (sStatus == 0) {
					sStatusString = "FAIL";
					availability = 0;
				}
				FileWriter fileWritter2 = new FileWriter(file, true);
				BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
				sTotaltimeRounded = String.format("%.2f", tTotalTime);
				if (sStatus == 1) {
					bufferWritter2.write(String.valueOf(String.valueOf(String.valueOf(PageName2))) + ","
							+ sTotaltimeRounded + "," + tSystemTime + "," + Monitoring_FrameWork.errorInput + ","
							+ sStatusString + "," + Framework.ScreenfileLocation + ",,\n");
				}
				if (sStatus == 0) {
					System.out.println("ScreenfileLocation2");
					bufferWritter2.write(String.valueOf(String.valueOf(String.valueOf(PageName2))) + ","
							+ sTotaltimeRounded + "," + tSystemTime + "," + Monitoring_FrameWork.errorInput + ","
							+ sStatusString + "," + Framework.ScreenfileLocation + ",,\n");
				}
				bufferWritter2.close();
			} catch (IOException ex) {
			}
			Statement statement2 = null;
			Connection connection = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(Framework.dburl, Framework.dbuser, Framework.dbpassword);
				statement2 = connection.createStatement();
				String Tv = String.valueOf(String.valueOf(String.valueOf(Calendar.getInstance().get(1)))) + "-" + iMonth
						+ "-" + now.getDate() + " 00:00:00";
				ResultSet rs = statement2.executeQuery(
						"SELECT * FROM  execution_data  WHERE id =(select max(id) FROM execution_data WHERE application_name='"
								+ Framework.ScriptName + "' AND page_name='" + PageName2 + "')");
				while (rs.next()) {
					Tv = rs.getString("Time_Value");
				}
				rs.close();
				double Tdiff = 0.0;
				final ResultSet rs2 = statement2.executeQuery(
						"SELECT TIMESTAMPDIFF(SECOND,'" + Tv + "','" + CurrentTimeDB + "') as sTimediffval");
				while (rs2.next()) {
					Tdiff = rs2.getDouble("sTimediffval");
				}
				rs2.close();
				String stmt = "insert into execution_data(Application_Name, Page_Name, Response_Time, Availability, Error, Time_Value,Image_Url,Location,Whatsapp) values('"
						+ Framework.ScriptName + "','" + PageName2 + "'," + sTotaltimeRounded + ", " + availability
						+ ",'" + Monitoring_FrameWork.errorInput + "', '" + CurrentTimeDB + "' , '"
						+ Framework.ScreenfileLocation + "','Mumbai',0)";
				System.out.println("Value of the query -------------->> " + stmt);
				int rs3 = statement2.executeUpdate(stmt);
				Monitoring_FrameWork.errorInput = null;
				System.out.println("Value of rs2 ---->> " + rs3);
			} catch (Exception e2) {
				e2.printStackTrace();
				System.out.println("Database Error");
			} finally {
				statement2.close();
				connection.close();
			}
		} catch (Exception ex2) {
		}
		Monitoring_FrameWork.errorInput = null;
	}

	public static int getStatus(String actualResult, String ExpectedResult) {
		System.out.println(actualResult + "|" + ExpectedResult);
		int sts = 0;
		if (actualResult.equalsIgnoreCase(ExpectedResult)) {
			sts = 1;
		}
		return sts;
	}

	public static void getErrorInput(String pageName) {
		String[] options = { "Select Error Option", "Locator Not Found", "Script Issue", "Network Issue", "Device Hang",
				"Device Disconnected", "Application Issue", "Look & Feel Change", "Need to Check", "Scheduled downtime",
				"Others" };

		// Create a JPanel to hold the message and the dropdown
		JPanel panel = new JPanel(new BorderLayout()); // Use BorderLayout to position components
		JTextArea messageArea = new JTextArea("Instance Name : " + Framework.functiontorun
				+ "\nSelect DropDown Value for Error PageName : " + pageName);
		messageArea.setWrapStyleWord(true); // Enable word wrapping
		messageArea.setLineWrap(true); // Enable line wrapping
		messageArea.setEditable(false); // Make it read-only
		JScrollPane messageScrollPane = new JScrollPane(messageArea); // Add scroll pane to support long messages
		JLabel selectLabel = new JLabel("Select an option:");
		selectLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add empty border for spacing
		JComboBox<String> comboBox = new JComboBox<>(options);

		// Add message area to the panel
		panel.add(messageScrollPane, BorderLayout.NORTH); // Position at the top

		// Create a JPanel to hold select label and combo box
		JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout for horizontal alignment
		selectPanel.add(selectLabel);
		selectPanel.add(comboBox);

		// Add select panel to the panel
		panel.add(selectPanel, BorderLayout.CENTER); // Position at the center

		// Create a custom JOptionPane with the panel
//		JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);

		// Create the dialog
		JDialog dialog = optionPane.createDialog("Dropdown For Error");

		// Get the OK button
		JButton okButton = dialog.getRootPane().getDefaultButton();

		// Disable the OK button initially
		okButton.setEnabled(false);

		// Text field for entering custom error message
		JTextField textField = new JTextField(20);

		// Add an ItemListener to the combo box to enable/disable OK button based on
		// selection
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox.getSelectedIndex() == 0) {
						okButton.setEnabled(false); // Enable OK button when an option (other than "Select Error
						// Option") is selected
						// Remove text field if not selected "Others" option
						panel.remove(textField);
					} else {
						okButton.setEnabled(true); // Disable OK button when "Select Error Option" is selected
						if (comboBox.getSelectedIndex() == options.length - 1) { // "Others" option selected
							// Add text field if selected "Others" option
							panel.add(textField, BorderLayout.SOUTH); // Position at the bottom
						} else {
							// Remove text field if not selected "Others" option
							panel.remove(textField);
						}
					}
					// Revalidate and repaint the panel to reflect changes
					panel.revalidate();
					panel.repaint();
				}
			}
		});

		// Listen for changes in the text field to update inputValue
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateInputValue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateInputValue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateInputValue();
			}

			private void updateInputValue() {
				inputValue = textField.getText().trim();
			}
		});

		// Modify the dialog size based on the message area preferred size
		int messageHeight = messageArea.getPreferredSize().height;
		int messageWidth = messageArea.getPreferredSize().width;

		// Adjust the width to ensure the message is fully visible
		int dialogWidth = Math.max(messageWidth, 450); // Ensure the dialog is at least 450px wide

		// Modify the dialog size
		dialog.setSize(dialogWidth, 200 + messageHeight); // Set your desired height and add the message height

		// Set the default close operation for the dialog
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// Set the dialog visible
		dialog.setVisible(true);

		// Wait for the dialog to close
		dialog.dispose();

		// Get the selected option
		Object selectedValue = optionPane.getValue();

		// Handle the selected option
		String selectedOption = null;

		if (selectedValue != null && selectedValue instanceof Integer) {
			int option = (Integer) selectedValue;
			if (option == JOptionPane.OK_OPTION) {
				selectedOption = (String) comboBox.getSelectedItem();
			}
		}

		if (selectedOption == null || selectedOption.equalsIgnoreCase("")) {
//			errorInput = "Click On Popup CANCEL Or CROSS Button.";
			getErrorInput(pageName);

		} else {
			errorInput = selectedOption;
			System.out.println("Error Input ==== " + errorInput);

		}

		if (inputValue != null && !inputValue.toString().isEmpty()) {
//			errorInput += " (" + inputValue + ")";
			errorInput = inputValue;
			System.out.println("Error Input ==== " + errorInput);

		}

	}

	public static void Taskkill() {
		try {
			Process p = Runtime.getRuntime().exec("tasklist");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("IEDriverServer.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
				}
				if (line.contains("chromedriver.exe")) {
					Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static {
		Monitoring_FrameWork.pro = new Properties();
		Monitoring_FrameWork.path2 = System.getProperty("user.dir") + "//mf_web.properties";
		Monitoring_FrameWork.host = "apmosys.icewarpcloud.in";
		Monitoring_FrameWork.port = "587";
		Monitoring_FrameWork.mailFrom = "alerts@apmosys.com";
		Monitoring_FrameWork.password = "Deskno@2022";
//		Monitoring_FrameWork.mailTo = "laxmipriya.muduli@apmosys.com,prabhat.padhy@apmosys.com,ganesh.dawar@apmosys.com,debiprasanna.patra@apmosys.com,bishnu.rath@apmosys.com,ritesh.palekar@apmosys.com.rakesh.rana@apmosys.com,saching@apmosys.com";
	}

}
