package controll.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import modele.BDD.RemplissageBDD;

public class chargementController implements Initializable{
	
	private static String typeInstalation;
	
	@FXML
	private ProgressBar progression;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MenuChoixController.getWindowInstallation().show();
		new RemplissageBDD(progression, typeInstalation);
	}

	public static String getTypeInstalation() {
		return typeInstalation;
	}

	public static void setTypeInstalation(String typeInstalation) {
		chargementController.typeInstalation = typeInstalation;
	}
	
	
}
