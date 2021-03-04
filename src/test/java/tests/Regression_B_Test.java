package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pages.BasePage;
import pages.HomePage;
import pages.LogInPage;
import utils.DataTable;
import utils.Driver;
import utils.ResultTables;

@RunWith(Parameterized.class)
public class Regression_B_Test extends BaseTest {

	private String className = this.getClass().getSimpleName();
	static Object[] data;

	private static String[] resultFile = new ResultTables().getResultsTableB();
	private static String CONFIG_DATAFILE_PROPERTY = "data_table_B";
	private DataTable dto;

	/*----------------------------------------------*/

	/* Cambiar clase DataTable si se necesita usar otro formato de data table. */
	/* dto guarda en memoria los parametros del escenario en ejecucion. */

	public Regression_B_Test(Object[] data) throws Exception {
		Regression_B_Test.data = data;
		System.out.println("--Constructure Regression_B_Test.java--");
	}

	@Parameterized.Parameters // ( name = "{index}: {0}-{1}" )
	public static Object[] Data() throws Throwable {
		return getTests(CONFIG_DATAFILE_PROPERTY, resultFile);
	}

	@BeforeClass
	public static void onceExecutedBeforeAll() {
		System.out.println("@BeforeClass: onceExecutedBeforeAll");
	}

	@Before
	public void runBeforTest() throws Exception {
		System.out.println("@Before each test.");
		driver = null;
		this.dto = null;

		try {
			dto = new DataTable(data);
			Driver driverConfig = new Driver(dto.getBrowser());
			driver = driverConfig.getDriver();

		} catch (Exception e) {
			String testHeader = "<b>" + "Prueba #</b>" + counter + "<br>";
			testHeader += "<b>" + " SETEO DE DATOS FALLIDO " + "</b>";
			report.startTest(testHeader, null, null, null);
			report.testFail(e.getMessage());
			throw e;
		}

	}

	@Test
	public void test_A_exceution() throws Throwable {
		String testParameter;

		try {
			initializeTest("Regresion B", className);

			/* * * INICIO DE PRUEBA * * */

			testParameter = dto.getTestParameter();
			BasePage basePage = new BasePage(driver, report);
			basePage.goToHomePage();

			HomePage home = new HomePage(driver, report);
			home.clicklogInbutton();

			LogInPage logIn = new LogInPage(driver, report);
			logIn.createNewUser(testParameter, "test@test.com", "password");

			/* * * FIN DE PRUEBA * * */
			// TODO determinar el estado verdadero del test con algun metodo del reporte
			this.testResult = "PASS";
		} catch (Exception e) {
			this.testResult = "FAIL";
			e.printStackTrace();

			throw e;
		}
	}

	private void initializeTest(String escenario, String className) throws Throwable {

		String brakeline = "<br>";
		String testID = Integer.toString(dto.getTestCase());
		String testBrowser = dto.getBrowser();
		String testDescription = dto.getTestDescription();
		String testParameter = dto.getTestParameter();
		String result_ID = counter + "_" + testID + "_" + className;

		System.out.println("Initializing TC #" + testID);

		String testHeader = "<b>" + "ID # " + testID + "</b>" + brakeline;
		testHeader += "Escenario: " + "<b>" + escenario + "</b>" + brakeline;
		testHeader += "Navegador: " + "<b>" + testBrowser + "</b>" + brakeline;
		testHeader += "Parametro: " + "<b>" + testParameter + "</b>";

		report.startTest(testHeader, driver, testDescription, result_ID);

	}

	@After
	public void endTest() throws Exception {
		tearDown(data, testResult);
	}
}
