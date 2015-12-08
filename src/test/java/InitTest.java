import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class InitTest {

	@Test
	public void firstTest(){
		// fake test
		System.out.println("fake test ok");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.de");
	}
}
