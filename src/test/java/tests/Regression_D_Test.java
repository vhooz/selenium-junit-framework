package tests;

import utils.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pages.BasePage;
import pages.HomePage;
import pages.NotFound404Page;

@RunWith(Parameterized.class)
public class Regression_D_Test extends BaseTest {
	private String className = this.getClass().getSimpleName();
	static Object[] data;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                               * 
	 *  Editar esta información acorde al escenario. *
	 *                                               *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	private static String [] resultFile = new ResultTables().getResultsTable();
	private static String CONFIG_DATAFILE_PROPERTY = "data_table_D";
	private DataTable dto; 
	/*----------------------------------------------*/
	


	
	/* Cambiar clase DataTable si se necesita usar otro formato de data table. */
	/* dto guarda en memoria los parametros del escenario en ejecucion. */
	
	public Regression_D_Test(Object[] data) throws Exception
	{
		Regression_D_Test.data = data;
		System.out.println("--Constructure Regression_D_Test.java--");
	}
	
	@Parameterized.Parameters//( name = "{index}: {0}-{1}" )
	public static Object[] Data() throws Throwable
	{
		return getTests(CONFIG_DATAFILE_PROPERTY,resultFile);
	}
	
	@Before
    public void runBeforTest() throws Exception {
        System.out.println("@Before each test.");
		driver = null;
		this.dto = null;		
		try
		{
			dto = new DataTable(data);
			Driver driverConfig = new Driver(dto.getBrowser());
			driver = driverConfig.getDriver();
			
			String testID = Integer.toString(dto.getTestCase());
			String testBrowser = dto.getBrowser();
			String testDescription = dto.getTestDescription();
			String testParameter = dto.getTestParameter();
			String result_ID = counter+"_"+testID+"_"+className;

			String
			testHeader = "<b>" + "Prueba #" + testID + "</b>" + "<br>"; 
			testHeader += "Navegador: " + "<b>" + testBrowser + "</b>" + "<br>";
			testHeader += "Parametro: " + "<b>" + testParameter+"</b>" + "<br>";
			
			report.startTest(testHeader, driver, testDescription,result_ID);
			
			System.out.println("Initializing TC #" + testID);
		}
		catch(Exception e)
		{
			String
			testHeader = "<b>" + "Prueba #</b>" + counter + "<br>"; 
			testHeader += "<b>" + " SETEO DE DATOS FALLIDO " + "</b>";
			report.startTest(testHeader, null,null,null);
			report.testFail(e.getMessage());
			throw e;
		}

    }

	@Test
	public void test_D_exceution() throws Throwable 
	{		 
		String testParameter;
		
		try
		{
			/* * * INICIO DE PRUEBA * * */
			
			testParameter = dto.getTestParameter();
			BasePage basePage = new BasePage(driver, report);
			basePage.goToHomePage();
			HomePage home = new HomePage(driver, report);
			home.writeInTheSearchBox(testParameter);
			home.goTo404Page();
			
			NotFound404Page notFound = new NotFound404Page(driver, report);
			notFound.validate404Page();
			notFound.goBackHome();
			
			/*this test is design to fail. leave it like that*/
			//home.displayStoreCategory(testParameter);
			
			/*** FIN DE PRUEBA ***/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			throw e;
		}
	}

	@After
    public void endTest() throws Exception {
		tearDown(data,testResult);
    }
}

