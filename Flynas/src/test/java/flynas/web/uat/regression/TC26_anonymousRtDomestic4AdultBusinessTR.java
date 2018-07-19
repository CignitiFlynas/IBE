package flynas.web.uat.regression;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC26_anonymousRtDomestic4AdultBusinessTR extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_26");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_26_anonymousRtDomestic4AdultBusinessTR(String tripType, String origin, String dest,String deptDate,String origin2,
			String departure2,String retdate, String strTolPass, String Adult,String Child,String infant, String promo, String strBookingClass, String bundle,
			String FlightType,String totalpass,String nationality,String Doctypr,String docNumber,String naSmiles,String Mobile,String email ,String SelectSeat,String paymenttype, String bookingtype,String Charity, 
			String Currency,String Description
			)  throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String deptdate = pickDate(deptDate);
			String 	rtrndate = pickDate(retdate);
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.Arabic_pdctn_AR("Türkçe"), "Tarkish Language");	
			click(BookingPageLocators.Arabic_pdctn_AR("Türkçe"), "Tarkish Language");
			inputBookingDetails_Tarkish(tripType,origin, dest, deptdate , origin2, departure2, rtrndate,Adult, Child, infant,"","");
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			inputPassengerDetails_Tarkish(FlightType, totalpass, nationality, Doctypr,docNumber,"", Mobile, email, "", "", "");
			coninueOnBaggage();
			continueOnSeatSelection();
				payment(paymenttype, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			validate_ticketStatus_TR(strPNR);
			
			updateStatus("IBE_UAT_Reg","TC26_anonymousRtDomestic4AdultBusinessTR","Pass");
			Reporter.SuccessReport("TC26_anonymousRtDomestic4AdultBusinessTR", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC26_anonymousRtDomestic4AdultBusinessTR","Fail");
			Reporter.SuccessReport("TC26_anonymousRtDomestic4AdultBusinessTR", "Failed");
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
	    		xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Bundle", "Value"),

	    		xls.getCellValue("Flight Type", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		"1234567890",
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Email Address", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		"Credit Card",
	    		"",
    			xls.getCellValue("Charity Donation", "Value"),"",
	    		"Validate Anonymous user Round Trip Domestic 4-Adult Business in TR"}};
	}


}
