package flynas.web.uat.regression;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC18_corporateLoginRToneAdultCancelFlight extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_18");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_18_corporateLoginRToneAdultCancelFlight(String BookingClass, String bundle,
			String mobilenum,String paymentType,String newDate,String departurDate,String rtnDate,String origin,
			String dest,String triptype,String adult,String child,String infant,String seatSelect,String domOrInt,
			String totalPass,String nationality,String docNum,String docType,String email, String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			String 	deptdate = pickDate(departurDate);
			String 	rtrndate = pickDate(rtnDate);
			
			String[] Credentials = pickCredentials("CorporateCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.corporatelogin_lnk, "Corporate Login");	
			click(BookingPageLocators.corporatelogin_lnk, "Corporate Login");
			switchtoChildWindow();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptdate , "", "", rtrndate,adult, child, infant,"","","");
			selectClass(BookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			String[] Passengername = inputPassengerDetails(domOrInt, totalPass, nationality, docType, docNum, "", mobilenum, email, "", "", "");
			coninueOnBaggage();
			selectSeat(seatSelect, "");	
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			validate_ticketStatus(strPNR);
			searchFlight(strPNR, username, "", Passengername[1]);
//			searchFlight("FETI8R", "", "", "wMIRH");
			cancelFlight("All");
				
			updateStatus("IBE_UAT_Reg","TC18_corporateLoginRToneAdultCancelFlight","Pass");
			Reporter.SuccessReport("TC18_corporateLoginRToneAdultCancelFlight", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC18_corporateLoginRToneAdultCancelFlight","Fail");
			Reporter.failureReport("TC18_corporateLoginRToneAdultCancelFlight", "Failed");
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
	    		xls.getCellValue("Flight Type", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Psngr email", "Value"),
	    		"Validate member Login Round Trip Cancel Flight"}};
	}

}
