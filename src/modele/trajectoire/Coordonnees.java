package modele.trajectoire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jxmapviewer.viewer.GeoPosition;

import modele.BDD.InitialisationBDD;

/**
 *La classe <b>Coordonnees</b> donne les coordonnées Gps d'une ville à partir de son nom.
 *@author Roxane Chatry
 *@version 1.0
 **/

public class Coordonnees {
	private final GeoPosition pos;
	
	
	/**
	 * Construit l'objet qui permet de nous donner la Geoposition si la ville existe 
	 * @param nomVille
	 */
	
	public Coordonnees(String nomVille) {
		double lng, lat;
		double[] coor = coordonneesGpsBdd(nomVille);
		if (coor != null) {
			lat = coor[0];
			lng= coor[1];
			pos = new GeoPosition(lat,lng);
		} else {
			pos = null;
		}
		
	}
	

	/**
	 * Permet de récupérer depuis la BDD les coordonnées voulues
	 * 
	 * @param nomVille
	 * @return un tableau statique avec la longitude et la latitude
	 */
	
	public double[] coordonneesGpsBdd(String nomVille) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		// coordonnées
		double lat,lng;
		double [] coor = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				// coordonnées de la première ville (une ville = un département dans notre modèle)
				ResultSet result = stat.executeQuery("SELECT gps_lat,gps_lng FROM Commune WHERE nom=\""+nomVille+"\";");
				if (result.next()) {
					lat = result.getDouble("gps_lat");
					lng = result.getDouble("gps_lng");
					coor = new double [] {lat,lng};
				}
				result.close();
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(coor);
	}


	/** getteur donne les coordonnées de l'objet
	 * 
	 * @return les coordonnées
	 */

	public GeoPosition getPos() {
		return pos;
	}

	
}
