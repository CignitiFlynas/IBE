package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC04_c_oneWaySimpleIntlMutiPaxCheckinFail extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC04_oneWayDomAdultCheckin");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC04c_oneWaySimpleIntlMutiPaxCheckinFail ( String tripType, 
			String origin, String dest, String deptDate,String origin2,String departure2,
			String retdate, String Adult,String Child,String infant, String promo, 
			String strBookingClass, String bundle, String FlightType,String totalpass,String nationality,
			String Doctype, String docNumber, String naSmiles,String Mobile,String SelectSeat,
			String paymenttype, String bookingtype,String Charity, String Currency,
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			//Initializing departure date and return date
			String depDate = pickDate(deptDate);
			String retrndate = pickDate(retdate);
			
			//User Login
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
			
			//Entering Booking Details
			inputBookingDetails(tripType, origin, dest, depDate, origin2,departure2,retrndate,Adult, Child, infant,promo,Currency,paymenttype);
			
			//Selecting flight and traveling class
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			
			//Clicking continue button on Passenger details page
			inputPassengerDetails(FlightType, totalpass, nationality, Doctype, 
					docNumber, naSmiles, Mobile, username,"","","");
			
			Baggage_Extra(tripType);
			clickContinueBtn();
			chooseInsurance("Add");
			selectSeat(SelectSeat, bookingtype);
			//selectSeat(SelectSeat,bookingtype);
			payment(paymenttype,"");
			String strpnr = getReferenceNumber();
			String PNR = strpnr.trim();
			System.out.println("PNR**********"+PNR);
			validate_ticketStatus(PNR);
			searchFlightCheckin(PNR, username, "",lastname);
			verifyAlertPopup();
			
			updateStatus("IBE_UAT_Reg","TC04_c_oneWaySimpleIntlMutiPaxCheckinFail","Pass");
			Reporter.SuccessReport("TC04_c_oneWaySimpleIntlMutiPaxCheckinFail", "Passed");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC04_c_oneWaySimpleIntlMutiPaxCheckinFail","Fail");
			Reporter.failureReport("TC04_c_oneWaySimpleIntlMutiPaxCheckinFail", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    			
	    			xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value2"),
		    		xls.getCellValue("Destination", "Value2"),
		    		xls.getCellValue("Departure Date", "Value2"),
		    		"",
		    		"",
		    		xls.getCellValue("Return Date", "Value"),
		    		xls.getCellValue("Adults Count", "Value2"),
		    		xls.getCellValue("Child Count", "Value2"),
		    		xls.getCellValue("Infant Count", "Value2"),
		    		xls.getCellValue("Promo", "Value"),
		    		xls.getCellValue("Booking Class", "Value"),

		    		xls.getCellValue("Bundle", "Value"),
		    		xls.getCellValue("Flight Type", "Value2"),
		    		xls.getCellValue("Total Passenger", "Value2"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value2"),
		    		xls.getCellValue("Doc Number", "Value"),
		    		xls.getCellValue("na Smiles", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Select Seat", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    		"",
	    			xls.getCellValue("Charity Donation", "Value"),
	    			"",
		    		"Validating checkIn failure with One way Simple class International Multi Pax booking"}};
	}

}