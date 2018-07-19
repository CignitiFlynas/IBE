package com.ctaf.googledrive;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Year;

import com.ctaf.support.ExcelReader;

public class TestGoogle {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int row = GoogleDriveAPI.fnGetRowNo("IBE_UAT_Reg", "TC_65D_IntlStaffCnfmdFriendsBookingChargeVerification");
		GoogleDriveAPI.getResponse("IBE_UAT_Reg", "B"+row, "B"+row);
		GoogleDriveAPI.setValue("IBE_UAT_Routes", "B"+row, "B"+row, "pass");
		
	}
	
	
	
	

}
