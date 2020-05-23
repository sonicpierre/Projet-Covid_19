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
import java.util.Map;
import java.util.Set;

import org.jxmapviewer.viewer.GeoPosition;

import modele.BDD.InitialisationBDD;

// permet de récupérer les coordonnées de villes 
public class Positionnement {
	
	private static HashMap<String,Integer> seuils;
	
	/** donne une suite de villes (coordonnées) qui forment une trajectoire entre les deux villes données
	 * 
	 * @param depart
	 * @param arrivee
	 * 
	 * @return
	 */
	public List<GeoPosition> positionnerTrajectoire(String depart, String arrivee) {
		//Si jamais aucun seuil n'a été défini.
		if(seuils == null)
			seuils = new HashMap<String, Integer>();
		List<String> villesNonConfinees = villesNonConfineesBDD(seuils);
		List<String> villes = calculerTrajectoire(depart,arrivee,villesNonConfinees);
		System.out.println(villes);
		if (villes != null && villes.size()!=0) {
			return positionnerVilles(villes);
		} else {
			return null;
		}
	}
	
	// donne les coordonnées GPS des n villes de la liste (à partir de leurs noms)
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
	
	/** calcule le chemin le plus court entre 2 villes
	 * 
	 * @param depart
	 * @param arrivee
	 * @param listeVilles la liste des villes non confinées de la bdd
	 * @return la liste ordonnée des villes étapes de la trajectoire
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
			String villeCourante = villeDeDistanceMinimale(listeVilles,distanceMin,visites,listeVilles);
			// quand on atteint l'arrivée
			if (villeCourante.equals(arrivee)) {
				return(reformerTrajectoire(predecesseur,depart,arrivee));
			}
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
	 * @param distanceMin la Map des distances minimales
	 * @param visites la liste des villes déjà visitées
	 * @return le nom de la ville non visitée la plus proche du départ
	 */
	public String villeDeDistanceMinimale(List<String> villes,Map<String,Double> distanceMin,List<String> visites,List<String> nonConfinees) {
		double min = Double.POSITIVE_INFINITY;
		String villeMin = null;
		for (int i=0; i<distanceMin.size(); i++) {
			String v = villes.get(i);
			double dist = distanceMin.get(v);
			// villeMin doit être non confinée et pas encore visitée
			if (!visites.contains(v) && nonConfinees.contains(v) && dist<min) {
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
	
	public double distanceBDD(String ville1, String ville2) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
				Statement stat = conn.createStatement();
				// coordonnées de la première ville (une ville = un département dans notre modèle)
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
	
	public List<String> villesNonConfineesBDD(HashMap<String,Integer> seuils) {
		String url = "jdbc:mysql://localhost/France?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = InitialisationBDD.user;
		String passwd = InitialisationBDD.passwd;
		
		// liste des villes non confinées
		List<String> villesNonConfinees = new ArrayList<String>();
		// liste totale des villes
		List<String> villesBDD = listeVillesBDD();
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
						// si la ville n'est pas confinée, on l'ajoute à la liste des villes non confinées
						if (!confiner(result.getInt("hospitalises"),result.getInt("reanimation"),result.getInt("morts"),seuils)) {
							villesNonConfinees.add(ville);
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
		return(villesNonConfinees);
	}
	
	/** Determine si une ville/département doit être confiné ou non en fonction des seuils donnés
	 * Si un des seuils est dépassé, la ville sera confinée
	 * @param hospitalises dernière donné du nombre d'hospitalisation en 24h
	 * @param reanimation dernière donné du nombre de personnes en réanimation en 24h
	 * @param morts dernière donné du nombre de morts en 24h
	 * @param seuils hashmap contenant les seuils recherchés
	 * @return un booléen indicant si la ville doit être confinée ou non
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

	public static HashMap<String, Integer> getSeuils() {
		return seuils;
	}

	public static void setSeuils(HashMap<String, Integer> seuils) {
		Positionnement.seuils = seuils;
	}
	
}

