package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.*;


@SuppressWarnings("rawtypes")
public class TC66_PricelockDomFeeVerificationBookingConfirmationCancellation extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_66");
	
	BookingPage BookingPg = new BookingPage();
	LoginPage LoginPg = new LoginPage();
	projectUtilities util = new projectUtilities();
	
	String[] Credentials = pickCredentials("UserCredentials");
	String username =Credentials[0];
	String password =Credentials[1];
	String lastname =Credentials[3];
	String strPNR;
	
	@DataProvider(name="testData")
	public Object[][] createdata() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value"),	
		    		xls.getCellValue("adults", "Value"),	
		    		xls.getCellValue("Booking Class", "Value"),
		    		xls.getCellValue("Bundle", "Value"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Card Type", "Value"),
		    		
		    	}
	    	};
	}
	
	@Test(priority=0,dataProvider = "testData",groups={"Chrome"})
	public  void TC_66A_PricelockDomFeeVerificationBooking(String tripType, String origin, String destination,
			String deptDate,String adults,String BookingClass, String bundle,String FlightType,String cardType) throws Throwable {
		try {			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Verify Friends booking");
			
			String	deptdate = pickDate(deptDate);
			
			LoginPg.SelectEmployeelogin();
			switchtoChildWindow();
			LoginPg.login(username,password);
			BookingPg.selectTripType(tripType);
			BookingPg.enterOwTripdetails(origin, destination, deptdate);
			BookingPg.enterPassengerscount(adults, "0", "0");
			BookingPg.clickSearchFlights();
			selectClass(BookingClass, bundle);
			selectHoldfare();
			verifyPLCK(FlightType);
			clickContinueBtn();
			enterCardDetails(cardType);
			submit3Dsecurepin();
			strPNR = getReferenceNumber();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);
			
			updateStatus("IBE_UAT_Reg","TC_66A_PricelockDomFeeVerification","Pass");
			Reporter.SuccessReport("TC_66A_PricelockDomFeeVerification", "Pass");						
		}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_66A_PricelockDomFeeVerification","Fail");
			Reporter.failureReport("TC_66A_PricelockDomFeeVerification", "Failed");
		}
	}
	

}