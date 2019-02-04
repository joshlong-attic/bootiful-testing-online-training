package selenium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SeleniumApplicationTest {

	@Autowired
	private WebDriver driver;

	@Test
	public void navigate() {
		driver.get("http://localhost:8080/p1.php");
		Page1 pg1 = PageFactory.initElements(driver, Page1.class);
		Assert.assertEquals(pg1.title(), "Page 1");
		By id = By.id("p2-link");
		WebElement element = this.driver.findElement(id);
		Assert.assertEquals(element.getText(), "Go To Page 2");
		element.click();

		Page2 pg2 = PageFactory.initElements(driver, Page2.class);
		WebElement message = pg2.message();
		Assert.assertEquals(message.getText(), "Welcome!");
	}

}