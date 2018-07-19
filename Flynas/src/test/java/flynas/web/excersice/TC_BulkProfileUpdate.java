package flynas.web.excersice;

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

public class TC_BulkProfileUpdate extends BookingPageFlow{
	ExcelReader xls = new ExcelReader(configProps.getProperty("Credentialsdata"),"UpdateProfiles");
	
	@SuppressWarnings("rawtypes")
	MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
	MemberDashboard memberdb = new MemberDashboard();
	
	@SuppressWarnings("rawtypes")
	@Test(groups={"Chrome"})
	public  void TC00_BulkProfileUpdate() throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Bulk Update");
			//instantiating page objects
			projectUtilities util = new projectUtilities();
			LoginPage LoginPg = new LoginPage();
			MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
			
			int row = xls.getRowCount("UpdateProfiles");
		
			util.clickLogin();
			
			for(int i=1;i<=row;i++){
			String username = xls.getCellValue("credentials"+i, "userid");
			String password = xls.getCellValue("credentials"+i, "password");
			String docNum = xls.getCellValue("credentials"+i, "Document Number");
			String[] DOB = xls.getCellValue("credentials"+i, "DOB").split("-");
						
			
			//switchtoChildWindow();
			login(username,password);
			memberdb.navigateToMyProfile();
			
			click(BookingPageLocators.dd_reg, "Date");
			click(By.xpath(BookingPageLocators.selectdd_reg+DOB[0]+"]"), "Date");
			click(BookingPageLocators.mm_reg, "Month");
			click(By.xpath(BookingPageLocators.selectmm_reg+DOB[1]+"]"), "Month");
			click(BookingPageLocators.yy_reg, "year");
			click(By.xpath(BookingPageLocators.selectyy_reg+36+"]"), "Year");
			click(BookingPageLocators.natinality_reg, "Nationality");
			click(BookingPageLocators.selectnatinality_reg("Saudi Arabia"), "Nationality");
			click(BookingPageLocators.doctype_reg, "Document Type");
			click(BookingPageLocators.selectdoctype_reg("Passport"), "Document Type");
			type(BookingPageLocators.docnumber_reg,docNum, "Document Number");
			click(BookingPageLocators.expdd, "Document Expiry Date");
			click(BookingPageLocators.selectDocExpdd, "Expiry Date");
			click(BookingPageLocators.expmm, "Expiry Month");
			click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(1,6)+"]"), "Expiry Month");
			click(BookingPageLocators.expyy, "Expiry year");
			click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(4,9)+"]"), "Expiry Year");
			
			click(BookingPageLocators.update_Btn, "Update");
			verifingProfileUpdatemessage();	
			click(BookingPageLocators.logout_lnk, "Logout");
			click(BookingPageLocators.ok, "OK");
			Thread.sleep(3000);
			
			Reporter.SuccessReport("Updating User Profiles", "User Profile "+i+" updated successfully");
			}
			Reporter.SuccessReport("TC00_BulkProfileUpdate", "Pass");				
	}
		
	catch (Exception e) {
			e.printStackTrace();
			Reporter.SuccessReport("TC00_BulkProfileUpdate", "Failed");
		}
	}	
	
}
