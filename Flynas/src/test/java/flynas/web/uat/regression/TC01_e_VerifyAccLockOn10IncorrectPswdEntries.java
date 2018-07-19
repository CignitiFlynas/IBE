package flynas.web.uat.regression;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ExcelReader;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPage;
import flynas.web.workflows.BookingPageFlow;
import flynas.web.workflows.LoginPage;
import flynas.web.workflows.MemberDashboard;
import flynas.web.workflows.MemberRegistrationPage;
import flynas.web.workflows.MyProfilePage;
import flynas.web.workflows.projectUtilities;

public class TC01_e_VerifyAccLockOn10IncorrectPswdEntries extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("TestDataIBEUAT"),"FL_WEB_22");

	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_01_e_VerifyAccLockOn10IncorrectPswdEntries( String Password,String Nationality,String DocumentType,
			String DocNumber,
			String Mobile,
			String EmailAddress,
			String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			
			projectUtilities util = new projectUtilities();
			LoginPage LoginPg = new LoginPage();
			BookingPage Bookingpg = new BookingPage();
			MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
			MyProfilePage profilePage = new MyProfilePage();
			MemberDashboard memberdb = new MemberDashboard();
			//navigating to login page
			util.clickLogin();
			LoginPg.ClickJoinNow();
			String username = memberRegisterPg.memberRegistration("","Adult"); // Registering a new Adult member
			memberRegisterPg.verifingMemberRegistration();
			
			//Loging out
			util.logout();
			util.clickok();
			
			LoginPg.lockAccount(username);
			util.VerifyErrorMessage("account is locked");
			
			updateStatus("IBE_UAT_Reg","TC01_e_VerifyAccLockOn10IncorrectPswdEntries","Pass");						
			Reporter.SuccessReport("TC01_e_VerifyAccLockOn10IncorrectPswdEntries", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC01_e_VerifyAccLockOn10IncorrectPswdEntries","Fail");
			Reporter.SuccessReport("TC01_e_VerifyAccLockOn10IncorrectPswdEntries", "Failed");
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
	    			"Verifying Account lock after ten incorrect password entries"}};
	}

}
