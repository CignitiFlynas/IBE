package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class ClassSelectionPage<RenderedWebElement> extends BookingPageLocators {
	
	public void WaittillPageLoad() throws Throwable{
		waitforElement(BookingPageLocators.body);
		}
	
	public void selectHoldfare() throws Throwable{
		WaittillPageLoad();
		click(BookingPageLocators.hldFareBtn,"Hold fare button");
	}
	
	public void verifyPLCK(String flightType ) throws Throwable{
		WaittillPageLoad();
		String feexpctd;
		if(flightType.equalsIgnoreCase("Domestic"))
		feexpctd = configProps.getProperty("Plckdom");
		else feexpctd = configProps.getProperty("PlckIntl");
		String feeaplied = Float.toString(projectUtilities.getfee("Other Fees"));
			if( feeaplied.equalsIgnoreCase(feexpctd))
				Reporter.SuccessReport("Verifying Friends travel fee", "Friends travel fee "+feeaplied+" as expected");
			else
				Reporter.failureReport("Verifying Friends travel fee", "Friends travel fee verification failed, expected - "+feexpctd+"Found - "+feeaplied);
		}
	
	public boolean selectClass(String bookingClass, String bundle) throws Throwable{
		boolean flag=false;
		waitforElement(BookingPageLocators.selectflightsection);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[contains(@class,'table flight_table')]"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<flighttables.size();j++)
		{	
			List<WebElement> Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]/button"));
			List<WebElement> Flexcols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
			List<WebElement> Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
			
		
			int li=1;
			for(int count = 0;count<10;count++)
				{if(Ecocols.size()==0	|| Buscols.size()==0 ||Flexcols.size()==0)
					{
						flighttables.get(j).findElement(By.xpath("preceding::a[1]")).click();
						current.get(j).findElement(By.xpath("following-sibling::li["+li+"]")).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]/button"));
						Flexcols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
						Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
						li=li+1;					
					}
				else{
					break;
					}
				}			
		
		
			if(bookingClass.equalsIgnoreCase("Simple"))
			{
				List<WebElement> ClassEco = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]/button"));
				for(int i=0;i<ClassEco.size();i++){			
					if(ClassEco.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||ClassEco.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||ClassEco.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
					}
					else{
						ClassEco.get(i).click();
						if(bundle.equalsIgnoreCase("Light")){
							driver.findElement(By.xpath("//table[@class='bundle-table']["+i+"]/tbody[1]/tr/td[2]/div[2]/div[2]")).click();
						}
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
					
				}
			}
			else if(bookingClass.equalsIgnoreCase("Extra")){
				List<WebElement> ClassFlex = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
				for(int i=0;i<ClassFlex.size();i++){
					if(ClassFlex.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||ClassFlex.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||ClassFlex.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
					}else{
						ClassFlex.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
			}
			else if(bookingClass.equalsIgnoreCase("Business")){
				List<WebElement> ClassBusines = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
				for(int i=0;i<ClassBusines.size();i++){
					if(ClassBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||ClassBusines.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||ClassBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
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
		
		}
		
//		clickContinueBtn();
//		waitUtilElementhasAttribute(BookingPageLocators.body);
		flag=true;
		return flag;
	}

	public boolean selectClassOneleg(String bookingClass, String tripType, String Flightleg) throws Throwable{
		boolean flag=false;
		waitforElement(BookingPageLocators.selectflightsection);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[contains(@class,'table flight_table')]"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<flighttables.size();j++)
		{	if(Flightleg.equalsIgnoreCase("Returning")==true){
			j=1;
			}
			List<WebElement> Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]/button"));
			List<WebElement> Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
			List<WebElement> Flexcols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
		
			int li=1;
			for(int count = 0;count<10;count++)
				{if(Ecocols.size()==0	|| Buscols.size()==0 ||Flexcols.size()==0)
					{
						flighttables.get(j).findElement(By.xpath("preceding::a[1]")).click();
						current.get(j).findElement(By.xpath("following-sibling::li["+li+"]")).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Buscols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
						Ecocols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]/button"));
						Flexcols = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
						li=li+1;					
					}
				else{
					break;
					}
				}			
		
		
			if(bookingClass.equalsIgnoreCase("Simple"))
			{
				List<WebElement> ClassEco = flighttables.get(j).findElements(By.xpath("tbody/tr/td[5]/button"));
				for(int i=0;i<ClassEco.size();i++){			
					if(ClassEco.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||ClassEco.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||ClassEco.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
					}
					else{
						ClassEco.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
					
				}
			}
			else if(bookingClass.equalsIgnoreCase("Extra")){
				List<WebElement> ClassFlex = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
				for(int i=0;i<ClassFlex.size();i++){
					if(ClassFlex.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||ClassFlex.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||ClassFlex.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
						System.out.println("Sold Out");
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,50)", "");
					}else{
						ClassFlex.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
			}
			else if(bookingClass.equalsIgnoreCase("Business")){
				List<WebElement> ClassBusines = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
				for(int i=0;i<ClassBusines.size();i++){
					if(ClassBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||ClassBusines.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||ClassBusines.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
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
		
		projectUtilities.clickContinueBtn();
		waitUtilElementhasAttribute(BookingPageLocators.body);
		flag=true;
		return flag;
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
		projectUtilities.clickContinueBtn();
		return true;
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
	
	public void selectClasswithPartCodeshare(String bookingClass,String bookingtype) throws Throwable
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
				
				List<WebElement> tbody = flighttables.get(i).findElements(By.tagName("tbody"));
				List<WebElement> Flights_row = tbody.get(0).findElements(By.tagName("tr"));
				System.out.println("Flight rows :"+Flights_row.size());
				//Below while loop executes to change the date of flight in case of no flights in current date selection	
				while(Flights_row.size()<1 && j<7){						
						current.get(i).findElement(By.xpath("following-sibling::li["+j+"]")).click();
						Flights_row = flighttables.get(i).findElements(By.tagName("tr"));
						j++;
					}
				
				//Below for loop iterates through each row of the flights table to checks for part-code share flights and
				//select the class
				for(int k=0;k<Flights_row.size();k++)
					{ 
					
						count=0;
						List<WebElement> Flights_td = Flights_row.get(k).findElements(By.tagName("td"));
						System.out.println("COL COUNT"+ Flights_td.size());											
						String stop =  Flights_row.get(k).findElement(By.xpath("td[2]/div/div/span")).getText();
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
						}
						if((count==7) && bookingtype.equalsIgnoreCase("PartCodeShare"))
						{
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",Flights_row.get(k));
							if(bookingClass.equalsIgnoreCase("Simple")){
								if((Flights_td.get(4).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
									System.out.println("Sold out");
								}else{
									Flights_td.get(4).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
							if(bookingClass.equalsIgnoreCase("Extra")){
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
					
				if(flag!=true)
					{
						Reporter.failureReport("Selecting class in a part code share flight", "Partcodeshare flight");
					}
			}
			
			projectUtilities.clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);						
	}
	
	
	public void selectCodeshareConectflight(String bookingClass,String bookingtype) throws Throwable
	{
		waitforElement(BookingPageLocators.selectflightsection);
		//waitUtilElementhasAttribute(BookingPageLocators.body);
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
						if(stop.contains("1 Stop"))
						{
							WebElement Flightnumber_span =Flights_td.get(3).findElement(By.xpath("div/div/span"));
							String Flightnumber = Flightnumber_span.getText();
							System.out.println(Flightnumber);
							for(int k=0;k<Flightnumber.length();k++){
							 char result = Flightnumber.charAt(k);
							 	 if(Character.isDigit(result)) {
								 count++;
								 System.out.println("Count : "+count);
							 	 }
							}
						}
						if((count==8 ||count==4) && bookingtype.equalsIgnoreCase("CodeShare"))
						{
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",Flights_row.get(j));
							if(bookingClass.equalsIgnoreCase("Simple")){
								if((Flights_td.get(4).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
									System.out.println("Sold out");
								}else{
									Flights_td.get(4).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
							if(bookingClass.equalsIgnoreCase("Extra")){
								if((Flights_td.get(5).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
									System.out.println("Sold out");
								}else{
									Flights_td.get(5).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
							if(bookingClass.equalsIgnoreCase("Business")){
								if((Flights_td.get(6).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out"))){
									System.out.println("Sold out");
								}else{
									Flights_td.get(6).findElement(By.tagName("button")).click();
									Thread.sleep(2000);
									flag=true;
									break;
									
								}
							}
						}		
					}
					
					if(flag!=true)
					{
						Reporter.failureReport("Selecting class in a part code share flight", "Could not select flight");
					}
			}
			
			projectUtilities.clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);						
	}
	
	
	public void selectClassForStaff(String bookingClass) throws Throwable
	{
		waitforElement(BookingPageLocators.selectflightsection);
		waitUtilElementhasAttribute(BookingPageLocators.body); 
		List<WebElement> flighttables = driver.findElements(By.xpath("//table[@class='table flight_table']"));
		List<WebElement> current = driver.findElements(By.xpath("//li[@class='current']"));
		for(int j=0;j<current.size();j++)
		{
			List<WebElement> flightstatus = driver.findElements(By.xpath("//li[@class='current']/a/span[2]"));
			List<WebElement> standByClass = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
			List<WebElement> stafcnfdClass = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
		
			int li=1;
			for(int count = 0;count<10;count++)
				{if(standByClass.size()==0	|| stafcnfdClass.size()==0)
					{
						flighttables.get(j).findElement(By.xpath("preceding::a[1]")).click();
						current.get(j).findElement(By.xpath("following-sibling::li["+li+"]")).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						standByClass = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
						stafcnfdClass = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
						li=li+1;
					
					}
				else{
					break;
					}
				}				
		
		
			if(bookingClass.equalsIgnoreCase("Staff Stand By"))
			{
				standByClass = flighttables.get(j).findElements(By.xpath("tbody/tr/td[6]/button"));
				for(int i=0;i<standByClass.size();i++){			
					if(standByClass.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||standByClass.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||standByClass.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
						System.out.println("Sold Out");
					}
					else{
						
						standByClass.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
					
				}
			}
			else if(bookingClass.equalsIgnoreCase("Staff Confirmed")){
				stafcnfdClass = flighttables.get(j).findElements(By.xpath("tbody/tr/td[7]/button"));
				for(int i=0;i<stafcnfdClass.size();i++){
					if(stafcnfdClass.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Sold out")
							||stafcnfdClass.get(i).findElement(By.tagName("div")).getText().contains("لا تتوفر مقاعد")
							||stafcnfdClass.get(i).findElement(By.tagName("div")).getText().equalsIgnoreCase("Tükendi")){
						System.out.println("Sold Out");
					}else{
						stafcnfdClass.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
					
				}
			}
			
			
			
		
		}
		
		projectUtilities.clickContinueBtn();
		waitUtilElementhasAttribute(BookingPageLocators.body);
			
	}
	
	
}
