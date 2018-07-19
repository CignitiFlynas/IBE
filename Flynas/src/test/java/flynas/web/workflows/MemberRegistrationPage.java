package flynas.web.workflows;

import java.util.Random;

import org.openqa.selenium.By;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class MemberRegistrationPage<RenderedWebElement> extends BookingPageLocators{
	
	public void waitforpageload() throws InterruptedException{	
		waitUtilElementhasAttribute(BookingPageLocators.body);
		}
	
	public String memberRegistration(String userID, String memberType) throws Throwable
	{ 
		waitforpageload();
		String  username;
		String  email;
		String age= null;
		if(userID!= ""){
			username = userID;
			email = userID;
		}else{		
			username = randomString(8);
			email = username+"@cigniti.com";
		}
		final String[] Doctype = {"Passport", "National ID Card", "Iqama"};
		Random random = new Random();
		int index = random.nextInt(Doctype.length);
		String doctyp = Doctype[index];
		
		System.out.println(email); 
		type(BookingPageLocators.userEmail, email, "UserEmail");
		type(BookingPageLocators.pasword_reg, "Test@1234", "Password");
		type(BookingPageLocators.cnfmpasword_reg, "Test@1234", "Conform Password");
		click(BookingPageLocators.title_reg, "Title");
		click(By.xpath(BookingPageLocators.selecttitle_reg.replace("#", "1")), "Title");
		type(BookingPageLocators.fname_reg, randomString(8), "Firstname");
		type(BookingPageLocators.lname_reg, randomString(5), "Last Name");
		click(BookingPageLocators.dd_reg, "Date");
		click(By.xpath(BookingPageLocators.selectdd_reg+randomNumber(1,28)+"]"), "Date");
		click(BookingPageLocators.mm_reg, "Month");
		click(By.xpath(BookingPageLocators.selectmm_reg+randomNumber(1,6)+"]"), "Month");
		click(BookingPageLocators.yy_reg, "year");
		
		if(memberType.equalsIgnoreCase("Adult")){
			age = randomNumber(13, 20);
		}else if(memberType.equalsIgnoreCase("Child")){
			age = randomNumber(4, 11);
		}
		click(By.xpath(BookingPageLocators.selectyy_reg+age+"]"), "Year");
		click(BookingPageLocators.natinality_reg, "Nationality");
		click(BookingPageLocators.selectnatinality_reg("Saudi Arabia"), "Nationality");
		click(BookingPageLocators.doctype_reg, "Document Type");
		click(BookingPageLocators.selectdoctype_reg(doctyp), "Document Type");
		type(BookingPageLocators.docnumber_reg,randomString(10), "Document Number");
		click(BookingPageLocators.expdd, "Document Expiry Date");
		click(BookingPageLocators.selectDocExpdd, "Expiry Date");
		click(BookingPageLocators.expmm, "Expiry Month");
		click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(1,6)+"]"), "Expiry Month");
		click(BookingPageLocators.expyy, "Expiry year");
		click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(4,9)+"]"), "Expiry Year");
		type(BookingPageLocators.mobileNum, "534786324", "MobileNumber");
		type(BookingPageLocators.emailAdd, email, "Email Address");
		click(BookingPageLocators.register, "Register");
		if(isElementPresent(BookingPageLocators.ok)==true){
			Thread.sleep(1000);
			click(BookingPageLocators.ok, "Register");
			Thread.sleep(1000);
			click(BookingPageLocators.register, "Register");
		}
		return email;
	}
	
	public void memberRegistration(String userID,String password,String Firstname,String LastName, String memberType) throws Throwable
	{ 
		waitforpageload();
		String age= null;		
						
		System.out.println(userID); 
		type(BookingPageLocators.userEmail, userID, "UserEmail");
		type(BookingPageLocators.pasword_reg, password, "Password");
		type(BookingPageLocators.cnfmpasword_reg, password, "Conform Password");
		click(BookingPageLocators.title_reg, "Title");
		click(By.xpath(BookingPageLocators.selecttitle_reg.replace("#", "1")), "Title");
		type(BookingPageLocators.fname_reg,Firstname, "Firstname");
		type(BookingPageLocators.lname_reg, LastName, "Last Name");
		click(BookingPageLocators.dd_reg, "Date");
		click(By.xpath(BookingPageLocators.selectdd_reg+randomNumber(1,28)+"]"), "Date");
		click(BookingPageLocators.mm_reg, "Month");
		click(By.xpath(BookingPageLocators.selectmm_reg+randomNumber(1,6)+"]"), "Month");
		click(BookingPageLocators.yy_reg, "year");
		
		if(memberType.equalsIgnoreCase("Adult")){
			age = randomNumber(13, 20);
		}else if(memberType.equalsIgnoreCase("Child")){
			age = randomNumber(4, 11);
		}
		click(By.xpath(BookingPageLocators.selectyy_reg+age+"]"), "Year");
		click(BookingPageLocators.natinality_reg, "Nationality");
		click(BookingPageLocators.selectnatinality_reg("Saudi Arabia"), "Nationality");
		click(BookingPageLocators.doctype_reg, "Document Type");
		click(BookingPageLocators.selectdoctype_reg("Passport"), "Document Type");
		type(BookingPageLocators.docnumber_reg,randomString(10), "Document Number");
		click(BookingPageLocators.expdd, "Document Expiry Date");
		click(BookingPageLocators.selectDocExpdd, "Expiry Date");
		click(BookingPageLocators.expmm, "Expiry Month");
		click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(1,6)+"]"), "Expiry Month");
		click(BookingPageLocators.expyy, "Expiry year");
		click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(4,9)+"]"), "Expiry Year");
		type(BookingPageLocators.mobileNum, randomNumericString(10), "MobileNumber");
		type(BookingPageLocators.emailAdd,userID , "Email Address");
		click(BookingPageLocators.register, "Register");
		if(isElementPresent(BookingPageLocators.ok)==true){
			Thread.sleep(1000);
			click(BookingPageLocators.ok, "Register");
			Thread.sleep(1000);
			click(BookingPageLocators.register, "Register");
		}
		Thread.sleep(1000);
		
	}
	
	
	
	public void verifingMemberRegistration() throws Throwable
	{
		waitforElement(BookingPageLocators.registerConformation);
		if(isElementPresent(BookingPageLocators.registerConformation)==true)
		{
			Reporter.SuccessReport("Verifing Member Registratin", "Member is Successfully Registered");
		}
		else
		{
			Reporter.failureReport("Verifing Member Registratin", "Member is not Registered");
		}
	}
	public void verifingMemberRegistrationFailure() throws Throwable
	{
		waitforElement(BookingPageLocators.registerConformation);
		if(isElementPresent(BookingPageLocators.registerConformation)==true)
		{
			Reporter.failureReport("Verifing Member Registratin", "Member is Registered");			
		}
		else
		{
			Reporter.SuccessReport("Verifing Member Registratin", "Member is not Registered");
		}
	}

}
