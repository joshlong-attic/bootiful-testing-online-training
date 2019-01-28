package clients.wiremock;

import clients.Reservation;
import clients.ReservationClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@AutoConfigureWireMock(port = 8080)
@RunWith(SpringRunner.class)
@SpringBootTest
public class WiremockTest {

	private final Reservation one = new Reservation(1L, "One");
	private final Reservation two = new Reservation(2L, "Two");
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ReservationClient reservationClient;

	@Before
	public void before() throws Exception {

		String json = this.objectMapper
			.writeValueAsString(Arrays.asList(this.one, this.two));

		WireMock
			.stubFor(WireMock.get("/reservations")
				.willReturn(WireMock.aResponse()
					.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.withBody(json)
				));
	}

	@Test
	public void getAllReservations() throws Exception {
		Collection<Reservation> allReservations = this.reservationClient
			.getAllReservations();
		Assert.assertEquals(allReservations.size(), 2);


	}

}
