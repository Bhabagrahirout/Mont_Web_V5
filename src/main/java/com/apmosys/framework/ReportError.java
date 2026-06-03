// 
// Decompiled by Procyon v0.5.36
// 

package com.apmosys.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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

public class ReportError {

	public static void axisUrlAlert(final String host, final String port, final String userName, final String password,
			final String toAddress, String mailCc, String subject, int loadWaitTime)
			throws AddressException, MessagingException {

		String message = "Dear Sir/Ma'am,<br/>" + "<br/> " + Framework.pagename + " is taking more than " + loadWaitTime
				+ " secs" + "<br/> <br/>URL: " + Functions.url + "<br/> <br/>Time: "
				+ new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())
				+ "<br/> <br/>Note: This is an auto generated mail.";

		try {
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

			msg.setSubject(Framework.pagename + " is taking more than " + loadWaitTime + " secs");

			msg.setSentDate(new Date());
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			System.out.println("Creates message part");
			messageBodyPart.setText(message);

			String signature = "<br/><br/><br/><br/>Thanks and Regards,<br/> "
					+ "ApMoSys | Performance Monitoring Team<br/>"
					+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
					+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";

			String Finalmessage = message + signature;

			messageBodyPart.setContent(Finalmessage, "text/html");
			Multipart multipart = (Multipart) new MimeMultipart();
			// for attach screenshot
//		 MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//		 DataSource source = new FileDataSource( new File( Monitoring_FrameWork.screenShot));
//		 messageBodyPart2.setDataHandler(new DataHandler(source));
//		 messageBodyPart2.setFileName(new File( Monitoring_FrameWork.screenShot).getName());
			System.out.println("Creates multi-part");

			multipart.addBodyPart((BodyPart) messageBodyPart);
//		multipart.addBodyPart((BodyPart) messageBodyPart2);

			msg.setContent(multipart);
			System.out.println("Sending mail....");
			Transport.send(msg);
			System.out.println("Mail has been sent....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addErrorDB(final String appName, final String pageName, final String screenshot,
			final String comments) {
		try {
			final String filePath = screenshot;
			System.out.println(String.valueOf(String.valueOf(String.valueOf(String.valueOf(appName)))) + " : "
					+ pageName + " : " + screenshot);
			final InputStream inputStream = new FileInputStream(new File(filePath));
			final String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			final Connection conn = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
					Framework.dbpassword);
			String qry = "INSERT INTO portal_error_screenshot (pic_name, pic_byte) values(?,?)";
			System.out.println(qry);
			final PreparedStatement statement1 = conn.prepareStatement(qry, 1);
			statement1.setString(1, String.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.ScriptName))))
					+ "_" + currentTime);
			statement1.setBlob(2, inputStream);
			final int i = statement1.executeUpdate();
			int scId = 0;
			if (i > 0) {
				final ResultSet rs = statement1.getGeneratedKeys();
				while (rs.next()) {
					scId = rs.getInt(1);
				}
			}
			qry = "insert into portal_error(appName,pageName,startTime,LEVEL,comments,errorScreenshot,STATUS,created_by,userName,Reported_To) values(?,?,?,?,?,?,?,?,?,?)";
			System.out.println(qry);
			final PreparedStatement statement2 = conn.prepareStatement(qry);
			statement2.setString(1, appName);
			statement2.setString(2, pageName);
			statement2.setString(3, currentTime);
			statement2.setInt(4, 0);
			statement2.setString(5, comments);
			statement2.setInt(6, scId);
			statement2.setInt(7, 0);
			statement2.setString(8, "Auto");
			statement2.setString(9, "Automation");
			statement2.setString(10, "");
			System.out.println(statement2.executeUpdate());
			statement2.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addWarningDB(final String appName, final String pageName) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			final Connection conn = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
					Framework.dbpassword);
			final String qry = "insert into portal_warnings(appName,pageName) values(?,?)";
			System.out.println("insert into portal_warnings(appName,pageName) values(?,?)");
			final PreparedStatement statement = conn
					.prepareStatement("insert into portal_warnings(appName,pageName) values(?,?)");
			statement.setString(1, appName);
			statement.setString(2, pageName);
			System.out.println(statement.executeUpdate());
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void VerifyHDFCGoldETFDate(String pageName, String status, String startTime, String endTime) {

		try {
			String qry = "insert into HDFCGoldETF(pageName,status,trigger_flag,startTime,endTime) values(?,?,?,?,?)";
			System.out.println("Query ==== " + qry);
			PreparedStatement statement = Framework.dbConnection.prepareStatement(qry);
			statement.setString(1, pageName);
			statement.setString(2, status);
			statement.setString(3, "Y");
			statement.setString(4, startTime);
			statement.setString(5, endTime);
			statement.executeUpdate();
			System.out.println(" === Insert Data into DB Successfully ===");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
