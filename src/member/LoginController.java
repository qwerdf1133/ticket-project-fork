package member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;

public class LoginController implements Initializable, Receivable {

	@FXML
	private TextField txtId, txtPw;
	@FXML
	private Button btnLogin, btnMember;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.thread.loginController = this;
		
		
		Platform.runLater(() -> {
			btnLogin.requestFocus();
		});

		btnLogin.setOnAction(e -> login());
		
		btnMember.setOnAction(e->{
			try {
				// 회원 페이지 이동
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/member/Member.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Login page");
				stage.show();
				
				// Login Stage close
				Stage loginStage = (Stage)btnMember.getScene().getWindow();
				loginStage.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	public void login() {
		if (txtId.getText().isEmpty() || txtPw.getText().isEmpty()) {
			System.out.println("아이디 혹은 비밀번호를 입력하지 않았습니다!");
			txtId.clear();
			txtPw.clear();
			txtId.requestFocus();
			return;
		}
		
		// 로그인에 필요한 정보로 서버에 로그인 요청
		Main.thread.sendData("0|0|"+txtId.getText().trim()+","+txtPw.getText().trim());
		
	}
	
	
	public void alert(String text){
		Platform.runLater(()->{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText(text);
			alert.showAndWait();
		});
	}

	/**
	 * server에서 전달된 로그인 요청에 대한 처리
	 */
	@Override
	public void receiveData(String message) {
		System.out.println("LoginController : " + message);
		// 로그인 실패 0|0|0
		// 로그인 성공 0|0|1|bobo17502,33332222,정순구,010-4143-0555
		String[] order = message.split("\\|");
		String isLogin = order[2];
		if(isLogin.equals("0")) {
			alert("로그인 실패");
			Platform.runLater(()->{
				// 로그인 실패
				txtId.clear();
				txtPw.clear();
				txtId.requestFocus();
			});

		}else if(isLogin.equals("1")) {
			// 로그인 성공
			// receive login data
			// page 이동
			// 로그인된 사용자 정보 저장.
			String[] loginMember = order[3].split(",");
			System.out.println(loginMember);
			Main.loginMember = new MemberVO(loginMember[0],loginMember[1],loginMember[2],loginMember[3]);
			
			Platform.runLater(()->{
				// 로그인 무대 종료
				Stage loginStage = (Stage)txtId.getScene().getWindow();
				loginStage.close();
				try {
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					Parent root;
					root = FXMLLoader.load(getClass().getResource("/reservation/Reservation.fxml"));
					stage.setScene(new Scene(root));
					stage.setTitle("예약하기");
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
		}
	}
}






