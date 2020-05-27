package modele.trajectoire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jxmapviewer.viewer.GeoPosition;

import modele.BDD.InitialisationBDD;

/** Coordonnées Gps d'une ville à partir de son nom
 * 
 *
 */
public class Coordonnees {
	/** objet du type Geoposition qui contient les coordonnées gps 
	 * 
	 */
	private GeoPosition pos;
	
	/** constructeur des coordonnées de la ville de nom donné
	 * 
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
	
	/** récupère les coordonnées gps d'une ville dans la bdd 
	 * 
	 * @param nomVille le nom de la ville 
	 * @return [latitude,longitude] les coordonnées gps
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

	/** change les coordonnées de l'objet
	 * 
	 * @param pos les coordonnées
	 */
	public void setPos(GeoPosition pos) {
		this.pos = pos;
	}
	
}
