package utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont; 
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clase que permite la gestión de archivos en general.
 * Last Update: April 04th, 2020.
 * @author Jean Guerra
 * @version $I$, $G$
 * @since 1.0
 */
public final class Files
{
	private int rowCount = 0;
	private String resultFile;
	
	
	/**
	 * Carga los datos a partir del datatable.
	 * @param xlsxPath Ruta del datatable.
	 * @return Matriz de datos cargados.
	 * @throws Exception
	 */
	public Object[][] getData(String xlsxPath) throws Exception
	{
		FileInputStream file = new FileInputStream(new java.io.File(xlsxPath));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		final int rowsSize = sheet.getLastRowNum();
		Object[][] data = new Object[rowsSize][];
		
		Iterator<Row> rowIterator = sheet.iterator();
		
		if(rowIterator.hasNext())
		{
			rowIterator.next(); // salta la primera fila
		}
		else
		{
			workbook.close();
			throw new Exception("La hoja de cálculo está vacía.");
		}

		int rowCounter = 0, columnCounter = 0;
		while (rowIterator.hasNext())
		{
			final int columnsSize = sheet.getRow(0).getLastCellNum();
			data[rowCounter] = new Object[columnsSize];	

			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			
			while (cellIterator.hasNext())
			{
				Cell celda = cellIterator.next();
				
				try
				{
					try
					{
						data[rowCounter][columnCounter] = celda.getStringCellValue();
					}
					catch(Throwable error)
					{
						data[rowCounter][columnCounter] = String.valueOf((int)celda.getNumericCellValue());
						//System.out.println("excisten celdas vacias (con algun valor invisible) en el excel");
						//System.out.println("limpiar el excel para evitar ArrayIndexOutOfBoundsException ");
					}
				}
				catch(Throwable error)
				{
					error.printStackTrace();
				}

				columnCounter++;
			}
			
			rowCounter++;
			columnCounter = 0;
		}
		
		workbook.close();
		file.close();
		return data;
	}
	
	public void createExcel(String xlsxPath, String tags[]) throws Exception
	{
		resultFile = xlsxPath;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		
		XSSFFont defaultFont= workbook.createFont();
		defaultFont.setFontHeightInPoints((short) 15);
		defaultFont.setBold(true);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(defaultFont);
		
		Row row = sheet.createRow(rowCount++);

		for (int i=0; i<tags.length; i++)
		{
			Cell cell = row.createCell(i);
			cell.setCellValue(tags[i]);
			cell.setCellStyle(style);
		}
		
		try (FileOutputStream outputStream = new FileOutputStream(xlsxPath))
		{
            workbook.write(outputStream);
            workbook.close();
        }
	}
	
	public void writeData(Object[] dto, String testResult) throws Exception
	{
		FileInputStream file = new FileInputStream(new java.io.File(resultFile));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Row row = sheet.createRow(rowCount++);
		
		Cell cell = row.createCell(0);
		cell.setCellValue(testResult);
		sheet.autoSizeColumn(0);
		
		for (int i=0; i<dto.length; i++)
		{
			cell = row.createCell(i + 1); // Ya que la 0 es la de PNR
			cell.setCellValue((String) dto[i]);
			sheet.autoSizeColumn(i + 1); // Ya que la 0 es la de PNR
		}

		try (FileOutputStream outputStream = new FileOutputStream(resultFile))
		{
            workbook.write(outputStream);
            workbook.close();
        }
		
		file.close();
	}

}

