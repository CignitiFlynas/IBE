package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPage;
import flynas.web.workflows.BookingPageFlow;

public class TC46_RTDomfullCancelVerifyMMB extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_45");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_46_RTDomfullCancelVerifyMMB(String tripType, 
			String origin, String dest,String deptDate, String origin2,String departure2,
			String retdate,String Adult,String Child,String infant, String promo, 
			String strBookingClass, String bundle,String FlightType,String totalpass, String nationality,
			String Doctypr,String docNumber,String naSmiles,String Mobile,
			String email ,String SelectSeat,String paymenttype,String bookingtype, 
			String charity,String Currency, String Description
			) throws Throwable {
		try {
			//System.out.println(paymenttype);
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String	deptdate = pickDate(deptDate);
			String	retrndate = pickDate(retdate);
			

			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			BookingPage.clickLogin();
			login(username,password);
					
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retrndate,Adult, Child, infant,promo,Currency,paymenttype);
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			
			//Clicking continue button on Baggage details page
			coninueOnBaggage();
			
			selectSeat(SelectSeat, bookingtype);
			payment(paymenttype,"");
			String strPNR = getReferenceNumber();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);
			searchFlight(strPNR, username, "", lastname);
			cancelFlight("All");
			searchFlight(strPNR, username, "", lastname);
			verifyAlertPopup();
			
			updateStatus("IBE_UAT_Reg","TC46_RTDomfullCancelVerifyMMB","Pass");
			Reporter.SuccessReport("TC46_RTDomfullCancelVerifyMMB", "Pass");
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC46_RTDomfullCancelVerifyMMB","Fail");
			Reporter.failureReport("TC46_RTDomfullCancelVerifyMMB", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    			
		    		xls.getCellValue("Trip Type", "Value2"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value2"),
		    		"",
		    		"",
		    		xls.getCellValue("Return Date", "Value2"),
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
		    		"Round trip Domestic booking, full cancellation and mmb verification"
    			}
	    	};
	}

}