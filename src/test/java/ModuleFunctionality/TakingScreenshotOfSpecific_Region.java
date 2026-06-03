package ModuleFunctionality;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class TakingScreenshotOfSpecific_Region {

	public static void main(String[] args) throws AWTException, IOException, InterruptedException {

//		String data = JOptionPane.showInputDialog(args, "enter points");
//
//		System.out.println(data);
//
//		String[] split = data.split("#");
//		// Define region (x, y, width, height)
//		Thread.sleep(2000);
//		int x = Integer.valueOf(split[0]);
//		int y = Integer.valueOf(split[1]);
//		int width = Integer.valueOf(split[2]);
//		int height = Integer.valueOf(split[3]);
		boolean status = true;

		while (status) {

			String data = JOptionPane.showInputDialog(args, "enter points");

			System.out.println(data);

			String[] split = data.split("#");
			// Define region (x, y, width, height)
			Thread.sleep(2000);
			int x = Integer.valueOf(split[0]);
			int y = Integer.valueOf(split[1]);
			int width = Integer.valueOf(split[2]);
			int height = Integer.valueOf(split[3]);
			Rectangle captureRect = new Rectangle(x, y, width, height);

			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(captureRect);

			Date now = new Date();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int monthDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			String monthName = new SimpleDateFormat("MMMM").format(now);

			// Create folder path
			String folderPath = System.getProperty("user.dir") + File.separator + "Logs" + File.separator + year
					+ File.separator + monthName + File.separator + monthDay + File.separator;

			File folder = new File(folderPath);
			folder.mkdirs();

			// Screenshot name
			String time = new SimpleDateFormat("HH_mm_ss").format(now);
			String screenshotName ="Img" + "_" + time + ".jpg";

			String screenshotPath = folderPath + File.separator + screenshotName;

			// Save screenshot
			ImageIO.write(image, "jpg", new File(screenshotPath));
			System.out.println("img path==="+screenshotPath);

			int result = JOptionPane.showConfirmDialog(null, "Continue?", "Confirm", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				status = true;
			} else {
				status = false;
			}
		}

	}

}
