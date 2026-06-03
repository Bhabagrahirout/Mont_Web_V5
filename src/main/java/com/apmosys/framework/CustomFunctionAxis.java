package com.apmosys.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.exception.FilloException;

public class CustomFunctionAxis {

	public static boolean pageErrorForAxisCorp = false;

	public static boolean isWithinTradingHours() {
		LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Kolkata"));
		LocalTime startTime = LocalTime.of(9, 15);
		LocalTime endTime = LocalTime.of(15, 30);
		return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
	}

	public static void checkCurrencyValues(String loaderPath, String counterParty, String account,
			List<String> curList) {

		String[] currencies = { "USDINR", "GBPINR", "EURINR" };
		String[] types = { "Cash", "TOM", "Spot" };
		String[] sellOrBuy = { "sell", "buy" };
		ArrayList<String> prev_missingValues = new ArrayList<>();
		int iteration = 0;
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(300));
		while (CustomFunctionAxis.isWithinTradingHours()) {

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loaderPath)));

			ArrayList<String> now_missingValues = new ArrayList<>();
			System.out.println("Iteration " + ++iteration + " started.");

			int i = 0;
			for (String currency : currencies) {

				if (curList == null) {
					for (String type : types) {
						for (String s : sellOrBuy) {

							String currencyText = getCurrencyValue(++i);
							System.out.println(currency + "_" + type + "_" + s + ":- " + currencyText);

							if (currencyText.equalsIgnoreCase("-") || currencyText.equalsIgnoreCase("")
									|| currencyText == null || currencyText.isEmpty()) {
								System.out.println("In if when text is null");
								String missingValue = "Currency: " + currency + " - Type: " + type;
								now_missingValues.add(missingValue);
								sendMissingValuesEmail(currency, counterParty, type, account);
							}
						}
					}
				} else {

					if (!curList.contains(currency)) {
						for (String type : types) {
							for (String s : sellOrBuy) {

								String currencyText = getCurrencyValue(++i);
								System.out.println(currency + "_" + type + "_" + s + ":- " + currencyText);

								if (currencyText.equalsIgnoreCase("-") || currencyText.equalsIgnoreCase("")
										|| currencyText == null || currencyText.isEmpty()) {
									System.out.println("In if when text is null");
									String missingValue = "Currency: " + currency + " - Type: " + type;
									now_missingValues.add(missingValue);
									sendMissingValuesEmail(currency, counterParty, type, account);
								}
							}
						}
					}
				}
				//

			}

			if (!prev_missingValues.isEmpty()) {
				System.out.println("in pre missing and now missing and pre missing value condition...");
				ListIterator<String> aIterator = prev_missingValues.listIterator();
				while (aIterator.hasNext()) {
					String string = aIterator.next();
					if (!now_missingValues.contains(string)) {
						System.out.println("in now misssing contains condition....");
						String[] parts = string.split(" - ");
						System.out.println(Arrays.toString(parts));
						String currency = parts[0].split(": ")[1];
						String type = parts[1].split(": ")[1];
						sendValuesVisibleEmail(currency, counterParty, type, account);
						aIterator.remove();
					}
				}
			}
			prev_missingValues = now_missingValues;
			System.out.println("missing values ---> " + now_missingValues);
			try {
				Thread.sleep(300000);
				Framework.driver.navigate().refresh();
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Iteration " + iteration + " complete.");
		}
	}

	public static void checkCurrencyValuesNew(String loaderPath, String counterParty, String account,
			List<String> curList) {

		if (curList != null && curList.get(0).startsWith("INR")) {
			System.out.println("****** Today's all Currencies off *******");
			return;
		}
		boolean status = false;
		String[] currencies = { "USDINR", "GBPINR", "EURINR" };
		String[] types = { "Cash", "TOM", "Spot" };
		String[] sellOrBuy = { "sell", "buy" };

		ArrayList<String> prev_missingValues = new ArrayList<>();
		ArrayList<String> sellAndBuyValues;
		int todaysIteration = 0;
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(60));
		// for first time page successfully found or not
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loaderPath)));
		} catch (Exception e) {
			System.out.println("-------------------------------------------------------");
			System.out.println("-------------------- Page Not Found -------------------");
			System.out.println("-------------------------------------------------------");
			try {
				Monitoring_FrameWork.SaveResult("False", "True");
				pageErrorForAxisCorp = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		while (CustomFunctionAxis.isWithinTradingHours()) {

			System.out.println("Iteration " + ++todaysIteration + " started.");
			Map<String, Map<String, String>> allCurrenciesValuesinFiveMinutes = new LinkedHashMap<>();
			ArrayList<String> now_missingValues = new ArrayList<>();

			System.out.println("Before Loader...........");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loaderPath)));
			System.out.println("After Loader.............");
			try {
				Thread.sleep(20000);
				Framework.tStartTime = Monitoring_FrameWork.StartTime();
			} catch (InterruptedException e) {
			}

			for (int j = 0; j < 5; j++) {

				if (j == 0) {
					int i = 0;
					for (String currency : currencies) {

						if (curList == null) {
							for (String type : types) {
								sellAndBuyValues = new ArrayList<>();
								for (String s : sellOrBuy) {

									String currencyPrice = getCurrencyValue(++i);
									String currencyName = currency + "_" + type + "_" + s;
									System.out.println(currencyName + ":- " + currencyPrice);
									if (currencyPrice.equalsIgnoreCase("-") || currencyPrice.equalsIgnoreCase("")
											|| currencyPrice == null || currencyPrice.isEmpty()) {
										String missingValue = "Currency: " + currency + " - Type: " + type;
										now_missingValues.add(missingValue);
										sendMissingValuesEmail(currency, counterParty, type, account);
										saveScreenShot();
										status = true;
										sellAndBuyValues.add("0");
									} else {
										sellAndBuyValues.add(currencyPrice);
									}

									allCurrenciesValuesinFiveMinutes.putIfAbsent(currencyName,
											new LinkedHashMap<String, String>());
									String time = new SimpleDateFormat("dd/MM/YYY HH:mm:ss").format(new Date());
									allCurrenciesValuesinFiveMinutes.get(currencyName).put(time, currencyPrice);

								}
								// adding In DB
								DB_Tables.axisNeoCurrencyDetails(currency.replaceAll("INR", ""), "INR",
										sellAndBuyValues.get(1), sellAndBuyValues.get(0), type);

							}
						} else {

							if (!curList.contains(currency)) {
								for (String type : types) {

									sellAndBuyValues = new ArrayList<>();
									for (String s : sellOrBuy) {

										String currencyPrice = getCurrencyValue(++i);
										String currencyName = currency + "_" + type + "_" + s;
										System.out.println(currencyName + ":- " + currencyPrice);
										if (currencyPrice.equalsIgnoreCase("-") || currencyPrice.equalsIgnoreCase("")
												|| currencyPrice == null || currencyPrice.isEmpty()) {
											System.out.println("In if when text is null");
											String missingValue = "Currency: " + currency + " - Type: " + type;
											now_missingValues.add(missingValue);
											sendMissingValuesEmail(currency, counterParty, type, account);
											saveScreenShot();
											status = true;
											sellAndBuyValues.add("0");
										} else {
											sellAndBuyValues.add(currencyPrice);
										}

										allCurrenciesValuesinFiveMinutes.putIfAbsent(currencyName,
												new LinkedHashMap<String, String>());
										String time = new SimpleDateFormat("dd/MM/YYY HH:mm:ss").format(new Date());
										allCurrenciesValuesinFiveMinutes.get(currencyName).put(time, currencyPrice);

									}
									// adding In DB
									DB_Tables.axisNeoCurrencyDetails(currency.replaceAll("INR", ""), "INR",
											sellAndBuyValues.get(1), sellAndBuyValues.get(0), type);

								}
							}
						}
					}

					if (status == false) {
						executionData("1",String.valueOf(status));
					} else {
						executionData("0",String.valueOf(status));
					}

					// in first iterartion checking now and previous missing value
					if (!prev_missingValues.isEmpty()) {
						System.out.println("in pre missing and now missing and pre missing value condition...");
						ListIterator<String> aIterator = prev_missingValues.listIterator();
						while (aIterator.hasNext()) {
							String string = aIterator.next();
							if (!now_missingValues.contains(string)) {
								System.out.println("in now misssing contains condition....");
								String[] parts = string.split(" - ");
								System.out.println(Arrays.toString(parts));
								String currency = parts[0].split(": ")[1];
								String type = parts[1].split(": ")[1];
								sendValuesVisibleEmail(currency, counterParty, type, account);
								aIterator.remove();
							}
						}
					}
					prev_missingValues = now_missingValues;
					System.out.println("missing values ---> " + now_missingValues);

					// First one minute end
					// Store response time for FMConnectPage
					try {
						Monitoring_FrameWork.SaveResult("true", "true");
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {// for next 4 iteration

					int i = 0;
					for (String currency : currencies) {

						if (curList == null) {
							for (String type : types) {
								for (String s : sellOrBuy) {

									String currencyPrice = getCurrencyValue(++i);
									String currencyName = currency + "_" + type + "_" + s;
									System.out.println(currencyName + ":- " + currencyPrice);
									allCurrenciesValuesinFiveMinutes.putIfAbsent(currencyName,
											new LinkedHashMap<String, String>());
									String time = new SimpleDateFormat("dd/MM/YYY HH:mm:ss").format(new Date());
									allCurrenciesValuesinFiveMinutes.get(currencyName).put(time, currencyPrice);
								}
							}
						} else {

							if (!curList.contains(currency)) {
								for (String type : types) {
									for (String s : sellOrBuy) {

										String currencyPrice = getCurrencyValue(++i);
										String currencyName = currency + "_" + type + "_" + s;
										System.out.println(currencyName + ":- " + currencyPrice);
										allCurrenciesValuesinFiveMinutes.putIfAbsent(currencyName,
												new LinkedHashMap<String, String>());
										String time = new SimpleDateFormat("dd/MM/YYY HH:mm:ss").format(new Date());
										allCurrenciesValuesinFiveMinutes.get(currencyName).put(time, currencyPrice);

									}
								}
							}
						}

					}
				}

				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
				}

			}
			// Five minutes end

			Set<String> currenciesNames = allCurrenciesValuesinFiveMinutes.keySet();

			for (String currecyName : currenciesNames) {

				Set<String> uniquePrices = new HashSet<>();
				Map<String, String> timePriceMap = allCurrenciesValuesinFiveMinutes.get(currecyName);
				uniquePrices.addAll(timePriceMap.values());

				if (uniquePrices.size() == 1) {
					
					String price=uniquePrices.iterator().next();
					
					saveCurrencyValue(currecyName,timePriceMap,"Fail");
					
					if (price.equals("")) {
						System.out.println(currecyName + ": Price Values not changed  === " + price);
					} else {
						System.out.println(currecyName + ": Price Values not changed  === " + price);
						// mail part
						sendMissingValeInFiveMinutes(currecyName);
					}
					
				} else {
					System.out.println(currecyName + ": Price value changed");
					saveCurrencyValue(currecyName,timePriceMap,"Pass");

				}

			}

			

//			Set<String> currenciesNames = allCurrenciesValuesinFiveMinutes.keySet();
//
//			for (String currecyName : currenciesNames) {
//
//				List<String> prices = allCurrenciesValuesinFiveMinutes.get(currecyName);
//
//				// Use stream().distinct() to find unique prices and check if the prices have
//				long distinctCount1 = prices.stream().filter(s -> !s.equals("-")).distinct().count();
//				if (distinctCount1 > 1) {
//					System.out.println(currecyName + ": Price value changed");
//				} else {
//
//					if (prices.get(0).equals("")) {
//						System.out.println(currecyName + ": Price Values not changed  === " + prices);
//					} else {
//						System.out.println(currecyName + ": Price Values not changed  === " + prices);
//						// mail part
//						sendMissingValeInFiveMinutes(currecyName);
//					}
//
//				}
//			}

			try {
				Thread.sleep(5000);
				Framework.driver.navigate().refresh();
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Iteration " + todaysIteration + " complete.");
		}
	}

	public static String getCurrencyValue(int iteration) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		WebDriverWait wait = new WebDriverWait(Framework.driver, Duration.ofSeconds(60));

		String price = "";
		String currencyText = null;
		String dollarText = null;
		String lastValueText = null;
		try {
			// (((//div[@class='hover pointer'])[6]//div[2]//div)//div)[2]
			try {
				currencyText = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath("(((//div[@class='hover pointer'])[" + iteration + "]//div[2]//div)//div)[2]")))
						.getText();

			} catch (Exception e) {
				currencyText = wait
						.until(ExpectedConditions.presenceOfElementLocated(By
								.xpath("(((//div[@class='hover pointer'])[" + iteration + "]//div[2]//div)//div)[2]")))
						.getText();
			}
			currencyText = currencyText.replaceAll("\n", " ").trim();

			if (!currencyText.equalsIgnoreCase("-") && !currencyText.equalsIgnoreCase("") && currencyText != null
					&& !currencyText.isEmpty()) {
				try {
					dollarText = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
									"(((//div[@class='hover pointer'])[" + iteration + "]//div[2]//div)//div)[1]")))
							.getText().trim();
					System.out.println("dollarText is ====== " + dollarText);
					lastValueText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
							"(((//div[@class='hover pointer'])[" + iteration + "]//div[2]//div)//div)[3]/div[1]")))
							.getText().trim();
					price = dollarText + currencyText + lastValueText;
				} catch (Exception e) {
					System.out.println("Dollar and lastValue not get");

				}
			}
			System.out.print(" \n currencyText ====> " + currencyText);
			System.out.print("  dollarText  ====> " + dollarText);
			System.out.print("  lastValueText ====> " + lastValueText);
			System.out.println("  price ====> " + price);
			System.out.println();

			return price;
		} catch (Exception e) {
			e.printStackTrace();
			return price;
		}
	}

	public static void sendMissingValuesEmail(String currencies, String counterparty, String type, String account) {
		String subject = "Alert: Missing Values for " + currencies + " - " + type + " Currency in AXIS " + account
				+ " account";
		String body = "Dear Sir/Ma'am,<br/><br/>" + type + " Rate for " + currencies + " are not visible.<br/><br/>"
				+ "Will refresh it again after 5 minutes and update you for the same.<br/><br/>"
				+ "Thank you for your prompt attention to this issue.<br/><br/>" + "Best regards,<br/>"
				+ "ApMoSys Technologies";

		sendEmail(subject, body);

		String message = "Alert: Missing Values for " + currencies + " - " + type + " Currency in AXIS " + account
				+ " account" + "issue with the " + counterparty;

		storeMailDate(message);

	}

	public static void sendValuesVisibleEmail(String currencies, String counterparty, String type, String account) {
		String subject = "Notification: Values Now Visible for " + currencies + " - " + type + " Currency in AXIS "
				+ account + " account";
		String body = "Dear Sir/Ma'am,<br/><br/>" + "We are pleased to inform you that the values for " + currencies
				+ " - " + type + " are visible.<br/><br/>" + "Details:-<br/><br/>" + "Currency: " + currencies + " - "
				+ type + "<br/><br/>" + "Current Status: Values are now visible.<br/><br/>"
				+ "Please verify the updated status and let us know if you encounter any further issues.<br/><br/>"
				+ "Thank you for your prompt attention to resolving this matter.<br/><br/>" + "Best regards,<br/>"
				+ "ApMoSys Technologies";

		sendEmail(subject, body);
		String message = "Values Now Visible for " + currencies + " - " + type + " Currency in AXIS " + account
				+ " account" + "				Details:" + "Previous Issue: Values were not visible for "
				+ counterparty;

		storeMailDate(message);
	}

	public static void sendMissingValeInFiveMinutes(String currencyName) {
		String subject = "Alert: Values Not Refreshing / Changed";

		String body = "Dear Sir/Ma'am,<br/><br/>" + "We've noticed that the " + currencyName
				+ "values are not refreshing as expected every 5 minutes. Could you please look into this issue? <br/><br/>"
				+ "Thanks & Regards,<br/>" + "ApMoSys Technologies";

		sendEmail(subject, body);

	}

	public static void sendEmail(String sub, String body) {
		Properties pro = new Properties();
		try {
			FileInputStream fis = new FileInputStream(Monitoring_FrameWork.path2);
			pro.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String userName = pro.getProperty("mailFrom");
		String password = pro.getProperty("password");
		String mailTo = pro.getProperty("mailTo");
		String mailCc = pro.getProperty("mailCc");

		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "apmosys.icewarpcloud.in");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.user", userName);
			properties.put("mail.password", password);
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};

			Session session = Session.getInstance(properties, auth);
			Message msg = (Message) new MimeMessage(session);
			msg.setFrom((Address) new InternetAddress(userName));
			msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(mailTo));
			if (mailCc != null && !mailCc.equalsIgnoreCase("")) {
				msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(mailCc));
			}

			msg.setSubject(sub);

			msg.setSentDate(new Date());
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			System.out.println("Creates message part");

			messageBodyPart.setContent(body, "text/html");
			Multipart multipart = (Multipart) new MimeMultipart();
			System.out.println("Creates multi-part");

			multipart.addBodyPart((BodyPart) messageBodyPart);

			msg.setContent(multipart);
			System.out.println("Sending mail....");
			Transport.send(msg);
			System.out.println("Mail has been sent....");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void storeMailDate(String Message) {
		String path = System.getProperty("user.dir") + File.separator + "MailLogs.csv";
		File file = new File(path);
		SimpleDateFormat smd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date = smd.format(new Date());
		try {
			if (!file.exists()) {
				FileWriter fW = new FileWriter(file, true);
				BufferedWriter bW = new BufferedWriter(fW);
				bW.write("Time,Message\n");
				bW.close();
				fW.close();
			}
			FileWriter fW = new FileWriter(file, true);
			BufferedWriter bW = new BufferedWriter(fW);
			bW.write(date + "," + Message + "\n");
			bW.close();
			fW.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void executionData(String responsetime,String status) {

		String createdOn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		Framework.pageId = DB_Tables.getPageId(Framework.applicationId, Framework.pagename);
		System.out.println("pageId ======= " + Framework.pageId);

		String errorInput = null;
		String errortUrl = null;
		System.out.println("--------------- Total time insert In DB " + responsetime);
		DB_Tables.Table_execution_data(Framework.dataSheetName, Framework.runTimeInstanceId,
				new SimpleDateFormat("yyyyMMdd").format(new Date()), new SimpleDateFormat("HH").format(new Date()),
				new SimpleDateFormat("mm").format(new Date()), errorInput, null, errortUrl,
				Double.parseDouble(responsetime), Framework.monitoringInstancesId, Framework.applicationId,
				Framework.pageId, Framework.createdBy, createdOn, Framework.macId, createdOn,status," ");

	}

	public static void saveCurrencyValue(String CurrenecyName, Map<String, String>timePriceMap,String status) {

		try {

			final int year = Calendar.getInstance().get(1);
			final String MonthName = new SimpleDateFormat("MMMM").format(new Date());
			final int monthday = Calendar.getInstance().get(5);

			String path = System.getProperty("user.dir") + File.separator + "Logs" + File.separator + "CurrencyDetails"
					+ File.separator + year + File.separator + MonthName + File.separator + monthday;
			File folder = new File(path);
			folder.mkdirs();

			
			path = path + File.separator + "CurrencyDetails.csv";
			File file = new File(path);

			if (!file.exists()) {
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("CurrenecyName,CurrencyPrice,Time,Rate_Changed_Status,Availability_Status \n");
				bw.close();
				fw.close();
			}

//			if (CurrencyPrice.equalsIgnoreCase("")) {
//				bw.write(CurrenecyName + "," + CurrencyPrice + "," + time + "," + "Fail Value Missing" + "\n");
//			} else {
//				bw.write(CurrenecyName + "," + CurrencyPrice + "," + time + "," + "Pass" + "\n");
//			}
			
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
		   Set<String> times=timePriceMap.keySet();
		   for(String time:times)
		   {
			   String CurrencyPrice=timePriceMap.get(time);
			   if (CurrencyPrice.equalsIgnoreCase("")) {
					bw.write(CurrenecyName + "," + CurrencyPrice + "," + time + ","+status +","+"Fail Value Missing"+ "\n");
				} else {
					bw.write(CurrenecyName + "," + CurrencyPrice + "," + time + "," + status + ","+"Pass"+"\n");
				}
		   }
			bw.close();
			fw.close();

			System.out.println("----- value saved in CSV file -----");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void saveScreenShot() {

		final int year = Calendar.getInstance().get(1);
		final String MonthName = new SimpleDateFormat("MMMM").format(new Date());
		final int monthday = Calendar.getInstance().get(5);

		String path = System.getProperty("user.dir") + File.separator + "CurrenciesImages" + File.separator + year
				+ File.separator + MonthName + File.separator + monthday;
		File folder = new File(path);
		folder.mkdirs();

		String time = new SimpleDateFormat("HH_mm_ss").format(new Date());
		path = path + File.separator + "img_" + time + ".png";
		WebElement ele = new WebDriverWait(Framework.driver, 40).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='react-grid-layout layout']")));

		File srcFile = ele.getScreenshotAs(OutputType.FILE);
		File destFile = new File(path);
		try {
			FileUtils.copyFile(srcFile, destFile);
			System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void recheckVisibility(String propertyValueRDS, String pageName) {

		try {

			WebDriverWait wait = new WebDriverWait(Framework.driver, 30);
			WebElement webElementVal = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propertyValueRDS)));
			String actualResult = String.valueOf(webElementVal.isDisplayed());

			if (actualResult.equalsIgnoreCase("true")) {
				System.out.println("---------- FX Page is Available ----------");
				Monitoring_FrameWork.SaveResult("True", "True");
			} else {
				if (Framework.status) {
					System.out.println("---------- FX Page is not  Available Retry once Again...........");
					Framework.driver.quit();
					Framework.recordsetRDS.moveFirst();
					Framework.status = false;
				} else {
					System.out.println(" Still FX Page is not  Available Quit Execution...........");
					Framework.errorsatus = "1";
					Framework.errorpagename = pageName;
					try {
						Monitoring_FrameWork.SaveResult("True", "Flase");
					} catch (Exception e) {
					}
				}

			}
		} catch (Exception e2) {

			if (Framework.status) {
				System.out.println("---------- FX Page is not  Available Retry once Again...........");
				Framework.driver.quit();
				try {
					Framework.recordsetRDS.moveFirst();

				} catch (FilloException e) {
					e.printStackTrace();
				}
				Framework.status = false;
			} else {
				System.out.println(" Still FX Page is not  Available Quit Execution...........");
				Framework.errorsatus = "1";
				Framework.errorpagename = pageName;
				try {
					Monitoring_FrameWork.SaveResult("True", "Flase");
				} catch (Exception e) {
				}
			}

		}

	}

}
