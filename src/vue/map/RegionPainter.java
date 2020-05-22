package vue.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

public class RegionPainter implements Painter<JXMapViewer>{
	
    private boolean antiAlias = true;

    private HashMap<GeoPosition, Double> region;

    private Color couleur;
    
    private JXMapViewer carte;
    
    /**
     * @param track the track
     */
    public RegionPainter(HashMap<GeoPosition, Double> region, Color couleur, JXMapViewer carte)
    {
        // copy the list so that changes in the 
        // original list do not have an effect here
        this.region = region;
        this.couleur = couleur;
        this.setCarte(carte);
        
   	 	List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(this);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        carte.setOverlayPainter(painter);
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int largeur, int hauteur)
    {
        g = (Graphics2D) g.create();

        // convert from viewport to world bitmap
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        if (antiAlias)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // do the drawing
        g.setColor(couleur);

        drawCircles(g, map, largeur);
        
        g.dispose();
    }

    
    private void drawCircles(Graphics2D g, JXMapViewer map, int diametre)
    {
		Set<Entry<GeoPosition, Double>> set = region.entrySet();
		Iterator<Entry<GeoPosition, Double>> monIterateur = set.iterator();
		int lastX;
		int lastY;
		
		  while(monIterateur.hasNext()){
			Entry<GeoPosition, Double> e = monIterateur.next();
			// convert geo-coordinate to world bitmap pixel
			Point2D pt = map.getTileFactory().geoToPixel(e.getKey(), map.getZoom());
			
			lastX = (int) pt.getX();
			lastY = (int) pt.getY();
			
		    //On calibre par rapport à une distance connue
		    GeoPosition maDeusiemeGeo = new GeoPosition(e.getKey().getLatitude(), e.getKey().getLongitude() + 2.2);
		    
		    Point2D pt2 = map.getTileFactory().geoToPixel(maDeusiemeGeo, map.getZoom());
		    int distance = (int) Math.sqrt(Math.pow((pt.getX() - pt2.getX()), 2) + Math.pow((pt.getY() - pt2.getY()), 2));
			distance = (int) (distance * e.getValue());
		    
			g.fillOval(lastX - (distance / 2), lastY - (distance / 2), distance, distance);       		
    	 }
     }

	public JXMapViewer getCarte() {
		return carte;
	}

	public void setCarte(JXMapViewer carte) {
		this.carte = carte;
	}
}
