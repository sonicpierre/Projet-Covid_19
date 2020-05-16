package controll.mouse;

import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

import vue.map.SelectionAdapter;
import vue.map.SelectionPainter;


public class MouseController{

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
