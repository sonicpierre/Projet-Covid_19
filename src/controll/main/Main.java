package controll.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

	
    // Primary Stage
    private static Stage window;
    // Two scenes
    private static Scene scene1, scene2;
	
    public static void main(String[] args) { 
        launch(args); 
    }

    @Override
    public void init() throws Exception {
    	
    	//Ici toutes les vérifes et importation des ressources
    	
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		
		Parent demarrage = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreSQL.fxml"));
		scene1 = new Scene(demarrage);
		
		Parent principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetrePrincipal.fxml"));
		scene2 = new Scene(principale);
		
		primaryStage.getIcons().add(new Image("file:lamas.jpg"));
		
		window.setScene(scene1);
		window.show();
		
	}
	
	public static void changerFenetre() {
		window.close();
		window.setTitle("Projet chemin le plus court");
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}
	
	
    @Override
    public void stop() throws Exception {
    	super.stop();
    	System.out.println("Au revoir");
    }
}
