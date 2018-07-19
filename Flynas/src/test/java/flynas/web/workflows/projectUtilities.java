package flynas.web.workflows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ctaf.support.ExcelReader;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;
import flynas.web.workflows.BookingPageFlow;

public class projectUtilities<RenderedWebElement> extends BookingPageLocators {
	BookingPageFlow<RenderedWebElement> bookingPageFlow=new BookingPageFlow<RenderedWebElement>();
	
	public void waitforpageload() throws InterruptedException{
		waitUtilElementhasAttribute(BookingPageLocators.body);	
	}
	
	public void clickLogin() throws Throwable{
		//click back to home button in case of error 500
		bookingPageFlow.clickBackToHomeButton();
		waitforpageload();
		waitForVisibilityOfElement(BookingPageLocators.login_lnk, "Login");	
		click(BookingPageLocators.login_lnk, "Login");
	}
	
	public void logout() throws Throwable{
		waitforpageload();
		waitForVisibilityOfElement(BookingPageLocators.logout_lnk, "Logout");	
		click(BookingPageLocators.logout_lnk, "Logout");
	}
	
	public void clickok() throws Throwable{
		waitforpageload();
		click(BookingPageLocators.ok, "ok");
	}
	
	public void selectLanguage(String Language) throws Throwable{
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.xpath("//div[@class='loading sk-wave']"))));
			waitforElement(BookingPageLocators.oneWay);
			if(Language.equalsIgnoreCase("Arabic")==true){
				if(isElementPresent(BookingPageLocators.Arabic_pdctn_AR("العربية"))==true)
				click(BookingPageLocators.Arabic_pdctn_AR("العربية"), "Arabic Language");
			}else if 
			(Language.equalsIgnoreCase("Turkish")==true){
				if(isElementPresent(BookingPageLocators.Arabic_pdctn_AR("Türkçe"))==true)
					click(BookingPageLocators.Arabic_pdctn_AR("Türkçe"), "Tarkish Language");
			}
			Reporter.SuccessReport("Selecting language", Language+" Language Selected");
		}catch(Exception e){
			Reporter.failureReport("Selecting language","Unable to select "+Language+" Language ");
		}
	}
	
	public void expandfaresummary() throws Throwable{
		waitforpageload();
		click(BookingPageLocators.faresumrypls, "ok");
	}
	
	public boolean compareArrays(String[] Arr1, String[] Arr2) throws Throwable{
		boolean flag = false;
		try{
			 if (Arrays.equals(Arr1, Arr2)){
		            System.out.println("Same");
		            flag = true;
		            System.out.println(Arr1+"and"+Arr2+" are equal");
			 	}else{
			 		System.out.println(Arr1+"and"+Arr2+" are not equal");
			 	}
			 return flag;
		}catch (Exception e){
			Reporter.failureReport("Comparing"+"Arr1"+"Arr2", "Exception while comparing"+"Arr1"+"Arr2");
			return flag;				
		}
		
	}
	
	public void verifyDateupdate(String lable, String[] firstDate, String[] updatedDate) throws Throwable{
		waitforpageload();
		if(compareArrays(firstDate,updatedDate)==false){
			Reporter.SuccessReport("Verifing if Date is Updated :", lable+" Updated Successfully ");
		}else{
			Reporter.failureReport("Verifing if Date is Updated :", lable+" Updated UnSuccessfull ");
		}
	}
	
	public boolean compareStrings(String String1, String String2) throws Throwable{
		boolean flag = false;
		try{
			 if (String1.equalsIgnoreCase(String2)){
		            flag = true;
		            System.out.println(String1+"and"+String2+" are equal");
			 	}else{
			 		System.out.println(String1+"and"+String1+" are not equal");
			 	}
			 return flag;
		}catch (Exception e){
			Reporter.failureReport("Comparing"+"Arr1"+"Arr2", "Exception while comparing"+"Arr1"+"Arr2");
			return flag;				
		}
		
	}
	
	public void verifytextupdate(String lable, String text1, String text2) throws Throwable{
		waitforpageload();
		System.out.println("comparing string :" +text1+","+text2);
		if(compareStrings(text1,text2)==false){
			Reporter.SuccessReport("Verifing if text is Updated :", lable+" Updated Successfully ");
		}else{
			Reporter.failureReport("Verifing if text is Updated :", lable+" Update UnSuccessfull ");
		}
	}
	
	public void verifyAlertPopup() throws Throwable
	{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true){
			click(BookingPageLocators.ok, "OK");
			Reporter.SuccessReport("Validating if failure message", "Failure message displayed");		
		}
		else{
			Reporter.failureReport("Validating if failure message", "Failure message is not displayed");					
		}
	}
	
	public void VerifyErrorMessage(String Text) throws Throwable
	{
		Thread.sleep(5000);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true){
			System.out.println(getText(BookingPageLocators.alertText, "Alert message"));
			if(getText(BookingPageLocators.alertText, "Alert message").contains(Text));			
			Reporter.SuccessReport("Validating if failure message", Text+" message displayed");		
			}
			else{
			Reporter.failureReport("Validating if failure message", Text+" message is not displayed");					
			}
	}
	
	public void VerifyAlertMessage(String Text) throws Throwable
	{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		if(isElementDisplayedTemp(BookingPageLocators.Alert)==true){
			System.out.println(getText(BookingPageLocators.alertText, "Alert message"));
			if(getText(BookingPageLocators.alertText, "Alert message").contains(Text));			
			Reporter.SuccessReport("Validating if failure message", Text+" message displayed");		
			}
			else{
			Reporter.failureReport("Validating if failure message", Text+" message is not displayed");					
			}
	}
	
	public static Float getfee(String category) throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		String[] fee = getText(BookingPageLocators.fee(category), category+" fare").split("\\s+");	
		return Float.parseFloat(fee[1]);	
	}
	
	public static  void clickContinueBtn() throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);	
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.continueBtn));
		click(BookingPageLocators.continueBtn, "Continue");
	}
	
	public static String removeSpecialchars(String value){
		
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match= pt.matcher(value);
        while(match.find())
        {
            String s= match.group();
            value=value.replaceAll("\\"+s, "");
        }
        System.out.println("string converted to : "+value);
        return value;
	}

	public String[] pickCredentials(String Sheetname){
		ExcelReader xls = new ExcelReader(configProps.getProperty("Credentialsdata"),Sheetname);
		int count = xls.getRowCount(Sheetname);
		System.out.println(count);
		int index = ((int)(Math.random() * count-1)) + 1;
		System.out.println(index);
		String[] credentials =new String[5];
		credentials[0] = xls.getCellValue("credentials"+index, "userid");
		credentials[1] = xls.getCellValue("credentials"+index, "password");
		credentials[2] = xls.getCellValue("credentials"+index, "firstname");
		credentials[3] = xls.getCellValue("credentials"+index, "lastname");
		return credentials;
	}
	
	public String[] pickNaSmileCredentials(String Sheetname){
		ExcelReader xls = new ExcelReader(configProps.getProperty("Credentialsdata"),Sheetname);
		int count = xls.getRowCount(Sheetname);
		int index = ((int)(Math.random() * count-1)) + 1;;
		String[] credentials =new String[5];
		credentials[0] = xls.getCellValue("credentials"+index, "userid");
		credentials[1] = xls.getCellValue("credentials"+index, "password");
		credentials[2] = xls.getCellValue("credentials"+index, "firstname");
		credentials[3] = xls.getCellValue("credentials"+index, "lastname");
		credentials[4] = xls.getCellValue("credentials"+index, "nasmiles");
		return credentials;
	}
	
	public void navigateToHome() throws Throwable{
		// add validation
		if(isElementDisplayedTemp(BookingPageLocators.Home)){
			click(BookingPageLocators.Home, "Home");
			Reporter.SuccessReport("Click Home tab", "Home Tab Clicked");
		}
		else{
			System.out.println("No Seat Page");		
			Reporter.failureReport("Click Home tab", "Hometab could not be clicked");
		}
	}
	
	public static void writingPNR(String sheetname,String value)
	{
		ExcelReader xls = new ExcelReader(configProps.getProperty("OutputPnrs"),sheetname);
		int rownum = xls.getRowCount(sheetname);
		if(xls.getCellData(sheetname, "PNR", rownum)==null){
			xls.setCellData(sheetname, "PNR", rownum, value);
			xls.setCellData(sheetname, "TestCaseNum", rownum,Integer.toString(rownum));
			
		}else{
			xls.setCellData(sheetname, "PNR", rownum+1, value);
			xls.setCellData(sheetname, "TestCaseNum", rownum+1,testName);
		}
	}
	
public static String pickDate(String xlsDate){
		
		String[] date = xlsDate.split("\\^");
		System.out.println("date[1] = " +date[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, Integer.parseInt(date[1])); // Adding 1 days
		String newDate = sdf.format(c.getTime());
		return newDate;		
	}
	
	
	
	public static String nextDateof(String date) throws ParseException
	{
		String dt = date;  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE,1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		return dt;
	}

}
