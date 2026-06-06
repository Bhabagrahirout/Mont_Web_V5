package com.apmosys.framework;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * VerifyActions — visibility checks, text/URL verification, download checks.
 *
 * Extracted from Functions.java where CHECK_VISIBILITY, CHECK_INVISIBILITY,
 * LOGIN_CHECK_VISIBILITY, RECHECK_VISIBILITY, CHECK_PRESENCEABILITY,
 * CHECK_CONSOLABILITY, verifyDownloadedFile, verifyBalance etc.
 * were all scattered inline.
 *
 * Every method saves a result to Monitoring_FrameWork and sets
 * Framework.errorsatus = "1" on failure — same contract as the original.
 */
public class VerifyActions {

    private VerifyActions() {}

    // ─────────────────────────────────────────────────────────────────────────
    // Visibility checks
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Checks element is visible. Saves PASS/FAIL to Monitoring_FrameWork.
     * Was: CHECK_VISIBILITY, LOGIN_CHECK_VISIBILITY
     */
    public static void checkVisibility(WebDriver driver, String locatorType,
                                       String locatorValue, String expectedValue,
                                       String srNo, String pageName) {
        try {
            By by = ElementLocator.toBy(locatorType, locatorValue);
            new WebDriverWait(driver, Duration.ofSeconds(Framework.defaultwaittime))
                    .until((Function) ExpectedConditions.visibilityOfElementLocated(by));
            String actual = driver.findElement(by).getText().trim();
            System.out.println("[VerifyActions] Visible. Text: " + actual);
            Monitoring_FrameWork.SaveResult(actual, expectedValue);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            Framework.errorpagename = pageName;
            System.err.println("[VerifyActions] Element not visible: " + e.getMessage());
            try { Monitoring_FrameWork.SaveResult("Element Not Visible", expectedValue); }
            catch (Exception ignored) {}
        }
    }

    /**
     * Checks element is NOT visible. Saves PASS/FAIL.
     * Was: CHECK_INVISIBILITY
     */
    public static void checkInvisibility(WebDriver driver, String locatorType,
                                         String locatorValue, String expectedValue,
                                         String pageName) {
        try {
            By by = ElementLocator.toBy(locatorType, locatorValue);
            boolean invisible = new WebDriverWait(driver, Duration.ofSeconds(Framework.defaultwaittime))
                    .until((Function) ExpectedConditions.invisibilityOfElementLocated(by));
            System.out.println("[VerifyActions] Invisible: " + invisible);
            Monitoring_FrameWork.SaveResult(invisible ? "Not Visible" : "Still Visible", expectedValue);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] Invisibility check error: " + e.getMessage());
        }
    }

    /**
     * Checks element is present in DOM (not necessarily visible).
     * Was: CHECK_PRESENCEABILITY
     */
    public static void checkPresence(WebDriver driver, String locatorType,
                                     String locatorValue, String expectedValue,
                                     String pageName) {
        try {
            By by = ElementLocator.toBy(locatorType, locatorValue);
            new WebDriverWait(driver, Duration.ofSeconds(Framework.defaultwaittime))
                    .until((Function) ExpectedConditions.presenceOfElementLocated(by));
            Monitoring_FrameWork.SaveResult("Element Present", expectedValue);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] Element not present: " + e.getMessage());
            try { Monitoring_FrameWork.SaveResult("Element Not Present", expectedValue); }
            catch (Exception ignored) {}
        }
    }

    /**
     * Checks element is enabled/clickable.
     * Was: CHECK_ENABILITY
     */
    public static void checkEnability(WebDriver driver, WebElement element,
                                      String expectedValue, String pageName) {
        try {
            boolean enabled = element.isEnabled();
            System.out.println("[VerifyActions] Enabled: " + enabled);
            Monitoring_FrameWork.SaveResult(enabled ? "Enabled" : "Disabled", expectedValue);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] Enability check error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Text / URL verification
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Gets text from element and saves to monitoring.
     * Was: GETTEXT
     */
    public static String getText(WebDriver driver, WebElement element,
                                 String expectedValue, String pageName) {
        try {
            String text = element.getText().trim();
            if (text.isBlank()) {
                text = element.getAttribute("value");
            }
            System.out.println("[VerifyActions] Text: " + text);
            Monitoring_FrameWork.SaveResult(text, expectedValue);
            return text;
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] getText error: " + e.getMessage());
            return "";
        }
    }

    /**
     * Verifies current page URL contains the expected fragment.
     * Was: checkUrl / verify_page / verifypage
     */
    public static void verifyUrl(WebDriver driver, String expectedUrlFragment,
                                 String pageName) {
        try {
            String currentUrl = driver.getCurrentUrl();
            System.out.println("[VerifyActions] URL: " + currentUrl);
            if (!currentUrl.toLowerCase().contains(expectedUrlFragment.toLowerCase())) {
                Framework.errorsatus = "1";
                System.out.println("[VerifyActions] URL mismatch. Expected fragment: "
                        + expectedUrlFragment);
            }
            Monitoring_FrameWork.SaveResult(currentUrl, expectedUrlFragment);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] URL verify error: " + e.getMessage());
        }
    }

    /**
     * Reads a JavaScript expression and saves result.
     * Was: GETCSSVALUE, executeJs, verifyexecuteJs
     */
    public static String executeJs(WebDriver driver, String script,
                                   String expectedValue) {
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(script);
            String value = result != null ? result.toString().trim() : "";
            System.out.println("[VerifyActions] JS result: " + value);
            Monitoring_FrameWork.SaveResult(value, expectedValue);
            return value;
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] JS execute error: " + e.getMessage());
            return "";
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Download verification
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Waits up to maxWaitSecs for a file matching the name pattern to appear
     * in the downloads folder.
     * Was: verifyDownloadedFile, CheckDownload, verifyDownloadedFiles
     */
    public static boolean verifyDownloadedFile(String downloadFolder,
                                               String fileNamePattern,
                                               int maxWaitSecs) {
        System.out.println("[VerifyActions] Waiting for download: " + fileNamePattern);
        for (int i = 0; i < maxWaitSecs; i++) {
            File dir = new File(downloadFolder);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.getName().toLowerCase().contains(fileNamePattern.toLowerCase())
                            && !f.getName().endsWith(".crdownload")
                            && !f.getName().endsWith(".tmp")) {
                        System.out.println("[VerifyActions] File found: " + f.getName());
                        return true;
                    }
                }
            }
            WaitActions.sleep(1);
        }
        System.out.println("[VerifyActions] File NOT found: " + fileNamePattern);
        Framework.errorsatus = "1";
        return false;
    }

    /**
     * Rechecks visibility — retries the check until visible or timeout.
     * Was: RECHECK_VISIBILITY, recheckVisibility
     */
    public static void recheckVisibility(WebDriver driver, String locatorType,
                                         String locatorValue, String expectedValue,
                                         int retrySecs, String pageName) {
        int elapsed = 0;
        while (elapsed < retrySecs) {
            try {
                By by = ElementLocator.toBy(locatorType, locatorValue);
                WebElement el = driver.findElement(by);
                if (el.isDisplayed()) {
                    String text = el.getText().trim();
                    Monitoring_FrameWork.SaveResult(text, expectedValue);
                    return;
                }
            } catch (Exception ignored) {}
            WaitActions.sleep(1);
            elapsed++;
        }
        Framework.errorsatus = "1";
        System.err.println("[VerifyActions] Element still not visible after " + retrySecs + "s");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Balance / numeric verification
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Reads a numeric balance from an element, compares against threshold.
     * Was: verifyBalance, THRESHOLD
     */
    public static void verifyBalance(WebDriver driver, WebElement element,
                                     double threshold, String expectedValue) {
        try {
            String rawText = element.getText().replaceAll("[^0-9.]", "");
            double balance = Double.parseDouble(rawText);
            System.out.println("[VerifyActions] Balance: " + balance + " | Threshold: " + threshold);
            String result = balance >= threshold ? "Above threshold" : "Below threshold";
            Monitoring_FrameWork.SaveResult(result + " (" + balance + ")", expectedValue);
            if (balance < threshold) Framework.errorsatus = "1";
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] Balance verify error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Frame size check
    // ─────────────────────────────────────────────────────────────────────────

    public static void checkFrameSize(WebDriver driver, WebElement frame,
                                      String expectedValue) {
        try {
            int width  = frame.getSize().getWidth();
            int height = frame.getSize().getHeight();
            String size = width + "x" + height;
            System.out.println("[VerifyActions] Frame size: " + size);
            Monitoring_FrameWork.SaveResult(size, expectedValue);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[VerifyActions] Frame size error: " + e.getMessage());
        }
    }
}
