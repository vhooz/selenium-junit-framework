package utils;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions; 
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author - InfosGroup - Jean Guerra
 * @version $I$, $G$
 * @since 1.0
 * Last Update: July 30, 2018 - victor Orrego
 * Add Cross-Platform functionality for Mac OS and Windows compatibility. 
 */
public final class Driver
{
	private String browser;
	//private WebDriver driver;
	
	public Driver(String browser) throws Exception
	{
		try {
			
			if(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("firefox") || browser.equalsIgnoreCase("explorer"))
			{
				this.browser = browser;
			}
			else
			{
				System.out.println(browser + " no está implementado.");
				//throw t;
				throw new Exception(browser + " no está implementado.");
				//Esta excepcion no esta siendo capturada por el
			}
			
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}

	}
	
	public WebDriver getDriver() throws Exception
	{
		switch(browser.toLowerCase())
		{
		
			case "chrome":
				
		        WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				options.setCapability(ChromeOptions.CAPABILITY,options );
				return new ChromeDriver(options);
				
			case "firefox":
				
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions caps = new FirefoxOptions();
				caps.setCapability("acceptInsecureCerts", true);
				
				return new FirefoxDriver(caps);
				
			case "explorer":
				
				if(SystemUtils.IS_OS_WINDOWS_10)
				{
					WebDriverManager.iedriver().setup();
					return new InternetExplorerDriver();
				}
				else 
				{
					WebDriverManager.edgedriver().setup();
					return new EdgeDriver();
				}
				
			default:
				//FIXME NO ESTA SALIENDO ESTE MENSAJE
				throw new Exception(browser + " no está implementado.");
		}

	}
	
	public void config(WebDriver driver)  throws Exception
	{
		//#IMPLICITLY_WAIT_OBJECT =1
		//#IMPLICITLY_WAIT_PAGE = 2
		long IMPLICITLY_WAIT_PAGE = Long.valueOf("2");
		 driver.manage().deleteAllCookies();
		// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_PAGE, TimeUnit.MINUTES);
		//driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(IMPLICITLY_WAIT_PAGE, TimeUnit.MINUTES);
	}
}