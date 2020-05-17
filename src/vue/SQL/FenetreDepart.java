package vue.SQL;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vue.main.PanelCentral;

public class FenetreDepart{
	
	private static FenetreDepart instance;

	private static Stage fenetre;
	
	public static void FenetreDepartAffichage() {
		fenetre = new Stage(StageStyle.UNDECORATED);
		
		fenetre.setTitle("Connexion à MYSQL");
		fenetre.initModality(Modality.APPLICATION_MODAL);			//Rend insencible ce qu'il y a derrière la fenêtre
		
		TabPane panelOranisation = new TabPane(new Login());
		
		Scene maScene = new Scene(panelOranisation, 350, 230);
		
		fenetre.setScene(maScene);
		fenetre.setOnCloseRequest(e->{
			fenetre.close();
		});
		fenetre.show();
	}
	
	
	
	public static Stage getFenetre() {
		return fenetre;
	}

	public static void setFenetre(Stage fenetre) {
		FenetreDepart.fenetre = fenetre;
	}



	public static FenetreDepart getInstance() {
		if (instance == null)
			instance = new FenetreDepart();
		return instance;
	}
	
}
