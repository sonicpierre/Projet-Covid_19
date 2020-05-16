package vue.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class MortGuerriHospitalise extends PieChart{

	public MortGuerriHospitalise() {
        this.setTitle("On kiffe les fruits");
        this.setData(data());
        this.setLabelLineLength(10);
        this.setLegendSide(Side.BOTTOM);
	}
	
	private ObservableList<PieChart.Data> data() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
        
        return pieChartData;
	}
}
