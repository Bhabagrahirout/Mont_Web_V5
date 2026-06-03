package productionSupport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class WeeklyReport {
	static Connection connection = null;

	
	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@SuppressWarnings("unused")
			public void run() {
				if (connection != null) {
					try {
						connection.close();
						System.out.println(" DB Connection close in shotdown hook");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});

		try {
			System.out.println("Current Time ----->" + LocalDateTime.now());

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://192.168.1.228:3306/nonsbi_final?connectTimeout=5000",
					"SynMonitoring", "Operation@123");
			Statement statement = connection.createStatement();
			HashMap<String, String> details = readDetails();
			String zones = details.get("zones");
			String[] zonelist = { zones };
			if (zones.contains(",")) {
				zonelist = zones.split(",");
			}
			for (String zone : zonelist) {

				String query = createQuery(zone, details.get("fromDate"), details.get("toDate"));

//				System.out.println("*******************************");
//				System.out.println("Your query"+query);
//				System.out.println("*******************************");
				System.out.println("Please wait,"+zone+" Zone query is executing.....");
				ResultSet rs = statement.executeQuery(query);
				String path = System.getProperty("user.dir") + "/Reports/";
				
				if(!new File(path).exists())
				{
					new File(path).mkdirs();
				}
				path=path+ zone + ".csv";
				File file = new File(path);
				file.createNewFile();

				FileWriter fw = new FileWriter(file);
				BufferedWriter bf = new BufferedWriter(fw);
				bf.write(
						"instance_name,zone,application_freq,desired_count,run_count,success_count,missed_additional\n");

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnSize = rsmd.getColumnCount();

				while (rs.next()) {
					for (int i = 1; i <= columnSize; i++) {
						bf.write(rs.getString(i) + ",");
					}
					bf.write("\n");
				}

				bf.close();
				fw.close();
				System.out.println(zone + " Zone successfully generated!");
			}
			connection.close();

		} catch (Exception e) {
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	public static String createQuery(String zone, String fromDate, String toDate) {

//		System.out.println("zone ->"+zone);
//		System.out.println("fromDate ->"+fromDate);
//		System.out.println("toDate ->"+toDate);
		String query = "SELECT  \n" + "    REPLACE(mi.instance_name, '_', ' ') AS instance_name,  \n"
				+ "    mi.zone,  \n" + "    mi.application_freq,\n" + "    FLOOR(TIMESTAMPDIFF(MINUTE, '" + fromDate
				+ "', '" + toDate + "') / mi.application_freq) AS desired_count,   \n"
				+ "    COUNT(ri.instance_run_id) AS run_count,\n"
				+ "    COUNT(CASE WHEN ri.status = 'success' THEN 1 END) AS success_count, \n"
				+ "    COUNT(CASE WHEN ri.status = 'success' THEN 1 END) -\n" + "        FLOOR(TIMESTAMPDIFF(MINUTE, '"
				+ fromDate + "', '" + toDate + "') / mi.application_freq) AS missed_additional  \n" + "FROM\n"
				+ "    monitoring_instances mi  \n" + "JOIN  \n"
				+ "    runtime_instance ri ON ri.monitoring_instance_id = mi.monitoring_instance_id\n" + "WHERE\n"
				+ "    mi.zone IN ('" + zone + "') \n" + "    AND mi.is_active = 'Y'\n"
				+ "    AND ri.start_time BETWEEN '" + fromDate + "' AND '" + toDate + "'   \n"
				+ "    AND ri.end_time BETWEEN  '" + fromDate + "' AND '" + toDate + "'  \n" + "GROUP BY  \n"
				+ "    mi.instance_name,\n" + "    mi.zone,  \n" + "    mi.application_freq";

		return query;
	}

	public static HashMap<String, String> readDetails() {

		String path=System.getProperty("user.dir")+File.separator+"report.properties";
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String zones = prop.getProperty("zones", " ");
		String fromDate = prop.getProperty("fromDate", " ");
		String toDate = prop.getProperty("toDate", " ");

		HashMap<String, String> details = new HashMap();
		details.put("zones", zones);
		details.put("fromDate", fromDate);
		details.put("toDate", toDate);

		return details;

	}

}
