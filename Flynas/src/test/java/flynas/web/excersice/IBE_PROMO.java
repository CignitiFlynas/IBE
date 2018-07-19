package flynas.web.excersice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;
import flynas.web.workflows.LoginPage;
import flynas.web.workflows.MemberRegistrationPage;
import flynas.web.workflows.projectUtilities;

public class IBE_PROMO extends BookingPageFlow{
	ExcelReader xls1 = new ExcelReader(configProps.getProperty("PromoData"),"PromoDetails");
	String promoCode = xls1.getCellValue("PromoCode", "Value");
	String discount = xls1.getCellValue("Discount", "Value");
	String paymentType = xls1.getCellValue("PaymentType", "Value");
	ExcelReader xls = new ExcelReader(configProps.getProperty("PromoData"),"Data");
	int row = xls.getRowCount("Data");

	
	@Test(groups={"Chrome"})
	public  void IBE_PromoTest() throws Throwable {
		for(int i=1;i<=row;i++){
		try {
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "IBE Promo Test");		
			
			String tripType = xls.getCellValue("dataset"+i, "TripType");
			String origin = xls.getCellValue("dataset"+i, "Origin");
			String dest = xls.getCellValue("dataset"+i, "Destination");
			String deptdate = xls.getCellValue("dataset"+i, "OnwardDate");
			System.out.println(deptdate);
		
			String retrndate = xls.getCellValue("dataset"+i, "ReturnDate");
			String adults = xls.getCellValue("dataset"+i, "Adults");
			String children = xls.getCellValue("dataset"+i, "Children");
			String infants = xls.getCellValue("dataset"+i, "Infants");
			String bookingClass = xls.getCellValue("dataset"+i, "BookingClass");
			String bundle = xls.getCellValue("dataset"+i, "Bundle");
			String paymenttype = xls.getCellValue("dataset"+i, "PaymentType");
			
			inputBookingDetails(tripType, origin, dest, deptdate, "", "", retrndate,adults, children, infants,promoCode,"","");
			selectClass(bookingClass, bundle);
			verifyDiscount(discount ,bookingClass);
			clickContinueBtn();
			upSellPopUpAction("Continue");
			
			//Clicking continue button on Passenger details page
			String TotalPessenger= String.valueOf(Integer.parseInt(adults)+Integer.parseInt(children)+Integer.parseInt(infants));
			inputPassengerDetails("",TotalPessenger,"Saudi Arabia","Passport",randomString(10), "","555555555","flynasqa@gmail.com","","","");
			
			//Clicking continue button on Baggage details page
			coninueOnBaggage();
			
			continueOnSeatSelection();
			payment(paymenttype,paymentType);
			String PNR = getReferenceNumber();
			System.out.println(PNR);
			WritePromoStatusPNR(i,PNR);		
			
			Reporter.SuccessReport("PromoCode :"+promoCode+" with datase :"+ i, "Pass");
			navigateToBookingPage();
			
			}
		catch (Exception e) {
			e.printStackTrace();
			Reporter.failureReport("PromoCode :"+promoCode+" with datase :"+ i, "Fail");
			}
		}
	}
	

}