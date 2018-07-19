package flynas.web.uat.regression;

import java.text.DateFormat;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.*;


@SuppressWarnings("rawtypes")
public class TC65_EmpLoginFriendsBookingFeeVerification extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_64");
	
	BookingPage BookingPg = new BookingPage();
	LoginPage LoginPg = new LoginPage();
	projectUtilities util = new projectUtilities();
	ExtrasPage ExtrasPg = new ExtrasPage();
	
	String[] Credentials = pickCredentials("EmployeeCreds");
	String username =Credentials[0];
	String password =Credentials[1];
	String lastname =Credentials[3];
		
	int maxfriends;
	int frndslimit;
	String adults;
	String strPNR;
	
	
	@DataProvider(name="testData1")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value4"),	
		    		xls.getCellValue("Booking Class", "Value"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    				    		
		    	}
	    	};
	}
	
	@Test(priority=0,dataProvider = "testData1",groups={"Chrome"})
	public  void TC_65A_DomStandByFriendsBookingFeeVerification(String tripType, String origin, String destination, String deptDate,
			String BookingClass, String FlightType, String Nationality,String DocumentType,String Mobile,
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
			inputPassengerDetails(FlightType, adults, Nationality, DocumentType,"", "", "", "","","","");
			ExtrasPg.verifyFriendsFee(Integer.parseInt(adults),FlightType);
			
			updateStatus("IBE_UAT_Reg","TC_65A_DomStandByFriendsBookingFeeVerification","Pass");
			Reporter.SuccessReport("TC_65A_DomStandByFriendsBookingFeeVerification", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_65A_DomStandByFriendsBookingFeeVerification","Fail");
			Reporter.failureReport("TC_65A_DomStandByFriendsBookingFeeVerification", "Failed");
			}			
		}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_65A_DomStandByFriendsBookingFeeVerification","Fail");
			Reporter.failureReport("TC_65A_DomStandByFriendsBookingFeeVerification", "Failed");
		}
	}
	
	
	@DataProvider(name="testData2")
	public Object[][] createdata2() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Departure Date", "Value"),	
		    		xls.getCellValue("Booking Class", "Value2"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    				    		
		    	}
	    	};
	}
	
	@Test(priority=1,dataProvider = "testData2",groups={"Chrome"})
	public  void TC_65B_DomStaffCnfmdFriendsBookingFeeVerification(String tripType, String origin, String destination, String deptDate,
			String BookingClass, String bundle,String FlightType, String Nationality,String DocumentType,String Mobile,
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
			inputPassengerDetails(FlightType, adults, Nationality, DocumentType,"", "", "", "","","","");
			ExtrasPg.verifyFriendsFee(Integer.parseInt(adults),FlightType);
			
			updateStatus("IBE_UAT_Reg","TC_65B_DomStaffCnfmdFriendsBookingFeeVerification","Pass");
			Reporter.SuccessReport("TC_65B_DomStaffCnfmdFriendsBookingFeeVerification", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_65B_DomStaffCnfmdFriendsBookingFeeVerification","Fail");
			Reporter.failureReport("TC_65B_DomStaffCnfmdFriendsBookingFeeVerification", "Failed");
			}			
		}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_65B_DomStaffCnfmdFriendsBookingFeeVerification","Fail");
			Reporter.failureReport("TC_65B_DomStaffCnfmdFriendsBookingFeeVerification", "Failed");
		}
	}
	
	
	@DataProvider(name="testData3")
	public Object[][] createdata3() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value2"),
		    		xls.getCellValue("Destination", "Value2"),
		    		xls.getCellValue("Departure Date", "Value"),	
		    		xls.getCellValue("Booking Class", "Value2"),
		    		xls.getCellValue("Bundle", "Value"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value2"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    				    		
		    	}
	    	};
	}
	
	@Test(priority=2,dataProvider = "testData3",groups={"Chrome"})
	public  void TC_65C_IntlStandByFriendsBookingChargeVerification(String tripType, String origin, String destination, String deptDate,
			String BookingClass, String bundle,String FlightType, String Nationality,String DocumentType,String Mobile,
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
			inputPassengerDetails(FlightType, adults, Nationality, DocumentType,"", "", "", "","","","");
			ExtrasPg.verifyFriendsFee(Integer.parseInt(adults),FlightType);
			
			updateStatus("IBE_UAT_Reg","TC_65C_IntlStandByFriendsBookingChargeVerification","Pass");
			Reporter.SuccessReport("TC_65C_IntlStandByFriendsBookingChargeVerification", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_65C_IntlStandByFriendsBookingChargeVerification","Fail");
			Reporter.failureReport("TC_65C_IntlStandByFriendsBookingChargeVerification", "Failed");
			}			
		}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_65C_IntlStandByFriendsBookingChargeVerification","Fail");
			Reporter.failureReport("TC_65C_IntlStandByFriendsBookingChargeVerification", "Failed");
		}
	}
	
	
	@DataProvider(name="testData4")
	public Object[][] createdata4() {
	    return (Object[][]) new Object[][] { 
	    		
		    	{	xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value2"),
		    		xls.getCellValue("Destination", "Value2"),
		    		xls.getCellValue("Departure Date", "Value"),	
		    		xls.getCellValue("Booking Class", "Value2"),
		    		xls.getCellValue("Bundle", "Value"),
		    		xls.getCellValue("Flight Type", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value2"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    				    		
		    	}
	    	};
	}
	
	@Test(priority=2,dataProvider = "testData4",groups={"Chrome"})
	public  void TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification(String tripType, String origin, String destination, String deptDate,
			String BookingClass, String bundle,String FlightType, String Nationality,String DocumentType,String Mobile,
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
			inputPassengerDetails(FlightType, adults, Nationality, DocumentType,"", "", "", "","","","");
			ExtrasPg.verifyFriendsFee(Integer.parseInt(adults),FlightType);
			
			updateStatus("IBE_UAT_Reg","TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification","Pass");
			Reporter.SuccessReport("TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification", "Pass");
			}
			else{
				updateStatus("IBE_UAT_Reg","TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification","Fail");
			Reporter.failureReport("TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification", "Failed");
			}			
		}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification","Fail");
			Reporter.failureReport("TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification", "Failed");
		}
	}
	

}