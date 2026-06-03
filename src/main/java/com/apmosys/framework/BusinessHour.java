package com.apmosys.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class BusinessHour {
	private static String monitoringDatasheet;
	static String monitoringTimeDatasheetPath;
	private static long DateDifference;
	private static int monitoringtime;
	private static String currentD;
	public static String currentDateAndTime;

	public static void monitoringHour() {
		BusinessHour.monitoringDatasheet = System.getProperty("user.dir") + "/DataSheet/"
				+ Framework.monitoringDataSheetPath;
		System.out.println("monitoringDatasheet   ============ " + BusinessHour.monitoringDatasheet);
		try {
			String output = null;
			String filepath = System.getProperty("user.dir");
			final File file = new File(String.valueOf(filepath) + "/monitoringDate.txt");
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
				monitoringCompareDate(output);
				final long dateDiff = BusinessHour.DateDifference;
				final Fillo fillo = new Fillo();
				final String query = "select * from Sheet3";
				Recordset record = null;
				String pageName = null;
				monitoringTime();
				if (dateDiff > 4L && BusinessHour.monitoringtime >= 17 && BusinessHour.monitoringtime < 18) {
					try {
						final Connection con = fillo.getConnection(BusinessHour.monitoringDatasheet);
						record = con.executeQuery(query);
						System.out.println(record.getCount());
						while (record.next()) {
							pageName = record.getField("PageName");
							System.out.println("PageName Is    ============ " + pageName);
							final String sql = "Update Sheet1 set RunStatus='Y' where PageName='" + pageName
									+ "' and RunStatus='N'";
							con.executeUpdate(sql);
						}
						file.createNewFile();
						final FileWriter fileWritter = new FileWriter(file, false);
						final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(BusinessHour.currentD);
						bufferWritter.close();
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				} else {
					final Connection con = fillo.getConnection(BusinessHour.monitoringDatasheet);
					record = con.executeQuery(query);
					System.out.println(record.getCount());
					while (record.next()) {
						pageName = record.getField("PageName");
						System.out.println("PageName Is    ============ " + pageName);
						final String sql = "Update Sheet1 set RunStatus='N' where PageName='" + pageName + "'";
						con.executeUpdate(sql);
					}
				}
			} else {
				file.createNewFile();
				final Date dt = new Date();
				final SimpleDateFormat smdt = new SimpleDateFormat("dd/MM/yyyy");
				final String currentDate = smdt.format(dt);
				System.out.println("In else Runable Date============" + currentDate);
				final FileWriter fileWritter2 = new FileWriter(file, false);
				final BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
				bufferWritter2.write(currentDate);
				bufferWritter2.close();
				executePage();
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
	}

	private static void executePage() {
		try {
			final Fillo fillo = new Fillo();
			final String query = "select * from Sheet3";
			Recordset record = null;
			String pageName = null;
			monitoringTime();
			if (BusinessHour.monitoringtime >= 16 && BusinessHour.monitoringtime < 17) {
				final Connection con = fillo.getConnection(BusinessHour.monitoringDatasheet);
				record = con.executeQuery(query);
				System.out.println(record.getCount());
				while (record.next()) {
					pageName = record.getField("PageName");
					System.out.println("PageName Is    ============ " + pageName);
					final String sql = "Update Sheet1 set RunStatus='Y' where PageName='" + pageName + "'";
					con.executeUpdate(sql);
					System.out.println(sql);
				}
			} else {
				final Connection con = fillo.getConnection(BusinessHour.monitoringDatasheet);
				record = con.executeQuery(query);
				System.out.println(record.getCount());
				while (record.next()) {
					pageName = record.getField("PageName");
					System.out.println("PageName Is    ============ " + pageName);
					final String sql = "Update Sheet1 set RunStatus='N' where PageName='" + pageName + "'";
					con.executeUpdate(sql);
					System.out.println(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void monitoringTime() {
		final SimpleDateFormat format = new SimpleDateFormat("HH");
		final Date d = new Date();
		final String currenttime = format.format(d);
		BusinessHour.monitoringtime = Integer.parseInt(currenttime);
		System.out.println("Current time  " + BusinessHour.monitoringtime);
	}

	private static void monitoringCompareDate(final String output) {
		final Date dt = new Date();
		final SimpleDateFormat smdt = new SimpleDateFormat("dd/MM/yyyy");
		BusinessHour.currentD = smdt.format(dt);
		System.out.println(BusinessHour.currentD);
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		final LocalDate date1 = LocalDate.parse(BusinessHour.currentD, dtf);
		final LocalDate date2 = LocalDate.parse(output, dtf);
		BusinessHour.DateDifference = ChronoUnit.DAYS.between(date2, date1);
		System.out.println("Days Difference == : " + BusinessHour.DateDifference);
	}

	public static void monitoringMinute() {
		BusinessHour.monitoringTimeDatasheetPath = System.getProperty("user.dir") + "/DataSheet/"
				+ Framework.monitoringTimeDataSheetName;
		System.out.println("monitoringTimeDatasheetPath   ============ " + BusinessHour.monitoringTimeDatasheetPath);
		try {
			String output = null;
			final String filepath = System.getProperty("user.dir");
			final File file = new File(String.valueOf(filepath) + "/monitoringMinute.txt");
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
				final String diffMinute = monitoringTimeCompare(output);
				final Fillo fillo = new Fillo();
				final String query = "select * from Sheet3";
				Recordset record = null;
				String pageName = null;
				if (Integer.parseInt(diffMinute) >= 35) {
					try {
						final Connection con = fillo.getConnection(BusinessHour.monitoringTimeDatasheetPath);
						record = con.executeQuery(query);
						System.out.println("Total Page Is == " + record.getCount());
						while (record.next()) {
							pageName = record.getField("PageName");
							System.out.println("PageName Name Is  ============ " + pageName);
							final String sql = "Update Sheet1 set RunStatus='Y' where PageName='" + pageName
									+ "' and RunStatus='N'";
							con.executeUpdate(sql);
						}
						file.createNewFile();
						final FileWriter fileWritter = new FileWriter(file, false);
						final BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(BusinessHour.currentDateAndTime);
						bufferWritter.close();
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				} else {
					final Connection con = fillo.getConnection(BusinessHour.monitoringTimeDatasheetPath);
					record = con.executeQuery(query);
					System.out.println(record.getCount());
					while (record.next()) {
						pageName = record.getField("PageName");
						System.out.println("PageName Is    ============ " + pageName);
						final String sql = "Update Sheet1 set RunStatus='N' where PageName='" + pageName + "'";
						con.executeUpdate(sql);
					}
				}
			} else {
				final Date date = new Date();
				final SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				BusinessHour.currentDateAndTime = d.format(date);
				System.out.println("Current Date And Time Is === " + BusinessHour.currentDateAndTime);
				file.createNewFile();
				final FileWriter fileWritter2 = new FileWriter(file, false);
				final BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
				bufferWritter2.write(BusinessHour.currentDateAndTime);
				bufferWritter2.close();
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
	}

	public static String monitoringTimeCompare(final String output) {
		final Date date = new Date();
		final SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BusinessHour.currentDateAndTime = d.format(date);
		System.out.println("Current Date And Time Is === " + BusinessHour.currentDateAndTime);
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		final LocalDateTime date2 = LocalDateTime.parse(BusinessHour.currentDateAndTime, dtf);
		final LocalDateTime date3 = LocalDateTime.parse(output, dtf);
		final long daysBetween = ChronoUnit.MINUTES.between(date3, date2);
		final String diffMinute = String.valueOf(daysBetween);
		System.out.println("Minute Difference Is === " + diffMinute);
		return diffMinute;
	}
}
