package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class DataGraphes {
	private static final String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = InitialisationBDD.user;
	private static String passwd = InitialisationBDD.passwd;
	
	
	//Retourne des couples (département,hospitalises totaux)
	public static Series<String, Number> hospitalisesDepartement() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat1 = conn.createStatement();
				Statement stat2 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT slug FROM Departement;");
				while (res1.next()) {
					ResultSet res2 = stat2.executeQuery("SELECT max(h.hospitalises) FROM Departement d, Historique h WHERE d.code=h.departement AND d.slug='"+ res1.getString("slug") + "';");
					res2.next();
					System.out.println(res2.getInt(1));
					series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), res2.getInt(1)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	//Retourne des couples (département,reanimes totaux)
	public static Series<String, Number> reanimationDepartement() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res1 = stat.executeQuery("SELECT nom FROM Departement;");
				while (res1.next()) {
					ResultSet res2 = stat.executeQuery(
							"SELECT max(h.reanimation) FROM Departement d, Historique h WHERE d.code=h.departement AND d.nom='"
									+ res1.getString(1) + "';");
					series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), res2.getInt(1)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	//Retourne des couples (département,gueris totaux)
	public static Series<String, Number> guerisDepartement() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res1 = stat.executeQuery("SELECT nom FROM Departement;");
				while (res1.next()) {
					ResultSet res2 = stat.executeQuery(
							"SELECT max(h.gueris) FROM Departement d, Historique h WHERE d.code=h.departement AND d.nom='"
									+ res1.getString(1) + "';");
					series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), res2.getInt(1)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	//Retourne des couples (département,morts totaux)
	public static Series<String, Number> mortsDepartement() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res1 = stat.executeQuery("SELECT nom FROM Departement;");
				while (res1.next()) {
					ResultSet res2 = stat.executeQuery(
							"SELECT max(h.morts) FROM Departement d, Historique h WHERE d.code=h.departement AND d.nom='"
									+ res1.getString(1) + "';");
					series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), res2.getInt(1)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	//Retourne les hospitalises d'un département au fil du temps
	public static Series<String, Number> hospitalisesDepartement(String departement) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT date,hospitalises FROM Departement WHERE nom='"+ departement +"';");
				while (res.next()) {
					series.getData().add(new XYChart.Data<String, Number>(res.getString("date"), res.getInt("morts")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	
	public static Series<String, Number> reanimationDepartement(String departement) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT date,reanimation FROM Departement WHERE nom='"+ departement +"';");
				while (res.next()) {
					series.getData().add(new XYChart.Data<String, Number>(res.getString("date"), res.getInt("morts")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	//Retourne les gueris d'un département au fil du temps
	public static Series<String, Number> guerisDepartement(String departement) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT date,gueris FROM Departement WHERE nom='"+ departement +"';");
				while (res.next()) {
					series.getData().add(new XYChart.Data<String, Number>(res.getString("date"), res.getInt("morts")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
	//Retourne les morts d'un département au fil du temps
	public static Series<String, Number> mortsDepartement(String departement) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT date,morts FROM Departement WHERE nom='"+ departement +"';");
				while (res.next()) {
					series.getData().add(new XYChart.Data<String, Number>(res.getString("date"), res.getInt("morts")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (series);
	}
}