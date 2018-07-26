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
import flynas.web.workflows.BookingPage;
import flynas.web.workflows.BookingPageFlow;

public class TC153_oneWayDomesticBusiness_AHB_GIZ extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUATRoutes"),"AllRoutes");

	@Test(dataProvider = "testData",groups={"Business"})
	public  void TC_153_oneWayDomesticBusiness_AHB_GIZ( String bookingClass, String bundle,
			String mobilenum,
			String paymentType,
			String newDate,
			String depDate,String rtnDate,
			String origin,
			String dest,String triptype,String adult,String child,String infant,String seatSelect,
			String newdate,	String strTolPass,String domOrInt,String Description) throws Throwable {
		try {
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];	
			String lastname=Credentials[3];	
			String deptDate = pickDate(depDate);
			String retrnDate = pickDate(rtnDate);
			BookingPage.clickLogin();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptDate , "", "", retrnDate,adult, child, infant,"","","");
			selectClass(bookingClass, bundle); 
			clickContinueBtn();
			//upSellPopUpAction("Continue");
			continueOnPassengerDetails();
			waitforElement(BookingPageLocators.baggagetittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			chooseInsurance("Add");
			selectSeat(seatSelect, "");
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);
			searchFlight(strPNR, username, "", lastname);
			cancelFlight("Äll");
							
		
			Reporter.SuccessReport("TC153_oneWayDomesticBusiness_AHB_GIZ", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC153_oneWayDomesticBusiness_AHB_GIZ", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    		xls.getCellValue("Booking Class", "Value2"),
	    		"",
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Payment Type", "Value"),
	    		xls.getCellValue("NewDate", "Value"),
	    		xls.getCellValue("Departure Date", "Value"),
	    		xls.getCellValue("Return Date", "Value"),
	    		xls.getCellValue("Origin", "Value29"),
	    		xls.getCellValue("Destination", "Value29"),
	    		xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Select Seat", "Value3"),
	    		"01-April 2017",
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("Flight Type", "Value"),
	    		"Validate oneWay Domestic Business_ABH_GIZ"}};
	}

}
