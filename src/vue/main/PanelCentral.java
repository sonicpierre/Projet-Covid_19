package vue.main;

import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import javafx.embed.swing.SwingNode;
import vue.map.MapView;
import vue.map.PointRoutePainter;

public class PanelCentral extends SwingNode{

	private static PanelCentral instance;
	private MapView map;
	
	public PanelCentral() {
		this.map = new MapView();
		this.setContent(map);
		List<GeoPosition> monPosition = new ArrayList<GeoPosition>();
		monPosition.add(new GeoPosition(46.65, 2.56));
		monPosition.add(new GeoPosition(45.43, 4.4));
		monPosition.add(new GeoPosition(43.60, 1.43));
		monPosition.add(new GeoPosition(43.3, -0.36));
		PointRoutePainter monPointRoutePainter = new PointRoutePainter(monPosition, map);
	}
	
	
	public static PanelCentral getInstance() {
		if (instance == null)
			instance = new PanelCentral();
		return instance;
	}
	
}
