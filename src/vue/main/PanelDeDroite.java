package vue.main;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import vue.graph.EvolutionMortsJours;
import vue.graph.MortGuerriHospitalise;

public class PanelDeDroite extends VBox{

private static PanelDeDroite instance;
	
	private PanelDeDroite() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().addAll(new EvolutionMortsJours().getMonGraph(), new MortGuerriHospitalise());
	}
	
	
	public static PanelDeDroite getInstance() {
		if (instance == null)
			instance = new PanelDeDroite();
		return instance;
	}
	
}
