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
		Response response = client.target("http://localhost:8080/UserManagement/webservice/users")
				.request().buildGet().invoke();
		
		User user = response.readEntity(User.class);
		System.out.println(user);
		response.close();
		
		User alfred = new User();
		alfred.setName("Alfred");
		alfred.setSurname("Alfredsson");
		
		
		Entity benEntity = Entity.entity(alfred, "application/XML");
		
		response = client.target("http://localhost:8080/UserManagement/webservice/users")
				.request().buildPost(benEntity).invoke();
//		System.out.println(response.readEntity(User.class).getId());
		response.close();
		
		
		response = client.target("http://localhost:8080/UserManagement/webservice/users")
				.request().buildGet().invoke();
		List<User> users = response.readEntity(new GenericType<List<User>>() {});
		
		for (User u : users) {
			System.out.println(u);
		}
	}
}
