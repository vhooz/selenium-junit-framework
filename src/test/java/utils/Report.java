package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * @author Jean Guerra
 * @version $I$, $G$
 * @since 1.0
 */
public final class Report {
	private WebDriver driver;
	private ExtentReports extent;
	private ExtentTest test;
	private String browserName;
	private String testId;

	private static final String fs = File.separator;
	private static final String currentTime = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date())
			.replace(":", ".").replace("/", "-");
	private static final String REPORTCONFIG_FILE = "." + fs + "src" + fs + "test" + fs + "resources" + fs
			+ "defaultData" + fs + "reportconfig.xml";
	private static final String OUTPUT_FOLDER = "." + fs + "target" + fs + "logs" + fs + currentTime + fs;

	public Report() throws IOException {
		String FILE_NAME = "report.html";
		this.extent = new ExtentReports(OUTPUT_FOLDER + FILE_NAME, true);
		extent.loadConfig(new File(REPORTCONFIG_FILE));
		System.out.println("Report Files:  " + OUTPUT_FOLDER);
	}

	public String getReportPath() {
		return OUTPUT_FOLDER;
	}

	/**
	 * @param name        Título del test.
	 * @param driver      WebDriver utilizado para cuando hay que hacer logs con
	 *                    imágenes.
	 * @param browser     Permite que pueda omitirse (para el caso de IE) el tipo de
	 *                    foto que no es de la página entera.
	 * @param descripción del escenario a ejecutar.
	 */
	public void startTest(String name, WebDriver driver, String testDescription, String testId) {
		this.test = extent.startTest(name, testDescription);
		this.driver = driver;
		this.testId = testId;

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		this.browserName = cap.getBrowserName().toLowerCase();
		// System.out.println(browserName);
	}

	public void testPass(String description) {
		this.test.log(LogStatus.PASS, description);
	}

	public void testPass(String description, String imgName, boolean isFullSize) throws Exception {
		try {
			testWithCapture(description, imgName, isFullSize, LogStatus.PASS);
		} catch (Exception e) {
			this.test.log(LogStatus.FAIL, "Error irrecuperable del framework, el test no concluirá correctamente.");
			throw e;
		}
	}

	public void testWarning(String description) {
		this.test.log(LogStatus.WARNING, description);
	}

	public void testWarning(String description, String imgName, boolean isFullSize) throws Exception {
		try {
			testWithCapture(description, imgName, isFullSize, LogStatus.WARNING);
		} catch (Exception e) {
			this.test.log(LogStatus.FAIL, "Error irrecuperable del framework, el test no concluirá correctamente.");
			throw e;
		}
	}

	public void testFail(String description) {
		this.test.log(LogStatus.FAIL, description);
	}

	public void testFail(String description, String imgName, boolean isFullSize) throws Exception {
		try {
			testWithCapture(description, imgName, isFullSize, LogStatus.FAIL);
		} catch (Exception e) {
			this.test.log(LogStatus.FAIL, "Error irrecuperable del framework, el test no concluirá correctamente.");
			throw e;
		}
	}

	private void testWithCapture(String description, String name, boolean isFullSize, LogStatus logStatus)
			throws Exception {
		String path = "Screenshots";
		name += ".png";
		String imgPath = "test_" + testId + fs + path;
		String imgFullPath = OUTPUT_FOLDER + fs + imgPath;

		new File(imgFullPath).mkdirs();
		File file = new File(imgFullPath + fs + name);
		file.createNewFile();

		if (isFullSize && !browserName.equalsIgnoreCase("InternetExplorer")) {
			if (browserName.equalsIgnoreCase("firefox")) {
				// Las nuevas versiones de FireFox generan problemas si no se coloca este
				// tiempo.
				Thread.sleep(5000);
			}

			ImageIO.write(new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver)
					.getImage(), "PNG", file);
		} else {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), file);
		}

		String html = test.addScreenCapture(imgPath + fs + name);
		this.test.log(logStatus, description + "<br>" + html);
	}

	public String getTestStatus() {
		LogStatus status = test.getRunStatus();
		return status.toString();
	}

	public void endTest() {
		extent.endTest(test);
		extent.flush();
	}

	public void endReport() {
		extent.flush();
		extent.close();
	}
}