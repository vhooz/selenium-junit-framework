package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pages.BasePage;
import pages.HomePage;
import utils.DataTable;
import utils.Driver;
import utils.ResultTables;

/**
 * Click derecho en la clase > Run As > JUnit tests > Regression_C_Test-tests
 * 
 * @author Victor Orrego Documentation:
 *         https://stackoverflow.com/questions/32776335/when-using-junits-parameterized-can-i-have-some-tests-still-run-only-once
 *
 */
@RunWith(Enclosed.class)
public class Regression_C_Test extends BaseTest {

	@RunWith(Parameterized.class)
	public static class PrameterizedTests extends Regression_C_Test {

		// private String className = this.getClass().getSimpleName();
		static Object[] data;

		private static String[] resultFile = new ResultTables().getResultsTableC();
		private static String CONFIG_DATAFILE_PROPERTY = "data_table_C";
		private DataTable dto;

		/*----------------------------------------------*/

		public PrameterizedTests(Object[] data) throws Exception {
			PrameterizedTests.data = data;
			System.out.println("--Constructure Regression_C_Test.ParameterizedTests--");
		}

		@Parameterized.Parameters // ( name = "{index}: {0}-{1}" )
		public static Object[] Data() throws Throwable {
			return getTests(CONFIG_DATAFILE_PROPERTY, resultFile);
		}

		@Before
		public void runBeforTest() throws Throwable {
			System.out.println("@Before each test.");
			driver = null;
			dto = null;

			try {
				dto = new DataTable(data);
				Driver driverConfig = new Driver(dto.getBrowser());
				driver = driverConfig.getDriver();

				String brakeline = "<br>";
				String testID = Integer.toString(dto.getTestCase());
				String testBrowser = dto.getBrowser();
				String testDescription = dto.getTestDescription();
				String testParameter = dto.getTestParameter();
				String result_ID = counter + "_" + testID + "_" + this.getClass().getSimpleName();
				System.out.println("Initializing TC #" + testID);
				String testHeader = "<b>" + "ID #" + testID + "</b>" + brakeline;
				testHeader += "Escenario: " + "<b>" + "Regresion C" + "</b>" + brakeline;
				testHeader += "Navegador: " + "<b>" + testBrowser + "</b>" + brakeline;
				testHeader += "Parametro: " + "<b>" + testParameter + "</b>" + brakeline;

				report.startTest(testHeader, driver, testDescription, result_ID);
			} catch (Throwable e) {
				String testHeader = "<b>" + "Prueba #</b>" + counter + "<br>";
				testHeader += "<b>" + " SETEO DE DATOS FALLIDO " + "</b>";
				report.startTest(testHeader, null, null, null);
				report.testFail(e.getMessage());
				throw e;
			}

		}

		@Test
		public void C_Parametrized_Test() throws Throwable {
			String testParameter;

			try {
				/* * * INICIO DE PRUEBA * * */

				testParameter = dto.getTestParameter();

				BasePage basePage = new BasePage(driver, report);
				basePage.goToHomePage();
				HomePage home = new HomePage(driver, report);
				home.displayStoreCategory("mens");
				home.writeInTheSearchBox(testParameter);

				/* * * FIN DE PRUEBA * * */
				// TODO determinar el estado verdadero del test con algun metodo del reporte
				this.testResult = "PASS";
			} catch (Exception e) {
				this.testResult = "FAIL";

				e.printStackTrace();

				throw e;
			}
		}

		@After
		public void endTest() throws Exception {
			tearDown(data, testResult);
		}

	}

	public static class SingleTests {
		private static String[] resultFile = new ResultTables().getResultsTableB();

		@Before
		public void runBeforTest() throws Throwable {
			System.out.println("@Before each test.");
			driver = null;
			try {
				inicializeReport(resultFile);
				Driver driverConfig = new Driver("chromee");
				driver = driverConfig.getDriver();

				String brakeline = "<br>";
				String testID = Integer.toString(counter);
				String testBrowser = "googlechrome";
				String testDescription = "single test escenario";
				String testParameter = "special";
				String result_ID = counter + "_" + testID + "_" + this.getClass().getSimpleName();

				String testHeader = "<b>" + "ID #" + testID + "</b>" + brakeline;
				testHeader += "Escenario: " + "<b>" + "Regresion C - Special Case" + "</b>" + brakeline;
				testHeader += "Navegador: " + "<b>" + testBrowser + "</b>" + brakeline;
				testHeader += "Parametro: " + "<b>" + testParameter + "</b>";

				report.startTest(testHeader, driver, testDescription, result_ID);

				System.out.println("Initializing TC #" + testID);
			} catch (Exception e) {
				// FIXME ESTO NO ESTA FUNCIONANDO!
				String testHeader = "<b>" + "Prueba #</b>" + counter + "<br>";
				testHeader += "<b>" + " SETEO DE DATOS FALLIDO " + "</b>";
				report.startTest(testHeader, null, null, null);
				report.testFail(e.getMessage());
				throw e;
			}

		}

		@Test
		public void C_Single_Test() throws Throwable {

			/*
			 * En ocaciones, existen pruebas 'negativas' o con condiciones que solo vale la
			 * pena correrlas una sola vez.
			 * 
			 * Este metodo ejecutara ese unico escenario.
			 */
			System.out.println("Single 'Special' Scenario to test.");

			try {
				/* * * INICIO DE PRUEBA * * */

				BasePage basePage = new BasePage(driver, report);
				basePage.goToHomePage();
				HomePage home = new HomePage(driver, report);
				/* Este escenario falla para propositos de demostración */
				home.displayStoreCategory("fail");

				/*** FIN DE PRUEBA ***/
			} catch (Exception e) {
				e.printStackTrace();

				throw e;
			}
		}

		@After
		public void endTest() throws Exception {
			tearDown();
		}

	}
}
