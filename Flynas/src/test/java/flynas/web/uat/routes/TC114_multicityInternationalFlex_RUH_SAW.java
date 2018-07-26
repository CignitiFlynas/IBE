package flynas.web.uat.routes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPage;
import flynas.web.workflows.BookingPageFlow;

public class TC114_multicityInternationalFlex_RUH_SAW extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUATRoutes"),"AllRoutes");

	@Test(dataProvider = "testData",groups={"Flex"})
	public  void TC_114_multicityInternationalFlex_RUH_SAW( String bookingClass, String bundle,
			String mobilenum,
			String paymentType,
			String newDate,
			String depDate,String rtnDate,
			String origin,
			String dest,String triptype,String adult,String child,String infant,String seatSelect,
			String Description) throws Throwable {
		try {

			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];			
			String deptDate = pickDate(depDate);
			String rtrnDate = pickDate(rtnDate);
			

			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);


			//click(BookingPageLocators.login_lnk, "Login");
			//click on login
			BookingPage.clickLogin();
			login(username,password);
			inputBookingDetails(triptype,origin, dest, deptDate , "", "", rtrnDate,adult, child, infant,"","","");
			selectClass(bookingClass, bundle); 
			clickContinueBtn();
			upSellPopUpAction("Continue");
			continueOnPassengerDetails();
			
			waitforElement(BookingPageLocators.baggagetittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			chooseInsurance("Add");
			waitforElement(BookingPageLocators.selectseattittle);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			clickContinueBtn();
			if(isElementDisplayedTemp(BookingPageLocators.ok)){
				click(BookingPageLocators.ok, "OK");
			}
			payment(paymentType, "");
			String strpnr = getReferenceNumber();
			String strPNR = strpnr.trim();
			System.out.println(strPNR);
			validate_ticketStatus(strPNR);

			String newdate = pickDate(newDate);
			String strPNRChangeDate = changeDate(strPNR, username, mobilenum, "", newdate, seatSelect,"",bookingClass,0);

			waitUtilElementhasAttribute(BookingPageLocators.body);
			if(strPNRChangeDate.trim().equalsIgnoreCase(strPNR)){
				Reporter.SuccessReport("Change Flight Date", "Flight Date has changed successfully");
			}else{
				Reporter.failureReport("Change Flight Date", "Flight Date has NOT changed successfully");
			}

			Reporter.SuccessReport("TC114_multicityInternationalFlex_RUH_SAW", "Pass");

		}

		catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("TC114_multicityInternationalFlex_RUH_SAW", "Failed");
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
				xls.getCellValue("Origin", "Value52"),
				xls.getCellValue("Destination", "Value52"),
				xls.getCellValue("Trip Type", "Value3"),
				xls.getCellValue("Adults Count", "Value"),
				xls.getCellValue("Child Count", "Value"),
				xls.getCellValue("Infant Count", "Value"),
				xls.getCellValue("Select Seat", "Value"),
			"Validate MultiCity International Flex_RUH_SAW"}};
	}

}
