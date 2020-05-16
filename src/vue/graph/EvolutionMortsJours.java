package vue.graph;

import java.util.List;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class EvolutionMortsJours{

	LineChart<String, Number> monGraph;
	
	//Liste à donner transformée.
	
	List<XYChart.Data<String, Number>> maListeDeDonnees; 
	
	
	public EvolutionMortsJours() {
		//On définit les différents axes
		final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    //On leur donne un nom
	    xAxis.setLabel("Month");
	    yAxis.setLabel("Week");
	    //On crée le graphique associé
		this.monGraph = new LineChart<String,Number>(xAxis,yAxis);
		//On ajoute un titre au graphique
		monGraph.setTitle("Nombre de morts par Moi");
		monGraph.getData().add(donneeGraph());
	}
	
	public Series<String, Number> donneeGraph() {
		
	    Series<String, Number> series = new XYChart.Series<String, Number>();
	    series.setName("Evolution des morts");
	    
	    //Sera remplacé par une un parcour de liste des données.
	    
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
	
	    return series;
	}

	public LineChart<String, Number> getMonGraph() {
		return monGraph;
	}

	public void setMonGraph(LineChart<String, Number> monGraph) {
		this.monGraph = monGraph;
	}

}
