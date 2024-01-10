package member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.input.KeyCode;
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
		
		String userID = rememberMe();
		if(userID != null) {
			txtId.setText(userID);
		}
		
		Platform.runLater(() -> {
			txtId.requestFocus();
		});
		
		//아이디에서 Enter -> 비밀번호 창으로 포커스
		txtId.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) txtPw.requestFocus();
		});
		
		txtPw.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) {
				btnLogin.fire();
			}
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
					/*
					if(Main.castVO == null){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("예매하실 날짜와 시간을 먼저 선택해주세요.");
						alert.showAndWait();
						return;
						
					}
					*/
					Parent root;
					root = FXMLLoader.load(getClass().getResource("/post/Post.fxml"));
					stage.setScene(new Scene(root));
					stage.setTitle("예약하기");
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
		} // end if
	} // end receiveData
	
	
	// 사용자 컴퓨터에 txt파일로 사용자 아이디 저장
	public void rememberUserId(String userId) {
		try {
			File dir = new File("/member");
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir,"rememberMe.txt");
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getPath());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(userId.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 사용자 컴퓨터에 txt파일로 저장된 사용자 ID 읽어오기
	public String rememberMe() {
		File dir = new File("/member");
		File file = new File(dir,"rememberMe.txt");
		if(!dir.exists() || !file.exists()) {
			return null;
		}
		
		String userID = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			// 연결된 파일의 읽을 수 있는 byte 개수 만큼 배열 생성
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			if(bytes.length > 0) {
				userID = new String(bytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userID;
	}
	
	
}





