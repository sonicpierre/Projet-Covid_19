package controll.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modele.BDD.InitialisationBDD;
import modele.BDD.RemplissageBDD;

public class SQLController extends Application implements Initializable{

	private InitialisationBDD ini = new InitialisationBDD();
	
	private static Stage fenetre = new Stage();
	
	@FXML
	private TextField loginConnexion;
	
	@FXML
	private PasswordField passwordConnexion;
	
	@FXML
	private TextField loginNouveau;
	
	@FXML
	private PasswordField passwordNouveau;
	
	@FXML
	private PasswordField passwordRoot;
	
	@FXML
	private ProgressIndicator chargement;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
	@FXML
	public void validerConnexion() {
		if(ini.initialiserBDD(loginConnexion.getText(), passwordConnexion.getText())) {
			chargement.setVisible(true);
			Main.changerFenetre();
			try {
				this.start(fenetre);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			loginConnexion.setStyle("-fx-background-color : #DE7272;");
			passwordConnexion.setStyle("-fx-background-color : #DE7272;");
		}
	}
	
	
	@FXML
	protected void quitterPremiereFenetre() {
		System.out.println("Au revoir");
	}
	
	
	@FXML
	protected void validerCreation() {
		ini.creerUser(loginNouveau.getText(), passwordNouveau.getText(), passwordRoot.getText());
		new RemplissageBDD();
	}

	//Ici on génère la deuxième fenêtre
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Projet chemin le plus court");
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.getIcons().add(new Image("file:lamas.jpg"));
		
		Parent principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/MenuChoix.fxml"));
		Scene scene2 = new Scene(principale);
		primaryStage.getIcons().add(new Image("file:lamas.jpg"));
		primaryStage.setScene(scene2);

		primaryStage.show();
	}

	public static Stage getFenetre() {
		return fenetre;
	}


	public static void setFenetre(Stage fenetre) {
		SQLController.fenetre = fenetre;
	}
	
	
}
