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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;

public class MemberController implements Initializable, Receivable {

	@FXML
	private TextField txtId, txtName, txtPw, txtRe, txtPhone;
	@FXML
	private Button btnJoin, buttonGo;
	@FXML
	private CheckBox check;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Main.thread.memberController = this;
		
		System.out.println("호출시 자동 실행");
		
		btnJoin.setOnAction(e -> join());

        buttonGo.setOnAction(e->{
			try {
				// 회원 페이지 이동
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/member/Login.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Join page");
				stage.show();
				
				// Member Stage close
				Stage memberStage = (Stage)btnJoin.getScene().getWindow();
				memberStage.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
		
	

	public void join() {

		String id = txtId.getText();
		String name = txtName.getText();
		String pw = txtPw.getText();
		String rPw = txtRe.getText();
		String phone =txtPhone.getText();
		
		if(id.length() == 0) {
			alert("아이디를 입력해주세요.");
			// 빈문자열
			txtId.requestFocus();
			return;
		}
		if(name.length() == 0) {
			alert("이름을 입력해주세요.");
			txtName.requestFocus();
			return;
		}
		if(pw.length() == 0) {
			alert("비밀번호를 입력해주세요.");
			txtPw.requestFocus();
			return;
		}
		if(rPw.length() == 0) {
			alert("비밃번호를 확인해주세요.");
			txtRe.requestFocus();
			return;
		}
		if(phone.length() == 0) {
			alert("전화번호를 입력해주세요.");
			txtPhone.requestFocus();
			return;
		}

		if (!rPw.equals(pw) ) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			alert("비밀번호가 일치하지 않습니다.");
			return;
		}

		MemberVO m = new MemberVO(id,pw,name,phone);
		Main.thread.sendData("0|1|"+m);
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
	 * 회원 관련 요청 처리에 대한 결과를 server 에서 receive
	 */
	@Override
	public void receiveData(String message) {
		System.out.println("MemberController : " + message);
		//0|1|true, 0|1|false
		String isJoin = message.split("\\|")[2];
		if(Boolean.parseBoolean(isJoin)) {
			System.out.println("회원가입 완료");
			alert("회원가입 완료");
			// 화면 전환 -> 로그인
			Platform.runLater(()->{
				buttonGo.fire();
			});
		}else {
			// 회원가입 실패
			alert("회원가입 실패");
			Platform.runLater(()->{
				txtId.clear();
				txtPw.clear();
				txtName.clear();
				txtRe.clear();
				txtId.clear();
				txtPhone.clear();
				txtId.requestFocus();
			});
		}
	}
}
