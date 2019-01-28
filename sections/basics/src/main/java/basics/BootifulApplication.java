package basics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootApplication
public class BootifulApplication {

	public static void main(String args[]) {
		SpringApplication.run(BootifulApplication.class, args);
	}
}

@Component
class Bar {

	public String getName() {
		return "Bar";
	}

}

@Component
class Foo {


	private final Bar bar;

	Foo(Bar bar) {
		this.bar = bar;
	}

	@Override
	public String toString() {
		return "Foo{" +
			"bar=" + bar.getName() +
			'}';
	}
}
