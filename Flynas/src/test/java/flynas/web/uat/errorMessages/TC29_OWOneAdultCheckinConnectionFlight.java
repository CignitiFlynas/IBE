package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC29_OWOneAdultCheckinConnectionFlight extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC04_oneWayDomAdultCheckin");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_29_OWOneAdultCheckinConnectionFlight ( String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage, String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			String depdate = pickDate(deptDate);
						
			inputBookingDetails(tripType, origin, destination, depdate, "","",retDate,adult, child, infant,promo,currency,paymentType);
			selectClassWithConnectionFlight(bookingClass, tripType);
			String lastname[]=inputPassengerDetails(flightType,totalpsngrs,nationality,docType,docNumber, naSmiles,mobile,email,"","","");
			System.out.println(lastname);
			Baggage_Extra(tripType);
			clickContinueBtn();
			selectallSeats(selectSeat, totalpsngrs, tripType);
			payment(paymentType,"");
			String PNR = getReferenceNumber().trim();
			System.out.println("PNR**********"+PNR);
			validate_ticketStatus(PNR);
			searchFlightCheckin(PNR, "", "", lastname[1]);
			performCheckin(selectSeat,paymentType,totalpsngrs);
			validateCheckin();
			
			
			Reporter.SuccessReport("TC29_OWOneAdultCheckinConnectionFlight", "Passed");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC29_OWOneAdultCheckinConnectionFlight", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("origin", "Value"),
	    		xls.getCellValue("destination", "Value"),
	    		xls.getCellValue("Departure Date", "Value"),
	    		xls.getCellValue("", "Value"),
	    		xls.getCellValue("destination2", "Value"),
	    		xls.getCellValue("Return Date", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("promo", "Value"),
	    		xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Flight Type", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		"1234567890",
	    		xls.getCellValue("mobile", "Value"),
	    		xls.getCellValue("email Address", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		"Credit Card",
	    		"",
    			xls.getCellValue("Charity Donation", "Value"),"",
	    		"Validate One Way Domestic with one Adult CheckIn Connection Flight"}};
	}

}