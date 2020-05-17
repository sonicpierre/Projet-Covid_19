package vue.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vue.SQL.FenetreDepart;

public class Main extends Application{

	
    public static void main(String[] args) { 
        launch(args); 
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Projet Java");
		primaryStage.setOnCloseRequest(e -> {
			primaryStage.close();
		});
		
		primaryStage.getIcons().add(new Image("file:lamas.jpg"));
		
		BorderPane panPrincial = new BorderPane();
		panPrincial.setCenter(new PanelCentral());
		panPrincial.setRight(PanelDeDroite.getInstance());
		panPrincial.setLeft(PanelDeGauche.getInstance());
		Scene sceneFinale = new Scene(panPrincial, 1550, 850);
		
		primaryStage.setScene(sceneFinale);
		primaryStage.show();
		FenetreDepart.FenetreDepartAffichage();
	}
    
}
