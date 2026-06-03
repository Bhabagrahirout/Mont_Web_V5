package websiteTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HDFCETF {

	public static void main(String[] args) throws InterruptedException {

		String path = "/home/apmosys/Desktop/Selenium Tools/chromedriver-linux64/chromedriver";
		ChromeOptions options2 = new ChromeOptions();
		options2.addArguments(new String[] { "--remote-allow-origins=*" });

		File file = new File(path);
		file.setExecutable(true);
		System.setProperty("webdriver.chrome.driver", path);

		WebDriver driver = new ChromeDriver(options2);
		driver.get("https://www.hdfcfund.com/product-solutions/overview/hdfc-silver-etf/regular");
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			String actualdate = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("((//div[@class=\"producttooltip\"])[5]/following::p)[1]")))
					.getText();
			actualdate = actualdate.replaceAll("\\(", "").replaceAll("\\)", "").trim();
			System.out.println("actualdate Date ---> " + actualdate);

			Date now = new Date();
			String time = new SimpleDateFormat("HH:mm").format(now);
			int day2 = Integer.parseInt(new SimpleDateFormat("dd").format(now));
			String suffix = getDaySuffix(day2);
			String date = new SimpleDateFormat("d'" + suffix + "' MMMM yyyy").format(now);
			String message = "#captured at " + time + " Hrs on " + date;
			String totalMessage = actualdate + message;

			file = new File("/home/apmosys/Downloads/New Students/A.txt");
			FileWriter fw = new FileWriter(file);
			fw.write(totalMessage);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread.sleep(60000);

		try {
			String expdate = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("((//div[@class=\"producttooltip\"])[5]/following::p)[1]")))
					.getText();
			expdate = expdate.replaceAll("\\(", "").replaceAll("\\)", "").trim();
			System.out.println("ExpDate Date ---> " + expdate);

			file = new File("/home/apmosys/Downloads/New Students/A.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String previousData = sb.toString();
			String readDate = previousData.split("#")[0].trim();
			System.out.println("readDate Date ---> " + readDate);
			br.close();
			fr.close();

			Date now = new Date();
			String time = new SimpleDateFormat("HH:mm").format(now);
			int day2 = Integer.parseInt(new SimpleDateFormat("dd").format(now));
			String suffix = getDaySuffix(day2);
			String date = new SimpleDateFormat("d'" + suffix + "' MMMM yyyy").format(now);
			String message = "#captured at " + time + " Hrs on " + date;
			String totalMeaasage = expdate + message;

			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\n" + totalMeaasage);
			bw.close();
			fw.close();

			if (readDate.equalsIgnoreCase(expdate)) {
				System.out.println("Db write successfully");
			} else {
				System.out.println("data changes.............");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getDaySuffix(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

}
