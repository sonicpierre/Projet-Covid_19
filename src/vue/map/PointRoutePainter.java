package vue.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *La classe <b>PointRoutePainter</b> est la classe permettant de dessiner les points qui correspondent à chacunes des villes mais aussi les trais entre les villes.
 *@author VIRGAUX Pierre
 *@version 2.0
 **/

public class PointRoutePainter {

	/**
	 * On utilise ici une set liste car on ne veut pas qu'apparaisse 2 fois les mêmes villes.
	 */
	
	Set<Waypoint> wayPoints = new HashSet<Waypoint>();
	
	/**
	 * On dessine directement le chemin dans le constructeur.
	 * @param listeDesPositions représente l'ensemble des points à relier
	 * @param map ici il s'agit de la carte sur laquelle on dessine
	 */
	
	public PointRoutePainter(List<GeoPosition> listeDesPositions, JXMapViewer map) {
		for(GeoPosition mesPosition : listeDesPositions)
			wayPoints.add(new DefaultWaypoint(mesPosition));
		
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(wayPoints);
        

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(waypointPainter);
        painters.add(new RoutePainter(listeDesPositions, map));

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        
        //Permet de dessiner par dessus la carte et pas la recharger à chaque fois.
        map.setOverlayPainter(painter);
	}
}