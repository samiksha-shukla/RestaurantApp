package takeaway.utilities;

public class Constant {

	static String projectPath = System.getProperty("user.dir");

	//Xlsx file path
	public static  ExcelUtils xlsxReader = new ExcelUtils(projectPath+"/src/test/resources/datacontroller/RestaurantAppController.xlsx");


	//Path to configuration properties
	public static String config_path = (projectPath+"/src/test/resources/pageObjects/commonLocators.properties");


}
