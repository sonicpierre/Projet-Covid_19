package vue.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

public class RegionPainter implements Painter<JXMapViewer>{
	
    private boolean antiAlias = true;

    private List<GeoPosition> region;

    /**
     * @param track the track
     */
    public RegionPainter(List<GeoPosition> region)
    {
        // copy the list so that changes in the 
        // original list do not have an effect here
        this.region = new ArrayList<GeoPosition>(region);
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
        g.setColor(new Color(Color.RED.getRed(),Color.RED.getGreen(),Color.RED.getBlue(),80));

        drawCircles(g, map, largeur);
        
        g.dispose();
    }

    
    private void drawCircles(Graphics2D g, JXMapViewer map, int diametre)
    {
        int lastX = 0;
        int lastY = 0;

        for (GeoPosition gp : region)
        {
            // convert geo-coordinate to world bitmap pixel
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
            
            g.drawLine(lastX, lastY, diametre, diametre); 
            
            lastX = (int) pt.getX();
            lastY = (int) pt.getY();
        }
    }
}
