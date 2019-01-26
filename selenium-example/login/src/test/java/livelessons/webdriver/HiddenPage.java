package livelessons.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class HiddenPage {

	private WebDriver driver;

	public HiddenPage(WebDriver driver) {
		this.driver = driver;
	}

	public static <T> T to(WebDriver driver, Class<T> page) {
		driver.get("http://localhost:8080/");
		return (T) PageFactory.initElements(driver, page);
	}

	public void assertAt() {
		assertThat(this.driver.getTitle()).endsWith("Yahaha! You Found Me!");
	}

}
