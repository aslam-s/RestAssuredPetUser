package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportmanager implements ITestListener {
	public ExtentSparkReporter sparkreporter;
	public ExtentReports extent;
	public ExtentTest test;
	String reportName;

	public void onStart(ITestContext context) {

		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date d =
		 * new Date(); String cdate = df.format(d);
		 */
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-report" + timestamp + ".html";
		sparkreporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/myreports.html"); // path
		sparkreporter.config().setDocumentTitle(" PetUser Automation Report"); // title of report
		sparkreporter.config().setReportName(" Functional testing");// name of report
		sparkreporter.config().setTheme(Theme.DARK); // Theme //dark

		extent = new ExtentReports();
		extent.attachReporter(sparkreporter);

		extent.setSystemInfo("Application", "petuser");
		extent.setSystemInfo("module", "Admin");
		extent.setSystemInfo("Submodule", "Customer");
		extent.setSystemInfo("username", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os = context.getCurrentXmlTest().getParameter("os");
		String browser = context.getCurrentXmlTest().getParameter("browser");
		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) { // test passed
		test = extent.createTest(result.getTestClass().getName()); // create new entery in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, "Test Case passed is :" + result.getName()); // update status pass/fail

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test Case Failed is :" + result.getName());
		test.log(Status.INFO, "Test Case Failed cause is :" + result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test Case skipped is :" + result.getName());
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {
		extent.flush();// consolidate all report generate

	}
}