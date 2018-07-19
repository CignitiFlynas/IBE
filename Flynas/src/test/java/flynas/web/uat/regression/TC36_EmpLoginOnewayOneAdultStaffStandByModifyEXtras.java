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

public class TC36_EmpLoginOnewayOneAdultStaffStandByModifyEXtras extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_15");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_36_EmpLoginOnewayOneAdultStaffStandByModifyEXtras(String BookingClass, String bundle,String mobilenum,
			String paymentType,String newDate,String pickDate,String origin,String dest,String triptype,String adult,String child,
			String infant,String strSelectSeat,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
		
			String depdat = pickDate(pickDate);
			
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
			continueOnPassengerDetails();
			coninueOnBaggage();
			continueOnSeatSelection();
			payment(paymentType, "");
			String strpnr = getReferenceNumber().trim();
			searchFlight(strpnr, username, "",lastname);
			modifyExtras();
			Baggage_Extra(triptype);
			addSportsEqpmnt(triptype);
			//Select_A_Meal();
			Select_lounge();
			inputExtras("12");
			clickModifySeats();
			if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)){
				selectallSeats(strSelectSeat, "1", triptype);
			}else{
				System.out.println("No seat Select Page");
			}
			waitforElement(BookingPageLocators.paynow);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.paynow, "Pay Now");
			payment(paymentType, "");
			String strPNR = getReferenceNumber().trim();
			validate_ticketStatus(strPNR);
		
			updateStatus("IBE_UAT_Reg","TC36_EmpLoginOnewayOneAdultStaffStandByModifyEXtras","Pass");
			Reporter.SuccessReport("TC36_EmpLoginOnewayOneAdultStaffStandByModifyEXtras", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC36_EmpLoginOnewayOneAdultStaffStandByModifyEXtras","Fail");
			Reporter.failureReport("TC36_EmpLoginOnewayOneAdultStaffStandByModifyEXtras", "Failed");
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
	    		xls.getCellValue("Departure date", "Value"),
	    		xls.getCellValue("Origin", "Value"),
	    		xls.getCellValue("Destination", "Value"),
	    		xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		"Validate Employe Login Oneway One Adult StaffStandBy Modify Extras"}};
	}
}
