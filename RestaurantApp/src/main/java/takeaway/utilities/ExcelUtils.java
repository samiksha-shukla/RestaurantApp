package takeaway.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row   =null;
	public XSSFCell cell = null;



	public ExcelUtils(String path) {
		try {
			this.path =path;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}

	}


	//We are using this in Data Provider
	public Object[][] getTableArray( String SheetName,int tocolm) throws Exception {   

		String[][] tabArray = null;

		try {

			// Access the required test data sheet

			int startRow = 1;

			int startCol = 1;

			int ci,cj;
			sheet =workbook.getSheet(SheetName);
			int totalRows = sheet.getLastRowNum();

			// you can write a function as well to get Column count

			int totalCols = tocolm;

			tabArray=new String[totalRows][totalCols];

			ci=0;

			for (int i=startRow;i<=totalRows;i++, ci++) {           	   

				cj=0;

				for (int j=startCol;j<=totalCols;j++, cj++){

					tabArray[ci][cj]=getCellData(i,j);


				}

			}

		}

		catch (FileNotFoundException e){

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e){

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return(tabArray);

	}

	public  String getCellData(int RowNum, int ColNum) throws Exception {

		try{
			cell = sheet.getRow(RowNum).getCell(ColNum);
			String value;
            try {
                value = cell.getRichStringCellValue().toString();
            } catch (Exception e) {
                value = ((XSSFCell) cell).getRawValue();
            }

			return value;
//			String CellData;
//			try
//			{ CellData = cell.getStringCellValue();
//			}
//			catch(Exception e)
//			{
//			 CellData= String.valueOf(cell.getNumericCellValue());
//			}
//			return CellData;


		}
		catch (Exception e){

			System.out.println(e.getMessage());
			System.out.println("incatch of get cell data");

			throw (e);

		}

	}

	public String getCellData(String sheetName,String colName,int rowNum){
		
		try{

			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";


			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){

				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return ""; 

			else
			{	
//				String finalValue;
//				try
//				{
//					if(cell.getStringCellValue() !=null)
//						finalValue = cell.getStringCellValue();
//				}
//				catch(Exception e)
//				{				
//					finalValue = String.valueOf((int) cell.getNumericCellValue());
//				}
				
				String value;
                try {
                    value = cell.getRichStringCellValue().toString();
                } catch (Exception e) {
                    value = ((XSSFCell) cell).getRawValue();
                }

				return value;
			}
		}
		catch(Exception e){

			e.printStackTrace();
			return "row" +rowNum+" or column "+colName +" does not exist in xls";
		}
	}




}






