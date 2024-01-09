package pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Receivable;
import reservation.TicketVO;

public class PayController implements Initializable, Receivable {

	Connection conn = null;
	Statement stmt = null;

	// 서버 소켓
	Socket server;
	InetAddress ip;
	int port;
	BufferedReader br;

	@FXML
	private ToggleGroup group;

	@FXML
	private RadioButton card, kakao, samsung, apple, naver, toss;

	@FXML
	private CheckBox terms1, terms2, terms3, terms4;

	@FXML
	private Button pay;

	@FXML
	private TextField price, // 가격
			seat, // 좌석
			musical, // 뮤지컬 이름
			date, // 날짜
			time; // 시간

	@FXML
	private Hyperlink trade, info, les, SMS;

	// 현재 payController 무대 정보 추가 setStage(stage);
	private Stage stage;

	// 좌석 예약 스테이지 추가
	private Stage reservStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.payController = this;
		
		// PayMain 화면에서 textfield 에 예매자의 구매 정보를 불러옴 
		
		DecimalFormat format = new DecimalFormat("#,###");
		price.setText(format.format(Main.reservTicket.getPay()));
		seat.setText(Main.reservTicket.getSeatNum());
		musical.setText(Main.reservTicket.getMusical());
		date.setText(Main.reservTicket.getDate());
		time.setText(Main.reservTicket.getTime());

		// 결제하기 버튼을 눌렀을 때 발생하는 이벤트
		pay.setOnAction((e) -> {

			// 필수 약관 동의에 체크를 하고 결제하기를 눌렀을 때만 다음 화면으로 넘어감.
			if (terms1.isSelected() && terms2.isSelected() && terms3.isSelected()) {
				// server에 결제 등록 정보 전송
				TicketVO vo = Main.reservTicket;
				String regReserv = "2|0|" + vo.getUserID() + "," + vo.getMusical() + "," + vo.getSeatNum() + ","
						+ vo.getPay() + "," + vo.getDate() + "," + vo.getTime();
				System.out.println(regReserv);
				Main.thread.sendData(regReserv);
				
				// 체크를 하지 않으면 약관에 동의하지 않았다는 화면이 새로 뜸.
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("필수 약관 동의");
				alert.setHeaderText("약관 동의에 실패했습니다.");
				alert.setContentText("모든 필수 약관에 동의 해주세요.\n문제가 발생했을 시 관리자에게 문의 해주세요.");
				alert.show();
			} // 필수 약관 if 문 끝
			
		}); // 결제하기 이벤트 끝

		// 전자금융거래 기본약관 상세보기 하이퍼링크
		trade.setOnAction(e -> {
			trade.setUserData(new String("https://obank.kbstar.com/quics?page=C021903&cc=b028364:b038204"));
			String link = (String) trade.getUserData();

			WebView webView = new WebView();
			WebEngine we = webView.getEngine();
			we.load(link);

			Stage stage = new Stage();
			BorderPane pane = new BorderPane();
			pane.setCenter(webView);
			stage.setScene(new Scene(pane));
			stage.setTitle("전자금융거래 기본약관 설명서");
			stage.setWidth(1500);
			stage.setHeight(800);
			stage.show();

		}); // 하이퍼링크 끝

		// 개인정보 수집 및 이용 동의 상세보기 하이퍼링크
		info.setOnAction(e -> {
			trade.setUserData(new String("https://www.kakao.com/policy/privacy?type=p&lang=ko"));
			String link = (String) trade.getUserData();

			WebView webView = new WebView();
			WebEngine we = webView.getEngine();
			we.load(link);

			Stage stage = new Stage();
			BorderPane pane = new BorderPane();
			pane.setCenter(webView);
			stage.setScene(new Scene(pane));
			stage.setTitle("개인정보 수집 및 이용 설명서");
			stage.setWidth(1500);
			stage.setHeight(800);
			stage.show();

		}); // 하이퍼링크 끝

		// 레미제라블 이용약관 동의 상세보기 하이퍼링크
		les.setOnAction(e -> {
			trade.setUserData(new String(
					"https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=wnalswl1&logNo=221468226786"));
			String link = (String) trade.getUserData();

			WebView webView = new WebView();
			WebEngine we = webView.getEngine();
			we.load(link);

			Stage stage = new Stage();
			BorderPane pane = new BorderPane();
			pane.setCenter(webView);
			stage.setTitle("레미제라블 이용약관 설명서");
			stage.setScene(new Scene(pane));
			stage.setWidth(1500);
			stage.setHeight(800);
			stage.show();

		}); // 하이퍼링크 끝

		// 마케팅 메일, SMS 수신 동의 상세보기 하이퍼링크
		SMS.setOnAction(e -> {
			trade.setUserData(new String("https://www.heybunny.io/marketing"));
			String link = (String) trade.getUserData();

			WebView webView = new WebView();
			WebEngine we = webView.getEngine();
			we.load(link);

			Stage stage = new Stage();
			BorderPane pane = new BorderPane();
			pane.setCenter(webView);
			stage.setScene(new Scene(pane));
			stage.setTitle("마케팅 메일, SMS 수신 동의 설명서");
			stage.setWidth(1500);
			stage.setHeight(800);
			stage.show();

		}); // 하이퍼링크 끝

	} // 이니셜라이즈 끝

	// 이거도 모름
	public void addPay() {

		PayVO p = new PayVO(price, date, musical, seat);
		Main.thread.sendData("0|1|" + p);

	}

	// 서버에서 데이터 받아오기
	// 데이터가 불러와지지 않으면 결제 완료 창이 뜨지 않음
	@Override
	public void receiveData(String message) {
		System.out.println("receive pay : " + message);
		String paying = message.split("\\|")[2];
		// 2|0|true, 2|0|false
		if (Boolean.parseBoolean(paying)) {
			Platform.runLater(() -> {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pay/PayDone.fxml"));

					Parent root1 = fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL); // 팝업처럼 화면이 뜸
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setTitle(Main.reservTicket.getMusical()+" 결제 완료");
					stage.setScene(new Scene(root1));
					PayDoneController controller = fxmlLoader.getController();
					controller.setStage(this.stage, stage, this.reservStage);
					stage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
		} else {
			System.out.println("결제 실패");
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setContentText("결제가 실패 하였습니다. 다시 확인해 주세요.");
				alert.show();
			});
		}

	}

	public void setStage(Stage payStage, Stage reservStage) {
		this.stage = payStage;
		this.reservStage = reservStage;

	}
}
