package pay;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;

public class PayDoneController implements Initializable, Receivable {

	@FXML private Label lbldata;
	@FXML private Button btnExit;
	@FXML private AnchorPane scenePane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.payController = this;
		
		// 결제 완료 창의 확인 버튼을 누르면 모든 창이 꺼짐
		btnExit.setOnAction((e)->{
//			System.exit(0);
// 			Platform.exit();
			// 현재 창 닫기
			Stage stage = (Stage)btnExit.getScene().getWindow();
			stage.close();
		});
	}

	
	/**
	 * Server receive data
	 */
	@Override
	public void receiveData(String message) {
		// TODO receive server Data PayDone answer
		
	}
	

}
