package utilities;

import java.io.FileInputStream;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelHelper {
	
	Workbook book;
	Sheet sh;
	
	// open  the excel to read the data
	public void openExcel(String folderName, String fileName, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(BrowserHelper.getFilePath(folderName, fileName));
			book = Workbook.getWorkbook(fis);
			sh = book.getSheet(sheetName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//count the number of rows
	public int rowCount() {
		return sh.getRows();
	}
	
	//count the number of columns
	public int columnCount() {
		return sh.getColumns();
	}
	
	
	//read data
	public String readData(int rnum, int cnum) {
		return sh.getCell(cnum, rnum).getContents();
	}
	
	
	//close the excel file
	public void closeExcel() {
		book.close();
	}

}
