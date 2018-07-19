package flynas.web.uat.errorMessages;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC09_Err_4_twoAdultStaffStandByRT extends BookingPageFlow{
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"ErrorMessage_4");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_09_Err_4_twoAdultStaffStandByRT( String tripType,String origin, String destination,
			String deptDate, String retDate,String adult,String child,String infant,String promo, String bookingClass,
			String flightType,String totalpsngrs,String nationality,String docType,String docNumber,String naSmiles,String mobile,
			String email ,String selectSeat,String paymentType,String bookingType,String charity,String currency, String payment2 ,
			String ErrorMessage, String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String depdat = pickDate(deptDate);
			String rtndate = pickDate(retDate);
		
			String[] Credentials = pickCredentials("EmployeeCreds");
			String username =Credentials[0];
			String password =Credentials[1];
			String lastname=Credentials[3];
			 
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			click(emplogin_lnk, "Employe Login");
			switchtoChildWindow();
			login(username,password);
			inputBookingDetails(tripType,origin, destination, depdat, "", "", rtndate,adult, child, infant,"","",paymentType);
			selectClassForStaff(bookingClass);
			selectPassenger();
			clickContinueBtn();
			payment(paymentType, "");
			String PNR = getReferenceNumber().trim();
			searchFlightCheckin(PNR, "", "", lastname);
			if(isElementDisplayedTemp(BookingPageLocators.ErrorMsg1)){
				String ErrorMsg = getText(BookingPageLocators.ErrorMsg1, "Error Message");
				if(ErrorMsg.contains("Your fare class is not eligible for online check-in. Please check-in at the airport.")){
					Reporter.SuccessReport("Validating Error Message For WCI", "Successfully Verified:"+ ErrorMsg);
				}else{
					Reporter.failureReport("Validating Erroe Message For WCI", "Expected Error Message not Came");
				}
				
			}					
		
			Reporter.SuccessReport("TC09_Err_4_twoAdultStaffStandByRT", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC09_Err_4_twoAdultStaffStandByRT", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {	
	    return (Object[][]) new Object[][] { 
	    {
	    		xls.getCellValue("Employeemail", "Value"),xls.getCellValue("Password", "Value"),xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("mobile", "Value"),xls.getCellValue("Payment Type", "Value"),xls.getCellValue("NewDate", "Value"),
	    		xls.getCellValue("Departure date", "Value"),xls.getCellValue("origin", "Value"),xls.getCellValue("destination", "Value"),
	    		xls.getCellValue("Return Date", "Value"),xls.getCellValue("Trip Type", "Value"),xls.getCellValue("Adults Count", "Value2"),
	    		xls.getCellValue("Child Count", "Value"),xls.getCellValue("Infant Count", "Value"),xls.getCellValue("Flight Type", "Value"),
	    		xls.getCellValue("Total Passenger", "Value"),xls.getCellValue("nationality", "Value"),xls.getCellValue("Doc Number", "Value"),
	    		"Validating Error Message for Conform Booking 2 Adult StaffStandby  RoundTrip"
	    }};
	}
}
