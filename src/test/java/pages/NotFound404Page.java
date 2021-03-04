package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Report;

public class NotFound404Page {

	WebDriver driver;
	Report report;

	@FindBy(xpath = "//h2/a[@href='index.html']")
	WebElement goBackHome;

	public NotFound404Page(WebDriver driver, Report report) {
		this.driver = driver;
		this.report = report;
		PageFactory.initElements(driver, this);
	}

	public void validate404Page() throws Exception {

		Thread.sleep(3000);
		String url = driver.getCurrentUrl();
		System.out.print(url);
		if (url.contains("404.html")) {

			report.testPass("Se ingresa a la pagina 404", "404pass", true);
		} else {
			report.testFail("No se ingresa a la pagina 404", "404fail", false);
		}

	}

	public void goBackHome() throws InterruptedException {

		goBackHome.click();
		report.testPass("El usuario hace click en el botón para regresar a la pagina principal.");
		Thread.sleep(3000);

	}

}
