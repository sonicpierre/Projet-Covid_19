package controll.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

	
    // Primary Stage
    private static Stage window1, window2;
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
		
		
		window1 = new Stage();
		window1.initModality(Modality.APPLICATION_MODAL);			//Rend insencible ce qu'il y a derrière la fenêtre
		window1.initStyle(StageStyle.UNDECORATED);
		window1.centerOnScreen();

		
		window2 = primaryStage;
		window2.setTitle("Projet chemin le plus court");
		window2.setResizable(false);
		window2.centerOnScreen();
		
		
		Parent demarrage = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreSQL.fxml"));
		scene1 = new Scene(demarrage);
		
		Parent principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetrePrincipal.fxml"));
		scene2 = new Scene(principale);
		
		window2.getIcons().add(new Image("file:lamas.jpg"));
		
		window2.setScene(scene2);
		
		window1.setScene(scene1);
		
		window2.show();
		window1.show();
		
	}
	
	public static void changerFenetre() {
		window1.close();

	}
	
	
    @Override
    public void stop() throws Exception {
    	super.stop();
    	System.out.println("Au revoir");
    }
}
