package serveur;

public class User {

	private int id;
	
	private static int sequence=0;
	
	private String pseudo;
	
	User(){
		this.id = ++sequence;
	}
	
	User(String pseudo){
		this.pseudo=pseudo;
		this.id = ++sequence;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setPseudo(String name) {
		this.pseudo=name;
	}
	
	public int getId() {
		return this.id;
	}
}
