package flynas.web.testObjects;

import org.openqa.selenium.By;

import com.ctaf.accelerators.ActionEngine;

public class BookingPageLocators extends ActionEngine{
	
	//Common
	public static By loadingBar = By.xpath("//div[@class='loading sk-wave']");
	public static By continueBtn = By.xpath("//button[@class='btn btn-primary pull-right']");
	public static By myProfile = By.xpath("//a[contains(text(),' My profile')]");
	public static By loadimg = By.xpath("//div[@class='loading sk-wave']");
	public static By body = By.xpath("//body[@block-ui='main']");
	public static By closepopup = By.xpath("//button[@type='submit']");
	public static By naSmileTaost = By.xpath("//iframe[@id='yief130002']");
	public static By closeTst= By.xpath("//*[@id='yie-close-button-f2ccb518-bf80-582c-b7cc-23a3ba0d9675']");
	public static By closeTstnew= By.xpath("//span[@id='yie-close-button-dc7c9f2b-e5d0-5832-b5e3-53b3ca5e018f']");	
	public static By closetoast = By.xpath("//span[@id='close']");
	public static By alertText = By.xpath("//div[@class='modal-body']/div");
	public static By overlay = By.xpath("//class[contains(text(),'overlay')]");
	public static By faresumrypls = By.xpath("//span[contains(Text(),'Extra')]/../span[2]/a");
	public static By backToHomeBtn = By.xpath("//a[contains(text(),'Back to homepage')]");
	//login page
	public static By email = By.xpath("//input[@name='iptid']");
	public static By password = By.xpath("//input[@name='iptpasswprd']");
	public static By login_btn = By.xpath("//button[@type='submit']");
	public static By JoinNow = By.xpath("//a[contains(text(),'Join now')]");
	public static By here = By.xpath("//a[contains(text(),' here')]");
	//added for insurance fees
		public static By otherfeeinSummaryExpand = By.xpath("//h3[contains(text(),'Summary ')]/following::span[contains(text(),'Other Fees')]/../span/a");
		public static By insuranceFee = By.xpath("//h3[contains(text(),'Summary ')]/following::span[contains(text(),'Insurance')]");
		public static By insuranceFeeAmount = By.xpath("//span[contains(text(),'Insurance')]/following-sibling::span");
		public static By insuranceFeeText=By.xpath("//div[@class='insurance-container']//input[@name='insuranceOption' and @value='yes']/..");

	//booking page &Member dashboard
	public static By manageMyBookings(String PNR){
		return By.xpath("//span[text()='"+PNR+"']/following::a[1]");
	}
	public static By login_lnk = By.xpath("//a[@title='Login']");
	public static By logout_lnk = By.xpath("//a[contains(text(),' Log out')]");
	public static By logout_lnk_TR = By.xpath("//a[contains(text(),'Çıkış')]");
	public static By logout_lnk_AR = By.xpath("//a[contains(text(),'تسجيل الخروج')]");
	public static By friendscout = By.xpath("//a[contains(text(),'Friends')]/span");
	public static By emplogin_lnk = By.xpath("//a[text()='Employee Login']");
	public static By corporatelogin_lnk = By.xpath("//a[text()='Corporate Login']");
	public static By agency_lnk = By.xpath("//a[text()='Agencies Login']");
	public static By agency_Register = By.xpath("//a[text()='Agencies Register']");
	public static By corporate_Register = By.xpath("//a[text()='Corporate Agencies Register']");
	public static By Manage_booking_lnk = By.xpath("//a[text()='Manage Booking']");
	public static By WebCheckIn_lnk = By.xpath("//a[text()='Web Check-in']");
	public static By roundTrip = By.xpath("//label[contains(text(),'Round trip')]");
//	public static By roundTrip = By.xpath("/html/body/div[1]/div[2]/div/div/div/flight-search/div/form/div[1]/div/div/label[1]");
//	public static By oneWay = By.xpath("/html/body/div[1]/div[2]/div/div/div/flight-search/div/form/div[1]/div/div/label[2]");
	public static By bookflighttittle = By.xpath("//h3[contains(text(),'Book a flight')]");
	public static By oneWay = By.xpath("//label[contains(text(),'One way')]");
	public static By multiCity = By.xpath("//label[contains(text(),'Multi-City')]");
	//	public static By multiCity = By.xpath("/html/body/div[1]/div[2]/div/div/div/flight-search/div/form/div[1]/div/div/label[3]");
	public static By bookingpageorigin = By.xpath("//div[@name='origin']/div[@placeholder='Origin']/span[@tabindex='-1']");
	public static By bookingpagetxtorigin = By.xpath("//div[@name='origin']/input[@placeholder='Origin']");
	public static By bookingpageDestination = By.xpath("//div[@name='destination']/div[@placeholder='Destination']/span[@tabindex='-1']");
	public static By bookingpagetxtDestination = By.xpath("//div[@name='destination']/input[@placeholder='Destination']");
//	public static By origin = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div/div");
	public static By origin = By.xpath("//div[@name='origin']");
	public static By txtorigin = By.xpath("//input[@placeholde='Origin' and @aria-owns = 'ui-select-choices-0']");
//	public static By selectOrigin = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div/div/div/ul/li//descendant::div[@class='ui-select-choices-row']");	
	public static By selectOrigin = By.xpath("//div[contains(@class,'ui-select-choices-row')]");	
//	public static By secOrigin = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[4]/div/div/div/div/div");
	public static By secOrigin = By.xpath("//div[@name='origin2']");
	public static By selectSecOrigin = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[4]/div/div/div/div/ul/li//descendant::div[@class='ui-select-choices-row']");
//	public static By secDest = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[4]/div/div[2]/div/div/div");
	public static By secDest = By.xpath("//div[@name='destination2']");
	public static By selectSecDest = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[4]/div/div[2]/div/div/ul/li//descendant::div[@class='ui-select-choices-row']");
	public static By dest = By.xpath("//div[@name='destination']");
//	public static By dest = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[2]/div");
//	public static By selectDest = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[2]/div/div/ul/li//descendant::div[@class='ui-select-choices-row']");
	public static By selectDest = By.xpath("//div[contains(@class,'ui-select-choices-row')]");
//	public static By dpDate = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[3]/div");
	public static By dpDate = By.xpath("//label[contains(text(),'Departure ')]/following::div[2]");
	public static By datePicker = By.xpath("//div[@class='input-group datepicker']/ul/li/div/table");
	public static String selectDate = "//div[@class='input-group datepicker']/ul/li/div/table";
	public static By rtDate = By.xpath("//label[contains(text(),'Return ')]/following::div[2]");
//	public static By rtDate = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[2]/div[5]/div");
	public static By selectRTDate = By.xpath("");
//	public static By adult = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div/div");
	public static By adult = By.xpath("//div[contains(@placeholder,'1 بالغ') or contains(@placeholder,'1 Adult') or contains(@placeholder,'1 Yetişkin')]");
	////*[@type='submit' OR @name='btnReset']  //*[contains(@href,'guru99.com')]
	
	public static By selectAdult = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div/div/div/ul/li//descendant::div[@class='ui-select-choices-row']");
	//public static By child = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div/div[2]");
	public static By child = By.xpath("//div[@placeholder='0 Child']");
	public static By selectChild = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div/div[2]/div/ul/li//descendant::div[@class='ui-select-choices-row']");
	public static By infant = By.xpath("//div[@placeholder='0 Infant']");
	public static By selectInfant = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div/div[3]/div/ul/li//descendant::div[@class='ui-select-choices-row']");
	public static By payWithnaSmile = By.xpath("//input[@name='nasmiles']");
	public static By promo = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div[2]/div[2]/input");
	public static By bookforFriendschkbx = By.xpath("//input[@name='bookfriends']");
	
	public static By Currency_link = By.xpath("//b[contains(text(),'Currency')]");
	public static By currencytyp(String Currency) {
		return By.xpath("//div[contains(text(),'"+Currency+"')]");
	}
	
	//public static By findFlights = By.xpath("//div[@class='main_con']/div[2]//descendant::form/div[3]/div[2]/div/button");
	public static By findFlights = By.xpath("//button[@class='btn btn-primary']");
	public static By myBookings = By.xpath("//span[contains(text(),'My Bookings')]");
	
	
	//Class selection page
	
	//public static By economyOW = By.xpath("//div[@class='main_con']/div/div/div[2]/div/flight-select/div/form/div//descendant::table/tbody//descendant::td[5]/button");
	//public static By economyOW = By.xpath("//div/button[@class='btn selectflightbtn col1']");
	public static By selectflightsection = By.xpath("//h3[contains(text(),' Select your departing flight ') or ./text()=' Kalkis ucusunu sec '  or ./text()=' رحلة الذهاب ']");
	public static By economyOW = By.xpath("//td/button[@class='btn selectflightbtn col1']");
	public static By flexOW = By.xpath("//td/button[@class='btn selectflightbtn col2']");
	public static By busOW = By.xpath("//td/button[@class='btn selectflightbtn col3']");
	public static String rtClassStr= "//div[@class='main_con']/div/div/div[2]/div/flight-select/div/form/div[2]//descendant::table/tbody/tr";
	public static By rtClass = By.xpath("//div[@class='main_con']/div/div/div[2]/div/flight-select/div/form/div[2]//descendant::table/tbody/tr");
	public static By okBtn = By.xpath("//div[@class='modal-dialog']/div/div[3]/button[@class='btn btn-primary']");
	public static By cancelBtn = By.xpath("//div[@class='modal-dialog']/div/div[3]/button[@class='btn btn-warning']");
	public static By standstaff = By.xpath("//button[@class='btn selectflightbtn col2']");
	public static By stafCnformed = By.xpath("//button[@class='btn selectflightbtn col3']");
	public static By flighttable = By.xpath("//table[@class='table flight_table']");
	public static By fee(String category) {
		return By.xpath("//span[contains(text(),'"+category+"')]/../span[2]/b");
	}
	
	public static By continueBundle =By.xpath("//a[contains(text(),'continue') or ./text()='استمر مع اختيارك الحالي']");
	public static By upgradeBundle=By.xpath("//a[contains(text(),'ADD THIS BUNDLE') or ./text()='اضف هذه الباقة']");
	
	public static By priceBeforeChange = By.xpath("//b[contains(text(),'Price before change')]/following::span[1]");
	public static By Totalprice = By.xpath("//span[@class='pull-right total']");
	public static By hldFareBtn = By.xpath("//*[@class='pricelock_con']/table/tbody/tr/td[3]/div/button");
	
	
	//Input Passenger Details
	public static By passengerDetailsTittle = By.xpath("//h1[contains(text(), 'Passenger and Document Details') or ./text()='معلومات المسافر']");
	public static String passType = "//div[@class='pass_tab']/div[#]/h3";
	public static String title = "//div[@class='pass_tab']/div[#]/div/ng-form//descendant::div[@class='dropdowntitlecon']";
	public static String titleSelect = "//div[@class='pass_tab']/div[#]/div/ng-form//descendant::div[@class='dropdowntitlecon']/div/ul/li//descendant::div[@class='ui-select-choices-row']";
	public static String fName = "//div[@class='pass_tab']/div[#]//descendant::input[@name='firstname']";
	public static String lName = "//div[@class='pass_tab']/div[#]//descendant::input[@name='lastname']";
	public static String dd = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_con dob_conday']";
	public static String selectDD = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_con dob_conday']/div/ul/li//descendant::div[@class='ui-select-choices-row'][";
	public static String mm = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_con dob_conmonth']";
	public static String selectMM = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_con dob_conmonth']/div/ul/li//descendant::div[@class='ui-select-choices-row'][";
	public static String yyyy = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_conyear']";
	public static String selectYYYY = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_conyear']/div/ul/li//descendant::div[@class='ui-select-choices-row'][";
	public static String selectyyinfant = "//div[@class='pass_tab']/div[#]//descendant::div[@class='dob_conyear']/div/ul/li/div["
			+ "(@class,'ui-select-choices-row')]";
	public static String nation = "//div[@class='pass_tab']/div[#]//descendant::div[@class='nationdropdown']";
	public static String selectNation = "//div[@class='pass_tab']/div[#]//descendant::div[@class='nationdropdown']/div/ul/li//descendant::div[@class='ui-select-choices-row']";
	
	//public static By travelDoc = By.xpath("//div[@class='traveldocdropdown']/div/div/span/span[2]/span");
	public static String travelDoc = "//booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[1]/div/div/div/span/span[2]/span";
	//public static By selecttravelDoc = By.xpath("//div[@class='traveldocdropdown']/div/ul/li//descendant::div[@class='ui-select-choices-row']");
	//public static String selecttravelDoc = "//booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[2]/input";
	//public static String inputDoc = "//booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[2]/input";
	public static String inputDoc = "//booking-passengers/div/ng-form/div/div[#]";
	//public static By naSmiles = By.xpath("//input[@name='asiamiles']");
	public static String naSmiles = "//booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[*]/div/input";
	
	public static String ppNumber= "/html/body/div[1]/div/div/div[2]/div/passengers-contacts/div/form/booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[3]/input";
	public static String ppExpDD= "/html/body/div[1]/div/div/div[2]/div/passengers-contacts/div/form/booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[4]/div[1]/div/div/span";	
	public static String ppExpMM = "/html/body/div[1]/div/div/div[2]/div/passengers-contacts/div/form/booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[4]/div[2]/div/div/span";
	public static String ppExpYY = "/html/body/div[1]/div/div/div[2]/div/passengers-contacts/div/form/booking-passengers/div/ng-form/div/div[#]/div/ng-form/div/div[2]/div[4]/div[3]/div/div/span";
	public static String ppSelectDD = "//ul/li/descendant::div[@class='ui-select-choices-row'][";
	
	
	public static By countryCode = By.xpath("//div[@placeholder='Country code*']");
	public static By selectCountCode = By.xpath("//div[@name='countrycode']/ul/li//descendant::div[@class='ui-select-choices-row']"); 
	public static By mobileNum = By.xpath("//input[@name='mobilenum']");
	public static By emailAdd = By.xpath("//input[@name='emailaddress']");
	public static By selectPassenger =  By.xpath("//a[contains(text(),'Select passenger')]");
	public static By memberDropdown  = By.xpath("//div[@class='memberdropdown']");
	public static By familyMembers = By.xpath("//ul[@class='ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu']/li/div[@class='ui-select-choices-row']");
	public static By seletcBtn = By.xpath("//button[contains(text(),'Select')]");
	//Extras
	//public static By selectCharity = By.xpath("//span[@tabindex='-1']");
	public static By selectCharity = By.xpath("//label[contains(text(),'Amount options')]/following-sibling::div");
	public static By charitydisable = By.xpath("//label[contains(text(),'Amount options')]/following::div[2]");
	public static By otherfeeinSummary=By.xpath("//h3[contains(text(),'Summary ')]/following::span[contains(text(),'Other Fees')]");
	//Seat Selection
	public static By flightsInSeat = By.xpath("//h1[contains(text(),' Select your seat')]/following-sibling::div[1]/div/div/div/ul/li");
	public static By selectseattittle = By.xpath("//h1[contains(text(),' Select your seat') or ./text()=' إختيار المقعد' or ./text()=' Lütfen koltugunuzu secin']");
	
	public static By selbusSeat = By.xpath("//div[@class='seatmap']/div/div//descendant::div[@class='seatimg type1']");
	//public static By selExLegSeat = By.xpath("//div[@class='seatmap']/div/div//descendant::div[@class='seatimg type2']");
	public static By selExLegSeat = By.xpath("//div[@class='seatimg type2']");
	public static By selPremSeat = By.xpath("//div[@class='seatmap']/div[2]/div//descendant::div[@class='seatimg type3']");
	public static By selUpfrontSeat = By.xpath("//div[@class='seatmap']/div[3]/div//descendant::div[@class='seatimg type4']");
	public static By selExLeg2Seat = By.xpath("//div[@class='seatmap']/div[4]/div//descendant::div[@class='seatimg type2']");
	public static By selStdSeat = By.xpath("//div[@class='seatmap']/div[5]/div//descendant::div[@class='seatimg type5']");
	public static By seatSelTabs = By.xpath("//div[@class='tabwrap']/ul/li");
	public static By seatInsummary = By.xpath("//h3[contains(text(),'Summary ')]/following::span[contains(text(),'Seat selection')]");
	
	//Payment Page
	public static By paymentTittle = By.xpath("//h1[text()=' Review and pay' or ./text()=' Odeme' or ./text()=' الدفع']");
	public static By paymentOptions = By.xpath("//div[@class='pass_tab']/div/div/ul/li");
	public static By paymentName = By.xpath("/a/div");
	public static By cardNumber = By.xpath("//div[@class='pass_tab']//descendant::input[@name='cardNumber']");
	public static By cardName = By.xpath("//div[@class='pass_tab']//descendant::input[@name='cardName']");
	public static By expMonth = By.xpath("//div[@class='pass_tab']//descendant::div[@name='expMonth']");
	public static By selectExpMonth = By.xpath("//div[@class='pass_tab']//descendant::div[@name='expMonth']/ul/li//descendant::div[@class='ui-select-choices-row']");
	public static By expYear = By.xpath("//div[@class='pass_tab']//descendant::div[@name='expYear']");
	public static By selectExpYear = By.xpath("//div[@class='pass_tab']//descendant::div[@name='expYear']/ul/li//descendant::div[@class='ui-select-choices-row']");
	public static By cvvNum = By.xpath("//div[@class='pass_tab']//descendant::input[@name='cvnum']");
	public static By ccCheckbox = By.xpath("//input[@name='paymentterms']");
	public static By voucherNum = By.xpath("//voucher[@form='paymentForm']/div/div/input");
	public static By retrieveVoucher = By.xpath("//voucher[@form='paymentForm']//descendant::button[@class='btn btn-primary']");
	public static By tabCreditCard = By.xpath("//a/div[text()='Credit Card']");
	public static By tabNasCredit = By.xpath("//a/div[text()='Nas Credit']");
	public static By tabSadad = By.xpath("//a/div[text()='SADAD']");
	public static By txtPNR = By.xpath("//div[text()='Booking Reference: ']");
	public static By sadadOP = By.xpath("//sadadop[@form='paymentForm']/div/div/div/input");
	public static By sadadEnter = By.xpath("//sadadop[@form='paymentForm']//descendant::button[@class='btn btn-primary']");
	public static By ccSubmit = By.xpath("//input[@name='UsernamePasswordEntry']");
	public static By helpimg = By.xpath("//a/img");
	public static By paymentType = By.xpath("//div[@class='passblock']");
	public static By ipt_pnr  = By.xpath("//input[@name='pnr']");
	public static By Amount = By.xpath("//input[@name='creditFileAmount']");
	public static By Retrive = By.xpath("//button[contains(text(),'Retrieve')]");
	public static By creditbal = By.xpath("//div[@class='col-md-6'][1]/p");
	public static By totaldue = By.xpath("//span[contains(text(),'Total amount due: ')]/b");
 	public static By pasword = By.xpath("//input[@type='password']");
	public static By Error  = By.xpath("//h3[contains(text(),'Error')]");
	public static By Alert  = By.xpath("//h3[contains(text(),'Alert')]");
	public static By naSmileId = By.xpath("//input[@name='rewardnum']");
	public static By naSmilepwd = By.xpath("//input[@name='rewardspassword']");
	public static By signIn_lnk = By.xpath("//a[contains(text(),'Sign In')]");
	public static By redeem = By.xpath("//span[contains(text(),'Redeem')]");
	
	//Summary Page
	public static By summaryRefNumber = By.xpath("//div[text()='Booking Reference: 'or ./text()=' رقم الحجز: 'or ./text()='Reservasyon: ']/b[@class = 'col_pink']");
	public static By summaryRefNumber_AR_uat = By.xpath("//div[text()=' رقم الحجز: ']/b");
	public static By summaryRefNumber_TR_uat = By.xpath("//div[text()='Reservasyon: ']/b");
	public static By summaryStatus = By.xpath("//div[text()='Status: ']/b");
	public static By summaryStatus_AR_uat = By.xpath("//div[text()='الحالة: ']/b");
	public static By summaryStatus_TR_uat = By.xpath("//div[text()='Durum: ']/b");
	public static By Home = By.xpath("//a[text()='Home']");
	public static By passengername = By.xpath("//table[@class='table confirmtable']/tbody/tr/td[1]");
	public static By passenger(String Passenger)
	{
		return By.xpath("//a[contains(text(),'"+Passenger+"')]");
	}
	public static By passenger_header = By.xpath("//ul[@class='header_menu dropdown-menu']/li[1]/b/a");
	//Summary Page with Edit Flight
	public static By summaryChangeFlight = By.xpath("//div[text()='//a[text()='Change flight']");
	public static By summaryCancelFlight = By.xpath("//a[text()='Cancel flight']");
	public static By modifyExtras = By.xpath("//a[text()='Modify extras']");
	public static By modifySeat = By.xpath("//a[text()='Seat selection']");
	public static By selectFlightstoCancel = By.xpath("//input[@name='cfcheckbox']");
	public static By cancelflightBtn = By.xpath("//button[@class='btn btn-primary']");
	public static By conformCharges = By.xpath("//a[contains(text(),'Click here to confirm changes')]");
	public static By conformedAftercharges = By.xpath("//b[contains(text(),'Confirmed')]");
	public static By cancelled = By.xpath("//b[contains(text(),'Cancelled')]");
	public static By manageMyBookingTittle = By.xpath("//h1[contains(text(),'Manage my booking')]");
	/*
	 * change flight elements 
	 */
	//Change Date
	public static By calender = By.xpath("//div[@class='padcon']");
	public static By btnchngFlight = By.xpath("//a[text()='Change flight']");
	public static By btnpopupchngFlight = By.xpath("//button[text()='Change flight']");
	public static By selflnextbutton = By.xpath("//a[@class='btn_next last']");
	public static By selfltxtNoFlight = By.xpath("//ng-form/div[3]/div[1]/div/div");
	public static By selflCurrentDate = By.xpath("//*[@id='select_departure']/div/ul/li[4]/a/span");
	public static By selflEcoFlight = By.xpath("//div[@class='btn_td col-xs-4']/"
			+ "button[@class = 'btn selectflightbtn col1']");			
	public static By selflightContinue = By.xpath("//button[text()='Continue']");
	public static By popupButtonOk = By.xpath("//button[@class='btn btn-primary']");
	public static By confirmmsg = By.xpath("//a[text()='Click here to confirm changes']");
	public static By paynow = By.xpath("//a[text()='Pay Now']");
	public static By balance = By.xpath("//b[text()='Balance']/following::span[1]");
			
	
	//Search Flight Page
	public static By sfpbookingReference = By.xpath("//input[@name='inputpnr']");
	public static By sfpEmail = By.xpath("//input[@name='inputemail']");
	public static By sfpMoblie = By.xpath("//input[@name='inputmobile']");
	public static By sfpLastName = By.xpath("//input[@name='inputlastname']");
	public static By sfpFindBooking = By.xpath("//span[text()='Find booking']");
	public static By  MandatePrompt = By.xpath("//*[@name='flightsForm2']/div[4]/div");
	public static By ErrorMsg1 = By.xpath("//h3[contains(text(),'Error')]/following::div[1]/div");
	
	//Add extra
	public static By baggagetittle =By.xpath("//h1[contains(text(),'Baggage') or ./text()='الأمتعة 'or ./text()='Kayıtlı Bagaj']");
	public static By Adult_baggagae = By.xpath("//h1[contains(text(),' Baggage') or ./text()='الأمتعة ' or ./text()='Kayıtlı Bagaj']/following-sibling::p/following::div[3]/ul/li/a");
	public static By InFlightMeal = By.xpath("//h1[contains(text(),'In-flight meals')]");
	public static By inflightmeal = By.xpath("//h1[contains(text(),'In-flight meals')]/following-sibling::p/following::div[3]/ul/li/a");
	public static By flightsinSelectmeal = By.xpath("//h1[contains(text(),'In-flight meals')]/following::div[1]/div/div/div/div[1]/ul/li/a");
	public static By passengersToSelectMeal = By.xpath("//div[contains(@class,'btn_con clearfix margin_t15')]/preceding-sibling::div[@class='col-pass active']");
	public static By selectedflightes(int value)
	{
		return By.xpath("//h1[contains(text(),'In-flight meals')]/following::ul["+value+"]/li/a");
	}
	public static By meal = By.xpath("//div[contains(text(),'Meal')]");
	public static By Remove =By.xpath("//a[@class='picon remove_btn']");
	public static By mealInSummary = By.xpath("//h3[contains(text(),'Summary ')]/following::span[contains(text(),'Meals')]");
	public static By select = By.xpath("//a[contains(text(),'Select')]");
	public static By mealError = By.xpath("//div[@class='error_msg']");
			
	public static By Loung = By.xpath("//h1[contains(text(),' Business class lounge')]");
	public static By Loung_table = By.xpath("//table[@class='table bloungetable']/tbody/tr[1]/td");
	public static By allPassengers_Loung =By.xpath("//table[@class='table bloungetable']/tbody/tr[1]/td[2]/label");
	public static By selected_Flights_baggage = By.xpath("//b[contains(text(),'Flight')]");
	
	public static By loung_passengers = By.xpath("//input[@class='class_type ng-pristine ng-untouched ng-valid']");
	
	public static By Baggage_weight = By.xpath("//div[@class='baggage_box']");
	public static By important = By.xpath("//h3[contains(text(),'Important')]");
	public static By ok=By.xpath("//button[text()='OK' or ./text()='موافق' or ./text()='Tamam']");
	public static By Add=By.xpath("//button[text()='Add' or ./text()='' or ./text()='']");
	public static By No=By.xpath("//button[text()='No, thanks' or ./text()='' or ./text()='']");
	
	public static By sportsEqpmntLink = By.xpath("//a[@class='link']");
	public static By sportsEqpmtDrop = By.xpath("//div[@name='sporteq']");
	public static By bicycle = By.xpath("//div[contains(text(),'Bicycle')]");
	
	public static By baggageInSummary = By.xpath("//h3[contains(text(),'Summary ')]/following::span[contains(text(),'Extra baggage')]");
	//checkins
	public static By checkinTitle = By.xpath("//h3[contains(text(),'Please select the passenger(s) you would like to check-in')]");
	public static By checkInNow = By.xpath("//button[@type='submit' or ./text()='Check-in now'or ./text()='أصدر بطاقة صعود الطائرة' or ./text()='Şimdi Check-in Yap']");
	public static By passengers_incheckin = By.xpath("//input[@name='passenger0']");
	public static By passengers_checkterms = By.xpath("//a[text()='Terms and conditions' or ./text()='الشروط و الأحكام' or ./text()='Hüküm ve Koşullar']/preceding-sibling::i/preceding-sibling::input");
	public static By checkinConformation = By.xpath("//h1/div[1]");
	public static By sfpChekin = By.xpath("//button[text()='Check-in now']");
	public static By downloadAllBoardingPasses= By.xpath("//a[@class='btn btn-primary pull-right margin_r10']");
	
	//Agent Portal
	
	public static By BookingRef = By.xpath("//table[@class='table agenttable']/tbody[1]/tr[1]/td[1]");
	public static By manageBookings = By.xpath("//table[@class='table agenttable']/tbody[1]/tr[1]/td[1]/following::div[1]/a[1]");
	public static By accountBalance =  By.xpath("//strong[contains(text(),'Account Balance:')]/parent::span");
	public static By manageBookings_agentportal(String pnr)
	{
		return By.xpath("//td[contains(text(),'"+pnr+"')]/following-sibling::td/div/a");
	}

	
	//Passenger Register
	public static By userEmail =  By.xpath("//input[@name='userid']");
	public static By pasword_reg =  By.xpath("//input[@name='password']");
	public static By changpwdbtn = By.xpath("//a[contains(text(),'Change my password')]");
	public static By currentpwd = By.xpath("//input[@name='oldpassword']");
	public static By newpwd = By.xpath("//input[@name='newpassword']");
	public static By cnfmnewpwd = By.xpath("//input[@name='confnewpassword']");
	public static By ConfirmPwdBtn = By.xpath("//button[contains(text(),'Confirm')]");
	public static By pwdChngeComnt = By.xpath("//div[contains(text(),'The password has been successfully changed.')]");
	public static By currentPwd =  By.xpath("//label[contains(text(),'Current password')]/following::div[1]/input");
	public static By newPwd =  By.xpath("//input[@name='newpassword']");
	public static By cnfmnewPwd =  By.xpath("//input[@name='confnewpassword']");
	public static By cnfmpasword_reg =  By.xpath("//input[@name='confpassword']");
	public static  By title_reg = By.xpath("//div[@name='title']");
	public static String selecttitle_reg=  "//div[contains(@class,'ui-select-choices-row')][#]";
	public static By fname_reg = By.xpath("//input[@name='firstname']");
	public static By lname_reg = By.xpath("//input[@name='lastname']");
	public static  By dd_reg = By.xpath("//div[@class='dob_con dob_conday']");
	public static String selectdd_reg=  "//div[contains(@class,'ui-select-choices-row')][";
	public static By dd(String value)
	{	
		return By.xpath("//label[contains(text(),'"+value+"')]/parent::div/div[1]/div/div/span/span[2]/span");
	}
	public static By mm(String value)
	{
		return By.xpath("//label[contains(text(),'"+value+"')]/parent::div/div[2]/div/div/span/span[2]/span");
	}
	public static By yy(String value)
	{
		return By.xpath("//label[contains(text(),'"+value+"')]/parent::div/div[3]/div/div/span/span[2]/span");
	}
	public static  By mm_reg = By.xpath("//div[@class='dob_con dob_conmonth']");
	public static String selectmm_reg=  "//div[contains(@class,'ui-select-choices-row')][";
	public static  By yy_reg = By.xpath("//div[@class='dob_conyear']");
	public static String selectyy_reg=  "//div[contains(@class,'ui-select-choices-row')][";
	public static By natinality_reg = By.xpath("//div[@class='nationdropdown']");
	public static By selectnatinality_reg(String value)
	{
		return By.xpath("//div[contains(text(),'"+value+"')]");
	}
	public static By doctype_reg =  By.xpath("//div[@class='traveldocdropdown']");
	public static By selectdoctype_reg(String value)
	{
		return By.xpath("//div[contains(text(),'"+value+"')]");
	}
	public static By  docnumber_reg= By.xpath("//input[@name='idnumber']");
	public static By expdd = By.xpath("//label[contains(text(),'Document expiry date*')]/following::div[@placeholder='DD']");
	public static By selectDocExpdd = By.xpath("//ul[@class='ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu']/li/div[contains(@class,'ui-select-choices-row')]/a/div[contains(text(),'"+randomNumericString()+"')]");
	public static By expmm = By.xpath("//label[contains(text(),'Document expiry date*')]/following::div[@placeholder='MONTH']");
	public static By expyy = By.xpath("//label[contains(text(),'Document expiry date*')]/following::div[@placeholder='YYYY']");
	
	public static By register = By.xpath("//button[contains(text(),'Register')]");
	
	public static By registerConformation =  By.xpath("//h2[contains(text(),'Welcome back, ')]");
	
	public static By naSmile_reg = By.xpath("//p[contains(text(),' naSmiles number: ')]/strong[1]");
			
	//my Profile page
	
	public static By naSmile_myProfile = By.xpath("//input[@name='nasmile']");
	public static By updateLnk_myProfile = By.xpath("//a[contains(text(),'Update')]");
	public static By updateBtn_myProfile = By.xpath("//button[contains(text(),'Update')]");
	public static By error_myProfile = By.xpath("//h3[contains(text(),'Error')]");
	
	//*****************production*******************************
	
	public static By collapsebtn(String bookingCls) {
		//String[] bookingclass = bookingCls.split("/");
		//return By.xpath("//span[contains(text(),'"+bookingclass[0]+"') or contains(text(),'"+bookingclass[1]+"') or contains(text(),'"+bookingclass[2]+"')]/following-sibling::span/a");
		return By.xpath("//span[contains(text(),'"+bookingCls+"')]/following-sibling::span/a");
		
	}
	public static By adultamt = By.xpath("//span[contains(text(),'Adult')]/following-sibling::span");	
	public static By childamt = By.xpath("//span[contains(text(),'Child')]/following-sibling::span");
	public static By discount = By.xpath("//span[contains(text(),'Discount')]/following-sibling::span");
	public static By serviceCharge = By.xpath("//span[text()='Service Charge']/following-sibling::span");
	
	public static By memberRegistrationConf = By.xpath("//h2[contains(text(),'Welcome back, ')]");
	public static By memberUpdateConf =By.xpath("//div[contains(text(),'successfully updated!')]");
	public static By update_Btn = By.xpath("//button[contains(text(),'Update')]");
	public static By changeMyPwdBtn = By.xpath("//a[contains(text(),'Change my password')]");
	//***********production Arabic language 
	
	//Booking page
	public static By oneWay_pdctn_AR=By.xpath("//label[contains(text(),'ذهاب فقط')]");
	public static By roundTrip_pdctn_AR = By.xpath("//label[contains(text(),'ذهاب وعودة')]");
	public static By multiCity_pdctn_AR = By.xpath("//label[contains(text(),'وجهات متعددة')]");
	public static By findFlights_pdctn_AR = By.xpath("//button[contains(text(),'البحث عن الرحلة')]");
	public static By child_pdctn_AR = By.xpath("//div[@placeholder='0 طفل']");
	public static By Arabic_pdctn_AR(String langhuage)
	{
		return 	By.xpath("//a[contains(text(),'"+langhuage+"')]");
	}
	public static By dpDate_pdctn_AR = By.xpath("//label[text()='المغادرة ']/following::div[2]");
	public static By rtnDate_pdctn_AR = By.xpath("//label[text()='العودة ']/following::div[2]");
	
	//select seat
	public static By ok_pdctn_AR = By.xpath("//button[contains(text(),'موافق')]");
	
	//summary page
	
	public static By summaryRefNumber_AR = By.xpath("//div[text()=' رقم الحجز: ']/b");
	public static By summaryStatus_AR = By.xpath("//div[text()='الحالة: ']/b");
	public static By childamt_AR = By.xpath("//span[contains(text(),'طفل')]/following-sibling::span");
	public static By discount_AR = By.xpath("//span[contains(text(),'خصم')]/following-sibling::span");
	
	//**************production Tarkish language
	//Booking page
	public static By oneWay_pdctn_TR=By.xpath("//label[contains(text(),'Tek Yön')]");
	public static By roundTrip_pdctn_TR = By.xpath("//label[contains(text(),'Gidiş')]");
	public static By multiCity_pdctn_TR = By.xpath("//label[contains(text(),'Çoklu Şehir')]");
	public static By findFlights_pdctn_TR = By.xpath("//button[contains(text(),'Uçuş Ara')]");
	public static By child_pdctn_TR = By.xpath("//div[@placeholder='0 Çocuk']");
	public static By dpDate_pdctn_TR = By.xpath("//label[text()='Kalkış ']/following::div[2]");
	public static By rtnDate_pdctn_TR = By.xpath("//label[text()='Dönüş ']/following::div[2]");
	
	//select seat
	public static By ok_pdctn_TR = By.xpath("//button[contains(text(),'Tamam')]");
	
	//summary page
	
		public static By summaryRefNumber_TR = By.xpath("//div[text()='Reservasyon: ']/b");
		public static By summaryStatus_TR = By.xpath("//div[text()='Durum: ']/b");
		public static By childamt_TR = By.xpath("//span[contains(text(),'Çocuk')]/following-sibling::span");
		public static By discount_TR = By.xpath("//span[contains(text(),'Indirim')]/following-sibling::span");
	
	//Manage Search page
		
		public static By pnrinput = By.xpath("//input[@name='inputpnr']");
		public static By emailinput = By.xpath("//input[@name='inputemail']");
		public static By lastnameinput = By.xpath("//input[@name='inputlastname']");
		public static By btnFindBooking = By.xpath("//button[@type='submit']");
		
		
		
		
	
}
