package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC43_ValidateErrOnNotEntrngVoucherNumber extends BookingPageFlow {
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"Errors_On_PnrRetrieval");

	@Test(dataProvider = "testData",groups={"Chrome"})
	
	public  void ValidateErrOnNotEntrngVoucherNumber (String Username,String Password, String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage, String Description) throws Throwable
	{
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			deptDate = pickDate(deptDate);
			retDate = pickDate(retDate);
			
			click(BookingPageLocators.login_lnk, "Login");
			switchtoChildWindow();
			login(Username,Password);
			
			inputBookingDetails(tripType, origin, destination, deptDate,"", "",retDate,
					adult, child, infant, promo,currency,paymentType);
			
			
			selectClass(bookingClass, tripType);
			clickContinueBtn();
			
			//Clicking continue button on Passenger details page
			waitforElement(BookingPageLocators.passengerDetailsTittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			
			//Clicking continue button on Baggage details page
			waitforElement(BookingPageLocators.baggagetittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			
			//Selecting seat
			selectSeat(selectSeat, bookingType);
			
			//Click on payment Type
			clickPaymentType(paymentType);
			
			//Click Continue with out Entering Any amount
			clickContinueBtn();
									
			if(isElementDisplayedTemp(BookingPageLocators.MandatePrompt)){
				String ErrorMsg = getText(BookingPageLocators.MandatePrompt, "Error Message");
				if(ErrorMsg.contains(ErrorMessage)){
					Reporter.SuccessReport("Validating EorrorMessage on not entering voucher number", "Successfully verified the error message :"+ ErrorMsg);
					driver.close();
				}else{
					Reporter.failureReport("Validating EorrorMessage on not entering voucher number", "Error Message is not as expected");
					driver.close();
				}
				
			}
			else{
				Reporter.failureReport("Validating EorrorMessage on not entering voucher number", "Error promt not found");
				driver.close();
			}			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC43_ValidateErrOnNotEntrngVoucherNumber", "Failed");
			driver.close();
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    		
	    		xls.getCellValue("username", "Value1"),
	    		xls.getCellValue("password", "Value1"),
	    		xls.getCellValue("Trip Type", "Value1"),
	    		xls.getCellValue("origin", "Value1"),
	    		xls.getCellValue("destination", "Value1"),
	    		xls.getCellValue("Departure Date", "Value1"),
	    		"",
	    		"",
	    		xls.getCellValue("Return Date", "Value1"),
	    		xls.getCellValue("Adults Count", "Value1"),
	    		xls.getCellValue("Child Count", "Value1"),
	    		xls.getCellValue("Infant Count", "Value1"),
	    		xls.getCellValue("promo", "Value1"),
	    		"",
	    		xls.getCellValue("Payment Type", "Value11"),
	    		xls.getCellValue("Booking Class", "Value1"),
	    		xls.getCellValue("Select Seat", "Value1"),
	    		"",
	    		xls.getCellValue("ErrorMessage", "Value11"),
	    		"Validating EorrorMessage On entering Invalid CC details"}};
	}
	

}
