package controll.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import modele.BDD.InitialisationBDD;
import modele.BDD.RemplissageBDD;

public class SQLController implements Initializable{

	
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
	public void validerConnexion() {
		InitialisationBDD ini = new InitialisationBDD();
		System.out.println(loginConnexion.getText() + passwordConnexion.getText());
		if(ini.initialiserBDD(loginConnexion.getText(), passwordConnexion.getText())) {
			//Faire un truc de chargement.
			RemplissageBDD remplissage = new RemplissageBDD();
			Main.changerFenetre();
		} else {
			loginConnexion.setStyle("-fx-background-color : #DE7272;");
			passwordConnexion.setStyle("-fx-background-color : #DE7272;");
		}
	}
	
	
	@FXML
	protected void quitterPremiereFenetre() {
		System.out.println("Au revoir");
		System.exit(0);
	}
	
	
	@FXML
	protected void validerCreation() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}


	
	
}
