package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC02_ConformBookingCancelPNR extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_12");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_02_ConformBookingCancelPNR(String tripType,String origin, String destination,String deptDate,
			String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			deptDate = pickDate(deptDate);			
			inputBookingDetails(tripType, origin, destination, deptDate, "", "", retDate,adult, child, infant,promo,currency,paymentType);
			selectClass(bookingClass, tripType);
			clickContinueBtn();
			String FirstLastname[]=inputPassengerDetails(flightType,totalpsngrs,nationality,docType,docNumber, naSmiles,mobile,email,"","","");
			System.out.println(FirstLastname[0]);	
			System.out.println(FirstLastname[1]);
			waitforElement(BookingPageLocators.baggagetittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			if(isElementDisplayedTemp(BookingPageLocators.baggagetittle)==true){
				clickContinueBtn();
			}else{
				System.out.println("No Baggage Page");
			}		
			waitForElementPresent(BookingPageLocators.selectseattittle, "selectseattittle");
			waitUtilElementhasAttribute(BookingPageLocators.body);
			if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)){
				clickContinueBtn();
				 if(isElementDisplayedTemp(BookingPageLocators.ok)){
					click(BookingPageLocators.ok, "OK");
				 }
			}else{
					System.out.println("No Seat Page");
			}
			payment(paymentType,"");
			String strpnr = getReferenceNumber().trim();
			System.out.println(strpnr);				
			searchFlight(strpnr, email, "", "");
			cancelFlight("All");
			searchFlightCheckin(strpnr, email, "", "");
			if(isElementDisplayedTemp(BookingPageLocators.ErrorMsg1)){
				String ErrorMsg=getText(BookingPageLocators.ErrorMsg1, "Error Message");
				if(ErrorMsg.contains("Unable to find the booking")){
					Reporter.SuccessReport("Validating Error Message For WCI", "Successfully Verified:" +ErrorMsg);
				}else{
					Reporter.failureReport("Validating Erroe Message For WCI", "Expected Error Message not Came");
				}
				
			}	
			Reporter.SuccessReport("TC02_ConformBookingCancelPNR", "Pass");
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC02_ConformBookingCancelPNR", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("origin", "Value"),
	    		xls.getCellValue("destination", "Value"),
	    		xls.getCellValue("Departure Date", "Value"),
	    		"",
	    		"",
	    		xls.getCellValue("Return Date", "Value"),
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
	    		xls.getCellValue("Payment Type", "Value"),
	    		"",
    			xls.getCellValue("Charity Donation", "Value"),
    			xls.getCellValue("currency", "Value"),
    			xls.getCellValue("Payment Type2", "Value"),
	    		"Validate Error Message After Cancel PNR "}};
	}
	

}
