package modele.territoire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modele.dates.Dates;

public class ComConf extends Commune {
	private Dates dateConfinement;
	private int dureePrevisionelle;

	public ComConf(String departement, int nbNouveauContamine24h, int nbDeces24h, int nbGuerison24h,
			ArrayList<Historique> historique, Dates dateConfinement, int dureePrevisionelle) {
		super(departement, nbNouveauContamine24h, nbDeces24h, nbGuerison24h, historique);
		this.dateConfinement = dateConfinement;
		this.dureePrevisionelle = dureePrevisionelle;
	}

	public int dureeRestante() {
		String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Dates dateActuelle = new Dates(dateStr);
		return (dureePrevisionelle - dateActuelle.conversionJours2020() + dateConfinement.conversionJours2020());
	}

	public Dates getDateConfinement() {
		return dateConfinement;
	}

	public int getDureePrevisionelle() {
		return dureePrevisionelle;
	}

	public void setDureePrevisionelle(int dureePrevisionelle) {
		this.dureePrevisionelle = dureePrevisionelle;
	}

	public void setDateConfinement(int annee, int mois, int jour) {
		dateConfinement.setDate(jour, mois, annee);
	}
	
}
