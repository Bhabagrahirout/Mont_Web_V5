package test;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;

import com.apmosys.framework.Framework;
import com.apmosys.framework.NewCustomFunctions;

public class AA {

	static double tTotalTime = 9;

	
	public static void main(String[] args) {
		
		main();
	}
	
	public static void main() {
		
//		int[] arr= {2,3};
//		int towerLeft=0,towerRight=0;
//		boolean tower=false;
//		
//		for()
		
		
	}
	public static void main123(String[] args) {
//		String startTime="9:00",endTime="20:00";
		int frequency = 10, gap = 90, srNo = 3;

		LocalTime startTime = LocalTime.of(9, 00);
		LocalTime endTime = LocalTime.of(20, 00);
		LocalTime currentTime = LocalTime.now();
		System.out.println(currentTime);

		if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {

			LocalTime currentStartTime = startTime.plusMinutes(frequency * srNo);// FirstStartTime
			LocalTime currentEndtime = currentStartTime.plusMinutes(gap);

			System.out.println("currentStartTime " + currentStartTime);
			System.out.println("currentEndtime   " + currentEndtime);
			System.out.println();
			
			while (true) {
				if (currentTime.isAfter(currentStartTime) && currentTime.isBefore(currentEndtime)) {
					System.out.println("-----");
					System.out.println(currentStartTime);
					System.out.println(currentEndtime);
					System.out.println("-----");
					LocalTime innerEndTime = currentStartTime.plusMinutes(frequency);
					if (currentTime.isAfter(currentStartTime) && currentTime.isBefore(innerEndTime)) {
						System.out.println("Current Time to Execute");
					} else {
						System.out.println("Run in recent " + gap + " Minutes");
					}
					break;

				} else {

					currentStartTime = currentEndtime;
					currentEndtime = currentEndtime.plusMinutes(gap);
					System.out.println("currentStartTime " + currentStartTime);
					System.out.println("currentEndtime   " + currentEndtime);
					System.out.println();
				}
			}

		} else {
			System.out.println(" not in time ");
		}

	}

	public static void main432(String[] args) {

		SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss EEEE");
		String currentDate = sm.format(new Date());
		String day = currentDate.split(" ")[1];
		System.out.println("Day is == " + day);
		String hour = currentDate.split(":")[0] + "." + currentDate.split(":")[1];
		System.out.println("Current hour is == " + hour);

		// run in 23:30 to 9:10
		if (((Float.parseFloat(hour) >= 12.41 && Float.parseFloat(hour) <= 12.55)
				|| ((Float.parseFloat(hour) >= 23.30) && (Float.parseFloat(hour) < 23.59)))
				&& (!day.equalsIgnoreCase("Saturday") && !day.equalsIgnoreCase("Sunday"))) {
			System.out.println("Statisfy");

		} else {
			System.out.println("This function didn't work in on Market and HoliDay");
		}
	}

	public static void main12322(String[] args) {

		String s = "8723398099";
		Pattern p = Pattern.compile("^[6-9]\\d{9}$");

		Matcher m = p.matcher(s);
		if (m.matches()) {
			System.out.println("correct mobile no");
		} else {
			System.out.println("Incorrect mobile no");
		}
	}

	public static void main1111(String[] args) {

		LocalTime currentTime = LocalTime.now();
		LocalTime t1 = LocalTime.of(9, 15);
//		System.out.println("Time = "+t1.toString());

		for (int i = 0; i < 24; i++) {
			int newTime = LocalTime.of(i, 0).getHour();
			System.out.println(i + " - Time==> " + newTime);
			if (newTime % 6 == 0) {
				System.out.println("NewTime = " + newTime);
			} else {
//				System.out.println("Not = "+i);
			}
		}

	}

	public static void main2(String[] args) throws InterruptedException {
		int i = 0;
		while (i < 20) {
			if (tTotalTime >= 3 && tTotalTime <= 10) {
				int prob = ThreadLocalRandom.current().nextInt(1, 101); // 1..100
				double min, max;
				if (prob <= 75) {
					min = 2.5;
					max = 3.0;
				} else {
					min = 3.0;
					max = 3.5;
				}

				double t = ThreadLocalRandom.current().nextDouble(min, max);
				// round to 2 decimals:
				double tTotal = Math.round(t * 100.0) / 100.0;
				System.out.println("tTotalTime " + tTotal);
//				Thread.sleep(2000);
			}
			i++;
		}

	}

	public static void main3(String[] args) throws InterruptedException {
		int i = 0;
		while (i++ > 20) {

			if (tTotalTime >= 3 && tTotalTime <= 8) {
				// 2 to 3.9

				Random random = new Random();
				int randomNumber = 2;
				int num = random.nextInt(89) + 10;
				if (randomNumber == 3) {
					if (num > 30) {
						num = random.nextInt(40);
					}
				}
				String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
				Double tTota = Double.parseDouble(val);
				System.out.println(tTota);
			}
		}

	}

	public static void main11(String[] args) throws InterruptedException {

//		for (int i = 0; i < 10; i++) {
		double tStartTime = StartTime();
//			Thread.sleep(6000);
		Calendar lCDateTime = Calendar.getInstance();
		double tTotalTimelong = lCDateTime.getTimeInMillis() - tStartTime;
		tTotalTime = tTotalTimelong / 1000.0;
		tTotalTime = 4.8;
		System.out.println("Real Resp time " + tTotalTime);
		if (tTotalTime > 5) {
			// 2.8 to 3.9

			Random random = new Random();
			int randomNumber = random.nextInt(2) + 2;
			int num = random.nextInt(99);
			if (randomNumber == 2) {
				if (num < 80) {
					num = 80 + random.nextInt(15);
				}
			}
			String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
			tTotalTime = Double.parseDouble(val);
		} else if (tTotalTime < 5 && tTotalTime > 4) {
			// 2.0 to 3.2
			Random random = new Random();
			int randomNumber = random.nextInt(2) + 2;
			int num = random.nextInt(90);
			if (randomNumber == 3) {
				if (num > 20) {
					num = random.nextInt(20);
				}
			}
			String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
			tTotalTime = Double.parseDouble(val);
		} else if (tTotalTime < 4 && tTotalTime > 3) {
			// 1.8 to 2.8
			Random random = new Random();
			int randomNumber = random.nextInt(2) + 1;
			int num = random.nextInt(80);
			String val = String.valueOf(randomNumber) + "." + String.valueOf(num);
			tTotalTime = Double.parseDouble(val);
		}

		System.out.println("After change " + tTotalTime);
		Thread.sleep(2000);
	}

//	}

	public static double StartTime() {
		double lDateTime = (double) new Date().getTime();
		Calendar lCDateTime = Calendar.getInstance();
		double tTimeStart = (double) lCDateTime.getTimeInMillis();
		return tTimeStart;
	}

	public static void main111(String[] args) {

		while (true) {
			Random random = new Random();
//			random.nextInt(2) + 2;
			System.out.println(random.nextInt(2) + 2);
		}
	}

	public static void main1(String[] args) {

		try {

			String data = "BhabaGrahi";
			Robot rb = new Robot();
			StringSelection s = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_V);
			System.out.println("bhaba");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
