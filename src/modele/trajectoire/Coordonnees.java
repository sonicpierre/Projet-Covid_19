package modele.trajectoire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jxmapviewer.viewer.GeoPosition;

/** Coordonnées Gps d'une ville à partir de son nom
 * 
 *
 */
public class Coordonnees {
	private GeoPosition pos;
	
	public Coordonnees(String nomVille) {
		double lng, lat;
		double[] coor = coordonneesGpsBdd(nomVille);
		if (coor != null) {
			lng= coor[0];
			lat = coor[1];
			pos = new GeoPosition(lat,lng);
		} else {
			pos = null;
		}
		
	}
	
	public double[] coordonneesGpsBdd(String nomVille) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "MoneyMan";
		String passwd = "money";
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

	
	public GeoPosition getPos() {
		return pos;
	}

	public void setPos(GeoPosition pos) {
		this.pos = pos;
	}
	
}
