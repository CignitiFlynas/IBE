package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPage;
import flynas.web.workflows.BookingPageFlow;

public class TC85_UAE_TI_oneWayPlus1AdultCC_RemoveTI extends BookingPageFlow {

	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"UAE_TI");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC85_UAE_TI_oneWayPlus1AdultCC_RemoveTI(String tripType, 
			String origin, String dest,String deptDate, String origin2,String departure2,
			String retdate,String Adult,String Child,String infant, String promo, 
			String strBookingClass, String bundle,String FlightType,String totalpass, String nationality,
			String Doctypr,String docNumber,String naSmiles,String Mobile, String countrycode,
			String email ,String SelectSeat,String paymenttype,String bookingtype, 
			String charity,String Currency, String Description
			) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String	deptdate = pickDate(deptDate);
			System.out.println(deptdate);
			String	retrndate = pickDate(retdate);
			

			String[] Credentials = pickCredentials("UserCredentialsTI");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			BookingPage.clickLogin();
			login(username,password);
					
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retrndate,Adult, Child, infant,promo,Currency,paymenttype);
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			//upSellPopUpAction("Continue");
			
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails_AE(countrycode);
			
			//Clicking continue button on Baggage details page
			coninueOnBaggage_AE();
			
			selectSeat(SelectSeat, bookingtype);
			payment(paymenttype,"");
			String strPNR = getReferenceNumber();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);
			validatepolicynumber(paymenttype);
			
			searchFlight(strPNR, username, "", lastname);	// Search flight on MMB page
			modifyExtras();	
			removeTI();
			clickContinueBtn();	
			payonMMB(paymenttype);						// Payment on MMB
			
			updateStatus("IBE_UAT_Reg","TC85_UAE_TI_oneWayPlus1AdultCC_RemoveTI","Pass");
			Reporter.SuccessReport("TC85_UAE_TI_oneWayPlus1AdultCC_RemoveTI", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC85_UAE_TI_oneWayPlus1AdultCC_RemoveTI","Fail");
			Reporter.failureReport("TC85_UAE_TI_oneWayPlus1AdultCC_RemoveTI", "Failed");
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
		    		xls.getCellValue("na Smiles", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Country Code", "Value"),
		    		xls.getCellValue("Email Address", "Value"),
		    		xls.getCellValue("Select Seat", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    		"",
	    			xls.getCellValue("Charity Donation", "Value"),
	    			xls.getCellValue("Currency", "Value"),
		    		"Validate Remove TI through MMB for UAE One way Plus bundle 1 Adult CC booking"
    			}
	    	};
	}
}
