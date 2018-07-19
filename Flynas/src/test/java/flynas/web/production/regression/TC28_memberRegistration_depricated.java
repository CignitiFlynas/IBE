package flynas.web.production.regression;

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

public class TC28_memberRegistration_depricated extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEProdReg"),"TC28");

	@Test(dataProvider = "testData",groups={"Production"})
	public  void TC_28_memberRegistration( String Password,String Nationality,String DocumentType,String DocNumber,String Mobile,
			String EmailAddress,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			click(BookingPageLocators.login_lnk, " Login");
			waitforElement(BookingPageLocators.here);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.here, "Creat an Account Here");
			String email=memberRegistration(Password,Nationality,DocumentType,DocNumber,Mobile,EmailAddress);
			xls.setCellData("TC29", "Value", 7, email);	
			verifingMemberRegistration_Production();
			//click(BookingPageLocators.ok, "OK");
			
			
			Reporter.SuccessReport("TC28_memberRegistration", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.SuccessReport("TC28_memberRegistration", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{
	    		xls.getCellValue("Password", "Value"),
	    		xls.getCellValue("Nationality", "Value"),
	    		xls.getCellValue("Document Type", "Value"),
	    		xls.getCellValue("Doc Number", "Value"),
	    		xls.getCellValue("Mobile", "Value"),
	    		xls.getCellValue("Email Address", "Value"),
	    		"Validate Member Login"}};
	}

}
