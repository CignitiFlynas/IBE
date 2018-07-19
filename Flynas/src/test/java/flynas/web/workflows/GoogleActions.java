package flynas.web.workflows;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import com.ctaf.googledrive.GoogleDriveAPI;

import flynas.web.testObjects.BookingPageLocators;

public class GoogleActions<RenderedWebElement> extends BookingPageLocators{
	public void updateStatus(String Sheetname, String TCID, String status ) throws IOException{
		
		int row = GoogleDriveAPI.fnGetRowNo(Sheetname, TCID);
		GoogleDriveAPI.getResponse(Sheetname, "B"+row, "B"+row);
		GoogleDriveAPI.setValue(Sheetname, "B"+row, "B"+row, status);
		String Exetime=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
		if(status.equalsIgnoreCase("Pass"))
		GoogleDriveAPI.setValue(Sheetname, "C"+row, "C"+row, Exetime+" IST");
		else GoogleDriveAPI.setValue(Sheetname, "D"+row, "D"+row, Exetime+" IST");
		
//		ExcelReader xls = new ExcelReader(configProps.getProperty("ResultSheet"),Sheetname);
//		int rowNum = xls.findRow(Sheetname, TCID);
//		System.out.println("Row number : "+rowNum);
//		String Pcount = xls.getCellValue(TCID, "Total Pass Count");
//		System.out.println("pass count: "+Pcount);
//		String Fcount = xls.getCellValue(TCID, "Total Fail Count");
//		System.out.println("Fail count : "+Fcount);
//		xls.setCellData(Sheetname, "Status", rowNum, status);
//		int count;
//		if (status.equalsIgnoreCase("Pass")){
//			count= Integer.parseInt(Pcount)+1;
//			xls.setCellData(Sheetname, "Total Pass Count", rowNum,Integer.toString(count));
//		}
//		else{
//			count= Integer.parseInt(Fcount)+1;
//			xls.setCellData(Sheetname, "Total Fail Count", rowNum,Integer.toString(count));
//		}
//		System.out.println(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE));
//		String Exetime=ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE);		
//		xls.setCellData(Sheetname, "Last Execution time", rowNum, Exetime);
	
		
	}

}
