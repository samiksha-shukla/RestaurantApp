# Takeaway_QA_Assignment

### Overview-
> This project is built to automate restaurant search and order placement flow of Takeaway application using > Selenium, RestAssured, TestNG and Maven and programming language used is Java.

### Prerequisite for execution-
1.	Application must be up and running
URL - https://www.thuisbezorgd.nl/en/
2.	Java should be installed on the system and environmental variable should be added for java, to check if java is installed, use below commands
```sh
java –version 
javac – version
```

3.	Google chrome or Firefox browsers or both browsers should be available(depends on which browser you select to run tests)

4.	maven should be available and M2_Home, MAVEN_Home, path variable with maven bin address should be set. To check if maven is available , run below command
```sh
mvn -version
```
In case Maven is not available, download it from internet, extract files and follow below step to set paths.
Steps to set system variable for maven using commands (variable can also be set using user interface )- 
1.	RUN CMD in admin mode
2.	 Set system variable for M2_Home, enter below command
```sh
setx M2_Home "path to maven folder" /M
```
Ex-  ```setx M2_Home "D:\programFilesInD\apache-maven-3.6.3" /M```

3.	Set system variable for MAVEN_Home,  enter below command 
```sh
setx MAVEN_Home "path to maven folder" /M
```
Ex ```setx MAVEN_Home "D:\programFilesInD\apache-maven-3.6.3" /M```

4. Append maven bin folder address to path variable under system variable in environment variable
MyComputer-> Proprties -> Advance ->Enviornment Variable-> System Variable-> Path

### Test suite Execution -
To run Test, follow below steps
1. open cmd and navigate till project folder

2. Test case be executed in hedless mode, to switch it on ON, select yes in headless sheet of ReasAppController.xlsx file
file path - project folder/RestaurantApp/src/test/resources/datacontroller/RestaurantAppController.xlsx

3. to run test on chrome  enter command 
``` mvn clean test -DtestngFile=chromeTestNG.xml ``` and press enter
or 
to run test on firefox enter command 
``` mvn clean test -DtestngFile=firefoxTestNG.xml ``` and press enter
(Ignore warring and POI related errors, it’s because of version compatibilities)

4. Once execution is completed, result with be displayed with count of test cases, failed error etc. (refer to screen shot below)

![Alt text](/images/MavenRunResult.PNG)

## Project Structure

### Test cases -
Automation suite is created with 3 test cases, 3rd case will run multiple times bases on data entered in datacontoller.xlsx. Test cases are listed below-
|TestCase#	|TestCaseName	|Description|SheetName|
| ------ | ------ | ------ |------ |
|1	|VerifyIfRestaurantIsListed|	verify the restaurant is listed in the search page, pincode and restaurant name read from data controller| UserInput Row 2|
|2	|verifyOrderPlacementFlow|	Place order for test restaurant, pin, restaurant name, address, postcode, city, name, e-mail read from data controller|UserInput Row 3|
|3	|VerifyIfRestaurantsAreListed (it will run multiple times and data will be taken from MultiRestaurant of dataController)	|verify the restaurants are listed in the search page, pincode and restaurant name read from data controller using data provider| MultiRestaurent|


### Drivers-
Chrome, gecko (for firefox) and IE Drivers are saved under folder drivers in below path-
Folder path: /RestaurantApp/drivers/

### Data Management-
1.	Data is managed using excel, saved under below path(RestaurantAppController.xlsx)
project folder - >src-> test -> resources -> datacontroller
2.	Data driven approach is achieved using apache POI library and methods are written in ExcelUtils.java class under takeaway.utilities package
3. Different sheets in data loader with its feature are listed below-

|SheetName|Feature|
| ------ | ------ |
|headless|select yes for headless, no for headed browser|	
|Testcases|select yes if test cases to be run, no if test cases to be skipped|
|MultiRestaurant|used be data providered to run same case for multiple set of data(test case# 3)|
|UserInput|Data for execution of test case# 1 and 2|

### Browser management-
Test is designed to run on Chrome, Firefox. Different testng files are available for same. 
chromeTestNG - to execute test in Chrome browser
firefoxTestNG - to execute test in firefox browser

 
### Locators and URL-
1.	Locators and URL are stored in properties file under below folder
Project -> pageObjects -> commonLocators.properties
(/RestaurantApp/pageObjects/commonLocators.properties)
2.	Config reader methods are written in ObjectMapper.java class under RestaurantApp.utilities package

### Reports-

1. Test report is available under below path-

Projectpath->test-output->emailable-report.html
(/RestaurantApp/test-output/emailable-report.html)

Or

Projectpath->target->surefire-reports->emailable-report.html
(/RestaurantApp/target/surefire-reports/emailable-report.html)

### Tests-
1. All the tests are written in RestAppTest.java class using TestNG annotations available under takeaway package
Folder path : /RestaurantApp/src/test/java/takeaway/RestAppTest.java
2. All the reusable methods like wait are written in RestAppFunctions.java class under takeaway package
Folder path: /RestaurantApp/src/test/java/takeaway/RestAppFunctions.java
### Dependencies-
All the dependencies are declared under POM.XML file available in below path
Folder path: /RestaurantApp/pom.xml

