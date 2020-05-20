package modele.trajectoire;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jxmapviewer.viewer.GeoPosition;

// permet de récupérer les coordonnées de villes 
public class Positionnement {
	// donne les coordonnées GPS des 2 villes à partir de leurs noms
	public List<GeoPosition> positionner2Villes(String ville1, String ville2) {
		List<String> villes = new ArrayList<String>();
		villes.add(ville1);
		villes.add(ville2);
		return positionnerVilles(villes);
	}
	
	// donne les coordonnées GPS des n villes de la liste (à partir de leurs noms)
	public List<GeoPosition> positionnerVilles(List<String> villes) {
		ListIterator<String> it = villes.listIterator();
		List<GeoPosition> listeCoor = new ArrayList<GeoPosition>();
		while (it.hasNext()) {
			Coordonnees coor = new Coordonnees(it.next());
			GeoPosition pos = coor.getPos();
			listeCoor.add(pos);
		}
		return(listeCoor);
	}
}
