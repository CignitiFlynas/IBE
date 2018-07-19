package flynas.web.uat.regression;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC39_agencyLoginRondTripModifyExtras extends BookingPageFlow {
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_19");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_39_agencyLoginRondTripModifyExtras( String BookingClass, String bundle,String mobilenum,
			String paymentType,String newDate,String pickDate,String rtnDate,String origin,String dest,String triptype,String adult,
			String child,String infant,String totalPass,String smiles,String nationality,String doctype,String docNum,String emailId,
			String domOrInt,String seatSelect,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			String	deptdate = pickDate(pickDate);
			String	rtrnDate = pickDate(rtnDate);
			String[] Credentials = pickCredentials("AgentCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.agency_lnk, "Agency Login");	
			click(BookingPageLocators.agency_lnk, "Agency Login");
			switchtoChildWindow();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptdate, "", "", rtrnDate,adult, child, infant,"","","");
			selectClass(BookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			String[] lastname =inputPassengerDetails(domOrInt, totalPass, nationality, doctype, docNum, smiles, mobilenum, emailId, "", "", "");
			coninueOnBaggage();
			continueOnSeatSelection();
			payment(paymentType, "");
			String strPNR = getReferenceNumber().trim();
			validate_ticketStatus(strPNR);
			searchFlight(strPNR, username, "",lastname[1]);
			modifyExtras();
			Baggage_Extra(triptype);
			addSportsEqpmnt(triptype);
			//Select_A_Meal();
			Select_lounge();
			inputExtras("12");
			clickModifySeats();
			selectallSeats(seatSelect, "1", triptype);			
			clickPayNowBtn();
			payment(paymentType, "");
			getReferenceNumber();
						
			updateStatus("IBE_UAT_Reg","TC39_agencyLoginRondTripModifyExtras","Pass");
			Reporter.SuccessReport("TC39_agencyLoginRondTripModifyExtras", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC39_agencyLoginRondTripModifyExtras","Fail");
			Reporter.SuccessReport("TC39_agencyLoginRondTripModifyExtras", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    	
	    		xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Bundle", "Value"),
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Payment Type", "Value"),
	    		xls.getCellValue("NewDate", "Value"),
	    		xls.getCellValue("Departure Date", "Value"),
	    		xls.getCellValue("Return Date", "Value"),
	    		xls.getCellValue("Origin", "Value"),
	    		xls.getCellValue("Destination", "Value"),
	    		xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),
	    		xls.getCellValue("na Smiles", "Value"),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		xls.getCellValue("Email Address", "Value"),
	    		xls.getCellValue("Flight Type", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		"Validate Agency Login RondTrip ModifyExtras"}};
	}
}
