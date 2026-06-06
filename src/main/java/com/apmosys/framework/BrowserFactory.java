package com.apmosys.framework;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BrowserFactory — creates and configures WebDriver instances.
 *
 * Previously this logic was buried inside Framework.StartBrowser() which
 * was 250 lines of deeply nested if/else.  Now each browser type has its
 * own private method, which is called by the single public entry point.
 *
 * Usage:
 *   WebDriver driver = BrowserFactory.create(browserConfig);
 *   // or the legacy shim method during migration:
 *   BrowserFactory.launch(homedir, browserName, headless, ...);
 */
public class BrowserFactory {

    private static final Logger log = LoggerFactory.getLogger(BrowserFactory.class);

    private BrowserFactory() {}

    // ─────────────────────────────────────────────────────────────────────────
    // Public API — new style (use this going forward)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Create a WebDriver for the given BrowserConfig.
     * The driver is fully configured (maximized, timeouts set).
     *
     * @throws IllegalArgumentException if the browser name is not supported.
     */
    public static WebDriver create(BrowserConfig cfg) throws Exception {
        String os      = Framework.getosname();
        String arch    = Framework.getosarch();
        String baseDir = Framework.homedir;
        String name    = cfg.getBrowserName();

        log.info("Browser : {}", name);
        log.info("OS      : {}", os);
        log.info("Arch    : {}", arch);

        String driverDir = baseDir + "/Drivers/" + os + "/" + name + "/" + arch;

        WebDriver driver;
        if (name.equalsIgnoreCase("Chrome")) {
            driver = createChrome(driverDir, os, cfg);
        } else if (name.equalsIgnoreCase("FF")) {
            driver = createFirefox(driverDir, os, cfg);
        } else if (name.equalsIgnoreCase("Edge")) {
            driver = createEdge(driverDir, os, cfg);
        } else if (name.equalsIgnoreCase("IE")) {
            driver = createInternetExplorer(driverDir, os);
        } else if (name.equalsIgnoreCase("Safari")) {
            driver = createSafari();
        } else if (name.equalsIgnoreCase("axisChrome")) {
            driver = createAxisChrome(driverDir, os, cfg);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + name);
        }

        cfg.markBrowserOpen(name);
        return driver;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Legacy shim — called from Framework.StartBrowser() during migration
    // TODO: remove once all callers use create(BrowserConfig)
    // ─────────────────────────────────────────────────────────────────────────

    public static void launch(String homedir, String browserName, String headless,
                              String fastPageLoad, String pageStability,
                              String pageLoadTime, String defaultDownload,
                              String ffProfilePath) throws Exception {
        if ("Y".equalsIgnoreCase(Framework.pro.getProperty("startBrowserStatus", "Y"))) {
            // Build a minimal BrowserConfig from the shim parameters
            BrowserConfig cfg = new BrowserConfig() {{
                setBrowserName(browserName);
            }};
            // Inline the field-level values not yet in BrowserConfig constructor
            // These will clean up once syncShimsFromConfigs() is removed.
            Framework.driver = create(cfg);
            Framework.browsersts = 1;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Chrome
    // ─────────────────────────────────────────────────────────────────────────

    private static WebDriver createChrome(String driverDir, String os,
                                          BrowserConfig cfg) throws Exception {
        String driverPath = driverDir + (os.equalsIgnoreCase("Windows")
                ? "/chromedriver.exe" : "/chromedriver");

        ChromeOptions options = new ChromeOptions();

        // Anti-detection
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // General stability
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.addArguments(
                "--test-type",
                "--allow-running-insecure-content",
                "--allow-insecure-localhost",
                "--disable-notifications",
                "--disable-extensions",
                "disable-infobars",
                "--remote-allow-origins=*",
                "--disable-popup-blocking",
                "--no-sandbox",
                "--no-proxy-server",
                "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/123.0.6312.106 Safari/537.36"
        );

        // Headless
        if (cfg.isHeadless()) {
            options.addArguments("--headless", "window-size=1920,1080");
        }

        // Page load strategy
        if ("Y".equalsIgnoreCase(cfg.getFastPageLoad())) {
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        } else if ("None".equalsIgnoreCase(cfg.getFastPageLoad())) {
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
        }

        // Stability
        if (cfg.isPageStabilityEnabled()) {
            options.addArguments("--disable-dev-shm-usage");
        }

        // Location permission
        options.setExperimentalOption("prefs",
                Map.of("profile.default_content_setting_values.geolocation", 1));

        // Download / password saving
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        if (!cfg.isDefaultDownload()) {
            prefs.put("plugins.always_open_pdf_externally", true);
            prefs.put("download.prompt_for_download", false);
            prefs.put("download.directory_upgrade", true);
        }
        options.setExperimentalOption("prefs", prefs);

        File driverFile = new File(driverPath);
        driverFile.setExecutable(true);
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        try {
            driver.manage().timeouts()
                    .pageLoadTimeout(Duration.ofSeconds(cfg.getPageLoadTimeoutSeconds()));
        } catch (Exception e) {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        }
        return driver;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Firefox
    // ─────────────────────────────────────────────────────────────────────────

    private static WebDriver createFirefox(String driverDir, String os,
                                           BrowserConfig cfg) {
        String driverPath = driverDir + (os.equalsIgnoreCase("Windows")
                ? "/geckodriver.exe" : "/geckodriver");
        System.setProperty("webdriver.gecko.driver", driverPath);

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--profile", cfg.getFfProfilePath());

        FirefoxDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        log.info("Firefox driver started");
        return driver;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Edge
    // ─────────────────────────────────────────────────────────────────────────

    private static WebDriver createEdge(String driverDir, String os,
                                        BrowserConfig cfg) {
        String driverPath = driverDir + (os.equalsIgnoreCase("Windows")
                ? "/MicrosoftWebDriver.exe" : "/msedgedriver");
        System.setProperty("webdriver.edge.driver", driverPath);

        EdgeOptions options = new EdgeOptions();
        options.addArguments(
                "--remote-allow-origins=*",
                "--disable-dev-shm-usage",
                "--no-sandbox",
                "--disable-blink-features=AutomationControlled"
        );
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        if (cfg.isHeadless()) {
            options.addArguments("--headless");
        }

        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        log.info("Edge driver started");
        return driver;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Internet Explorer
    // ─────────────────────────────────────────────────────────────────────────

    private static WebDriver createInternetExplorer(String driverDir, String os) throws Exception {
        if (os.equalsIgnoreCase("Ubuntu")) {
            Framework.ShowWarning("IE Browser is not supported on Linux");
            return null;
        }
        Monitoring_FrameWork.Taskkill();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("ignoreProtectedModeSettings", true);
        caps.setCapability("acceptSslCerts", true);
        caps.setCapability("unexpectedAlertBehaviour", UnexpectedAlertBehaviour.IGNORE);
        caps.setCapability("requireWindowFocus", true);
        caps.setCapability("enablePersistentHover", true);
        caps.setCapability("nativeEvents", true);
        caps.setCapability("ignoreZoomSetting", true);
        caps.setCapability("--remote-allow-origins=*", true);
        caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        System.setProperty("webdriver.ie.driver", driverDir + "/IEDriverServer.exe");
        InternetExplorerOptions options = new InternetExplorerOptions(caps);
        WebDriver driver = new InternetExplorerDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        log.info("IE driver started");
        return driver;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Safari
    // ─────────────────────────────────────────────────────────────────────────

    private static WebDriver createSafari() {
        SafariDriver driver = new SafariDriver(new SafariOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        log.info("Safari driver started");
        return driver;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Axis Chrome (custom internal variant)
    // ─────────────────────────────────────────────────────────────────────────

    private static WebDriver createAxisChrome(String driverDir, String os,
                                              BrowserConfig cfg) throws Exception {
        String driverPath = driverDir + (os.equalsIgnoreCase("Windows")
                ? "/chromedriver.exe" : "/chromedriver");
        File driverFile = new File(driverPath);
        driverFile.setExecutable(true);
        System.setProperty("webdriver.chrome.driver", driverPath);

        // Axis uses default Chrome options — customise here as needed
        return createChrome(driverDir, os, cfg);
    }
}
