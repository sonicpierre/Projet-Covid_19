package modele.dates;

public class Dates {
	private int jour;
	private int mois;
	private int annee;

	private boolean bissextile;

	public Dates(String date) {
		String[] valeurs = date.split("/");
		this.jour = Integer.parseInt(valeurs[0]);
		this.mois = Integer.parseInt(valeurs[1]);
		this.annee = Integer.parseInt(valeurs[2]);
		this.estBissextile();
	}

	public Dates(int jour, int mois, int annee) {
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
		this.estBissextile();
	}

	public void ajouterJours(int nbJours) {
		if ((this.jour + nbJours > 28 && this.bissextile == true && this.mois == 2)
				|| (this.jour + nbJours > 30 && estMois30(this.mois))
				|| (this.jour + nbJours > 31 && !estMois30(this.mois))) {
			this.jour = 1;
			this.mois++;
			if (this.mois > 12) {
				this.mois = 1;
				this.annee++;
			}
		}
	}

	public boolean estMois30(int mois) {
		int[] mois30 = { 4, 6, 9, 11 };
		for (int m : mois30) {
			if (m == mois) {
				return (true);
			}
		}
		return (false);
	}

	public void estBissextile() {
		if (this.annee % 400 == 0 || (this.annee % 4 == 0 && this.annee % 100 != 0)) {
			this.bissextile = true;
		} else {
			this.bissextile = false;
		}
	}
	
	public int conversionJours2020() {
		int res = 0;
		for (int i=2020; i<this.annee; i++) {
			if (i % 400 == 0 || (i % 4 == 0 && i % 100 != 0)) {
				res+=366;
			} else {
				res+=365;
			}
		}
		for (int i=1; i<this.mois; i++) {
			if (i==2 && bissextile) {
				res+=28;
			}
			else if (i==2 && !bissextile) {
				res+=29;
			}
			else if (estMois30(i)) {
				res+=30;
			} else {
				res+=31;
			}
		}
		return(res+this.jour);
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public boolean isBissextile() {
		return bissextile;
	}

	public void setDate(int jour, int mois, int annee) {
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}
}
