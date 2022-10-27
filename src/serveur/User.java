package serveur;

public class User {

	private String pseudo;
	
	User(){
		
	}
	
	User(String pseudo){
		this.pseudo=pseudo;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setPseudo(String name) {
		this.pseudo=name;
	}
}
