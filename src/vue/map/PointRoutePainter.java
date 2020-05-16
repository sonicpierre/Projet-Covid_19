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

public class PointRoutePainter {

	Set<Waypoint> wayPoints = new HashSet<Waypoint>();
	
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
        map.setOverlayPainter(painter);
	}
}
