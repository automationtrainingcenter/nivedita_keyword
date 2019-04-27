package in.srssprojects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import utilities.BrowserHelper;
import utilities.LocatorHelper;
import utilities.Reporter;

public class Keywords extends BrowserHelper {

	private static String text;

	// launch
	public void launch(String locType, String locValue, String testData) {
		launchBrowser(testData);

	}

	// navigate
	public void navigate(String locType, String locValue, String testData) {
		driver.get(testData);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// enterText
	public void enterText(String locType, String locValue, String testData) {
		driver.findElement(LocatorHelper.locate(locType, locValue)).sendKeys(testData);
		Reporter.test.log(LogStatus.INFO, testData + " entered in text field");
	}

	// click
	public void click(String locType, String locValue, String testData) {
		driver.findElement(LocatorHelper.locate(locType, locValue)).click();
		Reporter.test.log(LogStatus.INFO, "clicked on element");
	}

	// select
	public void select(String locType, String locValue, String testData) {
		new Select(driver.findElement(LocatorHelper.locate(locType, locValue))).selectByVisibleText(testData);
		Reporter.test.log(LogStatus.INFO, "selected an option " + testData);
	}

	// acceptAlert
	public void acceptAlert(String locType, String locValue, String testData) {
		text = driver.switchTo().alert().getText();
		System.out.println(text);
		Reporter.test.log(LogStatus.INFO, "alert came " + text);
		driver.switchTo().alert().accept();
		Reporter.test.log(LogStatus.INFO, "alert accepted");

	}

	// close
	public void close(String locType, String locValue, String testData) {
		closeBrowser();
	}

	// assert login
	public void assertLogin(String locType, String locValue, String testData) {
		WebElement logout = driver.findElement(LocatorHelper.locate(locType, locValue));
		if (driver.getCurrentUrl().equals(testData) && logout.isDisplayed()) {
			Reporter.test.log(LogStatus.PASS, "login test is passed");
		} else {
			Reporter.test.log(LogStatus.FAIL, "login test failed");
		}
	}

	// assert alert messages
	public void assertAlert(String locType, String locValue, String testData) {
		System.out.println();
		if (text.contains(testData)) {
			Reporter.test.log(LogStatus.PASS, "Test case passed");
		} else {
			Reporter.test.log(LogStatus.FAIL, "Test case failed");
		}
	}

}
