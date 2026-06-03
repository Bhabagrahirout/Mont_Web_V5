package test;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.apmosys.framework.Framework;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestDemo {
	
	public static void main(String[] args) {
		autoAcceptPopup("DEMO_PAGE",8);
	}

	public static void autoAcceptPopup(String pageName, double tTotalTime) {
		
//		JOptionPane.showMessageDialog(null, "OTP is Not Found");
		String msg = "OTP is NULL";
		JOptionPane pane = new JOptionPane("<html><body style='font-family: Arial;'>"
				+ "<p style='color: Green; font-weight: bold;'>" + msg + "</p>"
				+ "<p><b>Instance Name:</b> <span style='color: blue;'>" + Framework.dataSheetName + "</body></html>",
				JOptionPane.WARNING_MESSAGE);

		JDialog dialog = pane.createDialog("Otp Null Alert");

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//10000
		javax.swing.Timer timer = new javax.swing.Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);
		timer.stop();
		dialog.dispose();
		
	
	}




	public static void main111(String[] args) throws InterruptedException, AWTException {

		String str = "Bhaba\1Rout"; // The \1 is an actual non-printable character

		// Find the character that appears before splitting
		char foundChar = '\0'; // Default empty character
		for (char ch : str.toCharArray()) {
			if (ch >= '\u0001' && ch <= '\u0009') { // Check for ASCII 1-9
				foundChar = ch;
				break; // Stop at the first match
			}
		}

		// Split the string on the found character
		String[] parts = str.split("[\u0001-\u0009]");

		// Output the reconstructed string with the detected number
		if (parts.length > 1) {
			System.out.println(parts[0] + "\\\\" + (int) foundChar + parts[1]);
		} else {
			System.out.println(parts[0]); // If no split occurs, just print original part
		}
	}

	public static void main71(String[] args) {

//		int a=Integer.compare(29, 7);// 0,-1,1
		int b = Integer.max(2, 4);
		int c = Integer.min(2, 4);
		int d = Integer.parseInt("0010", 2);

		Integer.parseInt("8");// int
		Integer.valueOf("8");// Integer

	}

	public static void main11(String[] args) {

		List<String> curList = null;

		String str = "InRHYU";
//		curList.add(str);
		if (curList != null && curList.get(0).startsWith("INR")) {
//			if(curList.get(0).startsWith("INR"))
			System.out.println("Match");
		} else
			System.out.println("Not");

	}

	public static void main22(String[] args) {

		ArrayList<String> networkDetails = new ArrayList<String>();

		String networkProvider = "";
		String ip = "";
		String city = "";

		for (int i = 0; i <= 10; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			try {
				RestAssured.baseURI = "https://ipinfo.io/json";
				Response r = RestAssured.given().contentType("application/json").when().get().then().using().extract()
						.response();

				JSONObject jsonObject = new JSONObject(r.getBody().asString());
				ip = jsonObject.getString("ip").trim();
				city = jsonObject.getString("city");
				networkProvider = jsonObject.getString("org").trim();
				if (networkProvider.isEmpty()) {
					networkProvider = jsonObject.getString("asn_org");
				}
				System.out.println("Network Ip is ======== " + ip);
				System.out.println("City is ======== " + city);
				System.out.println("Network Provider is ======== " + networkProvider);
			} catch (Exception e) {
				System.out.println("Trying again for to get network details....");
			}
			if (!ip.isEmpty()) {
				break;
			}
		}
		networkDetails.add(ip);
		networkDetails.add(city);
		networkDetails.add(networkProvider);

		System.out.println();

	}


	public static void main23(String[] args) {

		Calendar.getInstance();

		List<String> prices = new ArrayList<String>();

//		for(int i=0;i<5;i++)
//		{
//			prices.add("22");
//		}
		prices.add("23");
		prices.add("33");
		prices.add("43");
		prices.add("53");
		prices.add("63");
		prices.add("");

//		filter(s -> !s.equals("-")).
		// Use stream().distinct() to find unique prices and check if the prices have
		long distinctCount1 = prices.stream().filter(s -> !s.equals("-")).distinct().count();
		System.out.println(distinctCount1);
		if (distinctCount1 > 1) {
			System.out.println("Price value changed");
		} else {
			System.out.println("Price Values not changed  === " + prices);
			// mail part
		}
	}

	

}
