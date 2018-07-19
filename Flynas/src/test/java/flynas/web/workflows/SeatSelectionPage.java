package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class SeatSelectionPage<RenderedWebElement> extends BookingPageLocators {
	
	public static void selectallSeats(String seatSelect,String totalpass,String triptype) throws Throwable {
		waitforElement(BookingPageLocators.selectseattittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement> Flights = driver.findElements(By.xpath("//ul[@class='nav nav-tabs']/li"));
		List<WebElement> ele = driver.findElements((By.xpath("//div[contains(@class, 'seatpassrow')]"))); 
		if(ele.size()==0)
		{
			projectUtilities.clickContinueBtn();
		}
		else
		{
			int k;
			List<WebElement> extlegseats = driver.findElements(BookingPageLocators.selExLegSeat);
			List<WebElement> busiseats = driver.findElements(BookingPageLocators.selbusSeat);
			int loop=0;
			for(int j=0;j<Flights.size();j++){
				for(int i=0;i<Integer.parseInt(totalpass);i++){
					if(seatSelect.equalsIgnoreCase("Extra Leg Room")){
						if(Flights.get(j).getAttribute("class").contains("active")){
							for(k=loop;k<extlegseats.size();k++)
							{
								if(extlegseats.get(k).isDisplayed()==true){
									extlegseats.get(k).click();
									Reporter.SuccessReport("Click", "Successfully Click on Extra Leg Room ");
									if(isElementDisplayedTemp(BookingPageLocators.ok)){
										click(BookingPageLocators.ok, "OK");
										waitUtilElementhasAttribute(BookingPageLocators.body);
										
									}	
									loop=loop+1;
									break;
								}else{
									System.out.println("Seat was Already Selected");
									loop=loop+1;
									
								}
																	
								}
							/*if(i==Integer.parseInt(totalpass)-1){
									break;
							}*/	
							
								
							//continue;	
						}
						
					}
					if(seatSelect.equalsIgnoreCase("Business")){
						if(Flights.get(j).getAttribute("class").contains("active")){
							for(k=loop;k<busiseats.size();k++)
							{
								if(busiseats.get(k).isDisplayed()==true){
									busiseats.get(k).click();
									Reporter.SuccessReport("Click", "Successfully Click on Business");
									if(isElementDisplayedTemp(BookingPageLocators.ok)){
										click(BookingPageLocators.ok, "OK");
										
									}	
									loop=loop+1;
									break;
								}else{
									System.out.println("Seat was Already Selected");
									loop=loop+1;
									
								}
																	
								}
							/*if(i==Integer.parseInt(totalpass)-1){
									break;
							}*/	
							
								
							//continue;	
						}
						
				}
				
			}
			
		}
		
			waitUtilElementhasAttribute(BookingPageLocators.body);
			projectUtilities.clickContinueBtn();
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK']"))==true){
			click(By.xpath("//button[text()='OK']"), "OK Button");
			}
			else{
			System.out.println("No alert Present");
			}
		}
	}
	public static void removeallseats(String seatSelect,String totalpass,String triptype) throws Throwable{
		List<WebElement> remove = driver.findElements(BookingPageLocators.Remove);
		List<WebElement> flights = driver.findElements(BookingPageLocators.flightsInSeat);
		flights.get(0).click();
		int j=1,loop=0;
		for(int i=loop;i<remove.size();i++){
			if(remove.get(i).isDisplayed()==false){
				flights.get(j).click();
				j=j+1;
				remove.get(i).click();
				loop=loop+1;
				waitUtilElementhasAttribute(BookingPageLocators.body);
			}else{
				remove.get(i).click();
				loop=loop+1;
				waitUtilElementhasAttribute(BookingPageLocators.body);
			}
		}
		List<WebElement> seatinsummary = driver.findElements(BookingPageLocators.seatInsummary);
		if(seatinsummary.size()==0){
			Reporter.SuccessReport("Verifing Seat Removing", "Successfully Removed");
		}
			
	}
	
	public static boolean selectSeat(String seatSelect, String bookingtype) throws Throwable {
		waitForElementPresent(BookingPageLocators.selectseattittle, "SelectSeatTittle");
		waitUtilElementhasAttribute(BookingPageLocators.body);	
		List<WebElement> Flights = driver.findElements(By.xpath("//div[@class='tabwrap']/ul/li/a"));
		if(bookingtype.equalsIgnoreCase("CodeShare")||bookingtype.equalsIgnoreCase("PartcodeShare"))
		{
			projectUtilities.clickContinueBtn();
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK']"))){
				click(By.xpath("//button[text()='OK']"), "OK Button");
			}
			else
			{
				System.out.println("No Alert Present");
			}

		}
		else
		{	
					
			for(int i=0;i<Flights.size();i++)
			{			
				Thread.sleep(3000);
				if(seatSelect.equalsIgnoreCase("Extra Leg Room"))
					{					
					List<WebElement> seats = driver.findElements(BookingPageLocators.selExLegSeat);
						try{
							for(int k=1;k<seats.size();k++)
							{
								try{
									seats.get(k).click();
									System.out.println("Extra  leg romm seat no " + k + "selected");
									break;
									}
								catch(Exception e){
								System.out.println("Extra  leg romm seat no " + k + "is not available");
								}
							}
						}
						catch(Exception e){
							System.out.println("Unable to click extra leg room seat ");
							
							}
					}
					
				else if(seatSelect.equalsIgnoreCase("Premium"))
					{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selPremSeat));
					List<WebElement> seats = driver.findElements(BookingPageLocators.selPremSeat);
					try{
						for(int k=1;k<seats.size();k++)
						{
							try{
								seats.get(k).click();
								System.out.println("Premium seat no " + k + "selected");
								break;
								}
							catch(Exception e){
							System.out.println("Premium seat no " + k + "is not available");
							}
						}
					}
					catch(Exception e){
						System.out.println("Unable to click Premium seat ");
						
						}
					}				
				
				
				else if(seatSelect.equalsIgnoreCase("Upfront"))
					{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selUpfrontSeat));
					List<WebElement> seats = driver.findElements(BookingPageLocators.selUpfrontSeat);
					try{
						for(int k=1;k<seats.size();k++)
						{
							try{
								seats.get(k).click();
								System.out.println("Upfront seat no " + k + "selected");
								break;
								}
							catch(Exception e){
							System.out.println("Upfront seat no " + k + "is not available");
							}
						}
					}
					catch(Exception e){
						System.out.println("Unable to click Upfront seat ");
						
						}
					}
					
				
				else if(seatSelect.equalsIgnoreCase("Standard"))
					{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selStdSeat));
					List<WebElement> seats = driver.findElements(BookingPageLocators.selStdSeat);
					try{
						for(int k=1;k<seats.size();k++)
						{
							try{
								seats.get(k).click();
								System.out.println("Upfront seat no " + k + "selected");
								break;
								}
							catch(Exception e){
							System.out.println("Upfront seat no " + k + "is not available");
							}
						}
					}
					catch(Exception e){
						System.out.println("Unable to click Upfront seat ");
						
						}
					}
				
					
				else if(seatSelect.equalsIgnoreCase("Business"))
					
					{
						//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(BookingPageLocators.selbusSeat));
						List<WebElement> seats = driver.findElements(BookingPageLocators.selbusSeat);
						try{
							for(int k=1;k<seats.size();k++)
							{
								try{
									seats.get(k).click();
									System.out.println("Upfront seat no " + k + "selected");
									break;
									}
								catch(Exception e){
								System.out.println("Upfront seat no " + k + "is not available");
								}
							}
						}
						catch(Exception e){
							System.out.println("Unable to click Upfront seat ");
							
							}
					}
					
								
				if(isElementDisplayedTemp(By.xpath("//button[text()='OK'  or ./text()='موافق' or ./text()='Tamam']")))
				{
					click(By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']"), "OK Button");
					Thread.sleep(5000);
					
				}
				else
				{
					System.out.println("No Ok");
				}
			}
			
			projectUtilities.clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);	
			if(isElementDisplayedTemp(By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']")))
			{
				click(By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']"), "OK Button");
			}
			else
			{
				System.out.println("No Alert Present");
			}
		
		}
		return true;
	}
	
	public static void continueOnSeatSelection() throws Throwable{
		waitforElement(BookingPageLocators.selectseattittle);
		//closeOverlay();			
		if(isElementDisplayedTemp(BookingPageLocators.selectseattittle)==true){
			projectUtilities.clickContinueBtn();
			if(isElementDisplayedTemp(BookingPageLocators.ok)){
			click(BookingPageLocators.ok, "OK");
			}
		}else{
			System.out.println("No Seat Page");
		}
	}


}
