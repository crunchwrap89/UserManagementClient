import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User1 {

	private int id;
	private String name;
	private String surname;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String toString() {
		return "User (" + this.id + ") - " + this.name + " " + this.surname;
	}
	
}
