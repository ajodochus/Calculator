
import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidConfiguration;
import io.selendroid.SelendroidDriver;
import io.selendroid.SelendroidLauncher;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.simpleHTTPServer.SimpleHTTPServer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class CalcDemoTest {
    private WebDriver driver;
    private POConfig testconfig;
    private ICalcPage calc;
    private SelendroidLauncher selendroidServer;
    private SimpleHTTPServer webServer;

    @BeforeSuite
    public void suiteSetUp() throws Exception {
        testconfig = new POConfig();

        // Initialize driver we want to use
        String browserToUse = testconfig.getProperty("browserToUse");
        String serverToUse	= testconfig.getProperty("serverToUse");

        // Initialize AUT
        if (browserToUse.equals("SELENDROID")) {
            if (selendroidServer != null) {
                selendroidServer.stopSelendroid();
            }
            SelendroidConfiguration config = new SelendroidConfiguration();
            config.addSupportedApp(CalcDemoTest.class.getResource("/DemoCalc.apk").getPath());
            selendroidServer = new SelendroidLauncher(config);
            selendroidServer.lauchSelendroid();
        } else {
        		//webServer = new SimpleHTTPServer(80, new File("src/test/resources/htmlcalcdemo"));
                //webServer.start();           
            // load the calculator page
        	
        }

        if (browserToUse.equals("HTMLUNIT")) {
            driver = new HtmlUnitDriver(true);
        } else if (browserToUse.equals("FIREFOX")) {
            FirefoxProfile ffprofile = new FirefoxProfile();
            ffprofile.setPreference("intl.accept_languages", "en-us,en");
            driver = new FirefoxDriver();
        } else if (browserToUse.equals("CHROME")) {
            System.setProperty("webdriver.chrome.driver", CalcDemoTest.class.getResource("/chromedriver").getPath());
            driver = new ChromeDriver();
        } else if (browserToUse.equals("SAFARI")) {
            driver = new SafariDriver();
        }  else if (browserToUse.equals("SELENDROID")) {
            SelendroidCapabilities caps = new SelendroidCapabilities("ch.racic.selendroid.democalc:1.0");
            WebDriver driver = new SelendroidDriver(caps);
        }

        

        driver.navigate().to(testconfig.getProperty("targetURL"));
        ((JavascriptExecutor) driver).executeScript("window.focus();");
        calc = getPageObject(ICalcPage.class);
    }

   @AfterSuite
    public void suiteTearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (selendroidServer != null) {
            selendroidServer.stopSelendroid();
        }
        if (webServer != null) {
        	System.out.println("trying to stop sever");
            webServer.stop();
        }

    }
    
   @Test
   public void targetServerReady() {
	  
	    

	   //Assert.assertTrue("Application initialized?", calc.verifyInitialized());
	   System.out.println("page title: " + calc.getButton7Text());
	   Assert.assertTrue("Starts with 0?", calc.displayed() == 0);
	
   }
   
    @Test
    public void simpleTest(){
    	calc.n2();
    	calc.multiply();
    	calc.n5();
    	calc.equal();
    }

   
    /**
     * helper methods *
     */
    public <T> T getPageObject(Class<T> pageInterfaceToProxy) {
        T page = null;
        try {
            Class<T> pageClassToProxy = testconfig.getPageObjectImplementation(driver, pageInterfaceToProxy);
            page = PageFactory.initElements(driver, pageClassToProxy);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException) {
                InvocationTargetException exc = (InvocationTargetException) e.getCause();
                if (exc.getTargetException() != null) {
                    Assert.fail(exc.getTargetException().getMessage());
                } else {
                    Assert.fail(exc.getMessage());
                }
            }
            Assert.fail(e.getMessage());
        }
        return page;
    }


}

	

