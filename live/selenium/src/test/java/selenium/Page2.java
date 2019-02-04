package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public class Page2 {

	private final WebDriver driver;

	public Page2(WebDriver driver) {
		this.driver = driver;
	}

	WebElement message() {
		return this.driver.findElement(By.id("message"));
	}

}
