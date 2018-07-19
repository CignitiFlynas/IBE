package flynas.web.uat.errorMessages;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC31_validatingErrorMsgOnPnrRetrievalwithWrongEmialId extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"Errors_On_PnrRetrieval");

	@DataProvider(name="bookingdata")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 	
	    		{	    		
	    		xls.getCellValue("username", "Value1"),
    			xls.getCellValue("password", "Value1"),
	    		xls.getCellValue("Trip Type", "Value1"),
		    	xls.getCellValue("origin", "Value1"),
		    	xls.getCellValue("destination", "Value1"),	
		    	xls.getCellValue("Departure Date", "Value1"),
		    	xls.getCellValue("Return Date", "Value1"),
		    	xls.getCellValue("Booking Class", "Value1"),
		    	xls.getCellValue("Adults Count", "Value1"),
		    	xls.getCellValue("Child Count", "Value1"),
		    	xls.getCellValue("Infant Count", "Value1"),
		    	xls.getCellValue("promo", "Value1"),
		    	"",
		    	xls.getCellValue("Payment Type", "Value1"),
		    	xls.getCellValue("Select Seat", "Value1"),    					
    			"",    			
	    		}
	    	};
	}
	
	//GeneratString PNR For TC31,TC32,TC33,TC34,TC36
	@Test (priority = 1,dataProvider = "bookingdata",groups={"Chrome"})
	public  void GeneratePNR( String username, String password, String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage) throws Throwable {
		try {
			
			String Description = "GeneratString PNR For PNR Retrieval Negetive scenarios";			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);		
							
			click(BookingPageLocators.login_lnk, "Login");
					
			login(username,password);
			
			deptDate = pickDate(deptDate);
			retDate = pickDate(retDate);
			
			inputBookingDetails(tripType, origin, destination, deptDate,"", "", retDate,
					adult, child, infant, promo,currency,paymentType);
			
			selectClass(bookingClass, tripType);
			clickContinueBtn();
			
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			
			//Clicking continue button on Baggage details page
			coninueOnBaggage();
			
			//Selecting seat
			selectSeat(selectSeat, bookingType);
			//Payment
			payment(paymentType,"");
			
			//CapturString PNR number
			String PNR = getReferenceNumber().trim();
			validate_ticketStatus(PNR);
			
			xls.setCellData("Errors_On_PnrRetrieval", "Value1", 22, PNR);
			
			if(PNR!=null)
			{
				Reporter.SuccessReport("PNR Generation", "PNR Generated successfully");
				driver.close();
				}
			else
			{
				Reporter.failureReport("PNR Generation", "PNR Generation failed");
				driver.close();
				}		
					
		}
		catch(Exception e){
				e.printStackTrace();
				Reporter.failureReport("TC_31_beforeClassToGeneratePNR", "Fail");
				driver.close();
			}	
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata2() {
	    return (Object[][]) new Object[][] { 
	    		{    			
    			xls.getCellValue("PNR", "Value1"),	    		
	    		"flynasqa@gmail.com",
	    		xls.getCellValue("ErrorMessage", "Value1"),
	    		}};
	}
		
	@Test(priority = 2,dataProvider = "testData",groups={"Chrome"})
	public  void validatingErrorMsgOnPnrRetrievalwithWrongEmialId (String PNR, String email,String ErrorMessage ) throws Throwable {
		try {
				
			String Description = "validatString ErrorMsg On PnrRetrieval with Wrong Emial Id";			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			navigateToMMB();
			
			//click(BookingPageLocators.Manage_booking_lnk, "Manage Booking link");
			//switchtoChildWindow();
			
			
			waitforElement(BookingPageLocators.pnrinput);
			type(BookingPageLocators.pnrinput, PNR, "PNR");
			type(BookingPageLocators.emailinput, email, "email");
			click(BookingPageLocators.btnFindBooking, "Find Booking");		
			
			
			if(isElementDisplayedTemp(BookingPageLocators.ErrorMsg1))
			{
				String ErrorMsg = getText(BookingPageLocators.ErrorMsg1, "Error Message");
				if(ErrorMsg.contains(ErrorMessage)){
					Reporter.SuccessReport("Validating Error Message on Pnr search with wrong email", "Successfully verified the error message :"+ ErrorMsg);
					driver.close();
				}
			else{
					Reporter.failureReport("Validating Error Message on Pnr search with wrong email", "Error Message is not as expected");
					driver.close();
				}
				
			}
			else
			{
				Reporter.failureReport("Validating Error Message on Pnr search with wrong email", "Error Promt not found");
				driver.close();
			}
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC31_validatingErrorMsgOnPnrRetrievalwithWrongEmialId", "Failed");
			driver.close();
		}
	}
	
	
	
	

}