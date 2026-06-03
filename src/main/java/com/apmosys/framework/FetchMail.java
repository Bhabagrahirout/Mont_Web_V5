// 
// Decompiled by Procyon v0.5.36
// 

package com.apmosys.framework;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

public class FetchMail {
	public static int messageCount;
	public static int unreadMsgCount;
	public static String emailSubject;
	public static Message emailMessage;

	public static void sendAlertMail(String subject, String messageBody) {

		String host = "apmosys.icewarpcloud.in";
		String port = "587";
		String from = Framework.pro.getProperty("mailFrom");
		String password = Framework.pro.getProperty("password");
		String toAddress = Framework.pro.getProperty("mailTo");
		String mailCc = Framework.pro.getProperty("mailCc");
		
//		String from ="vansh.choudhary@apmosys.com";
//		String password = "Dalchand@151";
//		String toAddress = "bhabagrahi.rout@apmosys.com";
//		String mailCc ="amar.jena@apmosys.com";

		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.user", from);
			properties.put("mail.password", password);
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			};

			Session session = Session.getInstance(properties, auth);
			Message msg = (Message) new MimeMessage(session);
			msg.setFrom((Address) new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(toAddress));
			if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
				msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
			}

			msg.setSubject(subject);

			msg.setSentDate(new Date());
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			System.out.println("Creates message part");
//			messageBodyPart.setText(message); *****


			messageBodyPart.setContent(messageBody, "text/html");
			Multipart multipart = (Multipart) new MimeMultipart();

			// for attach screenshot
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource(new File(Framework.ScreenshotfileLocation));
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(new File(Framework.ScreenshotfileLocation).getName());
			System.out.println("Creates multi-part");

			multipart.addBodyPart((BodyPart) messageBodyPart);
			multipart.addBodyPart((BodyPart) messageBodyPart2);

			msg.setContent(multipart);
			System.out.println("Sending mail....");
			Transport.send(msg);
			System.out.println("Mail has been sent....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getResetLinkForAxis(String host, String username, String password, String port,
			String subject) {

		Date now = new Date();
		int count = 1;
		// Define the desired date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

		// Format the date and time according to the specified format
		String currentTime = dateFormat.format(now);

		boolean flag = true;

		Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.smtp.port", port);
		sysProps.setProperty("mail.store.protocol", "imaps");
		String link = null;
		Store store = null;
		try {
			Session session = Session.getInstance(sysProps, null);
			store = session.getStore();

			try {
				store.connect(host, username, password);
			} catch (Exception e1) {
				try {
					Thread.sleep(1000);
					System.out.println("Reconnecting................");
					store.connect(host, username, password);
				} catch (Exception e) {
					try {
						Thread.sleep(1000);
						System.out.println("Reconnecting................");
						store.connect(host, username, password);
					} catch (Exception e3) {
						try {
							Thread.sleep(1000);
							System.out.println("Reconnecting................");
							store.connect(host, username, password);
						} catch (Exception e2) {
							System.out.println("Could not connect......");
							e3.printStackTrace();
						}

					}
				}
			}

		} catch (Exception mex) {
			mex.printStackTrace();
		}

		while (count < 16) {

			try {
				Folder emailInbox = store.getFolder("INBOX");
				emailInbox.open(Folder.READ_WRITE);
				messageCount = emailInbox.getMessageCount();
				System.out.println("Total Message Count: " + messageCount);
				unreadMsgCount = emailInbox.getUnreadMessageCount();
				System.out.println("Unread Emails count:" + unreadMsgCount);
				System.out.println("New Emails count:" + emailInbox.getNewMessageCount());
				// emailInbox.get

				for (int i = 0; i < 20; i++) {
					emailMessage = emailInbox.getMessage(messageCount);
					Date maildate = emailMessage.getReceivedDate();
					System.out.println("mail date is == " + maildate.toString());
					emailSubject = emailMessage.getSubject();
//				System.out.println("Mail Subject is === " + emailSubject.toString());
					long diffMinute = diffMailTime(currentTime, maildate.toString());
					if (diffMinute <= 3) {
						if (emailSubject.toString().equalsIgnoreCase(subject.trim())) {
							System.out.println("Mail Subject is === " + emailSubject.toString());
							String contentType = emailMessage.getContentType();
							String result = "";
							if (contentType.contains("multipart")) {
								// content may contain attachments
								MimeMultipart multiPart = (MimeMultipart) emailMessage.getContent();
								result = getTextFromMimeMultipart(multiPart);
							} else {
								result = emailMessage.getContent().toString();
							}
							System.out.println("Total Body get === " + result);

							link = result.split("Banking password:")[1].split("For any further assistance")[0].trim();
							System.out.println("Link Is === " + link);

							emailMessage.setFlag(Flags.Flag.SEEN, true);
							emailInbox.close(true);
							store.close();
							flag = false;
							break;
						} else {
							messageCount--;
						}
					} else {
						messageCount--;
					}

				}
				System.out.println("Searching Again ...... ");
			} catch (Exception e) {
				System.out.println("Exception occured in read mail == " + e.getMessage());
				// TODO: handle exception
			}
			if (flag == false) {
				break;
			}
			if (count == 15) {
				JOptionPane.showMessageDialog(new Frame(), "Link for login credentials did not received");
			}
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
			}
			count++;

		}

		return link;
	}
//	
	
	
	
	public static void main(String[] args) {
		getLinkForAxis("smtp.gmail.com", "kshirsagarsanket638@gmail.com", "jnsxgyltqgpgwgtx", "587", "Link for login credentials");
	}
	public static String getLinkForAxis(String host, String username, String password, String port, String subject) {

		Date now = new Date();
		int count = 1;
		// Define the desired date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

		// Format the date and time according to the specified format
		String currentTime = dateFormat.format(now);

		boolean flag = true;

		Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.smtp.port", port);
		sysProps.setProperty("mail.store.protocol", "imaps");
		String link = null;
		Store store = null;
		try {
			Session session = Session.getInstance(sysProps, null);
			store = session.getStore();

			try {
				store.connect(host, username, password);
			} catch (Exception e1) {
				try {
					Thread.sleep(1000);
					System.out.println("Reconnecting................");
					store.connect(host, username, password);
				} catch (Exception e) {
					try {
						Thread.sleep(1000);
						System.out.println("Reconnecting................");
						store.connect(host, username, password);
					} catch (Exception e3) {
						try {
							Thread.sleep(1000);
							System.out.println("Reconnecting................");
							store.connect(host, username, password);
						} catch (Exception e2) {
							System.out.println("Could not connect......");
							e3.printStackTrace();
						}

					}
				}
			}

		} catch (Exception mex) {
			mex.printStackTrace();
		}

		while (count < 16) {

			try {
				Folder emailInbox = store.getFolder("INBOX");
				emailInbox.open(Folder.READ_WRITE);
				messageCount = emailInbox.getMessageCount();
				System.out.println("Total Message Count: " + messageCount);
				unreadMsgCount = emailInbox.getUnreadMessageCount();
				System.out.println("Unread Emails count:" + unreadMsgCount);
				System.out.println("New Emails count:" + emailInbox.getNewMessageCount());
				// emailInbox.get

				for (int i = 0; i < 20; i++) {
					emailMessage = emailInbox.getMessage(messageCount);
					Date maildate = emailMessage.getReceivedDate();
					System.out.println("mail date is == " + maildate.toString());
					emailSubject = emailMessage.getSubject();
//				System.out.println("Mail Subject is === " + emailSubject.toString());
					long diffMinute = diffMailTime(currentTime, maildate.toString());
					if (diffMinute <= 3) {
						if (emailSubject.toString().equalsIgnoreCase(subject.trim())) {
							System.out.println("Mail Subject is === " + emailSubject.toString());
							String contentType = emailMessage.getContentType();
							String result = "";
							if (contentType.contains("multipart")) {
								// content may contain attachments
								MimeMultipart multiPart = (MimeMultipart) emailMessage.getContent();
								result = getTextFromMimeMultipart(multiPart);
							} else {
								result = emailMessage.getContent().toString();
							}
							System.out.println("Total Body get === " + result);

							link = result.split("Login ID")[1].split("For any further")[0].trim().split(" ")[1];
							System.out.println("Link Is === " + link);

							emailMessage.setFlag(Flags.Flag.SEEN, true);
							emailInbox.close(true);
							store.close();
							flag = false;
							break;
						} else {
							messageCount--;
						}
					} else {
						messageCount--;
					}

				}
				System.out.println("Searching Again ...... ");
			} catch (Exception e) {
				System.out.println("Exception occured in read mail == " + e.getMessage());
				// TODO: handle exception
			}
			if (flag == false) {
				break;
			}
			if (count == 15) {
				JOptionPane.showMessageDialog(new Frame(), "Link for login credentials did not received");
			}
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
			}
			count++;

		}

		return link;
	}
	public static long diffMailTime(String currentTime, String mailTime) {
		long differenceInMinutes = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

			// Parse the date strings into Date objects
			Date date1 = dateFormat.parse(mailTime);
			Date date2 = dateFormat.parse(currentTime);

			// Calculate the difference in milliseconds
			long differenceInMillis = date2.getTime() - date1.getTime();

			// Convert milliseconds to minutes
			differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);

			// Print the result
			System.out.println("Difference in minutes: " + differenceInMinutes + " minutes");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return differenceInMinutes;

	}
	public static String iciciIPRUOtp(String host, String username, String password, String port, String subject) {

		Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.smtp.port", port);
		sysProps.setProperty("mail.store.protocol", "imaps");
		String emandateOtp = null;
		try {
			Session session = Session.getInstance(sysProps, null);
			Store store = session.getStore();

			try {
				store.connect(host, username, password);
			} catch (Exception e1) {
				try {
					Thread.sleep(1000);
					System.out.println("Reconnecting................");
					store.connect(host, username, password);
				} catch (Exception e) {
					try {
						Thread.sleep(1000);
						System.out.println("Reconnecting................");
						store.connect(host, username, password);
					} catch (Exception e3) {
						try {
							Thread.sleep(1000);
							System.out.println("Reconnecting................");
							store.connect(host, username, password);
						} catch (Exception e2) {
							System.out.println("Could not connect......");
							e3.printStackTrace();
						}

					}
				}
			}

			Folder emailInbox = store.getFolder("INBOX");
			emailInbox.open(Folder.READ_WRITE);
			messageCount = emailInbox.getMessageCount();
			System.out.println("Total Message Count: " + messageCount);
			unreadMsgCount = emailInbox.getUnreadMessageCount();
			System.out.println("Unread Emails count:" + unreadMsgCount);
			System.out.println("New Emails count:" + emailInbox.getNewMessageCount());
			// emailInbox.get

			for (int i = 0; i < 500; i++) {
				emailMessage = emailInbox.getMessage(messageCount);
//				Date maildate = emailMessage.getSentDate();
//				System.out.println("mail date is == " + maildate.toString());
				emailSubject = emailMessage.getSubject();
				System.out.println("Mail Subject is === " + emailSubject.toString());
				if (emailSubject != null) {
					if (emailSubject.toString().equalsIgnoreCase(subject)) {
//						System.out.println("mail date is under condition == " + maildate.toString());
						System.out.println("Subject:: " + emailSubject);
						String contentType = emailMessage.getContentType();
						String result = "";
						if (contentType.contains("multipart")) {
							// content may contain attachments
							MimeMultipart multiPart = (MimeMultipart) emailMessage.getContent();
							result = getTextFromMimeMultipart(multiPart);
						} else {
							result = emailMessage.getContent().toString();
						}
						System.out.println("Total Body get === " + result);

						emandateOtp = extractOTP(result);

						System.out.println("OTP Is  == " + emandateOtp);

						// System.out.println(otp);

						emailMessage.setFlag(Flags.Flag.SEEN, true);
						emailInbox.close(true);
						store.close();
						break;
					} else {
						messageCount--;
					}
				} else {
					messageCount--;
				}

			}

		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return emandateOtp;
	}

	
	public static String extractOTP(String message) {
		String otp = null;
		Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			otp = matcher.group();
		}
		return otp;
	}
	

	public static String getResetLinkAXIS(String host, String username, String password, String port, String subject) {
		Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.smtp.port", port);
		sysProps.setProperty("mail.store.protocol", "imaps");
		String link = null;

		try {
			Session session = Session.getInstance(sysProps, null);
			Store store = session.getStore();

			try {
				store.connect(host, username, password);
			} catch (Exception e1) {
				try {
					Thread.sleep(1000);
					System.out.println("Reconnecting................");
					store.connect(host, username, password);
				} catch (Exception e) {
					try {
						Thread.sleep(1000);
						System.out.println("Reconnecting................");
						store.connect(host, username, password);
					} catch (Exception e3) {
						try {
							Thread.sleep(1000);
							System.out.println("Reconnecting................");
							store.connect(host, username, password);
						} catch (Exception e2) {
							System.out.println("Could not connect......");
							e3.printStackTrace();
						}

					}
				}
			}

			Folder emailInbox = store.getFolder("INBOX");
			emailInbox.open(Folder.READ_WRITE);
			messageCount = emailInbox.getMessageCount();
			System.out.println("Total Message Count: " + messageCount);
			unreadMsgCount = emailInbox.getUnreadMessageCount();
			System.out.println("Unread Emails count:" + unreadMsgCount);
			System.out.println("New Emails count:" + emailInbox.getNewMessageCount());
			// emailInbox.get

			for (int i = 0; i < 60; i++) {
				emailMessage = emailInbox.getMessage(messageCount);
				Date maildate = emailMessage.getSentDate();
				System.out.println("mail date is == " + maildate.toString());
				emailSubject = emailMessage.getSubject();
				System.out.println("Mail Subject is === " + emailSubject.toString());
				if (emailSubject != null) {
					if (emailSubject.toString().equalsIgnoreCase(subject.trim())) {
						System.out.println("mail date is under condition == " + maildate.toString());
						System.out.println("Subject:: " + emailSubject);
						String contentType = emailMessage.getContentType();
						String result = "";
						if (contentType.contains("multipart")) {
							// content may contain attachments
							MimeMultipart multiPart = (MimeMultipart) emailMessage.getContent();
							result = getTextFromMimeMultipart(multiPart);
						} else {
							result = emailMessage.getContent().toString();
						}
//						System.out.println("Total Body get === " + result);
						link = result.split("Banking password:")[1].split("For any further assistance")[0].trim();
						System.out.println("Link Is === " + link);

						emailMessage.setFlag(Flags.Flag.SEEN, true);
						emailInbox.close(true);
						store.close();
						break;
					} else {
						messageCount--;
					}
				} else {
					messageCount--;
				}

			}

		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return link;
	}

	private static String getTextFromMessage(final Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			final MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private static String getTextFromMimeMultipart(final MimeMultipart mimeMultipart)
			throws MessagingException, IOException {
		String result = "";
		for (int count = mimeMultipart.getCount(), i = 0; i < count; ++i) {
			final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			}
			if (bodyPart.isMimeType("text/html")) {
				final String html = (String) bodyPart.getContent();
				result = result + "\n" + Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result += getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	public static String getOtp(final String host, final String username, final String password, final String dataField,
			final String port) {
		final Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.smtp.port", port);
		sysProps.setProperty("mail.store.protocol", "imaps");
		String otp = null;
		try {
			final Session session = Session.getInstance(sysProps, (Authenticator) new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			final Store store = session.getStore();
			store.connect(host, username, password);
			final Folder emailInbox = store.getFolder("INBOX");
			emailInbox.open(2);
			FetchMail.messageCount = emailInbox.getMessageCount();
			System.out.println("Total Message Count: " + FetchMail.messageCount);
			FetchMail.unreadMsgCount = emailInbox.getNewMessageCount();
			System.out.println("Unread Emails count:" + FetchMail.unreadMsgCount);
			FetchMail.emailMessage = emailInbox.getMessage(FetchMail.messageCount);
			FetchMail.emailSubject = FetchMail.emailMessage.getSubject();
			System.out.println("Subject:: " + FetchMail.emailSubject);
			final String contentType = FetchMail.emailMessage.getContentType();
			String result = "";
			if (contentType.contains("multipart")) {
				final MimeMultipart multiPart = (MimeMultipart) FetchMail.emailMessage.getContent();
				result = getTextFromMimeMultipart(multiPart);
			} else {
				result = FetchMail.emailMessage.getContent().toString();
				System.out.println("Result Is == " + result);
			}
			if (dataField.equalsIgnoreCase("PANOTP")) {
				System.out.println("You are Under PanOTP");
				final String[] strArray = result.split("Connect is ");
				otp = strArray[1];
				otp = otp.split("\\.")[0].trim();
			} else if (dataField.equalsIgnoreCase("FORGOTOTP")) {
				System.out.println("You are Under ForgotOTP");
				final String[] strArray = result.split(">");
				otp = strArray[1];
				otp = otp.trim().split(" ")[0].trim();
			} else if (dataField.equalsIgnoreCase("SHCIL")) {
				System.out.println("You are Under SHCIL" + result);
				final String[] strArray = result.split("Dear PANKAJ A PATIL,</p>");
				otp = strArray[1];
				otp = otp.trim().split("is the one time")[0].trim();
				otp = otp.replace("<p>", "");
			} else if (dataField.equalsIgnoreCase("NSDL")) {
				System.out.println("OTP FOR NSDL");
				final String[] strArray = result.split("contribution is");
				otp = strArray[1];
				otp = otp.trim().split("\\.")[0].trim();
			} else if (dataField.equalsIgnoreCase("UTKARSH")) {
				System.out.println("You are Under UTKARSH_OTP");
				final String[] strArray = result.split("is");
				otp = strArray[0].trim();
				otp = otp.split(",")[1].trim();
			} else if (dataField.equalsIgnoreCase("UTKBNK")) {
				System.out.println("You are Under UTKARSH_OTP");
				final String[] strArray = result.split("Dear Customer,");
				otp = strArray[1];
				otp = otp.trim().split(" is")[0].trim();
			} else if (dataField.equalsIgnoreCase("AXISMF")) {
				try {
					System.out.println("You are Under AxisMF_OTP");
					final String[] strArray = result.split("OTP is");
					otp = strArray[1];
					otp = otp.trim().split("\\.")[0].trim();
				} catch (Exception e) {
					System.out.println("Inside catch block");
				}
			} else if (dataField.equalsIgnoreCase("AxisApproval")) {
				try {
					System.out.println("You are Under AxisApproval otp");
					final String[] strArray = result.split("<p>");
					otp = strArray[1];
					otp = otp.trim().split(" is the OTP")[0].trim();
				} catch (Exception e) {
					System.out.println("Inside catch block");
				}
			} else if (dataField.equalsIgnoreCase("Digio")) {
				final String[] strArray = result.split("Your One Time Security Code is: ");
				otp = strArray[1];
				otp = otp.split("\\.")[0].trim();
				System.out.println("OTP Is : " + otp);
			} else if (dataField.equalsIgnoreCase("5PaisaOTP")) {
				final String[] strArray = result
						.split("<span style='color:#C9354D;font-size:13px;text-align:center;font-weight: bold;'>");
				otp = strArray[1];
				otp = otp.split("</span>. This OTP")[0].trim();
				System.out.println("OTP is " + otp);
			} else if (dataField.equalsIgnoreCase("APPRLOGIN")) {
				final String[] strArray = result.split("code is: ");
				otp = strArray[1];
				otp = otp.split("For")[0].trim();
			} else if (dataField.equalsIgnoreCase("url")) {
				System.out.println("fetching url from mail");
				final String[] strArray = result.split("Please use this link ");
				otp = strArray[1];
				otp = otp.trim().split("\\ ")[0].trim();
				System.out.println("URL is:      " + otp);
			} else if (dataField.equalsIgnoreCase("mailsubject")) {
				System.out.println("mail subject");
				FetchMail.emailSubject = FetchMail.emailMessage.getSubject();
				System.out.println("Mail subject is==================" + FetchMail.emailSubject);
			} else if (dataField.equalsIgnoreCase("LeavePortal")) {
				otp = result.split("Please find your otp")[1].trim();
			} else if (dataField.equalsIgnoreCase("JMF")) {
				try {
					System.out.println("You are Under JM Financial otp");
					if (result.contains("Greetings from JM Financial Services Ltd!<br/><br/>")) {
						final String[] strArray = result.split("Services Ltd!<br/><br/>");
						otp = strArray[1];
						otp = otp.trim().split(" is")[0].trim();
					} else {
						final String[] strArray = result.split("Services Ltd!");
						otp = strArray[1];
						otp = otp.trim().split(" is")[0].trim();
					}
				} catch (Exception e) {
					System.out.println("Inside catch block");
				}
			} else if (dataField.equalsIgnoreCase("Edelwelss")) {
				try {
					System.out.println("You are Under Edelwelss otp");
					final String[] strArray = result.split("<strong style=\"font-size: 22px;color: #2857a4;\">");
					otp = strArray[1];
					otp = otp.trim().split("</strong>")[0].trim();
				} catch (Exception e) {
					System.out.println("Inside catch block");
					e.printStackTrace();
				}
			} else {
				System.out.println("You have Entered Wrong Field detail");
			}
			FetchMail.emailMessage.setFlag(Flags.Flag.SEEN, true);
			emailInbox.close(true);
			store.close();
		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return otp;
	}

	public static String getOtp1(final String host, final String username, final String password,
			final String dataField, final String port) {
		final Properties sysProps = System.getProperties();
		sysProps.setProperty("mail.smtp.port", port);
		sysProps.setProperty("mail.store.protocol", "imaps");
		String otp = null;
		try {
			final Session session = Session.getInstance(sysProps, (Authenticator) null);
			final Store store = session.getStore();
			store.connect(host, username, password);
			final Folder emailInbox = store.getFolder("INBOX");
			emailInbox.open(2);
			FetchMail.messageCount = emailInbox.getMessageCount();
			System.out.println("Total Message Count: " + FetchMail.messageCount);
			FetchMail.unreadMsgCount = emailInbox.getUnreadMessageCount();
			System.out.println("Unread Emails count:" + FetchMail.unreadMsgCount);
			System.out.println("New Emails count:" + emailInbox.getNewMessageCount());
			for (int i = 0; i < 5; ++i) {
				FetchMail.emailMessage = emailInbox.getMessage(FetchMail.messageCount);
				FetchMail.emailSubject = FetchMail.emailMessage.getSubject();
				if (FetchMail.emailSubject != null) {
					if (FetchMail.emailSubject.toString().equalsIgnoreCase(Functions.objectType)) {
						System.out.println("Subject:: " + FetchMail.emailSubject);
						final String contentType = FetchMail.emailMessage.getContentType();
						String result = "";
						if (contentType.contains("multipart")) {
							final MimeMultipart multiPart = (MimeMultipart) FetchMail.emailMessage.getContent();
							result = getTextFromMimeMultipart(multiPart);
						} else {
							result = FetchMail.emailMessage.getContent().toString();
						}
						System.out.println(result);
						if (dataField.equalsIgnoreCase("PANOTP")) {
							System.out.println("You are Under PanOTP");
							final String[] strArray = result.split("Connect is ");
							otp = strArray[1];
							otp = otp.split("\\.")[0].trim();
						} else if (dataField.equalsIgnoreCase("FORGOTOTP")) {
							System.out.println("You are Under ForgotOTP");
							final String[] strArray = result.split(">");
							otp = strArray[1];
							otp = otp.trim().split(" ")[0].trim();
						} else if (dataField.equalsIgnoreCase("NSDL")) {
							System.out.println("OTP FOR NSDL");
							final String[] strArray = result.split("contribution is");
							otp = strArray[1];
							otp = otp.trim().split("\\.")[0].trim();
						} else if (dataField.equalsIgnoreCase("UTKARSH")) {
							System.out.println("You are Under UTKARSH_OTP");
							final String[] strArray = result.split("is");
							otp = strArray[0].trim();
							otp = otp.split(",")[1].trim();
						} else if (dataField.equalsIgnoreCase("UTKBNK")) {
							System.out.println("You are Under UTKARSH_OTP");
							final String[] strArray = result.split("Dear Customer,");
							otp = strArray[1];
							otp = otp.trim().split(" is")[0].trim();
						} else if (dataField.equalsIgnoreCase("AXISMF")) {
							try {
								System.out.println("You are Under AxisMF_OTP");
								final String[] strArray = result.split("OTP is");
								otp = strArray[1];
								otp = otp.trim().split("\\.")[0].trim();
							} catch (Exception e) {
								System.out.println("Inside catch block");
							}
						} else if (dataField.equalsIgnoreCase("AxisApproval")) {
							try {
								System.out.println("You are Under AxisApproval otp");
								final String[] strArray = result.split("<p>");
								otp = strArray[1];
								otp = otp.trim().split(" is the OTP")[0].trim();
							} catch (Exception e) {
								System.out.println("Inside catch block");
							}
						} else if (dataField.equalsIgnoreCase("JMF")) {
							try {
								System.out.println("You are Under JM Financial otp");
								if (result.contains("Greetings from JM Financial Services Ltd!<br/><br/>")) {
									final String[] strArray = result.split("Services Ltd!<br/><br/>");
									otp = strArray[1];
									otp = otp.trim().split(" is")[0].trim();
								} else {
									final String[] strArray = result.split("Services Ltd!");
									otp = strArray[1];
									otp = otp.trim().split(" is")[0].trim();
								}
							} catch (Exception e) {
								System.out.println("Inside catch block");
							}
						} else if (dataField.equalsIgnoreCase("Edelwelss")) {
							try {
								System.out.println("You are Under Edelwelss otp");
								final String[] strArray = result.split("use the one-time password (OTP) given below.");
								otp = strArray[1];
								otp = otp.trim().split(" ")[0].trim();
							} catch (Exception e) {
								System.out.println("Inside catch block");
								e.printStackTrace();
							}
						} else {
							System.out.println("You have Entered Wrong Field detail");
						}
						FetchMail.emailMessage.setFlag(Flags.Flag.SEEN, true);
						emailInbox.close(true);
						store.close();
						break;
					}
					--FetchMail.messageCount;
				} else {
					--FetchMail.messageCount;
				}
			}
		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return otp;
	}

	public static String accessGettextfromMultipart(final MimeMultipart multipart)
			throws MessagingException, IOException {
		return getTextFromMimeMultipart(multipart);
	}

	public static String accessGetTextFromMessage(final Message message) throws MessagingException, IOException {
		return getTextFromMessage(message);
	}

}
