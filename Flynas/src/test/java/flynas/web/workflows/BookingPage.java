package flynas.web.workflows;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class BookingPage<RenderedWebElement> extends BookingPageLocators {
	
	public void waitforpageload() throws InterruptedException{
		
		waitUtilElementhasAttribute(BookingPageLocators.body);	
	}
	
	public void clickLogin() throws Throwable{
		waitforpageload();
		click(BookingPageLocators.login_lnk, "Login");
	}
	
	public int getFriendsCount() throws Throwable{
		WebElement elm = driver.findElement(BookingPageLocators.friendscout);
		String count = elm.getText();
		if(count!=null){
		Reporter.SuccessReport("Capturing friends limit", "Friends limit : "+count );
		return Integer.parseInt(count);}
		else
			{Reporter.failureReport("Capturing friends limit", "Friends limit : null");
		return (Integer) null;}
	}
	
	public void changeCurrencyto(String Currency) throws Throwable{
		waitforpageload();
		if(Currency!="")
		{
			click(BookingPageLocators.Currency_link, "Currency");
			Thread.sleep(2000);
			click(BookingPageLocators.currencytyp(Currency), Currency);
		}
		
	}
	
	public void selectTripType(String TripType) throws Throwable{
		waitforpageload();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.roundTrip));
		if(TripType.equalsIgnoreCase("Round Trip")){
			click(BookingPageLocators.roundTrip, "Round Trip");
		} else if(TripType.equalsIgnoreCase("One Way")){
			click(BookingPageLocators.oneWay, "One Way");
		} else if(TripType.equalsIgnoreCase("Multi City")){
			click(BookingPageLocators.multiCity, "Multi City");
		}
	}
	
	public void enterOwTripdetails(String origin, String destination, String deptDate) throws Throwable{
		//scrolling to find Origin field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.origin));			
		click(BookingPageLocators.origin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin", origin);
		click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", destination);
		click(BookingPageLocators.dpDate,"Departure Date");
		selectDate(BookingPageLocators.selectDate,"Departure Date",deptDate);
		
	}
	
	public void enterTwTripdetails(String origin, String destination, String deptDate, String rtrnDate ) throws Throwable{
		//scrolling to find Origin field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.origin));			
		click(BookingPageLocators.origin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin", origin);
		click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", destination);
		click(BookingPageLocators.dpDate,"Departure Date");
		selectDate(BookingPageLocators.selectDate,"Departure Date",deptDate);
		if(rtrnDate.equalsIgnoreCase(deptDate))
		{
			Reporter.failureReport("Select Return Date", "Return date can not be the same as departure date ");
		}
		else
		{
			//click(BookingPageLocators.rtDate,"Return Date");
			selectDate(BookingPageLocators.selectDate,"Return Date",rtrnDate);
		}
		
	}
	
	public void enterMwTripdetails(String origin1, String destination1, String origin2, String destination2,String deptDate1, String deptDate2 ) throws Throwable{
		//scrolling to find Origin field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.origin));			
		click(BookingPageLocators.origin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin", origin1);
		//click(BookingPageLocators.dest, "Destination");
		selectCity(BookingPageLocators.selectDest, "Destination", destination1);
		click(BookingPageLocators.dpDate,"Departure Date");
		selectDate(BookingPageLocators.selectDate,"Departure Date",deptDate1);
		click(BookingPageLocators.secOrigin, "Origin");
		selectCity(BookingPageLocators.selectOrigin, "Origin2", origin2);
		selectCity(BookingPageLocators.selectDest, "Destination2", destination2);
		
		if(deptDate2.equalsIgnoreCase(deptDate1))
		{
			Reporter.failureReport("Select Second Date", "Second date can not be the same as First date ");
		}
		else
		{
			click(BookingPageLocators.rtDate,"Return Date");
			selectDate(BookingPageLocators.selectDate,"Second Date",deptDate2);
		}
		
	}
	
	public void enterPassengerscount(String adults,String Children, String infants) throws Throwable{
		
		int adultcount = Integer.valueOf(adults);
		int childCount = Integer.valueOf(Children);
		int infatCount = Integer.valueOf(infants);
		
		 if(adultcount+childCount <= 9) {
			 if(adultcount>1){
				 click(BookingPageLocators.adult, "Adult");
				 JavascriptExecutor executor = (JavascriptExecutor)driver;
				 executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='"+adults+"']")));
				 
			 	}
			 if(childCount>=1){
				 click(BookingPageLocators.child, "Child");
				 JavascriptExecutor executor = (JavascriptExecutor)driver;
				 executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='"+Children+"']")));
				
			 	}
			 if(infatCount>=1){
				 if(infatCount<adultcount){
					 click(BookingPageLocators.infant, "infant");
					 JavascriptExecutor executor = (JavascriptExecutor)driver;
					 executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='"+infants+"']")));
				 }
		
			 else {
				 Reporter.failureReport("Entering infant Count", "Infant count cant be greater than adult count");
				 }
			 }
		 }
		 else{
			 Reporter.failureReport("Entering Passenger count", "Total passenger count should not be greater than 9");
			 }
	}
	
	
	public void selectPaywithSmilePoints() throws Throwable{
		//scrolling to find smiles paints payment option
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.payWithnaSmile));			
		click(BookingPageLocators.payWithnaSmile, "payWithnaSmile");
	}
	
	public void enterPromocode(String promo) throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.payWithnaSmile));			
		type(BookingPageLocators.promo, promo, "Promo");
	}
	
	public void selectBookforfriends() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.bookforFriendschkbx));			
		click(BookingPageLocators.bookforFriendschkbx,"Book for friends");
		
	}	
	
	public void clickSearchFlights() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.findFlights));			
		click(BookingPageLocators.findFlights,"Find Flights");
		explicityWait(BookingPageLocators.selectflightsection, "Select your Departing Flight");
	}
	
	
	
	public void clickEmployeeLogin() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
		click(BookingPageLocators.emplogin_lnk, "Employee Login");
	}
	
	public void clickAgenciesLogin() throws Throwable{		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
		click(BookingPageLocators.agency_lnk, "Agency Login");
	}
	
	public void clickCorporateLogin() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
	click(BookingPageLocators.corporatelogin_lnk, "Corporate Login");
	}
	
	public void clickAgenciesRegister() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
		click(BookingPageLocators.agency_Register, "Agency Registration");
	}
	
	public void clickCorporateAgenciesRegister() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
		click(BookingPageLocators.corporate_Register, "Corporate Registration");
	}
	public void clickManageBooking() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
		click(BookingPageLocators.Manage_booking_lnk, "Manage Booking");		
	}
	
	public void clickWebCheckIn() throws Throwable{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.emplogin_lnk));
		click(BookingPageLocators.WebCheckIn_lnk, "Web Check In");
	}
	
	
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
	
}
