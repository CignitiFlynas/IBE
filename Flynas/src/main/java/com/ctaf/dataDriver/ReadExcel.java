package com.ctaf.dataDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.ctaf.support.ConfiguratorSupport;
 
public class ReadExcel{
	public static ConfiguratorSupport configProps=new ConfiguratorSupport("config.properties");
 
  private String inputFile;
  public static String result=null;
  public static int count=0;
  ArrayList<String>  yeslist=new ArrayList<String>();
 
  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }
 
  public ArrayList<String> read() throws IOException  {
    File inputWorkbook = new File(inputFile);
    Workbook w;
    try {
      w = Workbook.getWorkbook(inputWorkbook);
      // Get the first sheet
      Sheet sheet = w.getSheet(0);
    
      Cell statuscell;
      Cell valueCell;
      for (int j = 0; j < sheet.getRows(); j++) 
      {
    	    statuscell =sheet.getCell(0, j);
            valueCell=sheet.getCell(1, j);
            if(statuscell.getContents().toString().equalsIgnoreCase("yes"))
              {
            	yeslist.add(valueCell.getContents().toString());
            	count++;
            	System.out.println(count);
            	
              }
      }
          XmlSuite suite = new XmlSuite();
  		  suite.setName("suitname");
  		  XmlTest test = new XmlTest(suite);
  		  test.setName("testname ");
  		  test.setPreserveOrder("true");
  		  ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
  		  XmlClass testClass = new XmlClass();
  		  for(int i=0;i<yeslist.size();i++)
  			{
  				 testClass = new XmlClass();
  				 testClass.setName(yeslist.get(i).toString());//"com.Sugar.tests."+
  				 classes.add(testClass);
  				 test.setXmlClasses(classes);
  			}
  		
  		List<XmlSuite> suites = new ArrayList<XmlSuite>();
  		suites.add(suite);
  		TestNG tng = new TestNG();
  		tng.setXmlSuites(suites);
  		Logger lpsOut = Logger.create(System.out);
        System.setOut(lpsOut);
  	    tng.run();
  		
  	//	System.out.println(suite.toXml());
  		
  		if(configProps.getProperty("ReRunFailureTestCases").equals("True"))
  		{
  		
       //  StringBuilder x=lpsOut.buf;
        System.out.flush();
        System.setOut(lpsOut.underlying);
        String strLine =( lpsOut.buf).toString();
        int index=strLine.indexOf("Total tests run:");
        String result=strLine.substring(index);
        String[] splitData=result.split("\\,");
        for(int i=0;i<=2;i++)
        	{
        		String totalTest[]=splitData[i].split("\\:") ;
        		if(i==1)
        		{
        			if(Integer.parseInt(totalTest[1].trim())!=0)
        			{
        				ExecutionDriver btexe=new ExecutionDriver();
				
        				btexe.runFailure();
				
        			}
        		}
        	}
       
  		}	
        
    } catch (Exception e) {
    	
      e.printStackTrace();
    }
	return  yeslist;
  }
 
 @Test
  public static void main() throws IOException {
    ReadExcel test = new ReadExcel();
    test.setInputFile("Run_Excel\\Run.xls");
    test.read();
  }
 
}