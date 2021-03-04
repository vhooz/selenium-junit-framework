package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

/**
 * https://mkyong.com/unittest/junit-4-tutorial-5-suite-test/
 * 
 * *******PARA CORRER ESTO EN MAVEN USAR bash COMANDO*********
 * *  mvn test -Dtest=classname (mvn test -Dtest=TestRunner) *
 * ***********************************************************
 * https://stackoverflow.com/questions/1873995/run-a-single-test-method-with-maven
 * https://www.guru99.com/junit-parameterized-test.html
 * https://mkyong.com/unittest/junit-4-tutorial-5-suite-test/
 * 
 * @author Victor Orrego
 *
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Regression_A_Test.class, //test case 1
        Regression_B_Test.class, //test case 2
        Regression_C_Test.class  //test case 3
})
public class TestRunner {
	//normally, this is an empty class
	
	/*El main permite correr como esto como un Java Aplication 
	 * (No es necesario, lo puedes correr coomo junit o mvn test sin el main)
	 * */
	public static void main(String[] args) {
		/*runClass ejecuta la clase que se le asigna en el parametro. 
		 * En este caso estamos corriendo esta misma clase TestRunner.class 
		 * que a su vez llama a los @Test A y B de sus respectivas claves.
		 */
		Result result = JUnitCore.runClasses(TestRunner.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
		
	}
}
