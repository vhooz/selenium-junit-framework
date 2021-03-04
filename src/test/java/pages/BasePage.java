package pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Report;

/**
 * @author Victor Orrego
 * @version 1.0
 * @since Marzo 30, 2020.
 */
public class BasePage {
	private WebDriver driver;
	private Report report;
	protected static WebDriverWait wait_60s;
	protected static WebDriverWait wait_30s;
	protected static WebDriverWait wait_10s;
	protected static WebDriverWait wait_5s;
	protected static WebDriverWait wait_2s;
	private static final String fs = File.separator;

	@FindBy(xpath = "//div[@id='invalidCertMsg']/following-sibling::div/a")
	private WebElement weBitdefender;

	@FindBy(xpath = "//div[@id='main-message']")
	private WebElement weconfiguration;

	@FindBy(xpath = "//button[@id='details-button']")
	private WebElement weBconfig;

	@FindBy(xpath = "//a[@id='proceed-link']")
	private WebElement weSLink;

	public BasePage(WebDriver driver, Report report) {
		this.driver = driver;
		this.report = report;
		BasePage.wait_60s = new WebDriverWait(driver, 60);
		BasePage.wait_30s = new WebDriverWait(driver, 30);
		BasePage.wait_10s = new WebDriverWait(driver, 10);
		BasePage.wait_5s = new WebDriverWait(driver, 5);
		BasePage.wait_2s = new WebDriverWait(driver, 2);
		PageFactory.initElements(this.driver, this);
	}

	public void goToHomePage() throws Throwable {
		try {

			String url = System.getProperty("user.dir") + fs + "src" + fs + "test" + fs + "resources" + fs
					+ "practicePage" + fs + "index.html";
			System.out.println(url);
			driver.navigate().to("file://" + url);
			// driver.get("https://www.google.com/");
			report.testPass("Se ingresa a la pagina de inicio", "goToHomePage", true);
		} catch (Throwable t) {

			report.testFail("no se desplego la página de inicio.", "goToHomePage", true);
			throw t;
		}
	}

	@SuppressWarnings("unused")
	private void openConfiguration() throws Exception {
		try {
			boolean result = weconfiguration.isDisplayed();
			if (result == true) {
				weBconfig.click();
				weSLink.click();
			}
		} catch (Throwable t) {
			// De no mostrarse bloqueada, debe continuar con normalidad.
		}
	}

	@SuppressWarnings("unused")
	private void closeBitdefenderWindow() throws Exception {
		try {
			weBitdefender.click();
		} catch (Throwable t) {
			// De no mostrarse la pantalla del firewall, continuar con normalidad.
		}
	}

	protected void setElementToAbsolute(String xpath) throws InterruptedException {
		WebElement element = wait_10s.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'position:absolute')", element);
	}

	protected void setHeaderPositionAbsolute(String headerXpath) throws InterruptedException {
		WebElement header = wait_10s.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headerXpath)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'position:absolute')", header);
	}

	protected void setHeaderPositionStatic(String headerXpath) throws InterruptedException {
		WebElement header = wait_10s.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headerXpath)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'position:static')", header);
	}

	protected void scrollTo(int x, int y) throws InterruptedException {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
		String javaScript = "window.scrollTo(" + String.valueOf(x) + ", " + String.valueOf(y) + "); ";
		javaScriptExecutor.executeScript(javaScript);
		Thread.sleep(2000);
	}

	protected void scrollToWebElement(WebElement element) throws InterruptedException {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		javaScriptExecutor.executeScript(scrollElementIntoMiddle, element);
	}

	protected boolean existsElement(String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected boolean existsElement(WebElement WebElement) {
		try {
			WebElement.isDisplayed();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected boolean elementIsVisible(WebElement WebElement) {
		try {
			wait_5s.until(ExpectedConditions.visibilityOf(WebElement));
		} catch (Exception e) {
			System.out.println("El elemento no es visible");
			return false;
		}
		return true;
	}

	protected boolean elementIsVisible(String xpath) {
		try {
			wait_5s.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
		} catch (Exception e) {
			System.out.println("El elemento no es visible");
			return false;
		}
		return true;
	}

	public String getCurrentUrl() {
		try {
			String url = driver.getCurrentUrl();
			return url;
		} catch (Exception e) {
			// do nothing
			return null;
		}

	}

}
