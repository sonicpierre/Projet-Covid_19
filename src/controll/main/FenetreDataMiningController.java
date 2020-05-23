package controll.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class FenetreDataMiningController implements Initializable{
	

	@FXML
	private WebView visionEtudes;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file = new File("Data_Viz_survie.html");
		URL url;
		try {
			url = file.toURI().toURL();
			visionEtudes.getEngine().load(url.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
