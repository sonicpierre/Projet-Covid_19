package controll.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *La classe <b>MenuChoixController</b> est la classe qui permet de choisir la partie du projet à explorer.
 *Comme le projet avait 2 parties distinctes nous avons décidé de ne pas mélanger en présentant bien séparemment la partie Data de la partie Carte
 *@author VIRGAUX Pierre
 *@version 2.0
 **/


public class MenuChoixController implements Initializable{
	
	/**
	 * Ici on déclare les différentes fenêtres. Nous avons décidé d'utiliser plusieurs fenêtres pour 
	 * des questions de redimentionnement de la fenêtre au moment des changements.
	 */
	
	private static final Stage windowMap = new Stage();
	private static final Stage windowInstallation = new Stage();
	private static final Stage windowDataMining = new Stage();

	/**
	 * Ici il s'agit principalement de variables liées au fichier MenuChoix.fxml.
	 * Il s'agit de définir les rotations liées au cercles.
	 * Nous avons fait cette animation certes pour des raisons esthétiques mais aussi pour montrer que JavaFX permet de gérer 
	 * les animations contrairement à Swing.
	 */
	
	private RotateTransition rot;
	
	/**
	 * Ici on veut changer l'ensemble de la couleur du Pan en fonction de l'entré et la sortie de la souris.
	 */
	
	@FXML
	private AnchorPane PanMap;
	
	@FXML
	private AnchorPane PanPrediction;
	
	/**
	 * Ici les Labels car on veut pouvoir faire un style dynamique
	 */
	
	@FXML
	private Label labelMap;
	
	@FXML
	private Label LaberPrediction;
	
	/**
	 * Ici les cercles
	 */
	
	@FXML
	private Circle LamaC1;
	
	@FXML
	private Circle LamaC2;
	
	@FXML
	private Circle LamaC3;
	
	@FXML
	private Circle VieC11;
	
	@FXML
	private Circle VieC21;
	
	@FXML
	private Circle VieC31;

	/**
	 * Cette méthode qui est hérité de Initialize permet de mettre des paramètres à la fenêtre avant qu'elle soit liée au fichier FXML. 
	 */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		windowInstallation.initStyle(StageStyle.UNDECORATED);
		windowInstallation.initModality(Modality.APPLICATION_MODAL);
	}
	
	/**
	 * On va faire tourner les cercles quand la souris rentre dans le panel.
	 * @param event
	 */
	
	@FXML
	public void play(MouseEvent event) {
		//On change la couleur du premienPan
		PanMap.setStyle("-fx-background-color : #454240;");
		//On change la couleur du Label.
		labelMap.setStyle("-fx-text-fill : CEDDF1");
		//On fait tourner les différents cercles
		setRotate(LamaC1, true, 360, 7);
		setRotate(LamaC2, true, 180, 14);
		setRotate(LamaC3, true, 100, 16);
	}
	
	/**
	 * On arrête de faire tourner au moment de la sortie de la souris et on repasse les couleurs à l'état d'origine.
	 * @param event
	 */
	
	@FXML
	public void stop(MouseEvent event) {
		PanMap.setStyle("-fx-background-color : #6B8AB4;");
		labelMap.setStyle("-fx-text-fill : #bad0a8;");
		//On arrête de faire tourner les cercles revient à dire que l'angle de rotation est de 0.
		setRotate(LamaC1, true, 0, 7);
		setRotate(LamaC2, true, 0, 14);
		setRotate(LamaC3, true, 0, 16);
		
	}
	
	// Event Listener on AnchorPane.onMouseEntered
	@FXML
	public void play2(MouseEvent event) {
		PanPrediction.setStyle("-fx-background-color : #454240;");
		LaberPrediction.setStyle("-fx-text-fill : CEDDF1");
		setRotate(VieC11, true, 360, 7);
		setRotate(VieC21, true, 180, 14);
		setRotate(VieC31, true, 100, 16);
	}
	// Event Listener on AnchorPane.onMouseExited
	@FXML
	public void stop2(MouseEvent event) {
		PanPrediction.setStyle("-fx-background-color : #6B8AB4;");
		LaberPrediction.setStyle("-fx-text-fill : #bad0a8;");
		setRotate(VieC11, true, 0, 7);
		setRotate(VieC21, true, 0, 14);
		setRotate(VieC31, true, 0, 16);
	}
	
	/**
	 * Cette fonction va quand on clique sur la partie map lancer la fenêtre correspondante.
	 */
	
	@FXML
	public void lancerLaMap() {
		SQLController.getFenetre().close();
		Parent principale;
		try {
			principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetrePrincipal.fxml"));
			Scene scene = new Scene(principale);
			windowMap.setTitle("Lamamap");
			windowMap.setResizable(true);
			windowMap.setScene(scene);
			windowMap.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cette fonction permet de récupérer la fenêtre avec les différentes études de données.
	 */
	
	@FXML
	public void lancementPrediction() {
		SQLController.getFenetre().close();
		Parent principale;
		try {
			principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreDataMining.fxml"));
			Scene scene = new Scene(principale);
			windowDataMining.setScene(scene);
			windowDataMining.setResizable(false);
			windowDataMining.setTitle("Etude prédictives");
			windowDataMining.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet d'installer qd on le veut la BDD. Sachant que ça prend beaucoup de temps comme entre 8 et 12 minutes il
	 * a été choisi de donner le choix à l'utilisateur de l'instant auquel il aller lancer l'installation.
	 */
	
	@FXML
	public void installationBDD() {
		Parent principale;
		try {
			principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/chargement.fxml"));
			Scene scene = new Scene(principale);
			windowInstallation.setScene(scene);
			windowInstallation.centerOnScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Cette fonction permet d'animer les cercles
	 * 
	 * @param c correspond au cercle à faire tourner
	 * @param reverse correspond au fait de faire une rotation dans un seul sens ou repartir en arrière.
	 * @param angle correspond à l'angle avant de repartir en arrière
	 * @param duration correspond à la durée de la rotation avant de repartir dans l'autre sens.
	 */
	
	private void setRotate(Circle c, boolean reverse, int angle, int duration) {
		rot = new RotateTransition(Duration.seconds(duration),c);		
		rot.setAutoReverse(reverse);
		rot.setByAngle(angle);
		rot.setDelay(Duration.seconds(0));
		rot.setRate(3);
		rot.setCycleCount(28);
		rot.play();
	}

	/**
	 * Permet de récupérer la fenêtre qui contient la carte.
	 * @return la fenêtre avec la map
	 */
	
	public static Stage getWindow() {
		return windowMap;
	}
	
	/**
	 * Permet de récupérer la fenêtre Installation
	 * @return fenêtre liée à l'installation
	 */

	public static Stage getWindowInstallation() {
		return windowInstallation;
	}

	/**
	 * Permet de récupérer la fenêtre des études liées à la Data
	 * @return fenêtre liée aux Data
	 */
	
	public static Stage getWindowDataMining() {
		return windowDataMining;
	}
}
