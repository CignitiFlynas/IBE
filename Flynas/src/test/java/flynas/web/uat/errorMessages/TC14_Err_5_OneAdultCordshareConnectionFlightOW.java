package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC14_Err_5_OneAdultCordshareConnectionFlightOW extends BookingPageFlow{

	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"ErrorMessage_5");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_14_Err_5_OneAdultCordshareConnectionFlightOW ( String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage, String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			deptDate = pickDate(deptDate);
			
			inputBookingDetails(tripType, origin, destination, deptDate,"", "",retDate,adult, child, infant,promo,currency,paymentType);
			selectCodeshareConectflight(bookingClass);
			inputPassengerDetails(flightType,totalpsngrs,nationality,docType,docNumber, naSmiles,mobile,email,"","","");
			if(isElementDisplayedTemp(BookingPageLocators.baggagetittle)==true){
				clickContinueBtn();
			}else{
				System.out.println("No Baggage Page");
			}
			if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)==true){
				clickContinueBtn();
				if(isElementDisplayedTemp(BookingPageLocators.ok)){
					click(BookingPageLocators.ok, "OK");
				}
			}else{
				System.out.println("No Seat Page");
			}
			payment(paymentType,"");
			String strpnr = getReferenceNumber().trim();
			searchFlightCheckin(strpnr, email, "", "");
			if(isElementDisplayedTemp(BookingPageLocators.ErrorMsg1)){
				String ErrorMsg = getText(BookingPageLocators.ErrorMsg1, "Error Message");
				if(ErrorMsg.contains("This flight is not eligible for online check-in. Please check-in at the airport")){
					Reporter.SuccessReport("Validating Error Message For WCI", "Successfully Verified:"+ ErrorMsg);
				}else{
					Reporter.failureReport("Validating Erroe Message For WCI", "Expected Error Message not Came");
				}
				
			}	
			
			Reporter.SuccessReport("TC14_Err_5_OneAdultCordshareConnectionFlightOW", "Passed");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC14_Err_5_OneAdultCordshareConnectionFlightOW", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{xls.getCellValue("Trip Type", "Value2"),
	    		xls.getCellValue("origin", "Value"),xls.getCellValue("destination", "Value"),xls.getCellValue("Departure Date", "Value"),
	    		xls.getCellValue("Return Date", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),xls.getCellValue("Child Count", "Value"),xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("promo", "Value"),xls.getCellValue("Flight Type", "Value"),xls.getCellValue("total pass", "Value"),
	    		xls.getCellValue("nationality", "Value"),xls.getCellValue("Document Type", "Value"),xls.getCellValue("Doc Number", "Value"),
	    		xls.getCellValue("na Smiles", "Value"),xls.getCellValue("mobile", "Value"),xls.getCellValue("email Address", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),"Credit Card",xls.getCellValue("Booking type", "Value"),
	    		xls.getCellValue("Booking Class", "Value"),"",
	    		"Validate Error Message For One Adult Cordshare OW Connection Flight CheckIn" }
	    				
	    
	    };
	}
	

}
