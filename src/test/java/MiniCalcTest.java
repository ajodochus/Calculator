import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class MiniCalcTest {

	
	private WebDriver driver;
	private POConfig testconfig;
	
	@Test
	public void miniTest() throws IOException{
		testconfig = new POConfig();
		driver = new FirefoxDriver();
		driver.get("http://localhost");
		
		
	}
}
