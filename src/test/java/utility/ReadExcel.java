package utility;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class ReadExcel {
	public String[][] getCellData(String path, String sheet) throws InvalidFormatException, IOException {
		FileInputStream stream = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(stream);
		Sheet sheets = workbook.getSheet(sheet);
		int rowCount = sheets.getLastRowNum();
		int cellCount = sheets.getRow(0).getLastCellNum();
		String data[][] = new String[rowCount][cellCount];

		for (int i = 1; i <= rowCount; i++) {
			Row row = sheets.getRow(i);

			for (int j = 0; j < cellCount; j++) {
				Cell cell = row.getCell(j);

				try {
					cell.getCellType();

					if (cell.getCellType() == CellType.STRING) {
						data[i - 1][j] = cell.getStringCellValue();
					} else {
						data[i - 1][j] = String.valueOf(cell.getNumericCellValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
}