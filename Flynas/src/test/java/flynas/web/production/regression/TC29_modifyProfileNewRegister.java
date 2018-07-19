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

public class TC29_modifyProfileNewRegister extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEProdReg"),"TC29");

	@Test(dataProvider = "testData",groups={"Production"})
	public  void TC_29_modifyProfileNewRegister( String Password,String Nationality,String DocumentType,String DocNumber,
			String Mobile,String EmailAddress,String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			click(BookingPageLocators.login_lnk, " Login");
			login(EmailAddress, Password);
			waitforElement(BookingPageLocators.myProfile);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.myProfile, "My Profile");
			String newPwd = randomString();
			//type(BookingPageLocators.pasword_reg_uppage, newPwd, "Password");
			//type(BookingPageLocators.cnfmpasword_reg, newPwd, "Conform Password");
			waitforElement(BookingPageLocators.dd_reg);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,200)", "");
			
			click(BookingPageLocators.dd_reg, "Date");
			click(By.xpath(BookingPageLocators.selectdd_reg+randomNumericString()+"]"), "Date");
			click(BookingPageLocators.mm_reg, "Month");
			click(By.xpath(BookingPageLocators.selectmm_reg+randomNumericString()+"]"), "Month");
			click(BookingPageLocators.yy_reg, "year");
			click(By.xpath(BookingPageLocators.selectyy_reg+randomNumber(12, 19)+"]"), "Year");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.update_Btn));
			click(BookingPageLocators.update_Btn, "Update");
			
			verifingProfileUpdatemessage();
			
			Reporter.SuccessReport("TC29_modifyProfileNewRegister", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.SuccessReport("TC29_modifyProfileNewRegister", "Failed");
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
	    		"Modify Profile New Register"}};
	}


}
