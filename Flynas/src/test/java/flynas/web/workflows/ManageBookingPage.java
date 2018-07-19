package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class ManageBookingPage<RenderedWebElement> extends BookingPageLocators {
	
	public void searchFight(String referenceNum, String email, String mobile, String lastName) throws Throwable{
		// add validation
		waitforElement(BookingPageLocators.sfpbookingReference);
		type(BookingPageLocators.sfpbookingReference, referenceNum, "Reference Number");
		//type(BookingPageLocators.sfpEmail, email, "Email");
		//type(BookingPageLocators.sfpMoblie, mobile, "Mobile");
		type(BookingPageLocators.sfpLastName, lastName, "Last Name");
		click(BookingPageLocators.sfpFindBooking, "Find booking");	
		waitforElement(BookingPageLocators.manageMyBookingTittle);
	}
	
	public void payonMMB(String PaymentType) throws Throwable{		
		waitforElement(BookingPageLocators.paynow);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		click(BookingPageLocators.paynow, "Pay Now");
		PaymentPage.selectPaymentType(PaymentType);
		PaymentPage.enterCardDetails("Visa");
		PaymentPage.submit3Dsecurepin();		
	}
	
	public  void cancelFlight(String flightway) throws Throwable
	{
		waitforElement(BookingPageLocators.manageMyBookingTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String priceBeforeChange = getText(BookingPageLocators.Totalprice, "PriceBeforeChange");
		String[] pricebefore = priceBeforeChange.split("\\s");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.summaryCancelFlight));
		click(BookingPageLocators.summaryCancelFlight, "CancelFlight");
		List<WebElement> cancelflights = driver.findElements(BookingPageLocators.selectFlightstoCancel);
		Thread.sleep(1000);
		if(flightway.equalsIgnoreCase("Departing")){
			cancelflights.get(0).click();
		}else if (flightway.equalsIgnoreCase("Returning")){
			cancelflights.get(1).click();
		}else
		for(int i=0;i<cancelflights.size();i++)
		{
			cancelflights.get(i).click();
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		click(BookingPageLocators.cancelflightBtn, "CancelFlight");
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String[] price=null;
		if(isElementPresent(BookingPageLocators.priceBeforeChange)==true){
			String priceBeforChange = getText(BookingPageLocators.priceBeforeChange, "PriceBeforeChange");
			price = priceBeforChange.split("\\s");
			waitforElement(BookingPageLocators.priceBeforeChange);
			}
		click(BookingPageLocators.conformCharges, "Conform Charges");
		if(isElementDisplayedTemp(BookingPageLocators.ok)){
		click(BookingPageLocators.ok, "ok");
		}
		verifyCancellation(flightway,pricebefore[1]);	
		
	}
	
	public  String agentcancelFlight(String flightway) throws Throwable
	{
		waitforElement(BookingPageLocators.manageMyBookingTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.summaryCancelFlight));
		click(BookingPageLocators.summaryCancelFlight, "CancelFlight");
		List<WebElement> cancelflights = driver.findElements(BookingPageLocators.selectFlightstoCancel);
		if(flightway.equalsIgnoreCase("Departing")){
			cancelflights.get(0).click();
		}else if (flightway.equalsIgnoreCase("Return")){
			cancelflights.get(1).click();
		}else
		for(int i=0;i<cancelflights.size();i++)
		{
			cancelflights.get(i).click();
		}
		waitUtilElementhasAttribute(BookingPageLocators.body);
		click(BookingPageLocators.cancelflightBtn, "CancelFlight");
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String priceBeforChange = getText(BookingPageLocators.priceBeforeChange, "PriceBeforeChange");
		String[] price = priceBeforChange.split("\\s");
		waitforElement(BookingPageLocators.priceBeforeChange);
		click(BookingPageLocators.conformCharges, "Conform Charges");
		if(isElementDisplayedTemp(BookingPageLocators.ok)){
		click(BookingPageLocators.ok, "ok");
		}
		//Thread.sleep(5000);
		verifyCancellation(flightway,price[1]);
		
		return price[1];
		
	}
	public void verifyCancellation(String Flightway,String priceBefore) throws Throwable
	{
		if(!Flightway.equalsIgnoreCase("Departing")&&!Flightway.equalsIgnoreCase("Returning")){
		waitforElement(BookingPageLocators.cancelled);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.cancelled)==true){
			Reporter.SuccessReport("Verifing cancellation status", "Successfully Verified Cancel Confirmed");
			}
		else{
			Reporter.failureReport("Verifing cancellation status", "Cancellation not confirmed");
			}
		}else{
			String priceBeforChange = getText(BookingPageLocators.Totalprice, "priceAfterChange");
			String[] priceAfter = priceBeforChange.split("\\s");
			if(!priceBefore.equalsIgnoreCase(priceAfter[1])){
				Reporter.SuccessReport("Verifing cancellation status", "Successfully Verified "+Flightway+" flight cancellation");
				}else{
				Reporter.SuccessReport("Verifing cancellation status", Flightway+" flight cancellation not confirmed");
				}
		}
	}
	
	

}
