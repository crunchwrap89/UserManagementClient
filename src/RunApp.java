import java.util.List;
import java.util.Scanner;

import javax.persistence.NoResultException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class RunApp {

	private static Scanner sc = new Scanner(System.in);
	private static String s;
	private static String s1;
	int i;

	public void appRunner() {
		while (true) {
			System.out.println(
					" Välj ett alternativ genom att skriva in en siffra för det alternativ du vill välja och tryck enter");
			System.out.println(" 1. Registrera en ny användare");
			System.out.println(" 2. Lista alla användare");
			System.out.println(" 3. Söka på användarid");
			System.out.println(" 4. Ändra användare");
			System.out.println(" 5. Ta bort användare");
			System.out.println(" 6. Avsluta");

			if (sc.hasNextInt() == false) {
				System.err.println("Du måste ange ett nummer");
				sc.reset();
				sc.nextLine();
				continue;

			} else {
				i = sc.nextInt();
				if (i == 1) {
					newUser();
					continue;
				} else if (i == 2) {
					listAll();
					sc.nextLine();
					continue;
				} else if (i == 3) {
					idSearch();
					sc.nextLine();
					continue;
				} else if (i == 4) {
					editUser();
					continue;
				}else if (i == 5) {
					deleteUser();
					continue;
				}else if (i == 6) {
					System.out.println("Hare gött!");
					System.exit(0);
				}
			}
		}
	}

	// Metdo för att söka via ID
	private static void idSearch() {
		while (true) {
			System.out.println("skriv in id på personen du vill söka efter");
			Scanner sc2 = new Scanner(System.in);
			if (sc2.hasNextInt() == false) {
				System.err.println("Du måste ange ett nummer");
				sc2.nextLine();
				continue;
			} else {
				try {
					String idnummer = "http://localhost:8080/UserManagement/webservice/users/" + sc2.nextInt();
					Client client = ClientBuilder.newClient();
					Response response = client.target(idnummer).request().buildGet().invoke();
					User1 user2 = response.readEntity(User1.class);
					System.out.println(user2);
					response.close();

				} catch (ProcessingException e) {
					System.err.println("Kunde inte hitta någon person med det id:t");
				}
				break;
			}
		}
	}

	// Metod för att lägga till ny användare
	public static void newUser() {
		System.out.println("Ange förnamn på användaren som ska registreras");
		sc.nextLine();
		s = sc.nextLine();
		System.out.println("Ange efternamn på användaren som ska registreras");
		s1 = sc.nextLine();
		addUser(s, s1);
		System.out.println("User " + s + " " + s1 + " added");
	}

	// Anropas av newUser när en ny användare läggs till
	public static void addUser(String fornamn, String efternamn) {
		Client client = ClientBuilder.newClient();
		User1 anvandare = new User1();
		anvandare.setName(fornamn);
		anvandare.setSurname(efternamn);
		Entity alfredity = Entity.entity(anvandare, "application/XML");
		Response response = client.target("http://localhost:8080/UserManagement/webservice/users").request()
				.buildPost(alfredity).invoke();
//		System.out.println(response.readEntity(User1.class).getId());
		response.close();

	}
	
	public static void deleteUser() {
		while (true) {
			System.out.println("skriv in id på användaren du vill ta bort");
			Scanner sc2 = new Scanner(System.in);
			if (sc2.hasNextInt() == false) {
				System.err.println("Du måste ange ett nummer");
				sc2.nextLine();
				continue;
			} else {
				try {
					String idnummer = "http://localhost:8080/UserManagement/webservice/users/" + sc2.nextInt();
					Client client = ClientBuilder.newClient();
					Response response = client.target(idnummer).request().buildDelete().invoke();
					response.close();

				} catch (ProcessingException e) {
					System.err.println("Kunde inte hitta någon person med det id:t");
				} catch (NoResultException e) {
					System.err.println("Kunde inte hitta någon person med det id:t");
				}
				break;
			}
		}
	}
	
	public static void editUser() {
		Scanner sc2 = new Scanner(System.in);
		int idnummer;
		while (true) {
			System.out.println("skriv in id på användaren du vill ändra");
			
			if (sc2.hasNextInt() == false) {
				System.err.println("Du måste ange ett nummer");
				sc2.nextLine();
				continue;
			} else {
				idnummer = sc2.nextInt();
				break;
			}
		}
		System.out.println("Skriv in nytt förnamn på användaren");
		sc.nextLine();
		s = sc.nextLine();
		System.out.println("Skriv in nytt efternamn på användaren");
		s1 = sc.nextLine();
		sendEditedUser(idnummer, s, s1);
//		System.out.println(idnummer + " " + s + " " + s1);
	}
	
	public static void sendEditedUser(int id, String name, String surname) {
		Client client = ClientBuilder.newClient();
		User1 anvandare = new User1();
		anvandare.setName(name);
		anvandare.setSurname(surname);
		String idnummer = "http://localhost:8080/UserManagement/webservice/users/" + id;
		Entity alfredity = Entity.entity(anvandare, "application/XML");
		Response response = client.target(idnummer).request()
				.buildPut(alfredity).invoke();
//		System.out.println(response.readEntity(User1.class).getId());
		response.close();
	}
	

	// Metod som skriver ut alla användare
	public static void listAll() {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/UserManagement/webservice/users").request().buildGet()
				.invoke();
		List<User1> users = response.readEntity(new GenericType<List<User1>>() {
		});

		for (User1 u : users) {
			System.out.println(u);
		}
	}

}
