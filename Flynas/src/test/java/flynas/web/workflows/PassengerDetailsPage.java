package flynas.web.workflows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import flynas.web.testObjects.BookingPageLocators;

public class PassengerDetailsPage<RenderedWebElement> extends BookingPageLocators {
	
	public String[] inputPassengerDetails(String flightType, String totalPass, String nationality,String travelDoc,
			String docNum, String naSmiles, String mobileNum, String emailId,String fname,String lname,String payment2) 
							throws Throwable{
				/*WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(BookingPageLocators.loadimg)));*/
				waitforElement(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(1))));
				waitUtilElementhasAttribute(BookingPageLocators.body);
				String lastname = null,firstname=null;
				String[] Passengername = null;
				Integer min=0,max=0;
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				try{
					for(Integer count = 1; count<=Integer.valueOf(totalPass); count++){
						if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Adult")){
							min = 14; max = 19;
						} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Child")){
							min = 5; max = 9;
						} else if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Infant")){
							min = 1; max = 2;
						}
						
						 				
						click(By.xpath(BookingPageLocators.title.replace("#", String.valueOf(count))),"Title");
						
					
						click(By.xpath(BookingPageLocators.titleSelect.replace("#", String.valueOf(count))), "Title");
					
						if(payment2.equalsIgnoreCase("Nas"))
						{
							type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), fname, "First Name");

						}
						else if(payment2.equalsIgnoreCase("naSmile"))
						{
								type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), fname, "First Name");
						}
						else
						{
						//Thread.sleep(1000);
						firstname = randomString(8);
						type(By.xpath(BookingPageLocators.fName.replace("#", String.valueOf(count))), firstname, "First Name");
						}
						if(payment2.equalsIgnoreCase("Nas"))
						{
							type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lname, "Last Name");

						}
						else if(payment2.equalsIgnoreCase("naSmile"))
						{
							type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lname, "Last Name");
						}
						else
						{
						lastname = randomString(5);
						type(By.xpath(BookingPageLocators.lName.replace("#", String.valueOf(count))), lastname, "Last Name");
						}
						
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,225)", "");
						
						click(By.xpath(BookingPageLocators.dd.replace("#", String.valueOf(count))), "DD");
						//Thread.sleep(3000);
						click(By.xpath(BookingPageLocators.selectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "DD");
						
						click(By.xpath(BookingPageLocators.mm.replace("#", String.valueOf(count))), "MM");
						//Thread.sleep(3000);
						click(By.xpath(BookingPageLocators.selectMM.replace("#", String.valueOf(count))+randomNumericString()+"]"), "MM");
						
						click(By.xpath(BookingPageLocators.yyyy.replace("#", String.valueOf(count))), "YYYY");
						//Thread.sleep(3000);
						if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").contains("Infant"))
						{
							List<WebElement> year =driver.findElements(By.xpath(BookingPageLocators.selectyyinfant.replace("#", String.valueOf(count))));
							year.get(1).click();
						}
						else
						{
							executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@class='pass_tab']/div["+count+"]//descendant::div[@class='dob_conyear']/div/ul/li//descendant::div[@class='ui-select-choices-row']["+randomNumber(min,max)+"]")));
							//click(By.xpath(BookingPageLocators.selectYYYY.replace("#", String.valueOf(count))+randomNumber(min,max)+"]"), "YYYY");
						}
						
						click(By.xpath(BookingPageLocators.nation.replace("#", String.valueOf(count))), "Nationality");
						//Thread.sleep(3000);
						executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[text()='"+nationality+"']")));
						//click(By.xpath("//div[text()='"+nationality+"']"), "Natioanlity");
						//selectValueFromDropDown(By.xpath(BookingPageLocators.selectNation.replace("#", String.valueOf(count))), "Nationality", nationality);
						
						
						if(flightType.equalsIgnoreCase("Domestic")){
							//assertText(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))), "National ID Card");
						}
						else{
							//assertText(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))), "Passport");
						}
						//click(BookingPageLocators.travelDoc,"Travel Document");
						//click(By.xpath("//div[text()='"+travelDoc+"']"), "Travel Document");
						// - no need selectValueFromDropDown(BookingPageLocators.selecttravelDoc, "Travel Document", travelDoc);
						//type(BookingPageLocators.inputDoc, docNum, "Document Number");
						
						if(travelDoc.equalsIgnoreCase("National ID Card"))
						{
							click(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))),"Travel Document");
							click(By.xpath("//div[text()='"+travelDoc+"']"), "Travel Document");
							type(By.xpath(BookingPageLocators.inputDoc.replace("#", String.valueOf(count))), randomString(10), "Document Number");
						}
						else if (travelDoc.equalsIgnoreCase("Passport"))
						{	
							click(By.xpath(BookingPageLocators.travelDoc.replace("#", String.valueOf(count))),"Travel Document");
							click(By.xpath("//div[text()='"+travelDoc+"']"), "Travel Document");
								
							//Thread.sleep(3000);
							click(By.xpath(BookingPageLocators.ppNumber.replace("#", String.valueOf(count))), "Passport text box");
							type(By.xpath(BookingPageLocators.ppNumber.replace("#", String.valueOf(count))), randomString(10), "Passport Number");
							
							click(By.xpath(BookingPageLocators.ppExpDD.replace("#", String.valueOf(count))), "DD");
							//Thread.sleep(3000);
							click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "DD");	
							
							click(By.xpath(BookingPageLocators.ppExpMM.replace("#", String.valueOf(count))), "MM");
							//Thread.sleep(3000);
							click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumericString()+"]"), "MM");
							
							click(By.xpath(BookingPageLocators.ppExpYY.replace("#", String.valueOf(count))), "YYYY");
						//	Thread.sleep(3000);
						//	click(By.xpath(BookingPageLocators.ppSelectDD.replace("#", String.valueOf(count))+randomNumber(min,max)+"]"), "YYYY");
						//	click(By.xpath(BookingPageLocators.ppSelectDD+randomNumber(min,max)+"]"), "YYYY");
							
							executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//ul/li/descendant::div[@class='ui-select-choices-row']["+randomNumber(min,max)+"]")));
						}
					
						if(getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title").equalsIgnoreCase("Infant 1"))
						{
							System.out.println("No Smily for  "+ getText(By.xpath(BookingPageLocators.passType.replace("#", String.valueOf(count))), "Passenger Title"));
							
						}
						else
						{		System.out.println("nasmiles : "+naSmiles);
								if(!naSmiles.equalsIgnoreCase("") && !naSmiles.equalsIgnoreCase("Value"))
								type(By.xpath(BookingPageLocators.naSmiles.replace("#", String.valueOf(count))), naSmiles, "na Smiles");
						}
					
					}
					if(mobileNum!="")
						type(BookingPageLocators.mobileNum, mobileNum, "Mobile Number");
					if(emailId!="")
						type(BookingPageLocators.emailAdd, emailId, "Email Address");
					projectUtilities.clickContinueBtn();
					Passengername = new String[2];
					Passengername[0] =firstname;
					Passengername[1] =lastname;
					 return Passengername;
				}catch(Exception e){
					e.printStackTrace();
					return Passengername;
				}
				
			}
	
	public void continueOnPassengerDetails() throws Throwable{
		waitforElement(BookingPageLocators.passengerDetailsTittle);
		waitUtilElementhasAttribute(BookingPageLocators.body);
		projectUtilities.clickContinueBtn();
	}

}
