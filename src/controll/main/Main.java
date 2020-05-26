package controll.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *La classe <b>Main</b> est la classe principale de notre application.
 *Elle permet de lancer la fenêtre graphique qui active l'ensemble des fonctionnalités de l'application
 *@author VIRGAUX Pierre
 *@version 2.0
 **/


public class Main extends Application{

    // Première fenêtre qui est celle de la connexion à mysql 
    private static Stage window1;
    // Contien donc la scène avec les entrés textes et bouttons
    private static Scene scene1;
	
    /**
     * Permet de lancer l'application
     * @param args
     */
    
    public static void main(String[] args) {
    	//Ici cette méthode va appeler la fonction start qui hérite de Application de la librairie JavaFX
        launch(args); 
    }
    
    /**
     * Permet ici d'afficher la fenêtre avec son contenu.
     */
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		window1 = primaryStage;
		//Permet ici d'enlever la bordure de la fenêtre qui permettrait de resizer la fenêtre ou de la fermer
		window1.initStyle(StageStyle.UNDECORATED);
		//On centre la fenêtre sur l'écran
		window1.centerOnScreen();
		//On charge le fichier FXML correspondant
		Parent demarrage = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreSQL.fxml"));
		//On indique à la fenêtre son contenu.
		scene1 = new Scene(demarrage);
		//On place la scène dans la fenêtre
		window1.setScene(scene1);
		//On affiche la fenêtre
		window1.show();
	}
	
	public static void quitterFenetre() {
		window1.close();
	}
	
	/**
	 * Permet d'afficher un petit message dans la console pour bien s'assurer que l'utilisateur est parti.
	 */
	
    @Override
    public void stop() throws Exception {
    	super.stop();
    	System.out.println("Au revoir");
    }
}
