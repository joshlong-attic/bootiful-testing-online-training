package livelessons;

import livelessons.webdriver.IndexPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class WebDriverTests {

	@Autowired
	private WebDriver driver;

	@Test
	@DirtiesContext
	public void transfer() {
		IndexPage index = IndexPage.to(this.driver, IndexPage.class);
		index.assertAt();

		assertThat(index.balance()).isEqualTo(100);

		index = index.transfer(50);

		assertThat(index.balance()).isEqualTo(50);
	}

}
