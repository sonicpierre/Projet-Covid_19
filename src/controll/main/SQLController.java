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

/**
 *La classe <b>SQLController</b> est la classe qui permet de se connecter ou créer un utilisateur.
 *@author Virgaux Pierre
 *@version 1.0
 **/


public class SQLController implements Initializable{

	//Permettra d'initialiser et de créer la BDD si elle n'existe pas.
	
	private InitialisationBDD ini = new InitialisationBDD();
	
	//Il s'agit de la fenêtre qui acceuillera tout les éléments graphiques
	
	private static final Stage fenetre = new Stage();
	
	/**
	 * Ici on a toutes les entrés textes pour pouvoir récupérer les entrés de l'utilisateur
	 * On les veut aussi pour indiquer si l'utilisateur entre un nom d'utilisateur ou un mot de passe incorrrecte
	 */
	
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
	
	/**
	 * Permet d'initialiser les différentes option de la fenêtre
	 */
	
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
			//On change l'icone de la fenêtre
			fenetre.getIcons().add(new Image("file:lamas.jpg"));
			fenetre.setScene(scene2);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Permet de vérifier si l'utilisateur entré est le bon et changer l'apparence des entrés textes si ce n'est pas le cas
	 */
	
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
	
	/**
	 * Permet de fermet la fenêtre si on clique sur Quitter.
	 */
	
	@FXML
	protected void quitterPremiereFenetre() {
		System.out.println("Au revoir");
		Main.quitterFenetre();
	}
	
	/**
	 * Permet de valider le nom d'utilisateur et le mot de passe au moment de la création d'un nouvel utilisateur.
	 */
	
	@FXML
	protected void validerCreation() {
		if(ini.creerUser(loginNouveau.getText(), passwordNouveau.getText(), passwordRoot.getText())) {
			Main.quitterFenetre();
			fenetre.show();
		} else {
			passwordRoot.setStyle("-fx-background-color : #DE7272;");
		}
	}
	
	/**
	 * Permet de récupérer la fenêtre
	 * @return la fenêtre
	 */

	public static Stage getFenetre() {
		return fenetre;
	}
	
	
}
