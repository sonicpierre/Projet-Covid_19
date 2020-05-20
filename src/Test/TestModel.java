package Test;

import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import modele.BDD.InitialisationBDD;
import modele.territoire.France;
import modele.trajectoire.Positionnement;

public class TestModel {

	public static void main(String[] args) {
		InitialisationBDD init = new InitialisationBDD();
//		boolean valid = init.initialiserBDD("MoneyMan","money"); //si l'user est déjà créé
//		System.out.println(valid);
		// OU :
//		boolean valid = init.initialiserBDD("MoneyMan","money","Mot de passe root"); //pour créer l'user
//		System.out.println(valid);
		
		
		
//		France.getInstance().ajouterHistoriqueBDD();
//		Positionnement pos = new Positionnement();
//		List<GeoPosition> listeCoor = pos.positionner2Villes("Basta", "Rodez");
//		System.out.println(listeCoor);
		
	}

}
