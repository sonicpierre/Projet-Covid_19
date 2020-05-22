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

	private static final String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = InitialisationBDD.user;
	private static String passwd = InitialisationBDD.passwd;
	
	
	public static HashMap<GeoPosition, Double> cercleMortsVille() {
        final String prefectureRegion[] = { "lyon", "besancon", "rennes", "orleans", "strasbourg", "lille", "paris",
                "caen", "bordeaux", "toulouse", "nantes", "marseille", "ajaccio" };
        HashMap<GeoPosition, Double> hashmap = new HashMap<GeoPosition, Double>();
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            // On récupère la date la plus récente
            Statement stat1 = conn.createStatement();
            ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
            res1.last();
            // On récupère le plus grand nombre de morts
            Statement stat2 = conn.createStatement();
            ResultSet res2 = stat2.executeQuery("SELECT max(morts) FROM Historique;");
            res2.next();
            double mortsMax = (double) res2.getInt(1);
            // On récupère les coordonnées et le nbre de morts actuel pour chaque préfecture
            // de région
            for (String prefecture : prefectureRegion) {
                String requeteSQL = "SELECT c.gps_lat,c.gps_lng,h.morts FROM Historique h, Commune c"
                        + " WHERE h.date=? AND c.slug=? AND h.departement=c.code_dept;";
                try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
                    stat3.setString(1, res1.getString(1));
                    stat3.setString(2, prefecture);
                    ResultSet res3 = stat3.executeQuery();
                    res3.next();
                    hashmap.put(new GeoPosition(res3.getDouble(1), res3.getDouble(2)),
                            (double) res3.getInt(3) / mortsMax);
                }
            }
		} catch (SQLException e) {
		            e.printStackTrace();
        }
        return (hashmap);
	}
	
	
	public static HashMap<GeoPosition, Double> cercleActifsVille() {
        final String prefectureRegion[] = { "lyon", "besancon", "rennes", "orleans", "strasbourg", "lille", "paris",
                "caen", "bordeaux", "toulouse", "nantes", "marseille", "ajaccio" };
        HashMap<GeoPosition, Double> hashmap = new HashMap<GeoPosition, Double>();
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            // On récupère la date la plus récente
            Statement stat1 = conn.createStatement();
            ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
            res1.last();
            // On récupère le plus grand nombre de morts
            Statement stat2 = conn.createStatement();
            ResultSet res2 = stat2.executeQuery("SELECT max(hospitalises),max(reanimation) FROM Historique;");
            res2.next();
            double actifsMax = (double) res2.getInt(1) + (double) res2.getInt(2);
            // On récupère les coordonnées et le nbre de morts actuel pour chaque préfecture
            // de région
            for (String prefecture : prefectureRegion) {
                String requeteSQL = "SELECT c.gps_lat,c.gps_lng,h.hospitalises,h.reanimation FROM Historique h, Commune c"
                        + " WHERE h.date=? AND c.slug=? AND h.departement=c.code_dept;";
                try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
                    stat3.setString(1, res1.getString(1));
                    stat3.setString(2, prefecture);
                    ResultSet res3 = stat3.executeQuery();
                    res3.next();
                    hashmap.put(new GeoPosition(res3.getDouble(1), res3.getDouble(2)),
                            (double) (res3.getInt(3) + res3.getInt(4)) / actifsMax);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (hashmap);
    }
	
	
	public static HashMap<GeoPosition, Double> cercleGuerisVille() {
        final String prefectureRegion[] = { "lyon", "besancon", "rennes", "orleans", "strasbourg", "lille", "paris",
                "caen", "bordeaux", "toulouse", "nantes", "marseille", "ajaccio" };
        HashMap<GeoPosition, Double> hashmap = new HashMap<GeoPosition, Double>();
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            // On récupère la date la plus récente
            Statement stat1 = conn.createStatement();
            ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
            res1.last();
            // On récupère le plus grand nombre de morts
            Statement stat2 = conn.createStatement();
            ResultSet res2 = stat2.executeQuery("SELECT max(gueris) FROM Historique;");
            res2.next();
            double guerisMax = (double) res2.getInt(1);
            // On récupère les coordonnées et le nbre de morts actuel pour chaque préfecture
            // de région
            for (String prefecture : prefectureRegion) {
                String requeteSQL = "SELECT c.gps_lat,c.gps_lng,h.gueris FROM Historique h, Commune c"
                        + " WHERE h.date=? AND c.slug=? AND h.departement=c.code_dept;";
                try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
                    stat3.setString(1, res1.getString(1));
                    stat3.setString(2, prefecture);
                    ResultSet res3 = stat3.executeQuery();
                    res3.next();
                    System.out.println(
                            prefecture + "-" + res3.getDouble(1) + "-" + res3.getDouble(2) + "-" + (double) res3.getInt(3) / guerisMax);
                    hashmap.put(new GeoPosition(res3.getDouble(1), res3.getDouble(2)),
                            (double) res3.getInt(3) / guerisMax);
                }
            }
		} catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return (hashmap);
		    }
}
