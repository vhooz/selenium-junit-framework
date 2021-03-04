package pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Report;

public class HomePage {

	private WebDriver driver;
	private Report report;
	@SuppressWarnings("unused")
	private static final String fs = File.separator;

	/*** Web elemetnes ***/
	@FindBy(xpath = "(//li/a[@href='login.html'])[1]")
	WebElement logIn;

	@FindBy(xpath = "(//li/a[@href= 'cart.html'])[1]")
	WebElement cart;

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement searchBox;

	@FindBy(id = "SubmitLogin")
	WebElement submitButton;

	@FindBy(xpath = "//a[@href='404.html']")
	WebElement btn404;

	/*** Web elemetnes ***/

	public HomePage(WebDriver driver, Report report) {
		this.driver = driver;
		this.report = report;
		PageFactory.initElements(driver, this);
	}

	public void clicklogInbutton() {
		logIn.click();
		report.testPass("Se hace click en el boton log in.");
	}

	public void clickCartbutton() {
		cart.click();
		report.testPass("Se hace click en el carrito.");
	}

	public void writeInTheSearchBox(String searchWord) throws Exception {

		this.searchBox.clear();
		this.searchBox.sendKeys(searchWord);
		report.testPass("Se ingresa ( " + searchWord + " ) en el cuadro de busqueda", "writeInTheSearchBox", false);
	}

	public void displayStoreCategory(String menuname) throws Throwable {

		try {
			driver.findElement(By.xpath(
					"//*[@id='accordian']/descendant::div[@class='panel panel-default']/descendant::div[@class='panel-heading']/descendant::a[@href='#"
							+ menuname + "']"))
					.click();
			Thread.sleep(1000);
			report.testPass("Se hace click en la seccion " + menuname, "gotoStoreCategory" + menuname, false);
		} catch (Throwable t) {

			report.testFail("fallo al hacer click en " + menuname);
			throw t;
		}

	}

	public void goTo404Page() {

		btn404.click();
		report.testPass("El usuario hace click en el boton 404.");

	}

}
