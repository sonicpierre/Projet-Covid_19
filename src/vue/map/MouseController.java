package vue.map;

import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

/**
 *La classe <b>MouseController</b> permet de gérer toutes les intéractions de l'utilisateur avec l'interface.
 *@author VIRGAUX Pierre
 *@version 2.0
 **/

public class MouseController{

	/**
	 * Lie la mape au mouse controller.
	 * @param map
	 */
	public static void MouseMapController(JXMapViewer map) {
	     // Add interactions
        MouseInputListener mia = new PanMouseInputListener(map);
        map.addMouseListener(mia);
        map.addMouseMotionListener(mia);

        map.addMouseListener(new CenterMapListener(map));

        map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map));

        map.addKeyListener(new PanKeyListener(map));

        // Add a selection painter
        SelectionAdapter sa = new SelectionAdapter(map);
        SelectionPainter sp = new SelectionPainter(sa);
        map.addMouseListener(sa);
        map.addMouseMotionListener(sa);
        map.setOverlayPainter(sp);
	}
}
