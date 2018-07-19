package flynas.web.uat.regression;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC37_EmpLoginStaffConfirmedModifyExtras extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_16");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_37_EmpLoginStaffConfirmedModifyExtras(String BookingClass, String bundle,String mobilenum,
			String paymentType,String newDate,String pickDate,String rtndate,String origin,String dest,String triptype,
			String adult,String child,String infant,String selectseat,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String 	deptdate = pickDate(pickDate);
			String 	rtrndate = pickDate(rtndate);
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
			inputBookingDetails(triptype,origin, dest, deptdate, "", "", rtrndate,adult, child, infant,"","",paymentType);
			selectClassForStaff(BookingClass);
			waitforElement(BookingPageLocators.mobileNum);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			continueOnPassengerDetails();
			coninueOnBaggage();
			continueOnSeatSelection();
			payment(paymentType, "");
			String strpnr = getReferenceNumber().trim();
			searchFlight(strpnr, username, "",lastname);
			modifyExtras();
			Baggage_Extra(triptype);
			addSportsEqpmnt(triptype);
			Select_A_Meal();
			Select_lounge();
			inputExtras("12");
			waitforElement(BookingPageLocators.manageMyBookingTittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.modifySeat, "Seat Selection");
			waitforElement(BookingPageLocators.selectseattittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)){
				selectallSeats(selectseat, "1", triptype);
			}else{
				System.out.println("No seat Select Page");
			}
			waitforElement(BookingPageLocators.paynow);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.paynow, "Pay Now");
			payment(paymentType, "");
			String strPNR = getReferenceNumber().trim();
			validate_ticketStatus(strPNR);
			
			updateStatus("IBE_UAT_Reg","TC37_EmpLoginStaffConfirmedModifyExtras","Pass");
			Reporter.SuccessReport("TC37_EmpLoginStaffConfirmedModifyExtras", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC37_EmpLoginStaffConfirmedModifyExtras","Fail");
			Reporter.failureReport("TC37_EmpLoginStaffConfirmedModifyExtras", "Failed");
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
	    		xls.getCellValue("NewDate", "Value2"),
	    		xls.getCellValue("Departure Date", "Value2"),
	    		xls.getCellValue("Return Date", "Value2"),
	    		xls.getCellValue("Origin", "Value2"),
	    		xls.getCellValue("Destination", "Value2"),
	    		xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		"Validate Employe Login Round Trip One Adult StaffConfirmed Modify Extras"}};
	}

}
