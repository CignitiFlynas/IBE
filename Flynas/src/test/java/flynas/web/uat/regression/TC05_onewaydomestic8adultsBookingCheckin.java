package flynas.web.uat.regression;


import java.util.concurrent.TimeUnit;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ConfiguratorSupport;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC05_onewaydomestic8adultsBookingCheckin extends BookingPageFlow {
	
//	public static Logger logger = Logger.getLogger(TC05_onewaydom8Business.class.getName());
	
	public static ConfiguratorSupport configProps=new ConfiguratorSupport("config.properties");
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"TC05_oneWayDom8AdultCheckin");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public void TC_05_onewaydomestic8adultsBookingCheckin(String strTripType, 
			String strFlightType, String strOrigin,String strDestination, String strDepatureDate,
			String strAdultCount, String strChildCount, String strInfantCount, String strPromo,  String strTotalPessenger,
			String strNationality,String strDocumentType,String strDocumentNum,String strNaSmile,String strMobile,
			String strBookingClass, String bundle, String strSelectSeat, String strPaymentType,
			String Currency)throws Throwable{
				
		try{
					String description = "Validate oneway Domastic 8 Adults Business";
					TestEngine.testDescription.put(HtmlReportSupport.tc_name, description);
					
					//Initializing departure date and return date
					strDepatureDate = pickDate(strDepatureDate);
				
					String[] Credentials = pickCredentials("UserCredentials");
					String username =Credentials[0];
					String password =Credentials[1];
					String lastname = Credentials[3];
					//click back to home button in case of error 500
					clickBackToHomeButton();
										
					//User Login
					waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
					click(BookingPageLocators.login_lnk, "Login");
					switchtoChildWindow();
					login(username,password);
					
					//Entering Booking Details
					inputBookingDetails(strTripType, strOrigin, strDestination, strDepatureDate, "","","",
							strAdultCount, strChildCount, strInfantCount, strPromo,Currency,strPaymentType);
					
					//Selecting flight and traveling class
					selectClass(strBookingClass, bundle);
					clickContinueBtn();
					upSellPopUpAction("Continue");
					
					//Entering passenger details
					inputPassengerDetails(strFlightType,strTotalPessenger,strNationality,strDocumentType,strDocumentNum, strNaSmile,strMobile,username,"","","");
					
					Baggage_Extra(strTripType);
					addSportsEqpmnt(strTripType);
					clickContinueBtn();
					selectallSeats(strSelectSeat,strTotalPessenger,strTripType);
					payment(strPaymentType,"");
					String strpnr = getReferenceNumber();
					String strPNR = strpnr.trim();
					validate_ticketStatus(strPNR);
					searchFlightCheckin(strPNR, username, "",lastname);
					performCheckin(strSelectSeat, strPaymentType,strTotalPessenger);
					validateCheckin();
					
					updateStatus("IBE_UAT_Reg","TC05_onewaydomestic8adultsBookingCheckin","Pass");
					Reporter.SuccessReport("TC05_onewaydomestic8adultsBookingCheckin", "Pass");
					
								
					}catch(Exception e){
						e.printStackTrace();
						updateStatus("IBE_UAT_Reg","TC05_onewaydomestic8adultsBookingCheckin","Fail");
						Reporter.failureReport("TC05_onewaydomestic8adultsBookingCheckin", "Fail");
						
					}
	}
		
		@DataProvider(name="testData")
		public Object[][] createdata1() {
		    return (Object[][]) new Object[][] {{
		    	xls.getCellValue("Trip Type", "Value"),
		    	xls.getCellValue("Flight Type", "Value"),
		    	xls.getCellValue("Origin", "Value"),
		    	xls.getCellValue("Destination", "Value"),
		    	xls.getCellValue("Departure Date", "Value"),
		    	xls.getCellValue("Adults Count", "Value"),
		    	xls.getCellValue("Child Count", "Value"),
		    	xls.getCellValue("Infant Count", "Value"),
		    	xls.getCellValue("Promo", "Value"),
		    	xls.getCellValue("Total Passenger", "Value"),
		    	xls.getCellValue("Nationality", "Value"),
		    	xls.getCellValue("Document Type", "Value"),
		    	xls.getCellValue("Doc Number", "Value"),
		    	xls.getCellValue("na Smiles", "Value"),
		    	xls.getCellValue("Mobile", "Value"),
		    	xls.getCellValue("Email Address", "Value"),		    	
		    	xls.getCellValue("Booking Class", "Value"),
		    	xls.getCellValue("Bundle", "Value"),

		    	xls.getCellValue("Select Seat", "Value"),
    			"Credit Card",
    			""}};
	}
	
}

