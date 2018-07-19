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

public class TC22_roundTripDomesticEconomy_DMM_ELQ extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUATRoutes"),"AllRoutes");

	@Test(dataProvider = "testData",groups={"Economy"})
	public  void TC_22_roundTripDomesticEconomy_DMM_ELQ( String bookingClass, String bundle,
			String mobilenum,
			String paymentType,
			String newDate,
			String depDate,String rtnDate,
			String origin,
			String dest,String triptype,String adult,String child,String infant,String totalpass,String seatSelect,
			String Description) throws Throwable {
		try {

			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];			
			String deptDate = pickDate(depDate);
			String rtrnDate = pickDate(rtnDate);
			click(BookingPageLocators.login_lnk, "Login");	

			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);

			click(BookingPageLocators.login_lnk, "Login");

			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptDate , "", "", rtrnDate,adult, child, infant,"","","");
			selectClass(bookingClass, bundle); 
			clickContinueBtn();
			upSellPopUpAction("Continue");
			waitforElement(BookingPageLocators.passengerDetailsTittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			waitforElement(BookingPageLocators.baggagetittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			selectallSeats(seatSelect, totalpass, triptype);
			if(isElementDisplayedTemp(BookingPageLocators.ok)){
				click(BookingPageLocators.ok, "OK");
			}
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);

			Reporter.SuccessReport("TC22_roundTripDomesticEconomy_DMM_ELQ", "Pass");

		}

		catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC22_roundTripDomesticEconomy_DMM_ELQ", "Failed");
		}
	}

	@DataProvider(name="testData")
	public Object[][] createdata1() {
		return (Object[][]) new Object[][] { 
			{
				
				
				xls.getCellValue("Booking Class", "Value"),
				xls.getCellValue("Mobile", "Value"),
				xls.getCellValue("Payment Type", "Value"),
				xls.getCellValue("NewDate", "Value"),
				xls.getCellValue("Departure Date", "Value"),
				xls.getCellValue("Return Date", "Value"),
				xls.getCellValue("Origin", "Value22"),
				xls.getCellValue("Destination", "Value22"),
				xls.getCellValue("Trip Type", "Value2"),
				xls.getCellValue("Adults Count", "Value"),
				xls.getCellValue("Child Count", "Value"),
				xls.getCellValue("Infant Count", "Value"),
				xls.getCellValue("Total Passenger", "Value"),
				xls.getCellValue("Select Seat", "Value"),
			"Validate RoundTrip Domestic Economy_DMM_ELQ"}};
	}


}
