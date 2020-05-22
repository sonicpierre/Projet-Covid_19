package controll.main;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.BDD.RemplissageBDD;

public class MenuChoixController extends Application{
	
	private RotateTransition rot;
		
	private static Stage window = new Stage();
	
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
		try {
			this.start(window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Permet d'installer qd on le veut.
	 */
	
	@FXML
	public void installationBDD() {
		//Faire un truc de chargement.
		RemplissageBDD remplissage = new RemplissageBDD();
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
	public void start(Stage arg0) throws Exception {
		Parent principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/FenetrePrincipal.fxml"));
		Scene scene = new Scene(principale);
		window.setTitle("Lamamap");
		SQLController.getFenetre().setScene(scene);
		SQLController.getFenetre().centerOnScreen();
	}
}
