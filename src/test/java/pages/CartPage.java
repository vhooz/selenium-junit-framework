package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Report;

public class CartPage {
	
	
	WebDriver driver;
	Report report;
	
	@FindBy(xpath="(//div[@class='total_area']/ul/li)[4]")
	WebElement total;
	
	public CartPage(WebDriver driver,Report report){
		this.driver = driver;
		this.report = report;
		PageFactory.initElements(driver, this);	
	}
	
	public void validateTotalPrice() throws Exception{
		String totalValue = total.getText();
		report.testPass(totalValue);
	}
	
}
