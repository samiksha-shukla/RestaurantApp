package takeaway.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;
//import qwicQA.utilities.Constant;

public class ObjectMapper {

	public FileInputStream FIS;
	public String RepositoryFile;
	public Properties prop = new Properties();
 
	public ObjectMapper(String fileName)
	{
		this.RepositoryFile = fileName;
		try {
			FIS = new FileInputStream(RepositoryFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(FIS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public By getbjectLocator(String locatorName) throws IOException
	{
		String locatorProperty = prop.getProperty(locatorName);
		String locatorType = locatorProperty.split(":")[0];
		String locatorValue = locatorProperty.split(":")[1];
 
		By locator = null;
		switch(locatorType)
		{
		case "Id":
			locator = By.id(locatorValue);
			break;
		case "Name":
			locator = By.name(locatorValue);
			break;
		case "Css":
			locator = By.cssSelector(locatorValue);
			break;
		case "LinkText":
			locator = By.linkText(locatorValue);
			break;
		case "PartialLinkText":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TagName":
			locator = By.tagName(locatorValue);
			break;
		case "Xpath":
			locator = By.xpath(locatorValue);
			break;
		case "ClassName":
			locator =By.className(locatorValue);
			break;
		}
		return locator;
	}
}


