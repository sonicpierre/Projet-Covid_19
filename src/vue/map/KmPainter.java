package vue.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

public class KmPainter implements Painter<JXMapViewer>{

    private boolean antiAlias = true;
	
	private GeoPosition ville;
	private JXMapViewer carte;
	
	public KmPainter(GeoPosition ville, JXMapViewer carte) {
		this.ville = ville;
		this.carte = carte;
   	 	List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(this);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        carte.setOverlayPainter(painter);
	}
	
	
	@Override
	public void paint(Graphics2D g, JXMapViewer map, int length, int width) {
        g = (Graphics2D) g.create();

        // convert from viewport to world bitmap
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        if (antiAlias)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // do the drawing
        g.setColor(new Color(Color.RED.getRed(),Color.RED.getGreen(),Color.RED.getBlue(),80));

        drawCircles(g, map, length);
        
        g.dispose();	
	}

    private void drawCircles(Graphics2D g, JXMapViewer map, int diametre)
    {
	    // convert geo-coordinate to world bitmap pixel
	    Point2D pt = map.getTileFactory().geoToPixel(ville, map.getZoom());
	    int lastX = (int) pt.getX();
	    int lastY = (int) pt.getY();
	    
	    //On calibre par rapport à une distance connue
	    GeoPosition maDeusiemeGeo = new GeoPosition(ville.getLatitude(), ville.getLongitude() + 1.68);
	    
	    Point2D pt2 = map.getTileFactory().geoToPixel(maDeusiemeGeo, map.getZoom());
	    int distance = (int) Math.sqrt(Math.pow((pt.getX() - pt2.getX()), 2) + Math.pow((pt.getY() - pt2.getY()), 2));
	    
	    g.fillOval(lastX - (distance/2), lastY - (distance/2), distance, distance);
    }
}
