package modele.BDD;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controll.main.MenuChoixController;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

/**
 * La classe <b>RemplissageBDD</b> est la classe qui permet de remplir la BDD à partir des csv.
 * Cette classe fait les différentes opérations :
 * <lu>
 * <li>Créer la table Région et la remplir</li>
 * <li>Créer la table département et la remplir</li>
 * <li>Créer la table ville la remplir</li>
 * <li>Faire les association entre villes et département</li>
 * <li>Créer la table historique et la remplir</li>
 * </lu>
 * </p>
 *
 *@author Roxane Chatry
 *@version 1.0
 **/


public class RemplissageBDD {
	private ProgressBar maProgresseBarre;
	/**
	 * On initialise les variables d'accès à la BDD qui ont été mis en place au moment du login de l'utilisateur.
	 */
	private static String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = InitialisationBDD.user;
	private static String passwd = InitialisationBDD.passwd;

	/**
	 * Le chargement de la BDD se fait directement à la création de la classe.
	 * @param progresseBarre montre l'avancement de l'installation
	 */
	
	public RemplissageBDD(ProgressBar progresseBarre) {
		maProgresseBarre = progresseBarre;

		/**
		 * Ici on définit une tache qui va se dérouler sur un autre Thread pour évider de faire freeze la fenêtre graphique.
		 */
		
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				updateProgress(0, 6);
				// RemplissageBDD.clear();
				System.out.println("Nettoyé");
				updateProgress(1, 6);
				RemplissageBDD.importationRegions();
				System.out.println("Régionné");
				updateProgress(2, 6);
				RemplissageBDD.importationDept();
				System.out.println("Départementé");
				updateProgress(3, 6);
				RemplissageBDD.importationVilles();
				System.out.println("Villé");
				updateProgress(4, 6);
				RemplissageBDD.association();
				System.out.println("Associationné");
				updateProgress(5, 6);
				RemplissageBDD.importationHistorique();
				System.out.println("Historiqué");

				return null;
			}
		};

		/**
		 * On lance le thread.
		 */
		
		task.setOnSucceeded(e -> MenuChoixController.getWindowInstallation().close());
		progresseBarre.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}

	/**
	 * Ecrase les tables pré-existantes
	 */
	public static void clear() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				stat.executeUpdate("DROP TABLE IF EXISTS Historique;");
				stat.executeUpdate("DROP TABLE IF EXISTS Adjacence;");
				stat.executeUpdate("DROP TABLE IF EXISTS Commune;");
				stat.executeUpdate("DROP TABLE IF EXISTS Departement;");
				stat.executeUpdate("DROP TABLE IF EXISTS Region;");
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crée et remplit la table des régions françaises
	 */
	public static void importationRegions() {
		/**
		 * fichier source :
		 * https://www.data.gouv.fr/fr/datasets/regions-departements-villes-et-villages-de-france-et-doutre-mer/
		 */
		Path filepath = Paths.get("data/regions.csv");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * Création de la table région
				 */
				Statement stat = conn.createStatement();
				stat.executeUpdate("CREATE TABLE IF NOT EXISTS Region (" + "id int(10) NOT NULL,"
						+ "code VARCHAR(3) NOT NULL," + "nom VARCHAR(255)," + "slug VARCHAR(255),"
						+ "CONSTRAINT pk_region PRIMARY KEY(id)," + "CONSTRAINT key_code_region UNIQUE KEY (code));");
				List<String> lines = null;
				try {
					/**
					 * Lecture de data/regions.csv qui contient les régions et leur code
					 */
					lines = Files.readAllLines(filepath);
				} catch (IOException e) {
					System.out.println("Impossible de lire le fichier");
				}
				for (int i = 1; i < lines.size(); i++) {
					String[] split = lines.get(i).split(",");
					int id = Integer.parseInt(split[0]);
					String code = split[1];
					String nom = split[2];
					String slug = split[3];
					/**
					 * Nettoyage des données
					 */
					if (nom.charAt(0) != '\"') {
						nom = '\"' + nom + '\"';
					}
					if (slug.charAt(0) != '\"') {
						slug = '\"' + slug + '\"';
					}
					/**
					 * Insertion dans la table
					 */
					String req = "INSERT INTO Region VALUES (" + id + ",\"" + code + "\"," + nom + "," + slug + ");";
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

	/**
	 * Crée et remplit la table des départments français
	 */
	public static void importationDept() {
		/**
		 * fichier source :
		 * https://www.data.gouv.fr/fr/datasets/regions-departements-villes-et-villages-de-france-et-doutre-mer/
		 */
		Path filepath = Paths.get("data/departments.csv");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				/**
				 * Création de la table département
				 */
				stat.executeUpdate("CREATE TABLE IF NOT EXISTS Departement (" + "id int(10) NOT NULL,"
						+ "code_region VARCHAR(3) NOT NULL," + "code VARCHAR(3) NOT NULL," + "nom VARCHAR(255),"
						+ "slug VARCHAR(255)," + "CONSTRAINT pk_dept PRIMARY KEY(id),"
						+ "FOREIGN KEY fk_region(code_region) REFERENCES Region (code),"
						+ "CONSTRAINT key_code_dept UNIQUE KEY (code));");
				List<String> lines = null;
				try {
					/**
					 * Lecture de data/departments.csv qui contient les départements, leur code et
					 * leur région
					 */
					lines = Files.readAllLines(filepath);
				} catch (IOException e) {
					System.out.println("Impossible de lire le fichier");
				}
				for (int i = 1; i < lines.size(); i++) {
					String[] split = lines.get(i).split(",");
					int id = Integer.parseInt(split[0]);
					String codeRegion = split[1];
					String code = split[2];
					String nom = split[3];
					String slug = split[4];
					/**
					 * Nettoyage des données
					 */
					if (nom.charAt(0) != '\"') {
						nom = '\"' + nom + '\"';
					}
					if (slug.charAt(0) != '\"') {
						slug = '\"' + slug + '\"';
					}
					/**
					 * Insertion dans la table
					 */
					String req = "INSERT INTO Departement VALUES (" + id + ",\"" + codeRegion + "\",\"" + code + "\","
							+ nom + "," + slug + ");";
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

	/**
	 * Crée et remplit la tables des préfectures de chaque département français
	 */
	public static void importationVilles() {
		Path filepath0 = Paths.get("data/prefectures.csv");
		/**
		 * Hashmap de toutes les prefectures indexées sur le code du département
		 * correspondant
		 */
		HashMap<String, String> prefectures = new HashMap<String, String>();
		List<String> lines0;
		try {
			/**
			 * Lecture de data/prefectures.csv qui contient les préfectures, leur
			 * département et leur région
			 */
			lines0 = Files.readAllLines(filepath0);
			for (int i = 1; i < lines0.size(); i++) {
				String[] split0 = lines0.get(i).split(",");
				prefectures.put(split0[0], '\"' + split0[3] + '\"');
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Impossible de lire le fichier des préfectures!");
		}
		Path filepath = Paths.get("data/cities.csv");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				/**
				 * Création de la table commune
				 */
				stat.executeUpdate("CREATE TABLE IF NOT EXISTS Commune (" + "id int(10) NOT NULL,"
						+ "code_dept VARCHAR(3) NOT NULL," + "code_insee VARCHAR(5) NOT NULL," + "nom VARCHAR(255),"
						+ "slug VARCHAR(255)," + "gps_lat double(16,14) NOT NULL," + "gps_lng double(17,14) NOT NULL,"
						+ "CONSTRAINT pk_commune PRIMARY KEY(id),"
						+ "FOREIGN KEY fk_dept(code_dept) REFERENCES Departement (code));");
				List<String> lines = null;
				try {
					/**
					 * Lecture de data/cities.csv qui contient toutes les villes de france, leur
					 * département et leurs coordonnées
					 */
					lines = Files.readAllLines(filepath);
				} catch (IOException e) {
					System.out.println("Impossible de lire le fichier");
				}
				for (int i = 1; i < lines.size(); i++) {
					String[] split = lines.get(i).split(",");
					int id = Integer.parseInt(split[0]);
					String codeDept = split[1];
					String codeInsee = split[2];
					String nom = split[4];
					String slug = split[5];
					Double lat = Double.parseDouble(split[6]);
					Double lng = Double.parseDouble(split[7]);
					/**
					 * Nettoyage des données
					 */
					if (nom.charAt(0) != '\"') {
						nom = '\"' + nom + '\"';
					}
					if (slug.charAt(0) != '\"') {
						slug = '\"' + slug + '\"';
					}
					/**
					 * Vérification que la ville n'existe pas déjà (élimine les doublons du fichier
					 * de données) et insertion dans la table
					 */
					if ((prefectures.containsKey(codeDept) && prefectures.get(codeDept).equals(nom))) {
						ResultSet result = stat.executeQuery(
								"SELECT id FROM Commune WHERE (nom=" + nom + " and code_dept=\"" + codeDept + "\");");
						result.last();
						if (result.getRow() == 0) {
							String req = "INSERT INTO Commune VALUES (" + id + ",\"" + codeDept + "\",\"" + codeInsee
									+ "\"," + nom + "," + slug + "," + lat + "," + lng + ");";
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

	/**
	 * Crée et remplit la table des adjacences entre communes (et donc entre
	 * départements dans notre modèle) qui indique si deux communes (déparements)
	 * sont adjacents
	 */
	public static void association() {
		Path filepath = Paths.get("data/adjacences.txt");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On crée la table
				 */
				Statement stat = conn.createStatement();
				stat.executeUpdate("CREATE TABLE IF NOT EXISTS Adjacence (" + "code1 VARCHAR(3) NOT NULL,"
						+ "code2 VARCHAR(3) NOT NULL," + "distance DOUBLE(17,14),"
						+ "CONSTRAINT pk_adjacance PRIMARY KEY(code1,code2),"
						+ "CONSTRAINT ville1_foreign FOREIGN KEY (code2) REFERENCES Commune (code_dept) ON DELETE CASCADE,"
						+ "CONSTRAINT ville2_foreign FOREIGN KEY (code2) REFERENCES Commune (code_dept) ON DELETE CASCADE);");
				List<String> lines = null;
				try {
					/**
					 * On récupère les données dans data/adjacences.txt
					 */
					lines = Files.readAllLines(filepath);
					for (int i = 1; i < lines.size(); i++) {
						String[] champs = lines.get(i).split(":");
						String code1 = champs[0];
						String[] liste = champs[1].split(",");
						for (int j = 0; j < liste.length; j++) {
							String code2 = liste[j];
							double dist = calculDistance(code1, code2);
							if (dist != -1) {
								/**
								 * On remplit la BDD
								 */
								String req = "INSERT INTO Adjacence VALUES (\"" + code1 + "\",\"" + code2 + "\"," + dist
										+ ");";
								stat.executeUpdate(req);
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

	/**
	 * Calcule la distance entre deux villes
	 */
	public static double calculDistance(String code1, String code2) {
		/**
		 * Les coordonnées des villes
		 */
		double x1, x2, y1, y2;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				/**
				 * On récupère les coordonées de la première ville (associée à son déparement
				 * dans notre modèle)
				 */
				Statement stat = conn.createStatement();
				ResultSet result = stat
						.executeQuery("SELECT gps_lat,gps_lng FROM Commune WHERE code_dept=\"" + code1 + "\";");
				if (result.next()) {
					y1 = result.getDouble("gps_lat");
					x1 = result.getDouble("gps_lng");
				} else {
					return (-1);
				}
				result.close();
				/**
				 * On récupère les coordonées de la seconde ville (associée à son déparement
				 * dans notre modèle)
				 */
				ResultSet result2 = stat
						.executeQuery("SELECT gps_lat,gps_lng FROM Commune WHERE code_dept=\"" + code2 + "\";");
				if (result2.next()) {
					y2 = result2.getDouble("gps_lat");
					x2 = result2.getDouble("gps_lng");
				} else {
					return (-1);
				}
				result2.close();
				stat.close();
				return (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (-1);
	}

	/**
	 * Sert à mettre à créer/mettre la jour la base de donnée sur le nombre
	 * d'hospitalisés, de personnes en réanimation, de guéris et de morts pour
	 * chaque commune et pour chaque date.
	 */
	public static void importationHistorique() {
		/**
		 * On récupère le nombre de lignes du fichier déjà importé pour mettre à jour
		 * uniquement les données manquantes
		 */
		int nbLigne = 0;
		try {
			File file = new File("dead_data.csv");
			FileReader freader = new FileReader(file);
			BufferedReader breader = new BufferedReader(freader);
			try {
				while (breader.readLine() != null) {
					nbLigne++;
				}
				breader.close();
				freader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
		/**
		 * On récupère les données du fichier csv de github et on les écrit dans un
		 * fichier local
		 */
		try (BufferedInputStream bis = new BufferedInputStream(new URL(
				"https://raw.githubusercontent.com/opencovid19-fr/data/master/data-sources/sante-publique-france/covid_hospit.csv")
						.openStream());
				FileOutputStream fos = new FileOutputStream("dead_data.csv")) {
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = bis.read(data, 0, 1024)) != -1) {
				fos.write(data, 0, byteContent);
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		/**
		 * On crée la base de données si elle n'existe pas déjà
		 */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				stat.executeUpdate("CREATE TABLE IF NOT EXISTS Historique(" + "departement VARCHAR(3) NOT NULL,"
						+ "date VARCHAR(10)," + "hospitalises INT," + "reanimation INT," + "gueris INT," + "morts INT,"
						+ "CONSTRAINT pk_historique PRIMARY KEY(departement,date),"
						+ "CONSTRAINT departmentCode FOREIGN KEY (departement) REFERENCES Departement (code) ON DELETE CASCADE);");
				stat.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * On lit le fichier local à partir de la ligne présentant de nouvelles données
		 * et on remplit la base de données
		 */
		try {
			File f = new File("dead_data.csv");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			try {
				for (int i = 0; i < nbLigne + 1; i++) {
					br.readLine();
				}
				String ligne;
				String champs[];
				String departement = "";
				String date = "";
				int hospitalises = 0;
				int reanimation = 0;
				int gueris = 0;
				int morts = 0;
				if ((ligne = br.readLine()) != null) {
					champs = ligne.split(";");
					departement = champs[0];
					date = champs[2];
					hospitalises = Integer.parseInt(champs[3]);
					reanimation = Integer.parseInt(champs[4]);
					gueris = Integer.parseInt(champs[5]);
					morts = Integer.parseInt(champs[6]);

					while ((ligne = br.readLine()) != null) {
						champs = ligne.split(";");
						/**
						 * Pour chaque département et chaque date, on fait la somme des données des
						 * sexes différents car nous n'en avons pas besion
						 */
						if (champs[0].equals(departement) && champs[2].equals(date)) {
							/**
							 * Si on lit une ligne ayant le meme département et la même date que la
							 * précédente on augmente les compteurs
							 */
							hospitalises += Integer.parseInt(champs[3]);
							reanimation += Integer.parseInt(champs[4]);
							gueris += Integer.parseInt(champs[5]);
							morts += Integer.parseInt(champs[6]);
						} else {
							/**
							 * Sinon on insère dans la BDD les compteurs et on les réinitialise avec les
							 * valeurs de la ligne lue actuellement
							 */
							try {
								Class.forName("com.mysql.cj.jdbc.Driver");
								try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
									Statement stat = conn.createStatement();
									stat.executeUpdate("INSERT IGNORE Historique VALUES(" + departement + "," + date
											+ "," + hospitalises + "," + reanimation + "," + gueris + "," + morts
											+ ");");
									stat.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							departement = champs[0];
							date = champs[2];
							hospitalises = Integer.parseInt(champs[3]);
							reanimation = Integer.parseInt(champs[4]);
							gueris = Integer.parseInt(champs[5]);
							morts = Integer.parseInt(champs[6]);
						}
					}
					/**
					 * On insère les dernières valeurs qu'on avait dans les compteurs
					 */
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
							Statement stat = conn.createStatement();
							stat.executeUpdate("INSERT IGNORE Historique VALUES(" + departement + "," + date + ","
									+ hospitalises + "," + reanimation + "," + gueris + "," + morts + ");");
							stat.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					br.close();
					fr.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
	}

	/**
	 * Retourne la liste des régions.(sauf DOM TOM)
	 */
	public static List<String> listeRegions() {
		List<String> listeRegions = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet res = stat.executeQuery("SELECT nom,slug FROM Region;");
				while (res.next()) {
					if (res.getString(2).equals("collectivites doutre mer"))
						listeRegions.add(res.getString(1));
				}
				res.close();
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (listeRegions);
	}
}