package pay;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Receivable;

public class PayCheckController implements Initializable, Receivable {

	@FXML Label name, date, musical, seat;
	@FXML Button btnEnd, btnCancel;

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
		
		// MySQL 연결
//			try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				System.out.println("driver 존재");
//				
//				Properties prop = new Properties();
//				
//				// 임의로 DB를 만들어서 주소를 정함
//				// 주소만 바꾸면 연동 될 것 같아요
//				prop.load(new FileReader("src/pay/DB/mysql.properties")); 
//				
//				System.out.println(prop);
//				
//				conn = DriverManager.getConnection(prop.getProperty("url"),prop);
//				System.out.println(conn);
//				
//				// 임의로 만든 DB에서 선택한 데이터만 찾는 수식
//				String sql = "" +
//				 "SELECT name, price, seat, musical, date from dummy where userID=?";
//				
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				// 아이디가 h 인 사람의 가격, 좌석, 예매한 작품, 날짜 정보 구현
//				// 나중에 로그인 정보와 맞추면 될 듯
//				// 어덯게 하지..?
//				pstmt.setString(1, "h");	
//				ResultSet rs = pstmt.executeQuery();
//				if(rs.next()) {
//					String userID = rs.getString("userID");
//					// 가격 정보
//					String price = rs.getString("price");
//					// 좌석 정보
//					String seat = rs.getString("seat");
//					// 뮤지컬 정보
//					String musical = rs.getString("musical");
//					// 날짜 정보
//					String date = rs.getString("date");
//					
//					// 확인용 
//					System.out.println(userID+":"+price+":"+seat+":"+musical+":"+date);
//					
//				}else {
//					System.out.println("존재X");
//				}
//				
//				name.setText(rs.getString("userID"));
//				seat.setText(rs.getString("seat"));
//				musical.setText(rs.getString("musical"));
//				date.setText(rs.getString("date"));
//					
//				} catch (FileNotFoundException e) {
//					System.out.println("file not found");
//					
//				} catch (IOException e) {
//					System.out.println("ioe err");
//					
//				} catch (SQLException e1) {
//					System.out.println("sql error");
//					
//				} catch (ClassNotFoundException e1) {
//					System.out.println("class not found");
//	
//				}
		
			
			
			// 확인 버튼으로 메인화면 이동
			// 이전에 열려 있던 창은 닫게 하고 싶은데 방법을 모름
			
			btnEnd.setOnAction((e)->{
				Stage end = (Stage)btnEnd.getScene().getWindow();
				end.close();
				payStage.close();
				payDoneStage.close();
				reservStage.close();
			});
				
			
			btnCancel.setOnAction((e)->{
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("예매 취소");
				alert.setHeaderText("정말 예매를 취소하시겠습니까?");
				alert.setContentText("이전에 예매하신 내역은 모두 삭제됩니다.\n저희 열심히 준비했는데 ㅠㅠ");
				Optional<ButtonType> result = alert.showAndWait();
				
				
				if(result.get() == ButtonType.OK) {
					System.out.println("예매 취소");
					Alert alert1 = new Alert(Alert.AlertType.WARNING);
					alert1.setTitle("예매 취소 확인");
					alert1.setHeaderText("예매가 취소되었습니다.");
					alert1.setContentText("다음에 다시 만나요 ㅠㅠ");
					Optional<ButtonType> result1 = alert1.showAndWait();
					if(result1.get() == ButtonType.OK) {
						// Platform.exit();		// 바꿔야 확인 눌렀을때 안사라짐
						// 예매 취소를 눌러서 DB에 delete table where = ? 가 작동되게 해야 함
						// 현재창 
						Stage now = (Stage)btnCancel.getScene().getWindow();
						now.close();
						payStage.close();
						payDoneStage.close();
						reservStage.close();
						
						// 다시 메인 포스터 화면으로 이동
						// 원래 있던 창을 다시 끄고 싶은데 방법을 모름
						
					}
					
				}else {
					System.out.println("예매 취소를 취소");
					// 아무 일 없음
				}
			});
			
	}

	// 방법을 모름
	@Override
	public void receiveData(String message) {
		
		
	}

	public void setStage(Stage payStage, Stage payDoneStage, Stage reservStage) {
		this.payStage = payStage;
		this.payDoneStage = payDoneStage;
		this.reservStage = reservStage;
	}
	
}
