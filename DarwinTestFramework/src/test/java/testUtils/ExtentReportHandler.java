package testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportHandler {

	public ExtentHtmlReporter htmlReporter;

	private static ExtentReports extent;
	public ExtentTest test;
	public ExtentReportHandler Reporthandler;

	public ExtentReportHandler() {
		Getinstance();
	}

	public ExtentReports Getinstance() {

		if (extent == null) {
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/DarwinTestingReport.html");

			htmlReporter.config().setDocumentTitle("Automation Report");
			htmlReporter.config().setReportName("Drawin Testing Report");
			htmlReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Project Name", "Darwin Testing");

			return extent;
		} else {
			return extent;
		}

	}

	public ExtentReports getExtent() {
		return extent;
	}

}
