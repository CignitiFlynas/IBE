package flynas.web.uat.errorMessages;

import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC36_ValidatingErrOnMMBWithBlnkFields extends BookingPageFlow {
	
	@Test
	public  void ValidatingErrOnMMBWithBlnkFields () throws Throwable {
		try {
			
			String Description = "Validating Error On MMB With Blnk Fields";
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			click(BookingPageLocators.Manage_booking_lnk, "Manage Booking link");
			switchtoChildWindow();
			
			waitforElement(BookingPageLocators.btnFindBooking);
			
			boolean flag = isElementClickable(BookingPageLocators.btnFindBooking, "Find Booking");
			
			if(flag){	
				Reporter.failureReport("Validating Error On MMB With Blnk Fields", "Find booking button is enabled");					
				driver.close();
				}
			else{
				Reporter.SuccessReport("Validating Error On MMB With Blnk Fields", "Find booking button is disabled");
				driver.close();
				}
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC35_ValidatingEorrorMessageWithIncorrectPNRCorrectemailID", "Failed");
			driver.close();
			}
	}

}
