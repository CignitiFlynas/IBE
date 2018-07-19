package flynas.web.uat.regression;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC17_RTChangeflightCheckIN extends BookingPageFlow {
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_17");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_17_RTChangeflightCheckIN( String BookingClass, String bundle,
			String mobilenum,
			String paymentType,
			String newDate,
			String departurDate,String rtnDate,
			String origin,
			String dest,String triptype,String adult,String child,String infant,String seatSelect,
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			String 	deptdate = pickDate(departurDate);
			String 	rtrndate = pickDate(rtnDate);
			
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
			click(BookingPageLocators.login_lnk, "Login");
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptdate , "", "", rtrndate,adult, child, infant,"","","");
			selectClass(BookingClass, bundle); 
			clickContinueBtn();
			upSellPopUpAction("Continue");
			continueOnPassengerDetails();
			coninueOnBaggage();
			selectSeat(seatSelect, "");	
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			validate_ticketStatus(strpnr);
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			
			String newdate = nextDateof(deptdate);
			String strPNRChangedate = changeDate(strPNR, username, mobilenum,lastname, newdate, "","",BookingClass,0);
			String strPNRChangeDate = strPNRChangedate.trim();
			
			System.out.println(strPNRChangeDate);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			if(strPNRChangeDate.equalsIgnoreCase(strPNR)){
				Reporter.SuccessReport("Change Flight Date", "Flight Date has changed successfully");
			}else{
				Reporter.SuccessReport("Change Flight Date", "Flight Date has NOT changed successfully");
			}

			searchFlightCheckin(strPNRChangeDate, "", "",lastname );
			performCheckin(seatSelect,paymentType,"");
			validateCheckin();
			
			updateStatus("IBE_UAT_Reg","TC17_RTChangeflightCheckIN","Pass");
			Reporter.SuccessReport("TC17_RTChangeflightCheckIN", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC17_RTChangeflightCheckIN","Fail");
			Reporter.failureReport("TC17_RTChangeflightCheckIN", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    			xls.getCellValue("Booking Class", "Value"),
		    		xls.getCellValue("Bundle", "Value2"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Payment Type", "Value"),
		    		xls.getCellValue("NewDate", "Value"),
		    		xls.getCellValue("Departure Date", "Value"),
		    		xls.getCellValue("Return Date", "Value"),
		    		xls.getCellValue("Origin", "Value"),
		    		xls.getCellValue("Destination", "Value"),
		    		xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Adults Count", "Value"),
		    		xls.getCellValue("Child Count", "Value"),
		    		xls.getCellValue("Infant Count", "Value"),
		    		"Extra Leg Room",
		    		"Validate member Round Trip Changeflight CheckIN"}};
	}

}
