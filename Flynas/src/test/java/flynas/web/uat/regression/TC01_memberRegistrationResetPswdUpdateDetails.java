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

public class TC01_memberRegistrationResetPswdUpdateDetails extends BookingPageFlow{
	
	String username;
	String newPswd;
	
	//instantiating page objects
	projectUtilities util = new projectUtilities();
	LoginPage LoginPg = new LoginPage();
	MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
	MemberDashboard memberdb = new MemberDashboard();
	MyProfilePage profilePage = new MyProfilePage();
	
	@SuppressWarnings("rawtypes")
	@Test(priority=0, groups={"Chrome"})
	public  void TC01_a_memberRegistration() throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Validate Member Login");
					
			//navigating to login page
			util.clickLogin();
			LoginPg.ClickJoinNow();
			username = memberRegisterPg.memberRegistration("","Adult"); // Registering a new Adult member
			memberRegisterPg.verifingMemberRegistration();
			
			//Loging out
			util.logout();
			util.clickok();
			
			//Verifying member registration by loging in.
			LoginPg.login(username, "Test@1234");		
			
			updateStatus("IBE_UAT_Reg","TC01_a_memberRegistration","Pass");
			Reporter.SuccessReport("TC01_a_memberRegistration", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC01_a_memberRegistration","Fail");
			Reporter.SuccessReport("TC01_a_memberRegistration", "Failed");
		}
	}
	
	@Test(priority=1, dependsOnMethods={"TC01_a_memberRegistration"},groups={"Chrome"})
	public  void TC01_b_resetPasswordusingmyProfile() throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Verifying reset password");
			//click back to home button in case of error 500
			clickBackToHomeButton();
			
			waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");				
			click(BookingPageLocators.login_lnk, " Login");
			switchtoChildWindow();
			//Don't change this login to random pickup. 
			LoginPg.login(username,"Test@1234");			
			memberdb.navigateToMyProfile();
			String newPswd = RandomStringUtils.random(8, true, true);
			waitforElement(BookingPageLocators.changeMyPwdBtn);
			click(BookingPageLocators.changeMyPwdBtn, "Change My Password");
			waitforElement(BookingPageLocators.currentpwd);
			type(BookingPageLocators.currentpwd, "Test@1234", "Current Password");
			type(BookingPageLocators.newpwd, newPswd, "New Password");
			type(BookingPageLocators.cnfmnewPwd, newPswd, "Conform New Password");
			click(BookingPageLocators.ConfirmPwdBtn, "Confirm");
			waitforElement(BookingPageLocators.pwdChngeComnt);
			waitUtilElementhasAttribute(BookingPageLocators.body);
			click(BookingPageLocators.ok, "OK");
			click(BookingPageLocators.update_Btn, "Update");
			verifingProfileUpdatemessage();
			click(BookingPageLocators.logout_lnk, "Logout");
			click(BookingPageLocators.ok, "OK");
			login(username,newPswd);
			if(isElementPresent(By.xpath("//h2[contains(text(),'Welcome back, ')]"))==true)
			{			
				Reporter.SuccessReport("Verifing Pasword Reset", "User is able to login with new password");
			}
			else
			{
				Reporter.failureReport("Verifing Pasword Reset", "User is unable to login with new password");
			}
			
			updateStatus("IBE_UAT_Reg","TC01_b_resetPasswordusingmyProfile","Pass");
			Reporter.SuccessReport("TC01_b_resetPasswordusingmyProfile", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC01_b_resetPasswordusingmyProfile","Fail");
			Reporter.SuccessReport("TC01_b_resetPasswordusingmyProfile", "Failed");
		}
	}
	
	@Test(priority=2,dependsOnMethods={"TC01_a_memberRegistration"},groups={"Chrome"})
	public  void TC_01_c_updatemyProfileOfMemberDetails() throws Throwable {
		try {
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, "Verifying Member details update from My Profile Page");
			
					
			//navigating to login page
			util.clickLogin();
			
			//Signing in and navigating to my profile page
			LoginPg.login(username,"Test@1234");
			memberdb.navigateToMyProfile();		
			
			//Capturing current DOB,Document number, Document expire date & Mobile number
			String firstDOB[] = profilePage.getdate("Date of birth");
			
			String firstDocNum = getText(BookingPageLocators.docnumber_reg, "Documen Type");
			String firstDocExpDt[] = profilePage.getdate("Document expiry date");
			String FirstMobileNum = getText(BookingPageLocators.mobileNum, "MobileNumber");
			
			//Updating DOB,Document number, Document expire date & Mobile number
			profilePage.ModifyDate("Date of birth");
			profilePage.ModifyDocNum();
			profilePage.ModifyDate("Document expiry date");
			profilePage.updateMobilenum();
			profilePage.clickUpdatebtn();
			
			//Verifying successful update message
			verifingProfileUpdatemessage();
			
			//Refreshing the page and capturing the all the modified data 
			refreshPage();
			String[] updateDOB = profilePage.getdate("Date of birth");
			String updatedDocNum = getText(BookingPageLocators.docnumber_reg, "Documen Type");
			String[] updateDocExpdt = profilePage.getdate("Date of birth");
			String updatedMobileNum = getText(BookingPageLocators.mobileNum, "MobileNumber");
			
			//Verifying if data is modified
			util.verifyDateupdate("Date of birth",firstDOB,updateDOB);
			//util.verifytextupdate("Document Number",firstDocNum,updatedDocNum);
			util.verifyDateupdate("Document expire date",firstDocExpDt,updateDocExpdt);
			//util.verifytextupdate("Mobile Number",FirstMobileNum,updatedMobileNum);
			
			updateStatus("IBE_UAT_Reg","TC01_c_updatemyProfileOfMemberDetails","Pass");
			Reporter.SuccessReport("TC01_c_updatemyProfileOfMemberDetails", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC01_c_updatemyProfileOfMemberDetails","Fail");
			Reporter.SuccessReport("TC01_c_updatemyProfileOfMemberDetails", "Failed");
		}
	}
	
	
}
