package chromeTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AllowCamera {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "--remote-allow-origins=*" });
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("prefs", Map.of("profile.default_content_setting_values.geolocation", 1));

//		Map<String, Object> prefs = new HashMap<>();
//		prefs.put("hardware.audio_capture_enabled", true);
//		prefs.put("hardware.video_capture_enabled", true);
//		prefs.put("hardware.audio_capture_allowed_urls", Arrays.asList("https://teams.live.com/"));
//		prefs.put("hardware.video_capture_allowed_urls", Arrays.asList("https://teams.live.com/"));

		// Add the preferences to ChromeOptions
//		options.setExperimentalOption("prefs", prefs);

		options.addArguments("--use-fake-ui-for-media-stream");

		WebDriver driver = new ChromeDriver(options);
		driver.get("https://teams.live.com/meet/9325479498925?p=YLwo2aYxCPXvMD1zIt");

		driver.manage().window().maximize();

	}
}
