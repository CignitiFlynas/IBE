package flynas.web.uat.routes;

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

public class TC124_roundTripDomesticFlexAddExtra_RUH_AHB extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUATRoutes"),"AllRoutes");

	@Test(dataProvider = "testData",groups={"Flex"})
	public  void TC_124_roundTripDomesticFlexAddExtra_RUH_AHB( 
			String depDate,String rtnDate,String origin,String dest,String triptype,String adult,
			String child,String infant,String flightType,String bookingClass, String bundle, String seatSelect,
			String totalPassenger,String nationality,String documentType, String docNumber,String nasmiles,
			String mobilenum,String paymentType,String newDate, String Description) throws Throwable {
		try {
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];			
			String deptDate = pickDate(depDate);
			String retrnDate = pickDate(rtnDate);			
			inputBookingDetails(triptype,origin, dest, deptDate , "RUH", "AMM", retrnDate,adult, child, infant,"","","");
			selectClass(bookingClass, bundle); 
			clickContinueBtn();
			upSellPopUpAction("Continue");
			inputPassengerDetails(flightType, totalPassenger, nationality, documentType,
					docNumber, nasmiles, mobilenum, username,"","","");
			Baggage_Extra(triptype);
			addSportsEqpmnt(triptype);
			Select_A_Meal();
			Select_lounge();
			inputExtras("12");
			selectallSeats(seatSelect,"2",triptype);
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			
			validate_ticketStatus(strPNR);
		
			Reporter.SuccessReport("TC124_roundTripDomesticFlexAddExtra_RUH_AHB", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC124_roundTripDomesticFlexAddExtra_RUH_AHB", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    		
		    	
		    	xls.getCellValue("Departure Date", "Value"),
	    		xls.getCellValue("Return Date", "Value"),
	    		xls.getCellValue("Origin", "Value62"),
	    		xls.getCellValue("Destination", "Value62"),
	    		xls.getCellValue("Trip Type", "Value2"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value2"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Flight Type", "Value"),    		
	    		xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Bundle", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		xls.getCellValue("na Smiles", "Value"),	    		
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Payment Type", "Value"),
	    		xls.getCellValue("NewDate", "Value"),    		
	    		"Validate RoundTrip Domestic Flex AddExtra_RUH_AHB"}};
	}


}
