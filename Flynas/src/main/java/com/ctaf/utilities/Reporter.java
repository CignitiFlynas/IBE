package com.ctaf.utilities;
  
import java.io.File;

import com.ctaf.accelerators.ActionEngine;
import com.ctaf.accelerators.TestEngine;
import com.ctaf.support.ConfiguratorSupport;
import com.ctaf.support.HtmlReportSupport;
import com.ctaf.support.ReportStampSupport;

public class Reporter extends TestEngine {
	public static ConfiguratorSupport configProps = new ConfiguratorSupport(
			"config.properties");
	static String timeStamp = ReportStampSupport.timeStamp().replace(":", "_")
			.replace(".", "_");

	public static void reportCreater() throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));

		switch (intReporterType) {
		case 1:

			break;
		case 2:

			HtmlReportSupport.htmlCreateReport();
			HtmlReportSupport.createDetailedReport();

			break;
		default:

			HtmlReportSupport.htmlCreateReport();
			break;
		}
	}

	public static void SuccessReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:

			break;
		case 2:
			if (configProps.getProperty("OnSuccessScreenshot")
					.equalsIgnoreCase("True")) {
				ActionEngine.screenShot(TestEngine.filePath()
						+ strStepDes.replace(" ", "_") + "_"
						+ TestEngine.timeStamp + ".jpeg");
			}
			HtmlReportSupport.onSuccess(strStepName, strStepDes);

			break;

		default:
			if (configProps.getProperty("OnSuccessScreenshot")
					.equalsIgnoreCase("True")) {
				ActionEngine.screenShot(TestEngine.filePath()
						+ strStepDes.replace(" ", "_") + "_"
						+ TestEngine.timeStamp + ".jpeg");
			}
			HtmlReportSupport.onSuccess(strStepName, strStepDes);
			break;
		}
	}

	public static void failureReport(String strStepName, String strStepDes)
			throws Throwable {
		/*String strStepDes1 = strStepDes.replace(":", "_");
		strStepDes1 = strStepDes1.replace("\\", "_");
		strStepDes1 = strStepDes1.replace("/", "_");
		strStepDes1 = strStepDes1.replace("?", "_");
		strStepDes1 = strStepDes1.replace("*", "_");
		strStepDes1 = strStepDes1.replace("<", "_");
		strStepDes1 = strStepDes1.replace(">", "_");
		System.out.println(strStepDes1);*/
		String randomValue = ReportStampSupport.randomValue();
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			ActionEngine.screenShot(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepName.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + randomValue + ".jpeg");
			flag = true;
			HtmlReportSupport.onFailure(strStepName, strStepDes,randomValue);
			break;

		default:
			flag = true;
			ActionEngine.screenShot(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepName.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + randomValue + ".jpeg");
			System.out.println(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepName.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + ".jpeg");
			HtmlReportSupport.onFailure(strStepName, strStepDes, randomValue);
			break;
		}

	}
	
	public static void failureReportContinue(String strStepName, String strStepDes)
			throws Throwable {
		/*String strStepDes1 = strStepDes.replace(":", "_");
		strStepDes1 = strStepDes1.replace("\\", "_");
		strStepDes1 = strStepDes1.replace("/", "_");
		strStepDes1 = strStepDes1.replace("?", "_");
		strStepDes1 = strStepDes1.replace("*", "_");
		strStepDes1 = strStepDes1.replace("<", "_");
		strStepDes1 = strStepDes1.replace(">", "_");
		System.out.println(strStepDes1);*/
		String randomValue = ReportStampSupport.randomValue();
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			ActionEngine.screenShot(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepName.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + randomValue + ".jpeg");
			flag = true;
			HtmlReportSupport.onFailureContinue(strStepName, strStepDes,randomValue);
			break;

		default:
			flag = true;
			ActionEngine.screenShot(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepName.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + randomValue + ".jpeg");
			System.out.println(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepName.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + ".jpeg");
			HtmlReportSupport.onFailureContinue(strStepName, strStepDes, randomValue);
			break;
		}

	}
	public static void warningReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			ActionEngine.screenShot(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepDes.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + ".jpeg");
			flag = true;
			HtmlReportSupport.onWarning(strStepName, strStepDes);
			break;

		default:
			flag = true;
			ActionEngine.screenShot(TestEngine.filePath()+File.separator+"Screenshots"+File.separator
					+ strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp
					+ ".jpeg");
			HtmlReportSupport.onWarning(strStepName, strStepDes);
			break;
		}

	}
}
