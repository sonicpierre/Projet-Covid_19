package controll.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

/**
 * La classe <b>FenetreDataScience</b> est la classe qui permet d'afficher les différentes études faite Data.
 * Elle est découpé en 6 parties :
 * 
 * <lu>
 * <li>Une étude statistique sur la France.</li>
 * <li>Un modèle pour prévoir le nombre de morts en France d'ici les prochains mois</li>
 * <li>Une étude statistique sur la Nouvelle Aquitaine</li>
 * <li>Une étude sur statistique sur la Corée</li>
 * <li>Un modèle pour prévoir en fonction du type de personne si on va mourir ou non</li>
 * <li>Une étude statistique sur les Etats-Unis</li>
 * <li>Une partie réservée aux prédictions.</li>
 * </lu>
 * </p>
 * 
 * @author Pierre Virgaux
 * @version 1.0
 */

public class FenetreDataMiningController implements Initializable{
	
	/**
	 * Les différentes WebView permettent  
	 */
	@FXML
	private WebView etudeFrance;
	@FXML
	private WebView modeleFrance;
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
	@FXML
	private WebView etudeNouvelleAquitaine;
	
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
			
			file = new File("Etudes/Prediction_FR.html");
			url= file.toURI().toURL();
			modeleFrance.getEngine().load(url.toString());
			
			file = new File("Etudes/Data_Viz_France.html");
			url= file.toURI().toURL();
			etudeFrance.getEngine().load(url.toString());
			
			file = new File("Etudes/Data_Viz_Aquitaine.html");
			url= file.toURI().toURL();
			etudeNouvelleAquitaine.getEngine().load(url.toString());
			
			
			
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
