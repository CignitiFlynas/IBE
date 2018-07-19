package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC15_Err_6_ConformBooking1Adt1OW extends BookingPageFlow{
ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"ErrorMessage_6");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public void TC_15_Err_6_ConformBooking1Adt1OW( String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage, String Description)throws Throwable{
				try{
					
					
					TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
					deptDate = pickDate(deptDate);
				
					inputBookingDetails(tripType, origin, destination, deptDate,"","", retDate,
							adult, child, infant, promo,currency,paymentType);
					selectClass(bookingClass, tripType);
					clickContinueBtn();
					inputPassengerDetails(flightType, totalpsngrs, nationality, docType, 
							docNumber, naSmiles, mobile, email,"","","");
					waitforElement(BookingPageLocators.baggagetittle);
					waitUtilElementhasAttribute(BookingPageLocators.body);
					if(isElementDisplayedTemp(BookingPageLocators.baggagetittle)){
					clickContinueBtn();
						}else{
							System.out.println("No Baggage Page");
						}
					waitForElementPresent(BookingPageLocators.selectseattittle, "Select Seat Tittle");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)==true){
					click(BookingPageLocators.continueBtn, "Contiue");
						if(isElementDisplayedTemp(BookingPageLocators.ok)){
							click(BookingPageLocators.ok, "OK");
						}
					}
					payment(paymentType,"");
					String PNR = getReferenceNumber();
							
					searchFlightCheckin(PNR, email, "", "");
					if(isElementDisplayedTemp(BookingPageLocators.ErrorMsg1)){
						String ErrorMsg = getText(BookingPageLocators.ErrorMsg1, "Error Message");
						if(ErrorMsg.contains("Online check-in is not available on this route. Please check-in at the airport.")){
							Reporter.SuccessReport("Validating Error Message For WCI", "Successfully Verified:"+ ErrorMsg);
						}else{
							Reporter.failureReport("Validating Erroe Message For WCI", "Expected Error Message not Came");
						}
						
					}	
								
					Reporter.SuccessReport("TC15_Err_6_ConformBooking1Adt1OW", "Pass");
					
					
			
					}catch(Exception e){
						e.printStackTrace();
						Reporter.failureReport("TC15_Err_6_ConformBooking1Adt1OW", "Fail");
						
					}
	}
		
		@DataProvider(name="testData")
		public Object[][] createdata1() {
		    return (Object[][]) new Object[][] {{
		    	xls.getCellValue("Trip Type", "Value2"),
		    	xls.getCellValue("Flight Type", "Value"),
		    	xls.getCellValue("origin", "Value"),
		    	xls.getCellValue("destination", "Value"),
		    	xls.getCellValue("Departure Date", "Value"),"","",
		    	xls.getCellValue("Return Date", "Value"),
		    	xls.getCellValue("total pass", "Value"),
		    	xls.getCellValue("Adults Count", "Value"),
		    	xls.getCellValue("Child Count", "Value"),
		    	xls.getCellValue("Infant Count", "Value"),
		    	xls.getCellValue("promo", "Value"),
		    	xls.getCellValue("Booking Class", "Value"),
		    	xls.getCellValue("nationality", "Value"),
		    	xls.getCellValue("Document Type", "Value"),
		    	xls.getCellValue("Doc Number", "Value"),
		    	"1234567890",
    			xls.getCellValue("mobile", "Value"),
    			xls.getCellValue("email Address", "Value"),
    			xls.getCellValue("Select Seat", "Value"),
    			"Credit Card","",
    			xls.getCellValue("New Date", "Value"),
    			xls.getCellValue("Charity Donation", "Value"),"",
    			"Validating Error Message for Conform Booking 1 Adult OW"}};
	}

}
