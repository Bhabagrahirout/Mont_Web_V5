package com.apmosys.framework;

import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DB_Tables extends Framework {

	public static void axisNeoCurrencyDetails(String sourceCurency, String targetCurrency, String conversionRatebuy,
			String conversionRatesell, String type) {

		String fetchAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String query = "INSERT INTO currency_conversion_rates (source_currency, target_currency, conversion_rate_buy,conversion_rate_sell,type, fetched_at) VALUES (?, ?, ?, ?,?,?)";

			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
				preparedStatement.setString(1, sourceCurency);
				preparedStatement.setString(2, targetCurrency);
				preparedStatement.setBigDecimal(3, new BigDecimal(conversionRatebuy));
				preparedStatement.setBigDecimal(4, new BigDecimal(conversionRatesell));
				preparedStatement.setString(5, type);
				preparedStatement.setTimestamp(6, Timestamp.valueOf(fetchAt));

				preparedStatement.executeUpdate();
				System.out.println(" === Insert data in currency_conversion_rates table successfully === ");
				preparedStatement.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getHostip() {
		String hostIp = null;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			hostIp = inetAddress.getHostAddress();
			System.out.println("Hostip: " + hostIp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hostIp;
	}

	public static String getHostname() {
		String hostname = null;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			hostname = inetAddress.getHostName();
			System.out.println("Hostname: " + hostname);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hostname;
	}

	public static String getMac() {
		List<String> mac = new ArrayList<String>();
		try {

			Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
			// iterate over all interfaces
			while (networkInterface.hasMoreElements()) {
				// get an interface
				NetworkInterface network = networkInterface.nextElement();
				// get its hardware or mac address
				byte[] macAddressBytes = network.getHardwareAddress();
				if (macAddressBytes != null) {
					// initialize a string builder to hold mac address
					StringBuilder macAddressStr = new StringBuilder();
					// iterate over the bytes of mac address
					for (int i = 0; i < macAddressBytes.length; i++) {
						// convert byte to string in hexadecimal form
						macAddressStr.append(String.format("%02X", macAddressBytes[i]));
					}
					mac.add(macAddressStr.toString());
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		String s = mac.toString().replace("[", "").replace("]", "").replaceAll(" ", "");
		return s;

	}

	public static void Table_otp(String sender, String message, String message_time, String sender_mobile, int otp,
			int created_by, Date created_on, int updated_by, Date updated_on) {

		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date date = null;
		try {
			date = fm.parse(message_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String query = "INSERT INTO otp (sender, message, message_time, sender_mobile, otp, created_by, created_on, updated_by, updated_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
				preparedStatement.setString(1, sender);
				preparedStatement.setString(2, message);
				preparedStatement.setTimestamp(3, (Timestamp) date);
				preparedStatement.setString(4, sender_mobile);
				preparedStatement.setInt(5, otp);
				preparedStatement.setInt(6, created_by);
				preparedStatement.setTimestamp(7, (Timestamp) created_on);
				preparedStatement.setInt(8, updated_by);
				preparedStatement.setTimestamp(9, (Timestamp) updated_on);

				preparedStatement.executeUpdate();
				System.out.println(" === Insert data in otp table successfully === ");
				preparedStatement.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Table_execution_data(String instance_name, int instanceRunID, String business_date,
			String business_hour, String business_min, String error, String harid, String error_url,
			double responseTime, int monitoring_instance_id, int application_id, int page_id, String created_by,
			String created_on, String updated_by, String updated_on, String status,String apiFilePath) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String query = "INSERT INTO execution_data (instance_name,business_date,business_hour, business_min,page_name, error, harid, page_url,response_time, monitoring_instance_id, application_id, page_id,instance_run_id,created_by,updated_by,error_description,status,screenshot_path) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
			System.out.printf(" [DB]  QUERY        : %s%n", query);
			
			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, instance_name);
				preparedStatement.setInt(2, Integer.parseInt(business_date));
				preparedStatement.setInt(3, Integer.parseInt(business_hour));
				preparedStatement.setInt(4, Integer.parseInt(business_min));
				preparedStatement.setString(5, Framework.pagename);
				preparedStatement.setString(6, error);
				preparedStatement.setString(7, harid);
				preparedStatement.setString(8, error_url);
				preparedStatement.setDouble(9, responseTime);
				preparedStatement.setInt(10, monitoring_instance_id);
				preparedStatement.setInt(11, application_id);
				preparedStatement.setInt(12, page_id);
				preparedStatement.setInt(13, instanceRunID);
				preparedStatement.setString(14, created_by);
//			preparedStatement.setTimestamp(15, Timestamp.valueOf(created_on));
				preparedStatement.setString(15, updated_by);
				preparedStatement.setString(16, Framework.errorDesc);
				preparedStatement.setString(17, status);
				preparedStatement.setString(18, apiFilePath);
//			preparedStatement.setTimestamp(17, Timestamp.valueOf(updated_on));

				preparedStatement.executeUpdate();
				System.out.printf(" [DB]  INSERT       : execution_data table [✓]%n");
				ResultSet executionRs = preparedStatement.getGeneratedKeys();

				if (executionRs.next()) {
					executionId = executionRs.getInt(1);
					System.out.printf(" [DB]  EXEC ID      : %d%n",executionId);
				}

				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void mailQueue(String alert_category, String alert_severity, String business_date,
			String error_description, String error_type, int monitoring_instance_id, int page_id,
			double response_time) {

		try {
			int triggerCount = -1;

			String selectQuery = "SELECT trigger_count FROM mail_queue WHERE page_id = ? AND monitoring_instance_id = ?";
			try (PreparedStatement selectStatement = dbConnection.prepareStatement(selectQuery)) {
				selectStatement.setInt(1, page_id);
				selectStatement.setInt(2, monitoring_instance_id);

//				System.out.println("Executing: " + selectQuery);
				System.out.printf(" [DB]  QUERY        : %s%n", selectQuery);

				try (ResultSet rs = selectStatement.executeQuery()) {
					if (rs.next()) {
						triggerCount = rs.getInt("trigger_count") + 1;
					}
				}
			}

			if (triggerCount >= 0) {
				String updateQuery = "UPDATE mail_queue SET trigger_count = ? WHERE page_id = ? AND monitoring_instance_id = ?";
				try (PreparedStatement updateStatement = dbConnection.prepareStatement(updateQuery)) {
					updateStatement.setInt(1, triggerCount);
					updateStatement.setInt(2, page_id);
					updateStatement.setInt(3, monitoring_instance_id);
					updateStatement.executeUpdate();
//					System.out.println("Updated mailQueue table successfully.");
					System.out.printf(" [DB]  UPDATE       : mail_queue table [✓]%n");
					
				}
			} else {
				String insertQuery = "INSERT INTO mail_queue (alert_category, alert_severity, business_date, error_description, "
						+ "error_type, monitoring_instance_id, page_id, response_time, trigger_count,execution_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
				try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery)) {
					preparedStatement.setString(1, alert_category);
					preparedStatement.setString(2, alert_severity);
					preparedStatement.setInt(3, Integer.parseInt(business_date));
					preparedStatement.setString(4, error_description);
					preparedStatement.setString(5, error_type);
					preparedStatement.setInt(6, monitoring_instance_id);
					preparedStatement.setInt(7, page_id);
					preparedStatement.setDouble(8, response_time);
					preparedStatement.setInt(9, 0);// for 1st time
					preparedStatement.setInt(10, Framework.executionId);// for 1st time
					preparedStatement.executeUpdate();
//					System.out.println(" === Insert data in mailQueue table successfully === ");
					System.out.printf(" [DB]  INSERT       : mail_queue table [✓]%n");
					preparedStatement.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void deleteMailQueuePageData(int monitoring_instance_id, int page_id) {
//
//		try {
//			int trigger_level = -1;
//
//			String selectQuery = "SELECT trigger_level FROM mail_queue WHERE page_id = ? AND monitoring_instance_id = ?";
//			try (PreparedStatement selectStatement = dbConnection.prepareStatement(selectQuery)) {
//				selectStatement.setInt(1, page_id);
//				selectStatement.setInt(2, monitoring_instance_id);
//
//				System.out.println("Executing: " + selectQuery);
//
//				try (ResultSet rs = selectStatement.executeQuery()) {
//					if (rs.next()) {
//						trigger_level = rs.getInt("trigger_level");
//					}
//				}
//			}
//
//			if (trigger_level > 0) {
//				String updateQuery = "UPDATE mail_queue SET trigger_level = ? WHERE page_id = ? AND monitoring_instance_id = ?";
//				try (PreparedStatement updateStatement = dbConnection.prepareStatement(updateQuery)) {
//					updateStatement.setInt(1, -1);
//					updateStatement.setInt(2, page_id);
//					updateStatement.setInt(3, monitoring_instance_id);
//					int rowsAffected = updateStatement.executeUpdate(); // Use executeUpdate() for DELETE
//					if (rowsAffected > 0) {
//						System.out.println("Successfully Updated the mailQueue table, " + rowsAffected + " row(s) Affected. Set the level to Default (-1).");
//					} else {
//						System.out.println("No matching rows found to Updated the mailQueue table to Set the level to Default (-1)");
//					}
//				}
//			}else if(trigger_level == 0)
//			{
//				System.out.println("Mail did't send yet. Implementation Peding.....");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace(); // Print the exception for debugging
//		}
//	}

	public static void deleteMailQueuePageData() {
		String deleteQuery = "DELETE FROM mail_queue WHERE monitoring_instance_id = ? AND page_id = ?";

		try (PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteQuery)) {
			deleteStatement.setInt(1, Framework.monitoringInstancesId);
			deleteStatement.setInt(2, Framework.pageId);

			System.out.printf(" [DB]  QUERY        : %s%n", deleteQuery);
			int rowsAffected = deleteStatement.executeUpdate(); // Use executeUpdate() for DELETE

			if (rowsAffected > 0) {
				System.out.printf(" [DB]  STATUS       : %d rows deleted successfully [✓]%n", rowsAffected);
				
			} else {
				System.out.printf(" [DB]  INFO         :No matching rows found to delete.%n");
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	public static void insertErrorLog(String screenshot_path) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String query = "INSERT INTO error_log (execution_id, log_type, error_message, screenshot_path) VALUES (?, ?, ?, ?)";
			System.out.println(Framework.subDiv);
			System.out.printf(" [DB]  QUERY        : %s%n", query);
			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
				preparedStatement.setInt(1, Framework.executionId);
				preparedStatement.setString(2, Framework.errorType);
				preparedStatement.setString(3, Framework.errorMessage);
				preparedStatement.setString(4, screenshot_path);
				preparedStatement.executeUpdate();
				System.out.printf(" [DB]  INSERT       : error_log table [✓]%n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double getNetworkSpeed() {
		String fileUrl = "https://storage.googleapis.com/chrome-for-testing-public/117.0.5912.0/mac-arm64/chromedriver-mac-arm64.zip";
		double speed = 0;
		try {
			long startTime = System.currentTimeMillis();

			long bytesRead = downloadFile(fileUrl);

			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;

			// Convert duration to seconds
			double durationSeconds = duration / 1000.0;

			// Convert file size to megabytes
			double fileSizeMB = bytesRead / (1024.0 * 1024.0);

			// Calculate speed in MB/s
//			double speed = fileSizeMB / durationSeconds;
			speed = Math.round((fileSizeMB / durationSeconds) * 100.0) / 100.0;

//			System.out.println(speed);

//			System.out.printf("Downloaded file size: %.2f MB%n", fileSizeMB);
//			System.out.printf("Duration: %.2f seconds%n", durationSeconds);
//			System.out.printf("Download speed: %.2f MB/s%n", speed);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return speed;

	}

	private static long downloadFile(String fileURL) throws IOException {
		URL url = new URL(fileURL);
		long totalBytesRead = 0;
		try (BufferedInputStream in = new BufferedInputStream(url.openStream())) {
			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				totalBytesRead += bytesRead;
			}
		}
		return totalBytesRead;
	}

	public static String getIpAdress() {
		String ipAdress = "";

		// using ip r
//		try {
//			Process process = Runtime.getRuntime().exec("ip r");
//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				ipAdress = line;
//			}
//			ipAdress = ipAdress.split("link src")[1].trim().split(" ")[0].trim();
//			System.out.println(ipAdress);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		try {
			Process process = Runtime.getRuntime().exec("hostname -I");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				ipAdress = line.trim();
			}
//			ipAdress = ipAdress.split("inet")[1].trim().split("/")[0].trim();
//			System.out.println("Ip Address is --> "+ipAdress);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ipAdress;
	}

	public static ArrayList<String> networkDetails() {
		ArrayList<String> networkDetails = new ArrayList<String>();

		String networkProvider = "";
		String ip = "";
		String city = "";

		for (int i = 0; i <= 10; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			try {
				RestAssured.baseURI = "https://ipinfo.io/json";
				Response r = RestAssured.given().contentType("application/json").when().get().then().using().extract()
						.response();

				JSONObject jsonObject = new JSONObject(r.getBody().asString());
				ip = jsonObject.getString("ip").trim();
				city = jsonObject.getString("city");
				networkProvider = jsonObject.getString("org").trim();
				if (networkProvider.isEmpty()) {
					networkProvider = jsonObject.getString("asn_org");
				}
				System.out.println("Network Ip is ======== " + ip);
				System.out.println("City is ======== " + city);
				System.out.println("Network Provider is ======== " + networkProvider);
			} catch (Exception e) {
				System.out.println("Trying again for to get network details....");
			}
			if (!ip.isEmpty()) {
				break;
			}
		}
		networkDetails.add(ip);
		networkDetails.add(city);
		networkDetails.add(networkProvider);
		return networkDetails;
	}

	public static ArrayList<String> getNetworkDetails() {
		ArrayList<String> networkDetails = new ArrayList<String>();
		String publicIP = "";
		String org = "";
		try {

			URL url = new URL("https://checkip.amazonaws.com/");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			publicIP = reader.readLine();

			if (publicIP.equalsIgnoreCase("") || publicIP == null) {

				URL url1 = new URL("https://api64.ipify.org");
				BufferedReader reader1 = new BufferedReader(new InputStreamReader(url1.openStream()));
				publicIP = reader1.readLine();

				if (publicIP.equalsIgnoreCase("") || publicIP == null) {
					URL url2 = new URL("https://ifconfig.me/ip");
					HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
					connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Adding User-Agent
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					publicIP = reader2.readLine();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String apiUrl = "http://ip-api.com/json/";
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

//            System.out.println(response.toString());
			JSONObject jsonResponse = new JSONObject(response.toString());
//            String isp = jsonResponse.getString("isp");
			org = jsonResponse.getString("as");

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Network Ip is :: " + publicIP);
		System.out.println("ISP  is  :: " + org);
		networkDetails.add(publicIP);
		networkDetails.add(org);

		return networkDetails;
	}

	public static void Table_runtime_instance(String instance_name, String start_time, String end_time, String status,
			String business_date, String business_hour, String business_min, String browser_type, String device_type,
			String version, String container_ip, String jenkins_master_ip, int monitoring_instance_id,
			int application_ids, int infra_id, String created_by, String created_on, String updated_by,
			String updated_on, double netSpeed) {

//		ArrayList<String> networkDetails = networkDetails();
		ArrayList<String> networkDetails = getNetworkDetails();
		String networkIp = null;
		String location = "";
		String networkProvider = null;
		try {
			networkIp = networkDetails.get(0);
//			location = networkDetails.get(1);
			networkProvider = networkDetails.get(1);
		} catch (Exception e) {
			System.out.println("Network Details not found....");
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String query = "INSERT INTO runtime_instance (instance_name,location,start_time, end_time, status, business_date, business_hour, business_min,browser_type,device_type,"
					+ "application_type,version,container_ip,jenkins_master_ip,bandwidth,monitoring_instance_id,application_id,"
					+ "infra_id, created_by, created_on, updated_by, updated_on,network_provider,network_ip)"
					+ " VALUES ( ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?,?,?)";

			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, instance_name);
				preparedStatement.setString(2, location);
				preparedStatement.setTimestamp(3, Timestamp.valueOf(start_time));
				preparedStatement.setTimestamp(4, Timestamp.valueOf(end_time));
				preparedStatement.setString(5, status);
				preparedStatement.setInt(6, Integer.parseInt(business_date));
				preparedStatement.setInt(7, Integer.parseInt(business_hour));
				preparedStatement.setInt(8, Integer.parseInt(business_min));
				preparedStatement.setString(9, browser_type);
				preparedStatement.setString(10, device_type);
				preparedStatement.setString(11, "Web");
				preparedStatement.setString(12, version);
				preparedStatement.setString(13, container_ip);
				preparedStatement.setString(14, jenkins_master_ip);
				preparedStatement.setDouble(15, netSpeed);

				preparedStatement.setInt(16, monitoring_instance_id);
				preparedStatement.setInt(17, application_ids);
				preparedStatement.setInt(18, infra_id);

				preparedStatement.setString(19, created_by);
				preparedStatement.setTimestamp(20, Timestamp.valueOf(created_on));
				preparedStatement.setString(21, updated_by);
				preparedStatement.setTimestamp(22, Timestamp.valueOf(updated_on));

				preparedStatement.setString(23, networkProvider);
				preparedStatement.setString(24, networkIp);

				preparedStatement.executeUpdate();

				ResultSet keys = preparedStatement.getGeneratedKeys();
				if (keys.next()) {
					Framework.runTimeInstanceId = keys.getInt(1);
					System.out.println(
							"============ Runtime Instance Id is " + Framework.runTimeInstanceId + " =========");
				}
				System.out.println(" === get data in runtime_instance table successfully === ");

				preparedStatement.close();
				keys.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateLocation(String location) {

		int rs = 0;
		PreparedStatement selectStatement = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String Query = "UPDATE runtime_instance SET location ='" + location + "' WHERE instance_run_id="
					+ Framework.runTimeInstanceId;

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeUpdate();

			System.out.println("\n runtime_instance Rows updated successfully for location ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

	}

//	public static void Table_runtime_instance(String instance_name, String location, String start_time, String end_time,
//			String status, String business_date, String business_hour, String business_min, String browser_type,
//			String device_type, String version, String container_ip, String jenkins_master_ip,
//			int monitoring_instance_id, int application_ids, int infra_id, String created_by, String created_on,
//			String updated_by, String updated_on) {
//
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//
//			String query = "INSERT INTO runtime_instance (instance_name,location,start_time, end_time, status, business_date, business_hour, business_min,browser_type,device_type,application_type,version,container_ip,jenkins_master_ip,monitoring_instance_id,application_id,infra_id, created_by, created_on, updated_by, updated_on) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
//
//			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query,
//					Statement.RETURN_GENERATED_KEYS)) {
//				preparedStatement.setString(1, instance_name);
//				preparedStatement.setString(2, location);
//				preparedStatement.setTimestamp(3, Timestamp.valueOf(start_time));
//				preparedStatement.setTimestamp(4, Timestamp.valueOf(end_time));
//				preparedStatement.setString(5, status);
//				preparedStatement.setInt(6, Integer.parseInt(business_date));
//				preparedStatement.setInt(7, Integer.parseInt(business_hour));
//				preparedStatement.setInt(8, Integer.parseInt(business_min));
//				preparedStatement.setString(9, browser_type);
//				preparedStatement.setString(10, device_type);
//				preparedStatement.setString(11, "Web");
//				preparedStatement.setString(12, version);
//				preparedStatement.setString(13, container_ip);
//				preparedStatement.setString(14, jenkins_master_ip);
//
//				preparedStatement.setInt(15, monitoring_instance_id);
//				preparedStatement.setInt(16, application_ids);
//				preparedStatement.setInt(17, infra_id);
//
//				preparedStatement.setString(18, created_by);
//				preparedStatement.setTimestamp(19, Timestamp.valueOf(created_on));
//				preparedStatement.setString(20, updated_by);
//				preparedStatement.setTimestamp(21, Timestamp.valueOf(updated_on));
//
//				preparedStatement.executeUpdate();
//
//				ResultSet keys = preparedStatement.getGeneratedKeys();
//				if (keys.next()) {
//					Framework.runTimeInstanceId = keys.getInt(1);
//					System.out.println(
//							"============ Runtime Instance Id is " + Framework.runTimeInstanceId + " =========");
//				}
//				System.out.println(" === get data in runtime_instance table successfully === ");
//
//				preparedStatement.close();
//				keys.close();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void Table_infra_master(String container_ip, String container_name, int application_id,
//			int created_by, int updated_by) {
//
//		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//		Date date = null;
//		try {
//			date = fm.parse(fm.format(new Date()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//
//			Connection con = DriverManager.getConnection(dbDetails.get("hostUrl"), dbDetails.get("userId"),
//					dbDetails.get("password"));
//
//			String query = "INSERT INTO infra_master (host_name, host_ip, host_os, container_ip, host_mac_id, container_name, is_active, application_id, created_by, created_on, updated_by, updated_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
//				preparedStatement.setString(1, getHostname().trim());
//				preparedStatement.setString(2, getHostip().trim());
//				preparedStatement.setString(3, System.getProperty("os.name").toString().trim());
//				preparedStatement.setString(4, container_ip);
//				preparedStatement.setString(5, getMac().trim());
//				preparedStatement.setString(6, container_name);
//				preparedStatement.setString(7, "Y");
//				preparedStatement.setInt(8, application_id);
//				preparedStatement.setInt(9, created_by);
//				preparedStatement.setTimestamp(10, (Timestamp) new Date());
//				preparedStatement.setInt(11, updated_by);
//				preparedStatement.setTimestamp(12, (Timestamp) new Date());
//
//				preparedStatement.executeUpdate();
//				System.out.println(" === Insert data in infra_master table successfully === ");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static int getMonitoringInstancesId(String sheetName) {

		int instanceId = 0;
		ResultSet rs = null;
		PreparedStatement selectStatement = null;

		try {
			String Query = "SELECT MAX(monitoring_instance_id) AS monitoring_instance_id FROM monitoring_instances where instance_name= '"
					+ sheetName + "'";
//			String Query = "SELECT MAX(monitoring_instance_id) AS monitoring_instance_id FROM monitoring_instances where instance_name=\"" + sheetName + "\"";

//				
//				statement = connection.createStatement();
			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {
				instanceId = rs.getInt("monitoring_instance_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}
		if (instanceId == 0) {
			System.out.println("***** monitoring_instance_id of " + sheetName
					+ " datasheet (instance_name) is not found in monitoring_instances table *****");
			JOptionPane.showMessageDialog(new Frame(), "monitoring_instance_id of " + sheetName
					+ " datasheet (instance_name) is not found in monitoring_instances table");
			Framework.driver.quit();
			System.exit(1);
		}
		return instanceId;

	}

	public static String getMonitoringDetails(int instanceId) {

		String zone = "";
		ResultSet rs = null;
		PreparedStatement selectStatement = null;

		try {
			String Query = "SELECT zone FROM monitoring_instances where monitoring_instance_id=" + instanceId;
			selectStatement = dbConnection.prepareStatement(Query);
			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {
				zone = rs.getString("zone");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

		return zone;

	}

	public static List<String> getDeviceDetails(String projectName) {
		ArrayList<String> arr = new ArrayList<String>();
		String device_type = null;
		String browser_type = null;
		String version = null;
		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		try {
//			String Query = "SELECT * FROM monitoring_instances where instance_name= '" + projectName + "'";
			String Query = "SELECT * FROM monitoring_instances where instance_name= \"" + projectName + "\"";

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {

				device_type = rs.getString("device_type");
				browser_type = rs.getString("browser_type");
				version = rs.getString("version");
			}

			arr.add(device_type);
			arr.add(browser_type);
			arr.add(getBrowserVersion());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}
		return arr;
	}

	public static List<String> getContainerJenkinMasterIP(String createdBy) {
		ArrayList<String> arr = new ArrayList<String>();
		String infraId = null;
		String containerIP = null;
		String jenkinsIP = null;

		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		try {
			String Query = "SELECT * FROM infra_master where created_by= \"" + createdBy + "\"";
			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {

				infraId = String.valueOf(rs.getInt("infra_id"));
				containerIP = rs.getString("container_ip");
				jenkinsIP = rs.getString("jenkins_master_ip");
			}

			arr.add(infraId);
			arr.add(containerIP);
			arr.add(jenkinsIP);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}
		return arr;
	}

//	public static int getInstanceRunId() {
//
//		int instanceRunId = 0;
//		ResultSet rs = null;
//		PreparedStatement selectStatement = null;
//		Connection connection = null;
//		try {
//			String Query = "SELECT MAX(instance_run_id) AS instance_run_id FROM runtime_instance";
//
//			connection = DriverManager.getConnection(dbDetails.get("hostUrl"), dbDetails.get("userId"),
//					dbDetails.get("password"));
//			selectStatement = connection.prepareStatement(Query);
//
//			System.out.println("=============>>>>>>>>" + Query);
//			rs = selectStatement.executeQuery();
//			while (rs.next()) {
//
//				instanceRunId = rs.getInt("instance_run_id");
//				System.out.println("instanceRunId Id  ===>" + instanceRunId);
//
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				selectStatement.close();
//				connection.close();
//			} catch (Exception e) {
//				System.out.println("In Finally");
//				e.printStackTrace();
//			}
//		}
//
//		return instanceRunId;
//
//	}
//
//	public static int getMaxInstanceRunId(int applicationId) {
//
//		int instanceRunId = 0;
//		ResultSet rs = null;
//		PreparedStatement selectStatement = null;
//		Connection connection = null;
//		try {
//			String Query = "SELECT MAX(instance_run_id) AS instance_run_id FROM runtime_instance WHERE application_id="
//					+ applicationId;
//
//			connection = DriverManager.getConnection(dbDetails.get("hostUrl"), dbDetails.get("userId"),
//					dbDetails.get("password"));
//			selectStatement = connection.prepareStatement(Query);
//
//			System.out.println("=============>>>>>>>>" + Query);
//			rs = selectStatement.executeQuery();
//			while (rs.next()) {
//
//				instanceRunId = rs.getInt("instance_run_id");
//				System.out.println("instanceRunId Id  ===>" + instanceRunId);
//
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				selectStatement.close();
//				connection.close();
//			} catch (Exception e) {
//				System.out.println("In Finally");
//				e.printStackTrace();
//			}
//		}
//
//		return instanceRunId;
//
//	}

	public static ArrayList<Integer> getApplicationMasterData(String appName) {

		ArrayList<Integer> al = new ArrayList<>();
		int applicationId = 0;
		int clientId = 0;
		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		try {
			String Query = "SELECT * FROM application_master where application_name= \"" + appName + "\""
					+ " AND application_type=\"WEB\"";

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {
				applicationId = rs.getInt("application_id");
				clientId = rs.getInt("client_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}
		al.add(applicationId);
		al.add(clientId);

		return al;
	}

	public static String getclientMasterData(int clientId) {

		String domain = "";
		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		try {
			String Query = "SELECT * FROM client_master where client_id=" + clientId;

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {
				domain = rs.getString("domain");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

		return domain;
	}

	public static int getPageId(int applicationId, String pageName) {

		int pageId = 0;
		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

//			String Query = "SELECT page_id FROM page_master where page_name='" + pageName + "'";

			String Query = "SELECT page_id FROM page_master where page_name=\"" + pageName + "\"";

//				
			selectStatement = dbConnection.prepareStatement(Query);
			System.out.printf(" [DB]  QUERY        : %s%n", Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {

				pageId = rs.getInt("page_id");
//				System.out.println("page Id  ===>" + pageId);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}
		if (pageId == 0) {
			System.out.println("***** page_id of " + pageName + " is not found in page_master Table *****");
			JOptionPane.showMessageDialog(new Frame(), "page_id of " + pageName + " is not found in page_master Table");
			Framework.driver.quit();
			System.exit(1);
		}
		return pageId;

	}

	public static void updateEndTimeRunTimeInstance(String status, String endTime, int appId, int runId) {

		int rs = 0;
		PreparedStatement selectStatement = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String Query = "UPDATE runtime_instance SET end_time ='" + Timestamp.valueOf(endTime) + "' , status='"
					+ status + "' WHERE application_id=" + appId + " AND instance_run_id=" + runId;

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.printf(" [DB]  QUERY      : %s%n", Query);
			rs = selectStatement.executeUpdate();
			System.out.printf(" [DB]  STATUS     : %d rows updated successfully [✓]%n", rs);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

	}

	public static void updateAppluctionUrl(int applicationId, String url) {

		int rs = 0;
		PreparedStatement selectStatement = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String Query = "UPDATE application_master SET application_url ='" + url + "' WHERE application_id="
					+ applicationId;

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeUpdate();

			System.out.println("rows updated successfully " + rs);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

	}

	public static String getCreatedBy(int applicationId) {

		String createdById = null;

		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		try {
			String Query = "SELECT created_by FROM application_master where application_id=" + applicationId
					+ " AND application_type='Web'";

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeQuery();
			while (rs.next()) {

				createdById = rs.getString("created_by");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

		return createdById;

	}

	// Amar
	public static void Table_reports_runtime_instance(String instance_name, String start_time, String end_time,
			String status, String business_date, String business_hour, String business_min, String browser_type,
			String device_type, String version, String container_ip, String jenkins_master_ip,
			int monitoring_instance_id, int application_ids, int infra_id, String created_by, String created_on,
			String updated_by, String updated_on, double netSpeed) {

//		ArrayList<String> networkDetails = networkDetails();
//		String networkIp = null;
//		String location = null;
//		String networkProvider = null;

		ArrayList<String> networkDetails = new ArrayList<String>();
		String networkIp = "";
		String location = "";
		String networkProvider = "";

		try {
			networkIp = networkDetails.get(0);
			location = networkDetails.get(1);
			networkProvider = networkDetails.get(2);
		} catch (Exception e) {
			System.out.println("Network Details not found....");
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String query = "INSERT INTO reports_runtime (instance_name,location,start_time, end_time, status, business_date, business_hour, business_min,browser_type,device_type,"
					+ "application_type,version,container_ip,jenkins_master_ip,bandwidth,monitoring_instance_id,application_id,"
					+ "infra_id, created_by, created_on, updated_by, updated_on,network_provider,network_ip) VALUES ( ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?,?,?)";

			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, instance_name);
				preparedStatement.setString(2, location);
				preparedStatement.setTimestamp(3, Timestamp.valueOf(start_time));
				preparedStatement.setTimestamp(4, Timestamp.valueOf(end_time));
				preparedStatement.setString(5, status);
				preparedStatement.setInt(6, Integer.parseInt(business_date));
				preparedStatement.setInt(7, Integer.parseInt(business_hour));
				preparedStatement.setInt(8, Integer.parseInt(business_min));
				preparedStatement.setString(9, browser_type);
				preparedStatement.setString(10, device_type);
				preparedStatement.setString(11, "Web");
				preparedStatement.setString(12, version);
				preparedStatement.setString(13, container_ip);
				preparedStatement.setString(14, jenkins_master_ip);
				preparedStatement.setDouble(15, netSpeed);

				preparedStatement.setInt(16, monitoring_instance_id);
				preparedStatement.setInt(17, application_ids);
				preparedStatement.setInt(18, infra_id);

				preparedStatement.setString(19, created_by);
				preparedStatement.setTimestamp(20, Timestamp.valueOf(created_on));
				preparedStatement.setString(21, updated_by);
				preparedStatement.setTimestamp(22, Timestamp.valueOf(updated_on));

				preparedStatement.setString(23, networkProvider);
				preparedStatement.setString(24, networkIp);

				preparedStatement.executeUpdate();

				ResultSet keys = preparedStatement.getGeneratedKeys();
				if (keys.next()) {
					Framework.reportRunTimeInstanceId = keys.getInt(1);
					System.out.println("============ Report Runtime Instance Id is " + Framework.reportRunTimeInstanceId
							+ " =========");
				}
				System.out.println(" === get data in reports_runtime table successfully === ");

				preparedStatement.close();
				keys.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Amar
	public static void Table_reports_table(String instance_name, int instanceRunID, String business_date,
			String business_hour, String business_min, String error, String harid, String error_url,
			double responseTime, int monitoring_instance_id, int application_id, int page_id, String created_by,
			String created_on, String updated_by, String updated_on) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String query = "INSERT INTO reports_table (instance_name,business_date,business_hour, business_min,page_name, error, harid, page_url,response_time, monitoring_instance_id, application_id, page_id,instance_run_id,created_by,updated_by,error_description) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

			try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
				preparedStatement.setString(1, instance_name);
				preparedStatement.setInt(2, Integer.parseInt(business_date));
				preparedStatement.setInt(3, Integer.parseInt(business_hour));
				preparedStatement.setInt(4, Integer.parseInt(business_min));
				preparedStatement.setString(5, Framework.pagename);
				preparedStatement.setString(6, error);
				preparedStatement.setString(7, harid);
				preparedStatement.setString(8, error_url);
				preparedStatement.setDouble(9, responseTime);
				preparedStatement.setInt(10, monitoring_instance_id);
				preparedStatement.setInt(11, application_id);
				preparedStatement.setInt(12, page_id);
				preparedStatement.setInt(13, instanceRunID);
				preparedStatement.setString(14, created_by);
//			preparedStatement.setTimestamp(15, Timestamp.valueOf(created_on));
				preparedStatement.setString(15, updated_by);
				preparedStatement.setString(16, Framework.errorDesc);
//			preparedStatement.setTimestamp(17, Timestamp.valueOf(updated_on));

				preparedStatement.executeUpdate();
				System.out.println(" === Insert data in reports_table table successfully === ");
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Amar
	public static void updateEndTimeReportsRuntime(String status, String endTime, int appId, int runId) {

		int rs = 0;
		PreparedStatement selectStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String Query = "UPDATE reports_runtime SET end_time ='" + Timestamp.valueOf(endTime) + "' , status='"
					+ status + "' WHERE application_id=" + appId + " AND instance_run_id=" + runId;

			selectStatement = dbConnection.prepareStatement(Query);

			System.out.println("=============>>>>>>>>" + Query);
			rs = selectStatement.executeUpdate();

			System.out.println("rows updated successfully " + rs);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				selectStatement.close();
			} catch (Exception e) {
				System.out.println("In Finally");
				e.printStackTrace();
			}
		}

	}

	public static void insertIntoAllStockAlert(String stockName, String mailBody) {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format1.format(new Date());
		PreparedStatement preparedStatement = null;
		try {

			String query = "INSERT INTO AllStockAlert (stockName,actStockPrice,trigger_flag,dateAndtime) VALUES (?,?,?,?)";
			preparedStatement = dbConnection.prepareStatement(query);
			preparedStatement.setString(1, stockName);
			preparedStatement.setString(2, mailBody);
			preparedStatement.setString(3, "Y");
			preparedStatement.setString(4, dateTime);

			System.out.println("=============>>>>>>>>" + query);
			int executeUpdate = preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("rows updated successfully " + executeUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
