package com.apmosys.framework;

import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

/**
 * AlertActions — all browser alert, popup, and security-question handling.
 *
 * Extracted from Functions.java where alert logic was spread across
 * ACCEPTALERT, ALERT, verifyAlert, verifyAcceptalert, OKPopup,
 * SECURITYQ, SECURITYQUE, and 10 more variants.
 */
public class AlertActions {

    private AlertActions() {}

    // ─────────────────────────────────────────────────────────────────────────
    // Basic alert operations
    // ─────────────────────────────────────────────────────────────────────────

    public static void accept(WebDriver driver) {
        try {
            Alert alert = WaitActions.forAlert(driver, 10);
            System.out.println("[AlertActions] Alert text: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("[AlertActions] No alert present to accept");
        }
    }

    public static void dismiss(WebDriver driver) {
        try {
            Alert alert = WaitActions.forAlert(driver, 10);
            alert.dismiss();
        } catch (Exception e) {
            System.out.println("[AlertActions] No alert present to dismiss");
        }
    }

    public static String getAlertText(WebDriver driver) {
        try {
            Alert alert = WaitActions.forAlert(driver, 10);
            return alert.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static void acceptIfPresent(WebDriver driver) {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {}
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Alert with text verification (was verifyAlert / GETTEXTALART)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Accepts an alert and returns its text for verification.
     * Logs a pass/fail against the expected value.
     */
    public static String acceptAndVerify(WebDriver driver, String expectedText) {
        try {
            Alert alert = WaitActions.forAlert(driver, 10);
            String actual = alert.getText().trim();
            alert.accept();
            System.out.println("[AlertActions] Alert text: " + actual);
            if (!expectedText.isBlank() && !actual.equalsIgnoreCase(expectedText.trim())) {
                System.out.println("[AlertActions] MISMATCH — expected: " + expectedText);
                Framework.errorsatus = "1";
            }
            return actual;
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[AlertActions] Alert not found: " + e.getMessage());
            return "";
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Security question handler (was SECURITYQ, SECURITYQUE, SECURITYQ5 …)
    //
    // In the original code there were 10+ near-identical copy-paste blocks for
    // different banks. The logic is the same: find a security question text,
    // map it to the right answer, type the answer. This single method replaces
    // all of them.
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Reads the security question text from the given element, looks it up in
     * the answers map (question fragment → answer), and types the answer into
     * the answer field.
     *
     * @param questionElement  the element whose text contains the question
     * @param answerElement    the input field for the answer
     * @param answersMap       maps question fragments to answers,
     *                         e.g. "mother" → "Mary", "city" → "Mumbai"
     */
    public static void answerSecurityQuestion(WebDriver driver,
                                              WebElement questionElement,
                                              WebElement answerElement,
                                              java.util.Map<String, String> answersMap) {
        try {
            String questionText = questionElement.getText().toLowerCase().trim();
            System.out.println("[AlertActions] Security question: " + questionText);

            String answer = answersMap.entrySet().stream()
                    .filter(e -> questionText.contains(e.getKey().toLowerCase()))
                    .map(java.util.Map.Entry::getValue)
                    .findFirst()
                    .orElse("");

            if (answer.isBlank()) {
                System.out.println("[AlertActions] No matching answer for: " + questionText);
                Framework.errorsatus = "1";
            } else {
                answerElement.clear();
                answerElement.sendKeys(answer);
                System.out.println("[AlertActions] Answered security question");
            }
        } catch (Exception e) {
            Framework.errorsatus = "1";
            System.err.println("[AlertActions] Security question error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Popup window (was OKPopup / closepopupwindow)
    // ─────────────────────────────────────────────────────────────────────────

    public static void closePopup(WebDriver driver, String parentHandle) {
        try {
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(parentHandle)) {
                    driver.switchTo().window(handle).close();
                }
            }
            driver.switchTo().window(parentHandle);
        } catch (Exception e) {
            System.err.println("[AlertActions] Popup close error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Unhandled alert safety net — call before any action that might trigger one
    // ─────────────────────────────────────────────────────────────────────────

    public static void dismissUnhandledAlertIfPresent(WebDriver driver) {
        try {
            driver.switchTo().alert().dismiss();
        } catch (Exception ignored) {}
    }
}
