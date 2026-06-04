package com.apmosys.framework;

import org.openqa.selenium.WebDriver;

import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * ExecutionContext — mutable runtime state for a single test run.
 *
 * This replaces the 50+ public static fields on Framework that described
 * "what is currently executing".  Instead of global state, a single context
 * object is created per run and passed (or held by the runner).
 *
 * Fields are grouped by concern:
 *   - Driver / browser
 *   - Execution identity (app, instance, script)
 *   - Current step state (page, step, error)
 *   - DB / infra IDs
 *   - Timing
 *   - Reporting
 *   - Data access
 *
 * Backward compatibility note:
 *   During migration Framework still holds these as static fields.
 *   Each time you move a consumer class to use ExecutionContext, remove
 *   the corresponding static field from Framework.
 */
public class ExecutionContext {

    // ─────────────────────────────────────────────────────────────────────────
    // Driver / browser
    // ─────────────────────────────────────────────────────────────────────────

    private WebDriver driver;

    // ─────────────────────────────────────────────────────────────────────────
    // Execution identity
    // ─────────────────────────────────────────────────────────────────────────

    private String applicationName;
    private String scriptName;
    private String functionToRun;    // current instance name from MainController
    private String dataSheetName;    // e.g. "Axis_CINB_Login_Canada"
    private String dataSheetsPath;   // full path to the Excel datasheet

    // ─────────────────────────────────────────────────────────────────────────
    // Current step state (changes row-by-row during execution)
    // ─────────────────────────────────────────────────────────────────────────

    private String srNo;
    private String module;
    private String pageName;
    private String controlRDS;
    private String objectTypeRDS;
    private String propertyValues;
    private String actionRDS;
    private String step;

    // Error state for the current test case
    private String errorStatus   = "0";   // "0" = ok, "1" = error
    private String errorType     = "";
    private String errorMessage  = "";
    private String errorDesc     = "";
    private String errorPageName = "";

    // Test case identifiers
    private String testCaseId;
    private String description;

    // ─────────────────────────────────────────────────────────────────────────
    // DB / infra IDs (populated from DB_Tables at start of each instance)
    // ─────────────────────────────────────────────────────────────────────────

    private int  monitoringInstancesId;
    private int  applicationId;
    private int  clientId;
    private int  pageId;
    private int  runTimeInstanceId;
    private int  reportRunTimeInstanceId;
    private int  executionId;
    private String zone;
    private String domain;
    private String createdBy;
    private String macId;
    private String location = "";

    // ─────────────────────────────────────────────────────────────────────────
    // Timing
    // ─────────────────────────────────────────────────────────────────────────

    private String scriptStartTime;
    private String scriptEndTime;
    private String currentTime;
    private String otpCurrentTime;
    private Double tStartTime;
    private int    iResponseTime;
    private int    availabilityAlert;
    private int    responseTimeAlert;
    private int    defaultWaitTime;

    // ─────────────────────────────────────────────────────────────────────────
    // Reporting
    // ─────────────────────────────────────────────────────────────────────────

    private ExtentTest    extentTest;
    private ExtentReports extentReports;
    private String logStatus  = "0";
    private String screenShotFileLocation;
    private String screenFileLocation;

    // ─────────────────────────────────────────────────────────────────────────
    // Data access helpers
    // ─────────────────────────────────────────────────────────────────────────

    private Fillo     fillo;
    private Fillo     filloDs;
    private Recordset recordsetRDS;

    // ─────────────────────────────────────────────────────────────────────────
    // Lifecycle flags
    // ─────────────────────────────────────────────────────────────────────────

    /** true while this run is active — used by shutdown hook. */
    private boolean runActive = true;

    /**
     * true if execution should stop (logout detected or exitStatus triggered).
     * Was Framework.exitFlag.
     */
    private boolean exitFlag = false;

    /** Was Framework.status */
    private boolean status = true;

    // ─────────────────────────────────────────────────────────────────────────
    // Misc
    // ─────────────────────────────────────────────────────────────────────────

    private String recentCaptcha;
    private String parentWindow;
    private String scType;
    private String userMobile;
    private String fileNameV;

    // ─────────────────────────────────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────────────────────────────────

    public ExecutionContext() {
        // intentionally empty — fields set by runner during execution
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Convenience reset — call at the start of each test case row
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Resets per-step state.  Call this at the start of each row read
     * from Sheet1 to avoid stale error state leaking between steps.
     */
    public void resetStepState() {
        errorStatus  = "0";
        errorType    = "";
        errorMessage = "";
        errorDesc    = "";
        logStatus    = "0";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Getters and setters
    // ─────────────────────────────────────────────────────────────────────────

    public WebDriver getDriver()                    { return driver; }
    public void      setDriver(WebDriver d)         { this.driver = d; }

    public String getApplicationName()              { return applicationName; }
    public void   setApplicationName(String v)      { this.applicationName = v; }

    public String getScriptName()                   { return scriptName; }
    public void   setScriptName(String v)           { this.scriptName = v; }

    public String getFunctionToRun()                { return functionToRun; }
    public void   setFunctionToRun(String v)        { this.functionToRun = v; }

    public String getDataSheetName()                { return dataSheetName; }
    public void   setDataSheetName(String v)        { this.dataSheetName = v; }

    public String getDataSheetsPath()               { return dataSheetsPath; }
    public void   setDataSheetsPath(String v)       { this.dataSheetsPath = v; }

    public String getSrNo()                         { return srNo; }
    public void   setSrNo(String v)                 { this.srNo = v; }

    public String getModule()                       { return module; }
    public void   setModule(String v)               { this.module = v; }

    public String getPageName()                     { return pageName; }
    public void   setPageName(String v)             { this.pageName = v; }

    public String getControlRDS()                   { return controlRDS; }
    public void   setControlRDS(String v)           { this.controlRDS = v; }

    public String getObjectTypeRDS()                { return objectTypeRDS; }
    public void   setObjectTypeRDS(String v)        { this.objectTypeRDS = v; }

    public String getPropertyValues()               { return propertyValues; }
    public void   setPropertyValues(String v)       { this.propertyValues = v; }

    public String getActionRDS()                    { return actionRDS; }
    public void   setActionRDS(String v)            { this.actionRDS = v; }

    public String getStep()                         { return step; }
    public void   setStep(String v)                 { this.step = v; }

    public String getErrorStatus()                  { return errorStatus; }
    public void   setErrorStatus(String v)          { this.errorStatus = v; }

    public String getErrorType()                    { return errorType; }
    public void   setErrorType(String v)            { this.errorType = v; }

    public String getErrorMessage()                 { return errorMessage; }
    public void   setErrorMessage(String v)         { this.errorMessage = v; }

    public String getErrorDesc()                    { return errorDesc; }
    public void   setErrorDesc(String v)            { this.errorDesc = v; }

    public String getErrorPageName()                { return errorPageName; }
    public void   setErrorPageName(String v)        { this.errorPageName = v; }

    public String getTestCaseId()                   { return testCaseId; }
    public void   setTestCaseId(String v)           { this.testCaseId = v; }

    public String getDescription()                  { return description; }
    public void   setDescription(String v)          { this.description = v; }

    public int  getMonitoringInstancesId()          { return monitoringInstancesId; }
    public void setMonitoringInstancesId(int v)     { this.monitoringInstancesId = v; }

    public int  getApplicationId()                  { return applicationId; }
    public void setApplicationId(int v)             { this.applicationId = v; }

    public int  getClientId()                       { return clientId; }
    public void setClientId(int v)                  { this.clientId = v; }

    public int  getPageId()                         { return pageId; }
    public void setPageId(int v)                    { this.pageId = v; }

    public int  getRunTimeInstanceId()              { return runTimeInstanceId; }
    public void setRunTimeInstanceId(int v)         { this.runTimeInstanceId = v; }

    public int  getReportRunTimeInstanceId()        { return reportRunTimeInstanceId; }
    public void setReportRunTimeInstanceId(int v)   { this.reportRunTimeInstanceId = v; }

    public int  getExecutionId()                    { return executionId; }
    public void setExecutionId(int v)               { this.executionId = v; }

    public String getZone()                         { return zone; }
    public void   setZone(String v)                 { this.zone = v; }

    public String getDomain()                       { return domain; }
    public void   setDomain(String v)               { this.domain = v; }

    public String getCreatedBy()                    { return createdBy; }
    public void   setCreatedBy(String v)            { this.createdBy = v; }

    public String getMacId()                        { return macId; }
    public void   setMacId(String v)                { this.macId = v; }

    public String getLocation()                     { return location; }
    public void   setLocation(String v)             { this.location = v; }

    public String getScriptStartTime()              { return scriptStartTime; }
    public void   setScriptStartTime(String v)      { this.scriptStartTime = v; }

    public String getScriptEndTime()                { return scriptEndTime; }
    public void   setScriptEndTime(String v)        { this.scriptEndTime = v; }

    public String getCurrentTime()                  { return currentTime; }
    public void   setCurrentTime(String v)          { this.currentTime = v; }

    public String getOtpCurrentTime()               { return otpCurrentTime; }
    public void   setOtpCurrentTime(String v)       { this.otpCurrentTime = v; }

    public Double getTStartTime()                   { return tStartTime; }
    public void   setTStartTime(Double v)           { this.tStartTime = v; }

    public int  getIResponseTime()                  { return iResponseTime; }
    public void setIResponseTime(int v)             { this.iResponseTime = v; }

    public int  getAvailabilityAlert()              { return availabilityAlert; }
    public void setAvailabilityAlert(int v)         { this.availabilityAlert = v; }

    public int  getResponseTimeAlert()              { return responseTimeAlert; }
    public void setResponseTimeAlert(int v)         { this.responseTimeAlert = v; }

    public int  getDefaultWaitTime()                { return defaultWaitTime; }
    public void setDefaultWaitTime(int v)           { this.defaultWaitTime = v; }

    public ExtentTest    getExtentTest()            { return extentTest; }
    public void          setExtentTest(ExtentTest v){ this.extentTest = v; }

    public ExtentReports getExtentReports()         { return extentReports; }
    public void setExtentReports(ExtentReports v)   { this.extentReports = v; }

    public String getLogStatus()                    { return logStatus; }
    public void   setLogStatus(String v)            { this.logStatus = v; }

    public String getScreenShotFileLocation()       { return screenShotFileLocation; }
    public void   setScreenShotFileLocation(String v){ this.screenShotFileLocation = v; }

    public String getScreenFileLocation()           { return screenFileLocation; }
    public void   setScreenFileLocation(String v)   { this.screenFileLocation = v; }

    public Fillo     getFillo()                     { return fillo; }
    public void      setFillo(Fillo v)              { this.fillo = v; }

    public Fillo     getFilloDs()                   { return filloDs; }
    public void      setFilloDs(Fillo v)            { this.filloDs = v; }

    public Recordset getRecordsetRDS()              { return recordsetRDS; }
    public void      setRecordsetRDS(Recordset v)   { this.recordsetRDS = v; }

    public boolean isRunActive()                    { return runActive; }
    public void    setRunActive(boolean v)          { this.runActive = v; }

    public boolean isExitFlag()                     { return exitFlag; }
    public void    setExitFlag(boolean v)           { this.exitFlag = v; }

    public boolean isStatus()                       { return status; }
    public void    setStatus(boolean v)             { this.status = v; }

    public String getRecentCaptcha()                { return recentCaptcha; }
    public void   setRecentCaptcha(String v)        { this.recentCaptcha = v; }

    public String getParentWindow()                 { return parentWindow; }
    public void   setParentWindow(String v)         { this.parentWindow = v; }

    public String getScType()                       { return scType; }
    public void   setScType(String v)               { this.scType = v; }

    public String getUserMobile()                   { return userMobile; }
    public void   setUserMobile(String v)           { this.userMobile = v; }

    public String getFileNameV()                    { return fileNameV; }
    public void   setFileNameV(String v)            { this.fileNameV = v; }
}
