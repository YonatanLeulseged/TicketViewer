package ticketviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {

	private static HttpURLConnection connection;

	public static void main(String[] args) {

		apiConnect();

	}

	// Establishes connection with Zendesk API and runs
	public static void apiConnect() {

		try {
			// Url connection to grab tickets
			URL url = new URL("https://teamyonatan.zendesk.com/api/v2/tickets.json/");
			connection = (HttpURLConnection) url.openConnection();

			String credentials = ("yleulseged@gmail.com" + ":" + "GoldenRetriever37");
			// Encoding into base64 credentials for header to accept
			String encodeBytes = Base64.getEncoder().encodeToString((credentials).getBytes());

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Basic " + encodeBytes);

			InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
			BufferedReader bReader = new BufferedReader(inputReader);
			int status = connection.getResponseCode();

			// If we get status code 200
			if (status == connection.HTTP_OK) {

				Gson gson = new Gson();
				BufferedReader br = null;
				br = new BufferedReader(inputReader);
				Ticket ticket = gson.fromJson(br, Ticket.class);

				String userChoice = "";

				// Main area: Where user chooses options
				while (!userChoice.equalsIgnoreCase("q")) {

					// Calls for user input in Interface method
					userChoice = Interface();

					switch (userChoice) {

					case "1":
						requestAll(ticket);
						break;

					case "2":
						requestOne(ticket);
						break;

					case "q":
						break;

					default:
						System.out.println("Please input either: '1' , '2' , or 'q'  ");
						System.out.println("\n" + "\n");
						break;

					}
				}
				System.out.println("Ending ticket viewer... Thank you come again!");

			}
			// Exception handling
			else if (status == connection.HTTP_BAD_REQUEST) {
				System.out.println("The URL you're sending us doesn't seem to exist");
				throw new RuntimeException("HttpResponseCode: " + status);
			} else if (status == connection.HTTP_SERVER_ERROR) {
				System.out.println("That's on us. We'll try to get it back up soon");
				throw new RuntimeException("HttpResponseCode: " + status);
			} else if (status == connection.HTTP_FORBIDDEN) {
				System.out.println("The URL you're giving me is forbidden");
				throw new RuntimeException("HttpResponseCode: " + status);
			} else if (status == connection.HTTP_UNAVAILABLE) {
				System.out.println("The URL you're giving me is unavailable");
				throw new RuntimeException("HttpResponseCode: " + status);
			}

			bReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Prints out 25 tickets each request
	public static void requestAll(Ticket ticket) {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		try {
			if (ticket != null) {
				for (Ticket t : ticket.getTickets()) {

					System.out.println(t.toString());

					if (t.getId() % 25 == 0) {
						System.out.println("--------------------------------------");
						System.out.println("\n" + "\n");
						System.out.println("View next 25 tickets: type '1' ");
						System.out.println("Main Menu: type 2");
						System.out.println(" ");
						input = scan.nextInt();

						if (input == 2) {
							break;
						}
						if (input == 1) {

						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Main menu Interface: Grabs user input
	public static String Interface() {

		Scanner kb = new Scanner(System.in);
		String input = "";

		System.out.println("Welcome to the ticket viewer");
		System.out.println("Select view options:");
		System.out.println("Press 1 to view all tickets");
		System.out.println("Press 2 to view a ticket");
		System.out.println("Type 'q' to quit");
		System.out.print("\n" + "\n");
		input = kb.next();

		return input;
	}

	// Displays one individual ticket via id
	public static void requestOne(Ticket ticket) {

		Scanner scanId = new Scanner(System.in);

		System.out.println("Enter ticket number: ");
		String id = scanId.nextLine();

		try {
			if (ticket != null) {
				for (Ticket t : ticket.getTickets()) {

					if (t.getId() == Integer.parseInt(id)) {
						System.out.println(t.toString());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
