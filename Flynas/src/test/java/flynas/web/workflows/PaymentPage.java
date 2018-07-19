package flynas.web.workflows;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ctaf.support.ExcelReader;
import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class PaymentPage<RenderedWebElement> extends BookingPageLocators {
	
	public static void selectPaymentType(String paymentType) throws Throwable{	
		try{
		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
		if(paymentType.equalsIgnoreCase("Credit Card")){
				for(int i=0;i<paymentss.size();i++){
					if(paymentss.get(i).getText().contains("Credit Card")
					||paymentss.get(i).getText().contains("Kredi Kartı")
					||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
		}
		Reporter.SuccessReport("Selecting payment type", paymentType+" button cliked successfully");
		}catch (Exception e){
			Reporter.failureReport("Selecting payment type", paymentType+" button click failed");
		}
	}
	
	public static void enterCardDetails(String CardType) throws Throwable{
		try{
			System.out.println("CardType : "+CardType);
		ExcelReader xls = new ExcelReader(configProps.getProperty("Miscellaneous"),"Cards");		
		String Number = xls.getCellValue(CardType, "Number");
		System.out.println("Card Number : "+Number);
		String Name = xls.getCellValue(CardType, "Name");
		System.out.println("Name : "+Name);
		String expMonth= xls.getCellValue(CardType, "Expmonth");
		System.out.println("Exp month : "+expMonth);
		String expYear= xls.getCellValue(CardType, "Expyear");
		System.out.println("Exp Year : "+expYear);
		String cvv	 = xls.getCellValue(CardType, "CVV");
		
			
		waitforElement(BookingPageLocators.cardNumber);
		type(BookingPageLocators.cardNumber,Number,"Card Number");
		type(BookingPageLocators.cardName,Name,"Card Holder Name");
		click(BookingPageLocators.expMonth,"Expiry Month");
		//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
		driver.findElement(By.xpath("//*[@role='option']["+expMonth+"]")).click();
		//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
		click(BookingPageLocators.expYear,"Expiry Year");
		System.out.println("//div[contains(text(),'"+expYear+"')]");
		driver.findElement(By.xpath("//div[contains(text(),'"+expYear+"')]")).click();
		//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
		type(BookingPageLocators.cvvNum,cvv,"cvv");
		projectUtilities.clickContinueBtn();	
		Reporter.SuccessReport("Entering Card details", "Card details entered successfully");
		}catch(Exception e){
			Reporter.failureReport("Entering Card details", "Card details Could not be entered");
		}
	}
	
	public static void submit3Dsecurepin() throws Throwable{
		try{
		if(isElementDisplayedTemp(BookingPageLocators.pasword)==true)
		{
			type(BookingPageLocators.pasword, "1234", "Password");
			click(BookingPageLocators.ccSubmit,"Submit Button");
			if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
				click(BookingPageLocators.ok, "OK");}
			Reporter.SuccessReport("Entering 3D secure Pin ", "3D Secure Pin entered successfully");
		}
		else{System.out.println("No Secure Page");
			Reporter.SuccessReport("Entering 3D secure Pin ", "No Secure Pin Page Displayed");
			}		
		}catch(Exception e){
			Reporter.failureReport("Entering 3D secure Pin ", "3D Secure pin entry failed");
		}
	}
	
	
	public void nasmilespayment(String Username,String Password) throws Throwable {
		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		System.out.println(paymentType);		
		List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
		for(int i=0;i<paymentss.size();i++)
		{
			if(paymentss.get(i).getText().contains("naSmiles"))
			{
				paymentss.get(i).click();
				Thread.sleep(1000);
				break;
			}
		}
		type(BookingPageLocators.naSmileId,Username, "naSmileID");
		type(BookingPageLocators.naSmilepwd,Password, "naSmilePwd");
		click(BookingPageLocators.signIn_lnk, "SignIn");
		Thread.sleep(2000);
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true)
		{
			System.out.println("NO Sufitiant points");
		}
		else
		{
		click(BookingPageFlow.redeem, "Redeem");
		Thread.sleep(2000);
		if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
			click(BookingPageLocators.ok, "OK");
			payment("Credit Card", "");			
			}
		}
	}
	
	public static boolean payment(String paymentType,String value) throws Throwable 
	{		
 		waitforElement(BookingPageLocators.paymentTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		System.out.println(paymentType);
		
		if(paymentType.equalsIgnoreCase("Credit Card"))
			{
				List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++){
					if(paymentss.get(i).getText().contains("Credit Card")
					||paymentss.get(i).getText().contains("Kredi Kartı")
					||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
						paymentss.get(i).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						break;
					}
				}
				waitforElement(BookingPageLocators.cardNumber);
				type(BookingPageLocators.cardNumber,configProps.getProperty("cardNumber").trim(),"Card Number");
				type(BookingPageLocators.cardName,configProps.getProperty("cardHolderName"),"Card Holder Name");
				click(BookingPageLocators.expMonth,"Expiry Month");
				//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
				driver.findElement(By.xpath("//a/div[contains(text(),'"+configProps.getProperty("expMM")+"') or ./text()='يناير' or ./text()='Ocak']")).click();
				//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
				click(BookingPageLocators.expYear,"Expiry Year");
				driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expYYYY")+"']")).click();
				//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
				type(BookingPageLocators.cvvNum,configProps.getProperty("cvv"),"cvv");
			}
		
			else if(paymentType.equalsIgnoreCase("Voucher"))
			{
				type(BookingPageLocators.voucherNum,configProps.getProperty("voucher"),"Voucher");
				click(BookingPageLocators.retrieveVoucher, "Retrieve Voucher");
			} 
		
			else if(paymentType.equalsIgnoreCase("SADAD"))
			{
		
				List<WebElement>  paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().equalsIgnoreCase("SADAD"))
					{
						paymentss.get(i).click();					
						break;
					}
				}
			}
		
			else if(paymentType.equalsIgnoreCase("Nas"))
			{
				List<WebElement> payments = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<payments.size();i++)
				{
					if(payments.get(i).getText().contains("Nas Credit"))
					{
						payments.get(i).click();
						if(isElementPresent(BookingPageLocators.ipt_pnr)==true){
							type(BookingPageLocators.ipt_pnr, value, "PNR");
							Thread.sleep(3000);
							click(BookingPageLocators.Retrive, "Retrive");
							Thread.sleep(5000);
							}
						String totaldue = getText(BookingPageLocators.totaldue, "TotalDue");
						System.out.println(totaldue);
						String[] due = totaldue.split("\\s");
						String tempdue = due[due.length-1].split("\\.")[0];						
						tempdue = projectUtilities.removeSpecialchars(tempdue);
						int pay = Integer.parseInt(tempdue);
						System.out.println("Pay -" + pay);
						String creditbalance = getText(BookingPageLocators.creditbal, "Credit Balance");
						System.out.println(creditbalance);
						String[] credit = creditbalance.split("\\s");
						String tempcredit = credit[credit.length-1].split("\\.")[0];	
						tempcredit = projectUtilities.removeSpecialchars(tempcredit);
						int creditbal =Integer.parseInt(tempcredit);
						System.out.println("Creditbal - "+creditbal);
						if(creditbal<=pay){
						type(BookingPageLocators.Amount, credit[credit.length-1], "Amount");
						type(BookingPageLocators.Amount, credit[credit.length-1], "Amount");
						}else{
							type(BookingPageLocators.Amount, due[due.length-1], "Amount");
						}
						break;				
					}
				}

			}
		
			else if(paymentType.equalsIgnoreCase("Agency Payment"))
			{
				List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Agency Payment"))
					{
						paymentss.get(i).click();					
						break;
					}
				}
			}
		
			else if(paymentType.equalsIgnoreCase("Hold Booking"))
			{
				List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
				for(int i=0;i<paymentss.size();i++)
				{
					if(paymentss.get(i).getText().contains("Hold Booking"))
					{
						paymentss.get(i).click();					
						break;
					}
				}
			}
			
		
		if(driver.findElement(BookingPageLocators.ccCheckbox).isSelected()==true)
			{
				System.out.println("Terms and conditions already Checked");
			}
			else
			{
			click(BookingPageLocators.ccCheckbox, "Terms & Conditions");
			}
			Thread.sleep(2000);
			
			
		//Click continue on payment page
			projectUtilities.clickContinueBtn();
		
		//handling error pop-up
		if(isElementDisplayedTemp(BookingPageLocators.Error)==true && paymentType.equalsIgnoreCase("SADAD"))
			{
				click(BookingPageLocators.ok, "OK");
				Reporter.SuccessReport("Verifing SADAD", "Payment through SADAD not posible");
			
			}
			else
			{
				System.out.println("No Error Message");
			}
		
		//handling alert pop-up
		if(isElementDisplayedTemp(BookingPageLocators.ok)==true)
			{
				click(BookingPageLocators.ok, "OK");				
			}
			else
			{
				System.out.println("No Alert");
			}
		
		//handling 3D secure page
		if(paymentType.equalsIgnoreCase("Credit Card"))
			{
				boolean flag = false;
				Thread.sleep(5000);
				List<WebElement> frames = driver.findElements(By.tagName("iframe"));
				for(WebElement frame:frames){
					System.out.println(frame.getAttribute("id"));
					if(frame.getAttribute("id").equalsIgnoreCase("authWindow")){
						driver.switchTo().frame("authWindow");
						driver.manage().timeouts().implicitlyWait(5000,TimeUnit.MILLISECONDS);
						if(isElementPresent(BookingPageLocators.pasword)==true)
						{
							type(BookingPageLocators.pasword, "1234", "Password");
							click(BookingPageLocators.ccSubmit,"Submit Button");
							if(isElementDisplayedTemp(BookingPageLocators.ok)==true){
								click(BookingPageLocators.ok, "OK");
								//payment("Credit Card", "");								
							}
							flag = true;
							break;
						}
						}
					}										
				if(flag== false)
				{
				System.out.println("No Secure Page");
				}
			
		}
			
		
	
	//Code to add invalid card detail
	
	if(paymentType.equalsIgnoreCase("invalidcc"))
		{
			List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
			for(int i=0;i<paymentss.size();i++){
				if(paymentss.get(i).getText().contains("Credit Card")
						||paymentss.get(i).getText().contains("Kredi Kartı")
						||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
					paymentss.get(i).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
					break;
				}
			}
			waitforElement(BookingPageLocators.cardNumber);
			type(BookingPageLocators.cardNumber,configProps.getProperty("InvalidcardNumber").trim(),"Card Number");
			type(BookingPageLocators.cardName,configProps.getProperty("cardHolderName"),"Card Holder Name");
			click(BookingPageLocators.expMonth,"Expiry Month");
			//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
			driver.findElement(By.xpath("//a/div[contains(text(),'"+configProps.getProperty("expMM")+"') or ./text()='يناير' or ./text()='Ocak']")).click();
			//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
			click(BookingPageLocators.expYear,"Expiry Year");
			driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expYYYY")+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
			type(BookingPageLocators.cvvNum,configProps.getProperty("cvv"),"cvv");
		}
	
	if(paymentType.equalsIgnoreCase("wrongcc"))
		{
			List<WebElement> paymentss = driver.findElements(BookingPageLocators.paymentType);
			for(int i=0;i<paymentss.size();i++){
				if(paymentss.get(i).getText().contains("Credit Card")
						||paymentss.get(i).getText().contains("Kredi Kartı")
						||paymentss.get(i).getText().contains("البطاقات الإئتمانية")){
					paymentss.get(i).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
					break;
				}
			}
			waitforElement(BookingPageLocators.cardNumber);
			type(BookingPageLocators.cardNumber,configProps.getProperty("MasterCrdNumber").trim(),"Card Number");
			type(BookingPageLocators.cardName,configProps.getProperty("cardHolderName"),"Card Holder Name");
			click(BookingPageLocators.expMonth,"Expiry Month");
			//	driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expMM")+"' OR text()='يناير']")).click();
			driver.findElement(By.xpath("//a/div[contains(text(),'"+configProps.getProperty("expMM")+"') or ./text()='يناير' or ./text()='Ocak']")).click();
			//	selectValueFromDropDown(BookingPageLocators.selectExpMonth, "Expiry Month", configProps.getProperty("expMM"));
			click(BookingPageLocators.expYear,"Expiry Year");
			driver.findElement(By.xpath("//a/div[text()='"+configProps.getProperty("expYYYY")+"']")).click();
			//selectValueFromDropDown(BookingPageLocators.selectExpYear, "Expiry Year", configProps.getProperty("expYYYY"));
			type(BookingPageLocators.cvvNum,configProps.getProperty("cvv"),"cvv");
		}
	
		return true;
	}


}
