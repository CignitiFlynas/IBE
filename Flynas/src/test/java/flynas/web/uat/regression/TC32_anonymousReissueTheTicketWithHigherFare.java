package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ConfiguratorSupport;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class TC32_anonymousReissueTheTicketWithHigherFare extends BookingPageFlow{
	
public static ConfiguratorSupport configProps=new ConfiguratorSupport("config.properties");
	
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_32");
	
	@Test(dataProvider = "testData",groups={"Chrome"})
	public void TC_32_anonymousReissueTheTicketWithHigherFare(String strTripType, String strFlightType, String strOrigin,
			String strDestination, String strDepatureDate, String origin2,String departure2,String strReturnDate, 
			String strTotalPessenger,String strAdultCount, String strChildCount, String strInfantCount, String strPromo,
			String strBookingClass, String bundle, String strNationality, String strDocumentType,	String strDocumentNum,String strNaSmile,
			String strMobile, String strEmail, String strSelectSeat, String strPaymentType,String bookingtype,
			String strNewDate, String charity,String Currency)throws Throwable{
			try{
					String[] bookingClass = strBookingClass.split("\\/");
					String[] seatselect = strSelectSeat.split("\\/");
					
					String description = "Reissue The Ticket With Higher Fare";
					TestEngine.testDescription.put(HtmlReportSupport.tc_name, description);
					String deptdate = pickDate(strDepatureDate);
				
					inputBookingDetails(strTripType, strOrigin, strDestination, deptdate,origin2, departure2,strReturnDate,
							strAdultCount, strChildCount, strInfantCount, strPromo,Currency,strPaymentType);
					
					selectClass(bookingClass[0], bundle);
					clickContinueBtn();
					upSellPopUpAction("Continue");
					String strLastName[] = inputPassengerDetails(strFlightType, strTotalPessenger, strNationality, strDocumentType, 
							strDocumentNum, strNaSmile, strMobile, strEmail,"","","");
					coninueOnBaggage();
					selectSeat(seatselect[0], bookingtype);
					payment(strPaymentType,"");
					String strpnr = getReferenceNumber();
					String strPNR = strpnr.trim();
					validate_ticketStatus(strPNR);
					
					//how to get last name 
					String newdate = pickDate(strNewDate);
					String strPNRChangeDate = changeDate(strPNR, strEmail, strMobile, strLastName[1], newdate, seatselect[1],strTotalPessenger,bookingClass[1],0);
									
					validate_ticketStatus(strPNR);
					
					updateStatus("IBE_UAT_Reg","TC32_anonymousReissueTheTicketWithHigherFare","Pass");
					Reporter.SuccessReport("TC32_anonymousReissueTheTicketWithHigherFare", "Pass");
					driver.close();
			
					}catch(Exception e){
						e.printStackTrace();
						updateStatus("IBE_UAT_Reg","TC32_anonymousReissueTheTicketWithHigherFare","Fail");
						Reporter.SuccessReport("TC32_anonymousReissueTheTicketWithHigherFare", "Fail");
						driver.close();
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
		    	"",
		    	"",
		    	xls.getCellValue("Return Date", "Value"),
		    	xls.getCellValue("Total Passenger", "Value"),
		    	xls.getCellValue("Adults Count", "Value"),
		    	xls.getCellValue("Child Count", "Value"),
		    	xls.getCellValue("Infant Count", "Value"),
		    	xls.getCellValue("Promo", "Value"),
		    	xls.getCellValue("Booking Class", "Value"),
	    		xls.getCellValue("Bundle", "Value"),
		    	xls.getCellValue("Nationality", "Value"),
		    	xls.getCellValue("Document Type", "Value"),
		    	xls.getCellValue("Doc Number", "Value"),
		    	"1234567890",
    			xls.getCellValue("Mobile", "Value"),
    			xls.getCellValue("Email Address", "Value"),
    			xls.getCellValue("Select Seat", "Value"),
    			"Credit Card",
    			"",
    			xls.getCellValue("New Date", "Value"),
    			xls.getCellValue("Charity Donation", "Value"),""}};
	}
}
