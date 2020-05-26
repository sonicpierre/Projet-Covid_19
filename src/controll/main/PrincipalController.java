package controll.main;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import org.jxmapviewer.viewer.GeoPosition;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.BDD.DataGraphes;
import modele.BDD.RepresentationSurCarte;
import modele.trajectoire.Coordonnees;
import modele.trajectoire.Positionnement;
import vue.map.MapView;

/**
 *La classe <b>PrincipalController</b> est la classe avec la carte et les graphiques liés.
 *Elle est découpé en 3 parties :
 * <lu>
 * <li>Une partie à gauche contenant la liste des régions, un menu itinéraire et enfin un menu pour tracer un rayon de 100 km autour de sa ville</li>
 * <li>Au centre la carte qui s'adapte avec différentes options pour visualiser sur celle-ci directement, mort, hospitalisé, cas actifs</li>
 * <li>A droite on retrouve les graphiques qui correspondent tout d'abord à la France puis aux régions en particulier</li>
 * </p>
 *@author VIRGAUX Pierre
 *@version 2.0
 **/

@SuppressWarnings("unchecked")
public class PrincipalController implements Initializable{
	
	/**
	 * @see MapView();
	 */
	private MapView carte = new MapView();
	
	/**
	 * @see Positionnement
	 */
	private Positionnement monPos = new Positionnement();
	
	/**
	 * Fenêtre qui contiendra les seuils pour construire les itinéraires
	 */
	private static final Stage window = new Stage();
	
	/**
	 * Permet ici de changer le label du boutton entre Classique et Satellite
	 */
	
	private static final String nomSatellite = "Satellite";
	private static final String nomClassique = "Classique";
	
	//Ici la librairie JavaFX permet de faire de jolis graphiques.
	
	@FXML
	private LineChart<String, Number> lineChart;
	
	@FXML
	private PieChart camembert;

    @FXML
    private StackPane panePrincipal;
    
    //On a ici les différentes composantes pour faire le menu itinéraire
    
    @FXML
    private Button changerMap;
    
    @FXML
    private TextField villeNum1;
    
    @FXML
    private TextField villeNum2;
    
    @FXML
    private TextField text100;
    
    @FXML
    private Label labelRegion;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Parent principale;
		try {
			principale = FXMLLoader.load(getClass().getResource("/ressource/fxml/choixSeuil.fxml"));
			Scene scene = new Scene(principale);
			window.setResizable(false);
			window.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		window.setTitle("Choix seuils");
		//Permet de rendre la carte insensible derrière la fenêtre du choix des seuils
		window.initModality(Modality.APPLICATION_MODAL);
		
		//La carte utilise Swing donc on est obligé de définir un SwingNode qui donne la possibilité de mettre la carte
		SwingNode swingNode = new SwingNode();
		
		//Permet de définir les données globales propres à la France pour le graphique avec des lignes et le graphique camembert.
	    lineChart.getData().addAll(DataGraphes.guerisRegion(), DataGraphes.hospitalisesRegion(), DataGraphes.reanimesRegion(), DataGraphes.mortsRegion());
        camembert.setData(DataGraphes.statsQuotidiennes());
        
        //Au lancement de la carte on veut directement la représentation du nombre de morts.
        carte.dessinerCercle(RepresentationSurCarte.cercleMortsVille(), new Color(Color.RED.getRed(),Color.RED.getGreen(),Color.RED.getBlue(),80));
        
        //On remplie le Swing Node avec la carte.
	    SwingUtilities.invokeLater(()->swingNode.setContent(carte));
        panePrincipal.getChildren().add(swingNode);
	}
	
	/**
	 * Permet de lancer la fenêtre qui donne la possibilité de faire varier les seuils.
	 */
	
	@FXML
	private void fenetreConfinement() {
		window.show();
	}
	
	/**
	 * Permet de retrouver les graphes de la France en général
	 */
	
	@FXML
	private void retourEtatInitial() {
		lineChart.getData().setAll(DataGraphes.guerisRegion(), DataGraphes.hospitalisesRegion(), DataGraphes.reanimesRegion(), DataGraphes.mortsRegion());
		camembert.setData(DataGraphes.statsQuotidiennes());
		labelRegion.setText("Region");
	}
	
	/**
	 * Permet de faire l'itinéraire et de l'afficher sur la carte.
	 * @see Positionnement
	 */
	
	@FXML
	private void validationItineraire() {
		//On vérifie que l'itinéraire n'est pas nul
		List<GeoPosition> itineraire = monPos.positionnerTrajectoire(villeNum1.getText(), villeNum2.getText());
		if(itineraire != null) {
			carte.dessinerItineraire(itineraire);
			villeNum1.setStyle("-fx-background-color : white;");
			villeNum2.setStyle("-fx-background-color : white;");
		}
		else {
			//Si il est nul on color en rouge pour montrer qu'il y a une erreur.
			villeNum1.setStyle("-fx-background-color : red;");
			villeNum2.setStyle("-fx-background-color : red;");
		}
	}
	
	/**
	 * Ces trois fonctions permettent de dessiner sur la carte et de visualiser directement le nombre de morts, guerris et cas actifs.
	 * @see MapView
	 */
	
	@FXML
	private void mortAction() {
		carte.dessinerCercle(RepresentationSurCarte.cercleMortsVille(), new Color(Color.RED.getRed(),Color.RED.getGreen(),Color.RED.getBlue(),80));
	}
	
	@FXML
	private void guerriAction() {
		carte.dessinerCercle(RepresentationSurCarte.cercleGuerisVille(), new Color(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue(),80));
	}
	
	@FXML
	private void actifAction() {
		carte.dessinerCercle(RepresentationSurCarte.cercleActifsVille(), new Color(Color.ORANGE.getRed(),Color.ORANGE.getGreen(),Color.ORANGE.getBlue(),80));
	}
	
	/**
	 * Ici on a les différents bouttons et leur lien au modèle pour récupérer les données qui serviront à la construction des graphiques.
	 * Le code est un peu répétitif mais permet de ne pas mélanger la vue du controller
	 * @see DataGraphes
	 */
	
	@FXML
	private void modifGraphGuadeloupe() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Guadeloupe"), DataGraphes.reanimesRegion("Guadeloupe"), DataGraphes.mortsRegion("Guadeloupe"), DataGraphes.hospitalisesRegion("Guadeloupe"));
		camembert.setData(DataGraphes.statsQuotidiennes("Guadeloupe"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphMartinique() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Martinique"), DataGraphes.reanimesRegion("Martinique"), DataGraphes.mortsRegion("Martinique"), DataGraphes.hospitalisesRegion("Martinique"));
		camembert.setData(DataGraphes.statsQuotidiennes("Martinique"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphGuyane() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Guyane"), DataGraphes.reanimesRegion("Guyane"), DataGraphes.mortsRegion("Guyane"), DataGraphes.hospitalisesRegion("Guyane"));
		camembert.setData(DataGraphes.statsQuotidiennes("Guyane"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphRéunion() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("La Réunion"), DataGraphes.reanimesRegion("La Réunion"), DataGraphes.mortsRegion("La Réunion"), DataGraphes.hospitalisesRegion("La Réunion"));
		camembert.setData(DataGraphes.statsQuotidiennes("La Réunion"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphMayotte() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Mayotte"), DataGraphes.reanimesRegion("Mayotte"), DataGraphes.mortsRegion("Mayotte"), DataGraphes.hospitalisesRegion("Mayotte"));
		camembert.setData(DataGraphes.statsQuotidiennes("Mayotte"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphIleFrance() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Île-de-France"), DataGraphes.reanimesRegion("Île-de-France"), DataGraphes.mortsRegion("Île-de-France"), DataGraphes.hospitalisesRegion("Île-de-France"));
		camembert.setData(DataGraphes.statsQuotidiennes("Île-de-France"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphCentre() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Centre-Val de Loire"), DataGraphes.reanimesRegion("Centre-Val de Loire"), DataGraphes.mortsRegion("Centre-Val de Loire"), DataGraphes.hospitalisesRegion("Centre-Val de Loire"));
		camembert.setData(DataGraphes.statsQuotidiennes("Centre-Val de Loire"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphBourgogne() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Bourgogne-Franche-Comté"), DataGraphes.reanimesRegion("Bourgogne-Franche-Comté"), DataGraphes.mortsRegion("Bourgogne-Franche-Comté"), DataGraphes.hospitalisesRegion("Bourgogne-Franche-Comté"));
		camembert.setData(DataGraphes.statsQuotidiennes("Bourgogne-Franche-Comté"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphNormandie() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Normandie"), DataGraphes.reanimesRegion("Normandie"), DataGraphes.mortsRegion("Normandie"), DataGraphes.hospitalisesRegion("Normandie"));
		camembert.setData(DataGraphes.statsQuotidiennes("Normandie"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphHaut() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Hauts-de-France"), DataGraphes.reanimesRegion("Hauts-de-France"), DataGraphes.mortsRegion("Hauts-de-France"), DataGraphes.hospitalisesRegion("Hauts-de-France"));
		camembert.setData(DataGraphes.statsQuotidiennes("Hauts-de-France"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphGrandEst() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Grand Est"), DataGraphes.reanimesRegion("Grand Est"), DataGraphes.mortsRegion("Grand Est"), DataGraphes.hospitalisesRegion("Grand Est"));
		camembert.setData(DataGraphes.statsQuotidiennes("Grand Est"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphPaysLoire() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Pays de la Loire"), DataGraphes.reanimesRegion("Pays de la Loire"), DataGraphes.mortsRegion("Pays de la Loire"), DataGraphes.hospitalisesRegion("Pays de la Loire"));
		camembert.setData(DataGraphes.statsQuotidiennes("Pays de la Loire"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphBretagne() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Bretagne"), DataGraphes.reanimesRegion("Bretagne"), DataGraphes.mortsRegion("Bretagne"), DataGraphes.hospitalisesRegion("Bretagne"));
		camembert.setData(DataGraphes.statsQuotidiennes("Bretagne"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphAquitaine() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Nouvelle Aquitaine"), DataGraphes.reanimesRegion("Nouvelle Aquitaine"), DataGraphes.mortsRegion("Nouvelle Aquitaine"), DataGraphes.hospitalisesRegion("Nouvelle Aquitaine"));
		camembert.setData(DataGraphes.statsQuotidiennes("Nouvelle Aquitaine"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphOccitanie() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Occitanie"), DataGraphes.reanimesRegion("Occitanie"), DataGraphes.mortsRegion("Occitanie"), DataGraphes.hospitalisesRegion("Occitanie"));
		camembert.setData(DataGraphes.statsQuotidiennes("Occitanie"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphAuvergne() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Auvergne-Rhône-Alpes"), DataGraphes.reanimesRegion("Auvergne-Rhône-Alpes"), DataGraphes.mortsRegion("Auvergne-Rhône-Alpes"), DataGraphes.hospitalisesRegion("Auvergne-Rhône-Alpes"));
		camembert.setData(DataGraphes.statsQuotidiennes("Auvergne-Rhône-Alpes"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphProvence() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Provence-Alpes-Côte d'Azur"), DataGraphes.reanimesRegion("Provence-Alpes-Côte d'Azur"), DataGraphes.mortsRegion("Provence-Alpes-Côte d'Azur"), DataGraphes.hospitalisesRegion("Provence-Alpes-Côte d'Azure"));
		camembert.setData(DataGraphes.statsQuotidiennes("Provence-Alpes-Côte d'Azur"));
		labelRegion.setText("France");
	}
	
	@FXML
	private void modifGraphCorse() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Corse"), DataGraphes.reanimesRegion("Corse"), DataGraphes.mortsRegion("Corse"), DataGraphes.hospitalisesRegion("Corse"));
		camembert.setData(DataGraphes.statsQuotidiennes("Corse"));
		labelRegion.setText("France");
	}
	

	@FXML
	private void modifGraphCollectivites() {
		lineChart.getData().clear();
		lineChart.getData().addAll(DataGraphes.guerisRegion("Collectivités d'Outre-Mer"), DataGraphes.reanimesRegion("Collectivités d'Outre-Mer"), DataGraphes.mortsRegion("Collectivités d'Outre-Mer"), DataGraphes.hospitalisesRegion("Collectivités d'Outre-Mer"));
		camembert.setData(DataGraphes.statsQuotidiennes("Collectivités d'Outre-Mer"));
		labelRegion.setText("France");
	}
	
	/**
	 * Permet ici d'avoir le rayon de 100 km autour d'une ville souhaité.
	 * @see MapView 
	 */
	
	@FXML
	private void validerRayon() {
		Coordonnees coordonnee = new Coordonnees(text100.getText());
		if(coordonnee!= null)
			carte.dessinerRayon(coordonnee.getPos());
	}
	
	/**
	 * Cette fonction va permettre de passer d'une carte de type classique à une carte satellite.
	 * @see MapView
	 */
	
    @FXML
    private void changerLaCarte() {
    	carte.changerMap();
    	if(changerMap.getText().equals(nomSatellite))
    		changerMap.setText(nomClassique);
    	else
    		changerMap.setText(nomSatellite);
    }

	public static Stage getWindow() {
		return window;
	}
	
	/**
	 * Cette fonction permet de revenir au menu de choix pour aller voir les études data.
	 */

	@FXML
	private void revenirEnArriere() {
		MenuChoixController.getWindow().close();
		SQLController.getFenetre().show();
	}
}
