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
public class TC64_EmpLoginFriendsBookingCancellationLimitValidation extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_64");
	
	BookingPage BookingPg = new BookingPage();
	LoginPage LoginPg = new LoginPage();
	projectUtilities util = new projectUtilities();
	
	String[] Credentials = pickCredentials("EmployeeCreds");
	String username =Credentials[0];
	String password =Credentials[1];
	String lastname =Credentials[3];
	int maxfriends;
	int frndslimit;
	String adults;
	String strPNR;
	
	@DataProvider(name="testData")
	public Object[][] createdata() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value"),	
		    		xls.getCellValue("Booking Class", "Value"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    				    		
		    	}
	    	};
	}
	
	@Test(priority=0,dataProvider = "testData",groups={"Chrome"})
	public  void TC_64A_TravelPartnerBooking(String tripType, String origin, String destination, String deptDate,
			String BookingClass,String FlightType, String Nationality,String DocumentType,String Mobile,
			String paymenttype) throws Throwable {
		try {			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Verify Friends booking");
			
			String	deptdate = pickDate(deptDate);
		
			LoginPg.SelectEmployeelogin();
			switchtoChildWindow();
			LoginPg.login(username,password);
			
			maxfriends = BookingPg.getFriendsCount();
			if(maxfriends>0){
			adults = Integer.toString(((int)(Math.random() * maxfriends-1)) + 1);
			BookingPg.selectTripType(tripType);
			BookingPg.enterOwTripdetails(origin, destination, deptdate);
			BookingPg.enterPassengerscount(adults, "0", "0");
			BookingPg.selectBookforfriends();	
			BookingPg.clickSearchFlights();
			
			selectClassForStaff(BookingClass);
			inputPassengerDetails(FlightType, adults, Nationality, DocumentType,"", "", Mobile, username,"","","");
			coninueOnBaggage();
			continueOnSeatSelection();
			payment(paymenttype,"");
			strPNR = getReferenceNumber();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);
			
			
			updateStatus("IBE_UAT_Reg","TC_64A_TravelPartnerBooking","Pass");
			Reporter.SuccessReport("TC_64A_TravelPartnerBooking", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_64A_TravelPartnerBooking","Fail");
			Reporter.failureReport("TC_64A_TravelPartnerBooking", "Failed");
			}
			
		}
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_64A_TravelPartnerBooking","Fail");
			Reporter.failureReport("TC_64A_TravelPartnerBooking", "Failed");
		}
	}
	
	
	@Test(priority=1,dependsOnMethods = { "TC_64A_TravelPartnerBooking" },groups={"Chrome"})
	public  void TC_64B_VerifyAllowableFriendscountAfterbooking() 
			throws Throwable {
		try {			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Verify Allowable friends count after booking");
			
						
			LoginPg.SelectEmployeelogin();
			switchtoChildWindow();
			LoginPg.login(username,password);
			
			frndslimit = BookingPg.getFriendsCount();
			if(frndslimit == maxfriends-Integer.parseInt(adults)){
				updateStatus("IBE_UAT_Reg","TC_64B_VerifyAllowableFriendscountAfterbooking","Pass");
				Reporter.SuccessReport("TC_64B_VerifyAllowableFriendscountAfterbooking", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_64B_VerifyAllowableFriendscountAfterbooking","Fail");
				Reporter.failureReport("TC_64B_VerifyAllowableFriendscountAfterbooking", "Failed");
			}
			
		}
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_64B_VerifyAllowableFriendscountAfterbooking","Fail");
			Reporter.failureReport("TC_64B_VerifyAllowableFriendscountAfterbooking", "Failed");
		}
	}
	
	
	@Test(priority=2,dependsOnMethods = { "TC_64A_TravelPartnerBooking" },groups={"Chrome"})
	public  void TC_64C_VerifyAllowableFriendscountAfterCancellation() 
			throws Throwable {
		try {			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Verify Allowable friends count after booking");
			
			LoginPg.SelectEmployeelogin();
			switchtoChildWindow();
			LoginPg.login(username,password);
			
			searchFlight(strPNR, username, "", lastname);
			cancelFlight("All");
			
			navigateToHome();
			
			frndslimit = BookingPg.getFriendsCount();
			if(frndslimit == maxfriends){
				updateStatus("IBE_UAT_Reg","TC_64C_VerifyAllowableFriendscountAfterCancellation","Pass");
				Reporter.SuccessReport("TC_64C_VerifyAllowableFriendscountAfterCancellation", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_64C_VerifyAllowableFriendscountAfterCancellation","Fail");
				Reporter.failureReport("TC_64C_VerifyAllowableFriendscountAfterCancellation", "Failed");
			}
			
		}
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_64C_VerifyAllowableFriendscountAfterCancellation","Fail");
			Reporter.failureReport("TC_64C_VerifyAllowableFriendscountAfterCancellation", "Failed");
		}
	}	

}