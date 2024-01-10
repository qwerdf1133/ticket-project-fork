package pay;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;
import reservation.TicketVO;

public class ReserveCheckController implements Initializable, Receivable {

	@FXML
	TextField musicalNa1, musicalNa2, musicalNa3, seat1, seat2, seat3, date1, date2, date3, result1, result2, result3;
	@FXML
	Button btnCk;

	Connection conn = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.ReserveCheckController = this;

		// 텍스트필드에 데이터 값 입력
		musicalNa1.setText(Main.reservTicket.getMusical());
		seat1.setText(Main.reservTicket.getSeatNum());
		date1.setText(Main.reservTicket.getDate() + " / " + (Main.reservTicket.getTime()));
		result1.setText(Main.reservTicket.getUserID());

		// 텍스트필드 수정 불가능
		musicalNa1.setEditable(false);
		seat1.setEditable(false);
		date1.setEditable(false);
		result1.setEditable(false);

		musicalNa2.setEditable(false);
		seat2.setEditable(false);
		date2.setEditable(false);
		result2.setEditable(false);
		
		musicalNa3.setEditable(false);
		seat3.setEditable(false);
		date3.setEditable(false);
		result3.setEditable(false);
		
		
		
		// 예매 확인창 꺼짐 
		btnCk.setOnAction((e) -> {

			Stage now = (Stage) btnCk.getScene().getWindow();
			now.close();

		});
		
	}

	// 방법을 모름
	@Override
	public void receiveData(String message) {

	}

}
