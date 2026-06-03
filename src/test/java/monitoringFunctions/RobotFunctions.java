package monitoringFunctions;

import java.awt.AWTException;
import java.awt.Robot;

public class RobotFunctions {

	public static void main(String[] args) throws AWTException, InterruptedException {
		
		
		
		String actionRDS=null;
		if (actionRDS.equalsIgnoreCase("RB_Nick_name")) {
			Robot rb2 = new Robot();
			rb2.keyPress(83);
			rb2.keyPress(85);
			rb2.keyPress(77);
			rb2.keyPress(65);
			rb2.keyPress(78);
		} else if (actionRDS.equalsIgnoreCase("RB_Name")) {
			Robot rb2 = new Robot();
			rb2.keyPress(83);
			rb2.keyPress(85);
			rb2.keyPress(77);
			rb2.keyPress(65);
			rb2.keyPress(78);
			rb2.keyPress(74);
			rb2.keyPress(65);
			rb2.keyPress(89);
			rb2.keyPress(32);
			rb2.keyRelease(32);
			rb2.keyPress(68);
			rb2.keyPress(69);
			rb2.keyPress(79);
		} else if (actionRDS.equalsIgnoreCase("RB_IFSC")) {
			Robot rb2 = new Robot();
			rb2.keyPress(16);
			rb2.keyPress(85);
			rb2.keyPress(84);
			rb2.keyPress(73);
			rb2.keyPress(66);
			rb2.keyRelease(16);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			rb2.keyPress(56);
			rb2.keyRelease(56);
			rb2.keyPress(56);
			rb2.keyRelease(56);
			rb2.keyPress(56);
			rb2.keyPress(53);
		} else if (actionRDS.equalsIgnoreCase("RB_AC_NO")) {
			Robot rb2 = new Robot();
			rb2.keyPress(57);
			rb2.keyRelease(57);
			Thread.sleep(2001L);
			rb2.keyPress(49);
			rb2.keyRelease(49);
			Thread.sleep(200L);
			rb2.keyPress(52);
			rb2.keyRelease(52);
			Thread.sleep(200L);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			Thread.sleep(200L);
			rb2.keyPress(49);
			rb2.keyRelease(49);
			Thread.sleep(200L);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			Thread.sleep(200L);
			rb2.keyPress(48);
			rb2.keyRelease(48);
			Thread.sleep(200L);
			rb2.keyPress(53);
			rb2.keyRelease(53);
			Thread.sleep(200L);
			rb2.keyPress(54);
			rb2.keyRelease(54);
			Thread.sleep(200L);
			rb2.keyPress(51);
			rb2.keyRelease(51);
			Thread.sleep(200L);
			rb2.keyPress(51);
			rb2.keyRelease(51);
			Thread.sleep(200L);
			rb2.keyPress(50);
			rb2.keyRelease(50);
			Thread.sleep(200L);
			rb2.keyPress(52);
			rb2.keyRelease(52);
			Thread.sleep(200L);
			rb2.keyPress(57);
			rb2.keyRelease(57);
			Thread.sleep(200L);
			rb2.keyPress(57);
			rb2.keyRelease(57);
			Thread.sleep(200L);
		} else if (actionRDS.equalsIgnoreCase("RB_ADDRESS")) {
			Robot rb2 = new Robot();
			rb2.keyPress(84);
			rb2.keyPress(69);
			rb2.keyPress(83);
			rb2.keyPress(84);
		}

	}

}
