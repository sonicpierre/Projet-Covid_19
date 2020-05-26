package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class DataGraphes {
	/**
	 * On initialise les variables d'accès à la BDD
	 */
	private static final String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = InitialisationBDD.user;
	private static String passwd = InitialisationBDD.passwd;

	/**
	 * Retourne des couples (region,hospitalises actuels)
	 */
	public static Series<String, Number> hospitalisesRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la date du jour
				 */
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
				res.last();
				/**
				 * On récupère la liste des régions
				 */
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					/**
					 * Pour chaque région on récupère ses départements (sauf les DOM TOM)
					 */
					if (!res1.getString(2).equals("collectivites doutre mer")) {
						String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(2));
							ResultSet res2 = stat2.executeQuery();
							int hospitalisesRegion = 0;
							while (res2.next()) {
								/**
								 * Pour chaque département on récupère le nombre d'hospitalisés actuels et on
								 * l'ajoute au total de la région
								 */
								requeteSQL = "SELECT h.hospitalises FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement AND h.date=?;";
								try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
									stat3.setString(1, res2.getString(1));
									stat3.setString(2, res.getString(1));
									ResultSet res3 = stat3.executeQuery();
									res3.next();
									hospitalisesRegion += res3.getInt(1);
									res3.close();
									stat3.close();
								}
							}
							res2.close();
							stat2.close();
							/**
							 * On remplit la liste des couples
							 */
							series.getData()
									.add(new XYChart.Data<String, Number>(res1.getString(1), hospitalisesRegion));
						}
					}
				}
				res1.close();
				stat1.close();
				res.close();
				stat.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Hospitalisés");
		return (series);
	}

	/**
	 * Retourne des couples (region,personnes en réanimation actuellement)
	 */
	public static Series<String, Number> reanimesRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la date du jour
				 */
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
				res.last();
				/**
				 * On récupère la liste des régions
				 */
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					/**
					 * Pour chaque région on récupère ses départements (sauf les DOM TOM car manque
					 * de données parfois)
					 */
					if (!res1.getString(2).equals("collectivites doutre mer")) {
						String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(2));
							ResultSet res2 = stat2.executeQuery();
							int reanimationRegion = 0;
							while (res2.next()) {
								/**
								 * Pour chaque département on récupère le nombre de personnes en réanimation
								 * actuellement et on l'ajoute au total de la région
								 */
								requeteSQL = "SELECT h.reanimation FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement AND h.date=?;";
								try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
									stat3.setString(1, res2.getString(1));
									stat3.setString(2, res.getString(1));
									ResultSet res3 = stat3.executeQuery();
									res3.next();
									reanimationRegion += res3.getInt(1);
									res3.close();
									stat3.close();
								}
							}
							res2.close();
							stat2.close();
							/**
							 * On remplit la liste des couples
							 */
							series.getData()
									.add(new XYChart.Data<String, Number>(res1.getString(1), reanimationRegion));
						}
					}
				}
				res1.close();
				stat1.close();
				res.close();
				stat.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Réanimation");
		return (series);
	}

	/**
	 * Retourne des couples (region,gueris actuels)
	 */
	public static Series<String, Number> guerisRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la date du jour
				 */
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
				res.last();
				/**
				 * On récupère la liste des régions
				 */
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					/**
					 * Pour chaque région on récupère ses départements (sauf les DOM TOM car manque
					 * de données parfois)
					 */
					if (!res1.getString(2).equals("collectivites doutre mer")) {
						String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(2));
							ResultSet res2 = stat2.executeQuery();
							int guerisRegion = 0;
							while (res2.next()) {
								/**
								 * Pour chaque département on récupère le nombre de guéris actuels et on
								 * l'ajoute au total de la région
								 */
								requeteSQL = "SELECT h.gueris FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement AND h.date=?;";
								try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
									stat3.setString(1, res2.getString(1));
									stat3.setString(2, res.getString(1));
									ResultSet res3 = stat3.executeQuery();
									res3.next();
									guerisRegion += res3.getInt(1);
									res3.close();
									stat3.close();
								}
							}
							res2.close();
							stat2.close();
							/**
							 * On remplit la liste des couples
							 */
							series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), guerisRegion));
						}
					}
				}
				res1.close();
				stat1.close();
				res.close();
				stat.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Guéris");
		return (series);
	}

	/**
	 * Retourne des couples (region,morts actuels)
	 */
	public static Series<String, Number> mortsRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la date du jour
				 */
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
				res.last();
				/**
				 * On récupère la liste des régions
				 */
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					/**
					 * Pour chaque région on récupère ses départements (sauf les DOM TOM car manque
					 * de données parfois)
					 */
					if (!res1.getString(2).equals("collectivites doutre mer")) {
						String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(2));
							ResultSet res2 = stat2.executeQuery();
							int mortsRegion = 0;
							while (res2.next()) {
								/**
								 * Pour chaque département on récupère le nombre de morts actuels et on l'ajoute
								 * au total de la région
								 */
								requeteSQL = "SELECT h.morts FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement AND h.date=?;";
								try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
									stat3.setString(1, res2.getString(1));
									stat3.setString(2, res.getString(1));
									ResultSet res3 = stat3.executeQuery();
									res3.next();
									mortsRegion += res3.getInt(1);
									res3.close();
									stat3.close();
								}
							}
							res2.close();
							stat2.close();
							/**
							 * On remplit la liste des couples
							 */
							series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), mortsRegion));
						}
					}
				}
				res1.close();
				stat1.close();
				res.close();
				stat.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Morts");
		return (series);
	}

	/**
	 * Retourne des couples (date,hospitalises) pour une région
	 */
	public static Series<String, Number> hospitalisesRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la liste des départements d'une région
				 */
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					/**
					 * On récupère la liste des dates
					 */
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					/**
					 * Ce tableau sert à stocker provisoirement le nombre d'hospitalisés à chaque
					 * date pour une région
					 */
					int listeHospitalises[] = new int[nbDates];
					while (res1.next()) {
						/**
						 * On récupère le nombre d'hospitalises à chaque date pour un département
						 */
						requeteSQL = "SELECT h.hospitalises FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							/**
							 * On somme le nombre d'hospitalisés de tous les départements d'une région pour
							 * chaque date
							 */
							while (res2.next()) {
								listeHospitalises[i] += res2.getInt(1);
								i++;
							}
							res2.close();
							stat2.close();
						}
					}
					/**
					 * On remplit la liste des couples
					 */
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeHospitalises[i]));
						i++;
					}
					res1.close();
					stat1.close();
					res.close();
					stat.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Hospitalisés");
		return (series);
	}

	/**
	 * Retourne des couples (date,personnes en réanimation) pour une région
	 */
	public static Series<String, Number> reanimesRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la liste des départements d'une région
				 */
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					/**
					 * On récupère la liste des dates
					 */
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					/**
					 * Ce tableau sert à stocker provisoirement le nombre den personnes en
					 * réanimation à chaque date pour une région
					 */
					int listeReanimes[] = new int[nbDates];
					while (res1.next()) {
						/**
						 * On récupère le nombre de personnes en réanimation à chaque date pour un
						 * département
						 */
						requeteSQL = "SELECT h.reanimation FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							/**
							 * On somme le nombre de personnes en réanimation de tous les départements d'une
							 * région pour chaque date
							 */
							while (res2.next()) {
								listeReanimes[i] += res2.getInt(1);
								i++;
							}
							res2.close();
							stat2.close();
						}
					}
					/**
					 * On remplit la liste des couples
					 */
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeReanimes[i]));
						i++;
					}
					res1.close();
					stat1.close();
					res.close();
					stat.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Réanimation");
		return (series);
	}

	/**
	 * Retourne des couples (date,gueris) pour une région
	 */
	public static Series<String, Number> guerisRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la liste des départements d'une région
				 */
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					/**
					 * On récupère la liste des dates
					 */
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					/**
					 * Ce tableau sert à stocker provisoirement le nombre de guéris à chaque date
					 * pour une région
					 */
					int listeGueris[] = new int[nbDates];
					while (res1.next()) {
						/**
						 * On récupère le nombre de guéris à chaque date pour un département
						 */
						requeteSQL = "SELECT h.gueris FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							/**
							 * On somme le nombre de guéris de tous les départements d'une région pour
							 * chaque date
							 */
							while (res2.next()) {
								listeGueris[i] += res2.getInt(1);
								i++;
							}
							res2.close();
							stat2.close();
						}
					}
					/**
					 * On remplit la liste des couples
					 */
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeGueris[i]));
						i++;
					}
					res1.close();
					stat1.close();
					res.close();
					stat.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Guéris");
		return (series);
	}

	/**
	 * Retourne des couples (date,morts) pour une région
	 */
	public static Series<String, Number> mortsRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère la liste des départements d'une région
				 */
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					/**
					 * On récupère la liste des dates
					 */
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					/**
					 * Ce tableau sert à stocker provisoirement le nombre de morts à chaque date
					 * pour une région
					 */
					int listeMorts[] = new int[nbDates];
					while (res1.next()) {
						/**
						 * On récupère le nombre de morts à chaque date pour un département
						 */
						requeteSQL = "SELECT h.morts FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							/**
							 * On somme le nombre de morts de tous les départements d'une région pour chaque
							 * date
							 */
							while (res2.next()) {
								listeMorts[i] += res2.getInt(1);
								i++;
							}
							res2.close();
							stat2.close();
						}
					}
					/**
					 * On remplit la liste des couples
					 */
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeMorts[i]));
						i++;
					}
					res1.close();
					stat1.close();
					res.close();
					stat.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Morts");
		return (series);
	}

	/**
	 * Retourne les hospitalises, personnes en réanimation, gueris et morts du jour
	 */
	public static ObservableList<PieChart.Data> statsQuotidiennes() {
		ObservableList<PieChart.Data> series = FXCollections.observableArrayList();
		try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
			int hospitalisesQuotidiens = 0;
			int reanimesQuotidiens = 0;
			int guerisQuotidiens = 0;
			int mortsQuotidiens = 0;
			/**
			 * On récupère la date du jour
			 */
			Statement stat1 = conn.createStatement();
			ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
			res1.last();
			/**
			 * On récupère les stats totales d'aujourd'hui
			 */
			String requeteSQL = "SELECT sum(hospitalises),sum(reanimation),sum(gueris),sum(morts) FROM Historique WHERE date=?;";
			try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
				stat2.setString(1, res1.getString(1));
				ResultSet res2 = stat2.executeQuery();
				while (res2.next()) {
					hospitalisesQuotidiens = res2.getInt(1);
					reanimesQuotidiens = res2.getInt(2);
					guerisQuotidiens = res2.getInt(3);
					mortsQuotidiens = res2.getInt(4);
				}
				res2.close();
				stat2.close();
			}
			res1.close();
			stat1.close();
			series.add(new PieChart.Data("Hospitalisés", hospitalisesQuotidiens));
			series.add(new PieChart.Data("Réanimés", reanimesQuotidiens));
			series.add(new PieChart.Data("Guéris", guerisQuotidiens));
			series.add(new PieChart.Data("Morts", mortsQuotidiens));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (series);
	}

	/**
	 * Retourne les hospitalises, reanimes, gueris et morts du jour d'une région
	 */
	public static ObservableList<PieChart.Data> statsQuotidiennes(String region) {
		ObservableList<PieChart.Data> series = FXCollections.observableArrayList();
		try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
			int hospitalisesQuotidiens = 0;
			int reanimesQuotidiens = 0;
			int guerisQuotidiens = 0;
			int mortsQuotidiens = 0;
			/**
			 * On récupère la date du jour
			 */
			Statement stat1 = conn.createStatement();
			ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
			res1.last();
			/**
			 * On récupère les stats totales d'aujourd'hui
			 */
			String requeteSQL = "SELECT sum(h.hospitalises),sum(h.reanimation),sum(h.gueris),sum(h.morts) FROM Historique h, Region r, Departement d"
					+ " WHERE date=? AND r.nom=? AND r.code=d.code_region AND d.code=h.departement;";
			try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
				stat2.setString(1, res1.getString(1));
				stat2.setString(2, region);
				ResultSet res2 = stat2.executeQuery();
				while (res2.next()) {
					hospitalisesQuotidiens = res2.getInt(1);
					reanimesQuotidiens = res2.getInt(2);
					guerisQuotidiens = res2.getInt(3);
					mortsQuotidiens = res2.getInt(4);
				}
				res2.close();
				stat2.close();
			}
			res1.close();
			stat1.close();
			series.add(new PieChart.Data("Hospitalisés", hospitalisesQuotidiens));
			series.add(new PieChart.Data("Réanimés", reanimesQuotidiens));
			series.add(new PieChart.Data("Guéris", guerisQuotidiens));
			series.add(new PieChart.Data("Morts", mortsQuotidiens));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (series);
	}
}