package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.*;
import flynas.web.workflows.*;

public class WebCheckinPage<RenderedWebElement> extends BookingPageLocators {
	
	public void searchFlightCheckin(String referenceNum, String email, String mobile, String lastName) throws Throwable{
		// add validation
		driver.get(chekinurl);
		waitForElementPresent(BookingPageLocators.sfpbookingReference, "Reference Number");
		type(BookingPageLocators.sfpbookingReference, referenceNum, "Reference Number");
		//type(BookingPageLocators.sfpEmail, email, "Email");
		//type(BookingPageLocators.sfpMoblie, mobile, "Mobile");
		type(BookingPageLocators.sfpLastName, lastName, "Last Name");
		click(BookingPageLocators.checkInNow, "Check-in button");
		}
	
	
	public void performCheckin(String SelectSeat,String paymenttype, String strPassenger) throws Throwable
	{
		waitforElement(BookingPageLocators.checkinTitle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
 		List<WebElement> passengers = driver.findElements(BookingPageLocators.passengers_incheckin);
		System.out.println(passengers.size());
		for(int i=0;i<passengers.size();i++)
		{
			passengers.get(i).click();
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.continueBtn));
		click(BookingPageLocators.passengers_checkterms, "CheckBox for agriment");
		projectUtilities.clickContinueBtn();
		waitforElement(BookingPageLocators.emailAdd);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String emailformat = getText(BookingPageLocators.emailAdd, "EmailAddress");
		if(!emailformat.contains("@"))
		{
			type(BookingPageLocators.emailAdd, "flynasqa@gmail.com", "EmailAddress");
		}
		waitforElement(BookingPageLocators.continueBtn);
		projectUtilities.clickContinueBtn();
		waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		projectUtilities.clickContinueBtn();
		waitforElement(BookingPageLocators.selectseattittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.selectseattittle))
		{
			SeatSelectionPage.selectSeat(SelectSeat, "");
		}else
		{
			System.out.println("No Seat Page is Available in CheckIn");
		}
		if(isElementDisplayedTemp(BookingPageLocators.paymentTittle))
		{
			PaymentPage.payment(paymenttype, "");
		}else
		{
			System.out.println("No Payment is Available in CheckIn");
		}
		
	}
	
	public void validateCheckin() throws Throwable
	{
		waitforElement(BookingPageLocators.checkinConformation);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementPresent(BookingPageLocators.checkinConformation,"Check-in Conformation"))
		{
			Reporter.SuccessReport("Validating check in", "Checkin is Done");
		}
		else
		{
			Reporter.failureReport("Validating check in", "Check in is Not Done");
		}
	}

}
