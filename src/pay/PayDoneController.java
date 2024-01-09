package pay;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Receivable;

public class PayDoneController implements Initializable, Receivable  {

	@FXML private Label userID, price, date, musical, seat;
	@FXML private Button btnExit, btnCheck;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.payDoneController = this;
		
		
		// 결제 완료 창에서 예매 확인 버튼을 누르면 PayCheck 으로 넘어감
		btnCheck.setOnAction((e)->{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pay/PayCheck.fxml"));
			Parent root1;
			Stage stage;
			try {
				root1 = (Parent) fxmlLoader.load();
				stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL); // 팝업처럼 화면이 뜸
				stage.initStyle(StageStyle.UTILITY);		
				stage.setTitle("레미제라블 예매 확인");
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			};
		});
		
		
		// 결제 완료 창의 확인 버튼을 누르면 모든 창이 꺼짐
		btnExit.setOnAction((e)->{
//			System.exit(0);
			Platform.exit();
		});
	}

	@Override
	public void receiveData(String message) {
		
	}
	

}
