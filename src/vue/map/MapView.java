package vue.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import controll.mouse.MouseController;


@SuppressWarnings("serial")
public class MapView extends JXMapViewer{

    public MapView() {

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
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
}
