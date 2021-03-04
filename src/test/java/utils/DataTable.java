package utils;

/**
 * 
 * @author Jean Guerra
 * 
 * @apiNote Si se requiere usar un data table diferente en un caso de prueba, 
 * se debe duplicar esta clase y hacer los ajustes necesarios para poder hacer 
 * referencia a esa clase de data table desde la clase del test que lo requiere.
 * 
 * @implNote
 * ie:
 * 
 * Regression_c_Test.java -> crear una clase DaTable_C.java y ajustar acorde 
 * al requerimiento del data table para ejecutar la Regresión C 
 * 
 */
public class DataTable {
	
	/**
	 * Clase para cargar datos del excel
	 * @param oRow
	 * @throws Exception 
	 */
	
	private int testcase;
	private String browser;
	private String TestDescription;
	private String TestParameter;
	
	
	public DataTable(final Object[] oRow) throws Exception {
		
		setTestCase(oRow[0]);
		setBrowser(oRow[1]);
		setTestDescription(oRow[2]); 
		setTestParameter(oRow[3]);
		
		
	}
	
	//Set TestCase value
	
	final void setTestCase(Object testcase){
		this.testcase = Integer.parseInt(testcase.toString().trim());
	}
	public final int getTestCase() {
		return testcase;
	}
	
	//set Browser value
	
	final void setBrowser(Object browser) {
		String temp = browser.toString().trim().toLowerCase();
		this.browser = temp;
	}
	public final String getBrowser() {
		return browser;
	}
	
	//set Test Description
	
	final void setTestDescription(Object TestDescription) {
		this.TestDescription = TestDescription.toString().trim().toLowerCase();
		
	}
	
	public final String getTestDescription() {
		return TestDescription;
	}
	
	//set Test Parameter
	
	final void setTestParameter(Object TestParameter) {
		this.TestParameter = TestParameter.toString().trim().toLowerCase();
		
	}
	
	public final String getTestParameter() {
		return TestParameter;
	}

	

}
