package controll.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class FenetreDataMiningController implements Initializable{
	
	@FXML
	private WebView etudeFrance;
	@FXML
	private WebView etudeAquitaine;
	@FXML
	private WebView etudeCoreeState;
	@FXML
	private WebView etudeCoreeModele;
	@FXML
	private WebView etudeEtatUnisStat;
	@FXML
	private WebView etudeEtatUnisModele;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			
			File file = new File("Etudes/Prédiction_Coree.html");
			URL url= file.toURI().toURL();
			etudeCoreeModele.getEngine().load(url.toString());
			
			file = new File("Etudes/Data_Viz_Coree.html");
			url= file.toURI().toURL();
			etudeCoreeState.getEngine().load(url.toString());
			
			file = new File("Etudes/Data_viz_mortalite_US.html");
			url= file.toURI().toURL();
			etudeEtatUnisStat.getEngine().load(url.toString());
			
			/*
			file = new File("Etudes/Prédiction_survie.html");
			url= file.toURI().toURL();
			etudeCoreeModele.getEngine().load(url.toString());
			
			file = new File("Etudes/Prédiction_survie.html");
			url= file.toURI().toURL();
			etudeCoreeModele.getEngine().load(url.toString());
			*/
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void retourMenu() {
		MenuChoixController.getWindowDataMining().close();
		SQLController.getFenetre().show();
	}

}
