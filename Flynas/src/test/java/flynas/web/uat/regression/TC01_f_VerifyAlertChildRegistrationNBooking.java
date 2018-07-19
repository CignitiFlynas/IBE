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
import flynas.web.workflows.LoginPage;
import flynas.web.workflows.MemberDashboard;
import flynas.web.workflows.MemberRegistrationPage;
import flynas.web.workflows.MyProfilePage;
import flynas.web.workflows.projectUtilities;

public class TC01_f_VerifyAlertChildRegistrationNBooking extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_20");

	@SuppressWarnings("rawtypes")
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_01_f_VerifyAlertChildRegistrationNBooking(String tripType,String origin,String dest,
			String deptdate,String origin2,String departure2,String retrndate,String Adult,String Child,
			String infant,String promo,String Currency,String paymenttype,String BookingClass, String bundle, 
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			//instantiating page objects
			projectUtilities util = new projectUtilities();
			LoginPage LoginPg = new LoginPage();
			MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
			MemberDashboard memberdb = new MemberDashboard();
			String deptDate = pickDate(deptdate);
			String retdate = pickDate(retrndate);		
			
			//navigating to login page
			util.clickLogin();
			LoginPg.ClickJoinNow();
			memberRegisterPg.memberRegistration("","Child"); // Registering a new Adult member
			memberRegisterPg.verifingMemberRegistration();
			
			inputBookingDetails(tripType, origin, dest, deptDate, origin2,departure2,retdate,Adult, Child, infant,promo,Currency,paymenttype);
			
			//Selecting a fare class
			selectClass(BookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			util.VerifyAlertMessage("mandatory fields");
			
			updateStatus("IBE_UAT_Reg","TC01_f_VerifyAlertChildRegistrationNBooking","Pass");
			Reporter.SuccessReport("TC01_f_VerifyAlertChildRegistrationNBooking", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC01_f_VerifyAlertChildRegistrationNBooking","Fail");
			Reporter.SuccessReport("TC01_f_VerifyAlertChildRegistrationNBooking", "Failed");
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
		    		xls.getCellValue("Currency", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    		xls.getCellValue("Booking Class", "Value"),

		    		xls.getCellValue("Bundle", "Value"),
		    	  	"Validate Child booking failure"}};
	}

}
