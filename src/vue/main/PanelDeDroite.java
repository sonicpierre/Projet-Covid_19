package vue.main;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vue.graph.EvolutionMortsJours;
import vue.graph.MortGuerriHospitalise;

public class PanelDeDroite extends VBox{

	private static PanelDeDroite instance;
	
	LineChart<String, Number> graphiqueLigne;
	PieChart camenbert;

	private PanelDeDroite() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		
		/**
		 * Test intéractions !!&
		 */
		
		Button monBoutton = new Button("Evolution");
		
		graphiqueLigne = new EvolutionMortsJours().getMonGraph();
		camenbert = new MortGuerriHospitalise();
		
		List<Data> maListeSauv = new ArrayList<Data>();
		
		
		HBox bouttonTest = new HBox(10);
		Button supprimer = new Button("Supprimer");
		supprimer.setOnAction(e->{
			if((camenbert.getData().size()-1)!=0) {
				maListeSauv.add(camenbert.getData().get(camenbert.getData().size()-1));
				camenbert.getData().remove(camenbert.getData().size()-1);
			}
		});
		
		Button ajouter = new Button("Ajouter");
		ajouter.setOnAction(e->{
			if(!maListeSauv.isEmpty()) {
				camenbert.getData().add(maListeSauv.get(0));
				maListeSauv.remove(0);
			}
		});
		
		Button modifButton = new Button("Modifier");
		modifButton.setOnAction(e->{
			
	        ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	                new PieChart.Data("Grapefruit", 10),
	                new PieChart.Data("Oranges", 10),
	                new PieChart.Data("Plums", 25),
	                new PieChart.Data("Pears", 13),
	                new PieChart.Data("Apples", 11));
	        
	        changerLesDonneesPieChart(pieChartData);
	        
			});
		
		bouttonTest.getChildren().addAll(ajouter, supprimer, modifButton);
		bouttonTest.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(graphiqueLigne, camenbert, bouttonTest);
		
	}
	
	public void changerLesDonneesPieChart(ObservableList<PieChart.Data> nouvelleListeDeDonnees) {
		camenbert.getData().removeAll(camenbert.getData());
		camenbert.setData(nouvelleListeDeDonnees);
		
	}
	
	public static PanelDeDroite getInstance() {
		if (instance == null)
			instance = new PanelDeDroite();
		return instance;
	}
	
}
