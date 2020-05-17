package vue.menusGauche;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MenuItinireraire extends TabPane{
	
	public MenuItinireraire() {
		
		VBox premierOnglet = new VBox(5);
		
		premierOnglet.setAlignment(Pos.CENTER);
		
        TextField depart = new TextField();
        TextField arrive = new TextField();
        Button validerItineraire = new Button("Valider");
        
        depart.setPromptText("Départ");
        arrive.setPromptText("Arrivée");
        depart.setMaxWidth(200);
        arrive.setMaxWidth(200);
        
        premierOnglet.getChildren().addAll(depart, arrive, validerItineraire);
        premierOnglet.getStylesheets().add(getClass().getResource("StyleItineraire.css").toExternalForm());
        
        Tab tabItineraire = new Tab("Itinéraire", premierOnglet);
        
        tabItineraire.setClosable(false);
        
        this.getTabs().add(tabItineraire);
        this.setStyle("-fx-background-color : #C4B4B2;");
        
		VBox deuxiemeOnglet = new VBox();
		
		deuxiemeOnglet.setAlignment(Pos.CENTER);
        
        TextField ville = new TextField();
        Button validerVille = new Button("Valider");
        
        ville.setPromptText("Votre village");
        ville.setMaxWidth(200);
        
        deuxiemeOnglet.getChildren().addAll(ville, validerVille);
        
       Tab rayonCentBornes = new Tab ("Rayon", deuxiemeOnglet);
       rayonCentBornes.setClosable(false);
       
       this.getTabs().add(rayonCentBornes);
        
	}

}
