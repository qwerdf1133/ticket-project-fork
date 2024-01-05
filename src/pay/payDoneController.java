package pay;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class payDoneController implements Initializable {

	@FXML private Label lbldata;
	@FXML private Button btnExit;
	@FXML private AnchorPane scenePane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// 결제 완료 창의 확인 버튼을 누르면 모든 창이 꺼짐
		btnExit.setOnAction((e)->{
//			System.exit(0);
			Platform.exit();
		});
	}
	

}
