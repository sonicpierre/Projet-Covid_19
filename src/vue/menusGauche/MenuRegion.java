package vue.menusGauche;


import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import modele.territoire.Region;

public class MenuRegion extends GridPane{

	List<Region> listesRegion;
	
	public MenuRegion(List<Region> lesRegions) {
		Label titre = new Label("Region");
		titre.setStyle("-fx-font-size : 20;");
		this.add(titre, 1, 1);
		
		int compteur = 2;
		for(Region maRegion : lesRegions) {
			Label labelTempo = new Label(maRegion.getNom());
			this.add(labelTempo, compteur, 1);
			compteur++;
		}
	}
}
