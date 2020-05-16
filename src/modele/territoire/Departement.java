package modele.territoire;

import java.util.ArrayList;
import java.util.List;

import modele.dates.Dates;

public class Departement {
	private String nom;
	private List<Commune> communes = new ArrayList<Commune>();
	private List<ComConf> listeComConf = new ArrayList<ComConf>();
	private List<ComNonConf> listeComNonConf = new ArrayList<ComNonConf>();
	private String region;
	
	public Departement(String nom, String region) {
		this.nom=nom;
		this.region=region;
	}

	public int confiner(Commune c, Dates dateConfinement, int dureePrevisionelle) { //retirer de listeComNonConf
		communes.set(communes.indexOf(c), new ComConf(c.getDepartement(), c.getNbNouveauContamine24h(),
				c.getNbDeces24h(), c.getNbGuerison24h(), c.getHistorique(), dateConfinement, dureePrevisionelle));
		listeComConf.add(new ComConf(c.getDepartement(), c.getNbNouveauContamine24h(), c.getNbDeces24h(),
				c.getNbGuerison24h(), c.getHistorique(), dateConfinement, dureePrevisionelle));
		return (0);
	}

	public double tauxComConf() {
		return ((double) this.listeComConf.size() / (double) this.communes.size());
	}

	public void ajouterCommune(Commune c) {
		communes.add(c);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<Commune> getCommunes() {
		return communes;
	}

	public List<ComConf> getListeComConf() {
		return listeComConf;
	}

	public List<ComNonConf> getListeComNonConf() {
		return listeComNonConf;
	}
	
}