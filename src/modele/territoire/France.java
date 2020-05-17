package modele.territoire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import modele.dates.Dates;

public class France {
	Set<Region> regions;
	private static France instance;

	private France() {
		regions = new HashSet<Region>();
	}

	public static France getInstance() {
		if (instance == null) {
			instance = new France();
		}
		return (instance);
	}

	public void ajouterRegion(Region r) {
		regions.add(r);
	}

	public void supprimerRegion(Region r) {
		regions.remove(r);
	}

	public void carteColoreTau(Dates date) {

	}

	/**
	 * A appeler toutes les 24h pour remplir l'historique
	 * 
	 * @throws IOException
	 */

	public void ajouterHistoriqueBDD() {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "MoneyMan";
		String passwd = "money";
		try {
			File f = new File("covid_hospit.csv");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			try {
				String ligne = br.readLine();
				while ((ligne = br.readLine()) != null) {
					String champs[] = ligne.split(";");
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
							Statement stat = conn.createStatement();
							stat.executeUpdate("CREATE TABLE IF NOT EXISTS `Historique`("
									+ "`departement` VARCHAR(3) COLLATE utf8mb4_unicode_ci NOT NULL,"
									+ "`date` VARCHAR(10)," + "`hospitalises` INT," + "`reanimation` INT,"
									+ "`gueris` INT," + "`morts` INT," + "PRIMARY KEY(`departement`,`date`),"
									+ "KEY `departmentCode` (`departement`),"
									+ "CONSTRAINT `departmentCode` FOREIGN KEY (`departement`) REFERENCES `departments` (`code`) ON DELETE CASCADE);");
							stat.executeUpdate("INSERT IGNORE Historique SET `departement`=" + champs[0] + ",`date`='"
									+ champs[2] + "',`hospitalises`=" + Integer.parseInt(champs[3]) + ",`reanimation`="
									+ Integer.parseInt(champs[4]) + ",`gueris`=" + Integer.parseInt(champs[5])
									+ ",`morts`=" + Integer.parseInt(champs[6]));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}

	}

	/*
	 * public ArrayList<ArrayList<ComNonConf>> comFortementConnexe() {
	 * 
	 * }
	 * 
	 * public ArrayList<ComNonConf> chemin(ComNonConf com1, ComNonConf com2) {
	 * 
	 * }
	 */
	public void statistique() {

	}
}
