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
    private static Stage window1;
    // Two scenes
    private static Scene scene1;
	
    public static void main(String[] args) { 
        launch(args); 
    }

    @Override
    public void init() throws Exception {
    	
    	//Ici toutes les vérifes et importation des ressources
    	
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		window1 = primaryStage;
		window1.initStyle(StageStyle.UNDECORATED);
		window1.centerOnScreen();
	
		
		Parent demarrage = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreSQL.fxml"));
		scene1 = new Scene(demarrage);
		
		window1.setScene(scene1);

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
