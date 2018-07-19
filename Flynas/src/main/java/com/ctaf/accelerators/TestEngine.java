package com.ctaf.accelerators;       

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.BeforeTest;


import com.ctaf.support.ActionEngineSupport;
import com.ctaf.support.ConfiguratorSupport;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.support.ReportStampSupport;
import com.ctaf.utilities.Reporter;



import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestEngine extends HtmlReportSupport {
		
	public static Logger logger = Logger.getLogger(TestEngine.class.getName());
	public static ConfiguratorSupport configProps = new ConfiguratorSupport(
			"config.properties");
	public static ConfiguratorSupport counterProp = new ConfiguratorSupport(
			configProps.getProperty("counterPath"));
	public static String currentSuite = "";
	public static String method = "";
	public static String timeStamp = ReportStampSupport.timeStamp()
			.replace(" ", "_").replace(":", "_").replace(".", "_");
	public static boolean flag = false;
	
	public static RemoteWebDriver driver=null;
	public static int stepNum = 0;
	public static int PassNum =0;
	public static int FailNum =0;
	public static int passCounter =0;
	public static int failCounter =0;
	public static String testName = "";
	public static String testCaseExecutionTime = "";
	public static RemoteWebDriver webDriver = null;
	public static StringBuffer x=new StringBuffer();
	public static String finalTime = "";
	public static boolean isSuiteRunning=false;
	public static Map<String, String> testDescription = new LinkedHashMap<String, String>();
	public static Map<String, String> testResults = new LinkedHashMap<String, String>();
	public static String url=null;
	public static String mmburl=null;
	public static String chekinurl=null;
	public static String loginurl=null;
	static ExcelReader xlsrdr = new ExcelReader(configProps.getProperty("SettingsWB"),configProps.getProperty("sheetName0"));
	public DesiredCapabilities capabilities;
	//***************************************************************************************************
	public static String DeviceName = configProps.getProperty("DeviceName");

	public static AppiumDriver AndroidDriver2 = null;
	public static AppiumDriver Iosdriver = null;
	public static String bundleID = null;
	//***************************************************************************************************

	/*
	 * public static Screen s; public static String url =
	 * "jdbc:mysql://172.16.6.121/"; public static String dbName = "root";
	 * public static String userName = "root"; public static Connection conn =
	 * null; public static Statement stmt = null; public static
	 * PreparedStatement pstmt = null; public static ResultSet rs = null;
	 */
	public static int countcompare = 0;
	public static String browser = null;
	static int len = 0;
	static int i = 0;
	public static ITestContext itc;
	public static String groupNames = null;


	@BeforeSuite(alwaysRun = true)
	public static void setupSuite(ITestContext ctx) throws Throwable {
		System.out.println("In Before sutie");
		itc = ctx;
		groupNames = ctx.getCurrentXmlTest().getIncludedGroups().toString();
		System.out.println("+++++"+groupNames);
		ReportStampSupport.calculateSuiteStartTime();
		try {
			if (groupNames.contains("chrome")) {
				logger.info("chrome_browser : "	+ xlsrdr.getCellValue("chrome_browser", "value"));
				browser = xlsrdr.getCellValue("chrome_browser", "value");
				System.out.println("browser "+browser  );
			} else if (groupNames.contains("firefox")) {
				logger.info("firefox_browser : " + xlsrdr.getCellValue("firefox_browser", "value"));
				browser = xlsrdr.getCellValue("firefox_browser", "value");
			} else if (groupNames.contains("ie")) {
				logger.info("ie_browser : "	+ xlsrdr.getCellValue("ie_browser", "value"));
				browser = xlsrdr.getCellValue("ie_browser", "value");
			}else if (groupNames.contains("Android")) {
				logger.info("Android");
				browser = "android";
			}
			else if (groupNames.contains("Mobile")) {
				logger.info("iPhone");
				browser = "iphone";
			}
			else {
				logger.info("Starting browser : " + configProps.getProperty("browserType"));
				browser = configProps.getProperty("browserType");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1);
		}
		
		try {
				if(ctx.getName().contains("IBE-PROD"))
				 {
					 url = (configProps.getProperty("URL_PRODUCTION"));
					 mmburl = (configProps.getProperty("URL_PROD_MMB"));
					 loginurl = (configProps.getProperty("URL_PROD_Login"));
				 }
				 else if(ctx.getName().contains("IBE-UAT"))
				 {
					 url = (configProps.getProperty("URL_UAT"));
					 mmburl = (configProps.getProperty("URL_UAT_MMB"));
					 loginurl = (configProps.getProperty("URL_UAT_Login"));
					 chekinurl = (configProps.getProperty("URL_Checkin"));
				 }
				 else if(ctx.getName().contains("UAT_PnrRetrievalErrors"))
				 {
					 url = (configProps.getProperty("URL_UAT_MMB"));
				 }
				 else if(ctx.getName().contains("PROD_PnrRetrievalErrors"))
				 {
					 url = (configProps.getProperty("URL_PROD_MMB"));
				 }
				 else
				 {
//					 url = (configProps.getProperty("URL_PRODUCTION"));
//					 mmburl = (configProps.getProperty("URL_PROD_MMB"));
//					 loginurl = (configProps.getProperty("URL_PROD_Login"));
					 
//		********* Comment the above lines and uncomment below lines while running scripts individually to test on UAT
					 
					 url = (configProps.getProperty("URL_UAT"));
					 mmburl = (configProps.getProperty("URL_UAT_MMB"));
					 loginurl = (configProps.getProperty("URL_UAT_Login"));
					 chekinurl = (configProps.getProperty("URL_Checkin"));
				 }
			
				Reporter.reportCreater();
				HtmlReportSupport.currentSuit = ctx.getCurrentXmlTest().getSuite().getName();
			} 
			catch (Exception e1) 
			{
			e1.printStackTrace();
			}
		
		if(browser.equalsIgnoreCase("iphone")){
			try {
				// ---------------------------------------------------
				//System.out.println(System.getProperty("user.home")+"/Log/RPMob_" + timeStamp + ".log");
				String logFile = System.getProperty("user.dir")+"/Logs/RPMob_"+System.currentTimeMillis()+".log";
				System.out.println("In iphone block");
			/*while (true) {
				if (RedPlanetUtils.startAppiumForiOS(logFile)) {
					break;
				}
			}
			if ((new File(logFile).exists())) {
				System.out.println("Log File Created by Appium at path : " + System.getProperty("user.dir")
						+ "/Log/RPMob_" + timeStamp + ".log");
			}
			Thread.sleep(10000);*/
				// -----------------------------------------------------
				DeviceName = configProps.getProperty("iOSDeviceName");
				String device = configProps.getProperty("Device");
				String appPath = configProps.getProperty("appPath");
				
				String ipaPath = configProps.getProperty("ipaPath");
				String temp = System.getProperty("user.dir")+ipaPath;
				String temp2 = System.getProperty("user.dir")+appPath;
				File ipa = new File(temp);
				File app = new File(temp2);
				String platformVer = configProps.getProperty("platformVersion");
				String udid = configProps.getProperty("UDID");
				bundleID = configProps.getProperty("BundleID");
				DesiredCapabilities capabilitiesForAppium = new DesiredCapabilities();
				//System.out.println("DeviceName is : " + DeviceName);
				capabilitiesForAppium.setCapability("deviceName",device);
				capabilitiesForAppium.setCapability("platformName","iOS");
				capabilitiesForAppium.setCapability("platformVersion",platformVer);
				capabilitiesForAppium.setCapability("deviceName",device);
				capabilitiesForAppium.setCapability("bundleId", bundleID);
				capabilitiesForAppium.setCapability("newCommandTimeout","8000");
				capabilitiesForAppium.setCapability("takesScreenshot", true);
				capabilitiesForAppium.setCapability("autoWebviewTimeout","8000");
				capabilitiesForAppium.setCapability("locationServicesAuthorized", true);
				//capabilitiesForAppium.setCapability("autoLaunch", true);
				//capabilitiesForAppium.setCapability("fullReset", false);
				//capabilitiesForAppium.setCapability("noReset", true);
				capabilitiesForAppium.setCapability("waitForAppScript",
						"target.elements().length > 0; $.delay(30000); $.acceptAlert();");			
				if((DeviceName.contains("Simulator"))||((udid.length()==0))){
					System.out.println("using simulator");
					System.out.println("app Path "+app.getCanonicalPath());
					capabilitiesForAppium.setCapability("app",app.getCanonicalPath());
					
				}else{
					System.out.println("+++++using real device   "+groupNames+"+++++");
					capabilitiesForAppium.setCapability("udid", udid);
					System.out.println("ipa Path "+ipa.getCanonicalPath());
					capabilitiesForAppium.setCapability("app",ipa);
				}
				Iosdriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),
						capabilitiesForAppium);
				Iosdriver.resetApp();
				driver = Iosdriver;
				driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if (browser.equalsIgnoreCase("Android")) {
			try {
				//String AppPackage = configProps.getProperty("appPackage");
				//String AppActivity = configProps.getProperty("appActivity");
				//System.out.println(AppActivity);
				String OSVersion =  configProps.getProperty("OSVersion");
				String apkPath = configProps.getProperty("apkPath");
				String tempAPK = System.getProperty("user.dir")+apkPath;
				File apk = new File(tempAPK);
				// ---------------------------------------------------
				
				// -----------------------------------------------------
				DesiredCapabilities capabilitiesForAppium = new DesiredCapabilities();
				System.out.println("DeviceName is : " + DeviceName);
				capabilitiesForAppium.setCapability("deviceName",DeviceName);
				capabilitiesForAppium.setCapability("platformVersion",OSVersion);
				capabilitiesForAppium.setCapability("platformName","Android");
				capabilitiesForAppium.setCapability("newCommandTimeout","120000");
				//capabilitiesForAppium.setCapability("autoWebview", "true");
				capabilitiesForAppium.setCapability("autoWebviewTimeout","1000");
				//capabilitiesForAppium.setCapability("fullReset", true);
				//capabilitiesForAppium.setCapability("noReset", true);
				//capabilitiesForAppium.setCapability("appPackage", AppPackage);
				//capabilitiesForAppium.setCapability("appActivity", AppActivity);
				System.out.println(apk.getCanonicalPath());
				capabilitiesForAppium.setCapability("app", apk.getCanonicalPath());
				AndroidDriver2 = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilitiesForAppium);
				AndroidDriver2.resetApp();
				driver = (AndroidDriver2);				
			} catch (Exception e) {
				e.printStackTrace();			
			}

		}else if (browser.equalsIgnoreCase("AndroidChrome")) {
			try{
				String logFile = System.getProperty("user.dir")+"/Logs/AndroidChrome_"+System.currentTimeMillis()+".log";
				System.out.println("LOGFILE-----"+logFile);
				System.out.println("In android browser block");
				// Create object of  DesiredCapabilities class and specify android platform
				DesiredCapabilities capabilities=DesiredCapabilities.android();
				// set the capability to execute test in chrome browser
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.CHROME);
				// set the capability to execute our test in Android Platform
				  capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);
				 
				// we need to define platform name
				 capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
				 
				// Set the device name as well (you can give any name)
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Galaxy Note3");
				 
				// set the android version as well 
				  capabilities.setCapability(MobileCapabilityType.VERSION,"5.0");
				    
				  //set the time for browser to wait    
				  capabilities.setCapability("newCommandTimeout","120000");
				AndroidDriver2 = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
				driver = (AndroidDriver2);  
				
				
				}catch(Exception e){
				e.printStackTrace();
				}
		}
	}

	

	@AfterSuite(alwaysRun = true)
	public void tearDown(ITestContext ctx) throws Throwable {
		try{
		ReportStampSupport.calculateSuiteExecutionTime();
		
		HtmlReportSupport.createHtmlSummaryReport(browser, url);
		closeSummaryReport();
		if (browser.equalsIgnoreCase("iphone")) {
			//Iosdriver.removeApp(bundleID);
		}
		driver.quit();
		}catch(Exception e){
			e.printStackTrace();
		} /*finally {
			if (browser.contains("Android")) {
				RedPlanetUtils.stopAppium();
			} else if (browser.contains("iPhone")) {
				RedPlanetUtils.stopAppiumForIos();
			}
		}*/
	}
	

	/**
	 * Write results to Browser specific path
	 */
	// @Parameters({"browserType"})
	public static String filePath() {
		String strDirectoy = "";
		
		if (browser.equalsIgnoreCase("ie")) {
			strDirectoy = "IE"+File.separator+"IE";
		} else if (browser.equalsIgnoreCase("firefox")) {
			strDirectoy = "Firefox"+File.separator+"Firefox";
		} else if(browser.equalsIgnoreCase("chrome")){
			strDirectoy = "Chrome"+File.separator+"Chrome";
		}else if(browser.equalsIgnoreCase("Android")){
			strDirectoy = "Android"+File.separator+"Android";
		}else if(browser.equalsIgnoreCase("iphone")){
			strDirectoy = "iPhone"+File.separator+"iPhone";
		}else if(browser.equalsIgnoreCase("AndroidChrome")){
			strDirectoy = "AndroidChrome"+File.separator+"AndroidChrome";
		}

	if (strDirectoy != "") {
		new File(configProps.getProperty("screenShotPath") + strDirectoy
				+ "_" + timeStamp).mkdirs();
	}

	File results = new File(configProps.getProperty("screenShotPath") + strDirectoy
		+ "_" + timeStamp+File.separator+"Screenshots");
	if(!results.exists())
	{
		results.mkdir();
		HtmlReportSupport.copyLogos();
	}

	return configProps.getProperty("screenShotPath") + strDirectoy + "_"
			+ timeStamp + File.separator;

}

	/**
	 * Browser type prefix for Run ID
	 * 
	 */
	public static String result_browser() {
		if (groupNames.equals("")) {
			
		/*	if (configProps.getProperty("browserType").equals("ie")) {
				return "IE";
			} else if (configProps.getProperty("browserType").equals("firefox")) {
				return "Firefox";
			} else {
				return "Chrome";
			}*/
			
			return "Chrome";
		} 
		else {
			if (browser.equalsIgnoreCase("ie")) {
				return "IE";

			} else if (browser.equalsIgnoreCase("firefox")) {
				return "Firefox";

			}  else if (browser.equalsIgnoreCase("android")) {
				return "Android";

			}else if (browser.equalsIgnoreCase("iPhone")) {
				return "iPhone";

			}else {
				return "Chrome";

			}
			
			
			
			
			
		}
	}

	/**
	 * Related to Xpath
	 * 
	 * @Date 19/02/2013
	 * @Revision History
	 * 
	 */
	public static String methodName() {
		
			if (browser.equals("ie")) {
				return "post";
			} else {
				return "POST";
			}
		}
	@BeforeMethod(alwaysRun = true)
	public void reportHeader(Method method) throws Throwable {
		//itc = ctx;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
		String formattedDate = sdf.format(date);
		ReportStampSupport.calculateTestCaseStartTime();
			if (browser.equalsIgnoreCase("firefox")) {
			
				File file = new File("Drivers\\geckodriver.exe");
			//	System.setProperty("webdriver.gecko.driver",file.getAbsolutePath());
				System.setProperty("webdriver.firefox.marionette", file.getAbsolutePath());
				File pathToBinary = new File("C:\\Users\\E003901\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
			
				FirefoxProfile firefoxProfile = new FirefoxProfile();

				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.download.dir", "C:\\Users\\E003901\\Downloads");
				firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword, application/csv, application/ris, text/csv, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);  
				firefoxProfile.setPreference("browser.download.useDownloadDir", true);
				firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
				firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
				firefoxProfile.setPreference("browser.download.manager.useWindow", false);
				firefoxProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("pdfjs.disabled", true);
				firefoxProfile.setPreference("plugin.scan.plid.all", false);
				firefoxProfile.setPreference("plugin.scan.Acrobat", "99.0");
				firefoxProfile.setPreference("xpinstall.signatures.required", false);
				
				/*DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				FirefoxOptions options = new FirefoxOptions();
									
				options.addPreference("log", "{level: trace}");
				options.addPreference("network.proxy.http", "127.0.0.1");
				options.addPreference("network.proxy.http_port", "51969");
			
				capabilities.setCapability("marionette", true);
				capabilities.setCapability("moz:firefoxOptions", options);*/
				       
				webDriver = new FirefoxDriver(ffBinary,firefoxProfile);
				//webDriver = new FirefoxDriver(ffprofile);
			} else if (browser.equalsIgnoreCase("ie")) {
				File file = new File("Drivers\\IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
				DesiredCapabilities caps = DesiredCapabilities
						.internetExplorer();
				caps.setCapability(
						InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(CapabilityType.BROWSER_NAME, "IE");
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://uat-book-flynas.matchbyte.net/develop/#/manage/search");
				webDriver = new InternetExplorerDriver(caps);
				i = i + 1;
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver","Drivers\\chromedriver.exe");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("--disable-extensions");
				Map<String, Object> preferences = new Hashtable<String, Object>();
				options.setExperimentalOption("prefs", preferences);
				
				preferences.put("profile.default_content_settings.popups", 0);
				preferences.put("download.default_directory", "C:\\Users\\E003901\\Downloads");
				// disable flash and the PDF viewer
				preferences.put("plugins.plugins_disabled", new String[] {
				    "Adobe Flash Player",
				    "Chrome PDF Viewer"
				});
				
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				webDriver = new ChromeDriver(capabilities);
			}else if(browser.equalsIgnoreCase("iphone")){
				Iosdriver.resetApp();
			}else if (browser.equalsIgnoreCase("android")) {
					AndroidDriver2.resetApp();
			}
		flag = false;
		if((!(browser.equalsIgnoreCase("Android")))&(!(browser.equalsIgnoreCase("iPhone")))&(!(browser.equalsIgnoreCase("AndroidChrome")))){
			driver =(webDriver); /*new EventFiringWebDriver*/
			//ActionEngineSupport myListener = new ActionEngineSupport();
			//driver.register(myListener);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(url);
		}
		else if(browser.equalsIgnoreCase("AndroidChrome")){
			
			driver = (AndroidDriver2);  
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(url);
		}
		/*else{
			driver = (AndroidDriver2);  
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(url);
		}*/
		
		HtmlReportSupport.tc_name = method.getName().toString() + "-"
				+ formattedDate;
		String[] ts_Name = this.getClass().getName().toString().split("\\.");
		HtmlReportSupport.packageName = ts_Name[0] + "." + ts_Name[1] + "."
				+ ts_Name[2];
			HtmlReportSupport.testHeader(HtmlReportSupport.tc_name, browser);
		stepNum = 0;
		PassNum = 0;
		FailNum = 0;
		testName = method.getName();
		logger.info("Current Test : "+testName);
	}
		
	@AfterMethod(alwaysRun = true)
	public static void tearDownMethod() throws Throwable
	{
					
		try{
		ReportStampSupport.calculateTestCaseExecutionTime();
		closeDetailedReport();
		if(FailNum!=0)
		{
			failCounter=failCounter+1;
			testResults.put(HtmlReportSupport.tc_name, "FAIL");
		}
		else
		{
			testResults.put(HtmlReportSupport.tc_name, "PASS");
			passCounter=passCounter+1;
		}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if ((browser.toLowerCase().contains("iphone"))) {
				Iosdriver.resetApp();
			}else if(browser.toLowerCase().contains("android")){ 
				AndroidDriver2.resetApp();
				
			}else{
				driver.quit();
			}
		}
	}
	
	
}
