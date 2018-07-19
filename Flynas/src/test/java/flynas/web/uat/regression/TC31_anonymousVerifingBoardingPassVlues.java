package flynas.web.uat.regression;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC31_anonymousVerifingBoardingPassVlues extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC04_oneWayDomAdultCheckin");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_31_anonymousVerifingBoardingPassVlues (String tripType, String origin, String dest,String deptDate,String origin2,
			String departure2,String retdate, String strTolPass, String Adult,String Child,String infant, String promo, 
			String strBookingClass, String bundle,String FlightType,String totalpass,String nationality,String Doctypr,String docNumber,
			String naSmiles,String Mobile,String email ,String SelectSeat,String paymenttype, String bookingtype,String Charity, 
			String Currency,String browser,String Description
			) throws Throwable {
		try {			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String deptdate = pickDate(deptDate);
			
		
			inputBookingDetails(tripType, origin, dest, deptdate, origin2,departure2,retdate,Adult, Child, infant,promo,Currency,paymenttype);
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			String lastname[]=inputPassengerDetails(FlightType,totalpass,nationality,Doctypr,docNumber, naSmiles,Mobile,email,"","","");
			System.out.println(lastname);
			Baggage_Extra(tripType);
			clickContinueBtn();
			chooseInsurance("Add");
			selectSeat(SelectSeat,bookingtype);
			payment(paymenttype,"");
			String strpnr = getReferenceNumber();
			String PNR = strpnr.trim();
			System.out.println("PNR**********"+PNR);
			validate_ticketStatus(PNR);
			searchFlightCheckin(PNR, "", "", lastname[1]);
			performCheckin(SelectSeat,paymenttype,strTolPass);
			validateCheckin();
			clickDownloadBoardingpasses();		
			isFileDownloaded("bp.pdf");
			
			updateStatus("IBE_UAT_Reg","TC31_anonymousVerifingBoardingPassVlues","Pass");
			Reporter.SuccessReport("TC31_anonymousVerifingBoardingPassVlues", "Passed");
		}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC31_anonymousVerifingBoardingPassVlues","Fail");
			Reporter.failureReport("TC31_anonymousVerifingBoardingPassVlues", "Failed");
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
	    			configProps.getProperty("browserType"),
		    		"Validate Boarding passes"}};
	}

}
