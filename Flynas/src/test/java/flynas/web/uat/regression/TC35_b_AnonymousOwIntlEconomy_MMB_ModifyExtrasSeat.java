package flynas.web.uat.regression;

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
import flynas.web.workflows.BookingPageFlow;

public class TC35_b_AnonymousOwIntlEconomy_MMB_ModifyExtrasSeat extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_35");

	@Test(dataProvider = "testData",groups={"Economy"})
	public void TC_35_b_AnonymousOwIntlEconomy_MMB_ModifyExtrasSeat(String strTripType,String strFlightType,String strOrigin,String strDestination,
			String strDepatureDate,String origin2,String departure2,String strReturnDate,String strTotalPessenger,String strAdultCount,
			String strChildCount,String strInfantCount,String strPromo,String strBookingClass, String bundle, String strNationality, String strDocumentType,	
			String strDocumentNum, String strNaSmile,  String strMobile, String strEmail, String strSelectSeat, String strPaymentType,String bookingtype,
			String strNewDate, String charity,String Currency,String description)throws Throwable{
				try{
					
					TestEngine.testDescription.put(HtmlReportSupport.tc_name, description);
					String	deptdate = pickDate(strDepatureDate);
					String	rtrndate = pickDate(strReturnDate);
										
						
					inputBookingDetails(strTripType, strOrigin, strDestination, deptdate,origin2,departure2, rtrndate,
							strAdultCount, strChildCount, strInfantCount, strPromo,Currency,strPaymentType);
					selectClass(strBookingClass, bundle);
					clickContinueBtn();
					upSellPopUpAction("Continue");
					String LastName[] = inputPassengerDetails(strFlightType, strTotalPessenger, strNationality, strDocumentType, 
							strDocumentNum, strNaSmile, strMobile, strEmail,"","","");
					coninueOnBaggage();
					continueOnSeatSelection();
					payment(strPaymentType, "");
					String strpnr = getReferenceNumber().trim();
					searchFlight(strpnr, strEmail, "",LastName[1]);
					modifyExtras();
					Baggage_Extra(strTripType);
					addSportsEqpmnt(strTripType);
					//Select_A_Meal();
					Select_lounge();
					inputExtras("12");
					waitforElement(BookingPageLocators.manageMyBookingTittle);
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(BookingPageLocators.modifySeat, "Seat Selection");
					waitforElement(BookingPageLocators.selectseattittle);
					waitUtilElementhasAttribute(BookingPageLocators.body);
					selectallSeats(strSelectSeat, "1", strTripType);
					waitforElement(BookingPageLocators.paynow);
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(BookingPageLocators.paynow, "Pay Now");
					payment(strPaymentType, "");
					String strPNR = getReferenceNumber().trim();
					validate_ticketStatus(strPNR);
					
					updateStatus("IBE_UAT_Reg","TC35_b_AnonymousOwIntlEconomy_MMB_ModifyExtrasSeat","Pass");
					Reporter.SuccessReport("TC35_b_AnonymousOwIntlEconomy_MMB_ModifyExtrasSeat", "Pass");
					
					}catch(Exception e){
						e.printStackTrace();
						updateStatus("IBE_UAT_Reg","TC35_b_AnonymousOwIntlEconomy_MMB_ModifyExtrasSeat","Fail");
						Reporter.failureReport("TC35_b_AnonymousOwIntlEconomy_MMB_ModifyExtrasSeat", "Fail");
						
					}
	}
		
		@DataProvider(name="testData")  
		public Object[][] createdata1() {
		    return (Object[][]) new Object[][] {{
		    	xls.getCellValue("Trip Type", "Value"),
		    	xls.getCellValue("Flight Type", "Value2"),
		    	xls.getCellValue("Origin", "Value2"),
		    	xls.getCellValue("Destination", "Value2"),
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
		    	xls.getCellValue("Document Type", "Value2"),
		    	xls.getCellValue("Doc Number", "Value"),
		    	"",
    			xls.getCellValue("Mobile", "Value"),
    			xls.getCellValue("Email Address", "Value"),
    			xls.getCellValue("Select Seat", "Value"),
    			xls.getCellValue("Payment Type", "Value"),"",
    			xls.getCellValue("New Date", "Value"),
    			xls.getCellValue("Charity Donation", "Value"),
    			xls.getCellValue("Currency", "Value"),
    			"Validate OneWay International Economy MMB- change date, modify extras, select business lounge"}};
	}
	
}
