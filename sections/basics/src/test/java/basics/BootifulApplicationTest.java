package basics;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootifulApplicationTest {

	@Autowired
	private Foo foo;

	@MockBean
	private Bar bar;

	@Test
	public void mocks() {
		String value = "Mock Bar!";
		Mockito.when(this.bar.getName()).thenReturn(value);
		String toString = this.foo.toString();
		Assertions.assertThat(toString).contains(value);
	}
}