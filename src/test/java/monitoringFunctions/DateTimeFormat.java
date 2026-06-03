package monitoringFunctions;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeFormat {

	
	public static void main(String[] args) throws InterruptedException {

		Calendar lCDateTime = Calendar.getInstance();
		long timeInMillis = lCDateTime.getTimeInMillis();

		// 1.761202390892E12
		// 1.761202421144E12
		System.out.println("tTimeStart" + timeInMillis);
		Thread.sleep(2000);
		lCDateTime = Calendar.getInstance();
		double tTotalTimeDouble = lCDateTime.getTimeInMillis() - timeInMillis;
		System.out.println("tTotalTimelong" + tTotalTimeDouble);
		double tTotalTime = tTotalTimeDouble / 1000.0;

		String sTotaltimeRounded = String.format("%.2f", tTotalTime);
		System.out.println("sTotaltimeRounded:" + sTotaltimeRounded);
	}
	
	
	public static void main21(String[] args) {

//		 October 13, 2025
		LocalDate today = LocalDate.now();
		LocalDate upComingDays = today.plusDays(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd,yyyy");
		String formattedDate = upComingDays.format(formatter);
		System.out.println("Date: " + formattedDate);
		String goingXpath = "//div[contains(@aria-label,'" + formattedDate + "')]";
		
		int year = Calendar.getInstance().get(1);
		String MonthName = new SimpleDateFormat("MMMM").format(new Date());
		int monthday = Calendar.getInstance().get(5);

	}
	
	public static void main2(String[] args) throws InterruptedException {

//		 double lDateTime = (double) new Date().getTime();
		Calendar lCDateTime = Calendar.getInstance();
		double tTimeStart = (double) lCDateTime.getTimeInMillis();

		System.out.println(tTimeStart);

		Thread.sleep(1000);

		lCDateTime = Calendar.getInstance();
		System.out.println((double) lCDateTime.getTimeInMillis());
		double tTotalTimelong = (double) lCDateTime.getTimeInMillis() - tTimeStart;
		double tTotalTime = tTotalTimelong / 1000.0;
		System.out.println("Total Time Is == " + tTotalTime);

		double reduceDouble = Double.parseDouble("2");
		System.out.println("Time Is == " + reduceDouble);
		tTotalTime -= reduceDouble;

		tTotalTime = 0;
		System.out.println("reduced , Time Is == " + tTotalTime);

		if (tTotalTime < 0) {
			tTotalTime = -(tTotalTime);
			System.out.println(" ----" + tTotalTime);
		}

	}


}
