package flynas.web.uat.regression;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC15_EmpLoginOnewayOneAdultStaffStandByChangFlightCancel extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_15");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_15_EmpLoginOnewayOneAdultStaffStandByChangFlightCancel(String BookingClass,String mobilenum,
			String paymentType,String newDate,String departureDate,String origin,String dest,String triptype,String adult,String child,
			String infant,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			String depdat = pickDate(departureDate);
			String[] Credentials = pickCredentials("EmployeeCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			waitForVisibilityOfElement(BookingPageLocators.emplogin_lnk, "Employee Login");	
			click(emplogin_lnk, "Employee Login");
			switchtoChildWindow();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, depdat, "", "", "",adult, child, infant,"","",paymentType);
			selectClassForStaff(BookingClass);
			//Clicking continue button on Passenger details page
			continueOnPassengerDetails();
			
			//Clicking continue button on Baggage details page
			coninueOnBaggage();
			//Clicking continue button on Seat selection page
			continueOnSeatSelection();
			
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			
			String	newdate  = nextDateof(depdat);
			String strPNRChangeDate = changeDate(strPNR, username, mobilenum, lastname, newdate, "","",BookingClass,0);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			System.out.println(strPNRChangeDate);
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			if(strPNRChangeDate.trim().equalsIgnoreCase(strPNR)){
				Reporter.SuccessReport("Change Flight Date", "Flight Date has changed successfully");
			}else{
				Reporter.SuccessReport("Change Flight Date", "Flight Date has NOT changed successfully");
			}
			
			waitforElement(BookingPageLocators.Home);
			click(BookingPageLocators.Home, "Home");
			waitUtilElementhasAttribute(BookingPageLocators.body);
			driver.navigate().refresh();
			waitforElement(BookingPageLocators.bookflighttittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[contains(text(),'"+strPNR+"')]")));
			click(BookingPageLocators.manageMyBookings(strPNR), "ManageMyBookings");
			cancelFlight("All");
			
			updateStatus("IBE_UAT_Reg","TC15_EmpLoginOnewayOneAdultStaffStandByChangFlightCancel","Pass");
			Reporter.SuccessReport("TC15_EmpLoginOnewayOneAdultStaffStandByChangFlightCancel", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC15_EmpLoginOnewayOneAdultStaffStandByChangFlightCancel","Fail");
			Reporter.failureReport("TC15_EmpLoginOnewayOneAdultStaffStandByChangFlightCancel", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    		xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Payment Type", "Value"),
	    		xls.getCellValue("NewDate", "Value"),
	    		xls.getCellValue("Departure date", "Value"),
	    		xls.getCellValue("Origin", "Value"),
	    		xls.getCellValue("Destination", "Value"),
	    		xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		"Validate Employe Login Oneway One Adult StaffStandBy ChangFlight Cancel"}};
	}
}
