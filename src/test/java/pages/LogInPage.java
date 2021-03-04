package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Report;

public class LogInPage {

	WebDriver driver;
	Report report;

	@FindBy(xpath = "//div[@class = 'signup-form']/form/input[@type = 'text']")
	WebElement signUpname;

	@FindBy(xpath = "//div[@class = 'signup-form']/form/input[@type = 'email']")
	WebElement signUpemail;

	@FindBy(xpath = "//div[@class = 'signup-form']/form/input[@type = 'password']")
	WebElement signUpPassword;

	@FindBy(xpath = "//*[@id='header']/div[1]/div/div/div[1]/div/ul/li[1]/a")
	WebElement phoneNumber;

	public LogInPage(WebDriver driver, Report report) {
		this.driver = driver;
		this.report = report;
		PageFactory.initElements(driver, this);
	}

	public void createNewUser(String firstName, String email, String password) throws Exception {
		
		this.signUpname.clear();
		this.signUpname.sendKeys(firstName);
		this.signUpemail.clear();
		this.signUpemail.sendKeys(email);
		this.signUpPassword.clear();
		this.signUpPassword.sendKeys(password);
		report.testPass("Se ingresa información del pasajero", "createNewUser", false);
	}

	public String getPhoneNumber() {
		return (phoneNumber.getText());
	}


	public void clickonSportswearProduct(String product) {

		//.//*[@id='accordian']/descendant::div[@class='panel panel-default']/descendant::div[@id='sportswear']/descendant::div[@class='panel-body']/descendant::ul/child::li/child::a[contains(text(),'Nike')]
		driver.findElement(By
				.xpath("//*[@id='accordian']/descendant::div[@class='panel panel-default']/descendant::div[@id='sportswear']/descendant::div[@class='panel-body']/descendant::ul/child::li/child::a[contains(text(),'"
						+ product + "')]")).click();
		//log("Clicked on "+product+" of Sportswear");
	}
	
	public void chickonMensProducts(String product){
		driver.findElement(By
				.xpath("//*[@id='accordian']/descendant::div[@class='panel panel-default']/descendant::div[@id='mens']/descendant::div[@class='panel-body']/descendant::ul/child::li/child::a[contains(text(),'"
						+ product + "')]")).click();
		//log("Clicked on "+product+" of Mens");
	}
}
