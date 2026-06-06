package com.apmosys.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BrowserActions — click, type, clear, navigate, window, and frame actions.
 *
 * Extracted from Functions.java (was lines 400–700, 1490–1600, 2060–2450,
 * 5600–6350 of the original 8074-line god class).
 *
 * Every method is stateless and takes the WebDriver explicitly.
 * Nothing reads from Framework static fields — callers pass what's needed.
 */
public class BrowserActions {

    private static final Logger log = LoggerFactory.getLogger(BrowserActions.class);


    private BrowserActions() {}

    // ─────────────────────────────────────────────────────────────────────────
    // Navigation
    // ─────────────────────────────────────────────────────────────────────────

    public static void browseUrl(WebDriver driver, String url) {
        log.info("[BrowserActions] Navigating to: " + url);
        driver.get(url);
    }

    public static void browseBack(WebDriver driver) {
        driver.navigate().back();
    }

    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static void openNewTab(WebDriver driver, String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        if (url != null && !url.isBlank()) {
            driver.get(url);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Click variants
    // ─────────────────────────────────────────────────────────────────────────

    public static void click(WebDriver driver, WebElement element) {
        element.click();
    }

    public static void doubleClick(WebDriver driver, WebElement element) {
        new Actions(driver).doubleClick(element).perform();
    }

    public static void clickJs(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void clickOnCoordinate(WebDriver driver, int x, int y) throws Exception {
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void robotClick(WebDriver driver, WebElement element) throws Exception {
        Point location = element.getLocation();
        Robot robot = new Robot();
        robot.mouseMove(location.getX() + 5, location.getY() + 5);
        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void actionClick(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).click().perform();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Sendkeys / type variants
    // ─────────────────────────────────────────────────────────────────────────

    public static void sendKeys(WebDriver driver, WebElement element, String value) {
        element.sendKeys(value);
    }

    public static void sendKeysWithTab(WebDriver driver, WebElement element, String value) {
        element.sendKeys(value);
        element.sendKeys(Keys.TAB);
    }

    public static void sendKeysWithEnter(WebDriver driver, WebElement element, String value) {
        element.sendKeys(value);
        element.sendKeys(Keys.ENTER);
    }

    public static void sendKeysJs(WebDriver driver, WebElement element, String value) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value=arguments[1];", element, value);
    }

    /**
     * Sends keys using clipboard paste — useful for fields that block
     * direct keyboard input.
     */
    public static void sendKeysCopyPaste(WebDriver driver, WebElement element, String value)
            throws Exception {
        StringSelection selection = new StringSelection(value);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        element.click();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public static void sendKeysRobot(WebDriver driver, WebElement element, String value)
            throws Exception {
        element.click();
        Robot robot = new Robot();
        for (char c : value.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }

    public static void actionSendKeys(WebDriver driver, WebElement element, String value) {
        new Actions(driver).moveToElement(element).sendKeys(value).perform();
    }

    public static void numericSendKeys(WebDriver driver, WebElement element, String value) {
        element.clear();
        element.sendKeys(value.replaceAll("[^0-9]", ""));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Clear variants
    // ─────────────────────────────────────────────────────────────────────────

    public static void clear(WebElement element) {
        element.clear();
    }

    public static void clearUsingAction(WebDriver driver, WebElement element) {
        new Actions(driver)
                .moveToElement(element)
                .click()
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE)
                .perform();
    }

    public static void clearUsingRobot(WebDriver driver, WebElement element) throws Exception {
        element.click();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Keyboard actions
    // ─────────────────────────────────────────────────────────────────────────

    public static void pressTab(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    public static void pressEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

    public static void pressF4(WebElement element) {
        element.sendKeys(Keys.F4);
    }

    public static void pressArrowDown(WebDriver driver, WebElement element, int times) {
        for (int i = 0; i < times; i++) {
            element.sendKeys(Keys.ARROW_DOWN);
        }
    }

    public static void pressArrowUp(WebDriver driver, WebElement element, int times) {
        for (int i = 0; i < times; i++) {
            element.sendKeys(Keys.ARROW_UP);
        }
    }

    public static void arrowDownEnter(WebDriver driver, WebElement element, int times) {
        pressArrowDown(driver, element, times);
        element.sendKeys(Keys.ENTER);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Frame switching
    // ─────────────────────────────────────────────────────────────────────────

    public static void switchToFrame(WebDriver driver, String frameIdentifier) {
        try {
            int index = Integer.parseInt(frameIdentifier.trim());
            driver.switchTo().frame(index);
        } catch (NumberFormatException e) {
            driver.switchTo().frame(frameIdentifier.trim());
        }
    }

    public static void switchToFrameByElement(WebDriver driver, WebElement element) {
        driver.switchTo().frame(element);
    }

    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public static void switchToParentFrame(WebDriver driver) {
        driver.switchTo().parentFrame();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Window management
    // ─────────────────────────────────────────────────────────────────────────

    public static void switchToWindow(WebDriver driver, String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    /**
     * Switches to the most recently opened window (last in handle set).
     * Falls back to parent if only one window exists.
     */
    public static void switchToLastWindow(WebDriver driver) {
        String lastHandle = null;
        for (String handle : driver.getWindowHandles()) {
            lastHandle = handle;
        }
        if (lastHandle != null) {
            driver.switchTo().window(lastHandle);
        }
    }

    public static void switchToSmallWindow(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        String smallest = null;
        int smallestSize = Integer.MAX_VALUE;
        for (String handle : handles) {
            driver.switchTo().window(handle);
            int size = driver.manage().window().getSize().getWidth()
                    * driver.manage().window().getSize().getHeight();
            if (size < smallestSize) {
                smallestSize = size;
                smallest = handle;
            }
        }
        if (smallest != null) {
            driver.switchTo().window(smallest);
        }
    }

    public static void closeCurrentWindow(WebDriver driver, String parentHandle) {
        driver.close();
        driver.switchTo().window(parentHandle);
    }

    public static void closeWindowJs(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.close();");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scroll actions
    // ─────────────────────────────────────────────────────────────────────────

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollDown(WebDriver driver, int pixels) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0," + pixels + ");");
    }

    public static void scrollUp(WebDriver driver, int pixels) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0,-" + pixels + ");");
    }

    public static void scrollToTop(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0);");
    }

    public static void scrollToBottom(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollElementToLeft(WebDriver driver, WebElement element, int pixels) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollLeft -= " + pixels + ";", element);
    }

    public static void scrollElementToRight(WebDriver driver, WebElement element, int pixels) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollLeft += " + pixels + ";", element);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Drag and drop
    // ─────────────────────────────────────────────────────────────────────────

    public static void dragAndDrop(WebDriver driver, WebElement source, WebElement target) {
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Hover / move to element
    // ─────────────────────────────────────────────────────────────────────────

    public static void moveToElement(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public static void moveToElementAndClick(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).click().perform();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Page text / source
    // ─────────────────────────────────────────────────────────────────────────

    public static String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public static String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public static String getTitle(WebDriver driver) {
        return driver.getTitle();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Wait helpers (basic — full wait strategy is in WaitActions)
    // ─────────────────────────────────────────────────────────────────────────

    public static void waitSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }

    public static void waitForPageLoad(WebDriver driver, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }
}
