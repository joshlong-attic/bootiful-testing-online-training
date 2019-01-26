package livelessons;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithMockUser(roles = "ADMIN")
@Retention(RetentionPolicy.RUNTIME)
public @interface WithAdmin {

}
