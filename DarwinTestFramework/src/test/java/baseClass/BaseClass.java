package baseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentTest;

import testUtils.ExtentReportHandler;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	static Properties prop;

	
	public Response response;
	public static ExtentTest test;
	public Logger logger;
	public ExtentReportHandler ReportHandler = new ExtentReportHandler();
	public static String testDataPath;

	@BeforeClass
	public void setup() {
		logger = Logger.getLogger("API Logger");
		PropertyConfigurator.configure("Log4j.properties");
		logger.setLevel(Level.DEBUG);
		getTestDatapath();
	}

	public static Properties loadProperties() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\Config\\config.properties");
			prop.load(fis);
			System.out.println(fis.read());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String getUserBaseURI() {
		prop = loadProperties();
		String baseURI = (String) prop.get("userBaseURI");
		System.out.println("Base URI is: " + baseURI);
		return baseURI;
	}

	public static String getTestDatapath() {
		prop = loadProperties();
		String path = (String) prop.get("Testdatapath");
		System.out.println("Test Data Path: " + path);
		testDataPath = System.getProperty("user.dir") + path;
		return testDataPath;
	}

	@AfterClass
	public void Teardown() {
		ReportHandler.getExtent().flush();
	}

}
