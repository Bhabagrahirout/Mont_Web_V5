package com.apmosys.framework;

import java.awt.Frame;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Total_Report {

	public static void responseTimeAlert(String host, String port, String userName, String password, String toAddress,
			String mailCc, double responseTime) throws AddressException, MessagingException {

		String message = "Dear Sir/Ma'am,<br/><br/> Response Time of " + Framework.pagename + " is " + responseTime
//				+ "<br/> Page Name : "+Framework.pagename+""
//				+ "<br/> Error Msg : "+Monitoring_FrameWork.ErrorInput+" "
				+ "<br/> Application Type : web" + "<br/> Application Name : " + Framework.functiontorun + " "
				// + "<br/> URL : "+Framework.driver.getCurrentUrl()+" "
				+ "<br/> Time : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
//			    + "<br/> <br/> Kindly find attachment for error screenshot.";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);
		Message msg = (Message) new MimeMessage(session);
		msg.setFrom((Address) new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
		if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
		}

		msg.setSubject("Response time alert (" + Framework.dataSheetName + ")");

		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		System.out.println("Creates message part");
//		messageBodyPart.setText( message);

		String signature = "<br/><br/><br/><br/>Thanks and Regards,<br/> "
				+ "ApMoSys | Performance Monitoring Team<br/>"
				+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
				+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";
		String Finalmessage = message + signature;

		messageBodyPart.setContent(Finalmessage, "text/html");
		Multipart multipart = (Multipart) new MimeMultipart();
		// for attach screenshot
		// MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//		 DataSource source = new FileDataSource( new File( Framework.ScreenfileLocation));
//		 messageBodyPart2.setDataHandler(new DataHandler(source));
//		 messageBodyPart2.setFileName(new File( Framework.ScreenfileLocation).getName());
		System.out.println("Creates multi-part");

		multipart.addBodyPart((BodyPart) messageBodyPart);
		// multipart.addBodyPart((BodyPart) messageBodyPart2);

		msg.setContent(multipart);
		System.out.println("Sending mail....");
		Transport.send(msg);
		System.out.println("Mail has been sent....");
	}
	
	
	public static void mailResponseTimeAlertForAbcdPlNative(String host, String port, String userName, String password, String toAddress,
			String mailCc, String responseTime) throws AddressException, MessagingException {

		String message = "Dear Sir/Ma'am,<br/><br/> " + Framework.pagename + " is taking more than 3 seconds to appear."
				+ "<br/> Response Time : "+responseTime+""
//				+ "<br/> Error Msg : "+Monitoring_FrameWork.ErrorInput+" "
				+ "<br/> Application Type : Web" + "<br/> Application Name : " + Framework.functiontorun + " "
				// + "<br/> URL : "+Framework.driver.getCurrentUrl()+" "
				+ "<br/> Time : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
//			    + "<br/> <br/> Kindly find attachment for error screenshot.";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);
		Message msg = (Message) new MimeMessage(session);
		msg.setFrom((Address) new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
		if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
		}

		msg.setSubject(Framework.pagename+" Slowness alert");

		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		System.out.println("Creates message part");
//		messageBodyPart.setText( message);

		String signature = "<br/><br/><br/><br/>Thanks and Regards,<br/> "
				+ "ApMoSys | Performance Monitoring Team<br/>"
				+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
				+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";
		String Finalmessage = message + signature;

		messageBodyPart.setContent(Finalmessage, "text/html");
		Multipart multipart = (Multipart) new MimeMultipart();
		// for attach screenshot
		// MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//		 DataSource source = new FileDataSource( new File( Framework.ScreenfileLocation));
//		 messageBodyPart2.setDataHandler(new DataHandler(source));
//		 messageBodyPart2.setFileName(new File( Framework.ScreenfileLocation).getName());
		System.out.println("Creates multi-part");

		multipart.addBodyPart((BodyPart) messageBodyPart);
		// multipart.addBodyPart((BodyPart) messageBodyPart2);

		msg.setContent(multipart);
		System.out.println("Sending mail....");
		Transport.send(msg);
		System.out.println("Mail has been sent....");
	}

	public static void sendEmailAlert(String host, String port, String userName, String password, String toAddress,
			String message, String mailCc) throws AddressException, MessagingException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		final Session session = Session.getInstance(properties, auth);
		final Message msg = (Message) new MimeMessage(session);
		msg.setFrom((Address) new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
		msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
		msg.setSubject("Alert For HDFC MF ETF");
		msg.setSentDate(new Date());
		final MimeBodyPart messageBodyPart = new MimeBodyPart();
		System.out.println("Creates message part");
//		messageBodyPart.setText( message);

		String signature = "<br/><br/>Thanks and Regards,<br/> " + "ApMoSys | Performance Monitoring Team<br/>"
				+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
				+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";
		String Finalmessage = "Dear Sir/Ma'am,<br/><br/> " + message + signature;

		messageBodyPart.setContent(Finalmessage, "text/html");
		final Multipart multipart = (Multipart) new MimeMultipart();
		System.out.println("Creates multi-part");
		multipart.addBodyPart((BodyPart) messageBodyPart);
		msg.setContent(multipart);
		System.out.println("Sending mail....");
		Transport.send(msg);
		System.out.println("Mail has been sent....");
	}

	public static void sendErrorAlertToAxisNeo( String host,  String port,  String userName,
			 String password,  String toAddress, String mailCc) throws AddressException, MessagingException {

//		String message = "Dear Sir/Ma'am,<br/><br/> Page Name : " + Framework.pagename + "" + "<br/> Error Msg : "
//				+ Monitoring_FrameWork.errorInput + " " + "<br/> Application Type : Web" + "<br/> Application Name : "
//				+ Framework.functiontorun + " " + "<br/> URL : " + Framework.driver.getCurrentUrl() + " "
//				+ "<br/> Time : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())
//				+ "<br/> <br/> Kindly find attachment for error screenshot.";

		String message = "Dear Sir/Ma'am,<br/><br/> " + Framework.pagename
				+ " is not working. Please find the attached screenshot & accordingly take action<br/>";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);
		Message msg = (Message) new MimeMessage(session);
		msg.setFrom((Address) new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
		if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
		}

		msg.setSubject(Framework.pagename + " not visible");

		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		System.out.println("Creates message part");
//		messageBodyPart.setText( message);

		String signature = "<br/><br/>Best regards,<br/>" + "ApMoSys Technologies<br/>";
		String Finalmessage = message + signature;

		messageBodyPart.setContent(Finalmessage, "text/html");
		Multipart multipart = (Multipart) new MimeMultipart();
		// for attach screenshot
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		DataSource source = new FileDataSource(new File(Framework.ScreenshotfileLocation));
		messageBodyPart2.setDataHandler(new DataHandler(source));
		messageBodyPart2.setFileName(new File(Framework.ScreenshotfileLocation).getName());
		System.out.println("Creates multi-part");

		multipart.addBodyPart((BodyPart) messageBodyPart);
		multipart.addBodyPart((BodyPart) messageBodyPart2);

		msg.setContent(multipart);
		System.out.println("Sending mail....");
		Transport.send(msg);
		System.out.println("Mail has been sent....");

	}

	public static void sendErrorAlertOnMail( String host,  String port,  String userName,
			 String password,  String toAddress, String mailCc) throws AddressException, MessagingException {

		String message = "Dear Sir/Ma'am,<br/><br/> Page Name : " + Framework.pagename + "" + "<br/> Error Msg : "
				+ Monitoring_FrameWork.errorInput + " " + "<br/> Application Type : Web" + "<br/> Application Name : "
				+ Framework.functiontorun + " " + "<br/> URL : " + Framework.driver.getCurrentUrl() + " "
				+ "<br/> Time : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())
				+ "<br/> <br/> Kindly find attachment for error screenshot.";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);
		Message msg = (Message) new MimeMessage(session);
		msg.setFrom((Address) new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
		if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
		}

		msg.setSubject("Validation Failed Alert (" + Framework.dataSheetName + ")");

		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		System.out.println("Creates message part");
//		messageBodyPart.setText( message);

		String signature = "<br/><br/><br/><br/>Thanks and Regards,<br/> "
				+ "ApMoSys | Performance Monitoring Team<br/>"
				+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
				+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";
		String Finalmessage = message + signature;

		messageBodyPart.setContent(Finalmessage, "text/html");
		Multipart multipart = (Multipart) new MimeMultipart();
		// for attach screenshot
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		DataSource source = new FileDataSource(new File(Framework.ScreenshotfileLocation));
		messageBodyPart2.setDataHandler(new DataHandler(source));
		messageBodyPart2.setFileName(new File(Framework.ScreenshotfileLocation).getName());
		System.out.println("Creates multi-part");

		multipart.addBodyPart((BodyPart) messageBodyPart);
		multipart.addBodyPart((BodyPart) messageBodyPart2);

		msg.setContent(multipart);
		System.out.println("Sending mail....");
		Transport.send(msg);
		System.out.println("Mail has been sent....");
	}

	public static void sendErrorAlertToAxis(final String host, final String port, final String userName,
			final String password, final String toAddress, String mailCc) throws AddressException, MessagingException {

		String message = "Dear Sir/Ma'am,<br/><br/> Page Name : " + Framework.pagename + "" + "<br/> Error Msg : "
				+ Monitoring_FrameWork.errorInput + " " + "<br/> Application Type : Web" + "<br/> Application Name : "
				+ Framework.functiontorun + " " + "<br/> URL : " + Framework.driver.getCurrentUrl() + " "
				+ "<br/> Time : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())
				+ "<br/> <br/> Kindly find attachment for error screenshot.";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);
		Message msg = (Message) new MimeMessage(session);
		msg.setFrom((Address) new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
		if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
		}

		msg.setSubject("Validation Failed Alert (" + Framework.dataSheetName + ")");

		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		System.out.println("Creates message part");
//		messageBodyPart.setText( message);

		String signature = "<br/><br/><br/><br/>Thanks and Regards,<br/> "
				+ "ApMoSys | Performance Monitoring Team<br/>"
				+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
				+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";
		String Finalmessage = message + signature;

		messageBodyPart.setContent(Finalmessage, "text/html");
		Multipart multipart = (Multipart) new MimeMultipart();
		// for attach screenshot
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		DataSource source = new FileDataSource(new File(Framework.ScreenshotfileLocation));
		messageBodyPart2.setDataHandler(new DataHandler(source));
		messageBodyPart2.setFileName(new File(Framework.ScreenshotfileLocation).getName());
		System.out.println("Creates multi-part");

		multipart.addBodyPart((BodyPart) messageBodyPart);
		multipart.addBodyPart((BodyPart) messageBodyPart2);

		msg.setContent(multipart);
		System.out.println("Sending mail....");
		Transport.send(msg);
		System.out.println("Mail has been sent....");
	}

	public static void checkNumber(String propertyValueRDS, String dataFieldRDS) throws Exception {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));
		Frame frame = new Frame();
		int validation = 0;
		int popUpCount = 0;

		// String rowXpath = "//table/tbody/tr";
		String rowXpath = propertyValueRDS;
		// String selectXpath = "//select[@id=\"viewStatusSearchOptions\"]";
		String selectXpath = dataFieldRDS.split("#")[0];// select class xpath

		List<WebElement> totalRow = Framework.driver.findElements(By.xpath(rowXpath));
		System.out.println("List Size is === " + totalRow.size());

		if (totalRow.size() < 10) {
			frame.setVisible(true);
			frame.toFront();
			JOptionPane.showMessageDialog(frame, totalRow.size() + " cheque number present out 10");
		}
		List<String> listOfcheckNumber = new ArrayList<String>();

		for (int i = 1; i <= totalRow.size(); i++) {

			String checkNumber = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rowXpath + "[" + i + "]/td[2]")))
					.getText();
			System.out.println("Check Number Is === " + checkNumber);
			listOfcheckNumber.add(checkNumber);
		}
		String checkNumber = null;
		for (int i = 0; i < listOfcheckNumber.size(); i++) {
			WebElement selectWb = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selectXpath)));
			Select dropdown = new Select(selectWb);
			dropdown.selectByIndex(2);

			char[] ch = new char[listOfcheckNumber.get(i).length()];
			for (int k = 0; k < listOfcheckNumber.get(i).length(); ++k) {
				ch[k] = listOfcheckNumber.get(i).charAt(k);
				// "//input[@id='chequeNum']"
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[1])))
						.sendKeys(String.valueOf(ch[k]));
				// Webelementval.sendKeys(String.valueOf(ch[i]));
				Thread.sleep(1000);
			}

			Thread.sleep(3000);
			// "//button[text()='Search']"
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[2]))).click();

			List<WebElement> totalRow1 = Framework.driver.findElements(By.xpath(rowXpath));
//			System.out.println("2nd check number size  === " + totalRow1.size());
			for (int j = 1; j <= totalRow1.size(); j++) {

				String refrenceNumber = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(rowXpath + "[" + j + "]/td[1]")))
						.getText();

				checkNumber = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(rowXpath + "[" + j + "]/td[2]")))
						.getText();

				if (refrenceNumber.equalsIgnoreCase(Functions.referenceid)
						&& checkNumber.equalsIgnoreCase(listOfcheckNumber.get(i))) {
					validation++;
					break;
				} else {
					popUpCount++;
				}

			}
			if (popUpCount == totalRow1.size()) {
//				frame.setVisible(true);
//				frame.toFront();
//				JOptionPane.showInputDialog(frame, "Failed At Cheque No. = " + listOfcheckNumber.get(i));
				System.out.println("Failed At Cheque No. = " + listOfcheckNumber.get(i));
				writeInDb(Functions.referenceid, listOfcheckNumber.get(i));
			}

			totalRow1.clear();
			popUpCount = 0;

		}

//		if (validation == listOfcheckNumber.size()) {
//			Monitoring_FrameWork.SaveResult("true", "true");
//		} else {
//			Monitoring_FrameWork.SaveResult("false", "true");
//		}

	}

	public static void writeInDb(String refrence_ID, String cheque_no) {
		String status = "FAIL";
		String triggerFlag = "Y";
		String pageName = Framework.pagename;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateAndTime = format.format(new Date());
		System.out.println("current date Is == " + dateAndTime);
		try {
			Class.forName("com.mysql.jdbc.Driver");

//			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
//					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO SBI_Corp_maker (page_name, reference_id, cheque_no, trigger_flag, status,dateAndTime) VALUES (?, ?, ?, ?, ?,?)";

			PreparedStatement preparedStatement = Framework.dbConnection.prepareStatement(query);
			preparedStatement.setString(1, pageName);
			preparedStatement.setString(2, refrence_ID); // Assuming reference_ID is a String
			preparedStatement.setString(3, cheque_no); // Assuming cheque_no is a String
			preparedStatement.setString(4, triggerFlag);
			preparedStatement.setString(5, status);
			preparedStatement.setString(6, dateAndTime);

			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
//	public static void main(String[] args) {
//		HDFC_Report.writeInDb("76583", "53758");
//	}

	public static void refrenceId(String propertiesValueRDS, String datafieldRDS) throws Exception {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(5));
		Frame frame = new Frame();
		boolean flag = false;

		for (int k = 0; k < 4; k++) {
			// select[@id='viewStatusSearchOptions']
			WebElement selectWb = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertiesValueRDS)));
			Select dropdown = new Select(selectWb);
			dropdown.selectByIndex(1);

			String id = Functions.referenceid;
			// input[@name='referenceNum']
			WebElement ref = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[0])));
//			char[] ch = new char[id.length()];
//			for (int i = 0; i < id.length(); ++i) {
//				ch[i] = id.charAt(i);
//				ref.sendKeys(String.valueOf(ch[i]));
//				Thread.sleep(1000);
//			}
			ref.sendKeys(id);
			Thread.sleep(3000);
			// button[text()='Search']
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[1]))).click();// search
																													// button

			try {
				// th[text()='Cheque Number']
				WebElement wb = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[2])));// cheque
																													// number
				System.out.println("locator is == " + wb.isDisplayed());
				if (wb.isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formate.format(new Date());
				if (flag == false) {
//					frame.setVisible(true);
//					frame.toFront();
//					JOptionPane.showInputDialog(frame, "Press Ok To Send Alert");
					Total_Report.insertRefrenceId(id, date);
					System.out.println(" == Alert send == ");
					flag = true;
				}
				System.out.println(" == In catch Cheque Number Locator Not Found == ");
			}

		}

	}

	public static void insertRefrenceId(String refrence_ID, String dateAndTime) {

		String status = "FAIL";
		String triggerFlag = "Y";
		String pageName = Framework.pagename;

		try {
//			Class.forName("com.mysql.jdbc.Driver");
//
//			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
//					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO SBI_maker_referenceId (page_name, reference_id, trigger_flag, status,dateAndTime) VALUES (?, ?, ?, ?,?)";

			PreparedStatement preparedStatement = Framework.dbConnection.prepareStatement(query);
			preparedStatement.setString(1, pageName);
			preparedStatement.setString(2, refrence_ID); // Assuming reference_ID is a String
			preparedStatement.setString(3, triggerFlag); // Assuming cheque_no is a String
			preparedStatement.setString(4, status);
			preparedStatement.setString(5, dateAndTime);
			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void verifyDate(String propertiesValueRDS, String datafieldRDS) throws Exception {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
		Frame frame = new Frame();

		SimpleDateFormat foramte = new SimpleDateFormat("dd/MM/yyyy");
		String d = foramte.format(new Date());
		WebElement selectdate = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertiesValueRDS)));
		// select[@id='viewStatusSearchOptions']

		Select dropdown = new Select(selectdate);
		dropdown.selectByIndex(3);

		WebElement selectAccount = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[0])));
		// select[@id='selectAccNum']
		Select accountDropDown = new Select(selectAccount);
		accountDropDown.selectByIndex(1);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[1]))).sendKeys(d);
		// input[@name='chqFromDate']
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[2]))).sendKeys(d);
		// input[@name='chqToDate']
		Thread.sleep(3000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[3]))).click();// search
		// button[text()='Search'] // button

		try {
			WebElement wb = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafieldRDS.split("#")[4])));// cheque
			// th[text()='Cheque Number'] // number
			System.out.println("locator is == " + wb.isDisplayed());
			if (wb.isDisplayed()) {
			}
		} catch (Exception e) {
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = formate.format(new Date());
//			frame.setVisible(true);
//			frame.toFront();
//			JOptionPane.showInputDialog(frame, "Press Ok To Send Alert");
			Total_Report.insertDate(d, d, date);
			System.out.println(" == Alert send == ");
			System.out.println(" == In catch Cheque Number Locator Not Found == ");
		}

	}

	public static void insertDate(String fromDate, String toDate, String dateAndTime) {
		String status = "FAIL";
		String triggerFlag = "Y";
		String pageName = Framework.pagename;

		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();

//			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
//					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO SBI_maker_dateRange (page_name, fromDate,toDate,trigger_flag, status,dateAndTime) VALUES (?, ?, ?, ?,?,?)";

			PreparedStatement preparedStatement = Framework.dbConnection.prepareStatement(query);
			preparedStatement.setString(1, pageName);
			preparedStatement.setString(2, fromDate);
			preparedStatement.setString(3, toDate);
			preparedStatement.setString(4, triggerFlag);
			preparedStatement.setString(5, status);
			preparedStatement.setString(6, dateAndTime);
			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void alertSendInDB(String responseTime) {
		String status = "FAIL";
		String triggerFlag = "Y";
		String pageName = Framework.pagename;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateAndTime = format.format(new Date());
		System.out.println("current date Is == " + dateAndTime);
		try {
//			Class.forName("com.mysql.jdbc.Driver");

//			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
//					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO response_time_alert (page_name,response_time,status,trigger_flag,dateAndTime) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = Framework.dbConnection.prepareStatement(query);
			preparedStatement.setString(1, pageName);
			preparedStatement.setString(2, responseTime); // Assuming reference_ID is a String
			preparedStatement.setString(3, status); // Assuming cheque_no is a String
			preparedStatement.setString(4, triggerFlag);
			preparedStatement.setString(5, dateAndTime);

			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void whiteOakCapital(String responseTime) {

		String status = "FAIL";
		String triggerFlag = "Y";
		String pageName = Framework.pagename;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateAndTime = format.format(new Date());
		System.out.println("current date Is == " + dateAndTime);
		try {
//			Class.forName("com.mysql.jdbc.Driver");

//			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
//					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO whiteoak_Capital (page_name,response_time,status,trigger_flag,dateAndTime) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = Framework.dbConnection.prepareStatement(query);
			preparedStatement.setString(1, pageName);
			preparedStatement.setString(2, responseTime);
			preparedStatement.setString(3, status);
			preparedStatement.setString(4, triggerFlag);
			preparedStatement.setString(5, dateAndTime);

			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void checkLTP(String companyName, String actualLTP, String expLTP, String startTime, String endTime) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO checkLTP (companyName,actualLTP,expLTP,startTime,endTime,status,trigger_flag,dateAndTime) VALUES (?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, companyName);
			preparedStatement.setString(2, actualLTP);
			preparedStatement.setString(3, expLTP);
			preparedStatement.setString(4, startTime);
			preparedStatement.setString(5, endTime);
			preparedStatement.setString(6, "fail");
			preparedStatement.setString(7, "Y");
			preparedStatement.setString(8, endTime);

			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void checkLTPforTopIndices(String stockName, String actualLTP, String expLTP, String startTime,
			String endTime) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://114.79.172.202:3306/test", "root",
					"Apmosys@123");
//            String query = "INSERT INTO SBI_Corp_maker (page_name,reference_id,cheque_no,trigger_flag,status) VALUES('"
//                    + pageName + "," + refrence_ID + "," + cheque_no + "," + triggerFlag + "," + status + "')";
//            java.sql.Statement stmt = con.createStatement();
//            stmt.executeUpdate(query);
			// System.out.println(rs);

			String query = "INSERT INTO checkIndices (stockName,actualLTP,expLTP,startTime,endTime,status,trigger_flag,dateAndTime) VALUES (?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, stockName);
			preparedStatement.setString(2, actualLTP);
			preparedStatement.setString(3, expLTP);
			preparedStatement.setString(4, startTime);
			preparedStatement.setString(5, endTime);
			preparedStatement.setString(6, "fail");
			preparedStatement.setString(7, "Y");
			preparedStatement.setString(8, endTime);

			preparedStatement.executeUpdate();
			System.out.println(" === Insert Into db successfully === ");
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
