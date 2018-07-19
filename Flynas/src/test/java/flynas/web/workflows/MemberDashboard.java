package flynas.web.workflows;

import flynas.web.testObjects.BookingPageLocators;

public class MemberDashboard<RenderedWebElement> extends BookingPageLocators {
	
	public void waitforpageload() throws InterruptedException{	
		waitUtilElementhasAttribute(BookingPageLocators.body);
		}
	
	public void navigateToMyProfile() throws Throwable{
		waitUtilElementhasAttribute(BookingPageLocators.body);
		waitforElement(BookingPageLocators.myProfile);
		click(BookingPageLocators.myProfile, "My Profile");
		waitUtilElementhasAttribute(BookingPageLocators.body);
	}

}
