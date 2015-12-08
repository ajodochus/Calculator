import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MiniCalcPage implements IMiniCalcPage {
	 @FindBy(id="calculator-button-ac")
	    private WebElement clear;
	 
	 public IMiniCalcPage clear() {
	        clear.click();
	        return this;
	    }
}
