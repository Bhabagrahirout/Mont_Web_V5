package test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PerformanceMetrics {
	
	public static void main(String[] args) {
		
		
		
        System.setProperty("webdriver.gecko.driver", "/home/apmosys/Desktop/Selenium Tools/geckodriver-v0.34.0-linux64/geckodriver");
        WebDriver driver = new FirefoxDriver();

        long startTime = System.currentTimeMillis();

        driver.get("https://www.google.com");

        long endTime = System.currentTimeMillis();

        long pageLoadTime = endTime - startTime;
        System.out.println("Page Load Time (Manual Calculation): " + pageLoadTime + " ms");

        // Using JavaScriptExecutor to get Performance Timing Metrics
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get Response Time (Time taken for first byte from server)
        long responseTime = (Long) js.executeScript("return performance.timing.responseStart - performance.timing.requestStart;");
        System.out.println("Response Time: " + responseTime + " ms");

        // Get Full Page Load Time (Time from navigation start to load completion)
        long loadTime = (Long) js.executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
        System.out.println("Page Load Time (Using JS API): " + loadTime + " ms");

        // Close browser
        driver.quit();
		
		
	}
	
    public static void main2(String[] args) {
        // Set up WebDriver (Make sure to set your WebDriver path)
        System.setProperty("webdriver.chrome.driver", "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");
        
        ChromeOptions op=new ChromeOptions();
        
        op.addArguments(new String[] { "--remote-allow-origins=*" });
        WebDriver driver = new ChromeDriver(op);

        // Start time before loading the page
        long startTime = System.currentTimeMillis();

        // Open Google
        driver.get("https://www.google.com");

        // End time after page is fully loaded
        long endTime = System.currentTimeMillis();

        long pageLoadTime = endTime - startTime;
        System.out.println("Page Load Time (Manual Calculation): " + pageLoadTime + " ms");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        long responseTime = (Long) js.executeScript("return performance.timing.responseStart - performance.timing.requestStart;");
        System.out.println("Response Time: " + responseTime/1000.000 + " ms");

        // Get Full Page Load Time (Time from navigation start to load completion)
        long loadTime = (Long) js.executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
        System.out.println("Page Load Time (Using JS API): " + loadTime + " ms");

        // Close browser
        driver.quit();
    }
}
