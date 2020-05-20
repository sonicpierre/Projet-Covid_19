package modele.BDD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class RemplissageBDD {
	private String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String user = InitialisationBDD.user;
	private String passwd = InitialisationBDD.passwd;
	
	public RemplissageBDD() { 
		this.clear();
		this.importationRegions();
		this.importationDept();
		this.importationVilles();
		this.association();
	}
	
	
	// écrase les tables pré-existantes 
	public void clear() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				stat.executeUpdate("DROP TABLE IF EXISTS Adjacence;");
				stat.executeUpdate("DROP TABLE IF EXISTS Commune;");
				stat.executeUpdate("DROP TABLE IF EXISTS Departement;");
				stat.executeUpdate("DROP TABLE IF EXISTS Region;");
				stat.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void importationRegions() {
		// fichier source
		// https://www.data.gouv.fr/fr/datasets/regions-departements-villes-et-villages-de-france-et-doutre-mer/
		Path filepath = Paths.get("data/regions.csv");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
       
				// création de la table 
//				stat.executeUpdate("DROP TABLE IF EXISTS Region;");
				stat.executeUpdate("CREATE TABLE Region ("
						+ "id int(10) NOT NULL,"
						+ "code VARCHAR(3) NOT NULL,"
						+ "nom VARCHAR(255),"
						+ "slug VARCHAR(255),"
						+ "CONSTRAINT pk_region PRIMARY KEY(id),"
						+ "CONSTRAINT key_code_region UNIQUE KEY (code));");
				List<String> lines = null; 
		        try {
		            lines = Files.readAllLines(filepath);
		        } catch (IOException e) {
		            System.out.println("Impossible de lire le fichier");
		        }
		        // importation des données
		        for (int i = 1; i < lines.size(); i++) {
		        	// récupération des données
		        	String[] split = lines.get(i).split(",");
		        	int id = Integer.parseInt(split[0]);
		        	String code = split[1];
		        	String nom = split[2];
		        	String slug = split[3];	        			        	
		        	// ajout à la bdd
		        	if (nom.charAt(0)!='\"') {
		        		nom='\"'+nom+'\"';
		        	} 
		        	if (slug.charAt(0)!='\"') {
		        		slug='\"'+slug+'\"';
		        	}
		        	String req = "INSERT INTO Region VALUES ("+id+",\""+code+"\","+nom+","+slug+");";
//		        	System.out.println(req);
		        	stat.executeUpdate(req);
		        }                 
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void importationDept() {
		// fichier source
		// https://www.data.gouv.fr/fr/datasets/regions-departements-villes-et-villages-de-france-et-doutre-mer/
		Path filepath = Paths.get("data/departments.csv");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
		       
				// création de la table 
//				stat.executeUpdate("DROP TABLE IF EXISTS Departement;");
				stat.executeUpdate("CREATE TABLE Departement ("
						+ "id int(10) NOT NULL,"
						+ "code_region VARCHAR(3) NOT NULL,"
						+ "code VARCHAR(3) NOT NULL,"
						+ "nom VARCHAR(255),"
						+ "slug VARCHAR(255),"
						+ "CONSTRAINT pk_dept PRIMARY KEY(id),"
						+ "FOREIGN KEY fk_region(code_region) REFERENCES Region (code),"						
						+ "CONSTRAINT key_code_dept UNIQUE KEY (code));");
				List<String> lines = null; 
		        try {
		            lines = Files.readAllLines(filepath);
		        } catch (IOException e) {
		            System.out.println("Impossible de lire le fichier");
		        }
		        // importation des données
		        for (int i = 1; i < lines.size(); i++) {
		        	// récupération des données
		        	String[] split = lines.get(i).split(",");
		        	int id = Integer.parseInt(split[0]);
		        	String codeRegion = split[1];
		        	String code = split[2];
		        	String nom = split[3];
		        	String slug = split[4];	        			        	
		        	// ajout à la bdd
		        	if (nom.charAt(0)!='\"') {
		        		nom='\"'+nom+'\"';
		        	} 
		        	if (slug.charAt(0)!='\"') {
		        		slug='\"'+slug+'\"';
		        	}
		        	String req = "INSERT INTO Departement VALUES ("+id+",\""+codeRegion+"\",\""+code+"\","+nom+","+slug+");";
//		        	System.out.println(req);
		        	stat.executeUpdate(req);
		        }                 
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	// importation des villes en se limitant aux prefectures Françaises
	public void importationVilles() {
		// fichier des prefectures
		Path filepath0 = Paths.get("data/prefectures.csv");
		// map de toutes les prefectures indexées sur le code du département correspondant
		HashMap<String,String> prefectures = new HashMap<String,String>();
		List<String> lines0;
		try {
			lines0 = Files.readAllLines(filepath0);
			for (int i = 1; i < lines0.size(); i++) {
	        	String[] split0 = lines0.get(i).split(",");
//	        	System.out.println(split0[0]+split0[3]);
	        	prefectures.put(split0[0],'\"'+split0[3]+'\"');
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Impossible de lire le fichier des préfectures!");
		}
		
		// fichier des villes de france
		Path filepath = Paths.get("data/cities.csv");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
		       
				// création de la table
				stat.executeUpdate("CREATE TABLE Commune ("
						+ "id int(10) NOT NULL,"
						+ "code_dept VARCHAR(3) NOT NULL,"
						+ "code_insee VARCHAR(5) NOT NULL,"
						+ "nom VARCHAR(255),"
						+ "slug VARCHAR(255),"
						+ "gps_lat double(16,14) NOT NULL,"
						+ "gps_lng double(17,14) NOT NULL,"
						+ "CONSTRAINT pk_commune PRIMARY KEY(id),"
						+ "FOREIGN KEY fk_dept(code_dept) REFERENCES Departement (code));");
				List<String> lines = null; 
		        try {
		            lines = Files.readAllLines(filepath);
		        } catch (IOException e) {
		            System.out.println("Impossible de lire le fichier");
		        }
		        // importation des données
		        for (int i = 1; i < lines.size(); i++) {
		        	// récupération des données
		        	String[] split = lines.get(i).split(",");
		        	int id = Integer.parseInt(split[0]);
		        	String codeDept = split[1];
		        	String codeInsee = split[2];
		        	String nom = split[4];
		        	String slug = split[5];
		        	Double lat = Double.parseDouble(split[6]);
		        	Double lng = Double.parseDouble(split[7]);
		        	
		        	if (nom.charAt(0)!='\"') {
		        		nom='\"'+nom+'\"';
		        	} 
		        	if (slug.charAt(0)!='\"') {
		        		slug='\"'+slug+'\"';
		        	}
//		        	System.out.println(prefectures.get(codeDept)+" - "+nom);
		        	// séléction des préfectures
		        	if ((prefectures.containsKey(codeDept) && prefectures.get(codeDept).equals(nom))) {
		        		// vérification que la ville n'existe pas déjà (élimine les doublons dans le fichier de données)
		        		ResultSet result = stat.executeQuery("SELECT id FROM Commune WHERE (nom="+nom+" and code_dept=\""+codeDept+"\");");
		        		result.last();
		        		if (result.getRow()==0) {
		        			String req = "INSERT INTO Commune VALUES ("+id+",\""+codeDept+"\",\""+codeInsee+"\","+nom+","+slug+","+lat+","+lng+");";
//		        			System.out.println(req);
		        			stat.executeUpdate(req);
		        		}
		        		result.close();
		        	}
		        }
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void association() {
		Path filepath = Paths.get("data/adjacences.txt");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
		       
//				stat.executeUpdate("DROP TABLE IF EXISTS Adjacence;");

				// création de la table 
				stat.executeUpdate("CREATE TABLE Adjacence ("
						+ "code1 VARCHAR(3) NOT NULL," 
						+ "code2 VARCHAR(3) NOT NULL,"
						+ "distance DOUBLE(17,14),"
						+ "CONSTRAINT pk_adjacance PRIMARY KEY(code1,code2),"
						+ "CONSTRAINT ville1_foreign FOREIGN KEY (code2) REFERENCES Commune (code_dept) ON DELETE CASCADE,"
						+ "CONSTRAINT ville2_foreign FOREIGN KEY (code2) REFERENCES Commune (code_dept) ON DELETE CASCADE);"
						);
				List<String> lines = null; 
		        try {
		            lines = Files.readAllLines(filepath);
		            // importation des données
		            for (int i = 1; i < lines.size(); i++) {
		            	// récupération des données
		            	String[] champs = lines.get(i).split(":");
		            	String code1 = champs[0];
		            	String[] liste = champs[1].split(",");
		            	for (int j=0; j<liste.length; j++) {
		            		String code2= liste[j];
		            		double dist = calculDistance(code1,code2);
		            		
		            		if (dist!=-1) {
		            			String req="INSERT INTO Adjacence VALUES (\""+code1+"\",\""+code2+"\","+dist+");";		        		
//		            			System.out.println(req);
		            			stat.executeUpdate(req);
		            		} else {
		            			System.out.println(code1+" - "+code2);
		            		}
		            	}
		            }                 
					stat.close();
		        } catch (IOException e) {
		            System.out.println("Impossible de lire le fichier");
		        }
		        
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public double calculDistance(String code1, String code2) {
		// coordonnées
		double x1, x2, y1, y2;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				// coordonnées de la première ville (une ville = un département dans notre modèle)
				ResultSet result = stat.executeQuery("SELECT gps_lat,gps_lng FROM Commune WHERE code_dept=\""+code1+"\";");
				if (result.next()) {
					y1 = result.getDouble("gps_lat");
					x1 = result.getDouble("gps_lng");
				} else {
					return(-1);
				}
				result.close();
				// coordonnées de la seconde ville
				ResultSet result2 = stat.executeQuery("SELECT gps_lat,gps_lng FROM Commune WHERE code_dept=\""+code2+"\";");
				if (result2.next()) {
					y2 = result2.getDouble("gps_lat");
					x2 = result2.getDouble("gps_lng");
				} else {
					return(-1);
				}
				result2.close();
				stat.close();
				return(Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// cas d'erreur
		return(-1);
	}

}
