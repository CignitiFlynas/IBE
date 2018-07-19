package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC55_oneWayDomesticBusOneAdultMasterBookingAR extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_55");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_55_oneWayDomesticBusOneAdultAmexBookingAR(String tripType, String origin, String dest, 
			String deptDate,String origin2,String departure2,String retdate, String strTolPass, String Adult,
			String Child,String infant, String promo, String strBookingClass, String FlightType,String totalpass,String nationality,String Doctypr,String docNumber,
			String naSmiles,String Mobile,String email ,String SelectSeat,String paymenttype, String bookingtype,String Charity, 
			String Currency,String Description
			)  throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			deptDate = pickDate(deptDate);
			String 	rtrndate = pickDate(retdate);
			
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
			click(BookingPageLocators.login_lnk, "Login");
			switchtoChildWindow();
			login(username,password);
			selectLanguage("Arabic");
			inputBookingDetails_Arabic(tripType,origin, dest, deptDate , origin2, departure2, rtrndate,Adult, Child, infant,"","","");
			selectClass(strBookingClass, "");
			clickContinueBtn();
			continueOnPassengerDetails();
			coninueOnBaggage();
			continueOnSeatSelection();
			selectPaymentType(paymenttype);
			enterCardDetails("Master");
			waitforElement(BookingPageLocators.summaryRefNumber_AR_uat);
			String strpnr = getText(BookingPageLocators.summaryRefNumber_AR_uat,"PNR");
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			validate_ticketStatus_AR(strPNR);
		
			updateStatus("IBE_UAT_Reg","TC55_oneWayDomesticBusOneAdultMasterBookingAR","Pass");
			Reporter.SuccessReport("TC55_oneWayDomesticBusOneAdultMasterBookingAR", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC55_oneWayDomesticBusOneAdultMasterBookingAR","Fail");
			Reporter.SuccessReport("TC55_oneWayDomesticBusOneAdultMasterBookingAR", "Failed");
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
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Promo", "Value"),
	    		xls.getCellValue("Booking Class", "Value3"),
	    		xls.getCellValue("Flight Type", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		"",
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Email Address", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		xls.getCellValue("Payment Type", "Value"),
	    		"",
    			xls.getCellValue("Charity Donation", "Value"),"",
	    		"Validate One way Domestic Business class Amex card booking in Arabic UI"}};
	}

}
