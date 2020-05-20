package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialisationBDD {
	private String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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
			try (Connection conn = DriverManager.getConnection(url, login, mdp)) {
				Statement stat = conn.createStatement();
				stat.executeUpdate("CREATE DATABASE IF NOT EXISTS France;");
				stat.close();
			}
			catch (SQLException e) {
				return(false);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(true);
	}
	
	public boolean creerUser(String login, String mdp, String mdpRoot) {
		// connection en tant que root pour créer l'user
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, "root", mdpRoot)) {
				Statement stat = conn.createStatement();
				int verif1 = stat.executeUpdate("CREATE USER '"+login+"'@'localhost' IDENTIFIED BY '"+mdp+"';");
				int verif2 = stat.executeUpdate("GRANT ALL PRIVILEGES ON *France* TO '"+login+"'@'localhost';");
				stat.close();
				if (verif1!=0 && verif2!=0) {
					return(true);
				}
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(false);
	}
}
