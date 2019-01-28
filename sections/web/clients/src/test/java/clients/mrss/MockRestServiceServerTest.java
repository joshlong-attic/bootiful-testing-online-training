package clients.mrss;

import clients.Reservation;
import clients.ReservationClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@AutoConfigureWireMock(port = 8080)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockRestServiceServerTest {

	private final Reservation one = new Reservation(1L, "One");
	private final Reservation two = new Reservation(2L, "Two");
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ReservationClient reservationClient;
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void getAllReservations() throws Exception {

		RestGatewaySupport gw = new RestGatewaySupport();
		gw.setRestTemplate(this.restTemplate);
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(gw);

		String json = this.objectMapper
			.writeValueAsString(Arrays.asList(this.one, this.two));

		mockServer
			.expect(ExpectedCount.once(), requestTo("http://localhost:8080/reservations"))
			.andRespond(withSuccess(json, MediaType.APPLICATION_JSON_UTF8));
		Collection<Reservation> allReservations = this.reservationClient.getAllReservations();
		Assert.assertEquals(allReservations.size(), 2);

		mockServer.verify();

	}

}
