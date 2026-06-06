package com.apmosys.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * ElementLocator — resolves (locatorType, locatorValue) pairs into WebElements.
 *
 * Extracted from Functions.CheckObjectVisibility() which was a 150-line
 * switch statement buried inside the god class.
 *
 * Usage:
 *   WebElement el = ElementLocator.find(driver, "XPATH", "//button[@id='submit']", 30);
 *   WebElement el = ElementLocator.find(driver, "ID",    "loginBtn", 30);
 *
 * Frame switching locator types (FID, FNAME, FXPATH, INDEX) perform the
 * switch as a side effect and return null — same behaviour as before.
 */
public class ElementLocator {

    private static final Logger log = LoggerFactory.getLogger(ElementLocator.class);


    private ElementLocator() {}

    /**
     * Resolve a locator to a visible WebElement.
     *
     * @param driver        active WebDriver
     * @param locatorType   ID | XPATH | CSS | NAME | LINKTEXT | PARTIALLINKTEXT |
     *                      CLASSNAME | TAGNAME | FID | FNAME | FXPATH | INDEX | PARTENTFRAME
     * @param locatorValue  the actual locator string
     * @param timeoutSecs   explicit wait timeout in seconds
     * @return the found WebElement, or null for frame-switch types / not found
     */
    public static WebElement find(WebDriver driver, String locatorType,
                                  String locatorValue, int timeoutSecs) throws Exception {
        if (locatorType == null || locatorValue == null) return null;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
        String type = locatorType.toUpperCase().trim();

        try {
            switch (type) {
                case "ID": {
                    wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
                    return driver.findElement(By.id(locatorValue));
                }
                case "XPATH": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
                    wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                    return driver.findElement(By.xpath(locatorValue));
                }
                case "CSS": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
                    WebElement el = driver.findElement(By.cssSelector(locatorValue));
                    return el.isDisplayed() ? el : null;
                }
                case "NAME": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
                    WebElement el = driver.findElement(By.name(locatorValue));
                    return el.isDisplayed() ? el : null;
                }
                case "LINKTEXT": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
                    WebElement el = driver.findElement(By.linkText(locatorValue));
                    return el.isDisplayed() ? el : null;
                }
                case "PARTIALLINKTEXT": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
                    WebElement el = driver.findElement(By.partialLinkText(locatorValue));
                    return el.isDisplayed() ? el : null;
                }
                case "CLASSNAME": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
                    WebElement el = driver.findElement(By.className(locatorValue));
                    return el.isDisplayed() ? el : null;
                }
                case "TAGNAME": {
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
                    WebElement el = driver.findElement(By.tagName(locatorValue));
                    return el.isDisplayed() ? el : null;
                }
                // ── Frame-switch types — side effect only, return null ────────
                case "FID": {
                    driver.switchTo().frame(driver.findElement(By.id(locatorValue)));
                    log.info("[Locator] Switched to frame by ID: " + locatorValue);
                    return null;
                }
                case "FNAME": {
                    driver.switchTo().frame(driver.findElement(By.name(locatorValue)));
                    log.info("[Locator] Switched to frame by Name: " + locatorValue);
                    return null;
                }
                case "FXPATH": {
                    driver.switchTo().frame(driver.findElement(By.xpath(locatorValue)));
                    log.info("[Locator] Switched to frame by XPath: " + locatorValue);
                    return null;
                }
                case "INDEX": {
                    int index = Integer.parseInt(locatorValue.trim());
                    driver.switchTo().frame(index);
                    log.info("[Locator] Switched to frame by index: " + index);
                    return null;
                }
                case "PARTENTFRAME": {
                    driver.switchTo().parentFrame();
                    log.info("[Locator] Switched to parent frame");
                    return null;
                }
                case "XPATHS": {
                    // XPATHS skips element finding — used for custom logic elsewhere
                    return null;
                }
                default: {
                    log.info("[Locator] Unknown locator type: " + type);
                    return null;
                }
            }
        } catch (Exception e) {
            Framework.errorsatus = "1";
            Framework.errorpagename = Framework.pagename;
            Framework.errorType = e.getClass().getName();
            try {
                Framework.errorMessage = e.getMessage().split("\n")[0];
            } catch (Exception ex) {
                Framework.errorMessage = "Unknown error";
            }
            log.error("[Locator] Failed to find element [" + type + "=" + locatorValue + "]: " + e.getMessage());
            Monitoring_FrameWork.SaveResult("Locator not found", " ");
            return null;
        }
    }

    /**
     * Convenience overload — uses Framework.defaultwaittime.
     */
    public static WebElement find(WebDriver driver, String locatorType, String locatorValue) throws Exception {
        return find(driver, locatorType, locatorValue, Framework.defaultwaittime);
    }

    /**
     * Build a By from a locator type string.
     * Useful for WaitActions which need the By object directly.
     */
    public static By toBy(String locatorType, String locatorValue) {
        switch (locatorType.toUpperCase().trim()) {
            case "ID":              return By.id(locatorValue);
            case "XPATH":           return By.xpath(locatorValue);
            case "CSS":             return By.cssSelector(locatorValue);
            case "NAME":            return By.name(locatorValue);
            case "LINKTEXT":        return By.linkText(locatorValue);
            case "PARTIALLINKTEXT": return By.partialLinkText(locatorValue);
            case "CLASSNAME":       return By.className(locatorValue);
            case "TAGNAME":         return By.tagName(locatorValue);
            default:                return By.xpath(locatorValue);
        }
    }
}
