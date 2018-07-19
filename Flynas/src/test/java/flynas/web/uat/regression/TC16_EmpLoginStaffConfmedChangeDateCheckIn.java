package flynas.web.uat.regression;

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

public class TC16_EmpLoginStaffConfmedChangeDateCheckIn extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_16");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_16_EmpLoginStaffConfmedChangeDateCheckIn(String BookingClass,
			String mobilenum,
			String paymentType,
			String newDate,
			String departurDate,String rtndate,
			String origin,
			String dest,String triptype,String adult,String child,String infant,String selectseat,
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String 	deptdate = pickDate(departurDate);
			String 	rtrndate = pickDate(rtndate);
			
			
			String[] Credentials = pickCredentials("EmployeeCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname =Credentials[3];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.emplogin_lnk, "Employee Login");	
			click(emplogin_lnk, "Employee Login");
			switchtoChildWindow();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptdate, "", "", rtrndate,adult, child, infant,"","",paymentType);
			selectClassForStaff(BookingClass);
			continueOnPassengerDetails();
			coninueOnBaggage();
			continueOnSeatSelection();
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			
			String newdate = nextDateof(deptdate);
			String strPNRChangeDate = changeDate("G3R1VY", username, mobilenum,lastname, newdate, selectseat,"",BookingClass,0);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			System.out.println(strPNRChangeDate);
			if(strPNRChangeDate.trim().equalsIgnoreCase("G3R1VY")){
				Reporter.SuccessReport("Change Flight Date", "Flight Date has changed successfully");
			}else{
				Reporter.SuccessReport("Change Flight Date", "Flight Date has NOT changed successfully");
			}
			searchFlightCheckin("G3R1VY", username, "", lastname);
			performCheckin("","","");
			validateCheckin();
			
			updateStatus("IBE_UAT_Reg","TC16_EmpLoginStaffConfmedChangeDateCheckIn","Pass");
			Reporter.SuccessReport("TC16_EmpLoginStaffConfmedChangeDateCheckIn", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC16_EmpLoginStaffConfmedChangeDateCheckIn","Fail");
			Reporter.failureReport("TC16_EmpLoginStaffConfmedChangeDateCheckIn", "Failed");
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
	    		xls.getCellValue("Departure Date", "Value"),
	    		xls.getCellValue("Return Date", "Value"),
	    		xls.getCellValue("Origin", "Value"),
	    		xls.getCellValue("Destination", "Value"),
	    		xls.getCellValue("Trip Type", "Value"),
	    		xls.getCellValue("Adults Count", "Value"),
	    		xls.getCellValue("Child Count", "Value"),
	    		xls.getCellValue("Infant Count", "Value"),
	    		xls.getCellValue("Select Seat", "Value"),
	    		"Validate Employe Login Round Trip One Adult StaffConformed ChangFlight CheckIN"}};
	}

}
