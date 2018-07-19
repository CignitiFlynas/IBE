package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC56_RTDomBothLegMMBChangedateSeatsExtrasBaggageLounge extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_56");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_56_RTDomBothLegMMBChangedateSeatsExtrasBaggageLounge(String tripType, 
			String origin, String dest,String deptDate, String origin2,String departure2,
			String retdate,String Adult,String Child,String infant, String promo, 
			String strBookingClass, String bundle,String FlightType,String totalpass, String nationality,
			String Doctypr,String docNumber,String naSmiles,String Mobile,
			String email ,String SelectSeat,String paymenttype,String bookingtype, 
			String charity,String Currency,String Description
			) throws Throwable {
		try {
			//System.out.println(paymenttype);
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String	deptdate = pickDate(deptDate);
			String	retrndate = pickDate(retdate);
			String	changeDeptDt = nextDateof(deptdate);
			String	changertrnDt = nextDateof(retrndate);
			
			//Pick credentials and login
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
			click(BookingPageLocators.login_lnk, "Login");
			switchtoChildWindow();
			login(username,password);
			
			//Input trip details
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retrndate,Adult, Child, infant,promo,Currency,paymenttype);
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
					
			continueOnPassengerDetails(); 				//Clicking continue button on Passenger details page
			coninueOnBaggage();							//Clicking continue button on Baggage details page
			continueOnSeatSelection(); 					//Skipping seat selection
			payment(paymenttype,"");  					// payment 
			String strPNR = getReferenceNumber();		//Capturing  PNR 
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);				// Verifying booking status
			searchFlight(strPNR, username, "", lastname);		// Search flight on MMB page
			changeDate(changeDeptDt,changertrnDt,"All");	// change date on both departure and return
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");  	// Selecting class in new flight
			selectSeat(SelectSeat, bookingtype);		// Selecting Seats in New flight
			modifyExtras();								// Adding Extras 
			Baggage_Extra(tripType);					// Adding Baggage
			Select_lounge();							// Selecting Business lounge
			clickContinueBtn();						
			payonMMB(paymenttype);						// Payment on MMB
			validate_ticketStatus(strPNR);				// Verifying booking status
			
			updateStatus("IBE_UAT_Reg","TC56_RTDomBothLegMMBChangedateSeatsExtrasBaggageLounge","Pass");
			Reporter.SuccessReport("TC56_RTDomBothLegMMBChangedateSeatsExtrasBaggageLounge", "Pass");
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC56_RTDomBothLegMMBChangedateSeatsExtrasBaggageLounge","Fail");
			Reporter.failureReport("TC56_RTDomBothLegMMBChangedateSeatsExtrasBaggageLounge", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    			
		    		xls.getCellValue("Trip Type", "Value2"),
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
		    		xls.getCellValue("na Smiles", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Email Address", "Value"),
		    		xls.getCellValue("Select Seat", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    		"",
	    			xls.getCellValue("Charity Donation", "Value"),
	    			xls.getCellValue("Currency", "Value"),
	    			"Verify Round trip Domestic booking and MMB - change date, modify extras, Select business lounge Select seat"
    			}
	    	};
	}

}