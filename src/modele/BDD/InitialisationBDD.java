package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class InitialisationBDD {
	/**
	 * url d'accès à la bdd sans table 
	 */
	private static final String url = "jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	/**
	 * nom d'utilisateur
	 */
	public static String user;
	/**
	 * mot de passe utilisateur
	 */
	public static String passwd;
	
	/** définit le mot de passe utilisateur
	 * 
	 * @param mdp la mot de passe à utiliser
	 */
	private void setPasswd(String mdp) {
		InitialisationBDD.passwd = mdp;
	}
	/** définit le nom d'utilisateur
	 * 
	 * @param login le nom à utiliser
	 */
	private void setUser(String login) {
		InitialisationBDD.user = login;
	}
	
	/**
	 * initialise la bdd avec les données utilisateur fournies, dans le cas où il faut créer un user (mot de passe root donné)
	 * @param login le nom d'utilisateur
	 * @param mdp le mot de passe utilisateur
	 * @param mdpRoot el mot de passe root
	 * @return un booléen indiquant que tout a fonctionné
	 */
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

	/** 
	 * Initialise la bdd avec les données utilisateurs fournies si l'utilisateur existe déjà (pas de mot de passe root fourni)
	 * @param login le nom utilisateur
	 * @param mdp le mot de passe utilisateur
	 * @return un booléen indiquant que tout a fonctionné
	 */
	public boolean initialiserBDD(String login, String mdp) { 
		if (this.existeUser(login,mdp)) {
			this.setUser(login);
			this.setPasswd(mdp);
			return(true);
		}
		return(false);
	}
	
	/**
	 * vérifie que l'user existe + crée la database France si elle n'existe pas
	 * @param login le nom d'utilisateur
	 * @param mdp le mot de passe utilisateur
	 * @return un booléen indiquant si le couple login/mdp est valable (connection possible à la bdd)
	 */	
	public boolean existeUser(String login, String mdp) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, login, mdp)) {
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

	/**
	 * crée un user 
	 * @param login le nom d'utilisateur
	 * @param mdp le mot de passe utilisateur
	 * @param mdpRoot le mot de passe root
	 * @return un booléen indiquant si tout a fonctionné
	 */
	public boolean creerUser(String login, String mdp, String mdpRoot) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		try(Connection conn = DriverManager.getConnection(url, "root", mdpRoot)){
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
