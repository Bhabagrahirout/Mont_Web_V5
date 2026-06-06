package com.apmosys.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WaitActions — all explicit wait strategies in one place.
 *
 * Extracted from Functions.java where WebDriverWait was instantiated
 * inline 20+ times with hard-coded timeouts scattered across 8000 lines.
 *
 * KEY RULE: never use implicitlyWait() — mixing implicit + explicit waits
 * causes unpredictable timeouts. Always use the methods here.
 *
 * Usage:
 *   WaitActions.forVisible(driver, By.id("submit"), 30);
 *   WaitActions.forClickable(driver, element, 10);
 *   WaitActions.forCondition(driver, "XPATH", "//span", "visibilityOfElement(30)");
 */
public class WaitActions {

    private static final Logger log = LoggerFactory.getLogger(WaitActions.class);


    private WaitActions() {}

    // ── Default timeout (pulled from Framework at call time) ─────────────────
    private static int defaultTimeout() {
        return Framework.defaultwaittime > 0 ? Framework.defaultwaittime : 30;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Visibility / presence
    // ─────────────────────────────────────────────────────────────────────────

    public static WebElement forVisible(WebDriver driver, By locator, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement forVisible(WebDriver driver, By locator) {
        return forVisible(driver, locator, defaultTimeout());
    }

    public static WebElement forPresent(WebDriver driver, By locator, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement forPresent(WebDriver driver, By locator) {
        return forPresent(driver, locator, defaultTimeout());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Clickability
    // ─────────────────────────────────────────────────────────────────────────

    public static WebElement forClickable(WebDriver driver, By locator, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement forClickable(WebDriver driver, By locator) {
        return forClickable(driver, locator, defaultTimeout());
    }

    public static WebElement forClickable(WebDriver driver, WebElement element, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.elementToBeClickable(element));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Invisibility
    // ─────────────────────────────────────────────────────────────────────────

    public static boolean forInvisible(WebDriver driver, By locator, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean forInvisible(WebDriver driver, By locator) {
        return forInvisible(driver, locator, defaultTimeout());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Text / attribute conditions
    // ─────────────────────────────────────────────────────────────────────────

    public static boolean forTextPresent(WebDriver driver, By locator, String text, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean forUrlContains(WebDriver driver, String urlFragment, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.urlContains(urlFragment));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Alert
    // ─────────────────────────────────────────────────────────────────────────

    public static org.openqa.selenium.Alert forAlert(WebDriver driver, int timeoutSecs) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until((Function) ExpectedConditions.alertIsPresent());
    }

    public static org.openqa.selenium.Alert forAlert(WebDriver driver) {
        return forAlert(driver, 10);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Page load complete
    // ─────────────────────────────────────────────────────────────────────────

    public static void forPageLoad(WebDriver driver, int timeoutSecs) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void forPageLoad(WebDriver driver) {
        forPageLoad(driver, defaultTimeout());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Fluent wait (for polling / flaky elements)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Polls every 1 second until the element is visible, ignoring
     * NoSuchElementException.  Useful for elements that appear asynchronously.
     */
    public static WebElement fluentForVisible(WebDriver driver, By locator,
                                              int timeoutSecs, int pollSecs) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSecs))
                .pollingEvery(Duration.ofSeconds(pollSecs))
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .until(d -> d.findElement(locator));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Simple thread sleep (use sparingly — prefer explicit waits above)
    // ─────────────────────────────────────────────────────────────────────────

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleepMs(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Legacy Excel-driven condition dispatcher
    //
    // Called from ActionDispatcher when action = "ExplicitWaits".
    // Parses the condition string format used in the test sheets, e.g.:
    //   "visibilityOfElement(30)"
    //   "invisibilityOfElement(60)"
    //   "presenceOfElement(15)"
    //   "elementToBeClickable(20)"
    //   "alertIsPresent(10)"
    // ─────────────────────────────────────────────────────────────────────────

    public static void forCondition(WebDriver driver, String locatorType,
                                    String locatorValue, String condition,
                                    String srNo, String pageName) {
        try {
            log.info("[WaitActions] Waiting for condition: " + condition);
            long waitSecs = parseTimeout(condition);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSecs));
            By locator = ElementLocator.toBy(locatorType, locatorValue);

            if (condition.contains("invisibilityOfElement")) {
                wait.until((Function) ExpectedConditions.invisibilityOfElementLocated(locator));
            } else if (condition.contains("visibilityOfElement")) {
                wait.until((Function) ExpectedConditions.visibilityOfElementLocated(locator));
            } else if (condition.contains("presenceOfElement")) {
                wait.until((Function) ExpectedConditions.presenceOfElementLocated(locator));
            } else if (condition.contains("elementToBeClickable")) {
                wait.until((Function) ExpectedConditions.elementToBeClickable(locator));
            } else if (condition.contains("alertIsPresent")) {
                wait.until((Function) ExpectedConditions.alertIsPresent());
            } else {
                log.info("[WaitActions] Unknown condition: " + condition);
            }
            log.info("[WaitActions] Condition met: " + condition);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            Framework.errorpagename = pageName;
            log.error("[WaitActions] Condition not met: " + e.getMessage());
            try {
                Monitoring_FrameWork.SaveResult("Locator not found", " ");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Parses timeout from strings like "visibilityOfElement(30)" → 30.
     */
    private static long parseTimeout(String condition) {
        try {
            String inside = condition.split("\\(")[1].split("\\)")[0];
            return Long.parseLong(inside.trim());
        } catch (Exception e) {
            return defaultTimeout();
        }
    }
}
