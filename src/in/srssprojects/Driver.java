package in.srssprojects;

import java.lang.reflect.Method;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.BrowserHelper;
import utilities.ExcelHelper;
import utilities.Reporter;

public class Driver extends Reporter{
	public static void main(String[] args) {
		// create ExtentReports class object
		
		ExcelHelper tcExcel = new ExcelHelper();
		tcExcel.openExcel("resources", "input.xls", "testcases");
		ExcelHelper tsExcel = new ExcelHelper();
		tsExcel.openExcel("resources", "input.xls", "teststeps");
		// create an object of the keywords class
		Keywords keywords = new Keywords();
		// get all the methods of the keywords class and store in an Array of type
		// Method
		Method[] methods = keywords.getClass().getMethods();
		report = new ExtentReports(BrowserHelper.getFilePath("reports", "report.html"));
		for (int i = 1; i < tcExcel.rowCount(); i++) {
			String tcd_tcname = tcExcel.readData(i, 0);
			String tcd_runmode = tcExcel.readData(i, 1);
			if (tcd_runmode.equals("yes")) {
				test = report.startTest(tcd_tcname);
				for (int j = 1; j < tsExcel.rowCount(); j++) {
					String tsd_tcname = tsExcel.readData(j, 0);
					if (tcd_tcname.equals(tsd_tcname)) {
						String tsname = tsExcel.readData(j, 1);
						String locType = tsExcel.readData(j, 2);
						String locValue = tsExcel.readData(j, 3);
						String keyword = tsExcel.readData(j, 4);
						String testData = tsExcel.readData(j, 5);
						test.log(LogStatus.INFO, "executing "+tsname);
						if(!locType.isEmpty()) {
							test.log(LogStatus.INFO, "locating eleemnt using "+locType+" = "+locValue);
						}
						for (Method method : methods) {
							if (method.getName().equals(keyword)) {
								try {
									method.invoke(keywords, locType, locValue, testData);
									break;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
				report.endTest(test);
			}
		}
		//save the report
		report.flush();
		report.close();
		tcExcel.closeExcel();
		tsExcel.closeExcel();
	}

}
