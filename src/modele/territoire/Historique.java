package modele.territoire;

import modele.dates.Dates;

public class Historique {
	private int nbContamination;
	private int nbDeces;
	private int nbGuerison;
	private boolean estConfine;
	private Dates date;

	public Historique(int nbContamination, int nbDeces, int nbGuerison, boolean estConfine, Dates date) {
		super();
		this.nbContamination = nbContamination;
		this.nbDeces = nbDeces;
		this.nbGuerison = nbGuerison;
		this.estConfine = estConfine;
		this.date = date;
	}

	public int getNbContamination() {
		return nbContamination;
	}

	public void setNbContamination(int nbContamination) {
		this.nbContamination = nbContamination;
	}

	public int getNbDeces() {
		return nbDeces;
	}

	public void setNbDeces(int nbDeces) {
		this.nbDeces = nbDeces;
	}

	public int getNbGuerison() {
		return nbGuerison;
	}

	public void setNbGuerison(int nbGuerison) {
		this.nbGuerison = nbGuerison;
	}

	public boolean isEstConfine() {
		return estConfine;
	}

	public void setEstConfine(boolean estConfine) {
		this.estConfine = estConfine;
	}

	public Dates getDate() {
		return date;
	}

	public void setDate(Dates date) {
		this.date = date;
	}
}
