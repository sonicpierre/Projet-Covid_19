package controll.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import org.jxmapviewer.viewer.GeoPosition;

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
import javafx.scene.layout.StackPane;
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
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		SwingNode swingNode = new SwingNode();
		
		Series<String, Number> series = new XYChart.Series<String, Number>();
		
	    series.getData().add(new XYChart.Data<String, Number>("Jan", 23));
	    series.getData().add(new XYChart.Data<String, Number>("Feb", 14));
	    series.getData().add(new XYChart.Data<String, Number>("Mar", 15));
	    series.getData().add(new XYChart.Data<String, Number>("Apr", 24));
	    series.getData().add(new XYChart.Data<String, Number>("May", 34));
	    series.getData().add(new XYChart.Data<String, Number>("Jun", 36));
	    series.getData().add(new XYChart.Data<String, Number>("Jul", 22));
	    series.getData().add(new XYChart.Data<String, Number>("Aug", 45));
	    series.getData().add(new XYChart.Data<String, Number>("Sep", 43));
	    series.getData().add(new XYChart.Data<String, Number>("Oct", 17));
	    series.getData().add(new XYChart.Data<String, Number>("Nov", 29));
	    series.getData().add(new XYChart.Data<String, Number>("Dec", 25));
	    
	    lineChart.getData().add(series);
	    
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
		
		/**
		 * Fonction Roxane à implémenter
		 */
		
		List<GeoPosition> listeDePoints = new ArrayList<GeoPosition>();
		
		listeDePoints.add(new GeoPosition(50.63, 3.06));
		listeDePoints.add(new GeoPosition(45.75, 4.85));
		listeDePoints.add(new GeoPosition(45.78, 3.08));
		
		carte.dessinerItineraire(listeDePoints);
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
