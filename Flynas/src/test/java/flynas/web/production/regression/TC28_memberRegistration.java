package flynas.web.production.regression;

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

public class TC28_memberRegistration extends BookingPageFlow{
	
	@SuppressWarnings("rawtypes")
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_01_a_memberRegistration(String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			//instantiating page objects
			projectUtilities util = new projectUtilities();
			LoginPage LoginPg = new LoginPage();
			MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
		
			
			//navigating to login page
			util.clickLogin();
			LoginPg.ClickJoinNow();
			String username = memberRegisterPg.memberRegistration("","Adult"); // Registering a new Adult member
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
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{"Validate Member Login"}};
	}

}
