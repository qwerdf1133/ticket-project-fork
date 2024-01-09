package pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Receivable;

public class PayController implements Initializable, Receivable {
	
	Connection conn = null;
	Statement stmt = null;
	
	// 서버 소켓
	Socket server;
	InetAddress ip;
	int port;
	BufferedReader br;
	
	@FXML private ToggleGroup group;
	
	@FXML private RadioButton card, kakao, samsung,	apple, naver, toss;		
	
	@FXML private CheckBox terms1, terms2, terms3, terms4;
	
	@FXML private Button pay;				
	
	@FXML private TextField price,			// 가격
							seat,			// 좌석				
							musical,		// 뮤지컬 이름
							date;			// 날짜
	
	@FXML private Hyperlink trade, info, les, SMS;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.payController = this;
		
		// 결제 화면에서 MySQL 연결
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
			
//			Properties prop = new Properties();
			
			// 임의로 DB를 만들어서 주소를 정함
//			prop.load(new FileReader("src/pay/DB/mysql.properties")); 
//			
//			System.out.println(prop);
			
//				);
			
//			conn = DriverManager.getConnection(prop.getProperty("url"),prop);
//			System.out.println(conn);
			
			// 임의로 만든 DB에서 선택한 데이터만 찾는 수식
//			String sql = "" +
//			 "SELECT name, price, seat, musical, date from pay";
			
//			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 아이디가 h 인 사람의 가격, 좌석, 예매한 작품, 날짜 정보 구현
			// 나중에 로그인 정보와 맞추면 될 듯
			// 어덯게 하지..?
//			pstmt.setString(1, "a");	
				
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) {
//				// 가격 정보
//				String price = rs.getString("price");
//				// 좌석 정보
//				String seat = rs.getString("seat");
//				// 뮤지컬 정보
//				String musical = rs.getString("musical");
//				// 날짜 정보
//				String date = rs.getString("date");
//				
//				// 확인용 
//				System.out.println(price+":"+seat+":"+musical+":"+date);
//				
//				
//			}else {
//				System.out.println("존재X");
//			}
//			
//			// 텍스트 필드에게 mysql 정보를 불러오기
//			price.setText(rs.getString("price"));
//			seat.setText(rs.getString("seat"));
//			musical.setText(rs.getString("musical"));
//			date.setText(rs.getString("date"));
				
//			} catch (FileNotFoundException e) {
//				System.out.println("file not found");
				
//			} catch (IOException e) {
//				System.out.println("ioe err");
				
//			} catch (SQLException e1) {
//				System.out.println("sql error");
//				
//			} catch (ClassNotFoundException e1) {
//				System.out.println("class not found");
//			}
				
		// 결제하기 버튼을 눌렀을 때 발생하는 이벤트
		pay.setOnAction((e)->{
			
			// 필수 약관 동의에 체크를 하고 결제하기를 눌렀을 때만 다음 화면으로 넘어감.
			if(terms1.isSelected() && terms2.isSelected() && terms3.isSelected()) {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pay/PayDone.fxml"));
			Parent root1;
			Stage stage;
			
			try {
				root1 = (Parent) fxmlLoader.load();
				stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL); // 팝업처럼 화면이 뜸
				stage.initStyle(StageStyle.UNDECORATED);		
				stage.setTitle("레미제라블 결제 완료");
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			};
			
			// 체크를 하지 않으면 약관에 동의하지 않았다는 화면이 새로 뜸.
			}else {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("필수 약관 동의");
				alert.setHeaderText("약관 동의에 실패했습니다.");
				alert.setContentText("모든 필수 약관에 동의 해주세요.\n문제가 발생했을 시 관리자에게 문의 해주세요.");
				Optional<ButtonType> result = alert.showAndWait();
				
				if(result.get() == ButtonType.OK) {
					System.out.println("실패 확인창");
				}else {
					System.out.println("실패 거절창");
				}
			} // 필수 약관 if 문 끝
		}); // 결제하기 이벤트 끝 
		
		// 전자금융거래 기본약관 상세보기 하이퍼링크 
		trade.setOnAction(e->{
			trade.setUserData(new String("https://obank.kbstar.com/quics?page=C021903&cc=b028364:b038204"));
			String link = (String)trade.getUserData();
			
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
		info.setOnAction(e->{
			trade.setUserData(new String("https://www.kakao.com/policy/privacy?type=p&lang=ko"));
			String link = (String)trade.getUserData();
			
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
		les.setOnAction(e->{
			trade.setUserData(new String("https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=wnalswl1&logNo=221468226786"));
			String link = (String)trade.getUserData();
			
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
		SMS.setOnAction(e->{
			trade.setUserData(new String("https://www.heybunny.io/marketing"));
			String link = (String)trade.getUserData();
			
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

	public void addPay() {
	
		PayVO p = new PayVO(price, date, musical, seat);
		Main.thread.sendData("0|1|"+p);
	
	}
	
	// 서버에서 데이터 받아오기
	@Override
	public void receiveData(String message) {
		
		System.out.println("receive pay : " +message);
		String paying = message.split("\\|")[2];
		if(Boolean.parseBoolean(paying)) {
			System.out.println("내가 뭘 하고 있는거야");
			Platform.runLater(()->{
				pay.fire();
			});
		}else {
			System.out.println("결제 실패");
				
		}
		
	}
}
