package com.ctaf.dataDriver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* 
* @Class Details
*            : Data Driver Class to perform batch execution with specific test data sets
*            
* @Suite Details
*            : For Different Batch suits including Functional,Regression
*  
*/
public class ExecutionDriver {
	public static void main(String[] args) throws IOException {

		ExecutionDriver batchExecuteService = new ExecutionDriver();
		batchExecuteService.runFunctional();
		batchExecuteService.runRegression();
		batchExecuteService.runFailure();
	}

public String runSmoke() {
	String resultSmoke = null;
		try {

			System.out.println("Welcome");
			String cmds[] = { "Smoke.bat" };

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmds);

			process.getOutputStream().close();
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedrReader = new BufferedReader(
					inputstreamreader);
			String strLine = "";
			while ((strLine = bufferedrReader.readLine()) != null) {
				System.out.println(strLine);
				if (strLine.contains("Total tests run:")) {
					resultSmoke = strLine;
				}
			}

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return resultSmoke;
	}

	public void runFunctional() {
		try {
			System.out.println("Welcome");
			String cmds[] = { "Functional.bat" };

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmds);

			process.getOutputStream().close();
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedrReader = new BufferedReader(
					inputstreamreader);
			String strLine = "";
			while ((strLine = bufferedrReader.readLine()) != null) {
				System.out.println(strLine);
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void runRegression() {
		try {
			System.out.println("Welcome");
			String cmds[] = { "Regression.bat" };

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmds);
			process.getOutputStream().close();
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedrReader = new BufferedReader(
					inputstreamreader);
			String strLine = "";
			while ((strLine = bufferedrReader.readLine()) != null) {
				System.out.println(strLine);
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void runFailure() {
		try {
			System.out.println("Welcome");
			String cmds[] = { "TestData\\Failure.bat" };

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmds);
			process.getOutputStream().close();
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedrReader = new BufferedReader(
					inputstreamreader);
			String strLine = "";
			while ((strLine = bufferedrReader.readLine()) != null) {
				System.out.println(strLine);
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
