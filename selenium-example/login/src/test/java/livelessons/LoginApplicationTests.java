package livelessons;

import livelessons.webdriver.HiddenPage;
import livelessons.webdriver.LoginPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LoginApplicationTests {

	@Autowired
	private WebDriver driver;

	@Autowired
	private SecurityProperties securityProperties;

	@Test
	public void requiresLogin() {
		HiddenPage.to(this.driver, LoginPage.class).assertAt();
	}

	@Test
	public void loginFailure() {
		HiddenPage.to(this.driver, LoginPage.class).form().username("user")
				.password("invalid").login(LoginPage.class).assertAt();
	}

	@Test
	public void loginSuccess() {
		HiddenPage.to(this.driver, LoginPage.class).form()
				.username(this.securityProperties.getUser().getName())
				.password(this.securityProperties.getUser().getPassword())
				.login(HiddenPage.class).assertAt();
	}

}
