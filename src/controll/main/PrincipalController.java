package controll.main;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import modele.BDD.DataGraphes;
import modele.trajectoire.Positionnement;
import vue.map.MapView;

public class PrincipalController implements Initializable{
	
	private MapView carte = new MapView(); 
	
	private static final String nomSatellite = "Satellite";
	private static final String nomClassique = "Classique";
	
	@FXML
	private LineChart<String, Number> lineChart;
	
	@FXML
	private PieChart camembert;

    @FXML
    private StackPane panePrincipal;
    
    @FXML
    private Button changerMap;
    
    @FXML
    private TextField villeNum1;
    
    @FXML
    private TextField villeNum2;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		SwingNode swingNode = new SwingNode();
	    
	    lineChart.getData().add(DataGraphes.hospitalisesDepartement());
	    
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
        
        camembert.setData(pieChartData);
        
	    SwingUtilities.invokeLater(()->swingNode.setContent(carte));
        panePrincipal.getChildren().add(swingNode);
	}
	
	@FXML
	private void validationItineraire() {
		Positionnement monPos = new Positionnement();
		
		carte.dessinerItineraire(monPos.positionner2Villes(villeNum1.getText(), villeNum2.getText()));
	}
	
	@FXML
	private void modifGraph() {
		
	}
	
    @FXML
    private void changerLaCarte() {
    	carte.changerMap();
    	if(changerMap.getText().equals(nomSatellite))
    		changerMap.setText(nomClassique);
    	else
    		changerMap.setText(nomSatellite);
    }

}
