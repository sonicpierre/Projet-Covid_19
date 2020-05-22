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
	private static final String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = InitialisationBDD.user;
	private static String passwd = InitialisationBDD.passwd;
	
	
	// Retourne des couples (region,hospitalises totaux)
	public static Series<String, Number> hospitalisesRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
					try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
						stat2.setString(1, res1.getString(2));
						ResultSet res2 = stat2.executeQuery();
						int hospitalisesRegion = 0;
						while (res2.next()) {
							requeteSQL = "SELECT max(h.hospitalises) FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement;";
							try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
								stat3.setString(1, res2.getString(1));
								ResultSet res3 = stat3.executeQuery();
								res3.next();
								hospitalisesRegion += res3.getInt(1);
							}
						}
						series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), hospitalisesRegion));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Hospitalisés");
		return (series);
	}

	// Retourne des couples (region,reanimes totaux)
    public static Series<String, Number> reanimesRegion() {
        Series<String, Number> series = new XYChart.Series<String, Number>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
                Statement stat1 = conn.createStatement();
                ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
                while (res1.next()) {
                    String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
                    try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
                        stat2.setString(1, res1.getString(2));
                        ResultSet res2 = stat2.executeQuery();
                        int reanimesRegion = 0;
                        while (res2.next()) {
                            requeteSQL = "SELECT max(h.reanimation) FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement;";
                            try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
                                stat3.setString(1, res2.getString(1));
                                ResultSet res3 = stat3.executeQuery();
                                res3.next();
                                reanimesRegion += res3.getInt(1);
                            }
                        }
                        series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), reanimesRegion));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        series.setName("Réanimation");
        return (series);
    }

	// Retourne des couples (region,gueris totaux)
	public static Series<String, Number> guerisRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
					try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
						stat2.setString(1, res1.getString(2));
						ResultSet res2 = stat2.executeQuery();
						int guerisRegion = 0;
						while (res2.next()) {
							requeteSQL = "SELECT max(h.gueris) FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement;";
							try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
								stat3.setString(1, res2.getString(1));
								ResultSet res3 = stat3.executeQuery();
								res3.next();
								guerisRegion += res3.getInt(1);
							}
						}
						series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), guerisRegion));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Guerris");
		return (series);
	}

	// Retourne des couples (region,morts totaux)
	public static Series<String, Number> mortsRegion() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat1 = conn.createStatement();
				ResultSet res1 = stat1.executeQuery("SELECT nom,slug FROM Region;");
				while (res1.next()) {
					String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE r.slug=? AND d.code_region=r.code;";
					try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
						stat2.setString(1, res1.getString(2));
						ResultSet res2 = stat2.executeQuery();
						int mortsRegion = 0;
						while (res2.next()) {
							requeteSQL = "SELECT max(h.morts) FROM Departement d, Historique h WHERE d.slug=? AND d.code=h.departement;";
							try (PreparedStatement stat3 = conn.prepareStatement(requeteSQL)) {
								stat3.setString(1, res2.getString(1));
								ResultSet res3 = stat3.executeQuery();
								res3.next();
								mortsRegion += res3.getInt(1);
							}
						}
						series.getData().add(new XYChart.Data<String, Number>(res1.getString(1), mortsRegion));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Morts");
		return (series);
	}

	// Retourne des couples (date,hospitalises) pour une région
	public static Series<String, Number> hospitalisesRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				// On récupère la liste des départements d'une région
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					// On récupère la liste des dates
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					int listeHospitalises[] = new int[nbDates];
					while (res1.next()) {
						// On récupère le nombre d'hospitalises à chaque date pour un département
						requeteSQL = "SELECT h.hospitalises FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							// On nomme le nombre d'hospitalises de tous les départements pour chaque date
							while (res2.next()) {
								listeHospitalises[i] += res2.getInt(1);
								i++;
							}
						}
					}
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeHospitalises[i]));
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Hospitalisé");
		return (series);
	}

	// Retourne des couples (date,reanimes) pour une région
	public static Series<String, Number> reanimesRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				// On récupère la liste des départements d'une région
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					// On récupère la liste des dates
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					int listeReanimes[] = new int[nbDates];
					while (res1.next()) {
						// On récupère le nombre de reanimes à chaque date pour un département
						requeteSQL = "SELECT h.reanimation FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							// On nomme le nombre de reanimes de tous les départements pour chaque date
							while (res2.next()) {
								listeReanimes[i] += res2.getInt(1);
								i++;
							}
						}
					}
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeReanimes[i]));
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Reanimation");
		return (series);
	}

	// Retourne des couples (date,gueris) pour une région
	public static Series<String, Number> guerisRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				// On récupère la liste des départements d'une région
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					// On récupère la liste des dates
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					int listeGueris[] = new int[nbDates];
					while (res1.next()) {
						// On récupère le nombre de gueris à chaque date pour un département
						requeteSQL = "SELECT h.gueris FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							// On nomme le nombre de gueris de tous les départements pour chaque date
							while (res2.next()) {
								listeGueris[i] += res2.getInt(1);
								i++;
							}
						}
					}
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeGueris[i]));
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Guerris");
		return (series);
	}

	// Retourne des couples (date,morts) pour une région
	public static Series<String, Number> mortsRegion(String region) {
		Series<String, Number> series = new XYChart.Series<String, Number>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				// On récupère la liste des départements d'une région
				String requeteSQL = "SELECT d.slug FROM Departement d, Region r WHERE d.code_region=r.code AND r.nom=?;";
				try (PreparedStatement stat1 = conn.prepareStatement(requeteSQL)) {
					stat1.setString(1, region);
					ResultSet res1 = stat1.executeQuery();
					// On récupère la liste des dates
					Statement stat = conn.createStatement();
					ResultSet res = stat.executeQuery("SELECT DISTINCT date FROM Historique;");
					int nbDates = 0;
					while (res.next()) {
						nbDates++;
					}
					int listeMorts[] = new int[nbDates];
					while (res1.next()) {
						// On récupère le nombre de morts à chaque date pour un département
						requeteSQL = "SELECT h.morts FROM Historique h,Departement d, Region r WHERE h.departement=d.code AND d.code_region=r.code AND d.slug=?;";
						try (PreparedStatement stat2 = conn.prepareStatement(requeteSQL)) {
							stat2.setString(1, res1.getString(1));
							ResultSet res2 = stat2.executeQuery();
							int i = 0;
							// On nomme le nombre de morts de tous les départements pour chaque date
							while (res2.next()) {
								listeMorts[i] += res2.getInt(1);
								i++;
							}
						}
					}
					res.beforeFirst();
					int i = 0;
					while (res.next()) {
						series.getData().add(new XYChart.Data<String, Number>(res.getString(1), listeMorts[i]));
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		series.setName("Morts");
		return (series);
	}
	
	// Retourne les hospitalises, reanimes, gueris et morts du jour
    public static ObservableList<PieChart.Data> statsQuotidiennes() {
        ObservableList<PieChart.Data> series = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            int hospitalisesQuotidiens = 0;
            int reanimesQuotidiens = 0;
            int guerisQuotidiens = 0;
            int mortsQuotidiens = 0;
            // On récupère la date la plus récente
            Statement stat1 = conn.createStatement();
            ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
            res1.last();
            // On récupère les stats totales d'aujourd'hui
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
            }
            series.add(new PieChart.Data("Hospitalisés", hospitalisesQuotidiens));
            series.add(new PieChart.Data("Réanimés", reanimesQuotidiens));
            series.add(new PieChart.Data("Guéris", guerisQuotidiens));
            series.add(new PieChart.Data("Morts", mortsQuotidiens));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (series);
    }
    
	 // Retourne les hospitalises, reanimes, gueris et morts du jour d'une région
	    public static ObservableList<PieChart.Data> statsQuotidiennes(String region) {
	    	ObservableList<PieChart.Data> series = FXCollections.observableArrayList();
	        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
	            int hospitalisesQuotidiens = 0;
	            int reanimesQuotidiens = 0;
	            int guerisQuotidiens = 0;
	            int mortsQuotidiens = 0;
	            // On récupère la date la plus récente
	            Statement stat1 = conn.createStatement();
	            ResultSet res1 = stat1.executeQuery("SELECT DISTINCT date FROM Historique;");
	            res1.last();
	            // On récupère les stats totales d'aujourd'hui
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
	            }
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