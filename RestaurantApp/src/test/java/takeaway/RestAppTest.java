/**
 * 
 */
package takeaway;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.sun.tools.xjc.generator.bean.field.FieldRendererFactory;

import takeaway.RestAppFunctions;
import takeaway.utilities.Constant;
import takeaway.utilities.ObjectMapper;

/**
 * @author Sam
 *
 */
public class RestAppTest {
	ObjectMapper config = new ObjectMapper(Constant.config_path);
	RestAppFunctions objFunction = new RestAppFunctions();
	String projectPath = System.getProperty("user.dir");
	String url = config.prop.getProperty("url");	
	public static WebDriver driver;
	//WebDriver driver;
	String tc_runmode;	
	By locator;
	ChromeOptions option = new ChromeOptions();

	@DataProvider(name = "PlaceOrder")
	public Object[][] buyACar() throws Exception {
		Object[][] testObjArray = Constant.xlsxReader.getTableArray("MultiRestaurant", 2); 
		System.out.println("in DL");
		return (testObjArray);
	}

	@Parameters("browserName")
	@BeforeTest
	public void Setup(String browserName)
	{ String headless = Constant.xlsxReader.getCellData("headless", "value", 2);
	System.out.println(headless);
	if(browserName.equalsIgnoreCase("chrome"))
	{ 
		//	driver = new HtmlUnitDriver();
		//option.addArguments("--headless");
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	if(browserName.equalsIgnoreCase("firefox"))
	{
		FirefoxBinary firefoxBinary = new FirefoxBinary();
		//			firefoxBinary.addCommandLineOptions("--headless");
		FirefoxOptions fo = new FirefoxOptions();
		//			fo.setBinary(firefoxBinary);
		System.setProperty("webdriver.gecko.driver", projectPath+"/drivers/geckodriver1.exe");
		//	driver = new FirefoxDriver(fo);

	}
	driver.get(url);
	driver.manage().window().maximize();
	}

	@BeforeMethod
	public void refreshURL()
	{
		driver.get(url);
		System.out.println("in before method");
	}

	@Test(enabled = true, priority =3, dataProvider = "PlaceOrder")
	public void VerifyIfRestaurantsAreListed( String pin, String restaurant)
	{ 	
		String flag = "false";
		tc_runmode =Constant.xlsxReader.getCellData("TestCases", "runmode",4);
		if (tc_runmode.equals("Yes"))
		{ 		
			objFunction.searchPinWithAlpha(driver, pin);
			flag = objFunction.findRestaurant(driver,restaurant);
			Assert.assertEquals(flag, "true");
			System.out.println(restaurant + pin);
		}
		else {
			throw new SkipException("Skipping / Ignoring - Script not include for Execution ");
		}
	}

	@Test(priority =1,enabled = true)
	public void VerifyIfRestaurantIsListed()
	{  
		String flag = "false";
		tc_runmode =Constant.xlsxReader.getCellData("TestCases", "runmode",2);
		if (tc_runmode.equals("Yes"))
		{
			// pin and restaurant name is picked from Excel UserInput, row 2
			//Searching restaurant by Pin and alpha
			String pin = Constant.xlsxReader.getCellData("UserInput", "SearchPin", 2);
			objFunction.searchPinWithAlpha(driver, pin);
			//Find if restaurant is listed
			String restaurant =Constant.xlsxReader.getCellData("UserInput", "RestaurantName", 2);
			flag = objFunction.findRestaurant(driver,restaurant);
			Assert.assertEquals(flag, "true");
		}
		else {
			throw new SkipException("Skipping / Ignoring - Script not include for Execution ");
		}
	}

	@Test(priority =2,enabled = true )
	public void verifyOrderPlacementFlow()
	{ 
		tc_runmode =Constant.xlsxReader.getCellData("TestCases", "runmode",3);
		if (tc_runmode.equals("Yes"))
		{
			String pin = Constant.xlsxReader.getCellData("UserInput", "SearchPin", 3);
			//search for menu for provided pin
			objFunction.searchPinWithAlpha(driver, pin);
			//select restaurant
			objFunction.selectRestaurant(driver,"UserInput", "RestaurantName", 3);
			//add Item to the cart and click Order button
			objFunction.addItemAndClickOrder(driver, "salami");
			//Enter address and other details in payment page
			objFunction.enterDetails(driver, "UserInput", 3);
			//get order reference number after order is place
			System.out.println(objFunction.getOrderReferenceNumber(driver));
		}
		else {
			throw new SkipException("Skipping / Ignoring - Script not include for Execution ");
		}

	}
	@AfterTest
	public void Teardown()
	{
		driver.quit();
	}



}
