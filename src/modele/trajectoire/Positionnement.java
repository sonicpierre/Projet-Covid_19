package modele.trajectoire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jxmapviewer.viewer.GeoPosition;

import modele.BDD.InitialisationBDD;

/**
 *La classe <b>Positionnement</b> permet de récupérer les coordonnées de villes.
 *@author Roxane Chatry
 *@version 1.0
 **/


public class Positionnement {
	
	/** les seuils de détermination si une ville est confinée ou non
	 * 
	 */
	private static HashMap<String,Integer> seuils;

	
	/** 
	 * Donne une suite de villes (coordonnées) qui forment une trajectoire entre les deux villes données
	 * 
	 * @param depart la ville de départ
	 * @param arrivee la ville d'arrivée
	 * @return la liste des coordonnées des villes formant la trajectoire
	 */
	public List<GeoPosition> positionnerTrajectoire(String depart, String arrivee) {
		//Si jamais aucun seuil n'a été défini.
		if(seuils == null)
			seuils = new HashMap<String, Integer>();
		List<String> villesNonConfinees = villesNonConfineesBDD(seuils);
		List<String> villes = calculerTrajectoire(depart,arrivee,villesNonConfinees);
		if (villes != null && villes.size()!=0) {
			return positionnerVilles(villes);
		} else {
			return null;
		}
	}
	
	/** Donne la liste des coordonnées des villes et leur niveau d'atteinte par l'épidémie
	 * 
	 * @return la map des coordonnées des villes associées à un entier entre 1 et 3 indiquant leur niveau d'épidémie
	 */
	public HashMap<GeoPosition,Integer> positionnerVillesConfinees() {
		//Si jamais aucun seuil n'a été défini.
		if(seuils == null)
			seuils = new HashMap<String, Integer>();
		// répartition des villes en 3 niveau d'épidémie
		HashMap<String,Integer> repartition = repartitionVilles(seuils);
		Set<String> villes = repartition.keySet();
		// Map des positions géographiques des villes à placer et niveau associé
		HashMap<GeoPosition,Integer> coord = new HashMap<GeoPosition,Integer>();
		Iterator<String> it = villes.iterator();
		while (it.hasNext()) {
			String v = it.next();
			Coordonnees coordonnee = new Coordonnees(v);
			GeoPosition pos = coordonnee.getPos();
			coord.put(pos,repartition.get(v));
		}
		return(coord);
	}

	/**
	 * Donne les coordonnées GPS des n villes de la liste (à partir de leurs noms)
	 * @param villes
	 * @return la liste des différentes coordonnées
	 */
	
	public List<GeoPosition> positionnerVilles(List<String> villes) {
		ListIterator<String> it = villes.listIterator();
		List<GeoPosition> listeCoor = new ArrayList<GeoPosition>();
		while (it.hasNext()) {
			String v = it.next();
			Coordonnees coor = new Coordonnees(v);
			GeoPosition pos = coor.getPos();
			listeCoor.add(pos);
		}
		return(listeCoor);
	}
	
	/** calcule le chemin le plus court entre 2 villes par l'algorithme de Dijkstra
	 * 
	 * @param depart le nom de la ville de départ
	 * @param arrivee le nom de la ville d'arrivée
	 * @param listeVilles la liste des villes non confinées de la bdd
	 * @return la liste ordonnée des villes étapes de la trajectoire (leurs noms) ou null si le trajet est impossible
	 */
	public List<String> calculerTrajectoire(String depart, String arrivee,List<String> listeVilles) {
		// si une des deux villes est confinée, on ne peut pas calculer de trajectoire
		if (!listeVilles.contains(depart) || !listeVilles.contains(arrivee)) {
			return null;
		}
		// nombre de villes de la bdd
		int n = listeVilles.size();
		//distance minimale entre la ville de départ et la ville <NomVille,distance>
		Map<String,Double> distanceMin = new HashMap<String,Double>();
		// prédécesseur <nomVille,nomPredecesseur>
		Map<String,String> predecesseur = new HashMap<String,String>();
		// liste des villes déjà visitées par l'algorithme
		List<String> visites = new ArrayList<String>();
			
		// initialisation des valeurs
		for (int i=0;i<n;i++) {
			String ville = listeVilles.get(i);
			// prédécesseurs non définis
			predecesseur.put(ville,null);
			// distances initialisées à l'infini
			distanceMin.put(ville,Double.POSITIVE_INFINITY);
		}
		
		// ville de départ = depart 
		distanceMin.replace(depart,(double) 0);
		
		// tant qu'on n'a pas visité toutes les villes
		while (visites.size()!=n ) {
			String villeCourante = villeDeDistanceMinimale(listeVilles,distanceMin,visites);
			// quand on atteint l'arrivée
			if (villeCourante.equals(arrivee)) {
				return(reformerTrajectoire(predecesseur,depart,arrivee));
			}
			
			if (villeCourante != null) {
				// ajout de la ville à la liste des villes déjà visitées
				visites.add(villeCourante);
				// liste des villes adjacentes à villeCourante
				List<String> adjacents = villesAdjacentesBDD(villeCourante);
				for (int i=0;i<n;i++) {
					String ville = listeVilles.get(i);
					// si la ville est adjacente à la ville courante
					if (adjacents.contains(ville)) {
						// distance de depart à ville en passant par villeCourante
						double dist = distanceMin.get(villeCourante)+distanceBDD(villeCourante,ville);
						// mise à jour des valeurs si le chemin est meilleur
						if (dist<distanceMin.get(ville)) {
							predecesseur.replace(ville,villeCourante);
							distanceMin.replace(ville, dist);
						}
					}
				}
			} else {
				// si la recherche de ville adjacente la plus proche n'a pas aboutit
				return(null);
			}
		}
		// cas d'erreur : aucun trajet possible
		return(null);
	}
	
	/** liste des villes contenues dans la bdd (nom des villes)
	 * 
	 * @return la liste des noms des villes
	 */
	public List<String> listeVillesBDD() {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		List<String> villes = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				// coordonnées de la première ville (une ville = un département dans notre modèle)
				ResultSet result = stat.executeQuery("SELECT nom FROM Commune;");
				while (result.next()) {
					villes.add(result.getString("nom"));
				}
				result.close();
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(villes);
	}
	
	/** donne la ville non visitée de distance minimale
	 * 
	 * @param villes la liste des villes non confinnées 
	 * @param distanceMin la Map des distances minimales
	 * @param visites la liste des villes déjà visitées
	 * @return le nom de la ville non visitée la plus proche du départ ou null si pas de ville possible
	 */
	public String villeDeDistanceMinimale(List<String> villes,Map<String,Double> distanceMin,List<String> visites) {
		double min = Double.POSITIVE_INFINITY;
		String villeMin = null;
		for (int i=0; i<distanceMin.size(); i++) {
			String v = villes.get(i);
			double dist = distanceMin.get(v);
			// villeMin doit être pas encore visitée
			if (!visites.contains(v) && dist<min) {
				min = dist;
				villeMin = v;
			}
		}
		return(villeMin);
	}
	
	/** recherche dans la base de données la liste des villes adjacentes à une ville donnée
	 * 
	 * @param ville la ville de référence
	 * @return le liste des noms de villes adjacentes
	 */
	public List<String> villesAdjacentesBDD(String ville) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		List<String> villes = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				// coordonnées de la première ville (une ville = un département dans notre modèle)
				ResultSet result = stat.executeQuery("SELECT nom FROM Commune WHERE code_dept IN (SELECT code2 FROM Adjacence WHERE code1 = (SELECT code_dept FROM Commune WHERE nom=\""+ville+"\"));");
				while (result.next()) {
					villes.add(result.getString("nom"));
				}
				result.close();
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(villes);
	}
	
	/** recherche dans la bdd la distance entre deux villes adjacentes
	 * 
	 * @param ville1 le nom de la première ville
	 * @param ville2 le nom de la seconde ville
	 * @return la distance entre les deux 
	 */
	public double distanceBDD(String ville1, String ville2) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				ResultSet result = stat.executeQuery("SELECT distance FROM Adjacence WHERE ( code1 = (SELECT code_dept FROM Commune WHERE nom=\""+ville1+"\") AND code2 = (SELECT code_dept FROM Commune WHERE nom=\""+ville2+"\"));");
				if (result.next()) {
					return (result.getDouble("distance"));
				}
				result.close();
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// cas d'erreur : si on ne trouve pas de distance, les villes ne sont pas adjacentes (équivalent à une distance infinie)
		return(Double.POSITIVE_INFINITY);
	}
	
	
	/** reforme la trajectoire à partir de l'état final de l'algorithme de Dijkstra 
	 * 
	 * @param predecesseur la map des prédécesseurs de chaque ville déjà visitée
	 * @param depart la ville de départ
	 * @param arrivee la ville d'arrivée
	 * @return la liste des noms des villes formant la trajectoire entre les deux villes (dans le sens inverse)
	 */
	public List<String> reformerTrajectoire(Map<String,String> predecesseur,String depart,String arrivee) {
		List<String> trajet = new ArrayList<String>();
		String villeCourante;
		if (predecesseur.get(arrivee)!=null) {
			villeCourante = arrivee;
			// retrace le trajet jusqu'au départ ou jusqu'à être coincé sur une ville sans antécédent
			while (villeCourante != depart && villeCourante != null) {
				trajet.add(villeCourante);
				// prédecesseur de ville courante
				villeCourante = predecesseur.get(villeCourante);
			}
		}
		return(trajet);
	}
	
	/** recherche dans la bdd la liste des villes non confinées en fonction des seuils donnés par l'utilisateur
	 * 
	 * @param seuils la HashMap des seuils(indicateur,valeurSeuil)
	 * @return la liste des noms des villes qui ne sont pas confinées à la dernière date des données de la bdd et selon les seuils donnés
	 */
	public List<String> villesNonConfineesBDD(HashMap<String,Integer> seuils) {
		// liste des villes non confinées
		List<String> villesNonConfinees = new ArrayList<String>();
		// liste totale des villes
		List<String> villesBDD = listeVillesBDD();
		// répartition des villes par rapport au seuil
		HashMap<String,Integer> repartition = repartitionVilles(seuils);
		for (int i=0; i<villesBDD.size(); i++) {
			String ville = villesBDD.get(i);
			// si la ville n'est pas confinée (niveau 1 ou 2)
			if (repartition.get(ville)!=3) {
				villesNonConfinees.add(ville);
			}	
		}
		return(villesNonConfinees);
	}
	
	/** Forme une répartition des villes en 3 catégories en fonction des seuils donnés
	 * 
	 * @param seuils la HashMap des seuils (indicateur,valeurSeuil)
	 * @return la hashMap contenant toute les villes de la bdd liées à un entier indiquant son niveau d'atteinte par l'épidémie (1 faible à 3 élevé)
	 */
	public HashMap<String,Integer> repartitionVilles(HashMap<String,Integer> seuils) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		
		// Villes triées selon leur position par rapport au seuil
		HashMap<String,Integer> villesTriees = new HashMap<String,Integer>();
		// liste totale des villes
		List<String> villesBDD = listeVillesBDD();
		
		// HashMap des demi-seuils
		HashMap<String,Integer> demiSeuils = new HashMap<String,Integer>();
		if (seuils.keySet().contains("morts")) {
			demiSeuils.put("morts", seuils.get("morts")/2);
		}
		if (seuils.keySet().contains("reanimation")) {
			demiSeuils.put("reanimation", seuils.get("reanimation")/2);
		}
		if (seuils.keySet().contains("hospitalises")) {
			demiSeuils.put("hospitalises", seuils.get("hospitalises")/2);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				// pour chaque ville, on regarde si elle est confinée
				for (int i=0; i<villesBDD.size(); i++) {
					String ville = villesBDD.get(i);
					ResultSet result = stat.executeQuery("SELECT hospitalises,reanimation,morts,date FROM Historique WHERE departement=(SELECT code_dept FROM Commune WHERE nom = \""+ville+"\") ORDER BY date DESC;");
					// on prend le premier
					if (result.next()) {
						// ville confinée = état 3 (rouge)
						if (confiner(result.getInt("hospitalises"),result.getInt("reanimation"),result.getInt("morts"),seuils)) {
							villesTriees.put(ville, 3);
						// ville confinée pour le demi-seuil = état 2 (orange)
						} else if (confiner(result.getInt("hospitalises"),result.getInt("reanimation"),result.getInt("morts"),demiSeuils)) {
							villesTriees.put(ville,2);
						} else {
							villesTriees.put(ville,1);
						}
					}
					result.close();
				}
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return(villesTriees);
	}
	
	/** Determine si une ville/département doit être confiné ou non en fonction des seuils donnés
	 * Si un des seuils est dépassé, la ville sera confinée
	 * @param hospitalises dernière donné du nombre d'hospitalisation en 24h
	 * @param reanimation dernière donné du nombre de personnes en réanimation en 24h
	 * @param morts dernière donné du nombre de morts en 24h
	 * @param seuils hashmap contenant les seuils recherchés
	 * @return un booléen indiquant si la ville doit être confinée ou non
	 */
	public boolean confiner(int hospitalises, int reanimation, int morts, HashMap<String,Integer> seuils) {
		// liste des indicateurs à prendre en compte
		Set<String> indicateurs = seuils.keySet();
		// ville non confinée par défaut
		boolean confine = false;
		// si trop de personnes hospitalisés, la ville est confinée
		if (indicateurs.contains("hospitalises")) {
			confine = confine || (hospitalises >= seuils.get("hospitalises"));
		}
		if (indicateurs.contains("reanimation")) {
			confine = confine || (reanimation >= seuils.get("reanimation"));
		}
		if (indicateurs.contains("morts")) {
			confine = confine || (morts >= seuils.get("morts"));
		}
		return (confine);
	}
	
	 /** donne la map des seuils
	  * 
	  * @return les seuils
	  */
	public static HashMap<String, Integer> getSeuils() {
		return seuils;
	}
	
	/** set la Map des seuils
	 * 
	 * @param seuils
	 */
	public static void setSeuils(HashMap<String, Integer> seuils) {
		Positionnement.seuils = seuils;
	}
}

