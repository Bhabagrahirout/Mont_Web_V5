package com.apmosys.framework;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class DB_Read_OTP {
	private static String inputBoxTime;

	public static String readotp3(final String userMobile, final String appName, final int time)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			InterruptedException {
		int val;
		String otp;
		int i;
		for (val = time / 2, otp = "", i = 0; i <= val && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = getOtp3(userMobile, appName);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Date date4 = new Date();
			final SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DB_Read_OTP.inputBoxTime = dft3.format(date4);
			System.out.println();

			try {
				String popUpStatus = Framework.pro.getProperty("popUpStatus");
				if (popUpStatus.equalsIgnoreCase("y")) {
					final Frame frame = new Frame();
					otp = JOptionPane.showInputDialog(frame, "Enter OTP");
				} else {
					System.out.println("<----------  Otp Not Fetch  --------->");
				}

			} catch (Exception e) {
				final Frame frame = new Frame();
				otp = JOptionPane.showInputDialog(frame, "Enter OTP");
			}
		}
		return otp;
	}

	public static String getOtp3(final String mobileNumber, final String Sendername) throws SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url1");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username1");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password1");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT OTP FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + mobileNumber + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs1 = statement.executeQuery(Query);
			while (rs1.next()) {
				OTP = rs1.getString("OTP");
			}
			System.out.println("Otp is ===> " + OTP);
			rs1.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OTP;
	}

	public static String getOtp(final String Sendername) throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, InterruptedException {
		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("jdbc.url");
			final String otpDriver = Functions.pro.getProperty("jdbc.driver");
			final String otpUser = Functions.pro.getProperty("jdbc.username");
			final String otpPass = Functions.pro.getProperty("jdbc.password");
			final String otpMobile = Functions.pro.getProperty("jdbc.userMobile");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT OTP FROM IGRS_Message where Messege_time>'" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + otpMobile + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs = statement.executeQuery(Query);
			Thread.sleep(2000L);
			while (rs.next()) {
				OTP = rs.getString("OTP");
				System.out.println("My Otp===>" + OTP);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return OTP;
	}

	public static String readotp(final String appName) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, InterruptedException {
		String otp;
		int i;
		for (otp = "", i = 0; i <= 10 && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = getOtp(appName);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Frame frame = new Frame();
			frame.setVisible(true);
			frame.toFront();
			otp = JOptionPane.showInputDialog(frame, "Enter OTP");
			frame.setVisible(false);
		}
		return otp;
	}

	public static String readotp1(final String userMobile, final String appName, final int time)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			InterruptedException {
		int val;
		String otp;
		int i;
		for (val = time / 2, otp = "", i = 0; i <= val && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = getOtp1(userMobile, appName);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Date date4 = new Date();
			final SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DB_Read_OTP.inputBoxTime = dft3.format(date4);

			try {

				String captchStatus = Framework.pro.getProperty("captchStatus");
				if (captchStatus.equalsIgnoreCase("N")) {
					System.out.println("------------------  otp not fetch  -------------");
				}

			} catch (Exception e) {
				final Frame frame = new Frame();
				frame.setVisible(true);
				frame.toFront();
				otp = JOptionPane.showInputDialog(frame, "Enter OTP");
				frame.setVisible(false);
			}

		}
		return otp;
	}

	public static String readotpFor2Sender(String userMobile, String sender) {

		String sender1 = sender.split("#")[0].trim();
		String sender2 = sender.split("#")[1].trim();
		String sender3 = sender.split("#")[2].trim();
		String sender4 = sender.split("#")[3].trim();

		String OTP = "";
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);

			Statement statement = connection.createStatement();
			String Query = "SELECT OTP FROM IGRS_Message where Messege_time>= '" + Framework.OTPcurrentTime
					+ "' and User_Mobile='" + userMobile + "' and Sender in ('+" + sender1 + "','+" + sender2 + "','+"
					+ sender3 + "','+" + sender4 + "')";
			System.out.println("========>>>>>>>>" + Query);

			ResultSet rs = null;
			int count = 1;
			while (count < 11) {
				boolean flag = false;
				rs = statement.executeQuery(Query);
				while (rs.next()) {
					OTP = rs.getString("OTP");
					System.out.println("My Otp===>" + OTP);
					if (OTP != null) {
						flag = true;
						break;
					}
				}
				System.out.println("count == " + count);
				if (flag) {
					break;
				}
				count++;
				Thread.sleep(2000);
			}

			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return OTP;
	}

	public static String readotpInb(final String userMobile, final String appName, final int time)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			InterruptedException {
		int val;
		String otp;
		int i;
		for (val = time / 2, otp = "", i = 0; i <= val && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = getOtpInb(userMobile, appName);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Date date4 = new Date();
			final SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DB_Read_OTP.inputBoxTime = dft3.format(date4);
			System.out.println();
			final Frame frame = new Frame();
			frame.setVisible(true);
			frame.toFront();
			otp = JOptionPane.showInputDialog(frame, "Enter OTP");
			frame.setVisible(false);
		}
		return otp;
	}

	public static String iciciOtp(String userMobile, String sender1, String sender2, int time)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			InterruptedException {
		int val;
		String otp;
		int i;
		for (val = time / 2, otp = "", i = 0; i <= val && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = iciciGetOtp(userMobile, sender1, sender2);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Date date4 = new Date();
			final SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DB_Read_OTP.inputBoxTime = dft3.format(date4);
			System.out.println();
			final Frame frame = new Frame();
			frame.setVisible(true);
			frame.toFront();
			otp = JOptionPane.showInputDialog(frame, "Enter OTP");
			frame.setVisible(false);
		}
		return otp;
	}

	public static String getOtp1(final String mobileNumber, final String Sendername) throws SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		String OTP = "";
		String msg = "";
		String msgTime = null;
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			String otpUrl = Functions.pro.getProperty("otp.jdbc.url");
			String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			String otpUser = Functions.pro.getProperty("otp.jdbc.username");
			String otpPass = Functions.pro.getProperty("otp.jdbc.password");

			// for old db
			Class.forName(otpDriver);
			Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);

			String Query = "SELECT * FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + mobileNumber + "'";

			// for new db
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.228:3306/nonsbi_final", "root",
//					"Welcome@2023");
//
//			String Query = "SELECT * FROM otp where message_time>='" + Framework.OTPcurrentTime + "' and sender='"
//					+ Sendername + "' and sender_mobile='" + mobileNumber + "'";

			System.out.println("========================>>>>>>>>" + Query);
			Statement statement = connection.createStatement();

			final ResultSet rs1 = statement.executeQuery(Query);
			while (rs1.next()) {
				OTP = rs1.getString("OTP");
//				msg=rs1.getString("message");
//				msgTime=rs1.getString("message_time");
				System.out.println("My Otp===>" + OTP);
			}
			rs1.close();
			statement.close();
			connection.close();
//			if(OTP != null && msg != null && msgTime != null && !OTP.equalsIgnoreCase("") && !msg.equalsIgnoreCase("")
//					&& !msgTime.equalsIgnoreCase("")) {
//				
//				DB_Tables.Table_otp(Sendername, msg, msgTime, mobileNumber, 
//						Integer.parseInt(OTP), 0, new Date(), 0, new Date());
//				
//				
//			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OTP;
	}

	public static String getOtpInb(final String mobileNumber, final String Sendername) throws SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT Message FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + mobileNumber + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs1 = statement.executeQuery(Query);
			while (rs1.next()) {
				OTP = rs1.getString("Message");
				OTP = OTP.split(" ")[0].trim();
				System.out.println("My Otp===>" + OTP);
			}
			rs1.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OTP;
	}

	public static String readotp2(final String userMobile, final String appName, final int time)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			InterruptedException {
		int val;
		String otp;
		int i;
		for (val = time / 2, otp = "", i = 0; i <= val && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = getOtp2(userMobile, appName);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Date date4 = new Date();
			final SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DB_Read_OTP.inputBoxTime = dft3.format(date4);
			System.out.println();
			final Frame frame = new Frame();
			frame.setVisible(true);
			frame.toFront();
			otp = JOptionPane.showInputDialog(frame, "Enter OTP");
			frame.setVisible(false);
		}
		return otp;
	}

	public static String getOtp2(String Sendername, String mobileNumber) {

		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url1");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver1");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username1");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password1");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT OTP FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + mobileNumber + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs1 = statement.executeQuery(Query);
			while (rs1.next()) {
				OTP = rs1.getString("OTP");
				System.out.println("My Otp===>" + OTP);
			}
			rs1.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OTP;

	}

	public static String readAxisOtp(final String userMobile, final String sender, final int time)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			InterruptedException {
		int val;
		String otp;
		int i;
		for (val = time / 2, otp = "", i = 0; i <= val && (otp == null || otp.equalsIgnoreCase("")); ++i) {
			otp = getAxisOtp(userMobile, sender);
			Thread.sleep(2000L);
		}
		if (i >= 10 && (otp == null || otp.equalsIgnoreCase(""))) {
			final Date date4 = new Date();
			final SimpleDateFormat dft3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DB_Read_OTP.inputBoxTime = dft3.format(date4);
			System.out.println();
			final Frame frame = new Frame();
			frame.setVisible(true);
			frame.toFront();
			otp = JOptionPane.showInputDialog(frame, "Enter OTP");
			frame.setVisible(false);
		}
		return otp;
	}

	public static String getAxisOtp(String mobileNumber, String Sendername) {

		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT Message FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + mobileNumber + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs1 = statement.executeQuery(Query);
			while (rs1.next()) {
				String message = rs1.getString("Message");
				OTP = message.split(" ")[0].trim();
				System.out.println("My Otp===>" + OTP);
			}
			rs1.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OTP;

	}

	public static String iciciGetOtp(String mobileNumber, String sender1, String sender2) throws SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT OTP FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
					+ "' and Sender='" + sender1 + "' and User_Mobile='" + mobileNumber + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs1 = statement.executeQuery(Query);
			while (rs1.next()) {
				System.out.println(" === In sender 1===");
				OTP = rs1.getString("OTP");
				System.out.println("My Otp Sender 1===>" + OTP);
			}
			rs1.close();
			statement.close();
			connection.close();
			System.out.println("My OTP After sender 1 =  " + OTP);
			if (OTP == null || OTP.equalsIgnoreCase("")) {
				// Class.forName(otpDriver);
				System.out.println(" === In sender 2===");
				final Connection connection1 = DriverManager.getConnection(otpUrl, otpUser, otpPass);
				final Statement statement1 = connection1.createStatement();
				final String Query1 = "SELECT OTP FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
						+ "' and Sender='" + sender2 + "' and User_Mobile='" + mobileNumber + "'";
				System.out.println("========================>>>>>>>>" + Query1);
				final ResultSet rs2 = statement1.executeQuery(Query1);
				while (rs2.next()) {
					OTP = rs2.getString("OTP");
					System.out.println("My Otp Sender 2===>" + OTP);
				}
				rs1.close();
				statement1.close();
				connection1.close();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OTP;
	}

	static String getMessageFromDb(final String senderName) throws InterruptedException, IOException {
		String message = "";
		for (int i = 0; i <= 10 && (message == null || message.equalsIgnoreCase("")); ++i) {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("otp.jdbc.url");
			final String otpDriver = Functions.pro.getProperty("otp.jdbc.driver");
			final String otpUser = Functions.pro.getProperty("otp.jdbc.username");
			final String otpPass = Functions.pro.getProperty("otp.jdbc.password");
			final String mobileNumber = Functions.pro.getProperty("Miraeno");
			try {
				Class.forName(otpDriver).newInstance();
				final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
				final Statement statement = connection.createStatement();
				final String Query = "SELECT Message FROM IGRS_Message where Messege_time>='" + Framework.OTPcurrentTime
						+ "' and Sender='" + senderName + "' and User_Mobile='" + mobileNumber + "'";
				System.out.println("========================>>>>>>>>" + Query);
				final ResultSet rs = statement.executeQuery(Query);
				while (rs.next()) {
					message = rs.getString("Message");
				}
				System.out.println("My Message===>" + message);
				rs.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			Thread.sleep(2000L);
		}
		return message;
	}

	public static void main(final String[] args) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, InterruptedException {
	}

	public static String miraeAssetReadotp(String otpMobile,final String Sendername) throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, InterruptedException {
		String OTP = "";
		System.out.println(Framework.currentTime);
		try {
			final FileInputStream fis = new FileInputStream(Functions.path2);
			Functions.pro.load(fis);
			final String otpUrl = Functions.pro.getProperty("jdbc.url");
			final String otpDriver = Functions.pro.getProperty("jdbc.driver");
			final String otpUser = Functions.pro.getProperty("jdbc.username");
			final String otpPass = Functions.pro.getProperty("jdbc.password");
//			final String otpMobile = Functions.pro.getProperty("jdbc.userMobile");
			Class.forName(otpDriver);
			final Connection connection = DriverManager.getConnection(otpUrl, otpUser, otpPass);
			final Statement statement = connection.createStatement();
			final String Query = "SELECT Message FROM IGRS_Message where Messege_time>'" + Framework.OTPcurrentTime
					+ "' and Sender='" + Sendername + "' and User_Mobile='" + otpMobile + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs = statement.executeQuery(Query);
			Thread.sleep(2000L);
			while (rs.next()) {
				String message = rs.getString("Message");
				System.out.println("My Message===>" + message);
				OTP = message.split("is")[0].trim();
				System.out.println("My Otp===>" + OTP);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return OTP;
	}
}
