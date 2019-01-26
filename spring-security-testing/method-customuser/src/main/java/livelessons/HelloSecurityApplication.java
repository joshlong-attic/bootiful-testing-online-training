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

package livelessons;

import livelessons.message.Message;
import livelessons.user.MessageUser;
import livelessons.user.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HelloSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSecurityApplication.class, args);
	}

	@Bean
	Message message() {
		return new Message(josh(), "Hi Josh!");
	}

	@Bean
	MessageUser josh() {
		return new MessageUser(1L, "josh@example.com", "password");
	}

	@Bean
	MessageUser rob() {
		return new MessageUser(2L, "rob@example.com", "password");
	}

}
