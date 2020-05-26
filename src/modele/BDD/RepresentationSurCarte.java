package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.jxmapviewer.viewer.GeoPosition;

public class RepresentationSurCarte {
	/**
	 * On initialise les variables d'accès à la BDD
	 */
	private static final String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = InitialisationBDD.user;
	private static String passwd = InitialisationBDD.passwd;

	/**
	 * Retourne une hashmap de clé la géoposition de la préfecture d'une région et
	 * de valeur son rapport de nombre de morts par rapport à celui de la ville en
	 * ayant le plus. Cette fonction sert à tracer des disques sur les préfectures
	 * de région de rayon proportionnel au nombre de morts de la ville.
	 */
	public static HashMap<GeoPosition, Double> cercleMortsVille() {
		/**
		 * La liste des préfectures de région
		 */
		final String prefectureRegion[] = { "lyon", "besancon", "rennes", "orleans", "strasbourg", "lille", "paris",
				"caen", "bordeaux", "toulouse", "nantes", "marseille", "ajaccio" };
		HashMap<GeoPosition, Double> hashmap = new HashMap<GeoPosition, Double>();
		try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
			/**
			 * On récupère la date du jour
			 */
			Statement stat1 = conn.createStatement();
			ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
			res1.last();
			/**
			 * On récupère le plus grand nombre de morts dans une ville
			 */
			Statement stat2 = conn.createStatement();
			ResultSet res2 = stat2.executeQuery("SELECT max(morts) FROM Historique;");
			res2.next();
			double mortsMax = (double) res2.getInt(1);
			res2.close();
			stat2.close();
			/**
			 * On récupère les coordonnées et le nbre de morts actuel pour chaque préfecture
			 * de région
			 */
			for (String prefecture : prefectureRegion) {
				String requeteSQL = "SELECT c.gps_lat,c.gps_lng,h.morts FROM Historique h, Commune c"
						+ " WHERE h.date=? AND c.slug=? AND h.departement=c.code_dept;";
				try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
					stat3.setString(1, res1.getString(1));
					stat3.setString(2, prefecture);
					ResultSet res3 = stat3.executeQuery();
					res3.next();
					/**
					 * on remplit la hashmap avec les coordonnées de la ville et le rapport nb morts
					 * de la ville sur nb morts maximal
					 */
					hashmap.put(new GeoPosition(res3.getDouble(1), res3.getDouble(2)),
							(double) res3.getInt(3) / mortsMax);
					res3.close();
					stat3.close();
				}
			}
			res1.close();
			stat1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (hashmap);
	}

	/**
	 * Retourne une hashmap de clé la géoposition de la préfecture d'une région et
	 * de valeur son rapport de nombre de cas actifs (personnes hospitalisées et en
	 * réanimation) par rapport à celui de la ville en ayant le plus. Cette fonction
	 * sert à tracer des disques sur les préfectures de région de rayon
	 * proportionnel au nombre de cas actifs de la ville.
	 */
	public static HashMap<GeoPosition, Double> cercleActifsVille() {
		final String prefectureRegion[] = { "lyon", "besancon", "rennes", "orleans", "strasbourg", "lille", "paris",
				"caen", "bordeaux", "toulouse", "nantes", "marseille", "ajaccio" };
		HashMap<GeoPosition, Double> hashmap = new HashMap<GeoPosition, Double>();
		try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
			/**
			 * On récupère la date du jour
			 */
			Statement stat1 = conn.createStatement();
			ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
			res1.last();
			/**
			 * On récupère le plus grand nombre de cas actifs dans une ville
			 */
			Statement stat2 = conn.createStatement();
			ResultSet res2 = stat2.executeQuery("SELECT max(hospitalises),max(reanimation) FROM Historique;");
			res2.next();
			double actifsMax = (double) res2.getInt(1) + (double) res2.getInt(2);
			res2.close();
			stat2.close();
			/**
			 * On récupère les coordonnées et le nbre de cas actifs actuel pour chaque
			 * préfecture de région
			 */
			for (String prefecture : prefectureRegion) {
				String requeteSQL = "SELECT c.gps_lat,c.gps_lng,h.hospitalises,h.reanimation FROM Historique h, Commune c"
						+ " WHERE h.date=? AND c.slug=? AND h.departement=c.code_dept;";
				try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
					stat3.setString(1, res1.getString(1));
					stat3.setString(2, prefecture);
					ResultSet res3 = stat3.executeQuery();
					res3.next();
					/**
					 * on remplit la hashmap avec les coordonnées de la ville et le rapport nb cas
					 * actifs de la ville sur nb cas actifs maximal
					 */
					hashmap.put(new GeoPosition(res3.getDouble(1), res3.getDouble(2)),
							(double) (res3.getInt(3) + res3.getInt(4)) / actifsMax);
					res3.close();
					stat3.close();
				}
			}
			res1.close();
			stat1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (hashmap);
	}

	/**
	 * Retourne une hashmap de clé la géoposition de la préfecture d'une région et
	 * de valeur son rapport de nombre de guéris par rapport à celui de la ville en
	 * ayant le plus. Cette fonction sert à tracer des disques sur les préfectures
	 * de région de rayon proportionnel au nombre de guéris de la ville.
	 */
	public static HashMap<GeoPosition, Double> cercleGuerisVille() {
		final String prefectureRegion[] = { "lyon", "besancon", "rennes", "orleans", "strasbourg", "lille", "paris",
				"caen", "bordeaux", "toulouse", "nantes", "marseille", "ajaccio" };
		HashMap<GeoPosition, Double> hashmap = new HashMap<GeoPosition, Double>();
		try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
			/**
			 * On récupère la date du jour
			 */
			Statement stat1 = conn.createStatement();
			ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
			res1.last();
			/**
			 * On récupère le plus grand nombre de guéris dans une ville
			 */
			Statement stat2 = conn.createStatement();
			ResultSet res2 = stat2.executeQuery("SELECT max(gueris) FROM Historique;");
			res2.next();
			double guerisMax = (double) res2.getInt(1);
			res2.close();
			stat2.close();
			/**
			 * On récupère les coordonnées et le nbre de guéris actuel pour chaque
			 * préfecture de région
			 */
			for (String prefecture : prefectureRegion) {
				String requeteSQL = "SELECT c.gps_lat,c.gps_lng,h.gueris FROM Historique h, Commune c"
						+ " WHERE h.date=? AND c.slug=? AND h.departement=c.code_dept;";
				try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
					stat3.setString(1, res1.getString(1));
					stat3.setString(2, prefecture);
					ResultSet res3 = stat3.executeQuery();
					res3.next();
					/**
					 * on remplit la hashmap avec les coordonnées de la ville et le rapport nb
					 * guéris de la ville sur nb guéris maximal
					 */
					hashmap.put(new GeoPosition(res3.getDouble(1), res3.getDouble(2)),
							(double) res3.getInt(3) / guerisMax);
					res3.close();
					stat3.close();
				}
			}
			res1.close();
			stat1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (hashmap);
	}
}