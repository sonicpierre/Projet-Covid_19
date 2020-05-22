package vue.map;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


@SuppressWarnings("serial")
public class MapView extends JXMapViewer{
	
	private static final TileFactoryInfo NORMAL = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
	private static final TileFactoryInfo SATELLITE = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
	
    public MapView() {
    	
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = NORMAL;
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);

        // Set the focus
        GeoPosition centre = new GeoPosition(46.65, 2.56);

        this.setZoom(13);
        this.setAddressLocation(centre);
        MouseController.MouseMapController(this);
    }
    
    public void changerMap() {
    	if(this.getTileFactory().getInfo() == NORMAL) {
    		this.setTileFactory(new DefaultTileFactory(SATELLITE));
    	}
    	else {
    		this.setTileFactory(new DefaultTileFactory(NORMAL));
    	}
    }
    
    public void dessinerItineraire(List<GeoPosition> listeDesVilles) {
    	PointRoutePainter monItineraire = new PointRoutePainter(listeDesVilles, this);
    }
    
    public void dessinerRayon(GeoPosition ville) {
         new KmPainter(ville,this);
    }
    
    public void dessinerCercle(HashMap<GeoPosition, Double> ensembleAffichage, Color couleur) {
    	new RegionPainter(ensembleAffichage, couleur, this);
    }
}
