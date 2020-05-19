package controll.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	
    public static void main(String[] args) { 
        launch(args); 
    }

    @Override
    public void init() throws Exception {
    	
    	//Ici toutes les vérifes et importation des ressources
    	
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreSQL.fxml"));
		Scene scene = new Scene(root);
		/*
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
		*/
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
    
}
