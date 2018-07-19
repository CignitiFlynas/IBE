package flynas.web.workflows;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ctaf.dataDriver.Logger;
import com.ctaf.googledrive.GoogleDriveAPI;
import com.ctaf.support.ExcelReader;
import com.ctaf.utilities.Reporter;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;

import flynas.web.testObjects.BookingPageLocators;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

public class BookingPageFlow<RenderedWebElement> extends BookingPageLocators{
	
	public void WaittillPageLoad() throws InterruptedException{
		waitUtilElementhasAttribute(BookingPageLocators.body);		
	}
	
	public void  login(String username,String password) throws Throwable
	{
		//click back to home button in case of error 500
		clickBackToHomeButton();
		waitForVisibilityOfElement(BookingPageLocators.email, "Email");
		waitforElement(BookingPageLocators.email);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		type(BookingPageLocators.email, username, "Email");
		type(BookingPageLocators.password, password, "Password");
		click(BookingPageLocators.login_btn, "Login");	
		if(isElementDisplayed(BookingPageLocators.logout_lnk)==true||isElementDisplayed(BookingPageLocators.logout_lnk_AR)==true||isElementDisplayed(BookingPageLocators.logout_lnk_TR)==true)
			Reporter.SuccessReport("Verifing login: ", "User Log-in successful");
		else
			Reporter.failureReport("Verifing login: ", "User Log-in failed");
		
	}
	
	public void selectLanguage(String Language) throws Throwable{
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.xpath("//div[@class='loading sk-wave']"))));
			waitforElement(BookingPageLocators.oneWay);
			if(Language.equalsIgnoreCase("Arabic")==true){
				if(isElementPresent(BookingPageLocators.Arabic_pdctn_AR("العربية"))==true)
				click(BookingPageLocators.Arabic_pdctn_AR("العربية"), "Arabic Language");
			}else if 
			(Language.equalsIgnoreCase("Turkish")==true){
				if(isElementPresent(BookingPageLocators.Arabic_pdctn_AR("Türkçe"))==true)
					click(BookingPageLocators.Arabic_pdctn_AR("Türkçe"), "Tarkish Language");
			}
			Reporter.SuccessReport("Selecting language", Language+" Language Selected");
		}catch(Exception e){
			Reporter.failureReport("Selecting language","Unable to select "+Language+" Language ");
		}
	}
	
	public boolean inputBookingDetails(String tripType, String origin, String dest, String deptDate,
			String origin2, String departure2, String retDate, String adults, String child, String infant, String promo,String Currency,String payment) throws Throwable{
		//click back to home button in case of error 500
		clickBackToHomeButton();
		waitForVisibilityOfElement(BookingPageLocators.oneWay, "One Way visible");	
		waitforElement(BookingPageLocators.oneWay);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.xpath("//div[@class='loading sk-wave']"))));
		
		if(Currency!="")
		{
			
			click(BookingPageLocators.Currency_link, "Currency");
			Thread.sleep(2000);
			//driver.findElement(By.xpath("//div[contains(text(),'"+Currency+"')]")).click();
			System.out.println(BookingPageLocators.currencytyp(Currency));
			click(BookingPageLocators.currencytyp(Currency), Currency);
		}
		
		//scrolling to find triptype
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.roundTrip));
		//Select Trip Mode
		String atrib;
		if(tripType.equalsIgnoreCase("Round Trip")){
			atrib =driver.findElement(BookingPageLocators.roundTrip).getAttribute("class");
			if(!atrib.contains("active"))
				click(BookingPageLocators.roundTrip, "Round Trip");
		} else if(tripType.equalsIgnoreCase("One Way")){
			atrib =driver.findElement(BookingPageLocators.oneWay).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.oneWay, "One Way");
		} else if(tripType.equalsIgnoreCase("Multi City")){
			atrib =driver.findElement(BookingPageLocators.multiCity).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.multiCity, "Multi City");
		}
		
		//scrolling to find Origin field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.origin));			
		
		click(BookingPageLocators.origin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin", origin);
		click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", dest);
		/*click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", dest);*/
		
		//scrolling to find departure date filed
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.dpDate));			
			
		click(BookingPageLocators.dpDate,"Departure Date");
		selectDate(BookingPageLocators.selectDate,"Departure Date",deptDate);
		
		if(tripType.equalsIgnoreCase("Round Trip")){
			if(retDate.equalsIgnoreCase(deptDate))
			{
				Reporter.failureReport("Select Return Date", "Return date can not be the same as departure date ");
			}
			else
			{
				if(isElementPresent(BookingPageLocators.datePicker)==false){
				click(BookingPageLocators.rtDate,"Return Date");}
				selectDate(BookingPageLocators.selectDate,"Return Date",retDate);
			}			
			
		}
		
		if(tripType.equalsIgnoreCase("Multi City")){
			//scrolling to find second origin fields
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.secOrigin));			
			click(BookingPageLocators.secOrigin, "Origin");
			selectCity(BookingPageLocators.selectOrigin, "Origin2", origin2);
			selectCity(BookingPageLocators.selectDest, "Destination2", departure2);
			//scrolling to find second return date fields
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.rtDate));			
			click(BookingPageLocators.rtDate,"Return Date");
			selectDate(BookingPageLocators.selectDate,"Return Date",retDate);
		}
		
		//scrolling to find passenger count fields
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.adult));			
				
		
		if(Integer.valueOf(adults)>1){
			click(BookingPageLocators.adult, "Adult");
			System.out.println("Adults: "+adults);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='"+adults+"']")));
			//driver.findElement(By.xpath("//div[text()='"+adults+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectAdult, "Adult", adults);
		}
		if(Integer.valueOf(child)>=1){
			click(BookingPageLocators.child, "Child");
			driver.findElement(By.xpath("//div[text()='"+child+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectChild, "Child", child);
		}
		if(Integer.valueOf(infant)>=1){
			click(BookingPageLocators.infant, "Infant");
			driver.findElement(By.xpath("//div[text()='"+infant+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectInfant, "Infant", infant);
		}
		
				
		
		if(payment.trim().equalsIgnoreCase("naSmile")){
			//scrolling to find smiles paints payment option
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.payWithnaSmile));			
			click(BookingPageLocators.payWithnaSmile, "payWithnaSmile");
		}
		if(!promo.equalsIgnoreCase("")){
			//scrolling to find smiles paints payment option
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.promo));			
			type(BookingPageLocators.promo, promo, "Promo");
		}
		
		//scrolling to find ""find flights button"
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.findFlights));			
		click(BookingPageLocators.findFlights,"Find Flights");
		explicityWait(BookingPageLocators.selectflightsection, "Select your Departing Flight");
		return true;
	}
	
	public void selectHoldfare() throws Throwable{
		waitforElement(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.hldFareBtn));
		click(BookingPageLocators.hldFareBtn,"Hold fare button");
	}
	
	public Float getfee(String category) throws Throwable{
		WaittillPageLoad();
		String[] fee = getText(BookingPageLocators.fee(category), category+" fare").split("\\s+");	
		return Float.parseFloat(fee[1]);	
	}
	
	public void verifyPLCK(String flightType ) throws Throwable{
		WaittillPageLoad();
		String feexpctd;
		if(flightType.equalsIgnoreCase("Domestic"))
		feexpctd = configProps.getProperty("Plckdom");
		else feexpctd = configProps.getProperty("PlckIntl");
		String feeaplied = Float.toString(getfee("Other Fees"));
			if( feeaplied.equalsIgnoreCase(feexpctd))
				Reporter.SuccessReport("Verifying Friends travel fee", "Friends travel fee "+getfee("Other Fees")+" as expected");
			else
				Reporter.failureReport("Verifying Friends travel fee", "Friends travel fee verification failed, expected - "+feexpctd+"Found - "+feeaplied);
		}
	
	public boolean selectClass(String bookingClass, String bundle) throws Throwable{
		boolean flag=false;
		waitforElement(BookingPageLocators.selectflightsection);
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[contains(@class,'table flight_table')]"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<flighttables.size();j++)
		{	
			List<WebElement> Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
			List<WebElement> Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
			
			System.out.println("E"+Ecocols.size()+"B"+Buscols.size());
		
			int li=1;
			for(int count = 0;count<10;count++)
				{if(Ecocols.size()==0 || Buscols.size()==0 )
					{
						flighttables.get(j).findElement(By.xpath("preceding::a[1]")).click();
						current.get(j).findElement(By.xpath("following-sibling::li["+li+"]")).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
						Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
						li=li+1;					
					}
				else{
					break;
					}
				}			
		
			boolean classfound = true;
			if(bookingClass.equalsIgnoreCase("Economy"))
			{
				List<WebElement> ClassEco = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
				for(int i=1;i<=ClassEco.size();i++){			
					List <WebElement> pricelables = ClassEco.get(i-1).findElements(By.xpath("div"));
					String clsname =null;
					if(pricelables.size()==1){
						clsname = pricelables.get(0).getText();
					}
					else{
						pricelables = ClassEco.get(i-1).findElements(By.xpath("button/div"));
						clsname = pricelables.get(0).getText();
					}
					System.out.println("Class name : "+clsname);
					if(clsname.equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
						classfound=false;
					}
					else{
						ClassEco.get(i-1).click();
						if(bundle.equalsIgnoreCase("Light")){
							List <WebElement> lightBtns = driver.findElements(By.xpath("//*[@class='table flight_table']/tbody["+i+"]/tr[2]/td/div/table/tbody[1]/tr/td[2]/div[2]/div[3]"));
							lightBtns.get(j).click();
						}
						else if(bundle.equalsIgnoreCase("Plus")){
							//List <WebElement> PlusBtns = driver.findElements(By.xpath("//*[@class='table flight_table']/tbody["+i+"]/tr[2]/td/div/table/tbody[1]/tr/td[3]/div[2]/div[3]"));
							//PlusBtns.get(j).click();							
						}
						else if(bundle.equalsIgnoreCase("Premium")){	
							List <WebElement> PremiumBtns = driver.findElements(By.xpath("//*[@class='table flight_table']/tbody["+i+"]/tr[2]/td/div/table/tbody[1]/tr/td[4]/div[2]/div[3]"));
							System.out.println("PremiumBTns Size : "+PremiumBtns.size());
							System.out.println("j = "+j);
							PremiumBtns.get(j).click();		
						}
						classfound=true;
						break;
					}
					
				}
				if(classfound==false){
					Reporter.failureReport("Selecting class fare", "All the flights show the simple fare to be 'Sold out'");
				}
			}
	
			else if(bookingClass.equalsIgnoreCase("Business")){
				List<WebElement> ClassBusines = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
				for(int i=1;i<=ClassBusines.size();i++){
					List <WebElement> pricelables = ClassBusines.get(i-1).findElements(By.xpath("div"));
					String clsname =null;
					if(pricelables.size()==1){
						clsname = pricelables.get(0).getText();
					}
					else{
						pricelables = ClassBusines.get(i-1).findElements(By.xpath("button/div"));
						clsname = pricelables.get(0).getText();
					}
					System.out.println("Class name : "+clsname);
					if(clsname.equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
						classfound=false;
					}else{
						ClassBusines.get(i-1).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
					
				}
				
			}		
		
		}
		
//		clickContinueBtn();
//		waitUtilElementhasAttribute(BookingPageLocators.body);
		flag=true;
		return flag;
	}
	
	//method to handle upsell pop-up. Consumes values(Continue,Upgrade)
	public static void upSellPopUpAction(String action) throws Throwable {
		waitUtilElementhasAttribute(BookingPageLocators.body);
		try{	
		if(action.equalsIgnoreCase("Continue")){
			click(BookingPageLocators.continueBundle,"Conitnue with bundle");
			//driver.findElement(By.xpath("//a[contains(text(),'continue')]")).click();
			}
		else if(action.equalsIgnoreCase("Upgrade")){
			click(BookingPageLocators.upgradeBundle,"Conitnue with bundle");
			//driver.findElement(By.xpath("//a[contains(text(),'ADD THIS BUNDLE')]")).click();
			}
		Reporter.SuccessReport("Handling pop-up", "Successfully clicked on "+action+" Button");
		}catch(Exception e){
			Reporter.failureReport("Handling pop-up", "Unable to  click on "+action+" Button");
		}
	}
	
	public boolean selectClassOneleg(String bookingClass, String bundle, String Flightleg) throws Throwable{
		boolean flag=false;
		waitforElement(BookingPageLocators.selectflightsection);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[contains(@class,'table flight_table')]"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<flighttables.size();j++)
		{	if(Flightleg.equalsIgnoreCase("Returning")==true){
			j=1;
			}
			List<WebElement> Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
			List<WebElement> Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
			
			int li=1;
			for(int count = 0;count<10;count++)
				{if(Ecocols.size()==0	|| Buscols.size()==0)
					{
						flighttables.get(j).findElement(By.xpath("preceding::a[1]")).click();
						current.get(j).findElement(By.xpath("following-sibling::li["+li+"]")).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
						Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
						
						li=li+1;					
					}
				else{
					break;
					}
				}			
		
		
			if(bookingClass.equalsIgnoreCase("Economy"))
			{
				List<WebElement> ClassEco = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
				for(int i=1;i<=ClassEco.size();i++){			
					List <WebElement> pricelables = ClassEco.get(i-1).findElements(By.xpath("div"));
					String clsname =null;
						if(pricelables.size()==1){
							clsname = pricelables.get(0).getText();
							}
						else{
							pricelables = ClassEco.get(i-1).findElements(By.xpath("button/div"));
							clsname = pricelables.get(0).getText();
						}
					System.out.println("Class name : "+clsname);
					if(clsname.equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");						
					}
					else{
						ClassEco.get(i-1).click();
						if(bundle.equalsIgnoreCase("Light")){
							List <WebElement> lightBtns = driver.findElements(By.xpath("//*[@class='table flight_table']/tbody["+i+"]/tr[2]/td/div/table/tbody[1]/tr/td[2]/div[2]/div[2]"));
							lightBtns.get(j).click();
						}
						else if(bundle.equalsIgnoreCase("Plus")){
							List <WebElement> PlusBtns = driver.findElements(By.xpath("//*[@class='table flight_table']/tbody["+i+"]/tr[2]/td/div/table/tbody[1]/tr/td[3]/div[2]/div[2]"));
							PlusBtns.get(j).click();							
						}
						else if(bundle.equalsIgnoreCase("Premium")){	
							List <WebElement> PremiumBtns = driver.findElements(By.xpath("//*[@class='table flight_table']/tbody["+i+"]/tr[2]/td/div/table/tbody[1]/tr/td[4]/div[2]/div[2]"));
							PremiumBtns.get(j).click();		
						}
						break;
					}
					
				}
			}
		else if(bookingClass.equalsIgnoreCase("Business")){
				List<WebElement> ClassBusines = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
				for(int i=1;i<=ClassBusines.size();i++){
					List <WebElement> pricelables = ClassBusines.get(i-1).findElements(By.xpath("div"));
					String clsname =null;
						if(pricelables.size()==1){
							clsname = pricelables.get(0).getText();
							}
						else{
							pricelables = ClassBusines.get(i-1).findElements(By.xpath("button/div"));
							clsname = pricelables.get(0).getText();
						}
					System.out.println("Class name : "+clsname);
					if(clsname.equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");						
					}else{
						ClassBusines.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}					
				}				
			}
			if(Flightleg.equalsIgnoreCase("Departing")==true){
				break;
				}		
		}
		
		clickContinueBtn();
		waitUtilElementhasAttribute(BookingPageLocators.body);
		flag=true;
		return flag;
	}
	
	
	
	public String[] inputPassengerDetails(String flightType, String totalPass, String nationality,String travelDoc,
	String docNum, String naSmiles, String mobileNum, String emailId,String fname,String lname,String payment2) 
					throws Throwable{
		/*WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(BookingPageLocators.loadimg)));*/
		waitforElement(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(1))));
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String lastname = null,firstname=null;
		String[] Passengername = null;
		Integer min=0,max=0;
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		try{
			//iteration for each passenger
			for(Integer count = 1; count<=Integer.valueOf(totalPass); count++){
				//defining age selection range fro each passenger type(Adult,Child,infant)
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Adult")){
					min = 14; max = 19;
				} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Child")){
					min = 5; max = 9;
				} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Infant")){
					min = 1; max = 2;
				}
				
				Thread.sleep(1000);
				//Selecting salutation 				
				click(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(count))),"Title");			
				click(By.xpath(BookingPageLocators.titleSelect.replace("#", String.valueOf(count))), "Title");
			
				if(payment2.equalsIgnoreCase("Nas")||payment2.equalsIgnoreCase("naSmile"))
				{
					//Entering given first name and last name
					type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), fname, "First Name");
					Thread.sleep(1000);
					type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lname, "Last Name");

				}
				else
				{
				//Entering random first name, last name in passenger details
					String[] Fnames = {"Zenia","Brielle","Alec","Grady","Mikayla","Kalia","Jared","Mallory","Moana","Clinton","Renee","Griffin","Merritt","Jenna","Zoe","Carla","Amber","Ayanna","Elvis","Camilla","Scarlet","Andrew","Joel","Timon","Thor","Shad","Simone","Dexter","Tana","Helen","Robert","Veda","Kirby","Molly"};
				    String[] Lnames = {"Jones","Williams","Bond","Dawney","Stathom","Stevens","Mccall","Bernard","Sanford","Matthews","Collier","Hooper","Clemons","Graham","Richmond","Richard","Morton","Watts","Bryan","Woods"};
				    int Fnameindex = (int) (Math.random() * Fnames.length);      
				    firstname =  Fnames[Fnameindex];
				    int index = (int) (Math.random() * Lnames.length);
				    lastname = Lnames[index];
					type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), firstname, "First Name");
					Thread.sleep(1000);
					type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lastname, "Last Name");
				}
				
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,225)", "");
				
				String[] DOB = new String[3];
				
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Adult"))
				{
					//fetching adult passenger's DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase("National ID card"))
						DOB = getyakeenDOB("Adult","NidDOB");
					else if (travelDoc.equalsIgnoreCase("Passport"))
						DOB = getyakeenDOB("Adult","PsprtDOB");
					else if (travelDoc.contains("Iqama"))
						DOB = getyakeenDOB("Adult","IqamaDOB");
				}
				
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Child"))
				{
					//fetching Child passenger's DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase("National ID card"))
						DOB = getyakeenDOB("Child","NidDOB");
					else if (travelDoc.equalsIgnoreCase("Passport"))
						DOB = getyakeenDOB("Child","PsprtDOB");
					else if (travelDoc.contains("Iqama"))
						DOB = getyakeenDOB("Child","IqamaDOB");
				}
				
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Infant"))
				{
					//fetching infant's  DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase("National ID card"))
						DOB = getyakeenDOB("infant","NidDOB");
					else if (travelDoc.equalsIgnoreCase("Passport"))
						DOB = getyakeenDOB("infant","PsprtDOB");
					else if (travelDoc.contains("Iqama"))
						DOB = getyakeenDOB("infant","IqamaDOB");
				}	
					
				Thread.sleep(1000);
				//Entering  Date of birth					
				click(By.xpath(BookingPageLocators.dd.replace("#", String.valueOf(count))), "DD");
				Thread.sleep(1000);
				click(By.xpath(BookingPageLocators.selectDD.replace("#", String.valueOf(count))+DOB[0]+"]"), "DD");
				Thread.sleep(1000);
				click(By.xpath(BookingPageLocators.mm.replace("#", String.valueOf(count))), "MM");
				Thread.sleep(1000);
				click(By.xpath(BookingPageLocators.selectMM.replace("#", String.valueOf(count))+DOB[1]+"]"), "MM");
				Thread.sleep(1000);
				click(By.xpath(BookingPageLocators.yyyy.replace("#", String.valueOf(count))), "YYYY");
				Thread.sleep(1000);				
				executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@class='pass_tab']/div["+count+"]//descendant::div[@class='dob_conyear']/div/ul/li//descendant::div[@class='ui-select-choices-row']["+DOB[2]+"]")));
									
				
				
				//Selecting nationality
				click(By.xpath(BookingPageLocators.nation.replace("#", String.valueOf(count))), "Nationality");
				//Thread.sleep(3000);
				executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[text()='"+nationality+"']")));
				
				//Selecting travel document Type and entering document number				
				click(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))),"Travel Document");
				click(By.xpath("//div[text()='"+travelDoc+"']"), "Travel Document");
				WebElement psngrDtlsRow = driver.findElement(By.xpath(BookingPageLocators.inputDoc.replace("#", String.valueOf(count))));
				WebElement Documentnmbr = psngrDtlsRow.findElement(By.name("idnumber"));
				Documentnmbr.clear();
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Adult"))
					Documentnmbr.sendKeys( getyakeenDOC("Adult",travelDoc));
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Child"))
					Documentnmbr.sendKeys(getyakeenDOC("Child",travelDoc));
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Infant"))
					Documentnmbr.sendKeys(getyakeenDOC("Child",travelDoc));				
		
				//Entering Expire date  if travel document is a passport
				if (travelDoc.equalsIgnoreCase("Passport"))
				{	
			
					click(By.xpath(BookingPageLocators.ppExpDD.replace("#", String.valueOf(count))), "DD");
					Thread.sleep(1000);
					click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "DD");	
					
					click(By.xpath(BookingPageLocators.ppExpMM.replace("#", String.valueOf(count))), "MM");
					Thread.sleep(1000);
					click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "MM");
					
					click(By.xpath(BookingPageLocators.ppExpYY.replace("#", String.valueOf(count))), "YYYY");
					Thread.sleep(1000);
					executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//ul/li/descendant::div[@class='ui-select-choices-row']["+randomNumber(min,max)+"]")));
				}
			
				//Entering nasmile account number
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Infant"))
				{
					System.out.println("No Smily for  "+ getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title"));
				}
				else
				{		System.out.println("nasmiles : "+naSmiles);
					//	if(!naSmiles.equalsIgnoreCase(""))
					//	type(By.xpath(BookingPageLocators.naSmiles.replace("#", String.valueOf(count))), naSmiles, "na Smiles");
				}
			
			}
			
			//Entering mobile number after filling all the passenger detail
			if(mobileNum!="")
				type(BookingPageLocators.mobileNum, mobileNum, "Mobile Number");
			//Entering email ID
			if(emailId!="")
				type(BookingPageLocators.emailAdd, emailId, "Email Address");
			
			//Clicking on contninue button 
			clickContinueBtn();
			Thread.sleep(2000);
			if(isElementPresent(BookingPageLocators.okBtn)== true){
				click(BookingPageLocators.okBtn,"Ok button on yakeen popup");
				clickContinueBtn();
			}
			
			if(isElementPresent(BookingPageLocators.okBtn)==true){
				click(BookingPageLocators.okBtn,"Ok button");
				clickContinueBtn();
			}
			
			//returning passenger first name and last name 
			Passengername = new String[2];
			Passengername[0] =firstname;
			Passengername[1] =lastname;
			return Passengername;
		}catch(Exception e){
			e.printStackTrace();
			return Passengername;
		}
		
	}
	
	public String[] getyakeenDOB(String psngrType, String travelDocType){
		ExcelReader xls = new ExcelReader(configProps.getProperty("Miscellaneous"),"Yakeendata");
		String[] DOB = xls.getCellValue(psngrType, travelDocType).split("-");
		//Changing date to index
		DOB[0] = String.valueOf(Integer.parseInt(DOB[0])-1);
		//Changing year to index
		int year = Integer.parseInt(DOB[2]);
		String thisyear = Year.now().toString();
		DOB [2] = String.valueOf(Integer.parseInt(thisyear)-year);
		return DOB;		
	}
	public String getyakeenDOC(String psngrType, String travelDocType){
		ExcelReader xls = new ExcelReader(configProps.getProperty("Miscellaneous"),"Yakeendata");
		String DOC = xls.getCellValue(psngrType, travelDocType);	
		return DOC;		
	}
	
	public boolean inputExtras(String charity) throws Throwable{
		/*WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(BookingPageLocators.loadimg)));		*/
	//	waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		//scrolling to find charity field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.selectCharity));			
				
		if (Integer.parseInt(charity)>1){
			
			if(isElementDisplayedTemp(By.xpath("//h1[contains(text(),'Charity donation')]"))){
				if(driver.findElement(BookingPageLocators.selectCharity).isEnabled()==true){
					click(BookingPageLocators.selectCharity,"Charity");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(By.xpath("//ul[@role='listbox']/li/div/a/div[contains(text(),'"+charity+"')]"), "Charity Amount");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(BookingPageLocators.selectCharity,"Charity");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(By.xpath("//ul[@role='listbox']/li/div/a/div[contains(text(),'0.00')]"), "Charity Amount");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					if(isElementDisplayedTemp(BookingPageLocators.otherfeeinSummary)==false){
						Reporter.SuccessReport("Verifing Remove Charity", "Successfully Removed");
					}
					clickContinueBtn();
					
					waitUtilElementhasAttribute(BookingPageLocators.body);
				}else{
					System.out.println("Charity is Disabled");
				}
			
			
			}
		}
		else
		{			
			clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);		
		}
			return true;
		
	}

	public void selectallSeats(String seatSelect,String totalpass,String triptype) throws Throwable {
		waitforElement(BookingPageLocators.selectseattittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> Flights = driver.findElements(By.xpath("//ul[@class='nav nav-tabs']/li"));
		List<WebElement> ele = driver.findElements((By.xpath("//div[contains(@class, 'seatpassrow')]"))); 
		if(ele.size()==0)
		{
			clickContinueBtn();
		}
		else
		{
			int k;
			List<WebElement> extlegseats = driver.findElements(BookingPageLocators.selExLegSeat);
			List<WebElement> busiseats = driver.findElements(BookingPageLocators.selbusSeat);
			int loop=0;
			for(int j=0;j<Flights.size();j++){
				for(int i=0;i<Integer.parseInt(totalpass);i++){
					if(seatSelect.equalsIgnoreCase("Spacious")){
						if(Flights.get(j).getAttribute("class").contains("active")){
							for(k=loop;k<extlegseats.size();k++)
							{
								if(extlegseats.get(k).isDisplayed()==true){
									extlegseats.get(k).click();
									Reporter.SuccessReport("Click", "Successfully Click on Extra Leg Room ");
									if(isElementDisplayedTemp(BookingPageLocators.ok)){
										click(BookingPageLocators.ok, "OK");
										waitUtilElementhasAttribute(BookingPageLocators.body);
										
									}	
									loop=loop+1;
									break;
								}else{
									System.out.println("Seat was Already Selected");
									loop=loop+1;
									
								}
																	
								}
							/*if(i==Integer.parseInt(totalpass)-1){
									break;
							}*/	
							
								
							//continue;	
						}
						
					}
					if(seatSelect.equalsIgnoreCase("Business")){
						if(Flights.get(j).getAttribute("class").contains("active")){
							for(k=loop;k<busiseats.size();k++)
							{
								if(busiseats.get(k).isDisplayed()==true){
									busiseats.get(k).click();
									Reporter.SuccessReport("Click", "Successfully Click on Business");
									if(isElementDisplayedTemp(BookingPageLocators.ok)){
										click(BookingPageLocators.ok, "OK");
										
									}	
									loop=loop+1;
									break;
								}else{
									System.out.println("Seat was Already Selected");
									loop=loop+1;
									
								}
																	
								}
							/*if(i==Integer.parseInt(totalpass)-1){
									break;
							}*/	
							
								
							//continue;	
						}
						
				}
				
			}
			
		}
		
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK']"))==true){
			click(By.xpath("//button[text()='OK']"), "OK Button");
			}
			else{
			System.out.println("No alert Present");
			}
		}
	}
	
	public void removeallseats(String seatSelect,String totalpass,String triptype) throws Throwable{
		List<WebElement> remove = driver.findElements(BookingPageLocators.Remove);
		List<WebElement> flights = driver.findElements(BookingPageLocators.flightsInSeat);
		flights.get(0).click();
		int j=1,loop=0;
		for(int i=loop;i<remove.size();i++){
			if(remove.get(i).isDisplayed()==false){
				flights.get(j).click();
				j=j+1;
				remove.get(i).click();
				loop=loop+1;
				waitUtilElementhasAttribute(BookingPageLocators.body);
			}else{
				remove.get(i).click();
				loop=loop+1;
				waitUtilElementhasAttribute(BookingPageLocators.body);
			}
		}
		List<WebElement> seatinsummary = driver.findElements(BookingPageLocators.seatInsummary);
		if(seatinsummary.size()==0){
			Reporter.SuccessReport("Verifing Seat Removing", "Successfully Removed");
		}
			
	}
	
	public boolean selectSeat(String seatSelect, String bookingtype) throws Throwable {
		waitForElementPresent(BookingPageLocators.selectseattittle, "SelectSeatTittle");
		waitUtilElementhasAttribute(BookingPageLocators.body);	
		List<WebElement> Flights = driver.findElements(By.xpath("//div[@class='tabwrap']/ul/li/a"));
		if(bookingtype.equalsIgnoreCase("CodeShare")||bookingtype.equalsIgnoreCase("PartcodeShare"))
		{
			clickContinueBtn();
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK']"))){
				click(By.xpath("//button[text()='OK']"), "OK Button");
			}
			else
			{
				System.out.println("No Alert Present");
			}

		}
		else
		{	
					
			for(int i=0;i<Flights.size();i++)
			{			
				Thread.sleep(3000);
				
				if(seatSelect.equalsIgnoreCase("Spacious"))
				{
				//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.selExLegSeat));
				List<WebElement> seats = driver.findElements(BookingPageLocators.selExLegSeat);
				try{
					for(int k=1;k<seats.size();k++)
					{
						try{
							seats.get(k).click();
							System.out.println("Premium seat no " + k + "selected");
							break;
							}
						catch(Exception e){
						System.out.println("Premium seat no " + k + "is not available");
						}
					}
				}
				catch(Exception e){
					System.out.println("Unable to click Premium seat ");
					
					}
				}	
				
				else if(seatSelect.equalsIgnoreCase("Super Practical"))
					{					
					List<WebElement> seats = driver.findElements(BookingPageLocators.selPremSeat);
						try{
							for(int k=1;k<seats.size();k++)
							{
								try{
									seats.get(k).click();
									System.out.println("Extra  leg romm seat no " + k + "selected");
									break;
									}
								catch(Exception e){
								System.out.println("Extra  leg romm seat no " + k + "is not available");
								}
							}
						}
						catch(Exception e){
							System.out.println("Unable to click extra leg room seat ");
							
							}
					}
					
				else if(seatSelect.equalsIgnoreCase("Practical"))
					{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selUpfrontSeat));
					List<WebElement> seats = driver.findElements(BookingPageLocators.selUpfrontSeat);
					try{
						for(int k=1;k<seats.size();k++)
						{
							try{
								seats.get(k).click();
								System.out.println("Upfront seat no " + k + "selected");
								break;
								}
							catch(Exception e){
							System.out.println("Upfront seat no " + k + "is not available");
							}
						}
					}
					catch(Exception e){
						System.out.println("Unable to click Upfront seat ");
						
						}
					}
					
				
				else if(seatSelect.equalsIgnoreCase("Simple"))
					{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selStdSeat));
					List<WebElement> seats = driver.findElements(BookingPageLocators.selStdSeat);
					try{
						for(int k=1;k<seats.size();k++)
						{
							try{
								seats.get(k).click();
								System.out.println("Upfront seat no " + k + "selected");
								break;
								}
							catch(Exception e){
							System.out.println("Upfront seat no " + k + "is not available");
							}
						}
					}
					catch(Exception e){
						System.out.println("Unable to click Upfront seat ");
						
						}
					}
				
					
				else if(seatSelect.equalsIgnoreCase("Business"))
					
					{
						//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selbusSeat));
						List<WebElement> seats = driver.findElements(BookingPageLocators.selbusSeat);
						try{
							for(int k=1;k<seats.size();k++)
							{
								try{
									seats.get(k).click();
									System.out.println("Upfront seat no " + k + "selected");
									break;
									}
								catch(Exception e){
								System.out.println("Upfront seat no " + k + "is not available");
								}
							}
						}
						catch(Exception e){
							System.out.println("Unable to click Upfront seat ");
							
							}
					}
					
								
				if(isElementDisplayedTemp(By.xpath("//button[text()='OK'  or ./text()='موافق' or ./text()='Tamam']")))
				{
					click(By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']"), "OK Button");
					Thread.sleep(5000);
					
				}
				else
				{
					System.out.println("No Ok");
				}
			}
			
			clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);	
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']")))
			{
				click(By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']"), "OK Button");
			}
			else
			{
				System.out.println("No Alert Present");
			}
		
		}
		return true;
}
	
	public void selectPaymentType(String paymentType) throws Throwable{	
		try{
		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
		if(paymentType.equalsIgnoreCase("Credit Card")){
				for(int i=0;i<paymentss.size();i++){
					if(paymentss.get(i).getText().contains("Credit Card")
					||paymentss.get(i).getText().contains("Kredi Kartı")
					||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
		}
		Reporter.SuccessReport("Selecting payment type", paymentType+" button cliked successfully");
		}catch (Exception e){
			Reporter.failureReport("Selecting payment type", paymentType+" button click failed");
		}
	}
	
	public void enterCardDetails(String CardType) throws Throwable{
		try{
			System.out.println("CardType : "+CardType);
		ExcelReader xls = new ExcelReader(configProps.getProperty("Miscellaneous"),"Cards");		
		String Number = xls.getCellValue(CardType, "Number");
		System.out.println("Card Number : "+Number);
		String Name = xls.getCellValue(CardType, "Name");
		System.out.println("Name : "+Name);
		String expMonth= xls.getCellValue(CardType, "Expmonth");
		System.out.println("Exp month : "+expMonth);
		String expYear= xls.getCellValue(CardType, "Expyear");
		System.out.println("Exp Year : "+expYear);
		String cvv	 = xls.getCellValue(CardType, "CVV");
		
			
		waitforElement(BookingPageLocators.cardNumber);
		type(BookingPageLocators.cardNumber,Number,"Card Number");
		type(BookingPageLocators.cardName,Name,"Card Holder Name");
		click(BookingPageLocators.expMonth,"Expiry Month");
		//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
		driver.findElement(By.xpath("//*[@role='option']["+expMonth+"]")).click();
		//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
		click(BookingPageLocators.expYear,"Expiry Year");
		System.out.println("//div[contains(text(),'"+expYear+"')]");
		driver.findElement(By.xpath("//div[contains(text(),'"+expYear+"')]")).click();
		//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
		type(BookingPageLocators.cvvNum,cvv,"cvv");
		clickContinueBtn();	
		Reporter.SuccessReport("Entering Card details", "Card details entered successfully");
		}catch(Exception e){
			Reporter.failureReport("Entering Card details", "Card details Could not be entered");
		}
	}
	
	public void submit3Dsecurepin() throws Throwable{
		try{
		if(isElementDisplayedTemp(BookingPageLocators.pasword)==true)
		{
			type(BookingPageLocators.pasword, "1234", "Password");
			click(BookingPageLocators.ccSubmit,"Submit Button");
			if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
				click(BookingPageLocators.ok, "OK");}
			Reporter.SuccessReport("Entering 3D secure Pin ", "3D Secure Pin entered successfully");
		}
		else{System.out.println("No Secure Page");
			Reporter.SuccessReport("Entering 3D secure Pin ", "No Secure Pin Page Displayed");
			}		
		}catch(Exception e){
			Reporter.failureReport("Entering 3D secure Pin ", "3D Secure pin entry failed");
		}
	}
	
	public void payonMMB(String PaymentType) throws Throwable{		
		waitforElement(BookingPageLocators.paynow);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		click(BookingPageLocators.paynow, "Pay Now");
		selectPaymentType(PaymentType);
		enterCardDetails("Visa");
		handleSecurePage();	
		waitUtilElementhasAttribute(BookingPageLocators.body);
	}
	
	public void handleSecurePage() throws Throwable{

		boolean flag = false;
		Thread.sleep(5000);
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for(WebElement frame:frames){
			System.out.println(frame.getAttribute("id"));
			if(frame.getAttribute("id").equalsIgnoreCase("authWindow")){
				driver.switchTo().frame("authWindow");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.MILLISECONDS);
				if(isElementPresent(BookingPageLocators.pasword)==true)
				{
					//type(BookingPageLocators.pasword, "1234", "Password");
					click(BookingPageLocators.ccSubmit,"Submit Button");
					if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
						click(BookingPageLocators.ok, "OK");
						//payment("Credit Card", "");								
					}
					flag = true;
					break;
				}
				}
			}										
		if(flag== false)
		{
		System.out.println("No Secure Page");
		}
	

	}
	
	

	public boolean payment(String paymentType,String value) throws Throwable 
	{		
 		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		System.out.println(paymentType);
		
		if(paymentType.equalsIgnoreCase("Credit Card"))
			{
				List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++){
					if(paymentss.get(i).getText().contains("Credit/Debit Card")
					||paymentss.get(i).getText().contains("Kredi Kartı")
					||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
				//waitforElement(BookingPageLocators.cardNumber);
				waitForVisibilityOfElement(BookingPageLocators.cardNumber, "CardNumber");
				type(BookingPageLocators.cardNumber,configProps.getProperty("cardNumber").trim(),"Card Number");
				type(BookingPageLocators.cardName,configProps.getProperty("cardHolderName"),"Card Holder Name");
				click(BookingPageLocators.expMonth,"Expiry Month");
				//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
				driver.findElement(By.xpath("//a/div[contains(text(),'"+configProps.getProperty("expMM")+"') or ./text()='يناير' or ./text()='Ocak']")).click();
				//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
				click(BookingPageLocators.expYear,"Expiry Year");
				driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expYYYY")+"']")).click();
				//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
				type(BookingPageLocators.cvvNum,configProps.getProperty("cvv"),"cvv");
			}
		
			else if(paymentType.equalsIgnoreCase("Voucher"))
			{
				type(BookingPageLocators.voucherNum,configProps.getProperty("voucher"),"Voucher");
				click(BookingPageLocators.retrieveVoucher, "Retrieve Voucher");
			} 
		
			else if(paymentType.equalsIgnoreCase("SADAD"))
			{
		
				List<WebElement>  paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().equalsIgnoreCase("SADAD"))
					{
						paymentss.get(i).click();					
						break;
					}
				}
			}
		
			else if(paymentType.equalsIgnoreCase("Nas"))
			{
				List<WebElement> payments = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<payments.size();i++)
				{
					if(payments.get(i).getText().contains("Nas Credit"))
					{
						payments.get(i).click();
						if(isElementPresent(BookingPageLocators.ipt_pnr)==true){
							type(BookingPageLocators.ipt_pnr, value, "PNR");
							Thread.sleep(3000);
							click(BookingPageLocators.Retrive, "Retrive");
							Thread.sleep(5000);
							}
						String totaldue = getText(BookingPageLocators.totaldue, "TotalDue");
						System.out.println(totaldue);
						String[] due = totaldue.split("\\s");
						String tempdue = due[due.length-1].split("\\.")[0];						
						tempdue = removeSpecialchars(tempdue);
						int pay = Integer.parseInt(tempdue);
						System.out.println("Pay -" + pay);
						String creditbalance = getText(BookingPageLocators.creditbal, "Credit Balance");
						System.out.println(creditbalance);
						String[] credit = creditbalance.split("\\s");
						String tempcredit = credit[credit.length-1].split("\\.")[0];	
						tempcredit = removeSpecialchars(tempcredit);
						int creditbal =Integer.parseInt(tempcredit);
						System.out.println("Creditbal - "+creditbal);
						if(creditbal<=pay){
						type(BookingPageLocators.Amount, credit[credit.length-1], "Amount");
						type(BookingPageLocators.Amount, credit[credit.length-1], "Amount");
						}else{
							type(BookingPageLocators.Amount, due[due.length-1], "Amount");
						}
						break;				
					}
				}

			}
		
			else if(paymentType.equalsIgnoreCase("Agency Payment"))
			{
				List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Agency Payment"))
					{
						paymentss.get(i).click();					
						break;
					}
				}
			}
		
			else if(paymentType.equalsIgnoreCase("Hold Booking"))
			{
				List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Hold Booking"))
					{
						paymentss.get(i).click();					
						break;
					}
				}
			}
			
		
		if(driver.findElement(BookingPageLocators.ccCheckbox).isSelected()==true)
			{
				System.out.println("Terms and conditions already Checked");
			}
			else
			{
			click(BookingPageLocators.ccCheckbox, "Terms & Conditions");
			}
			Thread.sleep(2000);
			
			
		//Click continue on payment page
		clickContinueBtn();
		Thread.sleep(2000); //Don't delete this thread. Solution for Sync issue on error pop-up
		//handling error pop-up
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true && paymentType.equalsIgnoreCase("SADAD"))
			{
			String env = driver.getCurrentUrl();
			if(env.contains("develop_r41")||env.contains("uat")){
				Reporter.SuccessReport("Verifing SADAD", "Payment through SADAD not posible");		
				}
			else{
				Reporter.failureReport("Verifing SADAD", "Payment through SADAD not posible");
				}
			}
		else
			{
				System.out.println("No Error Message");
			}
		
		//handling alert pop-up
		if(isElementDisplayedTemp(BookingPageLocators.ok)==true)
			{
				click(BookingPageLocators.ok, "OK");				
			}
			else
			{
				System.out.println("No Alert");
			}
		
		//handling 3D secure page
		if(paymentType.equalsIgnoreCase("Credit Card"))
			{
				boolean flag = false;
				Thread.sleep(5000);
				List<WebElement> frames = driver.findElements(By.tagName("iframe"));
				for(WebElement frame:frames){
					System.out.println(frame.getAttribute("id"));
					if(frame.getAttribute("id").equalsIgnoreCase("authWindow")){
						driver.switchTo().frame("authWindow");
						driver.manage().timeouts().implicitlyWait(5000,TimeUnit.MILLISECONDS);
						if(isElementPresent(BookingPageLocators.pasword)==true)
						{
							type(BookingPageLocators.pasword, "1234", "Password");
							click(BookingPageLocators.ccSubmit,"Submit Button");
							if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
								click(BookingPageLocators.ok, "OK");
								//payment("Credit Card", "");								
							}
							flag = true;
							break;
						}
						}
					}										
				if(flag== false)
				{
				System.out.println("No Secure Page");
				}
			
		}
			
		
	
	//Code to add invalid card detail
	
	if(paymentType.equalsIgnoreCase("invalidcc"))
		{
			List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
			for(int i=0;i<paymentss.size();i++){
				if(paymentss.get(i).getText().contains("Credit Card")
						||paymentss.get(i).getText().contains("Kredi Kartı")
						||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
					paymentss.get(i).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
					break;
				}
			}
			waitforElement(BookingPageLocators.cardNumber);
			type(BookingPageLocators.cardNumber,configProps.getProperty("InvalidcardNumber").trim(),"Card Number");
			type(BookingPageLocators.cardName,configProps.getProperty("cardHolderName"),"Card Holder Name");
			click(BookingPageLocators.expMonth,"Expiry Month");
			//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
			driver.findElement(By.xpath("//a/div[contains(text(),'"+configProps.getProperty("expMM")+"') or ./text()='يناير' or ./text()='Ocak']")).click();
			//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
			click(BookingPageLocators.expYear,"Expiry Year");
			driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expYYYY")+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
			type(BookingPageLocators.cvvNum,configProps.getProperty("cvv"),"cvv");
		}
	
	if(paymentType.equalsIgnoreCase("wrongcc"))
		{
			List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
			for(int i=0;i<paymentss.size();i++){
				if(paymentss.get(i).getText().contains("Credit Card")
						||paymentss.get(i).getText().contains("Kredi Kartı")
						||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
					paymentss.get(i).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
					break;
				}
			}
			waitforElement(BookingPageLocators.cardNumber);
			type(BookingPageLocators.cardNumber,configProps.getProperty("MasterCrdNumber").trim(),"Card Number");
			type(BookingPageLocators.cardName,configProps.getProperty("cardHolderName"),"Card Holder Name");
			click(BookingPageLocators.expMonth,"Expiry Month");
			//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
			driver.findElement(By.xpath("//a/div[contains(text(),'"+configProps.getProperty("expMM")+"') or ./text()='يناير' or ./text()='Ocak']")).click();
			//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
			click(BookingPageLocators.expYear,"Expiry Year");
			driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expYYYY")+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
			type(BookingPageLocators.cvvNum,configProps.getProperty("cvv"),"cvv");
		}
	
		return true;
	}
	
	public void nasmilespayment(String Username,String Password) throws Throwable {
		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		System.out.println(paymentType);		
		List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
		for(int i=0;i<paymentss.size();i++)
		{
			if(paymentss.get(i).getText().contains("naSmiles"))
			{
				paymentss.get(i).click();
				Thread.sleep(1000);
				break;
			}
		}
		type(BookingPageLocators.naSmileId,Username, "naSmileID");
		type(BookingPageLocators.naSmilepwd,Password, "naSmilePwd");
		click(BookingPageLocators.signIn_lnk, "SignIn");
		Thread.sleep(2000);
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true)
		{
			System.out.println("NO Sufitiant points");
		}
		else
		{
		click(BookingPageFlow.redeem, "Redeem");
		Thread.sleep(2000);
		if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
			click(BookingPageLocators.ok, "OK");
			payment("Credit Card", "");			
			}
		}
	}
	
	public String getReferenceNumber() throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		waitforElement(BookingPageLocators.summaryRefNumber);
		return getText(BookingPageLocators.summaryRefNumber, "Reference Number");			
	}
	
	public void closetoast() throws Throwable{		
	try{
		//driver.switchTo().activeElement();
		//String handle = driver.getWindowHandle();
		List<WebElement> elements = driver.findElements(By.tagName("iframe"));
		System.out.println("No of iframes : "+ elements.size());
	  	if(isElementPresent(BookingPageLocators.naSmileTaost)==true){
	  		driver.switchTo().frame("yief130002");
			System.out.println("nasmile Toast appeared");
			click(BookingPageLocators.closetoast, "nasmile Toast close button");
			}
	  	else if(isElementPresent(BookingPageLocators.closeTst)==true){
	  		click(BookingPageLocators.closeTst, "nasmile Toast close button");
	  	}
	  	else if(isElementPresent(BookingPageLocators.closeTstnew)==true){
	  		click(BookingPageLocators.closeTstnew, "nasmile Toast close button");
	  	}
		else{
			System.out.println("No nasmile Toast");
			}
		//driver.switchTo().window(handle);
		}catch (Exception e){
		System.out.println("No nasmile Toast");
		}
	}
	
	public void searchFlight(String referenceNum, String email, String mobile, String lastName) throws Throwable{
		// add validation
		driver.get(mmburl);
		waitforElement(BookingPageLocators.sfpbookingReference);
		type(BookingPageLocators.sfpbookingReference, referenceNum, "Reference Number");
		//type(BookingPageLocators.sfpEmail, email, "Email");
		//type(BookingPageLocators.sfpMoblie, mobile, "Mobile");
		type(BookingPageLocators.sfpLastName, lastName, "Last Name");
		click(BookingPageLocators.sfpFindBooking, "Find booking");	
		waitforElement(BookingPageLocators.manageMyBookingTittle);
	
	}
	
	
	public void searchFlightCheckin(String referenceNum, String email, String mobile, String lastName) throws Throwable{
		// add validation
		driver.get(chekinurl);
		waitForElementPresent(BookingPageLocators.sfpbookingReference, "Reference Number");
		type(BookingPageLocators.sfpbookingReference, referenceNum, "Reference Number");
		//type(BookingPageLocators.sfpEmail, email, "Email");
		//type(BookingPageLocators.sfpMoblie, mobile, "Mobile");
		type(BookingPageLocators.sfpLastName, lastName, "Last Name");
		click(BookingPageLocators.checkInNow, "Check-in button");
		}
	
	public void changeDate(String newDepatureDate,String newReturnDate, String Flightway) throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.btnchngFlight));
		click(BookingPageLocators.btnchngFlight, "Change Flight");
		waitforElement(BookingPageLocators.btnpopupchngFlight);
		List<WebElement> calenders = driver.findElements(BookingPageLocators.calender);
		if(Flightway.equalsIgnoreCase("Departing")){
			calenders.get(0).click();
			selectDate(BookingPageLocators.selectDate,"Departure Date", newDepatureDate);
		}
		else if(Flightway.equalsIgnoreCase("Returning")){
			calenders.get(1).click();
			selectDate(BookingPageLocators.selectDate,"Returning Date", newReturnDate);
		}
		else{
			String[] Date = new String[2];
			Date[0]=newDepatureDate;
			Date[1]=newReturnDate;
			for (int i=0; i<calenders.size();i++){
				System.out.println(Date[i]);
				calenders.get(i).click();
				selectDate(BookingPageLocators.selectDate,"All Dates", Date[i]);
				}
		}		
		click(BookingPageLocators.btnpopupchngFlight, "Change flight");
	}

	
	public String changeDate(String referenceNum, String email, String mobile, String lastName, String newDate, 
			String selectSeat,String bookingtype,String bookingClass,int i) throws Throwable
		{
		searchFlight(referenceNum, email, mobile, lastName);
		Thread.sleep(10000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.btnchngFlight));
		click(BookingPageLocators.btnchngFlight, "Change Flight");
		waitforElement(BookingPageLocators.btnpopupchngFlight);
		List<WebElement> calenders = driver.findElements(BookingPageLocators.calender);
		calenders.get(i).click();
		//	click(BookingPageLocators.calender, "Flight Calender");
		selectDate(BookingPageLocators.selectDate,"Departure Date", newDate);
		click(BookingPageLocators.btnpopupchngFlight, "Change flight");
		
		if(bookingClass.equalsIgnoreCase("Economy"))
		{
			selectClass(bookingClass, "Premium");
			/*waitforElement(BookingPageLocators.economyOW);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.economyOW, "Economy New Flight");*/
			
		}
		else if(bookingClass.equalsIgnoreCase("Business"))
			{
				selectClass(bookingClass, "");
				/*waitforElement(BookingPageLocators.busOW);
				waitUtilElementhasAttribute(BookingPageLocators.body);
				click(BookingPageLocators.busOW, "Business New Flight");*/
			}
		/*else if(bookingClass.equalsIgnoreCase("Flex"))
			{
				waitforElement(BookingPageLocators.flexOW);
				waitUtilElementhasAttribute(BookingPageLocators.body);
				click(BookingPageLocators.flexOW, "Flex New Flight");
			}*/
		else if(bookingClass.contains("Staff"))
			{			
				selectClassForStaff(bookingClass);
			/*	waitForElementPresent(By.xpath("//table[@class='table flight_table']"), "");
				List<WebElement> flighttables = driver.findElements(By.xpath("//table[@class='table flight_table']"));
				List<WebElement> stafcnfdClass = flighttables.get(i).findElements(By.xpath("tbody/tr/td[7]"));
				for(int j=0;j<stafcnfdClass.size();j++)
				{
					if(stafcnfdClass.get(j).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
					}
					else
					{
						stafcnfdClass.get(j).click();
						break;
					}
			
				} */

			}
		else
		{
		/*	waitForElementPresent(By.xpath("//table[@class='table flight_table']"), "");
			List<WebElement> flighttables = driver.findElements(By.xpath("//table[@class='table flight_table']"));
			List<WebElement> stafstandClass = flighttables.get(i).findElements(By.xpath("tbody/tr/td[6]"));
			for(int j=0;j<stafstandClass.size();j++)
			{
				if(stafstandClass.get(j).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))
				{
					System.out.println("Sold Out");
				}
				else
				{
					stafstandClass.get(j).click();
					break;
				}
		
			}*/
		}
		waitforElement(BookingPageLocators.continueBtn);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		clickContinueBtn();
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementPresent(BookingPageLocators.selectseattittle)==true){			
			selectSeat(selectSeat,bookingtype);}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		waitForElementPresent(BookingPageLocators.manageMyBookingTittle, "Manage My Booking");
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementPresent(BookingPageLocators.paynow)==true)
		{
			System.out.println("In paynow");
			/*String balance= getText(BookingPageLocators.balance, "Balance");
			String bal[] = balance.split("\\s");*/
			click(BookingPageLocators.paynow, "PayNow");			
			payment("Credit Card", "");
		}
		else
		{
			System.out.println("In confirmmsg");
			if(isElementPresent(BookingPageFlow.confirmmsg)==true)
			{
				click(BookingPageLocators.confirmmsg, "Confirmation");
				if(isElementPresent(BookingPageLocators.ok)==true)
				{
					click(BookingPageLocators.ok, "OK");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					waitForElementPresent(BookingPageLocators.manageMyBookingTittle, "Manage My Bookings");
					waitUtilElementhasAttribute(BookingPageLocators.body);
				}
			}
		}
		//Alert();
		return getReferenceNumber();
	}	
	
	public void performCheckin(String SelectSeat,String paymenttype, String strPassenger) throws Throwable
	{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true){
			Reporter.SuccessReport("Performimg Checkin", "Departure time of the flight is with in 4 hrs or after 48 hours");
		}
		else{
		waitforElement(BookingPageLocators.checkinTitle);
		List<WebElement> passengers = driver.findElements(BookingPageLocators.passengers_incheckin);
		System.out.println(passengers.size());
		for(int i=0;i<passengers.size();i++)
		{
			passengers.get(i).click();
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.continueBtn));
		click(BookingPageLocators.passengers_checkterms, "CheckBox for agriment");
		clickContinueBtn();
		waitforElement(BookingPageLocators.emailAdd);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String emailformat = getText(BookingPageLocators.emailAdd, "EmailAddress");
		if(emailformat.contains("@")==false)
		{
			driver.findElement(BookingPageLocators.emailAdd).clear();
			type(BookingPageLocators.emailAdd, "flynasqa@gmail.com", "EmailAddress");
		}
		waitforElement(BookingPageLocators.continueBtn);
		clickContinueBtn();
		coninueOnBaggage();
		waitforElement(BookingPageLocators.selectseattittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.selectseattittle))
		{
			selectSeat(SelectSeat, "");
		}else
		{
			System.out.println("No Seat Page is Available in CheckIn");
		}
		if(isElementDisplayedTemp(BookingPageLocators.paymentTittle))
		{
			payment(paymenttype, "");
		}else
		{
			System.out.println("No Payment is Available in CheckIn");
		}
		}
	}

	public void Baggage(String bookingtype) throws Throwable
	{	
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.Adult_baggagae));
		
		//explicityWait(BookingPageLocators.Adult_baggagae, "Baggage");
		
		if(bookingtype.equalsIgnoreCase("CodeShare")||bookingtype.equalsIgnoreCase("PartcodeShare"))
		{
			clickContinueBtn();	
		}
		else
		{		
		List<WebElement>  Adults = driver.findElements(BookingPageLocators.Adult_baggagae);
		for(int i=0;i<Adults.size();i++)
		{
			String value=Adults.get(i).getText();
			if(value.equalsIgnoreCase("Adult 1"))
			{
				Adults.get(i).click();
				ImplicitWait();
				List<WebElement>  Baggage_weight = driver.findElements(BookingPageLocators.Baggage_weight);
				for(int j=1;j<=Baggage_weight.size();j++)
				{
					Baggage_weight.get(j).click();
					ImplicitWait();
					break;

				}
				

			}
			else
				if(value.equalsIgnoreCase("Child 1"))
				{
					Adults.get(i).click();
					ImplicitWait();
					List<WebElement>  Baggage_weight = driver.findElements(BookingPageLocators.Baggage_weight);
					for(int j=1;j<=Baggage_weight.size();j++)
					{
						Baggage_weight.get(j).click();
						ImplicitWait();
						break;
					}

					
				}
			}	
		}
		coninueOnBaggage();
	}	
	
	public void validateCheckin() throws Throwable
	{
		waitforElement(BookingPageLocators.checkinConformation);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementPresent(BookingPageLocators.checkinConformation,"Check-in Conformation"))
		{
			Reporter.SuccessReport("Validating check in", "Checkin is Done");
		}
		else
		{
			Reporter.failureReport("Validating check in", "Check in is Not Done");
		}
	}
	
	public void verifyAlertPopup() throws Throwable
	{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true){
			click(BookingPageLocators.ok, "OK");
			Reporter.SuccessReport("Validating if failure message", "Failure message displayed");		
		}
		else{
			Reporter.failureReport("Validating if failure message", "Failure message is not displayed");					
		}
	}

	public void validate_ticketStatus(String pnr) throws Throwable
	{
		waitforElement(BookingPageLocators.summaryStatus);
		//waitUtilElementhasAttribute(BookingPageLocators.body);
		if(getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Confirmed")
				||getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Pending")
				||getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Hold"))
		{
			String env = driver.getCurrentUrl();
			if(env.contains("develop_r41")){writingPNR("IBE_NAV_PNR",pnr);}
			else if(env.contains("uat")){writingPNR("IBE_UAT_PNR",pnr);}
			else{writingPNR("IBE_PROD_PNR",pnr);}
			
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked with PNR "+pnr);			
		}
		else
		{	
			Reporter.failureReport("Ticket Confirmation", "Ticket has not booked");
			
		}
		closetoast();
	}
	
	public void validate_ticketStatus_AR(String pnr) throws Throwable
	{
		waitforElement(BookingPageLocators.summaryStatus_AR_uat);
		if(getText(BookingPageLocators.summaryStatus_AR_uat,"Status").trim().equals("مؤكد"))
		{
			String env = driver.getCurrentUrl();
			if(env.contains("develop_r41")){writingPNR("IBE_NAV_PNR",pnr);}
			else if(env.contains("uat")){writingPNR("IBE_UAT_PNR",pnr);}
			else{writingPNR("IBE_PROD_PNR",pnr);}
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked with PNR "+pnr);
		}
		else
		{
			Reporter.failureReport("Ticket Confirmation", "Ticket has not booked");
		}
		closetoast();
	}
	public void validate_ticketStatus_TR(String pnr) throws Throwable
	{
		waitforElement(BookingPageLocators.summaryStatus_TR_uat);
		if(getText(BookingPageLocators.summaryStatus_TR_uat,"Status").trim().equals("Confirmed")
			||getText(BookingPageLocators.summaryStatus_TR_uat,"Status").trim().equals("Onaylandı")	)
		{
			String env = driver.getCurrentUrl();
			if(env.contains("develop_r41")){writingPNR("IBE_NAV_PNR",pnr);}
			else if(env.contains("uat")){writingPNR("IBE_UAT_PNR",pnr);}
			else{writingPNR("IBE_PROD_PNR",pnr);}
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked with PNR "+pnr);
		}
		else
		{
			Reporter.failureReport("Ticket Confirmation", "Ticket has not booked");
		}
		closetoast();
	}
	
	public void verifyPNRforSadad() throws Throwable{
		waitforElement(BookingPageLocators.body);
		Thread.sleep(15000);//Don't change this sleep. Prod response takes time in sadad payment
		if(isElementPresent(BookingPageLocators.txtPNR, "Booking Reference")){
			String Status= getText(BookingPageLocators.summaryStatus, "PNR Status");
			if (Status.equalsIgnoreCase("Pending")){
			String pnr = driver.findElement(By.xpath("//div[text()='Booking Reference: ']/b")).getText();
			String env = driver.getCurrentUrl();
				if(env.contains("develop_r41")){writingPNR("IBE_NAV_PNR",pnr);}
				else if(env.contains("uat")){writingPNR("IBE_UAT_PNR",pnr);}
				else{writingPNR("IBE_PROD_PNR",pnr);}
				
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked,PNR : " + pnr);
			}
			else {
				Reporter.failureReport("Ticket Confirmation", "Ticket not booked");
				writingPNR("ProductionRoutes_pnr","Fail");
			}
		}
		else{
			Reporter.failureReport("Ticket Confirmation", "Payment failed");			
		}	
		closetoast();
	}
	
	public void WritePromoStatusPNR(int rownum, String pnr) throws Throwable
	{
		waitforElement(BookingPageLocators.summaryStatus);
		//waitUtilElementhasAttribute(BookingPageLocators.body);
		if(getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Confirmed")
				||getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Pending")
				||getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Hold")){
			ExcelReader xls = new ExcelReader(configProps.getProperty("PromoData"),"Data");
			xls.setCellData("Data", "Status", rownum+1, "PASS");
			xls.setCellData("Data", "PNR", rownum+1, pnr);
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked with PNR "+pnr);}
		else
			Reporter.failureReport("Ticket Confirmation", "Ticket has not booked");
		closetoast();
	}
	
	
	public void writingPNR(String sheetname,String value)
	{
		ExcelReader xls = new ExcelReader(configProps.getProperty("OutputPnrs"),sheetname);
		int rownum = xls.getRowCount(sheetname);
		if(xls.getCellData(sheetname, "PNR", rownum)==null){
			xls.setCellData(sheetname, "PNR", rownum, value);
			xls.setCellData(sheetname, "TestCaseNum", rownum,Integer.toString(rownum));
			
		}else{
			xls.setCellData(sheetname, "PNR", rownum+1, value);
			xls.setCellData(sheetname, "TestCaseNum", rownum+1,testName);
		}
	}
	
	public static String pickDate(String xlsDate){
		
		String[] date = xlsDate.split("\\^");
		System.out.println("date[1] = " +date[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, Integer.parseInt(date[1]));
		String newDate = sdf.format(c.getTime());
		return newDate;		
	}
	
	
	
	public static String nextDateof(String date) throws ParseException
	{
		String dt = date;  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE,1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		return dt;
	}
	
	
	public void selectClasswithPartCodeshare(String bookingClass) throws Throwable
	{
		waitforElement(BookingPageLocators.selectflightsection);
		//waitUtilElementhasAttribute(BookingPageLocators.body);
		int count=0;
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[@class='table flight_table']"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		int j = 1;
		for(int i=0;i<flighttables.size();i++)
			{
				boolean flag=false;
				//Below if condition will work only in round-trip journey. this is to change the return date if 
				//there is any change in the onward journey date due to no flights.
			
				
				//taking all flight rows into a list
				
				List<WebElement> Flights = flighttables.get(i).findElements(By.tagName("tbody"));
				System.out.println("Flights :"+Flights.size());
				//Below while loop executes to change the date of flight in case of no flights in current date selection	
				while(Flights.size()<1 && j<7){						
						current.get(i).findElement(By.xpath("following-sibling::li["+j+"]")).click();
						Flights = flighttables.get(i).findElements(By.tagName("tbody"));
						j++;
					}
				
				//Below for loop iterates through each row of the flights table to checks for part-code share flights and
				//select the class
				for(int k=1;k<=Flights.size();k++)
					{ 
					
						count=0;
						List <WebElement> Flight_Rows = driver.findElements(By.xpath("//table[@class='table flight_table']/tbody["+k+"]/tr"));
						System.out.println("Flight_Rows COUNT "+ Flight_Rows.size());
						List<WebElement> Flights_td = Flight_Rows.get(i).findElements(By.tagName("td"));
						System.out.println("COL COUNT "+ Flights_td.size());											
						String stop =  Flights_td.get(1).findElement(By.xpath("div/div/span[1]")).getText();
						if(stop.contains("1 Stop"))
						{
							WebElement Flightnumber_span =Flights_td.get(3).findElement(By.xpath("div/div/span"));
							String Flightnumber = Flightnumber_span.getText();
							System.out.println(Flightnumber);
							for(int l=0;l<Flightnumber.length();l++){
							 char result = Flightnumber.charAt(l);
							 	 if(Character.isDigit(result)) {
								 count++;
								 System.out.println("Count : "+count);
							 	 }
							}
						
						if((count==7))
						{
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",Flights.get(k-1));
							if(bookingClass.equalsIgnoreCase("Economy")){
//								if((Flights_td.get(4).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
//									System.out.println("Sold out");
//								}
								
								List <WebElement> pricelables = Flights_td.get(4).findElements(By.xpath("div"));
								String clsname =null;
								if(pricelables.size()==1){
									clsname = pricelables.get(0).getText();
								}
								else{
									pricelables = Flights_td.get(4).findElements(By.xpath("button/div"));
									clsname = pricelables.get(0).getText();
								}
								System.out.println("Class name : "+clsname);
								if(clsname.equalsIgnoreCase("Sold out"))
								{
									System.out.println("Sold Out");
									JavascriptExecutor jse = (JavascriptExecutor)driver;
									jse.executeScript("window.scrollBy(0,50)", "");
								}
								else{
									Flights_td.get(4).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;									
								}
							}
							if(bookingClass.equalsIgnoreCase("Business")){
								List <WebElement> pricelables = Flights_td.get(5).findElements(By.xpath("div"));
								String clsname =null;
								if(pricelables.size()==1){
									clsname = pricelables.get(0).getText();
								}
								else{
									pricelables = Flights_td.get(4).findElements(By.xpath("button/div"));
									clsname = pricelables.get(0).getText();
								}
								System.out.println("Class name : "+clsname);
								if(clsname.equalsIgnoreCase("Sold out"))
								{
									System.out.println("Sold Out");
									JavascriptExecutor jse = (JavascriptExecutor)driver;
									jse.executeScript("window.scrollBy(0,50)", "");
								}else{
									Flights_td.get(5).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
						}
					 }
												
					}
					
				if(flag!=true)
					{
						Reporter.failureReport("Selecting class in a part code share flight", "Partcodeshare flightis not listed in the page");
					}
			}
			
			clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);						
	}
	
	
	public void selectCodeshareConectflight(String bookingClass) throws Throwable
	{
		waitforElement(BookingPageLocators.selectflightsection);
		//waitUtilElementhasAttribute(BookingPageLocators.body);
		int count=0;
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[@class='table flight_table']"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		int j = 1;
		for(int i=0;i<flighttables.size();i++)
			{
				boolean flag=false;
				//Below if condition will work only in round-trip journey. this is to change the return date if 
				//there is any change in the onward journey date due to no flights.
			
				
				//taking all flight rows into a list
				
				List<WebElement> Flights = flighttables.get(i).findElements(By.tagName("tbody"));
				System.out.println("Flights :"+Flights.size());
				//Below while loop executes to change the date of flight in case of no flights in current date selection	
				while(Flights.size()<1 && j<7){						
						current.get(i).findElement(By.xpath("following-sibling::li["+j+"]")).click();
						Flights = flighttables.get(i).findElements(By.tagName("tbody"));
						j++;
					}
				
				//Below for loop iterates through each row of the flights table to checks for part-code share flights and
				//select the class
				for(int k=1;k<=Flights.size();k++)
					{ 
					
						count=0;
						List <WebElement> Flight_Rows = driver.findElements(By.xpath("//table[@class='table flight_table']/tbody["+k+"]/tr"));
						System.out.println("Flight_Rows COUNT "+ Flight_Rows.size());
						List<WebElement> Flights_td = Flight_Rows.get(i).findElements(By.tagName("td"));
						System.out.println("COL COUNT "+ Flights_td.size());											
						String stop =  Flights_td.get(1).findElement(By.xpath("div/div/span[1]")).getText();
						if(stop.contains("1 Stop"))
						{
							WebElement Flightnumber_span =Flights_td.get(3).findElement(By.xpath("div/div/span"));
							String Flightnumber = Flightnumber_span.getText();
							System.out.println(Flightnumber);
							for(int l=0;l<Flightnumber.length();l++){
							 char result = Flightnumber.charAt(l);
							 	 if(Character.isDigit(result)) {
								 count++;
								 System.out.println("Count : "+count);
							 	 }
							}
						
						if((count == 4 || count == 8 ))
						{
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",Flights.get(k-1));
							if(bookingClass.equalsIgnoreCase("Economy")){
								List <WebElement> pricelables = Flights_td.get(4).findElements(By.xpath("div"));
								String clsname =null;
								if(pricelables.size()==1){
									clsname = pricelables.get(0).getText();
								}
								else{
									pricelables = Flights_td.get(4).findElements(By.xpath("button/div"));
									clsname = pricelables.get(0).getText();
								}
								System.out.println("Class name : "+clsname);
								if(clsname.equalsIgnoreCase("Sold out"))
								{
									System.out.println("Sold Out");
									JavascriptExecutor jse = (JavascriptExecutor)driver;
									jse.executeScript("window.scrollBy(0,50)", "");
								}
								else{
									Flights_td.get(4).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;									
								}
							}
							if(bookingClass.equalsIgnoreCase("Business")){
								List <WebElement> pricelables = Flights_td.get(5).findElements(By.xpath("div"));
								String clsname =null;
								if(pricelables.size()==1){
									clsname = pricelables.get(0).getText();
								}
								else{
									pricelables = Flights_td.get(4).findElements(By.xpath("button/div"));
									clsname = pricelables.get(0).getText();
								}
								System.out.println("Class name : "+clsname);
								if(clsname.equalsIgnoreCase("Sold out"))
								{
									System.out.println("Sold Out");
									JavascriptExecutor jse = (JavascriptExecutor)driver;
									jse.executeScript("window.scrollBy(0,50)", "");
								}else{
									Flights_td.get(5).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
						}
					 }
												
					}
					
				if(flag!=true)
					{
						Reporter.failureReport("Selecting class in a part code share flight", "Partcodeshare flightis not listed in the page");
					}
			}
			
			clickContinueBtn();
			upSellPopUpAction("Continue");
			waitUtilElementhasAttribute(BookingPageLocators.body);						
	}
	
	
	
	public void Select_A_Meal() throws Throwable
	{
	//	waitforElement(BookingPageLocators.baggagetittle);
		if(isElementDisplayedTemp(BookingPageLocators.InFlightMeal)==true)
		{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.InFlightMeal));
		List<WebElement> flights = driver.findElements(BookingPageLocators.flightsinSelectmeal);
		List<WebElement> passengers = driver.findElements(BookingPageLocators.passengersToSelectMeal);
		List<WebElement> select = driver.findElements(BookingPageLocators.select);
		List<WebElement> error = driver.findElements(BookingPageLocators.mealError);
		List<WebElement> meal =driver.findElements(BookingPageLocators.meal);
		int i=1, j=0, k=0,l=0 ;
			if(error.size()==0){
				for(l=0;l<flights.size();l++){
					flights.get(l).click();
					passengers = driver.findElements(BookingPageLocators.passengersToSelectMeal);
					System.out.println(passengers.size());
					for(j=0;j<passengers.size()/flights.size();j++){					
							passengers.get(j).click();
							select.get(k).click();
							Reporter.SuccessReport("Selecting Meal", "Meal Selected");
							waitUtilElementhasAttribute(BookingPageLocators.body);
						}						
					j=0;
				}
			}else{
				
				if(error.size()>0){
					int a=0;
					for(int f=0;f<flights.size();f++){
					flights.get(f).click();
									
					if(error.get(a).isDisplayed()==true){
						if(error.size()==1){
							a=0;
						}else{
							a=a+1;
						}
						Reporter.SuccessReport("Selecting Meal", "No Meal Available to select"+f);
					
					}else{
						for(int p=0;p<passengers.size();p++){
							if(p>passengers.size()){
								break;
							}
							if(passengers.get(p).isDisplayed()==false){
									break;
							}		
						
						passengers.get(p).click();
						select.get(k).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Reporter.SuccessReport("Selecting Meal", "Selected");
						k=k+2;
						waitUtilElementhasAttribute(BookingPageLocators.body);
					}					
					}
					
				}
			}
			}	
		}else{
			System.out.println("NO Meal Available in this Route");
			Reporter.SuccessReport("Select meal", "NO Meal Available in this Route");
		}removeMeal();
	}
	
	public void removeMeal() throws Throwable{
		
		List<WebElement> flights = driver.findElements(BookingPageLocators.flightsinSelectmeal);
		List<WebElement> remove = driver.findElements(BookingPageLocators.Remove);
		flights.get(0).click();
		for(int i=0;i<flights.size();i++){
			for(int j=0;j<remove.size();j++){
				if(remove.get(j).isDisplayed()==true){
					remove.get(j).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
				}else{
					j=j-1;
					flights.get(i+1).click();
					i=i+1;
				}
			}
		}
		List<WebElement> mealSummary = driver.findElements(BookingPageLocators.mealInSummary);
		if(mealSummary.size()==0){
			Reporter.SuccessReport("Unselect Meal", "Successfully Unselected");
			
		}
	}
	
	public void Select_lounge() throws Throwable
	{
		if(isElementPresent(BookingPageLocators.Loung)==true)
		{((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.Loung));
			List<WebElement> allPassengers=driver.findElements(BookingPageLocators.allPassengers_Loung);
			for(int i=0;i<allPassengers.size();i++)
			{
				allPassengers.get(i).click();
				waitUtilElementhasAttribute(BookingPageLocators.body);
				Reporter.SuccessReport("Selecting Lounge", "Successfully Selected");
				
			}
		/*List<WebElement> Loung = driver.findElements(BookingPageLocators.Loung_table);
		
			for(int i=1 ;i<Loung.size()-1;i++)
		{
			if(Loung.get(i).findElement(By.tagName("input")).getAttribute("value")=="true")
			{
				Loung.get(i).findElement(By.tagName("input")).click();
				break;
			}
			else
			{
				Loung.get(i+1).findElement(By.tagName("input")).click();
				break;
			}
		}*/
		}
		
		else
		{
			Reporter.SuccessReport("Selecting Lounge", "No Lounge is available to select");
		}
		//clickContinueBtn();
	}
	
	public  void cancelFlight(String flightway) throws Throwable
	{
		waitforElement(BookingPageLocators.manageMyBookingTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.summaryCancelFlight));
		click(BookingPageLocators.summaryCancelFlight, "CancelFlight");
		List<WebElement> cancelflights = driver.findElements(BookingPageLocators.selectFlightstoCancel);
		Thread.sleep(1000);
		if(flightway.equalsIgnoreCase("Departing")){
			cancelflights.get(0).click();
		}else if (flightway.equalsIgnoreCase("Returning")){
			cancelflights.get(1).click();
		}else
		for(int i=0;i<cancelflights.size();i++)
		{
			cancelflights.get(i).click();
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		click(BookingPageLocators.cancelflightBtn, "CancelFlight");
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String priceBeforChange = null;
		String[] price=null;
		if(isElementPresent(BookingPageLocators.priceBeforeChange)==true){
			priceBeforChange = getText(BookingPageLocators.priceBeforeChange, "PriceBeforeChange");
			price = priceBeforChange.split("\\s");
			System.out.println(price[1]);
			waitforElement(BookingPageLocators.priceBeforeChange);
			}
		click(BookingPageLocators.conformCharges, "Conform Charges");
		if(isElementDisplayedTemp(BookingPageLocators.ok)){
		click(BookingPageLocators.ok, "ok");
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(price!=null){
		verifyCancellation(flightway,price[1]);}		
	}
	
	public  String agentcancelFlight(String flightway) throws Throwable
	{
		waitforElement(BookingPageLocators.manageMyBookingTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.summaryCancelFlight));
		click(BookingPageLocators.summaryCancelFlight, "CancelFlight");
		List<WebElement> cancelflights = driver.findElements(BookingPageLocators.selectFlightstoCancel);
		if(flightway.equalsIgnoreCase("Departing")){
			cancelflights.get(0).click();
		}else if (flightway.equalsIgnoreCase("Return")){
			cancelflights.get(1).click();
		}else
		for(int i=0;i<cancelflights.size();i++)
		{
			cancelflights.get(i).click();
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		click(BookingPageLocators.cancelflightBtn, "CancelFlight");
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String priceBeforChange = null;
		String[] price=null;
		if(isElementPresent(BookingPageLocators.priceBeforeChange)==true){
			priceBeforChange = getText(BookingPageLocators.priceBeforeChange, "PriceBeforeChange");
			price = priceBeforChange.split("\\s");
			waitforElement(BookingPageLocators.priceBeforeChange);
			}
		click(BookingPageLocators.conformCharges, "Conform Charges");
		Thread.sleep(2000);
		if(isElementDisplayedTemp(BookingPageLocators.ok)){			
		click(BookingPageLocators.ok, "ok");
		}
		//Thread.sleep(5000);
		verifyCancellation(flightway,price[1]);
		return price[1];
		
	}
	public void verifyCancellation(String Flightway,String priceBefore) throws Throwable
	{
		if(!Flightway.equalsIgnoreCase("Departing")&&!Flightway.equalsIgnoreCase("Returning")){
		waitforElement(BookingPageLocators.cancelled);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.cancelled)==true){
			Reporter.SuccessReport("Verifing cancellation status", "Successfully Verified Cancel Confirmed");
			}
		else{
			Reporter.failureReport("Verifing cancellation status", "Cancellation not confirmed");
			}
		}else{
			String priceAfterChange = getText(BookingPageLocators.Totalprice, "price After Change");
			String[] priceAfter = priceAfterChange.split("\\s");
			if(!priceBefore.equalsIgnoreCase(priceAfter[1])){
				Reporter.SuccessReport("Verifing cancellation status", "Successfully Verified "+Flightway+" flight cancellation");
				}else{
				Reporter.SuccessReport("Verifing cancellation status", Flightway+" flight cancellation not confirmed");
				}
		}
	}
	
	public void Baggage_Extra(String Triptype) throws Throwable
	{
		waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		boolean flag=true;
		if(Triptype.equalsIgnoreCase("One way"))
		{
			List<WebElement>  passengers = driver.findElements(BookingPageLocators.Adult_baggagae);
			List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
			int loop=0;
			for(int i=0;i<passengers.size();i++)
			{
				
					passengers.get(i).click();
					Thread.sleep(2000);
					List<WebElement>  Baggage_weight = driver.findElements(BookingPageLocators.Baggage_weight);
					for(int j=loop;j<=Baggage_weight.size();j++)
					{
						if(addBaggage.size()!=0)
						{
							addBaggage.get(j).click();
							waitforElement(BookingPageLocators.Adult_baggagae);
							waitUtilElementhasAttribute(BookingPageLocators.body);
							Reporter.SuccessReport("Add Extra Baggage", "Selected");
							loop=j+3;
							break;
						}
						else
						{
							Reporter.SuccessReport("Add Extra Baggage", "No Baggage Available");
						}
					}
				
				
			}
			
	

	}
		else
			
		{ 
			
			int x=0;
			List<WebElement>  flights = driver.findElements(BookingPageLocators.selected_Flights_baggage);
		
			for(int j=0;j<flights.size();j++)
			{
				if(flights.get(j).isDisplayed()==false)
				{
					List<WebElement>  pasenger = driver.findElements(BookingPageLocators.Adult_baggagae);
					pasenger.get(pasenger.size()-1).click();
					j=j-1;
					continue;
					
				}
				System.out.println(flights.get(j)+" "+ ":" +j);
				List<WebElement> Weight= flights.get(j).findElements(By.xpath("following::div[3]/div[1]/div/div/div[2]/a[2]/i"));
			//	List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
				if(Weight.size()==3)
				{   

					Weight.get(x).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
					Reporter.SuccessReport("Select Extra Baggage", "Selected successfully");
					break;
										
				}
				else
				{
					System.out.println("No Extra Baggage is Available to select");
				}

			}
			
			
			
		}
}
	
	public void validatingRemoveBaggage() throws Throwable
	{
		waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		boolean flag=true;
		List<WebElement> plusBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
		for(int i=0;i<2;i++){
			plusBaggage.get(i).click();
			waitUtilElementhasAttribute(BookingPageLocators.body);
		}
		List<WebElement> minusBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[1]/i"));
		for(int i=0;i<minusBaggage.size()-1;i++){
			minusBaggage.get(i).click();
			waitUtilElementhasAttribute(BookingPageLocators.body);
		}
		List<WebElement> baginsummary = driver.findElements(BookingPageLocators.baggageInSummary);
		if(baginsummary.size()==0){
			Reporter.SuccessReport("Removing Baggage", "Baggage Removed Successfully");
		}
		/*if(isElementDisplayedTemp(BookingPageLocators.baggageInSummary)==false){
			Reporter.SuccessReport("Validating Remove Baggage Functionality", "Baggage Removed Successfully");
		}else{
			Reporter.failureReport("Validating Remove Baggage Functionality", "Baggage Not Removed");
		}*/
	}
	
	public void addSportsEqpmnt(String Triptype) throws Throwable
	{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement>  passengers = driver.findElements(BookingPageLocators.Adult_baggagae);
		List<WebElement> sportsEqpmntLink = driver.findElements(BookingPageLocators.sportsEqpmntLink);
		
		if(Triptype.equalsIgnoreCase("One way")){
			for(int i=0;i<passengers.size();i++)
			{
				passengers.get(i).click();
				waitforElement(BookingPageLocators.sportsEqpmntLink);
				waitUtilElementhasAttribute(BookingPageLocators.body);
				sportsEqpmntLink.get(i).click();
				List<WebElement> sportsEqpmntdrop = driver.findElements(BookingPageLocators.sportsEqpmtDrop);
				sportsEqpmntdrop.get(i).click();
				click(BookingPageLocators.bicycle, "Bicycle");
				waitUtilElementhasAttribute(BookingPageLocators.body);
			}
		}
		else
		{
			passengers.get(0).click();
			List<WebElement>  flights = driver.findElements(BookingPageLocators.selected_Flights_baggage);
			for(int j=0;j<flights.size();j++){
				if(flights.get(j).isDisplayed()==false)
				{
					List<WebElement>  pasenger = driver.findElements(BookingPageLocators.Adult_baggagae);
					pasenger.get(pasenger.size()-1).click();
					j=j-1;
					continue;
					
				}
				System.out.println(flights.get(j)+" "+ ":" +j);
				WebElement sortsLink= flights.get(j).findElement(By.xpath("following::a[@class='link']"));
			//	List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
				if(sortsLink.isDisplayed())
				{   
					sortsLink.click();
					flights.get(j).findElement(By.xpath("following::div[@name='sporteq']")).click();
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.bicycle));
					click(BookingPageLocators.bicycle, "Bicycle");
					waitUtilElementhasAttribute(BookingPageLocators.body);
														
				}
				else
				{
					System.out.println("No Sports Equipment is Available to select");
				}
			}
			
	}
		
	}
	
	
	public void selectClassForStaff(String bookingClass) throws Throwable
	{
		boolean flag=false;
		waitforElement(BookingPageLocators.selectflightsection);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[contains(@class,'table flight_table')]"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<flighttables.size();j++)
		{	
			List<WebElement> Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
			List<WebElement> Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
			
			System.out.println("E"+Ecocols.size()+"B"+Buscols.size());
		
			int li=1;
			for(int count = 0;count<10;count++)
				{if(Ecocols.size()==0 || Buscols.size()==0 )
					{
						flighttables.get(j).findElement(By.xpath("preceding::a[1]")).click();
						current.get(j).findElement(By.xpath("following-sibling::li["+li+"]")).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
						Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
						li=li+1;					
					}
				else{
					break;
					}
				}			
		
			boolean classfound = true;
			if(bookingClass.equalsIgnoreCase("Staff Stand By"))
			{
				List<WebElement> ClassEco = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]"));
				for(int i=1;i<=ClassEco.size();i++){			
//					if(ClassEco.get(i-1).findElement(By.className("price")).getText().equalsIgnoreCase("Sold out")
//							||ClassEco.get(i-1).findElement(By.className("price")).getText().contains("لا تتوفر مقاعد")
//							||ClassEco.get(i-1).findElement(By.className("price")).getText().equalsIgnoreCase("Tükendi"))
					List <WebElement> pricelables = ClassEco.get(i-1).findElements(By.xpath("div"));
					String clsname =null;
					if(pricelables.size()==1){
						clsname = pricelables.get(0).getText();
					}
					else{
						pricelables = ClassEco.get(i-1).findElements(By.xpath("button/div"));
						clsname = pricelables.get(0).getText();
					}
					System.out.println("Class name : "+clsname);
					if(clsname.equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
						classfound=false;
					}
					else{
						ClassEco.get(i-1).click();
						classfound=true;
						break;
					}
					
				}
				if(classfound==false){
					Reporter.failureReport("Selecting class fare", "All the flights show the simple fare to be 'Sold out'");
				}
			}
		else if(bookingClass.equalsIgnoreCase("Staff Confirmed")){
				List<WebElement> ClassBusines = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]"));
				for(int i=1;i<ClassBusines.size();i++){
					List <WebElement> pricelables = ClassBusines.get(i-1).findElements(By.xpath("div"));
					String clsname =null;
					if(pricelables.size()==1){
						clsname = pricelables.get(0).getText();
					}
					else{
						pricelables = ClassBusines.get(i-1).findElements(By.xpath("button/div"));
						clsname = pricelables.get(0).getText();
					}
					System.out.println("Class name : "+clsname);
					if(clsname.equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
						classfound=false;					
					}else{
						ClassBusines.get(i-1).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
					
				}
				
			}		
		
		}
		
		clickContinueBtn();
		waitUtilElementhasAttribute(BookingPageLocators.body);
		
		
	}
	
	
	
	public String memberRegistration(String pwd,String natinality,String doctype,String docnum,String mobile,String emailadd) throws Throwable
	{
		waitforElement(BookingPageLocators.userEmail);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String  username = randomString(10);
		String  email = username+"@cigniti.com";
	
		type(BookingPageLocators.userEmail, email, "UserEmail");
		type(BookingPageLocators.pasword_reg, pwd, "Password");
		type(BookingPageLocators.cnfmpasword_reg, pwd, "Conform Password");
		click(BookingPageLocators.title_reg, "Title");
		click(By.xpath(BookingPageLocators.selecttitle_reg.replace("#", "1")), "Title");
		type(BookingPageLocators.fname_reg, randomString(8), "Firstname");
		type(BookingPageLocators.lname_reg, randomString(5), "Last Name");
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,200)", "");
		
		click(BookingPageLocators.dd_reg, "Date");
		click(By.xpath(BookingPageLocators.selectdd_reg+randomNumericString()+"]"), "Date");
		click(BookingPageLocators.mm_reg, "Month");
		click(By.xpath(BookingPageLocators.selectmm_reg+randomNumericString()+"]"), "Month");
		click(BookingPageLocators.yy_reg, "year");
		click(By.xpath(BookingPageLocators.selectyy_reg+randomNumber(12, 19)+"]"), "Year");
		click(BookingPageLocators.natinality_reg, "Nationality");
		click(BookingPageLocators.selectnatinality_reg(natinality), "Nationality");
		click(BookingPageLocators.doctype_reg, "Document Type");
		click(BookingPageLocators.selectdoctype_reg(doctype), "Document Type");
		if(doctype.equalsIgnoreCase("National ID Card"))
		{
			type(BookingPageLocators.docnumber_reg, docnum, "Document Number");
			click(BookingPageLocators.expdd, "Document Expiry Date");
			click(BookingPageLocators.selectDocExpdd, "Expiry Date");
			click(BookingPageLocators.expmm, "Expiry Month");
			click(By.xpath(BookingPageLocators.ppSelectDD+randomNumericString()+"]"), "Expiry Month");
			click(BookingPageLocators.expyy, "Expiry year");
			click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(10,15)+"]"), "Expiry Year");
						
		}
		else
		{
			System.out.println("Passport");
		}
		type(BookingPageLocators.mobileNum, mobile, "MobileNumber");
	//	type(BookingPageLocators.emailAdd, email, "Email Address");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.register));
		click(BookingPageLocators.register, "Register");
		return email;
	}
	
	
	public void verifingMemberRegistration() throws Throwable
	{
		waitforElement(BookingPageLocators.registerConformation);
		if(isElementPresent(BookingPageLocators.registerConformation)==true)
		{
			Reporter.SuccessReport("Verifing Member Registration", "Member is Successfully Registered");
		}
		else
		{
			Reporter.failureReport("Verifing Member Registration", "Member is not Successfully Registered");
		}
	}
	public int verifingFlightsAvailablity() throws InterruptedException
	{
		int count=0;
		List<WebElement> flightTable = driver.findElements(BookingPageLocators.flighttable);
		for(int j=0;j<flightTable.size();j++)
		{
			List<WebElement> flights=flightTable.get(j).findElements(By.xpath("tbody/tr/td[7]"));
			for(int k=0;k<flights.size();k++)
			{
				if(flights.get(k).findElement(By.tagName("button")).isDisplayed()==true)
				{
					count++;
					break;
				}
				else
				{
					System.out.println("All tickets are sold");
				}
			}
		}
		return count;
	}
	
	public void validateFare(String fare) throws Throwable
	{
		
		By Fare = By.xpath("//span[contains(text(),'"+fare+"')]");
		if(isElementPresent(Fare)==true)
		{
			Reporter.SuccessReport("Revalidate the ticket with same fare", "Ticket revalidated without any fare changes");
		}
	}
	//production
	public void verifingServiceCharge(String tripType,String Bookingcls,String totalpass) throws Throwable
	{		
			List<WebElement> collapseButton = driver.findElements(BookingPageLocators.collapsebtn(Bookingcls));
			List<WebElement> flights = driver.findElements(By.xpath("//div[@class='s_flightinfo1 clearfix']/span[1]"));
					
			for(int i=0;i<collapseButton.size();i++)
			{
				collapseButton.get(i).click();
				Thread.sleep(3000);
				List<WebElement> srcharge = driver.findElements(BookingPageLocators.serviceCharge);
				String serviceCharge = srcharge.get(i).getText();
				String[] scharges = serviceCharge.split("\\s");
				float ServiceCharge = Float.parseFloat(scharges[1]);
				int totalpsngrs = Integer.parseInt(totalpass);
				System.out.println(totalpsngrs);
				if(ServiceCharge==25.00*totalpsngrs)
				{
					System.out.println("Successfully Verified Service Charge");
					Reporter.SuccessReport("Verifing service Charge Per Person as "+serviceCharge, "Successfully Verified");
				}
				else if(flights.get(i).getText().contains("/"))
				{
					if(ServiceCharge==50.00*(Integer.parseInt(totalpass)))
					{
						System.out.println("Successfully Verified Service Charge");
						Reporter.SuccessReport("Verifing service Charge Per Person to be : "+serviceCharge, "Service charge applied ");
					}
				}
				else{
					Reporter.failureReport("Verifing service Charge Per Person to be : "+serviceCharge, "Service charge not applied");
				}
				collapseButton.get(i).click();
				Thread.sleep(2000);
			}
		
	}
	public void verifingChildDiscount(String bookingClass) throws Throwable
	{
		//String childFare=null;
		List<WebElement> collapseButton = driver.findElements(BookingPageLocators.collapsebtn(bookingClass));
		for(int i=0;i<collapseButton.size();i++)
		{		
			collapseButton.get(i).click();
			Thread.sleep(2000);
			List<WebElement> childamt = driver.findElements(BookingPageLocators.childamt);
			String amount = childamt.get(i).getText();
			String[] Amount = amount.split("\\s");
			String childamount = Amount[1];
			if(childamount.contains("\\,"))
			{
				childamount = childamount.replaceAll("\\,", "");
			}
			float childFre = Float.parseFloat(childamount);
			float avg = (childFre*25)/100;
			String Avg=String.valueOf(avg).split("\\.")[0];
			
			String  discount= getText(BookingPageLocators.discount, "Discount");
			String[] disAmount = discount.split("\\s");
			float Discount = Float.parseFloat(disAmount[1]);
			if(disAmount[1].contains(Avg))
			{
				Reporter.SuccessReport("Verifing Child Discount of 25%", "Discount Successfully Applied");
			}
			/*String[] childamount = Amount[1].split("\\.");
			int avg=0;
			if(childamount[0].contains("\\,"))
			{
				String childFare = childamount[0].replaceAll("\\,", "");
				int fare = Integer.parseInt(childFare);
				avg = (fare*25)/100;
			}				
			else
			{
				int fare = Integer.parseInt(childamount[0]);
				avg = (fare*25)/100;
			}
		
			if(getText(BookingPageLocators.discount, "Discount").contains(Integer.toString(avg)))
			{
				System.out.println("Successfully Verified Discount");
				Reporter.SuccessReport("Verifing Child Discount of 25%", "Discount Successfully Applied");
			}*/
		}
	}
	
	public static void verifyDiscount(String discountPercentage,String bookingClass) throws Throwable{

		boolean applied = false;
		List<WebElement> collapseButton = driver.findElements(BookingPageLocators.collapsebtn(bookingClass));
		for(int i=0;i<collapseButton.size();i++)
		{	
			Thread.sleep(5000);
			collapseButton.get(i).click();
			Thread.sleep(2000);
			String adultAvg=null;
			String childAvg=null;
			List<WebElement> adultamt = driver.findElements(BookingPageLocators.adultamt);
			String adltamount = adultamt.get(i).getText();
			String[] Amount1 = adltamount.split("\\s");
			String adultamount = Amount1[1];
			if(adultamount.contains("\\,"))
			{
				adultamount = adultamount.replaceAll("\\,", "");
				System.out.println(adultamount);
			}
			float adultFre = Float.parseFloat(adultamount);
			float adultavg = (adultFre*Integer.parseInt(discountPercentage))/100;
			System.out.println("adultAvg :"+adultavg);
			adultAvg=String.valueOf(adultavg);
			System.out.println("adultAvg :"+adultAvg);
						
			List<WebElement> childamt = driver.findElements(BookingPageLocators.childamt);
			if(childamt.size()!=0){
				String chldamount = childamt.get(i).getText();
				String[] Amount2 = chldamount.split("\\s");
				String childamount = Amount2[1];
				if(childamount.contains("\\,"))
				{
					childamount = childamount.replaceAll("\\,", "");
				}
				float childFre = Float.parseFloat(childamount);
				float childavg = (childFre*25)/100;
				childAvg=String.valueOf(childavg);
				}
			else childAvg = "0";
			
			System.out.println("childAvg :"+childAvg);
			String Avg = String.valueOf(Float.parseFloat(adultAvg)+Float.parseFloat(childAvg));
			System.out.println("Avg :"+Avg);
			
			String  discount= getText(BookingPageLocators.discount, "Discount");
			String[] disAmount = discount.split("\\s");
			float Discount = Float.parseFloat(disAmount[1]);
			System.out.println("Discount :"+Discount);
			
			if(String.valueOf(disAmount[1]).contains(String.valueOf(Avg))){
				applied=true;}
		}
//	if(applied==true)
//		Reporter.SuccessReport("Verifing Promo", "Discount Successfully Applied on all tiers");
//	else
//		Reporter.failureReport("Verifing Promo", "Discount not applied on all tiers");
	}
	
	public boolean inputBookingDetails_Arabic(String tripType, String origin, String dest, String deptDate,
			String origin2, String departure2, String retDate, String adults, String child, String infant, String promo,String Currency,String payment) throws Throwable
	{
		waitforElement(BookingPageLocators.oneWay_pdctn_AR);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		//scrolling to find triptype
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.roundTrip_pdctn_AR));
		//Selecting Trip Type
		String atrib;
		if(tripType.equalsIgnoreCase("Round Trip")){
			atrib =driver.findElement(BookingPageLocators.roundTrip_pdctn_AR).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.roundTrip_pdctn_AR, "Round Trip");
		} else if(tripType.equalsIgnoreCase("One Way")){
			atrib =driver.findElement(BookingPageLocators.oneWay_pdctn_AR).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.oneWay_pdctn_AR, "One Way");
		} else if(tripType.equalsIgnoreCase("Multi City")){
			atrib =driver.findElement(BookingPageLocators.multiCity_pdctn_AR).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.multiCity_pdctn_AR, "Multi City");
		}
		
		//scrolling to find Origin field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.origin));
		//Selecting origin	
		click(BookingPageLocators.origin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin", origin);
		click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", dest);
		/*click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", dest);*/
		
		//scrolling to find departure date field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.dpDate_pdctn_AR));
		//Selecting departure date	
		click(BookingPageLocators.dpDate_pdctn_AR,"Departure Date");
		selectDate(BookingPageLocators.selectDate,"Departure Date",deptDate);
		
		if(tripType.equalsIgnoreCase("Round Trip")){
			
			if(retDate.equalsIgnoreCase(deptDate))
			{
				Reporter.failureReport("Select Return Date", "Return date can not be the same as departure date ");
			}
			else
			{
				//click(BookingPageLocators.rtDate,"Return Date");
				selectDate(BookingPageLocators.selectDate,"Return Date",retDate);
			}
		
		}
		if(tripType.equalsIgnoreCase("Multi City")){
			
			//scrolling to find Second Origin  field
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.secOrigin));
			//Selecting Second Origin				
			click(BookingPageLocators.secOrigin, "Origin");
			selectCity(BookingPageLocators.selectOrigin, "Origin", origin2);
			selectCity(BookingPageLocators.selectDest, "Destination", departure2);
			//scrolling to find second return date field
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.rtnDate_pdctn_AR));			
			click(BookingPageLocators.rtnDate_pdctn_AR,"Return Date");
			selectDate(BookingPageLocators.selectDate,"Return Date",retDate);
		}
		
		//scrolling to find Passenger count fields
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.adult));			
				
		if(Integer.valueOf(adults)>1){
			
			
			click(BookingPageLocators.adult, "Adult");
			System.out.println("Adults: "+adults);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='"+adults+"']")));
			//driver.findElement(By.xpath("//div[text()='"+adults+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectAdult, "Adult", adults);
		}
		if(Integer.valueOf(child)>=1){
			click(BookingPageLocators.child_pdctn_AR, "Child");
			driver.findElement(By.xpath("//div[text()='"+child+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectChild, "Child", child);
		}
		if(Integer.valueOf(infant)>=1){
			click(BookingPageLocators.infant, "Infant");
			driver.findElement(By.xpath("//div[text()='"+infant+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectInfant, "Infant", infant);
		}
		
		if(payment.trim().equalsIgnoreCase("naSmile")){
			//scrolling to find smiles paints payment option
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.payWithnaSmile));			
			click(BookingPageLocators.payWithnaSmile, "payWithnaSmile");
		}
		
		if(!promo.equalsIgnoreCase("")){
			//scrolling to find Promo fields
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.promo));			
			type(BookingPageLocators.promo, promo, "Promo");
		}
		
		//scrolling to find Find flights button
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.findFlights_pdctn_AR));			
		click(BookingPageLocators.findFlights_pdctn_AR,"Find Flights");
		return true;
	}
	
	
	
	public String[] inputPassengerDetails_Arabic(String flightType, String totalPass, String nationality,
			String travelDoc, String docNum, String naSmiles, String mobileNum, String emailId,String fname,
				String lname,String payment2) throws Throwable
	{
		waitforElement(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(1))));
		waitUtilElementhasAttribute(BookingPageLocators.body);
		
		String lastname = null,firstname=null;
		String[] FirstLastName = null;
		Integer min=0,max=0;
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		try{
			for(Integer count = 1; count<=Integer.valueOf(totalPass); count++){
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("بالغ")){
					min = 14; max = 19;
				} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("طفل")){
					min = 5; max = 9;
				} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("رضيع")){
					min = 1; max = 2;
				}
				
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,100)", "");
				
				click(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(count))),"Title");
				//Thread.sleep(3000);
				click(By.xpath(BookingPageLocators.titleSelect.replace("#", String.valueOf(count))), "Title");
			
				if(payment2.equalsIgnoreCase("Nas")||payment2.equalsIgnoreCase("naSmile"))
				{
					type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), fname, "First Name");
					Thread.sleep(1000);
					type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lname, "Last Name");
				}
				else
				{
					//Thread.sleep(1000);
					firstname = randomString(8);
					lastname = randomString(5);
					type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), firstname, "First Name");
					Thread.sleep(1000);
					type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lastname, "Last Name");
				}
				
				
				jse.executeScript("window.scrollBy(0,200)", "");
				
				String[] DOB = new String[3];
				
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("بالغ")){
					//fetching adult passenger's DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase(" الهوية الوطنية "))
						DOB = getyakeenDOB("Adult","NidDOB");
					else if (travelDoc.equalsIgnoreCase("جواز سفر"))
						DOB = getyakeenDOB("Adult","PsprtDOB");
					else if (travelDoc.equalsIgnoreCase("Iqama"))
						DOB = getyakeenDOB("Adult","IqamaDOB");
					}
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("طفل")){
					//fetching Child passenger's DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase(" الهوية الوطنية "))
						DOB = getyakeenDOB("Child","NidDOB");
					else if (travelDoc.equalsIgnoreCase("جواز سفر"))
						DOB = getyakeenDOB("Child","PsprtDOB");
					else if (travelDoc.equalsIgnoreCase("Iqama"))
						DOB = getyakeenDOB("Child","IqamaDOB");
					}
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("رضيع")){
					//fetching infant's  DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase(" الهوية الوطنية "))
						DOB = getyakeenDOB("infant","NidDOB");
					else if (travelDoc.equalsIgnoreCase("جواز سفر"))
						DOB = getyakeenDOB("infant","PsprtDOB");
					else if (travelDoc.equalsIgnoreCase("Iqama"))
						DOB = getyakeenDOB("infant","IqamaDOB");
				}	
				
				System.out.println(DOB[0]+"-"+DOB[1]+"-"+DOB[2]);
				
				//Entering Date of birth
				click(By.xpath(BookingPageLocators.dd.replace("#", String.valueOf(count))), "DD");
				//Thread.sleep(3000);
				click(By.xpath(BookingPageLocators.selectDD.replace("#", String.valueOf(count))+DOB[0]+"]"), "DD");
				
				click(By.xpath(BookingPageLocators.mm.replace("#", String.valueOf(count))), "MM");
				//Thread.sleep(3000);
				click(By.xpath(BookingPageLocators.selectMM.replace("#", String.valueOf(count))+DOB[1]+"]"), "MM");
				
				click(By.xpath(BookingPageLocators.yyyy.replace("#", String.valueOf(count))), "YYYY");
				//Thread.sleep(3000);				
				executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@class='pass_tab']/div["+count+"]//descendant::div[@class='dob_conyear']/div/ul/li//descendant::div[@class='ui-select-choices-row']["+DOB[2]+"]")));
									
							
				//Selecting nationality
				click(By.xpath(BookingPageLocators.nation.replace("#", String.valueOf(count))), "Nationality");
				//Thread.sleep(3000);
				executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[text()='"+nationality+"']")));
				
				//Selecting travel document Type and entering document number				
				click(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))),"Travel Document");
				click(By.xpath("//div[text()='"+travelDoc+"']"), "Travel Document");
				WebElement psngrDtlsRow = driver.findElement(By.xpath(BookingPageLocators.inputDoc.replace("#", String.valueOf(count))));
				WebElement Documentnmbr = psngrDtlsRow.findElement(By.name("idnumber"));
				Documentnmbr.clear();
				
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("بالغ")){
					if (travelDoc.equalsIgnoreCase(" الهوية الوطنية "))
						Documentnmbr.sendKeys( getyakeenDOC("Adult","National ID card"));
					else if (travelDoc.equalsIgnoreCase("جواز سفر"))
						Documentnmbr.sendKeys( getyakeenDOC("Adult","Passport"));
					else if (travelDoc.equalsIgnoreCase("Iqama"))
						Documentnmbr.sendKeys( getyakeenDOC("Adult","Iqama"));
				}
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("طفل")){
					if (travelDoc.equalsIgnoreCase(" الهوية الوطنية "))
						Documentnmbr.sendKeys( getyakeenDOC("Child","National ID card"));
					else if (travelDoc.equalsIgnoreCase("جواز سفر"))
						Documentnmbr.sendKeys( getyakeenDOC("Child","Passport"));
					else if (travelDoc.equalsIgnoreCase("Iqama"))
						Documentnmbr.sendKeys( getyakeenDOC("Child","Iqama"));
				}
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("رضيع")){					
					if (travelDoc.equalsIgnoreCase(" الهوية الوطنية "))
						Documentnmbr.sendKeys( getyakeenDOC("infant","National ID card"));
					else if (travelDoc.equalsIgnoreCase("جواز سفر"))
						Documentnmbr.sendKeys( getyakeenDOC("infant","Passport"));
					else if (travelDoc.equalsIgnoreCase("Iqama"))
						Documentnmbr.sendKeys( getyakeenDOC("infant","Iqama"));			
				}
				
				//Entering Expire date  if travel document is a passport
				if (travelDoc.equalsIgnoreCase("جواز سفر")||(travelDoc.equalsIgnoreCase(" الهوية الوطنية ") && flightType.contains("International")))
				{	
			
					click(By.xpath(BookingPageLocators.ppExpDD.replace("#", String.valueOf(count))), "DD");
					//Thread.sleep(3000);
					click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "DD");	
					
					click(By.xpath(BookingPageLocators.ppExpMM.replace("#", String.valueOf(count))), "MM");
					//Thread.sleep(3000);
					click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "MM");
					
					click(By.xpath(BookingPageLocators.ppExpYY.replace("#", String.valueOf(count))), "YYYY");
					//	Thread.sleep(3000);
					executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//ul/li/descendant::div[@class='ui-select-choices-row']["+randomNumber(min,max)+"]")));
				}
			
		
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("رضيع"))
				{
					System.out.println("No Smily for  "+ getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title"));
					
				}
				else
				{
					//	if(!naSmiles.equalsIgnoreCase("") && !naSmiles.equalsIgnoreCase("Value"))
					//	type(By.xpath(BookingPageLocators.naSmiles.replace("#", String.valueOf(count))), naSmiles, "na Smiles");
				}
			
			}
			
			type(BookingPageLocators.mobileNum, mobileNum, "Mobile Number");
			type(BookingPageLocators.emailAdd, emailId, "Email Address");
			clickContinueBtn();
			if(isElementPresent(BookingPageLocators.ok)==true){
				click(BookingPageLocators.ok,"ok button on yakken alert");
				clickContinueBtn();
			}
			
			 FirstLastName = new String[2];
			 FirstLastName[0] =firstname;
			 FirstLastName[1] =lastname;
			 return FirstLastName;
		}catch(Exception e){
			e.printStackTrace();
			return FirstLastName;
		}
		
}
	
	public void payment_Production_Arabic(String paymentType) throws Throwable
	{
		System.out.println(paymentType);
		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
		for(int i=0;i<paymentss.size();i++)
		{
			if(paymentss.get(i).getText().contains("سداد"))
			{
				paymentss.get(i).click();					
				break;

			}
			}
		/*Thread.sleep(2000);
		driver.findElement(BookingPageLocators.ccCheckbox).click();*/
		Thread.sleep(2000);
		clickContinueBtn();
	}
	
	public boolean selectClass_Arabic(String bookingClass, String tripType) throws Throwable{
		Thread.sleep(3000);
		boolean flag=false;
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<current.size();j++)
		{
			String flightstatus=current.get(j).findElement(By.xpath("a/span[2]")).getText();
		//	String flightstatus = driver.findElement(By.xpath("//li[@class='current']/a/span[2]")).getText();
			if(flightstatus.equalsIgnoreCase("No flights"))
			{
				for(int i=1;i<=10;i++)
				{
					String status=current.get(j).findElement(By.xpath("following-sibling::li["+i+"]/a/span[2]")).getText();
				//	String status=driver.findElement(By.xpath("//li[@class='current']/following-sibling::li["+i+"]/a/span[2]")).getText();
					if(!status.equalsIgnoreCase("No flights"))
					{
						current.get(j).findElement(By.xpath("following-sibling::li["+i+"]")).click();
						Thread.sleep(3000);
						break;
						
						/*int count = verifingFlightsAvailablity();
						if(count==1)
						{
						Thread.sleep(3000);
						break;
						}*/
					}
					else
					{
						System.out.println("NO Flights");
						//continue;
					}
				}
			}
		}
		if(bookingClass.equalsIgnoreCase("اقتصاد")){
			click(BookingPageLocators.economyOW,"Simple");
		} else if(bookingClass.equalsIgnoreCase("ثني")){
			click(BookingPageLocators.flexOW,"Extra");
		} else if(bookingClass.equalsIgnoreCase("عمل")){
			click(BookingPageLocators.busOW,"Business");
			
		}
		if(!tripType.equalsIgnoreCase("One Way")){
			List<WebElement> ele = driver.findElements(BookingPageLocators.rtClass);
			if(bookingClass.equalsIgnoreCase("اقتصاد")){
				/*BookingPageLocators.rtClassStr = BookingPageLocators.rtClassStr + "[" + (ele.size()-1) + "]/td[5]";
				click(By.xpath(rtClassStr),"Economy");*/
				List<WebElement> rtClasBusines = driver.findElements(By.xpath("//div[@class='main_con']/div/div/div[2]/div/flight-select/div/form/div[2]//descendant::table/tbody/tr/td[5]"));
				for(int i=0;i<rtClasBusines.size();i++)
				{
					if(rtClasBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
					}
					else
					{
						rtClasBusines.get(i).click();
						break;
					}
					
				}
			} else if(bookingClass.equalsIgnoreCase("ثني")){
				/*BookingPageLocators.rtClassStr = BookingPageLocators.rtClassStr + "[" + (ele.size()-1) + "]/td[6]";
				click(By.xpath(rtClassStr),"Flex");*/
				List<WebElement> rtClasBusines = driver.findElements(By.xpath("//div[@class='main_con']/div/div/div[2]/div/flight-select/div/form/div[2]//descendant::table/tbody/tr/td[6]"));
				for(int i=0;i<rtClasBusines.size();i++)
				{
					if(rtClasBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
					}
					else
					{
						rtClasBusines.get(i).click();
						break;
					}
					
				}
			} else if(bookingClass.equalsIgnoreCase("عمل")){
				/*BookingPageLocators.rtClassStr = BookingPageLocators.rtClassStr + "[" + (ele.size()-1) + "]/td[7]";
				click(By.xpath(rtClassStr),"Business");*/
				List<WebElement> rtClasBusines = driver.findElements(By.xpath("//div[@class='main_con']/div/div/div[2]/div/flight-select/div/form/div[2]//descendant::table/tbody/tr/td[7]"));
				for(int i=0;i<rtClasBusines.size();i++)
				{
					if(rtClasBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))
					{
						System.out.println("Sold Out");
					}
					else
					{
						rtClasBusines.get(i).click();
						break;
					}
					
				}
				
			}
			
		}		
		clickContinueBtn();
		return true;
	}
	
	public void verifyPNRforSadad_Arabic() throws Throwable{
		waitforElement(BookingPageLocators.body);
		Thread.sleep(15000);
		if(isElementPresent(BookingPageLocators.summaryRefNumber_AR_uat, "Booking Reference")){
		String Status= getText(BookingPageLocators.summaryStatus_AR, "PNR Status");
		if (Status.equalsIgnoreCase("قيد الانتظار")){
			String pnr = driver.findElement(BookingPageLocators.summaryRefNumber_AR_uat).getText();
			String env = driver.getCurrentUrl();
				if(env.contains("develop_r41")){writingPNR("IBE_NAV_PNR",pnr);}
				else if(env.contains("uat")){writingPNR("IBE_UAT_PNR",pnr);}
				else{writingPNR("IBE_PROD_PNR",pnr);}			
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked,PNR : " + pnr);
			}
			else
			{
			Reporter.SuccessReport("Ticket Confiramation", "Ticket not booked");
			}
		}else{
			Reporter.failureReport("Ticket Confirmation", "Payment failed");			
		}	
		closetoast();

	}
	
	
	public void verifingChildDiscount_Arabic(String bookingClass) throws Throwable
	{
		
		List<WebElement> collapseButton = driver.findElements(BookingPageLocators.collapsebtn(bookingClass));
		for(int i=0;i<collapseButton.size();i++)
		{		
			collapseButton.get(i).click();
			Thread.sleep(3000);
			List<WebElement> childamt = driver.findElements(BookingPageLocators.childamt_AR);
			String amount = childamt.get(i).getText();
			String[] Amount = amount.split("\\s");
			String[] childamount = Amount[1].split("\\.");
			int avg=0;
			if(childamount[0].contains(","))
			{
				String childFare = childamount[0].replaceAll("\\,", "");
				int fare = Integer.parseInt(childFare);
				avg = (fare*25)/100;
			}				
			else
			{
				int fare = Integer.parseInt(childamount[0]);
				avg = (fare*25)/100;
			}
		
			if(getText(BookingPageLocators.discount_AR, "Discount").contains(Integer.toString(avg)))
			{
				System.out.println("Successfully Verified Discount");
				Reporter.SuccessReport("Verifing Child Discount of 25%", "Discount Successfully Applied");
			}
		}
	}
	public void verifingChildDiscount_Tarkish(String bookingClass) throws Throwable
	{
		
		List<WebElement> collapseButton = driver.findElements(BookingPageLocators.collapsebtn(bookingClass));
		for(int i=0;i<collapseButton.size();i++)
		{		
			collapseButton.get(i).click();
			Thread.sleep(3000);
			List<WebElement> childamt = driver.findElements(BookingPageLocators.childamt_TR);
			String amount = childamt.get(i).getText();
			String[] Amount = amount.split("\\s");
			String[] childamount = Amount[1].split("\\.");
			int avg=0;
			if(childamount[0].contains(","))
			{
				String childFare = childamount[0].replaceAll("\\,", "");
				int fare = Integer.parseInt(childFare);
				avg = (fare*25)/100;
			}				
			else
			{
				int fare = Integer.parseInt(childamount[0]);
				avg = (fare*25)/100;
			}
		
			if(getText(BookingPageLocators.discount_TR, "Discount").contains(Integer.toString(avg)))
			{
				System.out.println("Successfully Verified Discount");
				Reporter.SuccessReport("Verifing Child Discount of 25%", "Discount Successfully Applied");
			}
		}
	}
	
	//Production Tarkish language
	
	public boolean inputBookingDetails_Tarkish(String tripType, String origin, String dest, String deptDate,
			String origin2, String departure2, String retDate, String adults, String child, String infant, String promo,String Currency) throws Throwable
	{
		waitforElement(BookingPageLocators.oneWay_pdctn_TR);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		//scrolling to find Trip type button
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.roundTrip_pdctn_TR));			
		//Selecting Trip type		
		String atrib;
		if(tripType.equalsIgnoreCase("Round Trip")){
			atrib = driver.findElement(BookingPageLocators.roundTrip_pdctn_TR).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.roundTrip_pdctn_TR, "Round Trip");
		} else if(tripType.equalsIgnoreCase("One Way")){
			atrib = driver.findElement(BookingPageLocators.oneWay_pdctn_TR).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.oneWay_pdctn_TR, "One Way");
		} else if(tripType.equalsIgnoreCase("Multi City")){
			atrib = driver.findElement(BookingPageLocators.multiCity_pdctn_TR).getAttribute("class");
			if(!atrib.contains("active"))
			click(BookingPageLocators.multiCity_pdctn_TR, "Multi City");
		}
		
		//scrolling to find Origin field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.origin));			
		// Selecting origin		
		click(BookingPageLocators.origin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin", origin);
		click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", dest);
		/*click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", dest);*/
		
		//scrolling to find Departure date field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.dpDate_pdctn_TR));			
			
		click(BookingPageLocators.dpDate_pdctn_TR,"Departure Date");
		selectDate(BookingPageLocators.selectDate,"Departure Date",deptDate);
		
		if(tripType.equalsIgnoreCase("Round Trip")){
		//	click(BookingPageLocators.rtDate,"Return Date");
			selectDate(BookingPageLocators.selectDate,"Return Date",retDate);
		}
		if(tripType.equalsIgnoreCase("Multi City")){
			//scrolling to find Second origin field
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.secOrigin));			
			//Selecting second Origin
			click(BookingPageLocators.secOrigin, "Origin");
			selectCity(BookingPageLocators.selectOrigin, "Origin", origin2);
			selectCity(BookingPageLocators.selectDest, "Destination", departure2);
			//scrolling to find Second Departure date field
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.rtnDate_pdctn_TR));			
			//Selecting second Departure date
			click(BookingPageLocators.rtnDate_pdctn_TR,"Return Date");
			selectDate(BookingPageLocators.selectDate,"Return Date",retDate);
		}
		
		//scrolling to find passenger count fields
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.adult));			
		//Entering passenger count
		if(Integer.valueOf(adults)>1){
			click(BookingPageLocators.adult, "Adult");
			System.out.println("Adults: "+adults);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='"+adults+"']")));
			//driver.findElement(By.xpath("//div[text()='"+adults+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectAdult, "Adult", adults);
		}
		if(Integer.valueOf(child)>=1){
			click(BookingPageLocators.child_pdctn_TR, "Child");
			driver.findElement(By.xpath("//div[text()='"+child+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectChild, "Child", child);
		}
		if(Integer.valueOf(infant)>=1){
			click(BookingPageLocators.infant, "Infant");
			driver.findElement(By.xpath("//div[text()='"+child+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectInfant, "Infant", infant);
		}
		
		//scrolling to find promo fields

		if(!promo.equalsIgnoreCase("")){
			type(BookingPageLocators.promo, promo, "Promo");
		}
		
		//scrolling to find Find Flights button
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.findFlights_pdctn_TR));			
		click(BookingPageLocators.findFlights_pdctn_TR,"Find Flights");
		return true;
	}
	
	
	public String[] inputPassengerDetails_Tarkish(String flightType, String totalPass, String nationality, 
			String travelDoc, String docNum, String naSmiles, String mobileNum, String emailId,String fname,String lname,String payment2) 
					throws Throwable{
		waitforElement(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(1))));
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String lastname = null,firstname=null;
		String[] FirstLastName = null;
		Integer min=0,max=0;
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		try{
			for(Integer count = 1; count<=Integer.valueOf(totalPass); count++){
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Yetişkin")){
					min = 14; max = 19;
				} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Çocuk")){
					min = 5; max = 9;
				} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Bebek")){
					min = 1; max = 2;
				}
				click(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(count))),"Title");
				//Thread.sleep(3000);
				click(By.xpath(BookingPageLocators.titleSelect.replace("#", String.valueOf(count))), "Title");
			
				if(payment2.equalsIgnoreCase("Nas"))
				{
					type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), fname, "First Name");

				}
				else
				{
				//Thread.sleep(1000);
				firstname = randomString(8);
				type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), firstname, "First Name");
				}
				if(payment2.equalsIgnoreCase("Nas"))
				{
					type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lname, "Last Name");

				}
				else
				{
				lastname = randomString(5);
				type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lastname, "Last Name");
				}
				
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,200)", "");
				
				String[] DOB = new String[3];
				
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Yetişkin"))
				{
					//fetching adult passenger's DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase("Kimlik Kartı"))
						DOB = getyakeenDOB("Adult","NidDOB");
					else if (travelDoc.equalsIgnoreCase("Pasaport"))
						DOB = getyakeenDOB("Adult","PsprtDOB");
					else if (travelDoc.contains("Oturum Belgesi"))
						DOB = getyakeenDOB("Adult","");
				}
				
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Çocuk"))
				{
					//fetching Child passenger's DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase("Kimlik Kartı"))
						DOB = getyakeenDOB("Child","NidDOB");
					else if (travelDoc.equalsIgnoreCase("Pasaport"))
						DOB = getyakeenDOB("Child","PsprtDOB");
					else if (travelDoc.contains("Oturum Belgesi"))
						DOB = getyakeenDOB("Child","");
				}
				
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Bebek"))
				{
					//fetching infant's  DOB from Yakeen data sheet based on the document type
					if (travelDoc.equalsIgnoreCase("Kimlik Kartı"))
						DOB = getyakeenDOB("infant","NidDOB");
					else if (travelDoc.equalsIgnoreCase("Pasaport"))
						DOB = getyakeenDOB("infant","PsprtDOB");
					else if (travelDoc.contains("Oturum Belgesi"))
						DOB = getyakeenDOB("infant","");
				}	
				
				System.out.println(DOB[0]+"-"+DOB[1]+"-"+DOB[2]);
				
				click(By.xpath(BookingPageLocators.dd.replace("#", String.valueOf(count))), "DD");
				//Thread.sleep(3000);
				click(By.xpath(BookingPageLocators.selectDD.replace("#", String.valueOf(count))+DOB[0]+"]"), "DD");
				
				click(By.xpath(BookingPageLocators.mm.replace("#", String.valueOf(count))), "MM");
				//Thread.sleep(3000);
				click(By.xpath(BookingPageLocators.selectMM.replace("#", String.valueOf(count))+DOB[1]+"]"), "MM");
				
				click(By.xpath(BookingPageLocators.yyyy.replace("#", String.valueOf(count))), "YYYY");
				//Thread.sleep(3000);
				executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@class='pass_tab']/div["+count+"]//descendant::div[@class='dob_conyear']/div/ul/li//descendant::div[@class='ui-select-choices-row']["+DOB[2]+"]")));
					
				
				//Selecting nationality
				click(By.xpath(BookingPageLocators.nation.replace("#", String.valueOf(count))), "Nationality");
				//Thread.sleep(3000);
				executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[text()='"+nationality+"']")));
				
				//Selecting travel document Type and entering document number				
				click(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))),"Travel Document");
				click(By.xpath("//div[text()='"+travelDoc+"']"), "Travel Document");
				WebElement psngrDtlsRow = driver.findElement(By.xpath(BookingPageLocators.inputDoc.replace("#", String.valueOf(count))));
				WebElement Documentnmbr = psngrDtlsRow.findElement(By.name("idnumber"));
				Documentnmbr.clear();
				
				if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Yetişkin")){
					if (travelDoc.equalsIgnoreCase("Kimlik Kartı"))
						Documentnmbr.sendKeys( getyakeenDOC("Adult","National ID card"));
					else if (travelDoc.equalsIgnoreCase("Pasaport"))
						Documentnmbr.sendKeys( getyakeenDOC("Adult","Passport"));
					else if (travelDoc.equalsIgnoreCase("Oturum Belgesi"))
						Documentnmbr.sendKeys( getyakeenDOC("Adult",""));
				}
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Çocuk")){
					if (travelDoc.equalsIgnoreCase("Kimlik Kartı"))
						Documentnmbr.sendKeys( getyakeenDOC("Child","National ID card"));
					else if (travelDoc.equalsIgnoreCase("Pasaport"))
						Documentnmbr.sendKeys( getyakeenDOC("Child","Passport"));
					else if (travelDoc.equalsIgnoreCase("Oturum Belgesi"))
						Documentnmbr.sendKeys( getyakeenDOC("Child","Oturum Belgesi"));
				}
				else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Bebek")){					
					if (travelDoc.equalsIgnoreCase("Kimlik Kartı"))
						Documentnmbr.sendKeys( getyakeenDOC("infant","National ID card"));
					else if (travelDoc.equalsIgnoreCase("Pasaport"))
						Documentnmbr.sendKeys( getyakeenDOC("infant","Passport"));
					else if (travelDoc.equalsIgnoreCase("Oturum Belgesi"))
						Documentnmbr.sendKeys( getyakeenDOC("infant",""));			
				}
				
				//Entering Expire date  if travel document is a passport
				if (travelDoc.equalsIgnoreCase("Pasaport")||(travelDoc.equalsIgnoreCase("Kimlik Kartı") && flightType.contains("International")))
				{	
			
					click(By.xpath(BookingPageLocators.ppExpDD.replace("#", String.valueOf(count))), "DD");
					//Thread.sleep(3000);
					click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "DD");	
					
					click(By.xpath(BookingPageLocators.ppExpMM.replace("#", String.valueOf(count))), "MM");
					//Thread.sleep(3000);
					click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "MM");
					
					click(By.xpath(BookingPageLocators.ppExpYY.replace("#", String.valueOf(count))), "YYYY");
					//	Thread.sleep(3000);
					executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//ul/li/descendant::div[@class='ui-select-choices-row']["+randomNumber(min,max)+"]")));
				}
			}
			
			
			type(BookingPageLocators.mobileNum, mobileNum, "Mobile Number");
			type(BookingPageLocators.emailAdd, emailId, "Email Address");
			clickContinueBtn();
			 FirstLastName = new String[2];
			 FirstLastName[0] =firstname;
			 FirstLastName[1] =lastname;
			 return FirstLastName;
		}catch(Exception e){
			e.printStackTrace();
			return FirstLastName;
		}
	}
	
	public void verifyPNRforSadad_Tarkish() throws Throwable{
		waitforElement(BookingPageLocators.body);
		Thread.sleep(15000);
		if(isElementPresent(BookingPageLocators.summaryRefNumber_TR, "Booking Reference")){
			String Status= getText(BookingPageLocators.summaryStatus_TR, "PNR Status");
			if (Status.equalsIgnoreCase("Beklemede")){
			String pnr = driver.findElement(summaryRefNumber_TR).getText();
			String env = driver.getCurrentUrl();
				if(env.contains("develop_r41")){writingPNR("IBE_NAV_PNR",pnr);}
				else if(env.contains("uat")){writingPNR("IBE_UAT_PNR",pnr);}
				else{writingPNR("IBE_PROD_PNR",pnr);}				
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked,PNR : " + pnr);
			}
			else {
				Reporter.failureReport("Ticket Confirmation", "Ticket not booked");
				writingPNR("ProductionRoutes_pnr","Fail");
			}
		}
		else
		{
			Reporter.SuccessReport("Ticket Confiramation", "Payment failed");
		}	
		closetoast();
	}
	
	
	public void verifingMemberRegistration_Production() throws Throwable
	{
		if(isElementPresent(BookingPageLocators.memberRegistrationConf)==true)
		{
			Reporter.SuccessReport("Verifing Member Registratin", "Member is Successfully Registered");
		}
		else
		{
			Reporter.failureReport("Verifing Member Registratin", "Member is not Successfully Registered");
		}
	}
	public void verifingProfileUpdatemessage() throws Throwable
	{
		waitforElement(BookingPageLocators.memberUpdateConf);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementPresent(BookingPageLocators.memberUpdateConf)==true)
		{
			click(BookingPageLocators.ok, "ok");
			Reporter.SuccessReport("Verifing Member Update", "Member is Successfully Updated");
		}
		else
		{
			Reporter.failureReport("Verifing Member Update", "Member is not Successfully Updated");
		}
	}
	public void validating_BaggageWeights() throws Throwable
	{
		waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		int count =0;
		List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
		for(int  i=0;i<addBaggage.size()-1;i++)
		{
			addBaggage.get(i).click();
			waitUtilElementhasAttribute(BookingPageLocators.body);
			count++;
		}
		System.out.println(addBaggage.get(count));
		if(addBaggage.get(count).getAttribute("class").contains("disable"))
		{			
			Reporter.SuccessReport("Validating Excess Baggage Adding", "No Excess Baggage Added");
		}
		else
		{
			Reporter.failureReport("Validating Excess Baggaeg Adding", "Excess Baggage Added");
		}
		
	}
	public void selectPassenger() throws Throwable
	{
		waitforElement(BookingPageLocators.passengerDetailsTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> selPasngr = driver.findElements(BookingPageLocators.selectPassenger);
		selPasngr.get(1).click();
		click(BookingPageLocators.memberDropdown, "Family Member Dropdown");
		List<WebElement> familyMembrs = driver.findElements(BookingPageLocators.familyMembers);
		familyMembrs.get(1).click();
		click(BookingPageLocators.seletcBtn, "Select");
	}
	
	public void selectallSeatstoremove(String seatSelect,String totalpass,String triptype) throws Throwable {
		waitforElement(BookingPageLocators.selectseattittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> Flights = driver.findElements(By.xpath("//ul[@class='nav nav-tabs']/li"));
		List<WebElement> ele = driver.findElements((By.xpath("//div[contains(@class, 'seatpassrow')]"))); 
		if(ele.size()==0)
		{
			clickContinueBtn();
		}
		else
		{
			int k;
			List<WebElement> extlegseats = driver.findElements(BookingPageLocators.selExLegSeat);
			List<WebElement> busiseats = driver.findElements(BookingPageLocators.selbusSeat);
			int loop=0;
			for(int j=0;j<Flights.size();j++){
				for(int i=0;i<Integer.parseInt(totalpass);i++){
					if(seatSelect.equalsIgnoreCase("Extra Leg Room")){
						if(Flights.get(j).getAttribute("class").contains("active")){
							for(k=loop;k<extlegseats.size();k++)
							{
								if(extlegseats.get(k).isDisplayed()==true){
									extlegseats.get(k).click();
									Reporter.SuccessReport("Click", "Successfully Click on Extra Leg Room ");
									if(isElementDisplayedTemp(BookingPageLocators.ok)){
										click(BookingPageLocators.ok, "OK");
										waitUtilElementhasAttribute(BookingPageLocators.body);
										
									}	
									loop=loop+1;
									break;
								}else{
									System.out.println("Seat was Already Selected");
									loop=loop+1;
									
								}
																	
								}
							/*if(i==Integer.parseInt(totalpass)-1){
									break;
							}*/	
							
								
							//continue;	
						}
						
					}
					if(seatSelect.equalsIgnoreCase("Business")){
						if(Flights.get(j).getAttribute("class").contains("active")){
							for(k=loop;k<busiseats.size();k++)
							{
								if(busiseats.get(k).isDisplayed()==true){
									busiseats.get(k).click();
									Reporter.SuccessReport("Click", "Successfully Click on Business");
									if(isElementDisplayedTemp(BookingPageLocators.ok)){
										click(BookingPageLocators.ok, "OK");
										
									}	
									loop=loop+1;
									break;
								}else{
									System.out.println("Seat was Already Selected");
									loop=loop+1;
									
								}
																	
								}
							/*if(i==Integer.parseInt(totalpass)-1){
									break;
							}*/	
							
								
							//continue;	
						}
						
				}
				
			}
			
		}
		
			waitUtilElementhasAttribute(BookingPageLocators.body);
			removeallseats(seatSelect, totalpass, triptype);			
			clickContinueBtn();
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK']"))==true){
			click(By.xpath("//button[text()='OK']"), "OK Button");
			}
			else{
			System.out.println("No alert Present");
			}
		}
	}
	public void selectClassWithConnectionFlight(String bookingClass, String bundle) throws Throwable{
		waitforElement(BookingPageLocators.selectflightsection);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		boolean flag=false;
		int count=0;
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[@class='table flight_table']"));
			for(int i=0;i<flighttables.size();i++)
			{
				
					List<WebElement> Flights_row = flighttables.get(i).findElements(By.tagName("tr"));
					System.out.println("ROWS COUNT"+ Flights_row.size());
					for(int j=1;j<Flights_row.size();j++)
					{ 
						count=0;
						List<WebElement> Flights_td = Flights_row.get(j).findElements(By.tagName("td"));
						System.out.println("COL COUNT"+ Flights_td.size());
						
						String stop =  Flights_row.get(j).findElement(By.xpath("td[2]/div/div/span")).getText();
						if(stop.contains("1 Stop")){
							if(bookingClass.equalsIgnoreCase("Simple")){
								if((Flights_td.get(4).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
									System.out.println("Sold out");
								}else{
									Flights_td.get(4).findElement(By.tagName("button")).click();
									if(bundle.equalsIgnoreCase("Light")){
										driver.findElement(By.xpath("//table[@class='bundle-table']["+(i+1)+"]/tbody[1]/tr/td[2]/div[2]/div[2]")).click();
									}
									else if(bundle.equalsIgnoreCase("Plus")){
										driver.findElement(By.xpath("//table[@class='bundle-table']["+(i+1)+"]/tbody[1]/tr/td[3]/div[2]/div[2]")).click();
									}
									else if(bundle.equalsIgnoreCase("Premium")){								
										driver.findElement(By.xpath("//table[@class='bundle-table']["+(i+1)+"]/tbody[1]/tr/td[4]/div[2]/div[2]")).click();
									}
									break;									
								}
							}
							if(bookingClass.equalsIgnoreCase("Business")){
								if((Flights_td.get(5).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
									System.out.println("Sold out");
								}else{
									Flights_td.get(5).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
						}
					
					}		
			}
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			
}
	
	//*******************************Home Page Actions************************
	
	//Click on Manage booking link
	public void navigateToMMB() throws Throwable{
		// add validation
		String env = driver.getCurrentUrl();
		if(env.contains("uat")){
		driver.get(configProps.getProperty("URL_UAT_MMB"));
		waitforElement(BookingPageLocators.sfpbookingReference);
		}else{
		driver.get(configProps.getProperty("URL_PROD_MMB"));
		waitforElement(BookingPageLocators.sfpbookingReference);
		}
	}
	
	public void navigateToBookingPage() throws Throwable{
		// add validation
		String env = driver.getCurrentUrl();
		if(env.contains("uat")){
		driver.get(configProps.getProperty("URL_UAT"));
		waitforElement(BookingPageLocators.sfpbookingReference);
		}else{
		driver.get(configProps.getProperty("URL_PRODUCTION"));
		waitforElement(BookingPageLocators.sfpbookingReference);
		}
	}
	
	public void navigateToLoginPage() throws Throwable{
		// add validation
		String env = driver.getCurrentUrl();
		if(env.contains("uat")){
		driver.get(configProps.getProperty("URL_UAT_Login"));
		waitforElement(BookingPageLocators.sfpbookingReference);
		}else{
		driver.get(configProps.getProperty("URL_PROD_Login"));
		waitforElement(BookingPageLocators.sfpbookingReference);
		}
	}
	
	//Click on Agency Login Link link
	public void navigateToAgencyLogin() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.agency_lnk));
		click(BookingPageLocators.agency_lnk, "Agency Login");	
		}
		
		//Click on Agency Register link
	public void navigateToAgencyRegister() throws Throwable{
		// add validation
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.agency_Register));
		click(BookingPageLocators.agency_Register, "Agency Login");		
	}
	
	public void navigateToMemberRegistration() throws Throwable{
		List<WebElement> Registrationlinks = driver.findElements(BookingPageLocators.here);
		Registrationlinks.get(1).click();
	}
	
	
	//Click on Continue button
	public void clickContinueBtn() throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);	
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.continueBtn));
		Thread.sleep(1000);
		click(BookingPageLocators.continueBtn, "Continue");
	}
	
	//********************Payment page Actions*****************************
	
	//Click on PaymentType
	public void clickPaymentType(String Paymentmode) throws Throwable
	{
		try
		{
			waitUtilElementhasAttribute(BookingPageLocators.body);
			List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
			if(Paymentmode.equalsIgnoreCase("Credit Card"))
			{
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Credit Card")
						||paymentss.get(i).getText().contains("Kredi Kartı")
						||paymentss.get(i).getText().contains("البطاقات الإئتمانية"))
					{
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
			}
			else if(Paymentmode.equalsIgnoreCase("Nas Credit"))
			{
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Nas Credit")
						||paymentss.get(i).getText().contains("رصيد ناس"))
					{
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
			}
			else if(Paymentmode.equalsIgnoreCase("Voucher"))
			{
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Voucher")
						||paymentss.get(i).getText().contains("قسيمة شراء"))
					{
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
			}
			else if(Paymentmode.equalsIgnoreCase("SADAD"))
				{
					for(int i=0;i<paymentss.size();i++)
					{
						if(paymentss.get(i).getText().contains("SADAD Account")
								||paymentss.get(i).getText().contains("SADAD Online Payment")
								||paymentss.get(i).getText().contains("حساب سداد"))
						{
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
						}
					}
				}
			Reporter.SuccessReport("Successfully Clicked on payment type", "Payment Type Clicked");
		}
		catch(Exception e)
		{
			Reporter.failureReport("Failed to click on payment type", "Payment Type not clicked");
		}
		
	
	}
	
	
	//*******************************MMB Page Actions************************
	
	public void modifyExtras() throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.modifyExtras));
		Thread.sleep(2000);
		click(BookingPageLocators.modifyExtras, "Modify Extras");
	}
	
	public static void clickModifySeats() throws Throwable{	
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.modifySeat));
		Thread.sleep(2000);
		click(BookingPageLocators.modifySeat, "Seat Selection");
		waitUtilElementhasAttribute(BookingPageLocators.body);
	}
	
	public static void clickPayNowBtn() throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if( isElementPresent(BookingPageLocators.paynow)==true){
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.paynow));
		Thread.sleep(2000);
		click(BookingPageLocators.paynow, "Pay Now");
		waitUtilElementhasAttribute(BookingPageLocators.body);
		}
	}
	
	//*******************************Utilities*******************************
	public String removeSpecialchars(String value){
		
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match= pt.matcher(value);
        while(match.find())
        {
            String s= match.group();
            value=value.replaceAll("\\"+s, "");
        }
        System.out.println("string converted to : "+value);
        return value;
	}
	
	
		public String[] pickCredentials(String Sheetname){
			ExcelReader xls = new ExcelReader(configProps.getProperty("Credentialsdata"),Sheetname);
			int count = xls.getRowCount(Sheetname);
			System.out.println(count);
			int index = ((int)(Math.random() * count-1)) + 1;
			System.out.println(index);
			String[] credentials =new String[5];
			credentials[0] = xls.getCellValue("credentials"+index, "userid");
			credentials[1] = xls.getCellValue("credentials"+index, "password");
			credentials[2] = xls.getCellValue("credentials"+index, "firstname");
			credentials[3] = xls.getCellValue("credentials"+index, "lastname");
			return credentials;
		}
		
		public String[] pickNaSmileCredentials(String Sheetname){
			ExcelReader xls = new ExcelReader(configProps.getProperty("Credentialsdata"),Sheetname);
			int count = xls.getRowCount(Sheetname);
			int index = ((int)(Math.random() * count-1)) + 1;;
			String[] credentials =new String[5];
			credentials[0] = xls.getCellValue("credentials"+index, "userid");
			credentials[1] = xls.getCellValue("credentials"+index, "password");
			credentials[2] = xls.getCellValue("credentials"+index, "firstname");
			credentials[3] = xls.getCellValue("credentials"+index, "lastname");
			credentials[4] = xls.getCellValue("credentials"+index, "nasmiles");
			return credentials;
		}
		
		public void continueOnPassengerDetails() throws Throwable{
			waitforElement(BookingPageLocators.passengerDetailsTittle);
			clickContinueBtn();
			Thread.sleep(5000);
			if(isElementDisplayedTemp(BookingPageLocators.okBtn)==true)
			{
				click(BookingPageLocators.okBtn, "OK");
				clickContinueBtn();
			}
		}
		
		/*public void coninueOnBaggage() throws Throwable{
			waitforElement(BookingPageLocators.baggagetittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			if(isElementDisplayedTemp(BookingPageLocators.baggagetittle)){
				clickContinueBtn();
				chooseInsurance("Add");
			}else{
				System.out.println("No Baggage page Available");
			}
		}
		
		public void chooseInsurance(String choice) throws Throwable{
			Thread.sleep(5000);
			if(isElementDisplayedTemp(BookingPageLocators.Add)==true)
			{ 	if(choice.equalsIgnoreCase("Add"))
					click(BookingPageLocators.Add, "Travel insurance selected");
				else
					click(BookingPageLocators.No, "Travel insurance not selected");
				clickContinueBtn();
			}
		}*/
		
		public void coninueOnBaggage() throws Throwable{

            waitforElement(BookingPageLocators.baggagetittle);

            waitUtilElementhasAttribute(BookingPageLocators.body);

            if(isElementDisplayedTemp(BookingPageLocators.baggagetittle)){

                  clickContinueBtn();

                  chooseInsurance("Add");
                  
                  Thread.sleep(5000);
                  
                  //compareInsuranceDetailsInSummary();
                  
                  //clickContinueBtn();

            }else{

                  System.out.println("No Baggage page Available");

            }

     }

    

     public void chooseInsurance(String choice) throws Throwable{

            Thread.sleep(5000);

            if(isElementDisplayedTemp(BookingPageLocators.Add)==true)

            {      if(choice.equalsIgnoreCase("Add")){
            	 	click(BookingPageLocators.Add, "Travel insurance selected");
            	 	Thread.sleep(7000);
            	 	waitForVisibilityOfElement(BookingPageLocators.insuranceFeeText,"Insurance Fees Amount in Page");
            	 	compareInsuranceDetailsInSummary();
            	 	
            	
            }                       

                  else{
                	  click(BookingPageLocators.No, "Travel insurance not selected");
                  }

                     

                  clickContinueBtn();

            }

     }
     
     public void compareInsuranceDetailsInSummary() throws Throwable{
    	 waitForVisibilityOfElement(BookingPageLocators.insuranceFeeText,"Insurance Fees Amount in Page");
    	 String insuranceFeesText=getText(BookingPageLocators.insuranceFeeText,"Insurance Fees Amount in Page");
    	 System.out.println("Insurance fees Text in page->"+insuranceFeesText);
    	 int iFeeBeginIndex=insuranceFeesText.indexOf("S");
    	 System.out.println(iFeeBeginIndex);
    	 int iFeeEndIndex=insuranceFeesText.indexOf("(");  
    	 System.out.println(iFeeEndIndex);
    	 System.out.println("End Index Character"+insuranceFeesText.charAt(iFeeEndIndex));
    	 String insuranceFeesInPage=insuranceFeesText.substring(iFeeBeginIndex,iFeeEndIndex-1);
    	 System.out.println("Insurance Fess In Page:"+insuranceFeesInPage);
    	 
    	 if(isElementDisplayedTemp(BookingPageLocators.otherfeeinSummaryExpand)==true){
    		 click(BookingPageLocators.otherfeeinSummaryExpand, "Other fee section Expand");
    		 
    	 }
    	 if(isElementDisplayedTemp(BookingPageLocators.insuranceFee)==true){
    		 String insuranceFees=getText(BookingPageLocators.insuranceFeeAmount,"Insurance Fees Amount");
    		 int iFeeSummaryEndIndex=insuranceFees.indexOf(".");
    		 String insuranceFeesInSummary=insuranceFees.substring(0, iFeeSummaryEndIndex);
    		 System.out.println("Insurance Fees In Summary->"+insuranceFeesInSummary);
    		 Reporter.SuccessReport("Insurance Fees in Summary", "Insurance fees displayed");
    		 System.out.println("Insurance Fees Amount in Summary is:"+insuranceFees);
    		 Reporter.SuccessReport("Insurance Fees Amount in Summary is:"+insuranceFees, "Insurance fees amount displayed");
    		 //Assert.assertEquals(insuranceFeesInPage, insuranceFeesInSummary);
    		 
    	 }
    		 
    		 
    	 }
            	   	  
         
    	 public void verifyInsuranceDetailsInSummary() throws Throwable{
    	    	
        	 
        	 if(isElementDisplayedTemp(BookingPageLocators.otherfeeinSummaryExpand)==true){
        		 click(BookingPageLocators.otherfeeinSummaryExpand, "Other fee section Expand");
        		 
        	 }
        	 if(isElementDisplayedTemp(BookingPageLocators.insuranceFee)==true){
        		 String insuranceFees=getText(BookingPageLocators.insuranceFeeAmount,"Insurance Fees Amount");
        		 Reporter.SuccessReport("Insurance Fees in Summary", "Insurance fees displayed");
        		 System.out.println("Insurance Fees Amount in Summary is:"+insuranceFees);
        		 Reporter.SuccessReport("Insurance Fees Amount in Summary is:"+insuranceFees, "Insurance fees amount displayed");
        		 //Assert.assertEquals(insuranceFeesInPage, insuranceFees);
        		 
        		 
        	 }

         

  }

    	 public  void clickBackToHomeButton() throws Throwable{
    		 if(isElementDisplayedTemp(BookingPageLocators.backToHomeBtn)==true){
    		 click(BookingPageLocators.backToHomeBtn, "Back To Home Button");
    		 }
    		 else{
    		 System.out.println("Back To Home button not visible");
    		 }
    		 }
		
		public void closeOverlay() throws Throwable{
			List<WebElement> frames = driver.findElements(By.tagName("iframe"));
			for(WebElement frame:frames){
				System.out.println(frame.getAttribute("id"));
				if(frame.getAttribute("id").equalsIgnoreCase("yief137528")){
					driver.switchTo().frame("yief137528");
					driver.manage().timeouts().implicitlyWait(5000,TimeUnit.MILLISECONDS);
					if(isElementPresent(BookingPageLocators.closepopup)==true)
					{
						
						click(BookingPageLocators.closepopup,"Submit Button");
						
						flag = true;
						break;
					}
					}
				}										
			if(flag== false)
			{
			System.out.println("No POP up"); 
			}
		
		}
		
		public void continueOnSeatSelection() throws Throwable{
			waitforElement(BookingPageLocators.selectseattittle);
			//closeOverlay();			
			if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)==true){
				clickContinueBtn();
				if(isElementDisplayedTemp(BookingPageLocators.ok)){
				click(BookingPageLocators.ok, "OK");
				}
			}else{
				System.out.println("No Seat Page");
			}
		}
		
		public void navigateToHome() throws Throwable{
			// add validation
			if(isElementDisplayedTemp(BookingPageLocators.Home)){
				click(BookingPageLocators.Home, "Home");
				Reporter.SuccessReport("Click Home tab", "Home Tab Clicked");
			}
			else{
				System.out.println("No Seat Page");		
				Reporter.failureReport("Click Home tab", "Hometab could not be clicked");
			}
		}
		
		public void clickDownloadBoardingpasses() throws Throwable{
			waitUtilElementhasAttribute(BookingPageLocators.body);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.downloadAllBoardingPasses));
			click(BookingPageLocators.downloadAllBoardingPasses, "Download All Boarding Passes");	
		}
		
		public void isFileDownloaded(String fileName) throws Throwable {
			File dir = new File(System.getenv("USERPROFILE")+"\\Downloads");
			File[] dirContents = dir.listFiles();
			flag = false;		
			for (int i = 0; i < dirContents.length; i++) {
			    if (dirContents[i].getName().equals(fileName)) {
			          // File has been found, it can now be deleted:
			          dirContents[i].delete();
			          flag = true;
			          Reporter.SuccessReport("Verifying file download", "File successfully Downloaded");
			          break;
			        }
			    }
			   if(flag== false){
				  Reporter.failureReport("Verifying file download", "File not found in download path");
			  }			     
		}
			
		
		public void updateStatus(String Sheetname, String TCID, String status ) throws IOException{
			
			int row = GoogleDriveAPI.fnGetRowNo(Sheetname, TCID);
			GoogleDriveAPI.getResponse(Sheetname, "B"+row, "B"+row);
			GoogleDriveAPI.setValue(Sheetname, "B"+row, "B"+row, status);
			String Exetime=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
			if(status.equalsIgnoreCase("Pass"))
			GoogleDriveAPI.setValue(Sheetname, "C"+row, "C"+row, Exetime+" IST");
			else GoogleDriveAPI.setValue(Sheetname, "D"+row, "D"+row, Exetime+" IST");
			
//			ExcelReader xls = new ExcelReader(configProps.getProperty("ResultSheet"),Sheetname);
//			int rowNum = xls.findRow(Sheetname, TCID);
//			System.out.println("Row number : "+rowNum);
//			String Pcount = xls.getCellValue(TCID, "Total Pass Count");
//			System.out.println("pass count: "+Pcount);
//			String Fcount = xls.getCellValue(TCID, "Total Fail Count");
//			System.out.println("Fail count : "+Fcount);
//			xls.setCellData(Sheetname, "Status", rowNum, status);
//			int count;
//			if (status.equalsIgnoreCase("Pass")){
//				count= Integer.parseInt(Pcount)+1;
//				xls.setCellData(Sheetname, "Total Pass Count", rowNum,Integer.toString(count));
//			}
//			else{
//				count= Integer.parseInt(Fcount)+1;
//				xls.setCellData(Sheetname, "Total Fail Count", rowNum,Integer.toString(count));
//			}
//			System.out.println(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE));
//			String Exetime=ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE);		
//			xls.setCellData(Sheetname, "Last Execution time", rowNum, Exetime);
		
			
		}
		
	
		
}
		
		
	



