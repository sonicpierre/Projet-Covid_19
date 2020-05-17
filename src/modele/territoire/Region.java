package modele.territoire;

import java.util.List;

/**
 * Modélise une région comme une liste de départements et un nom. On peut avoir
 * le taux de communes confinées de la région.
 */

public class Region {
	private String nom;
	private List<Departement> departements;
	
	public Region(String nom) {
		this.nom=nom;
	}

	public double tauxComConf() {
		int nbComConf = 0;
		int nbComTotal = 0;
		for (Departement departement : departements) {
			nbComConf += departement.getListeComConf().size();
			nbComTotal += departement.getCommunes().size();
		}
		return ((double) nbComConf / (double) nbComTotal);
	}
	
	public void ajouterDepartement(Departement d) {
		departements.add(d);
	}
	
	public void supprimerDepartement(Departement d) {
		departements.remove(d);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Departement> getDepartements() {
		return departements;
	}
	
}
