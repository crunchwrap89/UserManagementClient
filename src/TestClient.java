import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class TestClient {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/UserManagement/webservice/users/2")
				.request().buildGet().invoke();
		
		User1 user2 = response.readEntity(User1.class);
		System.out.println(user2);
		response.close();
		
		User1 alfred = new User1();
		alfred.setName("Alfred");
		alfred.setSurname("Alfredsson");
		
		
		Entity benEntity = Entity.entity(alfred, "application/XML");
		
		response = client.target("http://localhost:8080/UserManagement/webservice/users")
				.request().buildPost(benEntity).invoke();
		System.out.println(response.readEntity(User1.class).getId());
		response.close();
		
		
		response = client.target("http://localhost:8080/UserManagement/webservice/users")
				.request().buildGet().invoke();
		List<User1> users = response.readEntity(new GenericType<List<User1>>() {});
		
		for (User1 u : users) {
			System.out.println(u);
		}
	}
}
