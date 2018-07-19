package flynas.web.workflows;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class MyProfilePage<RenderedWebElement> extends BookingPageLocators{
	
	public void waitforpageload() throws InterruptedException{	
		waitUtilElementhasAttribute(BookingPageLocators.body);
		}
	
	public String[] getdate(String Lable) throws Throwable{
		waitforpageload();
		String[] date=new String[3];
		date[0] = getText(BookingPageLocators.dd(Lable), "date");
		date[1] = getText(BookingPageLocators.mm(Lable), "date");
		date[2] = getText(BookingPageLocators.yy(Lable), "date");
		System.out.println("date : "+date[0]+"-"+date[1]+"-"+date[2]);
		return date;	
		
	};
	
	public void ModifyDate(String lable) throws Throwable{
		waitforpageload();
		try{
			click(By.xpath("//label[contains(text(),'"+lable+"')]/parent::div/div[1]/div/div/span/i"),"date ddl");
			click(By.xpath(BookingPageLocators.selectdd_reg+randomNumericString()+"]"), "Date");
			click(By.xpath("//label[contains(text(),'"+lable+"')]/parent::div/div[2]/div/div/span/i"),"month ddl");
			click(By.xpath(BookingPageLocators.selectmm_reg+randomNumericString()+"]"), "Month");
			click(By.xpath("//label[contains(text(),'"+lable+"')]/parent::div/div[3]/div/div/span/i"),"month ddl");
			click(By.xpath(BookingPageLocators.selectyy_reg+randomNumber(12, 19)+"]"), "Year");		
			Reporter.SuccessReport("Modifying "+lable+" :" , "Successful");
		}catch(Exception e){
			Reporter.failureReport("Modifying "+lable+" :", "Unsuccessful");
		}
	}
	
			
	public void ModifyDocNum() throws Throwable{
		waitforpageload();
		try{
			type(BookingPageLocators.docnumber_reg, randomString(10), "Document Number");
			Reporter.SuccessReport("Modifying Document number :", "Document number updated Successfully");
		}catch(Exception e){
			Reporter.failureReport("Modifying Document number :", "Document number update failed");
		}
	}
	
	

	public void updateMobilenum() throws Throwable {
		waitforpageload();
		try{
			type(BookingPageLocators.mobileNum, randomNumericString(10), "MobileNumber");
			Reporter.SuccessReport("Modifying mobile number :", "Mobile number updated Successfully");
		}catch(Exception e){
			Reporter.failureReport("Modifying mobile number :", "Mobile number update failed");
		}
		
	}
	
	public void clickUpdatebtn() throws Throwable{
		waitforpageload();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.update_Btn));
		click(BookingPageLocators.update_Btn, "Update");
	}
	
	

}
