package ticketviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {

	private static HttpURLConnection connection;

	public static void main(String[] args) {
		
		apiConnect();
		

		
	}
	
	public static void apiConnect() {

		
		ArrayList<Ticket> tickets;

		try {
			URL url = new URL("https://teamyonatan.zendesk.com/api/v2/imports/tickets/tickets.json/");
			connection = (HttpURLConnection) url.openConnection();

			String credentials = ("yleulseged@gmail.com" + ":" + "GoldenRetriever37");
			String encodeBytes = Base64.getEncoder().encodeToString((credentials).getBytes());

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Basic " + encodeBytes);
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			String line = "";
			InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
			BufferedReader bReader = new BufferedReader(inputReader);
			StringBuilder response = new StringBuilder();

			System.out.println("Response : " + response.toString());

			int status = connection.getResponseCode();


			System.out.println(status);

			if (status == 200) {
				
				populateReader(inputReader);
				
				while ((line = bReader.readLine()) != null) {
					response.append(line);
				}
				bReader.close();

			}
			else if(status == 200) {
				System.out.println("Hmm, something is off. Check your credentials or try again.");
				throw new RuntimeException("HttpRespondseCode: " + status);
			}
			//add else if for general http exceptions

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void populateReader(InputStreamReader reader) {
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
			br = new BufferedReader(reader);
			Ticket ticket = gson.fromJson(br, Ticket.class);
			if (ticket != null) {
				for (Ticket t : ticket.getTickets()) {
					System.out.println(t.getId() + " - " + t.getSubject() + " - " + t.getStatus());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void Interface() {

		Scanner kb = new Scanner(System.in);

		while (kb.nextLine() != "quit") {

			System.out.println("Welcome to the ticket viewer");
			System.out.println("Select view options:");
			System.out.println("Press 1 to view all tickets");
			System.out.println("Press 2 to view a ticket");
			System.out.println("Type 'quit to exit");

			if (kb.nextInt() == 1) {
				// call allTickets()
			} else if (kb.nextInt() == 2) {
				// call oneTicket(int ticket)
			}

		}
	}

}
