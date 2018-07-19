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

public class TC19_agencyLoginRToneAdultoneChild extends BookingPageFlow {
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_19");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_19_agencyLoginRToneAdultoneChild(String BookingClass, String bundle,String mobilenum,String paymentType,
			String newDate,String departurDate,String rtnDate,String origin,String dest,String triptype,String adult,String child,String infant,
			String totalPass,String smiles,String nationality,String doctype,String docNum,String emailId,String domOrInt,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			String	deptdate = pickDate(departurDate);
			String 	rtrndate = pickDate(rtnDate);
			
			String[] Credentials = pickCredentials("AgentCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.agency_lnk, "Agency Login");	
			click(BookingPageLocators.agency_lnk, "Agency Login");
			switchtoChildWindow();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptdate, "", "", rtrndate,adult, child, infant,"","","");
			selectClass(BookingClass, bundle); 
			clickContinueBtn();
			upSellPopUpAction("Continue");
			inputPassengerDetails(domOrInt, totalPass, nationality, doctype, docNum, "", mobilenum, emailId, "", "", "");
			coninueOnBaggage();
			continueOnSeatSelection();
			payment(paymentType, "");
			String strPNR = getReferenceNumber().trim();
			validate_ticketStatus(strPNR);
			click(BookingPageLocators.passenger_header, "Passenger");
			waitUtilElementhasAttribute(BookingPageLocators.body);
			driver.navigate().refresh();
			waitUtilElementhasAttribute(BookingPageLocators.body);
			String AccountBalanceold = getText(BookingPageLocators.accountBalance, "Account Balance");
			String[] acctbalold = AccountBalanceold.split("SAR");
			String acountbal = acctbalold[1].trim();
			waitUtilElementhasAttribute(BookingPageLocators.body);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//td[contains(text(),'"+strPNR+"')]")));
			click(BookingPageLocators.manageBookings_agentportal(strPNR), "manageMyBookings");
			String priceBeforChange =agentcancelFlight("All");
			waitforElement(BookingPageLocators.Home);
			waitUtilElementhasAttribute(BookingPageLocators.body);
 			click(BookingPageLocators.Home, "Home");
 			waitUtilElementhasAttribute(BookingPageLocators.body);
			driver.navigate().refresh();
			waitUtilElementhasAttribute(BookingPageLocators.body);		
			String AccountBalancenew = getText(BookingPageLocators.accountBalance, "Account Balance");
			String[] acctbalnew = AccountBalancenew.split("SAR");
			String newactbal = acctbalnew[1].trim();
			String newbalance = newactbal.replaceAll("\\,", "");
			
			System.out.println(acountbal);
		
			double balance = Double.parseDouble(acountbal.replaceAll("\\,", ""))+Double.parseDouble(priceBeforChange.trim().replaceAll("\\,",""));
			String Balance = Double.toString(balance);
			
			if(newbalance.contains(Balance))
			{
				Reporter.SuccessReport("Verifing Account Balance Changed or not After Cancel Flight", "Account balance increased");
			}
			else
			{
				Reporter.failureReport("Verifing Account Balance Changed or not After Cancel Flight", "Account balance not Changed");
			}
			
			updateStatus("IBE_UAT_Reg","TC19_agencyLoginRToneAdultoneChild","Pass");
			Reporter.SuccessReport("TC19_agencyLoginRToneAdultoneChild", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC19_agencyLoginRToneAdultoneChild","Fail");
			Reporter.SuccessReport("TC19_agencyLoginRToneAdultoneChild", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    	
	    		xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Bundle", "Value2"),
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
	    		xls.getCellValue("na Smiles", ""),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		xls.getCellValue("Email Address", "Value"),
	    		xls.getCellValue("Flight Type", "Value"),
	    		"Validate Agency Login RoundTrip One Adult One Child Cancel Flight"}};
	}
}
