package databaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {

	public static void main1(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			 Connection connection = DriverManager
//					.getConnection("jdbc:mysql://192.168.12.74:3306/nonsbi_final_29Apt?connectTimeout=5000", "apmosys", "Apmosys@123");

			Connection connection = DriverManager
					.getConnection("jdbc:mysql://114.79.172.202:3306/test?connectTimeout=5000", "root", "Apmosys@123");

			final Statement statement = connection.createStatement();
			String Sendername = "AxisBk";
			String otpMobile = "8652732387";
			final String Query = "SELECT OTP FROM IGRS_Message where Messege_time>'2025/04/08 11:00:11'"
					+ " and Sender='" + Sendername + "' and User_Mobile='" + otpMobile + "'";
			System.out.println("========================>>>>>>>>" + Query);
			final ResultSet rs = statement.executeQuery(Query);
			Thread.sleep(2000L);
			while (rs.next()) {
				String OTP = rs.getString("OTP");
				System.out.println("My Otp===>" + OTP);
			}
			rs.close();
			statement.close();
			connection.close();

			connection.close();

			System.out.println("Connect successfully");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		TwoZeroTwo();
	}

	public static void TwoZeroTwo() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			final Connection connection = DriverManager
					.getConnection("jdbc:mysql://114.79.172.202:3306/test?connectTimeout=5000", "root", "Apmosys@123");

			Statement statement = connection.createStatement();
//		String Sendername="AxisBk";
//		String otpMobile="8652732387";
//		final String Query = "SELECT OTP FROM IGRS_Message where Messege_time>'2025/04/08 11:00:11'" + 
//				" and Sender='" + Sendername + "' and User_Mobile='" + otpMobile + "'";
//		System.out.println("========================>>>>>>>>" + Query);
//		final ResultSet rs = statement.executeQuery(Query);
//		Thread.sleep(2000L);
//		while (rs.next()) {
//			String OTP = rs.getString("OTP");
//			System.out.println("My Otp===>" + OTP);
//		}
//		rs.close();
			System.out.println("Connection established");
			statement.close();
			connection.close();

			connection.close();

			System.out.println("Connect successfully");
		} catch (Exception e) {
			System.out.println("Error !!!!!!!!!!!!!!!");
		}

	}

}
