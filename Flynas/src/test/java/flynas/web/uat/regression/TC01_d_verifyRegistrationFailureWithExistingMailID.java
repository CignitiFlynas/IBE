package flynas.web.uat.regression;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctaf.accelerators.TestEngine;
import com.ctaf.googledrive.GoogleDriveAPI;
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

public class TC01_d_verifyRegistrationFailureWithExistingMailID extends BookingPageFlow{
	
	@SuppressWarnings("rawtypes")
	@Test(dataProvider = "testData",groups={"Chrome"})
	public  void TC_01_d_verifyRegistrationFailureWithExistingMailID( String Description) throws Throwable {
		try {
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name, Description);
			//instantiating page objects
			projectUtilities util = new projectUtilities();
			LoginPage LoginPg = new LoginPage();
			MemberRegistrationPage memberRegisterPg = new MemberRegistrationPage();
			
		
			
			
			String[] Credentials = pickCredentials("UserCredentials");
			String username =Credentials[0];
			
			//navigating to login page
			util.clickLogin();
			LoginPg.ClickJoinNow();
			memberRegisterPg.memberRegistration(username,"Adult");
			util.VerifyErrorMessage("already exists");
				
			updateStatus("IBE_UAT_Reg","TC01_d_verifyRegistrationFailureWithExistingMailID","Pass");			
			Reporter.SuccessReport("TC01_d_verifyRegistrationFailureWithExistingMailID", "Pass");
			
			}
		
	catch (Exception e) {
			e.printStackTrace();
			updateStatus("IBE_UAT_Reg","TC01_d_verifyRegistrationFailureWithExistingMailID","Fail");
			Reporter.SuccessReport("TC01_d_verifyRegistrationFailureWithExistingMailID", "Failed");
		}
	}
	
	@DataProvider(name="testData")
	public Object[][] createdata1() {
	    return (Object[][]) new Object[][] { 
	    		{"Validate Error message on Regestration with existing Email ID"}};
	}

}
