package livelessons.webdriver;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage {

	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void assertAt() {
		assertThat(this.driver.getTitle()).endsWith("Please Log In");
	}

	public Form form() {
		return new Form(this.driver);
	}

	public class Form {

		@FindBy(name = "username")
		private WebElement username;

		@FindBy(name = "password")
		private WebElement password;

		@FindBy(name = "submit")
		private WebElement button;

		public Form(SearchContext context) {
			PageFactory.initElements(new DefaultElementLocatorFactory(context), this);
		}

		public Form username(String username) {
			this.username.sendKeys(username);
			return this;
		}

		public Form password(String password) {
			this.password.sendKeys(password);
			return this;
		}

		public <T> T login(Class<T> page) {
			this.button.click();
			return PageFactory.initElements(LoginPage.this.driver, page);
		}

	}

}
