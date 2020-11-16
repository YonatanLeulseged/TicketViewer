package ticketviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void testApiConnect() {
		HttpURLConnection connection;
		Ticket ticket = new Ticket();
		URL url;
		// create connection to test
		url = new URL("https://teamyonatan.zendesk.com/api/v2/tickets.json/");
		connection = (HttpURLConnection) url.openConnection();

		String credentials = ("yleulseged@gmail.com" + ":" + "GoldenRetriever37");
		
		// Encoding into base64 credentials for header to accept
		String encodeBytes = Base64.getEncoder().encodeToString((credentials).getBytes());

		connection.setRequestMethod("GET");

		connection.setRequestProperty("Authorization", "Basic " + encodeBytes);

		connection.setRequestMethod("GET");

		connection.setRequestProperty("Basic", "application/json");

		int status = connection.getResponseCode();

		assertEquals(401, Main.apiConnect());
	}
	//tests if api connection is unavailable
	@Test
	public void APIunavailable() {
		given().when().get("https://teamyonatan.zendesk.com/api/v2/tickets.json/")
			.then().statusCode(404);
	}
	//tests if api connection is available 
	@Test
	public void APIavailable() {
		given().when().get("https://teamyonatan.zendesk.com/api/v2/tickets.json/")
			.then().statusCode(200);
	}
	
	@Test
	void testRequestAll() {
		Main test = new Main();
		Ticket ticket = new Ticket();
		List<Ticket> tickets = new ArrayList<Ticket>();
		ticket.setId(1);
		ticket.setSubject("Subject");
		ticket.setStatus("Open");

		tickets.add(ticket);

	}

}

