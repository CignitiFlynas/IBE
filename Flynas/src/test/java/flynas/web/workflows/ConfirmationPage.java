package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class ConfirmationPage<RenderedWebElement> extends BookingPageLocators {
	
	
	public String getReferenceNumber() throws Throwable{
		waitforElement(BookingPageLocators.summaryRefNumber);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		return getText(BookingPageLocators.summaryRefNumber, "Reference Number");		
		
	}
	
	public void closetoast() throws Throwable{		
	try{
		//driver.switchTo().activeElement();
		//String handle = driver.getWindowHandle();
		List<WebElement> elements = driver.findElements(By.tagName("iframe"));
		System.out.println("No of iframes : "+ elements.size());
	  	if(isElementPresent(BookingPageLocators.naSmileTaost)==true){
	  		driver.switchTo().frame("yief130002");
			System.out.println("nasmile Toast appeared");
			click(BookingPageLocators.closetoast, "nasmile Toast close button");
			}
		else{
			System.out.println("No nasmile Toast");
			}
		//driver.switchTo().window(handle);
		}catch (Exception e){
		System.out.println("No nasmile Toast");
		}
	}
	
	public void validate_ticketStatus(String pnr) throws Throwable
	{
		waitforElement(BookingPageLocators.summaryStatus);
		//waitUtilElementhasAttribute(BookingPageLocators.body);
		if(getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Confirmed")
				||getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Pending")
				||getText(BookingPageLocators.summaryStatus,"Status").trim().equals("Hold"))
		{
			String env = driver.getCurrentUrl();
			if(env.contains("develop_r41")){projectUtilities.writingPNR("IBE_NAV_PNR",pnr);}
			else if(env.contains("uat")){projectUtilities.writingPNR("IBE_UAT_PNR",pnr);}
			else{projectUtilities.writingPNR("IBE_PROD_PNR",pnr);}
			
			Reporter.SuccessReport("Ticket Confirmation", "Ticket has booked with PNR "+pnr);			
		}
		else
		{	
			Reporter.failureReport("Ticket Confirmation", "Ticket has not booked");
			
		}
		closetoast();
	}
	
	public void navigatetommb(){
		//Need to modify to click on modify button once the the redirection is fixed 
		driver.get(mmburl);
	}
	
	public void navigatetowci(){
		//Need to modify to click on modify button once the the redirection is fixed 
		driver.get(chekinurl);
	}

}
