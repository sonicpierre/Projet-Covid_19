package vue.main;

import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vue.map.MapView;
import vue.map.PointRoutePainter;

public class PanelCentral extends StackPane{

	private static final String NOMNUMEROUN = "Satellite";
	private static final String NOMNUMERODEUX = "Classique";
	
	private static PanelCentral instance;
	private SwingNode paneMap;
	private final Button bouttonSatellite;
	
	public PanelCentral() {
		MapView map = new MapView();
		paneMap = new SwingNode();
		paneMap.setContent(map);
		List<GeoPosition> monPosition = new ArrayList<GeoPosition>();
		monPosition.add(new GeoPosition(46.65, 2.56));
		monPosition.add(new GeoPosition(45.43, 4.4));
		monPosition.add(new GeoPosition(43.60, 1.43));
		monPosition.add(new GeoPosition(43.3, -0.36));
		PointRoutePainter monPointRoutePainter = new PointRoutePainter(monPosition, map);
		bouttonSatellite = new Button(NOMNUMEROUN);
		bouttonSatellite.setStyle("-fx-padding : 10;");
		
		bouttonSatellite.setOnAction(e->{
			changerNom();
			map.changerMap();
		});
		
		this.getChildren().addAll(paneMap, bouttonSatellite);
		StackPane.setAlignment(bouttonSatellite, Pos.TOP_LEFT);
	}
	
	private void changerNom() {
		if(bouttonSatellite.getText().equals("Satellite")) {
			bouttonSatellite.setText(NOMNUMERODEUX);
		} else {
			bouttonSatellite.setText(NOMNUMEROUN);
		}
	}
	


	public static PanelCentral getInstance() {
		if (instance == null)
			instance = new PanelCentral();
		return instance;
	}
	
}
