package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BaseClass {

	
	  public static final String tname = null;
	public static WebDriver driver;
	  public Logger logger; //Log4j
	  public Properties p;
	  
	  
	  
	 @Parameters({"os","browser"})
	 @BeforeClass(groups= {"Sanity","Regression","Master"})
	  
	  
	  public void setup(@Optional("Windows") String os, @Optional("chrome") String br) throws IOException
	  {
		  
		    
		   //Loading config.properties file
		  
		  
		   FileReader file = new FileReader("./src/test/resources/config.properties");
           p = new Properties();
		   p.load(file);
		  
		   logger=LogManager.getLogger(this.getClass());
		    
		   if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			    DesiredCapabilities capabilities = new DesiredCapabilities();
			    
			    // OS selection
			    if (os.equalsIgnoreCase("Windows")) {
			        capabilities.setPlatform(Platform.WIN11);
			    }
			    else if (os.equalsIgnoreCase("linux"))
			    {
			        capabilities.setPlatform(Platform.LINUX);
			    } 
			    else if (os.equalsIgnoreCase("mac"))
			    {
			        capabilities.setPlatform(Platform.MAC);
			    }
			    else
			    {
			        throw new IllegalArgumentException("No matching OS found for: " + os);
			    }
			    
			    // Browser selection
			    switch (br.toLowerCase()) {
			        case "chrome":
			            capabilities.setBrowserName("chrome");
			            break;
			        case "edge":
			            capabilities.setBrowserName("MicrosoftEdge");
			            break;
			        case "firefox":
			            capabilities.setBrowserName("firefox");
			            break;
			        default:
			            throw new IllegalArgumentException("No matching browser found for: " + br);
			    }
			    
			    try {
			        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			    } catch (Exception e) {
			        throw new RuntimeException("Failed to connect to Selenium Grid: " + e.getMessage(), e);
			    }
			}

			   
		   
		 // local envirnoment
	
	    if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		  {
			switch(br.toLowerCase())
			{
			case "chrome":driver=new ChromeDriver();break;
			case "edge":driver=new EdgeDriver();break;
			case "firefox":driver=new FirefoxDriver();break;
			default:System.out.println("Invalid browser name..");return;
			}
		  }	
			
			
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.get(p.getProperty("appURL")); //reading url from properties file.
			driver.manage().window().maximize();
	  }
	
	
	  @AfterClass(groups= {"Sanity","Regression","Master"})
	  public void tearDown()
	  {
		  driver.quit();
	  }
	  
	  
	  
	  public String randomeString()
		{
			String generatedstring=RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		}
		
		public String randomeNumber()
		{
			String generatednumber=RandomStringUtils.randomNumeric(10);
			return generatednumber;
		}
		
		public String randomeAlphaNumberic()
		{
			String generatedstring=RandomStringUtils.randomAlphabetic(3);
			String generatednumber=RandomStringUtils.randomNumeric(3);
			return (generatedstring+"@"+generatednumber);
		}
	
		
		//Screenshot
	public String captureScreen(String tname) throws IOException{
			
			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			
			TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
			File sourceFile =takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\screenshorts\\"+ tname + " " +timeStamp+ ".png";
			File targetFile=new File(targetFilePath);
			
			// Ensure the screenshots directory exists
	        File screenshotDir = new File(System.getProperty("user.dir") + "\\screenshots\\");
	        if (!screenshotDir.exists()) {
	            screenshotDir.mkdirs();
	        }
	        
			FileUtils.copyFile(sourceFile, targetFile);

			
			return targetFilePath;
				
		}

	}

		

