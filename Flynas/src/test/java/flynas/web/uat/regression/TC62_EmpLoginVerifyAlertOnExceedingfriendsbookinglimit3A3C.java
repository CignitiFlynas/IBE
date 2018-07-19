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
public class TC62_EmpLoginVerifyAlertOnExceedingfriendsbookinglimit3A3C extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_61");

	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_62_VerifyAlertOnExceedingfriendsbookinglimit3A3C(String tripType, String origin, String destination,
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
			
			updateStatus("IBE_UAT_Reg","TC62_VerifyAlertOnExceedingfriendsbookinglimit3A3C","Pass");
			Reporter.SuccessReport("TC62_VerifyAlertOnExceedingfriendsbookinglimit3A3C", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC62_VerifyAlertOnExceedingfriendsbookinglimit3A3C","Fail");
			Reporter.failureReport("TC62_VerifyAlertOnExceedingfriendsbookinglimit3A3C", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value2"),		    		
		    		xls.getCellValue("Adults Count", "Value2"),
		    		xls.getCellValue("Child Count", "Value2"),
		    		xls.getCellValue("Infant Count", "Value2"),		    		
		    		"Verify Alert On Exceeding friends booking limit with 3 Adults and 3 Children"}
	    	};
	}
	

}