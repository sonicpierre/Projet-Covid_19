package controll.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class SQLController implements Initializable{

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
		fenetre.setTitle("Projet chemin le plus court");
		fenetre.setResizable(false);
		fenetre.centerOnScreen();
		fenetre.getIcons().add(new Image("file:lamas.jpg"));
		
		Parent principale;
		try {
			principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/MenuChoix.fxml"));
			Scene scene2 = new Scene(principale);
			fenetre.getIcons().add(new Image("file:lamas.jpg"));
			fenetre.setScene(scene2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	@FXML
	public void validerConnexion() {
		if(ini.initialiserBDD(loginConnexion.getText(), passwordConnexion.getText())) {
			Main.quitterFenetre();
			fenetre.show();
		} else {
			loginConnexion.setStyle("-fx-background-color : #DE7272;");
			passwordConnexion.setStyle("-fx-background-color : #DE7272;");
		}
	}
	
	@FXML
	protected void quitterPremiereFenetre() {
		System.out.println("Au revoir");
		Main.quitterFenetre();
	}
	
	
	@FXML
	protected void validerCreation() {
		if(ini.creerUser(loginNouveau.getText(), passwordNouveau.getText(), passwordRoot.getText())) {
			Main.quitterFenetre();
			fenetre.show();
		} else {
			passwordRoot.setStyle("-fx-background-color : #DE7272;");
		}
	}

	public static Stage getFenetre() {
		return fenetre;
	}


	public static void setFenetre(Stage fenetre) {
		SQLController.fenetre = fenetre;
	}
	
	
}
