package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ctaf.utilities.Reporter;

import flynas.web.testObjects.BookingPageLocators;

public class ExtrasPage<RenderedWebElement> extends BookingPageLocators {
	
	public void WaittillPageLoad() throws InterruptedException{
		waitUtilElementhasAttribute(BookingPageLocators.body);		
	}

	
	public Float getfee(String category) throws Throwable{
		WaittillPageLoad();
		String[] fee = getText(BookingPageLocators.fee(category), category+" fare").split("\\s+");	
		return Float.parseFloat(fee[1]);	
	}
	
	public void verifyFriendsFee(int totalpsngrs,String Triptype) throws Throwable{
		WaittillPageLoad();
		int feexpctd;
		int feeaplied = Math.round(getfee("Friends"));
		if(Triptype.equalsIgnoreCase("Domestic")){
			feexpctd  = totalpsngrs*Integer.parseInt(configProps.getProperty("Travelprntrdom"));
			if(feeaplied==feexpctd)
				Reporter.SuccessReport("Verifying Friends travel fee", "Friends travel fee "+getfee("Friends Travel")+" as expected");
			else
			Reporter.failureReport("Verifying Friends travel fee", "Friends travel fee verification failed, expected - "+feexpctd+"Found - "+feeaplied);
			}
		else{
			feexpctd  = totalpsngrs*Integer.parseInt(configProps.getProperty("TravelprntIntl"));
			if(feeaplied==feexpctd)
				Reporter.SuccessReport("Verifying Friends travel fee", "Friends travel fee "+getfee("Friends Travel")+" as expected");
			else
			Reporter.failureReport("Verifying Friends travel fee", "Friends travel fee verification failed, expected - "+feexpctd+"Found - "+feeaplied);
			}
		}
	
	public boolean inputExtras(String charity) throws Throwable{
		/*WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(BookingPageLocators.loadimg)));		*/
	//	waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		//scrolling to find charity field
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.selectCharity));			
				
		if (Integer.parseInt(charity)>1){
			
			if(isElementDisplayedTemp(By.xpath("//h1[contains(text(),'Charity donation')]"))){
				if(driver.findElement(BookingPageLocators.selectCharity).isEnabled()==true){
					click(BookingPageLocators.selectCharity,"Charity");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(By.xpath("//ul[@role='listbox']/li/div/a/div[contains(text(),'"+charity+"')]"), "Charity Amount");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(BookingPageLocators.selectCharity,"Charity");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					click(By.xpath("//ul[@role='listbox']/li/div/a/div[contains(text(),'0.00')]"), "Charity Amount");
					waitUtilElementhasAttribute(BookingPageLocators.body);
					if(isElementDisplayedTemp(BookingPageLocators.otherfeeinSummary)==false){
						Reporter.SuccessReport("Verifing Remove Charity", "Successfully Removed");
					}
					projectUtilities.clickContinueBtn();
					waitUtilElementhasAttribute(BookingPageLocators.body);
				}else{
					System.out.println("Charity is Disabled");
				}
			
			
			}
		}
		else
		{			
			projectUtilities.clickContinueBtn();
			waitUtilElementhasAttribute(BookingPageLocators.body);		
		}
			return true;
		
	}
	
	
	
	public void Baggage(String bookingtype) throws Throwable
	{	
		waitUtilElementhasAttribute(BookingPageLocators.body);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.Adult_baggagae));
		
		//explicityWait(BookingPageLocators.Adult_baggagae, "Baggage");
		
		if(bookingtype.equalsIgnoreCase("CodeShare")||bookingtype.equalsIgnoreCase("PartcodeShare"))
		{
			//Need not perform any action
			//projectUtilities.clickContinueBtn();	
		}
		else
		{		
		List<WebElement>  Adults = driver.findElements(BookingPageLocators.Adult_baggagae);
		for(int i=0;i<Adults.size();i++)
		{
			String value=Adults.get(i).getText();
			if(value.equalsIgnoreCase("Adult 1"))
			{
				Adults.get(i).click();
				ImplicitWait();
				List<WebElement>  Baggage_weight = driver.findElements(BookingPageLocators.Baggage_weight);
				for(int j=1;j<=Baggage_weight.size();j++)
				{
					Baggage_weight.get(j).click();
					ImplicitWait();
					break;

				}
				

			}
			else
				if(value.equalsIgnoreCase("Child 1"))
				{
					Adults.get(i).click();
					ImplicitWait();
					List<WebElement>  Baggage_weight = driver.findElements(BookingPageLocators.Baggage_weight);
					for(int j=1;j<=Baggage_weight.size();j++)
					{
						Baggage_weight.get(j).click();
						ImplicitWait();
						break;
					}

					
				}
			}				
		}
		
	}	
	
	public void Select_A_Meal() throws Throwable
	{
	//	waitforElement(BookingPageLocators.baggagetittle);
		if(isElementDisplayedTemp(BookingPageLocators.InFlightMeal)==true)
		{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.InFlightMeal));
		List<WebElement> flights = driver.findElements(BookingPageLocators.flightsinSelectmeal);
		List<WebElement> passengers = driver.findElements(BookingPageLocators.passengersToSelectMeal);
		List<WebElement> select = driver.findElements(BookingPageLocators.select);
		List<WebElement> error = driver.findElements(BookingPageLocators.mealError);
		List<WebElement> meal =driver.findElements(BookingPageLocators.meal);
		int i=1, j=0, k=0,l=0 ;
			if(error.size()==0){
				for(l=0;l<flights.size();l++){
					flights.get(l).click();
					passengers = driver.findElements(BookingPageLocators.passengersToSelectMeal);
					System.out.println(passengers.size());
					for(j=0;j<passengers.size()/flights.size();j++){					
							passengers.get(j).click();
							select.get(k).click();
							Reporter.SuccessReport("Selecting Meal", "Meal Selected");
							waitUtilElementhasAttribute(BookingPageLocators.body);
						}						
					j=0;
				}
			}else{
				
				if(error.size()>0){
					int a=0;
					for(int f=0;f<flights.size();f++){
					flights.get(f).click();
									
					if(error.get(a).isDisplayed()==true){
						if(error.size()==1){
							a=0;
						}else{
							a=a+1;
						}
						Reporter.SuccessReport("Selecting Meal", "No Meal Available to select"+f);
					
					}else{
						for(int p=0;p<passengers.size();p++){
							if(p>passengers.size()){
								break;
							}
							if(passengers.get(p).isDisplayed()==false){
									break;
							}		
						
						passengers.get(p).click();
						select.get(k).click();
						waitUtilElementhasAttribute(BookingPageLocators.body);
						Reporter.SuccessReport("Selecting Meal", "Selected");
						k=k+2;
						waitUtilElementhasAttribute(BookingPageLocators.body);
					}					
					}
					
				}
			}
			}	
		}else{
			System.out.println("NO Meal Available in this Route");
			Reporter.SuccessReport("Select meal", "NO Meal Available in this Route");
		}removeMeal();
	}
	
	public void removeMeal() throws Throwable{
		
		List<WebElement> flights = driver.findElements(BookingPageLocators.flightsinSelectmeal);
		List<WebElement> remove = driver.findElements(BookingPageLocators.Remove);
		flights.get(0).click();
		for(int i=0;i<flights.size();i++){
			for(int j=0;j<remove.size();j++){
				if(remove.get(j).isDisplayed()==true){
					remove.get(j).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
				}else{
					j=j-1;
					flights.get(i+1).click();
					i=i+1;
				}
			}
		}
		List<WebElement> mealSummary = driver.findElements(BookingPageLocators.mealInSummary);
		if(mealSummary.size()==0){
			Reporter.SuccessReport("Unselect Meal", "Successfully Unselected");
			
		}
	}
	
	public void Select_lounge() throws Throwable
	{
		if(isElementPresent(BookingPageLocators.Loung)==true)
		{((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.Loung));
			List<WebElement> allPassengers=driver.findElements(BookingPageLocators.allPassengers_Loung);
			for(int i=0;i<allPassengers.size();i++)
			{
				allPassengers.get(i).click();
				waitUtilElementhasAttribute(BookingPageLocators.body);
				Reporter.SuccessReport("Selecting Lounge", "Successfully Selected");
				
			}
		/*List<WebElement> Loung = driver.findElements(BookingPageLocators.Loung_table);
		
			for(int i=1 ;i<Loung.size()-1;i++)
		{
			if(Loung.get(i).findElement(By.tagName("input")).getAttribute("value")=="true")
			{
				Loung.get(i).findElement(By.tagName("input")).click();
				break;
			}
			else
			{
				Loung.get(i+1).findElement(By.tagName("input")).click();
				break;
			}
		}*/
		}
		
		else
		{
			Reporter.SuccessReport("Selecting Lounge", "No Lounge is available to select");
		}
		//clickContinueBtn();
	}
	
	public void Baggage_Extra(String Triptype) throws Throwable
	{
		waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		boolean flag=true;
		if(Triptype.equalsIgnoreCase("One way"))
		{
			List<WebElement>  passengers = driver.findElements(BookingPageLocators.Adult_baggagae);
			List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
			int loop=0;
			for(int i=0;i<passengers.size();i++)
			{
				
					passengers.get(i).click();
					Thread.sleep(2000);
					List<WebElement>  Baggage_weight = driver.findElements(BookingPageLocators.Baggage_weight);
					for(int j=loop;j<=Baggage_weight.size();j++)
					{
						if(addBaggage.size()!=0)
						{
							addBaggage.get(j).click();
							waitforElement(BookingPageLocators.Adult_baggagae);
							waitUtilElementhasAttribute(BookingPageLocators.body);
							Reporter.SuccessReport("Add Extra Baggage", "Selected");
							loop=j+3;
							break;
						}
						else
						{
							Reporter.SuccessReport("Add Extra Baggage", "No Baggage Available");
						}
					}
				
				
			}
			
	

	}
		else
			
		{ 
			
			int x=0;
			List<WebElement>  flights = driver.findElements(BookingPageLocators.selected_Flights_baggage);
		
			for(int j=0;j<flights.size();j++)
			{
				if(flights.get(j).isDisplayed()==false)
				{
					List<WebElement>  pasenger = driver.findElements(BookingPageLocators.Adult_baggagae);
					pasenger.get(pasenger.size()-1).click();
					j=j-1;
					continue;
					
				}
				System.out.println(flights.get(j)+" "+ ":" +j);
				List<WebElement> Weight= flights.get(j).findElements(By.xpath("following::div[3]/div[1]/div/div/div[2]/a[2]/i"));
			//	List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
				if(Weight.size()==3)
				{   

					Weight.get(x).click();
					waitUtilElementhasAttribute(BookingPageLocators.body);
					Reporter.SuccessReport("Select Extra Baggage", "Selected successfully");
					break;
										
				}
				else
				{
					System.out.println("No Extra Baggage is Available to select");
				}

			}
			
			
			
		}
}
	
	public void validatingRemoveBaggage() throws Throwable
	{
		waitforElement(BookingPageLocators.baggagetittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		boolean flag=true;
		List<WebElement> plusBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
		for(int i=0;i<2;i++){
			plusBaggage.get(i).click();
			waitUtilElementhasAttribute(BookingPageLocators.body);
		}
		List<WebElement> minusBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[1]/i"));
		for(int i=0;i<minusBaggage.size()-1;i++){
			minusBaggage.get(i).click();
			waitUtilElementhasAttribute(BookingPageLocators.body);
		}
		List<WebElement> baginsummary = driver.findElements(BookingPageLocators.baggageInSummary);
		if(baginsummary.size()==0){
			Reporter.SuccessReport("Removing Baggage", "Baggage Removed Successfully");
		}
		/*if(isElementDisplayedTemp(BookingPageLocators.baggageInSummary)==false){
			Reporter.SuccessReport("Validating Remove Baggage Functionality", "Baggage Removed Successfully");
		}else{
			Reporter.failureReport("Validating Remove Baggage Functionality", "Baggage Not Removed");
		}*/
	}
	
	public void addSportsEqpmnt(String Triptype) throws Throwable
	{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		List<WebElement>  passengers = driver.findElements(BookingPageLocators.Adult_baggagae);
		List<WebElement> sportsEqpmntLink = driver.findElements(BookingPageLocators.sportsEqpmntLink);
		
		if(Triptype.equalsIgnoreCase("One way")){
			for(int i=0;i<passengers.size();i++)
			{
				passengers.get(i).click();
				waitforElement(BookingPageLocators.sportsEqpmntLink);
				waitUtilElementhasAttribute(BookingPageLocators.body);
				sportsEqpmntLink.get(i).click();
				List<WebElement> sportsEqpmntdrop = driver.findElements(BookingPageLocators.sportsEqpmtDrop);
				sportsEqpmntdrop.get(i).click();
				click(BookingPageLocators.bicycle, "Bicycle");
				waitUtilElementhasAttribute(BookingPageLocators.body);
			}
		}
		else
		{
			passengers.get(0).click();
			List<WebElement>  flights = driver.findElements(BookingPageLocators.selected_Flights_baggage);
			for(int j=0;j<flights.size();j++){
				if(flights.get(j).isDisplayed()==false)
				{
					List<WebElement>  pasenger = driver.findElements(BookingPageLocators.Adult_baggagae);
					pasenger.get(pasenger.size()-1).click();
					j=j-1;
					continue;
					
				}
				System.out.println(flights.get(j)+" "+ ":" +j);
				WebElement sortsLink= flights.get(j).findElement(By.xpath("following::a[@class='link']"));
			//	List<WebElement> addBaggage = driver.findElements(By.xpath("//div[@class='baggage_box']/div[2]/a[2]/i"));
				if(sortsLink.isDisplayed())
				{   
					sortsLink.click();
					flights.get(j).findElement(By.xpath("following::div[@name='sporteq']")).click();
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingPageLocators.bicycle));
					click(BookingPageLocators.bicycle, "Bicycle");
					waitUtilElementhasAttribute(BookingPageLocators.body);
														
				}
				else
				{
					System.out.println("No Sports Equipment is Available to select");
				}
			}
			
	}
		
	}

	
	
}

