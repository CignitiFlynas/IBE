package flynas.web.uat.errorMessages;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC35_ValidatingEorrorMessageWithIncorrectPNRCorrectEmailID extends BookingPageFlow {
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"Errors_On_PnrRetrieval");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void ValidatingEorrorMessageWithIncorrectPnrValidemail ( String PNR, String email, String ErrorMessage, String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			click(BookingPageLocators.Manage_booking_lnk, "Manage Booking link");
			switchtoChildWindow();
			
			waitforElement(BookingPageLocators.pnrinput);
			type(BookingPageLocators.pnrinput, PNR, "PNR");
			type(BookingPageLocators.emailinput, email, "Last name");
			click(BookingPageLocators.btnFindBooking, "Find Booking");		
			
			
			if(isElementDisplayedTemp(BookingPageLocators.ErrorMsg1))
			{
				String ErrorMsg = getText(BookingPageLocators.ErrorMsg1, "Error Message");
				if(ErrorMsg.contains(ErrorMessage))
				{
					Reporter.SuccessReport("Validating EorrorMessage With Incorrect Pnr Valid email", "Successfully verified the error message :"+ ErrorMsg);
					driver.close();
				}
				else
				{
					Reporter.failureReport("Validating EorrorMessage With Incorrect Pnr Valid email", "Error Message is not as expected");
					driver.close();
				}				
			}
			else
			{
				Reporter.failureReport("Validating EorrorMessage With Incorrect Pnr Valid email", "Error Prompt not found");
				driver.close();
			}	
			
		}
		
	catch (Exception e)
		{
			e.printStackTrace();
			Reporter.failureReport("TC35_ValidatingEorrorMessageWithIncorrectPNRCorrectemailID", "Failed");
			driver.close();
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    		xls.getCellValue("PNR", "Value5"),	    		
	    		xls.getCellValue("username", "Value1"),
	    		xls.getCellValue("ErrorMessage", "Value1"),
	    		"Validating Error Message on Pnr search with Wrong PNR Valid email"}};
	}

}
