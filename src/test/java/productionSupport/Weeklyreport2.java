package productionSupport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;

public class Weeklyreport2 {
	static Connection connection = null;
	private static String filePath;
	static HashMap<String, String> details ;

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
			details = readDetails();
			String zones = details.get("zones");
			String[] zonelist = { zones };
			if (zones.contains(",")) {
				zonelist = zones.split(",");
			}
			for (String zone : zonelist) {
				
				filePath = createFilePath(zone);
				String mainQuery = createQuery(zone, details.get("fromDate"), details.get("toDate"));

//				System.out.println("*******************************");
//				System.out.println("Your query \n" + mainQuery);
//				System.out.println("*******************************");
				System.out.println("Please wait," + zone + " Zone query is executing.....");
				ResultSet fullDayResultSet = statement.executeQuery(mainQuery);
				
				int monitoring_instance_id;
				int frequency;
				int desireCount;
				int runcount;
				int successCount;
				int misAdditional;
				int totalDaysRunning;
				int pageCount;
				int successPageCount;
				int misAdditionalPageCount;
				String instanceName;
				while (fullDayResultSet.next()) {

					monitoring_instance_id = fullDayResultSet.getInt("monitoring_instance_id");
//					System.out.println("monitoring_instance_id  "+monitoring_instance_id);
					frequency = fullDayResultSet.getInt("application_freq");
					runcount = fullDayResultSet.getInt("run_count");
					successCount = fullDayResultSet.getInt("success_count");
					pageCount = fullDayResultSet.getInt("pageCount");
					instanceName = fullDayResultSet.getString("instance_name");
//					System.out.println("id " + monitoring_instance_id);

					String fromDate = details.get("fromDate").split(" ")[0];
					String toDate = details.get("toDate").split(" ")[0];
					totalDaysRunning = calculateHowManyDaysRun(monitoring_instance_id, fromDate, toDate);
//					System.out.println(" totalRunningDays" + totalDaysRunning);

					String ascValue = getTotalRunningTime(monitoring_instance_id, "ASC");
					String descValue = getTotalRunningTime(monitoring_instance_id, "desc");
					try {
						if (ascValue.equalsIgnoreCase(descValue)) {

//						System.out.println("Asc and Desc Value Same " + ascValue);
							String hour = ascValue.split(":")[0];
							String min = ascValue.split(":")[1];
							desireCount = (Integer.valueOf(hour) * 60) / frequency;
							desireCount = desireCount + ((Integer.valueOf(min) + 1)) / frequency;

						} else {
							// adding both offMarket and OnMarket
//						System.out.println("Asc and Desc Value Not Same " + ascValue);
							String ascHour = ascValue.split(":")[0];
							String ascMin = ascValue.split(":")[1];
							String descHour = descValue.split(":")[0];
							String descMin = descValue.split(":")[1];
							desireCount = ((Integer.valueOf(ascHour) + Integer.valueOf(descHour)) * 60) / frequency;
							desireCount = desireCount
									+ (Integer.valueOf(ascMin) + Integer.valueOf(descMin) + 2) / frequency;
						}
						desireCount=desireCount*totalDaysRunning;
						misAdditional = successCount - desireCount;
						pageCount=desireCount*pageCount;
						successPageCount=getSuccessPagecount(instanceName);
						misAdditionalPageCount=successPageCount-pageCount;
//					System.out.println(instanceName+"|"+ monitoring_instance_id+ " has desire count --->" + desireCount);
						csvWrite(filePath, instanceName, zone, frequency, desireCount, runcount, successCount, misAdditional,pageCount,successPageCount,misAdditionalPageCount);
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
				System.out.println(zone + " Zone successfully generated! \n");

				
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
		String query = "SELECT  \n" + "	 mi.monitoring_instance_id,\n"
//				+ "    REPLACE(mi.instance_name, '_', ' ') AS instance_name,  \n"
				+ "    mi.instance_name AS instance_name,  \n"
				+ "    mi.zone,  \n"
				+ "    mi.application_freq,\n" + "    0 AS desired_count,   \n"
				+ "    COUNT(ri.instance_run_id) AS run_count,\n"
				+ "    COUNT(CASE WHEN ri.status = 'success' THEN 1 END) AS success_count, \n"
				+ "    0 AS missed_additional , \n" 
				+ "    mi.page_count AS pageCount\n"
				+ "FROM\n" + "    monitoring_instances mi  \n" + "JOIN  \n"
				+ "    runtime_instance ri ON ri.monitoring_instance_id = mi.monitoring_instance_id\n" + "WHERE\n"
				+ "    mi.zone IN ('"+zone+"') \n" + "    AND mi.is_active = 'Y'\n" + "    AND ri.start_time BETWEEN '"
				+ fromDate + "' AND '" + toDate + "'   \n" + "    AND ri.end_time BETWEEN  '" + fromDate + "' AND '"
				+ toDate + "'   \n" + "GROUP BY  \n" + "    mi.instance_name,\n" + "    mi.zone,  \n"
				+ "    mi.application_freq";
		return query;
	}

	public static HashMap<String, String> readDetails() {

		String path = System.getProperty("user.dir") + File.separator + "report.properties";
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

	public static int calculateHowManyDaysRun(int monitoring_instance_id, String fromDate, String toDate)
			throws SQLException {

		LocalDate ldfromDate = LocalDate.parse(fromDate);// we can use formatter of specific type
		LocalDate ldtoDate = LocalDate.parse(toDate);
		int totalDays = 0;

		Statement statementCal = connection.createStatement();
		while (!ldfromDate.isAfter(ldtoDate)) {

			String dayWiseIdsQuery = "SELECT \n" + "    ms.monitoring_instance_id AS id,\n"
					+ "    GROUP_CONCAT(ms.days_of_week) AS schedules\n" + "FROM monitoring_schedules  ms\n" + "WHERE\n"
					+ "   (\n" + "\n" + "        (WEEKDAY('" + ldfromDate + "') = 6 AND ms.days_of_week = '1-7')\n"
					+ "     OR (WEEKDAY('" + ldfromDate + "') = 5 AND ms.days_of_week <> '1-5')\n"
					+ "     OR (WEEKDAY('" + ldfromDate + "') NOT IN (5,6))\n" + "   )\n"
					+ "   AND monitoring_instance_id=" + monitoring_instance_id + "\n"
					+ "GROUP BY ms.monitoring_instance_id;";

//			System.out.println("Query  ---\n "+dayWiseIdsQuery);
			ResultSet fullDayResultSet = statementCal.executeQuery(dayWiseIdsQuery);
			if (fullDayResultSet.next()) {
//				System.out.println(ldfromDate + "--------------" + fullDayResultSet.getString("schedules"));
				totalDays++;
			}
			ldfromDate = ldfromDate.plusDays(1);
		}
		statementCal.close();
		return totalDays;
	}

	public static String getTotalRunningTime(int monitoring_instance_id, String sort) throws SQLException {

		String runningHour = "";
		String query = "SELECT \n" + "      TIME_FORMAT(\n" + "        TIMEDIFF(\n"
				+ "            -- If end_time < start_time, treat it as next day\n"
				+ "            IF(ms.end_time < ms.start_time,\n"
				+ "               TIMESTAMP(CURDATE() + INTERVAL 1 DAY, ms.end_time),\n"
				+ "               TIMESTAMP(CURDATE(), ms.end_time)\n" + "            ),\n"
				+ "            TIMESTAMP(CURDATE(), ms.start_time)\n" + "        ),\n" + "        '%H:%i'\n"
				+ "    ) AS hours_min_diff\n" + "FROM monitoring_schedules ms\n" + "WHERE ms.monitoring_instance_id ="
				+ monitoring_instance_id + "\n" + "ORDER BY ms.schedule_id " + sort + "\n" + "LIMIT 1";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			runningHour = resultSet.getString("hours_min_diff");
		}
		return runningHour;
	}
	
	public static void csvWrite(String csvFilePath,String instance_name, String zone,int frequency, int desireCount,
			int runcount, int successCount,int misAdditional,int desired_pageCount,int success_pageCount,int missed_additional_pagecount)
					throws IOException {
		File csvFile = new File(csvFilePath);
		boolean fileExists = csvFile.exists();
		
		try {
			FileWriter fw = new FileWriter(csvFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			// Add header only once when file is created
			if (!fileExists) {
				out.println("instance_name,zone,application_freq,Desired_Count,Run_Count,success_count,Missed_Additional,desired_pageCount,success_pageCount,missed_additional_pagecount");
			}
			// Append data safely with quotes (avoid comma issues)
			out.println(String.join(",", "\"" + instance_name + "\"", "\"" + zone + "\"", "\"" + frequency + "\"", "\"" + desireCount + "\"",
					"\"" + runcount + "\"", "\"" + successCount + "\"","\"" + misAdditional + "\"","\"" + desired_pageCount + "\"","\"" + success_pageCount + "\"","\"" + missed_additional_pagecount + "\""));
			out.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String createFilePath(String zone) {
		
		String path = System.getProperty("user.dir") + "/Reports/";
		
		if(!new File(path).exists())
		{
			new File(path).mkdirs();
		}
		path=path+ zone + ".csv";
		return path;
	}
	
	
	public static int getSuccessPagecount(String instanceName) throws SQLException {
		
		int successCount = 0;
		String query = "SELECT COUNT(*) AS successCount\n"
				+ "FROM execution_data \n"
				+ "WHERE instance_name='"+instanceName+"' \n"
				+ "  AND created_on <'"+details.get("toDate")+"' \n"
				+ "  AND created_on > '"+ details.get("fromDate")+"'";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			successCount = resultSet.getInt("successCount");
		}
//		System.out.println("--------------- Successpage Count We get "+ successCount);
		return successCount;
		
	}
}
