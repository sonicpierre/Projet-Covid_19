package controll.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
		Main.changerFenetre();
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
