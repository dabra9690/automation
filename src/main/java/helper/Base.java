package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

//brower intilizationcode

public class Base {
	public static Properties prop;
	public static WebDriver driver;
	static {
		try {
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/recources/env.properties");
			prop = new Properties();
			prop.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 @Before
	public void Intalization() {
		String browsername = prop.getProperty("browser");// chrome
		if (browsername.equals("chrome")) {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--incognito");
			driver = new ChromeDriver(option);

		} else if (browsername.equals("Firefox")) {
			FirefoxOptions option = new FirefoxOptions();
			option.addArguments("--incognito");

			driver = new FirefoxDriver(option);
		}

		driver.get(prop.getProperty("appurl"));//url
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(14));

	}
	@After
	public void tearDown(Scenario s) {
		if (s.isFailed()) {
			//take screenshots
			TakesScreenshot ts= (TakesScreenshot)driver;
			File src= ts.getScreenshotAs(OutputType.FILE);
			try {
				FileHandler.copy(src, new File("Screenshots/"+s.getName()+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		driver.quit();
	}
	
		
	}


