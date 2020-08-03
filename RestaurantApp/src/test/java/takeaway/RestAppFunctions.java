/**
 * 
 */
package takeaway;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.gargoylesoftware.css.parser.Locator;
//import java.io.File;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
import takeaway.utilities.Constant;
import takeaway.utilities.ObjectMapper;

/**
 * @author Sam
 *
 */
public class RestAppFunctions {
	ObjectMapper config = new ObjectMapper(Constant.config_path);
	public void searchPinWithAlpha(WebDriver driver, String pin)
	{ 
		try { 			 
			xplictWaitEleClickable((config.getbjectLocator("searchBar")), driver);
		//	driver.findElement(config.getbjectLocator("searchBar")).click();

			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		driver.findElement(config.getbjectLocator("searchBar")).sendKeys(Keys.ENTER);
			xplictWaitElePresence((config.getbjectLocator("searchBar")), driver);
			driver.findElement(config.getbjectLocator("searchBar")).sendKeys(pin);
			Thread.sleep(2000);
			//driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			driver.findElement(config.getbjectLocator("searchBar")).sendKeys(Keys.ENTER);
//			TakesScreenshot scrShot =((TakesScreenshot)driver);
//			 File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
//			 File DestFile=new File("C:/Users/Sam/Downloads/test.png");
//			 FileUtils.copyFile(SrcFile, DestFile);
			xplictWaitElePresence(config.getbjectLocator("alpha"), driver);
			driver.findElement(config.getbjectLocator("alpha")).click();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String findRestaurant(WebDriver driver, String restaurant)
	{ 
		String flag = "false";
		List<WebElement> allOptions =null;
		try {
			xplictWaitEleClickable((config.getbjectLocator("restList")), driver);
			//	WebElement select = driver.findElement(By.xpath("//div[@class='js-restaurant-list-open']"));
			allOptions =driver.findElements(config.getbjectLocator("restList"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (WebElement option : allOptions)
		{
			if(option.getText().toLowerCase().contains(restaurant))
			{ 
				flag ="true";
			}
		}
		return flag;	
	}

	public void selectRestaurant(WebDriver driver, String sheet, String colName, int rowNum)
	{
		String restName = Constant.xlsxReader.getCellData(sheet, colName, rowNum);
		try 
		{	xplictWaitElePresence(config.getbjectLocator("cookies"), driver);
			//accept cookies
			driver.findElement(config.getbjectLocator("cookies")).click();
			//find restaurant element by locator
			xplictWaitElePresence(config.getbjectLocator("selenium"), driver);
			WebElement Element = driver.findElement(config.getbjectLocator("selenium"));
			scroll(driver,Element);			
			//click the restaurant
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(config.getbjectLocator(restName)).click();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void addItemAndClickOrder(WebDriver driver, String dishName)
	{
		try {
			scroll(driver,driver.findElement(config.getbjectLocator(dishName)) );
			driver.findElement(config.getbjectLocator(dishName)).click();
			xplictWaitEleClickable(config.getbjectLocator("order"), driver);
			scroll(driver,driver.findElement(config.getbjectLocator("order")) );
			driver.findElement(config.getbjectLocator("order")).click();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void enterDetails(WebDriver driver, String sheet, int rowNum)
	{
		try {
			//Enter address in payment page
			driver.findElement(	config.getbjectLocator("address")).sendKeys(Constant.xlsxReader.getCellData(sheet, "Address",rowNum));
			//Clean field and enter postcode in payment page
			driver.findElement(	config.getbjectLocator("postcode")).clear();
			driver.findElement(	config.getbjectLocator("postcode")).sendKeys(Constant.xlsxReader.getCellData(sheet, "Postcode",rowNum));
			//Enter city in payment page
			driver.findElement(	config.getbjectLocator("city")).sendKeys(Constant.xlsxReader.getCellData(sheet, "City",rowNum));
			//Enter name in payment page
			driver.findElement(	config.getbjectLocator("name")).sendKeys(Constant.xlsxReader.getCellData(sheet, "Name",rowNum));
			//Enter email in payment page
			driver.findElement(	config.getbjectLocator("email")).sendKeys(Constant.xlsxReader.getCellData(sheet, "Email",rowNum));
			//Enter phone number in payment page
			driver.findElement(	config.getbjectLocator("phonenumber")).sendKeys(Constant.xlsxReader.getCellData(sheet, "PhoneNumber",rowNum));
			//select delivery time as ASAP
			(new Select(driver.findElement(config.getbjectLocator("deliverytime")))).selectByVisibleText("As soon as possible");
			//click on pay and order button
			driver.findElement(	config.getbjectLocator("pay")).click();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getOrderReferenceNumber(WebDriver driver)
	{   String ref;
	try {
		ref =driver.findElement(config.getbjectLocator("reference")).getText();
	} catch (IOException e) {
		e.printStackTrace();
		ref="reference Not Found";
	}
	return ref;

	}

	public void xplictWaitElePresence(By locator, WebDriver driver) throws IOException 
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) 
		{
			Assert.fail("Unable to locate the elemnet");
			System.exit(0);
		}
	}

	public void xplictWaitEleClickable(By locator, WebDriver driver) throws IOException 
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			Assert.fail("Elemnet not clickable after 30 sec");
			System.exit(0);
		}
	}

	public void scroll(WebDriver driver, WebElement Element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//scroll down
		js.executeScript("arguments[0].scrollIntoView();", Element);
		js.executeScript("window.scrollBy(0,-100)");

	}
	public void scroll(WebDriver driver, Locator lo)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//scroll down
		js.executeScript("arguments[0].scrollIntoView();", lo);
		js.executeScript("window.scrollBy(0,-100)");

	}


}
