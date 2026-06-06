package com.apmosys.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * DropdownDateActions — dropdown selection, date pickers, DOB handling.
 *
 * Extracted from Functions.java where SELECT, SELECTINDEX, selectDropDown,
 * selectDOB, DOB, DATAPIC, SetFutureDate, selectAxisPreviousdate and
 * SENDKEYS_BIRTHDAY were all inline in the 8000-line god class.
 */
public class DropdownDateActions {

    private static final Logger log = LoggerFactory.getLogger(DropdownDateActions.class);


    private DropdownDateActions() {}

    // ─────────────────────────────────────────────────────────────────────────
    // Dropdown selection
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Selects a dropdown option by visible text.
     * Was: SELECT, selectDropDown
     */
    public static void selectByText(WebElement dropdown, String visibleText) {
        try {
            new Select(dropdown).selectByVisibleText(visibleText.trim());
            log.info("[DropdownDateActions] Selected by text: " + visibleText);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            log.error("[DropdownDateActions] Select by text failed: " + e.getMessage());
        }
    }

    /**
     * Selects a dropdown option by index.
     * Was: SELECTINDEX
     */
    public static void selectByIndex(WebElement dropdown, int index) {
        try {
            new Select(dropdown).selectByIndex(index);
            log.info("[DropdownDateActions] Selected index: " + index);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            log.error("[DropdownDateActions] Select by index failed: " + e.getMessage());
        }
    }

    /**
     * Selects a dropdown option by value attribute.
     * Was: SelectList
     */
    public static void selectByValue(WebElement dropdown, String value) {
        try {
            new Select(dropdown).selectByValue(value.trim());
            log.info("[DropdownDateActions] Selected by value: " + value);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            log.error("[DropdownDateActions] Select by value failed: " + e.getMessage());
        }
    }

    /**
     * Validates and then selects from a dropdown.
     * Was: ValidatefromDropdown
     */
    public static void validateAndSelect(WebElement dropdown, String expectedText,
                                         String selectValue) {
        try {
            Select select = new Select(dropdown);
            boolean found = select.getOptions().stream()
                    .anyMatch(o -> o.getText().trim().equalsIgnoreCase(expectedText.trim()));
            if (!found) {
                log.info("[DropdownDateActions] Option not found: " + expectedText);
                Framework.errorsatus = "1";
                return;
            }
            select.selectByVisibleText(selectValue.trim());
        } catch (Exception e) {
            Framework.errorsatus = "1";
            log.error("[DropdownDateActions] validateAndSelect error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Date / DOB helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the current date in the given format.
     * Was: getCurrentDate, SYSTEM_DATE
     */
    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * Returns a future date offset by the given days.
     * Was: SetFutureDate
     */
    public static String getFutureDate(int daysFromNow, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysFromNow);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * Returns a past date offset by the given days.
     * Was: selectAxisPreviousdate, lastDatePicker
     */
    public static String getPastDate(int daysBack, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -daysBack);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * Sends a DOB (Day/Month/Year) to three separate input fields.
     * Was: selectDOB, DOB, SENDKEYS_BIRTHDAY
     *
     * @param dob        date string, e.g. "15/08/1985"
     * @param separator  the separator character in the dob string, e.g. "/"
     * @param dayEl      input for day
     * @param monthEl    input for month
     * @param yearEl     input for year
     */
    public static void sendDob(String dob, String separator,
                               WebElement dayEl, WebElement monthEl, WebElement yearEl) {
        try {
            String[] parts = dob.split(separator.equals(".") ? "\\." : separator);
            if (parts.length < 3) {
                log.error("[DropdownDateActions] Invalid DOB format: " + dob);
                Framework.errorsatus = "1";
                return;
            }
            if (dayEl   != null) { dayEl.clear();   dayEl.sendKeys(parts[0]); }
            if (monthEl != null) { monthEl.clear(); monthEl.sendKeys(parts[1]); }
            if (yearEl  != null) { yearEl.clear();  yearEl.sendKeys(parts[2]); }
            log.info("[DropdownDateActions] DOB sent: " + dob);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            log.error("[DropdownDateActions] sendDob error: " + e.getMessage());
        }
    }

    /**
     * Sends a date to a single date input field, trying multiple formats.
     * Was: DATAPIC, DailyMonthlyWeeklyClick
     */
    public static void sendDate(WebElement dateField, String dateValue) {
        try {
            dateField.clear();
            dateField.sendKeys(dateValue);
            log.info("[DropdownDateActions] Date sent: " + dateValue);
        } catch (Exception e) {
            Framework.errorsatus = "1";
            log.error("[DropdownDateActions] sendDate error: " + e.getMessage());
        }
    }

    /**
     * Returns the last date of the current month in the given format.
     * Was: lastDatePicker
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * Checks whether today is a weekday (Mon-Fri).
     * Was: verifyDay
     */
    public static boolean isTodayWeekday() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return day != Calendar.SATURDAY && day != Calendar.SUNDAY;
    }
}
