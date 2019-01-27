package clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Component
public class ReservationClient {

	private final RestTemplate restTemplate;

	private final String url;

	public ReservationClient(
		@Value("http://localhost:8080") String url, RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public Collection<Reservation> getAllReservations() {
		ParameterizedTypeReference<Collection<Reservation>> parameterizedTypeReference =
			new ParameterizedTypeReference<Collection<Reservation>>() {
			};

		return this.restTemplate
			.exchange(this.url + "/reservations", HttpMethod.GET, null, parameterizedTypeReference)
			.getBody();
	}
}
