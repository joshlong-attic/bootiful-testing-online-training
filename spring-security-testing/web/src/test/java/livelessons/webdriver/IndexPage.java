/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package livelessons.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
public class IndexPage {

	private WebDriver driver;

	@FindBy(css = "#balance")
	private WebElement balance;

	private WebElement amount;

	@FindBy(css = "input[type=submit]")
	private WebElement submit;

	public IndexPage(WebDriver webDriver) {
		this.driver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public static <T> T to(WebDriver driver, Class<T> page) {
		driver.get("http://localhost:8080/");
		return (T) PageFactory.initElements(driver, page);
	}

	public IndexPage assertAt() {
		assertThat(this.driver.getTitle()).isEqualTo("Bank");
		return this;
	}

	public double balance() {
		return Double.parseDouble(this.balance.getText());
	}

	public IndexPage transfer(double amount) {
		this.amount.sendKeys(String.valueOf(amount));
		this.submit.click();
		return PageFactory.initElements(this.driver, IndexPage.class);
	}

}
