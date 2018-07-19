package flynas.web.uat.regression;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.ActionEngine;
import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ConfiguratorSupport;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC06_oneWay2InternationalEco extends BookingPageFlow {

	public static Logger logger = Logger.getLogger(TC06_oneWay2InternationalEco.class.getName());

	public static ConfiguratorSupport configProps=new ConfiguratorSupport("config.properties");

	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC_06_oneWay2InternationalEco");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public void TC_06_oneWay2InternationalEco(
			String strTripType, String strOrigin,String strDestination, 
			String strDepatureDate,	String strFlightType, String strTotalPessenger,
			String strNationality,String strDocumentType,String strDocumentNum,String strNaSmile,
			String strMobile, String strEmail, String strAdultCount, String strChildCount, 
			String strInfantCount, String strPromo,String strBookingClass, String bundle,String strSelectSeat,
			String strPaymentType, String Currency)throws Throwable{
		
		try{
			String description = "Validate oneWay 2Adults International Economy";
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, description);
			
			//Initializing departure date and return date
			String	deptdate = pickDate(strDepatureDate);
		
			
			//User Login
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			String password =Credentials[1];
			//click back to home button in case of error 500
			clickBackToHomeButton();
			waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
			click(BookingPageLocators.login_lnk, "Login");
			switchtoChildWindow();
			login(username,password);
			
			//Entering Booking Details
			inputBookingDetails(strTripType, strOrigin, strDestination, deptdate,"", "",
					"", strAdultCount, strChildCount, strInfantCount, strPromo,Currency,
					strPaymentType);
			
			selectClass(strBookingClass, bundle);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			
			
			//Entering passenger details
			inputPassengerDetails(strFlightType,strTotalPessenger,strNationality,strDocumentType,strDocumentNum, strNaSmile,strMobile,strEmail,"","","");
			
			
			//Clicking continue button on Baggage details page
			coninueOnBaggage();
			
			selectallSeats(strSelectSeat,strTotalPessenger,strTripType);
			payment(strPaymentType,"");
			String strPNR = getReferenceNumber();
			validate_ticketStatus(strPNR);
			
			updateStatus("IBE_UAT_Reg","TC06_oneWay2InternationalEco","Pass");
			Reporter.SuccessReport("TC_06_oneWay2InternationalEco", "Passed");
			
		}catch(Exception e){
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC06_oneWay2InternationalEco","Fail");
			Reporter.failureReport("TC_06_oneWay2InternationalEco", "Fail");
			
		}
	}

	@DataProvider(name="testData")
	public Object[][] createdata1() {
		return (Object[][]) new Object[][] {{
			
			xls.getCellValue("Trip Type", "Value"),
			xls.getCellValue("Origin", "Value"),
			xls.getCellValue("Destination", "Value"),
			xls.getCellValue("Departure Date", "Value"),
			xls.getCellValue("Flight Type", "Value"),
			xls.getCellValue("Total Passenger", "Value"),
			xls.getCellValue("Nationality", "Value"),
	    	xls.getCellValue("Document Type", "Value"),
	    	xls.getCellValue("Doc Number", "Value"),
	    	xls.getCellValue("na Smiles", "Value"),
	    	xls.getCellValue("Mobile", "Value"),
	    	xls.getCellValue("Email Address", "Value"),
			xls.getCellValue("Adults Count", "Value"),
			xls.getCellValue("Child Count", "Value"),
			xls.getCellValue("Infant Count", "Value"),
			xls.getCellValue("Promo", "Value"),
			xls.getCellValue("Booking Class", "Value"),
		    xls.getCellValue("Bundle", "Value"),
			xls.getCellValue("Select Seat", "Value"),
			"Credit Card",
			""}};
	}

}


