package com.apmosys.framework;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class NewCustomFunctions extends Framework {

	public static String adharOtp;

	public static void SendReport() {
		try {
			float IMPS_Success_per = 0.0f;
			float IMPS_Pending_per = 0.0f;
			float IMPS_Failure_per = 0.0f;
			float NEFT_Pending_per = 0.0f;
			float NEFT_Failure_per = 0.0f;
			float NEFT_Success_per = 0.0f;
			final float IMPS_Total_per = 0.0f;
			final float NEFT_Total_per = 0.0f;
			final int IMPS_Success = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblIMPSTotlasuccess")).getText());
			final int IMPS_Pending = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblIMPSTotalPending")).getText());
			final int IMPS_Failure = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblIMPSTotalfailure")).getText());
			final int NEFT_Success = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblNEFTTotlasuccess")).getText());
			final int NEFT_Pending = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblNEFTTotalPending")).getText());
			final int NEFT_Failure = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblNEFTTotalfailure")).getText());
			final int IMPSTotal = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblIMPSTotaltrnxCount")).getText());
			final int NEFTTotal = Integer.parseInt(
					Framework.driver.findElement(By.id("ctl00_ContentPlaceHolder1_lblNEFTTotaltrnxCount")).getText());
			final int IMPS_NEFT_Total = IMPSTotal + NEFTTotal;
			System.out.println("\n IMPSTotal===>" + IMPSTotal);
			System.out.println("\n NEFTTotal===>" + NEFTTotal);
			System.out.println("\n IMPS_Success===>" + IMPS_Success);
			System.out.println("\n IMPS_Pending===>" + IMPS_Pending);
			System.out.println("\n IMPS_Failure===>" + IMPS_Failure);
			System.out.println("\n NEFT_Success===>" + NEFT_Success);
			System.out.println("\n NEFT_Pending===>" + NEFT_Pending);
			System.out.println("\n NEFT_Failure===>" + NEFT_Failure);
			System.out.println("\n\n IMPS_NEFT_Total===>" + IMPS_NEFT_Total);
			final Date now2 = new Date();
			final int year3 = Calendar.getInstance().get(1);
			new File(String.valueOf(Framework.homedir) + "/Reports/" + year3).mkdir();
			final String MonthName2 = new SimpleDateFormat("MMMM").format(now2);
			new File(String.valueOf(Framework.homedir) + "/Reports/" + year3 + "/" + MonthName2).mkdir();
			final int monthday2 = Calendar.getInstance().get(5);
			new File(String.valueOf(Framework.homedir) + "/Reports/" + year3 + "/" + MonthName2 + "/" + monthday2)
					.mkdir();
			final int hr = Calendar.getInstance().get(10);
			final int hrofday = Calendar.getInstance().get(11);
			System.out.println("ht==>" + hr + "====== hrofday ===>" + hrofday);
			final DecimalFormat ft = new DecimalFormat("00");
			final int min = Calendar.getInstance().get(12);
			final int min2 = Calendar.getInstance().get(14);
			Functions.mailTime = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date());
			System.out.println("min==>" + min + "====== min1 ===>" + min2);
			new File(String.valueOf(Framework.homedir) + "/Reports/" + year3 + "/" + MonthName2 + "/" + monthday2 + "//"
					+ Framework.ScriptName).mkdir();
			new File(String.valueOf(Framework.homedir) + "/Reports/" + year3 + "/" + MonthName2 + "/" + monthday2 + "//"
					+ Framework.ScriptName + "/Percentage-Sheet").mkdir();
			Framework.FileNameV = String.valueOf(Framework.homedir) + "/Reports/" + year3 + "/" + MonthName2 + "/"
					+ monthday2 + "/" + Framework.ScriptName + "/NSDLPercentage_" + hrofday + "_" + min + ".xlsx";
			final String fname = String.valueOf(Framework.homedir) + "/Reports/" + year3 + "/" + MonthName2 + "/"
					+ monthday2 + "/" + Framework.ScriptName + "/NSDLPayment_" + hrofday + "_" + min + ".xlsx";
			System.out.println("File Name ==>" + Framework.FileNameV);
			int NeftFP = 0;
			int Diff_IS = 0;
			int Diff_IP = 0;
			int Diff_IF = 0;
			int Diff_NS = 0;
			int Diff_NP = 0;
			int Diff_NF = 0;
			int Diff_imps = 0;
			int Diff_neft = 0;
			String prevRunTime = "";
			final File ff = new File(String.valueOf(Framework.homedir) + "//Reports/Percentage1.xlsx");
			if (ff.exists()) {
				final Fillo fl = new Fillo();
				final com.codoid.products.fillo.Connection con2 = fl
						.getConnection(String.valueOf(Framework.homedir) + "//Reports/Percentage1.xlsx");
				final Recordset rs4 = con2.executeQuery("Select * from Configuration where Record = 1");
				final int count4 = rs4.getCount();
				System.out.println(" outside Count1 ==>" + count4);
				try {
					System.out.println("In side try block........");
					while (rs4.next() && rs4.getCount() != 0) {
						int totalImpsP = Integer.parseInt(rs4.getField("TotalIMPS"));
						int totalNeftP = Integer.parseInt(rs4.getField("TotalNEFT"));
						int ImpsSP = Integer.parseInt(rs4.getField("IMPS_success"));
						int ImpsPP = Integer.parseInt(rs4.getField("IMPS_pending"));
						int ImpsFP = Integer.parseInt(rs4.getField("IMPS_failure"));
						int NeftSP = Integer.parseInt(rs4.getField("NEFT_success"));
						int NeftPP = Integer.parseInt(rs4.getField("NEFT_pending"));
						NeftFP = Integer.parseInt(rs4.getField("NEFT_failure"));
						prevRunTime = rs4.getField("Time");
						if (IMPSTotal < totalImpsP) {
							totalImpsP = 0;
							totalNeftP = 0;
							ImpsSP = 0;
							ImpsPP = 0;
							ImpsFP = 0;
							NeftSP = 0;
							NeftPP = 0;
							NeftFP = 0;
						}
						System.out.println("-------------- Previous Value -------------------------------------");
						System.out.println("ImpsSP==>" + ImpsSP + "\n ImpsPP ==> " + ImpsPP + "\n ImpsFP ==> " + ImpsFP
								+ "\n totalImpsP ==> " + totalImpsP);
						System.out.println("NeftSP==>" + NeftSP + "\n NeftPP ==> " + NeftPP + "\n NeftFP ==> " + NeftFP
								+ "\n totalNeftP ==> " + totalNeftP);
						Diff_imps = IMPSTotal - totalImpsP;
						Diff_IS = IMPS_Success - ImpsSP;
						Diff_IF = IMPS_Failure - ImpsFP;
						Diff_IP = IMPS_Pending - ImpsPP;
						Diff_neft = NEFTTotal - totalNeftP;
						Diff_NS = NEFT_Success - NeftSP;
						Diff_NF = NEFT_Failure - NeftFP;
						Diff_NP = NEFT_Pending - NeftPP;
						System.out.println("-------------- Difference Value -------------------------------------");
						System.out.println("Diff_IS==>" + Diff_IS + "\n Diff_IP ==> " + Diff_IP + "\n Diff_IF ==> "
								+ Diff_IF + "\n Diff_imps ==> " + Diff_imps);
						System.out.println("Diff_NS==>" + Diff_NS + "\n Diff_NP ==> " + Diff_NP + "\n Diff_NF ==> "
								+ Diff_NF + "\n Diff_neft ==> " + Diff_neft);
						if (Diff_imps > 0) {
							IMPS_Success_per = (float) Math.round(Diff_IS / (float) Diff_imps * 100.0f);
						}
						if (Diff_neft > 0) {
							NEFT_Success_per = (float) Math.round(Diff_NS / (float) Diff_neft * 100.0f);
						}
						System.out.println("-------------- Percentage Value -------------------------------------");
						System.out.println("IMPS_SuccessPer ==>" + IMPS_Success_per + "\n");
						System.out.println("NEFT_SuccessPer ==>" + NEFT_Success_per + "\n");
					}
				} catch (Exception e21) {
					System.out.println("In side catch block........");
				}
				con2.close();
			} else {
				System.out.println("File Not Exist... Please create file.");
			}
			final String rec = "1";
			final Fillo filloU = new Fillo();
			final com.codoid.products.fillo.Connection conU = filloU
					.getConnection(String.valueOf(Framework.homedir) + "/Reports/Percentage1.xlsx");
			conU.executeUpdate("update Configuration set TotalIMPS= '" + IMPSTotal + "', TotalNEFT='" + NEFTTotal
					+ "' , IMPS_success='" + IMPS_Success + "' ,  IMPS_pending='" + IMPS_Pending + "' ,  IMPS_failure='"
					+ IMPS_Failure + "' , NEFT_success='" + NEFT_Success + "' , NEFT_pending='" + NEFT_Pending
					+ "' , NEFT_failure='" + NEFT_Failure + "' , Time='" + ft.format(hrofday) + ":" + ft.format(min)
					+ "' where Record='1'");
			conU.close();
			System.out.println("Records Updated Successfully ....");
			if (Diff_IP > 0) {
				IMPS_Pending_per = (float) Math.round(Diff_IP / (float) Diff_imps * 100.0f);
			}
			if (Diff_IF > 0) {
				IMPS_Failure_per = (float) Math.round(Diff_IF / (float) Diff_imps * 100.0f);
			}
			if (Diff_NP > 0) {
				NEFT_Pending_per = (float) Math.round(Diff_NP / (float) Diff_neft * 100.0f);
			}
			if (Diff_NF > 0) {
				NEFT_Failure_per = (float) Math.round(Diff_NF / (float) Diff_neft * 100.0f);
			}
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("cellstyle");
			XSSFRow row = spreadsheet.createRow(1);
			row.setHeight((short) 800);
			XSSFCell cell = row.createCell(6);
			cell.setCellValue(
					"Total Txn as on " + hrofday + ":" + min + ", " + monthday2 + "-" + MonthName2 + "-" + year3);
			spreadsheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));
			final XSSFCell cell2 = row.createCell(9);
			cell2.setCellValue((double) IMPS_NEFT_Total);
			spreadsheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 11));
			XSSFRow row2 = spreadsheet.createRow(2);
			row2.setHeight((short) 800);
			final XSSFCell cell3 = row2.createCell(6);
			cell3.setCellValue("Total IMPS:");
			final XSSFCell cell4 = row2.createCell(7);
			cell4.setCellValue((double) IMPSTotal);
			spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 8));
			final XSSFCell cell5 = row2.createCell(9);
			cell5.setCellValue("Total NEFT:");
			final XSSFCell cell6 = row2.createCell(10);
			cell6.setCellValue((double) NEFTTotal);
			spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
			final XSSFRow row3 = spreadsheet.createRow(3);
			row3.setHeight((short) 800);
			final XSSFCell cell7 = row3.createCell(6);
			cell7.setCellValue("Success:");
			final XSSFCell cell8 = row3.createCell(7);
			cell8.setCellValue("Pending:");
			final XSSFCell cell9 = row3.createCell(8);
			cell9.setCellValue("Failure:");
			final XSSFCell cell10 = row3.createCell(9);
			cell10.setCellValue("Success:");
			final XSSFCell cell11 = row3.createCell(10);
			cell11.setCellValue("Pending:");
			final XSSFCell cell12 = row3.createCell(11);
			cell12.setCellValue("Failure:");
			final XSSFRow row4 = spreadsheet.createRow(4);
			row3.setHeight((short) 800);
			final XSSFCell cell13 = row4.createCell(6);
			cell13.setCellValue((double) IMPS_Success);
			final XSSFCell cell14 = row4.createCell(7);
			cell14.setCellValue((double) IMPS_Pending);
			final XSSFCell cell15 = row4.createCell(8);
			cell15.setCellValue((double) IMPS_Failure);
			final XSSFCell cell16 = row4.createCell(9);
			cell16.setCellValue((double) NEFT_Success);
			final XSSFCell cell17 = row4.createCell(10);
			cell17.setCellValue((double) NEFT_Pending);
			final XSSFCell cell18 = row4.createCell(11);
			cell18.setCellValue((double) NEFT_Failure);
			final XSSFRow row5 = spreadsheet.createRow(5);
			row5.setHeight((short) 800);
			final XSSFCell cell_55 = row5.createCell(5);
			cell_55.setCellValue("Percentage");
			final XSSFCell cell_56 = row5.createCell(6);
			cell_56.setCellValue(String.valueOf(IMPS_Success_per) + "%");
			final XSSFCell cell_57 = row5.createCell(7);
			cell_57.setCellValue(String.valueOf(IMPS_Pending_per) + "%");
			final XSSFCell cell_58 = row5.createCell(8);
			cell_58.setCellValue(String.valueOf(IMPS_Failure_per) + "%");
			final XSSFCell cell_59 = row5.createCell(9);
			cell_59.setCellValue(String.valueOf(NEFT_Success_per) + "%");
			final XSSFCell cell_60 = row5.createCell(10);
			cell_60.setCellValue(String.valueOf(NEFT_Pending_per) + "%");
			final XSSFCell cell_61 = row5.createCell(11);
			cell_61.setCellValue(String.valueOf(NEFT_Failure_per) + "%");
			final FileOutputStream out = new FileOutputStream(new File(fname));
			workbook.write((OutputStream) out);
			out.close();
			System.out.println("................Creating File to attach with mail..................");
			workbook = new XSSFWorkbook();
			spreadsheet = workbook.createSheet("cellstyle");
			spreadsheet.setHorizontallyCenter(true);
			row = spreadsheet.createRow(0);
			cell = row.createCell(0);
			final String timestamp = String.valueOf(ft.format(monthday2)) + " " + MonthName2 + " " + year3 + " from "
					+ prevRunTime + "Hrs to " + ft.format(hrofday) + ":" + ft.format(min) + " Hrs";
			cell.setCellValue(timestamp);
			spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			final CellStyle style = (CellStyle) cell.getCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			cell.setCellStyle(style);
			row2 = spreadsheet.createRow(1);
			final XSSFCell cell2A = row2.createCell(0);
			cell2A.setCellValue("Total IMPS:");
			final XSSFCell cell2B = row2.createCell(1);
			cell2B.setCellValue("Success");
			final XSSFCell cell2C = row2.createCell(2);
			cell2C.setCellValue("Pending");
			final XSSFCell cell2D = row2.createCell(3);
			cell2D.setCellValue("Failure");
			final XSSFCell cell2E = row2.createCell(4);
			cell2E.setCellValue(" ");
			spreadsheet.addMergedRegion(new CellRangeAddress(1, 3, 4, 4));
			final XSSFCell cell2F = row2.createCell(5);
			cell2F.setCellValue("NEFT");
			final XSSFCell cell2G = row2.createCell(6);
			cell2G.setCellValue("Success");
			final XSSFCell cell2H = row2.createCell(7);
			cell2H.setCellValue("Pending");
			final XSSFCell cell2I = row2.createCell(8);
			cell2I.setCellValue("Failure");
			final XSSFRow row6 = spreadsheet.createRow(2);
			final XSSFCell cell3A4A = row6.createCell(0);
			cell3A4A.setCellValue((double) Diff_imps);
			spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
			final XSSFCell cell3B = row6.createCell(1);
			cell3B.setCellValue((double) Diff_IS);
			final XSSFCell cell3c = row6.createCell(2);
			cell3c.setCellValue((double) Diff_IP);
			final XSSFCell cell3D = row6.createCell(3);
			cell3D.setCellValue((double) Diff_IF);
			final XSSFCell cell3F = row6.createCell(5);
			cell3F.setCellValue((double) Diff_neft);
			spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
			final XSSFCell cell3G = row6.createCell(6);
			cell3G.setCellValue((double) Diff_NS);
			final XSSFCell cell3H = row6.createCell(7);
			cell3H.setCellValue((double) Diff_NP);
			final XSSFCell cell3I = row6.createCell(8);
			cell3I.setCellValue((double) Diff_NF);
			final XSSFRow row7 = spreadsheet.createRow(3);
			final XSSFCell cell4B = row7.createCell(1);
			cell4B.setCellValue(String.valueOf(IMPS_Success_per) + "%");
			final XSSFCell cell4c = row7.createCell(2);
			cell4c.setCellValue(String.valueOf(IMPS_Pending_per) + "%");
			final XSSFCell cell4D = row7.createCell(3);
			cell4D.setCellValue(String.valueOf(IMPS_Failure_per) + "%");
			final XSSFCell cell4G = row7.createCell(6);
			cell4G.setCellValue(String.valueOf(NEFT_Success_per) + "%");
			final XSSFCell cell4H = row7.createCell(7);
			cell4H.setCellValue(String.valueOf(NEFT_Pending_per) + "%");
			final XSSFCell cell4I = row7.createCell(8);
			cell4I.setCellValue(String.valueOf(NEFT_Failure_per) + "%");
			spreadsheet.setHorizontallyCenter(true);
			spreadsheet.setVerticallyCenter(true);
			spreadsheet.autoSizeColumn(4);
			final FileOutputStream out2 = new FileOutputStream(new File(Framework.FileNameV));
			workbook.write((OutputStream) out2);
			out2.close();
			boolean mailStatus = false;
			String alertMessage = "";
			if (IMPS_Pending_per >= 10.0f || IMPS_Failure_per >= 10.0f || NEFT_Failure_per >= 20.0f) {
				System.out.println("In side team first For IMPS............");
				System.out.println(" IMPS Pending Percentage==>" + IMPS_Pending_per + "\n");
				System.out.println(" IMPS Failure Percentage==>" + IMPS_Failure_per + "\n");
				Boolean commaOrAnd = false;
				if (NEFT_Failure_per >= 20.0f) {
					alertMessage = String.valueOf(alertMessage) + "NEFT Failure";
					commaOrAnd = true;
				}
				if (IMPS_Pending_per >= 10.0f) {
					if (commaOrAnd) {
						alertMessage = String.valueOf(alertMessage) + ", IMPS Pending";
					} else {
						alertMessage = String.valueOf(alertMessage) + "IMPS Pending";
					}
				}
				if (IMPS_Failure_per >= 10.0f) {
					if (commaOrAnd) {
						alertMessage = String.valueOf(alertMessage) + " and IMPS Failure";
					} else {
						alertMessage = String.valueOf(alertMessage) + "IMPS Failure";
					}
				}
				final SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
				final Date starttime = parser.parse("07:30:00");
				final Date Endtime = parser.parse("23:30:00");
				final Date currenttime = parser.parse(new SimpleDateFormat("HH:mm:ss").format(new Date()));

				if (currenttime.after(starttime) && currenttime.before(Endtime)) {
					final String subject = alertMessage;
					System.out.println("XYZ");
					NewCustomFunctions.insertData(timestamp, Diff_imps, Diff_IS, Diff_IP, Diff_IF, Diff_neft, Diff_NS,
							Diff_NP, Diff_NF, IMPS_Success_per, IMPS_Pending_per, IMPS_Failure_per, NEFT_Success_per,
							NEFT_Pending_per, NEFT_Failure_per);
					System.out.println(timestamp + Diff_imps + Diff_IS + Diff_IP + Diff_IF + Diff_neft + Diff_NS
							+ Diff_NP + Diff_NF + IMPS_Success_per + IMPS_Pending_per + IMPS_Failure_per
							+ NEFT_Success_per + NEFT_Pending_per + NEFT_Failure_per);
					final String message = NewCustomFunctions.createMessage(timestamp, Diff_imps, Diff_IS, Diff_IP,
							Diff_IF, Diff_neft, Diff_NS, Diff_NP, Diff_NF, IMPS_Success_per, IMPS_Pending_per,
							IMPS_Failure_per, NEFT_Success_per, NEFT_Pending_per, NEFT_Failure_per);
					TestMail.Sendmail(subject, message);
					mailStatus = true;
					System.out.println("Mail sent....");
				}
			}
			if (mailStatus) {
				final Frame frame7 = new Frame();
				frame7.setVisible(true);
				frame7.toFront();
				if (NEFT_Failure_per >= 20.0f) {
					final Object[] objects2 = { String.valueOf(alertMessage) + " Percentage is Greater than 20 ! " };
					JOptionPane.showMessageDialog(null, objects2, "Alert", -1);
					frame7.setVisible(false);
				}
				if (IMPS_Failure_per >= 10.0f) {
					final Object[] objects2 = { String.valueOf(alertMessage) + " Percentage is Greater than 10 ! " };
					JOptionPane.showMessageDialog(null, objects2, "Alert", -1);
					frame7.setVisible(false);
				}
				if (IMPS_Pending_per >= 10.0f) {
					final Object[] objects2 = { String.valueOf(alertMessage) + " Percentage is Greater than 10 ! " };
					JOptionPane.showMessageDialog(null, objects2, "Alert", -1);
					frame7.setVisible(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void insertData(final String timestamp, final int diff_imps, final int diff_IS, final int diff_IP,
			final int diff_IF, final int diff_neft, final int diff_NS, final int diff_NP, final int diff_NF,
			final float iMPS_Success_per, final float iMPS_Pending_per, final float iMPS_Failure_per,
			final float nEFT_Success_per, final float nEFT_Pending_per, final float nEFT_Failure_per) {
		try {
			System.out.println("InsertData");
			final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final Timestamp date = new Timestamp(new Date().getTime());
			System.out.println("DB Date Is === " + date);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			final Connection conn = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
					Framework.dbpassword);
			final String sql = "INSERT INTO nsdl_whatsapp (application_name,Diff_imps,Diff_IS,Diff_IP,Diff_IF,Diff_neft,Diff_NS,Diff_NP,Diff_NF,iMPS_Success_per,iMPS_Pending_per,iMPS_Failure_per,nEFT_Success_per,nEFT_Pending_per,nEFT_Failure_per,timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			final PreparedStatement statement1 = conn.prepareStatement(
					"INSERT INTO nsdl_whatsapp (application_name,Diff_imps,Diff_IS,Diff_IP,Diff_IF,Diff_neft,Diff_NS,Diff_NP,Diff_NF,iMPS_Success_per,iMPS_Pending_per,iMPS_Failure_per,nEFT_Success_per,nEFT_Pending_per,nEFT_Failure_per,timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement1.setString(1, Framework.ScriptName);
			statement1.setInt(2, diff_imps);
			statement1.setInt(3, diff_IS);
			statement1.setInt(4, diff_IP);
			statement1.setInt(5, diff_IF);
			statement1.setInt(6, diff_neft);
			statement1.setInt(7, diff_NS);
			statement1.setInt(8, diff_NP);
			statement1.setInt(9, diff_NF);
			statement1.setFloat(10, iMPS_Success_per);
			statement1.setFloat(11, iMPS_Pending_per);
			statement1.setFloat(12, iMPS_Failure_per);
			statement1.setFloat(13, nEFT_Success_per);
			statement1.setFloat(14, nEFT_Pending_per);
			statement1.setFloat(15, nEFT_Failure_per);
			statement1.setTimestamp(16, date);
			// statement1.setString(17, "Y");

			System.out.println("Execution done");
			System.out.println(statement1.executeUpdate());
			statement1.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String createMessage(final String timestamp, final int diff_imps, final int diff_IS,
			final int diff_IP, final int diff_IF, final int diff_neft, final int diff_NS, final int diff_NP,
			final int diff_NF, final float iMPS_Success_per, final float iMPS_Pending_per, final float iMPS_Failure_per,
			final float nEFT_Success_per, final float nEFT_Pending_per, final float nEFT_Failure_per) {
		String message = "";
		message = String.valueOf(message)
				+ "<table border=1 style='text-align:center;'><tr><th colspan='9' style='text-align:center;'>"
				+ timestamp + "</td></tr>";
		message = String.valueOf(message)
				+ "<tr><th>IMPS</th><th>Success</th><th>Pending</th><th>Failure</th><th rowspan='3'></th><th>NEFT</th><th>Success</th><th>Pending</th><th>Failure</th></tr>";
		message = String.valueOf(message) + "<tr><td rowspan='2'>" + diff_imps + "</td><td>" + diff_IS + "</td><td>"
				+ diff_IP + "</td><td>" + diff_IF + "</td><td rowspan='2'>" + diff_neft + "</td><td>" + diff_NS
				+ "</td><td>" + diff_NP + "</td><td>" + diff_NF + "</td></tr>";
		message = String.valueOf(message) + "<tr><td>" + iMPS_Success_per + "</td><td>" + iMPS_Pending_per + "</td><td>"
				+ iMPS_Failure_per + "</td><td>" + nEFT_Success_per + "</td><td>" + nEFT_Pending_per + "</td><td>"
				+ nEFT_Failure_per + "</td></tr></table>";
		return message;
	}

	public static void insertDataInstantAirpay(final String timestamp, final int aIRPAY_Count, final int aIRPAY_Pending,
			final int aIRPAY_Failure, final int iNSTANT_Count, final int iNSTANT_Pending, final int iNSTANT_Failure,
			final float pending_PercentageAIRPAY, final float failure_PercentageAIRPAY,
			final float pending_PercentageINSTANT, final float failure_PercentageINSTANT) {
		try {
			System.out.println("InsertData");
			final DateFormat formatter = new SimpleDateFormat("HH:mm");
			final Timestamp date = new Timestamp(new Date().getTime());
			Class.forName("com.mysql.jdbc.Driver");
			final Connection conn = DriverManager.getConnection(Framework.dburl, Framework.dbuser,
					Framework.dbpassword);
			System.out.println("DB Connection Done");
			final String sql1 = "INSERT INTO nsdl_whatsapp (application_name,timestamp,AIRPAY_Count, AIRPAY_Pending, AIRPAY_Failure, INSTANT_Count, INSTANT_Pending, INSTANT_Failure, Pending_PercentageAIRPAY, Failure_PercentageAIRPAY, Pending_PercentageINSTANT, Failure_PercentageINSTANT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			final PreparedStatement statement2 = conn.prepareStatement(
					"INSERT INTO nsdl_whatsapp (application_name,timestamp,AIRPAY_Count, AIRPAY_Pending, AIRPAY_Failure, INSTANT_Count, INSTANT_Pending, INSTANT_Failure, Pending_PercentageAIRPAY, Failure_PercentageAIRPAY, Pending_PercentageINSTANT, Failure_PercentageINSTANT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			statement2.setString(1, Framework.ScriptName);
			statement2.setTimestamp(2, date);
			statement2.setInt(3, aIRPAY_Count);
			statement2.setInt(4, aIRPAY_Pending);
			statement2.setInt(5, aIRPAY_Failure);
			statement2.setInt(6, iNSTANT_Count);
			statement2.setInt(7, iNSTANT_Pending);
			statement2.setInt(8, iNSTANT_Failure);
			statement2.setFloat(9, pending_PercentageAIRPAY);
			statement2.setFloat(10, failure_PercentageAIRPAY);
			statement2.setFloat(11, pending_PercentageINSTANT);
			statement2.setFloat(12, failure_PercentageINSTANT);
			System.out.println(statement2.executeUpdate());
			System.out.println("Execution done");
			statement2.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String createMessage2(final String timestamp, final int aIRPAY_Count, final int aIRPAY_Pending,
			final int aIRPAY_Failure, final int iNSTANT_Count, final int iNSTANT_Pending, final int iNSTANT_Failure,
			final float pending_PercentageAIRPAY, final float failure_PercentageAIRPAY,
			final float pending_PercentageINSTANT, final float failure_PercentageINSTANT) {
		String message = "";
		message = String.valueOf(message)
				+ "<table border=1 style='text-align:center;'><tr><th colspan='9' style='text-align:center;'>"
				+ timestamp + "</td></tr>";
		message = String.valueOf(message)
				+ "<tr><th>AIRPAY</th><th>Pending</th><th>Failure</th><th rowspan='3'></th><th>INSTANT</th><th>Pending</th><th>Failure</th></tr>";
		message = String.valueOf(message) + "<tr><td rowspan='2'>" + aIRPAY_Count + "</td><td>" + aIRPAY_Pending
				+ "</td><td>" + aIRPAY_Failure + "</td><td rowspan='2'>" + iNSTANT_Count + "</td><td>" + iNSTANT_Pending
				+ "</td><td>" + iNSTANT_Failure + "</td></tr>";
		message = String.valueOf(message) + "<tr><td>" + pending_PercentageAIRPAY + "</td><td>"
				+ failure_PercentageAIRPAY + "</td><td>" + pending_PercentageINSTANT + "</td><td>"
				+ failure_PercentageINSTANT + "</td></tr></table>";
		return message;
	}

	public static void sendKeysCopyPaste(WebElement ele, String dataFieldRDS) {

		try {
			((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();", ele);
//			ele.click();
			Thread.sleep(2000);
			Robot rb = new Robot();
			StringSelection s = new StringSelection(dataFieldRDS.trim());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_V);
			Thread.sleep(2000);
			System.out.println("Send successfully == " + dataFieldRDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public static void robotSendkeys(WebElement webElementVal, String dataFieldRDS) {
		webElementVal.click();
		sleep(1000);

		Robot rb2 = null;
		try {
			rb2 = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		System.out.println(dataFieldRDS);
		final char[] ch2 = new char[dataFieldRDS.length()];
		for (int l2 = 0; l2 < dataFieldRDS.length(); ++l2) {
			ch2[l2] = dataFieldRDS.charAt(l2);
		}
		for (int m2 = 0; m2 < ch2.length; ++m2) {
			if (ch2[m2] == '.') {
				System.out.println(ch2[m2]);
				rb2.keyPress(46);
				rb2.keyRelease(46);
			} else if (ch2[m2] == '@') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(50);
				rb2.keyRelease(16);
				rb2.keyRelease(50);
			} else if (ch2[m2] == 'A') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(65);
				rb2.keyRelease(16);
				rb2.keyRelease(65);
			} else if (ch2[m2] == 'B') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(66);
				rb2.keyRelease(16);
				rb2.keyRelease(66);
			} else if (ch2[m2] == 'C') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(67);
				rb2.keyRelease(16);
				rb2.keyRelease(67);
			} else if (ch2[m2] == 'D') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(68);
				rb2.keyRelease(16);
				rb2.keyRelease(68);
			} else if (ch2[m2] == 'E') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(69);
				rb2.keyRelease(16);
				rb2.keyRelease(69);
			} else if (ch2[m2] == 'F') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(70);
				rb2.keyRelease(16);
				rb2.keyRelease(70);
			} else if (ch2[m2] == 'G') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(71);
				rb2.keyRelease(16);
				rb2.keyRelease(71);
			} else if (ch2[m2] == 'H') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(72);
				rb2.keyRelease(16);
				rb2.keyRelease(72);
			} else if (ch2[m2] == 'I') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(73);
				rb2.keyRelease(16);
				rb2.keyRelease(73);
			} else if (ch2[m2] == 'J') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(74);
				rb2.keyRelease(16);
				rb2.keyRelease(74);
			} else if (ch2[m2] == 'K') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(75);
				rb2.keyRelease(16);
				rb2.keyRelease(75);
			} else if (ch2[m2] == 'L') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(76);
				rb2.keyRelease(16);
				rb2.keyRelease(76);
			} else if (ch2[m2] == 'M') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(77);
				rb2.keyRelease(16);
				rb2.keyRelease(77);
			} else if (ch2[m2] == 'N') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(78);
				rb2.keyRelease(16);
				rb2.keyRelease(78);
			} else if (ch2[m2] == 'O') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(79);
				rb2.keyRelease(16);
				rb2.keyRelease(79);
			} else if (ch2[m2] == 'P') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(80);
				rb2.keyRelease(16);
				rb2.keyRelease(80);
			} else if (ch2[m2] == 'Q') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(81);
				rb2.keyRelease(16);
				rb2.keyRelease(81);
			} else if (ch2[m2] == 'R') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(82);
				rb2.keyRelease(16);
				rb2.keyRelease(82);
			} else if (ch2[m2] == 'S') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(83);
				rb2.keyRelease(16);
				rb2.keyRelease(83);
			} else if (ch2[m2] == 'T') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(84);
				rb2.keyRelease(16);
				rb2.keyRelease(84);
			} else if (ch2[m2] == 'U') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(85);
				rb2.keyRelease(16);
				rb2.keyRelease(85);
			} else if (ch2[m2] == 'V') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(86);
				rb2.keyRelease(16);
				rb2.keyRelease(86);
			} else if (ch2[m2] == 'W') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(87);
				rb2.keyRelease(16);
				rb2.keyRelease(87);
			} else if (ch2[m2] == 'X') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(88);
				rb2.keyRelease(16);
				rb2.keyRelease(88);
			} else if (ch2[m2] == 'Y') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(89);
				rb2.keyRelease(16);
				rb2.keyRelease(89);
			} else if (ch2[m2] == 'Z') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(90);
				rb2.keyRelease(16);
				rb2.keyRelease(90);
			} else if (ch2[m2] == 'a') {
				System.out.println(ch2[m2]);
				rb2.keyPress(65);
				rb2.keyRelease(65);
			} else if (ch2[m2] == 'b') {
				System.out.println(ch2[m2]);
				rb2.keyPress(66);
				rb2.keyRelease(66);
			} else if (ch2[m2] == 'c') {
				System.out.println(ch2[m2]);
				rb2.keyPress(67);
				rb2.keyRelease(67);
			} else if (ch2[m2] == 'd') {
				System.out.println(ch2[m2]);
				rb2.keyPress(68);
				rb2.keyRelease(68);
			} else if (ch2[m2] == 'e') {
				System.out.println(ch2[m2]);
				rb2.keyPress(69);
				rb2.keyRelease(69);
			} else if (ch2[m2] == 'f') {
				System.out.println(ch2[m2]);
				rb2.keyPress(70);
				rb2.keyRelease(70);
			} else if (ch2[m2] == 'g') {
				System.out.println(ch2[m2]);
				rb2.keyPress(71);
				rb2.keyRelease(71);
			} else if (ch2[m2] == 'h') {
				System.out.println(ch2[m2]);
				rb2.keyPress(72);
				rb2.keyRelease(72);
			} else if (ch2[m2] == 'i') {
				System.out.println(ch2[m2]);
				rb2.keyPress(73);
				rb2.keyRelease(73);
			} else if (ch2[m2] == 'j') {
				System.out.println(ch2[m2]);
				rb2.keyPress(74);
				rb2.keyRelease(74);
			} else if (ch2[m2] == 'k') {
				System.out.println(ch2[m2]);
				rb2.keyPress(75);
				rb2.keyRelease(75);
			} else if (ch2[m2] == 'l') {
				System.out.println(ch2[m2]);
				rb2.keyPress(76);
				rb2.keyRelease(76);
			} else if (ch2[m2] == 'm') {
				System.out.println(ch2[m2]);
				rb2.keyPress(77);
				rb2.keyRelease(77);
			} else if (ch2[m2] == 'n') {
				System.out.println(ch2[m2]);
				rb2.keyPress(78);
				rb2.keyRelease(78);
			} else if (ch2[m2] == 'o') {
				System.out.println(ch2[m2]);
				rb2.keyPress(79);
				rb2.keyRelease(79);
			} else if (ch2[m2] == 'p') {
				System.out.println(ch2[m2]);
				rb2.keyPress(80);
				rb2.keyRelease(80);
			} else if (ch2[m2] == 'q') {
				System.out.println(ch2[m2]);
				rb2.keyPress(81);
				rb2.keyRelease(81);
			} else if (ch2[m2] == 'r') {
				System.out.println(ch2[m2]);
				rb2.keyPress(82);
				rb2.keyRelease(82);
			} else if (ch2[m2] == 's') {
				System.out.println(ch2[m2]);
				rb2.keyPress(83);
				rb2.keyRelease(83);
			} else if (ch2[m2] == 't') {
				System.out.println(ch2[m2]);
				rb2.keyPress(84);
				rb2.keyRelease(84);
			} else if (ch2[m2] == 'u') {
				System.out.println(ch2[m2]);
				rb2.keyPress(85);
				rb2.keyRelease(85);
			} else if (ch2[m2] == 'v') {
				System.out.println(ch2[m2]);
				rb2.keyPress(86);
				rb2.keyRelease(86);
			} else if (ch2[m2] == 'w') {
				System.out.println(ch2[m2]);
				rb2.keyPress(87);
				rb2.keyRelease(87);
			} else if (ch2[m2] == 'x') {
				System.out.println(ch2[m2]);
				rb2.keyPress(88);
				rb2.keyRelease(88);
			} else if (ch2[m2] == 'y') {
				System.out.println(ch2[m2]);
				rb2.keyPress(89);
				rb2.keyRelease(89);
			} else if (ch2[m2] == 'z') {
				System.out.println(ch2[m2]);
				rb2.keyPress(90);
				rb2.keyRelease(90);
			} else if (ch2[m2] == '1') {
				System.out.println(ch2[m2]);
				rb2.keyPress(49);
				rb2.keyRelease(49);
				sleep(200);
			} else if (ch2[m2] == '2') {
				System.out.println(ch2[m2]);
				rb2.keyPress(50);
				rb2.keyRelease(50);
				sleep(200);
			} else if (ch2[m2] == '3') {
				System.out.println(ch2[m2]);
				rb2.keyPress(51);
				rb2.keyRelease(51);
				sleep(200);
			} else if (ch2[m2] == '4') {
				System.out.println(ch2[m2]);
				rb2.keyPress(52);
				rb2.keyRelease(52);
				sleep(200);
			} else if (ch2[m2] == '5') {
				System.out.println(ch2[m2]);
				rb2.keyPress(53);
				rb2.keyRelease(53);
				sleep(200);
			} else if (ch2[m2] == '6') {
				System.out.println(ch2[m2]);
				rb2.keyPress(54);
				rb2.keyRelease(54);
				sleep(200);
			} else if (ch2[m2] == '7') {
				System.out.println(ch2[m2]);
				rb2.keyPress(55);
				rb2.keyRelease(55);
				sleep(200);
			} else if (ch2[m2] == '8') {
				System.out.println(ch2[m2]);
				rb2.keyPress(56);
				rb2.keyRelease(56);
				sleep(200);
			} else if (ch2[m2] == '9') {
				System.out.println(ch2[m2]);
				rb2.keyPress(57);
				rb2.keyRelease(57);
				sleep(200);
			} else if (ch2[m2] == '0') {
				System.out.println(ch2[m2]);
				rb2.keyPress(48);
				rb2.keyRelease(48);
				sleep(200);
			} else if (ch2[m2] == '/') {
				System.out.println(ch2[m2]);
				rb2.keyPress(47);
				rb2.keyRelease(47);
				sleep(200);
			} else if (ch2[m2] == '.') {
				System.out.println(ch2[m2]);
				rb2.keyPress(46);
				rb2.keyRelease(46);
				sleep(200);
			} else if (ch2[m2] == ':') {
				System.out.println(ch2[m2]);
				rb2.keyPress(16);
				rb2.keyPress(59);
				sleep(200);
				rb2.keyRelease(59);
				rb2.keyRelease(16);
				sleep(200);
			}
		}

	}

	public static void fileUpdateAndUpload(String folderPath, String actFileName) {

		try {

			Date cur = new Date();
			String curDate = new SimpleDateFormat("dd-MM-yyyy").format(cur);
			String mon = new SimpleDateFormat("MMM").format(cur);
			String year = new SimpleDateFormat("yyyy").format(cur);

			File folder = new File(folderPath);

			String fileName = "";
			String filePath = "";

			if (folder.isDirectory()) {
				File[] files = folder.listFiles(); // Get all files in the folder

				if (files != null) {
					for (File file : files) {
						if (file.isFile() && file.getName().startsWith(actFileName)) { // Ensure it's a file and not a
																						// directory
							System.out.println("File found " + file.getName());
							fileName = file.getName().split("_")[0].trim();
							filePath = file.getAbsolutePath();
							break;
						}
					}

				} else {
					System.out.println("The folder is empty or cannot be accessed.");
				}
			} else {
				System.out.println("The provided path is not a directory.");
			}

			FileInputStream io = new FileInputStream(filePath);
			HSSFWorkbook wb = new HSSFWorkbook(io);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(1);
			if (row != null) {

				// Payment Date
				HSSFCell cell = row.getCell(0);
				if (cell != null) {
					cell.setCellValue(curDate);
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Source Account Number
				cell = row.getCell(3);
				if (cell != null) {
					CellStyle textStyle = wb.createCellStyle();
					textStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					cell.setCellStyle(textStyle);
					cell.setCellValue("20240000007371");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Source Narration

				cell = row.getCell(4);
				if (cell != null) {
					cell.setCellValue("Salary " + mon + " " + year);
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination Account Number
				cell = row.getCell(5);
				if (cell != null) {
					CellStyle textStyle = wb.createCellStyle();
					textStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					cell.setCellStyle(textStyle);
					cell.setCellValue("557802010068799");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination Narration
				cell = row.getCell(8);
				if (cell != null) {
					cell.setCellValue("Salary " + mon + " " + year);
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination bank
				cell = row.getCell(9);
				if (cell != null) {
					cell.setCellValue("UNION BANK OF INDIA");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination Bank IFSC Code

				cell = row.getCell(10);
				if (cell != null) {
					cell.setCellValue("UBIN0555789");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Beneficiary Nick Name

				cell = row.getCell(11);
				if (cell != null) {
					cell.setCellValue("BIBHUP");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Beneficiary Name

				cell = row.getCell(12);
				if (cell != null) {
					cell.setCellValue("Bibhu Padhi");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

			} else {
				System.out.println("Row is empty.");
			}

			// remove the 3rd extra row

			row = sheet.getRow(2);
			if (row != null) {
				sheet.removeRow(row);
			}

			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);

			int number = (int) (Math.random() * 1000000);
			String randomNo = String.format("%06d", number);

			File oldFile = new File(filePath);
			String directory = oldFile.getParent();
			String newFilePath = directory + File.separator + fileName + "_"
					+ new SimpleDateFormat("ddMMyy").format(cur).toString() + "_" + randomNo + ".xls";

			File newFile = new File(newFilePath);
			if (oldFile.renameTo(newFile)) {
				System.out.println("File renamed  Successfully ===== " + newFile.getAbsolutePath());

				Thread.sleep(2000);

				Robot rb = new Robot();
				StringSelection s = new StringSelection(newFile.getAbsolutePath());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_L);
				rb.keyRelease(KeyEvent.VK_L);
				rb.keyRelease(KeyEvent.VK_CONTROL);

				Thread.sleep(2000);

				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_CONTROL);

				Thread.sleep(3000);

				rb.keyPress(KeyEvent.VK_ENTER);
				rb.keyRelease(KeyEvent.VK_ENTER);

				Thread.sleep(2000);

			} else {
				System.out.println("File rename failed!!!!!!!!!!!!!!!");
			}

		} catch (Exception e) {
			System.out.println("in Catch Block");
			e.printStackTrace();
		}
	}

	public static void fileUpdateAndUploadVendor(String folderPath, String actFileName) {

		try {

			Date cur = new Date();
			String curDate = new SimpleDateFormat("dd-MM-yyyy").format(cur);
			String mon = new SimpleDateFormat("MMM").format(cur);
			String year = new SimpleDateFormat("yyyy").format(cur);

			File folder = new File(folderPath);

			String fileName = "";
			String filePath = "";

			if (folder.isDirectory()) {
				File[] files = folder.listFiles(); // Get all files in the folder

				if (files != null) {
					for (File file : files) {
						if (file.isFile() && file.getName().startsWith(actFileName)) { // Ensure it's a file and not a
																						// directory
							System.out.println("File found " + file.getName());
							fileName = file.getName().split("_")[0].trim();
							filePath = file.getAbsolutePath();
							break;
						}
					}

				} else {
					System.out.println("The folder is empty or cannot be accessed.");
				}
			} else {
				System.out.println("The provided path is not a directory.");
			}

			FileInputStream io = new FileInputStream(filePath);
			HSSFWorkbook wb = new HSSFWorkbook(io);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(1);
			if (row != null) {

				// Payment Date
				HSSFCell cell = row.getCell(0);
				if (cell != null) {
//					cell.setCellValue(curDate);
					cell.setCellValue("28-01-2025");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Source Account Number
				cell = row.getCell(3);
				if (cell != null) {
					CellStyle textStyle = wb.createCellStyle();
					textStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					cell.setCellStyle(textStyle);
					cell.setCellValue("20240000007371");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Source Narration

				cell = row.getCell(4);
				if (cell != null) {
					cell.setCellValue("Salary " + "jan" + " " + year);
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination Account Number
				cell = row.getCell(5);
				if (cell != null) {
					CellStyle textStyle = wb.createCellStyle();
					textStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					cell.setCellStyle(textStyle);
					cell.setCellValue("50100169856250");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination Narration
				cell = row.getCell(8);
				if (cell != null) {
					cell.setCellValue("Salary " + "jan" + " " + year);
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination bank
				cell = row.getCell(9);
				if (cell != null) {
					cell.setCellValue("HDFC BANK");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Destination Bank IFSC Code

				cell = row.getCell(10);
				if (cell != null) {
					cell.setCellValue("HDFC0000001");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Beneficiary Nick Name

				cell = row.getCell(11);
				if (cell != null) {
					cell.setCellValue("Rishi");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

				// Beneficiary Name

				cell = row.getCell(12);
				if (cell != null) {
					cell.setCellValue("Ritesh");
					System.out.println("Updated cell value: " + cell.toString());
				} else {
					System.out.println("NO data found");
				}

			} else {
				System.out.println("Row is empty.");
			}

			// remove the 3rd extra row

			row = sheet.getRow(2);
			if (row != null) {
				sheet.removeRow(row);
			}

			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);

			int number = (int) (Math.random() * 1000000);
			String randomNo = String.format("%06d", number);

			File oldFile = new File(filePath);
			String directory = oldFile.getParent();
			String newFilePath = directory + File.separator + fileName + "_"
					+ new SimpleDateFormat("ddMMyy").format(cur).toString() + "_" + randomNo + ".xls";

			File newFile = new File(newFilePath);
			if (oldFile.renameTo(newFile)) {
				System.out.println("File renamed  Successfully ===== " + newFile.getAbsolutePath());

				Thread.sleep(2000);

				Robot rb = new Robot();
				StringSelection s = new StringSelection(newFile.getAbsolutePath());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_L);
				rb.keyRelease(KeyEvent.VK_L);
				rb.keyRelease(KeyEvent.VK_CONTROL);

				Thread.sleep(2000);

				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_CONTROL);

				Thread.sleep(3000);

				rb.keyPress(KeyEvent.VK_ENTER);
				rb.keyRelease(KeyEvent.VK_ENTER);

				Thread.sleep(2000);

			} else {
				System.out.println("File rename failed!!!!!!!!!!!!!!!");
			}

		} catch (Exception e) {
			System.out.println("in Catch Block");
			e.printStackTrace();
		}

	}

	public static void checkerImpsRtgs(String arrowXpath, String paymentModeXpath, String objectType) {

		// propertyValue= arrows xpath
		// dataField= 1) paymentModexpath 2) paymentMode
		// objectType= 1)name 2) price

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		List<WebElement> arrows = driver.findElements(By.xpath(arrowXpath.split("#")[0].trim()));
		List<WebElement> arrowsClose;

		System.out.println("All arrows in table ==== " + arrows.size());

		for (int i = 0; i < arrows.size(); i++) {

			wait.until(ExpectedConditions.elementToBeClickable(arrows.get(i))).click();
			CustomFunctions.sleep(1000);

			String paymentMode = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(paymentModeXpath.split("#")[0].trim())))
					.getText();

			System.out.println("Payment type === " + paymentMode);

			if (paymentMode.equalsIgnoreCase(paymentModeXpath.split("#")[1].trim())) {

				// name xpath(bibhu)
				String name = objectType.split("#")[0].trim() + "[" + (i + 1) + "]/div/span";
				// price xpath
				String price = objectType.split("#")[1].trim() + "[" + (i + 1) + "]/div/span/div/span)[2]";
				CustomFunctions.sleep(500);

				String nameValue = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(name))).getText();
				String priceValue = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(price))).getText();

				System.out.println("name  === " + nameValue);
				System.out.println("price Value === " + priceValue);

				String amount = "1";
				if (paymentMode.equalsIgnoreCase("RTGS")) {
					amount = "2,00,000";
				}

				if (nameValue.equalsIgnoreCase("BIBHU") && priceValue.equalsIgnoreCase(amount)) {
					String checkBox = objectType.split("#")[1].trim() + "[" + (i + 1) + "]";
					CustomFunctions.sleep(500);
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkBox))).click();
					} catch (Exception e) {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);
					}

					break;
				}

			} else {
				arrowsClose = driver.findElements(By.xpath(arrowXpath.split("#")[1].trim()));

				wait.until(ExpectedConditions.elementToBeClickable(arrowsClose.get(i))).click();
				CustomFunctions.sleep(1000);

			}

		}
	}

	public static void clickAmountTwoLakh(String propertyValueRDS, String dataFieldRDS) {
		String aPaths = dataFieldRDS.split("#")[0].trim();
		String textPath = dataFieldRDS.split("#")[1].trim();
		List<WebElement> amountPaths = Framework.driver.findElements(By.xpath(aPaths));
		int i = 1;
		for (WebElement ele : amountPaths) {
			String amount = ele.getText().replace(",", "").trim();
//			System.out.println("amount ===== "+amount);
			if (amount.equalsIgnoreCase("200000.00")) {
				textPath = "(" + textPath + ")[" + i + "]";
//				System.out.println("Xpath for Name ===== "+textPath);
				String text = Framework.driver.findElement(By.xpath(textPath)).getText().trim();
				System.out.println("Amount 200000.00 with Name ===== " + text);
				if (text.contains("RTGS")) {
					String clickPath = "(" + propertyValueRDS + ")[" + i + "]";
					System.out.println("Authorized button path ==" + clickPath);
					WebElement ele1 = new WebDriverWait(Framework.driver, Duration.ofSeconds(20))
							.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(clickPath)));
					ele1.click();
					System.out.println("Successfully Click.....");
				}
			}
			i++;
		}
	}

	public static void VerifyHDFCNiftyETF(WebElement ele) {

		String actualdate = ele.getText().trim();
		actualdate = actualdate.replaceAll("\\(", "").replaceAll("\\)", "");
		System.out.println("Actual Date   ---> " + actualdate);
		try {
			Thread.sleep(12000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String expDate = ele.getText().trim();
		expDate = expDate.replaceAll("\\(", "").replaceAll("\\)", "");
		System.out.println("Expected Date ---> " + expDate);

		if (actualdate.equalsIgnoreCase(expDate)) {
			String status = "FAIL(Time Not Changed)";
			ReportError.VerifyHDFCGoldETFDate(Framework.pagename, status, actualdate, expDate);
			System.out.println("Date's are not changing !!!!!!!!!");
		} else {
			System.out.println("---------- Date's are  changing ---------");
		}

	}

	public static void VerifyHDFCGoldETFold(WebElement ele) {

		String actualdate = ele.getText();
		System.out.println("Getting time from Ui == " + actualdate);
		actualdate = actualdate.replaceAll("\\(", "").replaceAll("\\)", "");
		actualdate = actualdate.split(":")[0] + ":" + actualdate.split(":")[1];

		actualdate = actualdate.split(":", 1)[0];
		System.out.println("Actual Date   ---> " + actualdate);

		String expDate = new SimpleDateFormat("dd MMM yyyy, HH:mm").format(new Date());
		System.out.println("Expected Date ---> " + expDate);

		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		if (actualdate.equalsIgnoreCase(expDate)) {
			System.out.println("Date's are  changing.... ");
		} else {
			String status = "FAIL(Time Not Changed)";
			ReportError.VerifyHDFCGoldETFDate(Framework.pagename, status, time, time);
			System.out.println("Date's are not changing.... ");
		}

	}

	public static void getOTPAPI(WebElement ele, String mobileNumber, String sender) {

		String OTP = null;

		for (int i = 0; i < 5 && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		ele.sendKeys(OTP);
	}

	public static void getOTPAPIAxis(WebElement ele, String mobileNumber, String sender) {

		String OTP = null;

		for (int i = 0; i < 5 && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (OTP == null) {

			final JOptionPane optionPane = new JOptionPane("Enter OTP", JOptionPane.QUESTION_MESSAGE,
					JOptionPane.OK_CANCEL_OPTION);

			final JDialog dialog = optionPane.createDialog(null, "OTP Input (Valid for 2 Minutes)");

			// Timer to auto close dialog after 2 minutes (120000 ms)
			new javax.swing.Timer(120000, e -> dialog.dispose()).start();

			dialog.setVisible(true);

			Object selectedValue = optionPane.getInputValue();
			if (selectedValue != null) {
				OTP = selectedValue.toString();
			}
		}

		ele.sendKeys(OTP);
	}

	public static void getOTPAPILongTime(WebElement ele, String mobileNumber, String sender) {

		String OTP = null;

		for (int i = 0; i < 20 && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		ele.sendKeys(OTP);
	}

	public static void verifyUtkarshAdharOtp(String dataField) throws Exception {
		String OTP = null;
		String mobileNumber = LocalTime.now().getMinute() < 30 ? dataField.split("#")[0] : dataField.split("#")[1];
		System.out.println("Now Mobile No :" + mobileNumber);
		String sender = dataField.split("#")[2].trim();
		for (int i = 0; i < 10 && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (OTP != null) {
			System.out.println("OTP is :-" + OTP);
		} else {
			String msg = "OTP is NULL";
			JOptionPane pane = new JOptionPane(
					"<html><body style='font-family: Arial;'>" + "<p style='color: Green; font-weight: bold;'>" + msg
							+ "</p>" + "<p><b>Instance Name:</b> <span style='color: blue;'>" + Framework.dataSheetName
							+ "</body></html>",
					JOptionPane.WARNING_MESSAGE);

			JDialog dialog = pane.createDialog("Otp Null Alert");

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
			Monitoring_FrameWork.SaveResult("false", "true");
			System.exit(1);
		}

	}

	public static void sendCaptch(WebElement ele) {

		ele.sendKeys(Functions.captchText);
		System.out.println("Send Captch Successfully");

	}

	public static void fetchCaptcha(String objectTypeRDS, WebElement ele, String dataFieldRDS) {

		File screenshotAs = ele.getScreenshotAs(OutputType.FILE);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String r = RestAssured.given().multiPart("image", screenshotAs) // 'file' is the form field name, and 'file' is
																		// the File object
				.when().post("http://192.168.7.38:5011/predict").then().extract().response().asString();

//		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		Framework.recentCaptcha = data;
		System.out.println("Text is === " + data);

		WebElement ele2 = new WebDriverWait(Framework.driver, 10)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS)));
		if (objectTypeRDS.equalsIgnoreCase("js")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + data + "'", ele2);
		} else if (objectTypeRDS.equalsIgnoreCase("action")) {
			Actions act = new Actions(driver);
			act.sendKeys(ele2, data).build().perform();
		} else if (objectTypeRDS.equalsIgnoreCase("upperCase")) {
			System.out.println("Text after upperCase === " + data.toUpperCase());
			ele2.sendKeys(data.toUpperCase());
		} else {
			ele2.sendKeys(data);
		}

		System.out.println(" Captch Send Successfully");

	}

	public static void SendCaptchaStatus(String captchStatus) {

		try {
			String response = RestAssured.given().contentType("application/json")
					.body("{\"image_name\":\"" + Framework.recentCaptcha + "\",\n" + "\"script_status\":\""
							+ captchStatus + "\"\n" + "}")
					.when().post("http://192.168.7.38:5011/log-script-status").then().extract().asString();
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("------------ Error in Sending Captch Status to protean Api -------");
		}

	}

	public static void verifyABMFLoginPage(String propertyValueRDS, String dataFieldRDS) {

		System.out.println("In verifyABMFLoginPage function");
		try {
			new WebDriverWait(Framework.driver, 12)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
			System.out.println("-------- In incorrect Login Page!!!!! ------");
			String child = Framework.driver.getWindowHandle();
			Set<String> wlist = Framework.driver.getWindowHandles();
			List<String> wl = new ArrayList<String>(wlist);
			for (String s : wl) {
				if (!s.equalsIgnoreCase(child)) {
					driver.close();
					Thread.sleep(2000);
					driver.switchTo().window(s);
				}
			}
			new WebDriverWait(Framework.driver, 12)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS))).click();
			;
		} catch (Exception e) {
			System.out.println("-------- In Correct Login Page ------");
		}

	}

	public static void readHDFCMailotp(WebElement webElementVal, String dataFieldRDS) {

		System.out.println("------ In readHDFCMailotp ----");
		String host = "imap.gmail.com";
		String userName = dataFieldRDS.split("#")[0];
		String password = dataFieldRDS.split("#")[1];
		String fromMail = dataFieldRDS.split("#")[2];
		String subject = dataFieldRDS.split("#")[3];

//		String userName = "apmosys05@gmail.com";
//		String password = "zzst guga pczf cagv";
//		String fromMail="noreply@mailer-mf.hdfcfund.com";
//		String subject="HDFCMF - OTP for Login";
		String body = readMail(host, userName, password, fromMail, subject);
		String otp = body.split("DETAILS One Time Password")[1].split("The above mentioned OTP")[0].trim();
		System.out.println("Your Otp ====>" + otp);
		webElementVal.sendKeys(otp);
	}

	public static void readAffinityMailotp(WebElement webElementVal, String dataFieldRDS) {

		System.out.println("------ In readAffinityMailotp ----");
		String host = "imap.gmail.com";
		String userName = dataFieldRDS.split("#")[0];
		String password = dataFieldRDS.split("#")[1];
		String fromMail = dataFieldRDS.split("#")[2];
		String subject = dataFieldRDS.split("#")[3];

//		String userName = "apmosys13@gmail.com";
//		String password = "fwns gvty qjbi qsru";
//		String fromMail="NoReply@libertyinsurance.in";
//		String subject="OTP From Liberty Insurance";
		String body = readMail(host, userName, password, fromMail, subject);
		String otp = body.split("OTP Number")[1].split("to log in to your Liberty")[0].trim();
		System.out.println("Your Otp ====>" + otp);
		webElementVal.sendKeys(otp);
	}

	public static void readMstockMailOtp(WebElement webElementVal, String dataFieldRDS) {

		System.out.println("------ In readMstockMailOtp ----");
		String host = "imap.gmail.com";
		String userName = dataFieldRDS.split("#")[0];
		String password = dataFieldRDS.split("#")[1];
		String fromMail = dataFieldRDS.split("#")[2];
		String subject = dataFieldRDS.split("#")[3];

//		String host = "imap.gmail.com";
//		String userName = "apmosys04@gmail.com";
//		String password = "qhck nryw mmoy akcw";
//		String fromMail="info@mstock.com";
//		String subject="Is your OTP for Email Verification";
		String mailSubject = readMailSubject(host, userName, password, fromMail, subject);
		String otp = mailSubject.split("Is your OTP for Email Verification")[0].trim();
		System.out.println("Your Otp ====>" + otp);
		webElementVal.sendKeys(otp);
	}

	public static void readYesBankMailOtp(WebElement webElementVal, String dataFieldRDS) {

		System.out.println("------ In readMstockMailOtp ----");
		String host = "imap.gmail.com";
		String userName = dataFieldRDS.split("#")[0];
		String password = dataFieldRDS.split("#")[1];
		String fromMail = dataFieldRDS.split("#")[2];
		String subject = dataFieldRDS.split("#")[3];

//		String host = "imap.gmail.com";
//		String userName = "apmosys04@gmail.com";
//		String password = "qhck nryw mmoy akcw";
//		String fromMail="customer.service@ysil.in";
//		String subject="One Time Password";
		String body = readMail(host, userName, password, fromMail, subject);
		String otp = body.split("is your OTP and is valid for")[0].trim();
		System.out.println("Your Otp ====>" + otp);
		webElementVal.sendKeys(otp);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
//	public static void main(String[] args) {
////		String host = "imap.gmail.com";
////		String userName = "esafrib@gmail.com";
////		String password = "xwqg eobr osyy huvb";
////		String fromMail="alerts@trans.esafbank.com";
////		String subject="OTP for Secure Transaction";
//
//		String host = "mail.apmosys.com";
//		String userName = "alerts@apmosys.com";
//		String password = "Dolphin$2025";
//		String fromMail = "alerts@axis.bank.com";
//		String subject = "Link for login credentials";
//		String body = readMail(host, userName, password, fromMail, subject);
//		System.out.println("otp =" + body);
//		body = body.split("Greetings from ESAF Small Finance Bank! ")[1].split(" ")[0].trim();
//		System.out.println("otp =" + body);
//
//	}

	public static String readMail(String host, String username, String password, String fromMail, String subject) {

		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imaps.host", host);
		properties.put("mail.imaps.port", "993");
		properties.put("mail.imaps.ssl.enable", "true");

		Session session = Session.getInstance(properties);
		Store store = null;
		Folder inbox = null;
		String body = null;
		System.out.println("start");
		try {
			store = session.getStore("imaps");
			Thread.sleep(3000);
			store.connect(host, username, password);
			System.out.println("Connected......");

			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			SearchTerm senderFilter = new FromStringTerm(fromMail);
			SearchTerm subjectFilter = new SubjectTerm(subject);
			SearchTerm searchCondition = new AndTerm(senderFilter, subjectFilter);

			Message[] messages = inbox.search(searchCondition);

			if (messages.length == 0) {
				System.out.println("No matching emails found.");
			} else {
				Message latestMessage = messages[messages.length - 1];

				System.out.println("===================================");
				System.out.println("From: " + latestMessage.getFrom()[0]);
				System.out.println("Subject: " + latestMessage.getSubject());
				System.out.println("Sent Date: " + latestMessage.getSentDate());
				body = getTextFromMessage(latestMessage);
//				System.out.println("Body: " + body);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inbox != null)
					inbox.close(false);
				if (store != null)
					store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	private static String getTextFromMessage(Message message) throws Exception {
		if (message.isMimeType("text/plain")) {
			System.out.println("-------------Normal -------------");
			return message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			System.out.println("------------------Multipatt ---------------");
			return getTextFromMimeMultipart((MimeMultipart) message.getContent());
		} else if (message.isMimeType("text/html")) {
			System.out.println("------------------Html ---------------");
			String html = (String) message.getContent();
			String data = org.jsoup.Jsoup.parse(html).text();
			return data;
		} else {
			System.out.println("MIME Type: " + message.getContentType());
		}
		return "";
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		String plainText = null;
		String htmlText = null;

		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);

			if (bodyPart.isMimeType("text/plain")) {
				plainText = bodyPart.getContent().toString().trim(); // Store plain text if available
			} else if (bodyPart.isMimeType("text/html")) {
				String htmlContent = bodyPart.getContent().toString();
				htmlText = Jsoup.parse(htmlContent).text().trim(); // Convert HTML to plain text
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				return getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()); // Handle nested multiparts
			}
		}
		return plainText != null ? plainText : (htmlText != null ? htmlText : "");

	}

	public static String readMailSubject(String host, String username, String password, String fromMail,
			String subject) {

		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imaps.host", host);
		properties.put("mail.imaps.port", "993");
		properties.put("mail.imaps.ssl.enable", "true");

		Session session = Session.getInstance(properties);
		Store store = null;
		Folder inbox = null;
		String mailSubject = "";

		try {
			store = session.getStore("imaps");
			store.connect(host, username, password);

			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			SearchTerm senderFilter = new FromStringTerm(fromMail);
			SearchTerm subjectFilter = new SubjectTerm(subject);
			SearchTerm searchCondition = new AndTerm(senderFilter, subjectFilter);

			Message[] messages = inbox.search(searchCondition);

			if (messages.length == 0) {
				System.out.println("No matching emails found.");
			} else {
				Message latestMessage = messages[messages.length - 1];

				System.out.println("===================================");
				System.out.println("From: " + latestMessage.getFrom()[0]);
				System.out.println("Subject: " + latestMessage.getSubject());
				System.out.println("Sent Date: " + latestMessage.getSentDate());
				mailSubject = latestMessage.getSubject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inbox != null)
					inbox.close(false);
				if (store != null)
					store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return mailSubject;
	}

	public static void sendUniqueMobileNo(WebElement webElementVal, String dataFieldRDS) {

		SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss");
		String currentDate = sm.format(new Date());
		String min = currentDate.split(":")[1];
		System.out.println("min is == " + min);
		String mob = "";
		if (Integer.valueOf(min) <= 30) {
			mob = dataFieldRDS.split("#")[0].trim();
		} else {
			mob = dataFieldRDS.split("#")[1].trim();
		}
		webElementVal.sendKeys(mob);
	}

	public static void readLibertyApiotp(WebElement webElementVal, String objectTypeRDS, String dataFieldRDS) {

		SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss");
		String currentDate = sm.format(new Date());
		String min = currentDate.split(":")[1];
		System.out.println("min is == " + min);
		String mob = "";
		if (Integer.valueOf(min) <= 30) {
			mob = dataFieldRDS.split("#")[0].trim();
		} else {
			mob = dataFieldRDS.split("#")[1].trim();
		}

		NewCustomFunctions.getOTPAPI(webElementVal, mob, objectTypeRDS);
	}

	public static void sendRandomNo(WebElement webElementVal, String dataFieldRDS) {

		System.out.println("In sendRandomNo");
		webElementVal.clear();
		Random ran = new Random();
		int no = ran.nextInt(1850) + 100;
		no = no - (no % 100);
		webElementVal.sendKeys(String.valueOf(no));

	}

	// Bhaba , Amar
	public static void verifyCaptchaError(String dataField, String ObjectType) {
		WebDriverWait wait = new WebDriverWait(Framework.driver,
				Duration.ofSeconds(Integer.parseInt(ObjectType.trim())));
		try {
			wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.xpath(dataField)));
			wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(dataField)));
			WebElement WebElementVal = Framework.driver.findElement(By.xpath(dataField));
			System.out.println("Showing Captcha Error !!!- Move To First Line");
			Framework.recordsetRDS.moveFirst();
		} catch (Exception e) {
			System.out.println("No Captcha Error Present");
		}

	}

	private static String getDaySuffix(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public static void VerifyHDFCGoldETF(WebElement ele) {

		SimpleDateFormat sm = new SimpleDateFormat("HH.mm");
		String currentHourMin = sm.format(new Date());
		System.out.println("Current hour is == " + currentHourMin);

		if ((Float.parseFloat(currentHourMin) >= 23.30 && Float.parseFloat(currentHourMin) < 23.59)) {

			String today = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator
					+ "HDFCGold_DateTime_" + today + ".txt";
			File file = new File(path);
			if (!file.exists()) {
				try {
					file.createNewFile();
					String actualdate = ele.getText().trim();
					actualdate = actualdate.replaceAll("\\(", "").replaceAll("\\)", "").trim();
					System.out.println("Actual Date   ---> " + actualdate);

					Date now = new Date();
					String time = new SimpleDateFormat("HH:mm").format(now);
					int day2 = Integer.parseInt(new SimpleDateFormat("dd").format(now));
					String suffix = getDaySuffix(day2);
					String date = new SimpleDateFormat("d'" + suffix + "' MMMM yyyy").format(now);
					String message = "#captured at " + time + " Hrs on " + date;
					actualdate = actualdate + message;

					FileWriter fw = new FileWriter(file);
					fw.write(actualdate);
					fw.flush();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ((Float.parseFloat(currentHourMin) >= 9.30 && Float.parseFloat(currentHourMin) < 9.45)) {

			String previousDate = getPreviousWorkday();
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator
					+ "HDFCGold_DateTime_" + previousDate + ".txt";
			File file = new File(path);
			if (file.exists()) {
				try {

					String expDate = ele.getText().trim();
					expDate = expDate.replaceAll("\\(", "").replaceAll("\\)", "").trim();
					System.out.println("Expected Date ---> " + expDate);

					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					StringBuilder sb = new StringBuilder();
					String line;
					int lineCount = 0;
					while ((line = br.readLine()) != null) {
						lineCount++;
						sb.append(line);
					}
					if (lineCount == 1) {

						String previousData = sb.toString();
						String actualDate = previousData.split("#")[0].trim();
						br.close();
						fr.close();

						Date now = new Date();
						String time = new SimpleDateFormat("HH:mm").format(now);
						int day2 = Integer.parseInt(new SimpleDateFormat("dd").format(now));
						String suffix = getDaySuffix(day2);
						String date = new SimpleDateFormat("d'" + suffix + "' MMMM yyyy").format(now);
						String message = "#captured at " + time + " Hrs on " + date;
						String currentData = expDate + message;

						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("\n" + currentData);
						bw.close();
						fw.close();

						if (actualDate.equalsIgnoreCase(expDate)) {
							String status = "FAIL(Time & Date Not Changed)";
							ReportError.VerifyHDFCGoldETFDate(Framework.pagename, status, previousData, currentData);
							System.out.println("Date's are not changing !!!!!!!!!");

						} else {
							System.out.println("---------- Date's are  changing ---------");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void VerifyHDFCSilverETF(WebElement ele) {

		SimpleDateFormat sm = new SimpleDateFormat("HH.mm");
		String currentHourMin = sm.format(new Date());
		System.out.println("Current hour is == " + currentHourMin);

		if ((Float.parseFloat(currentHourMin) >= 23.30 && Float.parseFloat(currentHourMin) < 23.59)) {

			String today = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator
					+ "HDFCSilver_DateTime_" + today + ".txt";
			File file = new File(path);
			if (!file.exists()) {
				try {
					file.createNewFile();
					String actualdate = ele.getText().trim();
					actualdate = actualdate.replaceAll("\\(", "").replaceAll("\\)", "").trim();
					System.out.println("Actual Date   ---> " + actualdate);

					Date now = new Date();
					String time = new SimpleDateFormat("HH:mm").format(now);
					int day2 = Integer.parseInt(new SimpleDateFormat("dd").format(now));
					String suffix = getDaySuffix(day2);
					String date = new SimpleDateFormat("d'" + suffix + "' MMMM yyyy").format(now);
					String message = "#captured at " + time + " Hrs on " + date;
					actualdate = actualdate + message;

					FileWriter fw = new FileWriter(file);
					fw.write(actualdate);
					fw.flush();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ((Float.parseFloat(currentHourMin) >= 9.30 && Float.parseFloat(currentHourMin) < 9.45)) {

			String previousDate = getPreviousWorkday();
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator
					+ "HDFCSilver_DateTime_" + previousDate + ".txt";
			File file = new File(path);
			if (file.exists()) {
				try {
					String expDate = ele.getText().trim();
					expDate = expDate.replaceAll("\\(", "").replaceAll("\\)", "").trim();
					System.out.println("Expected Date ---> " + expDate);

					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					StringBuilder sb = new StringBuilder();
					String line;
					int lineCount = 0;
					while ((line = br.readLine()) != null) {
						lineCount++;
						sb.append(line);
					}

					if (lineCount == 1) {
						String previousData = sb.toString();
						String actualDate = previousData.split("#")[0].trim();
						br.close();
						fr.close();

						Date now = new Date();
						String time = new SimpleDateFormat("HH:mm").format(now);
						int day2 = Integer.parseInt(new SimpleDateFormat("dd").format(now));
						String suffix = getDaySuffix(day2);
						String date = new SimpleDateFormat("d'" + suffix + "' MMMM yyyy").format(now);
						String message = "#captured at " + time + " Hrs on " + date;
						String currentData = expDate + message;

						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("\n" + currentData);
						bw.close();
						fw.close();

						if (actualDate.equalsIgnoreCase(expDate)) {
							String status = "FAIL(Time & Date Not Changed)";
							ReportError.VerifyHDFCGoldETFDate(Framework.pagename, status, previousData, currentData);
							System.out.println("Date's are not changing !!!!!!!!!");

						} else {
							System.out.println("---------- Date's are  changing ---------");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static String getPreviousWorkday() {
		Calendar cal = Calendar.getInstance();
		String dayName = new SimpleDateFormat("EEEE").format(cal.getTime());

		if (dayName.equalsIgnoreCase("Monday")) {
			cal.add(Calendar.DATE, -3); // Go to Friday
		} else {
			cal.add(Calendar.DATE, -1); // Go to previous day
		}

		String previousDay = new SimpleDateFormat("dd_MM_yyyy").format(cal.getTime()).trim();
		System.out.println("Previous Day is === " + previousDay);
		return previousDay;
	}

	public static void axis_BROWSEURL(String objectTypeRDS, String dataFieldRDS, String controlRDS) throws Exception {
		// TODO Auto-generated method stub
		int time = Integer.parseInt(objectTypeRDS);
		Framework.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));

		if (controlRDS.equalsIgnoreCase("T")) {
			Framework.tStartTime = Monitoring_FrameWork.StartTime();
		}
		try {
			Functions.url = dataFieldRDS;
			Framework.driver.get(dataFieldRDS);
		} catch (Exception ex10) {
			ex10.printStackTrace();
			Functions.screenShotPath = Monitoring_FrameWork.takeScreenshot();
			ReportError.axisUrlAlert("apmosys.icewarpcloud.in", "587", Framework.mailFrom, Framework.password,
					Framework.mailTo, Framework.mailCc, subject, time);

			ex10.printStackTrace();
		}

	}

	public static void js_script(String propertyValueRDS, String dataFieldRDS) throws Exception {
		// TODO Auto-generated method stub
		final StringSelection s33 = new StringSelection(
				"document.getElementById('" + propertyValueRDS + "').value='" + dataFieldRDS + "';");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s33, null);
		final Robot rb3 = new Robot();
		rb3.keyPress(17);
		Thread.sleep(100L);
		rb3.keyPress(16);
		Thread.sleep(100L);
		rb3.keyPress(74);
		Thread.sleep(100L);
		rb3.keyRelease(74);
		Thread.sleep(100L);
		rb3.keyRelease(16);
		Thread.sleep(100L);
		rb3.keyRelease(17);
		Thread.sleep(2000L);
		rb3.keyPress(17);
		Thread.sleep(100L);
		rb3.keyPress(86);
		Thread.sleep(100L);
		rb3.keyRelease(86);
		Thread.sleep(100L);
		rb3.keyRelease(17);
		Thread.sleep(3000L);
		rb3.keyPress(10);
		Thread.sleep(100L);
		rb3.keyRelease(10);
		Thread.sleep(5000L);
		rb3.keyPress(17);
		Thread.sleep(100L);
		rb3.keyPress(16);
		Thread.sleep(100L);
		rb3.keyPress(74);
		Thread.sleep(100L);
		rb3.keyRelease(74);
		Thread.sleep(100L);
		rb3.keyRelease(16);
		Thread.sleep(100L);
		rb3.keyRelease(17);

	}

	public static void getOTPAPIloopTime(WebElement ele, String objectTypeRDS, String dataFieldRDS, String time,
			String propertyValue) {

		String OTP = null;
		String mobileNumber = dataFieldRDS.split("#")[0];
		String sender = dataFieldRDS.split("#")[1];

		for (int i = 0; i < Integer.valueOf(time) && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		if (!propertyValue.equalsIgnoreCase("single")) {

			if (objectTypeRDS.equalsIgnoreCase("JS")) {
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + OTP + "';",
						new Object[] { ele });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(ele, new CharSequence[] { dataFieldRDS }).build().perform();
			} else if (objectTypeRDS.equalsIgnoreCase("char")) {
				ele.sendKeys(new CharSequence[] { OTP });
			} else if (objectTypeRDS.equalsIgnoreCase("usingXpath")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValue)))
						.sendKeys(new CharSequence[] { OTP });
			} else if (objectTypeRDS.equalsIgnoreCase("usingFlutter")) {
				Actions ac = new Actions(driver);
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValue)));
				Point location = ele.getLocation();
//				ac.moveByOffset(0,0).perform();
				// Move to location using offset (relative to page)
				ac.moveByOffset(location.getX(), location.getY()).click().sendKeys(OTP).perform();

			} else if (objectTypeRDS.equalsIgnoreCase("robot")) {
				CustomFunctions.robot_otp(OTP);
			} else {
				ele.sendKeys(OTP);
			}
		} else {
			Functions.otp1 = OTP.charAt(0);
			Functions.otp2 = OTP.charAt(1);
			Functions.otp3 = OTP.charAt(2);
			Functions.otp4 = OTP.charAt(3);

			if (OTP.length() > 4) {
				Functions.otp5 = OTP.charAt(4);
				Functions.otp6 = OTP.charAt(5);
			}
		}
	}

//	
	public static String readMobileOtp(WebElement ele, String objectTypeRDS, String dataFieldRDS, String time,
			String propertyValue) {

		String OTP = null;
		String mobileNumber = dataFieldRDS.split("#")[0];
		String sender = dataFieldRDS.split("#")[1];

		for (int i = 0; i < Integer.valueOf(time) && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return OTP;

	}

	public static String readMobileOtpTillNull(WebElement ele, String objectTypeRDS, String dataFieldRDS, String time,
			String propertyValue, String OTP) {

		String mobileNumber = dataFieldRDS.split("#")[0];
		String sender = dataFieldRDS.split("#")[1];
		String latestOtp = "";

		for (int i = 0; i < Integer.valueOf(time) && OTP != null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			if (OTP != null)
				latestOtp = OTP;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return latestOtp;

	}

	public static void readOtpApiLatest(WebElement ele, String objectTypeRDS, String dataFieldRDS, String time,
			String propertyValue) {
		String OTP = null;
		OTP = readMobileOtp(ele, objectTypeRDS, dataFieldRDS, time, propertyValue);
		OTP = readMobileOtpTillNull(ele, objectTypeRDS, dataFieldRDS, time, propertyValue, OTP);

		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		if (!propertyValue.equalsIgnoreCase("single")) {

			if (objectTypeRDS.equalsIgnoreCase("JS")) {
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + OTP + "';",
						new Object[] { ele });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(ele, new CharSequence[] { dataFieldRDS }).build().perform();
			} else if (objectTypeRDS.equalsIgnoreCase("char")) {
				ele.sendKeys(new CharSequence[] { OTP });
			} else if (objectTypeRDS.equalsIgnoreCase("usingXpath")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValue)))
						.sendKeys(new CharSequence[] { OTP });
			} else {
				ele.sendKeys(OTP);
			}
		} else {
			Functions.otp1 = OTP.charAt(0);
			Functions.otp2 = OTP.charAt(1);
			Functions.otp3 = OTP.charAt(2);
			Functions.otp4 = OTP.charAt(3);

			if (OTP.length() > 4) {
				Functions.otp5 = OTP.charAt(4);
				Functions.otp6 = OTP.charAt(5);
			}
		}
	}

	public static void verifypage(String propertyValueRDS) {
		try {
			new WebDriverWait(Framework.driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));
		} catch (Exception e) {
			System.out.println("--------------------Page Not Appear !!!!--------------------");
			Framework.driver.navigate().refresh();
		}

	}

	public static void verifySessionExpire() {
		try {
			String previousPageName = Framework.pagename;
			while (Framework.pagename.equalsIgnoreCase(previousPageName)) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String renameFile(String propertyName, String dataField) {

		String currentDate = new SimpleDateFormat("dd/MM/yyyy HHmmss").format(new Date());
		String time = currentDate.split(" ")[1];
		currentDate = currentDate.split(" ")[0];
		File renameFile = null;

		try {
			// Step 1: Select the file using its name
			File folder = new File(dataField);
			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().startsWith(propertyName)) {

						FileInputStream foi = new FileInputStream(file);

						XSSFWorkbook workbook = new XSSFWorkbook(foi);
						XSSFSheet sheet = workbook.getSheetAt(0);

						if (file.getName().startsWith("across all banks")) {
							System.out.println("Write in across all banks");
							XSSFRow row = sheet.getRow(1);
							XSSFCell cell1 = row.getCell(6);
							cell1.setCellValue(currentDate);

							XSSFCell cell2 = row.getCell(8);
							cell2.setCellValue("Customer" + time);

						} else if (file.getName().startsWith("Within Axis Bank")) {
							System.out.println("Write in Withing Axis Bank");
							XSSFRow row = sheet.getRow(1);
							XSSFCell cell1 = row.getCell(5);
							cell1.setCellValue(currentDate);

							XSSFCell cell2 = row.getCell(6);
							cell2.setCellValue("Customer" + time);

						} else if (file.getName().startsWith("Salary Payments")) {
							System.out.println("Write in salary payments");
							XSSFRow row = sheet.getRow(1);
							XSSFCell cell1 = row.getCell(4);
							cell1.setCellValue(currentDate);

							XSSFCell cell2 = row.getCell(5);
							cell2.setCellValue("Customer" + time);
						} else {
							System.out.println("Excel file not found.......");
						}

						try (FileOutputStream fileOut = new FileOutputStream(file)) {
							workbook.write(fileOut);
							System.out.println("Data written in " + file.getName() + " successfully.");
						} catch (Exception e) {
							System.err.println(
									"Error while writting in " + file.getName() + " Excel file: " + e.getMessage());
						} finally {
							try {
								foi.close();
								workbook.close();
//								fileOut.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						try {
							// Step 2: Rename the file
							renameFile = new File(file.getParent(), propertyName + time + ".xlsx");
							file.renameTo(renameFile);
							System.out.println("File renamed successfully");

						} catch (Exception e) {
							System.out.println("Failed to rename the file.");
							e.printStackTrace();
						}

						break;
					}
				}
			} else {
				System.out.println("No files found in the specified folder.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return renameFile.getAbsolutePath();

	}

	public static void AxisClickLastCheckBox(String dataFieldRDS) {
		System.out.println("In AxisClickLastCheckBox");
		String rowXpath = dataFieldRDS.split("#")[0].trim();
		String checkBoxXpath = dataFieldRDS.split("#")[1].trim();
		List<WebElement> elements = Framework.driver.findElements(By.xpath(rowXpath));
		String xpathChkBox = rowXpath + "[" + elements.size() + "]/td/span";
		Framework.driver.findElement(By.xpath(xpathChkBox)).click();
	}

	public static String generateRandomString(String length, String noOfString, String stringFormat) {
		String randomString = "";
		if (stringFormat.equals("AA")) {
			char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
					'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
					'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
			Random rnd = new Random();
			int charsLength = chars.length;
			for (int j = 1; j <= Integer.parseInt(noOfString); ++j) {
				StringBuilder password = new StringBuilder();
				for (int i = 0; i < Integer.parseInt(length); ++i) {
					int index = rnd.nextInt(charsLength - i - 1);
					char a = chars[i + index];
					chars[i + index] = chars[i];
					password.append(chars[i] = a);
				}
				randomString = password.toString();
				System.out.println(randomString);
			}
		}
		return randomString;// GOWVJUWHDK like
	}

	public static void verifyDownloadedFile(String objectTypeRDS, String propertyValueRDS, String dataFieldRDS)
			throws Exception {

		// downloadpath,FileName#Passsword#ValidationData inner data
		File folder = new File(propertyValueRDS.trim());

		String actFileName = dataFieldRDS.split("#")[0];
		String password = dataFieldRDS.split("#")[1];
		String innerData = dataFieldRDS.split("#")[2];

		if (folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null && files.length > 0) {
				File latestFile = Arrays.stream(files).filter(File::isFile)
						.max(Comparator.comparingLong(File::lastModified)).orElse(null);

				if (latestFile == null) {
					System.out.println("No files found in the folder.");
					return;
				}

				System.out.println("Latest File found: " + latestFile.getName());

				if (latestFile.getName().startsWith(actFileName)) {
					System.out.println("<------------ File Matched ------------>");
					try (PDDocument document = PDDocument.load(latestFile, password)) {
						PDFTextStripper pdfStripper = new PDFTextStripper();
						String text = pdfStripper.getText(document);
						if (objectTypeRDS.equalsIgnoreCase("Showdata")) {
							System.out.println("PDF Content:\n" + text);
						}
						if (text.contains(innerData)) {
							System.out.println("✅ Successfully Verified that file downloaded and content is correct.");
						} else {
							System.out.println("❌ Content does not match expected data.");
						}
					}
				} else {
					System.out.println("❌ Latest file name does not start with expected: " + actFileName);
				}
			} else {
				System.out.println("The folder is empty or cannot be accessed.");
			}
		} else {
			System.out.println("Provided path is not a directory.");
		}
	}

	public static void checkPaymentMode(String propertyValueRDS, String dataFieldRDS) throws Exception {

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(20));

		List<WebElement> list = Framework.driver.findElements(By.xpath(propertyValueRDS));
		System.out.println("Total CRN Size is == " + list.size());

		for (int i = 0; i < list.size(); i++) {
			Thread.sleep(2000);
			list.get(i).click();
			Thread.sleep(2000);
			int j = i + 2;

			String paymentMode = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[0]))).getText();
			System.out.println("Payment Mode is == " + paymentMode);

			if (paymentMode.equalsIgnoreCase(dataFieldRDS.split("#")[1].trim())) {

				String checkBox = "(" + dataFieldRDS.split("#")[2] + ")[" + j + "]";
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkBox))).click();
				break;
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS.split("#")[3]))).click();

			Thread.sleep(3000);
			list = Framework.driver.findElements(By.xpath(propertyValueRDS));

		}
	}

	public static void sendNickname(WebElement webElementVal, String dataFieldRDS) {

		Random random = new Random();
		int min = 10000;
		int max = 99999;
		int randomFiveDigitNumber = random.nextInt((max - min) + 1) + min;
		dataFieldRDS = dataFieldRDS + String.valueOf(randomFiveDigitNumber);
		System.out.println("your nick name === " + dataFieldRDS);
		webElementVal.sendKeys(dataFieldRDS);
	}

	// Amar
	public static void robotClickFileUpload(String propertyValueRDS) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		System.out.println("file path ==== " + propertyValueRDS);
		StringSelection s = new StringSelection(propertyValueRDS);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		Thread.sleep(3000);
		int centerX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
		int centerY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
		Thread.sleep(1000);
		robot.mouseMove(centerX, centerY);
		robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_L);
		robot.keyRelease(KeyEvent.VK_L);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(500);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(500);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(3000);
	}

	// Amar
	public static void publicFetchCaptcha(String objectTypeRDS, WebElement ele, String dataFieldRDS) {

		System.out.println("------- Inside publicFetchCaptch -------");
		final int year = Calendar.getInstance().get(1);
		final String MonthName = new SimpleDateFormat("MMMM").format(new Date());
		final int monthday = Calendar.getInstance().get(5);

		String path = System.getProperty("user.dir") + File.separator + "CaptchImages" + File.separator + year
				+ File.separator + MonthName + File.separator + monthday;
		File folder = new File(path);
		folder.mkdirs();

		String time = new SimpleDateFormat("HH_mm_ss").format(new Date());
		path = path + File.separator + "captchImg_" + time + ".png";
		File destination = new File(path);
		File screenshotAs = ele.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(screenshotAs, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String r = RestAssured.given().multiPart("image", screenshotAs).when()
				.post("https://proteansaasuat.apmosys.com/captcha/predict").then().extract().response().asString();

//		System.out.println("Response Body: " + r);
		JsonPath js = new JsonPath(r);
		String data = js.getString("text");
		System.out.println("Text is === " + data);

		WebElement ele2 = new WebDriverWait(Framework.driver, 10)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dataFieldRDS)));
		if (objectTypeRDS.equalsIgnoreCase("js")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + data + "'", ele2);
		} else if (objectTypeRDS.equalsIgnoreCase("action")) {
			Actions act = new Actions(driver);
			act.sendKeys(ele2, data).build().perform();
		} else {
			ele2.sendKeys(data);
		}

		System.out.println(" Captch Send Successfully");

	}

	// Amar
	public static void rb_TRAN1() throws AWTException {
		final Robot rb2 = new Robot();
		rb2.keyPress(16);
		rb2.keyPress(77);
		rb2.keyRelease(77);
		rb2.keyRelease(16);
		rb2.keyPress(65);
		rb2.keyRelease(65);
		rb2.keyPress(82);
		rb2.keyRelease(82);
		rb2.keyPress(16);
		rb2.keyPress(50);
		rb2.keyRelease(50);
		rb2.keyRelease(16);
		rb2.keyPress(50);
		rb2.keyRelease(50);
		rb2.keyPress(48);
		rb2.keyRelease(48);
		rb2.keyPress(50);
		rb2.keyRelease(50);
		rb2.keyPress(51);
		rb2.keyRelease(51);
	}

	// Amar
	public static void rb_TRAN2() throws AWTException {
		final Robot rb2 = new Robot();
		rb2.keyPress(KeyEvent.VK_SHIFT);
		rb2.keyPress(KeyEvent.VK_J);
		rb2.keyRelease(KeyEvent.VK_J);
		rb2.keyRelease(KeyEvent.VK_SHIFT);

		rb2.keyPress(KeyEvent.VK_A);
		rb2.keyRelease(KeyEvent.VK_A);

		rb2.keyPress(KeyEvent.VK_N);
		rb2.keyRelease(KeyEvent.VK_N);

		rb2.keyPress(KeyEvent.VK_SHIFT);
		rb2.keyPress(KeyEvent.VK_2);
		rb2.keyRelease(KeyEvent.VK_2);
		rb2.keyRelease(KeyEvent.VK_SHIFT);

		rb2.keyPress(KeyEvent.VK_2);
		rb2.keyRelease(KeyEvent.VK_2);
		rb2.keyPress(KeyEvent.VK_0);
		rb2.keyRelease(KeyEvent.VK_0);
		rb2.keyPress(KeyEvent.VK_2);
		rb2.keyRelease(KeyEvent.VK_2);
		rb2.keyPress(KeyEvent.VK_4);
		rb2.keyRelease(KeyEvent.VK_4);

	}

	// Amar
	public static void setFutureDate(WebElement ele, String datafield, String objectType) {
		int futureDtVal = Integer.parseInt(datafield.split("#")[0]);
		String dateFormat = datafield.split("#")[1];
		String fututredate = LocalDate.now().plusDays(futureDtVal).format(DateTimeFormatter.ofPattern(dateFormat));
		System.out.println("FututreDate:--- " + fututredate);

		if (objectType.equalsIgnoreCase("JS")) {
			((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + fututredate + "';",
					new Object[] { ele });
		} else if (objectType.equalsIgnoreCase("action")) {
			Actions act = new Actions(Framework.driver);
			act.sendKeys(ele, new CharSequence[] { fututredate }).build().perform();
		} else if (objectType.equalsIgnoreCase("char")) {
			ele.sendKeys(new CharSequence[] { fututredate });
		} else {
			ele.sendKeys(fututredate);
		}
	}

	public static void verifyAxisSubmitPage(String propertyValueRDS) throws IOException, FilloException {

		String todayDate = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
		String path = System.getProperty("user.dir") + File.separator + "data";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(path + File.separator + "AxisVerify.txt");

		if (!file.exists()) {
			file.createNewFile();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(todayDate);
			}
			System.out.println("--------------------------  Execution for the Submit Page -------------------------- ");
		} else {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String fileDate = br.readLine();
				if (todayDate.equalsIgnoreCase(fileDate)) {
					System.out.println(
							"-------------------------- Already Submitted For Today, Once per Day!! --------------------------");
					while (true) {
						if (!Framework.pagename.equals(propertyValueRDS)) {
							Framework.recordsetRDS.moveNext();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
						} else {
							Framework.recordsetRDS.movePrevious();
							Framework.pagename = Framework.recordsetRDS.getField("PageName");
							break;
						}
					}
				} else {
					System.out.println(
							"--------------------------  Execution for the Submit Page -------------------------- ");
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
						bw.write(todayDate);
					}
				}
			}
		}
	}

	public static void readBajajOtpApi(WebElement ele, String objectTypeRDS, String dataFieldRDS, String time,
			String propertyValue) {

		String OTP = null;
		int curNo = (LocalDateTime.now().getMinute() / 15);
		String mobileNumber = dataFieldRDS.split("#")[curNo];
		String sender = dataFieldRDS.split("#")[4];

		for (int i = 0; i < Integer.valueOf(time) && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		if (!propertyValue.equalsIgnoreCase("single")) {

			if (objectTypeRDS.equalsIgnoreCase("JS")) {
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + OTP + "';",
						new Object[] { ele });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(ele, new CharSequence[] { dataFieldRDS }).build().perform();
			} else if (objectTypeRDS.equalsIgnoreCase("char")) {
				ele.sendKeys(new CharSequence[] { OTP });
			} else {
				ele.sendKeys(OTP);
			}
		} else {
			Functions.otp1 = OTP.charAt(0);
			Functions.otp2 = OTP.charAt(1);
			Functions.otp3 = OTP.charAt(2);
			Functions.otp4 = OTP.charAt(3);

			if (OTP.length() > 4) {
				Functions.otp5 = OTP.charAt(4);
				Functions.otp6 = OTP.charAt(5);
			}
		}
	}

	public static void sendbajajMobileNo(String objectTypeRDS, WebElement ele, String dataFieldRDS) {

		int curNo = (LocalDateTime.now().getMinute() / 15);
		String mobNo = dataFieldRDS.split("#")[curNo];

		if (objectTypeRDS.equalsIgnoreCase("JS")) {
			((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + mobNo + "';",
					new Object[] { ele });
		} else if (objectTypeRDS.equalsIgnoreCase("action")) {
			Actions act = new Actions(Framework.driver);
			act.sendKeys(ele, new CharSequence[] { dataFieldRDS }).build().perform();
		} else if (objectTypeRDS.equalsIgnoreCase("char")) {
			ele.sendKeys(new CharSequence[] { mobNo });
		} else {
			ele.sendKeys(mobNo);
		}
	}

	public static void openBandhanurl(WebElement ele) {
		String link = ele.getText();
		System.out.println("Your Url         ---------------> " + link);
//		driver.switchTo().newWindow(WindowType.TAB);
		driver.get(link);

	}

	public static void verifyReadYesBankOtp(WebElement ele, String objectTypeRDS, String dataFieldRDS, String time,
			String propertyValue) throws Exception {

		String OTP = null;
		String mobileNumber = dataFieldRDS.split("#")[0];
		String sender = dataFieldRDS.split("#")[1];

		for (int i = 0; i < Integer.valueOf(time) && OTP == null; i++) {

			String body = "{\n" + " \"number\": \"" + mobileNumber + "\"," + " \"sender\": \"" + sender + "\"" + "}";

			String r = RestAssured.given().contentType(ContentType.JSON).when().body(body)
					.post("https://mservice.apmosys.com/api/o1/otps").then().extract().response().asString();

			System.out.println("Response Body: " + r);
			JsonPath js = new JsonPath(r);

			OTP = js.getString("[0].otp");
			System.out.println("OTP is " + OTP);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		if (OTP == null || OTP.isEmpty()) {
			Monitoring_FrameWork.SaveResult("OTP FOUND", "OTP NOT FOUND");
		} else {
			Monitoring_FrameWork.SaveResult("OTP FOUND", "OTP FOUND");
		}
		if (!propertyValue.equalsIgnoreCase("single")) {

			if (objectTypeRDS.equalsIgnoreCase("JS")) {
				((JavascriptExecutor) Framework.driver).executeScript("arguments[0].value='" + OTP + "';",
						new Object[] { ele });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(ele, new CharSequence[] { dataFieldRDS }).build().perform();
			} else if (objectTypeRDS.equalsIgnoreCase("char")) {
				ele.sendKeys(new CharSequence[] { OTP });
			} else if (objectTypeRDS.equalsIgnoreCase("usingXpath")) {
				WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValue)))
						.sendKeys(new CharSequence[] { OTP });
			} else if (objectTypeRDS.equalsIgnoreCase("sikuli")) {
				try {
					Screen s34 = new Screen();
					s34.wait((Object) propertyValue, 15.0);
					s34.exists((Object) propertyValue).click();
					s34.type(OTP);
				} catch (FindFailed e) {
					e.printStackTrace();
				}
			}
//			else if (objectTypeRDS.equalsIgnoreCase("sikuli")) {
//				Screen s34 = new Screen();
//				s34.type(OTP);
//			}
			else {
				ele.sendKeys(OTP);
			}
		} else {
			Functions.otp1 = OTP.charAt(0);
			Functions.otp2 = OTP.charAt(1);
			Functions.otp3 = OTP.charAt(2);
			Functions.otp4 = OTP.charAt(3);

			if (OTP.length() > 4) {
				Functions.otp5 = OTP.charAt(4);
				Functions.otp6 = OTP.charAt(5);
			}
		}
	}

	public static void verifyUtkarshRefNo(String dataField) {

		String refNo = Functions.globleValues.get(dataField);
		String xpath = "//span[text()='" + refNo + "']/following::span[3]";
		String statusText;
		try {
			WebElement statusEle = Functions.CheckObjectVisibility("xpath", xpath, "", Framework.pagename);
			((JavascriptExecutor) Framework.driver)
					.executeScript("arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", statusEle);
			Thread.sleep(3000L);
			statusText = statusEle.getText();
			Monitoring_FrameWork.SaveResult(statusText, "Accepted");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void verifySbiDownloadConfirmationPage(WebElement ele) {

		try {
			String path = System.getProperty("user.dir") + "/data";
			if (!new File(path).exists()) {
				System.out.println("Please create data folder first !!!!!!!");
				return;
			}
			path = path + "/SbiDownloadConformationPage.txt";
			File file = new File(path);
			if (!new File(path).exists()) {
				file.createNewFile();
				System.out.println("SbiDownloadConformationPage.txt file created successfully");
			}

			int currentHour = LocalDateTime.now().getHour();
			if (currentHour > 9 && currentHour <= 14) {

				System.out.println(
						"Current hour is working Hour for SbiDownloadConformationPage , Hour : " + currentHour);

				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String runHour = "";
				try {
					runHour = br.readLine();
				} catch (IOException readEx) {
					runHour = "";
				}
				if (runHour == null) {
					runHour = "";
				}
				br.close();
				fr.close();

				if (!runHour.equalsIgnoreCase(String.valueOf(currentHour))) {

					ele.click();
					FileWriter fw = new FileWriter(file);
					BufferedWriter bf = new BufferedWriter(fw);
					bf.write(String.valueOf(currentHour));
					bf.close();
					fw.close();
					System.out.println("DownLoad Button click successfully");
				} else {
					System.out.println("Already SbiDownloadConformationPage Page verified");
					skipCurrentPage();
				}

			} else {
				System.out.println("Current hour is not  work for SbiDownloadConformationPage , Hour : " + currentHour);
				skipCurrentPage();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void skipCurrentPage() throws FilloException {

		String prevPageName = Framework.pagename;
		while (Framework.pagename.equals(prevPageName)) {
			Framework.recordsetRDS.moveNext();
			Framework.pagename = Framework.recordsetRDS.getField("PageName");
		}
		Framework.recordsetRDS.movePrevious();

	}

	public static void VerifyskipCurrentPage(String propertyValue) throws FilloException {

		try {
			WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(18));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValue)));
			System.out.println("Current page Appear , Continue the execution ");
		} catch (Exception e) {
			System.out.println("Current Page Not appear , so go the Next Page");
			skipCurrentPage();

		}

	}

	public static void verifyBandhanPartnerSkipPages() throws Exception {
		try {
			int hour = LocalDateTime.now().getHour();
			if (hour < 8 || hour > 20) {
				System.out.println("Skip the remaining pages current hour is not working hour");
				Framework.recordsetRDS.moveLast();
				return;
			}

			String filePath = System.getProperty("user.dir") + File.separator + "data/" + "BandhanPageExeTime.txt";
			LocalDateTime curTime = LocalDateTime.now();
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				curTime.minusHours(2).toString();
				bw.write(curTime.minusHours(2).toString());
				bw.close();
				fw.close();
			}
			LocalDateTime oldTime;

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String prevTime = br.readLine();
				oldTime = LocalDateTime.parse(prevTime);
			}

			long diffInMinutes = Duration.between(oldTime, curTime).toMinutes();
			System.out.println("Previous Execution Time: " + oldTime);
			System.out.println("Difference between times: " + diffInMinutes + " minutes");

			if (diffInMinutes >= 118) {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
					bw.write(curTime.toString());
				}
				System.out.println("All pages to execute");
			} else {
				System.out.println("Skip the remaining pages");
				Framework.recordsetRDS.moveLast();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void verifySharekhanSkipPages() throws Exception {
		try {
			LocalTime localTime = LocalTime.now();
			if (localTime.isAfter(LocalTime.of(23, 30)) || localTime.isBefore(LocalTime.of(6, 30))) {
				System.out.println("Skipping the current page: current hour is not a working hour for this page.");
				skipCurrentPage();
			} else {
				System.out.println("Now time to Run all Pages");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static void yesBankMultipleVisibility(String dataFieldRDS, String objectTypeRDS, String controlRDS)
			throws Exception {
		String sectionName = "";
		String actualResult = "";
		try {
			String paths[] = dataFieldRDS.split("#");
			String allSection[] = objectTypeRDS.split("#");

			WebDriverWait wait = new WebDriverWait(Framework.driver, Framework.defaultwaittime);
			for (int i = 0; i < paths.length; i++) {
				sectionName = allSection[i];
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(paths[i])));
			}
			System.out.println("All checkVisibility Passes");
			actualResult = "True";
			if (controlRDS.equalsIgnoreCase("V")) {
				Monitoring_FrameWork.SaveResult(actualResult, "True");
			}
		} catch (Exception e3) {
			Framework.errorsatus = "1";
			Framework.errorpagename = Framework.pagename;
			e3.printStackTrace();
			actualResult = "False";
			Framework.pagename = Framework.pagename + "_" + sectionName;
			Monitoring_FrameWork.SaveResult(actualResult, "True");
		}

	}

	public static void verifyEsafUrl(String propertyValue, String datafield, String controlRDS, String objectType)
			throws Exception {

		String actualResult = "";
		try {
			WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
			WebElement webelement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValue)));
			actualResult = String.valueOf(webelement.isDisplayed());
			System.out.println("Actual Result is == " + actualResult);
		} catch (Exception e2) {
			actualResult = "false";
			Framework.errorsatus = "1";
			Framework.errorpagename = Framework.pagename;
			e2.printStackTrace();
		}
		if (controlRDS.equalsIgnoreCase("V")) {
			Framework.exitStatus = "N";
			Monitoring_FrameWork.SaveResult(actualResult, "true");
		}
		if (actualResult.equalsIgnoreCase("true")) {
			System.out.println("Visibility Pass...");
			return;
		} else {
			System.out.println("Visibility Not Pass...");
			Framework.driver.get(datafield);

			while (!Framework.pagename.equalsIgnoreCase(objectType)) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
			System.out.println("Current Page==> " + Framework.pagename);
		}

	}
	// Yes Bank Value check

	public static void sendMail(String sub, String from, String pass, String content, String toEmails, String ccEmails,
			String attchament) {

		if (Framework.mailAlert.equalsIgnoreCase("N")) {
			return;
		}

		String host = "mail.apmosys.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails));
			message.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(ccEmails));
			message.setSubject(sub);

			String signature = "<br/><br/><br/>Thanks and Regards,<br/> " + "ApMoSys | Performance Monitoring Team<br/>"
					+ "Contact: Desk No.022 41222250 | Mobile No.9167640945 | 8591462632<br/>"
					+ "Email : alerts@apmosys.com<br/>" + "Website: www.apmosys.com";

			content = content + signature;
			Multipart multipart = (Multipart) new MimeMultipart();

			// for Message Body
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/html");

			// attachment
			MimeBodyPart singleattachment = new MimeBodyPart();
			DataSource source = new FileDataSource(new File(attchament));
			singleattachment.setDataHandler(new DataHandler(source));
			singleattachment.setFileName(new File(attchament).getName());

			multipart.addBodyPart((BodyPart) messageBodyPart);
			multipart.addBodyPart((BodyPart) singleattachment);

			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Email Sent Successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void csvWrite(String currency, String sellValue, String buyValue, String time, String status)
			throws IOException {
		String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
		String csvPath = System.getProperty("user.dir") + File.separator + "data" + File.separator
				+ "YesBankValues.csv";
		System.out.println("ValuesFilePath ==> " + csvPath);
		File csvFile = new File(csvPath);
		boolean fileExists = csvFile.exists();

		try {
			FileWriter fw = new FileWriter(csvFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			// Add header only once when file is created
			if (!fileExists) {
				out.println("Currency,Sell Value,Buy Value,Time,Status");
			}
			// Append data safely with quotes (avoid comma issues)
			out.println(String.join(",", "\"" + currency + "\"", "\"" + sellValue + "\"", "\"" + buyValue + "\"",
					"\"" + time + "\"", "\"" + status + "\""));
			out.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("---------  All Value written Successfully in CSV Done  ---------");
	}

	public static void verifyYesBankValues() throws Exception {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
		String mainXpath = "//div[@class='counterpartydbwins bordered-table']";
		List<WebElement> allCurrencyBoxes = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(mainXpath)));
		String yesbankStatus = "pass";
		int retryCount = 0;

		for (int i = 1; i <= allCurrencyBoxes.size(); i++) {
			String currencyNameXpath = "(//div[@class='counterpartydbwins bordered-table']/descendant::b)[" + i + "]";
			String currencyName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(currencyNameXpath)))
					.getText();
			System.out.println("CorrencyName ==> " + currencyName);

			String sellFirstXapth = "(((//div[@class='counterpartydbwins bordered-table'])[" + i
					+ "]/descendant::a[contains(@class,'x-class-')])[1]/descendant::span[@class!='ratespan'])[1]";
			String sellSecXpath = "(((//div[@class='counterpartydbwins bordered-table'])[" + i
					+ "]/descendant::a[contains(@class,'x-class-')])[1]/descendant::span[@class!='ratespan'])[2]";
			String buyFirstXapth = "(((//div[@class='counterpartydbwins bordered-table'])[" + i
					+ "]/descendant::a[contains(@class,'x-class-')])[2]/descendant::span[@class!='ratespan'])[1]";
			String buySecXapth = "(((//div[@class='counterpartydbwins bordered-table'])[" + i
					+ "]/descendant::a[contains(@class,'x-class-')])[2]/descendant::span[@class!='ratespan'])[2]";

			String firstSellVal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sellFirstXapth)))
					.getText();
			String secSellVal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sellSecXpath)))
					.getText();
			String firstBuylVal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(buyFirstXapth)))
					.getText();
			String secBuyVal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(buySecXapth))).getText();

			System.out.println("firstSellVal-->" + firstSellVal + "\n secSellVal-->" + secSellVal);
			System.out.println("firstBuylVal-->" + firstBuylVal + "\n secBuyVal-->" + secBuyVal);
			String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
			String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

			String subject = "";
			String body = "";
			String screenShotPath = null;

			if (firstSellVal.equalsIgnoreCase("NA") || firstSellVal.equalsIgnoreCase("-")
					|| firstSellVal.equalsIgnoreCase("") || firstSellVal == null || firstSellVal.isEmpty()
					|| firstBuylVal.equalsIgnoreCase("NA") || firstBuylVal.equalsIgnoreCase("-")
					|| firstBuylVal.equalsIgnoreCase("") || firstBuylVal == null || firstBuylVal.isEmpty()) {
				if (retryCount < 3) {
					retryToGetYesBankValues("left");
					Thread.sleep(3000);
					retryToGetYesBankValues("Right");
					retryCount++;
					i = 0;
					continue;
				}
			}

			if (firstSellVal.equalsIgnoreCase("NA") || firstSellVal.equalsIgnoreCase("-")
					|| firstSellVal.equalsIgnoreCase("") || firstSellVal == null || firstSellVal.isEmpty()) {

				yesbankStatus = "fail";
				body = currencyName + " Sell data not available at " + time + " on " + date;
				subject = "Yes FX  " + currencyName + " Sell";
				if (screenShotPath == null)
					screenShotPath = Framework.TakeScreenshots();
				String mailBody = "Dear Sir/Ma'am,<br/><br/>" + body;
				NewCustomFunctions.sendMail(subject, Framework.mailFrom, Framework.password, mailBody, Framework.mailTo,
						Framework.mailCc, screenShotPath);
				// insert db
				DB_Tables.insertIntoAllStockAlert("YesBankValue", body);
			}
			if (firstBuylVal.equalsIgnoreCase("NA") || firstBuylVal.equalsIgnoreCase("-")
					|| firstBuylVal.equalsIgnoreCase("") || firstBuylVal == null || firstBuylVal.isEmpty()) {

				yesbankStatus = "fail";
				body = currencyName + " Buy data not available at " + time + " on " + date;
				subject = "Yes FX  " + currencyName + " Buy";
				if (screenShotPath == null)
					screenShotPath = Framework.TakeScreenshots();
				String mailBody = "Dear Sir/Ma'am,<br/><br/>" + body;
				NewCustomFunctions.sendMail(subject, Framework.mailFrom, Framework.password, mailBody, Framework.mailTo,
						Framework.mailCc, screenShotPath);
				// insert db
				DB_Tables.insertIntoAllStockAlert("YesBankValue", body);
			}
			NewCustomFunctions.csvWrite(currencyName, firstSellVal + secSellVal, firstBuylVal + secBuyVal, currentTime,
					yesbankStatus);
			yesbankStatus = "pass";
		}
	}

	public static void retryToGetYesBankValues(String configuration) throws Exception {

		System.out.println("In retryToGetYesBankValues for " + configuration);
		WebElement settingEle = Functions.CheckObjectVisibility("xpath", "//input[@class=\"button icon-setting-btn\"]",
				"", Framework.pagename);
		((JavascriptExecutor) Framework.driver).executeScript("arguments[0].click();", new Object[] { settingEle });

		if (configuration.equalsIgnoreCase("left")) {
			WebElement optionsEle = Functions.CheckObjectVisibility("xpath", "//select[@id=\"selectRightCurr\"]", "",
					Framework.pagename);
			new Actions(Framework.driver).doubleClick(optionsEle).build().perform();

			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_A);
			Thread.sleep(500);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_A);
			Functions.CheckObjectVisibility("id", "btnLeft", "", Framework.pagename).click();
		} else {
			WebElement optionsEle = Functions.CheckObjectVisibility("id", "selectLeftCurr", "", Framework.pagename);
			new Actions(Framework.driver).doubleClick(optionsEle).build().perform();

			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_A);
			Thread.sleep(500);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_A);
			Functions.CheckObjectVisibility("id", "btnRight", "", Framework.pagename).click();
		}
		Functions.CheckObjectVisibility("xpath", "// button[text()=\"Save\"]", "", Framework.pagename).click();

	}

	public static void verifyBajajTellUsPage(String propertyValue) throws FilloException {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Framework.defaultwaittime);
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(propertyValue)));
			System.out.println("Page Appear...");
		} catch (Exception e) {
			System.out.println("Page Not Appear !!!");
			String pageName = Framework.pagename;
			while (Framework.pagename.equalsIgnoreCase(pageName)) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
		}
	}

	public static void ReadBandhanBNPMailOtp(WebElement webElementVal, String dataFieldRDS) {

		System.out.println("------ In ReadBandhanBNPMailOpt ----");
		String host = "imap.gmail.com";
		String userName = dataFieldRDS.split("#")[0];
		String password = dataFieldRDS.split("#")[1];
		String fromMail = dataFieldRDS.split("#")[2];
		String subject = dataFieldRDS.split("#")[3];

//		String host = "imap.gmail.com";
//		String userName = "kanganeajinkya1994@gmail.com";
//		String password = "cier zrlu jxax jrli";
//		String fromMail = "cs.barodabnppmf@kfintech.com";
//		String subject = "Baroda BNP Paribas Mutual Fund – One Time Password Authentication";
		String body = readMail(host, userName, password, fromMail, subject);
//		System.out.println(body);
		String otp = body.split("is your One Time Password \\(OTP\\)")[0].split("Paribas Mutual Fund!")[1].trim();
		otp = otp.replaceAll("^[\\s\\u00A0]+|[\\s\\u00A0]+$", "").trim();
		System.out.println("Your Otp ====>" + otp);
		webElementVal.sendKeys(otp);

	}

	public static void readMobileOtpOrMailOtp(WebElement webElementVal, String objectTypeRDS, String dataFieldRDS,
			String times, String propertyValueRDS) {

		String OTP = null;
		OTP = readMobileOtp(webElementVal, objectTypeRDS, dataFieldRDS, times, propertyValueRDS);
		if (OTP == null) {
			String host = "imap.gmail.com";
			String userName = dataFieldRDS.split("#")[2];
			String password = dataFieldRDS.split("#")[3];
			String fromMail = dataFieldRDS.split("#")[4];
			String subject = dataFieldRDS.split("#")[5];
			String otpText = dataFieldRDS.split("#")[6];

//			String host = "imap.gmail.com";
//			String userName = "kanganeajinkya1994@gmail.com";
//			String password = "cier zrlu jxax jrli";
//			String fromMail = "cs.barodabnppmf@kfintech.com";
//			String subject = "Baroda BNP Paribas Mutual Fund – One Time Password Authentication";
			String body = readMail(host, userName, password, fromMail, subject);
			System.out.println(body);
			OTP = body.split(otpText)[0];
			OTP = OTP.replaceAll("^[\\s\\u00A0]+|[\\s\\u00A0]+$", " ").trim();
			OTP = OTP.split(" ")[OTP.split(" ").length - 1].trim();
			System.out.println("Your Otp ====>" + OTP);
		}

		if (OTP == null) {
			OTP = JOptionPane.showInputDialog(null, "Enter OTP");
		}
		webElementVal.sendKeys(OTP);

	}

	public static void OldverifyMACMAppPage(String pageName) throws FilloException {
//		 Between Market Hours(9:15AM-3:30PM)
//       Profile --> DP Transfer Page -> Transfer Now Page-->Get CMR on email	
		LocalTime hourAfter_3 = LocalTime.of(9, 14).plusHours(3);
		LocalTime hourAfter_6 = LocalTime.of(9, 15).plusHours(6);
		LocalTime currentTime = LocalTime.now();
		System.out.println("After 3 hors of 9:15am :- " + hourAfter_3);
		System.out.println("After 6 hors of 9:15am :- " + hourAfter_6);
		if (pageName.equalsIgnoreCase("Forgot Password Page")) {
//			9:15-10:15, 12:15 12:15-13:15, 	
			if ((currentTime.isAfter(LocalTime.of(9, 15)) && currentTime.isBefore(LocalTime.of(10, 00)))
					|| (currentTime.isAfter(LocalTime.of(12, 15)) && currentTime.isBefore(LocalTime.of(13, 00)))
					|| (currentTime.isAfter(LocalTime.of(15, 15)) && currentTime.isBefore(LocalTime.of(15, 30)))) {
				System.out.println("Run this Page ==> Forgot Password Page ");
			} else {
				while (Framework.pagename.equalsIgnoreCase("Forgot Password Page")) {
					Framework.recordsetRDS.moveNext();
					Framework.pagename = Framework.recordsetRDS.getField("PageName");
				}
				Framework.recordsetRDS.movePrevious();
				System.out.println("After Skipping Forgot Password Page , current page ==> " + Framework.pagename);
			}
		}
		if (pageName.equalsIgnoreCase("Profile") || pageName.equalsIgnoreCase("DP Transfer Page")
				|| pageName.equalsIgnoreCase("Transfer Now Page") || pageName.equalsIgnoreCase("Get CMR on email")) {
			if ((currentTime.isAfter(LocalTime.of(9, 15)) && currentTime.isBefore(LocalTime.of(10, 00)))
					|| (currentTime.isAfter(LocalTime.of(15, 15)) && currentTime.isBefore(LocalTime.of(15, 30)))) {
				System.out.println("Run this Page ==> Profile,DP Transfer Page,Transfer Now Page,Get CMR on email");
			} else {
				List<String> allPages = new ArrayList<String>();
				allPages.add("Profile");
				allPages.add("DP Transfer Page");
				allPages.add("Transfer Now Page");
				allPages.add("Get CMR on email");
				while (allPages.contains(Framework.pagename)) {
					Framework.recordsetRDS.moveNext();
					Framework.pagename = Framework.recordsetRDS.getField("PageName");
				}
				Framework.recordsetRDS.movePrevious();
				System.out.println("After Skipping current page is ==> " + Framework.pagename);
			}
		}
	}

	public static void verifyMACMAppPage() throws FilloException {
		int hour = LocalTime.now().getHour();
		System.out.println("Current Hour :- " + hour);
		List<String> all6HrPages = new ArrayList<String>();
		all6HrPages.add("Profile Page");
//		all6HrPages.add("Dp Transfer Page");
		all6HrPages.add("Transfer Now Page");
		all6HrPages.add("Get CMR on email Page");
		if (Framework.pagename.equalsIgnoreCase("Forgot Password Page")) {
			if (hour % 3 == 0) {
				System.out.println("Run Once After 3 Hour :- " + Framework.pagename);
			} else {
				while (Framework.pagename.equalsIgnoreCase("Forgot Password Page")) {
					Framework.recordsetRDS.moveNext();
					Framework.pagename = Framework.recordsetRDS.getField("PageName");
				}
				Framework.recordsetRDS.movePrevious();
				System.out.println("After Skipping Forgot Password Page , current page ==> " + Framework.pagename);
			}
		}
		if (Framework.pagename.equalsIgnoreCase("Profile Page")) {
			if (hour % 6 == 0) {
				System.out.println("Run Once After 6 Hour :- " + Framework.pagename);
			} else {
				while (all6HrPages.contains(Framework.pagename)) {
					Framework.recordsetRDS.moveNext();
					Framework.pagename = Framework.recordsetRDS.getField("PageName");
				}
				Framework.recordsetRDS.movePrevious();
				System.out.println("After Skipping All pages ==> " + Framework.pagename);
			}
		}

	}

	public static void axisCINBLastTab(WebElement ele, String datafield) {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Framework.defaultwaittime);
		String lastTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(datafield.trim()))).getText();
		System.out.println("Last Tab Text:- " + lastTab);
		ele.sendKeys(lastTab);
	}

	public static void skipFileUploadGC(String xpath, String objectTypeCondition) throws FilloException {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Framework.defaultwaittime);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			return;
		} catch (Exception e) {
			System.out.println("File Upload Section Skip...");

			while (Framework.ObjectTypeRDS.equalsIgnoreCase(objectTypeCondition)) {
				Framework.recordsetRDS.moveNext();
				Framework.ObjectTypeRDS = Framework.recordsetRDS.getField("ObjectType");
			}
			Framework.recordsetRDS.movePrevious();
			System.out.println("After Skipping All pages ==> " + Framework.pagename);
		}
	}

	public static void rb_NavigateBack() throws AWTException {
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_ALT);
		rb.keyPress(KeyEvent.VK_LEFT);
		rb.keyRelease(KeyEvent.VK_LEFT);
		rb.keyRelease(KeyEvent.VK_ALT);
	}

	public static void rb_right() throws AWTException, InterruptedException {
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_RIGHT);
		rb.keyRelease(KeyEvent.VK_RIGHT);
		Thread.sleep(1000);
	}

	public static void rb_left() throws AWTException, InterruptedException {
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_LEFT);
		rb.keyRelease(KeyEvent.VK_LEFT);
		Thread.sleep(1000);
	}

	public static void sendUtkarshAdharNo(WebElement webElementVal, String dataFieldRDS) throws AWTException {

		LocalTime lt = LocalTime.now();
		dataFieldRDS = lt.getMinute() < 30 ? dataFieldRDS.split("#")[0] : dataFieldRDS.split("#")[1];
		System.out.println("Your adhar No :" + dataFieldRDS);
		webElementVal.sendKeys(dataFieldRDS);

	}

	public static void clearAndSendValue(WebElement webElementVal, String dataFieldRDS) {
		try {
			webElementVal.click();
			webElementVal.clear();
			webElementVal.sendKeys(dataFieldRDS);

			System.out.println("Entered value successfully: " + dataFieldRDS);
		} catch (Exception e) {

			System.out.println("Failed to clear and send value: " + e.getMessage());
		}
	}

	public static void clickAtEndOfElement(WebElement element) {
		Actions actions = new Actions(Framework.driver);
		int width = element.getSize().getWidth();
		int height = element.getSize().getHeight();
		System.out.println("Width ==" + width);
		System.out.println("height ==" + height);
		// Move to element and click near right edge
		actions.moveToElement(element, (width / 2) - 35, 0).click().build().perform();
		System.out.println("Clicked at end of element successfully");
	}

	public static void verifyBandhanMFSkipPages() throws FilloException {

		LocalTime localTime = LocalTime.now();

		if (localTime.isAfter(LocalTime.of(8, 0)) && localTime.isBefore(LocalTime.of(20, 15))) {

			boolean isOddHour = localTime.getHour() % 2 == 0;
			boolean isFirst15Min = localTime.getMinute() < 15;
			if (isOddHour && isFirst15Min) {
				System.out.println("Now Run for all pages");
			} else {
				System.out.println("Run in recent two hour, we should run in next");
				Framework.recordsetRDS.moveLast();
			}

		} else {
			System.out.println("Now Time is not run pages");
			Framework.recordsetRDS.moveLast();
//			Framework.recordsetRDS.movePrevious();
		}
	}

	public static void checkElementandRefresh(String propertyValueRDS) {
		try {
			new WebDriverWait(Framework.driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
		} catch (Exception e) {
			System.out.println("Element not found, now page to Refresh");
			Framework.driver.navigate().refresh();
		}
	}

	public static void SBINPSverifyAndClick(String propertyValueRDS) {
		LocalTime localTime = LocalTime.now();
		if (localTime.isBefore(LocalTime.of(8, 0)) || localTime.isAfter(LocalTime.of(20, 0))) {
			WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(15));
			try {
				((WebElement) wait
						.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS))))
						.click();
			} catch (Exception e) {

			}
		} else {
			System.out.println("Now time is not to check , close popup");
		}

	}

	public static void CheckLibertyPreviousPaymentMode() throws Exception {

		File file = new File(Framework.homedir + File.separator + "data" + File.separator + "LibertyPaymentMode.txt");
		if (!file.exists()) {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("paytm");
			bw.close();
			fw.close();
		}

		FileReader fw = new FileReader(file);
		BufferedReader bw = new BufferedReader(fw);
		String previousRunMode = bw.readLine();
		bw.close();
		fw.close();

		if (previousRunMode.equalsIgnoreCase("payu") && Framework.pagename.toLowerCase().contains("payu")) {
			System.out.printf(" [INFO] PAYU ALREADY RUN  :  %s [✓]%n", "true");

			while (Framework.pagename.toLowerCase().contains("payu")) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
		}

		else if (previousRunMode.equalsIgnoreCase("paytm") && Framework.pagename.toLowerCase().contains("paytm")) {
			System.out.printf(" [INFO] PAYTM ALREADY RUN :  %s [✓]%n", "true");

			while (Framework.pagename.toLowerCase().contains("paytm")) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
		} else {
			if (previousRunMode.equalsIgnoreCase("paytm")) {
				System.out.printf("Now time to run PAYU Page");
				writeLibertyPaymentMode("payu");
			} else {
				System.out.printf("Now time to run PAYTM Page");
				writeLibertyPaymentMode("paytm");
			}
		}

	}

	public static void writeLibertyPaymentMode(String dataFieldRDS) throws IOException {

		File file = new File(Framework.homedir + File.separator + "data" + File.separator + "LibertyPaymentMode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(dataFieldRDS);
		bw.close();
		fw.close();
		System.out.println("write successfully " + dataFieldRDS);
	}

	public static void YesBankBulkFileUpload(String propertyValueRDS, String dataFieldRDS) throws Exception {

		String path = Framework.homedir + File.separator + "data" + File.separator + dataFieldRDS;

		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection con = null;
		Recordset record = null;

		try {
			con = fillo.getConnection(path);

			String query = "SELECT * FROM Sheet1";
			record = con.executeQuery(query);

			String value = null;

			if (record.next()) {
				value = record.getField("Remarks");
				System.out.println("Before Change Remark = " + value);
			}

			// Format timestamp
			String timestamp = java.time.LocalDateTime.now()
					.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

			value = "TEST " + timestamp;

			// ✅ Update only first row (assuming Remarks is unique or using another column)
			String updateQuery = "UPDATE Sheet1 SET Remarks='" + value.replace("'", "''") + "' WHERE Remarks='"
					+ record.getField("Remarks") + "'";

			con.executeUpdate(updateQuery);
			System.out.println("Update successfully");

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (record != null)
				record.close();
			if (con != null)
				con.close();
		}

		sendkeysUsingxpath(propertyValueRDS, path);
	}

	public static void sendkeysUsingxpath(String propertyValueRDS, String dataFieldRDS) {
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS.trim())))
				.sendKeys(dataFieldRDS);
		System.out.println("Send successfully");
	}

	public static void VerifyYesbankPaymentmode(String propertyValueRDS, String dataFieldRDS) throws Exception {

		String[] arr = dataFieldRDS.split("#");
		String paymentModepath = arr[0];
		String backPath = arr[1];
		String paymentMode = arr[2];

		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
		List<WebElement> allRow = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(propertyValueRDS)));

		System.out.println("Total Row ==" + allRow.size());
		for (int i = 0; i < allRow.size() && i < 3; i++) {

			WebElement row = allRow.get(i);
			WebElement button = wait.until(driver -> row.findElement(By.xpath(".//img")));

			wait.until(ExpectedConditions.elementToBeClickable(button)).click();

			String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(paymentModepath)))
					.getText();
			System.out.println("getText ==" + text);
			if (text.toLowerCase().contains(paymentMode.toLowerCase())) {
				System.out.println(paymentMode + " PaymentMode Found");
				break;
			} else {
				System.out.println(paymentMode + " PaymentMode not Found, Back & check further");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(backPath))).click();
			}
			allRow = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(propertyValueRDS)));

		}

	}

	public static void flutterSendkeys(String propertyValueRDS, String dataFieldRDS) {

		System.out.println("try to Send ..");
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(Framework.defaultwaittime));
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValueRDS)));
//		Point location = ele.getLocation();

		Actions ac = new Actions(driver);
//		ac.moveByOffset(0,0).perform();
		// Move to location using offset (relative to page)
//		ac.moveByOffset(location.getX(), location.getY()).click().sendKeys(dataFieldRDS).perform();
		ac.moveToElement(ele).click().sendKeys(dataFieldRDS).perform();
		System.out.println("Send successfully");

	}

	public static void verifyToRunPages(String dataFieldRDS) throws FilloException {

		// input: 9:30#15:30#Logout Page#runningGap#extraWork$extraWork...
		String[] arr = dataFieldRDS.split("#");

		String start = arr[0];
		String end = arr[1];
		String jumpPageName = arr[2];

		String runningGap = null;
		String extraWork = null;

		if (arr.length >= 4) {
			runningGap = arr[3];
		}
		if (arr.length >= 5) {
			extraWork = arr[4];
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime startTime = LocalTime.parse(start, formatter);
		LocalTime endTime = LocalTime.parse(end, formatter);
		LocalTime currentTime = LocalTime.now();

		// Inclusive condition (recommended)
		if (!currentTime.isBefore(startTime) && !currentTime.isAfter(endTime)) {

			if (runningGap != null) {
				int gap = Integer.parseInt(runningGap);
				if (gap <= 0) {
					System.out.println("Invalid runningGap ..., Run in every frequency");
					return;
				}
				int currentHour = currentTime.getHour();
				int startHour = startTime.getHour();
				int hourDiff = currentHour - startHour;
				System.out.println("hourDiff ==" + hourDiff);
				if (hourDiff % gap == 0 && currentTime.getMinute() < 15) {
					System.out.println("Now Run for all pages");
				} else {
					System.out.println("Run in recent " + gap + " hour, will run in next slot");

					while (!Framework.pagename.equals(jumpPageName)) {
						Framework.recordsetRDS.moveNext();
						Framework.pagename = Framework.recordsetRDS.getField("PageName");
					}
					Framework.recordsetRDS.movePrevious();
					if (extraWork != null)
						verifyToRunPagesExtraWork(extraWork, Framework.pagename);

					return;
				}
			} else {
				System.out.println("Now Run for all pages ,No Ruuing GAp ");
			}

		} else {
			System.out.println("Skipping page: Outside allowed time window");

			while (!Framework.pagename.equals(jumpPageName)) {
				Framework.recordsetRDS.moveNext();
				Framework.pagename = Framework.recordsetRDS.getField("PageName");
			}
			Framework.recordsetRDS.movePrevious();
			if (extraWork != null)
				verifyToRunPagesExtraWork(extraWork, Framework.pagename);

		}
	}

	public static void verifyToRunPagesExtraWork(String extraWork, String pageName) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		for (String str : extraWork.split("$")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(str)));
			System.out.println("Click Done in " + str);
		}

	}

	public static void clickLicPolicyNo(String objectTypeRDS, String dataFieldRDS) throws Exception {

		String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + "LicPolicyNo.txt";

		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
			FileWriter fr = new FileWriter(file);
			BufferedWriter br = new BufferedWriter(fr);
			br.write("1");
			br.close();
			fr.close();
		}
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		int data = Integer.valueOf(line);
		fr.close();
		br.close();

		String xpath = dataFieldRDS.split("#")[data - 1];

		System.out.println("Now poily to click === " + xpath);
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(10));
		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		if (objectTypeRDS.equalsIgnoreCase("JS")) {
		} else if (objectTypeRDS.equalsIgnoreCase("action")) {
		} else if (objectTypeRDS.equalsIgnoreCase("char")) {
		} else {
			ele.click();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			if (data == 3) {
				data = 1;
			} else {
				data = data + 1;

			}
			bw.write(String.valueOf(data));
			bw.close();
			fw.close();
		}
	}
	
	
	public static void selectLAndTPLHLDob(WebElement webElementVal, String dataFieldRDS) throws InterruptedException {

		String[] split = dataFieldRDS.split("#");
		String dob = split[0];
		webElementVal.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[1])));
		ele.click();
		Thread.sleep(4000);
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[2])));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0,-180)", ele);

		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(split[3])));

		for (WebElement e : elements) {
			System.out.println(e.getText());

			if (e.getText().equalsIgnoreCase(dob.split("-")[2])) {
				e.click();
				break;
			}
		}

		Thread.sleep(2000);
		int currentMonthValue = LocalDate.now().getMonthValue();
		int MonthValue = Integer.valueOf(dob.split("-")[1]);
		int clickTime = currentMonthValue - MonthValue;

		System.out.println("Now click Time " + clickTime);
		if (clickTime > 0) {
			while (clickTime > 0) {
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[4])));
				ele.click();
				clickTime--;
				Thread.sleep(1500);
			}
		} else if (clickTime < 0) {

			while (clickTime < 0) {
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(split[5])));
				ele.click();
				clickTime++;
				Thread.sleep(1500);
			}
		}

		Thread.sleep(2000);
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//flt-semantics[contains(text(), \"" + dob.split("-")[0] + "\")]")));
		ele.click();
		ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//flt-semantics[text()=\"OK\"]")));
		ele.click();

	}
	
	 public static void sendMinimumValue(String objectTypeRDS, WebElement webElementVal, String dataFieldRDS) {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS)));
			String text = ele.getText();
			System.out.println("Text == " + text);
			String price = text.split("Range:")[1].split("-")[0].trim();
			if (objectTypeRDS.equalsIgnoreCase("js")) {
				JavascriptExecutor jse4 = (JavascriptExecutor) Framework.driver;
				jse4.executeScript("arguments[0].value='" + price + "';", new Object[] { webElementVal });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(webElementVal, new CharSequence[] { price }).build().perform();
			} else {
				webElementVal.sendKeys(price);
			}

		}

		public static void sendModifiedValue(String objectTypeRDS, WebElement webElementVal, String dataFieldRDS) {

			// xpath#percent to increase
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[0])));
			String text = ele.getText();
			System.out.println("Text == " + text);
			String price = text.split("Range:")[1].split("-")[0].trim();
			double priceValue = Double.parseDouble(price);
			priceValue = priceValue + (priceValue * Double.parseDouble(dataFieldRDS.split("#")[1]));
			price = String.valueOf(priceValue);
			if (objectTypeRDS.equalsIgnoreCase("js")) {
				JavascriptExecutor jse4 = (JavascriptExecutor) Framework.driver;
				jse4.executeScript("arguments[0].value='" + price + "';", new Object[] { webElementVal });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(webElementVal, new CharSequence[] { price }).build().perform();
			} else {
				webElementVal.sendKeys(price);
			}

		}
		public static void sendMaximumValue(String objectTypeRDS, WebElement webElementVal, String dataFieldRDS) {

			//Range:1.9 - 2.6
			// xpath#percent to increase
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dataFieldRDS.split("#")[0])));
			String text = ele.getText();
			System.out.println("Text == " + text);
			String price = text.split("Range:")[1].split("-")[1].trim();
			double priceValue = Double.parseDouble(price);
			priceValue = priceValue - (priceValue * Double.parseDouble(dataFieldRDS.split("#")[1]));
			price = String.valueOf(priceValue);
			if (objectTypeRDS.equalsIgnoreCase("js")) {
				JavascriptExecutor jse4 = (JavascriptExecutor) Framework.driver;
				jse4.executeScript("arguments[0].value='" + price + "';", new Object[] { webElementVal });
			} else if (objectTypeRDS.equalsIgnoreCase("action")) {
				Actions act = new Actions(Framework.driver);
				act.sendKeys(webElementVal, new CharSequence[] { price }).build().perform();
			} else {
				webElementVal.sendKeys(price);
			}

		}

}
