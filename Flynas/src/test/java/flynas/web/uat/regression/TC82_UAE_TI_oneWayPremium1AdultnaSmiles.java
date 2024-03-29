package flynas.web.uat.regression;



import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPage;
import flynas.web.workflows.BookingPageFlow;

public class TC82_UAE_TI_oneWayPremium1AdultnaSmiles extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"UAE_TI");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC82_UAE_TI_oneWayPremium1AdultnaSmiles(String tripType, String origin, String dest, 
			String deptDate, String origin2,String departure2, String retdate,String Adult,String Child,String infant, String promo, String strBookingClass, String bundle,
			String FlightType,String totalpass,String nationality,String Doctypr,String docNumber,
			String naSmiles,String Mobile,String countrycode,String SelectSeat,String paymenttype,String bookingtype, 
			String charity,String Currency, String accType,String Description
			) throws Throwable {
		try {
			//System.out.println(paymenttype);
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			String deptdate = pickDate(deptDate);
			
			String[] Credentials = pickCredentials("UserCredentialsTI");
			String username =Credentials[0];
			String password =Credentials[1];
			BookingPage.clickLogin();
			login(username,password);
			
			inputBookingDetails(tripType, origin, dest, deptdate, origin2, departure2, retdate,Adult, Child, infant,promo,Currency,paymenttype);
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			//upSellPopUpAction("Continue");
			continueOnPassengerDetails_AE(countrycode);
			coninueOnBaggage_AE();
			continueOnSeatSelection();
			nasmilespayment_AE(username,password);
			
			String PNR=getReferenceNumber();
			validate_ticketStatus(PNR);
			
			updateStatus("IBE_UAT_Reg","TC82_UAE_TI_oneWayPremium1AdultnaSmiles","Pass");
			Reporter.SuccessReport("TC82_UAE_TI_oneWayPremium1AdultnaSmiles", "Pass");
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC82_UAE_TI_oneWayPremium1AdultnaSmiles","Fail");
			Reporter.SuccessReport("TC82_UAE_TI_oneWayPremium1AdultnaSmiles", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    			xls.getCellValue("Trip Type", "Value"),
		    		xls.getCellValue("Origin", "Value2"),
		    		xls.getCellValue("Destination", "Value2"),
		    		xls.getCellValue("Departure Date", "Value3"),
		    		"",
		    		"",
		    		xls.getCellValue("Return Date", "Value3"),
		    		xls.getCellValue("Adults Count", "Value"),
		    		xls.getCellValue("Child Count", "Value"),
		    		xls.getCellValue("Infant Count", "Value"),
		    		xls.getCellValue("Promo", "Value"),
		    		xls.getCellValue("Booking Class", "Value"),
		    		xls.getCellValue("Bundle", "Value3"),
		    		xls.getCellValue("Flight Type", "Value2"),
		    		xls.getCellValue("Total Passenger", "Value"),
		    		xls.getCellValue("Nationality", "Value"),
		    		xls.getCellValue("Document Type", "Value"),
		    		xls.getCellValue("Doc Number", "Value"),
		    		xls.getCellValue("naSmile", "Value"),
		    		xls.getCellValue("Mobile", "Value"),
		    		xls.getCellValue("Country Code", "Value"),
		    		xls.getCellValue("Select Seat", "Value"),
		    		xls.getCellValue("Payment Type", "Value3"),
		    		"",
	    			xls.getCellValue("Charity Donation", "Value"),
	    			xls.getCellValue("Currency", "Value"),
	    			xls.getCellValue("AccountType", "Value"),
		    		"Validate TI for UAE One way Premium bundle 1 Adult nasmiles booking"}};
	}

}

