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

public class TC02_e_RTIntlBusiness1AdultCC extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_02_RTIntlBusiness1AdultCC");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_02e_RTIntlBusiness1AdultCC(String tripType, 
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
			BookingPage.clickLogin();
			login(username,password);
					
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retrndate,Adult, Child, infant,promo,Currency,paymenttype);
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			
			
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			//Clicking continue button on Baggage details page
			coninueOnBaggage();
			selectSeat(SelectSeat, bookingtype);
			payment(paymenttype,"");
			String strPNR = getReferenceNumber();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);
			
			updateStatus("IBE_UAT_Reg","TC02_e_RTIntlBusiness1AdultCC","Pass");
			Reporter.SuccessReport("TC02_e_RTIntlBusiness1AdultCC", "Pass");
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC02_e_RTIntlBusiness1AdultCC","Fail");
			Reporter.failureReport("TC02_e_RTIntlBusiness1AdultCC", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    			
		    		xls.getCellValue("Trip Type", "Value2"),
		    		xls.getCellValue("Origin", "Value2"),
		    		xls.getCellValue("Destination", "Value2"),
		    		xls.getCellValue("Departure Date", "Value5"),
		    		"",
		    		"",
		    		xls.getCellValue("Return Date", "Value5"),
		    		xls.getCellValue("Adults Count", "Value"),
		    		xls.getCellValue("Child Count", "Value"),
		    		xls.getCellValue("Infant Count", "Value"),
		    		xls.getCellValue("Promo", "Value"),
		    		xls.getCellValue("Booking Class", "Value2"),
		    		xls.getCellValue("Bundle", "Value"),
		    		xls.getCellValue("Bundle", "Value"),
		    		xls.getCellValue("Flight Type", "Value2"),
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
		    		"Validate Roundtrip International Business Class 1 Adult CC booking with"
    			}
	    	};
	}

}