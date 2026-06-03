package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.apmosys.framework.Framework;

public class PopupDemo extends Thread {
	public static void main(String[] args) {
		autoAcceptPopup("YesBank","1");
	}
	public static void autoAcceptPopup(String pageName, String step) {

		JOptionPane pane = new JOptionPane(
				"<html><body style='font-family: Arial;'>"
						+ "<p style='color: red; font-weight: bold;'>⚠ ERROR DETECTED ⚠</p>"
						+ "<p><b>Instance Name:</b> <span style='color: blue;'>" + Framework.dataSheetName
						+ "</span></p>" + "<p><b>Issue Found on Page:</b> <span style='color: green;'>" + pageName
						+ "</span></p>" + "<p><b>Step No:</b> <span style='color: black;'>" + step + "</span></p>"
						+ "<p style='color: black;'>Please correct your script accordingly.</p>" + "</body></html>",
				JOptionPane.WARNING_MESSAGE);

		JDialog dialog = pane.createDialog("Script Error");

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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

	public static void main2(String[] args) {

		System.out.println("Start.................");
		
		String step = "10";
		String pageName="Login Page";
		JOptionPane pane = new JOptionPane(
				"<html><body style='font-family: Arial;'>"
						+ "<p style='color: red; font-weight: bold;'>⚠ ERROR DETECTED ⚠</p>"
						+ "<p><b>Instance Name:</b> <span style='color: blue;'>ABCD Application</span></p>"
						+ "<p><b>Issue Found on Page:</b> <span style='color: green;'>" + pageName + "</span></p>"
						+ "<p><b>Step No:</b> <span style='color: black;'>" + step + "</span></p>"
						+ "<p style='color: black;'>Please correct your script accordingly.</p>" + "</body></html>",
				JOptionPane.WARNING_MESSAGE);

		JDialog dialog = pane.createDialog("Script Error");

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		Timer timer = new Timer(10000, new ActionListener() {
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

		System.out.println("end..........");

	}

}
