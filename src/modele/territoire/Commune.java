package modele.territoire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modele.dates.Dates;

public abstract class Commune {
	private String departement;
	private int nbNouveauContamine24h;
	private int nbDeces24h;
	private int nbGuerison24h;
	private ArrayList<Historique> historique;
	
	public Commune(String departement, int nbNouveauContamine24h, int nbDeces24h, int nbGuerison24h,
			ArrayList<Historique> historique) {
		this.departement = departement;
		this.nbNouveauContamine24h = nbNouveauContamine24h;
		this.nbDeces24h = nbDeces24h;
		this.nbGuerison24h = nbGuerison24h;
		this.historique = historique;
	}

	public void ajouterHistorique() {
		boolean confine;
		confine = this.equals((ComConf)this);
		String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Dates dateActuelle = new Dates(dateStr);
		Historique hist = new Historique(nbNouveauContamine24h, nbDeces24h, nbGuerison24h, confine, dateActuelle);
		this.historique.add(hist);
	}
	
	public void supprimerHistorique() {
		this.historique.clear();
	}
	
	public void statsMois() {
		
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public int getNbNouveauContamine24h() {
		return nbNouveauContamine24h;
	}

	public void setNbNouveauContamine24h(int nbNouveauContamine24h) {
		this.nbNouveauContamine24h = nbNouveauContamine24h;
	}

	public int getNbDeces24h() {
		return nbDeces24h;
	}

	public void setNbDeces24h(int nbDeces24h) {
		this.nbDeces24h = nbDeces24h;
	}

	public int getNbGuerison24h() {
		return nbGuerison24h;
	}

	public void setNbGuerison24h(int nbGuerison24h) {
		this.nbGuerison24h = nbGuerison24h;
	}

	public ArrayList<Historique> getHistorique() {
		return historique;
	}
}
