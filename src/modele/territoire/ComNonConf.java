package modele.territoire;

import java.util.ArrayList;

import modele.dates.Dates;

public class ComNonConf extends Commune{
	private Dates dateDeconfinement;
	
	public ComNonConf(String departement, int nbNouveauContamine24h, int nbDeces24h, int nbGuerison24h,
			ArrayList<Historique> historique, Dates dateDeconfinement, int dureePrevisionelle) {
		super(departement, nbNouveauContamine24h, nbDeces24h, nbGuerison24h, historique);
		this.dateDeconfinement = dateDeconfinement;
	}

	public Dates getDateDeconfinement() {
		return dateDeconfinement;
	}

	public void setDateDeconfinement(int annee, int mois, int jour) {
		dateDeconfinement.setDate(jour, mois, annee);
	}
}
