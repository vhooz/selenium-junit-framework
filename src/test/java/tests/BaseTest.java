package tests;

import java.io.File;

import org.openqa.selenium.WebDriver;

import utils.ConfigProperties;
import utils.Files;
import utils.Report;

public class BaseTest {
	
	public static WebDriver driver;
	public static Report report;
	public static Files file;	
	protected String testResult;
	protected static int counter;
	protected static int firstRow;
	protected static int LastRow;
	private static final String fs = File.separator;

	public static void inicializeReport(String[] resultTable) throws Throwable {
		if (report == null) 
		{
			report = new Report();
			file = new Files();		
			file.createExcel( report.getReportPath() + fs + "ResultsTable.xlsx", resultTable);
		}
	}
	
    public static void tearDown(Object[] data, String testResult) throws Exception {
        System.out.println("@After each test:");
        System.out.println("Escribiendo resultados en el los reportes html / .xlsx");
        
        counter++;
		report.endTest();
		file.writeData(data,testResult);
		closeBrowser();
    }
    
    public static void tearDown( ) throws Exception {
        System.out.println("@After each test:");
        System.out.println("Escribiendo resultados en el los reportes html");
        
        counter++;
		report.endTest();
		closeBrowser();
    }
    
    public static void tearDownWithoutClosingBrowser(Object[] data, String testResult) throws Exception {
        System.out.println("@After each test:");
        System.out.println("Escribiendo resultados en el los reportes html / .xlsx");

        counter++;		
		report.endTest();
		file.writeData(data,testResult);
    }
    
    public static void closeBrowser() throws Exception {
		driver.quit();
    }
    
    public static void endReport() throws Exception {
		report.endReport();
    }
    
	protected static Object[] convertToJUnitArray(Object[][] biarray)
	{
		Object[] triarray = new Object[(LastRow-firstRow) + 1];
		
		for (int i=0; i<(LastRow-firstRow) + 1; i++)
		{
			triarray[i] = new Object[] { biarray[(firstRow-1)+i] };
		}
		System.out.println("Se carga data table a memoria excitosamente.");
		return triarray;
	}
	
	protected static Object[] getTests(String config_property_name, String[] resultTable) throws Throwable
	{
		System.out.println("Cargando informacion del .xlsx del test: "+ config_property_name);
		firstRow = Integer.valueOf(ConfigProperties.getPropertyValue("StartRow"));
		LastRow = Integer.valueOf(ConfigProperties.getPropertyValue("EndRow"));

		if(firstRow < 1)
		{
			firstRow = 1;
		}
		counter = firstRow;
		
		inicializeReport(resultTable);
		
		String test_file_path = ConfigProperties.getPathofFile(config_property_name);
		Object[][] data = file.getData(test_file_path);
		if(LastRow > data.length)
		{
			LastRow = data.length;
		}
		return convertToJUnitArray(data);
	}
}
