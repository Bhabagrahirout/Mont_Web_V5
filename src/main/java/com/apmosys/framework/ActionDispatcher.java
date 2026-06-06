package com.apmosys.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * ActionDispatcher — routes action strings to the right action class.
 *
 * This replaces the monolithic Functions.Actions() method which was a
 * single 7900-line method with 200+ if/else branches.
 *
 * HOW IT WORKS
 * ─────────────
 * 1. Receives the same parameters as the original Actions() method.
 * 2. Resolves the WebElement via ElementLocator.
 * 3. Routes to one of the focused action classes:
 *      BrowserActions      — click, type, scroll, navigate, frames, windows
 *      WaitActions         — explicit waits, conditions
 *      AlertActions        — alerts, popups, security questions
 *      VerifyActions       — visibility, text, URL, download checks
 *      DropdownDateActions — dropdowns, date pickers, DOB
 *      OtpActions          — OTP reading (SMS, mail, API)
 *      NewCustomFunctions  — bank-specific delegations (unchanged)
 *      CustomFunctions     — bank-specific delegations (unchanged)
 *
 * BACKWARD COMPATIBILITY
 * ──────────────────────
 * Functions.Actions() now delegates here.  All existing callers continue
 * to compile and behave identically.  The internal routing is cleaner.
 *
 * ADDING NEW ACTIONS
 * ──────────────────
 * Old way: add an else-if branch anywhere in 8000 lines.
 * New way: add a method to the relevant action class, then add a one-liner
 *          else-if case in the correct section below.  The section headers
 *          (BROWSER, WAIT, VERIFY …) make it obvious where it belongs.
 */
public class ActionDispatcher {

    private static final Logger log = LoggerFactory.getLogger(ActionDispatcher.class);


    private ActionDispatcher() {}

    /**
     * Main dispatch entry point — mirrors the original Functions.Actions() signature.
     */
    public static void dispatch(String controlRDS, String objectTypeRDS,
                                String propertyNameRDS, String propertyValueRDS,
                                String dataFieldRDS, String actionRDS,
                                String srNo, int fieldStatus, String pageName)
            throws Exception {

        WebDriver driver = Framework.driver;

        System.out.println("[Dispatch] Control=" + controlRDS
                + " | ObjectType=" + objectTypeRDS
                + " | Action=" + actionRDS
                + " | Page=" + pageName);

        // ── Resolve element (skipped for non-element actions) ─────────────────
        WebElement el = null;
        boolean needsElement = !isNonElementAction(actionRDS);
        if (needsElement && propertyNameRDS != null && !propertyNameRDS.isBlank()) {
            el = ElementLocator.find(driver, propertyNameRDS, propertyValueRDS);
        }

        // ── Route by action string ────────────────────────────────────────────

        String action = actionRDS.trim();

        // ── WAIT ──────────────────────────────────────────────────────────────
        if (action.equalsIgnoreCase("ExplicitWaits")) {
            WaitActions.forCondition(driver, propertyNameRDS, propertyValueRDS,
                    dataFieldRDS, srNo, pageName);

        } else if (action.equalsIgnoreCase("ExplisitWait") || action.equalsIgnoreCase("WAITUNTIL")) {
            int secs = parseInt(objectTypeRDS, 10);
            WaitActions.sleep(secs);

        } else if (action.equalsIgnoreCase("WAITVISIBILITY")) {
            WaitActions.forVisible(driver, ElementLocator.toBy(propertyNameRDS, propertyValueRDS));

        } else if (action.equalsIgnoreCase("WAITINVISIBILITY")) {
            WaitActions.forInvisible(driver, ElementLocator.toBy(propertyNameRDS, propertyValueRDS));

        } else if (action.equalsIgnoreCase("WAITUNTILinvisible")) {
            WaitActions.forInvisible(driver, ElementLocator.toBy(propertyNameRDS, propertyValueRDS),
                    parseInt(objectTypeRDS, 30));

        } else if (action.toUpperCase().contains("WAIT(")) {
            int secs = parseParenInt(action, 5);
            WaitActions.sleep(secs);

        // ── NAVIGATE ─────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("BROWSEURL") || action.equalsIgnoreCase("AxisOpenUrl")
                || action.equalsIgnoreCase("navigateTo") || action.equalsIgnoreCase("navigateUrl")
                || action.equalsIgnoreCase("openBandhanurl")) {
            BrowserActions.browseUrl(driver, dataFieldRDS.isBlank() ? propertyValueRDS : dataFieldRDS);

        } else if (action.equalsIgnoreCase("BROWSEBACK") || action.equalsIgnoreCase("actionBack")
                || action.equalsIgnoreCase("Rb_NavigateBack")) {
            BrowserActions.browseBack(driver);

        } else if (action.equalsIgnoreCase("jsBack") || action.equalsIgnoreCase("jsBack2")) {
            BrowserActions.jsBack(driver);

        } else if (action.equalsIgnoreCase("REFRESH") || action.equalsIgnoreCase("Refresh")) {
            BrowserActions.refresh(driver);

        } else if (action.equalsIgnoreCase("deleteAllCookies")) {
            BrowserActions.deleteAllCookies(driver);

        } else if (action.equalsIgnoreCase("getCurrentUrl") || action.equalsIgnoreCase("checkUrl")) {
            String url = BrowserActions.getCurrentUrl(driver);
            Monitoring_FrameWork.SaveResult(url, dataFieldRDS);

        // ── CLICK ────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("CLICK") || action.equalsIgnoreCase("verify_click")
                || action.equalsIgnoreCase("CLASS_CLICK")) {
            if (el != null) BrowserActions.click(el);

        } else if (action.equalsIgnoreCase("DOUBLECLICK")) {
            if (el != null) BrowserActions.doubleClick(driver, el);

        } else if (action.equalsIgnoreCase("JAVASCRIPTCLICK") || action.equalsIgnoreCase("Js_click")
                || action.equalsIgnoreCase("executor_click") || action.equalsIgnoreCase("clickJQuery")) {
            if (el != null) BrowserActions.clickJs(driver, el);

        } else if (action.equalsIgnoreCase("ActionClick")) {
            if (el != null) BrowserActions.actionClick(driver, el);

        } else if (action.equalsIgnoreCase("RobotClick") || action.equalsIgnoreCase("RBClick")) {
            if (el != null) BrowserActions.robotClick(driver, el);

        } else if (action.equalsIgnoreCase("ClickOnCoordinate")) {
            String[] xy = propertyValueRDS.split(",");
            BrowserActions.clickOnCoordinate(parseInt(xy[0].trim(), 0), parseInt(xy[1].trim(), 0));

        } else if (action.equalsIgnoreCase("clickAtEndOfElement")) {
            if (el != null) BrowserActions.clickAtEndOfElement(driver, el);

        // ── SENDKEYS / TYPE ───────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("page") || action.equalsIgnoreCase("ActionSendkeys")
                || action.equalsIgnoreCase("normalSendKeys") || action.equalsIgnoreCase("sendValue1")) {
            if (el != null) BrowserActions.sendKeys(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("SENDKEYS") || action.equalsIgnoreCase("sendkeysUsingxpath")) {
            if (el != null) BrowserActions.sendKeys(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("js_sendKeys") || action.equalsIgnoreCase("SendValue")
                || action.equalsIgnoreCase("hdfcInvestorSendkeys")) {
            if (el != null) BrowserActions.sendKeysJs(driver, el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("SendInnerHtml")) {
            if (el != null) BrowserActions.sendInnerHtml(driver, el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("ActionSendkeys")) {
            if (el != null) BrowserActions.actionSendKeys(driver, el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("movetosendkeys")) {
            if (el != null) BrowserActions.moveToSendKeys(driver, el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("sendKeysCopyPaste") || action.equalsIgnoreCase("Clipboard")) {
            if (el != null) BrowserActions.sendKeysCopyPaste(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("sendkeysrobot") || action.equalsIgnoreCase("RobotSendkeys")) {
            if (el != null) BrowserActions.sendKeysRobot(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("NumericSendkeys")) {
            if (el != null) BrowserActions.numericSendKeys(el, dataFieldRDS);

        // ── CLEAR ─────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("CLEAR")) {
            if (el != null) BrowserActions.clear(el);

        } else if (action.equalsIgnoreCase("clearAndSendValue")) {
            if (el != null) BrowserActions.clearAndSendValue(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("CLEAR_USING_ACTION") || action.equalsIgnoreCase("CLEAR_USING_ACTION1")) {
            if (el != null) BrowserActions.clearUsingAction(driver, el);

        } else if (action.equalsIgnoreCase("CLEAR_USING_ROBOT")) {
            if (el != null) BrowserActions.clearUsingRobot(el);

        // ── KEYBOARD ─────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("TAB")) {
            if (el != null) BrowserActions.pressTab(el);

        } else if (action.equalsIgnoreCase("RB_ENTER") || action.equalsIgnoreCase("RB_ENTER1")) {
            BrowserActions.robotEnter();

        } else if (action.equalsIgnoreCase("RB_ESCAPE")) {
            BrowserActions.robotEscape();

        } else if (action.equalsIgnoreCase("RB_TAB") || action.equalsIgnoreCase("RB_SHIFTTAB")) {
            BrowserActions.robotTab();

        } else if (action.equalsIgnoreCase("RB_PAGEDOWN") || action.equalsIgnoreCase("ROBOT_PAGEDOWN")) {
            BrowserActions.robotPageDown();

        } else if (action.equalsIgnoreCase("RB_PAGEUP")) {
            BrowserActions.robotPageUp();

        } else if (action.equalsIgnoreCase("RB_BACK") || action.equalsIgnoreCase("RB_BackSpace")) {
            BrowserActions.robotBack(driver);

        } else if (action.equalsIgnoreCase("CLOSEF") || action.equalsIgnoreCase("RB_ALT")) {
            if (el != null) BrowserActions.pressF4(el);

        // ── SCROLL ────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("scrollIntoView") || action.equalsIgnoreCase("scrollIntoViewOTP")) {
            if (el != null) BrowserActions.scrollIntoView(driver, el);

        } else if (action.equalsIgnoreCase("ScrollDown") || action.equalsIgnoreCase("Scroll")
                || action.equalsIgnoreCase("Scrollsbi")) {
            int px = parseInt(objectTypeRDS, 300);
            BrowserActions.scrollDown(driver, px);

        } else if (action.equalsIgnoreCase("ScrollUp") || action.equalsIgnoreCase("Scrollupsbi")
                || action.equalsIgnoreCase("scrollUpTo")) {
            int px = parseInt(objectTypeRDS, 300);
            BrowserActions.scrollUp(driver, px);

        } else if (action.equalsIgnoreCase("scrollElement")) {
            if (el != null) BrowserActions.scrollElement(driver, el, parseInt(objectTypeRDS, 200));

        } else if (action.equalsIgnoreCase("scrollElementToLeft")) {
            if (el != null) BrowserActions.scrollElementToLeft(driver, el, parseInt(objectTypeRDS, 200));

        } else if (action.equalsIgnoreCase("ScrollRight")) {
            if (el != null) BrowserActions.scrollRight(driver, el, parseInt(objectTypeRDS, 200));

        } else if (action.equalsIgnoreCase("MOUSEOVER")) {
            if (el != null) BrowserActions.moveToElement(driver, el);

        } else if (action.equalsIgnoreCase("DragAndDrop")) {
            WebElement target = ElementLocator.find(driver, objectTypeRDS, dataFieldRDS);
            if (el != null && target != null) BrowserActions.dragAndDrop(driver, el, target);

        // ── FRAME ────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("FrameSwitch")) {
            BrowserActions.switchToFrame(driver, propertyValueRDS);

        } else if (action.equalsIgnoreCase("DefaultContent")) {
            BrowserActions.switchToDefaultContent(driver);

        // ── WINDOW ────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("SWITCH_WINDOW") || action.equalsIgnoreCase("switchOnWindow")
                || action.equalsIgnoreCase("NewBrowser")) {
            BrowserActions.switchToLastWindow(driver);

        } else if (action.equalsIgnoreCase("SWITCHSMALLWINDOW")) {
            BrowserActions.switchToSmallWindow(driver);

        } else if (action.equalsIgnoreCase("SWITCH_PARENTWINDOW")) {
            BrowserActions.switchToParentWindow(driver);

        } else if (action.equalsIgnoreCase("SET_PARENTWINDOW")) {
            BrowserActions.setParentWindow(driver);

        } else if (action.equalsIgnoreCase("Closewindow") || action.equalsIgnoreCase("ClosewindowAC")) {
            BrowserActions.closeCurrentWindowAndSwitch(driver, Framework.parenwindow);

        } else if (action.equalsIgnoreCase("ClosewindowJS")) {
            BrowserActions.closeWindowJs(driver);

        } else if (action.equalsIgnoreCase("openNewTab") || action.equalsIgnoreCase("jsOpenNewTab")
                || action.equalsIgnoreCase("RB_NEWTAB")) {
            BrowserActions.openNewTab(driver);

        } else if (action.equalsIgnoreCase("RB_OPENURL")) {
            BrowserActions.openNewTabWithUrl(driver, dataFieldRDS);

        // ── UPLOAD ────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("FILE_UPLOAD") || action.equalsIgnoreCase("File_Upload2")
                || action.equalsIgnoreCase("fileUploadAxis")) {
            if (el != null) BrowserActions.fileUpload(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("robotFileUpload") || action.equalsIgnoreCase("RobotClickFileUpload")) {
            BrowserActions.robotFileUpload(dataFieldRDS);

        // ── GET TEXT / VERIFY ─────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("GETTEXT") || action.equalsIgnoreCase("getValue")
                || action.equalsIgnoreCase("js_gettext") || action.equalsIgnoreCase("RB_gettext")) {
            if (el != null) VerifyActions.getText(driver, el, dataFieldRDS, pageName);

        } else if (action.equalsIgnoreCase("GETTEXTALART")) {
            String txt = AlertActions.getAlertText(driver);
            Monitoring_FrameWork.SaveResult(txt, dataFieldRDS);

        } else if (action.equalsIgnoreCase("CHECK_VISIBILITY") || action.equalsIgnoreCase("LOGIN_CHECK_VISIBILITY")
                || action.equalsIgnoreCase("MultipleCheck_Visibility")) {
            VerifyActions.checkVisibility(driver, propertyNameRDS, propertyValueRDS,
                    dataFieldRDS, srNo, pageName);

        } else if (action.equalsIgnoreCase("CHECK_INVISIBILITY")) {
            VerifyActions.checkInvisibility(driver, propertyNameRDS, propertyValueRDS,
                    dataFieldRDS, pageName);

        } else if (action.equalsIgnoreCase("CHECK_PRESENCEABILITY")) {
            VerifyActions.checkPresence(driver, propertyNameRDS, propertyValueRDS,
                    dataFieldRDS, pageName);

        } else if (action.equalsIgnoreCase("CHECK_ENABILITY")) {
            if (el != null) VerifyActions.checkEnability(driver, el, dataFieldRDS, pageName);

        } else if (action.equalsIgnoreCase("RECHECK_VISIBILITY") || action.equalsIgnoreCase("recheckVisibility")) {
            VerifyActions.recheckVisibility(driver, propertyNameRDS, propertyValueRDS,
                    dataFieldRDS, parseInt(objectTypeRDS, 30), pageName);

        } else if (action.equalsIgnoreCase("CheckDownload") || action.equalsIgnoreCase("verifyDownloadedFile")) {
            VerifyActions.verifyDownloadedFile(
                    System.getProperty("user.dir") + "/downloads", dataFieldRDS, 60);

        } else if (action.equalsIgnoreCase("THRESHOLD")) {
            if (el != null) VerifyActions.verifyBalance(driver, el,
                    parseDouble(objectTypeRDS, 0), dataFieldRDS);

        } else if (action.equalsIgnoreCase("executeJs") || action.equalsIgnoreCase("js_script")
                || action.equalsIgnoreCase("verifyexecuteJs") || action.equalsIgnoreCase("GETCSSVALUE")) {
            VerifyActions.executeJs(driver, objectTypeRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("verifyAlert") || action.equalsIgnoreCase("verifyAcceptalert")) {
            AlertActions.acceptAndVerify(driver, dataFieldRDS);

        } else if (action.equalsIgnoreCase("ACCEPTALERT") || action.equalsIgnoreCase("ALERT")
                || action.equalsIgnoreCase("OKPopup")) {
            AlertActions.accept(driver);

        } else if (action.equalsIgnoreCase("verify_page") || action.equalsIgnoreCase("verifypage")
                || action.equalsIgnoreCase("checkUrl")) {
            VerifyActions.verifyUrl(driver, dataFieldRDS, pageName);

        } else if (action.equalsIgnoreCase("FRAME_SIZE")) {
            if (el != null) VerifyActions.checkFrameSize(driver, el, dataFieldRDS);

        // ── DROPDOWN / DATE ────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("SELECT") || action.equalsIgnoreCase("selectDropDown")) {
            if (el != null) DropdownDateActions.selectByText(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("SELECTINDEX")) {
            if (el != null) DropdownDateActions.selectByIndex(el, parseInt(dataFieldRDS, 0));

        } else if (action.equalsIgnoreCase("SelectList")) {
            if (el != null) DropdownDateActions.selectByValue(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("ValidatefromDropdown")) {
            if (el != null) DropdownDateActions.validateAndSelect(el, propertyValueRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("selectDOB") || action.equalsIgnoreCase("DOB")) {
            // DOB fields require 3 separate elements — delegate to NewCustomFunctions
            NewCustomFunctions.selectDOB(el, objectTypeRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("DATAPIC")) {
            if (el != null) DropdownDateActions.sendDate(el, dataFieldRDS);

        } else if (action.equalsIgnoreCase("SetFutureDate")) {
            if (el != null) {
                String future = DropdownDateActions.getFutureDate(
                        parseInt(objectTypeRDS, 1), "dd/MM/yyyy");
                DropdownDateActions.sendDate(el, future);
            }

        } else if (action.equalsIgnoreCase("SYSTEM_DATE") || action.equalsIgnoreCase("getCurrentDate")) {
            String date = DropdownDateActions.getCurrentDate("dd/MM/yyyy");
            Monitoring_FrameWork.SaveResult(date, dataFieldRDS);

        // ── OTP ──────────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("MAILOTP_READ") || action.equalsIgnoreCase("readOtpMail")
                || action.equalsIgnoreCase("EmailOTP") || action.equalsIgnoreCase("BajajOtpMail")) {
            NewCustomFunctions.readMailOtpAndSend(el, objectTypeRDS, dataFieldRDS, propertyValueRDS);

        } else if (action.toUpperCase().contains("READOTPAPI(")) {
            String[] params = parseParams(action);
            NewCustomFunctions.getOTPAPIloopTime(el, objectTypeRDS, dataFieldRDS,
                    parseInt(params[0], 5), parseInt(params[1], 60));

        } else if (action.toUpperCase().contains("READOTPAPILATEST(")) {
            String[] params = parseParams(action);
            NewCustomFunctions.readOtpApiLatest(el, objectTypeRDS, dataFieldRDS,
                    parseInt(params[0], 5), parseInt(params[1], 60));

        } else if (action.toUpperCase().contains("READMOBILEOTPORMAILOTP(")) {
            String[] params = parseParams(action);
            NewCustomFunctions.readMobileOtpOrMailOtp(el, objectTypeRDS, dataFieldRDS,
                    parseInt(params[0], 5), parseInt(params[1], 60));

        } else if (action.equalsIgnoreCase("readOTPAPI") || action.equalsIgnoreCase("OTP_READ")) {
            NewCustomFunctions.getOTPAPI(el, objectTypeRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("readOTPAPIAxis")) {
            NewCustomFunctions.getOTPAPIAxis(el, objectTypeRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("readOTPAPILongTime")) {
            NewCustomFunctions.getOTPAPILongTime(el, objectTypeRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("sendSingleOtp")) {
            NewCustomFunctions.sendSingleOtp(el, objectTypeRDS, dataFieldRDS);

        } else if (action.equalsIgnoreCase("StartTime") || action.equalsIgnoreCase("getTimeForOTP")) {
            Framework.OTPcurrentTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new java.util.Date());
            log.info("[Dispatch] OTP start time recorded: " + Framework.OTPcurrentTime);

        // ── BROWSER LIFECYCLE ─────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("startBrowser") || action.equalsIgnoreCase("CHROMELAUNECH")
                || action.equalsIgnoreCase("CHROMELAUNEH") || action.equalsIgnoreCase("openChrome")) {
            Framework.StartBrowser(Framework.homedir, objectTypeRDS.isBlank() ? Framework.browser : objectTypeRDS);

        } else if (action.equalsIgnoreCase("quit")) {
            if (driver != null) driver.quit();

        } else if (action.equalsIgnoreCase("Exit")) {
            Framework.exitFlag = true;

        // ── SCREENSHOT ────────────────────────────────────────────────────────
        } else if (action.equalsIgnoreCase("takeScreenShot")) {
            Framework.TakeScreenshots();

        // ── CUSTOM / BANK-SPECIFIC DELEGATIONS ────────────────────────────────
        } else {
            // Delegate everything else to original Functions — removes nothing,
            // just defers the long tail of bank-specific actions.
            Functions.Actions(controlRDS, objectTypeRDS, propertyNameRDS,
                    propertyValueRDS, dataFieldRDS, actionRDS,
                    srNo, fieldStatus, pageName);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** Actions that don't require a WebElement to be resolved first. */
    private static boolean isNonElementAction(String action) {
        String a = action.toUpperCase();
        return a.startsWith("BROWSE") || a.startsWith("WAIT")
                || a.equals("REFRESH") || a.equals("DELETEALLCOOKIES")
                || a.equals("DEFAULTCONTENT") || a.equals("SWITCH_WINDOW")
                || a.equals("SWITCH_PARENTWINDOW") || a.equals("SET_PARENTWINDOW")
                || a.equals("CLOSEWINDOW") || a.equals("JSBACK")
                || a.equals("STARTIME") || a.equals("GETTIMEFOROTP")
                || a.equals("QUIT") || a.equals("EXIT")
                || a.equals("TAKESCREENSHOT") || a.contains("EXPLICIT")
                || a.contains("WAIT(") || a.equals("GETCURRENTDATE")
                || a.equals("SYSTEM_DATE") || a.equals("GETCURRENTURL")
                || a.equals("CHECKURL") || a.equals("JSOPENTAB")
                || a.equals("OPENNEWWTAB") || a.equals("DELETEALLCOOKIES");
    }

    private static int parseInt(String value, int fallback) {
        try { return Integer.parseInt(value.trim()); }
        catch (Exception e) { return fallback; }
    }

    private static double parseDouble(String value, double fallback) {
        try { return Double.parseDouble(value.trim().replaceAll("[^0-9.]", "")); }
        catch (Exception e) { return fallback; }
    }

    /** Extracts comma-separated integers from "ACTION(a,b)" format. */
    private static String[] parseParams(String action) {
        try {
            String inside = action.split("\\(")[1].replace(")", "").trim();
            return inside.split(",");
        } catch (Exception e) {
            return new String[]{"5", "60"};
        }
    }

    /** Extracts a single integer from "ACTION(n)" format. */
    private static int parseParenInt(String action, int fallback) {
        try {
            String inside = action.split("\\(")[1].replace(")", "").trim();
            return Integer.parseInt(inside);
        } catch (Exception e) {
            return fallback;
        }
    }
}
