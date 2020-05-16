package vue.main;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import vue.menusGauche.MenuItinireraire;

public class PanelDeGauche extends VBox{

	private static PanelDeGauche instance;
	
	private PanelDeGauche() {
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color : #C4B4B2;");
		this.getChildren().add(new MenuItinireraire());
	}
	
	
	
	public static PanelDeGauche getInstance() {
		if (instance == null)
			instance = new PanelDeGauche();
		return instance;
	}
}
