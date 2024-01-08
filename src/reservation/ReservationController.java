package reservation;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;

public class ReservationController implements Initializable, Receivable {

	@FXML
	private Button btnSc;
	@FXML
	private VBox btnBox;
	@FXML
	private TableView<SeatVO> tableView;
	@FXML
	private TextField selectS;
	@FXML
	private MenuButton selectDate;
	@FXML
	private ComboBox<String> selectTime;

	public static ObservableList<SeatVO> list;
	ObservableList<String> ttime; // TableView

	public static SeatVO red;

	public String reservSeat; // 선택된 좌석

	// 선택된 버튼
	public Button selectedButton;
	public String btnStyle;
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection conn = null;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.thread.reservationController = this;

		// 예매하기 버튼 클릭시 창 띄우기		파일명 넣기 null 자리에 "pay.fxml"+fxml 파일에서 컨트롤러 확인
		btnSc.setOnAction((e) -> {
			if (selectS.equals(null) && selectDate.equals(null) && selectTime.equals(null))
				return;
			try {
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/pay/PayMain.fxml"));
				stage.setScene(new Scene(root));
				stage.setTitle("결제하기");
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});


// 날짜 선택
		for (MenuItem n : selectDate.getItems()) {
			n.setOnAction((e) -> {
				System.out.println(e);
				String date = n.getText();
				if (date == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("날짜를 선택해주세요");
					alert.show();
					return;
				}
				System.out.println(date);
			});
		}

// 시간 선택
		ttime = FXCollections.observableArrayList("14시30분", "19시30분");
		selectTime.setItems(ttime);

		selectTime.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(newValue);
			}
		});

		selectTime.setOnAction((e) -> {
			String time = selectTime.toString();
			if (time == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("시간을 선택해주세요");
				alert.show();
				return;
			}
		});
// 좌석 정보 노출
		ObservableList<SeatVO> list = FXCollections.observableArrayList();
		SeatVO vips = new SeatVO("VIP 좌석", "50석", "100000원");
		SeatVO order = new SeatVO("일반좌석", "50석", "30000원");
		list.add(vips);
		list.add(order);

		ObservableList<TableColumn<SeatVO, ?>> columnList = tableView.getColumns();
		TableColumn<SeatVO, ?> gradeColumn = columnList.get(0);
		TableColumn<SeatVO, ?> priceColumn = columnList.get(1);
		TableColumn<SeatVO, ?> charsColumn = columnList.get(2);
		gradeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		charsColumn.setCellValueFactory(new PropertyValueFactory<>("chars"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableView.setItems(list);

	} // end initialize

	
	/**
	 * 서버 수신 데이터
	 */
	@Override
	public void receiveData(String message) {
		// 예약 좌석 목록 
		setSeats();
		
		
		// 좌석정보 전달하여 중복체크하고 결제로 넘어가기		
		String chars[] = selectS.getText().split(" ");
		String seat = chars[2];
		for(int i = 1; i<11; i++) {
			/*
			if() {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("다른 자리를 선택해주세요.");
				alert.show();
				return;
			}
			*/
		}
		
	// 날짜 및 시간정보 받아오기
		
	} // end receiveData
	
	public void setSeats() {
		// 버튼 만들기
		for (int i = 1; i < 11; i++) {
			HBox hbox = new HBox();
			hbox.setPrefWidth(500);
			hbox.setSpacing(20);
			for (int j = 1; j < 11; j++) {
				int val = 64 + i;
				char value = (char) val;
				Button b = new Button(value + "|" + j + "");
				b.setMaxWidth(Double.MAX_VALUE);
				b.setStyle("-fx-border-color:black");
				HBox.setHgrow(b, Priority.ALWAYS);

				// 스타일 적용. 자리마다 다른 색
				if (i < 6) {
					b.setStyle("-fx-background-color:lightblue");
					VBox.setMargin(hbox, new Insets(0, 0, 10, 0));
				} else {
					b.setStyle("-fx-background-color:lightpink");
					VBox.setMargin(hbox, new Insets(10, 0, 10, 0));
				}

				hbox.getChildren().add(b);
				// 버튼 클릭 event
				b.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						reservSeat = b.getText();
						selectS.setText(reservSeat);
						String receiveData = reservSeat;
						if (selectedButton != null && btnStyle != null) {
							selectedButton.setStyle(btnStyle);
						}
						selectedButton = b;
						btnStyle = b.getStyle();
						b.setStyle("-fx-background-color:gray");
						b.setDefaultButton(false);
						String[] ticket = receiveData.split("\\|");
						String code = ticket[0];

						if (code.equals("A") || code.equals("B") || code.equals("C") || code.equals("D") || code.equals("E")) {
							System.out.println("10만원");
							selectS.setText("VIP 좌석      " + reservSeat + "      10만원");
						} else {
							selectS.setText("일반좌석       " + reservSeat + "      3만원");
							System.out.println("3만원");
						}
					}
				});
			}
			btnBox.getChildren().add(hbox);
		}
	}
	
}
