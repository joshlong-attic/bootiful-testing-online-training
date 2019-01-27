package selenium;

import org.openqa.selenium.WebDriver;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public class Page1 {

	private final WebDriver driver;

	public Page1(WebDriver driver) {
		this.driver = driver;
	}

	public String title() {
		return (this.driver.getTitle());
	}
}
