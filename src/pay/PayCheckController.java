package pay;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
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

public class PayCheckController implements Initializable, Receivable {

	@FXML
	TextField name, date, musical, seat;
	@FXML
	Button btnEnd, btnCancel;

	Connection conn = null;

	// PayController Stage
	private Stage payStage;
	// payDoneController Stage
	private Stage payDoneStage;
	// 좌석 선택 스테이지
	private Stage reservStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.payCheckController = this;

		// 텍스트필드에 데이터 값 입력
		name.setText(Main.reservTicket.getUserID());
		seat.setText(Main.reservTicket.getSeatNum());
		musical.setText(Main.reservTicket.getMusical());
		date.setText(Main.reservTicket.getDate() + " / " + (Main.reservTicket.getTime()));

		// 텍스트필드 수정 불가능
		name.setEditable(false);
		seat.setEditable(false);
		musical.setEditable(false);
		date.setEditable(false);

		// 확인 버튼을 누르면 메인 화면만 남고 나머지는 없어짐
		btnEnd.setOnAction((e) -> {
			Stage end = (Stage) btnEnd.getScene().getWindow();
			end.close();
			if (payStage != null)
				payStage.close();
			if (payDoneStage != null)
				payDoneStage.close();
			if (reservStage != null)
				reservStage.close();
		});

		// 예매 취소 버튼을 누르면 알림창이 뜨면서 정말 취소 할 것이냐고 물어봄
		btnCancel.setOnAction((e) -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("예매 취소");
			alert.setHeaderText("정말 예매를 취소하시겠습니까?");
			alert.setContentText("이전에 예매하신 내역은 모두 삭제됩니다.\n저희 열심히 준비했는데 ㅠㅠ");
			Optional<ButtonType> result = alert.showAndWait();

			// 예매 취소를 진행 할 경우 알림이 뜨며 예매 취소가 됨.
			if (result.get() == ButtonType.OK) {
				System.out.println("예매 취소");
				Alert alert1 = new Alert(Alert.AlertType.WARNING);
				alert1.setTitle("예매 취소 확인");
				alert1.setHeaderText("예매가 취소되었습니다.");
				alert1.setContentText("다음에 다시 만나요 ㅠㅠ");
				Optional<ButtonType> result1 = alert1.showAndWait();
				if (result1.get() == ButtonType.OK) {
					// 예약 취소 화면에서 예매했던 구매자의 데이터를 보냄
					// 데이터를 보낸 후 데이터 삭제
					TicketVO vo = Main.reservTicket;
					String regReserv = "2|2|" + vo.getUserID() + "," + vo.getMusical() + "," + vo.getSeatNum() + ","
							+ vo.getPay() + "," + vo.getDate() + "," + vo.getTime();
					System.out.println(regReserv);
					Main.thread.sendData(regReserv);
				}
			} else {
				System.out.println("예매 취소를 취소");
				// 아무 일 없음
			}
		});

	}

	@Override
	public void receiveData(String message) {
		// 2|2|true or 2|2|false
		boolean isDeleted = Boolean.parseBoolean(message.split("\\|")[2]);
		if (isDeleted) {
			Platform.runLater(() -> {
				// 예매 취소 완료
				// 데이터를 보낸 후 메인 화면 제외 모든 창이 꺼짐
				Stage now = (Stage) btnCancel.getScene().getWindow();
				now.close();
				if (payStage != null)
					payStage.close();
				if (payDoneStage != null)
					payDoneStage.close();
				if (reservStage != null)
					reservStage.close();
			});
		} else {
			// 혹시나 예매취소 실패 시 기능 추가...
		}
	}

	public void setStage(Stage payStage, Stage payDoneStage, Stage reservStage) {
		this.payStage = payStage;
		this.payDoneStage = payDoneStage;
		this.reservStage = reservStage;
	}

}
