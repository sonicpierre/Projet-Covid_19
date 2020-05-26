package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialisationBDD {
	private static final String url2 = "jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static String user;
	public static String passwd;
	
	private void setPasswd(String mdp) {
		InitialisationBDD.passwd = mdp;
	}
	private void setUser(String login) {
		InitialisationBDD.user = login;
	}
	
	// constructeur 
	public boolean initialiserBDD(String login, String mdp, String mdpRoot) { 
		// vérification que l'user n'existe pas 
		boolean existe = this.existeUser(login, mdp);
		if (!existe) {
			this.creerUser(login,mdp,mdpRoot);
			// vérification que la création de l'user a fonctionné
			existe = this.existeUser(login, mdp);
		}
		if (existe) {
			this.setUser(login);
			this.setPasswd(mdp);
			return(true);
		}
		return(false);
	}

	public boolean initialiserBDD(String login, String mdp) { 
		if (this.existeUser(login,mdp)) {
			this.setUser(login);
			this.setPasswd(mdp);
			return(true);
		}
		return(false);
	}
	
	// vérifie que l'user existe + crée la database France si elle n'existe pas
	public boolean existeUser(String login, String mdp) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url2, login, mdp)) {
				Statement stat = conn.createStatement();
				stat.executeUpdate("CREATE DATABASE IF NOT EXISTS France;");
				stat.close();
			}
			catch (SQLException e) {
				System.out.println("Je suis ici");
				return(false);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(true);
	}
	
	public boolean creerUser(String login, String mdp, String mdpRoot) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		try(Connection conn = DriverManager.getConnection(url2, "root", mdpRoot)){
			System.out.println("Connexion effective !");
		
			Statement stat = conn.createStatement();
			
			stat.executeUpdate("CREATE USER IF NOT EXISTS '"+ login +"'@'localhost' IDENTIFIED BY '" + mdp + "'");
			
			stat.executeUpdate("GRANT ALL PRIVILEGES ON *.* TO '" + login + "'@'localhost'");
			return true;

			}
		
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
