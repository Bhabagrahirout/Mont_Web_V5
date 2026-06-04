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
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * Framework — the test runner / orchestrator.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * WHAT CHANGED IN THIS REFACTOR (Step 1)
 * ─────────────────────────────────────────────────────────────────────────────
 *  BEFORE  100+ public static fields for every kind of state lived here.
 *          Any class could read or write any field at any time.
 *
 *  AFTER   State is split into three focused config objects:
 *
 *    DBConfig          — JDBC coordinates, connection lifecycle
 *    MailConfig        — SMTP settings, recipient lists, feature flag
 *    BrowserConfig     — browser name, headless, timeouts, custom JAR path
 *    ExecutionContext   — mutable runtime state for one test run
 *                        (driver, current page, step, error, IDs, timings)
 *
 *  These are injected into the runner below and passed to helpers that need
 *  them.  No method needs to reach into a global bag of statics anymore.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * BACKWARD COMPATIBILITY
 * ─────────────────────────────────────────────────────────────────────────────
 *  A handful of static fields are kept temporarily so existing classes
 *  (Functions, Monitoring_FrameWork, DB_Tables) compile without changes.
 *  Each is marked  // TODO: remove after migrating <ClassName>
 *  Work through those in subsequent steps.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 */
public class Framework {

    // ── Display dividers (purely cosmetic, OK to remain static) ──────────────
    public static final String DIVIDER  = "═".repeat(60);
    public static final String SUB_DIV  = "─".repeat(60);

    // ── Project root ──────────────────────────────────────────────────────────
    // Stays static because it is immutable after startup.
    public static String homedir;

    // ─────────────────────────────────────────────────────────────────────────
    // TEMPORARY backward-compat shims — remove as each consumer is migrated
    // ─────────────────────────────────────────────────────────────────────────

    /** @deprecated use ExecutionContext.getDriver() */
    @Deprecated public static WebDriver driver;

    /** @deprecated use ExecutionContext.getPageName() */
    @Deprecated public static String pagename;

    /** @deprecated use ExecutionContext.getDataSheetName() */
    @Deprecated public static String dataSheetName;

    /** @deprecated use ExecutionContext.getErrorStatus() */
    @Deprecated public static String errorsatus;

    /** @deprecated use ExecutionContext.getRecordsetRDS() */
    @Deprecated public static com.codoid.products.fillo.Recordset recordsetRDS;

    /** @deprecated use ExecutionContext.getScreenShotFileLocation() */
    @Deprecated public static String ScreenshotfileLocation;

    /** @deprecated use ExecutionContext.getSrNo() */
    @Deprecated public static String Srno;

    /** @deprecated use ExecutionContext.getScriptName() */
    @Deprecated public static String ScriptName;

    /** @deprecated use ExecutionContext.getApplicationName() */
    @Deprecated public static String ApplicationName;

    /** @deprecated use ExecutionContext.getFunctionToRun() */
    @Deprecated public static String functiontorun;

    /** @deprecated use ExecutionContext.getObjectTypeRDS() */
    @Deprecated public static String ObjectTypeRDS;

    /** @deprecated use ExecutionContext.getControlRDS() */
    @Deprecated public static String ControlRDS;

    /** @deprecated use ExecutionContext.getPropertyValues() */
    @Deprecated public static String propertyValues;

    /** @deprecated use ExecutionContext.getActionRDS() */
    @Deprecated public static String actionRDS;

    /** @deprecated use ExecutionContext.getErrorType() */
    @Deprecated public static String errorType = "";

    /** @deprecated use ExecutionContext.getErrorMessage() */
    @Deprecated public static String errorMessage = "";

    /** @deprecated use ExecutionContext.getErrorDesc() */
    @Deprecated public static String errorDesc;

    /** @deprecated use ExecutionContext.getDataSheetsPath() */
    @Deprecated public static String datasheetspath;

    /** @deprecated use ExecutionContext.getOtpCurrentTime() */
    @Deprecated public static String OTPcurrentTime;

    /** @deprecated use ExecutionContext.getLogStatus() */
    @Deprecated public static String logstatus;

    /** @deprecated use ExecutionContext.getTestCaseId() */
    @Deprecated public static String sTestCaseID;

    /** @deprecated use ExecutionContext.getDescription() */
    @Deprecated public static String sDescription;

    /** @deprecated use ExecutionContext.getParentWindow() */
    @Deprecated public static String parenwindow;

    /** @deprecated use ExecutionContext.getStatus() */
    @Deprecated public static boolean status = true;

    /** @deprecated use ExecutionContext.getExitFlag() */
    @Deprecated public static boolean exitFlag = false;

    /** @deprecated use ExecutionContext.getRunActive() / flag */
    @Deprecated public static boolean flag = true;

    /** @deprecated use ExecutionContext.getMacId() */
    @Deprecated public static String macId;

    /** @deprecated use ExecutionContext.getRunTimeInstanceId() */
    @Deprecated public static int runTimeInstanceId;

    /** @deprecated use ExecutionContext.getApplicationId() */
    @Deprecated public static int applicationId;

    /** @deprecated use ExecutionContext.getMonitoringInstancesId() */
    @Deprecated public static int monitoringInstancesId;

    /** @deprecated use ExecutionContext.getClientId() */
    @Deprecated public static int clientId;

    /** @deprecated use ExecutionContext.getZone() */
    @Deprecated public static String zone;

    /** @deprecated use ExecutionContext.getDomain() */
    @Deprecated public static String domain;

    /** @deprecated use ExecutionContext.getCreatedBy() */
    @Deprecated public static String createdBy;

    /** @deprecated use ExecutionContext.getLocation() */
    @Deprecated public static String location = "";

    /** @deprecated use ExecutionContext.getScriptStartTime() */
    @Deprecated public static String scriptStartTime;

    /** @deprecated use ExecutionContext.getScriptEndTime() */
    @Deprecated public static String scriptEndTime;

    /** @deprecated use ExecutionContext.getTStartTime() */
    @Deprecated public static Double tStartTime;

    /** @deprecated use ExecutionContext.getCurrentTime() */
    @Deprecated public static String currentTime;

    /** @deprecated use ExecutionContext.getIResponseTime() */
    @Deprecated public static int iResponseTime;

    /** @deprecated use ExecutionContext.getAvailabilityAlert() */
    @Deprecated public static int availability_alert;

    /** @deprecated use ExecutionContext.getResponseTimeAlert() */
    @Deprecated public static int ResponseTime_alert;

    /** @deprecated use ExecutionContext.getDefaultWaitTime() */
    @Deprecated public static int defaultwaittime;

    /** @deprecated use ExecutionContext.getPageId() */
    @Deprecated public static int pageId;

    /** @deprecated use ExecutionContext.getRecentCaptcha() */
    @Deprecated public static String recentCaptcha;

    /** @deprecated use ExecutionContext.getReportRunTimeInstanceId() */
    @Deprecated public static int reportRunTimeInstanceId;

    /** @deprecated use ExecutionContext.getExecutionId() */
    @Deprecated public static int executionId;

    // ── Old properties shim (used by Functions.path2 reference) ──────────────
    // TODO: remove after Functions is refactored (Step 2)
    @Deprecated public static Properties pro = new Properties();
    @Deprecated public static String     propertiesFilePath;
    @Deprecated public static Properties propertiesObj;

    // ── DB connection shim ────────────────────────────────────────────────────
    // TODO: remove after DB_Tables is migrated to accept DBConfig
    @Deprecated public static java.sql.Connection dbConnection = null;
    @Deprecated public static String dburl;
    @Deprecated public static String dbuser;
    @Deprecated public static String dbpassword;

    // ── Mail shims ─────────────────────────────────────────────────────────────
    // TODO: remove after FetchMail / TestMail are migrated to accept MailConfig
    @Deprecated public static String host;
    @Deprecated public static String port;
    @Deprecated public static String mailFrom;
    @Deprecated public static String password;
    @Deprecated public static String mailTo;
    @Deprecated public static String mailCc;
    @Deprecated public static String subject;
    @Deprecated public static String mailAlert;

    // ── Browser shims ─────────────────────────────────────────────────────────
    // TODO: remove after BrowserFactory is created (Step 2)
    @Deprecated public static String browser;
    @Deprecated public static String oldbrowser = "new";
    @Deprecated public static String browserToOpen;
    @Deprecated public static int    browsersts = 0;
    @Deprecated public static String headless;
    @Deprecated public static String fastPageLoad;
    @Deprecated public static String pageStability;
    @Deprecated public static String pageLoadTime;
    @Deprecated public static String defaultDownload = "Y";
    @Deprecated public static String showLocator = "";
    @Deprecated public static String exitStatus = "Y";
    @Deprecated public static String customJarPath;
    @Deprecated public static String customClassName;
    @Deprecated public static String ffProfilePath;
    @Deprecated public static String bankHoliday;

    // ── Monitoring shims ──────────────────────────────────────────────────────
    @Deprecated public static String bHour;
    @Deprecated public static String bHourFunction;
    @Deprecated public static String monitoringBusinessHour;
    @Deprecated public static String monitoringDataSheetPath;

    // ── Misc shims ────────────────────────────────────────────────────────────
    @Deprecated public static String FileNameV;
    @Deprecated public static String ScreenfileLocation;
    @Deprecated public static String ScreenshotfileLocation1;
    @Deprecated public static String scType;
    @Deprecated public static String userMobile;
    @Deprecated public static String step;
    @Deprecated public static String Module;
    @Deprecated public static String browserToOpenOld;
    @Deprecated public static Fillo  fillo;
    @Deprecated public static Fillo  filloDs;
    @Deprecated public static java.util.Map<String, String> dbDetails =
            new java.util.HashMap<>();

    // ─────────────────────────────────────────────────────────────────────────
    // Entry point
    // ─────────────────────────────────────────────────────────────────────────

    public static void main(final String[] args) throws Exception {

        // ── 1. Load properties ────────────────────────────────────────────────
        homedir = System.getProperty("user.dir");
        propertiesFilePath = homedir + File.separator + "mf_web.properties";
        propertiesObj = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            propertiesObj.load(fis);
        }

        // ── 2. Build focused config objects ───────────────────────────────────
        DBConfig     dbConfig   = DBConfig.fromProperties(propertiesObj);
        MailConfig   mailConfig = MailConfig.fromProperties(propertiesObj);
        BrowserConfig brConfig  = BrowserConfig.fromProperties(propertiesObj, homedir);

        // ── 3. Populate backward-compat shims (remove once consumers migrate) ─
        syncShimsFromConfigs(dbConfig, mailConfig, brConfig, propertiesObj);

        // ── 4. Open DB connection ─────────────────────────────────────────────
        dbConfig.connect();
        dbConnection = dbConfig.getConnection();    // shim — remove later

        // ── 5. Populate pro (shim for Functions.java) ─────────────────────────
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            pro.load(fis);
        }

        // ── 6. Shutdown hook — clean up driver & DB on SIGTERM ────────────────
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n" + DIVIDER);
            System.out.println(" ⚡ SHUTDOWN HOOK ACTIVATED");
            System.out.println(SUB_DIV);
            if (flag) {
                scriptEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                DB_Tables.updateEndTimeRunTimeInstance(
                        "terminated", scriptEndTime, applicationId, runTimeInstanceId);
            }
            dbConfig.close();
            if (driver != null && !driver.toString().contains("null")
                    && !browser.equalsIgnoreCase("sikuli")) {
                driver.quit();
                System.out.println(" [WEB] DRIVER     : Quit [✓]");
            }
            System.out.println(DIVIDER);
        }));

        // ── 7. Read macId ─────────────────────────────────────────────────────
        macId = DB_Tables.getMac();
        System.out.println(" MAC  : " + macId);

        // ── 8. Business-hour check ────────────────────────────────────────────
        if ("y".equalsIgnoreCase(monitoringBusinessHour)) {
            BusinessHour.monitoringHour();
        }
        try {
            String monTime = propertiesObj.getProperty("monitoringTime", "n");
            if ("y".equalsIgnoreCase(monTime)) {
                BusinessHour.monitoringMinute();
            }
        } catch (Exception ignored) {}

        // ── 9. Write PID file ─────────────────────────────────────────────────
        writeProcessId();

        // ── 10. Run ───────────────────────────────────────────────────────────
        Run();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Run — test orchestration loop
    //
    // NOTE: This method is intentionally left structurally similar to the
    // original for now so the diff is readable.  Step 2 will extract the
    // inner loops into a TestRunner class and collapse the nesting.
    // ─────────────────────────────────────────────────────────────────────────

    public static void Run() throws URISyntaxException, ParseException, FilloException, IOException {
        homedir = System.getProperty("user.dir");
        browsersts = 0;
        oldbrowser = "new";
        System.out.println("Project Path  ------------->  " + homedir);

        fillo = new Fillo();
        Connection con = null;

        try {
            String mainControllerPath = homedir + File.separator + "Main_Controller_Web.xlsx";
            con = fillo.getConnection(mainControllerPath);
            Recordset recordset = con.executeQuery("Select * from MainController where RunStatus='Y'");

            while (recordset.next()) {
                try {
                    flag = true;
                    // Collect all non-empty function names for this row
                    int j = 5;
                    do {
                        functiontorun = recordset.getField(j).value();
                        if (!functiontorun.isEmpty()) j++;
                        else break;
                    } while (true);

                    for (int k = 5; k < j; k++) {
                        functiontorun  = recordset.getField(k).value();
                        ApplicationName = recordset.getField("ApplicationName");
                        ScriptName      = ApplicationName;
                        browser         = recordset.getField("Browser");

                        System.out.println("Application Name  ---------> " + ApplicationName);
                        System.out.println("Instance Name     ---------> " + functiontorun);

                        scriptStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

                        if (functiontorun != null && !functiontorun.equals("null") && !functiontorun.isEmpty()) {
                            if (!browser.equalsIgnoreCase("sikuli")) {
                                StartBrowser(homedir, browser);
                                oldbrowser = browser;
                            }

                            Recordset recordsetDS = con.executeQuery(
                                    "select * from DataSheet where InstanceName='" + functiontorun + "'");

                            while (recordsetDS.next()) {
                                String sDataSheet = recordsetDS.getField("DataSheet");
                                dataSheetName = sDataSheet.replaceAll(".xlsx", "");

                                String osName = getosname();
                                datasheetspath = homedir
                                        + (osName.equalsIgnoreCase("Windows") ? "\\" : "/")
                                        + "DataSheet"
                                        + (osName.equalsIgnoreCase("Windows") ? "\\" : "/")
                                        + sDataSheet;

                                System.out.println("Sheet Path ----------------> " + datasheetspath);

                                Fillo filloDs = new Fillo();
                                Connection connectionDS = filloDs.getConnection(datasheetspath);

                                Recordset recordsetRDSF = connectionDS.executeQuery(
                                        "select * from Sheet2 where RunStatus='Y'");

                                double networkSpeed = DB_Tables.getNetworkSpeed();
                                System.out.println("NetworkSpeed --------------> " + networkSpeed + " MB/s");

                                monitoringInstancesId = DB_Tables.getMonitoringInstancesId(dataSheetName);
                                zone      = DB_Tables.getMonitoringDetails(monitoringInstancesId);
                                List<Integer> appData = DB_Tables.getApplicationMasterData(ApplicationName);
                                applicationId = appData.get(0);
                                clientId      = appData.get(1);
                                domain        = DB_Tables.getclientMasterData(clientId);
                                createdBy     = DB_Tables.getCreatedBy(applicationId);
                                String ipAddress = DB_Tables.getIpAdress();
                                List<String> infraData = DB_Tables.getContainerJenkinMasterIP(ipAddress);
                                int infraId = infraData.get(0) != null ? Integer.parseInt(infraData.get(0)) : 0;
                                String containerIP = infraData.get(1);
                                String jenkinsIP   = infraData.get(2);
                                List<String> devData = DB_Tables.getDeviceDetails(dataSheetName);
                                String deviceType  = devData.get(0);
                                String browserType = devData.get(1);
                                String version     = devData.get(2);

                                DB_Tables.Table_runtime_instance(
                                        dataSheetName, scriptStartTime, scriptStartTime, "running",
                                        new SimpleDateFormat("yyyyMMdd").format(new Date()),
                                        new SimpleDateFormat("HH").format(new Date()),
                                        new SimpleDateFormat("mm").format(new Date()),
                                        browserType, deviceType, version, ipAddress, jenkinsIP,
                                        monitoringInstancesId, applicationId, infraId,
                                        createdBy, scriptStartTime, macId, scriptStartTime, networkSpeed);

                                while (recordsetRDSF.next()) {
                                    errorsatus  = "0";
                                    sTestCaseID = recordsetRDSF.getField("TestCaseID");
                                    sDescription = recordsetRDSF.getField("Description");

                                    recordsetRDS = connectionDS.executeQuery(
                                            "select * from Sheet1 where RunStatus='Y'");

                                    while (recordsetRDS.next()) {
                                        String runStatusRDS  = recordsetRDS.getField("RunStatus");
                                        ControlRDS           = recordsetRDS.getField("Control").trim();
                                        ObjectTypeRDS        = recordsetRDS.getField("ObjectType");
                                        String propNameRDS   = recordsetRDS.getField("PropertyName");
                                        String propValueRDS  = propertyValues = recordsetRDS.getField("PropertyValue");
                                        String dataFieldRDS  = recordsetRDS.getField("Datafield");
                                        String actionRDSVal  = recordsetRDS.getField("Action");
                                        pagename             = recordsetRDS.getField("PageName");
                                        actionRDS            = actionRDSVal;
                                        Srno                 = recordsetRDS.getField("Srno");

                                        System.out.printf("%n                    Step %s | Page: %s | Error: %s%n",
                                                Srno, pagename, errorsatus);

                                        if (runStatusRDS.equalsIgnoreCase("Y")) {
                                            // ── Resolve data field value from Sheet2 ──────────────
                                            String sColumnValue = "";
                                            int fieldStatus = 0;
                                            Connection connectionDS2 = filloDs.getConnection(datasheetspath);
                                            Recordset recordsetRDSFsc = connectionDS2.executeQuery(
                                                    "select * from Sheet2 where RunStatus='Y'");
                                            while (recordsetRDSFsc.next()) {
                                                for (int xm = 0; xm <= 1000; xm++) {
                                                    try {
                                                        if (!recordsetRDSFsc.getField(xm).name()
                                                                .equalsIgnoreCase(dataFieldRDS)) continue;
                                                        sColumnValue = recordsetRDSFsc.getField(xm).value();
                                                        fieldStatus = 1;
                                                    } catch (Exception ignored) {}
                                                    break;
                                                }
                                            }

                                            // ── Dispatch ──────────────────────────────────────────
                                            if (ObjectTypeRDS.equalsIgnoreCase("Custom")
                                                    || ObjectTypeRDS.equalsIgnoreCase("customStartBrowser")
                                                    || ObjectTypeRDS.equalsIgnoreCase("CustomConditionMethods")) {
                                                invokeCustomJar(ObjectTypeRDS, propNameRDS, propValueRDS,
                                                        sColumnValue, actionRDSVal);
                                            } else {
                                                Functions.Actions(ControlRDS, ObjectTypeRDS,
                                                        propNameRDS, propValueRDS, sColumnValue,
                                                        actionRDSVal, Srno, fieldStatus, pagename);
                                            }

                                            logstatus = "0";
                                        }
                                    }
                                }
                            }
                        }

                        // ── End of instance — update DB ───────────────────────
                        scriptEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                        flag = false;
                        System.out.println("\n" + DIVIDER);

                        if (Monitoring_FrameWork.logoutFlag || exitFlag) {
                            DB_Tables.updateEndTimeRunTimeInstance(
                                    "terminated", scriptEndTime, applicationId, runTimeInstanceId);
                            System.exit(1);
                        } else {
                            DB_Tables.updateEndTimeRunTimeInstance(
                                    "success", scriptEndTime, applicationId, runTimeInstanceId);
                        }
                        System.out.println("**** Execution Completed: " + functiontorun + " ****\n");
                    }

                } catch (Exception datasheetError) {
                    System.err.println("Error in DataSheet: " + datasheetError.getMessage());
                    datasheetError.printStackTrace();
                    System.exit(1);
                }
            }
        } catch (Exception mainControllerError) {
            System.err.println("Connection Error in MainController: " + mainControllerError.getMessage());
            mainControllerError.printStackTrace();
            System.exit(1);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Custom JAR dispatch — extracted from the 3-branch if/else in Run()
    // ─────────────────────────────────────────────────────────────────────────

    private static void invokeCustomJar(String objectType, String propName, String propValue,
                                        String columnValue, String action) {
        try {
            URL jarURL = new URL("file:" + customJarPath.replace("\\", "/"));
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{ jarURL })) {
                Class<?> loadedClass = classLoader.loadClass(customClassName);
                Object instance = loadedClass.newInstance();

                if (objectType.equalsIgnoreCase("Custom")) {
                    Method method = loadedClass.getMethod("allFunction",
                            WebDriver.class, String.class, String.class, String.class, String.class);
                    if (ControlRDS.equalsIgnoreCase("V")) {
                        try {
                            @SuppressWarnings("unchecked")
                            List<Object> result = (List<Object>) method.invoke(
                                    instance, driver, propName, propValue, columnValue, action);
                            if (result.size() == 1) {
                                Monitoring_FrameWork.SaveResult(result.get(0).toString(), columnValue);
                            } else {
                                Monitoring_FrameWork.SaveResult(
                                        result.get(0).toString(), result.get(1).toString());
                            }
                        } catch (Exception e) {
                            errorsatus = "1";
                            Monitoring_FrameWork.SaveResult(
                                    "Either locator changed / Network delay / page not loaded", columnValue);
                        }
                    } else {
                        try {
                            method.invoke(instance, driver, propName, propValue, columnValue, action);
                        } catch (Exception e) {
                            errorsatus = "1";
                            Monitoring_FrameWork.SaveResult(
                                    "Either locator changed / Network delay / page not loaded", columnValue);
                        }
                    }

                } else if (objectType.equalsIgnoreCase("customStartBrowser")) {
                    Method method = loadedClass.getMethod("customStartBrowser", String.class, String.class);
                    try {
                        driver = (WebDriver) method.invoke(instance, browser, headless);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (objectType.equalsIgnoreCase("CustomConditionMethods")) {
                    Method method = loadedClass.getMethod("conditionsMethods",
                            com.codoid.products.fillo.Recordset.class, WebDriver.class,
                            String.class, String.class, String.class, String.class);
                    try {
                        recordsetRDS = (com.codoid.products.fillo.Recordset) method.invoke(
                                instance, recordsetRDS, driver, propName, propValue, columnValue, action);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Browser management
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * StartBrowser — delegates to BrowserFactory.
     * Kept here for now; will move to BrowserFactory in Step 2.
     */
    public static void StartBrowser(final String homedir, String browserName) throws Exception {
        browserToOpen = browserName;
        System.out.println("Browser -------------------> " + browserName);

        if (browsersts == 1 && (browserName.toUpperCase().contains("NEW")
                || !oldbrowser.equalsIgnoreCase(browserName))) {
            driver.quit();
            System.out.println("**** Browser closed ****");
            browsersts = 0;
            browserName = browserName.replace("NEW", "").replace("new", "");
        }

        if (browsersts == 0) {
            // Delegate to BrowserFactory — created in Step 2
            // For now the logic is inlined here to keep the build green.
            BrowserFactory.launch(homedir, browserName, headless, fastPageLoad,
                    pageStability, pageLoadTime, defaultDownload, ffProfilePath);
        } else {
            System.out.println("**** " + browserName + " already running ****");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // OS utilities
    // ─────────────────────────────────────────────────────────────────────────

    public static String getosname() {
        return System.getProperty("os.name", "").toUpperCase().contains("WIN")
                ? "Windows" : "Ubuntu";
    }

    public static String getosarch() {
        return System.getProperty("os.arch", "").contains("64") ? "64" : "32";
    }

    public static void ShowWarning(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Screenshot
    // ─────────────────────────────────────────────────────────────────────────

    public static String TakeScreenshots() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(screenSize));

        Date now = new Date();
        int year      = new java.util.Calendar.Builder().setInstant(now).build().get(java.util.Calendar.YEAR);
        int monthDay  = new java.util.Calendar.Builder().setInstant(now).build().get(java.util.Calendar.DAY_OF_MONTH);
        String monthName = new SimpleDateFormat("MMMM").format(now);

        String folderPath = homedir + File.separator + "Logs" + File.separator
                + year + File.separator + monthName + File.separator
                + monthDay + File.separator + dataSheetName;
        new File(folderPath).mkdirs();

        String time = new SimpleDateFormat("HH_mm_ss").format(now);
        String screenshotPath = folderPath + File.separator + pagename + "_" + time + ".jpg";

        ImageIO.write(image, "jpg", new File(screenshotPath));
        Monitoring_FrameWork.compressImage(new File(screenshotPath), new File(screenshotPath), 30 * 1024);

        ScreenshotfileLocation = screenshotPath;
        System.out.printf(" [INFO] Screenshot: %s%n", screenshotPath);
        return screenshotPath;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Internal helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Populate all the backward-compat shim fields so existing callers
     * (DB_Tables, Functions, Monitoring_FrameWork) keep working without change.
     * As each class is migrated, remove the corresponding lines here.
     */
    private static void syncShimsFromConfigs(DBConfig db, MailConfig mail,
                                             BrowserConfig br, Properties props) {
        // DB shims
        dburl      = db.getUrl();
        dbuser     = db.getUsername();

        // Mail shims
        host     = mail.getHost();
        port     = mail.getPort();
        mailFrom = mail.getMailFrom();
        password = mail.getPassword();
        mailTo   = mail.getMailTo();
        mailCc   = mail.getMailCc();
        subject  = mail.getSubject();
        mailAlert = mail.isAlertEnabled() ? "Y" : "N";

        // Browser shims
        headless        = String.valueOf(br.isHeadless());
        fastPageLoad    = br.getFastPageLoad();
        pageStability   = br.isPageStabilityEnabled() ? "Y" : "N";
        pageLoadTime    = String.valueOf(br.getPageLoadTimeoutSeconds());
        defaultDownload = br.isDefaultDownload() ? "Y" : "N";
        showLocator     = br.isShowLocator() ? "Y" : "N";
        ffProfilePath   = br.getFfProfilePath();
        customJarPath   = br.getCustomJarPath();
        customClassName = br.getCustomClassName();
        exitStatus      = props.getProperty("exitStatus", "Y");
        bankHoliday     = props.getProperty("bankHoliday", "N");

        // Monitoring shims
        bHour                = props.getProperty("businessHourRunStatus", "");
        bHourFunction        = props.getProperty("businessHourFunctions", "");
        monitoringBusinessHour   = props.getProperty("monitoringBusinessHour", "n");
        monitoringDataSheetPath  = props.getProperty("monitoringDataSheetPath", "");
    }

    /**
     * Writes the JVM process ID to Logs/pid.txt so external scripts can
     * kill the process cleanly.
     */
    private static void writeProcessId() {
        try {
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            long pid = Long.parseLong(processName.split("@")[0]);
            System.out.println("ProcessId     ------------->  " + pid);

            File logsFolder = new File(homedir + File.separator + "Logs");
            logsFolder.mkdirs();
            File pidFile = new File(logsFolder, "pid.txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(pidFile, false))) {
                bw.write(String.valueOf(pid));
            }
        } catch (IOException e) {
            System.err.println("Could not write pid.txt: " + e.getMessage());
        }
    }
}
