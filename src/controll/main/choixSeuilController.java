package controll.main;

import java.util.HashMap;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import modele.trajectoire.Positionnement;

public class choixSeuilController {
	
	@FXML
	private TextField labelHospitalise;
	@FXML
	private TextField labelReanimation;
	@FXML
	private TextField labelMort;

	// Event Listener on Button.onMouseClicked
	@FXML
	public void validationSeuil(MouseEvent event) {
		HashMap<String, Integer> monHashi = new HashMap<String, Integer>();
		if(!labelHospitalise.getText().equals(""))
			monHashi.put("hospitalises", Integer.parseInt(labelHospitalise.getText()));
		if(!labelReanimation.getText().equals(""))
			monHashi.put("reanimation", Integer.parseInt(labelReanimation.getText()));
		if(!labelMort.getText().equals(""))
			monHashi.put("morts", Integer.parseInt(labelMort.getText()));
		Positionnement.setSeuils(monHashi);
		PrincipalController.getWindow().close();
	}
}
