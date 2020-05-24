package controll.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modele.trajectoire.Positionnement;

public class ChoixSeuilController implements Serializable, Initializable{
	
	private static final long serialVersionUID = 7484054558935679617L;
	
	private HashMap<String, Integer> monHashi;
	
	@FXML
	private transient TextField labelHospitalise;
	@FXML
	private transient TextField labelReanimation;
	@FXML
	private transient TextField labelMort;

	// Event Listener on Button.onMouseClicked
	@FXML
	public void validationSeuil(MouseEvent event) {
		
		monHashi = new HashMap<String, Integer>();
		
		if(!labelHospitalise.getText().equals(""))
			monHashi.put("hospitalises", Integer.parseInt(labelHospitalise.getText()));
		if(!labelReanimation.getText().equals(""))
			monHashi.put("reanimation", Integer.parseInt(labelReanimation.getText()));
		if(!labelMort.getText().equals(""))
			monHashi.put("morts", Integer.parseInt(labelMort.getText()));
		this.sauvegarder();
		Positionnement.setSeuils(monHashi);
		PrincipalController.getWindow().close();
	}
	
	/**
	 *La méthode permet de serializer un objet via un flux de sortie initialisé.
	 **/
	
	public void sauvegarder() {
		
		/**
		 *Flux de sortie
		 **/
		
		ObjectOutputStream oos = null;
		
		/**
		 *Sérialization en utilisant un "try catch" afin d'éviter les erreurs.
		 **/
	    try {
	    	/**
	    	 *Déclaration du fichier d'écriture
	    	 **/
	    	
	        final FileOutputStream fichier = new FileOutputStream("Sauvegardes/seuil.sauv");
	        
	        
	        oos = new ObjectOutputStream(fichier);
	        
	        /**
	         *Ecriture de l'objet à serializer
	         **/
	        
	        oos.writeObject(this);
	        
	        /**Force l'écriture dans le fichier pour libérer le contenant
	         **/
	        
	        oos.flush();
	        
	        /**Gestion des problèmes liés à l'écriture dans le fichier
	         **/
	        
	      } catch (final IOException e) {
	        e.printStackTrace();
	      } finally {
	        try {
	          if (oos != null) {
	            oos.flush();
	            
	            /**Fermeture du flux s'il n'est pas vide
	             **/
	            
	            oos.close();
	          }
	        } catch (final IOException ex) {
	          ex.printStackTrace();
	        }
	      }
	}
	
	/**
	 *Utilisation de la serialization pour charger l'objet et le mettre à jour selon le procédé suivant :
	 *<ul>
	 *<li>Initialisation de la lecture d'une personne déjà inscrite</li>
	 *<li>Indexation du fichier de lecture</li>
	 *<li>Lecture du flux et affichage de la confirmation du chargement</li>
	 *<li>Fermeture du flux</li>
	 *@return Personne inscrite dans le fichier
	 *</ul>
	 **/
	
	public ChoixSeuilController chargerObjet() {
	    ObjectInputStream ois = null;
	    ChoixSeuilController choixSeuilController = null;
	    
	    try {
	        final FileInputStream fichier = new FileInputStream("Sauvegardes/seuil.sauv");
	        ois = new ObjectInputStream(fichier);
	        choixSeuilController = (ChoixSeuilController) ois.readObject();
	        System.out.println("Fichier Charge");
	      } catch (final java.io.IOException e) {
	        e.printStackTrace();
	      } catch (final ClassNotFoundException e) {
	        e.printStackTrace();
	      } finally {
	        try {
	          if (ois != null) {
	            ois.close();
	          }
	        } catch (final IOException ex) {
	        	
	          ex.printStackTrace();
	        }
	      }
	    
	    return choixSeuilController;
	}
	
	public HashMap<String, Integer> getMonHashi() {
		return monHashi;
	}

	public void setMonHashi(HashMap<String, Integer> monHashi) {
		this.monHashi = monHashi;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File f = new File("Sauvegardes/seuil.sauv");
		if (f.exists())
			monHashi = chargerObjet().monHashi;
		Positionnement.setSeuils(monHashi);
	}
	
}
