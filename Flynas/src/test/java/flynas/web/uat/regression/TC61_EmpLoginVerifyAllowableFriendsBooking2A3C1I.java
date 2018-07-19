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
public class TC61_EmpLoginVerifyAllowableFriendsBooking2A3C1I extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_61");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_61A_VerifyAllowableFriendsBooking2A3C1I(String tripType, String origin, String destination,
			String deptDate, String adults,String Children,String infants, String Description) 
			throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			BookingPage BookingPg = new BookingPage();
			LoginPage LoginPg = new LoginPage();
			projectUtilities util = new projectUtilities();
			String	deptdate = pickDate(deptDate);
			
			String[] Credentials = pickCredentials("EmployeeCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			
			
			LoginPg.SelectEmployeelogin();
			switchtoChildWindow();
			LoginPg.login(username,password);
			
			int maxfriends = BookingPg.getFriendsCount();
			BookingPg.selectTripType(tripType);
			BookingPg.enterOwTripdetails(origin, destination, deptdate);
			BookingPg.enterPassengerscount(adults, Children, infants);
			BookingPg.selectBookforfriends();	
			BookingPg.clickSearchFlights();
			if(Integer.parseInt(adults)+Integer.parseInt(Children) > maxfriends)
				util.VerifyErrorMessage("Passengers Count more than Maximum allowed friends booking.");
			
			updateStatus("IBE_UAT_Reg","TC61_VerifyAllowableFriendsBooking2A3C1I","Pass");
			Reporter.SuccessReport("TC61_VerifyAllowableFriendsBooking2A3C1I", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC61_VerifyAllowableFriendsBooking2A3C1I","Fail");
			Reporter.failureReport("TC61_VerifyAllowableFriendsBooking2A3C1I", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value"),		    		
		    		xls.getCellValue("Adults Count", "Value"),
		    		xls.getCellValue("Child Count", "Value"),
		    		xls.getCellValue("Infant Count", "Value"),		    		
		    		"Verify if the app allows friends booking for more than max count"
		    	}		    
	    	};
	}

}