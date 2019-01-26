package rsb.testing.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// <1>
@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer {

	// <2>
	private String id;

	private String name;

}
