package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC19_ConformBooking1Adt1ChildRMT extends BookingPageFlow{
ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"ErrorMessage_6");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public void TC_19_ConformBooking1Adt1ChildRMT( String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage, String Description)throws Throwable{
				try{
					
					
					TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
					String depdate = pickDate(deptDate);
					retDate = pickDate(retDate);
				
					
					
					inputBookingDetails(tripType, origin, destination, depdate,"","", retDate,
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
					selectallSeats(selectSeat, "2", "");
					payment(paymentType,"");
					String PNR = getReferenceNumber();
							
					searchFlightCheckin(PNR, email, "", "");
					performCheckin("", "", "");
					validateCheckin();
								
					Reporter.SuccessReport("TC19_ConformBooking1Adt1ChildRMT", "Pass");
					
					
			
					}catch(Exception e){
						e.printStackTrace();
						Reporter.failureReport("TC19_ConformBooking1Adt1ChildRMT", "Fail");
						
					}
	}
		
		@DataProvider(name="testData")
		public Object[][] createdata1() {
		    return (Object[][]) new Object[][] {{
		    	xls.getCellValue("Trip Type", "Value"),
		    	xls.getCellValue("Flight Type", "Value3"),
		    	xls.getCellValue("origin", "Value3"),
		    	xls.getCellValue("destination", "Value3"),
		    	xls.getCellValue("Departure Date", "Value2"),"","",
		    	xls.getCellValue("Return Date", "Value2"),
		    	xls.getCellValue("total pass", "Value2"),
		    	xls.getCellValue("Adults Count", "Value"),
		    	xls.getCellValue("Child Count", "Value2"),
		    	xls.getCellValue("Infant Count", "Value"),
		    	xls.getCellValue("promo", "Value"),
		    	xls.getCellValue("Booking class", "Value"),
		    	xls.getCellValue("nationality", "Value"),
		    	xls.getCellValue("Document Type", "Value3"),
		    	xls.getCellValue("Doc Number", "Value"),
		    	"1234567890",
    			xls.getCellValue("mobile", "Value"),
    			xls.getCellValue("email Address", "Value"),
    			xls.getCellValue("Select Seat", "Value"),
    			"Credit Card","",
    			xls.getCellValue("New Date", "Value"),
    			xls.getCellValue("Charity Donation", "Value"),"",
    			"Validating Error Message for Conform Booking 1 Adult 1 Child Multi City"}};
	}

}
