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
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MenuChoixController implements Initializable{
	
	private RotateTransition rot;
		
	private static Stage windowMap = new Stage();
	private static Stage windowInstallation = new Stage();
	private static Stage windowDataMining = new Stage();

	
	@FXML
	private ProgressBar progression;
	
	@FXML
	private AnchorPane PanMap;
	
	@FXML
	private AnchorPane PanPrediction;
	
	@FXML
	private Label labelMap;
	
	@FXML
	private Label LaberPrediction;
	
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

	// Event Listener on AnchorPane.onMouseEntered
	@FXML
	public void play(MouseEvent event) {
		PanMap.setStyle("-fx-background-color : #454240;");
		labelMap.setStyle("-fx-text-fill : CEDDF1");
		setRotate(LamaC1, true, 360, 7);
		setRotate(LamaC2, true, 180, 14);
		setRotate(LamaC3, true, 100, 16);
	}
	
	//Changement de style
	// Event Listener on AnchorPane.onMouseExited
	@FXML
	public void stop(MouseEvent event) {
		PanMap.setStyle("-fx-background-color : #6B8AB4;");
		labelMap.setStyle("-fx-text-fill : #bad0a8;");
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
	
	@FXML
	public void lancementPrediction() {
		SQLController.getFenetre().close();
		Parent principale;
		try {
			principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetreDataMining.fxml"));
			Scene scene = new Scene(principale);
			windowDataMining.setScene(scene);
			windowDataMining.setResizable(false);
			windowDataMining.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet d'installer qd on le veut.
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
	
	
	private void setRotate(Circle c, boolean reverse, int angle, int duration) {
		rot = new RotateTransition(Duration.seconds(duration),c);		
		rot.setAutoReverse(reverse);
		rot.setByAngle(angle);
		rot.setDelay(Duration.seconds(0));
		rot.setRate(3);
		rot.setCycleCount(28);
		rot.play();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		windowInstallation.initStyle(StageStyle.UNDECORATED);
		windowInstallation.initModality(Modality.APPLICATION_MODAL);
		
		windowDataMining.setTitle("Etude prédictives");
	}

	public static Stage getWindow() {
		return windowMap;
	}

	public static void setWindow(Stage window) {
		MenuChoixController.windowMap = window;
	}

	public static Stage getWindowInstallation() {
		return windowInstallation;
	}

	public static void setWindowInstallation(Stage windowInstallation) {
		MenuChoixController.windowInstallation = windowInstallation;
	}
	
	
}
