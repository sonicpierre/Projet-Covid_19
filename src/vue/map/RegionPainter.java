package vue.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *La classe <b>RegionPainter</b> est la classe permettant de dessiner sur chacunes des villes pour représenter le nombre de mort, réanimation, hospitalisé.
 *@author VIRGAUX Pierre
 *@version 2.0
 **/

public class RegionPainter implements Painter<JXMapViewer>{
	
    private boolean antiAlias = true;

    //Ici on a la liste des villes identifiée par longitude et latitude associées avec un coefficient qui permet de d'avoir des cercles adaptées à chacunes 
    
    private HashMap<GeoPosition, Double> villes;

    //On a choisi de mettre différentes couleurs en fonction de ce qu'on représente, rouge -> mort, orange -> actif, vert -> guerri
    
    private final Color couleur;
    
    private final JXMapViewer carte;
    
    /**
     * On crée l'objet et on en profite pour dessiner directement sur la carte
     * 
     * @param villes prends en compte les différentes villes sur lesquelles on va dessiner les cercles.
     * @param couleur définie la couleur des cercles voulus
     * @param carte
     */
    public RegionPainter(HashMap<GeoPosition, Double> villes, Color couleur, JXMapViewer carte)
    {
        this.villes = villes;
        this.couleur = couleur;
        this.carte = carte;
        
        
        MyWaypoint monPoint = new MyWaypoint("C", Color.RED, new GeoPosition(46.65, 2.56));
        
       
        
        
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

        drawCircles(g, map);
        
        g.dispose();
    }

    /**
     * On dessine sur chacunes des cartes les cercles correspondants à l'état de la maladie
     * 
     * @param g objet graphique qui nous donne la possibilité de dessiner
     * @param map carte sur laquelle on dessine
     */
    
    private void drawCircles(Graphics2D g, JXMapViewer map)
    {
    	// Ce système permet d'itérer la Hash Map
    	
		Set<Entry<GeoPosition, Double>> set = villes.entrySet();
		Iterator<Entry<GeoPosition, Double>> monIterateur = set.iterator();
		int lastX;
		int lastY;
		
		  while(monIterateur.hasNext()){
			Entry<GeoPosition, Double> e = monIterateur.next();
			// convert geo-coordinate to world bitmap pixel
			Point2D pt = map.getTileFactory().geoToPixel(e.getKey(), map.getZoom());
			
			lastX = (int) pt.getX();
			lastY = (int) pt.getY();
			
		    //On calibre par rapport à une distance connue qui correspondra au diamètre maximum que peut avoir un cercle
		    GeoPosition maDeusiemeGeo = new GeoPosition(e.getKey().getLatitude(), e.getKey().getLongitude() + 2.2);
		    
		    Point2D pt2 = map.getTileFactory().geoToPixel(maDeusiemeGeo, map.getZoom());
		    int distance = (int) Math.sqrt(Math.pow((pt.getX() - pt2.getX()), 2) + Math.pow((pt.getY() - pt2.getY()), 2));
			distance = (int) (distance * e.getValue());
		    
			//On dessine en pensant bien à recentrer le cercle
			g.fillOval(lastX - (distance / 2), lastY - (distance / 2), distance, distance);       		
    	 }
     }
    
    /**
     * Permet de récupérer la carte.
     * @return carte
     */

	public JXMapViewer getCarte() {
		return carte;
	}

}
