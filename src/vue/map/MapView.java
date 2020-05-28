package vue.map;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *La classe <b>MapView</b> est la classe qui permet de gérer toutes les fonctionnalités liées à la carte.
 *On peut à partir de cette classe faire de nombreuses opérations :
 * <lu>
 * <li>On peut dessiner sur la map des cercles avec un rayon proportionnel au nombre de morts, hospitalisés, ou patient en rea</li>
 * <li>On peut dessiner un itinéraire en passant par chacune des préfectures</li>
 * <li>On peut changer le style de la carte</li>
 * <li>On peut dessiner un cercle de 100 km autour d'une ville</li>
 * </lu>
 * </p>
 *
 *@author VIRGAUX Pierre
 *@version 2.0
 **/


@SuppressWarnings("serial")
public class MapView extends JXMapViewer{
	
	
	
	/**
	 * Ici on a 2 types de carte, la carte satellite et la carte classique tirées d'OSM.
	 */
	
	private static final TileFactoryInfo NORMAL = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
	private static final TileFactoryInfo SATELLITE = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
	
	/**
	 * Ici il s'agit du constructeur qui donne les informations principales sur la carte.
	 * @see MouseController
	 */
	
    public MapView() {
    	
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = NORMAL;
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);

        // Set the focus
        GeoPosition centre = new GeoPosition(46.65, 2.56);

        //Set the zoom
        this.setZoom(13);
        this.setAddressLocation(centre);
        MouseController.MouseMapController(this);
    }
    
    /**
     * Cette fonction permet de passer d'un style de map à un autre
     */
    
    public void changerMap() {
    	if(this.getTileFactory().getInfo() == NORMAL) {
    		this.setTileFactory(new DefaultTileFactory(SATELLITE));
    	}
    	else {
    		this.setTileFactory(new DefaultTileFactory(NORMAL));
    	}
    }
    
    /**
     * Cette fonction permet de dessiner un itinéraire d'un point A à un point B.
     * @see PointRoutePainter
     * @param listeDesVilles On a la liste des points intermédiaires qui constituent le chemin.
     */
    
    public void dessinerItineraire(List<GeoPosition> listeDesVilles) {
    	new PointRoutePainter(listeDesVilles, this);
    }
    
    /**
     * Permet de dessiner le rayon autour d'une ville donnée
     * @see KmPainter
     * @param ville identifié par sa longitude et latitude
     */
    
    public void dessinerRayon(GeoPosition ville) {
         new KmPainter(ville,this);
    }
    
    /**
     * Permet de dessiner des cercles de taille différentes sur les villes.
     * @see RegionPainter
     * @param ensembleAffichage Associe à chaque ville un coefficient qui détermine le diamètre du cercle.
     * @param couleur la couleur du cercle voulue
     */
    
    public void dessinerCercle(HashMap<GeoPosition, Double> ensembleAffichage, Color couleur) {
    	new RegionPainter(ensembleAffichage, couleur, this);
    }
    
    public void signalerLesConfines(HashMap<GeoPosition, Integer> maListeConfinee, boolean confineActive, boolean presqueActive, boolean nonConfActive) {
        
        // Create waypoints from the geo-positions
        Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>();
        
    	Set<Entry<GeoPosition, Integer>> set = maListeConfinee.entrySet();
		Iterator<Entry<GeoPosition, Integer>> monIterateur = set.iterator();
		
		  while(monIterateur.hasNext()){
			Entry<GeoPosition, Integer> e = monIterateur.next();
			if((e.getValue() == 3)&& confineActive )
				waypoints.add(new MyWaypoint("C", Color.RED, e.getKey()));
			if((e.getValue() == 2)&& presqueActive)
				waypoints.add(new MyWaypoint("D", Color.ORANGE, e.getKey()));
			if((e.getValue() == 1) && nonConfActive)
				waypoints.add(new MyWaypoint("L", Color.GREEN, e.getKey()));
		  }
		  
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer());

        this.setOverlayPainter(waypointPainter);
	}
}
