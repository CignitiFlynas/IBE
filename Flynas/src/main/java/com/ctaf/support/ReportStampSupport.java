package com.ctaf.support;  

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ReportStampSupport {
//	return date
	public static String dateStamp(){
		DateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();
		return dateFormat.format(date).substring(0,7);
	}
	
	//
	public static String dateTime() 
	{
		Date todaysDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
		String formattedDate = formatter.format(todaysDate);
		return formattedDate;
	}
	public static String getTime()
	{
		Date todaysDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss a");
		String formattedDate = formatter.format(todaysDate);
		return formattedDate;
	}
	//return randomValue
	public static String randomValue(){
		Random rand = new Random();
		int randomNumber = rand.nextInt(100000) + 1;
		return Integer.toString(randomNumber);
	}
	
	public static int biRandomValue(){
		Random rand = new Random();
		int randomNumber = rand.nextInt(10) + 1;
		return (randomNumber);
	}
	//return time and date
	public static String timeStamp(){
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime()).toString();
	}
	// return environmental details
	public static String osEnvironment(){

		return "Current suit exicuted on : "+System.getProperty("os.name")
				+"/version : "+System.getProperty("os.version")
				+"/Architecture : "+System.getProperty("os.arch");
	}
	public static String getHostName() throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();

		return hostname;
	}
	public static void calculateTestCaseStartTime(){			
		HtmlReportSupport.iStartTime = System.currentTimeMillis();
	}

	
	/***
	 * 		This method is supposed to be used in the @AfterMethod to calculate the total test case execution time 
	 * to show in Reports by taking the start time from the calculateTestCaseStartTime method.
	 */
	public static void calculateTestCaseExecutionTime(){	
		HtmlReportSupport.iEndTime = System.currentTimeMillis();
		HtmlReportSupport.iExecutionTime=(HtmlReportSupport.iEndTime-HtmlReportSupport.iStartTime);
		TimeUnit.MILLISECONDS.toSeconds(HtmlReportSupport.iExecutionTime);
		HtmlReportSupport.executionTime.put(HtmlReportSupport.tc_name,String.valueOf(TimeUnit.MILLISECONDS.toSeconds(HtmlReportSupport.iExecutionTime)));
		System.out.println(HtmlReportSupport.tc_name+";Time :"+String.valueOf(TimeUnit.MILLISECONDS.toSeconds(HtmlReportSupport.iExecutionTime)));
	}
	
	
	/***
	 * 		This method is supposed to be used in the @BeforeSuite in-order trigger the Suite Start Time
	 * which inturn used to calculate the Total Suite execution time to show in Reports.
	 */
	public static void calculateSuiteStartTime(){
		
		HtmlReportSupport.iSuiteStartTime = System.currentTimeMillis(); //Newly added
	}
	
	
	/***
	 * 		This method is supposed to be used in the @AfterMethod to calculate the total suite execution time
	 * to show in Reports by taking the suite start time from the calculateSuiteStartTime method.
	 */
	public static void calculateSuiteExecutionTime(){
		
		HtmlReportSupport.iSuiteEndTime = System.currentTimeMillis(); //Newly added
		HtmlReportSupport.iSuiteExecutionTime = (HtmlReportSupport.iSuiteEndTime-HtmlReportSupport.iSuiteStartTime)/1000.000;//Newly added
	}
}
