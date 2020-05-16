package vue.menusGauche;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MenuItinireraire extends VBox{
	
	public MenuItinireraire() {
        TextField depart = new TextField("Départ");
        TextField arrive = new TextField("Arrivée");
        Button valider = new Button("Valider");
        
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-padding: 10;");
        
        depart.setMaxWidth(200);
        arrive.setMaxWidth(200);
        
        this.getChildren().addAll(depart, arrive, valider);
        this.getStylesheets().add(getClass().getResource("StyleItineraire.css").toExternalForm());
	}

}
