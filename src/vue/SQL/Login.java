package vue.SQL;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Login extends Tab{
	
	public Login() {
		this.setText("Se connecter");
		this.setClosable(false);
		
		GridPane panGrille = new GridPane();
		panGrille.setPadding(new Insets(20));
        panGrille.setHgap(25);
        panGrille.setVgap(15);
        
		Label login = new Label("Login");
		TextField entreLogin = new TextField();
		entreLogin.snapSizeX(200);
		entreLogin.setPromptText("Login");
		
		Label motDePasse = new Label("Mot de passe");
		PasswordField entrePassword = new PasswordField();
		entrePassword.setPromptText("Mot de passe");
		entrePassword.snapSizeX(200);
		
		Button connection = new Button("Valider");
		//Mettre la vérife BDD ici
		connection.setOnAction(e -> FenetreDepart.getFenetre().close());
		
		
		Button quitter = new Button("Quitter");
		quitter.setOnAction(e->System.exit(0));
		
		panGrille.add(login, 1, 1);
		panGrille.add(entreLogin, 2, 1);
		panGrille.add(motDePasse, 1, 2);
		panGrille.add(entrePassword, 2, 2);
		panGrille.add(connection, 1, 3);
		panGrille.add(quitter, 2, 3);
		
		this.setContent(panGrille);
	}
}
