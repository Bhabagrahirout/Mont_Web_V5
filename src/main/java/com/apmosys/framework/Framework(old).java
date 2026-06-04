package com.apmosys.framework;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
//import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Framework {
	public static String FileNameV;
	public static String homedir;
	public static String ScreenfileLocation;
	public static String oldbrowser;
	public static String ScriptName;
	public static String browser;
	public static String ScreenshotfileLocation;
	public static String ScreenshotfileLocation1;
	public static String parenwindow;
	public static String functiontorun;
	public static String sTestCaseID;
	public static String ApplicationName;
	public static String logstatus;
	public static String errorsatus;
	public static String sDescription;
	public static String dburl;
	public static String dbuser;
	public static String dbpassword;
	public static String errorpagename;
	public static String OTPcurrentTime;
	public static Fillo fillo;
	public static Fillo filloDs;
	public static WebDriver driver;
	public static String step;
	public static String datasheetspath;
	public static int browsersts;
	public static int defaultwaittime;
	public static int iResponseTime;
	public static int availability_alert;
	public static int ResponseTime_alert;
	public static ExtentTest extent;
	public static ExtentReports extentrpt;
	public static Double tStartTime;
	public static String currentTime;
	public static String userMobile;
	public static Properties pro;
	public static String bHour;
	public static String bHourFunction;
	public static String browserToOpen;
	public static Recordset recordsetRDS;
	public static String monitoringBusinessHour;
	public static String monitoringDataSheetPath;
	private static String osname;
	static String monitoringTimeDataSheetName;
	private static String monitoringTime;
	public static String host;
	public static String port;
	public static String mailFrom;
	public static String password;
	public static String mailTo;
	public static String subject;
	public static String mailCc;
	public static String scType;
	public static String pageLoadTime;
	public static String headless;
	public static String scriptStartTime;
	public static String scriptEndTime;
	public static int monitoringInstancesId;
	public static int applicationId;
	public static int clientId;
	public static String zone;
	public static String domain;
	public static int pageId;
	public static boolean flag = true;
	public static String createdBy;
	public static Map<String, String> dbDetails = new HashMap<String, String>();
	public static String macId;
	public static int runTimeInstanceId;
	public static int reportRunTimeInstanceId;
	public static java.sql.Connection dbConnection = null;
	public static String dataSheetName;
	public static String Srno;
	public static String Module;
	public static String pagename;
	public static String ControlRDS;
	public static String ObjectTypeRDS;
	public static String propertyValues;
	public static String actionRDS;
	public static String errorType = "";
	public static String errorMessage = "";
	public static int executionId;
	public static String errorDesc;
	public static String customJarPath;
	public static String customClassName;
	public static String bankHoliday;
	public static String location = "";
	public static String fastPageLoad, pageStability;
	public static boolean status = true;
	public static String showLocator = "";
	public static String defaultDownload = "";
	public static String mailAlert;
	public static String exitStatus = "Y";
	public static boolean exitFlag = false;
	public static String ffProfilePath;
	public static String propertiesFilePath;
	public static Properties propertiesObj;
	public static String recentCaptcha;
	public static String divider = "═".repeat(60);
	public static String subDiv = "─".repeat(60);

	public static void main(final String[] args) throws Exception {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {

				// updating end time in runtime instance
				if (flag) {
					System.out.println("\n" + divider);
					System.out.println(" ⚡ SHUTDOWN HOOK ACTIVATED");
					System.out.println(subDiv);
					scriptEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
					DB_Tables.updateEndTimeRunTimeInstance("terminated", scriptEndTime, applicationId,
							runTimeInstanceId);
//					DB_Tables.updateEndTimeReportsRuntime("terminated", scriptEndTime, applicationId,
//							reportRunTimeInstanceId);
				}
				if (dbConnection != null) {
					try {
						dbConnection.close();
						System.out.printf(" [DB]  CONNECTION : Closed [✓]%n");
						System.out.println(subDiv);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (!browser.equalsIgnoreCase("sikuli")) {
					if (!Framework.driver.toString().trim().contains("null")) {
						Framework.driver.quit();
						System.out.printf(" [WEB] DRIVER     : Quit successfully [✓]%n");
						System.out.println(divider);
					}
				}

			}
		});

		macId = DB_Tables.getMac();
		System.out.println("Imported Date MAY 1 ");
		System.out.println("Mac Id Is ====>" + macId);
//		dbDetails.put("url", "jdbc:mysql://192.168.1.228:3306/nonsbi_final");
//		dbDetails.put("url", "jdbc:mysql://192.168.9.16:3306/nonsbi_final_15jan2025");
//		dbDetails.put("id", "root");
//		dbDetails.put("id", "SynMonitoring");
//		dbDetails.put("pass", "Welcome@2023");
//		dbDetails.put("pass", "Operation@123");

		/////////////

		propertiesFilePath = System.getProperty("user.dir") + File.separator + "mf_web.properties";
		propertiesObj = new Properties();
		FileInputStream fis = new FileInputStream(propertiesFilePath);
		Framework.propertiesObj.load(fis);
		fis.close();

		ReadDB();
		byte[] decodedBytes = Base64.getDecoder().decode(Framework.dbpassword);
		String decodedString = new String(decodedBytes);
//		System.out.println("Decoded String: " + decodedString);

		dbDetails.put("url", Framework.dburl);
		dbDetails.put("id", Framework.dbuser);
		dbDetails.put("pass", decodedString);

		try {
			System.out.println("Getting connection ................");
			Class.forName("com.mysql.cj.jdbc.Driver");
			dbConnection = DriverManager.getConnection(dbDetails.get("url"), dbDetails.get("id"),
					dbDetails.get("pass"));
			System.out.println("************** Connection established with db *************");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("************** Connection Fail With db *************");
		}

		fis = new FileInputStream(propertiesFilePath);
		Framework.pro.load(fis);

		Framework.bHour = Framework.pro.getProperty("businessHourRunStatus");
		Framework.bHourFunction = Framework.pro.getProperty("businessHourFunctions");
		Framework.monitoringBusinessHour = Framework.pro.getProperty("monitoringBusinessHour");
		Framework.monitoringDataSheetPath = Framework.pro.getProperty("monitoringDataSheetPath");
		Framework.monitoringTimeDataSheetName = Framework.pro.getProperty("monitoringTimeDataSheetName");
		Framework.monitoringTime = Framework.pro.getProperty("monitoringTime");
		Framework.pageLoadTime = Framework.pro.getProperty("pageLoadTime");
		Framework.headless = Framework.pro.getProperty("headless");
		Framework.bankHoliday = Framework.pro.getProperty("bankHoliday", "N");
		Framework.fastPageLoad = Framework.pro.getProperty("fastPageLoad", "N");
		Framework.showLocator = Framework.pro.getProperty("showLocator", "N");
		Framework.defaultDownload = Framework.pro.getProperty("defaultDownload", "Y");
		Framework.location = Framework.pro.getProperty("location", "");
		Framework.mailAlert = Framework.pro.getProperty("mailAlert", "N");
		Framework.exitStatus = Framework.pro.getProperty("exitStatus", "Y");
		Framework.ffProfilePath = Framework.pro.getProperty("ffProfilePath",
				"/home/apmosys/.mozilla/firefox/s3v60wo7.default-release");
		Framework.pageStability = Framework.pro.getProperty("pageStability", "N");


		// For Custom Jar Path and Class Name
		Framework.customJarPath = System.getProperty("user.dir") + File.separator
				+ Framework.pro.getProperty("customJarPath", "");
		Framework.customClassName = Framework.pro.getProperty("customClassName", "");
		if (Framework.customJarPath.isEmpty() && Framework.customClassName.isEmpty()) {
			System.out.println("---------Custom Jar Path and Class Name is Empty----------");
		}
		// for hdfc alert
		host = (String) pro.get("host");
		port = (String) pro.get("port");
		mailFrom = (String) pro.get("mailFrom");
		password = (String) pro.get("password");
		mailTo = (String) pro.get("mailTo");
		mailCc = (String) pro.get("mailCc");
		subject = (String) pro.get("subject");

		fis.close();
		if (Framework.monitoringBusinessHour.equalsIgnoreCase("y") && Framework.monitoringBusinessHour != null
				&& !Framework.monitoringBusinessHour.equalsIgnoreCase("")) {
			BusinessHour.monitoringHour();
		}
		try {
			if (Framework.monitoringTime.equalsIgnoreCase("y") && Framework.monitoringTime != null
					&& !Framework.monitoringTime.equalsIgnoreCase("")) {
				BusinessHour.monitoringMinute();
			}
		} catch (Exception ex) {
		}
		getProcessId();
		Run();
	}

	public static void Run() throws URISyntaxException, ParseException, FilloException, IOException {
		CodeSource codeSource = Framework.class.getProtectionDomain().getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		Connection con = null;
		Framework.homedir = System.getProperty("user.dir");
		Framework.browsersts = 0;
		Framework.oldbrowser = "new";

		System.out.println("Project Path  -------------> " + Framework.homedir);

		Framework.fillo = new Fillo();
		try {
			String maincontrollerpath = Framework.homedir + File.separator + "Main_Controller_Web.xlsx";
			con = Framework.fillo.getConnection(maincontrollerpath);

			String strmaincontroller = "Select * from MainController where RunStatus='Y'";
			Recordset recordset = con.executeQuery(strmaincontroller);
			while (recordset.next()) {
				try {
					flag = true;
					int j = 5;
					do {
						Framework.functiontorun = recordset.getField(j).value();
						if (Framework.functiontorun != "") {
							++j;
						}
					} while (Framework.functiontorun != "");

					if (j > 4) {
						for (int k = 5; k <= j - 1; ++k) {
							Framework.functiontorun = recordset.getField(k).value();
							Framework.ApplicationName = recordset.getField("ApplicationName");
							Framework.ScriptName = recordset.getField("ApplicationName");
							Framework.browser = recordset.getField("Browser");

							System.out.println("Application Name  ---------> " + Framework.ApplicationName);
							System.out.println("Instance Name     ---------> " + Framework.functiontorun);

							scriptStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

							Framework.osname = getosname();

							if (Framework.functiontorun != "null" && Framework.functiontorun != null
									&& Framework.functiontorun != "" && !Framework.functiontorun.isEmpty()) {
								if (Framework.browser.equalsIgnoreCase("sikuli")) {
									System.out.println("Sikuli Running............................");
								} else {

									StartBrowser(Framework.homedir, Framework.browser);
									Framework.oldbrowser = Framework.browser;
								}
								String strQueryDS = "select * from DataSheet where InstanceName ='" + functiontorun
										+ "'";
								Recordset recordsetDS = con.executeQuery(strQueryDS);
								while (recordsetDS.next()) {
									String sDataSheet = recordsetDS.getField("DataSheet");
									System.out.println("Sheet Name ----------------> " + sDataSheet);
									dataSheetName = sDataSheet.replaceAll(".xlsx", "");

									if (Framework.osname.equalsIgnoreCase("Windows")) {
										Framework.datasheetspath = String.valueOf(Framework.homedir) + "\\DataSheet\\"
												+ sDataSheet;
										System.out.println("Sheet Path ----------------> " + Framework.datasheetspath);
									} else {
										Framework.datasheetspath = String.valueOf(Framework.homedir) + "/DataSheet/"
												+ sDataSheet;
										System.out.println("Sheet Path ----------------> " + Framework.datasheetspath);
									}

									Fillo filloDs = new Fillo();
									Connection connectionDS = filloDs.getConnection(Framework.datasheetspath);
									String strQueryRDSF = "select * from Sheet2 where RunStatus='Y'";
									Recordset recordsetRDSF = connectionDS.executeQuery(strQueryRDSF);

									double networkSpeed = DB_Tables.getNetworkSpeed();
									System.out.println("NetworkSpeed --------------> " + networkSpeed + " MB/s");
//									String locaton = DB_Tables.getLocation();
//									System.out.println("locaton ======= " + locaton);

									monitoringInstancesId = DB_Tables.getMonitoringInstancesId(dataSheetName);
									System.out.println("MonitoringInstancesId -----> " + monitoringInstancesId);
									zone = DB_Tables.getMonitoringDetails(monitoringInstancesId);
									System.out.println("MonitoringInstancesZone ---> " + zone);

									List<Integer> applicationMasterData = DB_Tables
											.getApplicationMasterData(ApplicationName);
									applicationId = applicationMasterData.get(0);
									clientId = applicationMasterData.get(1);
									System.out.println("ApplicationId -------------> " + applicationId);
									System.out.println("ClientId ------------------> " + clientId);

									domain = DB_Tables.getclientMasterData(clientId);
									System.out.println("Domain --------------------> " + domain);

									createdBy = DB_Tables.getCreatedBy(applicationId);
									System.out.println("createdBy id --------------> " + createdBy);

									String ipAdress = DB_Tables.getIpAdress();
									System.out.println("ipAdress id ---------------> " + ipAdress);

									List<String> infraMasterData = DB_Tables.getContainerJenkinMasterIP(ipAdress);
									System.out.println("infra Master Data ---------> " + infraMasterData.toString());
									int infraId = 0;
									if (infraMasterData.get(0) != null) {
										infraId = Integer.parseInt(infraMasterData.get(0));
									}
									String containerIP = infraMasterData.get(1);
									String jenkinsIP = infraMasterData.get(2);

									List<String> monitoringInstanceData = DB_Tables.getDeviceDetails(dataSheetName);
									System.out.println(
											"monitoring Instance Data == " + monitoringInstanceData.toString());
									String device_type = monitoringInstanceData.get(0);
									String browser_type = monitoringInstanceData.get(1);
									String version = monitoringInstanceData.get(2);

									DB_Tables.Table_runtime_instance(dataSheetName, scriptStartTime, scriptStartTime,
											"running", new SimpleDateFormat("yyyyMMdd").format(new Date()),
											new SimpleDateFormat("HH").format(new Date()),
											new SimpleDateFormat("mm").format(new Date()), browser_type, device_type,
											version, ipAdress, jenkinsIP, monitoringInstancesId, applicationId, infraId,
											createdBy, scriptStartTime, macId, scriptStartTime, networkSpeed);
									// for reports_runtime
									// Amar
//									DB_Tables.Table_reports_runtime_instance(dataSheetName, scriptStartTime,
//											scriptStartTime, "running", new SimpleDateFormat("yyyyMMdd").format(new Date())
//											, new SimpleDateFormat("HH").format(new Date())
//											, new SimpleDateFormat("mm").format(new Date()),
//											browser_type, device_type, version, ipAdress, jenkinsIP,
//											monitoringInstancesId, applicationId, infraId, createdBy, scriptStartTime,
//											macId, scriptStartTime, networkSpeed);

///*********************************************************************************//

									while (recordsetRDSF.next()) {
										Framework.errorsatus = "0";
										Framework.sTestCaseID = recordsetRDSF.getField("TestCaseID");
										Framework.sDescription = recordsetRDSF.getField("Description");
//									Framework.extent = Framework.extentrpt.startTest(
//											"<font color=\"Blue\"><b>" + Framework.ScenarioID
//													+ "</b></font> - <font color=\"Green\"><b>" + Framework.sTestCaseID
//													+ "</b></font>(" + Framework.sDescription + ")",
//											"</br><h5>" + Framework.sDescription + "</h5>");
										String strQueryRDS = "select * from Sheet1 where RunStatus='Y'";
//										try {
										Framework.recordsetRDS = connectionDS.executeQuery(strQueryRDS);
										while (Framework.recordsetRDS.next()) {
											int count = Framework.recordsetRDS.getCount();
											String RunStatusRDS = Framework.recordsetRDS.getField("RunStatus");
											ControlRDS = Framework.recordsetRDS.getField("Control").trim();
											Framework.ObjectTypeRDS = Framework.recordsetRDS.getField("ObjectType");
											String PropertyNameRDS = Framework.recordsetRDS.getField("PropertyName");
											String PropertyValueRDS = propertyValues = Framework.recordsetRDS
													.getField("PropertyValue");
											String DataFieldRDS = Framework.recordsetRDS.getField("Datafield");
											String ActionRDS = Framework.recordsetRDS.getField("Action");
											pagename = Framework.recordsetRDS.getField("PageName");
											actionRDS = ActionRDS;
											// scType=Framework.recordsetRDS.getField("scType");
//											if (Framework.errorsatus == "1"
//													&& !Framework.pagename.equalsIgnoreCase(Framework.errorpagename)) {
//												Framework.errorsatus = "0";
//											}
											Framework.Srno = Framework.recordsetRDS.getField("Srno");
											String sColumnValue = "";
											int FieldStatus = 0;
											if (RunStatusRDS.equalsIgnoreCase("Y")) {
//												strQueryRDSF = "select * from Sheet2 where TestCaseID='"
//														+ Framework.sTestCaseID + "' and ScenarioID='"
//														+ Framework.ScenarioID + "' and RunStatus='Y'";
												strQueryRDSF = "select * from Sheet2 where RunStatus='Y'";

												Connection connectionDS2 = filloDs
														.getConnection(Framework.datasheetspath);
												Recordset recordsetRDSFsc = connectionDS2.executeQuery(strQueryRDSF);
												String Stringtoappend = "";
												String sColumnName = "";
												int moduleStatus = 0;
												while (recordsetRDSFsc.next()) {
													for (int xm = 0; xm <= 1000; ++xm) {
														try {
															sColumnName = recordsetRDSFsc.getField(xm).name();
															if (!sColumnName.equalsIgnoreCase(DataFieldRDS)) {
																continue;
															}
															sColumnValue = recordsetRDSFsc.getField(xm).value();
															FieldStatus = 1;
														} catch (Exception ex) {
														}
														break;
													}
													if (FieldStatus == 1) {
														Stringtoappend = "<font color=\"Red\"><b>Data Field - </b></font>"
																+ sColumnName
																+ " <font color=\"Green\"><b>TestData - </b></font>"
																+ sColumnValue;
													}
												}
												System.out.println();
												System.out.println();
												System.out.println(
														"                    Step No - " + Framework.Srno + " Page  is "
																+ pagename + " Error status " + Framework.errorsatus);
												// if (Framework.errorsatus == "0") {

												if (ObjectTypeRDS.equalsIgnoreCase("Custom")) {
													try {
														// Create a URLClassLoader with the JAR file path
//														URLClassLoader classLoader = new URLClassLoader(
//																new URL[] { new URL("file://" + jarFilePath) });
//
//														// Load the class dynamically
//														Class<?> loadedClass = classLoader.loadClass(className);
//
//														// Create an instance of the loaded class
//														Object instance = loadedClass.newInstance();

														URL jarURL = new URL(
																"file:" + Framework.customJarPath.replace("\\", "/"));

														URLClassLoader classLoader = new URLClassLoader(
																new URL[] { jarURL });

														// Load a class from the JAR file
														Class<?> loadedClass = classLoader
																.loadClass(Framework.customClassName);

														Object instance = loadedClass.newInstance();

														// Invoke a method on the instance
														Method method = loadedClass.getMethod("allFunction",
																WebDriver.class, String.class, String.class,
																String.class, String.class);

														if (ControlRDS.equalsIgnoreCase("V")) {
															try {
																List actualResultValue = (List) method.invoke(instance,
																		Framework.driver, PropertyNameRDS,
																		PropertyValueRDS, sColumnValue, ActionRDS);

																System.out.println(
																		"List Size Is === " + actualResultValue.size());
																System.out.println(actualResultValue);

																if (actualResultValue.size() == 1) {
																	System.out.println("Method return value == "
																			+ actualResultValue.get(0));
																	Monitoring_FrameWork.SaveResult(
																			actualResultValue.get(0).toString(),
																			sColumnValue);
																} else {
																	System.out.println("Actual value == "
																			+ actualResultValue.get(0) + "||"
																			+ "Expected value == "
																			+ actualResultValue.get(1));
																	Monitoring_FrameWork.SaveResult(
																			actualResultValue.get(0).toString(),
																			actualResultValue.get(1).toString());
																}
															} catch (Exception e) {
																Framework.errorsatus = "1";
																Monitoring_FrameWork.SaveResult(
																		"Either locator got change/ Network delay/ page not loaded",
																		sColumnValue);

															}
														} else {
															try {

																method.invoke(instance, Framework.driver,
																		PropertyNameRDS, PropertyValueRDS, sColumnValue,
																		ActionRDS);
															} catch (Exception e) {
																e.printStackTrace();
																Framework.errorsatus = "1";
																Monitoring_FrameWork.SaveResult(
																		"Either locator got change/ Network delay/ page not loaded",
																		sColumnValue);

															}
														}
														classLoader.close();

													} catch (Exception e) {
														e.printStackTrace();
													}
												} else if (ObjectTypeRDS.equalsIgnoreCase("customStartBrowser")) {

													try {
														// Create a URLClassLoader with the JAR file path
//														URLClassLoader classLoader = new URLClassLoader(
//																new URL[] { new URL("file://" + jarFilePath) });
//
//														// Load the class dynamically
//														Class<?> loadedClass = classLoader.loadClass(className);
//
//														// Create an instance of the loaded class
//														Object instance = loadedClass.newInstance();

														URL jarURL = new URL(
																"file:" + Framework.customJarPath.replace("\\", "/"));

														URLClassLoader classLoader = new URLClassLoader(
																new URL[] { jarURL });

														// Load a class from the JAR file
														Class<?> loadedClass = classLoader
																.loadClass(Framework.customClassName);

														Object instance = loadedClass.newInstance();

														// Invoke a method on the instance
														Method method = loadedClass.getMethod("customStartBrowser",
																String.class, String.class);

														try {
															Framework.driver = (WebDriver) method.invoke(instance,
																	Framework.browser, Framework.headless);
														} catch (Exception e) {
															e.printStackTrace();
															Monitoring_FrameWork.SaveResult(
																	"Either locator got change/ Network delay/ page not loaded",
																	sColumnValue);

														}

														classLoader.close();

													} catch (Exception e) {
														e.printStackTrace();
													}

												} else if (ObjectTypeRDS.equalsIgnoreCase("CustomConditionMethods")) {

//													String jarFilePath =System.getProperty("user.dir")+File.separator+"CustomJar"+
//															  File.separator+"CustomiseFunctions.jar";
//															String className = "com.apmosys.framework.CustomiseFunctions";

													try {
														// Create a URLClassLoader with the JAR file path
//														URLClassLoader classLoader = new URLClassLoader(
//																new URL[] { new URL("file://" + jarFilePath) });
//
//														// Load the class dynamically
//														Class<?> loadedClass = classLoader.loadClass(className);
//
//														// Create an instance of the loaded class
//														Object instance = loadedClass.newInstance();

														URL jarURL = new URL(
																"file:" + Framework.customJarPath.replace("\\", "/"));

														URLClassLoader classLoader = new URLClassLoader(
																new URL[] { jarURL });

														// Load a class from the JAR file
														Class<?> loadedClass = classLoader
																.loadClass(Framework.customClassName);

														Object instance = loadedClass.newInstance();

														// Invoke a method on the instance
														Method method = loadedClass.getMethod("conditionsMethods",
																Recordset.class, WebDriver.class, String.class,
																String.class, String.class, String.class);

														try {

															Framework.recordsetRDS = (Recordset) method.invoke(instance,
																	Framework.recordsetRDS, Framework.driver,
																	PropertyNameRDS, PropertyValueRDS, sColumnValue,
																	ActionRDS);
														} catch (Exception e) {
															e.printStackTrace();
															Monitoring_FrameWork.SaveResult(
																	"Either locator got change/ Network delay/ page not loaded",
																	sColumnValue);

														}

														classLoader.close();

													} catch (Exception e) {
														e.printStackTrace();
													}

												}

												else {

													Functions.Actions(ControlRDS, Framework.ObjectTypeRDS,
															PropertyNameRDS, PropertyValueRDS, sColumnValue, ActionRDS,
															Framework.Srno, FieldStatus, pagename);
												}

												Framework.logstatus = "0";
											}
										}
									}
								}
							}

						}
					}

					// updating end time in runtime instance
					scriptEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
					flag = false;
					System.out.println("\n" + divider);
					if (Monitoring_FrameWork.logoutFlag == true || Framework.exitFlag == true) {

						DB_Tables.updateEndTimeRunTimeInstance("terminated", scriptEndTime, applicationId,
								runTimeInstanceId);
//						DB_Tables.updateEndTimeReportsRuntime("terminated", Framework.scriptEndTime, Framework.applicationId,
//								Framework.reportRunTimeInstanceId);
						System.exit(1);
					} else {

						DB_Tables.updateEndTimeRunTimeInstance("success", scriptEndTime, applicationId,
								runTimeInstanceId);
//						DB_Tables.updateEndTimeReportsRuntime("success", Framework.scriptEndTime, Framework.applicationId,
//								Framework.reportRunTimeInstanceId);
					}
					System.out.println();
					System.out.println("********************** Execution Completed " + functiontorun
							+ " *********************************");
					System.out.println();

				} catch (Exception datasheetError) {
					System.out.println("Error in DataSheet ......\n");
					datasheetError.printStackTrace();
					System.exit(1);
				}
				/////////////
			}
		} catch (Exception maincontroller) {

			System.out.println("Connection Error in Maincontroller \n");
			maincontroller.printStackTrace();
			System.exit(1);
		}
	}

	public static void StartBrowser(final String homedir, String Browsername) throws Exception {
		Framework.browserToOpen = Browsername;
		System.out.println("Browser -------------------> " + Browsername);
		if (Framework.browsersts == 1
				&& (Browsername.toUpperCase().contains("NEW") || !Framework.oldbrowser.equalsIgnoreCase(Browsername))) {
			Framework.driver.quit();
			System.out.println("***************** Browser Closed **************************");
			Framework.browsersts = 0;
			Browsername = Browsername.toUpperCase();
			Browsername = Browsername.replace("NEW", "");
		}
		if (Framework.browsersts == 0) {
			Framework.osname = getosname();
			final String osarch = getosarch();
			System.out.println("OS      -------------------> " + osname);
			System.out.println("Architecture --------------> " + osarch);

			final String driverpath = homedir + "/Drivers/" + Framework.osname + "/" + Browsername + "/" + osarch;
			if (Browsername.equalsIgnoreCase("IE")) {
				if (Framework.osname.equalsIgnoreCase("Ubuntu")) {
					ShowWarning("IE Browser is not Supported in Linux");
				} else {
					try {
						Monitoring_FrameWork.Taskkill();
					} catch (Exception ex) {
					}
					final DesiredCapabilities caps = new DesiredCapabilities();
					caps.setCapability("ignoreProtectedModeSettings", true);
					// caps.setCapability("browserName", "internet explorer");
					caps.setCapability("acceptSslCerts", true);
					caps.setCapability("ignoreProtectedModeSettings", true);
					// caps.setCapability("version", "11");
					// caps.setCapability("platform", "WINDOWS");
					// caps.setCapability("ie.ensureCleanSession", true);
					caps.setCapability("unexpectedAlertBehaviour", (Object) UnexpectedAlertBehaviour.IGNORE);
					caps.setCapability("requireWindowFocus", true);
					caps.setCapability("enablePersistentHover", true);
					caps.setCapability("nativeEvents", true);
					caps.setCapability("ignoreZoomSetting", true);
					caps.setCapability("--remote-allow-origins=*", true);
					// after add
					caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

					System.setProperty("webdriver.ie.driver", driverpath + "/IEDriverServer.exe");
					// WebDriverManager.iedriver().setup();
					final InternetExplorerOptions options = new InternetExplorerOptions((Capabilities) caps);
					Framework.driver = (WebDriver) new InternetExplorerDriver(options);
					Framework.driver.manage().window().maximize();
					Framework.driver.manage().timeouts().implicitlyWait(90L, TimeUnit.SECONDS);
					Framework.browsersts = 1;
				}
			}
			if (Browsername.equalsIgnoreCase("FF")) {
				String path = driverpath + "/geckodriver.exe";

				if (Framework.osname.equalsIgnoreCase("Windows")) {
					path = driverpath + "/geckodriver.exe";

				} else {
					path = driverpath + "/geckodriver";
				}
				System.setProperty("webdriver.gecko.driver", path);
//				DesiredCapabilities caps2 = new DesiredCapabilities();
//				caps2.setCapability("browserName", "Firefox");
//				caps2.setCapability("firefoxOptions", "org.openqa.selenium.firefox.FirefoxOptions@1f22f3");
//				caps2.setCapability("moz:firefoxOptions", "org.openqa.selenium.firefox.FirefoxOptions@1f22f3");
//				caps2.setCapability("marionette", true);
//				caps2.setCapability("version", "91");
//				caps2.setCapability("platform", "WINDOWS");
				FirefoxOptions options1 = new FirefoxOptions();
				options1.setCapability("--profile", ffProfilePath);
				Framework.driver = (WebDriver) new FirefoxDriver(options1);
				Framework.driver.manage().timeouts().implicitlyWait(90L, TimeUnit.SECONDS);
				Framework.driver.manage().timeouts().pageLoadTimeout(90L, TimeUnit.SECONDS);
				Framework.driver.manage().window().maximize();
				Framework.browsersts = 1;
			}
			if (Browsername.equalsIgnoreCase("Chrome")) {
				String path = driverpath;
				if (Framework.osname.equalsIgnoreCase("Windows")) {
					path = driverpath + "/chromedriver.exe";
				} else {
					path = driverpath + "/chromedriver";
				}

				ChromeOptions options2 = new ChromeOptions();

				options2.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
				options2.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

				options2.addArguments("--disable-blink-features=AutomationControlled");// for cloud flarecaptcha
				options2.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
//				options2.addArguments("--ignore-certificate-errors");
				options2.addArguments("--test-type");
				options2.addArguments("--allow-running-insecure-content");
//				options2.addArguments("--disable-web-security");
				options2.addArguments("--allow-insecure-localhost");
				options2.addArguments(new String[] { "--disable-notifications" });
				options2.addArguments(new String[] { "--disable-extentions" });
				options2.addArguments(new String[] { "disable-infobars" });
				options2.addArguments(new String[] { "disable-captcha" });
				options2.addArguments(new String[] { "--remote-allow-origins=*" });
				options2.addArguments(new String[] { "--disable-popup-blocking" });
				options2.addArguments(
						"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.106 Safari/537.36");
				options2.addArguments(new String[] { "--no-sandbox" });
				options2.addArguments("--no-proxy-server");
//				options2.addArguments(new String[] { "window-size=1920,1080" });
//				 new added for disable password window popup
//				options2.addArguments(new String[] { "--disable-save-password-bubble" });
//				 options2.addArguments("Browser.setDownloadBehavior","allow");
				if (headless.equalsIgnoreCase("true")) {
					options2.addArguments(new String[] { "--headless" });
					options2.addArguments(new String[] { "window-size=1920,1080" });
				}
				if (fastPageLoad.equalsIgnoreCase("Y")) {
					options2.setPageLoadStrategy(PageLoadStrategy.EAGER);
				}else if (fastPageLoad.equalsIgnoreCase("None")) {
					options2.setPageLoadStrategy(PageLoadStrategy.NONE);
				}

				if (pageStability.equalsIgnoreCase("Y")) {
					System.out.println("pageStability  ==="+pageStability);
					options2.addArguments("--disable-dev-shm-usage");
				}
				// for location allow
				options2.addArguments("--disable-blink-features=AutomationControlled");
				options2.setExperimentalOption("prefs",
						Map.of("profile.default_content_setting_values.geolocation", 1));

				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false); // Disable credential saving(Password Saveing)
				prefs.put("profile.default_content_setting_values.automatic_downloads", 1);// Disable credential

				System.out.println("Default Download Set " + defaultDownload);
				if (defaultDownload.equalsIgnoreCase("N")) {
					System.out.println("Default Download Set " + defaultDownload);
					prefs.put("plugins.always_open_pdf_externally", true); // make Chrome download PDFs instead of
																			// opening them
					prefs.put("download.prompt_for_download", false);
					prefs.put("download.directory_upgrade", true);
				}

				options2.setExperimentalOption("prefs", prefs);

				File file = new File(path);
				file.setExecutable(true);
				System.setProperty("webdriver.chrome.driver", path);

				String startBrowserStatus = Framework.pro.getProperty("startBrowserStatus", "Y");
				if (startBrowserStatus.equalsIgnoreCase("Y")) {
					try {

						Framework.driver = new ChromeDriver(options2);
						driver.manage().window().maximize();
						try {
							Framework.driver.manage().timeouts()
									.pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(pageLoadTime)));
						} catch (Exception e) {
							Framework.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
//				*/

//				Framework.browsersts = 1;
			}

			if (Browsername.equalsIgnoreCase("axisChrome")) {

				String path = driverpath;
				if (Framework.osname.equalsIgnoreCase("Windows")) {
					path = driverpath + "/chromedriver.exe";
				} else {
					path = driverpath + "/chromedriver";
				}

//				options2.addArguments(
//						"--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.6045.123 Safari/537.36");

//				options2.setCapability("acceptInsecureCerts", false);
				File file = new File(path);
				file.setExecutable(true);
				System.setProperty("webdriver.chrome.driver", path);
				// WebDriverManager.chromedriver().setup();

			}

			if (Browsername.equalsIgnoreCase("Edge")) {

				String path = "";
				if (Framework.osname.equalsIgnoreCase("Windows")) {
					path = driverpath + "/MicrosoftWebDriver.exe";
				} else {
					path = driverpath + "/msedgedriver";
				}
				FileInputStream fis2 = new FileInputStream(Functions.path2);
				Properties prop = new Properties();
				prop.load(fis2);
				String headless = prop.getProperty("headless");

				System.setProperty("webdriver.edge.driver", path);
				EdgeOptions op = new EdgeOptions();
				op.addArguments("--remote-allow-origins=*");
				op.addArguments("--disable-dev-shm-usage"); // Optional: for stability
				op.addArguments("--no-sandbox"); // Optional: for security restrictions
				op.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				op.setExperimentalOption("useAutomationExtension", false);
				op.addArguments("--disable-blink-features=AutomationControlled");
				if (headless.equalsIgnoreCase("true")) {
					System.out.println("We are in Headless");
					op.addArguments(new String[] { "headless" });
				}
				Framework.driver = (WebDriver) new EdgeDriver(op);
				Framework.driver.manage().window().maximize();
				Framework.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				Framework.browsersts = 1;
			}
			if (Browsername.equalsIgnoreCase("Safari")) {

				// Safari does not require driver path
				// Just enable Safari driver in browser settings

				SafariOptions options = new SafariOptions();

				Framework.driver = new SafariDriver(options);
				Framework.driver.manage().window().maximize();
				Framework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				Framework.browsersts = 1;

			}
			if (Browsername.equalsIgnoreCase("chrome1")) {
				System.setProperty("webdriver.chrome.driver",
						"/home/apmosys/Music/MONT_ASA_WEB/Drivers/Ubuntu/Chrome/64/chromedriver");
				Framework.driver = (WebDriver) new ChromeDriver();
				final WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofMinutes(1L));
				Framework.driver.manage().window().maximize();
				Framework.driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5L));
				Framework.browsersts = 1;
			}
		} else {
			System.out.println(
					"**************************** " + Browsername + " Already Runnning ****************************");
		}
	}

	public static String getosname() {
		String osnamedump = System.getProperty("os.name");
		String osname = "Ubuntu";
		if (osnamedump.toUpperCase().contains("WIN")) {
			osname = "Windows";
		}
		return osname;
	}

	public static String getosarch() {
		final String amddummp = System.getProperty("os.arch");
		String amdtype = "32";
		if (amddummp.toUpperCase().contains("64")) {
			amdtype = "64";
		}
		return amdtype;
	}

	public static void ShowWarning(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public static String getBrowserVersion() {
		String line = "";
		try {
			String os = getosname();
			Process process;
			BufferedReader reader = null;

			if (os.contains("Windows")) {
				process = Runtime.getRuntime()
						.exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					if (line.toLowerCase().contains("version")) {
						int length = line.split(" ").length;
						line = line.split(" ")[length - 1];
						break;
					}
				}

			} else if (os.contains("mac")) {
				process = Runtime.getRuntime()
						.exec("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome --version");
			} else {
				// Linux command
				process = Runtime.getRuntime().exec("google-chrome --version");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				line = reader.readLine().split(" ")[2].trim();
			}

			System.out.println("Chrome Version: " + line);
			reader.close();
		} catch (Exception e) {
			System.out.println("Error while fetching Version");
			e.printStackTrace();
		}
		return line;
	}

	public static String TakeScreenshots() throws Exception {

//	    System.out.println("ScreenShot through Robot");

		// Capture full screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);

		Date now = new Date();

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int monthDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String monthName = new SimpleDateFormat("MMMM").format(now);

		// Create folder path
		String folderPath = Framework.homedir + File.separator + "Logs" + File.separator + year + File.separator
				+ monthName + File.separator + monthDay + File.separator + Framework.dataSheetName;

		File folder = new File(folderPath);
		folder.mkdirs();

		// Screenshot name
		String time = new SimpleDateFormat("HH_mm_ss").format(now);
		String screenshotName = Framework.pagename + "_" + time + ".jpg";

		String screenshotPath = folderPath + File.separator + screenshotName;

		// Save screenshot
		ImageIO.write(image, "jpg", new File(screenshotPath));

		// Compress image
		Monitoring_FrameWork.compressImage(new File(screenshotPath), new File(screenshotPath), 30 * 1024);
		Framework.ScreenshotfileLocation = screenshotPath;

		System.out.printf(" [INFO] ScreenshotfileLocation : %s %n", Framework.ScreenshotfileLocation);

		return screenshotPath;
	}

	public static void savetoextfile(String TC, String Actualresult, String Col) throws FilloException {
		try {
			if (!Col.equalsIgnoreCase("Write_Policy_NO") && !Col.equalsIgnoreCase("Write_Payment_ID")) {
				Framework.filloDs = new Fillo();
				Connection connectionextfile = Framework.filloDs
						.getConnection(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Framework.homedir))))
								+ "/Output/output.xls");
				String strQueryextfile = "select * from Sheet1 where TestCaseID = '" + TC + "'";
				int count = 0;
				try {
					Recordset recordset = connectionextfile.executeQuery(strQueryextfile);
					while (recordset.next()) {
						++count;
					}
				} catch (Exception e2) {
					System.out.println("Error in Select Data from - " + Framework.homedir + "/Output/output.xls");
				}
				if (count > 0) {
					strQueryextfile = "update Sheet1 set Proposal_No = '" + Actualresult + "',Time='"
							+ new Date().toString() + "' where TestCaseID = '" + TC + "'";
				} else {
					strQueryextfile = "insert into Sheet1 (TestCaseID,Proposal_No,Time) values ('" + TC + "','"
							+ Actualresult + "','" + new Date().toString() + "')";
				}
				connectionextfile.executeUpdate(strQueryextfile);
				connectionextfile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getProcessId() {
		// Get the process ID of the current Java process

		try {
			String processName = ManagementFactory.getRuntimeMXBean().getName();
			long processid = Long.parseLong(processName.split("@")[0]);

			System.out.println("ProcessId     -------------> " + processid);
			File folder = new File(System.getProperty("user.dir") + File.separator + "Logs");
			folder.mkdirs();
			File textFile = new File(folder + File.separator + "pid.txt");
			textFile.createNewFile();

			FileWriter writter = new FileWriter(textFile, false);
			BufferedWriter bf = new BufferedWriter(writter);
			bf.write(String.valueOf(processid));
			bf.close();
			writter.close();
			System.out.println("status        -------------> Processid Written in file successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void ReadDB() throws Exception {

		String driver = propertiesObj.getProperty("jdbc.driver");
		if (driver != null) {
			Class.forName(driver);
		}
		Framework.dburl = propertiesObj.getProperty("jdbc.url");
		Framework.dbuser = propertiesObj.getProperty("jdbc.username");
		Framework.dbpassword = propertiesObj.getProperty("jdbc.password");
//		Framework.userMobile = props.getProperty("userMobile");
	}

	static {
		Framework.FileNameV = null;
		Framework.pro = new Properties();

	}
}
