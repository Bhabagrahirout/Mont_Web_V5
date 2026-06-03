package gitLabMigration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test {

	private static ChromeDriver driver;
	private static String DomainName;
	private static String clientName;
	private static String IP;
	
//	public static void main(String[] args) throws Exception {
//		
//		
//		 FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator+"config.properties");
//		 Properties pro=new Properties();
//		 pro.load(fis);
//		
////		 DomainName=pro.getProperty("domainName");
//		  IP=pro.getProperty("ip");
//		 String userName=pro.getProperty("userName");
//		 String pass=pro.getProperty("pass");
//		 String url1=pro.getProperty("url");
//		
////		System.setProperty("webdriver.chrome.driver", "/home/prasad.deshmukh@apmosys.mahape/Downloads/chromedriver-linux64 (6)/chromedriver-linux64/chromedriver");
//
//		ChromeOptions options2 = new ChromeOptions();
//		options2.addArguments(new String[] { "--remote-allow-origins=*" });
//
//		 driver=new ChromeDriver(options2);
//		driver.manage().window().maximize();
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		System.out.println(url1);
//
//		driver.get(url1);
//		
//		
//		
//		getElement(By.id("j_username")).sendKeys(userName); // username
//		getElement(By.id("j_password")).sendKeys(pass); // password
//
////		getElement(By.id("//button[text()='Sign in']")).click(); //click on sign
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//		}
//		JavascriptExecutor jse6 = (JavascriptExecutor) driver;
//		jse6.executeScript("document.getElementsByName('Submit')[0].click()");//click on sign
//		
//		
//		Fillo fillo = new Fillo();
//		Connection filloCon1 = fillo.getConnection("ClientData.xlsx");
//		String queryString1 = "SELECT * FROM Sheet1 WHERE DomainRunStatus = 'Y'";
//		Recordset executeQuery1 = filloCon1.executeQuery(queryString1);
//		while (executeQuery1.next()) {
//			DomainName=executeQuery1.getField("DOMAIN NAME").trim();
//			System.out.println("domain name   "+DomainName);
//			Map appStatusMap = checkApplications(DomainName+"_"+IP+"_");
//
//
//			
//			try {
//            	js.executeScript(
//    					"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", driver.findElement(By.xpath("//span[text()='"+DomainName+"']")));
//    }catch (Exception e) {
//    	js.executeScript(
//				"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", driver.findElement(By.xpath("//span[text()='"+DomainName+"']")));
//    	e.printStackTrace();
//    }
//			
//			Thread.sleep(1000);
//
//		getElement(By.xpath("//span[text()='"+DomainName+"']")).click(); //click on banking 
//		
//		try {
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
//				new Object[] { driver.findElement(By.xpath("//span[text()='"+DomainName+"']")) });
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//		
////		JOptionPane.showMessageDialog(null, "ok");
//Thread.sleep(1000);
//		
//		//table[@id='projectstatus']/tbody/tr  ---- all row 
//		String allRow="//table[@id='projectstatus']/tbody/tr/td[3]/a/span[1]";
//      
////		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(allRow)));
////		List<WebElement> list=  driver.findElements(By.xpath(allRow));
//       
//		List<WebElement> list = wait.until(
//                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(allRow))
//        );
//		
//		System.out.println("Client List -- "+list.size());
//		
//		
//		File excelFile = new File("ClientData.xlsx");
//		if(excelFile.exists()) {
//			Fillo fillo1 = new Fillo();
//			Connection filloCon = fillo1.getConnection("ClientData.xlsx");
//			String domain=DomainName.replaceAll(" ", "_");
//			String queryString = "SELECT * FROM "+domain+" WHERE RunStatus = 'Y'";
//			Recordset executeQuery = filloCon.executeQuery(queryString);
//			while (executeQuery.next()) {
//				String clientname=executeQuery.getField("CLIENT NAME").trim();
//				System.out.println("Client:-"+clientname);
//			
//        
////        for (int i = 2; i <= list.size(); i++) {
//
//        	
//        	//			list.get(i).click();
////        	String rowXpath="("+allRow+")["+i+"]";
//        	Thread.sleep(1000);
//        	try {
//            	js.executeScript(
//    					"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", driver.findElement(By.xpath("(//a[@href='job/"+clientname+"/'])[1]")));
//    }catch (Exception e) {
//    	js.executeScript(
//				"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", driver.findElement(By.xpath("(//a[@href='job/"+clientname+"/'])[1]")));
//    	e.printStackTrace();
//    }
//        	
//        	WebElement wb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='job/"+clientname+"/'])[1]")));
//    		 clientName=wb.getText();
//        	System.out.println("Client Name -- "+clientName);
//
//        	
////    		WebElement wb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='job/CIty_Union_Bank/'])[1]")));
////    		
////
//			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
//					new Object[] { wb });
//			
//			
//			// enter in domain
//			try {
////			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Web']")));
//				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Web' or text()='Mobile' or text()='API']")));
//
//				
//			
//			try {
//				Thread.sleep(6000);
//			} catch (InterruptedException e) {
//			}
//			List<WebElement> webMobileList = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(allRow))
//	        );
//
//	        System.out.println("Web - Mobile list -- "+webMobileList.size());
//
//	        for (int j = 1; j <= webMobileList.size(); j++) {
//
//	        	try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//				}
//	        	try {
//	        	String rowXpath2="("+allRow+")["+j+"]";
//	        	WebElement wb2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rowXpath2)));
//	        	String platformName=wb2.getText();
//	        	((JavascriptExecutor) driver).executeScript("arguments[0].click();",
//						new Object[] { wb2 });
//				
//				
//				
//
//	        	try {
//					Thread.sleep(8000);
//				} catch (InterruptedException e) {
//				}
//	        	
//		        List<WebElement> applicationsList = wait.until(
//		                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(allRow))
//		        );
//		        System.out.println("All Applications list -- "+applicationsList.size());
//
//		        for (int k = 1; k <= applicationsList.size(); k++) {
//
//		        	try {
//						Thread.sleep(10000);
//					} catch (InterruptedException e) {
//					}
//		        	
//		        	String rowXpath1="("+allRow+")["+k+"]";
//		        	
//		        	try {
//		        	js.executeScript(
//							"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", driver.findElement(By.xpath(rowXpath1)));
//		        	}catch (Exception e) {
//						e.printStackTrace();
//					}
//		        	
//		        	WebElement wb1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rowXpath1)));
//		        	String applicationName=wb1.getText().trim();
//		    		System.out.println("Application Name -- "+applicationName);
//		    		Thread.sleep(3000);
//		    		boolean condition=!(appStatusMap.toString().contains(applicationName)) ;
//		    		System.out.println("condition --- "+condition);
//		    		Thread.sleep(3000);
////System.out.println(appStatusMap);
//		    		if (condition) { 
////		    				&& "success".equalsIgnoreCase(appStatusMap.get(applicationName))) {
//		        	wb1.click();
////					applicationsList.get(k).click();
//					// enter in application
//
//		        	try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//					}
//					getElement(By.xpath("//*[@id='tasks']/div[5]/span")).click(); //click on Configure
//					
//					
//					String url="";
//					try {
//					// scroll to Repository URL
//					js.executeScript(
//							"arguments[0].scrollIntoView({ block: 'center' ,inline :'nearest' });", driver.findElement(By.xpath("(//div[text()='Repository URL'])[1]")));
//					
//					//gettext the attribute url
//					
//					@Nullable
//					String repositoryURL = getElement(By.xpath("(//div[text()='Repository URL'])[1]/following-sibling::div/input")).getDomAttribute("value");
//					System.out.println("actual url -- "+repositoryURL);
//					
//					 url=getURL(repositoryURL);
//					
//					
//					WebElement urlElement = getElement(By.xpath("(//div[text()='Repository URL'])[1]/following-sibling::div/input"));
//					urlElement.clear();
//					urlElement.sendKeys(url);
//					Thread.sleep(3000);
//					//click on save button
//					//getElement(By.id("//button[@name='Submit']")).click();
//					
//					jse6.executeScript("document.getElementsByName('Submit')[0].click()");
//					
//					
//					try {
//						csvWrite(DomainName,clientName,platformName,applicationName,url,"success");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//					}
//					
//					driver.navigate().back();
//					
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//					}
//					driver.navigate().back();
//					
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//					}
//					driver.navigate().back();
//					
//					
//					}catch (Exception e) {
//						e.printStackTrace();
//						System.out.println("Repository URL not found on the application [ "+applicationName+" ]");
//						try {
//							csvWrite(DomainName,clientName,platformName,applicationName,url,"fail");
//						} catch (Exception e1) {
//							e1.printStackTrace();
//						}
//						
//						
//							Thread.sleep(1000);
//						
//						driver.navigate().back();
//						
//							Thread.sleep(1000);
//						
//						driver.navigate().back();
//					}
//		    		}else {
//		    			continue;
//		    		}
//		    		System.out.println();
//		    		System.out.println();
//				//end if condition
//				}
//		        
//		        driver.navigate().back();
//		        try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//				}
//		        
//	        } catch (Exception e) {
//	        	System.out.println("Data not present for client { "+clientName+" }");
//			}
//				
//			}
//	        driver.navigate().back();
//	        try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//			}
//			
////		}
//			} catch (Exception e) {
//				System.out.println("Data not present for client { "+clientName+" }");
//						Thread.sleep(1000);
//			
//		        driver.navigate().back();
//		        Thread.sleep(1000);
//			}  
//        }
////		executeQuery.close();
////		filloCon1.close();
//	}
//		 Thread.sleep(1000);
//		 driver.navigate().back();
//	        Thread.sleep(1000);
//		
//		}
//	}
	
	public static WebElement getElement(By by) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement webelement = null;
		try {
		 webelement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return webelement;
	}
	
	public static String getURL(String str) {
		 String result=null;
		try {
            URL url = new URL(str);
           
            String baseUrl = url.getProtocol() + "://" + url.getHost() + (url.getPort() != -1 ? ":" + url.getPort() : "");

           result = str.replace(baseUrl, "${GIT_URL}");

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return result;
	}
	
	
	
	public static void csvWrite(String val1, String val2, String val3, String val4, String val5, String val6)
			throws Exception {
		
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String time=sf.format(new Date());
		
		String csvPath = System.getProperty("user.dir") + File.separator+"Logs"+File.separator + DomainName+"_"+IP+"_"+"Log.csv";
		File csvFile = new File(csvPath);
		boolean fileExists = csvFile.exists();

		
		
		try {
			FileWriter fw = new FileWriter(csvFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			// Add header only once when file is created
			if (!fileExists) {
				out.println("DomainName,ClientName,PlatformName,ApplicationName,RepositoryUrl,Date,Status");
			}
			 // Append data safely with quotes (avoid comma issues)
            out.println(String.join(",",
                    "\"" + val1 + "\"",
                    "\"" + val2 + "\"",
                    "\"" + val3 + "\"",
                    "\"" + val4 + "\"",
                    "\"" + val5 + "\"",
                    "\"" + time + "\"",
                    "\"" + val6 + "\""
            ));
			out.close();
			bw.close();
			fw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Write Done...");
	}
	
	public static Map checkApplications(String domainName) {
		
		
		String csvFile = domainName+"Log.csv";
		
		String line;
		String splitBy = ",";
//		Map<String, String> appStatusMap = new HashMap<>();
//		String appName = "\"BNK_Axis bank urls 15min\"";
//		String status = "\"success\"";
		 Map<String, String> appStatusMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+File.separator+"Logs"+File.separator+csvFile))) {
			String[] headers = br.readLine().split(splitBy);
			int appIdx = Arrays.asList(headers).indexOf("ApplicationName");
			int statusIdx = Arrays.asList(headers).indexOf("Status");

			while ((line = br.readLine()) != null) {
				String[] values = line.split(splitBy);
//				appStatusMap.put(values[appIdx], values[statusIdx]);

				String app = values[appIdx];
				String value = values[statusIdx];
				System.out.println("AppName: " + app);
				System.out.println("value: " + value);
				appStatusMap.put(app, value);
				
			}
			

			// Check condition without loop
			
//			if (appStatusMap.containsKey(appName) && status.equalsIgnoreCase(appStatusMap.get(appName))) {
//				System.out.println("✅ App present and status is Pass → Do something");
//			} else {
//				System.out.println("❌ Condition not met → Do something else");
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return appStatusMap;
	}
		
		
		
	
	
	
	
	
	

}
