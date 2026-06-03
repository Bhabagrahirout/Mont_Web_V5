package monitoringFunctions;

import java.awt.Frame;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.apmosys.framework.Framework;
import com.apmosys.framework.Monitoring_FrameWork;

public class FunctionLists {

	static String actionRDS = "";
	private static String propertyValueRDS;
	private static WebElement webElementVal;
	private static String controlRDS;
	private static String actualResult;
	private static String pageName;
	private static String dataFieldRDS;

	public static void main(String[] args) throws Exception {

		if (actionRDS.equalsIgnoreCase("AXISCODEBOX")) {
			Frame frame15 = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame15, "Enter OTP for E-verification");
			Framework.driver.findElement(By.xpath("//*[@id='otpBox']/input")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("INPUTBOX_CAPTCHA")) {
			Frame frame = new Frame();
			frame.setVisible(true);
			frame.toFront();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter CAPTCHA for Verification");
			Framework.driver.findElement(By.xpath("//*[@name='loginCaptchaValue']"))
					.sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("CAPTCHAAXISMF")) {
			final Frame frame = new Frame();
			final String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("txt_Captcha")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("CAPTCHAFASTAG")) {
			final Frame frame = new Frame();
			final String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("m_tbCaptcha")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("SARASWATOTPBOX")) {
			final Frame frame15 = new Frame();
			final String otp4 = JOptionPane.showInputDialog(frame15, "Enter OTP for E-verification");
			Framework.driver.findElement(By.xpath(propertyValueRDS)).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("SARASWATCAPTCHA")) {
			final Frame frame15 = new Frame();
			final String otp4 = JOptionPane.showInputDialog(frame15, "Enter Captcha For Saraswat");
			Framework.driver.findElement(By.xpath(propertyValueRDS)).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("AegonOTP")) {
			final Frame frame = new Frame();
			final String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver
					.findElement(By.xpath(
							"/html/body/aegon-app/aegon-iterm/aegon-iterm-plus-summary/div/form/div/div[1]/input"))
					.sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("LIBERTYBIKEOTP")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("txtOTP")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("LIBERTYHEALTOTP")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("txtOneTimePassword")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("ETTWGPAYLATEROTP")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("txtOtp")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("OTPDIALOGBOXPAYNOW")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("verifyotp")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("PANOTPBOX")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("myPanDownloadOTP")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("OTPIDFCMF")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.xpath("//*[@id='otp-container']/div/div[1]/input"))
					.sendKeys(new CharSequence[] { otp4 });
			Framework.driver.findElement(By.xpath("//*[@id='otp-container']/div/div[2]/input"))
					.sendKeys(new CharSequence[] { otp4 });
			Framework.driver.findElement(By.xpath("//*[@id='otp-container']/div/div[3]/input"))
					.sendKeys(new CharSequence[] { otp4 });
			Framework.driver.findElement(By.xpath("//*[@id='otp-container']/div/div[4]/input"))
					.sendKeys(new CharSequence[] { otp4 });
			Framework.driver.findElement(By.xpath("//*[@id='otp-container']/div/div[5]/input"))
					.sendKeys(new CharSequence[] { otp4 });
			Framework.driver.findElement(By.xpath("//*[@id='otp-container']/div/div[6]/input"))
					.sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("OTPPAYLATER")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("txtOTP")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("CLOSEB")) {
			String s51 = Framework.driver.findElement(By.xpath("//*[@id='vbrk']")).getText();
			System.out.println("close - " + s51);
			if (s51.contains("Close")) {
				Framework.driver.findElement(By.xpath("//*[@id='vbrk']")).click();
			}
		} else if (actionRDS.equalsIgnoreCase("INPUTBOX_CAPTCHACHECK1")) {
			System.out.println("MessageBox.............................!");
			Frame frame = new Frame();
			frame.setVisible(false);
			frame.toFront();
			String activationcode = JOptionPane.showInputDialog(frame, "Enter the result for verification");
			String id_value = webElementVal.getAttribute("id");
			if (id_value.equalsIgnoreCase("nlpAnswer")) {
				webElementVal.sendKeys(new CharSequence[] { activationcode });
			} else if (Framework.driver.findElement(By.id("fDgdIpXqzymRBtkUb")).isDisplayed()) {
				final Frame frame13 = new Frame();
				frame13.setVisible(false);
				frame13.toFront();
				JOptionPane.showMessageDialog(frame13, "Please Click on logo");
			} else {
				webElementVal = Framework.driver.findElement(By.id("captcha"));
				webElementVal.sendKeys(new CharSequence[] { activationcode });
			}
		} else if (actionRDS.equalsIgnoreCase("ABCMFOTPBOX")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.name("otpCode")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("FUNDOTP")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("orignipdef")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("DIPOTP")) {
			Frame frame = new Frame();
			String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("Input.RBAA.Ezidentity.otp")).sendKeys(new CharSequence[] { otp4 });
		} else if (actionRDS.equalsIgnoreCase("OTPDIALOGBOX")) {
			final Frame frame = new Frame();
			final String otp4 = JOptionPane.showInputDialog(frame, "Enter OTP for E-verification");
			Framework.driver.findElement(By.id("txtotp")).sendKeys(new CharSequence[] { otp4 });
		}
		
		else if(actionRDS.equalsIgnoreCase("WAIT_CHECK_VISIBILITY"))
		{
			Thread.sleep(5000);
			try {
				actualResult = String.valueOf(webElementVal.isDisplayed());
				System.out.println("Actual Result is == " + actualResult);
			} catch (Exception e2) {
				Framework.errorsatus = "1";
				Framework.errorpagename = pageName;
				e2.printStackTrace();
				// Framework.TakeScreenshots();
				// System.out.println("Screenshot to be Added " +
				// Framework.ScreenshotfileLocation);
				// Framework.extent.log(LogStatus.FAIL, "<br> Runtime Error <br>"
				// + Framework.extent.addScreenCapture(Framework.ScreenshotfileLocation));
				// Framework.extentrpt.flush();
				Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
			}
			System.out.println(actionRDS);
			if (controlRDS.equalsIgnoreCase("V")) {
				Monitoring_FrameWork.SaveResult(actualResult, dataFieldRDS);
//				break;
			}
//			break;
		}

	}

}
