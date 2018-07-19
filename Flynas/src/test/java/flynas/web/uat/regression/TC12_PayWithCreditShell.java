package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC12_PayWithCreditShell extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_12");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_12_PayWithCreditShell(
			String tripType, String origin, String dest, String departureDate, String origin2,
			String departure2, String retdate,String Adult,String Child,String infant, String promo, 
			String strBookingClass, String bundle,String FlightType,String totalpass,String nationality,String Doctypr,
			String docNumber, String naSmiles,String Mobile,String email ,String SelectSeat,
			String paymenttype,String bookingtype, String charity,String Currency, String payment2 ,
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			//Initializing departure date and return date
			String	deptdate = pickDate(departureDate);
			String	retrndate = pickDate(retdate);
			
			//User Login
			String[] Credentials = pickCredentials("NasCreditCredentials");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
			click(BookingPageLocators.login_lnk, "Login");
			switchtoChildWindow();
			login(username,password);
			
			//Entering Booking Details			
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retrndate,Adult, Child, infant,promo,Currency,paymenttype);
			
			//Selecting travel class
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			
			//entering passenger details
			//String Passengername[]=inputPassengerDetails(FlightType,totalpass,nationality,Doctypr,docNumber, naSmiles,Mobile,email,"","","");
			//System.out.println(Passengername[0]);	
			//System.out.println(Passengername[1]);
			
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			
			//Click continue on Baggage page
			coninueOnBaggage();
			
			selectSeat(SelectSeat, bookingtype);
			payment(paymenttype,"");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);	
			closetoast();
			//Searchin the pnr in mmb
			searchFlight(strPNR, username, "",lastname);
			
			//Cancelling the flight
			cancelFlight("All");
			
			//Navigating to booking page
			navigateToBookingPage();
			
			//Entering booking details
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retdate,Adult, Child, infant,promo,Currency,paymenttype);
			
			//Selecting class
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
						
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			
			//Clicking continue on baggage page			
			if(isElementDisplayedTemp(BookingPageLocators.baggagetittle)==true){
				clickContinueBtn();
			}else{
				System.out.println("No Baggage Page");
			}	
			
			//Selecting seat
			selectSeat(SelectSeat, bookingtype);
			
			payment(payment2,strPNR);
			String strPNR2 = getReferenceNumber();
			System.out.println(strPNR2);
			validate_ticketStatus(strPNR2);
			
			updateStatus("IBE_UAT_Reg","TC12_PayWithCreditShell","Pass");
			Reporter.SuccessReport("PayWithCreditShell", "Pass");					
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC12_PayWithCreditShell","Fail");
			Reporter.failureReport("PayWithCreditShell", "Failed");
		}
	}
	
	
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{	
	    			xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value"),
		    		"",
		    		"",
		    		xls.getCellValue("Return Date", "Value"),
		    		xls.getCellValue("Adults Count", "Value"),
		    		xls.getCellValue("Child Count", "Value"),
		    		xls.getCellValue("Infant Count", "Value"),
		    		xls.getCellValue("Promo", "Value"),
		    		xls.getCellValue("Booking Class", "Value"),
		    		xls.getCellValue("Bundle", "Value2"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Total Passenger", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value"),
		    		xls.getCellValue("Doc Number", "Value"),
		    		"1234567890",
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Email Address", "Value"),
		    		xls.getCellValue("Select Seat", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    		"",
	    			xls.getCellValue("Charity Donation", "Value"),
	    			xls.getCellValue("Currency", "Value"),
	    			xls.getCellValue("Payment Type2", "Value"),	
		    		"Validate Pay With Credit Shell"}};
	}
	

}
